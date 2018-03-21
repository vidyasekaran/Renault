/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000030
 * Module          : Ordering					
 * Process Outline :Create Monthly Order Take Base Period
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000030.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

import static com.nissangroups.pd.b000030.util.B000030Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

/**
 * The Class B000008.
 *
 * @author z015060
 */
public class B000030 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000030.class.getName());

	/** Variable porCd . */
	private static String porCd;

	/**
	 * Instantiates a new B000030.
	 */
	private B000030() {
	}

	/**
	 * Batch B000030 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && (args.length == 1 )) {
			porCd = args[0];
			run();
		} else {
			LOG.error(INPUT_PARAM_FAILURE_5);
			CommonUtil.stopBatch();
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * @author z015060
	 * @param colmn
	 */
	private static void run() {
		String[] batchConfig = { "B000030/B000030_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(BATCH_ID_B000030);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter
					.addString(PDConstants.BATCH_POR_CODE, porCd)
					.addString(PRMTR_STATUS_STAGE_CD, PRMTR_STATUS_STAGE_CD_VAL)
					.toJobParameters();
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(BATCH_ID_B000030, e);
		}
	}
}
