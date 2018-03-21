/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.header;

import static com.nissangroups.pd.b000047.util.B000047Constants.BUYER_CD;
import static com.nissangroups.pd.b000047.util.B000047Constants.ERROR_WARNING_MSG;
import static com.nissangroups.pd.b000047.util.B000047Constants.EX_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.OCF_REGION_CD;
import static com.nissangroups.pd.b000047.util.B000047Constants.ORDER_PLANT_CD;
import static com.nissangroups.pd.b000047.util.B000047Constants.ORDER_PROD_WEEK_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.ORDR_OFFLINE_DATE;
import static com.nissangroups.pd.b000047.util.B000047Constants.PRODUCTION_MONTH;
import static com.nissangroups.pd.b000047.util.B000047Constants.PROD_ORDR_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.SALES_NOTE_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.VIN_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.VIN_OFFLINE_DATE;
import static com.nissangroups.pd.b000047.util.B000047Constants.VIN_PLANT_CD;
import static com.nissangroups.pd.b000047.util.B000047Constants.VIN_PROD_WEEK_NO;
import static com.nissangroups.pd.b000047.util.B000047Constants.WARNING_MSG;
import static com.nissangroups.pd.util.PDConstants.CAR_SERIES;
import static com.nissangroups.pd.util.PDConstants.DELIMITE_TAB;
import static com.nissangroups.pd.util.PDConstants.END_ITEM_MODEL_CODE;
import static com.nissangroups.pd.util.PDConstants.REPORT_COLOR_CODE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POR;
import static com.nissangroups.pd.util.PDConstants.SPEC_DESTINATION_CODE;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * This Class B000047ErrorReportHeader is using create empty TSV file for writing the header to a file
 */
public class B000047ErrorReportHeader implements FlatFileHeaderCallback{

	/**
	 * This method is used to write file Header name in TSV file
	 * @see
	 * org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader
	 * (java.io.Writer)
	 */
	@Override
	public void writeHeader(Writer writer) throws IOException {
	
		writer.write(ERROR_WARNING_MSG + DELIMITE_TAB + REPORT_HEADER_POR + DELIMITE_TAB
					+ OCF_REGION_CD + DELIMITE_TAB + PRODUCTION_MONTH + DELIMITE_TAB
					+ CAR_SERIES + DELIMITE_TAB + BUYER_CD + DELIMITE_TAB + ORDER_PROD_WEEK_NO
					+ DELIMITE_TAB + VIN_PROD_WEEK_NO + DELIMITE_TAB + PROD_ORDR_NO + DELIMITE_TAB 
					+ VIN_NO + DELIMITE_TAB + END_ITEM_MODEL_CODE + DELIMITE_TAB + REPORT_COLOR_CODE
					+ DELIMITE_TAB + SPEC_DESTINATION_CODE + DELIMITE_TAB + SALES_NOTE_NO + DELIMITE_TAB
					+ EX_NO + DELIMITE_TAB + ORDR_OFFLINE_DATE + DELIMITE_TAB + VIN_OFFLINE_DATE
					+ DELIMITE_TAB +ORDER_PLANT_CD + DELIMITE_TAB +VIN_PLANT_CD + DELIMITE_TAB +WARNING_MSG);
	}
}