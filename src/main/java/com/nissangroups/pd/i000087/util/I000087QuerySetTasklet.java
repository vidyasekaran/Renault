/*
 * System Name     :Post Dragon 
 * Sub system Name :I Interface
 * Function ID     :PST_DRG_I000087
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly Production Schedule from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000087.util;

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

import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This Class I000087QuerySetTasklet is to generate the sequence number and insert a record in Common File Header
 * and also create the custom query and store in ChunkContext for I000087 reader to do further processing.
 * 
 * @author z016127
 *
 */
public class I000087QuerySetTasklet implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000087QuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private static StringBuilder finalQuery;

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable job parameter */
	private JobParameters jobParameters;
	
	/** Variable interface file id */
	private String ifFileId;
	
	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

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

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Getting job parameter
		this.jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();

		ifFileId = jobParameters
				.getString(PDConstants.INTERFACE_FILE_ID);

		chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext()
				.put("dynaQuery", createCustomQuery());
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}

	/**
	 * P0001 Query to extract the unprocessed data with minimum Sequence No, please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case
	 * 
	 * @return the query
	 */
	private String createCustomQuery() {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String porCd = jobParameters.getString(
				PDConstants.BATCH_POR_CODE);
		
		finalQuery = new StringBuilder().append("SELECT ROWNUM, a.* FROM ("+I000087QueryConstants.baseQuery
				.toString()
			.replace(":"+PDConstants.PRMTR_PORCD, (porCd == null)?"''":"'"+porCd.trim()+"'")
			.replace(":"+PDConstants.PRMTR_POT_CD, "'"+PDConstants.POT_CD+"'")
			.replace(":"+PDConstants.PRMTRT_INTERFACE_FILE_ID, "'"+ifFileId+"'"));

		finalQuery.append(") a");
		
		return finalQuery.toString();
	}

	/**
	 * Get the entityManager
	 *
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
