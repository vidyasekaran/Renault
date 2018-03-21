/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :S SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z013865(RNTBCI)               New Creation
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

import com.nissangroups.pd.util.PDConstants;


/**
 * Main class for Batch B000003.
 *
 * @author z011479
 */
public class B000003 {
	
	/**
	 * Instantiates a new b000003.
	 */
	private B000003() {

	}
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000003.class.getName());
	
	/** Variable por cd. */
	private static String porCd = null;
	
	/** Variable update flag. */
	private static String updateFlag = null;

	

	
	
	/**
	 * Batch B000003 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		porCd = args[0];
		updateFlag = args[1];
		run();

	}

	/**
	 * Run.
	 */
	private static void run() {
		String[] batchConfig = { "B000003/B000003_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000003");

		try {

			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_POR, porCd)
					.addString(PDConstants.PRMTR_UPDATE_FLAG, updateFlag)
					.addString(PDConstants.PRMTR_REPORT_FILENAME, "B000003Report.tsv")
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
