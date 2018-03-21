/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :All
 * Module          :All
 * Process Outline :All
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.I000030_END_MSG;

import com.nissangroups.pd.util.PDConstants;


public class CmnAfterStepDtls {
	
	/** Constant LOG. */
	private static final  Log LOG = LogFactory.getLog(CmnAfterStepDtls.class.getName());
	
	private CmnAfterStepDtls(){
	}
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	public static void retrieveAfterStepDtls(StepExecution stepExecution) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR+stepExecution.getJobParameters().getString(PDConstants.INTERFACE_FILE_ID));
		
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		
		int readCnt = stepExecution.getReadCount();
		int writeCnt = stepExecution.getWriteCount();

		if (readCnt == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (readCnt == writeCnt) {
			LOG.info(M00113);
		}
		else {
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		LOG.info(DOLLAR+I000030_END_MSG+DOLLAR);
		
	}
	
}
