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
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;

public class B000040OrderTakeBasePeriodTasklet implements Tasklet {

	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000040QueryInputVO queryInputVO;
	
	
	@Autowired(required = false)
	B000040QueryDataService queryDataService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext chunkContext)
			throws PdApplicationException {
							
		StepContext stepContext = chunkContext.getStepContext();
		String porCd = (String) stepContext.getJobParameters().get(
				B000040Constants.POR_CD);
		
		//P0001 : WEEKLY ORDER TAKE BASE PERIOD MST (Extract Order Take Base Period )
		queryDataService.storeOrderTakeBasePeriod(porCd);
		
		//P0002 : PARAMETER MST (Extract Order Production Period Horizon)
		queryDataService.storeProdPeriodHorizon(porCd);
				
		/*
		 * P0003 : Cong1:  PARAMETER MST (MONTHLY FIXED ORDER REQUIRED) ,Based on the values get for in this metho, Production Period Calculation will be performed.
		 */
		queryDataService.storeProdPeriodConfig(porCd);
		
		/*P0004 : P0004.1 PARAMTER MST (USE ORIGINAL LINE CLASS AND PLANT CD )
		 * set LineClassandPlntCdFlag
		 */
		queryDataService.storeLineSegmentPlantCD(porCd);
		
		/*P0004 : P0004.1 PARAMTER MST (SUSPENDED ORDER REQUIRED*/
		queryDataService.storeSuspendedOrderFlg(porCd);
		
		String dynaQuery = queryDataService.constructWeekProPrdDetailsQuery();			
		
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("dynaQuery", dynaQuery);	
		
		return RepeatStatus.FINISHED;
	}

}
