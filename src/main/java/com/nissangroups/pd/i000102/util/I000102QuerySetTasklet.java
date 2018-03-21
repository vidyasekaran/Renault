/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000102
 * Module          : CM COMMON					
 * Process Outline : Send Logical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-12-2014  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000102.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000113.util.I000113QuerySetTasklet;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class is used to set the SQL Query which will be used by I000102 Reader to fetch the data for this interface
 * 
 * @author z015847
 *
 */
public class I000102QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000113QuerySetTasklet.class.getName());
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private static StringBuilder finalQuery;

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	@Override
	public void afterPropertiesSet() throws Exception {

		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/**
	 * P0002 Generate the sequence number and insert a record in Common File Header
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
		String porLstStr = jobParameters.getString(IFConstants.INPUT_PARAM);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);

		commonutility.insertCmnFileHdr(ifFileId, seqNo, "", 'S');

		// Updating query in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);

		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery(ifFileId, porLstStr));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * Creates the query, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case.
	 * 
	 * @param ifFileId
	 * @param porCdLstStr
	 * @return the query
	 */
	private String createCustomQuery(String ifFileId, String porCdLstStr) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000102QueryConstants.baseQuery.toString());

		finalQuery.append(I000102QueryConstants.baseQueryCondition.toString());

		StringBuilder condition = new StringBuilder();

		if (null != porCdLstStr && !porCdLstStr.isEmpty()) {

			condition.append(I000102QueryConstants.inQueryCondition);
			finalQuery.append(condition.toString().replaceAll(
					IFConstants.porCd_Param, porCdLstStr));

		}

		finalQuery.append(") a");
		LOG.info("*****Final Query *****" + finalQuery);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
