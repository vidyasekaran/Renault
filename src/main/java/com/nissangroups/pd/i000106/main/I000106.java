/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Batch
 * Function ID            : PST-DRG-I000106
 * Module                 : CM Common		
 * Process Outline     	  : Interface for Sending  Buyer Master to SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000106.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 *@author z016127
 */
public class I000106 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000106.class);
	
	/**Constant argument length */
	private static final int ARGS_LENGTH = 2;

	/**
	 * Instantiates a new I000106.
	 */
	private I000106(){
		
	}
	/**
	 * Interface I000106 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		
		if (args == null || args.length < ARGS_LENGTH) {			
			LOG.error("Arguments expected : interfaceFileId, UpdatedData");									
			return;
		}
		
		// verifying arguments
		// Interface File Id
		String interfaceFileId = args[0];
		//Updated Data
		String updatedData=args[1];
		
		run(interfaceFileId,updatedData);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z016127
	 */
	@SuppressWarnings("resource")
	private static void run(String interfaceFileId,String updatedData) {
		String[] batchConfig = { PDConstants.INTERFACE_I106_CONFIG_PATH };
 		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_106_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID,
							interfaceFileId)
					.addString(IFConstants.UPDATED_DATA, updatedData)		
							.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

}
