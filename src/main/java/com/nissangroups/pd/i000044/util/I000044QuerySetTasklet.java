/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000044.bean.I000044InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000044QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000044 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class I000044QuerySetTasklet implements Tasklet, InitializingBean {

	private static final Log LOG = LogFactory
			.getLog(I000044QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManagerFactory;

	/** This object to store the list of values based on the input parameters */
	private List<I000044InputParam> paramList;
	
	/** Variable PorCd */
	private String porCdStr ="";
	
	/** Variable buyerGroupCd */
	private String buyerGrpCdStr ="";
	
	/** Variable PorCd */
	private String porCd = "";
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Create the custom query and store in ChunkContext
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 *            
	 * @return RepeatStatus object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters
				.getString(IFConstants.FILE_NAME);
		String jobInputs = jobParameters
				.getString(IFConstants.INPUT_PARAM);
		paramList = deformatInputs(jobInputs);

		// Generate sequence number and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, I000044Constants.S);

		// Updating custom query and sequence number in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(I000044Constants.PARAM_LIST, createCustomQuery(paramList,ifFileId));
		
		//Updating PorCd and BuyerGrpCd in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
		       .getExecutionContext().put(I000044Constants.PORCD_STR, porCdStr);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
		       .getExecutionContext().put(I000044Constants.BUYERGRP_CODE_STR, buyerGrpCdStr);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
	       		.getExecutionContext().put(I000044Constants.POR_CD, porCd);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
	/**
	 * P0002 Creates the custom query to extract the order take base period
	 * based on the input parameter
	 * 
	 * @param paramList 
	 * @param ifFileId 
	 * @param updatedData 
	 * 
	 * @return the list of I000044InputParam
	 */
	private List<I000044InputParam> createCustomQuery(List<I000044InputParam> paramList, String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<I000044InputParam> inputParamList = new ArrayList<I000044InputParam>();
			for(I000044InputParam param :paramList){
				Query query = entityManagerFactory
					.createNativeQuery(I000044QueryConstants.getOrderTakeBasePeriod
							.toString());
				query.setParameter(I000044Constants.PORCD, param.getPorCd());
				List selectResultSet = query.getResultList();
				if(selectResultSet != null && !(selectResultSet.isEmpty()) && selectResultSet.get(0) != null){
					param.setOrdrTakeBaseMnth((String)selectResultSet.get(0));
					inputParamList.add(param);
					porCdStr = porCdStr+" "+ param.getPorCd();
					buyerGrpCdStr = buyerGrpCdStr +" "+ param.getBuyerGrpCd();
				}else{ 
					 LOG.info(PDMessageConsants.M00159.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
							 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.Stage_status_CD)
							 .replace(PDConstants.ERROR_MESSAGE_3, param.getPorCd())
							 .replace(PDConstants.ERROR_MESSAGE_4, PDConstants.MONTHLY_ORDER_TAKE_BASE_PERIOD_MST));
					 porCd= porCd +""+param.getPorCd();
		      	   LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
				}
			  }
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return inputParamList;

	}
	
	/**
	 * Format the input values and store in list
	 * @param inputStr
	 * @return
	 */
	private List<I000044InputParam> deformatInputs(String inputStr) {

		List<I000044InputParam> list = new ArrayList<I000044InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(I000044Constants.FORMAT));
			if(mainList != null && !(mainList.isEmpty())){
				for (String str : mainList) {
					list.add(getInputParam(str));
				}
			}else{
				LOG.error("Array of Input param doesn't meet required length :"+mainList.toString());
			}
		return list;
	}
	
	/**
	 * Format the input values and store in I000044InputParam
	 * @param str
	 * @return
	 */
	private I000044InputParam getInputParam(String str){
		
		String[] val = str.split(I000044Constants.AMPERSAND);
		I000044InputParam inputParam = new I000044InputParam();
		inputParam.setPorCd(isDataValid(val[0])); //PorCd
		inputParam.setOcfRegionCd(isDataValid(val[1]));//OcfRegionCd
		inputParam.setOcfBuyerCd(isDataValid(val[2]));//OcfBuyerCd
		inputParam.setRhqCd(isDataValid(val[3]));//RhqCd
		inputParam.setBuyerGrpCd(isDataValid(val[4]));//BuyerGrpCd
		return inputParam;
	}
	
	/**
	 * Check if the data is valid and return the string
	 * @param value
	 * @return the string
	 */
	private String isDataValid(String value){
		
		String nullString ="null";
		return ("*").equals(value) ||  ((nullString).equals(value)) ? "" : value;
		
	}

	public EntityManager getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManager entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	
}
