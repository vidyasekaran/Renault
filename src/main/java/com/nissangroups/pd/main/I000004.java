/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000004
 * Module          :SP Spec Master					
 * Process Outline :Interface for Receive PL20 Spec Master Interface from DRG-VSM
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z002548(RNTBCI)               New Creation
 *
 */ 

package com.nissangroups.pd.main;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;

/**
 * Job Execution Main Class.
 *
 * @author z002548
 */
public class I000004 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000004.class);
	
	/** Variable context. */
	private static ClassPathXmlApplicationContext context;
	

	/** Constant arg length */
	private static int ARGLENGTH = 1;
	
	/** Variable file id. */
	private static String fileID;
	
	/**
	 * Interface I000004 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!= ARGLENGTH){
			//LOG.error(M00107);
			LOG.error("Arguments [1] expected, [FILE ID] ");
			CommonUtil.stopBatch();
		}
		
		fileID = args[0];
		run();
	}
	
	

	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 */
	private static void run() {
		LOG.info("Batch Started on " + new Date());
		String[] batchConfig = { "I000004/I000004_IF_Config.xml" };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("I000004");
		try {
			JobParameters jobParameters = new JobParametersBuilder().addString("fileID", fileID).toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			String exitStatus = "Exit Status :";
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);
		}
		catch(Exception e) {
			LOG.info("Bathc Failure for this reasons :",e);
		}
		finally {
			if(context != null)
				context.close();
		}
		
		LOG.info("Batch Completed on "+ new Date());
	}







}
