/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000033.main;

import static com.nissangroups.pd.util.PDMessageConsants.M00107;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.i000033.main.I000033;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 * @author z014676
 */
public class I000033 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000033.class);

	/** Batch Parameter interface file id. */
	private static String INTERFACE_FILE_ID;

	/** Constant arg length */
	private static int ARGLENGTH = 3;
	/** Batch Parameter POR CD. */
	private static String POR_CD;
	/*
	 * Batch Parameter FILE NAME
	 */
	private static String FILE_NAME;

	/**
	 * Instantiates a new I000033.
	 */
	private I000033() {
	}

	/**
	 * Interface I000033 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected args : I000033 07 I000033
		LOG.info("IF33 Args : " + Arrays.deepToString(args));
		/*
		 * Check the input arguments
		 */
		if (args == null || args.length < ARGLENGTH) {
			LOG.error(M00107);
			LOG.error("Arguments  expected, INTERFACE_FILE_ID  FILE_NAME  POR_CD");
		}
		INTERFACE_FILE_ID = args[0]; // I000033
		POR_CD = args[1]; // 07
		FILE_NAME = args[2]; // I000033
		

		run();
	}

	/**
	 * 
	 * Execute the Jobs and corresponding steps in sequence. .
	 */
	@SuppressWarnings("resource")
	private static void run() {

		String[] batchConfig = { PDConstants.INTERFACE_I33_CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("I000033");
		/*
		 * throws JobParametersInvalidException if the parameters are not valid
		 * for this job throws IllegalArgumentException if the job or
		 * jobInstanceProperties are null.
		 */
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, INTERFACE_FILE_ID)
					.addString(PDConstants.BATCH_POR_CODE, POR_CD)
					.addString(IFConstants.FILE_NAME, FILE_NAME)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread " + e);

		}

	}

}
