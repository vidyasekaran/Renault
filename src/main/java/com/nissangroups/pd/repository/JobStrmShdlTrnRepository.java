/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000055
 * Module          :CM Common		
 * Process Outline :JobStremShdlTrn repository																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-02-2016  	  z015883(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.repository;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nissangroups.pd.b000055.util.B000055Constants;
import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000056.util.B000056QueryConstants;
import com.nissangroups.pd.bean.TmpJobSchedule;
import com.nissangroups.pd.model.MstJobSteram;
import com.nissangroups.pd.model.TrnJobstrmShdl;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class JobStrmShdlTrnRepository {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Log LOG = LogFactory.getLog(JobStrmShdlTrnRepository.class);
	CommonUtil commonUtil=new CommonUtil();
	/*
	*@param jobStrmSeqId
	*@return
	*int
	*/
	@Transactional
	public TrnJobstrmShdl insertRecord(long jobStrmSeqId) {
		Query query=entityManager.createNativeQuery(B000056QueryConstants.JOBSTRM_EXEC_SEQ_ID);
		int id=Integer.parseInt(query.getResultList().get(0).toString());
		TrnJobstrmShdl exec=new TrnJobstrmShdl();
		try {
				Timestamp currentTimestamp=commonUtil.currentDateTime();
				exec.setJobstrmShdlSeqId(id);
				exec.setStDt(commonUtil.currentSQLDate());
				exec.setStTime(currentTimestamp);
				exec.setCrtdBy(B000056Constants.S079);
				exec.setCrtdDt(currentTimestamp);
				LOG.info("ShdlTrn Record is going to insert");
				exec.setUpdtdBy(B000056Constants.S079);
				exec.setUpdtdDt(currentTimestamp);
				exec.setJobStrmExecSeqId(B000056Constants.INT_ZERO);
				entityManager.merge(exec);
			
				LOG.info("JobStreamShdl persisted to JobStrmShdlTrn with Id="
					+ exec.getJobstrmShdlSeqId());
			return exec;
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			return null;
		}
	}
	/* use to add given schedule into jobstrmshdltrn
	*@param jobstrm
	*@return int
	*/
	@Transactional
	public int addSchedule(MstJobSteram jobstrm, TmpJobSchedule job) {
		try {
				TrnJobstrmShdl exec=getRecord(job);
				Date d=CommonUtil.addMinutesToDate(15, job.getBaseStDate());
				exec.setStDt(job.getBaseStDate());
				Timestamp t=commonUtil.convertDateToTimestamp(job.getBaseStDate());
				exec.setStTime(t);//start time and date should set according to value cmg from screen
				exec.setEndDt(d);
				exec.setEndTime(commonUtil.convertDateToTimestamp(d));
				entityManager.merge(exec);
				return B000055Constants.INT_ONE;
				
				
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			return B000055Constants.INT_ZERO;
		}
	}
	/*
	*@param job
	*@param leadData
	*void
	*/
	public TrnJobstrmShdl getRecord(TmpJobSchedule job) {
		TrnJobstrmShdl exec=getObject();
		try {
			exec.setOrdrTakeBaseMnth(job.getOrdrTkBsMnth());
			exec.setOrdrTakeBaseWkNo(job.getWkNumber());
			exec.setJobstrmSeqId(job.getJobstrmSeqId());
			Timestamp t=commonUtil.convertDateToTimestamp(job.getBaseStDate());
			exec.setCrtdBy(B000055Constants.B000055_BATCH);
			exec.setCrtdDt(t);
			exec.setUpdtdBy(B000055Constants.B000055_BATCH);
			exec.setUpdtdDt(t);
			return exec;
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			return null;
		}
	}
	/*
	 * to build simple object of this entity
	*@return TrnJobstrmShdl
	 */
	public TrnJobstrmShdl getObject()
	{
		Query query=entityManager.createNativeQuery(B000056QueryConstants.JOBSTRM_EXEC_SEQ_ID);
		int id=Integer.parseInt(query.getResultList().get(0).toString());
		TrnJobstrmShdl exec=new TrnJobstrmShdl();
			exec.setJobstrmShdlSeqId(id);
			return exec;
	}
	/*
	*@param exec
	*void
	*/
	@Transactional
	public TrnJobstrmShdl insertObject(TrnJobstrmShdl exec) {
		 TrnJobstrmShdl jobstrmShdl=entityManager.merge(exec);
		 if(jobstrmShdl!=null)
			 LOG.info(PDMessageConsants.M00189);
		 else
			 LOG.error(PDMessageConsants.M00043);
		 return jobstrmShdl;
	}
	
}
