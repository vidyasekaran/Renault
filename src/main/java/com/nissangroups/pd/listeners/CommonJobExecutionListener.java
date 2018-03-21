/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :ALL
 * Module          :ALL
 * Process Outline :Listener Interface to monitor the Job Executions
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z002548(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.listeners;

import static com.nissangroups.pd.util.PDConstants.FAILED;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;


/**
 * The listener interface for receiving JobExecution events.
 * The class that is interested in processing a JobExecution
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJobExecutionListener<code> method. When
 * the JobExecution event occurs, that object's appropriate
 * method is invoked.
 *
 * @author z002548
 */
@Component
public class CommonJobExecutionListener implements JobExecutionListener {

	/** Constant LOGGER. */
	private static final Log LOGGER = LogFactory.getLog(CommonJobExecutionListener.class);

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	/*
	 * Status of Each Job Before Job Status
	 * 
	 * @param
	 * JobExecution#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOGGER.info("Before Job");

	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	/*
	 * Status of Each Job After Job Status
	 * 
	 * @param JobExecution#afterJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		// To Extract the Step Execution to Find out any Step Level Failed occured or not
		Iterator<StepExecution> stepIterator = jobExecution.getStepExecutions().iterator();
		while (stepIterator.hasNext()) {
			StepExecution stepExecution = (StepExecution) stepIterator.next();
			ExitStatus stepExitStatus = stepExecution.getExitStatus();
 			if(stepExitStatus.getExitCode().equals(FAILED)) {
 				//If Step Level Failed to Set the Job Execution Status to Failed
 				jobExecution.setStatus(BatchStatus.FAILED);
 				
 			}
			LOGGER.info("Step Name :"+stepExecution.getStepName()+"   Status :"+stepExitStatus.getExitCode());
			
		}
		// Completion or Failure of Batch Status
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info(jobExecution.getJobInstance().getJobName()+" Successfully Completed");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			LOGGER.info("Job Failed with JobID {} " + jobExecution.getJobId());
		}
		else if(jobExecution.getStatus() == BatchStatus.UNKNOWN){
			LOGGER.info("Job Failed due to Exception " + jobExecution.getJobId());
		}
	}

}
