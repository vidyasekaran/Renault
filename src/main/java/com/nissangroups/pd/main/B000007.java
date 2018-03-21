/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :O Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z011479(RNTBCI)               New Creation
 *
 */   
package com.nissangroups.pd.main;

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
import com.nissangroups.pd.util.PDConstants;


/**
 * This is the Main Method to Execute the batch B000007.
 *
 * @author z011479
 */
public class B000007 {

/** Constant LOG. */
//Initializing Log properties
private static final Log LOG = LogFactory.getLog(B000007.class.getName());

/** Variable parameter cd. */
private static String parameterCd;

/** Variable por cd. */
private static String porCd;

/** Variable update only flag. */
private static String updateOnlyFlag;

/**
 * Instantiates a new b000007.
 */
private  B000007(){}



	
    /**
     * Batch B000007 Execution Start from this Main Method.
     *
     * @param args the arguments
     */
    public static void main(String args[]){
    	
		//verifying arguments
		if(args==null || args.length!=3){
			LOG.error("Arguments [3] expected, [POR_CD, PARAMETER_CD, UPDATE_ONLY_FLAG], ");
			CommonUtil.stopBatch();
		}
		
		porCd= args[0];
		parameterCd= args[1];
		updateOnlyFlag= args[2];
    	
		run();
	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { "B000007/B000007_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000007");
		
		try {
			
			JobParameters parameter = new JobParametersBuilder().addString(PDConstants.BATCH_POR_CODE, porCd).addString(PDConstants.PARAMETER_CD, parameterCd).addString(PDConstants.UPDATE_ONLY_FLAG, updateOnlyFlag).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		}
		catch(Exception e){
			LOG.error("B000007",e);
		}
		
	}

}
