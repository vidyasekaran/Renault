/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000044.bean.I000044InputParam;
import com.nissangroups.pd.i000044.util.I000044Constants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This Class I000044Decider to decide the execution of steps based on the condition.
 * The return value will be used as a status to determine the next step in the job. 
 * 
 * @author z016127
 *
 */
public class I000044Decider implements JobExecutionDecider {

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(I000044Decider.class.getName());

	/**
	 * This method is used to decide which step to execute next 
	 * based on the Input parameter
	 * If return value is NoOrderTakeBasePeriod it goes to step Fail 
	 * else if Completed it goes to Step2
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {

		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/** Input parameter list */
		List<I000044InputParam> paramList = (List<I000044InputParam>) jobExecution.getExecutionContext().get(I000044Constants.PARAM_LIST);
		
		/** Variable PorCD */
		String porCd = (String)stepExecution.getJobExecution().getExecutionContext().get(I000044Constants.POR_CD);
		
		/** Variable Interface File ID */
		String ifFileId = jobExecution.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
			if(paramList == null || paramList.isEmpty()){
				commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
				commonutility.setRemarks(PDMessageConsants.M00159.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
						 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.Stage_status_CD)
						 .replace(PDConstants.ERROR_MESSAGE_3, porCd)
						 .replace(PDConstants.ERROR_MESSAGE_4, PDConstants.MONTHLY_ORDER_TAKE_BASE_PERIOD_MST));
				return new FlowExecutionStatus(I000044Constants.NOORDR_TK_BASE_PRD);
			}else{
				return new FlowExecutionStatus(I000044Constants.COMPLETED);
			}
		}

}
