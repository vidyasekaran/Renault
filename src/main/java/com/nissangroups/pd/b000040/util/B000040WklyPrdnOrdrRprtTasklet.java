/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.util;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class B000040WklyPrdnOrdrRprtTasklet implements Tasklet {

	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000040QueryInputVO queryInputVO;
	
	
	@Autowired(required = false)
	B000040QueryDataService queryDataService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext chunkContext)
			throws Exception {
					
		queryDataService.getWklyProdOrdrRprt();
		
		return RepeatStatus.FINISHED;
	}

}
