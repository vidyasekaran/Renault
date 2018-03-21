/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000023
 * Module          :Send_OSEI_Frozen_Interface_to_NSC
 
 * Process Outline : Send the OSEI frozen master details to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000023.main;

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
 * This is the Main Class to Execute the interface I000023.
 * 
 * @author z014029
 */

public class I000023 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000023.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH =2;
	
	/**
	 * Instantiates a new I000023.
	 */
	private I000023() {
	}

	/**
	 * Interface I000023 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("Interface 23 Arguments : " + Arrays.deepToString(args));

		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected, [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, PERIOD LIMIT, END ITEM STATUS CD, BUYER GROUP CODE]...");
			return;
		}
		run(args);
	}

	/**
	 * Run method to kick start interface I000023.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I23_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.BATCH_ID_23);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, formatInputs(args))
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
	 * This method convert array of input arguments into single formatted String
	 * 
	 * @param args
	 * @return formatted String of POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, BUYER GROUP CD array
	 * 
	 *  
	 */
	private static String formatInputs(String[] args) {
		final int argLength = 5; 
		StringBuilder inputBuilder = new StringBuilder();

		for (int i = args.length - 1; i > 0; i--) {
			String[] finalarg = args[i].split(",");

			if (finalarg.length >= argLength) {

				inputBuilder.append(finalarg[0] + IFConstants.AMPERSAND); //POR CD
				inputBuilder.append(finalarg[1] + IFConstants.AMPERSAND); //OCF REGION CD
				inputBuilder.append(finalarg[2] + IFConstants.AMPERSAND); //OCF BUYER GROUP
				inputBuilder.append(finalarg[3] + IFConstants.AMPERSAND); //RHQ CD
				inputBuilder.append(finalarg[4] + IFConstants.AMPERSAND); //BUYER GROUP CD
				inputBuilder.append(IFConstants.FORMAT);
			} else {
				LOG.error("Array of Input param doesn't meet required length :"
						+ Arrays.toString(finalarg));
			}
		}
		return inputBuilder.toString();
	}
}