/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date        Name(Company Name)            Description 
 * ----------- ----------------------------- ---------------------
 * 14-Aug-2015 z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.header;

import static com.nissangroups.pd.util.PDConstants.*;
import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * The Class B000005ErrorReportHeader.
 *
 * @author z010343
 */
public class B000005ErrorReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {

		writer.write(B4_POR_CODE + DELIMITE_TAB + CAR_SERIES + DELIMITE_TAB
				+ B4_BUYER_CODE + DELIMITE_TAB + EI_MODEL_CODE + DELIMITE_TAB
				+ ADOPT_DATE + DELIMITE_TAB + ABOLISH_DATE + DELIMITE_TAB
				+ ADD_SPEC_CODE + DELIMITE_TAB + SPEC_DESTINATION
				+ DELIMITE_TAB + B4_OCF_REGION_CODE + DELIMITE_TAB
				+ B4_OCF_BUYER_GRP_CODE + DELIMITE_TAB + COMMENTS);

	}

}