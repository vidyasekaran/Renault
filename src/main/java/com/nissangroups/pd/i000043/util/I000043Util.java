/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000043
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly OCF from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000043.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
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

import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class implements Tasklet which will call the execute method repeatedly until 
 * it either returns RepeatStatus.FINISHED or throws an exception to signal a failure
 * @author z014676
 *
 */
public class I000043Util implements Tasklet, InitializingBean {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000043Util.class.getName());
	
	/* CommonRepository service injection */
	@Autowired(required = false)
	private CommonRepository cmnRep;
	
	@Override
	public void afterPropertiesSet() throws Exception {

		/**
		 * Indicates that a method declaration is intended to override a method
		 * declaration in a supertype.
		 */
	}

	
	/*
	 * P0002 Delete all the records from the DAILY_OCF_LIMIT_IF _TRN table on the given POR code 
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
	 * springframework.batch.core.StepContribution,
	 * org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		// getting job param
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		String porCd = jobParameters
				.getString(PDConstants.BATCH_POR_CODE);
		cmnRep.deleteDailtOcfIf(porCd);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}
	

}