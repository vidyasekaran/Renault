/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.util;

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
 * This Class I000055QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also create the custom query and store in ChunkContext for I000055 reader to do further processing.
 * 
 * @author z015895
 *
 */
public class I000055QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG */
	private static final Log LOG = LogFactory	
			.getLog(I000055QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** final Query String */
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
	 * P0001 Creates the custom query and store in ChunkContext
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
		String porCd = jobParameters
				.getString(IFConstants.POR_CD);

		// updating query in context
		Object seqNo = getMinSeqNoFrmHdr(ifFileId);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(IFConstants.SEQ_NO, seqNo);
		String dynaQuery = createCustomQuery(ifFileId);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", dynaQuery);

		//delete the table before insert
		deleteDailyOcfLmtIfTrn(dynaQuery, porCd);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}

	/**
	 * P0001: Extract the data form common file header and cmn interface tables based on conditions
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000055QueryConstants.baseQuery
				.toString());

		String condition = I000055QueryConstants.baseQueryCondition.toString()
				.replaceAll(IFConstants.param_IfFileID, ifFileId);				
		finalQuery.append(condition);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}
    
	/**
	 * P0001 Extract the Maximum seq number from cmn file hdr based on
	 * conditions
	 * @param ifFileId
	 */
	private Object getMinSeqNoFrmHdr(String ifFileId) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query seqNoQuery = entityManager
				.createNativeQuery(I000055QueryConstants.getMaxSeqNoFromHdr
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
	 * P0002 Delete all the records from the DAILY_OCF_LIMIT_IF_TRN table based
	 * on the given POR Code
	 * @param dynaQuery
	 * @param porCd
	 */
	public void deleteDailyOcfLmtIfTrn(String dynaQuery, String porCd) {

		String countQuery = "Select Count(*) from (" + dynaQuery + ")";
		Object result = entityManager.createNativeQuery(countQuery)
				.getSingleResult();
		long count = Long.valueOf(result + "");
		if (count != 0) {
			String queryStr = I000055QueryConstants.deleteDailyOcfLmtIfTrn
					.toString();				
			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter(IFConstants.POR_CD, porCd);
			query.executeUpdate();
			LOG.info("***"+count +":Records deleted***");
		}

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
