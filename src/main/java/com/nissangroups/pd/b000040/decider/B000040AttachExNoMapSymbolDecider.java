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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.util.B000040QueryDataService;
import com.nissangroups.pd.b000040.util.B000040QueryInputVO;

public class B000040AttachExNoMapSymbolDecider implements JobExecutionDecider {

	@Autowired(required = false)
	B000040QueryInputVO queryInputVO;
	
	
	@Autowired(required = false)
	B000040QueryDataService queryDataService;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000040AttachExNoMapSymbolDecider.class.getName());

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
		
		queryDataService.attachEXNoMapSymbols(queryInputVO.getPorCD());
				
		queryInputVO.setAttachExNoMapSymbol(Boolean.TRUE);
		
		List<String> list = new ArrayList<String>();
		
		String status = "";
		if(queryInputVO.isAttachExNoMapSymbol() ){
			list.add("OCF REGION CODE");
			list.add("EX-NO");
			list.add("End Item");
			list.add("PRODUCTION FAMILY CODE");
			list.add("ADDITIONAL SPEC CODE");
			list.add("SPEC DESTIANTION CODE");
			list.add("BUYER CODE");
			list.add("BUYER GROUP CODE");
			list.add("CAR SERIES");			
			queryInputVO.setExNoMapTrueCols(list);
			status = "true";
		}else{
			list.add("OCF REGION CODE	");			
			list.add("End Item");
			list.add("PRODUCTION FAMILY CODE");
			list.add("ADDITIONAL SPEC CODE");
			list.add("SPEC DESTIANTION CODE");
			list.add("BUYER CODE");
			list.add("BUYER GROUP CODE");
			list.add("CAR SERIES");
			queryInputVO.setExNoMapTrueCols(list);
			status = "false";
		}
						
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return new FlowExecutionStatus(status);
	}

}
