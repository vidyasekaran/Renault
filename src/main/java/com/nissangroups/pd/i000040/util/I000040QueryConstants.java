/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000040
 * Module                 : OR Ordering		
 * Process Outline     	  : Send A0 ETA Designated parameter to PCS															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000040.util;

/**
 * Constant file to keep the queries related to the Interface I000040
 * 
 * @author z014676
 *
 */
public class I000040QueryConstants {
	
	/** Constant Variable M000003 */
	public static  final String M00003 = "M00003 : A0 ETA PARAMETER TRN NO DATA FOUND";
	/**
	 * P0002 Extract data from A0 ETA Designated Parameter data based on POR and Conditions
	 */
	public static  final StringBuilder fetchA0EtaDsgntdPrmtr = new StringBuilder()
	.append("select taep.POR_CD,taep.PROD_PLNT_CD,taep.PROD_MNTH,taep.APPLD_MDL_CD ")
	.append(" , taep.PCK_CD,taep.EX_NO,taep.SPEC_DESTN_CD,taep.PROD_FMY_CD,taep.OCF_REGION_CD,mos.CAR_SRS,taep.DATE_FRM_1,taep.DATE_TO_1,taep.QTY_1 ")
	.append("   from TRN_AO_ETA_PRMTR taep ")
	.append("  INNER JOIN MST_OEI_SPEC mos on taep.POR_CD = mos.POR_CD and taep.APPLD_MDL_CD = mos.APPLD_MDL_CD and taep.PROD_FMY_CD = mos.PROD_FMY_CD and ")
	.append("  taep.PCK_CD = mos.PCK_CD and taep.SPEC_DESTN_CD = mos.SPEC_DESTN_CD ");
	
	/** This Constant Variable WhereClause */
	public static  final StringBuilder whereClause = new StringBuilder()
	.append(" where ");
	
	/** Constant Variable porValue */
	public static  final StringBuilder porVlaue = new StringBuilder()
	.append(" taep.POR_CD= ");
	
	public static  final String RC = "A0";
	
	public static  final String SPEC_DEFAULT_VALUE = "1";
	
	public static  final String PREF_ORDER_DEFAULT_VALUE ="1";
	
	public static  final String REASON_CODE_DEFAULT_VALUE ="1";
	
	public static  final String SPACE = " ";
	
	/**
	 * Instantiates a new I000040QueryConstants
	 */
	private I000040QueryConstants(){
		
	}
	

}
