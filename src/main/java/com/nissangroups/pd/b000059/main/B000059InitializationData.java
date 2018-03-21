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
package com.nissangroups.pd.b000059.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.exception.PdApplicationException;

public class B000059InitializationData implements Tasklet {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059InitializationData.class.getName());

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonService;
		

	/**
	 * @param method
	 *            used to trigger initialize data for batch B000059
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws PdApplicationException {

		LOG.info("In Execute method :: B000059InitializationData.......");
		StepContext stepContext = chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(
				B000059Constants.INTERFACE_FILE_ID);						
		commonService.initializeData(interfaceFileID);
		return RepeatStatus.FINISHED;
	}

}
