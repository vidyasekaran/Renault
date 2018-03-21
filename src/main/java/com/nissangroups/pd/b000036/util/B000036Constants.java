/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000036
 * Module                  : Ordering		
 * Process Outline     : Update Weekly order stage close															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000036.util;

/**
 * @author z014433
 *
 */
public final class B000036Constants {
	
	/** Constant BATCH_36_ID. */
	public static final String BATCH_36_ID = "b000036";
	
	/** Constant BATCH_36_CONFIG_PATH. */
	public static final String BATCH_36_CONFIG_PATH = "B000036/B000036_Batch_Config.xml";
	
	/** Constant BATCH_ID_B000036. */
	public static final String BATCH_ID_B000036 = "B000036";
	
	public static final String MST_WKLY_ORDR_ITEM_PROCESSOR = "mstwklyOrdrTkBsPrdProcessor";
	
	public static final String B000036WRITER = "b000036Writer";
	
	/** Constant ORDR_TK_BS_WEEK_NO. */
	public static final String ORDR_TK_BS_WK_NO = "OTWN";
	
	/** Constant WK*/
	public static final String CONSTANT_CLOSE = "C";
	
	/** Constant OPEN_STS*/
	public static final String OPEN_STS = "Open Status Order Take Base Period";
	
	/** Constant TBL_NM_MST_WKLY_ORDR_TAKE_BASE_PERIOD */
	public static final String TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD = "WEEKLY ORDER TAKE BASE PERIOD MST";
	
	/** Constant CLOSE*/
	public static final String CLOSED_STS = "closed";
	
private B000036Constants() {
    
}

}
