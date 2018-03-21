/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000005
 * Module          :SP Spec Master				
 * Process Outline :Receive Exterior Color master Interface from DRG-VSM													
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
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
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is the Main method to run the interface I000005.
 *
 * @author z011479
 */
public class I000005 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000005.class.getName());

	/** Constant arg length */
	private static int ARGLENGTH = 1;
	
	/**
	 * Instantiates a new i000005.
	 */
	private I000005() {
	}
	
	/** Variable batch id. */
	private static String batchId;

	/**
	 * Batch I000005 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		
		//verifying arguments
		if(args==null || args.length!= ARGLENGTH){
			LOG.error(PDMessageConsants.M00107.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.INTERFACE_FILE_ID));
			CommonUtil.stopBatch();
		}
		batchId= args[0];
		run();
	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { "I000005/I000005_IF_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.INTERFACE_ID_I000005);

		try {
			// Job input paramters should passed here.
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.INTERFACE_FILE_ID, batchId)
					.addString(PDConstants.INTERFACE_STATUS,
							PDConstants.INTERFACE_UNPROCESSED_STATUS)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
		} catch (Exception e) {
			LOG.error("Exception", e);
		}

	}

}
