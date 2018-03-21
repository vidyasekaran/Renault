/**
 * @author z015883 
 * System Name :Post Dragon
 * Sub system Name :Batch 
 * Function ID:PST-DRG-B000056
 * Module :CM Common
 * Process Outline :JobExecution Layer Class to Co-Ordinate with Repository.
 * 
 * <Revision History>
 *  Date 			Name(Company Name) 					Description 
 *  ------------------------------ --------------------- ----------
 *  18-11-15		z015883(RNTBCI)						New Creation
 *        
 *  */
package com.nissangroups.pd.b000056.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.model.MstJob;
import com.nissangroups.pd.model.TrnJobExec;
import com.nissangroups.pd.model.TrnJobstrmExec;
import com.nissangroups.pd.model.TrnJobstrmShdl;
import com.nissangroups.pd.repository.BatchJobRepository;
import com.nissangroups.pd.util.PDConstants;



public class B000056TriggerCoreJob implements Job {

	 @Autowired(required=false) 
	  BatchJobRepository batchJobRepository;
	 @Autowired
	 ApplicationContext applicationContext;
	 @PersistenceContext 
	 EntityManager entityManager;
	
	
	 private static final Log LOG = LogFactory.getLog(B000056TriggerCoreJob.class);
	
	
	public List<BigDecimal> jobStrmSeqId;
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 * This is the main function for Quartz job to execute its operation
	 * @param JobExecutionContext
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		 String[] batchConfig = { B000056Constants.B000056_XML };
			
	
		 applicationContext = new ClassPathXmlApplicationContext(
				batchConfig);
		 
		batchJobRepository = (BatchJobRepository)applicationContext.getBean(B000056Constants.BATCH_JOB_REPOSITORY);
		 
			List<String> stts=(List<String>) context.getJobDetail().getJobDataMap().get(B000056Constants.tmp_STATUS);
			 
			try
			{
				String result=processB000056CoreJob(stts);
				LOG.info(result);
				LOG.info("Job Completed...");
			}
			catch(Exception e)
			{
				LOG.error(e);
			}
		
	}

	
	/*use to process complete batch 56
	*@param stts
	*@return String
	*/
	public String processB000056CoreJob(List<String> stts)
	{
		String resultout="";
		List<TrnJobstrmShdl> jobStrms=new ArrayList<TrnJobstrmShdl>();
		
		  jobStrms=batchJobRepository.getJobStreamsToExecute(stts);
		for(TrnJobstrmShdl jobStrm:jobStrms)//iterating for each job Stream one by one 
		{
			processJobStreams(jobStrm);
		}
		
		return resultout;
		
	}

	
	/*Use to Process JobStreams one by one
	*@param jobStrm
	*void
	*/
	public void processJobStreams(TrnJobstrmShdl jobStrm)
	{
		TrnJobstrmExec jobStrmExec=batchJobRepository.insertJobStrmExecTrn(B000056Constants.IN_PROGRESS, jobStrm.getJobstrmShdlSeqId(),jobStrm.getJobStrmExecSeqId());//updating the JobStream Status as In progress
		 
		Object[] check=batchJobRepository.getFilePathForJobStrm(jobStrm.getJobstrmSeqId());//P0003
		if(check[2]!=null)
		{
			if(batchJobRepository.checkFile(check[2].toString().trim(),check[3].toString().trim()))//if file exist at expected location
			{
				List<MstJob> jobs=batchJobRepository.getJobsForJobStrm(jobStrm.getJobstrmSeqId());//P0004(got all the jobs of one particular jobStream)
				if(jobs!=null)
					processJobs(jobs, jobStrmExec);
			
			}
			else 		/** File exist for jobstream but may be path is wrong*/
			{
				batchJobRepository.FileNotExists(jobStrm,jobStrmExec.getJobstrmExecSeqId());//P0003.b
			}
		}
		else 		/** If File does not exist for jobstream at all*/
		{
			batchJobRepository.FileNotExists(jobStrm,jobStrmExec.getJobstrmExecSeqId());//P0003.b
		}
	}
	
	
	
	
		/*use to execute jobs of Jobstreams one by one
		*@param jobs
		*@param jobStrmExec
		*@return void
		*/
		public void processJobs(List<MstJob> jobs,TrnJobstrmExec jobStrmExec)
		{
			for(MstJob job:jobs)
			{
			 
			TrnJobExec jobtrn=batchJobRepository.insertJobExecTrn(job.getJobSeqId(), jobStrmExec.getJobstrmExecSeqId(), B000056Constants.IN_PROGRESS);//P0005
			if(jobtrn==null)
			{
				int i=batchJobRepository.updateJobStrmExecTrnStatus(jobStrmExec.getJobstrmExecSeqId(),B000056Constants.FAILURE);
				if(i==B000056Constants.INT_ZERO)
				 break;
				}
			int timeoutvalue=batchJobRepository.getTimeOutValue(B000056Constants.JOB_TIME_OUT,B000056Constants.TIME_OUT_VALUE);//P0007
			int exitCode=ExecuteFile.executeSHFile(job,timeoutvalue);//P0006
			
			/** NOTE: Before deploying, the CustomLog.java and Log4j.xml files has to change (path for log file has to change)
			 * and PDconstant LOG_FILES_PATH variable has to change
			 * */
			int result=batchJobRepository.updateJobExecTrn(exitCode, jobtrn.getJobExecSeqId(),jobStrmExec.getJobstrmExecSeqId());//P0007
			batchJobRepository.sendEmail(job.getJobSeqId(),exitCode, PDConstants.LOG_FILES_PATH,jobtrn.getJobExecSeqId());
			 
				if(exitCode==B000056Constants.INT_ZERO)
					batchJobRepository.updateJobStrmExecTrnStatus( jobStrmExec.getJobstrmExecSeqId(),B000056Constants.SUCCESS);
				else
				{
					batchJobRepository.updateJobStrmExecTrnStatus( jobStrmExec.getJobstrmExecSeqId(),B000056Constants.FAILURE);
					break;
				}
			}
	
		}

	/**
	 * @return the batchJobRepository
	 */
	public BatchJobRepository getBatchJobRepository() {
		return batchJobRepository;
	}


	/**
	 * @param batchJobRepository the batchJobRepository to set
	 */
	public void setBatchJobRepository(BatchJobRepository batchJobRepository) {
		this.batchJobRepository = batchJobRepository;
	}

	
}
