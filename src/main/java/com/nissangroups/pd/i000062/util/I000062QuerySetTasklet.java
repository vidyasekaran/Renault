/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : I000062
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.i000062.util;

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

import com.nissangroups.pd.i000113.util.I000113QueryConstants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * @author z015895
 *
 */
public class I000062QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000062QuerySetTasklet.class.getName());

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable final Query String. */
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
	 * P0001 Extract the sequence number and Create the custom query and store in ChunkContext
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
		//String porLstStr = jobParameters.getString(IFConstants.param_porCdLst);

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
		deleteWklyOrdrIfTrn(dynaQuery, ifFileId, seqNo+"");
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}

	/**
	 * Creates the query to fetch data from CMN_FILE_HDR and CMN_INTERFACE_DATA tables
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000062QueryConstants.baseQuery
				.toString());

		String condition = I000062QueryConstants.baseQueryCondition.toString()
				.replaceAll(IFConstants.param_IfFileID, ifFileId);
				//.replace(IFConstants.porCd_Param, porCdLstStr);
		finalQuery.append(condition);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}
	
	/**
	 * This method passes the interface file id to fetch the sequence number from CMN_FILE_HDR table
	 * 
	 * @param ifFileId
	 * @return
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
	 * This method deletes records from TRN_WKLY_ORDR_IF table which matches the interface file id, sequence number passed
	 * 
	 * @param dynaQuery
	 * @param ifFileId
	 * @param seqNo
	 */
	public void deleteWklyOrdrIfTrn(String dynaQuery, String ifFileId, String seqNo) {

		String countQuery = "Select Count(*) from (" + dynaQuery + ")";
		Object result = entityManager.createNativeQuery(countQuery)
				.getSingleResult();
		long count = Long.valueOf(result + "");
		if (count != 0) {
			String queryStr = I000062QueryConstants.deleteWklyOrdrIfTrn
					.toString();
			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter(IFConstants.ifFileID_Param, ifFileId);
			query.setParameter(IFConstants.SEQ_NO, seqNo);
			query.executeUpdate();
		
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
