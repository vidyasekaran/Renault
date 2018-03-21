/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000054
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z011479(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000054.main;

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
 * This is the Main Method to Execute the batch B000054.
 *
 * @author z011479
 */
public class B000054 {

	/** Constant LOG. */
	// Initializing Log properties
	private static final Log LOG = LogFactory.getLog(B000054.class.getName());

	/** Variable por cd. */
	private static String porCd;

	/** Variable update only flag. */
	private static String ordrTkBsMnth;

	/** Variable Stage Code. */
	private static String ordrTkBsWk;

	/** Variable Sequence Code. */
	private static String seqId;

	
	/** Variable environment. */
	@Autowired(required = false)
	static Environment environment;

	/**
	 * Instantiates a new B000054.
	 */
	private B000054() {
	}

	/**
	 * Batch b000054 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {

		// verifying arguments

		if (args == null || args.length < 4) {
			LOG.error("Arguments [4] expected, [POR_CD, ORDER_TK_BS_MNTH,ORDER_TK_BS_WK,SEQ ID], TO TRIGGER BATCH  ");
			CommonUtil.stopBatch();
		}

		porCd = args[0];
		ordrTkBsMnth = args[1];
		ordrTkBsWk = args[2];
		seqId = args[3];
		run();
	}

	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run() {
		ApplicationContext context = null;

			String[] batchConfig = { PDConstants.BATCH_54_BATCH_CONFIG_PATH };
			context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(PDConstants.BATCH_54_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.BATCH_POR_CODE, porCd)
					.addString(PDConstants.ORDER_TAKE_BASE_PERIOD, ordrTkBsMnth)
					.addString(PDConstants.ORDER_TAKE_BASE_WEEK, ordrTkBsWk)	
					.addString(PDConstants.BATCH_SEQ_ID, seqId).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error(PDConstants.BATCH_54_ID, e);
		}

	}

}
