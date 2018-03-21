/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I00008
 * Module          :Spec Master
 * Process Outline :Query Constants for I000008 Interface	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z010356(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;

import static com.nissangroups.pd.util.PDMessageConsants.M00107;

/**
 * The Class I000008.
 *
 * @author z010356
 * I00008 Main Class
 */
public class I000008 {
	
	/** Constant LOG. */
	private static final Log LOG  = LogFactory.getLog(I000008.class.getName());
	
	/** Constant arg length */
	private static int ARGLENGTH = 2;
	
	/** Variable interface file id. */
	private static String INTERFACE_FILE_ID;
	
	/** Variable por cd. */
	private static String POR_CD;
	
	
		
	/**
	 * Batch I000008 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!= ARGLENGTH){
			LOG.error(M00107);
			LOG.error("Arguments [2] expected, [INTERFACE_FILE_ID,POR_CD]");
			CommonUtil.stopBatch();
		}
				
		
		
		INTERFACE_FILE_ID= args[0];
		POR_CD= args[1];

		run();
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(){

		String[] batchConfig = { "I000008/I000008-IF-Config.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(batchConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("I000008");		
		try {
			JobParametersBuilder jobParametrs=new JobParametersBuilder();
			jobParametrs.addString("INTERFACE_FILE_ID", INTERFACE_FILE_ID);
			jobParametrs.addString("POR_CD", POR_CD);
			jobParametrs.addLong("featureCdPatternA", (long) 0);
			jobParametrs.addLong("featureCdPatternB", (long) 99);
			JobParameters parameter = jobParametrs.toJobParameters();
			jobLauncher.run(job, parameter);
		}
		catch(Exception e)
		{
			LOG.error("I000008",e);
		}
	}	
}

