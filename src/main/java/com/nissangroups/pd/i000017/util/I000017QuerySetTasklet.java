/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000017
 * Module          :SP SPEC MASTER
 * Process Outline :Send OSEI Feature CCF Interface to NSC (Standard Layout) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000017.util;

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

import com.nissangroups.pd.i000017.bean.I000017InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class is used to form the dynamic query based on the input arguments
 * 
 * @author z015895
 *
 */
public class I000017QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(I000017QuerySetTasklet.class.getName());

	/** Variable commonutility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable paramList. */
	private List<I000017InputParam> paramList;

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
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Creates the custom query and store in ChunkContext
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);
		
		/** Process Id - P0001 Move the Input parameter into the array of objects.*/
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		
		/** Process Id - P0002 INSERTING INTERFACE FILE DATA INTO COMMON FILE HEADER MST.*/
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, IFConstants.SEND_TRANSACTION_TYPE);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create final query based on the list of input param, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns.
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		finalQuery = new StringBuilder().append("select rownum, a.* from ("+I000017QueryConstants.baseQuery
				.toString()+"(");		

		for (I000017InputParam inputParams : paramList) {
			
			String whereClause = I000017QueryConstants.baseQueryCondition.toString();
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getBuyerGrpCd())) ? whereClause.replaceAll(IFConstants.param_buyer_buyerGrpCD," ") : 
				whereClause.replaceAll(IFConstants.buyer_buyerGrpCD_Param,"'"+inputParams.getBuyerGrpCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getRhqCd())) ? whereClause.replaceAll(IFConstants.param_rhqCd," ") : 
				whereClause.replaceAll(IFConstants.rhqCd_Param,"'"+inputParams.getRhqCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfRegionCd())) ? whereClause.replaceAll(IFConstants.param_ocfRegionCd," ") : 
				whereClause.replaceAll(IFConstants.ocfRegionCd_Param,"'"+inputParams.getOcfRegionCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfBuyerCd())) ? whereClause.replaceAll(IFConstants.param_ocfBuyerGrpCd," ") : 
				whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,"'"+inputParams.getOcfBuyerCd()+IFConstants.QRYSTRNG);
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getPorCd())) ? whereClause.replaceAll(IFConstants.param_porCd," ") : 
				whereClause.replaceAll(IFConstants.porCd_Param,"'"+inputParams.getPorCd()+IFConstants.QRYSTRNG);
			finalQuery.append("(" + whereClause + ") OR ");
			int ind = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(ind, ind + 3, " ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(")"+I000017QueryConstants.baseQueryCondition1.toString());
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
	private static List<I000017InputParam> deformatInputs(String inputStr) {

		List<I000017InputParam> list = new ArrayList<I000017InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000017InputParam inputParam = new I000017InputParam();
			inputParam.setPorCd(val[0]);       //POR CD
			inputParam.setOcfRegionCd(val[1]); //OCF Region Code
			inputParam.setOcfBuyerCd(val[2]);  //OCF Buyer Code
			inputParam.setRhqCd(val[3]);       //RHQ Code
			inputParam.setBuyerGrpCd(val[4]);  //Buyer Group Code
			list.add(inputParam);
		}

		return list;
	}

}
