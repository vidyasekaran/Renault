/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000040
 * Module          :
 * Process Outline : Attach Production method Code with the extracted Orders
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.decider;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.util.B000040QueryDataService;
import com.nissangroups.pd.b000040.util.B000040QueryInputVO;

public class B000040AttachProductionMethodCodeDecider implements JobExecutionDecider {

	@Autowired(required = false)
	B000040QueryInputVO queryInputVO;
	
	
	@Autowired(required = false)
	B000040QueryDataService queryDataService;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000040AttachProductionMethodCodeDecider.class.getName());

	/*
	 *  Decider for is Frozen Symbol Required Flag ?
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		
		queryDataService.attachProductionMethodCodeFlag(queryInputVO.getPorCD());
		queryDataService.storeCnstValProductionMonthCd(queryInputVO.getPorCD());
		
		String status = "true"; // queryInputVO.isProductionMthdCd() ? "true" : "false";					
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return new FlowExecutionStatus(status);
	}

}
