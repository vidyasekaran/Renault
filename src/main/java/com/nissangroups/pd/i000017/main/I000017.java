/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000017
 * Module          :SP SPEC MASTER
 * Process Outline :Send OSEI Feature CCF Interface to NSC (Standard Layout) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000017.main;

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
 *
 */
public class I000017 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000017.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 3;

	/**
	 * Instantiates a new I000017.
	 */
	private I000017() {
	}

	/**
	 * Interface I000017 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("IF17 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id, FileName, [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, Buyer Group Code]...");
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
	private static void run(String[] args) {

		String[] batchConfig = { PDConstants.INTERFACE_I17_CONFIG_PATH };
		String interfaceFileId = args[0];
		String fileName = args[1];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.BATCH_ID_17);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, formatInputs(args))
					.addString(IFConstants.FILE_NAME, fileName)
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
	 * @return formatted String of PorCd,OcfRegionCd,OcfBuyerCd,RhqCd,BuyerGrpCd
	 *         array
	 * 
	 */
	private static String formatInputs(String[] args) {

		StringBuilder inputBuilder = new StringBuilder();

		for (int i = args.length - 1; i > 1; i--) {

			String[] finalarg = args[i].split(",");

			if (finalarg.length >= IFConstants.ARGS_LENGTH) {

				inputBuilder.append(finalarg[0] + IFConstants.AMPERSAND);// PorCd
				inputBuilder.append(finalarg[1] + IFConstants.AMPERSAND);// OcfRegionCd
				inputBuilder.append(finalarg[2] + IFConstants.AMPERSAND);// OcfBuyerCd
				inputBuilder.append(finalarg[3] + IFConstants.AMPERSAND);// RhqCd
				inputBuilder.append(finalarg[4] + IFConstants.AMPERSAND);// BuyerGrpCd
				inputBuilder.append(IFConstants.FORMAT);
			} else {
				LOG.error("Array of Input param doesn't meet required length :"
						+ Arrays.toString(finalarg));
			}

		}

		return inputBuilder.toString();

	}

}
