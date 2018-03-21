/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000014
 * Module          :CM Common
 * Process Outline :Send OEI Feature OCF Interface to NSC (Standard) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000014.util;

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

import com.nissangroups.pd.i000014.bean.I000014InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class implements Tasklet which will call the execute method repeatedly
 * until it either returns RepeatStatus.FINISHED or throws an exception to
 * signal a failure
 * 
 * @author z015895
 *
 */
public class I000014QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(I000014QuerySetTasklet.class.getName());

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable paramList. */
	private List<I000014InputParam> paramList;

	/** Variable finalQuery. */
	private static StringBuilder finalQuery;

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
	 * P0001 Generate the sequence number and insert a record in Common File
	 * Header Creates the custom query and store in ChunkContext (non-Javadoc)
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
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);

		/**
		 * Process Id - P0001 Move the Input parameter into the array of
		 * objects.
		 */
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		/**
		 * Process Id - P0002 INSERTING OSEI Feature INTERFACE FILE DETAIL DATA
		 * INTO COMMON FILE HEADER MST.
		 */
		commonutility.insertCmnFileHdr(ifFileId, seqNo,
				IFConstants.EMPTY_FILENAME, IFConstants.SEND_TRANSACTION_TYPE);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create final query based on the list of input param,
	 * Please note the * in a.* returns only the columns selected in the
	 * subquery so it does not return all the columns.
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("+I000014QueryConstants.baseQuery
				.toString());
		
		for (I000014InputParam inputParams : paramList) {
						
			String whereClause = I000014QueryConstants.baseQueryCondition.toString();
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getBuyerGrpCd())) ? whereClause.replaceAll(IFConstants.param_buyerCD," ") : 
				whereClause.replaceAll(IFConstants.buyerCD_Param,"'"+inputParams.getBuyerGrpCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getRhqCd())) ? whereClause.replaceAll(IFConstants.param_rhqCd," ") : 
				whereClause.replaceAll(IFConstants.rhqCd_Param,"'"+inputParams.getRhqCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfRegionCd())) ? whereClause.replaceAll(IFConstants.param_ocfRegionCd," ") : 
				whereClause.replaceAll(IFConstants.ocfRegionCd_Param,"'"+inputParams.getOcfRegionCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfBuyerCd())) ? whereClause.replaceAll(IFConstants.param_ocfBuyerGrpCd," ") : 
				whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,"'"+inputParams.getOcfBuyerCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getPorCd())) ? whereClause.replaceAll(IFConstants.param_porCd," ") : 
				whereClause.replaceAll(IFConstants.porCd_Param,"'"+inputParams.getPorCd()+IFConstants.QRYSTRNG);

			finalQuery.append("(" + whereClause + ") OR ");

		}
		
		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(") a");

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters
	 */
	private static List<I000014InputParam> deformatInputs(String inputStr) {

		List<I000014InputParam> list = new ArrayList<I000014InputParam>();
		List<String> mainList = Arrays.asList(inputStr
				.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000014InputParam inputParam = new I000014InputParam();
			inputParam.setPorCd(val[0]);
			inputParam.setOcfRegionCd(val[1]);
			inputParam.setOcfBuyerCd(val[2]);
			inputParam.setRhqCd(val[3]);
			inputParam.setBuyerGrpCd(val[4]);
			list.add(inputParam);
		}

		return list;
	}

}
