/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000031.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.nissangroups.pd.b000031.util.B000031Constants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * Job Execution Main Class.
 *
 * @author z015399
 */

public class B000031 {
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000031.class);
	
	/** Variable por cd. */
	private static String porCd = null;
	
	/** Variable OCF region code */
	private static String ocfRegionCode = null;

	private B000031(){
		
	}
	
	/**
	 * Batch B000031 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!=2){
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.POR_CD));
			LOG.error("Arguments [2] expected, [POR_CD,OCF_REGION_CD]");
            return;
		}
		porCd = args[0];
		ocfRegionCode = args[1];
		run();
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(){
		String[] batchConfig = {B000031Constants.BATCH_CFG };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000031Constants.BEANVAL);

		try {

			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_POR, porCd)
					.addString(PDConstants.PRMTR_OCFRGNCD, ocfRegionCode)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus =PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}
	}
}
