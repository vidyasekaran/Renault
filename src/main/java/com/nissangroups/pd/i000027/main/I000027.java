/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send Monthly OCF Interface to NSC 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.main;

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

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * This is the Main Class to Execute the interface I000027
 * 
 * @author z014029
 */
public class I000027 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000027.class);

	/** Constant COMMA. */
	private static final String COMMA = "',";
	
	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH =4;
	
	/**
	 * Instantiates a new I000027.
	 */	
	private I000027() {
	}
	
	
	/**
	 * Interface I000027 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("IF27 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected, Interface_File_Id, FileName, StageCD, [POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, BUYER GROUP CODE]...") ;
			return;
		}
		run(args);
	}

	/**
	 * Run method to kick start interface I000027.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I27_CONFIG_PATH };
		String interfaceFileId = args[0];
		String fileName = args[1];
		String stgCd = args[2];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.INTERFACE_27_ID);
		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)					
					.addString(IFConstants.INPUT_PARAM, formatInputs(args)[0])
					.addString(IFConstants.param_porCdLst, formatInputs(args)[1])
					.addString(IFConstants.FILE_NAME, fileName)
					.addString(IFConstants.STAGE_CODE, formatString(stgCd))
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
	 * @return  formatted String of POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, BUYER GROUP CODE array
	 */
	private static String[] formatInputs(String[] args) {

		final int argLength = 5; 
		
		StringBuilder inputBuilder = new StringBuilder();
		StringBuilder porCdStr = new StringBuilder();
		for (int i = args.length - 1; i > 2; i--) {

			String[] finalarg = args[i].split(",");

			if (finalarg.length >= argLength) {

				inputBuilder.append(finalarg[0] + IFConstants.AMPERSAND);  //POR CD
				porCdStr.append("'"+finalarg[0] + COMMA); //POR CD	
				inputBuilder.append(finalarg[1] + IFConstants.AMPERSAND);  //OCF REGION CD
				inputBuilder.append(finalarg[2] + IFConstants.AMPERSAND);  //OCF BUYER GROUP
				inputBuilder.append(finalarg[3] + IFConstants.AMPERSAND);  //RHQ CD
				inputBuilder.append(finalarg[4] + IFConstants.AMPERSAND);  //BUYER GROUP CODE
				inputBuilder.append(IFConstants.FORMAT);
			}else{
				LOG.error("Array of Input param doesn't meet required length :" +Arrays.toString(finalarg));
			}
		}
		int ind = porCdStr.toString().lastIndexOf(",");
		porCdStr = porCdStr.replace(ind, ind + 1, "");
		 String[] varArr= new String[2];
		 varArr[0]= inputBuilder.toString();
		 varArr[1]= porCdStr.toString();
		 
		return varArr;
	}
	
	
	/**
	 * This method convert String passed with a comma seperated values 
	 * 
	 * @param args
	 * @return  formatted String of POR CD, OCF REGION CD, OCF BUYER GROUP, RHQ CD, BUYER GROUP CODE array
	 * 
	 * 
	 */
	private static String formatString(String inputStr){
		
		StringBuilder result = new StringBuilder();
		if(inputStr != null && !("").equals(inputStr) && !("''").equals(inputStr) && !("'").equals(inputStr)){
			
			String[] strArg = inputStr.split(",");			
			for (int i = 0; i < strArg.length; i++) {
				/*result.append("'"+strArg[i]+"',");*/ // Due to set parameter conversions
				result.append(strArg[i]+",");
			}	
			int ind = result.toString().lastIndexOf(",");
			result = result.replace(ind, ind + 1, "");		
			return result.toString();
		}
		else{
			LOG.error("Stage Code does not exist");
			CommonUtil.stopBatch();
		}
		return "''";		
	}	
}