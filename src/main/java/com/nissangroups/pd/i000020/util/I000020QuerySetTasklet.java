/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000020
 * Module          :CM Common
 * Process Outline :Send OSEI Production Type Master Interface to NSC(Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000020.util;

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

import com.nissangroups.pd.i000020.bean.I000020InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class is used to form the dynamic query based on the input arguments
 * 
 * @author z015895
 *
 */
public class I000020QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(I000020QuerySetTasklet.class.getName());

	/** Variable commonutility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable paramList. */
	private List<I000020InputParam> paramList;

	/** Variable finalQuery. */
	private static StringBuilder finalQuery;

	/** Variable por cd string. */
	private static String porCdStr;

	/** Variable buyer group string. */
	private static String buyerGrpCdStr;

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

	/**
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Create the custom query and store in ChunkContext
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

		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);		
		
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		
		/** Process Id - P0001 INSERTING DAILY SCHEDULE  INTERFACE FILE DETAIL DATA INTO COMMON FILE HEADER MST.*/
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, IFConstants.SEND_TRANSACTION_TYPE);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		// log data
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("porCdStr", porCdStr);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("buyerGrpCdStr", buyerGrpCdStr);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create final query based on the list of input param
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		finalQuery = new StringBuilder().append(I000020QueryConstants.baseQuery
				.toString()+"(");		

		for (I000020InputParam inputParams : paramList) {
			
			String whereClause = I000020QueryConstants.baseQueryCondition.toString();
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
		finalQuery.append(")"+I000020QueryConstants.baseQueryCondition1.toString());
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);		

		return finalQuery.toString();
	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters
	 */
	private static List<I000020InputParam> deformatInputs(String inputStr) {

		List<I000020InputParam> list = new ArrayList<I000020InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000020InputParam inputParam = new I000020InputParam();
			inputParam.setPorCd(val[0]);        //PorCd
			inputParam.setOcfRegionCd(val[1]);  //Ocf region Cd
			inputParam.setOcfBuyerCd(val[2]);   //Ocf BuyerCd
			inputParam.setRhqCd(val[3]);        //RhqCd
			inputParam.setBuyerGrpCd(val[4]);   //BuyerGrpCd
			list.add(inputParam);

			// log data
			porCdStr = (porCdStr == null) ? val[0] : porCdStr + " " + val[0];
			buyerGrpCdStr = (buyerGrpCdStr == null) ? val[4] : buyerGrpCdStr
					+ " " + val[4];
		}

		return list;
	}

}
