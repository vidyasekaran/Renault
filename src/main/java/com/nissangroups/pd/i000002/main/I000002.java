/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I00002
 * Module          : CM COMMON					
 * Process Outline : Send_ Buyer_ Code_ Interface _to_ VLM
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z015896(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000002.main;

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
 * @author z015896
 */
public class I000002 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000002.class);

	/** Constant arg length */
	private static int ARGLENGTH = 2;
	
	/**
	 * Instantiates a new I000044.
	 */
	private I000002() {

	}

	/**
	 * Interface I000002 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		/**
		 * String interfaceFileId = "I000002"; String prodregcodes=
		 * "2,1,4,5,77,9";
		 * */
		if (args == null || args.length < ARGLENGTH) {
			LOG.error("Arguments expected : interfaceFileId, [Production Region Code]...");
			return;
		}
		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] params) {
		String[] batchConfig = {PDConstants.INTERFACE_I02_CONFIG_PATH};
		String interfaceFileId = params[0];// interface file id
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.BATCH_ID_02);

		try {

			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, getProdRegCodes(params))
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

	/** get production region codes */
	private static String getProdRegCodes(String[] porcd) {
		StringBuilder values = new StringBuilder();
		for (int i = 1; i < porcd.length; i++) {
			values = values.append("'" + porcd[i] + "',");
		}
		int ind = values.toString().lastIndexOf(",");
		values = values.replace(ind, ind + 1, "");
		return values.toString();
	}
}
