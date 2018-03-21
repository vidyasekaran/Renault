/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000030
 * Module          : Ordering					
 * Process Outline :Create Monthly Order Take Base Period
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.b000008.util.B000008Constants.CONSTANT_V4;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P2Output;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.b000008.util.B000008QueryConstants;
import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.util.B000020QueryConstants;
import com.nissangroups.pd.b000030.util.B000030Constants;
import com.nissangroups.pd.b000030.util.B000030QueryConstants;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class MnthlyOrdrRepository.
 *
 * @author z015060
 */
public class MnthlyOrdrRepository {

	public MnthlyOrdrRepository() {

	}

	/** Variable entity manager. */
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(MnthlyOrdrRepository.class
			.getName());

	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	
	/**
	 * @param mstMnthData
	 * @return
	 * @throws ParseException
	 */
	public List<Object[]> overLapCheck(MstMnthOrdrTakeBasePd mstMnthData)
			throws ParseException {
		String overLapPeriod = B000008QueryConstants.checkOverlapPeriodExists
				.toString();
		Date ordrtkBsMnth = formatter.parse(mstMnthData.getId()
				.getOrdrTakeBaseMnth().toString());
		String preOrdrtkBsMnth = formatter.format(CommonUtil.addMonthToDate(
				ordrtkBsMnth, -1));
		Query query = entityMngr.createQuery(overLapPeriod);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, preOrdrtkBsMnth);
		query.setParameter(B000008Constants.STAGE_CODE, PDConstants.CONSTANT_SC);
		query.setParameter(PDConstants.PRMTR_PORCD, mstMnthData.getId()
				.getPorCd());
		//Result will have columns m.id.ordrTakeBaseMnth,m.stageCd FROM MstMnthOrdrTakeBasePd m
		return query.getResultList();
	}

	
	/**
	 * @param porCd
	 * @return
	 */
	public List<Object[]> chkHrzn(String porCd) {
		String carSrsHrznQuery = B000008QueryConstants.getCarSeriesHorizon
				.toString();
		Query carSrsQuery = entityMngr.createQuery(carSrsHrznQuery);
		carSrsQuery.setParameter(PDConstants.PRMTR_PORCD, porCd);
		LOG.info("Process P0003.1 SIZE " + carSrsQuery.getResultList().size());
		//Result array will have columns m.id.porCd, m.id.prodFmyCd, m.id.carSrs, m.carSrsOrdrHrzn FROM MstPorCarSr m
		List<Object[]> results = carSrsQuery.getResultList();
		if (results.size() == 0) {
			LOG.info(B000008Constants.INPUT_PARAM_FAILURE_6);
			CommonUtil.stopBatch();
		}
		return carSrsQuery.getResultList();
	}

	/**
	 * @param porCd
	 * @return
	 */
	public String getPorHorizon(String porCd) {
		String porHrzn = B000008QueryConstants.getPorHorizon.toString();
		String porHrznVal = null;
		Query porHorizonQuery = entityMngr.createQuery(porHrzn);
		porHorizonQuery.setParameter(PDConstants.PRMTR_PORCD, porCd);
		List<Object[]> results = porHorizonQuery.getResultList();
		//Result array will have columns m.id.porCd, m.id.prodFmyCd, m.id.carSrs, m.carSrsOrdrHrzn FROM MstPorCarSr m
		if (!results.isEmpty() && results.get(0)[0] != null) {
			porHrznVal = results.get(0)[0].toString();
		} else {
			CommonUtil
					.logErrorMessage(PDMessageConsants.M00160, CONSTANT_V4,
							new String[] { B000008Constants.BATCH_ID_B000008,
									PDConstants.POR_HORIZON, porCd,
									PDConstants.POR_MST });
			CommonUtil.stopBatch();
		}
		LOG.info("Process P0003.2 SIZE  " + porHrznVal.length());
		return porHrznVal;
	}

	
	/**
	 * @param objOutput
	 * @return
	 */
	public List<Object[]> getPreStageOrdrQtyData(B000008Output objOutput) {
		String preStageOrdrQuery = B000008QueryConstants.getPreStageOrdrQtyQuery
				.toString();
		List<Object[]> orderInfo = new ArrayList<Object[]>();
		String frznCd = null;
		for (Object[] newObj : objOutput.getObjB000008P1Output()
				.getSelectOverlap()) {
			Query query = entityMngr.createNativeQuery(preStageOrdrQuery);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, newObj[0]);
			query.setParameter(PDConstants.PRMTR_PORCD, objOutput
					.getObjB000008ParamOutput().getPorCd());
			query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
					newObj[1]);
			List<Object[]> selectResultSet = query.getResultList();
			//Result array will have columns POR_CD,PROD_MNTH,OSEI_ID,POT_CD,ORDR_QTY,ORDRTK_BASE_MNTH from TRN_MNTHLY_ORDR
			String frznTypeCdQuery = B000008QueryConstants.getfrznTypeCdQty
					.toString();
			for (Object[] select : selectResultSet) {
				Query queryFrznCd = entityMngr
						.createNativeQuery(frznTypeCdQuery);
				queryFrznCd.setParameter(PDConstants.OSEIID_PREFIX, select[2]);
				queryFrznCd.setParameter(PDConstants.PRMTR_PORCD, select[0]);
				queryFrznCd.setParameter(PDConstants.FROZEN_PRODUCTION_MONTH,
						select[1]);
				queryFrznCd.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						select[5]);
				// value is FRZN_TYPE_CD as temp production type code
				if (!queryFrznCd.getResultList().isEmpty()) {
					frznCd = PDConstants.CONSTANT_ONE;
				} else {
					frznCd = PDConstants.CONSTANT_ZERO;
				}
				orderInfo.add(new Object[] { select[0].toString(),
						select[1].toString(), select[4].toString(),
						select[2].toString(), select[3].toString(), frznCd });
			}
		}
		if (orderInfo.size() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00162,
					B000008Constants.CONSTANT_V6, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.PRMTR_ORDER,
							objOutput.getObjB000008ParamOutput().getPorCd(),
							objOutput.getObjReaderOutput().getOrdrTkBsMnth(),
							PDConstants.LATESET_MASTER_SCHDULE_TRN,
							B000008Constants.PROCESS_P6 });
		}
		return orderInfo;
	}

	
	/**
	 * @param objOutput
	 * @return
	 */
	public List<Object[]> getOrdrInfoData(B000008Output objOutput) {
		List<Object[]> orderInfoData = new ArrayList<Object[]>();
		String frznCd = null;
		String masterTrnOrdrQuery = B000008QueryConstants.getMasterTrnOrdrQuery
				.toString();
//		for (Object[] masterTrnList : objOutput.getObjB000008P1Output()
//				.getMaxPrdList()) {
			Query query = entityMngr.createNativeQuery(masterTrnOrdrQuery);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					objOutput.getObjReaderOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD, objOutput.getObjReaderOutput().getPorCd());
			List<Object[]> selectResultSet = query.getResultList();

			//Result array will have columns t.POR_CD,t.PROD_MNTH,count(t.PROD_ORDR_NO),t.OSEI_ID,t.POT_CD,m.FRZN_TYPE_CD from TRN_LTST_MST_SHDL t
			for (Object[] selectMstTrn : selectResultSet) {
				if (selectMstTrn[5] != null) {
					frznCd = PDConstants.CONSTANT_ONE;
				} else {
					frznCd = PDConstants.CONSTANT_ZERO;
				}
				orderInfoData.add(new Object[] { selectMstTrn[0].toString(),
						selectMstTrn[1].toString(), selectMstTrn[2].toString(),
						selectMstTrn[3].toString(), selectMstTrn[4].toString(),
						frznCd });
//			}
		}
		if (orderInfoData.size() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00162,
					B000008Constants.CONSTANT_V6, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.PRMTR_ORDER,
							objOutput.getObjB000008ParamOutput().getPorCd(),
							objOutput.getObjReaderOutput().getOrdrTkBsMnth(),
							PDConstants.LATESET_MASTER_SCHDULE_TRN,
							B000008Constants.PROCESS_P6 });
		}
		return orderInfoData;
	}

	/**
	 * @param objOutput
	 */
	public void updatePrdStageCd(B000008Output objOutput) {
		if (objOutput.getObjB000008ParamOutput().getPrdStgCd()
				.equals(PDConstants.TWENTY)) {
			String updateProdStageCdQuery = B000008QueryConstants.updateProdStageCdQuery
					.toString();
			try {
				Query query = entityMngr
						.createNativeQuery(updateProdStageCdQuery);
				query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, objOutput
						.getObjReaderOutput().getOrdrTkBsMnth());
				query.setParameter(PDConstants.PRMTR_PORCD, objOutput
						.getObjReaderOutput().getPorCd());
				query.setParameter(PDConstants.PRMTR_UPDT_BY,
						B000008Constants.BATCH_ID_B000008);
				query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
						objOutput.getObjB000008ParamOutput().getPrdStgCd());
				query.executeUpdate();
				CommonUtil.logMessage(PDMessageConsants.M00163,
						B000008Constants.CONSTANT_V3, new String[] {
								B000008Constants.BATCH_ID_B000008,
								PDConstants.UPDATED,
								PDConstants.MONTHLY_ORDER_TRN });
			} catch (Exception e) {
				LOG.error(e);
				CommonUtil.logErrorMessage(PDMessageConsants.M00164,
						B000008Constants.CONSTANT_V3, new String[] {
								B000008Constants.BATCH_ID_B000008,
								PDConstants.UPDATION,
								PDConstants.MONTHLY_ORDER_TRN });
				CommonUtil.stopBatch();
			}
		}
	}

	
	/**
	 * @param prdStgCd
	 * @param ordrTkBsMnth
	 * @param objB000008P2Output
	 */
	public void insertMthOrdrTrnOrdrInfo(String prdStgCd, String ordrTkBsMnth,
			B000008P2Output objB000008P2Output) {
		String insertMonthlyOrderTrn = B000008QueryConstants.insertMonthlyOrderTrnQuery
				.toString();
		String updateMonthlyOrderTrn = B000008QueryConstants.updateMonthlyOrderTrnQuery
				.toString();
		String chekDupinMnthOrdr = B000008QueryConstants.chekDupinMnthOrdrQuery
				.toString();

		for (Object[] selectMnthtMstTrn : objB000008P2Output.getOrdrInfo()) {
			Query selQuery = entityMngr.createNativeQuery(chekDupinMnthOrdr);
			selQuery.setParameter(PDConstants.PRMTR_PORCD, selectMnthtMstTrn[0]);
			selQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					ordrTkBsMnth);
			selQuery.setParameter(PDConstants.PRODUCTION_MONTH,
					selectMnthtMstTrn[1]);
			selQuery.setParameter(PDConstants.OSEIID_PREFIX,
					selectMnthtMstTrn[3]);
			selQuery.setParameter(PDConstants.PRMTR_POT_CD,
					selectMnthtMstTrn[4]);
			selQuery.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
					prdStgCd);
			String getDupChk =  selQuery.getSingleResult().toString();
			if (getDupChk.equals(PDConstants.PRMTR_ZERO)) {
				try {
					Query query = entityMngr
							.createNativeQuery(insertMonthlyOrderTrn);
					query.setParameter(PDConstants.PRMTR_PORCD,
							selectMnthtMstTrn[0]);
					query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
							ordrTkBsMnth);
					query.setParameter(PDConstants.PRODUCTION_MONTH,
							selectMnthtMstTrn[1]);
					query.setParameter(PDConstants.OSEIID_PREFIX,
							selectMnthtMstTrn[3]);
					query.setParameter(PDConstants.PRMTR_POT_CD,
							selectMnthtMstTrn[4]);
					query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
							prdStgCd);
					query.setParameter(PDConstants.PRMTR_DRAFT_QTY,
							PDConstants.CONSTANT_ZERO);
					query.setParameter(PDConstants.PRMTR_MS_QTY,
							selectMnthtMstTrn[4]);
					if (selectMnthtMstTrn[5].equals(PDConstants.PRMTR_ONE)) {
						query.setParameter(PDConstants.PRMTR_ORDR_QTY,
								selectMnthtMstTrn[5]);
					} else {
						query.setParameter(PDConstants.PRMTR_ORDR_QTY,
								PDConstants.PRMTR_ZERO);
					}
					query.setParameter(PDConstants.PRMTR_SIMU_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(B000008Constants.AUTO_ADJUST_ORDER_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(B000008Constants.SUSPENDED_ORDER_FLAG,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_CRTD_BY,
							B000008Constants.BATCH_ID_B000008);
					query.executeUpdate();
					CommonUtil.logMessage(PDMessageConsants.M00163,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.INSERTED,
									PDConstants.MONTHLY_ORDER_TRN });
				} catch (Exception e) {
					LOG.error(e);
					CommonUtil.logErrorMessage(PDMessageConsants.M00164,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.INSERTION,
									PDConstants.MONTHLY_ORDER_TRN });
					CommonUtil.stopBatch();
				}
			} else {
				try {
					Query updateQuery = entityMngr
							.createNativeQuery(updateMonthlyOrderTrn);
					updateQuery.setParameter(PDConstants.PRMTR_PORCD,
							selectMnthtMstTrn[0]);
					updateQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
							ordrTkBsMnth);
					updateQuery.setParameter(PDConstants.PRODUCTION_MONTH,
							selectMnthtMstTrn[1]);
					updateQuery.setParameter(PDConstants.OSEIID_PREFIX,
							selectMnthtMstTrn[3]);
					updateQuery.setParameter(PDConstants.PRMTR_POT_CD,
							selectMnthtMstTrn[4]);
					updateQuery.setParameter(
							PDConstants.PRMTR_PRODUCTION_STAGE_CODE, prdStgCd);
					updateQuery.setParameter(PDConstants.PRMTR_MS_QTY,
							selectMnthtMstTrn[2]);
					updateQuery.setParameter(PDConstants.PRMTR_UPDT_BY,
							B000008Constants.BATCH_ID_B000008);
					if (selectMnthtMstTrn[5].equals(PDConstants.PRMTR_ONE)) {
						updateQuery.setParameter(PDConstants.PRMTR_ORDR_QTY,
								selectMnthtMstTrn[5]);
					} else {
						updateQuery.setParameter(PDConstants.PRMTR_ORDR_QTY,
								PDConstants.CONSTANT_ZERO);
					}
					updateQuery.executeUpdate();
					CommonUtil.logMessage(PDMessageConsants.M00163,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATED,
									PDConstants.MONTHLY_ORDER_TRN });
				} catch (Exception e) {
					LOG.error(e);
					CommonUtil.logErrorMessage(PDMessageConsants.M00164,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATION,
									PDConstants.MONTHLY_ORDER_TRN });
					CommonUtil.stopBatch();
				}
			}
		}

	}

	
	/**
	 * @param prmtrCd
	 * @param key1
	 * @return
	 */
	public List<String> extractValFrmMstPrmtr(String prmtrCd, String key1) {
		String extractPrdStg = B000008QueryConstants.extractFrmMstPrmtr
				.toString();
		List<String> names = new ArrayList();
		Query query = entityMngr.createNativeQuery(extractPrdStg);
		query.setParameter(PDConstants.PARAMETER_CD, prmtrCd);
		query.setParameter(PDConstants.KEY_1, key1);
		List<Object[]> selectResultSet = query.getResultList();
		if (selectResultSet.size() > 0) {
			for (Object[] select : selectResultSet) {
				if (select[0] != null) {
					names.add(select[0].toString());
				}
			}
			return names;
		} else {
			return null;
		}
	}

	/**
	 * Extract orderable End Item Process P0006.2
	 * 
	 * @param objOutput
	 * @param object
	 * @return por_cd,OSEI_ID, BUYER_GRP_CD
	 */
	public List<Object[]> getOrdrableEndItem(B000008Output objOutput,
			B000008P2Output object) {
		String getOrdrableEndItem = B000008QueryConstants.getOrdrableEndItemQuery
				.toString();
		String getOrdrableEndItemp1 = B000008QueryConstants.getOrdrableEndItemQueryp1
				.toString();
		String getOrdrableEndItemp2 = B000008QueryConstants.getOrdrableEndItemQueryp2
				.toString();
		String getOrdrableEndItemp3 = B000008QueryConstants.getOrdrableEndItemQueryp3
				.toString();
		String getOrdrableEndItemp4 = B000008QueryConstants.getOrdrableEndItemQueryp4
				.toString();
		if (objOutput.getObjB000008ParamOutput().getUpdateOnlyFlg()
				.equals(PDConstants.Y)) {
			getOrdrableEndItem += getOrdrableEndItemp1;
		}
		getOrdrableEndItem += getOrdrableEndItemp2;
		if (objOutput.getObjB000008ParamOutput().getUpdateOnlyFlg()
				.equals(PDConstants.Y)) {
			getOrdrableEndItem += getOrdrableEndItemp3;
		}
		getOrdrableEndItem += getOrdrableEndItemp4;
		List<Object[]> ordrableEndItem = new ArrayList<Object[]>();
		List<String> names = object.getPorPrdStgeCd();
		for (Object[] selectedList : objOutput.getObjB000008P1Output()
				.getMaxPrdList()) {
			Query query = entityMngr.createNativeQuery(getOrdrableEndItem
					.toString());
			query.setParameter(PDConstants.PRMTR_PORCD, selectedList[0]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE, names);
			query.setParameter(PDConstants.PRMTR_CARSRS, selectedList[2]);
			query.setParameter(PDConstants.PRODUCTION_MONTH, selectedList[3]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE,
					selectedList[1]);
			if (objOutput.getObjB000008ParamOutput().getUpdateOnlyFlg()
					.equals(PDConstants.Y)) {
				query.setParameter(PDConstants.PRMTR_BATCH_ID,
						B000008Constants.BATCH_ID_B000008);
				query.setParameter(PDConstants.PRMTRT_TABLE_NAME,
						PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST);
			}
			List<Object[]> selectResultSet = query.getResultList();
			for (Object[] selectMstTrn : selectResultSet) {
				ordrableEndItem
						.add(new Object[] { selectMstTrn[0].toString(),
								selectedList[4].toString(),
								selectedList[3].toString(),
								selectMstTrn[1].toString(),
								selectMstTrn[2].toString() });
			}
		}
		if (ordrableEndItem.size() == 0) {
			if (objOutput.getObjB000008ParamOutput().getUpdateOnlyFlg()
					.equals(PDConstants.Y)) {
				CommonUtil
						.logMessage(
								PDMessageConsants.M00160,
								B000008Constants.CONSTANT_V4,
								new String[] {
										B000008Constants.BATCH_ID_B000008,
										PDConstants.UPDATED_END_ITEM,
										objOutput.getObjB000008ParamOutput()
												.getPorCd().toString(),
										PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST });
			} else {
				CommonUtil
						.logMessage(
								PDMessageConsants.M00160,
								B000008Constants.CONSTANT_V4,
								new String[] {
										B000008Constants.BATCH_ID_B000008,
										PDConstants.END_ITEM,
										objOutput.getObjB000008ParamOutput()
												.getPorCd().toString(),
										PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST });
			}
			CommonUtil.stopBatch();
		}
		LOG.info("Process P0006.2 OUTPUT IS " + ordrableEndItem.size());
		return ordrableEndItem;
	}

	/**
	 * Extraction of POT_CD from Parameter master based on POR and Buyer Group
	 * CD Process P0006.3, P0006.4
	 * 
	 * @param objOutput
	 * @param object
	 * @return
	 */
	public List<Object[]> extractPotCd(B000008Output objOutput,
			B000008P2Output object) {
		String potCd = PDConstants.EMPTY_STRING;
		String potCdQuery = B000008QueryConstants.getPotCdQuery.toString();
		List<String> cmnPotCd = new ArrayList();
		List<Object[]> potOrderableEndItmData = new ArrayList<Object[]>();
		for (Object[] potCdExtract : object.getOrderableEndItm()) {
			Query query = entityMngr.createNativeQuery(potCdQuery);
			query.setParameter(PDConstants.PARAMETER_CD,
					B000008Constants.PRMTR_POT_CD);
			query.setParameter(PDConstants.KEY_1, potCdExtract[0]);
			query.setParameter(PDConstants.KEY_2, potCdExtract[4]);
			List<Object[]> result = query.getResultList();
			//Result array will have val1,val2 from MST_PRMTR
			if (!result.isEmpty() && result.get(0)[0] != null) {
				potCd = result.get(0)[0].toString();
			} else {
				CommonUtil.logWarningMessage(
						PDMessageConsants.M00170,
						B000008Constants.CONSTANT_V6,
						new String[] { B000008Constants.BATCH_ID_B000008,
								B000008Constants.PRMTR_POT_CD,
								potCdExtract[0].toString(),
								potCdExtract[4].toString(),
								PDConstants.MESSAGE_MST_PARAMETER,
								B000008Constants.PROCESS_P6_4 });
				String extractPrdStg = B000008QueryConstants.extractFrmMstPrmtr
						.toString();
				Query queryDpot = entityMngr.createNativeQuery(extractPrdStg);
				queryDpot.setParameter(PDConstants.PARAMETER_CD,
						PDConstants.DEFAULT_POT_CD);
				queryDpot.setParameter(PDConstants.KEY_1, objOutput
						.getObjB000008ParamOutput().getPorCd());
				List<Object[]> resultPot = queryDpot.getResultList();
				if (!resultPot.isEmpty() && resultPot.get(0)[0] != null) {
					potCd = resultPot.get(0)[0].toString();
				} else {
					CommonUtil.logMessage(PDMessageConsants.M00160,
							B000008Constants.CONSTANT_V4, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.DEFAULT_POT_CD,
									objOutput.getObjB000008ParamOutput()
											.getPorCd(),
									PDConstants.MESSAGE_MST_PARAMETER });
					CommonUtil.stopBatch();
				}
			}
			potOrderableEndItmData.add(new Object[] {
					potCdExtract[0].toString(), potCdExtract[1].toString(),
					potCdExtract[2].toString(), potCdExtract[3].toString(),
					potCdExtract[4].toString(), potCd });
		}
		return potOrderableEndItmData;
	}

	/**
	 * Process Id P0007 Insert Into Month Order Trn PROCESS P0007
	 * 
	 * @param prdStgCd
	 * @param potOrderableEndItm
	 */
	public void insertMnthOrdrTrnOEIData(String prdStgCd,
			List<Object[]> potOrderableEndItm) {
		LOG.info("Process P0007" + potOrderableEndItm.size() + "--------");
		String insertMonthlyOrderTrnQuery = B000008QueryConstants.insertMonthlyOrderTrnfromOEIQuery
				.toString();
		String chkDupiacteQuery = B000008QueryConstants.chekDupinMnthOrdrQuery
				.toString();

		try {
			for (Object[] selectMnthtMstTrn : potOrderableEndItm) {
				Query selQuery = entityMngr.createNativeQuery(chkDupiacteQuery);
				selQuery.setParameter(PDConstants.PRMTR_PORCD,
						selectMnthtMstTrn[0]);
				selQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						selectMnthtMstTrn[1]);
				selQuery.setParameter(PDConstants.PRODUCTION_MONTH,
						selectMnthtMstTrn[2]);
				selQuery.setParameter(PDConstants.OSEIID_PREFIX,
						selectMnthtMstTrn[3]);
				selQuery.setParameter(PDConstants.PRMTR_POT_CD,
						selectMnthtMstTrn[5]);
				selQuery.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
						prdStgCd);
				String getDupChk = selQuery.getSingleResult().toString();
				if (getDupChk.equals(PDConstants.PRMTR_ZERO)) {
					Query query = entityMngr
							.createNativeQuery(insertMonthlyOrderTrnQuery);
					query.setParameter(PDConstants.PRMTR_PORCD,
							selectMnthtMstTrn[0]);
					query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
							selectMnthtMstTrn[1]);
					query.setParameter(PDConstants.PRODUCTION_MONTH,
							selectMnthtMstTrn[2]);
					query.setParameter(PDConstants.OSEIID_PREFIX,
							selectMnthtMstTrn[3]);
					query.setParameter(PDConstants.PRMTR_POT_CD,
							selectMnthtMstTrn[5]);
					query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
							prdStgCd);
					query.setParameter(PDConstants.PRMTR_DRAFT_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_MS_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_ORDR_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_SIMU_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(B000008Constants.AUTO_ADJUST_ORDER_QTY,
							PDConstants.PRMTR_ZERO);
					query.setParameter(B000008Constants.SUSPENDED_ORDER_FLAG,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_CRTD_BY,
							B000008Constants.BATCH_ID_B000008);
					query.executeUpdate();
				}
				CommonUtil.logMessage(PDMessageConsants.M00163,
						B000008Constants.CONSTANT_V3, new String[] {
								B000008Constants.BATCH_ID_B000008,
								PDConstants.INSERTED,
								PDConstants.MONTHLY_ORDER_TRN });
			}
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logErrorMessage(PDMessageConsants.M00164,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.INSERTION,
							PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * extract extractNscFrcstVol for updation Process P0008
	 * 
	 * @param OrdrTkBsMnth
	 * @param list
	 * @return
	 */
	public List<Object[]> extractNscFrcstVol(String ordrTkBsMnth,
			List<Object[]> list) {
		List<Object[]> nscFrcst = new ArrayList<Object[]>();
		String selectNscFrcstQuery = B000008QueryConstants.NscFrcstQuery
				.toString();
		for (Object[] selectMnthtMstTrn : list) {
			Query query = entityMngr.createNativeQuery(selectNscFrcstQuery);
			query.setParameter(PDConstants.PRMTR_PORCD, selectMnthtMstTrn[0]);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_CARSRS, selectMnthtMstTrn[2]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE,
					selectMnthtMstTrn[1]);
			query.setParameter(B000008Constants.MAXIMUM_PRODUCTION_MONTH,
					selectMnthtMstTrn[3]);
			List<Object[]> selectResultSet = query.getResultList();
			//Result will have val1,val2 from MST_PRMTR
			for (Object[] selectTrn : selectResultSet) {
				nscFrcst.add(new Object[] { selectTrn[0].toString(),
						selectTrn[1].toString(), selectTrn[2].toString(),
						selectTrn[3].toString(), selectTrn[4].toString() });
			}
		}
		return nscFrcst;
	}

	/**
	 * insert into NScFrcstVol process P0009
	 * 
	 * @param list
	 */
	public void insertNScFrcstVol(List<Object[]> list) {
		String insertNscFrcstVol = B000008QueryConstants.insertNscFrcstVolQuery
				.toString();
		int insertFlag = 0;
		String chkDupiacteQuery = B000008QueryConstants.chekDupNscFrcstVolQuery
				.toString();
		try {
			for (Object[] selectMnthtMstTrn : list) {
				Query selQuery = entityMngr.createNativeQuery(chkDupiacteQuery);
				selQuery.setParameter(PDConstants.PRMTR_PORCD,
						selectMnthtMstTrn[0]);
				selQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						selectMnthtMstTrn[1]);
				selQuery.setParameter(PDConstants.PRODUCTION_MONTH,
						selectMnthtMstTrn[2]);
				selQuery.setParameter(B000008Constants.BUYER_GROUP_CD,
						selectMnthtMstTrn[3]);
				selQuery.setParameter(PDConstants.PRMTR_CARSRS,
						selectMnthtMstTrn[4]);
				String getDupChk = selQuery.getSingleResult().toString();
				if (getDupChk.equals(PDConstants.PRMTR_ZERO)) {
					insertFlag = 1;
					Query query = entityMngr
							.createNativeQuery(insertNscFrcstVol);
					query.setParameter(PDConstants.PRMTR_PORCD,
							selectMnthtMstTrn[0]);
					query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
							selectMnthtMstTrn[1]);
					query.setParameter(PDConstants.PRODUCTION_MONTH,
							selectMnthtMstTrn[2]);
					query.setParameter(B000008Constants.BUYER_GROUP_CD,
							selectMnthtMstTrn[3]);
					query.setParameter(PDConstants.PRMTR_CARSRS,
							selectMnthtMstTrn[4]);
					query.setParameter(PDConstants.FORCAST_VOLUME,
							PDConstants.PRMTR_ZERO);
					query.setParameter(PDConstants.PRMTR_CRTD_BY,
							B000008Constants.BATCH_ID_B000008);
					query.executeUpdate();
				}
			}
			if (insertFlag == 1) {
				CommonUtil.logMessage(PDMessageConsants.M00163,
						B000008Constants.CONSTANT_V3, new String[] {
								B000008Constants.BATCH_ID_B000008,
								PDConstants.INSERTED,
								PDConstants.NSC_FORCAST_VOLUME_TRN });
			}
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logErrorMessage(PDMessageConsants.M00164,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.INSERTION,
							PDConstants.NSC_FORCAST_VOLUME_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * extract extractNscFrcstVol for updation process P0010
	 * 
	 * @param objOutput
	 * @return
	 */
	public List<Object[]> extractNscConf(B000008Output objOutput) {
		List<Object[]> nscConf = new ArrayList<Object[]>();
		String ordrTkBsMnth = objOutput.getObjReaderOutput().getOrdrTkBsMnth();
		String selectNscFrcstQuery = B000008QueryConstants.NscConfQuery
				.toString();
		for (Object[] selectMnthtMstTrn : objOutput.getObjB000008P1Output()
				.getMaxPrdList()) {
			Query query = entityMngr.createNativeQuery(selectNscFrcstQuery);
			query.setParameter(PDConstants.PRMTR_PORCD, selectMnthtMstTrn[0]);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_CARSRS, selectMnthtMstTrn[2]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE,
					selectMnthtMstTrn[1]);
			List<Object[]> selectResultSet = query.getResultList();
			//Result will have columns POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,BUYER_GRP_CD,CAR_SRS from TRN_NSC_FRCST_VOL
			for (Object[] selectTrn : selectResultSet) {
				nscConf.add(new Object[] { selectTrn[0].toString(),
						selectTrn[1].toString(), selectTrn[2].toString(),
						selectTrn[3].toString() });
			}
		}
		LOG.info(nscConf.size() + "  P00010");
		return nscConf;
	}

	/**
	 * insert into NScFrcstVol process P0011
	 * 
	 * @param prdstgCd
	 * @param list
	 */
	public void insertNScConf(String prdstgCd, List<Object[]> list) {
		String insertNscFrcstVol = B000008QueryConstants.insertNscConfQuery
				.toString();
		String chkDupiacteQuery = B000008QueryConstants.chekDupNscConfQuery
				.toString();
		try {
			for (Object[] selectMnthtMstTrn : list) {
				Query selQuery = entityMngr.createNativeQuery(chkDupiacteQuery);
				selQuery.setParameter(PDConstants.PRMTR_PORCD,
						selectMnthtMstTrn[0]);
				selQuery.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						selectMnthtMstTrn[1]);
				selQuery.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
						prdstgCd);
				selQuery.setParameter(B000008Constants.BUYER_GROUP_CD,
						selectMnthtMstTrn[2]);
				selQuery.setParameter(PDConstants.PRMTR_CARSRS,
						selectMnthtMstTrn[3]);
				String getDupChk = selQuery.getSingleResult().toString();
				if (getDupChk.equals(PDConstants.PRMTR_ZERO)) {
					Query query = entityMngr
							.createNativeQuery(insertNscFrcstVol);
					query.setParameter(PDConstants.PRMTR_PORCD,
							selectMnthtMstTrn[0]);
					query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
							selectMnthtMstTrn[1]);
					query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
							prdstgCd);
					query.setParameter(B000008Constants.BUYER_GROUP_CD,
							selectMnthtMstTrn[2]);
					query.setParameter(PDConstants.PRMTR_CARSRS,
							selectMnthtMstTrn[3]);
					query.setParameter(PDConstants.NSC_CMPLT_FLAG,
							PDConstants.N);
					query.setParameter(PDConstants.PRMTR_CRTD_BY,
							B000008Constants.BATCH_ID_B000008);
					query.executeUpdate();
				}
			}
			CommonUtil.logMessage(PDMessageConsants.M00163,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.INSERTED,
							PDConstants.NSC_CONFIRMATION_MONTHLY_TRN });
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logErrorMessage(PDMessageConsants.M00164,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.INSERTION,
							PDConstants.NSC_CONFIRMATION_MONTHLY_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * Extract Suspended /non suspended Data Process P0012
	 * 
	 * @param list
	 * @return
	 */
	public List<Object[]> extractNonSuspndData(List<Object[]> list) {
		List<Object[]> suspndData = new ArrayList<Object[]>();
		String selectSuspndDataQuery = B000008QueryConstants.selectSuspndDataQuery
				.toString();
		for (Object[] selectMnthtMstTrn : list) {
			Query query = entityMngr.createNativeQuery(selectSuspndDataQuery);
			// PORCD, productionFamilyCode, carSeries, MaximumProductionMonth,
			// OrderTakeBaseMonth, StageCode,ProductionStageCode
			query.setParameter(PDConstants.PRMTR_PORCD, selectMnthtMstTrn[0]);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					selectMnthtMstTrn[4]);
			query.setParameter(PDConstants.PRMTR_CARSRS, selectMnthtMstTrn[2]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE,
					selectMnthtMstTrn[1]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
					selectMnthtMstTrn[6]);
			List<Object[]> selectResultSet = query.getResultList();
			//Result will have columns POR_CD,ORDR_TAKE_BASE_MNTH,PROD_ORDR_STAGE_CD,BUYER_GRP_CD,CAR_SRS from TRN_NSC_CNFRMTN_MNTHLY
			if (!selectResultSet.isEmpty()) {
				for (Object[] selectTrn : selectResultSet) {
					suspndData.add(new Object[] { selectTrn[0].toString(),
							selectTrn[1].toString(), selectTrn[2].toString(),
							selectTrn[3].toString(), selectTrn[4].toString(),
							selectTrn[5].toString() });
				}
			}
		}
		return suspndData;
	}

	/**
	 * Update Non Suspended Data Process P0014
	 * 
	 * @param list
	 */
	public void updateNonSuspnd(List<Object[]> list) {
		String updateNonSuspnd = B000008QueryConstants.updateNonSuspndQuery
				.toString();
		try {
			for (Object[] selectMnthtMstTrn : list) {
				Query query = entityMngr.createNativeQuery(updateNonSuspnd);
				query.setParameter(PDConstants.PRMTR_PORCD,
						selectMnthtMstTrn[0]);
				query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						selectMnthtMstTrn[1]);
				query.setParameter(PDConstants.PRODUCTION_MONTH,
						selectMnthtMstTrn[2]);
				query.setParameter(PDConstants.OSEIID_PREFIX,
						selectMnthtMstTrn[4]);
				query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
						selectMnthtMstTrn[5]);
				query.setParameter(PDConstants.PRMTR_UPDT_BY,
						B000008Constants.BATCH_ID_B000008);
				query.executeUpdate();
			}
			CommonUtil
					.logMessage(PDMessageConsants.M00163,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATED,
									PDConstants.MONTHLY_ORDER_TRN });
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil
					.logErrorMessage(PDMessageConsants.M00164,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATION,
									PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * Update Suspended Data Process P0015
	 * 
	 * @param list
	 */
	public void updateSuspndData(List<Object[]> list) {
		LOG.info("Process P0015");
		String updateSuspnd = B000008QueryConstants.updateSuspndQuery
				.toString();
		// PORCD, productionFamilyCode, carSeries, MaximumProductionMonth,
		// OrderTakeBaseMonth, StageCode,ProductionStageCode

		try {
			for (Object[] selectMnthtMstTrn : list) {
				Query query = entityMngr.createNativeQuery(updateSuspnd);
				query.setParameter(PDConstants.PRMTR_PORCD,
						selectMnthtMstTrn[0]);
				query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						selectMnthtMstTrn[4]);
				query.setParameter(PDConstants.PRMTR_PRODUCTION_STAGE_CODE,
						selectMnthtMstTrn[6]);
				query.setParameter(PDConstants.PRMTR_UPDT_BY,
						B000008Constants.BATCH_ID_B000008);
				query.executeUpdate();
			}
			CommonUtil
					.logMessage(PDMessageConsants.M00163,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATED,
									PDConstants.MONTHLY_ORDER_TRN });
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil
					.logErrorMessage(PDMessageConsants.M00164,
							B000008Constants.CONSTANT_V3, new String[] {
									B000008Constants.BATCH_ID_B000008,
									PDConstants.UPDATION,
									PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * update spec ref time
	 * 
	 * @param porCd
	 */
	public void updateSpecRefTime(String porCd) {
		LOG.info("Process P0016");
		String updateSpecRef = B000008QueryConstants.updateSpecRefQuery
				.toString();
		try {
			Query upQuery = entityMngr.createNativeQuery(updateSpecRef);
			upQuery.setParameter(PDConstants.PRMTR_PORCD, porCd);
			upQuery.setParameter(PDConstants.PRMTR_CURRENT_TIME, new Timestamp(
					new Date().getTime()));
			upQuery.setParameter(PDConstants.REFERENCE_TIME, createDate);
			upQuery.setParameter(PDConstants.PRMTR_BATCH_ID,
					B000008Constants.BATCH_ID_B000008);
			upQuery.setParameter(PDConstants.PRMTRT_TABLE_NAME,
					PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST);
			upQuery.setParameter(PDConstants.PRMTR_UPDT_BY,
					B000008Constants.BATCH_ID_B000008);
			upQuery.executeUpdate();
			CommonUtil.logMessage(PDMessageConsants.M00163,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.UPDATED,
							PDConstants.MESSAGE_SPEC_REEXECUTE_STATTUS });
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logErrorMessage(PDMessageConsants.M00164,
					B000008Constants.CONSTANT_V3, new String[] {
							B000008Constants.BATCH_ID_B000008,
							PDConstants.UPDATION,
							PDConstants.MESSAGE_SPEC_REEXECUTE_STATTUS });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * Created by z011479 This is the method used to get the List of Buyer
	 * Group.
	 * 
	 * @param paramOutput
	 * @return
	 */
	
	public List<Object> getByrGrp(B000020ParamOutput paramOutput) {
		String extractByrGrpStr = B000020QueryConstants.extractByrGrpQry
				.toString();
		Query extractByrGrpQry = entityMngr.createNativeQuery(extractByrGrpStr);
		extractByrGrpQry.setParameter(PDConstants.PRMTR_PORCD,
				paramOutput.getPorCd());
		extractByrGrpQry.setParameter(PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG,
				PDConstants.CONSTANT_F);
		return extractByrGrpQry.getResultList();
	}

	/**
	 * created by Z015060 extract OrdertakeBasemnth and Stage code for PorCd And
	 * write the error message Process P0002
	 * 
	 * @param porCd
	 */
	public void extractOrdrFrErr(String porCd) {
		String extractOrdrFrErrQry = B000030QueryConstants.extractOrdrFrErr
				.toString();
		Query extractQry = entityMngr.createNativeQuery(extractOrdrFrErrQry);
		extractQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		List<Object[]> selectResultSet = extractQry.getResultList();
		//Result will have columns ORDR_TAKE_BASE_MNTH,STAGE_CD from MST_MNTH_ORDR_TAKE_BASE_PD
		CommonUtil.logMessage(PDMessageConsants.M00149,
				B000030Constants.CONSTANT_V1,
				new String[] { B000030Constants.PROCESS_P1 });
		for (Object[] selectSpecRef : selectResultSet) {
			CommonUtil.logMessage(PDMessageConsants.M00344,
					B000030Constants.CONSTANT_V3,
					new String[] { PDConstants.EMPTY_STRING, selectSpecRef[0].toString(),
							selectSpecRef[1].toString() });
		}
		CommonUtil.stopBatch();

	}

	/**
	 * To extract OrdrTkBsMnth from MnhtlyOrdrTkBsPd table Process: P0003.1
	 * 
	 * @param porCd
	 * @return OrdrTkBsMnth String
	 */
	public String extrxctOrdrTkBsMnth(String porCd) {
		String extrxctOrdrTkBsMnth = B000030QueryConstants.extrxctOrdrTkBsMnthQry
				.toString();
		String extractOrdrTkBsMnth = null;
		Query extractQry = entityMngr.createNativeQuery(extrxctOrdrTkBsMnth);
		extractQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		if (extractQry.getSingleResult() != null) {
			extractOrdrTkBsMnth = extractQry.getSingleResult().toString();
		} else {
			String extrxctOrdrStts = B000030QueryConstants.extrxctOrdrSttsQry
					.toString();
			Query extractSttsQry = entityMngr
					.createNativeQuery(extrxctOrdrStts);
			extractSttsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
			List<Object[]> selectResultSet = extractSttsQry.getResultList();
			for (Object[] selectMaxOrdr : selectResultSet) {
				CommonUtil.logMessage(
						PDMessageConsants.M00150,
						B000030Constants.CONSTANT_V4,
						new String[] { porCd, selectMaxOrdr[0].toString(),
								selectMaxOrdr[1].toString(),
								selectMaxOrdr[2].toString() });
			}
			CommonUtil.stopBatch();
		}
		return extractOrdrTkBsMnth;
	}

	public EntityManager getEntityMngr() {
		return entityMngr;
	}

	public void setEntityMngr(EntityManager entityMngr) {
		this.entityMngr = entityMngr;
	}

}
