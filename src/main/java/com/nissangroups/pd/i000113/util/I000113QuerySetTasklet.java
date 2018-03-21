/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015895(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class I000113QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000113 reader to do further processing.
 * 
 * @author z015895
 *
 */
public class I000113QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000113QuerySetTasklet.class.getName());
     
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable finalQuery. */
	private static StringBuilder finalQuery;

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/*
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a super type.
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
   
	/**
	 * P0001 Generate the get Minimum sequence number and insert a record into  Common File Header
	 * Create the custom query and store in ChunkContext
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 *            
	 * @return RepeatStatus object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();

		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String porLstStr = jobParameters.getString(IFConstants.param_porCdLst);

		// updating query in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(IFConstants.SEQ_NO, getMinSeqNoFrmHdr(ifFileId));
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery(ifFileId, porLstStr));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Creates the custom query to extract the Common file header and Common interface data
	 * based on the input parameter
	 * 
	 * @param porCdLstStr 
	 * @param ifFileId 
	 * @param updatedData 
	 * 
	 * @return the string value 
	 */
	private String createCustomQuery(String ifFileId, String porCdLstStr) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000113QueryConstants.baseQuery
				.toString());

		String condition = I000113QueryConstants.baseQueryCondition.toString()
				.replaceAll(IFConstants.param_IfFileID, ifFileId)
				.replace(IFConstants.porCd_Param, porCdLstStr);
		finalQuery.append(condition);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}
    
	/*
	 * P001: Generate the Minimum sequence number from Common file header
	 * @param ifFileId
	 * @return Object(Seqno)
	 */
	private Object getMinSeqNoFrmHdr(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query seqNoQuery = entityManager
				.createNativeQuery(I000113QueryConstants.getMinSeqNoFromHdr
						.toString());
		seqNoQuery.setParameter(IFConstants.ifFileID_Param, ifFileId);
		Object seqNo = seqNoQuery.getSingleResult();
		if (seqNo == null) {
			LOG.info("***No unprocessed data found from header***");
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);			
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return seqNo;
	}

	/**
	 * Gets the entityManager
	 *
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}    
	

}
