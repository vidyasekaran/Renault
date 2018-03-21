/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 25-11-2015      z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000027.process;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class SlsNoteCalProcess.
 *
 * @author z014433
 */
public class SlsNoteCalProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(SlsNoteCalProcess.class
			.getName());
	
	/** Constructor  */
	public SlsNoteCalProcess() {
	}

	/**
	 * @param prdnMnth
	 * @param potCd
	 * @return sales note number
	 * 
	 * This method is used to calculate the sales note number based on the production month and pot cd
	 */
	public String calcSlsNoteNum(String prdnMnth, String potCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
    	LOG.info("Production month  is : "+prdnMnth+" , POT value is " +potCd);
    	
    	String newSlsNoteNo = prdnMnth.substring(4, 6)+potCd;
    	
    	LOG.info("New Sales Note No is  :"+newSlsNoteNo);
    	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return newSlsNoteNo;
	}

}
