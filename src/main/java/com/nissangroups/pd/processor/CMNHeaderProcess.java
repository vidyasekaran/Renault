/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :All
 * Module          :All
 * Process Outline :All
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	 z002548(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.COMM_HEADER_STATUS_FAIL;
import static com.nissangroups.pd.util.PDConstants.COMM_HEADER_STATUS_SUC;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_FAIL;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.STEP_START;
import static com.nissangroups.pd.util.PDConstants.STEP_SUCCESS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnFileHdr;


/**
 * The Class CMNHeaderProcess.
 *
 * @author z002548
 */
public class CMNHeaderProcess implements ItemProcessor<CmnFileHdr, CmnFileHdr> 
{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(CMNHeaderProcess.class);

	/** Variable step execution id. */
	private String stepExecutionID;

	/**
	 * In this method to get the Step Name and assign this value to some String value.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		stepExecutionID = stepExecution.getStepName();
		LOG.info(STEP_START);
		LOG.info(STAR+STEP_ID + stepExecutionID+STAR);
	}

	/**
	 *  
	 * In this method to update Status and Remarks in the Common File Header table.
	 *
	 * @param item the item
	 * @return the cmn file hdr
	 * @throws Exception the exception
	 */
	@Override
	public CmnFileHdr process(CmnFileHdr item) throws Exception {
	    /* If the Step1 return Fails or complete then update Status & Remarks */
		if (stepExecutionID.equals(STEP_FAIL)) {
			item.setStts(COMM_HEADER_STATUS_FAIL);
			item.setRemarks(M00043);
		} else if (stepExecutionID.equals(STEP_SUCCESS) ) {
			item.setStts(COMM_HEADER_STATUS_SUC);
			item.setRemarks(M00113);
		} 
		return item;
	}
	
	/**
	 * After step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
	
	}

}
