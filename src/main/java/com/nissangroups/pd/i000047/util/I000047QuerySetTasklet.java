package com.nissangroups.pd.i000047.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00075;
import static com.nissangroups.pd.util.PDMessageConsants.M00164;

import java.util.List;

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
import com.nissangroups.pd.util.PDConstants;

public class I000047QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000047QuerySetTasklet.class.getName());

	@Autowired(required = false)
	IfCommonUtil commonutility;

	private static StringBuilder finalQuery;

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a super type.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * Extract the sequence number and Create the custom query and store in
	 * ChunkContext
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
		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();

		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String porCd = jobParameters.getString(IFConstants.POR_CD);

		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(IFConstants.porCd_Param, chkPorCd(porCd));

		// Updating query in context
		Object seqNo = getMaxSeqNoFrmHdr(ifFileId);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		String dynaQuery = createCustomQuery(ifFileId);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", dynaQuery);

		/**
		 * P0002 - Delete the POR CD against the input POR CD on
		 * WEEK_NO_CALENDAR_MST table before inserting the processed values.
		 */
		deleteWKNoClndr(dynaQuery, porCd);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}

	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000047QueryConstants.baseQuery
				.toString());

		String condition = I000047QueryConstants.baseQueryCondition.toString()
				.replaceAll(IFConstants.param_IfFileID, ifFileId);
		finalQuery.append(condition);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/**
	 * Getting the maximum sequence number from common file header.
	 * 
	 * @param String
	 *            ifFileId
	 */
	private Object getMaxSeqNoFrmHdr(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Query seqNoQuery = entityManager
				.createNativeQuery(I000047QueryConstants.getMaxSeqNoFromHdr
						.toString());
		seqNoQuery.setParameter(IFConstants.ifFileID_Param, ifFileId);
		Object seqNo = seqNoQuery.getSingleResult();
		if (seqNo == null) {
			LOG.info("There is No Unprocessed Data found in Common File Header");
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return seqNo;
	}

	/**
	 * P0001 - Checking the POR CD from MST_POR table.
	 * 
	 * @param String
	 *            porCd
	 */
	@SuppressWarnings("unchecked")
	private String chkPorCd(String porCd) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Query porCdQuery = entityManager
				.createNativeQuery(I000047QueryConstants.porCdChk.toString());
		porCdQuery.setParameter(IFConstants.POR_CD, porCd);

		List<Object> porCDdVal = porCdQuery.getResultList();
		if (porCDdVal.isEmpty()) {
			String errMsg = M00075.replace(PDConstants.ERROR_MESSAGE_1,
					PDConstants.IF_ID_47);
			LOG.error(errMsg);

			LOG.info("No PORCD from MST_POR Table");
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return porCd;
	}

	/**
	 * P0002 - Delete the POR CD against the input POR CD on
	 * WEEK_NO_CALENDAR_MST table before inserting the processed values.
	 * 
	 * @param String
	 *            dynaQuery
	 * @param String
	 *            porCd
	 * 
	 */
	public void deleteWKNoClndr(String dynaQuery, String porCd)
			throws Exception {

		String countQuery = "Select Count(*) from (" + dynaQuery + ")";
		Object result = entityManager.createNativeQuery(countQuery)
				.getSingleResult();
		long count = Long.valueOf(result + "");

		if (count != 0) {
			String queryStr = I000047QueryConstants.deleteWKNoClndrMst
					.toString();
			//queryStr = queryStr.replace(":" + IFConstants.POR_CD, porCd);
			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter(IFConstants.POR_CD, porCd);
			query.executeUpdate();
			LOG.info(count + " : Records got deleted from MST_WK_NO_CLNDR");
		} else {
			String errMsg = M00164
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.IF_ID_47)
					.replace(PDConstants.ERROR_MESSAGE_2, porCd)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MST_WK_NO_CLNDR);
			LOG.error(errMsg);
			commonutility.setRemarks(errMsg);
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}