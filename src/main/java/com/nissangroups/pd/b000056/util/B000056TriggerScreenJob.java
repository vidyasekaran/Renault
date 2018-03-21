/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000055
 * Module          :CM Common		
 * Process Outline :Batch for Job Schedule Execution																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-02-2016  	  z015883(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000056.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.MstJob;
import com.nissangroups.pd.model.TrnJobExec;
import com.nissangroups.pd.model.TrnJobstrmExec;
import com.nissangroups.pd.model.TrnJobstrmShdl;
import com.nissangroups.pd.repository.BatchJobRepository;
import com.nissangroups.pd.util.PDConstants;


public class B000056TriggerScreenJob {

	@Autowired
	private BatchJobRepository batchJobRepository;
	private static final Log LOG = LogFactory.getLog(B000056TriggerScreenJob.class);
	


	/*This method will get called when executing from screen76(P00010.2)
	*@param jobStrmSeqId,porCd
	*@return string
	*/
	public void executeFromS079(long jobStrmSeqId, String porCd)
	{
			executeScreenJobs(jobStrmSeqId);
	}
	/*This method will get called when executing from screen77(P00010.1)
	*@param jobStrmSeqId,porCd
	*@return string
	*/
	public void executeFromS077(long jobStrmSeqId,long jobStrmShdlSeqId,long jobStrmExecSeqId)
	{
		executeScreenJobs(jobStrmSeqId);
	}
	/*This method will get called when executing from screens
	*@param jobStrmSeqId,porCd
	*@return string
	*/
	public void executeScreenJobs(long jobStrmSeqId)
	{
		TrnJobstrmShdl jobShdl=batchJobRepository.insertJobStrmShdlTrn(jobStrmSeqId);//P00010.2
		 if(jobShdl!=null)
		 {
			 processJobStreams(jobShdl);
		 }
		 else
		 {
			 LOG.info("JobStreamShdl is Null so Cannot Process Further.");
		 }
		 
	}
	
	/*Use to Process JobStreams one by one
	*@param jobStrm
	*void
	*/
	public void processJobStreams(TrnJobstrmShdl jobStrm)
	{
		TrnJobstrmExec jobStrmExec=batchJobRepository.insertJobStrmExecTrn(B000056Constants.IN_PROGRESS, jobStrm.getJobstrmShdlSeqId(),jobStrm.getJobStrmExecSeqId());//P00011-updating the JobStream Status as In progress
		 
		Object[] check=batchJobRepository.getFilePathForJobStrm(jobStrm.getJobstrmSeqId());//P00012
		if(check[2]!=null)
		{
			if(batchJobRepository.checkFile(check[2].toString().trim(),check[3].toString().trim()))//if file exist at expected location
			{
				List<MstJob> jobs=batchJobRepository.getJobsForJobStrm(jobStrm.getJobstrmSeqId());//P00013(got all the jobs of one particular jobStream)
				if(jobs!=null)
					processJobs(jobs, jobStrmExec);
			
			}
			else 		/** File exist for jobstream but may be path is wrong*/
			{
				batchJobRepository.FileNotExistsForScreen(jobStrmExec.getJobstrmExecSeqId());//P00018(case 1)
			}
		}
		else 		/** If File does not exist for jobstream at all*/
		{
			batchJobRepository.FileNotExistsForScreen(jobStrmExec.getJobstrmExecSeqId());//P00018(case 2)
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
			 
			TrnJobExec jobtrn=batchJobRepository.insertJobExecTrn(job.getJobSeqId(), jobStrmExec.getJobstrmExecSeqId(), B000056Constants.IN_PROGRESS);//P00014
			if(jobtrn==null)
			{
				int i=batchJobRepository.updateJobStrmExecTrnStatus(jobStrmExec.getJobstrmExecSeqId(),B000056Constants.FAILURE);
				if(i==B000056Constants.INT_ZERO)
				 break;
				}
			int timeoutvalue=batchJobRepository.getTimeOutValue(B000056Constants.JOB_TIME_OUT,B000056Constants.TIME_OUT_VALUE);//P00015
			int exitCode=ExecuteFile.executeSHFile(job,timeoutvalue);//P00015
			
			/** NOTE: Before deploying, the CustomLog.java and Log4j.xml files has to change (path for log file has to change)
			 * and PDconstant LOG_FILES_PATH variable has to change
			 * */
			int result=batchJobRepository.updateJobExecTrn(exitCode, jobtrn.getJobExecSeqId(),jobStrmExec.getJobstrmExecSeqId());//P00016
			batchJobRepository.sendEmail(job.getJobSeqId(),exitCode, PDConstants.LOG_FILES_PATH,jobtrn.getJobExecSeqId());//P00017
			 
				if(exitCode==B000056Constants.INT_ZERO)
					batchJobRepository.updateJobStrmExecTrnStatus( jobStrmExec.getJobstrmExecSeqId(),B000056Constants.SUCCESS);//P00018 success case
				else
				{
					batchJobRepository.updateJobStrmExecTrnStatus( jobStrmExec.getJobstrmExecSeqId(),B000056Constants.FAILURE);//P00018 Failure case
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
