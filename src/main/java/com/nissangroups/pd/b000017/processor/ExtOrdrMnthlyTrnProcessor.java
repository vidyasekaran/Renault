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
package com.nissangroups.pd.b000017.processor;

import static com.nissangroups.pd.util.PDConstants.CONSTANT_V3;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V4;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00332;
import static com.nissangroups.pd.util.PDMessageConsants.M00163;
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00219;




import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000017.output.B000017Output;
import com.nissangroups.pd.b000017.output.TableDetails;
import com.nissangroups.pd.b000017.output.TrnFeatureInfo;
import com.nissangroups.pd.b000017.util.Constants;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmtPK;
import com.nissangroups.pd.model.TrnBuyerMnthlyOcfUsg;
import com.nissangroups.pd.model.TrnBuyerMnthlyOcfUsgPK;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmtPK;
import com.nissangroups.pd.repository.ByrMnthlyOcfUsgRepository;
import com.nissangroups.pd.repository.MnthlyOrdrTrnRepository;
import com.nissangroups.pd.repository.OCFCCFUsageRepository;
import com.nissangroups.pd.repository.ByrGrpMnthlyOcfLimitTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/** P00002 */

/**
 * @author z015399
 *
 */

public class ExtOrdrMnthlyTrnProcessor implements
ItemProcessor<B000017Output,B000017Output>{
	    
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtOrdrMnthlyTrnProcessor.class
			.getName());
	
	/** Job input parameters */
	   private String porCd;
	   private String updateOnlyFlag;
	   private String productionStageCd;
	   
	   /** Variable entity manager. */
		@PersistenceContext(name=PERSISTENCE_NAME)
		private EntityManager entityManager;
		
		@Autowired(required=false)
		private MnthlyOrdrTrnRepository mnthlyOrdrTrnRepositoryObj;
		
		@Autowired(required=false)
		private ByrMnthlyOcfUsgRepository byrMnthlyOcfUsgRepositoryObj;
		
		@Autowired(required=false)
		private ByrGrpMnthlyOcfLimitTrnRepository byrGrpMnthlyOcfLimitTrnRepositoryObj;
		
	   public String getPorCd() {
		return porCd;
	   }


		public void setPorCd(String porCd) {
		this.porCd = porCd;
		}


		public String getUpdateOnlyFlag() {
		return updateOnlyFlag;
		}


		public void setUpdateOnlyFlag(String updateOnlyFlag) {
		this.updateOnlyFlag = updateOnlyFlag;
		}


		public String getProductionStageCd() {
		return productionStageCd;
		}


		public void setProductionStageCd(String productionStageCd) {
		this.productionStageCd = productionStageCd;
		}

		/* (non-Javadoc)
		 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
		 */
		@Override
		public B000017Output process(B000017Output objB000017Output) throws Exception{
			
			LOG.info(DOLLAR + "Inside ExtOrdrMnthlyTrnProcessor" + DOLLAR);
			B000017Output thisProcessorOutput = null;
            String otbm = objB000017Output.getObjExtOrdrTkBsMnthOutput().getOtbm();
            
            OCFCCFUsageRepository objOCFCCFUsageRepository = new OCFCCFUsageRepository();
            
            List<TrnFeatureInfo> lstTrnFeatureInfo = objOCFCCFUsageRepository.getMnthlyTrnFeatureDtls(this.getPorCd(),this.getUpdateOnlyFlag(),otbm,entityManager);
            Map<String,Integer> mapSumOrdrQty = new HashMap<String,Integer>();
            int intSumQty =0;
            Map<String,String> mapFrmeCd = new HashMap<String,String>();
            
            Map<String,String> mapFeatureTypCd = new HashMap<String,String>();
            
            for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo){
            	if ( !(PDConstants.CONSTANT_ONE.equals(objTrnFeatureInfo.getStrSusOrdrFlg())) && (objTrnFeatureInfo.getIntOrdQty() != 0) 
            			&& (objTrnFeatureInfo.getIntMSQty() != 0)){
            		
            		mapFrmeCd.put(objTrnFeatureInfo.getStrOSEIId()+objTrnFeatureInfo.getStrPrdMnth(), objTrnFeatureInfo.getStrOCFFrmCd());
            		if (!mapFrmeCd.containsValue(PDConstants.FEATURE_CODE_00)){
                    	String[] strMsgParams = {Constants.BATCH_ID,Constants.MST_OEI_FEAT,objTrnFeatureInfo.getStrOSEIId()};
            			CommonUtil.logMessage(M00332, CONSTANT_V3 , strMsgParams);
            			CommonUtil.stopBatch();            	
                    }
            		
            	}
            	
            	if (mapFeatureTypCd.get(objTrnFeatureInfo.getStrByrGrpCd()+objTrnFeatureInfo.getStrFeatureCd()) == null){
            		
            		mapFeatureTypCd.put(objTrnFeatureInfo.getStrByrGrpCd()+objTrnFeatureInfo.getStrFeatureCd(), objTrnFeatureInfo.getStrFeatTypeCd());
            	}
            	else{
            		
            		if (mapFeatureTypCd.get(objTrnFeatureInfo.getStrByrGrpCd()+objTrnFeatureInfo.getStrFeatureCd()).
            				compareTo(objTrnFeatureInfo.getStrFeatTypeCd()) < 0 ){
            			
            			objTrnFeatureInfo.setStrFeatTypeCd(mapFeatureTypCd.get(objTrnFeatureInfo.getStrByrGrpCd()+objTrnFeatureInfo.getStrFeatureCd()));
            		}
            		else{
            			mapFeatureTypCd.put(objTrnFeatureInfo.getStrByrGrpCd()+objTrnFeatureInfo.getStrFeatureCd(),objTrnFeatureInfo.getStrFeatTypeCd());
            		}
            	}
            	if ( mapSumOrdrQty.get(this.getPorCd()+otbm+objTrnFeatureInfo.getStrPrdMnth()
            			+this.getProductionStageCd()+ objTrnFeatureInfo.getStrOSEIId()) == null){
            	 intSumQty = mnthlyOrdrTrnRepositoryObj.fetchSumOrdrQty(objTrnFeatureInfo.getStrOSEIId(), this.getPorCd(),otbm,objTrnFeatureInfo.getStrPrdMnth());
            	 mapSumOrdrQty.put(this.getPorCd()+otbm+objTrnFeatureInfo.getStrPrdMnth()
             			+this.getProductionStageCd()+ objTrnFeatureInfo.getStrOSEIId(), 
             			intSumQty);
            	}           	
            			
            }
         
            //Process 4.1
            
            for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo){            	
            	objOCFCCFUsageRepository.delByrMnthlyOCFUsg(this.getPorCd(), this.getUpdateOnlyFlag(), 
            			otbm, objTrnFeatureInfo.getStrOSEIId(), entityManager);
            	 
            }
            
            String[] strMsgParams = {Constants.BATCH_ID,Constants.DELETED,Constants.TRN_BUYER_MNTHLY_OCF_USG};
			CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams);
           //Process 4.2
            
            for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo){
            	TrnBuyerMnthlyOcfUsg objTrnBuyerMnthlyOcfUsg = new TrnBuyerMnthlyOcfUsg();          
            	TrnBuyerMnthlyOcfUsgPK objTrnBuyerMnthlyOcfUsgPK = new TrnBuyerMnthlyOcfUsgPK();
            	
            	objTrnBuyerMnthlyOcfUsgPK.setPorCd(this.getPorCd());
            	objTrnBuyerMnthlyOcfUsgPK.setOrdrTakeBaseMnth(otbm);
            	objTrnBuyerMnthlyOcfUsgPK.setFeatCd(objTrnFeatureInfo.getStrFeatureCd());
            	objTrnBuyerMnthlyOcfUsgPK.setOseiId(objTrnFeatureInfo.getStrOSEIId());
            	objTrnBuyerMnthlyOcfUsgPK.setProdMnth(objTrnFeatureInfo.getStrPrdMnth());
            	
            	objTrnBuyerMnthlyOcfUsg.setId(objTrnBuyerMnthlyOcfUsgPK);
            	
            	objTrnBuyerMnthlyOcfUsg.setOcfFrmeCd(objTrnFeatureInfo.getStrOCFFrmCd());
            	objTrnBuyerMnthlyOcfUsg.setBuyerOcfUsgQty(new BigDecimal(
            			mapSumOrdrQty.get(this.getPorCd()+otbm+objTrnFeatureInfo.getStrPrdMnth()
                    			+this.getProductionStageCd()+ objTrnFeatureInfo.getStrOSEIId())));
            	objTrnBuyerMnthlyOcfUsg.setCarSrs(objTrnFeatureInfo.getStrCarSrs());
            	objTrnBuyerMnthlyOcfUsg.setBuyerGrpCd(objTrnFeatureInfo.getStrByrGrpCd());
            	objTrnBuyerMnthlyOcfUsg.setFeatTypeCd(objTrnFeatureInfo.getStrFeatTypeCd());
            	
            	try{
            		entityManager.merge(objTrnBuyerMnthlyOcfUsg);
            	}
            	catch(Exception e){
            		LOG.info(e);
            	   String[] strMsgParams1 = {Constants.BATCH_ID,PDConstants.INSERTION,Constants.TRN_BUYER_MNTHLY_OCF_USG};
         		   CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams1);
         		   CommonUtil.stopBatch();
            	}
            	
            }
            
           String[] strMsgParams2 = {Constants.BATCH_ID,PDConstants.INSERTED,Constants.TRN_BUYER_MNTHLY_OCF_USG};
 		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams2);
            //Process 5 - Summarize the Buyer Group
            
            Map<String,Integer> mapSumOCFUsgQty = new HashMap<String,Integer>();
            
            int intByrOCFUsg = 0;
            int count=0;
            
            for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo)
            {
            	
            	if ( mapSumOCFUsgQty.get(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
            			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd()) == null){
            		List<Object[]> resultList = byrMnthlyOcfUsgRepositoryObj.fetchSumFeatUsage(this.getPorCd(), 
            				objTrnFeatureInfo.getStrPrdMnth(), otbm,objTrnFeatureInfo.getStrCarSrs() , objTrnFeatureInfo.getStrByrGrpCd());
            			if (resultList.isEmpty()){
            				CommonUtil.stopBatch();
            			}
            			intByrOCFUsg = Integer.parseInt(CommonUtil.convertObjectToString(resultList.get(0)[0]));
            			 mapSumOCFUsgQty.put(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
                     			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd(),intByrOCFUsg);
            	
            	}	
            	count++;
            	
            }	
            
           //Process 6 - Insert\Update Buyer Group Level OCF Usage
           //Process 6.1 a 
           for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo) 
           {
        	   objOCFCCFUsageRepository.initByrGrpLvlOCFUsg(this.getPorCd(), otbm,this.getUpdateOnlyFlag(), objTrnFeatureInfo.getStrByrGrpCd(), entityManager);
           }
           String[] strMsgParams3 = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams3);
           
           //Process 6.2 a
           
           for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo){
        	   TrnBuyerGrpMnthlyOcfLmt objTrnBuyerGrpMnthlyOcfLmt = new TrnBuyerGrpMnthlyOcfLmt();
        	   TrnBuyerGrpMnthlyOcfLmtPK objTrnBuyerGrpMnthlyOcfLmtPK = new TrnBuyerGrpMnthlyOcfLmtPK();
        	   
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setPorCd(this.getPorCd());
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setOrdrTakeBaseMnth(otbm);
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setProdMnth(objTrnFeatureInfo.getStrPrdMnth());
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setCarSrs(objTrnFeatureInfo.getStrCarSrs());
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setBuyerGrpCd(objTrnFeatureInfo.getStrByrGrpCd());
        	   objTrnBuyerGrpMnthlyOcfLmtPK.setFeatCd(objTrnFeatureInfo.getStrFeatureCd());
        	   objTrnBuyerGrpMnthlyOcfLmt.setId(objTrnBuyerGrpMnthlyOcfLmtPK);
        	   objTrnBuyerGrpMnthlyOcfLmt.setFeatTypeCd(objTrnFeatureInfo.getStrFeatTypeCd());
        	   objTrnBuyerGrpMnthlyOcfLmt.setBuyerGrpOcfUsgQty(new BigDecimal(mapSumOCFUsgQty.get(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
                     			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd())));
        	   objTrnBuyerGrpMnthlyOcfLmt.setOcfFrmeCd(objTrnFeatureInfo.getStrOCFFrmCd());
        	   if (PDConstants.FEATURE_CODE_00.equals(objTrnFeatureInfo.getStrOCFFrmCd())){
        		   objTrnBuyerGrpMnthlyOcfLmt.setBuyerGrpOcfLmtQty(new BigDecimal(0));
        	   }
        	   try{
        		   entityManager.merge(objTrnBuyerGrpMnthlyOcfLmt);
        	   }
        	   catch(Exception e){
        		   LOG.info(e);
        		   String[] strMsgParams4 = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
       			   CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams4);
       			   CommonUtil.stopBatch();
        		   
        	   }
           }
           
           String[] strMsgParams5 = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_BUYER_GRP_MNTHLY_OCF_LMT};
		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams5);
           //Process 7 - Summarize at Region level
           
           Map<String,Integer> mapSumRgnOCFUsgQty = new HashMap<String,Integer>();
           int intRgnOCFUsg = 0;
           int count1=0;
           Map<String,String> mapFeatureCd = new HashMap<String,String>();
           for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo)
           {
           	
           	if ( mapSumRgnOCFUsgQty.get(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
           			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd()+
           			objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrOCFByrGrpCd()) == null){
           		List<Object[]> resultList = byrGrpMnthlyOcfLimitTrnRepositoryObj.fetchSumFeatUsage(this.getPorCd(), 
           				objTrnFeatureInfo.getStrPrdMnth(), otbm,objTrnFeatureInfo.getStrCarSrs() , objTrnFeatureInfo.getStrByrGrpCd(),objTrnFeatureInfo.getStrOCFRgnCd(),objTrnFeatureInfo.getStrOCFByrGrpCd());
           		if (resultList.isEmpty()){
    				CommonUtil.stopBatch();
    			}
           	    intRgnOCFUsg = Integer.parseInt(CommonUtil.convertObjectToString(resultList.get(0)[0]));
           		mapSumRgnOCFUsgQty.put(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
                    			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd()+
                       			objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrOCFByrGrpCd(),intRgnOCFUsg);
           	
           	}	
           	
           	if ( (mapFeatureCd.get(this.getPorCd()+objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrByrGrpCd()
           			+objTrnFeatureInfo.getStrPrdMnth()+objTrnFeatureInfo.getStrCarSrs()) != null) && (PDConstants.FEATURE_CODE_00.equals(objTrnFeatureInfo.getStrOCFFrmCd()))
           			
           			&& (!mapFeatureCd.get(this.getPorCd()+objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrByrGrpCd()
                   			+objTrnFeatureInfo.getStrPrdMnth()+objTrnFeatureInfo.getStrCarSrs()).equals(objTrnFeatureInfo.getStrFeatureCd()))){
           		
           		String[] strMsgParams8 = {Constants.BATCH_ID,objTrnFeatureInfo.getStrFeatureCd(),Constants.TRN_REGIONAL_MNTHLY_OCF_LMT,"["+
						   this.getPorCd()+"]"+"["+objTrnFeatureInfo.getStrCarSrs()+"]"+"["+objTrnFeatureInfo.getStrOCFRgnCd()
						   +"]"+"["+objTrnFeatureInfo.getStrOCFByrGrpCd()+"]"+"["+objTrnFeatureInfo.getStrPrdMnth()+"]"};
           		CommonUtil.logMessage(M00219, CONSTANT_V4 , strMsgParams8);
           		CommonUtil.stopBatch();
           		
           	}
           	
           	if ( (mapFeatureCd.get(this.getPorCd()+objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrByrGrpCd()
           			+objTrnFeatureInfo.getStrPrdMnth()+objTrnFeatureInfo.getStrCarSrs()) == null) && 
           			(PDConstants.FEATURE_CODE_00.equals(objTrnFeatureInfo.getStrOCFFrmCd()))) {
           		
           		mapFeatureCd.put(this.getPorCd()+objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrByrGrpCd()
           			+objTrnFeatureInfo.getStrPrdMnth()+objTrnFeatureInfo.getStrCarSrs(),objTrnFeatureInfo.getStrFeatureCd());       			
           		
           		
           	}
           
           	count1++;
           	
           }	
           
           //Process 8.1a - Insert\Update Regional Level OCF Usage
           
           for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo) {
        	   
        	   objOCFCCFUsageRepository.initRgnLvlOCFUsg(this.getPorCd(), otbm, this.getUpdateOnlyFlag(), objTrnFeatureInfo.getStrCarSrs(), entityManager);
        	   
           }
           
           String[] strMsgParams6 = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
   		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams6);
         //Process 8.1b
           
           for (TrnFeatureInfo objTrnFeatureInfo : lstTrnFeatureInfo){
        	   TrnRegionalMnthlyOcfLmt objTrnRegionalMnthlyOcfLmt = new TrnRegionalMnthlyOcfLmt();
        	   TrnRegionalMnthlyOcfLmtPK objTrnRegionalMnthlyOcfLmtPK = new TrnRegionalMnthlyOcfLmtPK();
        	   
        	   objTrnRegionalMnthlyOcfLmtPK.setPorCd(this.getPorCd());
        	   objTrnRegionalMnthlyOcfLmtPK.setOrdrTakeBaseMnth(otbm);
        	   objTrnRegionalMnthlyOcfLmtPK.setProdMnth(objTrnFeatureInfo.getStrPrdMnth());
        	   objTrnRegionalMnthlyOcfLmtPK.setCarSrs(objTrnFeatureInfo.getStrCarSrs());
        	   objTrnRegionalMnthlyOcfLmtPK.setFeatCd(objTrnFeatureInfo.getStrFeatureCd());
        	   objTrnRegionalMnthlyOcfLmtPK.setOcfBuyerGrpCd(objTrnFeatureInfo.getStrOCFByrGrpCd());
        	   objTrnRegionalMnthlyOcfLmtPK.setOcfRegionCd(objTrnFeatureInfo.getStrOCFRgnCd());
        	   
        	   objTrnRegionalMnthlyOcfLmt.setId(objTrnRegionalMnthlyOcfLmtPK);
        	   
        	   objTrnRegionalMnthlyOcfLmt.setFeatTypeCd(objTrnFeatureInfo.getStrFeatTypeCd());
        	   objTrnRegionalMnthlyOcfLmt.setOcfFrmeCd(objTrnFeatureInfo.getStrOCFFrmCd());
        	   objTrnRegionalMnthlyOcfLmt.setRegionalOcfUsgQty(new BigDecimal(mapSumRgnOCFUsgQty.get(this.getPorCd()+ otbm +objTrnFeatureInfo.getStrPrdMnth()
              			+ objTrnFeatureInfo.getStrCarSrs() + objTrnFeatureInfo.getStrByrGrpCd() + objTrnFeatureInfo.getStrFeatureCd()+
               			objTrnFeatureInfo.getStrOCFRgnCd()+objTrnFeatureInfo.getStrOCFByrGrpCd())));
        	   
        	   if (PDConstants.FEATURE_CODE_00.equals(objTrnFeatureInfo.getStrOCFFrmCd())){
        		   objTrnRegionalMnthlyOcfLmt.setRegionalOcfLmtQty(new BigDecimal(0));
        	   }
        	   try{
        		   entityManager.merge(objTrnRegionalMnthlyOcfLmt);
        	   }
        	   catch(Exception e){
        		   LOG.info(e);
        		String[] strMsgParams7 = {Constants.BATCH_ID,PDConstants.UPDATION,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
       			CommonUtil.logMessage(M00164, CONSTANT_V3 , strMsgParams7);
       			CommonUtil.stopBatch();
        		   
        	   }
        	   
           }
           
           String[] strMsgParams8 = {Constants.BATCH_ID,PDConstants.UPDATED,Constants.TRN_REGIONAL_MNTHLY_OCF_LMT};
		   CommonUtil.logMessage(M00163, CONSTANT_V3 , strMsgParams8);
           
           //Process 9
           
           List<TableDetails> lstTableDetails = null;
           
           lstTableDetails = objOCFCCFUsageRepository.getTableUpdtDtls(this.getPorCd(), entityManager);
           
           Timestamp prsExeTime = new Timestamp((new Date()).getTime());
           
           objOCFCCFUsageRepository.uptSpecRefTime(this.getPorCd(), lstTableDetails, prsExeTime, entityManager);
            
		    LOG.info(DOLLAR + "Outside ExtOrdrMnthlyTrnProcessor");
			
			
		    return thisProcessorOutput;
		}
}