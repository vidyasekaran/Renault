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

package com.nissangroups.pd.b000061.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.b000061.util.B000061CommonUtilityService;

public class B000061InitializationData implements Tasklet, InitializingBean {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory
			.getLog(B000061InitializationData.class.getName());

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;

	
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

		LOG.info("In Execute method :: B000061InitializationData.......");
		StepContext stepContext = (StepContext) chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get("S_INTERFACE_FILE_ID");						
		B61commonutility.initializeData(interfaceFileID,chunkContext);
		return RepeatStatus.FINISHED;
	}

}
