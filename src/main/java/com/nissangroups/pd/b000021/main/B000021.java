/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-09-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.b000021.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000021.util.B000021CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * This is the Main Method to Execute the batch B000021.
 *
 * @author z014433
 */
public class B000021 {

/** Constant LOG. */
//Initializing Log properties
private static final Log LOG = LogFactory.getLog(B000021.class.getName());

/** Variable context. */
private static ClassPathXmlApplicationContext context;

/** Variable por cd. */
private static String porCd;

/** Variable update only flag. */
private static String updtOnlyFlg;

/** Variable system lock flag. */
private static String systmLckFlg;

/** Variable stage cd. */
private static String stgCd;

/** Variable stage status cd. */
private static String stgStsCd;

/** Variable stage update flag. */
private static String stgUpdtFlg;

/**
 * Instantiates a new b000021.
 */
private  B000021()
{
	}

    /**
     * Batch B000021 Execution Start from this Main Method.
     *
     * @param args the arguments
     */
public static void main(String[] args) {
	boolean batchIpValRes = false;

	// verifying arguments
	if (args != null && args.length == 6) {
		batchIpValRes = validateBatchInputPrms(args);
		if(batchIpValRes){
		porCd= args[0];
		updtOnlyFlg= args[1];
		systmLckFlg= args[2];
		stgCd= args[3];
		stgStsCd= args[4];
		stgUpdtFlg= args[5];
		
		run();
		
		}
	} 
	
	if(args==null || args.length!=6){
		LOG.error("Arguments [6] expected, [POR CD, UPDATE ONLY FLAG, SYSTEM LOCK FLAG, STAGE CD, STAGE STATUS CD, STAGE UPDATE FLAG] ");
        B000021CommonUtil.stopBatch();
	}
	
}

	/**
	 * @param args
	 * @return whether batch inputs are valid or not
	 */
	private static boolean validateBatchInputPrms(String[] args) {
		
		boolean valRes = false;
		
		valRes = validateFlagDtsl(args);
		
		if(!valRes)
			valRes = validateOrdrDtls(args);
		
		 if(!isExpectedsStgStsCd(args[4]))
		 {
			 LOG.error("Argument  - STAGE STATUS CODE should be either 'O' or 'C'");
			 B000021CommonUtil.stopBatch();
		 }
		 
		return true;
	}
	
	/**
	 * @param args
	 * @return whether the batch input stage code is valid or not
	 */
	private static boolean validateOrdrDtls(String[] args) {
		
		if((!isDraftOrdr(args[3])) && (!isFinalOrdr(args[3])))
		 {
			 LOG.error("Argument  - STAGE CODE should be 'D1, D2, F1, F2'");
			 String[] messageParams = {PDConstants.BATCH_21_ID};
	            
        	B000021CommonUtil.logMessage(PDMessageConsants.M00201, PDConstants.P0001, messageParams);
        	
        	B000021CommonUtil.stopBatch();
		 }
		return false;
	}

	/**
	 * @param args
	 * @return whether the flag values is either "Y" or "N"
	 */
	private static boolean validateFlagDtsl(String[] args) {
		
		if(!isExpectedFlgVal(args[1])||!isExpectedFlgVal(args[2])||!isExpectedFlgVal(args[5]))
		{
			LOG.error("Arguments  - UPDATE ONLY FLAG, SYSTEM LOCK FLAG, STAGE UPDATE FLAG should be either 'Y' or 'N'");
			B000021CommonUtil.stopBatch();
		}
		return false;
	}

	private static boolean isExpectedFlgVal(String inputFlg)
	{
		return (inputFlg.equalsIgnoreCase(PDConstants.YES)||inputFlg.equalsIgnoreCase(PDConstants.NO)) ?  true : false;
	}
	
	public static boolean isDraftOrdr(String stgCd)
	{
		return (stgCd.equals(PDConstants.DRAFT_D1)||stgCd.equals(PDConstants.DRAFT_D2)) ?  true : false;
	}

	public static boolean isFinalOrdr(String stgCd)
	{
		return (stgCd.equals(PDConstants.FINAL_F1)||stgCd.equals(PDConstants.FINAL_F2)) ?  true : false;
	}
	
	private static boolean isExpectedsStgStsCd(String stgStsCd)
	{
		return (stgStsCd.equals(PDConstants.CONSTANT_O)||stgStsCd.equals(PDConstants.CF_CONSTANT_C)) ?  true : false;
	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { PDConstants.BATCH_21_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(PDConstants.BATCH_21_ID);
		
		try {
			
			JobParameters parameter = new JobParametersBuilder()
			.addString(PDConstants.PRMTR_PORCD, porCd)
			.addString(PDConstants.PRMTR_UPDATE_FLAG, updtOnlyFlg)
			.addString(PDConstants.PRMTR_SYSTEM_LOCK_FLAG, systmLckFlg)
			.addString(PDConstants.PRMTR_STAGE_CD, stgCd)
			.addString(PDConstants.PRMTR_STAGE_STATUS_CD, stgStsCd)
			.addString(PDConstants.PRMTR_STG_UPDT_FLAG, stgUpdtFlg)
			.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		}
		catch(Exception e){
			LOG.error(PDConstants.BATCH_21_ID,e);
		}finally {
			if (context != null)
				context.close();
		}
		
	}

}
