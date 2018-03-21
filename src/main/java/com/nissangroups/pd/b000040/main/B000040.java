/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000040.util.B000040Constants;

public class B000040 {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory.getLog(B000040.class.getName());
	
	/**
	 * Instantiates a new B000040.
	 */
	private B000040() {
	}

	/** Variable interfaceFileID */
	private static String porCd;

	/**
	 * Batch B000059 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		// verifying arguments
		if (args != null && (args.length == 1)) {
			porCd = args[0];
		} else {
			LOG.error("POR_CD Argument is expected");			
		}

		run();
	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { "B000040/B000040_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000040");

		try {
			JobParameters parameter = new JobParametersBuilder().addString(B000040Constants.POR_CD, porCd)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = B000040Constants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error(B000040Constants.BATCH_ID_B000040, e);
		}
	}
}