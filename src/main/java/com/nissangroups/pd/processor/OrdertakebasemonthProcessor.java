/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :S SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z013865(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.ORDER_TAKE_BASE_MONTH_PROCESSOR;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

/**
 * The Class OrdertakebasemonthProcessor.
 */
@Component(ORDER_TAKE_BASE_MONTH_PROCESSOR)
public class OrdertakebasemonthProcessor implements ItemProcessor<String, String> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(OrdertakebasemonthProcessor.class);
	 
 	/** Variable step execution. */
 	private StepExecution stepExecution;


	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	
	public String process(String maxordertakebasemonth)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		 JobExecution jobExecution = stepExecution.getJobExecution();
	        ExecutionContext jobContext = jobExecution.getExecutionContext();
	        jobContext.put(PDConstants.MAX_ORDER_TAKE_BASE_MONTH, maxordertakebasemonth);
	    LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);    
		return maxordertakebasemonth;
	}

	/**
	 * Retrieve interstep data.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
    public void retrieveInterstepData(StepExecution stepExecution) {
	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR); 	
       this.stepExecution = stepExecution;
    }

	/**
	 * After step.
	 *
	 * @param stepExecution the step execution
	 * @return the exit status
	 */
	@AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
           if( stepExecution.getReadCount() == 0){
        	 LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_3_ID)
        			 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.TBL_NM_WEEKLY_ORDER_TAKE_BASE_MONTH)
        			 .replace(PDConstants.ERROR_MESSAGE_3, PDConstants.MESSAGE_POR_CD)
        			 .replace( PDConstants.MESSAGE_POR_CD,stepExecution.getJobParameters().getString(
        							 PDConstants.PRMTR_POR))
        			 .replace(PDConstants.ERROR_MESSAGE_4,  PDConstants.MESSAGE_WEEKLY_TAKE_BASE_PERIOD) );
        	stepExecution.setExitStatus(ExitStatus.FAILED);
        	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
   			return ExitStatus.FAILED;
           }
           LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
           return ExitStatus.COMPLETED;
    }

}
