/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000083
 * Module          :OR ORDERING
 * Process Outline :Send Weekly Production Order to Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000083.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00196;
import static com.nissangroups.pd.util.PDMessageConsants.M00160;

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

/**
 *This class implements Tasklet which will call the execute method repeatedly until 
 * it either returns RepeatStatus.FINISHED or throws an exception to signal a failure 
 * 
 * @author z015895
 *
 */
public class I000083QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(I000083QuerySetTasklet.class.getName());

	/** Variable commonutility. */
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

	/*
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Creates the custom query and store in ChunkContext
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
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
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);
		String porCd = jobParameters.getString(IFConstants.POR_CD);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, IFConstants.SEND_TRANSACTION_TYPE);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery(ifFileId, seqNo, porCd));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create final query based on the list of input param
	 * 
	 * @return the query
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String createCustomQuery(String ifFileId, long seqNo, String porCd)
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String prdFlg = "";
		
		String[] ordrTakVal = getOrdrTakVal(ifFileId, seqNo, porCd);
		
		List<Object> prdFlgLst = entityManager.createNativeQuery(
				I000083QueryConstants.prodNoCheckQuery.toString().replace(":" + IFConstants.POR_CD, porCd))
				.getResultList();
		if (prdFlgLst.size() != 0) {
			prdFlg = prdFlgLst.get(0).toString();
		}else{
			String remarks = M00160
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.PROD_ORDR_NO_FLG)
					.replace(PDConstants.ERROR_MESSAGE_3,porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.MESSAGE_MST_PARAMETER);
			commonutility.setRemarks(remarks);
			commonutility.setErrFlg(true);
			commonutility.updateCmnFileHdr(ifFileId, seqNo,
					PDConstants.INTERFACE_FAILURE_STATUS, remarks);
			CommonUtil.stopBatch();
		}
		
		String query = (!(IFConstants.PRMTR_VAL_YES).equalsIgnoreCase(prdFlg)) ? I000083QueryConstants.baseQueryWithQrpBy
				.toString() : I000083QueryConstants.baseQuery.toString();
		finalQuery = new StringBuilder().append(query
				.replace(":" + IFConstants.POR_CD, porCd)
				.replace(":" + IFConstants.ORDR_TAK_BAS_MNTH, ordrTakVal[0])
				.replace(":" + IFConstants.ORDR_TAK_BAS_WK_NO, ordrTakVal[1]));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/**
	 * This is method used to extract Max Order Take Base Month and Order Take Week No. from database
	 * 
	 * @param ifFileId
	 * @param seqNo
	 * @param porCd
	 * @return String[] Order Take Base Month and Order Take Week No.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String[] getOrdrTakVal(String ifFileId, long seqNo, String porCd)
			throws Exception {

		String[] ordrTakVal = new String[2];
		ordrTakVal[0] = "";
		ordrTakVal[1] = "";

		String ordrTakQuery = I000083QueryConstants.ordrTakBasMnthQuery
				.toString();
		Query query = entityManager.createNativeQuery(ordrTakQuery);
		query.setParameter(IFConstants.POR_CD, porCd);
		List<Object[]> ordrTakValLst = query.getResultList();

		StringBuilder wkno = new StringBuilder();
		if (ordrTakValLst.size() != 0) {
			ordrTakVal[0] = ordrTakValLst.get(0)[0] + "";
			for (Object[] obj : ordrTakValLst) {
				wkno.append("'" + obj[1] + "',");
			}
			int ind = wkno.toString().lastIndexOf(",");
			wkno = wkno.replace(ind, ind + 1, "");
			ordrTakVal[1] = wkno.toString();
		} else {
			String remarks = M00196
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MESSAGE_WEEKLY_TAKE_BASE_PERIOD)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.POR_CD+":"+porCd);
			commonutility.setRemarks(remarks);
			commonutility.setErrFlg(true);
			commonutility.updateCmnFileHdr(ifFileId, seqNo,
					PDConstants.INTERFACE_FAILURE_STATUS, remarks);
		}

		return ordrTakVal;
	}

}
