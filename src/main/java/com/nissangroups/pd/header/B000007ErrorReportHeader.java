/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date         Name(Company Name)           Description 
 * ------------ ---------------------------- ---------------------
 * 14-Jul-2015  z011479(RNTBCI)              New Creation
 *
 */
package com.nissangroups.pd.header;

import static com.nissangroups.pd.util.PDConstants.*;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * This is used for the Batch B00007 Report Headers.
 * 
 * @author z011479
 */
public class B000007ErrorReportHeader implements FlatFileHeaderCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(POR_CODE + DELIMITE_TAB + PRIORITY + DELIMITE_TAB
				+ FROZEN_PRODUCTION_MONTH + DELIMITE_TAB + FROZEN_TIMING
				+ DELIMITE_TAB + FROZEN_ORDERETAKE_BASE_MONTH + DELIMITE_TAB
				+ FROZEN_TYPE + DELIMITE_TAB + OCF_REGION_CODE + DELIMITE_TAB
				+ PREFIX_YES + DELIMITE_TAB + PREFIX_NO + DELIMITE_TAB
				+ SUFFIX_YES + DELIMITE_TAB + SUFFIX_NO + DELIMITE_TAB + EXT1
				+ DELIMITE_TAB + INT1 + DELIMITE_TAB + EXT2 + DELIMITE_TAB
				+ INT2 + DELIMITE_TAB + EXT3 + DELIMITE_TAB + INT3
				+ DELIMITE_TAB + EXT4 + DELIMITE_TAB + INT4 + DELIMITE_TAB
				+ EXT5 + DELIMITE_TAB + INT5 + DELIMITE_TAB + DEST1
				+ DELIMITE_TAB + DEST2 + DELIMITE_TAB + DEST3 + DELIMITE_TAB
				+ DEST4 + DELIMITE_TAB + DEST5 + DELIMITE_TAB + ERROR_MESSAGE);

	}

}