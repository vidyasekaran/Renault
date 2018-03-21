/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000104
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Send the OSEI Spec Master Data to SAP 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-01-2016  	  z014029(RNTBCI)               New Creation
 */
package com.nissangroups.pd.i000104.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class I000104.
 * 
 * @author z014029
 */

public class I000104 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000104.class);
	
	/** Variable Constant args lenth */
	private static int ARGLENGTH = 0;

	/**
	 * Instantiates a new I000011.
	 */
	private I000104() {
	}

	/**
	 * Interface I000104 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		if (args == null || args.length == ARGLENGTH) {
			LOG.error("Arguments expected:- Interface File ID");
			return;
		}
		run(args);
	}

	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I104_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_104_ID);

		try {

			JobParameters parameter = new JobParametersBuilder().addString(
					IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);
		}
	}
}