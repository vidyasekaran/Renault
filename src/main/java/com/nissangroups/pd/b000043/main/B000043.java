/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000043
 * Module          :Ordering		
 * Process Outline :Create_Weekly_Order_Take_Base_Period																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000043.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 *
 * @author z015060
 */
public class B000043 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000043.class.getName());

	/** Variable por cd. */
	private static String POR_CD;


	/**
	 * Instantiates a new B000043.
	 */
	private B000043() {
	}

	/**
	 * Batch B000043 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && args.length == 1) {
			POR_CD = args[0];
		}
		else {
			LOG.error("POR Argument is expected");
			CommonUtil.stopBatch();
		}
		run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z015060
	 */
	private static void run() {
		String[] batchConfig = { "B000043/B000043_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.B000043_ID);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter.addString(PDConstants.POR_CD, POR_CD);
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(PDConstants.B000043_ID, e);
		}
	}
}
