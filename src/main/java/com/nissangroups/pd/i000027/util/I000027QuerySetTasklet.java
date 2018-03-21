/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send the OSEI frozen master details to NSC 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

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

import com.nissangroups.pd.i000027.bean.I000027InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000027 reader to do further processing.
 * 
 * @author z014029
 */
public class I000027QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000027QuerySetTasklet.class.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable Common Utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Parameter List. */
	private List<I000027InputParam> paramList;

	/** Variable Final Query */
	private static StringBuilder finalQuery;

	/** Variable POR Code */
	private static String porCdStr;

	/** Variable Buyer Group Code */
	private static String buyerGrpCdStr;
	
	/** Variable Buyer Group Code */
	private static String ifFileId;
	
	/** Variable Buyer Group Code */
	private static Long seqNo;
	
	/** Variable Buyer Group Code */
	private static String stageCdStr;

	/** Variable Order Take Base */
	String ordrTkBS = null;

	/** Variable Order Take Base Month List */
	List<String> ordrTkBsMnthLst = new ArrayList<String>();

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

		// Getting job parameters value
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String jobInputs = jobParameters.getString(IFConstants.INPUT_PARAM);
		String porLstStr = jobParameters.getString(IFConstants.param_porCdLst);
		paramList = deformatInputs(jobInputs);
		ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);
		stageCdStr = jobParameters.getString(IFConstants.STAGE_CODE);

		// Generate sequence no and insert header data
		seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');

		// updating query and sequence no in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery());
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
//				.put(IFConstants.MAX_ORDR_TAKBAS_MNTH, maxOdrTk);

		// Log data
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("porCdStr", porCdStr);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("buyerGrpCdStr", buyerGrpCdStr);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * This method fetching the Latest order take base month value against the
	 * por code and Stage code
	 * 
	 * @param String
	 *            interfacePorCd
	 */
	public String fetchLatestOrdrTkBsOrdData(String porCd, String ifFileId,
			String buyerGrpCdStr, long seqNo, String stageCdStr)
			throws Exception {
		
		List<String> porList = Arrays.asList(porCd.split(","));
		List<String> stgList = Arrays.asList(stageCdStr.split(","));
		Query extOrdrTK = entityManager
				.createNativeQuery(I000027QueryConstants.extractOrdrTkBsMnth
						.toString());
		extOrdrTK.setParameter(PDConstants.PRMTR_PORCD, porList);
		extOrdrTK.setParameter(IFConstants.STAGE_CODE, stgList);
		Object ordrTk = extOrdrTK.getSingleResult();
		
		if (ordrTk != null) {
			return ordrTk.toString();
		} else {
			String remarks = PDMessageConsants.M00357
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, stageCdStr)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, buyerGrpCdStr)
					.replace(PDConstants.ERROR_MESSAGE_5,
							PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD);					
			commonutility.setRemarks(remarks);
			commonutility.updateCmnFileHdr(ifFileId, seqNo, "F", remarks);
			
			//CommonUtil.stopBatch();
			return "";
			
		}
		
	}

	/**
	 * This method used to create and return custom query based on the list of input param,  Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns.
	 * 
	 * @return the query
	 * @throws Exception 
	 */
	private String createCustomQuery()
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000027QueryConstants.baseQueryCondition1.toString());
		finalQuery.append(" (   ");

		for (I000027InputParam inputParams : paramList) {

			String maxOdrTk = fetchLatestOrdrTkBsOrdData(inputParams.getPorCd(), ifFileId,
					buyerGrpCdStr, seqNo, stageCdStr);
			
			String whereClause = I000027QueryConstants.baseQueryCondition2
					.toString();
			whereClause = whereClause.replace(
							IFConstants.ordrTkBsMnth_OCF_Param,"'" + maxOdrTk + "" + "' AND ");
			whereClause = (("*").equals(inputParams.getPorCd())) ? whereClause
					.replaceAll(IFConstants.param_porCd, " ") : whereClause
					.replaceAll(IFConstants.porCd_Param,
							"'" + inputParams.getPorCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getOcfRgnCd())) ? whereClause
					.replaceAll(IFConstants.param_ocfRegionCd, " ")
					: whereClause.replaceAll(IFConstants.ocfRegionCd_Param, "'"
							+ inputParams.getOcfRgnCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getOcfByrGrp())) ? whereClause
					.replaceAll(IFConstants.param_ocfBuyerGrpCd, " ")
					: whereClause.replaceAll(IFConstants.ocfBuyerGrpCd_Param,
							"'" + inputParams.getOcfByrGrp() + "' AND ");
			whereClause = (("*").equals(inputParams.getRhqCd())) ? whereClause
					.replaceAll(IFConstants.param_rhqCd, " ") : whereClause
					.replaceAll(IFConstants.rhqCd_Param,
							"'" + inputParams.getRhqCd() + "' AND ");
			whereClause = (("*").equals(inputParams.getByrGrpCd())) ? whereClause
					.replaceAll(IFConstants.param_buyer_buyerGrpCD, " ")
					: whereClause.replaceAll(
							IFConstants.buyer_buyerGrpCD_Param, "'"
									+ inputParams.getByrGrpCd() + "' AND ");

			finalQuery.append("(" + whereClause + ") OR ");
			int ind = finalQuery.toString().lastIndexOf("AND");
			finalQuery = finalQuery.replace(ind, ind + 3, " ");
		}

		int ind = finalQuery.toString().lastIndexOf("OR ");
		finalQuery = finalQuery.replace(ind, ind + 2, "");
		finalQuery.append(" )" + ") a");


		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}

	/**
	 * This method used the reformat the the formatted String parameter
	 * 
	 * @param inputStr
	 * @return list of input parameters from the formatted String
	 */
	private static List<I000027InputParam> deformatInputs(String inputStr) {

		List<I000027InputParam> list = new ArrayList<I000027InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000027InputParam inputParam = new I000027InputParam();
			inputParam.setPorCd(val[0]);
			inputParam.setOcfRgnCd(val[1]);
			inputParam.setOcfByrGrp(val[2]);
			inputParam.setRhqCd(val[3]);
			inputParam.setByrGrpCd(val[4]);
			list.add(inputParam);

			porCdStr = (porCdStr == null) ? val[0] : porCdStr + " " + val[0];
			buyerGrpCdStr = (buyerGrpCdStr == null) ? val[4] : buyerGrpCdStr
					+ " " + val[4];
		}
		return list;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}