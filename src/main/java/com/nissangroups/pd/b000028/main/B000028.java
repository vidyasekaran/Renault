/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;

import static com.nissangroups.pd.b000028.util.B000028Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

/**
 * The Class B000028.
 *
 * @author z015060
 */
public class B000028 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000028.class.getName());

	/** Variable por_Cd . */
	private static String porCd;

	/** Variable Stage code . */
	private static String stageCode;

	/**
	 * Instantiates a new B000028.
	 */
	private B000028() {
	}

	/**
	 * Batch B000028 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if(args.length ==2){
		porCd=args[0];
		stageCode=args[1];
		run();
		}
		else {
			LOG.error(INPUT_PARAM_FAILURE_1);
			CommonUtil.stopBatch();
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @author z015060
	 * @param colmn
	 */
	private static void run() {
		String[] batchConfig = { "B000028/B000028_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(BATCH_B000028);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter.addString(POR_CD, porCd).addString(STAGE_CODE, stageCode)
					.toJobParameters();
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(BATCH_ID_B000028, e);
		}
	}
}
