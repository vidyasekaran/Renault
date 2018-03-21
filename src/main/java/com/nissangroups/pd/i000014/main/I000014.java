/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000014
 * Module          :CM Common
 * Process Outline :Send OEI Feature OCF Interface to NSC (Standard) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000014.main;

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
public class I000014 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000014.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 2;

	/**
	 * Instantiates a new I000014.
	 */
	private I000014() {
	}

	/**
	 * Interface I000014 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("IF14 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, Buyer Group Code]...");
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

		String[] batchConfig = { PDConstants.INTERFACE_I14_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.BATCH_ID_14);
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
	 * @return formatted String of PorCd,OcfRegionCd,OcfBuyerCd,RhqCd,BuyerGrpCd
	 *         array
	 * 
	 */
	private static String formatInputs(String[] args) {

		StringBuilder inputBuilder = new StringBuilder();

		for (int i = args.length - 1; i > 0; i--) {

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
