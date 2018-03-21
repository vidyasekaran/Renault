/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000011.util.B000011Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

import static com.nissangroups.pd.b000011.util.B000011Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

/**
 * The Class B000011.
 *
 * @author z015060
 */
public class B000011 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000011.class.getName());

	/** Variable porCd . */
	private static String porCd;

	/** Variable prcsOlyFlg */
	private static String prcsOlyFlg;
	
	/** Variable stageCode */
	private static String stageCode;

	/**
	 * Instantiates a new B000011.
	 */
	private B000011() {
	}

	/**
	 * Batch B000011 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && args.length == 2) {
			porCd = args[0];
			prcsOlyFlg=args[1];
			if (!prcsOlyFlg.equals(N) && !prcsOlyFlg.equals(Y)) {
				LOG.error(INPUT_PARAM_FAILURE_2);
				CommonUtil.stopBatch();
			}
			stageCode=STAGE_MESSAGE_1;
			
		} else {
			LOG.error(INPUT_PARAM_FAILURE_1);
			CommonUtil.stopBatch();
		}
			run();
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * @author z015060
	 */
	private static void run() {
		String[] batchConfig = { B000011Constants.B000011_BATCH_CONFIG };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(BATCH_ID_B000011);
		
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter
					.addString(POR_CD, porCd)
					.addString(PROCESS_ONLY_FLAG, prcsOlyFlg)
					.addString(STAGE_CODE, stageCode)
					.toJobParameters();
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(BATCH_ID_B000011, e);
		}
	}
}