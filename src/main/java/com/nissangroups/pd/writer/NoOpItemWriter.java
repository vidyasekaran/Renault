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
package com.nissangroups.pd.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {
	 

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List items) throws Exception {
		
		
	}
	}
