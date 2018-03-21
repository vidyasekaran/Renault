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
 * 12-11-2015      z014433(RNTBCI)               Initial Version
 *
 */ 
package com.nissangroups.pd.b000027.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

public class B000027CommonUtil {
	
	private B000027CommonUtil(){
	}
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000027CommonUtil.class);
	
	public static void logMessage(String messageID,String processID,String[] messageParams){
		
		switch (processID) {
		case PDConstants.P0001:
		case B000027Constants.P0003_1:
		case B000027Constants.P0004:
		case B000027Constants.P0007:
		case B000027Constants.P0012:
			logMsgThreePrms(messageID,messageParams);
			stopBatch();
			break;
			
		case B000027Constants.P0003_2:
			if (PDMessageConsants.M00161.equals(messageID)){
				logM00161(messageID,messageParams);
		}
			else if (PDMessageConsants.M00182.equals(messageID)){
				logMsgTwoPrms(messageID,messageParams);
			}
			stopBatch(); 
			break;
		
		case PDConstants.P0002:
		case B000027Constants.P0003_2_VALIDATION:
		case B000027Constants.P0004_2:
		case B000027Constants.P0010_1_1:
		case B000027Constants.P0010_4_1:
		case B000027Constants.P0010_1_2:
		case B000027Constants.P0010_2:
			logMsgTwoPrms(messageID,messageParams);
			stopBatch(); 
			break;
		
		//Added for JT redmine defect # 2922,2920	
		case B000027Constants.P0010_5:
		case B000027Constants.P0010_4:
			logMsgTwoPrms(messageID,messageParams);
			break;
			
			default:
				break;
		}
	}
	
	private static void logMsgThreePrms(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
    			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]) ); 		
	}
	
	private static void logMsgTwoPrms(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]) ); 
	}
	
	
	private static void logM00161(String messageID, String[] messageParams) {
		LOG.error(messageID.replace(ERROR_MESSAGE_1, messageParams[0]).replace(ERROR_MESSAGE_2, messageParams[1])
				.replace(ERROR_MESSAGE_3, messageParams[2]).replace(ERROR_MESSAGE_4,messageParams[3] )
				.replace(ERROR_MESSAGE_5,messageParams[4] ).replace(ERROR_MESSAGE_6,messageParams[5] )); 		
	}

	public static void stopBatch(){
		CommonUtil.stopBatch();
	}

}
