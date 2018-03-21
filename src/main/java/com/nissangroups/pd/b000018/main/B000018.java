/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :validate the importer Monthly production order Interface data which received from NSC system.
 *					validate Master Spec Check, OCF validation, Breach Check.
 *					This batch process is used to auto confirm the Draft Order or Final Order
 *					Error Details will created as three report and as well as it create source data for MONTHLY ORDER ERROR INETRFACE.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.main;

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
 * The Class B000018.
 * 
 * @author z001870
 */
public class B000018 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000018.class);

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;

	/** Variable por code */
	private static String porCd;

	/** Variable file id */
	private static String fileId;

	/** Variable single Record Flag */
	private static String snglRcrdFlg;

	/** Variable production order stage code */
	private static String prdOrdrStgCd;
	
	private B000018(){
		
	}

	/**
	 * Batch B000018 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments arg0 - File ID arg1 - POR CD arg2 - production
	 *            order stage cd arg3 - single record flag
	 */
	public static void main(String[] args) {

		// verifying arguments
		
		if (args == null || (args.length != 3 && args.length != 4) ) {
			LOG.error("Arguments [4] expected, [FILE ID,POR_CD, PRODUCTION ORDER STAGE CD, SINGLE RECORD FLAG(Optional - TRUE)] ");
			CommonUtil.stopBatch();
		}

		fileId = args[0];
		porCd = args[1];
		prdOrdrStgCd = args[2];

		if (args[3] != null) {
			snglRcrdFlg = args[3];
		} else {
			snglRcrdFlg = "false";
		}

		run();
	}

	/**
	 * To Execute the Batch.
	 */
	private static void run() {
		// To Fetch the XML file
		String[] batchConfig = { "B000018/B000018_Batch_Config.xml" };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000018");

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_PORCD, porCd)
					.addString(PDConstants.FILE_ID, fileId)
					.addString(PDConstants.PRMTR_PRD_ORDR_STGE_CD,
							prdOrdrStgCd)
					.addString(PDConstants.PRMTR_SNGL_RCRD_FLG, snglRcrdFlg)
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
