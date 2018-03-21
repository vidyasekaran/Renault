package com.nissangroups.pd.b000053.main;



import static com.nissangroups.pd.util.PDConstants.POR_CD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.nissangroups.pd.util.CommonUtil;
import static com.nissangroups.pd.b000053.util.B000053Constants.*;
/**
 * @author z013931
 * 
 */
public class B000053Main {

	
	private static final Log LOG = LogFactory.getLog(B000053Main.class);
	
	
	
	/** variable porCd . */
	private static String porCd;

	/** variable ORDR_TAKE_BASE_MNTH . */
	private static String ordrTakeBaseMonth;

	/** Variable ORDR_TAKE_BASE_WK_NO . */
	private static String ordrTakeBaseWeekNo;

	/** variable BATCH_ID . */
	private static String batchId;

	/** variable SEQ_ID . */
	private static String sequenceId;
	/**
	 * Instantiates a new B000053.
	 */
	private B000053Main() {

	}
	/** Constant LOG. */
	
	/**
	 * Batch B000053 Execution Start from this Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		if(args.length==5)
		{
			
			porCd=args[0];
			ordrTakeBaseMonth=args[1];
			ordrTakeBaseWeekNo=args[2];
			batchId=args[3];
			sequenceId=args[4];
			run();
		}
		else
		{
			LOG.error(INPUT_PARAM_FAILURE_1);
			CommonUtil.stopBatch();
			
		}
		
		
	}
	
	
	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 * 
	 * @author z013931
	 * @param colmn
	 */
	private static void run() {
		
		String[] batchConfig = { "B000053/B000053_Wkly_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(BATCH_B000053);
		try {
			JobParametersBuilder parameter = new JobParametersBuilder();
		 parameter.addString(POR_CD, porCd)
		.addString(ORDR_TAKE_BASE_MONTH,ordrTakeBaseMonth)
		.addString(ORDR_TAKE_BASE_WEEK_NO,ordrTakeBaseWeekNo)
		.addString(BATCH_ID,batchId)
		.addString(SEQUENCE_ID,sequenceId).toJobParameters();
		 jobLauncher.run(job, parameter.toJobParameters());
		
		} catch (Exception e) {
			LOG.error(e);
		}
	}
	
}
