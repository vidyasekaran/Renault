/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is the main batch kick off program.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000059.util.B000059Constants;

public class B000059 {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory.getLog(B000059.class.getName());

	/** Variable interfaceFileID */
	private static String interfaceFileID;

	/**
	 * Instantiates a new B000059.
	 */
	private B000059() {

	}

	/**
	 * Batch B000059 Execution Start from this Main Method.
	 * 
	 * @param args
	 *the arguments
	 */
	public static void main(String[] args) {
		// verifying arguments
		if (args != null && (args.length == 1)) {
			interfaceFileID = args[0];
		} else {
			LOG.error("Interface File ID Argument is expected");

			return;
		}

		run();

	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { "B000059/B000059_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000059");

		try {
			JobParameters parameter = new JobParametersBuilder().addString(
					B000059Constants.INTERFACE_FILE_ID, interfaceFileID)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = B000059Constants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error(B000059Constants.BATCH_ID_B000059, e);
		}
	}
}