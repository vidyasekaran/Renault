
/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : Logs the mesage
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000059.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000059Logger {

	private static final Log LOG = LogFactory.getLog(B000059Logger.class
			.getName());

	public B000059Logger(){		
	}
	public static void logInfoMessage(String msg) {
		LOG.info(msg);		
	}

	public static void logErrorMessage(String msg) {
		LOG.error(msg);		
	}

}

enum CustomLogMessage {

	M00003_1 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			B000059Logger.logInfoMessage(PDMessageConsants.M00003.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00003));
		}
	},
	M00003_2 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			B000059Logger.logInfoMessage(PDMessageConsants.M00003.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00003));
		}
	},
	M00109 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {
            
			//local_system_path and filename_format required
			B000059Logger.logErrorMessage(PDMessageConsants.M00109.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID));			
		}
	},
	M00132 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//file_names_list required
			B000059Logger.logErrorMessage(PDMessageConsants.M00132.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID));			
		}
	},	
	M00167 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {
			
			B000059Logger.logErrorMessage(PDMessageConsants.M00335.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID));
		}
	},
	M00168 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//column name required
			B000059Logger.logErrorMessage(PDMessageConsants.M00336.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00336 + msgs));
		}
	},
	M00025 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//column name required
			B000059Logger.logErrorMessage(PDMessageConsants.M00025.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00025 + msgs));			
		}

	},	
	M00133 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//run time error msg required
			B000059Logger.logInfoMessage(PDMessageConsants.M00337.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00337 + msgs));
		}
	},
	M00043_1 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {
			
			B000059Logger.logErrorMessage(PDMessageConsants.M00043.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00043)+" "+msgs);
			
		}
	},
	M00043_2 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//run time error msg required
			B000059Logger.logErrorMessage(PDMessageConsants.M00043.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID).replace(
					PDConstants.ERROR_MESSAGE_2, PDMessageConsants.M00043));
			
		}
	},		
	M00113 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//run time error msg required
			B000059Logger.logInfoMessage(PDMessageConsants.M00113.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID));
		}

	},
	M00076 {
		@Override
		public void fetchLogMessage(String interfaceFileID, String msgs) {

			//run time error msg required
			B000059Logger.logErrorMessage(PDMessageConsants.M00076.replace(
					PDConstants.ERROR_MESSAGE_1, interfaceFileID));
		}

	};

	public abstract void fetchLogMessage(String interfaceFileID, String msgs);

}
