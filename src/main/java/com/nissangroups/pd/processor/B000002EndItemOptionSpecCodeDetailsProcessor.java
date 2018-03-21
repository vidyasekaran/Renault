/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemOptionSpecCodeDetailsProcessor
 * Module          :@Create Spec Masters
 * Process Outline :@Spliting Option Spec code 
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

import org.springframework.batch.item.ItemProcessor;

import static com.nissangroups.pd.util.CommonUtil.getSplittedString;
import static com.nissangroups.pd.util.CommonUtil.appendvalue;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.INT_SIX;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Processor Class to skip already present data
 * Process Id : P0006.
 * @version V1.0
 */
public class B000002EndItemOptionSpecCodeDetailsProcessor implements ItemProcessor<List<Object[]>, List<Object>>{
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000002EndItemOptionSpecCodeDetailsProcessor.class);
	/** Variable splittedlist. */
	List<Object> splittedList = new ArrayList<Object>();
	
	/** Variable empty list. */
	List<Object> emptyList = null;
	
	/** Variable to remove. */
	private List<Object[]> toRemove = new ArrayList<Object[]>();
	
	/**
	 * Processor method to split the Option Spec Code.
	 *
	 * @param eiOptionalSpecCodeDetailsList the ei optional spec code details list
	 * @return splittedList
	 * @throws Exception the exception
	 */
	@Override
	public List<Object> process(List<Object[]> eiOptionalSpecCodeDetailsList) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		for (Object[] fetchedData : eiOptionalSpecCodeDetailsList) {
			if ((String) fetchedData[0]  == null) {
				toRemove.add(fetchedData);
			}
		}
		/* removing already present data */
		eiOptionalSpecCodeDetailsList.removeAll(toRemove);
		
		/* Iterating the fetched Data */
		for(Object[] fetchedOptData : eiOptionalSpecCodeDetailsList)
		{
			String optSpecCd = (String) fetchedOptData[0];
			Object[] splittedArr = getSplittedString(optSpecCd, INT_SIX);
			for(Object splittedRes : splittedArr)	
				{
				/* Adding the appended Value */
				Object[] resArr = appendvalue(fetchedOptData, splittedRes);
				splittedList.add(resArr);
				}
			
			}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);	
	/* returning the splitted List */
		return splittedList;
	}

}

