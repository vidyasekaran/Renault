/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@Adding staus code details for each End Item
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @09 July 2015  	  @author(z013576)              New Creation
 *
 *
 */
package com.nissangroups.pd.processor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.batch.item.ItemProcessor;

import static com.nissangroups.pd.util.CommonUtil.appendvalue;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.INT_TEN;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Processor Class to add the end item Status Code
 * Process Id - P0005.
 * @version V1.0
 */

public class B000002EndItemDetailsProcessor implements ItemProcessor<List<Object[]>, List<Object> >{
	 private static final Log LOG = LogFactory.getLog(B000002EndItemDetailsProcessor.class);
	/** Variable entity mgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityMgr;

	/** Variable ei statuscd. */
	private int eiStatuscd = INT_TEN;
	
	/** Variable ei osei processed list. */
	private List<Object> eiOseiProcessedList = new ArrayList<Object>();

	/**
	 * Processor method to add the end item status code with the fetched result.
	 *
	 * @param eiOSEIDetailsList the ei osei details list
	 * @return eiOseiProcessedList
	 * @throws Exception the exception
	 */
	@Override
	public List<Object> process(List<Object[]> eiOSEIDetailsList) throws Exception {
		    LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			/* iterating the fetched List */
			for(Object[] val : eiOSEIDetailsList)
			{
				Object eiStsCd = eiStatuscd;
				Object[] crtdEiSttsCd = appendvalue(val, eiStsCd);
				eiOseiProcessedList.add(crtdEiSttsCd);
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning processed List */
		return eiOseiProcessedList;
	}
	
	

}
