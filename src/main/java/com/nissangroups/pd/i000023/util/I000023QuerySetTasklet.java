/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000023
 * Module          :Send_OSEI_Frozen_Interface_to_NSC

 * Process Outline : Send the OSEI frozen master details to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000023.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.nissangroups.pd.i000023.bean.I000023InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000023 reader to do further processing.
 * 
 * @author z014029
 */
public class I000023QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000023QuerySetTasklet.class.getName());

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable I000023 Input Parameter List . */
	private List<I000023InputParam> paramList;

	/** Variable final query */
	private static StringBuilder finalQuery;

	/*
	 * Indicates that a method declaration is intended to override a method declaration in a supertype.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * Not necessary to implement this abstract method in this case
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/*
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Create the custom query and store in ChunkContext
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);

		// Generate sequence no and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, "", 'S');

		// Updating query and sequence no in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create and return custom query based on the list of input param.
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append(I000023QueryConstants.baseQuery
				.toString());

		for (I000023InputParam inputParams : paramList) {

			String whereClause = I000023QueryConstants.baseQueryCondition1
					.toString();
			whereClause = (("*").equals(inputParams.getByrGrpCd())) ? whereClause
					.replaceAll(IFConstants.param_buyer_buyerGrpCD, " ")
					: whereClause.replaceAll(
							IFConstants.buyer_buyerGrpCD_Param, "'"
									+ inputParams.getByrGrpCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getRhqCd())) ? whereClause
					.replaceAll(IFConstants.param_rhqCd, " ") : whereClause
					.replaceAll(IFConstants.rhqCd_Param,
							"'" + inputParams.getRhqCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getOcfRgnCd())) ? whereClause
					.replaceAll(IFConstants.param_ocfRegionCd, " ")
					: whereClause.replaceAll(IFConstants.ocfRegionCd_Param, "'"
							+ inputParams.getOcfRgnCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getOcfByrCd())) ? whereClause
					.replaceAll(IFConstants.param_ocfBuyerGrpCd, " ")
					: whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,
							"'" + inputParams.getOcfByrCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getPorCd())) ? whereClause
					.replaceAll(IFConstants.param_porCd, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param,
							"'" + inputParams.getPorCd() + "' AND ");

			finalQuery.append("(" + whereClause + ") OR ");
			int ind = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(ind, ind + 3, " ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(" AND " + I000023QueryConstants.baseQueryCondition2);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters from the formatted String
	 */
	private static List<I000023InputParam> deformatInputs(String inputStr) {

		List<I000023InputParam> list = new ArrayList<I000023InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000023InputParam inputParam = new I000023InputParam();
			inputParam.setPorCd(val[0]);
			inputParam.setOcfRgnCd(val[1]);
			inputParam.setOcfByrCd(val[2]);
			inputParam.setRhqCd(val[3]);
			inputParam.setByrGrpCd(val[4]);
			list.add(inputParam);
		}
		return list;
	}
}