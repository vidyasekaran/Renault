/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is the main batch kick off program.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000065.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.PDConstants;

public class B000065 
{
	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(B000065.class
			.getName());
	/**
	 * Instantiates a new B000059.
	 */
	private B000065(){
	}
	
	/** Variable interfaceFileID */
	private static String interfaceFileID;
	
	/**
	 * Batch B000059 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) 
	{
		run();
	/* // verifying arguments
	 if (args != null && (args.length == 1)){
		 	interfaceFileID = args[0];
		 	run();
	 }	
	 else {
			LOG.error("Interface File ID Argument is expected");			
	 }	 */
	}

	/**
	 * Run.
	 */
	private static void run(){
		String[] batchConfig = { "B000065/B000065_Batch_Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("B000065");

		try{
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.S_INTERFACE_FILE_ID, interfaceFileID)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus =PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} 
		catch (Exception e){
			LOG.error("PDConstants.BATCH_ID_B000065", e);
		}
	}
}