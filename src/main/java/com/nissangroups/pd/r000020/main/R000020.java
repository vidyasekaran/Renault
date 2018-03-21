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

package com.nissangroups.pd.r000020.main;

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
public class R000020 {

	public R000020() {
	}

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(R000020.class);

	/** Variable context. */
	private static ClassPathXmlApplicationContext context;

	/** Variable por code */
	private static String porCd;

	/** Variable ocf region code */
	private static String ocfRgnCd;

	/** Variable Stage Code */
	private static String stgCd;

	/** Variable tableName */
	private static String tableName;

	/**
	 * Rep R000020 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments arg0 - File ID arg1 - POR CD arg2 - production
	 *            order stage cd arg3 - single record flag
	 */
	public static void main(String[] args) {

		// verifying arguments
		tableName = "";
		if (args == null || (args.length < 2)) {
			LOG.error("Arguments [1] expected, [POR_CD, STAGE_CODE,OCF RGN CD (Optional)] ");
			CommonUtil.stopBatch();
		}

		porCd = args[0];
		stgCd = args[1];
		if (args.length > 2) {
			ocfRgnCd = args[2];

		}
		run();
	}

	/**
	 * To Execute the Batch.
	 */
	private static void run() {
		// To Fetch the XML file
		String[] batchConfig = { "R000020/R000020_Batch_Config.xml" };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("R000020");
		tableName = "trn_mnth_prod_shdl_if";

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_PORCD, porCd)
					.addString(PDConstants.PRMTR_OCFRGNCD, ocfRgnCd)
					.addString(PDConstants.PRMTR_STAGE_CD, stgCd)
					.addString(PDConstants.MASTER_TABLE, tableName)
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
