/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.writer;


import static com.nissangroups.pd.util.PDConstants.POR_CD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {
	 
	/** Constant LOG. */
	private static final  Log LOG = LogFactory.getLog(NoOpItemWriter.class
			.getName());

	CommonUtil cmnUtil = new CommonUtil() ;
	
	@Override
	public void write(List items) throws Exception {
		LOG.info("COMPLETED");
	}
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			
			String errMsg = PDMessageConsants.M00041;
			Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", stepExecution.getJobParameters().getString(POR_CD));
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
	}
	}
