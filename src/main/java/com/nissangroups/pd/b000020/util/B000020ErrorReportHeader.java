/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.util;

import static com.nissangroups.pd.util.PDConstants.*;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
* This is used for the Batch B000020 Report Headers.
* 
* @author z011479
*/
public class B000020ErrorReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write( PRMTR_ORDER_TAKE_BASE_MONTH + DELIMITE_TAB
				+ REPORT_PRODUCTION_MONTH + DELIMITE_TAB + BATCH_POR_CODE
				+ DELIMITE_TAB + CR_SRS + DELIMITE_TAB
				+ BUYER_GROUP_CD + DELIMITE_TAB + REPORT_ORDER_STAGE + DELIMITE_TAB
				+ REPORT_AVERAGE_CAL_BY + DELIMITE_TAB + REPORT_EI_BREAKDOWN_PRIORITY + DELIMITE_TAB
				+ REPORT_COLOR_BREAKDOWN_PRIORITY + DELIMITE_TAB + REPORT_ERROR_TYPE + DELIMITE_TAB + REPORT_ERROR_MESSAGE
				+ DELIMITE_TAB + BATCH_ID + DELIMITE_TAB + REPORT_ERROR_ID + DELIMITE_TAB + REPORT_TIME);

	}

}