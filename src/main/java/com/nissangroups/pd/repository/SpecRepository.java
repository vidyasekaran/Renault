package com.nissangroups.pd.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.b000028.process.AutoAdjustReport;
import com.nissangroups.pd.b000028.util.B000028Constants;
import com.nissangroups.pd.b000028.util.B000028QueryConstants;
import com.nissangroups.pd.model.TmpTrnBuyerMnthlyOcfUsg;
import com.nissangroups.pd.model.TmpTrnBuyerMnthlyOcfUsgPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class SpecRepository {

	public SpecRepository() {

	}

	/** Variable entity manager. */
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;

	@Autowired(required = false)
	private AutoAdjustReport objAutoAdjustReport;
	
	Map<String,  Integer> breachCountMap = new HashMap<String,  Integer>();

	Date date = new Date();
	DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);
	Timestamp createDate = new Timestamp(date.getTime());

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(SpecRepository.class
			.getName());

	public List<Object[]> getCarSrsHrzn(String porCd, String ordrTkBsMnth) {

		String crSrsHrznQry = B000028QueryConstants.getCrSrsHrznQry.toString();
		Query query = entityMngr.createNativeQuery(crSrsHrznQry);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		List<Object[]> carSrsLst = query.getResultList();
		if (carSrsLst.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.CR_SRS, porCd,
							PDConstants.POR_CAR_SERIES_MST });
			CommonUtil.stopBatch();
		}
		return carSrsLst;
	}

	public List<Object[]> getbyrGrpLvl(List<Object[]> carSrsHrzn, String porCd,
			String ordrTkBsMnth) throws NumberFormatException, ParseException {
		List<Object[]> byrGrpList = new ArrayList<Object[]>();
		for (Object[] carSrs : carSrsHrzn) {
			String byrGrpLvlExtract = B000028QueryConstants.getbyrGrpLvlSelQry
					.toString();
			String byrGrpLvlMnExtract = B000028QueryConstants.getbyrGrpLvlMainQry
					.toString();
			String byrGrpLvlEndQry = B000028QueryConstants.getbyrGrpLvlEndQry
					.toString();
			byrGrpLvlExtract += byrGrpLvlMnExtract;
			byrGrpLvlExtract += byrGrpLvlEndQry;
			int prdMnthTo = 0;
			int prdMnthFrm = Math.max(Integer.parseInt(ordrTkBsMnth),
					Integer.parseInt(carSrs[2].toString().substring(0, 6)));
			if (!carSrs[3].toString().substring(6, 8).equals("11")) {
				prdMnthTo = Math.min(
						Integer.parseInt(carSrs[3].toString().substring(0, 6)),
						Integer.parseInt(formatter.format(CommonUtil
								.addMonthToDate(formatter.parse(ordrTkBsMnth), 
										Integer.parseInt(carSrs[1].toString()) - 1))));
			} else {
				prdMnthTo = Math.min(
						Integer.parseInt(formatter.format(CommonUtil
								.addMonthToDate(formatter.parse(carSrs[3]
										.toString().substring(0, 6)), -1))),
						Integer.parseInt(formatter.format(CommonUtil
								.addMonthToDate(formatter.parse(ordrTkBsMnth), 
										Integer.parseInt(carSrs[1].toString()) - 1))));
			}
			Query query = entityMngr.createNativeQuery(byrGrpLvlExtract);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_PORCD, porCd);
			query.setParameter(PDConstants.PRMTR_CARSRS, carSrs[0]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_MONTH_FROM,
					prdMnthFrm);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_MONTH_TO, prdMnthTo);
			List<Object[]> selectResultSet = query.getResultList();
			for (Object[] selectMstTrn : selectResultSet) {
				byrGrpList.add(new Object[] { selectMstTrn[0], selectMstTrn[1],
						selectMstTrn[2], selectMstTrn[3] });
			}
		}
		if (byrGrpList.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_ORDER_QTY, porCd,
							PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
		LOG.info(byrGrpList.size() + "------------");
		return byrGrpList;
	}

	public List<Object[]> getbyrGrpOCfLmt(List<Object[]> list, String porCd,
			String ordrTkBsMnth) {
		String byrGrpOCfLmt = B000028QueryConstants.getbyrGrpOCfLmtQry
				.toString();
			Query query = entityMngr.createNativeQuery(byrGrpOCfLmt);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_PORCD, porCd);
			List<Object[]> byrGrpOCFList = query.getResultList();
		if (byrGrpOCFList.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_BUYER_GROUP_DETAILS, porCd,
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN });
			CommonUtil.stopBatch();
		}
		return byrGrpOCFList;
	}

	public List<Object[]> getByrGrpCdLvlData(String porCd, String ordrTkBsMnth,
			B000028Output objB000008P2) {
		List<Object[]> byrGrpCdLvlDataList = new ArrayList<Object[]>();
		String byrGrpCdLvlData = B000028QueryConstants.getByrGrpCdLvlDataQry
				.toString();
		String byrGrpLvlMnExtract = B000028QueryConstants.getbyrGrpLvlMainQry
				.toString();
		String byrGrpLvlEndExtract = B000028QueryConstants.getByrGrpCdLvlEndDataQry
				.toString();
		byrGrpCdLvlData += byrGrpLvlMnExtract;
		byrGrpCdLvlData += byrGrpLvlEndExtract;
		for (Object[] byrGrp : objB000008P2.getByrGrpDiffList()) {
			Query query = entityMngr.createNativeQuery(byrGrpCdLvlData);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_PORCD, porCd);
			query.setParameter(PDConstants.PRMTR_CARSRS, byrGrp[0]);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrGrp[1]);
			query.setParameter(PDConstants.BUYER_GRP_CD, byrGrp[2]);
			List<Object[]> byrGrpOCfLmtLst = query.getResultList();
			if (byrGrpOCfLmtLst.isEmpty()) {
				objAutoAdjustReport.addReport(byrGrp, objB000008P2);
			} else {
				for (Object[] selectMstTrn : byrGrpOCfLmtLst) {
					byrGrpCdLvlDataList.add(new Object[] { selectMstTrn[0],
							selectMstTrn[1], selectMstTrn[2], selectMstTrn[3],
							selectMstTrn[4], selectMstTrn[5] });
				}
			}
		}

		return byrGrpCdLvlDataList;
	}

	public void insertTempAutoAdjust(String porCd, Object[] byrCdArry,
			float ratio, float allocation, int flag) {
		String oeiByrId = null;
		String oseiId = null;
		String potCd = null;
		if (flag > 2) {
			oeiByrId = byrCdArry[6].toString();
		}
		if (flag > 4) {
			oseiId = byrCdArry[7].toString();
		}
		if (flag > 6) {
			potCd = byrCdArry[8].toString();
		}
		String insertByrCdLvlData = B000028QueryConstants.insertAdjstAlloc
				.toString();
		Query query = entityMngr.createNativeQuery(insertByrCdLvlData);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_RATIO, ratio);
		query.setParameter(PDConstants.PRMTR_ALLOCATION, allocation);
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, flag);
		query.setParameter(PDConstants.PRMTR_CRTD_BY,
				B000028Constants.BATCH_B000028);
		query.setParameter(PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);
		query.setParameter(PDConstants.PRMTR_OSEI_ID, oseiId);
		query.setParameter(PDConstants.PRMTR_POT_CD, potCd);
		query.executeUpdate();
	}

	public void delTempAutoAdjust() {
		String delTempTable = B000028QueryConstants.delAutoAdjust.toString();
		Query delQuery = entityMngr.createNativeQuery(delTempTable);
		delQuery.executeUpdate();

	}

	public List<Object[]> getdatafromTempBuyrCD(Object[] byrCdArry) {
		String getTempBuyrCD = B000028QueryConstants.getdatafromTempBuyrCD
				.toString(); 
		Query query = entityMngr.createNativeQuery(getTempBuyrCD);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[0].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[1].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[2].toString());
		List<Object[]> results = query.getResultList();
		return results;
	}

	public String getRoundedAlloc(Object[] byrCdArry) {
		String totalAlloc = null;
		String getRoundedAlloc = B000028QueryConstants.getRoundedAllocByrGrp
				.toString();
		Query query = entityMngr.createNativeQuery(getRoundedAlloc);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[0].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[1].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[2].toString());
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			totalAlloc = results.get(0)[1].toString();
		}
		return totalAlloc;
	}

	public void insertListNewOrdrDtls(List<Object[]> calculatedOrdQty, int flag) {
		String insertByrCdLvlData = B000028QueryConstants.insertAdjstAlloc
				.toString();
		String oeiByrId = null;
		String oseiId = null;
		String potCd = null;
		String alloc = null;
		for (Object[] tempData : calculatedOrdQty) {
			Query query = entityMngr.createNativeQuery(insertByrCdLvlData);
			query.setParameter(PDConstants.PRMTR_PORCD, tempData[0].toString());
			query.setParameter(PDConstants.PRMTR_CARSRS, tempData[2].toString());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH,
					tempData[1].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, tempData[3].toString());
			if (flag == 2) {
				alloc = tempData[5].toString();
			} else if (flag == 4) {
				alloc = tempData[6].toString();
				oeiByrId = tempData[5].toString();
			} else if (flag == 6) {
				alloc = tempData[7].toString();
				oseiId = tempData[6].toString();
				oeiByrId = tempData[5].toString();
			} else if (flag == 8) {
				potCd = tempData[7].toString();
				alloc = tempData[8].toString();
				oseiId = tempData[6].toString();
				oeiByrId = tempData[5].toString();
			}
			query.setParameter(PDConstants.PRMTR_ALLOCATION, alloc);
			query.setParameter(PDConstants.PRMTR_RATIO, PDConstants.ZERO);
			query.setParameter(PDConstants.PRMTR_BYR_CD, tempData[4].toString());
			query.setParameter(PDConstants.PRMTR_PROCESS_FLAG, flag);
			query.setParameter(PDConstants.PRMTR_CRTD_BY,
					B000028Constants.BATCH_B000028);
			query.setParameter(PDConstants.PRMTR_OEI_BYR_ID, oeiByrId);
			query.setParameter(PDConstants.PRMTR_OSEI_ID, oseiId);
			query.setParameter(PDConstants.PRMTR_POT_CD, potCd);
			query.executeUpdate();
		}
	}

	public List<Object[]> getMnthlyOCFTrn(B000028Output object) {
		String getMnthOCF = B000028QueryConstants.getMnthlyOCFTrn.toString();
		// POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,POT_CD,ALLOCATION
		List<Object[]> potCdLvlDataList = new ArrayList<Object[]>();
		
		String delTempTable = B000028QueryConstants.delTmpByrMnthOcfUsg.toString();
		Query delQuery = entityMngr.createNativeQuery(delTempTable);
		delQuery.executeUpdate();
		
		
		for (Object[] potCdLvl : object.getPotLvlByrGrp()) {
			//POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD
			Query query = entityMngr.createNativeQuery(getMnthOCF);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, object
					.getObjB000028ParamOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000028ParamOutput().getPorCd());
			query.setParameter(PDConstants.PRMTR_CARSRS, potCdLvl[1]);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, potCdLvl[2]);
			query.setParameter(PDConstants.BUYER_GRP_CD, potCdLvl[3]);
			List<Object[]> potOCFLmtLst = query.getResultList();
			LOG.info(potOCFLmtLst.size()+"----");
			LOG.info("HERE----");
			for (Object[] selectMstTrn : potOCFLmtLst) {
				// tb.POR_CD,tb.ORDR_TAKE_BASE_MNTH,tb.CAR_SRS,tb.BUYER_GRP_CD,
				// tb.PROD_MNTH,tb.FEAT_CD,tb.FEAT_TYPE_CD,tb.OCF_FRME_CD,
				// sum(tm.AUTO_ADJST_ORDR_QTY) as auto_adjusted_order_qty
				// ,tm.OSEI_ID
				potCdLvlDataList.add(new Object[] { selectMstTrn[0],
						selectMstTrn[1], selectMstTrn[2], selectMstTrn[3],
						selectMstTrn[4], selectMstTrn[5], selectMstTrn[6],
						selectMstTrn[7], selectMstTrn[8], selectMstTrn[9] });
				TmpTrnBuyerMnthlyOcfUsg temp = new TmpTrnBuyerMnthlyOcfUsg();
				TmpTrnBuyerMnthlyOcfUsgPK tempPk = new TmpTrnBuyerMnthlyOcfUsgPK();
				tempPk.setPorCd(selectMstTrn[0].toString());
				temp.setBuyerGrpCd(selectMstTrn[3].toString());
				temp.setFeatTypeCd(selectMstTrn[6].toString());
				tempPk.setFeatCd(selectMstTrn[5].toString());
				temp.setCarSrs(selectMstTrn[2].toString());
				temp.setOcfFrmeCd(selectMstTrn[7].toString());
				tempPk.setProdMnth(selectMstTrn[4].toString());
				temp.setBuyerOcfUsgQty(new BigDecimal(selectMstTrn[8]
						.toString()));
				tempPk.setOseiId(selectMstTrn[9].toString());
				tempPk.setOrdrTakeBaseMnth(selectMstTrn[1].toString());
				temp.setId(tempPk);
				temp.setCrtdBy(B000028Constants.BATCH_B000028);
				temp.setCrtdDt(createDate);
				temp.setUpdtdBy(B000028Constants.BATCH_B000028);
				temp.setUpdtdDt(createDate);
				entityMngr.merge(temp);
			}
		}
		return potCdLvlDataList;
	}

	public List<Object[]> getBuyerMnthlyTemp(B000028Output object) {
		String getMnthOCF = B000028QueryConstants.getByrMnthlyTemp.toString();
		List<Object[]> byrMnthTemp = new ArrayList<Object[]>();
			Query query = entityMngr.createNativeQuery(getMnthOCF);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					object.getObjB000028ParamOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD,
					object.getObjB000028ParamOutput().getPorCd());
			List<Object[]> byrMnthTempLst = query.getResultList();
				for (Object[] selectMstTrn : byrMnthTempLst) {
					String porCd=selectMstTrn[0].toString();
					String ordrTkBsMnth=selectMstTrn[1].toString();
					String carSrs=selectMstTrn[2].toString();
					String byrGrp=selectMstTrn[3].toString();
					String prdMnth=selectMstTrn[4].toString();
					String ocfFrm=selectMstTrn[5].toString();
					String byrGrpLmt=selectMstTrn[6].toString();
					if(breachCountMap.get(porCd + ordrTkBsMnth + carSrs +byrGrp +prdMnth +ocfFrm +byrGrpLmt) == null){
						breachCountMap.put(porCd + ordrTkBsMnth + carSrs +byrGrp +prdMnth +ocfFrm +byrGrpLmt,1);
					byrMnthTemp.add(new Object[] { porCd , ordrTkBsMnth , carSrs ,byrGrp ,prdMnth ,ocfFrm ,byrGrpLmt });
				}
			}
		return byrMnthTemp;
	}

	public List<Object[]> getOCFBreach(B000028Output object) {
		List<Object[]> breachOCF = new ArrayList<Object[]>();
		for (Object[] temp : object.getBuyerMnthlyTemp()) {
			String breachChk = B000028QueryConstants.getBreachOCFCheck.toString();
			String breachChkC1 = B000028QueryConstants.getBreachOCFCheckC1
					.toString();
			String breachChkC2 = B000028QueryConstants.getBreachOCFCheckC2
					.toString();
			String breachOCFChkC3=B000028QueryConstants.getBreachOCFCheckC3.toString();
			LOG.info(temp[0]+"----"+temp[1]+"----"+temp[2]+"----"+temp[3]+"----"+temp[4]);
			LOG.info(temp[5]);
			if (temp[5].toString().equalsIgnoreCase("00")) {
				breachChk += breachChkC1;
			} else {
				breachChk += breachChkC2;
			}
			breachChk +=breachOCFChkC3;
			Query query = entityMngr.createNativeQuery(breachChk);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					temp[1].toString());
			query.setParameter(PDConstants.PRMTR_PORCD, temp[0].toString());
			query.setParameter(PDConstants.PRMTR_CARSRS, temp[2].toString());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, temp[4].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, temp[3].toString());
			query.setParameter(PDConstants.PRMTR_OCF_USAGE_QTY,
					temp[6].toString());
			List<Object[]> selectList = query.getResultList();
				for (Object[] selectMstTrn : selectList) {
					breachOCF.add(new Object[] { selectMstTrn[0],
							selectMstTrn[1], selectMstTrn[2], selectMstTrn[3],
							selectMstTrn[4], selectMstTrn[5], selectMstTrn[6],
							selectMstTrn[7], selectMstTrn[8], selectMstTrn[9],
							selectMstTrn[10], selectMstTrn[11] });
			}
				LOG.info("***********"+selectList.size());
				LOG.info("******");
		}
		LOG.info(breachOCF.size()+"B2");
		LOG.info(breachOCF.size()+"B2");
		return breachOCF;
	}

	public List<Object[]> getOeiBuyerId(B000028Output object) {
		List<Object[]> oeiByrIdLst = new ArrayList<Object[]>();
		String oeiByrIdExtract = B000028QueryConstants.getOeiByrIDLvlQry
				.toString();
		String byrGrpLvlMnExtract = B000028QueryConstants.getbyrGrpLvlMainQry
				.toString();
		String oeiByrIDLvlEndQry = B000028QueryConstants.getOeiByrIDLvlEndQry
				.toString();
		oeiByrIdExtract += byrGrpLvlMnExtract;
		oeiByrIdExtract += oeiByrIDLvlEndQry;
		for (Object[] temp : object.getByrCdLvl()) {
			Query query = entityMngr.createNativeQuery(oeiByrIdExtract);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, object
					.getObjB000028ParamOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000028ParamOutput().getPorCd());
			query.setParameter(PDConstants.PRMTR_CARSRS, temp[1].toString());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, temp[2].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, temp[4].toString());
			query.setParameter(PDConstants.PRMTR_BYR_CD, temp[5].toString());
			List<Object[]> byrIDLst = query.getResultList();
			for (Object[] selectMstTrn : byrIDLst) {
				oeiByrIdLst.add(new Object[] { selectMstTrn[0],
						selectMstTrn[1], selectMstTrn[2], selectMstTrn[3],
						selectMstTrn[4], selectMstTrn[5], selectMstTrn[6] });
			}
		}
		if (oeiByrIdLst.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_BUYER_EI_LEVEL_DETAILS,
							object.getObjB000028ParamOutput().getPorCd(),
							PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
		return oeiByrIdLst;
	}

	public List<Object[]> getByrCdAct() {
		String getByrCdAct = B000028QueryConstants.getByrCdActQry.toString();
		Query query = entityMngr.createNativeQuery(getByrCdAct);
		return query.getResultList();
	}

	public List<Object[]> getdataEimAutoAlloc(Object[] byrCdArry) {
		String getTempBuyrCD = B000028QueryConstants.getdatafromTempOeiBuyerId
				.toString();
		Query query = entityMngr.createNativeQuery(getTempBuyrCD);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		List<Object[]> results = query.getResultList();
		return results;
	}

	public String getRoundedAllocOeiBuyer(Object[] byrCdArry) {
		String totalAlloc = null;
		String getRoundedAlloc = B000028QueryConstants.getRoundedAllocOeiBuyer
				.toString();
		Query query = entityMngr.createNativeQuery(getRoundedAlloc);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			totalAlloc = results.get(0)[1].toString();
		}
		return totalAlloc;
	}

	public List<Object[]> getClrLvl(B000028Output object) {
		List<Object[]> clrLvlLst = new ArrayList<Object[]>();
		String oeiByrIdExtract = B000028QueryConstants.getClrLvlQry.toString();
		String byrGrpLvlMnExtract = B000028QueryConstants.getbyrGrpLvlMainQry
				.toString();
		String oeiByrIDLvlEndQry = B000028QueryConstants.getClrLvlEndQry
				.toString();
		oeiByrIdExtract += byrGrpLvlMnExtract;
		oeiByrIdExtract += oeiByrIDLvlEndQry;
		for (Object[] temp : object.getOeiBuyerId()) {
			Query query = entityMngr.createNativeQuery(oeiByrIdExtract);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, object
					.getObjB000028ParamOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000028ParamOutput().getPorCd());
			query.setParameter(PDConstants.PRMTR_CARSRS, temp[1].toString());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, temp[2].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, temp[4].toString());
			query.setParameter(PDConstants.PRMTR_BYR_CD, temp[5].toString());
			query.setParameter(PDConstants.PRMTR_OEI_BYR_ID, temp[6].toString());
			List<Object[]> byrIDLst = query.getResultList();
			for (Object[] selectMstTrn : byrIDLst) {
				clrLvlLst.add(new Object[] { selectMstTrn[0], selectMstTrn[1],
						selectMstTrn[2], selectMstTrn[3], selectMstTrn[4],
						selectMstTrn[5], selectMstTrn[6], selectMstTrn[7] });
			}
		}
		if (clrLvlLst.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_BUYER_COLOR_LEVEL_DETAILS,
							object.getObjB000028ParamOutput().getPorCd(),
							PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
		return clrLvlLst;
	}

	public List<Object[]> getOeiByrAct(B000028Output object) {
		String getByrCdAct = B000028QueryConstants.getOeiByrIdActQry.toString();
		Query query = entityMngr.createNativeQuery(getByrCdAct);
		return query.getResultList();
	}

	public List<Object[]> getdataClrLvlAutoAlloc(Object[] byrCdArry) {
		String getTempBuyrCD = B000028QueryConstants.getdataClrLvlAutoAlloc
				.toString();
		Query query = entityMngr.createNativeQuery(getTempBuyrCD);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		query.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				byrCdArry[6].toString());
		List<Object[]> results = query.getResultList();
		return results;
	}

	public String getRoundedAllocClrLvl(Object[] byrCdArry) {
		String totalAlloc = null;
		String getRoundedAlloc = B000028QueryConstants.getRoundedAllocClrLvl
				.toString();
		Query query = entityMngr.createNativeQuery(getRoundedAlloc);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		query.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				byrCdArry[6].toString());
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			totalAlloc = results.get(0)[1].toString();
		}
		return totalAlloc;
	}

	public List<Object[]> getPotLvl(B000028Output object) {
		List<Object[]> potLvlLst = new ArrayList<Object[]>();
		String potExtract = B000028QueryConstants.getPotLvlQry.toString();
		String byrGrpLvlMnExtract = B000028QueryConstants.getbyrGrpLvlMainQry
				.toString();
		String potEndExtract = B000028QueryConstants.getPotLvlEndQry.toString();
		potExtract += byrGrpLvlMnExtract;
		potExtract += potEndExtract;
		for (Object[] temp : object.getClrLvl()) {
			Query query = entityMngr.createNativeQuery(potExtract);
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, object
					.getObjB000028ParamOutput().getOrdrTkBsMnth());
			query.setParameter(PDConstants.PRMTR_PORCD, object
					.getObjB000028ParamOutput().getPorCd());
			query.setParameter(PDConstants.PRMTR_CARSRS, temp[1].toString());
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, temp[2].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, temp[4].toString());
			query.setParameter(PDConstants.PRMTR_BYR_CD, temp[5].toString());
			query.setParameter(PDConstants.PRMTR_OEI_BYR_ID, temp[6].toString());
			query.setParameter(PDConstants.PRMTR_OSEI_ID, temp[7].toString());
			List<Object[]> byrIDLst = query.getResultList();
			for (Object[] selectMstTrn : byrIDLst) {
				potLvlLst.add(new Object[] { selectMstTrn[0], selectMstTrn[1],
						selectMstTrn[2], selectMstTrn[3], selectMstTrn[4],
						selectMstTrn[5], selectMstTrn[6], selectMstTrn[7],
						selectMstTrn[8] });
			}
		}
		if (potLvlLst.isEmpty()) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_POT_LEVEL_DETAILS,
							object.getObjB000028ParamOutput().getPorCd(),
							PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}
		return potLvlLst;
	}

	public List<Object[]> getClrLvlAct(B000028Output object) {
		String getByrCdAct = B000028QueryConstants.getClrActQry.toString();
		Query query = entityMngr.createNativeQuery(getByrCdAct);
		return query.getResultList();
	}

	public List<Object[]> getdataPotLvlAutoAlloc(Object[] byrCdArry) {
		String getTempBuyrCD = B000028QueryConstants.getdataPotLvlAutoAlloc
				.toString();
		Query query = entityMngr.createNativeQuery(getTempBuyrCD);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		query.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				byrCdArry[6].toString());
		query.setParameter(PDConstants.PRMTR_OSEI_ID, byrCdArry[7].toString());
		List<Object[]> results = query.getResultList();
		return results;
	}

	public String getRoundedAllocPotLvl(Object[] byrCdArry) {
		String totalAlloc = null;
		String getRoundedAlloc = B000028QueryConstants.getRoundedAllocPotLvl
				.toString();
		Query query = entityMngr.createNativeQuery(getRoundedAlloc);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrCdArry[1].toString());
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrCdArry[2].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrCdArry[4].toString());
		query.setParameter(PDConstants.PRMTR_BYR_CD, byrCdArry[5].toString());
		query.setParameter(PDConstants.PRMTR_OEI_BYR_ID,
				byrCdArry[6].toString());
		query.setParameter(PDConstants.PRMTR_OSEI_ID, byrCdArry[7].toString());
		List<Object[]> results = query.getResultList();
		if (!results.isEmpty()) {
			totalAlloc = results.get(0)[1].toString();
		}
		return totalAlloc;
	}

	public List<Object[]> getPotAdjustLvlData() {
		String getByrCdAct = B000028QueryConstants.getPotLvlActQry.toString();
		Query query = entityMngr.createNativeQuery(getByrCdAct);
		return query.getResultList();
	}

	public void updateMnthOrdrTrnInit(String porCd, String ordrTkBsMnth) {

		String updtMnthOrdrTrn = B000028QueryConstants.updateMnthOrdrTrnInitQry
				.toString();
		Query query = entityMngr.createNativeQuery(updtMnthOrdrTrn);
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_UPDT_BY,B000028Constants.BATCH_B000028);
		query.executeUpdate();
	}

	public void updateMnthOrdrTrnOrdr(String ordrTkBsMnth, String porCd,
			List<Object[]> carSrsHrzn) throws NumberFormatException,
			ParseException {

		String updtMnthOrdrTrn = B000028QueryConstants.updateMnthOrdrTrnOrdrQtyQry
				.toString();
		for (Object[] carSrs : carSrsHrzn) {
			Query query = entityMngr.createNativeQuery(updtMnthOrdrTrn);
			int prdMnthTo = 0;
			int prdMnthFrm = Math.max(Integer.parseInt(ordrTkBsMnth),
					Integer.parseInt(carSrs[2].toString().substring(0, 6)));
			if (!carSrs[3].toString().substring(6, 8).equals("11")) {
				prdMnthTo = Math.min(
						Integer.parseInt(carSrs[3].toString().substring(0, 6)),
						Integer.parseInt(ordrTkBsMnth)
								+ Integer.parseInt(carSrs[1].toString()) - 1);
			} else {
				prdMnthTo = Math.min(
						Integer.parseInt(formatter.format(CommonUtil
								.addMonthToDate(formatter.parse(carSrs[3]
										.toString().substring(0, 6)), -1))),
						Integer.parseInt(ordrTkBsMnth)
								+ Integer.parseInt(carSrs[1].toString()) - 1);
			}
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_PORCD, porCd);
			query.setParameter(PDConstants.PRMTR_CARSRS, carSrs[0]);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_MONTH_FROM,
					prdMnthFrm);
			query.setParameter(PDConstants.PRMTR_PRODUCTION_MONTH_TO, prdMnthTo);
			query.executeUpdate();
		}

	}

	public void updateMnthOrdrTrn(String ordrTkBsMnth, String porCd,
			List<Object[]> potAdjustLvl) {
		// POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,POT_CD,ALLOCATION
		String updtMnthOrdrTrn = B000028QueryConstants.updateMnthOrdrTrnQry
				.toString();
		int i = 0;
		try {
			for (Object[] updteMnthTrn : potAdjustLvl) {
				Query query = entityMngr.createNativeQuery(updtMnthOrdrTrn);
				query.setParameter(PDConstants.PRMTR_PRD_MNTH,
						updteMnthTrn[2].toString());
				query.setParameter(PDConstants.PRMTR_OSEI_ID,
						updteMnthTrn[6].toString());
				query.setParameter(PDConstants.PRMTR_POT_CD,
						updteMnthTrn[7].toString());
				query.setParameter(PDConstants.PRMTR_AUTO_ADJUSTED_QTY,
						updteMnthTrn[8].toString());
				query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
						ordrTkBsMnth);
				query.setParameter(PDConstants.PRMTR_PORCD, porCd);
				i += query.executeUpdate();
			}
			if (i == 0) {
				CommonUtil.logMessage(PDMessageConsants.M00081,
						PDConstants.CONSTANT_V2, new String[] {
								B000028Constants.BATCH_ID_B000028,
								PDConstants.MONTHLY_ORDER_TRN });
				CommonUtil.stopBatch();
			} else {
				CommonUtil.logMessage(PDMessageConsants.M00092,
						PDConstants.CONSTANT_V1,
						new String[] { B000028Constants.BATCH_ID_B000028 });
			}
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil
					.logMessage(PDMessageConsants.M00164,
							PDConstants.CONSTANT_V3, new String[] {
									B000028Constants.BATCH_ID_B000028,
									PDConstants.UPDATION,
									PDConstants.MONTHLY_ORDER_TRN });
			CommonUtil.stopBatch();
		}

	}

	public List<Object[]> getAutoAdjustReport(String porCd,
			String ordrTkBsMnth, List<Object[]> potAdjustLvl) {
		String autoAdjustData = B000028QueryConstants.getAutoAdjustDataQry
				.toString();
		List<Object[]> autoadjustLst = new ArrayList<Object[]>();
		for (Object[] updteMnthTrn : potAdjustLvl) {
			Query query = entityMngr.createNativeQuery(autoAdjustData);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH,
					updteMnthTrn[2].toString());
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PDConstants.PRMTR_CARSRS,
					updteMnthTrn[1].toString());
			query.setParameter(PDConstants.PRMTR_PORCD,
					updteMnthTrn[0].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD,
					updteMnthTrn[3].toString());
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				autoadjustLst.add(new Object[] { result[0], result[1],
						result[2], result[3], result[4], result[5], result[6],
						result[7], result[8], result[9] });
			}
		}

		return autoadjustLst;
	}

	public List<Object[]> getAutoAdjustReportByrCd(List<Object[]> byrGrpOrdr) {
		String autoAdjustData = B000028QueryConstants.getAutoAdjustDataByrCdQry
				.toString();
		String byrGrpMain = B000028QueryConstants.getAutoAdjustMainCase20
				.toString();
		autoAdjustData += byrGrpMain;
		List<Object[]> autoadjustLstByrCd = new ArrayList<Object[]>();
		for (Object[] updteMnthTrn : byrGrpOrdr) {
			Query query = entityMngr.createNativeQuery(autoAdjustData);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH,
					updteMnthTrn[2].toString());
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					updteMnthTrn[1].toString());
			query.setParameter(PDConstants.PRMTR_PORCD,
					updteMnthTrn[0].toString());
			query.setParameter(PDConstants.PRMTR_CARSRS,
					updteMnthTrn[6].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD,
					updteMnthTrn[4].toString());
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				autoadjustLstByrCd.add(new Object[] { result[0], result[1],
						result[2], result[3], result[4], result[5], result[6],
						result[7], result[8], result[9] });
			}
		}
		return autoadjustLstByrCd;

	}

	public List<Object[]> getAutoAdjustReportClrLvl(
			List<Object[]> extrctAutoAdjstByrCd) {
		String autoAdjustDataClr = B000028QueryConstants.getAutoAdjustDataClrvlQry
				.toString();
		String autoAdjustMainCase30 = B000028QueryConstants.getAutoAdjustMainCase30
				.toString();
		autoAdjustDataClr += autoAdjustMainCase30;
		List<Object[]> autoadjustLstClrLvl = new ArrayList<Object[]>();
		for (Object[] updteMnthTrn : extrctAutoAdjstByrCd) {
			Query query = entityMngr.createNativeQuery(autoAdjustDataClr);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH,
					updteMnthTrn[2].toString());
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					updteMnthTrn[1].toString());
			query.setParameter(PDConstants.PRMTR_PORCD,
					updteMnthTrn[0].toString());
			query.setParameter(PDConstants.PRMTR_CARSRS,
					updteMnthTrn[7].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD,
					updteMnthTrn[4].toString());
			query.setParameter(PDConstants.PRMTR_BYR_CD,
					updteMnthTrn[5].toString());
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				autoadjustLstClrLvl.add(new Object[] { result[0], result[1],
						result[2], result[3], result[4], result[5], result[6],
						result[7], result[8], result[9],result[10], result[11],
						result[12], result[13], result[14], result[15], result[16]});
			}
		}
		return autoadjustLstClrLvl;
	}

	public List<Object[]> getbyrGrpC20(Object[] byrGrp, String porCd,
			String ordrTkBsMnth) {
		String byrGrpc2 = B000028QueryConstants.getbyrGrpCaseTwoQry.toString();
		String byrGrpMain = B000028QueryConstants.getAutoAdjustMainCase20
				.toString();
		byrGrpc2 += byrGrpMain;
		List<Object[]> getbyrGrpCaseTwo = new ArrayList<Object[]>();
		Query query = entityMngr.createNativeQuery(byrGrpc2);
		query.setParameter(PDConstants.PRMTR_PRD_MNTH, byrGrp[1].toString());
		query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_CARSRS, byrGrp[0].toString());
		query.setParameter(PDConstants.BUYER_GRP_CD, byrGrp[2].toString());
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			getbyrGrpCaseTwo.add(new Object[] { result[0], result[1],
					result[2], result[3], result[4], result[5], result[6],
					result[7] });
		}
		return getbyrGrpCaseTwo;
	}

	public List<Object[]> getbyrGrpC30(List<Object[]> extrctAutoAdjstByrCase20) {
		String byrGrpc3 = B000028QueryConstants.getbyrGrpCaseCLrLvlQry
				.toString();
		String autoAdjustMainCase30 = B000028QueryConstants.getAutoAdjustMainCase30
				.toString();
		byrGrpc3 += autoAdjustMainCase30;
		List<Object[]> getbyrGrpCaseCLrLvl = new ArrayList<Object[]>();
		for (Object[] temp : extrctAutoAdjstByrCase20) {
			// tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as
			// ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			// mbb.OCF_REGION_CD,ms.CAR_SRS,
			Query query = entityMngr.createNativeQuery(byrGrpc3);
			query.setParameter(PDConstants.PRMTR_PRD_MNTH, temp[2].toString());
			query.setParameter(PDConstants.PRMTR_ORDR_TK_BS_MNTH,
					temp[1].toString());
			query.setParameter(PDConstants.PRMTR_PORCD, temp[0].toString());
			query.setParameter(PDConstants.PRMTR_BYR_CD, temp[5].toString());
			query.setParameter(PDConstants.PRMTR_CARSRS, temp[7].toString());
			query.setParameter(PDConstants.BUYER_GRP_CD, temp[4].toString());
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				getbyrGrpCaseCLrLvl.add(new Object[] { result[0], result[1],
						result[2], result[3], result[4], result[5], result[6],
						result[7], result[8], result[9],result[10], result[11], 
						result[12], result[13], result[14]});
			}
		}
		return getbyrGrpCaseCLrLvl;
	}

	public List<Object[]> getPotLvlByrGrp() {
		String getByrCdAct = B000028QueryConstants.getPotLvlByrGrp.toString();
		Query query = entityMngr.createNativeQuery(getByrCdAct);
		return query.getResultList();
	}

	public void getOrdTkBsMnth(String porCd, String stgCd) {
		String getOrdTkBsMnth = B000028QueryConstants.getOrdTkBsMnthQry.toString();
		Query query = entityMngr.createNativeQuery(getOrdTkBsMnth);
		query.setParameter(PDConstants.PRMTR_PRDSTGCD, stgCd);
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		if(query.getResultList().size() > 1){
			CommonUtil.logMessage(PDMessageConsants.M00231,
					PDConstants.CONSTANT_V3, new String[] {
							B000028Constants.BATCH_ID_B000028,porCd,stgCd });
			CommonUtil.stopBatch();
		}
		
	}

}
