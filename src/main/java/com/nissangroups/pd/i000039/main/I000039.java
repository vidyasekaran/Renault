/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.main;

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
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 * @author z014135
 */
public class I000039 {
	
	/**
	 * Instantiates a new I000039.
	 */
	/** Constant arg length */
	private static int ARG_LENGTH = 3;
	private I000039(){
		
	}
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000039.class);
	
	/** Variable context. */
	private static ClassPathXmlApplicationContext context;
	
	/** Variable por code */
	private static String porCd;

	/** Variable Interface Id */
	private static String intrfaceId;
	
	/** Variable file name */
	private static String fileName;

	/**
	 * Interface I000039 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		
		if (args == null || (args.length != ARG_LENGTH )) {
			LOG.error("Arguments [3] expected, [Interface_ID,FILE_NAME,POR_CD]");
			CommonUtil.stopBatch();
		}
		
		intrfaceId = args[0]; //Interface Id
		fileName =  args[1]; //File name 
		porCd = args[2]; // POR CD
		run();

	}
	
	
	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *  
	 */
	private static void run(){
		
		
		String[] batchConfig = { PDConstants.INTERFACE_I39_CONFIG_PATH };	
		context = new ClassPathXmlApplicationContext(batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("I000039");

		try {
			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_PORCD, porCd)
					.addString(IFConstants.INTERFACE_FILE_ID, intrfaceId)
					.addString(IFConstants.FILE_NAME, fileName)
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
		}//Connection will already be in closed status if context is null so else not required

		LOG.info("Batch Completed on " + new Date());
	}
	


}
