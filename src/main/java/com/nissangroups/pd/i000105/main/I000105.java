/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000105
 * Module          :CM Common
 * Process Outline :Interface_Send Organization Master Interface to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000105.main;

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
 * @author z015895
 *
 */
public class I000105 {
	
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000105.class);
	
	/** Variable Constant args lenth */
	private static int ARG_LENGTH = 0;
	
		/**
	 * Instantiates a new I000105.
	 */	
	private I000105() {
	}

	/**
	 * Interface I000105 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		
		if (args == null || args.length == ARG_LENGTH) {			
			LOG.error("Arguments expected : interfaceFileId...");									
			return;
		}
		
		// verifying arguments
		String interfaceFileId = args[0];
		
		run(interfaceFileId);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @param interfaceFileId
	 */
	@SuppressWarnings("resource")
	private static void run(String interfaceFileId) {
		String[] batchConfig = { PDConstants.INTERFACE_I105_CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_105_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID,
							interfaceFileId).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

}
