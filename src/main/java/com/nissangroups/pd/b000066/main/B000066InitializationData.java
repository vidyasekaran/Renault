/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is the main batch kick off program.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000066.main;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.b000066.util.B000066CommonUtilityService;

public class B000066InitializationData implements Tasklet, InitializingBean {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory
			.getLog(B000066InitializationData.class.getName());

	/**
	 * Stores entity manager
	 */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	
	/**
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager
	 * 
	 * @param entityManager
	 *            the entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * @param method
	 *            used to trigger initialize data for batch B000059
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info("In Execute method :: B000066InitializationData.......");
		StepContext stepContext = (StepContext) chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(B000059Constants.INTERFACE_FILE_ID);
		
		B000066CommonUtilityService.setDecider(B000066CommonUtilityService.getController(interfaceFileID, entityManager));
		LOG.info("interfaceFileId : " + interfaceFileID);
		
		return RepeatStatus.FINISHED;
	}

}
