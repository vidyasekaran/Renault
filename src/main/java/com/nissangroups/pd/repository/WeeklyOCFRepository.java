/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000031.output.BuyerGrpInfo;
import com.nissangroups.pd.b000031.output.TrnDailyOCFLmtInfo;
import com.nissangroups.pd.b000031.output.TrnRgnWklyOCFInfo;
import com.nissangroups.pd.b000031.util.B000031Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.b000031.util.B000031QueryConstants.*;
import static com.nissangroups.pd.b000031.util.B000031Constants.*;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V5;

/**
 * Repository class for B000031
 *
 * @author z015399
 */
public class WeeklyOCFRepository {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(WeeklyOCFRepository.class);
	
	/**
	 * @param prmBn
	 * @param strPltLnSumry
	 * @param prdPlntCd
	 * @param lnClass
	 * @param prdDayNo
	 * @param ocfFrameCode00
	 * @param entityManager
	 * @return List<TrnDailyOCFLmtInfo>
	 */
	public List<TrnDailyOCFLmtInfo> getTrnDailyOCFInfo(QueryParamBean prmBn, String strPltLnSumry,
			String prdPlntCd, String lnClass, String prdDayNo,boolean ocfFrameCode00,
			EntityManager entityManager){
		
		String porCd = prmBn.getPorCd();
		String ocfRgnCd =prmBn.getOcfRgnCd();
		String otbm = prmBn.getOrdrTkBsMnth();
		String otbwn = prmBn.getOrdrTkBsWkNo();
		
		List<TrnDailyOCFLmtInfo> lstTrnDailyOCFLmtInfo = new ArrayList<TrnDailyOCFLmtInfo>();
		LOG.info("In parameters " + "PORCD="+porCd +"OCFREGIONCD="+ocfRgnCd+"OTBM="+otbm+"OTBWN="+otbwn+
				"PltLnSumry="+strPltLnSumry+"prdPlntCd="+prdPlntCd+"lnClass="+lnClass+"prdDayNo="+prdDayNo);
		String strQuery = EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_MAIN_PART.toString();
		
		if (PDConstants.YES.equals(strPltLnSumry)){
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_YES_MAIN_PART.toString();
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_CMN_CND.toString();	
			if (ocfFrameCode00){
				strQuery += FRM_CD_00_CND.toString();
			}
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_YES_PART.toString();			
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_YES_GRP_BY_PART.toString();
			
		}
		else{
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_NO_MAIN_PART.toString();
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_CMN_CND.toString();
			if (ocfFrameCode00){
				strQuery += FRM_CD_00_CND.toString();
			}
			strQuery += EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_NO_GRP_BY_PART.toString();			
		}
		
		Query query = null;
		
		if (PDConstants.YES.equals(strPltLnSumry)){			
			query = entityManager.createNativeQuery(strQuery);
			query.setParameter(PARAM_OTBM_PLUS_OTBWN,otbm+otbwn);
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_REGIONCD, ocfRgnCd);
			query.setParameter(PARAM_PROD_PLNT_CD, prdPlntCd);
			query.setParameter(PARAM_LINE_CLASS, lnClass);
			query.setParameter(PARAM_PROD_DAY_NO, prdDayNo);
			
		}
		else{			
			query = entityManager.createNativeQuery(strQuery);
			query.setParameter(PARAM_OTBM_PLUS_OTBWN,otbm+otbwn);
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_REGIONCD, ocfRgnCd);		
		}
		List<Object[]> resultList = null;
		try{
			resultList = query.getResultList();
			LOG.info("After getting result from DB");
			LOG.info("Result size is "+ resultList.size());
		}
		catch(Exception e){
			LOG.error(e);
			LOG.info("M00165"); 
		}
		
		if (PDConstants.YES.equals(strPltLnSumry)){
		
		  LOG.info("Into YES loop");
		  for (Object[] tempArr : resultList){
			
			TrnDailyOCFLmtInfo objTrnDailyOCFLmtInfo = new TrnDailyOCFLmtInfo();
			objTrnDailyOCFLmtInfo.setIntOCFLmtQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[0])));
			objTrnDailyOCFLmtInfo.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[1]));
			objTrnDailyOCFLmtInfo.setStrPrdWkNo(CommonUtil.convertObjectToString(tempArr[2]));
			objTrnDailyOCFLmtInfo.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[3]));
			objTrnDailyOCFLmtInfo.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[4]));
			objTrnDailyOCFLmtInfo.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[5]));
			objTrnDailyOCFLmtInfo.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[6]));
			objTrnDailyOCFLmtInfo.setStrOCFFrameCd(CommonUtil.convertObjectToString(tempArr[7]));
			objTrnDailyOCFLmtInfo.setStrLnCls(CommonUtil.convertObjectToString(tempArr[8]));
			objTrnDailyOCFLmtInfo.setStrPrdPltCd(CommonUtil.convertObjectToString(tempArr[9]));
			objTrnDailyOCFLmtInfo.setStrPrdDayNo(CommonUtil.convertObjectToString(tempArr[10]));
			lstTrnDailyOCFLmtInfo.add(objTrnDailyOCFLmtInfo);
		  }
		}
		else{
			LOG.info("Into NO loop");
			for (Object[] tempArr : resultList){
				
			TrnDailyOCFLmtInfo objTrnDailyOCFLmtInfo = new TrnDailyOCFLmtInfo();
			objTrnDailyOCFLmtInfo.setIntOCFLmtQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[0])));
			objTrnDailyOCFLmtInfo.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[1]));
			objTrnDailyOCFLmtInfo.setStrPrdWkNo(CommonUtil.convertObjectToString(tempArr[2]));
			objTrnDailyOCFLmtInfo.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[3]));
			objTrnDailyOCFLmtInfo.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[4]));
			objTrnDailyOCFLmtInfo.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[5]));
			objTrnDailyOCFLmtInfo.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[6]));
			objTrnDailyOCFLmtInfo.setStrOCFFrameCd(CommonUtil.convertObjectToString(tempArr[7]));
			objTrnDailyOCFLmtInfo.setStrLnCls(CommonUtil.convertObjectToString(tempArr[8]));
			objTrnDailyOCFLmtInfo.setStrPrdPltCd(CommonUtil.convertObjectToString(tempArr[9]));
			objTrnDailyOCFLmtInfo.setStrPrdDayNo(CommonUtil.convertObjectToString(tempArr[10]));
			lstTrnDailyOCFLmtInfo.add(objTrnDailyOCFLmtInfo);
			
			}
		}	
		return lstTrnDailyOCFLmtInfo;
		
	}
	
	/**
	 * @param porCd
	 * @param ocfRgnCd
	 * @param ocfByrGrpCd
	 * @param entityManager
	 * @return List<BuyerGrpInfo>
	 */
	public List<BuyerGrpInfo> getByrGrpInfo(String porCd,String ocfRgnCd, String ocfByrGrpCd, EntityManager entityManager){
		
		List<BuyerGrpInfo> lstBuyerGrpInfo = new ArrayList<BuyerGrpInfo>();
		
		Query query = entityManager.createNativeQuery(EXT_BYR_GRP_CD.toString());
		
		query.setParameter(PARAM_PORCD, porCd);
		query.setParameter(PARAM_OCFBYRGRPCD,ocfByrGrpCd);
		query.setParameter(PARAM_REGIONCD,ocfRgnCd);		
		
		List<Object[]> resultList = null;
		try{
			resultList = query.getResultList();
			for (Object[] tempArr : resultList){
				BuyerGrpInfo objBuyerGrpInfo = new BuyerGrpInfo();
				objBuyerGrpInfo.setStrByrGrpCd(CommonUtil.convertObjectToString(tempArr[0]));
				objBuyerGrpInfo.setStrOcfRgnCd(CommonUtil.convertObjectToString(tempArr[1]));
				objBuyerGrpInfo.setStrOcfByrGrpCd(CommonUtil.convertObjectToString(tempArr[2]));
				objBuyerGrpInfo.setStrAutoAllocFlag(CommonUtil.convertObjectToString(tempArr[3]));
				objBuyerGrpInfo.setCount(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[4])));
				lstBuyerGrpInfo.add(objBuyerGrpInfo);
			}
		}
		catch(Exception e){
			LOG.error(e);
		}
		return lstBuyerGrpInfo;
				
	}
	
	/**
	 * @param prmVal
	 * @param ocfByrGrpCd
	 * @param prdPlntCd
	 * @param lnClass
	 * @param prdDayNo
	 * @param entityManager
	 * @return List<TrnRgnWklyOCFInfo>
	 */
	public List<TrnRgnWklyOCFInfo> extractRgnWklyOCFLmt(QueryParamBean prmVal,String ocfByrGrpCd,String prdPlntCd,String lnClass,String prdDayNo,EntityManager entityManager ){
		
		String porCd = prmVal.getPorCd();
		String ocfRgnCd =prmVal.getOcfRgnCd();
		String otbm = prmVal.getOrdrTkBsMnth();
		String otbwn = prmVal.getOrdrTkBsWkNo();
		
		Query query = entityManager.createNativeQuery(EXT_RGN_WKLY_OCF_LMT.toString());
		query.setParameter(PARAM_OTBM_PLUS_OTBWN,otbm+otbwn);
		query.setParameter(PARAM_PORCD, porCd);
		query.setParameter(PARAM_REGIONCD, ocfRgnCd);
		query.setParameter(PARAM_PROD_PLNT_CD, prdPlntCd);
		query.setParameter(PARAM_LINE_CLASS, lnClass);
		query.setParameter(PARAM_PROD_DAY_NO, prdDayNo);
		query.setParameter(PARAM_OCFBYRGRPCD,ocfByrGrpCd);
		
		List<Object[]> resultList = null;
		
		List<TrnRgnWklyOCFInfo> lstTrnRgnWklyOCFInfo = new ArrayList<TrnRgnWklyOCFInfo>();
		try{
			resultList = query.getResultList();
			 
			for (Object[] tempArr : resultList){
				TrnRgnWklyOCFInfo objTrnRgnWklyOCFInfo = new TrnRgnWklyOCFInfo();
				objTrnRgnWklyOCFInfo.setIntRgnWklyOcfLmtQty(((BigDecimal)tempArr[0]).intValue());
				objTrnRgnWklyOCFInfo.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[1]));
				objTrnRgnWklyOCFInfo.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[2]));
				objTrnRgnWklyOCFInfo.setStrPrdWkNo(CommonUtil.convertObjectToString(tempArr[3]));
				objTrnRgnWklyOCFInfo.setStrOcfRgnCd(CommonUtil.convertObjectToString(tempArr[4]));
				objTrnRgnWklyOCFInfo.setStrOcfByrGrpCd(CommonUtil.convertObjectToString(tempArr[5]));
				objTrnRgnWklyOCFInfo.setStrOcfFrmCd(CommonUtil.convertObjectToString(tempArr[6]));
				objTrnRgnWklyOCFInfo.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[7]));
				lstTrnRgnWklyOCFInfo.add(objTrnRgnWklyOCFInfo);
			}
			
		}
		catch(Exception e){
			LOG.info(e);
			String[] strMsgParams1 = {B000031Constants.PRMTR1,B000031Constants.RGNL_WKLY_TBL,porCd,ocfRgnCd,ocfByrGrpCd};
			CommonUtil.logMessage(PDMessageConsants.M00168, CONSTANT_V5 , strMsgParams1);
			CommonUtil.stopBatch();
		}
		return lstTrnRgnWklyOCFInfo;
	}

}
