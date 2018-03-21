/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-I000002
 * Module          :CM Common
 * Process Outline :
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *19-11-2015      z015887(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000002.util;

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
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * Class I000002QuerySetTasklet which will call the execute method
 * repeatedly until it either returns RepeatStatus.FINISHED or throws an exception to signal a failure
 * 
 * @author z016127
 *
 */
public class I000002QuerySetTasklet implements Tasklet, InitializingBean {
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000002QuerySetTasklet.class.getName());
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private static StringBuilder finalQuery;
	/** interface Variable Production region code */
	private static String prodRegCodes;

	/*
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
	
	}

	/*
	 * 
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Create the custom query and store in ChunkContext
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		/** getting job param */
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		prodRegCodes = jobParameters.getString(IFConstants.INPUT_PARAM);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		/** generate seqno and insert header data */
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, "", 'S');

		/** updating query and seqno in context */
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Creates the query to Extract the Buyer master data.
	 * 
	 * @param prodRegCodes
	 * @param updatedData
	 * 
	 * @return the query
	 */
	private static synchronized String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000002QueryConstants.baseQuery
				.toString());
		finalQuery.append(I000002QueryConstants.baseQueryCondition.toString()
				.replace(":prodRegCd", prodRegCodes));

		LOG.info(finalQuery.toString());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

}
