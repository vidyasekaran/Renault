/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST_DRG_I000103
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000103.main;

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
 *@author z016127
 */
public class I000103 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000103.class);
	
	/** Variable Constant args lenth */
	private static int ARGLENGTH = 0;

	/**
	 * Instantiates a new I000103.
	 */
	private I000103() {
	}
	/**
	 * Interface I000103 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		LOG.info("Interface 103 Arguments : " + Arrays.deepToString(args));
		if (args == null || args.length == ARGLENGTH  ) {
			LOG.error("Arguments expected, InterfaceFileId");
			return;
		}
		run(args);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * @param args
	 *
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = { PDConstants.INTERFACE_I103_CONFIG_PATH };
		// Interface File Id
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_103_ID);

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID,interfaceFileId).toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}
	}
}
