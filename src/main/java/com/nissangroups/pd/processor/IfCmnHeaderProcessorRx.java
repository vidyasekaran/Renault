package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_FAIL;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.STEP_START;
import static com.nissangroups.pd.util.PDConstants.STEP_SUCCESS;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.util.IfCommonUtil;

public class IfCmnHeaderProcessorRx   implements ItemProcessor<CmnFileHdr, CmnFileHdr> {

	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(CMNHeaderProcess.class);

	/** Variable step execution id. */
	private String stepExecutionID;	
		
	@Autowired(required = false)
	IfCommonUtil commonutility;

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
		String status = commonutility.getStatus();		
		String remarks = commonutility.getRemarks();
		item.setStts(status);
		item.setRemarks(remarks);
		if (stepExecutionID.equals(STEP_FAIL)) {		
			//item.setRemarks(M00043);
			//item.setRemarks(M00076);
		} else if (stepExecutionID.equals(STEP_SUCCESS) && commonutility.getRowCount() != 0 ) {			
			item.setRecCount(new BigDecimal(commonutility.getRowCount()));			
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
