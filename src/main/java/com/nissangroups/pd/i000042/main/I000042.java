/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000042
 * Module          :Ordering
 * Process Outline :Interface To Receive Monthly Production Schedule from Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000042.main;

import static com.nissangroups.pd.util.PDConstants.INTERFACE42;
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
 * @author z014676
 * 
 */
public class I000042 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000042.class.getName());

	/** Variable interface file id. */
	private static String INTERFACE_FILE_ID;
	
	/** Variable Interface args length */
	private static int ARGLENGTH = 2;

	/** Variable por cd. */
	private static String POR_CD;

	/**
	 * Instantiates a new I000042
	 */
	public I000042() {
		super();
	}

	/**
	 * Batch I000042 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args == null || args.length < ARGLENGTH) {
			LOG.error(M00107);
			LOG.error("Arguments [2] expected, [INTERFACE_FILE_ID,POR_CD]");
			CommonUtil.stopBatch();
		}
		INTERFACE_FILE_ID = args[0];
		POR_CD = args[1];

		run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence..
	 */
	@SuppressWarnings("resource")
	private static void run() {

		String[] batchConfig = { PDConstants.INTERFACE_I42_CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(INTERFACE42);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.BATCH_POR_CODE, POR_CD)
					.addString(PDConstants.INTERFACE_FILE_ID, INTERFACE_FILE_ID)
					.addString(PDConstants.INTERFACE_STATUS,
							PDConstants.INTERFACE_UNPROCESSED_STATUS)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error(INTERFACE42, e);
		}
	}
}