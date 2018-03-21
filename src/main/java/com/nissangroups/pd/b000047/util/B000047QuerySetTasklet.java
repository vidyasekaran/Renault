/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.util;

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

/**
 * This Class B000047QuerySetTasklet is Create the custom query and store in ChunkContext for B000047 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class B000047QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000047QuerySetTasklet.class.getName());
	
	/** Variable por cd. */
	String porCd = null;
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Do nothing
	}

	/**
	 * P0001 Create the custom query and store in ChunkContext
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
		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
	
		porCd =  jobParameters.getString(PDConstants.PRMTR_PORCD);
		
		/** Process Id - P0001 */
		String lgclPplnQuery = fetchLgclPplnRcds(porCd);
		
		/**Process Id - P0005.5.3 */
		String physclPplnQuery = fetchRemaingVinLst(porCd);
		
		chunkContext.getStepContext().getStepExecution().getJobExecution()
			.getExecutionContext().put(B000047Constants.CONSTANT_DYNAQRY, lgclPplnQuery);
		
		chunkContext.getStepContext().getStepExecution().getJobExecution()
			.getExecutionContext().put(B000047Constants.PHYSCL_PPLN_DYNAQRY, physclPplnQuery);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
	/**
	 * P0001 Query to extract the VIN No not assigned Logical Pipeline records
	 *  
	 * @param porCd
	 * 				string
	 * 
	 * @return Logical Pipeline extraction Query String
	 */
	public String fetchLgclPplnRcds(String porCd){
	 		
	 		StringBuilder queryString = new StringBuilder()
			 		.append(B000047QueryConstants.getLgclPipLnTrnDtlsMain)
			 		.append(PDConstants.SINGLE_QUOTE)
			 		.append(porCd)
			 		.append(PDConstants.SINGLE_QUOTE)
			 		.append(B000047QueryConstants.getLgclPipLnTrnDtlsWhr)
			 		.append(B000047QueryConstants.orderByClause);
	 		
	 		LOG.info("Logical Pipeline Extraction Query String : "+ queryString);
	 		
	 		return queryString.toString();
	 	}
	
	
	/**
	 * P0001 Query to extract Remaining Vin List not allocated to Logical Pipeline Trn from Physical Pipeline Trn Table
	 *  
	 * @param porCd
	 * 				string
	 * 
	 * @return Physical Pipeline extraction Query String
	 */
	public String fetchRemaingVinLst(String porCd){
	 		
	 		StringBuilder queryString = new StringBuilder()
			 		.append(B000047QueryConstants.remaingVinLst)
			 		.append(PDConstants.SINGLE_QUOTE)
			 		.append(porCd)
			 		.append(PDConstants.SINGLE_QUOTE);
	 		
	 		LOG.info("Physical Pipeline Extraction Query String : "+ queryString);
	 		
	 		return queryString.toString();
	 	}
	
	

}
