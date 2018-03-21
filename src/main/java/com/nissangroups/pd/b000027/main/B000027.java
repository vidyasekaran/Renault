/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.b000027.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.util.PDConstants;

/**
 * This is the Main Method to Execute the batch B000027.
 *
 * @author z014433
 */
public class B000027 {

/** Constant LOG. */
//Initializing Log properties
private static final Log LOG = LogFactory.getLog(B000027.class.getName());

/** Variable context. */
private static ClassPathXmlApplicationContext context;

/** Variable por cd. */
private static String porCd;

/** Variable stage cd. */
private static String stgCd;

/** Variable Order quantity flag */
private static String ordrQtyFlg;

/**
 * Instantiates a new b000027.
 */
private  B000027()
{
	}

    /**
     * Batch B000027 Execution Start from this Main Method.
     *
     * @param args the arguments
     */
public static void main(String[] args) {
	
	boolean batchIpValRes = false;

	// verifying arguments
	if (args != null && args.length == 3) {
		batchIpValRes = validateBatchInputPrms(args);
		if(batchIpValRes){
		porCd= args[0];
		stgCd= args[1];
		ordrQtyFlg= args[2];
		
		run();
		
		}
	} 
	
	if(args==null || args.length!=3){
		LOG.error("Arguments [3] expected, [POR CD, STAGE CD, ORDER QUANTITY FLAG] ");
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
			valRes = validateStgCdDtls(args);
		
		return true;
	}
	
	/**
	 * @param args
	 * @return whether the batch input stage code is valid or not
	 */
	private static boolean validateStgCdDtls(String[] args) {
		
		if(!isFinalOrdr(args[1]))
		 {
			 LOG.error("Argument  - STAGE CODE should be 'F1, F2'");
        	
        	B000027CommonUtil.stopBatch();
		 }
		return false;
	}

	/**
	 * @param args
	 * @return whether the flag values is either "Y" or "N"
	 */
	private static boolean validateFlagDtsl(String[] args) {
		
		if(!isExpectedFlgVal(args[2]))
		{
			LOG.error("Argument  - ORDER QUANTITY FLAG should be either 'Y' or 'N'");
			B000027CommonUtil.stopBatch(); 
		}
		return false;
	}

	/**
	 * @param inputFlg
	 * @return whether order quantity flag is Yes or No
	 */
	private static boolean isExpectedFlgVal(String inputFlg)
	{
		return (inputFlg.equalsIgnoreCase(PDConstants.YES)||inputFlg.equalsIgnoreCase(PDConstants.NO)) ?  true : false;
	}
	
	/**
	 * @param stgCd
	 * @return whether stage code value is either F1 or F2
	 */
	public static boolean isFinalOrdr(String stgCd)
	{
		return (stgCd.equals(PDConstants.FINAL_F1)||stgCd.equals(PDConstants.FINAL_F2)) ?  true : false;
	}
	
	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { B000027Constants.BATCH_27_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000027Constants.BATCH_27_ID);
		
		try {
			
			JobParameters parameter = new JobParametersBuilder()
			.addString(PDConstants.PRMTR_PORCD, porCd)
			.addString(PDConstants.PRMTR_STAGE_CD, stgCd)
			.addString(PDConstants.PRMTR_ORDR_QTY, ordrQtyFlg)
			.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		}
		catch(Exception e){
			LOG.error(B000027Constants.BATCH_27_ID,e);
		}finally {
			if (context != null)
				context.close();
		}
		
	}

}
