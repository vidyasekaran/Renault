package com.nissangroups.pd.b000052.writer;
/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-NoOpItemWriter
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */


import static com.nissangroups.pd.util.PDConstants.READ_COUNT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {
	 

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(NoOpItemWriter.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List items) throws Exception {
		LOG.info("COMPLETED");
		
	}
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
	String M00213 = "M00213 : &1: There is no &2 Order Take Base Month is in Closed Status in the table &3  for given  POR =&4  . "
				+ " So Batch  Stopped";
	String porCd = stepExecution.getJobParameters().getString("PORCD");
	String type = stepExecution.getJobParameters().getString("TYPE");
	String table =null;
	if (type.equalsIgnoreCase("W")){
		table=B000052Constants.WKLY_TABLE;
	}
if (type.equalsIgnoreCase("M")){
		table=B000052Constants.MNTHLY_TABLE;
	}
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		CommonUtil commonUtil = new CommonUtil();
		int readCnt = stepExecution.getReadCount();
		if (readCnt == 0) {
			String errMsg = M00213;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1",B000052Constants.BATCH_ID_B000052);
			errPrm.put("2",type);
			errPrm.put("3",table);
			errPrm.put("4",porCd);
			LOG.error(commonUtil.getlogErrorMessage(errMsg,errPrm));
		}
	}
	
}
