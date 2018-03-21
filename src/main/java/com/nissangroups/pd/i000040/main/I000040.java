/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000040
 * Module                 : OR Ordering		
 * Process Outline     	  : Send A0 ETA Designated parameter to PCS															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000040.main;

import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class
 * 
 * @author z014676
 * 
 */
public class I000040 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000040.class);

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;
	
	/** Constant arg length */
	private static int ARG_LENGTH = 3;

	/** Variable por code */
	private static String porCd;

	/** Variable interface id */
	private static String intrfaceId;

	/** Variable File name */
	private static String fileName;

	/**
	 * Instantiates a new I000044.
	 */
	private I000040() {

	}

	/**
	 * Interface I000044 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		if (args == null || (args.length != ARG_LENGTH)) {
			LOG.error("Arguments [3] expected, [FILE_NAME,POR_CD,Interface_ID]");
			CommonUtil.stopBatch();
		}

		fileName = args[0];      //File name
		porCd = args[1];        // POR CD
		intrfaceId = args[2];  //  Interface Id

		run();

	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 */
	private static void run() {

		LOG.info("interface ID :" + intrfaceId);
		LOG.info("por :" + porCd);
		LOG.info("fileName :" + fileName);

		String[] batchConfig = { PDConstants.INTERFACE_I40_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("I000040");

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_PORCD, porCd)
					.addString(IFConstants.INTERFACE_FILE_ID, intrfaceId)
					.addString(IFConstants.FILE_NAME, fileName)

					.toJobParameters();

			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = "Exit Status :";
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);
		} catch (Exception e) {
			LOG.info(Level.SEVERE, e);
		} finally {
			if (context != null)
				context.close();
		}

		LOG.info("Batch Completed on " + new Date());
	}

}
