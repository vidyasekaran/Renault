/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Batch
 * Function ID            : PST-DRG-I000106
 * Module                 : CM Common		
 * Process Outline     	  : Interface for Sending  Buyer Master to SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000106.util;

/**
 * Constant file to keep the queries related to the Interface I000106
 * @author z016127
 *
 */
public class I000106QueryConstants {

	/**P0002 Constant to extract all the Buyer Group data details from the Master table,using this part of query along with 
	 * other query would returns only the columns selected in the subquery so it does not return all the columns */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM, a.* FROM ( ")
			.append("SELECT 'ZVMS_BUYER_GROUP' AS Col1, CASE WHEN ACTV_FLG ='Y' THEN 'N' ELSE 'Y' end  AS Col2, BUYER_GRP_CD  AS Col3, MC_REGION_CD  AS Col4, SUB_REGION_CD  AS Col5, BUYER_GRP_DESC  AS Col6, RHQ_CD  AS Col7 FROM  MST_BUYER_GRP ");
	
	/**P0002 Constant to extract all the Buyer details from the Master table.*/
	public static final StringBuilder baseQuery1 = new StringBuilder()
			.append("SELECT 'ZVMS_BUYER' AS Col1, CASE WHEN ACTV_FLG ='Y' THEN 'N' ELSE 'Y' end  AS Col2, PROD_REGION_CD AS Col3, BUYER_CD AS Col4,	BUYER_DESC AS Col5, BUYER_GRP_CD AS Col6, END_OF_PPLN_ACHV AS Col7 FROM MST_BUYER ");
	
	/**P0002 Constant to extract all the Buyer Spec Destination details from the Master table.*/
	public static final StringBuilder baseQuery2 = new StringBuilder()
			.append("SELECT 'ZVMS_BUYER_SDC' AS Col1, CASE WHEN ACTV_FLG ='Y' THEN 'N' ELSE 'Y' end  AS Col2, PROD_REGION_CD AS Col3, BUYER_CD AS Col4,	SPEC_DESTN_CD AS Col5, '' AS Col6, '' AS Col7 from MST_BUYER_SPEC_DESTN ");
	
	/**Add condition to extract Buyer Group data from Master table if Updated data equal to 1*/
	public static final StringBuilder whereCondition = new StringBuilder()
			.append("WHERE UPDTD_DT > = (SELECT MAX(PROCESS_EXECUTED_TIME) FROM SPEC_REEXECUTE_STATUS WHERE TABLE_NAME = 'MST_BUYER_GRP')");
	
	/**Add condition to extract Buyer data from Master table if Updated data equal to 1*/
	public static final StringBuilder whereCondition1 = new StringBuilder()
			.append("WHERE UPDTD_DT > = (SELECT MAX(PROCESS_EXECUTED_TIME) FROM SPEC_REEXECUTE_STATUS WHERE TABLE_NAME = 'MST_BUYER')");
	
	/**Add condition to extract Buyer Spec Destination from Master table if Updated data equal to 1*/
	public static final StringBuilder whereCondition2 = new StringBuilder()
			.append("WHERE UPDTD_DT > = (SELECT MAX(PROCESS_EXECUTED_TIME) FROM SPEC_REEXECUTE_STATUS WHERE TABLE_NAME = 'MST_BUYER_SPEC_DESTN')");
	
	/**
	 * Instantiates a new I000106 query constants.
	 */
	private I000106QueryConstants() {
		super();		
	} 
}
