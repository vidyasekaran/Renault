/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000050.main;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000050.util.B000050CommonUtil;
import com.nissangroups.pd.b000050.util.B000050Constants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 *
 * @author z014433
 */
public class B000050 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000050.class.getName());

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;

	/** Variable por cd. */
	private static String porCd;
	
	/** Variable process type. */
	private static String prcsTyp;
	
	/** Variable stage cd. */
	private static String stgCd;

	/**
	 * Instantiates a new B000050.
	 */
	private B000050() {
	}

	/**
	 * Batch B000050 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		
		boolean batchIpValRes = false;
		// verifying arguments
		if (args != null && args.length == 3) {
			batchIpValRes = validateBatchInputArgs(args);
			
			if(!batchIpValRes){
			porCd = args[0];
			prcsTyp = args[1];
			stgCd= args[2];
			
			run();
			
			}
		}
		
		if(args==null || args.length!=3){
			LOG.error("Arguments [3] expected, [POR CD, PROCESS TYPE, STAGE CD] ");
	        B000050CommonUtil.stopBatch();
		}
	}
	
	
	/**
	 * @param args
	 * @return whether batch inputs are valid or not
	 */
	private static boolean validateBatchInputArgs(String[] args) {
		
		boolean fnlVal = false;
		boolean valRes = false;
		
		valRes = validatePrcsTypDtsl(args);
		
		if(valRes && (args[1].equalsIgnoreCase(PDConstants.MONTHLY)))
			fnlVal = validateOrdrDtls(args);
		
		return fnlVal;
	}
	
	/**
	 * @param args
	 * @return whether the process type is either "Monthly" or "Weekly"
	 */
	private static boolean validatePrcsTypDtsl(String[] args) {
		
		boolean isVlsPrcsTyp = (args[1].equalsIgnoreCase(PDConstants.MONTHLY)||args[1].equalsIgnoreCase(PDConstants.WEEKLY)) ?  true : false;
		
		if(!isVlsPrcsTyp){
			LOG.error("Argument  - Process Type should be 'Monthly' or 'Weekly' ");
        	
        	B000050CommonUtil.stopBatch();
		}
		
		return isVlsPrcsTyp;
	}
	
	/**
	 * @param args
	 * @return whether the batch input stage code is valid or not
	 */
	private static boolean validateOrdrDtls(String[] args) {
		
		if((!isDraftOrdr(args[2])) && (!isFinalOrdr(args[2])) && (!isStgOpnCls(args[2])))
		 {
			 LOG.error("Argument  - STAGE CODE should be 'D1, D2, F1, F2, SO, SC'");
        	
        	B000050CommonUtil.stopBatch();
		 }
		return false;
	}
	

	public static boolean isDraftOrdr(String stgCd)
	{
		return (stgCd.equals(PDConstants.DRAFT_D1)||stgCd.equals(PDConstants.DRAFT_D2)) ?  true : false;
	}

	public static boolean isFinalOrdr(String stgCd)
	{
		return (stgCd.equals(PDConstants.FINAL_F1)||stgCd.equals(PDConstants.FINAL_F2)) ?  true : false;
	}
	
	public static boolean isStgOpnCls(String stgCd)
	{
		return (stgCd.equals(PDConstants.SO)||stgCd.equals(PDConstants.SC)) ?  true : false; 
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z014433
	 */
	private static void run() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		String[] batchConfig = { B000050Constants.BATCH_50_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000050Constants.BATCH_50_ID);
		
		try {
			
			JobParameters parameter = new JobParametersBuilder()
			.addString(PDConstants.PRMTR_PORCD, porCd)
			.addString(B000050Constants.PRMTR_PRCS_TYP, prcsTyp)
			.addString(B000050Constants.PRMTR_STAGE_CD, stgCd)
			.toJobParameters();
			LOG.info("Job parameters are : "+parameter.toString());
			
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
			
		} catch (Exception e) {
			LOG.error(B000050Constants.BATCH_ID_B000050, e);
		}
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
}
