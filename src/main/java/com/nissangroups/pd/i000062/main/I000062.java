/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : I000062
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000062.main;

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
 * @author z015847
 *
 */
public class I000062 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000062.class);

	/** Constant ARG LENGTH. */
	private static final int ARG_LENGTH = 1;

	/**
	 * Instantiates a new I000062.
	 */
	private I000062() {
	}

	/**
	 * Interface I000062 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("IF062 Args : " + Arrays.deepToString(args));
		if (args == null || args.length < ARG_LENGTH) {
			LOG.error("Arguments expected: Interface_File_Id");
			return;
		}

		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	private static void run(String args[]) {
		String[] batchConfig = { PDConstants.INTERFACE_I62_CONFIG_PATH };
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.IF_ID_62);

		try {

			JobParameters jobParameters = new JobParametersBuilder().addString(
					IFConstants.INTERFACE_FILE_ID, interfaceFileId)
			// .addString(IFConstants.param_porCdLst, getPorcodes(args))
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}
	}
}
