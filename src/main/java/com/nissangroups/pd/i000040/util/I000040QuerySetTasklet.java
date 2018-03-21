/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000040
 * Module                 : OR Ordering		
 * Process Outline     	  : Send A0 ETA Designated parameter to PCS															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000040.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

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

public class I000040QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000040QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable insert header */
	boolean insrthdr;

	@Override
	public void afterPropertiesSet() throws Exception {

		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/**
	 * P0001 Extract the sequence number and Create the custom query and store
	 * in ChunkContext
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
		String porCd = jobParameters.getString(PDConstants.PRMTR_PORCD);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		insrthdr = commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName,
				'S');
		if (!insrthdr) {
			CommonUtil.stopBatch();
		}
		/** Variable query */
		String query = buildQuery(porCd);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", query);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * Extract data from A0 ETA Designated Parameter data based on POR and Conditions, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns.
	 * P0002
	 * @param porCd2
	 * @return finalQuery
	 */
	private String buildQuery(String porCd2) {
		StringBuilder finalQuery = new StringBuilder()
				.append("select rownum, a.* from ( ")
				.append(I000040QueryConstants.fetchA0EtaDsgntdPrmtr)
				.append(I000040QueryConstants.whereClause)
				.append(I000040QueryConstants.porVlaue).append(porCd2)
				.append(" ) a ");

		return finalQuery.toString();
	}

}
