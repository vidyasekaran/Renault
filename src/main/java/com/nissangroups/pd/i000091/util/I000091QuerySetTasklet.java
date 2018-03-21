/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class I000091QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000091 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class I000091QuerySetTasklet implements Tasklet, InitializingBean {
 
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000091QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Query String to extract ocfAutoAllocationFlag */
	private static StringBuilder finalQuery;

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable jobParameters */
	private JobParameters jobParameters;
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
			//Do nothing
	}

	/**
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Creates the custom query and store in ChunkContext
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

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Getting job param
		this.jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();

		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String fileName = jobParameters
				.getString(IFConstants.FILE_NAME);
		// Generate the sequence number and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, fileName, 'S');

		// Update the sequence number and query in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery());
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002.2 Creates the query to extract ocfAutoAllocationFlag.
	 * 
	 * @return the string
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		/** Variable Por Code*/
		String porCd = jobParameters
				.getString(IFConstants.POR_CD);
		/** Variable Buyer Group  Code*/
		String buyrGrpCd = jobParameters
				.getString(IFConstants.BUYER_GRP_CD);
		/** Variable Ocf Buyer Group Code*/
		String ocfBuyrGrpCd = jobParameters
				.getString(IFConstants.OCF_BUYER_GRP_CD);
		/** Variable Ocf Region Code*/
		String ocfRgnCd = jobParameters
				.getString(IFConstants.OCF_REGION_CD);
		String queryString ="*";
		
		finalQuery = new StringBuilder().append(I000091Constants.queryString+I000091QueryConstants.getOcfAutoAllctnFlg
				.toString());
		
		String whereClause = "";
		whereClause = I000091QueryConstants.baseQueryCondition.toString();
		whereClause = ((I000091Constants.NULL).equals(porCd) || (queryString)
				.equals(porCd)) ? whereClause.replaceAll(
				IFConstants.param_porCd, " ") : whereClause.replaceAll(
				IFConstants.porCd_Param, "'" + porCd.trim()
						+ I000091Constants.AND_QRYSTRNG);
		whereClause = ((I000091Constants.NULL).equals(ocfRgnCd) || (queryString)
				.equals(ocfRgnCd)) ? whereClause.replaceAll(
				IFConstants.param_OcfRgnCd, " ") : whereClause.replaceAll(
				IFConstants.ocfRegionCd_Param, "'" + ocfRgnCd.trim()
						+ I000091Constants.AND_QRYSTRNG);
		whereClause = ((I000091Constants.NULL).equals(ocfBuyrGrpCd) || (queryString)
				.equals(ocfBuyrGrpCd)) ? whereClause.replaceAll(
				IFConstants.param_OcfByrGrpCd, " ") : whereClause.replaceAll(
				IFConstants.ocfBuyerGrpCd_Param, "'" + ocfBuyrGrpCd.trim()
						+ I000091Constants.AND_QRYSTRNG);
		whereClause = ((I000091Constants.NULL).equals(buyrGrpCd) || (queryString)
				.equals(buyrGrpCd)) ? whereClause.replaceAll(
				IFConstants.param_buyer_buyerGrpCD, " ") : whereClause
				.replaceAll(IFConstants.buyer_buyerGrpCD_Param,
						"'" + buyrGrpCd.trim() + I000091Constants.AND_QRYSTRNG);

		finalQuery.append("("+ whereClause + ")");
		int indx = finalQuery.toString().lastIndexOf("AND");
		finalQuery = finalQuery.replace(indx, indx + 3, " ");
		finalQuery.append(") a");			

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
