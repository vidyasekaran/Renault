/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000088
 * Module                 : Ordering		
 * Process Outline     	  : Send Weekly Production Schedule Interface to NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014029(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000088.main;

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
 *@author z014029
 */
public class I000088 {

	

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000088.class);
	
	/** Constant comma. */
	private static final String COMMA ="',";
	
	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 3;
	
	/**
	 * Instantiates a new I000088.
	 */
	private I000088() {
		
	}
	/**
	 * Interface I000088 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected args : I000088 I000088 06,G,GOM,NMEF,7BRD39

		LOG.info("IF88 Args : " + Arrays.deepToString(args));
		if (null == args || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected, Interface_File_Id, FileName, [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, BUYER GROUP CODE]...");
			return;
		} else {
			if ("null".equalsIgnoreCase(args[0])
					|| "null".equalsIgnoreCase(args[1])) {
				LOG.error("InterfaceFileId or FileName should not be null");
				return;
			} else {
				run(args);
			}
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I88_CONFIG_PATH };
		String interfaceFileId = args[0]; //I000088
		String fileName = args[1]; // I000088
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.INTERFACE_88_ID);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.INPUT_PARAM, formatInputs(args)[0])
					.addString(IFConstants.param_porCdLst,
							formatInputs(args)[1])
					.addString(IFConstants.FILE_NAME, fileName)
					// .addString(IFConstants.STAGE_CODE, formatString(stgCd))
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);
		}
	}

	/*
	 * This method formatting the inputs
	 * 
	 * @Param String[] args
	 */
	private static String[] formatInputs(String[] args) {
		String[] varArr = new String[2];
		StringBuilder inputBuilder = new StringBuilder();
		StringBuilder porCdStr = new StringBuilder();
		for (int i = args.length - 1; i > 1; i--) {

			String[] finalarg = args[i].split(",");

			if (finalarg.length >= IFConstants.ARGS_LENGTH && !"".equalsIgnoreCase(finalarg[0])) {

				inputBuilder.append(finalarg[0] + IFConstants.AMPERSAND);
				porCdStr.append("'" + finalarg[0] + COMMA);
				inputBuilder.append(finalarg[1] + IFConstants.AMPERSAND);
				inputBuilder.append(finalarg[2] + IFConstants.AMPERSAND);
				inputBuilder.append(finalarg[3] + IFConstants.AMPERSAND);
				inputBuilder.append(finalarg[4] + IFConstants.AMPERSAND);
				inputBuilder.append(IFConstants.FORMAT);
			} else {
				LOG.error("Array of Input param doesn't meet required length :"
						+ Arrays.toString(finalarg));
			}
		}

		if (inputBuilder.length() > 0) {
			int ind = porCdStr.toString().lastIndexOf(",");
			porCdStr = porCdStr.replace(ind, ind + 1, "");
			varArr[0] = inputBuilder.toString();
			varArr[1] = porCdStr.toString();
		} else {
			varArr[0] = "";
			varArr[1] = "";

		}
		return varArr;
	}

}