/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is a decider which decides the process flow based on interface master MultipleFiles column value
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;

public class B000059FileProcessDecider implements JobExecutionDecider {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059FileProcessDecider.class.getName());

	/**
	 * This method used to decide single file processing or multiple files
	 * processing
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {

		LOG.info("B000059FileProcessDecider" + DOLLAR + INSIDE_METHOD + DOLLAR);

		if (commonutility.getFileSpecVO().isFileDecider()) {
			LOG.info("********B000059FileSpecVO.fileDecider is TRUE and so going to process multiple files*********");
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return new FlowExecutionStatus("multiFiles");
		} else {
			LOG.info("********B000059FileSpecVO.fileDecider is FALSE and so going to process single file*********");
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return new FlowExecutionStatus("multiFiles");
		}
	}

}
