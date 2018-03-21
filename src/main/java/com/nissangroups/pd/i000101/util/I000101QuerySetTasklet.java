/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.util;

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
 * This Class is used to set the SQL Query which will be used by I000101 Reader to fetch the data for this interface
 * 
 * @author z014676
 * 
 */
public class I000101QuerySetTasklet implements Tasklet, InitializingBean {
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000101QuerySetTasklet.class.getName());
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private static StringBuilder finalQuery;

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/*
	 * This method is used to set the SQL Query which will be used by I000101 Reader to fetch the data for this interface
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
		// input porcd value
		String jobInputs = jobParameters.getString(IFConstants.porCd_Param);

		if (",*".equals(jobInputs) || "all".equals(jobInputs)) {
			jobInputs = jobInputs.replaceFirst(",", "");
			jobInputs = getPorcd();
			jobInputs = String.valueOf(jobInputs);
		}
		/*
		 * interface file id I000101
		 */
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);

		/** P002: generate seqno and insert header data */
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, "", 'S');

		/** P002: updating query and seqno in context */
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery(jobInputs));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;

	}

	/**
	 * P0003 Creates the query to Extract the Physical Pipeline data, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case.
	 * 
	 * @param params
	 * @param updatedData
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String params) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000101QueryConstants.baseQuery.toString());

		String whereClause = I000101QueryConstants.baseQueryCondition
				.toString();
		whereClause = (("*").equals(params)) ? whereClause.replaceAll(
				IFConstants.porCd_Param, " ") : whereClause.replaceAll(
				IFConstants.porCd_Param, params);

		finalQuery.append("(" + whereClause + ")  ");
		finalQuery.append(") a");

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/** select all porcd values */
	public static String getPorcd() {
		return I000101QueryConstants.porQuery.toString();
	}
}
