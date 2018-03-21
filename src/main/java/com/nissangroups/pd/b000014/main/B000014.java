/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000014.util.B000014Constants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * Job Execution Main Class.
 *
 * @author z015399
 */

public class B000014 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000014.class);
	
	/** Variable por cd. */
	private static String porCd = null;
	
	/** Variable production order stage code */
	private static String prodOrderStageCode = null;
	
	private B000014() {
	}

	/**
	 * Batch B000014 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!=2){
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.POR_CD));
			LOG.error("Arguments [2] expected, [POR_CD,PROD_ORDER_STAGE_CD]");
            return;
		}
		porCd = args[0];
		prodOrderStageCode = args[1];
		run();
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(){
		String[] batchConfig = { B000014Constants.CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000014Constants.B000014);

		try {

			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_POR, porCd)
					.addString(PDConstants.PRMTR_PRODUCTION_STAGE_CODE, prodOrderStageCode)
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
