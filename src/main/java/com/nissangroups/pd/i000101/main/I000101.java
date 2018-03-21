/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.main;

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
 * This is the main program to kick off interface I000101 
 * 
 * @author z014676
 */
public class I000101 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000101.class);

	/** Constant arg length */
	private static int ARGLENGTH = 2;
	/**
	 * Instantiates a new I000101.
	 */
	private I000101() {

	}

	/**
	 * Interface I000101 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected Input : I000101 09,10,11,12
		LOG.info("IF0101 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARGLENGTH) {
			LOG.error("Arguments expected: Interface_File_Id and [POR CD]...");
			return;
		}

		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I101_CONFIG_PATH };
		String interfaceFileId = args[0];// I000101
		String porcd = args[1];// 09,12,33...
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		/*
		 * throws JobParametersInvalidException if the parameters are not valid
		 * for this job throws IllegalArgumentException if the job or
		 * jobInstanceProperties are null.
		 */
		try {
			Job job = (Job) context.getBean(interfaceFileId);
			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.porCd_Param, porcd)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread " + e);

		}

	}

}
