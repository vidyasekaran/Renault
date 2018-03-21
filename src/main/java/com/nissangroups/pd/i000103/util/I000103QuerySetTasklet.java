/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.math.BigDecimal;
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

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000103QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also  Create the custom query and store in ChunkContext for I000103 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class I000103QuerySetTasklet implements Tasklet, InitializingBean {

	/**Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000103QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Variable query string */
	private static StringBuilder finalQuery;
	
	/** Variable entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManagerFactory;
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * P0001 Extract the sequence number and Create the custom query and store in ChunkContext
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
		chunkContext.getStepContext().getStepExecution().getJobExecution()
			.getExecutionContext().put(IFConstants.SEQ_NO, isDataExist(ifFileId));
		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext().put("dynaQuery", createCustomQuery(ifFileId));
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
	/**
	 * Creates the query to fetch data from CMN_FILE_HDR and CMN_INTERFACE_DATA tables
	 * 
	 * @param updatedData 
	 * 
	 * @return the query
	 */
	private String createCustomQuery(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try{
			finalQuery = new StringBuilder().append(I000103QueryConstants.baseQuery
					.toString().replace(IFConstants.param_IfFileID,"'"+ifFileId+"'"));
			finalQuery.append(") a");
		}catch(Exception e){
			LOG.error("Exception in Final Query :" +e);
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return finalQuery.toString();
	}
	
	/**
	 * P0001 Query to extract the maximum sequence number 
	 * 
	 * @param ifFileId
	 * 				interface file id 
	 * 
	 * @return long
	 */
	private long isDataExist(String ifFileId) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		long seqNo = 0;
			
		Query query = entityManagerFactory
					.createNativeQuery(I000103QueryConstants.baseQuery1.toString());
			query.setParameter(IFConstants.param_IfFileID,"'"+ifFileId+"'");
				List<Object[]> selectResultSet = query.getResultList();
				if(selectResultSet != null && !(selectResultSet.isEmpty()) && selectResultSet.get(0) != null)
				{
					Object obj = selectResultSet.get(0);
						seqNo = ((BigDecimal)obj).longValue();
					
				}else{
					LOG.info(PDMessageConsants.M00003);
					CommonUtil.stopBatch();
				}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return seqNo;
	}
}
