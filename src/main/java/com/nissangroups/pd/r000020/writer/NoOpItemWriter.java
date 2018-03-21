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
package com.nissangroups.pd.r000020.writer;

import static com.nissangroups.pd.util.PDConstants.READ_COUNT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

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
		
		
	}
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
	String M00159 = "M00159 : &1: There is no Order Take Base Period in &2  Stage  for  POR =&3  in &4 -Table. "
				+ " So Batch  Stopped";
	String porCd = stepExecution.getJobParameters().getString(PDConstants.PRMTR_PORCD);
	String stgCd = stepExecution.getJobParameters().getString(PDConstants.PRMTR_STAGE_CD);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		CommonUtil commonUtil = new CommonUtil();
		int readCnt = stepExecution.getReadCount();
		if (readCnt == 0) {
			String errMsg = PDMessageConsants.M00159;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1",PDConstants.R000020);
			errPrm.put("2",stgCd);
			errPrm.put("3",porCd);
			errPrm.put("4",PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD);
			LOG.info(commonUtil.getlogErrorMessage(errMsg,errPrm));
			commonUtil.stopBatch();
		}
	}
	
}
