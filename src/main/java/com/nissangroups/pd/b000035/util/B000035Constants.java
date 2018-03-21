/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000035.util;

/**
 * @author z014433
 *
 */
public final class B000035Constants {
	
	/** Constant BATCH_35_ID. */
	public static final String BATCH_35_ID = "b000035";
	
	/** Constant BATCH_35_CONFIG_PATH. */
	public static final String BATCH_35_CONFIG_PATH = "B000035/B000035_Batch_Config.xml";
	
	/** Constant BATCH_ID_B000035. */
	public static final String BATCH_ID_B000035 = "B000035";
	
	public static final String WKLY_ORDR_ITEM_PROCESSOR = "wklyOrdrTkBsPrdProcessor";
	
	public static final String B000035WRITER = "b000035Writer";
	
	/** Constant ORDR_TK_BS_WEEK_NO. */
	public static final String ORDR_TK_BS_WK_NO = "OTWN";
	
	/** Constant WK*/
	public static final String CONSTANT_WK = "WK";
	
	/** Constant OPEN_STS*/
	public static final String OPEN_STS = "Open Status Order Take Base Week";
	
	/** Constant TBL_NM_MST_WKLY_ORDR_TAKE_BASE_PERIOD */
	public static final String TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD = "WEEKLY ORDER TAKE BASE PERIOD MST";
	
	/** Constant OPEN*/
	public static final String OPENED_STS = "opened";
	
	
private B000035Constants() {
    
}

}
