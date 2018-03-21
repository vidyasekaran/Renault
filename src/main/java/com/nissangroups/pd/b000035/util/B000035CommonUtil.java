/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000035.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class B000035CommonUtil {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000035CommonUtil.class);
	
	private B000035CommonUtil(){
	}
	
	public static void logMessage(String messageID,String processID,String[] messageParams){
		
		switch (processID) {
		case PDConstants.P0001:
			logMsgThreePrms(messageID,messageParams);
			stopBatch(); 
			break;
			
		case PDConstants.P0002:
			logMsgFourPrms(messageID,messageParams);
			break;
			
			default:
				break;
		}
	}
	
	private static void logMsgThreePrms(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
    			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]) ); 		
	}
	
	private static void logMsgFourPrms(String messageID, String[] messageParams) {
		LOG.info(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
    			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]).replace(PDConstants.ERROR_MESSAGE_4, messageParams[3]).
    			replace(PDConstants.ERROR_MESSAGE_5, messageParams[4])); 		
	}
	
	public static void stopBatch(){
		CommonUtil.stopBatch();
	}

}
