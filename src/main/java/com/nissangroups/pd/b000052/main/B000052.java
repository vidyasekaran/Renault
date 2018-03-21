package com.nissangroups.pd.b000052.main;

import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.i000039.main.I000039;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;


public class B000052 {
	
	B000052(){
		
	}

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000052.class);
	
	private static  ClassPathXmlApplicationContext context;
	/** Variable por_Cd  */
	private static String porCd;
	
	/** Variable process type */
	private static String prcsType;

	/**
	 *Batch B000052 Execution Start from this Main Method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		
		
		if (args != null && (args.length == 2 )) {
			porCd = args[0];
			prcsType=args[1];
			if(prcsType.length()==1){
				run();
			}
			else{
				LOG.error(B000052Constants.BATCH_ID_WRGPRCSTYP);
			}
			
			LOG.info(porCd + "  "+prcsType);
		}else {
			LOG.error(B000052Constants.ERROR_MESSAGE_ID1);
		}
		

	}
	
	private static void run(){
		String[] batchConfig1 = {PDConstants.BATCHCONFIG1};
		String[] batchConfig2 = {PDConstants.BATCHCONFIG1};
		if(prcsType.equalsIgnoreCase(PDConstants.B52MONTH)){
			context= new ClassPathXmlApplicationContext(batchConfig1);
		}
		if(prcsType.equalsIgnoreCase(PDConstants.B52WEEK)){
			context= new ClassPathXmlApplicationContext(batchConfig2);
			}
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher); 
		Job job = (Job) context.getBean(PDConstants.B000052);
		LOG.info("PORCD :"+porCd);
		LOG.info("Process Type :"+prcsType);
		JobParameters jobParameter = new JobParametersBuilder()
		.addString(B000052Constants.PORCD, porCd)
		.addString(B000052Constants.TYPE, prcsType)
		.toJobParameters();
		
		try {
		JobExecution jobExecution =	jobLauncher.run(job, jobParameter);
		String executionStatus = jobExecution.getStatus().toString();
		LOG.info(executionStatus);
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error(PDConstants.EXCEPTION+e);
			LOG.info(Level.SEVERE, e);
		}finally{
			if(context!=null)
				context.close();
		}
		LOG.info("Batch Completed on " + new Date());
		
	}

}
