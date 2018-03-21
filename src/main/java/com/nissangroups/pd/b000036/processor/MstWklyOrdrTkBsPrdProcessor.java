/*
 * System Name       : Post Dragon
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000036
 * Module                  : Ordering		
 * Process Outline     : Update Weekly order stage close															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000036.processor;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000036.util.B000036CommonUtil;
import com.nissangroups.pd.b000036.util.B000036Constants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.b000036.util.B000036Constants.*;

/**
 * Processor class for B000036
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(MST_WKLY_ORDR_ITEM_PROCESSOR)
public class MstWklyOrdrTkBsPrdProcessor implements
		ItemProcessor<Object, Object> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MstWklyOrdrTkBsPrdProcessor.class);
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable job execution. */
	private JobExecution jobExecution;
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		this.stepExecution = stepExecution;
        jobExecution= this.stepExecution.getJobExecution(); 
        
        JobParameters jobParameters = jobExecution.getJobParameters();
		jobParamPor = jobParameters.getString(PDConstants.PRMTR_PORCD);
		
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public Object process(Object item){
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		Object [] itmVal = (Object[])item;
		
		LOG.info("Extracted Item Values are : "+jobParamPor+ " and Order take base month  is :" +itmVal[0] + " and Order take base week number  is :" +itmVal[1]);
		
		if(itmVal[0] == null || itmVal[1] == null) {
        	String[] messageParams = {B000036Constants.BATCH_ID_B000036,B000036Constants.OPEN_STS,jobParamPor};
        	B000036CommonUtil.logMessage(PDMessageConsants.M00071, PDConstants.P0001, messageParams);
		}
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return item;
	}
	
	/**
	 * @param stepExecution
	 * @return Exit status
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
    			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
    		}
            
            LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
            
           return ExitStatus.COMPLETED;
	}
	
}