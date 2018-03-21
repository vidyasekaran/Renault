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

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000059ExceptionHandler implements Tasklet
		 {

	/**
	 * common logger
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059ExceptionHandler.class.getName());

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	
	@SuppressWarnings("unchecked")
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		StepContext stepContext = (StepContext) chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(
				B000059Constants.INTERFACE_FILE_ID);	
		LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00076, new String[]{"&1"}, new String[]{ "Interface File ID: " + interfaceFileID}) );
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}

	
	

}
