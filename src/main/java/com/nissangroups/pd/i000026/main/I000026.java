/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000026
 * Module          :Ordering
 * Process Outline :main method for I000026 Interface	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000026.main;

import static com.nissangroups.pd.util.PDConstants.INTERFACE_26_ID;
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
 * This is the Main Class to Execute the interface I000026.
 * 
 * @author z014029 I000026 Main Class
 */
public class I000026 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000026.class.getName());
	
	/** Variable Constant args lenth */
	private static int ARGLENGTH = 2;

	/** Variable interface file id. */
	private static String INTERFACE_FILE_ID;

	/** Variable por cd. */
	private static String POR_CD;

	
	/**
	 * Instantiates a new I000026.
	 */	
	public I000026() {
		super();
	}

	/**
	 * Interface I000026 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected args : I000026 09
		
		if (args == null || args.length != ARGLENGTH) {
			LOG.error("Arguments [2] expected, [INTERFACE_FILE_ID,POR_CD]");
			CommonUtil.stopBatch();
		}
		INTERFACE_FILE_ID = args[0];
		POR_CD = args[1];

		run();
	}

	/**
	 * Run method to kick start interface I000026
	 */
	@SuppressWarnings("resource")
	private static void run() {

		String[] batchConfig = { PDConstants.INTERFACE_I26_CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(INTERFACE_26_ID);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.BATCH_POR_CODE, POR_CD)
					.addString(PDConstants.INTERFACE_FILE_ID, INTERFACE_FILE_ID)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error(INTERFACE_26_ID, e);
		}
	}
}