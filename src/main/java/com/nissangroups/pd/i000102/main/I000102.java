/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000102
 * Module          : CM COMMON					
 * Process Outline : Send Logical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-12-2014  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000102.main;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Job Execution Main Class I000102
 * 
 * @author z015895
 */
public class I000102 {

	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(I000102.class);
	
	/** Variable Interface args length */
	private static int ARG_LENGTH = 2;
	
	/**
	 * Instantiates a new I000102.
	 */
	private I000102() {

	}

	/**
	 * Interface I000102 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected args : I000102 11 22 33 44

		LOG.info("IF102 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id [PORCD1,PORCD2,PORCD3.....]");
			return;
		}

		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {

		String[] batchConfig = { PDConstants.INTERFACE_I102_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_102_ID);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, getPorcodes(args))
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

	/**
	 * This method returns a list of POR Codes with comma seperated values
	 * 
	 * @param args
	 * @return porcd value
	 */
	public static String getPorcodes(String[] args) {

		StringBuilder values = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (!("'*'").equals(args[i])) {
				values.append("'" + args[i] + "',");
			}
		}
		if (!values.toString().isEmpty()) {
			int ind = values.toString().lastIndexOf(",");
			values = values.replace(ind, ind + 1, "");
		}
		return values.toString();
	}

}
