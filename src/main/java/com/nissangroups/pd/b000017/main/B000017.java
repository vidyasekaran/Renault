/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000017.util.Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000017 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000017.class);
	
	/** Variable por cd. */
	private static String porCd = null;
	
	/** Variable update flag. */
	private static String updateFlag = null;
	
	/** Variable production order stage code */
	private static String prodOrderStageCode = null;

	private B000017() {
	}
	
	/**
	 * Batch B000017 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!=3){
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.POR_CD));
			LOG.error("Arguments [3] expected, [POR_CD,UPDATE_ONLY_FLAG,PROD_ORDER_STAGE_CD]");
			CommonUtil.stopBatch();
		}
		porCd = args[0];
		updateFlag = args[1];
		prodOrderStageCode = args[2];
		run();
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(){
		String[] batchConfig = { Constants.BATCH_CONFIG };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(Constants.BEANVAL);

		try {

			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_POR, porCd)
					.addString(PDConstants.UPDATE_ONLY_FLAG, updateFlag)
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
