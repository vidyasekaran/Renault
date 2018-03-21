/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/10  	 z002548(RNTBCI)               New Creation
 *
 */



package com.nissangroups.pd.main;

import java.util.Date;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * The Class B000001.
 *
 * @author z002548
 */
public class B000001 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000001.class);
	
	
	
	/** Variable context. */
	private static ClassPathXmlApplicationContext context;
	
	
	/**
	 * Batch B000001 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		 
		LOG.info("hello welcome to Batch56 testing...");
		
		run();
	}
	
	

	/**
	 * To Execute the Batch.
	 */
	private static void run() {
		// To Fetch the XML file 
		String[] batchConfig = { "B000001/B000001_Batch_Config.xml" };
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000001");
		
	
		
		try {
			
			JobParameters parameter = new JobParametersBuilder().toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = "Exit Status :";
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);
		}
		catch(Exception e){
			LOG.info("Bathc Failure for this reasons :",e);
			
			
		}
		finally {
			if(context != null)
				context.close();
		}
		
		LOG.info("Batch Completed on "+ new Date());
	}

}
