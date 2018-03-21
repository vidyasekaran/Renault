/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.main;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000047.util.B000047Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 *
 * @author z016127
 */
public class B000047 {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory.getLog(B000047.class.getName());
	
	/** Variable por cd. */
	private static String porCd;
	
	/** Variable context. */
	private static ClassPathXmlApplicationContext context;
	
	/**
	 * Instantiates a new B000047.
	 */
	private B000047() {

	}

	/**
	 * Batch B000047 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		
		// verifying arguments
		if (args != null && args.length == B000047Constants.ARGSLENGTH) {
			porCd = args[0];// POR CD
			run();
		}
		
		if(args==null || args.length != B000047Constants.ARGSLENGTH){
			LOG.error("Arguments [1] expected, [POR CD] ");
	        CommonUtil.stopBatch();
		}
	}

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z016127
	 */
	private static void run() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		String[] batchConfig = { B000047Constants.BATCH_47_CONFIG_PATH };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000047Constants.BATCH_47_ID);
		
		try {
			JobParameters parameter = new JobParametersBuilder()
			.addString(PDConstants.PRMTR_PORCD, porCd)
			.toJobParameters();
			LOG.info("Job parameters are : "+parameter.toString());
			
			JobExecution execution = jobLauncher.run(job, parameter);
			LOG.info(execution.getExitStatus());
			
		} catch (Exception e) {
			LOG.error(B000047Constants.BATCH_ID_B000047, e);
		}
        LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
}