/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.main;

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

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 * @author z015895
 */
public class I000055 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000055.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 2;

	/**
	 * Instantiates a new I000055.
	 */
	private I000055() {
	}

	/**
	 * Interface I000055 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected Input : I000055 12
		LOG.info("IF055 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id  POR_CD");
			return;
		} else {
			run(args);
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	private static void run(String args[]) {
		String[] batchConfig = { PDConstants.INTERFACE_I55_CONFIG_PATH };
		String interfaceFileId = args[0];// I000055
		String porCd = args[1]; // 12
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.IF_ID_55);

		try {

			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.POR_CD, porCd).toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

}
