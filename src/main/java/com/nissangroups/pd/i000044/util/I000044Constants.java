/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.util;

/**
 * Class I000044Constants to keep the Constants related to the Interface I000044
 *
 * @author z016127
 *
 */
public class I000044Constants {

	/** Constant INTERFACE_FILE_ID. */
	public static final String INTERFACE_FILE_ID = "interfaceFileId";
	
	/** Constant summarize order flag */
	public static final String SUMMARIZE_ORDR_FLG= "1";

	/** Constant Query String */
	public static final String quryStng = "' AND ";
	
	/** Constant Argument length */
	public static final int ARGS_LENGTH = 4;
	
	/** Constant input argument length */
	public static final int FINALARG_LENGTH =5;
	
	/** Constant parameter list */
	public static final String PARAM_LIST = "paramList";
	
	/** Constant Por code  */
	public static final String POR_CD= "porCd";
	
	/** Constant no order take base period */
	public static final String NOORDR_TK_BASE_PRD ="NoOrderTakeBasePeriod";
	
	/** Constant completed */
	public static final String COMPLETED ="Completed";
	
	/** Constant Por code String  */
	public static final String PORCD_STR ="porCdStr";
	
	/** Constant Buyer Group Code String  */
	public static final String BUYERGRP_CODE_STR = "buyerGrpCdStr";
	
	/** Constant W  */
	public static final String W ="W";
	
	/** Constant M  */
	public static final String M ="M";
	
	/** Constant B  */
	public static final String B ="B";
	
	/** Constant C */
	public static final String C ="C";
	
	/** Constant zero  */
	public static final String ZERO ="0";
	
	/** Constant one  */
	public static final String ONE ="1";
	
	/** Constant POR CD */
	public static final String PORCD = "POR_CD";
	
	/** Constant String */
	public static final char S = 'S';
	
	/** Constant AMPERSAND. */
	public static final String AMPERSAND ="&";
	
	/**Constant input FORMAT */
	public static final String FORMAT = "@@";
	
	public static final String query = "SELECT ROWNUM, a.* FROM ( ";

	/**
	 * Instantiates a new I000044 Constants.
	 */
    private I000044Constants() {
		super();		
	}
}
