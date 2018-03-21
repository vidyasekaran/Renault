/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Batch
 * Function ID            : PST-DRG-I000106
 * Module                 : CM Common		
 * Process Outline     	  : Interface for Sending  Buyer Master to SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000106.util;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This Class generate the sequence number and insert a record in Common File Header
 * and also create the custom query and store in ChunkContext for I000106 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class I000106QuerySetTasklet implements Tasklet, InitializingBean {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000106QuerySetTasklet.class.getName());

	
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Query String to extract buyer details from Master tables*/
	private static StringBuilder finalQuery;
	
	/** Variable Updated data	 */
	private static final String UPDATED_DATA ="1";

	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Do nothing
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
		
		String ifFileId = jobParameters
				.getString(IFConstants.INTERFACE_FILE_ID);
		String updatedData = jobParameters
				.getString(IFConstants.UPDATED_DATA);

		// generate seqno and insert header data
		long seqNo = commonutility.getSequenceNoForInterfaceId(ifFileId);
		commonutility.insertCmnFileHdr(ifFileId, seqNo, "", 'S');

		// updating query and seqno in context
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put(IFConstants.SEQ_NO, seqNo);
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery(updatedData));

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0002 Creates the query to extract all the buyer and buyer Group details from Master tables.
	 * 
	 * @param updatedData 
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String updatedData) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		finalQuery = new StringBuilder().append(I000106QueryConstants.baseQuery
					.toString());
			String whereClause = "";
			
			if((UPDATED_DATA).equals(updatedData)){
				whereClause = I000106QueryConstants.whereCondition.toString();
				finalQuery.append(whereClause);
			}
			
			finalQuery.append(" UNION ALL " + I000106QueryConstants.baseQuery1.toString());
			if((UPDATED_DATA).equals(updatedData)){
				whereClause = I000106QueryConstants.whereCondition1.toString();
				finalQuery.append(whereClause);
			}
			
			finalQuery.append(" UNION ALL " + I000106QueryConstants.baseQuery2.toString());
			if((UPDATED_DATA).equals(updatedData)){
				whereClause = I000106QueryConstants.whereCondition2.toString();
				finalQuery.append(whereClause);
			}
			finalQuery.append(") a");
			
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}
	
}
