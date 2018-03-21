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
package com.nissangroups.pd.b000008.writer;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

import static com.nissangroups.pd.b000008.util.B000008Constants.*;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;


/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {
	 
	/** Constant LOG. */
	private static final  Log LOG = LogFactory.getLog(NoOpItemWriter.class
			.getName());

	@Override
	public void write(List items) throws Exception {
		LOG.info("COMPLETED");
	}
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00159, PDConstants.CONSTANT_V4, new String[]{BATCH_ID_B000008,
					 OPEN_STAGE,stepExecution.getJobParameters().getString(POR_CD),
					  MONTHLY_ORDER_TAKE_BASE_PERIOD_MST});
			CommonUtil.stopBatch();
		}
	}
	}
