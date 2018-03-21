/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000011
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Control the Order Take on Local Systems 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000011.main;

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

import com.nissangroups.pd.i000011.util.I000011QueryConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 * @author z014029
 */
public class I000011 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000011.class);
	
	/**
	 * Instantiates a new I000011.
	 */	
	private I000011() {
	}

	/**
	 * Interface I000011 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("Interface I000011 Arguments : " + Arrays.deepToString(args));
		if (args == null || args.length < 2) {
			LOG.error("Arguments expected: Interface_File_Id, FileName, [POR CD, OCF REGION CD, OCF BUYER GROUP CD, RHQ CD, BUYER GROUP CD, PERIOD LIMIT, END ITEM STATUS CD]...");
			return;
		}
		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I11_CONFIG_PATH };
		String interfaceFileId = args[0];
		String filename = args[1];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.BATCH_ID_11);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, formatInputs(args))
					.addString(IFConstants.FILE_NAME, filename)
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
	 * @param args
	 * @return  formatted String of POR CD, OCF REGION CD, OCF BUYER GROUP CD, RHQ CD, BUYER GROUP CD, PERIOD LIMIT, END ITEM STATUS CD array
	 * 
	 * This method convert array of input arguments into single formatted String 
	 */
	private static String formatInputs(String[] args) {

		StringBuilder inputBuilder = new StringBuilder();

		for (int i = args.length - 1; i > 1; i--) {

			String[] finalarg = args[i].split(",");

			if (finalarg.length >= I000011QueryConstants.argLength) {

				inputBuilder.append(finalarg[0] + IFConstants.AMPERSAND); //PORCD
				inputBuilder.append(finalarg[1] + IFConstants.AMPERSAND); //OCF REGION CODE
				inputBuilder.append(finalarg[2] + IFConstants.AMPERSAND); //OCF BUYER GROUP CODE
				inputBuilder.append(finalarg[3] + IFConstants.AMPERSAND); //RHQ CD
				inputBuilder.append(finalarg[4] + IFConstants.AMPERSAND); //BUYER GROUP CODE
				inputBuilder.append(finalarg[5] + IFConstants.AMPERSAND); //PERIOD LIMIT

				StringBuilder statusCd = new StringBuilder();
				for (int j = I000011QueryConstants.argCount; j < finalarg.length; j++) {
					statusCd.append("'"
							+ finalarg[j].replace("(", "").replace(")", "")
							+ "',");
				}
				int ind = statusCd.toString().lastIndexOf(",");
				statusCd = statusCd.replace(ind, ind + 1, ""); 						//END ITEM STATUS CD
				inputBuilder.append(statusCd);
				inputBuilder.append(IFConstants.FORMAT);
			} else {
				LOG.error("Array of Input param doesn't meet required length :"
						+ Arrays.toString(finalarg));
			}
		}
		return inputBuilder.toString();
	}
}