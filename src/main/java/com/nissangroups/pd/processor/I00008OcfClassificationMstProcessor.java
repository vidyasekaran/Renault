/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I00008
 * Module          :Spec Master
 * Process Outline :extracts data from CMN_INTERFACE_DATA and writes into MST_OCF_CLASSFTN
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z010356(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;


import static com.nissangroups.pd.util.I00008QueryConstants.CheckQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.DeleteQuery;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectAbolishDateFromPrmMst;
import static com.nissangroups.pd.util.I00008QueryConstants.SelectAdoptedDateFromPrmMst;
import static com.nissangroups.pd.util.I00008QueryConstants.getDistinctOrdrTkBsMnth;
import static com.nissangroups.pd.util.PDConstants.CF_CONSTANT_C;
import static com.nissangroups.pd.util.PDConstants.CF_CONSTANT_M;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_N;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EMPTY_STRING;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.INTERFACE8;
import static com.nissangroups.pd.util.PDConstants.MWCCF;
import static com.nissangroups.pd.util.PDConstants.MWOCF;
import static com.nissangroups.pd.util.PDConstants.ORDER_TAKE_BASE_MONTH_PARAM;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.POR_CD;
import static com.nissangroups.pd.util.PDConstants.POR_PARAM;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CARSRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_SHRT_DESC;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_TYPE_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFBYRGRPCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFRGNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.SINGLE_SPACE;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDConstants.WCCF;
import static com.nissangroups.pd.util.PDConstants.WEEK1;
import static com.nissangroups.pd.util.PDConstants.WEEK_SUFFIX;
import static com.nissangroups.pd.util.PDConstants.WOCF;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstOcfClassftn;
import com.nissangroups.pd.model.MstOcfClassftnPK;
import com.nissangroups.pd.util.PDConstants;


/**
 * ;.
 *
 * @author z010356
 * OcfClassificationMstProcessor
 * This class reads from CMN_INTERFACE_DATA and writes into MST_OCF_CLASSFTN
 */
public class I00008OcfClassificationMstProcessor implements ItemProcessor<CmnInterfaceData, MstOcfClassftn> {
	
	/** Variable step exe. */
	private StepExecution stepExe;
    
    /** Variable exit flag. */
    boolean  exitFlag; 
    boolean  exitFlag1;
	
	/** Variable entity manager. */
	@PersistenceContext(name=PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable job para. */
	private JobParameters jobPara;	
	
	/** Variable job exe. */
	private JobExecution jobExe;	
	
	private static boolean firsttime = true;
	
	private String porCd;
	
	private String ifFileID;
	
	private List<String> ordrTkBsMnthList  = new ArrayList<String>();
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I00008OcfClassificationMstProcessor.class);
	
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		jobExe = stepExecution.getJobExecution();
		jobPara = jobExe.getJobParameters();
		porCd = jobPara.getString(POR_CD) ;
		ifFileID =jobPara.getString("INTERFACE_FILE_ID"); 
    	
		
		
		LOG.info(stepExecution.getStepName());
		ordrTkBsMnthList = getDistinctOrdrTkBsMnth(porCd,ifFileID);
		
			
		
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	
	
	/**
	 * Process.
	 *
	 * @author z010356
	 *  This method processes the records read from COMMON_INTERFACE_DATA
	 *  P0002-Deletion of data from MST_OCF_CLASSFTN 
	 *  P0003-Insertion of data into  MST_OCF_CLASSFTN
	 * @param commonInterfaceData the common interface data
	 * @return MstOcfClassftn
	 * @throws Exception the exception
	 */
	@Override
	public MstOcfClassftn process(CmnInterfaceData commonInterfaceData) throws Exception {		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		exitFlag=false;
		exitFlag1=false;
	    if(firsttime){
			if(ordrTkBsMnthList!=null && !(ordrTkBsMnthList.isEmpty())){
				for(String ordrTkBsMnth : ordrTkBsMnthList){
					orderTakeBaseMonthDelete(ordrTkBsMnth,porCd);
					firsttime = false;
				}
			}
	    }
		
		/** Build MstOcfClassftn object */
		MstOcfClassftn ocfObj=new MstOcfClassftn();
		MstOcfClassftnPK id=new MstOcfClassftnPK();
		id.setPorCd(jobPara.getString(POR_CD));			
		id.setCarSrs(commonInterfaceData.getCol1());			
		id.setShrtDesc(commonInterfaceData.getCol3());	
		id.setOcfBuyerGrpCd(commonInterfaceData.getCol4());		
		id.setOcfPrityCd(commonInterfaceData.getCol6());		
		id.setOcfFrmeSrtCd(commonInterfaceData.getCol21());		
		id.setOrdrTakeBaseMnth(commonInterfaceData.getCol24());
		ocfObj.setId(id);	
		ocfObj.setOcfFrmeCd(commonInterfaceData.getCol2());
		id.setOcfModelGrp(commonInterfaceData.getCol5());	
			
		/**Abolish Date Setting */
		
		setAbolishAdoptedDate(commonInterfaceData,ocfObj);
		
		
		
	    /**Feature Type Code Generation */
		
		setFeatTypeCodeMain(commonInterfaceData,ocfObj);
		
		
		
	   /**Prefix Setting */
		ocfObj.setPrfxYes(commonInterfaceData.getCol8().substring(0,7));
		ocfObj.setPrfxNo(commonInterfaceData.getCol9().substring(0,7));
	
		
		/**Suffix Setting */
		ocfObj.setSffxYes(commonInterfaceData.getCol10().substring(0,8));
		ocfObj.setSffxNo(commonInterfaceData.getCol11().substring(0,8));
		
		
		ocfObj.setOcfDelFlag(CONSTANT_N);
	    
		
		/**Color Code setting */
		setClrCdCndtn(commonInterfaceData,ocfObj);
		
		
	    /**Optional Spec Code Condition setting */
		setOptnSpecCdCndtn(commonInterfaceData,ocfObj);	
		 
		
		/**Spec Destination Code Setting */
		setSpecDestnCdCndtn(commonInterfaceData,ocfObj);
		
				
	    /**OCF Region Code Setting */
		if (commonInterfaceData.getCol22() != null){
		     ocfObj.setOcfRegionCd(commonInterfaceData.getCol22().substring(0, 1));	
		}
		else{
			ocfObj.setOcfRegionCd(EMPTY_STRING);
		}
		
		
	    /**Long Description Setting */
		if(commonInterfaceData.getCol23()!=null){
			ocfObj.setLngDesc(commonInterfaceData.getCol23()); 
			}
		else{
			String[] tempArgs = new String[6];
			tempArgs[0]=commonInterfaceData.getCol1();
			tempArgs[1]=commonInterfaceData.getCol4();
			tempArgs[2]=ocfObj.getOcfRegionCd();
			tempArgs[3]=ocfObj.getFeatTypeCd();
			tempArgs[4]=commonInterfaceData.getCol2();
			tempArgs[5]=commonInterfaceData.getCol3();
			List<String> results = longDesSetting(tempArgs);
			if(results.isEmpty())
				ocfObj.setLngDesc(commonInterfaceData.getCol3());
			else
				ocfObj.setLngDesc(results.get(0));
		}
		
		
		
	   /**Created and Updated By Setting */
		ocfObj.setCrtdBy(INTERFACE8);
		ocfObj.setUpdtdBy(INTERFACE8);
	   
		
	   /**Monthly OCF & VS Monitor FLAG Setting */
		ocfObj.setVsMntrFlag(CONSTANT_N);
		ocfObj.setMnlOcfFlag(CONSTANT_N);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		if (exitFlag || exitFlag1){
			throw new PdApplicationException();			
		}
		else{
			return ocfObj;
		}
		
		
	}
    
	
	/**
	 * This method sets the color code by adding spaces after every 5th Character.
	 *
	 * @param commonInterfaceData the common interface data
	 * @param ocfObj the ocf obj
	 */
	
    private void setClrCdCndtn(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);//20
    	ocfObj.setClrCdCndtn(insertSpaceAfterIthPosition(commonInterfaceData.getCol12(),5));
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * This method sets the optional spec code by adding space after every 6th Character.	
     *
     * @param commonInterfaceData the common interface data
     * @param ocfObj the ocf obj
     */
    private void setOptnSpecCdCndtn(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);//24
    	ocfObj.setOptnSpecCdCndtn(insertSpaceAfterIthPosition(commonInterfaceData.getCol13(),6));
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * This method sets the spec destination code by adding space after every 4th character.
     *
     * @param commonInterfaceData the common interface data
     * @param ocfObj the ocf obj
     */
    
    private void setSpecDestnCdCndtn(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);//15
    	ocfObj.setSpecDestnCdCndtn(insertSpaceAfterIthPosition(commonInterfaceData.getCol14(),4));
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    	
    }
    
    /**
     * This method inserts spaces in the input string after every ith position given as input.
     *
     * @param inputString the input string
     * @param position the position
     * @return the string
     */
    
    private String insertSpaceAfterIthPosition(String inputString,int position){
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	if ( inputString != null){
    		StringBuilder inputStringTemp = new StringBuilder(inputString);
    		for (int startIndex=position-1;startIndex <= inputStringTemp.length(); startIndex=startIndex+position){
    			inputStringTemp.insert(startIndex, SINGLE_SPACE);
    		}  
    		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    		return inputStringTemp.toString();
    	}
    	else{
    		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    		return EMPTY_STRING;
    	}
    	
    }
    
    /**
     * P0002
     * This method deletes the records with same order take base month.
     *
     * @param orderTakeBaseMonthDeleteTemp the order take base month delete temp
     */
	
	private void orderTakeBaseMonthDelete(String orderTakeBaseMonthDeleteTemp,String porCd){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
    	Query query=entityManager.createNativeQuery(DeleteQuery.toString());
		query.setParameter(POR_PARAM,porCd);
		query.setParameter(ORDER_TAKE_BASE_MONTH_PARAM, orderTakeBaseMonthDeleteTemp );
		query.executeUpdate();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
	private List<String> getDistinctOrdrTkBsMnth(String porCd,String ifFileId){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
    	Query query=entityManager.createNativeQuery(getDistinctOrdrTkBsMnth.toString());
		query.setParameter(PDConstants.IF_FILE_ID,ifFileId);
		
		List<String> ordrTkBsMnthList = query.getResultList();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ordrTkBsMnthList;
		
		
	}
	
	
	/**
	 * This method sets the long description.
	 *
	 * @param queryParameters the query parameters
	 * @return the list
	 */
	
	@SuppressWarnings(UNCHECKED)
	private List<String> longDesSetting(String[] queryParameters){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		Query querySelectLngDes=entityManager.createNativeQuery(CheckQuery.toString());
		querySelectLngDes.setParameter(PRMTR_PORCD, jobPara.getString(POR_CD));
		querySelectLngDes.setParameter(PRMTR_CARSRS, queryParameters[0]);
		querySelectLngDes.setParameter(PRMTR_OCFBYRGRPCD, queryParameters[1]);
		querySelectLngDes.setParameter(PRMTR_OCFRGNCD, queryParameters[2]);
		querySelectLngDes.setParameter(PRMTR_FEAT_TYPE_CD, queryParameters[3]);
		querySelectLngDes.setParameter(PRMTR_OCF_FRAME_CD, queryParameters[4]);
		querySelectLngDes.setParameter(PRMTR_FEAT_SHRT_DESC, queryParameters[5]);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return querySelectLngDes.getResultList();
		
	}
	
	/**
	 * This method checks whether the data is blank.
	 *
	 * @param year the year
	 * @param month the month
	 * @return true, if successful
	 */
	
	private boolean checkDateBlank(String year,String month){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);		
		return year == null || month == null ;
		
		    
	}
	
	/**
	 * This method gets the default date.
	 *
	 * @param inputQuery the input query
	 * @return the default date
	 */
	
	@SuppressWarnings(UNCHECKED)
	private String getDefaultDate(String inputQuery){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		Query queryDefaultDate = entityManager.createNativeQuery(inputQuery);
		List<String> results = queryDefaultDate.getResultList();
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return results.get(0);	
	}
	
	/**
	 * This method converts the date to ocf format.
	 *
	 * @param year the year
	 * @param month the month
	 * @param week the week
	 * @return the string
	 */
	private String convertDatetoOcfFormat(String year,String month,String week){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		StringBuilder ocfDateFormat = new StringBuilder();
		if (!checkDateBlank(year,month)){
			ocfDateFormat.append(year).append(month);
		}
		if (week == null){
			ocfDateFormat.append(WEEK1);
		}
		else
		{
			// Redmine issue # 1059
			ocfDateFormat.append(week+WEEK_SUFFIX);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ocfDateFormat.toString();	
		
	}
	
	/**
	 * This method sets the Adopted & Abolish date.
	 *
	 * @param commonInterfaceData the common interface data
	 * @param ocfObj the ocf obj
	 */
	private void setAbolishAdoptedDate(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		if (checkDateBlank(commonInterfaceData.getCol18(),commonInterfaceData.getCol19())){
			ocfObj.setOcfAblshDate(getDefaultDate(SelectAbolishDateFromPrmMst.toString()));
		}
		else{
			ocfObj.setOcfAblshDate(convertDatetoOcfFormat(commonInterfaceData.getCol18(),commonInterfaceData.getCol19(),commonInterfaceData.getCol20()));
		}
		
		/** Adopted Date Setting */
		if (checkDateBlank(commonInterfaceData.getCol15(),commonInterfaceData.getCol16())){
			ocfObj.setOcfAdptDate(getDefaultDate(SelectAdoptedDateFromPrmMst.toString()));
		}
		else{
			ocfObj.setOcfAdptDate(convertDatetoOcfFormat(commonInterfaceData.getCol15(),commonInterfaceData.getCol16(),commonInterfaceData.getCol17()));
		}
		
		if (firstDateGreaterThanSecond(ocfObj.getOcfAdptDate(),ocfObj.getOcfAblshDate())){
			exitFlag1 = true;
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * This is the main method for setting feature code. It uses 2 methods setFeatTypeCode2 & setFeatTypeCode3
	 *
	 * @param commonInterfaceData the common interface data
	 * @param ocfObj the ocf obj
	 */
	
	
	private void setFeatTypeCodeMain(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		// Redmine issue # 1044
		if ((commonInterfaceData.getCol12() != null) && (! (EMPTY_STRING.equals(commonInterfaceData.getCol12().trim())))){
			exitFlag = setFeatTypeCodeColorCodeNotNull(commonInterfaceData,ocfObj);
		}
		else{
			
			exitFlag = setFeatTypeCodeColorCodeNull(commonInterfaceData,ocfObj);
			
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
	/**
	 * This method is used in WCCF & MWCCF setting.
	 *
	 * @param commonInterfaceData the common interface data
	 * @param ocfObj the ocf obj
	 * @return true, if successful
	 */
	
	private boolean setFeatTypeCodeColorCodeNotNull(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		// Redmine issue # 1044
		boolean flag = ( (commonInterfaceData.getCol7() != null) && ((CF_CONSTANT_M.equals(commonInterfaceData.getCol7().trim())) || (CF_CONSTANT_C.equals(commonInterfaceData.getCol7().trim()))));
		//Redmine issue # 1044
		if ((commonInterfaceData.getCol7() == null)|| ((commonInterfaceData.getCol7() != null) && (commonInterfaceData.getCol7().trim().equals(EMPTY_STRING)) )){
			    ocfObj.setFeatTypeCd( WCCF);
			    LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
			    return false;
		}
		else{
			if (flag){
				ocfObj.setFeatTypeCd(MWCCF);
				LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
				return false;
			}
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return true;
		
	}
	
	/**
	 * This method is used in WOCF & MWOCF.
	 *
	 * @param commonInterfaceData the common interface data
	 * @param ocfObj the ocf obj
	 * @return true, if successful
	 */
	private boolean setFeatTypeCodeColorCodeNull(CmnInterfaceData commonInterfaceData,MstOcfClassftn ocfObj){
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		// Redmine issue # 1044
		boolean flag = ( (commonInterfaceData.getCol7() != null) && ((CF_CONSTANT_M.equals(commonInterfaceData.getCol7().trim())) || (CF_CONSTANT_C.equals(commonInterfaceData.getCol7().trim()))));
		// Redmine issue # 1044
		if ((commonInterfaceData.getCol7() == null) || ((commonInterfaceData.getCol7() != null) && (commonInterfaceData.getCol7().trim().equals(EMPTY_STRING)) )){
			ocfObj.setFeatTypeCd(WOCF);	
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
			return false;
	    }
	   else{
		if (flag){
			ocfObj.setFeatTypeCd(MWOCF);
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
			return false;
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return true;
	}
		
	}
	
	/**
	 * This method is used for date comparison.
	 *
	 * @param featAdptDate1 the feat adpt date1
	 * @param featAdptDate2 the feat adpt date2
	 * @return true, if successful
	 */
	private boolean firstDateGreaterThanSecond(String featAdptDate1, String featAdptDate2) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		return featAdptDate1.compareTo(featAdptDate2)>0;
	}
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
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


