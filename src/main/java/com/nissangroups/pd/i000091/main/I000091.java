/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.main;


import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.i000091.util.I000091Constants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;

/**
 * Job Execution Main Class.
 * 
 *@author z016127
 */
public class I000091 {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000091.class);

	/**
	 * Instantiates a new I000091.
	 */
	private I000091(){
	
	}
	/**
	 * Interface I000091 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Expected Input : I000091 TestFile 12 TEST01 GOM G
		
		LOG.info("Interface 91 Arguments : "+Arrays.deepToString(args));		
		if (args == null || args.length < I000091Constants.ARGS_LENGTH) {			
			LOG.error("Arguments expected: Interface_File_Id  Filename  POR_CD  BuyerGroupCode  OCF_BUYER_GROUP_CD  OCF_REGION_CD.");									
			return;
		}
		run(args);
	}
	
	/**
	 * Execute the Jobs and corresponding steps in sequence.
	 *
	 * @author z016127
	 */
	@SuppressWarnings("resource")
	private static void run(String[] args) {
		String[] batchConfig = {PDConstants.INTERFACE_I91_CONFIG_PATH };
		//Interface File Id
		String interfaceFileId = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(interfaceFileId);

		try {
			// PorCD
			String porCd = (args[2]
					.equalsIgnoreCase(I000091Constants.NULLSTRING) || args[2]
					.equalsIgnoreCase(I000091Constants.ASTREICK)) ? args[2]
					.replace(args[2], "*") : args[2];
			// Buyer GroupCd
			String buyerGroupCd = (args[3]
					.equalsIgnoreCase(I000091Constants.NULLSTRING) || args[3]
					.equalsIgnoreCase(I000091Constants.ASTREICK)) ? args[3]
					.replace(args[3], "*") : args[3];
			// Ocf BuyerGroupCD
			String ocfBuyerGrpCd = (args[4]
					.equalsIgnoreCase(I000091Constants.NULLSTRING) || args[4]
					.equalsIgnoreCase(I000091Constants.ASTREICK)) ? args[4]
					.replace(args[4], "*") : args[4];
			// Ocf Region CD
			String ocfRegionCd = (args[5]
					.equalsIgnoreCase(I000091Constants.NULLSTRING) || args[5]
					.equalsIgnoreCase(I000091Constants.ASTREICK)) ? args[5]
					.replace(args[4], "*") : args[5];
			
			JobParameters jobParameters = new JobParametersBuilder()
					.addString(IFConstants.INTERFACE_FILE_ID, interfaceFileId)
					.addString(IFConstants.FILE_NAME, args[1]) // File Name
					.addString(IFConstants.POR_CD, porCd)
					.addString(IFConstants.BUYER_GRP_CD, buyerGroupCd)
					.addString(IFConstants.OCF_BUYER_GRP_CD, ocfBuyerGrpCd)
					.addString(IFConstants.OCF_REGION_CD, ocfRegionCd)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);

			String exitStatus = PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);
		}
	}
}

