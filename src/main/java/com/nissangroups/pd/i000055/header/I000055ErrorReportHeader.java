/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.header;

import static com.nissangroups.pd.util.PDConstants.BATCH_POR_CODE;
import static com.nissangroups.pd.util.PDConstants.DELIMITE_TAB;
import static com.nissangroups.pd.util.PDConstants.CR_SRS;
import static com.nissangroups.pd.util.PDConstants.ORDER_TAKE_BASE_PERIOD;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE1;
import static com.nissangroups.pd.util.PDConstants.FEATURE_CD;
import static com.nissangroups.pd.util.PDConstants.OCF_BUYER_GROUP;
import static com.nissangroups.pd.util.PDConstants.OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.OCF_LIMIT;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_REGION_CD;
import static com.nissangroups.pd.util.PDConstants.OCF_SHORT_DESCRIPTION;
import static com.nissangroups.pd.util.PDConstants.PRODUCTION_PERIOD;



import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/*
 * P0005.1:This Class is using create empty TSV file for writing to a header to a file
 */
public class I000055ErrorReportHeader implements FlatFileHeaderCallback{

	/*
	 * This method using to write file Header name 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
	
		writer.write(BATCH_POR_CODE + DELIMITE_TAB  + ORDER_TAKE_BASE_PERIOD + DELIMITE_TAB
				+ PRODUCTION_PERIOD + DELIMITE_TAB + CR_SRS + DELIMITE_TAB
				+ REPORT_OCF_REGION_CD + DELIMITE_TAB + OCF_BUYER_GROUP + DELIMITE_TAB
				+ OCF_FRAME_CD + DELIMITE_TAB + FEATURE_CD + DELIMITE_TAB + OCF_SHORT_DESCRIPTION
				+ DELIMITE_TAB + OCF_LIMIT + DELIMITE_TAB + ERROR_MESSAGE1);		
	}
}