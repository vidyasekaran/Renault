/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000028.util;

import static com.nissangroups.pd.util.PDConstants.*;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * This is used for the Batch R00004 Report Headers.
 * 
 * @author z015060
 */
public class R000005ReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(POR_PARAM + DELIMITE_TAB + PRMTR_ORDER_TAKE_BASE_MONTH
				+ DELIMITE_TAB + REPORT_PRODUCTION_MONTH + DELIMITE_TAB
				+ REPORT_OCF_REGION + DELIMITE_TAB + CR_SRS + DELIMITE_TAB
				+ REPORT_BUYER_GROUP + DELIMITE_TAB + REPORT_OCF_FEATURE_CODE
				+ DELIMITE_TAB + REPORT_OCF_DESCRIPTION_SHORT + DELIMITE_TAB
				+ REPORT_OCF_DESCRIPTION_LONG + DELIMITE_TAB + OCF_LIMIT
				+ DELIMITE_TAB + REPORT_USAGE + DELIMITE_TAB
				+ REPORT_DIFFERENCE);

	}

}
