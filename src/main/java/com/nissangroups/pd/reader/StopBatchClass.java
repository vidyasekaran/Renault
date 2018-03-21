/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-StopBatchClass
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.reader;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import static com.nissangroups.pd.util.PDConstants.NOORDRFOUND;

/**
 * Reader class for stopping Batch .
 *
 * @author z013576
 */
public class StopBatchClass implements ItemReader<List<Object>>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(StopBatchClass.class);
	
	/**
	 * Item Reader Class.
	 *
	 * @return the list
	 * @throws Exception the exception
	 * @throws UnexpectedInputException the unexpected input exception
	 * @throws ParseException the parse exception
	 * @throws NonTransientResourceException the non transient resource exception
	 */
	@Override
	public List<Object> read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		LOG.info(NOORDRFOUND);
		return null;
	}
}
