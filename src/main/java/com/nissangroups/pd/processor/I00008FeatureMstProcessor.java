/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I00008
 * Module          :Spec Master			
 * Process Outline :Receive OCF Classification Master from Plant											
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z010356(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectOcfQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.SeqNoQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.UpdateStatusQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectFeatQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectMaxFC00;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectMaxFCNot00;
import static com.nissangroups.pd.util.I00008QueryConstants.InsertMSTFeatQuery;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CARSRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_SHRT_DESC;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.POR_CD;
import static com.nissangroups.pd.util.PDConstants.FEATURE_CODE_00;
import static com.nissangroups.pd.util.PDConstants.FEATURE_CODE_NOT00;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.FILE_ID_PARAM;
import static com.nissangroups.pd.util.PDConstants.SEQ_NO_PARAM;



import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.I000008_ID;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDConstants.RAW_TYPES;
import static com.nissangroups.pd.util.PDConstants.INTERFACE_FILE_ID;
import static com.nissangroups.pd.util.PDConstants.FILE_ID;
import static com.nissangroups.pd.util.PDConstants.VALUES;
import static com.nissangroups.pd.util.PDConstants.COMMA_IN_QUERY;
import static com.nissangroups.pd.util.PDConstants.END_BRACKET;

import java.sql.Timestamp;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.dao.I00008FeatureCodeKeys;
import com.nissangroups.pd.model.MstFeat;
import com.nissangroups.pd.model.MstFeatPK;
import com.nissangroups.pd.model.MstOcfClassftn;
import com.nissangroups.pd.model.MstOcfClassftnPK;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class I00008FeatureMstProcessor.
 *
 * @author z010356
 */
public class I00008FeatureMstProcessor implements ItemProcessor<MstOcfClassftn, MstFeat >{
	
	/** Variable step1. */
	private StepExecution step1;
	
	/** Variable entity manager. */
	@PersistenceContext(unitName=PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable job para. */
	private JobParameters jobPara;	
	
	/** Variable job exe. */
	private JobExecution jobExe;
	
	/** Variable feature cd temp. */
	long featureCdTemp= 0;
	
	/** Variable results. */
	List<Object[]> results;
	
	/** Variable feat cd objs. */
	I00008FeatureCodeKeys[] featCdObjs;
	
	/** Variable i. */
	int i=0;
	String interfaceID = I000008_ID;
	private static final Log LOG = LogFactory.getLog(I00008FeatureMstProcessor.class);
	
	/** Variable first time00 flag. */
	private boolean firstTime00Flag=true;
	
	/** Variable first time not00 flag. */
	private boolean firstTimeNot00Flag=true;
	
	/** Variable feature code. */
	Map<String,Long> featureCode =new HashMap<String,Long>();
	
	/** Variable feature code max val. */
	Map<String,Long> featureCodeMaxVal =new HashMap<String,Long>();
	
	/** Variable feature code region. */
	Map<String,String> featureCodeRegion = new HashMap<String,String>();
	
	
	

	/**
	 * Sets the feature code.
	 *
	 * @author z010356
	 * Generate the FeatureCode for the grouped data in OCF Classification MST table
	 * partial P0004
	 * @param stepExecution the new feature code
	 */
	
	@SuppressWarnings(UNCHECKED)
	@BeforeStep
	public void setFeatureCode(StepExecution stepExecution) {	
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		this.step1=stepExecution;
		jobExe = step1.getJobExecution();
		jobPara = jobExe.getJobParameters();
		Query seqNoQuery = entityManager.createNativeQuery(SeqNoQuery.toString());
		seqNoQuery.setParameter(FILE_ID_PARAM, jobPara.getString(INTERFACE_FILE_ID));
		String fileSeqNo = seqNoQuery.getSingleResult().toString();
		
		// Redmine issue #951
		Query query1 = entityManager.createNativeQuery(SelectOcfQuery.toString());
		query1.setParameter(PRMTR_PORCD, jobPara.getString(POR_CD));
		query1.setParameter(SEQ_NO_PARAM, fileSeqNo);
		results = query1.getResultList();
		
		try {
			featCdObjs =new I00008FeatureCodeKeys[results.size()];		
			for(Object[] featureCdObject : results) {
				featCdObjs[i] =new I00008FeatureCodeKeys();
				featCdObjs[i].setPorCode(featureCdObject[0].toString());
				featCdObjs[i].setCarSeries(featureCdObject[1].toString());
				featCdObjs[i].setShortDescription(featureCdObject[2].toString());
				featCdObjs[i].setOcfFrameCode(featureCdObject[3].toString());
				featCdObjs[i].setVsMonitor('N');
				@SuppressWarnings(RAW_TYPES)
				List result = null;
				
				Query query = entityManager.createNativeQuery(SelectFeatQuery.toString());
				query.setParameter(PRMTR_PORCD, featureCdObject[0]);
				query.setParameter(PRMTR_CARSRS, featureCdObject[1] );
				query.setParameter(PRMTR_FEAT_SHRT_DESC, featureCdObject[2]);
				query.setParameter(PRMTR_OCF_FRAME_CD, featureCdObject[3]);
				result = query.getResultList();
				if(result !=null && !result.isEmpty())
				{
					featureCdTemp=Long.parseLong(result.get(0).toString().trim());
				}
				else
				{
					
				/**	Check for Existing POR & CAR SERIES Combination and set the Feature_Cd */
					
					featureCdTemp = getFeatureCode(featCdObjs[i]);
				}
				featCdObjs[i].setFeatureCode(featureCdTemp);
				featureCode.put(featCdObjs[i].getPorCode()+featCdObjs[i].getCarSeries()+featCdObjs[i].getShortDescription()+featCdObjs[i].getOcfFrameCode(), featureCdTemp);
				i=i+1;
			}
		} catch (Exception e) {
            
			
			LOG.error(interfaceID,e);
		}		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * This method gets the feature code either already generated or generates new one.
	 *
	 * @param objI00008FeatureCodeKeys the obj i00008 feature code keys
	 * @return the feature code
	 */
	private long getFeatureCode(I00008FeatureCodeKeys objI00008FeatureCodeKeys){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		long featureCodeValue=0;
		
		if (featureCode.get(objI00008FeatureCodeKeys.getPorCode()+objI00008FeatureCodeKeys.getCarSeries()+objI00008FeatureCodeKeys.getShortDescription()+objI00008FeatureCodeKeys.getOcfFrameCode()) != null){
			featureCodeValue=featureCode.get(objI00008FeatureCodeKeys.getPorCode()+objI00008FeatureCodeKeys.getCarSeries()+objI00008FeatureCodeKeys.getShortDescription()+objI00008FeatureCodeKeys.getOcfFrameCode()).longValue();
		}
		else{
			featureCodeValue=newFeatureCdGeneration(featCdObjs[i].getOcfFrameCode());
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return featureCodeValue;
		
	}
	
	/**
	 * This method generates the new feature code.
	 *
	 * @param frameCode the frame code
	 * @return the long
	 */
	
	private long newFeatureCdGeneration(String frameCode){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		long newFeatureCode = 0;
		if (frameCode != null && FEATURE_CODE_00.equals(frameCode) ){
			if (firstTime00Flag)
			{
			     Query querySelect00 = entityManager.createNativeQuery(SelectMaxFC00.toString());
		
			     Object resultsMax00=querySelect00.getSingleResult();	
			     if(resultsMax00!=null)
			     {
				    newFeatureCode = Long.parseLong(resultsMax00.toString().trim())+1;
				    
				
		        }else{
		    	    newFeatureCode = 1;
		    	    featureCodeMaxVal.put(FEATURE_CODE_00, 1L);
		         }
			     firstTime00Flag=false;
			     
		    }else{
		    	
		    	 newFeatureCode = featureCodeMaxVal.get(FEATURE_CODE_00)+1;
		    }
			featureCodeMaxVal.put(FEATURE_CODE_00, newFeatureCode);
		    
	    }
		else if (frameCode != null){
			if (firstTimeNot00Flag){
			   
		       Query querySelectNot00 = entityManager.createNativeQuery(SelectMaxFCNot00.toString());

		       Object resultsMaxNot00=querySelectNot00.getSingleResult();	
		       if(resultsMaxNot00!=null)
		       {
			      
			      newFeatureCode = Long.parseLong(resultsMaxNot00.toString().trim())+1;
	          }else{
	   	          
	   	          newFeatureCode = 100;
	   	          
	          }
		       
		       firstTimeNot00Flag = false;
	       }else{
	    	    newFeatureCode = featureCodeMaxVal.get(FEATURE_CODE_NOT00)+1;
	        	
	        }
			featureCodeMaxVal.put(FEATURE_CODE_NOT00, newFeatureCode);
			
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
			return newFeatureCode;
		
	    }
	
		

		
	/**
	 * Update status.
	 *
	 * @author z010356
	 * Mark the Unprocessed data as Processed
	 * P0005
	 */
//	@AfterWrite
	public void updateStatus()
	{
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		jobExe = step1.getJobExecution();
		jobPara = jobExe.getJobParameters();
		Query query;
		try
		{

			query=entityManager.createNativeQuery(UpdateStatusQuery.toString());
			query.setParameter(FILE_ID, jobPara.getString(INTERFACE_FILE_ID));
			query.executeUpdate();
		}
		catch (Exception e)
		{
			query =null;
			LOG.error(interfaceID,e);
		}		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}	
	
	/**
	 * This method converts the feature code to 5 digits.
	 *
	 * @param inputFeatureCode the input feature code
	 * @return the string
	 */
	private String featureCodeTo5Digits(String inputFeatureCode){		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		StringBuilder featureCodeInput = new StringBuilder(inputFeatureCode);
		int length = featureCodeInput.length();
		StringBuilder prefixZeroStr = new StringBuilder();
		for (int prefixZeroLength=0;prefixZeroLength< 5-length;prefixZeroLength++){
			prefixZeroStr.append('0');
		}
		prefixZeroStr.append(featureCodeInput.toString());
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return prefixZeroStr.toString();
		
	}
	
	/**
	 * Process.
	 *
	 * @author z010356
	 * @param mstOcfClassificationData the mst ocf classification data
	 * @return the mst feat
	 * @throws Exception the exception
	 */
	@Override
	public MstFeat process(MstOcfClassftn mstOcfClassificationData) throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		MstFeat mstFeatObj=new MstFeat();		
		MstOcfClassftnPK mstOcfClassftnPKobj = mstOcfClassificationData.getId();		
		MstFeatPK mstFeatPKobj=new MstFeatPK();
		
		mstFeatPKobj.setPorCd(mstOcfClassftnPKobj.getPorCd());
		mstFeatPKobj.setCarSrs(mstOcfClassftnPKobj.getCarSrs());
		mstFeatPKobj.setFeatTypeCd(mstOcfClassificationData.getFeatTypeCd());
		mstFeatPKobj.setOcfBuyerGrpCd(mstOcfClassftnPKobj.getOcfBuyerGrpCd());
		mstFeatPKobj.setOcfRegionCd(mstOcfClassificationData.getOcfRegionCd());	
		mstFeatObj.setFeatAblshDate(mstOcfClassificationData.getOcfAblshDate());
		mstFeatObj.setFeatAdptDate(mstOcfClassificationData.getOcfAdptDate());
		mstFeatObj.setFeatLngDesc(mstOcfClassificationData.getLngDesc());
		mstFeatObj.setFeatShrtDesc(mstOcfClassificationData.getId().getShrtDesc());
		mstFeatObj.setOcfFrmeCd(mstOcfClassificationData.getOcfFrmeCd());
		mstFeatPKobj.setFeatCd(featureCodeTo5Digits(featureCode.get(mstOcfClassftnPKobj.getPorCd()+mstOcfClassftnPKobj.getCarSrs()+mstOcfClassificationData.getId().getShrtDesc()+mstOcfClassificationData.getOcfFrmeCd()).toString()));
		java.util.Date date= new java.util.Date();
		mstFeatObj.setCrtdDt(new Timestamp(date.getTime()));
		mstFeatObj.setCrtdBy(interfaceID);
		mstFeatObj.setUpdtdDt(new Timestamp(date.getTime()));
		mstFeatObj.setUpdtdBy(interfaceID);
		mstFeatObj.setId(mstFeatPKobj);
		if (!featureCodeRegion.isEmpty() && (featureCodeRegion.get(mstFeatObj.getId().getPorCd()+mstFeatObj.getId().getCarSrs()+mstFeatObj.getFeatShrtDesc()+mstFeatObj.getOcfFrmeCd()+mstFeatObj.getId().getOcfRegionCd()+mstFeatObj.getId().getOcfBuyerGrpCd()) == null))
				{					
		
		         String insertQuery =
		        		 InsertMSTFeatQuery
				 + VALUES + mstFeatObj.getId().getPorCd() + COMMA_IN_QUERY + mstFeatObj.getId().getCarSrs() + COMMA_IN_QUERY+ mstFeatObj.getId().getFeatCd() + COMMA_IN_QUERY 
				 + mstFeatObj.getOcfFrmeCd() + COMMA_IN_QUERY + mstFeatObj.getId().getFeatTypeCd() + COMMA_IN_QUERY + mstFeatObj.getMstFeatGrp() + COMMA_IN_QUERY 
				 + mstFeatObj.getFeatShrtDesc() + COMMA_IN_QUERY + mstFeatObj.getFeatLngDesc() + COMMA_IN_QUERY + mstFeatObj.getCrtdBy() + COMMA_IN_QUERY 
				 + mstFeatObj.getId().getOcfRegionCd() + COMMA_IN_QUERY + mstFeatObj.getId().getOcfBuyerGrpCd() + COMMA_IN_QUERY + mstFeatObj.getFeatAdptDate() + COMMA_IN_QUERY 
				 + mstFeatObj.getFeatAblshDate() + END_BRACKET;			 
				 Query query = entityManager.createNativeQuery(insertQuery);
				 query.executeUpdate();
				 featureCodeRegion.put(mstFeatObj.getId().getPorCd()+mstFeatObj.getId().getCarSrs()+mstFeatObj.getFeatShrtDesc()+mstFeatObj.getOcfFrmeCd()+mstFeatObj.getId().getOcfRegionCd()+mstFeatObj.getId().getOcfBuyerGrpCd(),mstFeatObj.getId().getFeatCd());
			   }
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return mstFeatObj;
	}
	
	
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

}
