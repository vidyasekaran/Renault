/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000033.util;

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

import com.nissangroups.pd.i000033.bean.I000033InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class implements Tasklet which will call the execute method repeatedly until 
 * it either returns RepeatStatus.FINISHED or throws an exception to signal a failure
 * 
 * @author z014676
 * 
 */
public class I000033QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000033QuerySetTasklet.class.getName());
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	// this object using store the list of values based on the input parameters
	private List<I000033InputParam> paramList;

	private static StringBuilder finalQuery;

	/*
	 * Indicates that a method declaration is intended to override a method declaration in a supertype.
	 * 
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * Not necessary to implement this abstract method in this case
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */

	}

	/*
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Creates the custom query and store in ChunkContext
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
		String jobInputs = jobParameters.getString(PDConstants.BATCH_POR_CODE);
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Creates the query to extract the Monthly order error interface trn table, Please note the * in a.* returns only the columns selected in the subquery 
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
				+ I000033QueryConstants.baseQuery.toString());

		for (I000033InputParam inputParams : paramList) {
			String whereClause = I000033QueryConstants.baseQueryCondition
					.toString();
			whereClause = (("*").equals(inputParams.getPorCd())) ? whereClause
					.replaceAll(IFConstants.param_porCd, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param,
							"'" + inputParams.getPorCd() + "' ");

			finalQuery.append("(" + whereClause + ") OR ");

		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(") a");
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();

	}

	/**
	 * format the input values and store in list
	 * @param inputStr
	 * @return list
	 */
	private static List<I000033InputParam> deformatInputs(String inputStr) {

		List<I000033InputParam> list = new ArrayList<I000033InputParam>();
		List<String> mainList = Arrays.asList(inputStr);
		for (String str : mainList) {
			I000033InputParam inputParam = new I000033InputParam();
			inputParam.setPorCd(str);
			list.add(inputParam);
		}

		return list;
	}

}
