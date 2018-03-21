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

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.MINIMUM_CAR_SERIES_LIMIT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * This Class is used to add the extracted  Weekly Order Take base month in tot .
 *
 * @author z011479
 */
public class WeeklyOrderTakeBaseMonthProcessor implements
		ItemProcessor<String, String> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(WeeklyOrderTakeBaseMonthProcessor.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	
	/** Variable step execution. */
	private StepExecution stepExecution;

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public String process(String maxOrderTakeBaseMonth) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		JobExecution jobExecution = this.stepExecution.getJobExecution();
		int minimumCarSeries = getCalculatedFrznOrderTakeBaeMonth();
		String minimumCarSeriesLimit = convertToDateAndAddMonth(maxOrderTakeBaseMonth,minimumCarSeries);
		ExecutionContext stepContext = jobExecution.getExecutionContext();
		stepContext.put(MINIMUM_CAR_SERIES_LIMIT, minimumCarSeriesLimit);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return minimumCarSeriesLimit;
	}

	/**
	 * This method will be executed before the STEP1 to access the batch Job
	 * parameter values.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.stepExecution = stepExecution;
		JobExecution jobExecution = stepExecution.getJobExecution();
		jobParamPor = jobExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
    
    /**
     * This method used to calculate the Frozen Order Take Base Month .
     *
     * @return the calculated frzn order take bae month
     */
    public int getCalculatedFrznOrderTakeBaeMonth()
    {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	String getMinimumCarSeriesLimit = QueryConstants.getMinimumCarSeriesLimitQuery.toString();
    	Query getMinimumCarSeriesLimitQry = entityManager.createQuery(getMinimumCarSeriesLimit); 
    	getMinimumCarSeriesLimitQry.setParameter(PDConstants.PRMTR_PORCD, jobParamPor);
    	String  minimumHorizon =  (String) getMinimumCarSeriesLimitQry.getSingleResult();
    	int minHorzn =Integer.parseInt(minimumHorizon.trim());
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    	return minHorzn;
    }
    
    /**
     * This method is convert String to date and add the Horizon.
     *
     * @param str the str
     * @param hrzn the hrzn
     * @return the string
     * @throws ParseException the parse exception
     */
    public String convertToDateAndAddMonth(String str,int hrzn) throws ParseException{
    	
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
     	DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);
		DateFormat formatteryyyyMMdd = new SimpleDateFormat(PDConstants.DATE_FORMAT);
    	Calendar c = Calendar.getInstance();
    	c.setTime(formatter.parse(str));
    	c.add(Calendar.MONTH, hrzn); 
    	String dt = formatteryyyyMMdd.format(c.getTime());  
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    	return dt;
    	
    }
    
    /**
     * After Step Spring framework method used to print the transaction in the log file.
     *
     * @param stepExecution the step execution
     * @return the exit status
     */
	@AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
           if(stepExecution.getReadCount() == 0){
        	   LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_7_ID).replace(PDConstants.ERROR_MESSAGE_2, PDConstants.TBL_NM_WEEKLY_ORDER_TAKE_BASE_MONTH).replace(PDConstants.ERROR_MESSAGE_3, jobParamPor) );
        	   stepExecution.setExitStatus(ExitStatus.FAILED);
        	   LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
        	   return ExitStatus.FAILED;
        	   }
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);   
        return ExitStatus.COMPLETED;
    }
}