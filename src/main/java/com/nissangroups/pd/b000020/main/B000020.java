/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000020.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This is the Main Method to Execute the batch b000020.
 *
 * @author z011479
 */
public class B000020 {

	/** Constant LOG. */
	// Initializing Log properties
	private static final Log LOG = LogFactory.getLog(B000020.class.getName());

	/** Variable por cd. */
	private static String porCd;

	/** Variable update only flag. */
	private static String scrnFlg;

	/** Variable Stage Code. */
	private static String stgCd;

	/** Variable Sequence Code. */
	private static String seqId;

	/** Variable Sequence Code. */
	private static String batchId;
	/** Variable environment. */
	@Autowired(required = false)
	static Environment environment;

	/**
	 * Instantiates a new B000020.
	 */
	private B000020() {
	}

	/**
	 * Batch b000020 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {

		// verifying arguments

		if (args == null || args.length < 3) {
			LOG.error("Arguments [3] expected, [POR_CD, STAGE_CODE,SCREEN_FLAG], TO TRIGGER BATCH  ");
			LOG.error("Arguments [3] expected, [POR_CD, STAGE_CODE,SCREEN_FLAG], TO TRIGGER FROM SCREEN  ");
			CommonUtil.stopBatch();
		}

		porCd = args[0];
		stgCd = args[1];
		scrnFlg = args[2];
		run();
	}

	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run() {
		ApplicationContext context = null;

		if (scrnFlg.equalsIgnoreCase(PDConstants.CONSTANT_ZERO)) {
			String[] batchConfig = { PDConstants.BATCH_20_BATCH_CONFIG_PATH };
			context = new ClassPathXmlApplicationContext(batchConfig);
		} else {
			String[] batchConfig = { PDConstants.BATCH_20_SCREEN_CONFIG_PATH };
			context = new ClassPathXmlApplicationContext(batchConfig);
		}

		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(PDConstants.BATCH_20_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.BATCH_POR_CODE, porCd)
					.addString(PDConstants.BATCH_SCRN_FLG, scrnFlg)
					.addString(PDConstants.SEQ_NO_PARAM, seqId)
					.addString(PDConstants.STG_CD, stgCd)
					.addString(PDConstants.BATCH_ID, batchId).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error(PDConstants.BATCH_20_ID, e);
		}

	}

}
