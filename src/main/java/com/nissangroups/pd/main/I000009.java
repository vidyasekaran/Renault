/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000009
 * Module          :Spec Master
 * Process Outline :Receive Vehicle Production Type Master from Plant																	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z014159(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.main;

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
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * Job Execution Main Class.
 *
 * @author z014159
 */
public class I000009 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000009.class.getName());

	/** Variable por cd. */
	private static String porCd;

	/** Variable interface id. */
	private static String INTERFACE_ID;

	/** Constant arg length */
	private static int ARGLENGTH = 2;
	
	/**
	 * Instantiates a new i000009.
	 */
	private I000009() {
	}

	/**
	 * Batch I000009 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && args.length == ARGLENGTH) {
			if (args[0] == null) {
				LOG.error(PDMessageConsants.M00107.replace(
						PDConstants.ERROR_MESSAGE_1,
						PDConstants.INTERFACE_FILE_ID));
				CommonUtil.stopBatch();
			}
			INTERFACE_ID = args[0];
			porCd = args[1];
		} else if  (args.length == 1) {
			LOG.error(PDMessageConsants.M00107
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_POR_CODE));
			CommonUtil.stopBatch();
		}
		else {
			LOG.error("Two Arguments expected, [INTERFACE_FILE_ID], [POR_CD]");
			CommonUtil.stopBatch();
		}
		run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z014159
	 */
	private static void run() {
		String[] batchConfig = { "I000009/I000009_IF_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.INTERFACE_ID_I000009);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.INTERFACE_FILE_ID, INTERFACE_ID)
					.addString(PDConstants.INTERFACE_STATUS,
							PDConstants.INTERFACE_UNPROCESSED_STATUS)
					.addString(PDConstants.BATCH_POR_CODE, porCd)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error(PDConstants.INTERFACE_ID_I000009, e);
		}
	}
}
