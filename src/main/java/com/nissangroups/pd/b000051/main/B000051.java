/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.nissangroups.pd.b000051.util.B000051Constants;
import static com.nissangroups.pd.b000051.util.B000051Constants.*;
import static com.nissangroups.pd.util.PDConstants.*;

/**
 * The Class B000051.
 *
 * @author z015060
 */
public class B000051 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000051.class.getName());

	/** Variable por_Cd  */
	private static String porCd;
	
	/** Variable reRunFlg */
	private static String reRunFlg;

	/**
	 * Instantiates a new B000051.
	 */
	private B000051() {
	}

	/**
	 * Batch B000051 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// verifying arguments
		if (args != null && (args.length == 2 )) {
			porCd = args[0];
			reRunFlg=args[1];
			run(porCd);
		}else {
			LOG.error(B000051Constants.ERROR_MESSAGE_ID1);
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @author z015060
	 * @param colmn
	 */
	private static void run(String porCd) {
		String[] batchConfig = { "B000051/B000051_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(BATCH_ID_B000051);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
			parameter.addString(POR_CD, porCd)
			.addString(RE_RUN_FLAG, reRunFlg);
			jobLauncher.run(job, parameter.toJobParameters());
		} catch (Exception e) {
			LOG.error(BATCH_ID_B000051, e);
		}
	}
}
