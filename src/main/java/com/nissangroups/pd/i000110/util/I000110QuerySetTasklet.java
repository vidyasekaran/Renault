/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000110
 * Module          :CM Common
 * Process Outline :This Interface extracts Feature data from the master tables and stores it in the Common Layer Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 * 
 */
package com.nissangroups.pd.i000110.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.text.ParseException;
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

import com.nissangroups.pd.i000110.bean.I000110InputParam;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000110 reader to do further processing.
 * 
 * @author z015895
 */
public class I000110QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000110QuerySetTasklet.class.getName());

	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** List of Input Parameter List . */
	private List<I000110InputParam> paramList;

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

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, IFConstants.EMPTY_FILENAME, IFConstants.SEND_TRANSACTION_TYPE);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method used to create and return custom query based on the list of input param, using this as a part 
	 * of query along with other query would returns only the columns selected in the subquery so it does 
	 * not return all the columns.
	 * 
	 * @return the query
	 * @throws ParseException
	 */
	private String createCustomQuery() throws ParseException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("+I000110QueryConstants.baseQuery.toString());

		String ablshDate = findAblshDate();

		for (I000110InputParam inputParams : paramList) {

			String whereClause = I000110QueryConstants.baseQueryCondition
					.toString();
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getBuyerGrpCd())) ? whereClause.replaceAll(IFConstants.param_buyer_buyerGrpCD, " ") : whereClause
					.replaceAll(IFConstants.buyer_buyerGrpCD_Param,"'" + inputParams.getBuyerGrpCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getRhqCd())) ? whereClause.replaceAll(IFConstants.param_rhqCd, " ") : whereClause
					.replaceAll(IFConstants.rhqCd_Param,"'" + inputParams.getRhqCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfRegionCd())) ? whereClause.replaceAll(IFConstants.param_ocfRegionCd, " ")
					: whereClause.replaceAll(IFConstants.ocfRegionCd_Param, "'"+ inputParams.getOcfRegionCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfBuyerCd())) ? whereClause.replaceAll(IFConstants.param_ocfBuyerGrpCd, " ")
					: whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,"'" + inputParams.getOcfBuyerCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getPorCd())) ? whereClause.replaceAll(IFConstants.param_porCd, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param,"'" + inputParams.getPorCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getPorCd())) ? whereClause.replaceAll(IFConstants.param_mstFeat_porCd, " ")
					: whereClause.replaceAll(IFConstants.porCd_Param, "'"+ inputParams.getPorCd() + IFConstants.QRYSTRNG);			
			whereClause = ((IFConstants.ASTRIECK).equals(inputParams.getOcfBuyerCd())) ? whereClause.replaceAll(IFConstants.param_mstFeat_ocfBuyerGrpCd, " ")
					: whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,"'" + inputParams.getOcfBuyerCd() + IFConstants.QRYSTRNG);
				
			
			finalQuery.append("(" + whereClause +""+ I000110QueryConstants.baseQueryCondition1.toString().replaceAll(IFConstants.ablshDate_Param, "'" + ablshDate + "' ")+ ") OR ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");	
		finalQuery.append(") a");

		LOG.info("Final Query :  "+finalQuery.toString());
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters from the formatted String
	 */
	private static List<I000110InputParam> deformatInputs(String inputStr) {

		List<I000110InputParam> list = new ArrayList<I000110InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000110InputParam inputParam = new I000110InputParam();
			inputParam.setPorCd(val[0]);        //POR CD
			inputParam.setOcfRegionCd(val[1]);  //OCF Region Code
			inputParam.setOcfBuyerCd(val[2]);   //OCF Buyer Code
			inputParam.setRhqCd(val[3]);        //RHQ Code
			inputParam.setBuyerGrpCd(val[4]);   //Buyer Group Code
			list.add(inputParam);
		}

		return list;
	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param ablshDate
	 * @return abolish date String
	 */
	
	public String findAblshDate() throws ParseException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ablshDate = "";
		String maxOrdrTakBasMnth = "";
		String prmtrVal1 = "";
		try {
			String prmtrCd = IFConstants.nscFeatAblhLmt;
			maxOrdrTakBasMnth = commonutility.getMaxOrdrTakBasMnthByCd();
			prmtrVal1 = commonutility.getVal1FrmPrmtrMasByCd(prmtrCd);
			if (!("").equals(maxOrdrTakBasMnth) && !("").equals(prmtrVal1)) {
				ablshDate = CommonUtil.prdMnthCal(maxOrdrTakBasMnth, prmtrVal1);
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			
		} catch (Exception e) {			
			LOG.error("***Abolish Date calc error. MaxOrdrMnth : "+maxOrdrTakBasMnth+" ,prmtrVal1 : "+prmtrVal1);
			LOG.error(""+e);			
		}
		return ablshDate;
	}

}
