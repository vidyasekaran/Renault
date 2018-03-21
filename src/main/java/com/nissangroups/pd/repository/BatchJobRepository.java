/**
  * System Name :Post Dragon
 * Sub system Name :Batch 
 * Function ID:PST-DRG-B000056 
 * Module :CM Common
 * Process Outline :Repository/Business Layer Class to Co-Ordinate with DB. This Class
 * performs all the necessary operations related to BatchJobStream.
 * 
 * <Revision History>
 *  Date 			Name(Company Name) 					Description 
 *  ------------------------------ --------------------- ----------
 *  18-11-15		z015883(RNTBCI)						New Creation
 *        
 *  */

package com.nissangroups.pd.repository;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000056.util.B000056QueryConstants;
import com.nissangroups.pd.b000056.util.ExecuteFile;
import com.nissangroups.pd.b000058.main.EmailService;
import com.nissangroups.pd.model.MstJob;
import com.nissangroups.pd.model.TrnJobExec;
import com.nissangroups.pd.model.TrnJobstrmExec;
import com.nissangroups.pd.model.TrnJobstrmShdl;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.LatestLogFile;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
public class BatchJobRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired(required = false)
	private JobStrmExecTrnRepository jobStrmExecTrnRepository;

	@Autowired(required = false)
	private JobExecTrnRepository jobExecTrnRepository;
	@Autowired(required = false)
	private EmailService emailService;
	@Autowired
	private JobStrmShdlTrnRepository jobStrmShdlTrnRepository;

	private static final Log LOG = LogFactory.getLog
			(BatchJobRepository.class);

	@Autowired
	CommonUtil commonUtil;

	public BatchJobRepository() {

	}

	
	/*// get the Unexecuted JobStrems at Current time and date (PST-DRG-B000056,P0001)
	*@param stts
	*@return List<TrnJobstrmShdl>
	*/
	public List<TrnJobstrmShdl> getJobStreamsToExecute(List<String> stts) {
		List<Object[]> result = new ArrayList<Object[]>();
		List<TrnJobstrmShdl> list = new ArrayList<TrnJobstrmShdl>();
		try {
			SimpleDateFormat smf = new SimpleDateFormat(B000056Constants.DATE_TIME_FORMAT);
			Date d = new Date();

			String data = smf.format(d);

			Query query = entityManager
					.createNativeQuery(B000056QueryConstants.getJobStreamLst
							.toString());

					Timestamp t = new Timestamp(d.getTime());
			query.setParameter(B000056Constants.CURNT_DT, data);

			query.setParameter(B000056Constants.CURNT_TIME, data);

			query.setParameter(B000056Constants.STATUS, stts);

			result = query.getResultList();
			//object[] returns JOBSTRM_SHDL_SEQ_ID,JOBSTRM_EXEC_SEQ_ID,JOBSTRM_SEQ_ID,ST_DT,ST_TIME,END_DT,END_TIME,JS_EXECUTION_STATUS
			LOG.info("total rows with JObStrmExec are " + result.size());
			for (Object[] val : result) {
				TrnJobstrmShdl bean = entityManager.find(TrnJobstrmShdl.class,
						Long.parseLong(val[0].toString()));
				bean.setJobStrmExecSeqId(Long.parseLong(val[1].toString()));
				if (bean != null)
					list.add(bean);
			}
			/*
			 * Now checking for all other jobstrmsshdl details which are not in
			 * jobstrmexec
			 */
			Query query1 = entityManager
					.createNativeQuery(B000056QueryConstants.getOnlyJobShdlJobStrms
							.toString());
			query1.setParameter(B000056Constants.CDT, data);
			result = query1.getResultList();
			//result object[] contains JOBSTRM_SHDL_SEQ_ID,JOBSTRM_SEQ_ID,ST_DT,ST_TIME,END_DT,END_TIME
			LOG.info("List of JobStrms which are not Available in JobStrmExec "
					+ result.size());
			for (Object[] val : result) {
				TrnJobstrmShdl bean = entityManager.find(TrnJobstrmShdl.class,
						Long.parseLong(val[0].toString()));
				bean.setJobStrmExecSeqId(B000056Constants.TRIPLE_ZERO);
				if (bean != null)
					list.add(bean);
			}
			if (list.size() ==B000056Constants.INT_ZERO|| list == null) {
				CommonUtil.logMessage(B000056Constants.M00003,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.TRN_JOBSTRM_SHDL });
				//CommonUtil.stopBatch();(system.exit() cannot be called because it even terminates the scheduler and do not allow to run for next periodic interval)
				return list;
			} else
				LOG.info("Returned [" + list.size()
						+ "] Unexecuted Job Streams on Date ["
						+ commonUtil.currentDateTime() + "]");
		} catch (Exception e) {
			LOG.info("stopping batch");
			LOG.error(e);
			return list;
		}
		return list;
	}

	
 	/*// Insert the Job Stream Execution details into JOB STREAM EXECUTION TRN// (PST-DRG-B000056-P0002)
 	*@param stts
 	*@param jobStrmShdlSeqId
 	*@param jobStrmExecSeqId
 	*@return *TrnJobstrmExec
  	*/
 	public TrnJobstrmExec insertJobStrmExecTrn(String stts,
			long jobStrmShdlSeqId, long jobStrmExecSeqId) {
		TrnJobstrmExec result = null;

		result = jobStrmExecTrnRepository.insertJobStrmExecTrn(stts,
				BigDecimal.valueOf(jobStrmShdlSeqId), jobStrmExecSeqId);
		if (result == null) {
			CommonUtil.logErrorMessage(B000056Constants.M000164,
					B000056Constants.CONSTANT_V1,
					new String[] { PDConstants.TRN_JOBSTRM_EXEC + " "
							+ PDConstants.INSERTION });
			return result;
		} else {
			LOG.info(PDMessageConsants.M00189);
		}
		return result;
	}

	
	/*Job Streams with Interface File to be checked and then processed(PST-DRG-B000056-P0003)
	
	*@param jobStrmSeqId
	*@return *Object[]
	*/
	public Object[] getFilePathForJobStrm(BigDecimal jobStrmSeqId) {
		long lid = jobStrmSeqId.longValue();
		Query query = entityManager
				.createNativeQuery(B000056QueryConstants.getFilePath.toString());
		query.setParameter(B000056Constants.JOB_STM_SEQ_ID, lid);
		List<Object[]> result = query.getResultList();
		//object[] contains=JOBSTRM_SEQ_ID,IF_JOBSTRM_FLG,IF_FILE_PATH,FILENAME_FORMAT
		Object[] obj = new Object[4];
		if (result != null) {
			for (Object[] ob : result) {
				obj[0] = ob[0];
				obj[1] = ob[1];
				obj[2] = ob[2];
				obj[3] = ob[3];
			}
			LOG.info(" file path extracted is " + obj[2] + obj[3]);
		} else {
			LOG.info("No File in DB[P00003]");
		}
		return obj;

	}

	
	
	
	/*// Check for the Waiting Duration of the Interface File and Update the Status of JOB STREAM CONDITION (PST-DRG-B000056)-P0003.b
	*@param jobStrmExecSeqId
	*@param stts
	*@return int
	*/
	public int updateJobStrmExecTrn(long jobStrmExecSeqId, String stts) {
		int result = jobStrmExecTrnRepository.updateJobStrmExecTrn(stts,
				jobStrmExecSeqId);
		if (result ==B000056Constants.INT_ZERO) {
			CommonUtil.logErrorMessage(B000056Constants.M000164,
					B000056Constants.CONSTANT_V1,
					new String[] { PDConstants.TRN_JOBSTRM_EXEC + " "
							+ PDConstants.UPDATION });
		}
		return result;
	}

	

	 
	/* Extract the related JOBs from JOB MST for the corresponding JOB STREAM
	 * (PST-DRG-B000056)P0004
	*@param jobStrmSeqId
	*@return List<MstJob>
	*/
	public List<MstJob> getJobsForJobStrm(BigDecimal jobStrmSeqId) {

		Query query = entityManager
				.createNativeQuery(B000056QueryConstants.getJobsForJobStream
						.toString());
		query.setParameter(B000056Constants.JOB_STM_SEQ_ID, jobStrmSeqId);
		List<Object[]> res = query.getResultList();
		//object[] contains JOBSTRM_SEQ_ID,JOB_SEQ_ID,EXEC_SEQ,SHELL_PATH,POR,js.IF_ID,STAGE_CD,BUYER_GRP_CD,mjs.PARAM
		List<MstJob> result = new ArrayList<MstJob>();
		if (res != null) {
			for (Object[] obj : res) {
				MstJob job = entityManager.find(MstJob.class,
						Long.parseLong(obj[1].toString()));
				if (job != null) {
					result.add(job);
					job.setInputParam(obj[8] != null ? obj[8].toString() : " ");
					B000056Constants.inputParams.put(B000056Constants.POR_CD,
							obj[4] != null ? obj[4].toString() : B000056Constants.NULL);
					B000056Constants.inputParams.put(B000056Constants.INTERFACE_ID,
							obj[5] != null ? obj[5].toString() : B000056Constants.NULL);
					B000056Constants.inputParams.put(B000056Constants.STG_CD,
							(obj[6] != null ? obj[6].toString() : B000056Constants.NULL));
					B000056Constants.inputParams.put(B000056Constants.BUYER_GRP_CD,
							obj[7] != null ? obj[7].toString() : B000056Constants.NULL);
					LOG.info("JobID to Execute is [" + job.getJobSeqId()
							+ " ]");
				}

			}

		} else {
			LOG.info("No Jobs are available");
		}
		return result;
	}

	 
	/* Insert the JOB details into the JOB EXECUTION TRN(PST-DRG-B000056)-P0005
	*@param jobSeqId
	*@param jobStrmExecSeqId
	*@param stts
	*@return TrnJobExec
	*/
	public TrnJobExec insertJobExecTrn(long jobSeqId, long jobStrmExecSeqId,
			String stts) {
		TrnJobExec result = jobExecTrnRepository.insertJobExecTrn(jobSeqId,
				jobStrmExecSeqId, stts);
		if (result == null) {

			CommonUtil.logErrorMessage(B000056Constants.M000164,
					B000056Constants.CONSTANT_V1,
					new String[] { PDConstants.JOB_EXEC_TRN + " "
							+ PDConstants.INSERTION });

		} else
			LOG.info(PDMessageConsants.M00189);
		return result;

	}

	/** Update the JOB details in JOB EXECUTION TRN Batch56-P0007 */
	
	/*
	*@param code
	*@param jobExecSeqId
	*@param jobStrmExecSeqId
	*@return int
	*/
	public int updateJobExecTrn(int code, long jobExecSeqId,
			long jobStrmExecSeqId) {
		int result = 0;
		try {
			String[] data = new String[B000056Constants.THREE];
			/** HERE LOGFILEPATH HAS TO FETCH */
			data[1] = PDConstants.LOG_FILES_PATH + ExecuteFile.filename;
			// here file path has to append with batch name
			 
			/** HERE LOGFILENAME HAS TO FETCH */
			data[2] = LatestLogFile.getLatestFile(data[1]); 
			B000056Constants.LATEST_LOG_FILE = data[1] + B000056Constants.BACK_SLASH + data[2];
			if (code == B000056Constants.INT_ZERO)
				data[0] = B000056Constants.SUCCESS;
			else if (code == B000056Constants.INT_ONE)
				data[0] = B000056Constants.FAILURE;
			else if (code ==B000056Constants.INT_HUNDRED || code == B000056Constants.INT_HUNDRED_ONE) {
				data[0] = B000056Constants.FAILURE;
				data[1] = B000056Constants.NIL;
				data[2] = B000056Constants.NIL;
			}
			 
			LOG.info("data to Update in JobExecTrn " + data[0] + " "
					+ data[1] + " " + data[2]);
			result = jobExecTrnRepository.updateJobExecTrnDetails(data,
					jobExecSeqId);
			if (result == B000056Constants.INT_ZERO) {
				CommonUtil.logErrorMessage(B000056Constants.M000164,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.JOB_EXEC_TRN + " "
								+ PDConstants.UPDATION });
				result = updateJobStrmExecTrnStatus(jobStrmExecSeqId, B000056Constants.FAILURE);
				return result;
			}

			else {
				result = B000056Constants.INT_TWO;
				LOG.info(PDMessageConsants.M00189);
			}
		} catch (Exception e) {
			LOG.error(e);

		}
		return result;
	}

	/**
	 * Checking file exist at Filepath or not.
	 * 
	 * @param fileNameFilePath
	 * @param jobStrms
	 * @param jobStrmExecSeqId
	 * @return string
	 */
	public String FileNotExists(TrnJobstrmShdl jobstrm, long jobStrmExecSeqId) {
		String result = "";

		if (commonUtil.currentDateTime().before(jobstrm.getEndDt())
				&& commonUtil.currentDateTime().before(jobstrm.getEndTime())) {
			LOG.info("condition is true");
			int r = jobStrmExecTrnRepository.updateJobStrmExecTrn(B000056Constants.WAITING_FOR_FILE, jobStrmExecSeqId);
			if (r == B000056Constants.INT_ZERO) {
				CommonUtil.logErrorMessage(B000056Constants.M000164,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.TRN_JOBSTRM_EXEC + " "
								+ PDConstants.UPDATION });
				r = updateJobStrmExecTrnStatus(jobStrmExecSeqId, B000056Constants.FAILURE);
				return String.valueOf(r);
			} else
				LOG.info("JobStrmExec Status Updated as Waiting for File.");

		} else {
			LOG.info("Condition is false..");
			int i = updateJobStrmExecTrnStatus(jobStrmExecSeqId, B000056Constants.FAILURE);
			return String.valueOf(i);
		}
		return result;
	}

	/**
	 * Checking file exist at File path or not.
	 * 
	 * @param fileNameFilePath
	 * @param jobStrms
	 * @param jobStrmExecSeqId
	 * @return string
	 */
	public String FileNotExistsForScreen(long jobStrmExecSeqId) {
			int i = updateJobStrmExecTrnStatus(jobStrmExecSeqId, B000056Constants.FAILURE);
			return String.valueOf(i);
	}

	/**
	 * P0006-To check Time Out value(considering it in no of Mins) of job NOTE:
	 * TimeOut value will be one & same for all the jobs
	 * */

	/*
	*@param prmtrCd
	*@param key1
	*@return int
	*/
	public int getTimeOutValue(String prmtrCd, String key1) {

		Query query = entityManager
				.createNativeQuery(B000056QueryConstants.getTimeOutValue
						.toString());
		query.setParameter(B000056Constants.PARAM_CD, prmtrCd);
		query.setParameter(B000056Constants.KEY1, key1);
		Object k = query.getSingleResult();
		LOG.info("Time out value for Job is " + k);
		int j = Integer.parseInt(k.toString());
		return j;
	}

	 
	/*  Process P0009 Batch B000056 to update jobstreamExecTrn 
	*@param jobStrmExecSeqId
	*@param stts
	*@return int
	*/
	public int updateJobStrmExecTrnStatus(long jobStrmExecSeqId, String stts) {
		int k = B000056Constants.INT_ZERO;
		try {
			k = jobStrmExecTrnRepository.updateJobStrmExecTrn(stts,
					jobStrmExecSeqId);

			if (k ==B000056Constants.INT_ZERO) {
				CommonUtil.logErrorMessage(B000056Constants.M000164,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.TRN_JOBSTRM_EXEC + " "
								+ PDConstants.UPDATION });
				//CommonUtil.stopBatch();
				LOG.info("Stopping Batch");
			} else {
				CommonUtil.logMessage(B000056Constants.M000187,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.TRN_JOBSTRM_EXEC + " "
								+ PDConstants.UPDATION });
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return k;
	}

	
	/*used to get timestamp
	*@return Timestamp
	*/
	public Timestamp getTimestamp() {
		Query query = entityManager
				.createNativeQuery(B000056QueryConstants.getTimeStampValue
						.toString());
		 
		Object o = query.getSingleResult();
		Timestamp t = (Timestamp) o;
		LOG.info("Timestamp is " + t);
		Date d = new Date();
		Timestamp time = new Timestamp(d.getTime());
		if (t.before(time))
			LOG.info("true");
		return t;

	}

	/** it is intermediate kind of method which will trigger Email service
	 * @param jobSeqId
	 * @param exitCode
	 * @param logFilesPath
	 * @param jobExecSeqId
	 * @return string
	 */
	public String sendEmail(long jobSeqId, int exitCode, String logFilesPath,
			long jobExecSeqId) {
		return emailService.sendEmail(jobSeqId, exitCode, logFilesPath,
				jobExecSeqId);
	}

	/** checkfile whether exist or not
	 * @param string
	 * @param string2
	 * @return boolean
	 */
	public boolean checkFile(String path, String filepattern) {
		boolean b = false;
		try {
			b = new File(path + filepattern).exists();
			if (!b) {
				Path p = Paths.get(path);
				b = Files.newDirectoryStream(p, filepattern).iterator()
						.hasNext();
			}
		} catch (Exception e) {
			LOG.error(e);
			b = false;
		}
		return b;
	}


	/*this method use to insert into JobstrmShdltrn table
	*@param jobStrmSeqId
	*@return integer
	*/
	public TrnJobstrmShdl insertJobStrmShdlTrn(long jobStrmSeqId) {
		
		TrnJobstrmShdl result=jobStrmShdlTrnRepository.insertRecord(jobStrmSeqId);
		if (result ==null) {

				CommonUtil.logErrorMessage(B000056Constants.M000164,
						B000056Constants.CONSTANT_V1,
						new String[] { PDConstants.TRN_JOBSTRM_SHDL + " "
								+ PDConstants.INSERTION });

			} else
				LOG.info(PDMessageConsants.M00189);
		return result;
		}
		
	
}
