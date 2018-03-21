/*
 * System Name       : Post Dragon 
 * Sub system Name : Interface
 * Function ID            : PST-DRG-I000030
 * Module                  : Ordering		
 * Process Outline     : Receive Monthly Order  Interface from NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-10-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.main;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * Job Execution Main Class.
 *
 * @author z014433
 */
public class I000030 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000030.class.getName());

	/** Variable interface id. */
	private static String INTERFACE_ID;
	
	/** Variable Constant args lenth */
	private static int ARGLENGTH = 1;

	/**
	 * Instantiates a new I000030.
	 */
	private I000030() {
	}

	/**
	 * Batch I000030 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// verifying arguments
		if (args != null && args.length == ARGLENGTH) {
				INTERFACE_ID = args[0];
		} else
		{
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.INTERFACE_FILE_ID));
			CommonUtil.stopBatch();
		}
		run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z014433
	 */
	private static void run() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String[] batchConfig = { PDConstants.I000030_CONFIG_FILE };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(PDConstants.INTERFACE_ID_I000030);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter
					.addString(PDConstants.INTERFACE_FILE_ID, INTERFACE_ID)
					.addString(PDConstants.INTERFACE_STATUS,
							PDConstants.INTERFACE_UNPROCESSED_STATUS)
					.toJobParameters();
			LOG.info("Job parameters are : "+parameter.toString());
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(PDConstants.INTERFACE_ID_I000030, e);
		}
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
}
