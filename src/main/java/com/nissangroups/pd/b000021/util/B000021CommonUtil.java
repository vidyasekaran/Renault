/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-10-2015      z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000021.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

public class B000021CommonUtil {
	
	private B000021CommonUtil(){
	}
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000021CommonUtil.class);
	
	public static void logMessage(String messageID,String processID,String[] messageParams){
		
		switch (processID) {
		case PDConstants.P0001:
			if (PDMessageConsants.M00159.equals(messageID)){
				logM00159(messageID,messageParams);
			}
				else if (PDMessageConsants.M00201.equals(messageID)){
					logM00201(messageID);
				}
			stopBatch(); 
			break;
			
		case PDConstants.P0002:
			if (PDMessageConsants.M00161.equals(messageID)){
				logM00161(messageID,messageParams);
		}
			else if (PDMessageConsants.M00190.equals(messageID)){
				logM00190(messageID,messageParams);
			}
			break;
			
		case PDConstants.P0003:
			
			LOG.info(PDMessageConsants.M00190.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
        			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]).replace(PDConstants.ERROR_MESSAGE_4, messageParams[3]) ); 
			
			break;
			
			default:
				break;
		}
	}
	
	private static void logM00190(String messageID, String[] messageParams) {
		LOG.info(messageID.replace(PDConstants.ERROR_MESSAGE_1, messageParams[0]).replace(PDConstants.ERROR_MESSAGE_2, messageParams[1]).
    			replace(PDConstants.ERROR_MESSAGE_3, messageParams[2]).replace(PDConstants.ERROR_MESSAGE_4, messageParams[3]) ); 		
	}

	private static void logM00161(String messageID, String[] messageParams) {
		
		LOG.error(messageID.replace(ERROR_MESSAGE_1, messageParams[0]).replace(ERROR_MESSAGE_2, messageParams[1])
				.replace(ERROR_MESSAGE_3, messageParams[2]).replace(ERROR_MESSAGE_4,messageParams[3] )
				.replace(ERROR_MESSAGE_5,messageParams[4] ).replace(ERROR_MESSAGE_6,messageParams[5] )); 		
	}

	private static void logM00201(String messageID) {
 	   LOG.info(messageID.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_21_ID));
	}

	private static void logM00159(String messageID,String[] messageParams) {

		LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0]).replace(ERROR_MESSAGE_2, messageParams[1])
				.replace(ERROR_MESSAGE_3, messageParams[2]).replace(ERROR_MESSAGE_4,messageParams[3]).replace(ERROR_MESSAGE_5, messageParams[1]) );		
	}

	public static void stopBatch(){
		CommonUtil.stopBatch();
	}

}
