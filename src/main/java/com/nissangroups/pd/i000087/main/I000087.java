/*
 * System Name     :Post Dragon 
 * Sub system Name :I Interface
 * Function ID     :PST_DRG_I000087
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly Production Schedule from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000087.main;

import static com.nissangroups.pd.util.PDConstants.INTERFACE87;
import static com.nissangroups.pd.util.PDMessageConsants.M00107;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 *@author z016127
 */
public class I000087 {
	
	/** Constant LOG. */
	private static final Log LOG  = LogFactory.getLog(I000087.class.getName());	
	
	/** Variable interface file id. */
	private static String INTERFACE_FILE_ID;
	
	/** Variable por cd. */
	private static String POR_CD;	
	
	/** Variable argument length */
	private static final int ARGS_LENGTH =2;
	
	/**
	 * Instantiates a new I000087.
	 */
	private I000087() {
		super();
	}

	/**
	 * Interface I000087 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length < ARGS_LENGTH){
			LOG.error(M00107);
			LOG.error("Arguments [2] expected, [INTERFACE_FILE_ID,POR_CD]");
			CommonUtil.stopBatch();
		}
		//Interface File Id
			INTERFACE_FILE_ID= args[0];
		//Por CD	
			POR_CD= args[1];
	    	
			run();
		}
	
	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z016127
	 */
	@SuppressWarnings("resource")
	private static void run(){

		String[] batchConfig = { PDConstants.INTERFACE_I87_CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(batchConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(INTERFACE87);		
		try {
			JobParameters parameter = new JobParametersBuilder().addString(PDConstants.BATCH_POR_CODE, POR_CD)
					.addString(PDConstants.INTERFACE_FILE_ID, INTERFACE_FILE_ID)
					.addString(PDConstants.INTERFACE_STATUS,
					PDConstants.INTERFACE_UNPROCESSED_STATUS).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		}
		catch(Exception e)
		{
			LOG.error(INTERFACE87,e);
		}
	}	
}