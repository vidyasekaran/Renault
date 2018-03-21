/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000011
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Control the Order Take on Local Systems 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000011.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.nissangroups.pd.i000011.bean.I000011InputParam;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000011 reader to do further processing.
 * 
 * @author z014029
 */

public class I000011QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000011QuerySetTasklet.class.getName());

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable I000011 Input Parameter List . */
	private List<I000011InputParam> paramList;

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
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// Generate the sequence no and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');

		// Updating query and sequence no in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method fetching the maximum order take base month value against the
	 * por code
	 * 
	 * @param String
	 *            interfacePorCd
	 */
	public String getBasePeriodWeekly(String interfacePorCd) {
		Query wkQuery = entityManager
				.createNativeQuery(I000011QueryConstants.fetchLatestOrdrTkBsOrdData
						.toString());
		wkQuery.setParameter("porCd", interfacePorCd);

		return wkQuery.getSingleResult().toString();
	}

	/**
	 * This method used to create and return custom query based on the list of input param, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns.
	 * 
	 * @return the query
	 * @throws ParseException
	 */
	private String createCustomQuery() throws ParseException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000011QueryConstants.baseQuery.toString() + "(");

		for (I000011InputParam inputParams : paramList) {

			String ablshDate = CommonUtil.prdMnthCal(
					getBasePeriodWeekly(inputParams.getPorCd()),
					inputParams.getPrdLmt());

			String whereClause = I000011QueryConstants.baseQueryCondition1
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
			whereClause = (("*").equals(inputParams.getPrdLmt())) ? whereClause
					.replaceAll(IFConstants.param_prdLmt, " ") : whereClause
					.replaceAll(IFConstants.prdLmt_Param, "'" + ablshDate
							+ "' AND ");
			whereClause = (("*").equals(inputParams.getEndItmSttsCd())) ? whereClause
					.replaceAll(IFConstants.param_endItmSttsCd, " ")
					: whereClause.replaceAll(IFConstants.endItmSttsCd_Param,
							"(" + inputParams.getEndItmSttsCd() + ") AND ");

			finalQuery.append("(" + whereClause + ") OR ");
			int ind = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(ind, ind + I000011QueryConstants.replacebyThree, " ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + I000011QueryConstants.replacebyTwo, "");
		finalQuery.append(")) a");

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters from the formatted String
	 */
	private static List<I000011InputParam> deformatInputs(String inputStr) {

		List<I000011InputParam> list = new ArrayList<I000011InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000011InputParam inputParam = new I000011InputParam();
			inputParam.setPorCd(val[0]);        // POR CD
			inputParam.setOcfRgnCd(val[1]);     // OCF REGION CODE
			inputParam.setOcfByrCd(val[2]);     // OCF BUYER CODE
			inputParam.setRhqCd(val[3]);        // RHQ CODE
			inputParam.setByrGrpCd(val[4]);     // BUYER GROUP CODE
			inputParam.setPrdLmt(val[5]);       // PRODUCTION LIMIT
			inputParam.setEndItmSttsCd(val[6]); // END ITEM STATUS CODE
			list.add(inputParam);
		}
		return list;
	}
}