/**
 * @author z015883
 *System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000056
 * Module          :CM Common
 * Process Outline : utility Class to Co-Ordinate with DB related queries.
 * 
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-Nov-2015  	 z015883(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000056.util;

/**
 * @author z015883
 * for all queries related to B000056 (scheduler process) 
 *
 */
public class B000056QueryConstants {

	//SEQ-ID FOR ALL TABLES RELATED TO BATCH56
	 
	public static final String JOBSTRM_EXEC_SEQ_ID = "select TRN_JOBSTRM_EXEC_SEQ_ID.nextval from dual"; 
	     
	//get the list of available Jobstreams to be executed based on time(B000056 Batch-P0001)
	public static final StringBuilder getJobStreamLst=new StringBuilder()
	.append("select jobShdl.JOBSTRM_SHDL_SEQ_ID,jobExec.JOBSTRM_EXEC_SEQ_ID,jobshdl.JOBSTRM_SEQ_ID,jobshdl.ST_DT,jobShdl.ST_TIME,jobshdl.END_DT,jobShdl.END_TIME,")
	.append("jobExec.JS_EXECUTION_STATUS from TRN_JOBSTRM_EXEC jobExec, TRN_JOBSTRM_SHDL jobShdl ")
	.append("where jobShdl.ST_DT <=to_timestamp(:currentDt,'yyyy-mm-dd hh24:mi:ss') AND jobShdl.ST_TIME <=to_timestamp(:currentTime,'yyyy-mm-dd hh24:mi:ss')")
	.append(" AND jobShdl.END_DT >=to_timestamp(:currentDt,'yyyy-mm-dd hh24:mi:ss') AND jobShdl.END_TIME>=to_timestamp(:currentTime,'yyyy-mm-dd hh24:mi:ss')")
	.append(" AND jobExec.JOBSTRM_SHDL_SEQ_ID=jobShdl.JOBSTRM_SHDL_SEQ_ID AND TRIM(jobExec.JS_EXECUTION_STATUS) IN (:stts)");
	
	public static final StringBuilder getOnlyJobShdlJobStrms=new StringBuilder().
			append("select DISTINCT(jobShdl.JOBSTRM_SHDL_SEQ_ID),jobshdl.JOBSTRM_SEQ_ID,jobshdl.ST_DT,jobShdl.ST_TIME,jobshdl.END_DT,jobShdl.END_TIME ")
			.append(" from TRN_JOBSTRM_SHDL jobShdl where jobShdl.JOBSTRM_SHDL_SEQ_ID not in (select jobExec.JOBSTRM_SHDL_SEQ_ID  from TRN_JOBSTRM_EXEC jobExec)")
			.append(" AND jobShdl.ST_DT <=to_timestamp(:cdt,'yyyy-mm-dd hh24:mi:ss') AND jobShdl.ST_TIME <=to_timestamp(:cdt,'yyyy-mm-dd hh24:mi:ss')")
			.append(" AND jobShdl.END_DT >=to_timestamp(:cdt,'yyyy-mm-dd hh24:mi:ss') AND jobShdl.END_TIME>=to_timestamp(:cdt,'yyyy-mm-dd hh24:mi:ss')");
	
	public static final StringBuilder getFilePath=new StringBuilder().
			append("select js.JOBSTRM_SEQ_ID,js.IF_JOBSTRM_FLG,TRIM(fm.IF_FILE_PATH),TRIM(inf.FILENAME_FORMAT) ")
			.append("from MST_JOB_STERAM js,MST_FILE_MNTRNG fm,MST_INTERFACE inf ")
			.append("where js.JOBSTRM_SEQ_ID =:jsSeqId AND ")
			.append("js.IF_JOBSTRM_FLG='Y' AND ")
			.append("fm.JOBSTRM_SEQ_ID=js.JOBSTRM_SEQ_ID AND ")
			.append("TRIM(inf.IF_NAME)=TRIM(js.IF_ID)");
	
	
	//update jobstream status as waiting for file(B000056 batch-P0003.b.1)
	public static final StringBuilder updateJobStreamStatus=new StringBuilder().
			append("update TRN_JOBSTRM_EXEC t set t.JS_EXECUTION_STATUS=:stts ")
			.append("where t.JOBSTRM_EXEC_SEQ_ID=:jsExeSeqId");
	
	
	
	//update jobstream status as failed for file(B000056 batch-P0003.b.2 and for P0009)
	public static final StringBuilder updateJobStreamStatusAsFailure=new StringBuilder().
			append("update TRN_JOBSTRM_EXEC t set t.JS_EXECUTION_STATUS=:stts,")
			.append("t.END_DT=:currentDt, t.END_TIME=:currentTime ")
			.append("where t.JOBSTRM_EXEC_SEQ_ID=:jsExeSeqId");
	
	//get Job Details for Each JobStream(B000056 Batch-P0004)
	public static final StringBuilder getJobsForJobStream=new StringBuilder().
			append("select mjs.JOBSTRM_SEQ_ID,mjs.JOB_SEQ_ID,mjs.EXEC_SEQ,TRIM(mj.SHELL_PATH),js.POR,TRIM(js.IF_ID),TRIM(js.STAGE_CD),TRIM(js.BUYER_GRP_CD),TRIM(mjs.PARAM) "
					+ " from MST_JOB mj,MST_JOBSTRM_COMBNTN mjs,MST_JOB_STERAM js ")
			.append("where mjs.JOBSTRM_SEQ_ID =:jsSeqId AND ")
			.append(" js.JOBSTRM_SEQ_ID=mjs.JOBSTRM_SEQ_ID AND mj.JOB_SEQ_ID=mjs.JOB_SEQ_ID order by mjs.EXEC_SEQ ASC");
	
	//extract the timeout value from Parameter MST(B000056 Batch-P0006)
	public static final StringBuilder getTimeOutValue=new StringBuilder().
			append("select TRIM(MST_PRMTR.VAL1) from MST_PRMTR where MST_PRMTR.PRMTR_CD=:prmtrCd AND MST_PRMTR.KEY1=:key1");
	
	//update the job details in JobExectnTrn (B000056 Batch-P0007)
	public static final StringBuilder updateJobExecutionTrn=new StringBuilder()
			.append("update TRN_JOB_EXEC t set t.JOB_EXEC_STTS=:stts, t.LOG_FILE_NAME=:logFileName, t.LOG_FILE_PATH=:logFilePath, t.END_DT=:currentDt,t.END_TIME=:currentTime ")
			.append("where t.JOB_EXEC_SEQ_ID=:jobExeSeqId");

	public static final StringBuilder getTimeStampValue = new StringBuilder()
	.append("SELECT max(UPDTD_DT) from MST_OSEI_DTL where POR_CD = '06' and UPDTD_DT<(SELECT max(UPDTD_DT) from MST_OSEI_DTL where POR_CD = '06')");
	
	
	
	
	
	public B000056QueryConstants() {

	}
}
