/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.writer;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

import static com.nissangroups.pd.b000011.util.B000011Constants.*;

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
	
	/**
	 * Checking no data found in the reader
	 * @param stepExecution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00191, PDConstants.CONSTANT_V2, new String[]{BATCH_ID_B000011,
					 stepExecution.getJobParameters().getString(POR_CD)});
			CommonUtil.stopBatch();
		}
	}
	}
