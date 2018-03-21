/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000067
 * Module          :MONTHLY ORDERING
 * Process Outline :This Batch Proces is used to create Offline request report and register Offline due date request data to A0 ETA Parameter Trn
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 26-NOV-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000067.main;

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
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class B000067.
 * 
 * @author z001870
 */
public class B000067 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000067.class);

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;

	/** Variable por code */
	private static String porCd;

	/** Variable targetMnth */
	private static String targetMnth;

	/**
	 * Batch B000067 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments arg0 - POR CD arg1 - TARGET MONTH
	 */
	public static void main(String[] args) {

		// verifying arguments
		
		if (args == null || (args.length != 2 ) ) {
			LOG.error("Arguments [2] expected, [POR_CD, TARGET MONTH (Horizon) ]");
			CommonUtil.stopBatch();
		}

		porCd = args[0];
		targetMnth = args[1];
		run();
	}

	/**
	 * To Execute the Batch.
	 */
	private static void run() {
		// To Fetch the XML file
		String[] batchConfig = { "B000067/B000067_Batch_Config.xml" };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000067");

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_PORCD, porCd)
					.addString(PDConstants.PRMTR_TARGET_MNTH, targetMnth)
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
