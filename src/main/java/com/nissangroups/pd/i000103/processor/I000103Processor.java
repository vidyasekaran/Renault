/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000103.output.I000103OutputBean;
import com.nissangroups.pd.i000103.process.UpdateAmoProcess;
import com.nissangroups.pd.i000103.process.UpdateBasedOnLineTypeProcess;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This method is to process all the extracted common interface data records except the error records and insert / update into different tables
 * 
 * @author z015847
 *
 */
public class I000103Processor implements
		ItemProcessor<I000103OutputBean, String> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000103Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution */
	private JobExecution jobExec;
	
	/** UpdateAmoProcess service bean injection */
	@Autowired(required = false)
	private UpdateAmoProcess updateAmoProcess;
	
	/** UpdateBasedOnLineTypeProcess service bean injection */
	@Autowired(required = false)
	private UpdateBasedOnLineTypeProcess updateBasedOnLineTypeProcess;
	
	/**
	 * This method will be called just before each step execution
	 * Get stepExecution and assign into instance variable
	 * 
	 * @param stepExecution 
	 * 					the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		updateBasedOnLineTypeProcess.insertUserMstrDtls();
		updateBasedOnLineTypeProcess.insertRoleDtls();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/** 
	 * This method is to process all the extracted common interface data records except the error records and insert / update into different tables
	 * P0001.A, P0001.B, P0001.C, P0001.D
	 * P0003, P0004, P0005, P0006, P0007
	 * 
	 * @param item 
	 * 				I000103OutputBean
	 * @return the string 
	 * @throws Exception the exception
	 */
	@Override
	public String process(I000103OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		/** P0001.A, P0001.B, P0001.C, P0001.D */
		updateAmoProcess.executeProcess(item, ifFileId);
		
		/**P0003, P0004, P0005, P0006, P0007 */
		updateBasedOnLineTypeProcess.executeProcess(item , ifFileId);
		return ifFileId; 
	}
	
	/**
	 * This method gets executed after each step Execution to get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getReadCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		
	if (stepExecution.getReadCount() == 0) {
		jobExec.getExecutionContext().put(IFConstants.SEQ_NO, 0);
		commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
		commonutility.setRemarks(PDMessageConsants.M00003);
		LOG.info(PDMessageConsants.M00003);
	}
	else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
		commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
		commonutility.setRemarks(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
									.getFailureExceptions().toString()));
		LOG.info(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
									.getFailureExceptions().toString()));
	}
	else {
		//write count in header
		commonutility.setRowCount(stepExecution.getReadCount());
		commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
		commonutility.setRemarks(PDMessageConsants.M00113);
		LOG.info(PDMessageConsants.M00113);
	}
	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	return ExitStatus.COMPLETED;
}

}
