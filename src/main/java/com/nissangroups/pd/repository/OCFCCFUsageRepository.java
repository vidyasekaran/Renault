/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.b000017.util.Constants.*;
import static com.nissangroups.pd.util.PDMessageConsants.*;
import static com.nissangroups.pd.b000017.util.QueryConstants.*;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000017.output.ByrGrpFeatureUsage;
import com.nissangroups.pd.b000017.output.FeatureInfo;
import com.nissangroups.pd.b000017.output.MnthlyOrder;
import com.nissangroups.pd.b000017.output.RgnLvlOCFUsage;
import com.nissangroups.pd.b000017.output.TableDetails;
import com.nissangroups.pd.b000017.output.TrnFeatureInfo;
import com.nissangroups.pd.b000017.output.UK_OSEI;
import com.nissangroups.pd.b000017.util.Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

import java.sql.Timestamp;

public class OCFCCFUsageRepository {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(OCFCCFUsageRepository.class
			.getName());
	
	/**
	 * Get the updated OSEI
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * @return List<UK_OSEI>
	 */
	public List<UK_OSEI> getUpdtOSEISpMst(String porCd,String otbm,EntityManager entityManager)
	{
		List<UK_OSEI> lstUK_OSEI = new ArrayList<UK_OSEI>();
		
		
		Query query = entityManager.createNativeQuery(UPDATED_OSEI_ID_IN_SPEC_MASTER.toString());
		
		try{
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			List<Object[]> resultList = query.getResultList();
			LOG.info("getUpdtOSEISpMst - No of UK_OSEI ---" + resultList.size());
			for (Object[] tmpArr : resultList){
				UK_OSEI objUK_OSEI = new UK_OSEI();
				objUK_OSEI.setStrUK_OSEI_ID(tmpArr[0].toString());
				objUK_OSEI.setStrOEI_BUYER_ID(tmpArr[1].toString());	
				lstUK_OSEI.add(objUK_OSEI);
			}			
			
		}
		catch(Exception e){
			LOG.info(e);
		}
		
		return lstUK_OSEI;
		
	}
	
	/**
	 * Get the updated OSEI for feature
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * @return lstOSEI 
	 */
	
	public List<String> getUpdtFeatureOSEI(String porCd,String otbm,EntityManager entityManager){
		
		List<String> lstOSEI=null;
		Query query1 = entityManager.createNativeQuery(UPDATED_FEATURE_OSEI_ID_IN_ORDRLE_SLS_FEAT.toString());
		
		try{
			query1.setParameter(PARAM_PORCD,porCd);
			query1.setParameter(PARAM_OTBM, otbm);
			List<Object[]> resultList1 = query1.getResultList();
			LOG.info(" getUpdtFeatureOSEI - No of Feature OSEI--- "+ resultList1.size());		
		    lstOSEI = new ArrayList<String>();
			
			for (Object[] tmpArr : resultList1){
				 lstOSEI.add(tmpArr[0].toString());				
			}		
			
		}
		catch(Exception e){
			LOG.info(e);
			
		}
		
		return lstOSEI;
		
	}
	
	/**
	 * Get the updated OEI for feature
	 * @param porCd
	 * @param otbm
	 * @param entityManager
	 * @return
	 */
	public List<String> getUpdtFeatureOEI(String porCd,String otbm,EntityManager entityManager){
		List<String> lstOEI=null;
		Query query2 = entityManager.createNativeQuery(UPDATED_FEATURE_OEI_BUYER_ID_IN_MST_OEI_FEAT.toString());
		try{
			query2.setParameter(PARAM_PORCD,porCd);
			query2.setParameter(PARAM_OTBM,otbm);
			List<Object[]> resultList2 = query2.getResultList();		
			LOG.info(" getUpdtFeatureOEI - No of Feature OEI--- "+ resultList2.size());
		    lstOEI = new ArrayList<String>();
			
			for (Object[] tmpArr : resultList2){
				 lstOEI.add(tmpArr[0].toString());				
			}		
			
		}
		catch(Exception e){
			LOG.info(e);
			
		}
		
		return lstOEI;
	}
	/**
	 * P0002.2
	 * This method is to get the MonthlyOrderDetails 
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param chgCount
	 * @param entityManager
	 * @return
	 */
	
	public List getMnthlyOrdrDtls(String porCd,String otbm,String updateOnlyFlag,int chgCount,EntityManager entityManager){
		List<Object> lst = new ArrayList<Object>();
		List<MnthlyOrder> lstMnthlyOrder = new ArrayList<MnthlyOrder>();
		List<TableDetails> lstTableDetails = new ArrayList<TableDetails>();
		Query query3 = null;
		if (updateOnlyFlag.equals(PDConstants.CONSTANT_ONE) && chgCount == 0){
			query3 = entityManager.createNativeQuery(ORDER_DETAILS_UPT_FLG.toString());
		}
		else{
			query3 = entityManager.createNativeQuery(ORDER_DETAILS.toString());
		}
		try{
			query3.setParameter(PARAM_PORCD, porCd);
			query3.setParameter(PARAM_OTBM,otbm);
			List<Object[]> resultList3 = query3.getResultList();
			LOG.info(" getMnthlyOrdrDtls - No of Order Details--- "+ resultList3.size());
			if ( resultList3 != null && !(resultList3.isEmpty()))
			{
			  for (Object[] tmpArr : resultList3){
				MnthlyOrder objMnthlyOrder = new MnthlyOrder();
				TableDetails objTableDetails = new TableDetails();
				objMnthlyOrder.setStrCarSeries(CommonUtil.convertObjectToString(tmpArr[0]));
				objMnthlyOrder.setStrUK_OSEI_ID(CommonUtil.convertObjectToString(tmpArr[1]));
				objMnthlyOrder.setStrUK_OEI_BUYER_ID(CommonUtil.convertObjectToString(tmpArr[2]));
				objMnthlyOrder.setStrPrdMnth(tmpArr[3].toString());
				objMnthlyOrder.setStrBuyerGroupCode(CommonUtil.convertObjectToString(tmpArr[4]));
				objMnthlyOrder.setIntMSQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[6])));
				objMnthlyOrder.setIntOrdQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[7])));
				objMnthlyOrder.setUpdateOnlyFlag(updateOnlyFlag);
				objTableDetails.setStrTableName(PDConstants.MASTER_TABLE_NAME);
				lstMnthlyOrder.add(objMnthlyOrder);
				lstTableDetails.add(objTableDetails);
				
			  }	
			}  
			else{
				if (PDConstants.CONSTANT_ONE.equals(updateOnlyFlag)){
					CommonUtil.stopBatch();
				}
			}
			
			
		}
		catch(Exception e){
			LOG.info(e);
		}
		
		lst.add(lstMnthlyOrder);
		lst.add(lstTableDetails);
		
		return lst;
	}

	/**
	 * P0003.1
	 * Get the CCF feature information for OSEI ID
	 * @param porCd
	 * @param oseiID
	 * @param prdMnth
	 * @param entityManager
	 * @return
	 */
	public List<FeatureInfo> getCCFFeatureInfo(String porCd,String oseiID,String prdMnth,EntityManager entityManager){
		
		List<FeatureInfo> lstFeatureInfo = new ArrayList<FeatureInfo>();
		Query query = entityManager.createNativeQuery(CCF_DETAILS.toString());
		
		try{			
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OSEIID, oseiID);
			query.setParameter(PARAM_PRDMNTH, prdMnth);
			
			List<Object[]> resultList = query.getResultList();
			LOG.info(" getCCFFeatureInfo - No of CCF Feature Info--- "+ resultList.size());
			
			for (Object[] tmpArr : resultList){
				FeatureInfo objFeatureInfo = new FeatureInfo();
				objFeatureInfo.setStrFeatureCode(CommonUtil.convertObjectToString(tmpArr[0]));
				objFeatureInfo.setStrOCFFrameCode(CommonUtil.convertObjectToString(tmpArr[1]));
				objFeatureInfo.setIntOrdrQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[2])));
				objFeatureInfo.setStrUK_OSEI_ID(oseiID);
				objFeatureInfo.setStrProductionMonth(prdMnth);
				lstFeatureInfo.add(objFeatureInfo);
			}
			
			
		}
		catch(Exception e){
			LOG.info(e);
			
		}
		
		return lstFeatureInfo;
	}
	
	/**
	 * P0003.2
	 * Get the OCF Feature Info
	 * @param porCd
	 * @param byrID
	 * @param prdMnth
	 * @param entityManager
	 * @return
	 */
	public List<FeatureInfo> getOCFFeatureInfo(String porCd,String byrID,String oseiID,String prdMnth,EntityManager entityManager){
		
		List<FeatureInfo> lstFeatureInfo1 = new ArrayList<FeatureInfo>();
		Query query1 = entityManager.createNativeQuery(OCF_DETAILS.toString());
		
		try{
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_BYRID, byrID);
			query1.setParameter(PARAM_OSEIID, oseiID);
			query1.setParameter(PARAM_PRDMNTH, prdMnth);
			List<Object[]> resultList = query1.getResultList();
			LOG.info(" getOCFFeatureInfo - No of OCF Feature Info--- "+ resultList.size());
			for (Object[] tmpArr : resultList){
				FeatureInfo objFeatureInfo = new FeatureInfo();
				objFeatureInfo.setStrFeatureCode(CommonUtil.convertObjectToString(tmpArr[0]));
				objFeatureInfo.setStrOCFFrameCode(CommonUtil.convertObjectToString(tmpArr[1]));
				objFeatureInfo.setIntOrdrQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[2])));
				objFeatureInfo.setStrProductionMonth(prdMnth);
				objFeatureInfo.setStrUK_OSEI_ID(oseiID);
				lstFeatureInfo1.add(objFeatureInfo);
			}
		}
		catch(Exception e){
			LOG.info(e);
			
		}
		
		return lstFeatureInfo1;
		
	}
	
	/**
	 * P0004.1
	 * This method deletes the existing data in Buyer Monthly OCF Usage
	 * @param porCd
	 * @param updateOnlyFlag
	 * @param otbm
	 * @param OCF_OSEI_ID
	 * @param CCF_OSEI_ID
	 * @param entityManager
	 */
	
	public void delByrMnthlyOCFUsg(String porCd,String updateOnlyFlag, String otbm,String strOSEIId,EntityManager entityManager){
		
		if ( updateOnlyFlag.equals(PDConstants.CONSTANT_ZERO)){
		
			Query query = entityManager.createNativeQuery(DELETE_BUYER_MNTHLY_OCF_USG_DETAILS.toString());
		
			try{
			
				query.setParameter(PARAM_PORCD, porCd);
				query.setParameter(PARAM_OTBM, otbm);			
				query.executeUpdate();
			
			}
			catch(Exception e){
				LOG.info(e);
				String[] strMsgParams = {Constants.BATCH_ID,Constants.DELETION,Constants.TRN_BUYER_MNTHLY_OCF_USG};
				CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
				CommonUtil.stopBatch();
			
			}
		
		}
		else {
			
			Query query = entityManager.createNativeQuery(DELETE_BUYER_MNTHLY_OCF_USG_DETAILS_UPT_FLG.toString());
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_OSEIID, strOSEIId);
			try{
				    query.executeUpdate();
				   
			}
				
			catch(Exception e){
				LOG.info(e);
			
					String[] strMsgParams = {Constants.BATCH_ID,Constants.DELETION,Constants.TRN_BUYER_MNTHLY_OCF_USG};
					CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
					CommonUtil.stopBatch();
				}
			}
		
	}
	
	/**
	 * P0004.2
	 *  This method inserts the Buyer Monthly OCF USG details 
	 * @param porCd
	 * @param otbm
	 * @param lstOCFFeatureInfo
	 * @param lstCCFFeatureInfo
	 * @param entityManager
	 */
	public void insByrMnthlyOCFUsg(String porCd, String otbm,List<FeatureInfo> lstOCFFeatureInfo,EntityManager entityManager){
		
	   Query query = entityManager.createNativeQuery(INSERT_BUYER_MNTHLY_OCF_USG_DETAILS.toString());
	   try{
		   query.setParameter(PARAM_PORCD, porCd);
		   query.setParameter(PARAM_OTBM, otbm);
		   
		  
		   
		   for (FeatureInfo objOCFFeatureInfo : lstOCFFeatureInfo){
			   query.setParameter(PARAM_PRDMNTH, objOCFFeatureInfo.getStrProductionMonth());
			   query.setParameter(PARAM_OSEIID, objOCFFeatureInfo.getStrUK_OSEI_ID());
			   
			   
			   
			   query.setParameter(PARAM_FEATURECD, objOCFFeatureInfo.getStrFeatureCode());
			   query.setParameter(PARAM_OCFFRMCD, objOCFFeatureInfo.getStrOCFFrameCode());
			   query.setParameter(PARAM_CARSRS, objOCFFeatureInfo.getStrCarSeries());
			   query.setParameter(PARAM_BYRGRPCD, objOCFFeatureInfo.getStrBuyerGroupCode());
			   query.setParameter(PARAM_BYROCFUSGQTY, objOCFFeatureInfo.getIntOrdrQty());	
			 
			   query.executeUpdate();
		   
		   }
		   String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTED,Constants.TRN_BUYER_MNTHLY_OCF_USG};
		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		   
	   }
	   catch(Exception e){
		   LOG.info(e);
		   String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_BUYER_MNTHLY_OCF_USG};
		   CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
		   CommonUtil.stopBatch();
		   
	   }
		
	}
	
	/**
	 * P0005
	 * This method summarizes the Feature usage at Buyer Group level
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstByrGrpCd
	 * @param entityManager
	 * @return
	 */
	
	public List<ByrGrpFeatureUsage> getLstByrGrpFeatureUsage(String porCd, String otbm,String updateOnlyFlag,List<String> lstByrGrpCd,EntityManager entityManager){
		
		List<ByrGrpFeatureUsage> lstByrGrpFeatureUsage = new ArrayList<ByrGrpFeatureUsage>();
		if (updateOnlyFlag.equals(PDConstants.CONSTANT_ONE)){
		Query query1 = entityManager.createNativeQuery(SUMMARIZE_BUYER_GROUP_OCF_USG_DETAILS_UPDATE_FLAG.toString());
		try{
			
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_OTBM, otbm);
			for (String byrGrpCd : lstByrGrpCd){				
					query1.setParameter(PARAM_BYRGRPCD, byrGrpCd);
					List<Object[]> resultList = query1.getResultList();
					if ( resultList.isEmpty() ){
						CommonUtil.stopBatch();
					}
					for (Object[] tmpArr : resultList){
						ByrGrpFeatureUsage objByrGrpFeatureUsage = new ByrGrpFeatureUsage();
						objByrGrpFeatureUsage.setStrPorCd(CommonUtil.convertObjectToString(tmpArr[0]));
						objByrGrpFeatureUsage.setIntSumByrGrpFeatureUsageQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[1])));
						objByrGrpFeatureUsage.setStrFeatureCd(CommonUtil.convertObjectToString(tmpArr[2]));
						objByrGrpFeatureUsage.setStrOCFFrmCd(CommonUtil.convertObjectToString(tmpArr[3]));
						objByrGrpFeatureUsage.setStrFeatureTypCd(CommonUtil.convertObjectToString(tmpArr[4]));
						objByrGrpFeatureUsage.setStrOrdrTkBsMnth(CommonUtil.convertObjectToString(tmpArr[5]));
						objByrGrpFeatureUsage.setStrPrdMnth(CommonUtil.convertObjectToString(tmpArr[6]));
						objByrGrpFeatureUsage.setStrByrGrpCd(byrGrpCd);
						lstByrGrpFeatureUsage.add(objByrGrpFeatureUsage);
						
					}
					
					
				}
			 
			
			
		}
		catch(Exception e){
			LOG.info(e);
			CommonUtil.stopBatch();
		  }
		
		}
		else{
			Query query2 = entityManager.createNativeQuery(SUMMARIZE_BUYER_GROUP_OCF_USG_DETAILS.toString());	
			try{
				
				query2.setParameter(PARAM_PORCD, porCd);
				query2.setParameter(PARAM_OTBM, otbm);
				List<Object[]> resultList = query2.getResultList();
				if ( resultList == null  ){
					CommonUtil.stopBatch();
				}
				for (Object[] tmpArr : resultList){
					ByrGrpFeatureUsage objByrGrpFeatureUsage = new ByrGrpFeatureUsage();
					objByrGrpFeatureUsage.setStrPorCd(CommonUtil.convertObjectToString(tmpArr[0]));
					objByrGrpFeatureUsage.setIntSumByrGrpFeatureUsageQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[1])));
					objByrGrpFeatureUsage.setStrFeatureCd(CommonUtil.convertObjectToString(tmpArr[2]));
					objByrGrpFeatureUsage.setStrOCFFrmCd(CommonUtil.convertObjectToString(tmpArr[3]));
					objByrGrpFeatureUsage.setStrFeatureTypCd(CommonUtil.convertObjectToString(tmpArr[4]));
					objByrGrpFeatureUsage.setStrOrdrTkBsMnth(CommonUtil.convertObjectToString(tmpArr[5]));
					objByrGrpFeatureUsage.setStrPrdMnth(CommonUtil.convertObjectToString(tmpArr[6]));
					lstByrGrpFeatureUsage.add(objByrGrpFeatureUsage);
					
				}
				
			}
			catch(Exception e){
				LOG.info(e);
				CommonUtil.stopBatch();
				
			}
			
		}
		return lstByrGrpFeatureUsage;
		
	}
	
	/**
	 * P00061a
	 * This method initializes the Buyer Group Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstByrGrpCd
	 * @param entityManager
	 */
	public void initByrGrpLvlOCFUsg(String porCd,String otbm,String updateOnlyFlag,String strByrGrpCd,EntityManager entityManager){
		
		if (updateOnlyFlag.equals(PDConstants.CONSTANT_ONE)){
			Query query1 = entityManager.createNativeQuery(Init_BUYER_GRP_MNTHLY_OCF_LMT.toString());
			try{
					query1.setParameter(PARAM_PORCD, porCd);
					query1.setParameter(PARAM_OTBM, otbm);
					query1.setParameter(PARAM_BYRGRPCD, strByrGrpCd);
					query1.executeUpdate();
					
								
			}
			catch(Exception e){
					LOG.info(e);
					String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
					CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
					CommonUtil.stopBatch();
			}
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
			
		}
		else{
			Query query2 = entityManager.createNativeQuery(Init_BUYER_GRP_MNTHLY_OCF_LMT_UPT_FLG_ZERO.toString());
			
			try{
				query2.setParameter(PARAM_PORCD, porCd);
				query2.setParameter(PARAM_OTBM, otbm);
				query2.executeUpdate();
			
			}
			catch(Exception e){
				LOG.info(e);
				String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
				CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
				CommonUtil.stopBatch();
			}
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		}
		
		
	}
	
	/**
	 * P00061b
	 * This method updates & inserts records into Buyer Group Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstByrGrpFeatureUsage
	 * @param entityManager
	 */
	
	public void uptInsByrGrpLvlOCFUsg(String porCd,String otbm,List<ByrGrpFeatureUsage> lstByrGrpFeatureUsage,EntityManager entityManager){		
		
		try{
			for (ByrGrpFeatureUsage objByrGrpFeatureUsage:lstByrGrpFeatureUsage){
				Query query = entityManager.createNativeQuery(SELECT_BUYER_GRP_MNTHLY_OCF_LMT.toString());
				query.setParameter(PARAM_PORCD, porCd);
				query.setParameter(PARAM_OTBM, otbm);
				query.setParameter(PARAM_PRDMNTH, objByrGrpFeatureUsage.getStrPrdMnth());
				query.setParameter(PARAM_CARSRS, objByrGrpFeatureUsage.getStrCrSrs());
				query.setParameter(PARAM_BYRGRPCD, objByrGrpFeatureUsage.getStrByrGrpCd());
				query.setParameter(PARAM_OCFFRMCD, objByrGrpFeatureUsage.getStrOCFFrmCd());
				query.setParameter(PARAM_FEATURECD, objByrGrpFeatureUsage.getStrFeatureCd());
				int result = query.getResultList().size();
				if (result > 0){			
					uptByrGrpLvlOCFUsg(porCd,otbm,objByrGrpFeatureUsage,entityManager);
				}
				else{
					insByrGrpLvlOCFUsg(porCd,otbm,objByrGrpFeatureUsage,entityManager);
				}
			
		}
		}
		catch(Exception e){
			LOG.info(e);
		}
		
	}
	
	/**
	 * P00061b
	 * This method updates into Buyer group Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param objByrGrpFeatureUsage
	 * @param entityManager
	 */
		
	private void uptByrGrpLvlOCFUsg(String porCd,String otbm,ByrGrpFeatureUsage objByrGrpFeatureUsage,EntityManager entityManager){
		try{
			Query query = entityManager.createNativeQuery(UPDATE_BUYER_GRP_MNTHLY_OCF_LMT.toString());
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_PRDMNTH, objByrGrpFeatureUsage.getStrPrdMnth());
			query.setParameter(PARAM_CARSRS, objByrGrpFeatureUsage.getStrCrSrs());
			query.setParameter(PARAM_BYRGRPCD, objByrGrpFeatureUsage.getStrByrGrpCd());
			query.setParameter(PARAM_OCFFRMCD, objByrGrpFeatureUsage.getStrOCFFrmCd());
			query.setParameter(PARAM_FEATURECD, objByrGrpFeatureUsage.getStrFeatureCd());
			query.setParameter(PARAM_BYRGRPOCFUSGQTY,objByrGrpFeatureUsage.getIntSumByrGrpFeatureUsageQty());
			
			query.executeUpdate();
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		}
		catch(Exception e){
			LOG.info(e);
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
			CommonUtil.stopBatch();
			
		}
	}
	
	/**
	 * P0006.2
	 * This method inserts into Buyer Group Level OCF usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param objByrGrpFeatureUsage
	 * @param entityManager
	 */
	private void insByrGrpLvlOCFUsg(String porCd,String otbm,ByrGrpFeatureUsage objByrGrpFeatureUsage,EntityManager entityManager){
		try{
			Query query = entityManager.createNativeQuery(INSERT_BUYER_GRP_MNTHLY_OCF_LMT.toString());
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_PRDMNTH, objByrGrpFeatureUsage.getStrPrdMnth());
			query.setParameter(PARAM_CARSRS, objByrGrpFeatureUsage.getStrCrSrs());
			query.setParameter(PARAM_BYRGRPCD, objByrGrpFeatureUsage.getStrByrGrpCd());
			query.setParameter(PARAM_OCFFRMCD, objByrGrpFeatureUsage.getStrOCFFrmCd());
			query.setParameter(PARAM_FEATURECD, objByrGrpFeatureUsage.getStrFeatureCd());
			query.setParameter(PARAM_FEATURETYPECD, objByrGrpFeatureUsage.getStrFeatureTypCd());
			query.setParameter(PARAM_BYRGRPOCFUSGQTY, objByrGrpFeatureUsage.getIntSumByrGrpFeatureUsageQty());			
			query.executeUpdate();
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		}
		catch(Exception e){
			LOG.info(e);
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
			CommonUtil.stopBatch();
		}
		
	}
	
	/**
	 * P0007
	 * This method summarizes  the Regional Level Feature Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstCarSeries
	 * @param entityManager
	 * @return
	 */
	public List<RgnLvlOCFUsage> getLstRgnFeatureUsage(String porCd, String otbm,String updateOnlyFlag,List<String> lstCarSeries,EntityManager entityManager){
		
		List<RgnLvlOCFUsage> lstRgnLvlOCFUsage = new ArrayList<RgnLvlOCFUsage>();
		
		if (updateOnlyFlag.equals(PDConstants.CONSTANT_ONE)){
			Query query1 = entityManager.createNativeQuery(SUMMARIZE_ORDER_VOLUME_REGION_LEVEL_UPT_FLG.toString());
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_OTBM, otbm);
			
			for (String strCarSrs : lstCarSeries){
				try{
					query1.setParameter(PARAM_CARSRS, strCarSrs);	
					List<Object[]> resultList = query1.getResultList();
					for (Object[] tmpArr : resultList ){
						RgnLvlOCFUsage objRgnLvlOCFUsage = new RgnLvlOCFUsage();
						objRgnLvlOCFUsage.setIntSumRgnOCFUsageQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[0])));
						objRgnLvlOCFUsage.setStrFeatureCd(CommonUtil.convertObjectToString(tmpArr[1]));
						objRgnLvlOCFUsage.setStrOCFFrmCd(CommonUtil.convertObjectToString(tmpArr[2]));
						objRgnLvlOCFUsage.setStrOrdrTkBsMnth(CommonUtil.convertObjectToString(tmpArr[4]));
						objRgnLvlOCFUsage.setStrPrdMnth(CommonUtil.convertObjectToString(tmpArr[5]));
						objRgnLvlOCFUsage.setStrOCFRgnCd(CommonUtil.convertObjectToString(tmpArr[6]));
						objRgnLvlOCFUsage.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tmpArr[7]));
						lstRgnLvlOCFUsage.add(objRgnLvlOCFUsage);
					}
				}
				catch(Exception e){
				LOG.info(e);
				}
			}	
			
		}
		else{
			Query query2 = entityManager.createNativeQuery(SUMMARIZE_ORDER_VOLUME_REGION_LEVEL.toString());
			query2.setParameter(PARAM_PORCD, porCd);
			query2.setParameter(PARAM_OTBM, otbm);
			try{					
				List<Object[]> resultList = query2.getResultList();
				for (Object[] tmpArr : resultList ){
					RgnLvlOCFUsage objRgnLvlOCFUsage = new RgnLvlOCFUsage();
					objRgnLvlOCFUsage.setIntSumRgnOCFUsageQty(Integer.parseInt(CommonUtil.convertObjectToString(tmpArr[0])));
					objRgnLvlOCFUsage.setStrFeatureCd(CommonUtil.convertObjectToString(tmpArr[1]));
					objRgnLvlOCFUsage.setStrOCFFrmCd(CommonUtil.convertObjectToString(tmpArr[2]));
					objRgnLvlOCFUsage.setStrOrdrTkBsMnth(CommonUtil.convertObjectToString(tmpArr[4]));
					objRgnLvlOCFUsage.setStrPrdMnth(CommonUtil.convertObjectToString(tmpArr[5]));				
					objRgnLvlOCFUsage.setStrOCFRgnCd(CommonUtil.convertObjectToString(tmpArr[6]));
					objRgnLvlOCFUsage.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tmpArr[7]));
					lstRgnLvlOCFUsage.add(objRgnLvlOCFUsage);
				}
				
			}
			catch(Exception e){
			LOG.info(e);
		
			}
			
		}
		
		return lstRgnLvlOCFUsage;
		
		
	}
	
	/**
	 * P0008.1a
	 * This method initializes the The Regional Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstCarSeries
	 * @param entityManager
	 */
	public void initRgnLvlOCFUsg(String porCd,String otbm,String updateOnlyFlag,String strCarSrs,EntityManager entityManager){
		
		if (updateOnlyFlag.equals(PDConstants.CONSTANT_ONE)){
		try{	
			Query query1 = entityManager.createNativeQuery(UPDATE_TRN_REGIONAL_MNTHLY_OCF_LMT_UPT_FLG.toString());
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_OTBM, otbm);
			query1.setParameter(PARAM_CARSRS, strCarSrs);
			query1.executeUpdate();
		}
		catch(Exception e){
					LOG.info(e);
					String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
					CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
					CommonUtil.stopBatch();
		}
			
		String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
		CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
			
		}		
		else{
			Query query2 = entityManager.createNativeQuery(UPDATE_TRN_REGIONAL_MNTHLY_OCF_LMT.toString());
			try{
				query2.setParameter(PARAM_PORCD, porCd);
				query2.setParameter(PARAM_OTBM, otbm);			
				query2.executeUpdate();
			}
			catch(Exception e){
				LOG.info(e);
				String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
				CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
				CommonUtil.stopBatch();
			}
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		}
		
	}
	
	/**
	 * P0008.1b
	 * This method updates & inserts into Regional Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param lstRgnLvlOCFUsage
	 * @param entityManager
	 */
	public void uptInsRgnLvlOCFUsg(String porCd,String otbm,List<RgnLvlOCFUsage> lstRgnLvlOCFUsage,EntityManager entityManager){		
		
		try{
			for (RgnLvlOCFUsage objRgnLvlOCFUsage:lstRgnLvlOCFUsage){
				Query query = entityManager.createNativeQuery(SELECT_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT.toString());
				query.setParameter(PARAM_PORCD, porCd);
				query.setParameter(PARAM_OTBM, otbm);
				query.setParameter(PARAM_PRDMNTH, objRgnLvlOCFUsage.getStrPrdMnth());
				query.setParameter(PARAM_CARSRS, objRgnLvlOCFUsage.getStrCrSrs());
				query.setParameter(PARAM_BYRGRPCD, objRgnLvlOCFUsage.getStrOCFByrGrpCd());
				query.setParameter(PARAM_OCFFRMCD, objRgnLvlOCFUsage.getStrOCFFrmCd());
				query.setParameter(PARAM_FEATURECD, objRgnLvlOCFUsage.getStrFeatureCd());
				query.setParameter(PARAM_REGIONCD, objRgnLvlOCFUsage.getStrOCFRgnCd());
				int result = query.getResultList().size();
				if (result > 0){			
					uptRgnLvlOCFUsg(porCd,otbm,objRgnLvlOCFUsage,entityManager);
					String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
					CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
				}
				else{
					insRgnLvlOCFUsg(porCd,otbm,objRgnLvlOCFUsage,entityManager);
					String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
					CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
				}
			
		}
		}
		catch(Exception e){
			LOG.info(e);
			
		}
		
	}
	
	/**
	 * This method updates into Regional Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param objRgnLvlOCFUsage
	 * @param entityManager
	 */
	private void uptRgnLvlOCFUsg(String porCd,String otbm,RgnLvlOCFUsage objRgnLvlOCFUsage,EntityManager entityManager){
		
		try{
			Query query = entityManager.createNativeQuery(UPDATE_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT.toString());
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_PRDMNTH, objRgnLvlOCFUsage.getStrPrdMnth());
			query.setParameter(PARAM_CARSRS, objRgnLvlOCFUsage.getStrCrSrs());
			query.setParameter(PARAM_BYRGRPCD, objRgnLvlOCFUsage.getStrOCFByrGrpCd());
			query.setParameter(PARAM_OCFFRMCD, objRgnLvlOCFUsage.getStrOCFFrmCd());
			query.setParameter(PARAM_FEATURECD, objRgnLvlOCFUsage.getStrFeatureCd());
			query.setParameter(PARAM_REGIONCD, objRgnLvlOCFUsage.getStrOCFRgnCd());
			query.setParameter(PARAM_REGOCFUSGQTY,objRgnLvlOCFUsage.getIntSumRgnOCFUsageQty());
			query.executeUpdate();
			
			
		}
		catch(Exception e){
			LOG.info(e);
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
			CommonUtil.stopBatch();
			
		}
	
	}
	
	/**
	 * This method inserts into Regional Level OCF Usage
	 * @param porCd
	 * @param otbm
	 * @param updateOnlyFlag
	 * @param objRgnLvlOCFUsage
	 * @param entityManager
	 */
	private void insRgnLvlOCFUsg(String porCd,String otbm,RgnLvlOCFUsage objRgnLvlOCFUsage,EntityManager entityManager){
		
		try{
			Query query = entityManager.createNativeQuery(INSERT_NOT_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT.toString());
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_PRDMNTH, objRgnLvlOCFUsage.getStrPrdMnth());
			query.setParameter(PARAM_CARSRS, objRgnLvlOCFUsage.getStrCrSrs());
			query.setParameter(PARAM_FEATURECD, objRgnLvlOCFUsage.getStrFeatureCd());
			query.setParameter(PARAM_OCFFRMCD, objRgnLvlOCFUsage.getStrOCFFrmCd());
			query.setParameter(PARAM_FEATURETYPECD, objRgnLvlOCFUsage.getStrFeatureTypCd());
			query.setParameter(PARAM_REGOCFUSGQTY,objRgnLvlOCFUsage.getIntSumRgnOCFUsageQty());
			query.setParameter(PARAM_BYRGRPCD, objRgnLvlOCFUsage.getStrOCFByrGrpCd());						
			query.setParameter(PARAM_REGIONCD, objRgnLvlOCFUsage.getStrOCFRgnCd());
			query.executeUpdate();
			
		}
		catch(Exception e){
			LOG.info(e);
			String[] strMsgParams = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
			CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
			CommonUtil.stopBatch();
			
		}
		
	}
	
	/**
	 * P0009
	 * This method updates the Spec Reference Time
	 * @param porCd
	 * @param lstTableDetails
	 * @param entityManager
	 */
	public void uptSpecRefTime(String porCd,List<TableDetails> lstTableDetails,Timestamp prsExeTime, EntityManager entityManager){
		
		Query query = entityManager.createNativeQuery(UPDATE_SPEC_REEXECUTE_STATUS.toString());
		query.setParameter(PARAM_PORCD, porCd);
		
		for (TableDetails objTableDetails : lstTableDetails){
			try{
				query.setParameter(PARAM_TBLNAME, objTableDetails.getStrTableName());
				query.setParameter(PARAM_REF_TIME, objTableDetails.getUpdatedTime());
				query.setParameter(PARAM_PROCESS_EXE_TIME,prsExeTime );
				query.executeUpdate();
			}
			catch(Exception e){
				LOG.info(e);
				String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.SPEC_REEXECUTE_STATUS};
				CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams);
				CommonUtil.stopBatch();
			}
		}
		String[] strMsgParams = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.SPEC_REEXECUTE_STATUS};
		CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
		
	}
	
	/**
	 * @param porCd
	 * @param updateOnlyFlag
	 * @param otbm
	 * @param entityManager
	 * @return List<TrnFeatureInfo>
	 */
	public List<TrnFeatureInfo> getMnthlyTrnFeatureDtls(String porCd,String updateOnlyFlag,String otbm,EntityManager entityManager){
		
		List<TrnFeatureInfo> lstTrnFeatureInfo = new ArrayList<TrnFeatureInfo>();
		Query query = null;
		
		String strQueryString = EXT_MNTHLY_TRN_MAIN_PART.toString();
		
		//CCF part
		String strCCFQueryString = strQueryString + EXT_MNTHLY_TRN_CCF_PART.toString();
		
		if (PDConstants.CONSTANT_ONE.equals(updateOnlyFlag)){
			strCCFQueryString +=  EXT_MNTHLY_TRN_UPT_FLG_PART.toString();
			strCCFQueryString +=  EXT_MNTHLY_TRN_CNDN_PART.toString();
			query = entityManager.createNativeQuery(strCCFQueryString);
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
			query.setParameter(PARAM_FEATURE_TBL, Constants.MST_OEI_FEAT);
		}
		else{
			//Redmine #3361
			strCCFQueryString +=  EXT_MNTHLY_TRN_CNDN_NO_UPT_FLG_PART.toString(); //Redmine #3361
			query = entityManager.createNativeQuery(strCCFQueryString);
			query.setParameter(PARAM_PORCD, porCd);
			query.setParameter(PARAM_OTBM, otbm);
		}
		
		List<Object[]> resultList = query.getResultList();
		for (Object[] tempArr : resultList){
			TrnFeatureInfo objTrnFeatureInfo = new TrnFeatureInfo();
			objTrnFeatureInfo.setStrOSEIId(CommonUtil.convertObjectToString(tempArr[0]));
			objTrnFeatureInfo.setStrByrId(CommonUtil.convertObjectToString(tempArr[1]));
			objTrnFeatureInfo.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[2]));
			objTrnFeatureInfo.setStrByrGrpCd(CommonUtil.convertObjectToString(tempArr[3]));
			objTrnFeatureInfo.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[4]));
			objTrnFeatureInfo.setStrOCFFrmCd(CommonUtil.convertObjectToString(tempArr[5]));
			objTrnFeatureInfo.setStrFeatTypeCd(CommonUtil.convertObjectToString(tempArr[6]));
			objTrnFeatureInfo.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[7]));
			objTrnFeatureInfo.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[8]));
			objTrnFeatureInfo.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[9]));
			objTrnFeatureInfo.setStrSusOrdrFlg(CommonUtil.convertObjectToString(tempArr[10]));
			objTrnFeatureInfo.setIntOrdQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[11])));
			objTrnFeatureInfo.setIntMSQty(Integer.parseInt(CommonUtil.convertObjectToString(tempArr[12])));
			lstTrnFeatureInfo.add(objTrnFeatureInfo);
		}
		
		Query query1 = null;
		//OCF Part
		
		String strOCFQueryString = strQueryString + EXT_MNTHLY_TRN_OCF_PART.toString();
		
		if (PDConstants.CONSTANT_ONE.equals(updateOnlyFlag)){
			strOCFQueryString +=  EXT_MNTHLY_TRN_UPT_FLG_PART.toString();
			strOCFQueryString +=  EXT_MNTHLY_TRN_CNDN_PART.toString();
			query1 = entityManager.createNativeQuery(strOCFQueryString);
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_OTBM, otbm);
			query1.setParameter(PARAM_FEATURE_TBL, Constants.MST_OEI_FEAT);
		}
		else{
			// Redmine # 3361
			strOCFQueryString +=  EXT_MNTHLY_TRN_CNDN_NO_UPT_FLG_PART.toString(); //Redmine #3361
			query1 = entityManager.createNativeQuery(strOCFQueryString);
			query1.setParameter(PARAM_PORCD, porCd);
			query1.setParameter(PARAM_OTBM, otbm);
		}
		
		List<Object[]> resultList1 = query1.getResultList();
		for (Object[] tempArr : resultList1){
			TrnFeatureInfo objTrnFeatureInfo = new TrnFeatureInfo();
			objTrnFeatureInfo.setStrOSEIId(CommonUtil.convertObjectToString(tempArr[0]));
			objTrnFeatureInfo.setStrByrId(CommonUtil.convertObjectToString(tempArr[1]));
			objTrnFeatureInfo.setStrCarSrs(CommonUtil.convertObjectToString(tempArr[2]));
			objTrnFeatureInfo.setStrByrGrpCd(CommonUtil.convertObjectToString(tempArr[3]));
			objTrnFeatureInfo.setStrFeatureCd(CommonUtil.convertObjectToString(tempArr[4]));
			objTrnFeatureInfo.setStrOCFFrmCd(CommonUtil.convertObjectToString(tempArr[5]));
			objTrnFeatureInfo.setStrFeatTypeCd(CommonUtil.convertObjectToString(tempArr[6]));
			objTrnFeatureInfo.setStrPrdMnth(CommonUtil.convertObjectToString(tempArr[7]));
			objTrnFeatureInfo.setStrOCFByrGrpCd(CommonUtil.convertObjectToString(tempArr[8]));
			objTrnFeatureInfo.setStrOCFRgnCd(CommonUtil.convertObjectToString(tempArr[9]));
			lstTrnFeatureInfo.add(objTrnFeatureInfo);
		}
		
		return lstTrnFeatureInfo;
	}
	
	/**
	 * @param porCd
	 * @param entityManager
	 * @return List<TableDetails>
	 */
	public List<TableDetails> getTableUpdtDtls(String porCd,EntityManager entityManager){
		
		List<TableDetails> lstTableDetails = new ArrayList<TableDetails>();
		Query query1 = entityManager.createNativeQuery(UPT_TIME_MST_OSEI_DTL.toString());
		query1.setParameter(PARAM_PORCD, porCd);
		TableDetails objTableDetails1 = new TableDetails();
		objTableDetails1.setStrTableName(PDConstants.MASTER_TABLE_NAME);
		try{
		objTableDetails1.setUpdatedTime(new Timestamp((CommonUtil.convertStringToDate(
				CommonUtil.convertObjectToString(query1.getSingleResult()))
				).getTime()));
		}
		catch(Exception e){
			LOG.info(e);
		}
		
		lstTableDetails.add(objTableDetails1);
		
		Query query2 = entityManager.createNativeQuery(UPT_TIME_MST_OSEI_FEAT.toString());
		query2.setParameter(PARAM_PORCD, porCd);
		TableDetails objTableDetails2 = new TableDetails();
		objTableDetails2.setStrTableName(Constants.MST_OEI_FEAT);
		try{
		objTableDetails2.setUpdatedTime(new Timestamp((CommonUtil.convertStringToDate(
				CommonUtil.convertObjectToString(query2.getSingleResult()))
				).getTime()));
		}
		catch(Exception e){
			LOG.info(e);
		}
		
		lstTableDetails.add(objTableDetails2);
		
		
		Query query3 = entityManager.createNativeQuery(UPT_TIME_MST_OEI_FEAT.toString());
		query2.setParameter(PARAM_PORCD, porCd);
		TableDetails objTableDetails3 = new TableDetails();
		objTableDetails3.setStrTableName(Constants.MST_OEI_FEAT);
		try{
		objTableDetails3.setUpdatedTime(new Timestamp((CommonUtil.convertStringToDate(
				CommonUtil.convertObjectToString(query3.getSingleResult()))
				).getTime()));
		}
		catch(Exception e){
			LOG.info(e);
		}
		
		lstTableDetails.add(objTableDetails3);
		
		return lstTableDetails;
		
	}
}
