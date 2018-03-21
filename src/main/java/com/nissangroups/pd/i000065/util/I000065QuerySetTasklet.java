/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000065.bean.I000065InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class is used to set the SQL Query which will be used by I0000065 Reader to fetch the data for this interface
 * 
 * @author z014676
 * 
 */
public class I000065QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000065QuerySetTasklet.class.getName());
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** this object using store the list of values based on the input parameters */
	private List<I000065InputParam> paramList;

	private static StringBuilder finalQuery;

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/*
	 * This method is used to set the SQL Query which will be used by I0000065 Reader to fetch the data for this interface
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
		String porcd = jobParameters.getString(IFConstants.porCd_Param);
		String buyergrpid = jobParameters
				.getString(IFConstants.buyer_buyerGrpCD_Param);

		paramList = new ArrayList<I000065InputParam>();
		I000065InputParam inputParam = new I000065InputParam();
		inputParam.setPorCd(porcd);
		inputParam.setBuyerGrpCd(buyergrpid);
		paramList.add(inputParam);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);
		/** generate seqno and insert header data */
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');

		/* updating query and seqno in context */
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Creates the query to extract the Weekly order error interface trn table, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case.
	 * 
	 * @param paramList
	 * @param updatedData
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000065QueryConstants.baseQuery.toString());

		for (I000065InputParam inputParams : paramList) {

			String whereClause = I000065QueryConstants.baseQueryCondition
					.toString();
			whereClause = whereClause.replaceAll(
					IFConstants.buyer_buyerGrpCD_Param,
					"'" + inputParams.getBuyerGrpCd() + "'");
			whereClause = whereClause.replaceAll(IFConstants.porCd_Param, "'"
					+ inputParams.getPorCd() + "'");

			finalQuery.append("(" + whereClause + ") OR ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(") a");

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

}
