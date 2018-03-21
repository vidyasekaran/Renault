/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class UptSpecTimeTblWriter implements ItemWriter<List<Object>> {
	
	private static final Log LOG = LogFactory.getLog(UptSpecTimeTblWriter.class.getName());
	
	 @Override
	    public void write(List<? extends List<Object>> items) throws Exception {
		 
		 LOG.info("Inside Writer");
		 
	 }
	
}	