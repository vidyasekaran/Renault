/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000088
 * Module                 : Ordering		
 * Process Outline     	  : Send Weekly Production Schedule Interface to NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014029(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000088.util;

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

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000088.bean.I000088InputParam;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000088QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also create the custom query and store in ChunkContext for I000088 reader to do further processing.
 * 
 * @author z014029
 *
 */
public class I000088QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000088QuerySetTasklet.class.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/**variable paramlist */
	private List<I000088InputParam> paramList;

	private static StringBuilder finalQuery;

	/** Variable porCdStr */
	private static String porCdStr;

	/** Variable buyer group cd status */
	private static String buyerGrpCdStr;

	/** Variable order take base */
	String ordrTkBS = null;

	/** Variable Order take base month list */
	List<String> ordrTkBsMnthLst = new ArrayList<String>();

	@Override
	public void afterPropertiesSet() throws Exception {

		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	/**
	 * P0001 Creates the custom query and store in ChunkContext
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
		String porLstStr = jobParameters.getString(IFConstants.param_porCdLst);
		paramList = deformatInputs(jobInputs);
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters.getString(IFConstants.FILE_NAME);
		/*
		 * String stageCd = jobParameters.getString(IFConstants.STAGE_CODE);
		 * String stageSttsCd =
		 * jobParameters.getString(IFConstants.STAGE_STTS_CD);
		 */

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');
		String maxOdrTk = fetchLatestOrdrTkBsOrdData(porLstStr, ifFileId,
				buyerGrpCdStr, seqNo);

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery(maxOdrTk, porLstStr));
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(IFConstants.MAX_ORDR_TAKBAS_MNTH, maxOdrTk);

		// log data
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("porCdStr", porCdStr);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("buyerGrpCdStr", buyerGrpCdStr);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Extract Order take base period based on given POR CD,iffileid ,
	 * buyergrpcdstr and seqno
	 * 
	 * @param porCd
	 * @param ifFileId
	 * @param buyerGrpCdStr
	 * @param seqNo
	 * @return empty
	 * @throws Exception
	 */
	public String fetchLatestOrdrTkBsOrdData(String porCd, String ifFileId,
			String buyerGrpCdStr, long seqNo) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		
		Query extOrdrTK = entityManager
				.createNativeQuery(I000088QueryConstants.getOrdrTkBsPd
						.toString());
		extOrdrTK.setParameter(PDConstants.POR_CD, porCd);
		
		Object ordrTk = extOrdrTK.getSingleResult();

		if (ordrTk != null) {
			return ordrTk.toString();
		} else {
			String remarks = PDMessageConsants.M00159
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.CONSTANT_SC)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4,
							PDConstants.WEEKLY_ORDER_TAKE_BASE_PERIOD_MST);
			commonutility.setRemarks(remarks);
			commonutility.updateCmnFileHdr(ifFileId, seqNo, "F", remarks);
			return "";
		}
	}

	/**
	 * P0003: Creates the createCustomQuery and Extracting Weekly Production
	 * Schedule data from weekly production schedule trn, mst por ,mst buyer,mst
	 * buyer group and conditions, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case.
	 * 
	 * @param maxOdrTk
	 * @param porCds
	 * @return the query
	 * @throws PdApplicationException
	 */
	private String createCustomQuery(String maxOdrTk, String porCds)
			throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		finalQuery = new StringBuilder().append("select rownum, a.* from ("
				+ I000088QueryConstants.baseQueryCondition1.toString()
						.replace(IFConstants.ordrTkBsMnth_Param, maxOdrTk + "")
						.replace(IFConstants.param_porCdLst, porCds + ""));
		finalQuery.append(" (   ");

		for (I000088InputParam inputParams : paramList) {

			String whereClause = I000088QueryConstants.baseQueryCondition2
					.toString();
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

		LOG.info("**********Final Query***********");
		LOG.info(finalQuery.toString());

		return finalQuery.toString();
	}
    /**
     * This method use  format the input values
     * @param inputStr
     * @return list
     */
	private static List<I000088InputParam> deformatInputs(String inputStr) {

		List<I000088InputParam> list = new ArrayList<I000088InputParam>();
		List<String> mainList = Arrays.asList(inputStr.split(IFConstants.FORMAT));
		for (String str : mainList) {
			String[] val = str.split(IFConstants.AMPERSAND);
			I000088InputParam inputParam = new I000088InputParam();
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