/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.main;

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

import com.nissangroups.pd.i000044.util.I000044Constants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 *@author z016127
 */
public class I000044 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000044.class);

	/**
	 * Instantiates a new I000044.
	 */
	private I000044(){
		
	}
	
	/**
	 * Interface I000044 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("Interface 44 Arguments : " + Arrays.deepToString(args));
		if (args == null || args.length < I000044Constants.ARGS_LENGTH  ) {
			LOG.error("Arguments expected, FileName, InterfaceFileId, SummarizeOrderQtyFlg, [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, Buyer Group Code]...");
			return;
		}else {
			for (int i = args.length -1; i > 2; i--) {
				String[] finalarg = args[i].split(",");
				if (finalarg.length < I000044Constants.FINALARG_LENGTH) {
					LOG.error("Array of Input param doesn't meet required length");
					return;
				}
			}
		}
		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I44_CONFIG_PATH };
		// Interface File Id
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_44_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID,interfaceFileId)
					.addString(IFConstants.FILE_NAME,args[1]) // File Name
					.addString(IFConstants.SUMMARIZE_ORDER_QTY_FLAG,
							args[2])// Summarize Order Flag
					.addString(IFConstants.INPUT_PARAM,
							formatInputs(args)).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

	/**
	 * Format the input values and stores in the list
	 *
	 * @param String[] args
	 * @return
	 */
	private static String formatInputs(String[] args) {

		StringBuilder inputBuilder = new StringBuilder();
		for (int i = args.length -1; i > 2; i--) {
			String[] finalarg = args[i].split(",");

			if (finalarg.length >= I000044Constants.FINALARG_LENGTH) {
				inputBuilder.append(finalarg[0] + I000044Constants.AMPERSAND);//PorCd -- 12
				inputBuilder.append(finalarg[1] + I000044Constants.AMPERSAND);//OcfRegionCd -- G
				inputBuilder.append(finalarg[2] + I000044Constants.AMPERSAND);//OcfBuyerCd -- GOM
				inputBuilder.append(finalarg[3] + I000044Constants.AMPERSAND);//RhqCd -- NMAP
				inputBuilder.append(finalarg[4] + I000044Constants.AMPERSAND);//BuyerGrpCd -- TEST01
				inputBuilder.append(I000044Constants.FORMAT);
			}else{
				LOG.error("Array of Input param doesn't meet required length :"+ Arrays.toString(finalarg));
			}
		}
		return inputBuilder.toString();
	}

}
