/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.main;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * This is the main program to kick off interface I000065 
 * 
 *@author z014676
 */
public class I000065 
{

	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(I000065.class);

	/** Constant arg length */
	private static int ARG_LENGTH = 3;
	/**
	 * Interface I000065 Execution Start from this Main Method.
	 * 
	 * @param args
	 */
	private I000065()
	{
		
	}
	/**
	 * Interface I000033 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) 
	{

		/** Expected args : "I000014" 08 I000065 5BUY11 */
		LOG.info("IF65 Args : " + Arrays.deepToString(args));

		if (args == null || args.length <= ARG_LENGTH) 
		{
			LOG.error("Arguments expected: [File_Name,POR CD, Interface_File_ID, Buyer Group Code]...");
			return;
		}

		run(args);
	}

	/**
	 *  Execute the Jobs and corresponding steps in sequence. 
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) 
	{
		//Process Id P00001 Fetch Input Values
		String[] batchConfig = { PDConstants.INTERFACE_I65_CONFIG_PATH };
		String fileName = args[0]; //FileName 
		String porCD = args[1]; // POR CD
		String interfaceFileId = args[2]; // Interface File ID
		String buyerGrpCode = args[3]; // Buyer Group Code

		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(PDConstants.Interface_65_ID);
		try 
		{
			JobParameters parameter = new JobParametersBuilder()
					.addString(IFConstants.FILE_NAME, fileName)
					.addString(IFConstants.porCd_Param, porCD)
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.buyer_buyerGrpCD_Param, buyerGrpCode)
					.toJobParameters();

			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} 
		catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}

}
