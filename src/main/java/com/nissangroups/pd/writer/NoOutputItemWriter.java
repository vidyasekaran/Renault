/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-NoOutputItemWriter
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import static com.nissangroups.pd.util.PDConstants.NO_OP_ITEM_WRITER;
import static com.nissangroups.pd.util.PDConstants.RAW_TYPES;
import static com.nissangroups.pd.util.PDConstants.IN_OUTPUT_WRITER_MSG;


/**
 * The Class NoOutputItemWriter.
 */
@Component( NO_OP_ITEM_WRITER)
@SuppressWarnings(RAW_TYPES)
public class NoOutputItemWriter implements ItemWriter{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(NoOutputItemWriter.class);

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List arg0) throws Exception {
		LOG.info(IN_OUTPUT_WRITER_MSG);
		
	}

}
