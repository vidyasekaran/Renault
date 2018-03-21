/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000047
 * Module          :SP SPEC MASTER
 *  
 * Process Outline :Receive Week No Calendar Interface from Plant. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-01-2016  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000047.main;

import java.util.Arrays;

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
 * Job Execution Main Class.
 * 
 * @author z014029
 */
public class I000047 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000047.class);
	
	/** Variable Constant args lenth */
	private static int ARGLENGTH = 2;

	/**
	 * Interface I000047 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("Interface I000047 Arguments : " + Arrays.deepToString(args));
		if (args == null || args.length < ARGLENGTH) {
			LOG.error("Arguments expected: Interface_File_Id  POR_CD");
			return;
		}

		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	@SuppressWarnings("resource")
	private static void run(String args[]) {
		String[] batchConfig = { PDConstants.INTERFACE_I47_CONFIG_PATH };
		String interfaceFileId = args[0];
		String porCd = args[1];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.IF_ID_47);

		try {

			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.POR_CD, porCd).toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();

			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);
		}
	}
}