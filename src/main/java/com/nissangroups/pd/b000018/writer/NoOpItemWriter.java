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
package com.nissangroups.pd.b000018.writer;

import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;


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
		
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		
		int readCnt = stepExecution.getReadCount();
		if (readCnt == 0) {
			LOG.info(M00003);
		}
	}
	
}
