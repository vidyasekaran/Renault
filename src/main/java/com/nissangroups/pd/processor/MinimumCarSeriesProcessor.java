/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-MinimumCarSeriesProcessor
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
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
import static com.nissangroups.pd.util.PDConstants.MIN_CAR_SERIES_PROCESSOR;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

/**
 * The Class MinimumCarSeriesProcessor.
 */
@Component(MIN_CAR_SERIES_PROCESSOR)
public class MinimumCarSeriesProcessor implements ItemProcessor<String, String> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MinimumCarSeriesProcessor.class);
	 
 	/** Variable step execution. */
 	private StepExecution stepExecution;

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public String process(String minimumcarseriesperiod)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        /** Start date */
        String minimumYearMonth = jobContext.get(PDConstants.MAX_ORDER_TAKE_BASE_MONTH).toString();  
		SimpleDateFormat sdf = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(minimumYearMonth));
		c.add(Calendar.MONTH,  Integer.valueOf(minimumcarseriesperiod));  
		minimumYearMonth = sdf.format(c.getTime()); 
        jobContext.put(PDConstants.MINIMUM_CAR_SERIES_PERIOD, minimumYearMonth);
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return minimumcarseriesperiod;
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
        			.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_MINIMUM_CAR_SERIES_PERIOD)
        			.replace(PDConstants.ERROR_MESSAGE_3, PDConstants.MESSAGE_POR_CD)
        			 .replace( PDConstants.MESSAGE_POR_CD,stepExecution.getJobParameters().getString(
        							 PDConstants.PRMTR_POR))
        			.replace(PDConstants.ERROR_MESSAGE_4,  PDConstants.MESSAGE_MST_PARAMETER));
        	stepExecution.setExitStatus(ExitStatus.FAILED);
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
   			return ExitStatus.FAILED;
           }
           LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
           return ExitStatus.COMPLETED;
    }

}
