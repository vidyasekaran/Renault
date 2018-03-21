/**
 * @author z015883
 *System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000056
 * Module          :Jobs
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 * This Class performs all the necessary operations related to JobExecTrn.  
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nissangroups.pd.b000056.util.B000056Constants;
import com.nissangroups.pd.b000056.util.B000056QueryConstants;
import com.nissangroups.pd.model.TrnJobExec;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class JobExecTrnRepository {

	 
	public JobExecTrnRepository()
	{
		
	}
	@PersistenceContext 
	private EntityManager entityManager;
 
	private static final Log LOG = LogFactory.getLog(JobExecTrnRepository.class);
	@Autowired
	CommonUtil commonUtil;
 
	@Autowired(required=false)
	private BatchJobRepository batchJobRepository;
	
	
	/** This Method is to insert Record into TrnJobExec
	 * @param jobSeqId
	 * @param jobStrmExecSeqId
	 * @param stts
	 * @return TrnJobExec
	 */
	@Transactional
	public  TrnJobExec insertJobExecTrn(long jobSeqId, long jobStrmExecSeq,String stts) {
		TrnJobExec exec=null; 
		try
		 {
		Query query=entityManager.createNativeQuery(B000056QueryConstants.JOBSTRM_EXEC_SEQ_ID);
		int id=Integer.parseInt(query.getResultList().get(0).toString());
		 exec=new TrnJobExec();
		 exec.setJobExecSeqId(id);
		 exec.setJobSeqId(BigDecimal.valueOf(jobSeqId));
		 exec.setJobstrmExecSeqId(BigDecimal.valueOf(jobStrmExecSeq));
		 exec.setCrtdBy(B000056Constants.BATCH_56);
		 exec.setCrtdDt(commonUtil.currentDateTime());
		 exec.setJobExecStts(stts);
		 exec.setStDt(commonUtil.currentSQLDate());
		 exec.setStTime(commonUtil.currentDateTime());
		 exec.setUpdtdBy(B000056Constants.BATCH_56);
		 exec.setUpdtdDt(commonUtil.currentDateTime());
		
			 entityManager.persist(exec);
			 entityManager.flush();
			 LOG.info("Inserted into JobExecTrn with ID [ "+exec.getJobExecSeqId()+" ]");
			  
		 }
		 catch(Exception e)
		 {
			 exec=null;
			 LOG.info("Unable to Insert into JobExeTrn");
			 LOG.error(PDConstants.EXCEPTION+e);
		 }
		 
		 return exec;
	}

	/** method to update JobExecTrn details
	 * @param data
	 * @param jobExecSeqId
	 * @return int
	 */
	@Transactional
	public int updateJobExecTrnDetails(String[] data, long jobExecSeqId) {
		 TrnJobExec exec= entityManager.find(TrnJobExec.class, jobExecSeqId);
		 LOG.info("Job to update is "+exec);
		 exec.setJobExecStts(data[0]);
		 exec.setLogFilePath(data[1]);
		 exec.setLogFileName(data[2]);
		 exec.setEndDt(commonUtil.currentSQLDate());
		 exec.setEndTime(commonUtil.currentDateTime());
		 exec.setUpdtdBy(B000056Constants.BATCH_56);
		 exec.setUpdtdDt(commonUtil.currentDateTime());
		 int result=B000056Constants.INT_ZERO;
		 try
		 {
			 entityManager.flush();
			 entityManager.merge(exec);
			 
			 result=B000056Constants.INT_ONE;
			 //LOG.info(result+" Row Updated in JobExecTrn Table");
		 }
		 catch(Exception e)
		 {
			 result=B000056Constants.INT_ZERO;
			 LOG.error(PDConstants.EXCEPTION+e);
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

