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
public class R000004ReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(POR_PARAM + DELIMITE_TAB+ PRMTR_ORDER_TAKE_BASE_MONTH + DELIMITE_TAB
				+ REPORT_PRODUCTION_MONTH + DELIMITE_TAB + REPORT_OCF_REGION
				+ DELIMITE_TAB + CR_SRS + DELIMITE_TAB + REPORT_BUYER_GROUP + DELIMITE_TAB
				+ REPORT_BUYER_CODE +  DELIMITE_TAB +REPORT_VOLUME_ALLOCATION + DELIMITE_TAB
				+ REPORT_ORDER_QTY + DELIMITE_TAB + REPORT_DIFFERENCE + DELIMITE_TAB
				+ REPORT_AUTO_ADJUST + DELIMITE_TAB+ REPORT_ORDER_QUANTITY_TO_PLANT + DELIMITE_TAB 
				+ REPORT_END_ITEM_APP + DELIMITE_TAB + REPORT_END_ITEM_PACK + DELIMITE_TAB + REPORT_SPEC_CODE
				+ DELIMITE_TAB + REPORT_HEADER_POT+ DELIMITE_TAB +REPORT_EXT_COLOR+ DELIMITE_TAB +REPORT_INT_COLOR
				+ DELIMITE_TAB +REPORT_EX_NO + DELIMITE_TAB +REPORT_ORDER_QTY + DELIMITE_TAB +REPORT_AUTO_ADJUST 
				+ DELIMITE_TAB + REPORT_ORDER_QUANTITY_TO_PLANT +DELIMITE_TAB +REPORT_RECORD_TYPE_CD
				+ DELIMITE_TAB + COMMENTS);

	}

}
