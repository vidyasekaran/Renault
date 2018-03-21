/**
 * @author z015883
 *System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000056
 * Module          :Jobs
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 * This Class performs all the necessary operations related to JobStrmExecTrn.  
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-Nov-2015  	 z015883(RNTBCI)               New Creation
 *
 */


package com.nissangroups.pd.repository;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000056.util.B000056QueryConstants;
import com.nissangroups.pd.model.TrnJobstrmExec;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * @author z015883
 * 
 */
 
public class JobStrmExecTrnRepository {

	 @PersistenceContext 
	 private EntityManager entityManager;


	 private static final Log LOG = LogFactory.getLog
			(JobStrmExecTrnRepository.class);

	 
	public JobStrmExecTrnRepository() {
		super();
	}


	CommonUtil commonUtil = new CommonUtil();

	
	// insert new jobstreamexec details (B000056 Batch-P0002)
	
	/*
	*@param stts
	*@param jobStrmShdlSeqId
	*@param jobStrmExecSeqId
	*@return TrnJobstrmExec
	*/
	@org.springframework.transaction.annotation.Transactional
	public TrnJobstrmExec insertJobStrmExecTrn(String stts,
			BigDecimal jobStrmShdlSeqId, long jobStrmExecSeqId) {
		TrnJobstrmExec exec= entityManager.find(TrnJobstrmExec.class, jobStrmExecSeqId) ;
		Query query=entityManager.createNativeQuery(B000056QueryConstants.JOBSTRM_EXEC_SEQ_ID);
		int id=Integer.parseInt(query.getResultList().get(0).toString());
		try {
			
			if(exec!=null)
			{
				LOG.info("Seq Id is Allready Available,so going to update in JobStrmExec "+jobStrmExecSeqId);
			}
			else
			{
				exec=new TrnJobstrmExec();
				exec.setJobstrmExecSeqId(id);
				exec.setStDt(commonUtil.currentSQLDate());
				exec.setStTime(commonUtil.currentDateTime());
				exec.setCrtdBy(B000056Constants.BATCH_56);
				exec.setCrtdDt(commonUtil.currentDateTime());
				LOG.info("SeqId is not available,going to insert");
				 
			}
				
			 exec.setJobstrmShdlSeqId((jobStrmShdlSeqId));
			
			exec.setJsExecutionStatus(stts);
			exec.setUpdtdBy(B000056Constants.BATCH_56);
			exec.setUpdtdDt(commonUtil.currentDateTime());
			//LOG.info("Entity "+entityManager);
			 
			entityManager.merge(exec);
			
			LOG.info("JobStream persisted to JobStrmExecTrn with Id "
					+ exec.getJobstrmExecSeqId());
			
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
		}
		return exec;
	}

	// update table based on status (B000056 Batch-P0003.b)
	
	/*
	*@param stts
	*@param jobStrmExecId
	*@return int
	*/
	@org.springframework.transaction.annotation.Transactional
	public int updateJobStrmExecTrn(String stts, long jobStrmExecId) {
		int result = 0;
		 
		TrnJobstrmExec exec = entityManager.find(TrnJobstrmExec.class,
				jobStrmExecId);
		if (exec != null) {
			if (stts.equalsIgnoreCase(B000056Constants.FAILURE)
					|| stts.equalsIgnoreCase(B000056Constants.SUCCESS)) {

				exec.setEndDt(commonUtil.currentSQLDate());
				exec.setEndTime(commonUtil.currentDateTime());
				 

			}

			exec.setUpdtdBy(B000056Constants.BATCH_56);
			exec.setUpdtdDt(commonUtil.currentDateTime());
			exec.setJsExecutionStatus(stts);
			exec.setUpdtdBy(B000056Constants.BATCH_56);
			exec.setUpdtdDt(commonUtil.currentDateTime());
			 

			try {
				//entityManager.flush();
				entityManager.persist(exec);
				result = B000056Constants.INT_ONE;
			} catch (Exception e) {
				result = B000056Constants.INT_ZERO;
				LOG.error(PDConstants.EXCEPTION+e);
			}

		}
		return result;
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return the entityManagerFactory
	 */
	 
}
