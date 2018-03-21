/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;



/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(NoOpItemWriter.class
			.getName());

	@Override
	public void write(List items) throws Exception {
		LOG.info("COMPLETED");
	}

}
