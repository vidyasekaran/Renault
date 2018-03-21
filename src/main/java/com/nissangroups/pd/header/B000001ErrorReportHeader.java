/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date         Name(Company Name)           Description 
 * ------------ ---------------------------- ---------------------
 * 04-Jul-2015  z002548(RNTBCI)              New Creation
 *
 */
package com.nissangroups.pd.header;

import java.io.IOException;

import static com.nissangroups.pd.util.PDConstants.*;

import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * The Class B000001ErrorReportHeader.
 *
 * @author z002548
 */
public class B000001ErrorReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	/*
	 * Report Header Write the Report header
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {

		writer.write(ERROR_TYPE_CODE + DELIMITE_TAB + POR_CODE + DELIMITE_TAB
				+ PRODUCTION_FAMILY_CODE + DELIMITE_TAB + PRODUCTION_STAGE_CODE
				+ DELIMITE_TAB + BUYER_CODE + DELIMITE_TAB
				+ END_ITEM_MODEL_CODE + DELIMITE_TAB + COLOR_CODE
				+ DELIMITE_TAB + ADDITION_SPEC_CODE + DELIMITE_TAB
				+ SPEC_DESTINATION_CODE + DELIMITE_TAB + ADOPT_MONTH
				+ DELIMITE_TAB + ABOLISH_MONTH + DELIMITE_TAB + COMMENTS);

	}

}
