/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date          Name(Company Name)          Description 
 * ------------- --------------------------- ---------------------
 * 14-July-2015  z013865(RNTBCI)             New Creation
 *
 */
package com.nissangroups.pd.header;

import java.io.IOException;

import static com.nissangroups.pd.util.PDConstants.*;

import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * The Class ErrorReportHeader.
 *
 * @author z002548
 */
public class ErrorReportHeader implements FlatFileHeaderCallback {

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

		writer.write(POR_CODE + DELIMITE_TAB + PRODUCTION_STAGE_CODE
				+ DELIMITE_TAB + PRODUCTION_FAMILY_CODE + DELIMITE_TAB
				+ BUYER_CODE + DELIMITE_TAB + END_ITEM_MODEL_CODE
				+ DELIMITE_TAB + END_ITEM_COLOR_CODE + DELIMITE_TAB
				+ SPEC_DESTINATION_CODE + DELIMITE_TAB + ADDITION_SPEC_CODE
				+ DELIMITE_TAB + ADOPT_MONTH + DELIMITE_TAB + ABOLISH_MONTH
				+ DELIMITE_TAB + ERROR_CODE + DELIMITE_TAB + ERROR_MESSAGE);

	}

}
