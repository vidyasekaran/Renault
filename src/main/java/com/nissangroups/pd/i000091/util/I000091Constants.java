/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.util;

/**
 * Class I000091Constants to keep the Constants related to the Interface I000091
 *
 * @author z016127
 *
 */
public class I000091Constants {

	/**Constant null String */
	public static final String NULLSTRING = "null";
	
	/**Constant astreick */
	public static final String ASTREICK = "'*'";

	/** Constant Query String */
	public static final String AND_QRYSTRNG = "' AND ";
	
	/**Constant String used to retrieve unique records,  using this part of query along with other query would returns only the 
	 * columns selected in the subquery so it does not return all the columns*/
	public static final String queryString ="SELECT ROWNUM, a.* FROM ( ";

	/**Constant argument length */
	public static final int ARGS_LENGTH = 6;
	
	/**Constant null String */
	public static final String NULL = "";
	
	/**Constant String Data Process Failed*/
	public static final String DATAPRCSFAILED = "dataPrcsFailed";
	
	/**Constant String Completed */
	public static final String COMPLETED = "Completed";
	
	/**Constant String Por code */
	public static final String PORCD = "porCd";
	
	/**Constant String Order take base month */
	public static final String ORDR_TAK_BSE_MNTH = "ordrTkeBseMnth";
	
	/**Constant String no order take base period */
	public static final String NO_ORDR_TAK_BSE_PRD = "NoOrderTakeBasePeriod";
	
	/** Constant W  */
	public static final String W ="W";
	
	/** Constant M  */
	public static final String M ="M";
	
	/** Constant error message */
	public static final String errMsg ="Pattern does not exist";

	/**
	 * Instantiates a new I000091 Constants
	 */
    private I000091Constants() {
		super();		
	}
}
