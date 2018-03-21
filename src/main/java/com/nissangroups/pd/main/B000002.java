/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002
 * Module          :@Create Spec Masters
 * Process Outline :@Create Spec Masters main Class
 *
 * <Revision History>
 * Date       			Name(RNTBCI)             	Description 
 * ---------- ------------------------------ ---------------------
 * @08-07-2015  	  @author(z013576)               New Creation
 *
 */

package com.nissangroups.pd.main;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static com.nissangroups.pd.util.PDConstants.B000002_ARG_EXCEPTION_MSG;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.BATCHNM;
import static com.nissangroups.pd.util.PDConstants.BATCH_2_ID;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;

/**
 * Job Execution Main Class.
 * @version V1.0
 */
public class B000002 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002.class);
	
	/** Variable por. */
	static String por ;
	
	/**
	 * Batch B000002 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 * @throws JobRestartException the job restart exception
	 * @throws JobInstanceAlreadyCompleteException the job instance already complete exception
	 */
	public static void main(String[] args) throws JobRestartException, JobInstanceAlreadyCompleteException{
    	
		/* Verifying Arguements */
        if(args==null || args.length!=1){
		   LOG.error(B000002_ARG_EXCEPTION_MSG);
		  throw new IllegalArgumentException();
		}
    	por = args[0];
        try {
			run();
		} catch (JobParametersInvalidException e) {
			LOG.error(EXCEPTION, e);
		}
     }
	
	/**
	 * Run method
	 *
	 * @throws JobRestartException the job restart exception
	 * @throws JobInstanceAlreadyCompleteException the job instance already complete exception
	 * @throws JobParametersInvalidException the job parameters invalid exception
	 */
	public static void run() throws JobRestartException,JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		String[] jobConfig = {"B000002/B000002_Batch_Config.xml"};
		@SuppressWarnings("resource")
		/* Creating Applicationcontext */
		ApplicationContext appContext = new ClassPathXmlApplicationContext(jobConfig);
		JobLauncher jobLauncher = (JobLauncher) appContext.getBean("jobLauncher");
		Job job = (Job) appContext.getBean("CreateSpecMsts");
		try {
			/* Adding Job Paramter */
			JobParameters jobParam = new JobParametersBuilder().addString(PRMTR_PORCD, por).addString(BATCHNM,BATCH_2_ID).toJobParameters();
			JobExecution jobExectution = jobLauncher.run(job,jobParam);
			LOG.info(jobExectution.getStatus().toString());
		} catch (JobExecutionAlreadyRunningException e) {
			LOG.error(EXCEPTION +e);
		}

	}

}
