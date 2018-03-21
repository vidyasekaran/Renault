/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */  
package com.nissangroups.pd.b000051.util;

/**
 * The Class B000051Constants.
 *
 * @author z002548
 */
public class B000051Constants {

	
	 
	public static final String BATCH_ID_B000051 = "B000051";
	
	public static final String BATCH_ID_B000051_PROCESSOR_1 = "B000051P1";
	
	public static final String BATCH_ID_B000051_PROCESSOR_2 = "B000051P2";

	public static final String ERROR_MESSAGE_ID1 = " Two Arguments Expected. [POR_CD] , [RERUN_FLAG]";
	
	public static final String ERROR_MESSAGE_ID2=" in table WEEKLY_ORDER_TRN"; 
	
	public static final String ERROR_MESSAGE_ID3 = " in LATEST MASTER SCHEDULE TRN for the given POR=";
	
	public static final String ERROR_MESSAGE_ID4=" Buyer Group lavel usage";
	
	public static final String ERROR_MESSAGE_ID5=" Regional Group lavel usage";
	
	public static final String ERROR_MESSAGE_ID6 =" in MST_WK_NO_CLNDR table ";
	
	public static final String ERROR_MESSAGE_ID7 =" in MST_WEEKLY_ORDER_TAKE_BASE_PERIOD table for previous month  ";

	

	
	private B000051Constants() {
	}
	
}
