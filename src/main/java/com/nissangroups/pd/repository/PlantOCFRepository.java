/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.nissangroups.pd.b000014.output.ExtByrInfoOutput;
import com.nissangroups.pd.b000014.output.ExtMnthlyBtchPrsSttsTblOutput;
import com.nissangroups.pd.b000014.output.ExtOCFInfoOutput;
import com.nissangroups.pd.b000014.output.FeatureInfo;
import com.nissangroups.pd.b000014.output.TrnExtract;
import com.nissangroups.pd.b000014.util.B000014Constants;
import com.nissangroups.pd.exception.PdApplicationException;

import static com.nissangroups.pd.b000014.util.B000014QueryConstants.*;
import static com.nissangroups.pd.b000014.util.B000014Constants.*;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V2;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V3;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V4;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class PlantOCFRepository for B000014
 *
 * @author z015399
 */

public class PlantOCFRepository {
	
	private static final Log LOG = LogFactory.getLog(PlantOCFRepository.class.getName());
	
	/** Variable porCd */
	String porCdVal = null;
	
	/* P2 */
	/**
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * @return EXTRACTED DETAILS FROM MONTHLY BATCH PROCESS STATUS TABLE
	 * @throws Exception
	 */
	public List<ExtMnthlyBtchPrsSttsTblOutput> extMnthlyBtchPrsSttsDtls(String porCd,String otbm,EntityManager entityManager) throws Exception{
		
		List<ExtMnthlyBtchPrsSttsTblOutput> lstExtMnthlyBtchPrsSttsTblOutput = new ArrayList<ExtMnthlyBtchPrsSttsTblOutput>();
		
		Query query = entityManager.createNativeQuery(EXT_DTLS_MNTHLY_BTCH_PRCS_STTS_TBL.toString());
		
		try{
			porCdVal = porCd;
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			List<Object[]> lstResultList = query.getResultList();
			
			
			for (Object[] tempArr : lstResultList){
				ExtMnthlyBtchPrsSttsTblOutput objExtMnthlyBtchPrsSttsTblOutput = new ExtMnthlyBtchPrsSttsTblOutput();
				objExtMnthlyBtchPrsSttsTblOutput.setStrPorCd(CommonUtil.convertObjectToString(tempArr[0]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrCrSrs(CommonUtil.convertObjectToString(tempArr[1]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrOrdrTkBsMnth(CommonUtil.convertObjectToString(tempArr[2]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrdMnthFrm(CommonUtil.convertObjectToString(tempArr[3]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrdMnthTo(CommonUtil.convertObjectToString(tempArr[4]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[5]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrOCFByrGrp(CommonUtil.convertObjectToString(tempArr[6]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrmtr1(CommonUtil.convertObjectToString(tempArr[7]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrmtr2(CommonUtil.convertObjectToString(tempArr[8]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrmtr3(CommonUtil.convertObjectToString(tempArr[9]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrmtr4(CommonUtil.convertObjectToString(tempArr[10]));
				objExtMnthlyBtchPrsSttsTblOutput.setStrPrmtr5(CommonUtil.convertObjectToString(tempArr[11]));
				
				lstExtMnthlyBtchPrsSttsTblOutput.add(objExtMnthlyBtchPrsSttsTblOutput);
			}
		}
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,porCd};
			CommonUtil.logMessage(PDMessageConsants.M00358, CONSTANT_V2 , strMsgParams);
			throw new PdApplicationException();
			
		}
		
		return lstExtMnthlyBtchPrsSttsTblOutput;
	}
	
	/* P3 */
	/**
	 * @param lstExtMnthlyBtchPrsSttsTblOutput
	 * @param prdOrdStgCd
	 * @param entityManager
	 * @return spec master related information
	 */
	public List<ExtByrInfoOutput> extByrInfo(List<ExtMnthlyBtchPrsSttsTblOutput> lstExtMnthlyBtchPrsSttsTblOutput,String prdOrdStgCd,EntityManager entityManager){
		List<ExtByrInfoOutput> lstExtByrInfoOutput = new ArrayList<ExtByrInfoOutput>();
		
		Query query = entityManager.createNativeQuery(EXT_BYRGRP_BYRCD_BYRID_OSEIID.toString());
		try{
		for (ExtMnthlyBtchPrsSttsTblOutput objExtMnthlyBtchPrsSttsTblOutput : lstExtMnthlyBtchPrsSttsTblOutput ){
			porCdVal = objExtMnthlyBtchPrsSttsTblOutput.getStrPorCd();
			query.setParameter(PARAM_PORCD, objExtMnthlyBtchPrsSttsTblOutput.getStrPorCd());
			query.setParameter(PARAM_BYRGRPCD, objExtMnthlyBtchPrsSttsTblOutput.getStrOCFByrGrp());
			query.setParameter(PARAM_OTBM, objExtMnthlyBtchPrsSttsTblOutput.getStrOrdrTkBsMnth());
			query.setParameter(PARAM_PRDMNTH_FROM, objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthFrm());
			query.setParameter(PARAM_PRDMNTH_TO, objExtMnthlyBtchPrsSttsTblOutput.getStrPrdMnthTo());
			query.setParameter(PARAM_PROD_ORDER_STAGE_CD,prdOrdStgCd );			
			List<Object[]> lstResultList = query.getResultList();
			ExtByrInfoOutput objExtByrInfoOutput = new ExtByrInfoOutput();
			objExtByrInfoOutput.setObjExtMnthlyBtchPrsSttsTblOutput(objExtMnthlyBtchPrsSttsTblOutput);
			objExtByrInfoOutput.setStrByrCd((lstResultList.get(0))[0].toString());
			objExtByrInfoOutput.setStrByrGrpCd((lstResultList.get(0))[1].toString());
			objExtByrInfoOutput.setStrOEIByrID((lstResultList.get(0))[2].toString());
			objExtByrInfoOutput.setStrOSEIID((lstResultList.get(0))[3].toString());
			objExtByrInfoOutput.setIntMSQty(Integer.parseInt((lstResultList.get(0))[4].toString()));
			objExtByrInfoOutput.setIntOrdrQty(Integer.parseInt((lstResultList.get(0))[5].toString()));
			
			lstExtByrInfoOutput.add(objExtByrInfoOutput);
		}
		}
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,porCdVal};
			CommonUtil.logMessage(PDMessageConsants.M00358, CONSTANT_V2 , strMsgParams);
			
		}
		return lstExtByrInfoOutput;
	}

	/* P4.1 */
	
	/**
	 * @param lstExtByrInfoOutput
	 * @param entityManager
	 * @return EXTRACT FEATURE_CD,FRAME_CD FROM BUYER_MONTHLY_OCF_USAGE_TRN
	 */
	public List<ExtOCFInfoOutput> extOCFInfo(List<ExtByrInfoOutput> lstExtByrInfoOutput,EntityManager entityManager){
		
		List<ExtOCFInfoOutput> lstExtOCFInfoOutput = new ArrayList<ExtOCFInfoOutput>();
		
		Query query = entityManager.createNativeQuery(EXT_FRMCD_FEATURECD_PRDMNTH.toString());
		try{
		for (ExtByrInfoOutput objExtByrInfoOutput : lstExtByrInfoOutput){
			porCdVal = objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrPorCd();
			query.setParameter(PARAM_PORCD, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrPorCd());
			query.setParameter(PARAM_OTBM, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrOrdrTkBsMnth());
			query.setParameter(PARAM_PRDMNTH_FROM, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrPrdMnthFrm());
			query.setParameter(PARAM_PRDMNTH_TO, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrPrdMnthTo());
			query.setParameter(PARAM_OSEIID, objExtByrInfoOutput.getStrOSEIID());
			query.setParameter(PARAM_CARSRS, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrCrSrs());
			query.setParameter(PARAM_BYRGRPCD, objExtByrInfoOutput.getStrByrGrpCd());		
			query.setParameter(PARAM_REGIONCD, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrOCFRgnCd());
			query.setParameter(PARAM_PARAM5, objExtByrInfoOutput.getObjExtMnthlyBtchPrsSttsTblOutput().getStrPrmtr5());
			List<Object[]> lstResultList = query.getResultList();
			List<FeatureInfo> lstFeatureInfo = new ArrayList<FeatureInfo>();
			for (Object[] tempArr : lstResultList){
				FeatureInfo objFeatureInfo = new FeatureInfo();
				objFeatureInfo.setStrOCFFrmCd(tempArr[0].toString());
				objFeatureInfo.setStrFeatureCd(tempArr[1].toString());
				lstFeatureInfo.add(objFeatureInfo);
			}
			ExtOCFInfoOutput objExtOCFInfoOutput = new ExtOCFInfoOutput();
			objExtOCFInfoOutput.setObjExtByrInfoOutput(objExtByrInfoOutput);
			objExtOCFInfoOutput.setLstFeatureInfo(lstFeatureInfo);						
		}
		}
		
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,porCdVal};
			CommonUtil.logMessage(PDMessageConsants.M00358, CONSTANT_V2 , strMsgParams);
			
		}
				
		return lstExtOCFInfoOutput;
		
	}
	
	/* P2.2 */
	
	/**
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * 
	 * Update MONTHLY BATCH PROCESS STATUS TABLE
	 */
	public void uptMnthlyBtchPrsSttsTblInProgress(String porCd,String otbm,EntityManager entityManager){
		
		Query query = entityManager.createNativeQuery(UPT_MNTHLY_BTCH_PRCS_STTS_TBL_IN_PROGRESS.toString());
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM,otbm);
			query.executeUpdate();
		}
		catch(Exception e){
			CommonUtil.logMessage(PDMessageConsants.M00164, 
					PDConstants.CONSTANT_V4, new String[] {
	    			B000014Constants.FUNCTION_ID,
					PDConstants.UPDATION,
					B000014Constants.REPOTBL });
			LOG.error(e);
		    updateFailedStatus(porCd,otbm,entityManager);
									
		}
	}
	
	// Extraction of Order Qty & MS Qty 
	
	/**
	 * To get the data from Monthly Order Trn and OCF feature
	 * 
	 * Query param bean has
	 * 	@param porCd
	 * 	@param otbm
	 * 	@param prodMnthFrm
	 *	 @param prodMnthTo
	 * 	@param entityManager
	 * @return
	 */
		public List<TrnExtract> getTrnDetails1(QueryParamBean qryPrm,EntityManager entityManager) throws Exception{
		
			String porCd = qryPrm.getPorCd();
			String otbm=qryPrm.getOrdrTkBsMnth();
			String prodStageCd=qryPrm.getPrdnStgCd();
			String prodMnthFrm=qryPrm.getPrdnMnthFrm();
			String prodMnthTo=qryPrm.getPrdnMnthTo();
			String param4=qryPrm.getPrmtr4();
			String param5=qryPrm.getPrmtr5();
			
		List<TrnExtract> lstTrnExtract = new ArrayList<TrnExtract>();
		
		String strQuery1 = EXT_ORDR_QTY_MS_QTY1_MAIN_PART.toString();
		
		if ( !(PDConstants.EMPTY_STRING.equals(param5)) ){
			strQuery1 += FEATURE_CD_PART.toString();
		}
		
		if ( PDConstants.TEN.equals(param4)){
			
			strQuery1 += OCF_FRAME_CD_00_PART.toString();
		}
		else if ( PDConstants.TWENTY.equals(param4)){
			strQuery1 += OCF_FRAME_CD_NOT_00_PART.toString();
		}
		
		strQuery1 += EXT_ORDR_QTY_MS_QTY1_GROUP_BY.toString();
		
		Query query = entityManager.createNativeQuery(strQuery1);
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM,otbm);
			query.setParameter(PARAM_PRDMNTH_FROM,prodMnthFrm);
			query.setParameter(PARAM_PRDMNTH_TO,prodMnthTo);
			
			if (  !(PDConstants.EMPTY_STRING.equals(param5)) ){
				query.setParameter(PARAM_FEATURECD,param5);
			}
			
			List<Object[]> lstResultList = query.getResultList();
			for (Object[] tempArr : lstResultList){
				TrnExtract objTrnExtract = new TrnExtract();
				
				objTrnExtract.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[0]));
				objTrnExtract.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[1]));
				objTrnExtract.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[2]));
				objTrnExtract.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[3]));
				objTrnExtract.setIntOrdrQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[4])));
				objTrnExtract.setIntMSQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[5])));
				objTrnExtract.setStrPorCd(CommonUtil.convertObjectToString(tempArr[6]));
				objTrnExtract.setStrOtbm(CommonUtil.convertObjectToString(tempArr[7]));
				objTrnExtract.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[8]));
				
				lstTrnExtract.add(objTrnExtract);
			}			
		}	
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,B000014Constants.P1TBL,porCd,prodStageCd};
			CommonUtil.logMessage(PDMessageConsants.M00359, CONSTANT_V4 , strMsgParams);
			throw new PdApplicationException();
			
			
		}
		return lstTrnExtract;
		
	}
	
	// Regional level Buyer Group Summary extraction
	
	/**
	 * 
	 * @param porCd
	 * @param otbm
	 * @param ocfByrGrpCd
	 * @param ocfRgnCd
	 * @param featureCd
	 * @param carSrs
	 * @param prodMnthFrm
	 * @param prodMnthTo
	 * @param entityManager
	 * @return
	 */
	
	public List<TrnExtract> getTrnDetails2(QueryParamBean qryPrm,String ocfByrGrpCd,String featureCd,	String prodMnth,EntityManager entityManager) throws Exception{
		
		String porCd=qryPrm.getPorCd();
		String otbm=qryPrm.getOrdrTkBsMnth();
		String ocfRgnCd=qryPrm.getOcfRgnCd();
		String carSrs=qryPrm.getCarSrs();
		String param4=qryPrm.getPrmtr4();
		
		List<TrnExtract> lstTrnExtract = new ArrayList<TrnExtract>();
		
		String strQuery1 = EXT_ORDR_QTY_MS_QTY2_MAIN_PART.toString();
		
		
		
		if ( PDConstants.TEN.equals(param4)){
			
			strQuery1 += OCF_FRAME_CD_00_PART.toString();
		}
		else if ( PDConstants.TWENTY.equals(param4)){
			strQuery1 += OCF_FRAME_CD_NOT_00_PART.toString();
		}
		
		strQuery1 += EXT_ORDR_QTY_MS_QTY2_GROUP_BY.toString();
		
		
		Query query = entityManager.createNativeQuery(strQuery1);
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM,otbm);
			query.setParameter(PARAM_OCFBYRGRPCD, ocfByrGrpCd);
			query.setParameter(PARAM_REGIONCD,ocfRgnCd);
			query.setParameter(PARAM_FEATURECD, featureCd);
			query.setParameter(PARAM_CARSRS,carSrs);
			query.setParameter(PARAM_PRDMNTH, prodMnth);
			
			
			
			List<Object[]> lstResultList = query.getResultList();
			for (Object[] tempArr : lstResultList){
				TrnExtract objTrnExtract = new TrnExtract();
				objTrnExtract.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[0]));
				objTrnExtract.setStrByrGrpCd(CommonUtil.convertObjectToString(tempArr[1]));
				objTrnExtract.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[2]));
				objTrnExtract.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[3]));
				objTrnExtract.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[4]));
				objTrnExtract.setIntOrdrQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[5])));
				objTrnExtract.setIntMSQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[6])));
				objTrnExtract.setStrPorCd(CommonUtil.convertObjectToString(tempArr[7]));
				objTrnExtract.setStrOtbm(CommonUtil.convertObjectToString(tempArr[8]));
				objTrnExtract.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[9]));
				lstTrnExtract.add(objTrnExtract);
							
			}
		}
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,PDConstants.MESSAGE_DATA,porCd,B000014Constants.P1TBL};
			CommonUtil.logMessage(PDMessageConsants.M00160, CONSTANT_V3 , strMsgParams); 
			throw new PdApplicationException();
			
		}
		
		return lstTrnExtract;
		
	}
	
	/**
	 *  To get the Regional OCF Limit
	 *  
	 *  Query param bean has
	 * 	@param porCd
	 * 	@param otbm
	 * 	@param carSrs
	 * 	@param ocfRegCd
	 * 	@param prmtr4
	 * @param prdMnth
	 * @param ocfByrGrpCd
	 * @param featureCd
	 * @param entityManager
	 * @return
	 */
	
	public int getRegOCFLmt(QueryParamBean qryParm, String prdMnth,String ocfByrGrpCd, String featureCd,EntityManager entityManager)
	{
		int intRegOCFLmt = 0;
		
		String porCd=qryParm.getPorCd();
		String otbm=qryParm.getOrdrTkBsMnth();
		String carSrs=qryParm.getCarSrs();
		String ocfRegCd=qryParm.getOcfRgnCd();
		String param4=qryParm.getPrmtr4();
		
		String strQuery = EXT_REG_MNTHLY_OCF_LMT.toString();
		
		if ( PDConstants.TEN.equals(param4)){
			
			strQuery += EXT_REG_MNTHLY_OCF_LMT_FRAME_CODE_ZERO.toString();
			
		}
		else if ( PDConstants.TWENTY.equals(param4)){
			
			strQuery += EXT_REG_MNTHLY_OCF_LMT_FRAME_CODE_NOT_ZERO.toString();
		}
		
		Query query = entityManager.createNativeQuery(strQuery);
		
		query.setParameter(PARAM_PORCD, porCd);
		query.setParameter(PARAM_OTBM, otbm);
		query.setParameter(PARAM_PRDMNTH, prdMnth);
		query.setParameter(PARAM_CARSRS, carSrs);
		query.setParameter(PARAM_REGIONCD, ocfRegCd);
		query.setParameter(PARAM_OCFBYRGRPCD, ocfByrGrpCd);
		query.setParameter(PARAM_FEATURECD, featureCd);
		
		try{
			Object objResult = query.getSingleResult();
			
			intRegOCFLmt = Integer.parseInt(CommonUtil.convertObjectToString(objResult));
		}
		
		catch(Exception e){
			LOG.error(e);
			String[] strMsgParams = {B000014Constants.FUNCTION_ID,porCd};
			CommonUtil.logMessage(PDMessageConsants.M00358, CONSTANT_V2 , strMsgParams);
		}
		
		return intRegOCFLmt;
		
		
	}
	
	// Process 7.3
	/**
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * 
	 * Update failed status in  MONTHLY_BATCH_PROCESS_STATUS table
	 */
	public void updateFailedStatus(String porCd,String otbm, EntityManager entityManager){
		
		Query query = entityManager.createNativeQuery(UPT_MNTHLY_BTCH_PRCS_STTS_TBL_FAILURE.toString());
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM,otbm);
			query.executeUpdate();
		}
		catch(Exception e){
			LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00164, 
					PDConstants.CONSTANT_V4, new String[] {
	    			B000014Constants.FUNCTION_ID,
					PDConstants.UPDATION,
					B000014Constants.REPOTBL });
			CommonUtil.stopBatch();
									
		}
	}
	
	//Process 7.2
	
	/**
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * 
	 * 
	 * Update MONTHLY BATCH PROCESS STATUS TABLE
	 */
	public void updateSuccessStatus(String porCd,String otbm, EntityManager entityManager){
		
		Query query = entityManager.createNativeQuery(UPT_MNTHLY_BTCH_PRCS_STTS_TBL_SUCCESS.toString());
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM,otbm);
			query.executeUpdate();
		}
		catch(Exception e){
			LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00164, 
					PDConstants.CONSTANT_V4, new String[] {
	    			B000014Constants.FUNCTION_ID,
					PDConstants.UPDATION,
					B000014Constants.REPOTBL });
			CommonUtil.stopBatch();
									
		}
		
	}
}
