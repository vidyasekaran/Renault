
/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : 
 * Module          :
 * Process Outline : Logs the mesage
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000059.util.B000059Logger;

public class InterfaceLogger {
	
	private static final Log LOG = LogFactory.getLog(B000059Logger.class
			.getName());

	public static void LogInfoMessage(String msg) {
		LOG.info(msg);		
	}

	public static void LogErrorMessage(String msg) {
		LOG.error(msg);		
	}

}

enum CustomLogMessage {

	M00003_1 {
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			B000059Logger.logInfoMessage(PDMessageConsants.M00003.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00003));
		}
	};
	
	public abstract void fetchLogMessage(String interfaceFileID, String msgs);

}