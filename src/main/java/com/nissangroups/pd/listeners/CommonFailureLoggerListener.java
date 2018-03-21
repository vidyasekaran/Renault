/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :ALL
 * Module          :ALL
 * Process Outline :Listener Interface to monitor the Job Executions
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z002548(RNTBCI)               New Creation
 *
 */  
package com.nissangroups.pd.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;

/**
 * The listener interface for receiving FailureLogger events.
 * The class that is interested in processing a FailureLogger
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addFailureLoggerListener<code> method. When
 * the FailureLogger event occurs, that object's appropriate
 * method is invoked.
 *
 * @author z002548
 */
@SuppressWarnings("rawtypes")
@Component
public class CommonFailureLoggerListener extends ItemListenerSupport{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CommonFailureLoggerListener.class);
	
	/**
	 * To Catch Read Data Error.
	 *
	 * @param e the e
	 */
	@Override
	public void onReadError(final Exception e) {
		LOG.info("Error on Reading data" + e);
	}
	
	/**
	 * To catch Write Error.
	 *
	 * @param e the e
	 * @param item the item
	 */
	public void onWriteError(final Exception e, final Object item) {
		LOG.info("Error on While Writting Data" +e);
		LOG.info("Error on the Object :" + item);
	}

}
