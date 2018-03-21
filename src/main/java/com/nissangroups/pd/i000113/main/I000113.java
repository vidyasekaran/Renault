/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z014433(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.main;

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
 * @author z014433
 */
public class I000113 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000113.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 2;

	/**
	 * Instantiates a new I000113.
	 */
	private I000113() {

	}

	/**
	 * Interface I000113 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("IF113 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id and [POR CD]...");
			return;
		}

		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	private static void run(String args[]) {
		String[] batchConfig = { PDConstants.INTERFACE_I113_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.IF_ID_113);

		try {

			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.param_porCdLst, getPorcodes(args))
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}
	}

	/**
	 * This method returns a list of POR Codes values
	 * 
	 * @param args
	 * @return porcd value
	 */
	private static String getPorcodes(String args[]) {
		StringBuilder values = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			values.append("'" + args[i] + "',");
		}
		int ind = values.toString().lastIndexOf(",");
		values = values.replace(ind, ind + 1, "");
		return values.toString();
	}
}
