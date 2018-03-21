/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000050.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import com.nissangroups.pd.util.PDConstants;

public class B000050QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000050QuerySetTasklet.class.getName());
	
	/** Variable por cd. */
	String porCd = null;
	
	/** Variable process type */
	String prcsTyp = null;

	/** Variable stage code. */
	String stgCd = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
	
		porCd =  jobParameters.getString(PDConstants.PRMTR_PORCD);
		prcsTyp =  jobParameters.getString(B000050Constants.PRMTR_PRCS_TYP);
		stgCd = jobParameters.getString(B000050Constants.PRMTR_STAGE_CD);
		String ordrTkBsMnthRdrQry = null;
		
		LOG.info(" Job Params --> Process Type is :" +prcsTyp +" and stage code is : "+stgCd);
		
		/** Process Id - P0001.1, 1.2 */
		if (prcsTyp.equalsIgnoreCase(PDConstants.MONTHLY)) {
			ordrTkBsMnthRdrQry = fetchMnthlyOrdrTkBsCnt(porCd,stgCd);
		} 
		/** Process Id - P0001.3 */
		else if (prcsTyp.equalsIgnoreCase(PDConstants.WEEKLY)) {
			ordrTkBsMnthRdrQry = fetchWklyOrdrTkBsCnt(porCd);
		} 
		
		chunkContext.getStepContext().getStepExecution().getJobExecution()
		.getExecutionContext().put(B000050Constants.CONSTANT_DYNAQRY, ordrTkBsMnthRdrQry);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
/**
 * @param porCd
 * @param stgCd
 * @return Monthly Extraction Query String
 */
public String fetchMnthlyOrdrTkBsCnt(String porCd, String stgCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(QueryConstants.getCntSelClause)
 		.append(QueryConstants.getOrdrTkBsMnthMnthlyMain)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(porCd)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(QueryConstants.getOrdrTkBsMnthMnthlyWhr)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(stgCd)
 		.append(PDConstants.SINGLE_QUOTE);
 		
 		LOG.info("Monthly Extraction Query String : "+ queryString);
 		
 		return queryString.toString();
 	}

/**
 * @param porCd
 * @return Weekly Extraction Query String
 */
public String fetchWklyOrdrTkBsCnt(String porCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(QueryConstants.getCntSelClause)
 		.append(QueryConstants.getOrdrTkBsMnthWklyMain)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(porCd)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(QueryConstants.getOrdrTkBsMnthWklyWhr);
 		
 		LOG.info("Weekly Extraction Query String : "+ queryString);
 		
 		return queryString.toString();
 	}

	
}
