/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000050.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class B000050CommonUtil {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000050CommonUtil.class);
	
	private B000050CommonUtil(){
	}
	
	public static void logMessage(String messageID,String processID,String[] messageParams){
		
		switch (processID) {
		case B000050Constants.P0001_1:
		case B000050Constants.P0001_2:
		case PDConstants.P0003:
		case B000050Constants.P0004_1:
			logMsgFourPrms(messageID,messageParams);
			stopBatch(); 
			break;
			
		case PDConstants.P0002:
			logMsgTwoPrms(messageID,messageParams);
			stopBatch();
			break;
			
			default:
				break;
		}
	}
	
	private static void logMsgTwoPrms(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1])); 		
	}
	
	private static void logMsgFourPrms(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
    			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]).replace(PDConstants.ERROR_MESSAGE_4, messageParams[3])); 		
	}
	
	public static void stopBatch(){
		CommonUtil.stopBatch();
	}

}
