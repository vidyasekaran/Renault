/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_1;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_2;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_3;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_4;

public class CommonUtil {
	
	private static final Log LOG = LogFactory.getLog(CommonUtil.class);
	
	public static void logMessage(String messageID,String processID,String[] messageParams){
		
		if (Constants.P1.equals(processID)){
			
			LOG.info("Inside logger");
			
			LOG.info(messageID.replace(ERROR_MESSAGE_1 , messageParams[0]).replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2]).replace(ERROR_MESSAGE_4,messageParams[3] ));
			
		}
		
	}
	
	private CommonUtil(){
		
	}
	
}
