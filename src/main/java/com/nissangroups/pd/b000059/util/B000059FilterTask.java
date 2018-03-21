/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :Roll back data if any validation or excepiton occures 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class B000059FilterTask implements Tasklet
{

	/**
	 * common logger
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059FilterTask.class.getName());

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
		
	/**
	 * This method used to check the error status of the processed  files and update the results in common file header.
	 * If there is any error occured this method will rollback the data from common pool
	 * 
	 * @param contributon Contribution object
	 * @param chunkContext ChunkContext object
	 * @return RepeatStatus
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info("Started Filter Task");
				
		return RepeatStatus.FINISHED;
	}

	

	

}
