/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.main;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

import static com.nissangroups.pd.b000008.util.B000008Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

/**
 * The Class B000008.
 *
 * @author z015060
 */
public class B000008 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000008.class.getName());

	/** Variable por_Cd . */
	private static String porCdInput;

	/** Variable Update Only Flag . */
	private static String updateOnlyFlag;

	/** Variable Production Stage Code . */
	private static String productionStageCd;

	/** Variable Overlap MS Qty Flag . */
	private static String overlapMsQtyFlag;

	/** Variable Stage code . */
	private static String stageCode;

	/**
	 * Instantiates a new B000008.
	 */
	private B000008() {
	}

	/**
	 * Batch B000008 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && (args.length == 3 || args.length == 4)) {
			porCdInput = args[0];
			updateOnlyFlag = args[1];
			overlapMsQtyFlag = args[2];
			if (args.length == 3) {
				if (updateOnlyFlag.equals(N)) {
					LOG.error(INPUT_PARAM_FAILURE_1);
					CommonUtil.stopBatch();
				}
				productionStageCd = CONSTANT_ZERO;
				stageCode = STAGE_MESSAGE_3;
			} else {
				productionStageCd = args[3];
				stageCode = CommonUtil.getInputParamB8(productionStageCd);
			}

		} else {
			LOG.error(INPUT_PARAM_FAILURE_5);
			CommonUtil.stopBatch();
		}
			run(porCdInput);
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @author z015060
	 * @param colmn
	 */
	private static void run(String porCd) {
		String[] batchConfig = { "B000008/B000008_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(BATCH_ID_B000008);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter.addString(POR_CD, porCd)
					.addString(UPDATE_ONLY_FLAG, updateOnlyFlag)
					.addString(OVERLAP_MS_QTY_FLAG, overlapMsQtyFlag)
					.addString(PRMTR_PRODUCTION_STAGE_CODE, productionStageCd)
					.addString(STAGE_CODE, stageCode).toJobParameters();
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(BATCH_ID_B000008, e);
		}
	}
}
