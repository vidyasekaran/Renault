/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000035.main;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000035.util.B000035CommonUtil;
import com.nissangroups.pd.b000035.util.B000035Constants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * Job Execution Main Class.
 *
 * @author z014433
 */
public class B000035 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000035.class.getName());

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;

	/** Variable por cd. */
	private static String porCd;

	/**
	 * Instantiates a new B000035.
	 */
	private B000035() {
	}

	/**
	 * Batch B000035 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// verifying arguments
		if (args != null && args.length == 1) {
			porCd = args[0];
		} else
		{
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.POR_CD));
			B000035CommonUtil.stopBatch();
		}
		run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 */
	private static void run() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		String[] batchConfig = { B000035Constants.BATCH_35_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000035Constants.BATCH_35_ID);
		
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			
			parameter.addString(PDConstants.PRMTR_PORCD, porCd).toJobParameters();
			LOG.info("Job parameters are : "+parameter.toString());
			
			JobExecution execution = jobLauncher.run(job, parameter.toJobParameters());
			LOG.info(execution.getExitStatus());
			
		} catch (Exception e) {
			LOG.error(B000035Constants.BATCH_ID_B000035, e);
		}
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
}
