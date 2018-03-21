/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000011.util.B000011CommonUtil;
import com.nissangroups.pd.b000011.util.B000011Constants;
import com.nissangroups.pd.b000011.util.B000011QueryConstants;
import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.b000011.output.B000011ParamOutput;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmtPK;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmtPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class MnthlyOCFTrnRepository.
 *
 * @author z015060
 */
public class MnthlyOCFTrnRepository {

	public MnthlyOCFTrnRepository() {
	}

	/** Variable entity manager. */
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(MnthlyOCFTrnRepository.class.getName());

	/**
	 * @param paramOutput
	 * @return
	 */
	public List<Object[]> getOCFTrnData(B000011ParamOutput paramOutput) {
		LOG.info("Process P0002");
		StringBuilder getOCFTrn = B000011QueryConstants.getOCFTrnQry;
		if (paramOutput.getPrcsOlyFlg().equals(PDConstants.Y)) {
			getOCFTrn.append(" and PROCESS_STTS_CD='0' ");
		}
		getOCFTrn.append(B000011QueryConstants.getOCFTrnQryp2.toString());
		Query query = entityMngr.createNativeQuery(getOCFTrn.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, paramOutput.getPorCd());
		query.setParameter(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
				paramOutput.getOrdrTkBsMnth());
		
		//Result will have columns POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,CAR_SRS,OCF_BUYER_GRP_CD,FEAT_CD, OCF_REGION_CD,SUM(OCF_MAX_QTY) as OCF_MAX_QTY,OCF_FRME_CD from TRN_MNTHLY_OCF
		return query.getResultList();
	}

	/**
	 * @param objB000011Output
	 * @param ordrTkBsMnth
	 */
	public void updateIntialMnthReg(B000011Output objB000011Output,
			List<String> ordrTkBsMnth) {
		LOG.info("Process 3.1");
		StringBuilder updateLmtMnthReg = B000011QueryConstants.updateLmtMnthRegQry;
		updateLmtMnthReg = B000011CommonUtil.addOrdrTkBsMnthPrcssFlg(
				objB000011Output.getObjB000011ParamOutput().getPrcsOlyFlg(),
				updateLmtMnthReg);
		Query query = entityMngr.createNativeQuery(updateLmtMnthReg.toString());
		try {
			query.setParameter(PDConstants.PRMTR_PORCD, objB000011Output
					.getObjB000011ParamOutput().getPorCd());
			query = B000011CommonUtil
					.addqueryParam(query, ordrTkBsMnth, objB000011Output
							.getObjB000011ParamOutput().getPrcsOlyFlg(),
							objB000011Output.getObjB000011ParamOutput()
									.getOrdrTkBsMnth());
			query.executeUpdate();
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					PDConstants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATION,
							PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}
		CommonUtil.logMessage(PDMessageConsants.M00163,
				PDConstants.CONSTANT_V3, new String[] {
						B000011Constants.BATCH_ID_B000011, PDConstants.UPDATED,
						PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
	}

	
	/**
	 * @param objB000011Output
	 */
	public void insertMnthReg(B000011Output objB000011Output) {
		LOG.info("Process 3.2 size is " + objB000011Output.getOcfLmt().size());
		for (Object[] data : objB000011Output.getOcfLmt()) {
			try {
				TrnRegionalMnthlyOcfLmt trnregional = new TrnRegionalMnthlyOcfLmt();
				TrnRegionalMnthlyOcfLmtPK trnregionalPk = new TrnRegionalMnthlyOcfLmtPK();
				trnregionalPk.setPorCd(data[0].toString());
				trnregionalPk.setOrdrTakeBaseMnth(data[1].toString());
				trnregionalPk.setProdMnth(data[2].toString());
				trnregionalPk.setCarSrs(data[3].toString());
				trnregionalPk.setOcfRegionCd(data[6].toString());
				trnregionalPk.setOcfBuyerGrpCd(data[4].toString());
				trnregionalPk.setFeatCd(data[5].toString());
				LOG.info(data[7].toString());
				if (Integer.parseInt(data[7].toString()) > 9999999) {
					if (data[8].toString().equals(PDConstants.ZERO)) {
						trnregional.setRegionalOcfLmtQty(new BigDecimal(0));
					} else {
						// trnregional.setRegionalOcfLmtQty(new BigDecimal(0));
					}
				} else {
					trnregional.setRegionalOcfLmtQty(new BigDecimal(Integer
							.parseInt(data[7].toString())));
				}
				trnregional.setFeatTypeCd(PDConstants.EMPTY_STRING);
				trnregional.setOcfFrmeCd(data[8].toString());
				trnregional.setCrtdBy(B000011Constants.BATCH_ID_B000011);
				trnregional.setUpdtdBy(B000011Constants.BATCH_ID_B000011);
				trnregional.setRegionalOcfUsgQty(new BigDecimal(0));
				trnregional.setId(trnregionalPk);
				entityMngr.merge(trnregional);
			} catch (Exception e) {
				LOG.info(e);
				CommonUtil.logMessage(PDMessageConsants.M00164,
						PDConstants.CONSTANT_V3, new String[] {
								B000011Constants.BATCH_ID_B000011,
								PDConstants.INSERTION,
								PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
				CommonUtil.stopBatch();
			}
			CommonUtil.logMessage(PDMessageConsants.M00163,
					PDConstants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.INSERTED,
							PDConstants.REGIONAL_MONTHLY_OCF_LIMIT_TRN });
		}
	}

	/**
	 * @param object
	 * @return
	 */
	public List<Object[]> getBuyerGrpLvlOCF(B000011Output object) {
		LOG.info("Process 4");
		StringBuilder buyerGrpLvlOCF = B000011QueryConstants.getBuyerGrpLvlOCFQry;
		Map<String, String> byrGrpChkMap = new HashMap<String, String>();
		buyerGrpLvlOCF = B000011CommonUtil.addOrdrTkBsMnthPrcssFlg(object
				.getObjB000011ParamOutput().getPrcsOlyFlg(), buyerGrpLvlOCF);
		buyerGrpLvlOCF.append(B000011QueryConstants.getBuyerGrpLvlOCFQryp2
				.toString());
		Query query = entityMngr.createNativeQuery(buyerGrpLvlOCF.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, object
				.getObjB000011ParamOutput().getPorCd());
		query = B000011CommonUtil.addqueryParam(query, object
				.getOrdrTkBsMnthLst(), object.getObjB000011ParamOutput()
				.getPrcsOlyFlg(), object.getObjB000011ParamOutput()
				.getOrdrTkBsMnth());
		List<Object[]> selectResultSet = query.getResultList();
		//Result array will have columns tr.Ordr_take_base_mnth,tr.PROD_MNTH,tr.CAR_SRS,tr.FEAT_CD,tr.OCF_FRME_CD,tr.REGIONAL_OCF_LMT_QTY,mb.BUYER_GRP_CD,tr.OCF_REGION_CD,tr.OCF_BUYER_GRP_CD from TRN_REGIONAL_MNTHLY_OCF_LMT tr
		for (Object[] temp : selectResultSet) {
			if (byrGrpChkMap.get(temp[6].toString()) == null) {
				byrGrpChkMap.put(temp[6].toString(), temp[7].toString()
						+ temp[8].toString());
			} else {
				if (!byrGrpChkMap.get(temp[6].toString()).equals(
						temp[7].toString() + temp[8].toString())) {
					CommonUtil.logMessage(PDMessageConsants.M00334,
							B000011Constants.CONSTANT_V1,
							new String[] { B000011Constants.BATCH_ID_B000011 });
					CommonUtil.stopBatch();
				}
			}
		}
		return selectResultSet;
	}

	
	/**
	 * @param object
	 */
	public void insertBuyerGrpOcfLmt(B000011Output object) {
		LOG.info("Process P0005");

		try {
			for (Object[] byrGrp : object.getBuyerGrpUsge()) {
				TrnBuyerGrpMnthlyOcfLmt trnByrGrp = new TrnBuyerGrpMnthlyOcfLmt();
				TrnBuyerGrpMnthlyOcfLmtPK trnPk = new TrnBuyerGrpMnthlyOcfLmtPK();
				if (byrGrp[4].equals(PDConstants.FEATURE_CODE_00)) {
					trnByrGrp.setBuyerGrpOcfLmtQty(new BigDecimal(
							PDConstants.ZERO));
				} else {
					// trnByrGrp.setBuyerGrpOcfLmtQty(new
					// BigDecimal(PDConstants.ZERO));
				}
				trnByrGrp
						.setBuyerGrpOcfUsgQty(new BigDecimal(PDConstants.ZERO));
				trnPk.setPorCd(object.getObjB000011ParamOutput().getPorCd());
				trnPk.setProdMnth(byrGrp[1].toString());
				trnPk.setCarSrs(byrGrp[2].toString());
				trnPk.setBuyerGrpCd(byrGrp[6].toString());
				trnByrGrp.setOcfFrmeCd(byrGrp[4].toString());
				trnPk.setFeatCd(byrGrp[3].toString());
				trnPk.setOrdrTakeBaseMnth(byrGrp[0].toString());
				trnByrGrp.setFeatTypeCd(PDConstants.EMPTY_STRING);
				trnByrGrp.setBuyerGrpSimuQty(new BigDecimal(PDConstants.ZERO));
				trnByrGrp.setId(trnPk);
				trnByrGrp.setCrtdBy(B000011Constants.BATCH_ID_B000011);
				trnByrGrp.setUpdtdBy(B000011Constants.BATCH_ID_B000011);
				entityMngr.merge(trnByrGrp);
			}
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.INSERTED,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}
		CommonUtil.logMessage(PDMessageConsants.M00163,
				B000011Constants.CONSTANT_V3, new String[] {
						B000011Constants.BATCH_ID_B000011,
						PDConstants.INSERTED,
						PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
	}

	/**
	 * @param object
	 * @return
	 */
	public B000011Output getByrGrpCdAutoAlloctn(B000011Output object) {
		LOG.info("Process P0006");
		Map<String, String> byrGrpChkMap = new HashMap<String, String>();
		StringBuilder ByrGrpCdAutoAlloctn = B000011QueryConstants.getByrGrpCdAutoAlloctnQry;
		Query query = entityMngr.createNativeQuery(ByrGrpCdAutoAlloctn
				.toString());
		query.setParameter(PDConstants.PRMTR_PORCD, object
				.getObjB000011ParamOutput().getPorCd());
		List<Object[]> byrIDLst = query.getResultList();
		//Result array will have columns mb.BUYER_GRP_CD,mo.OCF_REGION_CD,mo.OCF_BUYER_GRP_CD from MST_BUYER mb
		if (byrIDLst.isEmpty()) {
			LOG.error(PDMessageConsants.M00003);
			CommonUtil.stopBatch();
		} else {
			for (Object[] temp : byrIDLst) {
				if (byrGrpChkMap.get(temp[1].toString() + temp[2].toString()) == null) {
					byrGrpChkMap.put(temp[1].toString() + temp[2].toString(),
							temp[0].toString());
				} else {
					CommonUtil.logMessage(PDMessageConsants.M00333,
							B000011Constants.CONSTANT_V1,
							new String[] { B000011Constants.BATCH_ID_B000011 });
					CommonUtil.stopBatch();
				}
			}
			object.setOcfBuyerGrpCd(byrIDLst);
		}
		return object;
	}

	/**
	 * @param object
	 * @return
	 */
	public B000011Output cpyRgnMnthly(B000011Output object) {
		LOG.info("Process P0007.1");
		StringBuilder rgnlOcfLmtQuery = B000011QueryConstants.getRgnlOcfLmtQuery;
		List<Object[]> cpyRgnMnhtlyLst = new ArrayList<Object[]>();
		rgnlOcfLmtQuery = B000011CommonUtil.addOrdrTkBsMnthPrcssFlg(object
				.getObjB000011ParamOutput().getPrcsOlyFlg(), rgnlOcfLmtQuery);
		for (Object[] extrct : object.getOcfBuyerGrpCd()) {
			Query query = entityMngr.createNativeQuery(rgnlOcfLmtQuery
					.toString());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000011ParamOutput().getPorCd());
			query = B000011CommonUtil.addqueryParam(query, object
					.getOrdrTkBsMnthLst(), object.getObjB000011ParamOutput()
					.getPrcsOlyFlg(), object.getObjB000011ParamOutput()
					.getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_OCF_REGION, extrct[1]);
			query.setParameter(PDConstants.PRMTR_OCFBYRGRPCD, extrct[2]);
			List<Object[]> cpyRgnMnhtlyTempLst = query.getResultList();
			//Result Array will have columns REGIONAL_OCF_LMT_QTY,PROD_MNTH,CAR_SRS,FEAT_CD,OCF_FRME_CD,FEAT_TYPE_CD,OCF_BUYER_GRP_CD,ORDR_TAKE_BASE_MNTH from TRN_REGIONAL_MNTHLY_OCF_LMT
			for (Object[] temp : cpyRgnMnhtlyTempLst) {
				cpyRgnMnhtlyLst.add(new Object[] { temp[0], temp[1], temp[2],
						temp[3], temp[4], temp[5], temp[6],
						extrct[0].toString(),temp[7] });
			}
		}
		if (!cpyRgnMnhtlyLst.isEmpty()) {
			object.setRgnlMnthlyOcfLst(cpyRgnMnhtlyLst);
			object.setRgnlMnthlyFlg(PDConstants.Y);
		} else {
			object.setRgnlMnthlyFlg(PDConstants.N);
			CommonUtil.logMessage(PDMessageConsants.M00160,
					B000011Constants.CONSTANT_V4, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.OCF_LIMIT,
							object.getObjB000011ParamOutput().getPorCd(),
							PDConstants.MONTHLY_OCF_TRN });
		}
		return object;
	}

	/**
	 * @param object
	 */
	public void intialiseMnthlyBuyerOcf(B000011Output object) {
		LOG.info("Process P0007.2.a");
		StringBuilder intialiseMnthlyBuyer = B000011QueryConstants.intialiseMnthlyBuyerOcfQuery;
		intialiseMnthlyBuyer = B000011CommonUtil.addOrdrTkBsMnthPrcssFlg(object
				.getObjB000011ParamOutput().getPrcsOlyFlg(),
				intialiseMnthlyBuyer);
		try {
			for (Object[] extrct : object.getOcfBuyerGrpCd()) {
				Query query = entityMngr.createNativeQuery(intialiseMnthlyBuyer
						.toString());
				query.setParameter(PDConstants.PRMTR_PORCD, object
						.getObjB000011ParamOutput().getPorCd());
				query = B000011CommonUtil.addqueryParam(query, object
						.getOrdrTkBsMnthLst(), object
						.getObjB000011ParamOutput().getPrcsOlyFlg(), object
						.getObjB000011ParamOutput().getOrdrTkBsMnth());
				query.setParameter(PDConstants.PRMTR_BYR_GRP_CD, extrct[0]);
				query.executeUpdate();
			}
			CommonUtil.logMessage(PDMessageConsants.M00163,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATED,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATION,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}
	}

	
	/**
	 * @param object
	 */
	public void updatelmtBuyrGrp(B000011Output object) {
		try {
			for (Object[] extrct : object.getRgnlMnthlyOcfLst()) {
				TrnBuyerGrpMnthlyOcfLmt trnByrGrp = new TrnBuyerGrpMnthlyOcfLmt();
				TrnBuyerGrpMnthlyOcfLmtPK trnPk = new TrnBuyerGrpMnthlyOcfLmtPK();
				trnPk.setPorCd(object.getObjB000011ParamOutput().getPorCd());
				
				trnPk.setBuyerGrpCd(extrct[7].toString());
				trnPk.setOrdrTakeBaseMnth(extrct[8].toString());
				trnPk.setCarSrs(extrct[2].toString());
				trnPk.setFeatCd(extrct[3].toString());
				trnPk.setProdMnth(extrct[1].toString());
				trnByrGrp.setOcfFrmeCd(extrct[4].toString());
				trnByrGrp.setId(trnPk);
				
				TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmtOld = entityMngr.find(TrnBuyerGrpMnthlyOcfLmt.class, trnPk);
				if(byrGrpOcfLmtOld!=null){
					trnByrGrp = byrGrpOcfLmtOld; 
				}
				
				if (extrct[0] != null) {
					trnByrGrp.setBuyerGrpOcfLmtQty(new BigDecimal(extrct[0].toString()));
				} else {
					trnByrGrp.setBuyerGrpOcfLmtQty(null);
				}
				
				
				entityMngr.merge(trnByrGrp);
			}
			CommonUtil.logMessage(PDMessageConsants.M00163,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATED,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATION,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}
	}

	/**
	 * @param object
	 */
	public void updateProcessRec(B000011Output object) {
		StringBuilder updateProcessRec = B000011QueryConstants.updateProcessRecQuery;
		if (object.getObjB000011ParamOutput().getPrcsOlyFlg()
				.equals(PDConstants.Y)) {
			updateProcessRec.append(B000011Constants.PROCESS_FLAG_QUERY);
		}
		try {
			Query query = entityMngr.createNativeQuery(updateProcessRec
					.toString());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000011ParamOutput().getPorCd());
			query.executeUpdate();
			CommonUtil.logMessage(PDMessageConsants.M00163,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATED,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00164,
					B000011Constants.CONSTANT_V3, new String[] {
							B000011Constants.BATCH_ID_B000011,
							PDConstants.UPDATION,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}

	}
}
