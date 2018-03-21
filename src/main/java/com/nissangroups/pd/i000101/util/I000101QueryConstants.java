/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000101
 * 
 * @author z014676
 * 
 */
public class I000101QueryConstants {

	/** Add where condition to Extract the Physical Pipeline data */
	public static final StringBuilder baseQueryCondition = new StringBuilder()

			.append(" TRN_LGCL_PPLN.VHCL_SEQ_ID	<> ' ' AND ")
			.append(" TRN_PHYSCL_PPLN.POR_CD IN (" + IFConstants.porCd_Param
					+ ")")
			.append(" AND TRN_LGCL_PPLN.VIN_NO=TRN_PHYSCL_PPLN.VIN_NO AND	MST_OSEI.OSEI_ID =	TRN_LGCL_PPLN.OSEI_ID AND	MST_OEI_BUYER.OEI_BUYER_ID	= MST_OSEI.OEI_BUYER_ID   ")
			.append(" AND MST_OEI_BUYER.OEI_SPEC_ID    = MST_OEI_SPEC.OEI_SPEC_ID ")
			.append(" AND TRN_PHYSCL_PPLN.UPDTD_DT >= (select process_executed_time from spec_reexecute_status where table_name = 'TRN_PHYSCL_PPLN') ");
	/** P0003 Extract the Physical Pipeline data from mention tables */
	public static final StringBuilder baseQuery = new StringBuilder()

			.append("SELECT  LGCL_PPLN_SEQ_ID,TRN_PHYSCL_PPLN.VIN_NO ,TRN_PHYSCL_PPLN.POR_CD,TRN_LGCL_PPLN.PROD_ORDR_NO,TRN_PHYSCL_PPLN.PROD_PLNT_CD, TRN_PHYSCL_PPLN.CAR_SRS ,TRN_PHYSCL_PPLN.APPLD_MDL_CD, ")
			.append(" TRN_PHYSCL_PPLN.PCK_CD ,TRN_PHYSCL_PPLN.EXT_CLR_CD,TRN_PHYSCL_PPLN.INT_CLR_CD,MST_OEI_BUYER.BUYER_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,TRN_PHYSCL_PPLN.SPEC_DESTN_CD,")
			.append("TRN_PHYSCL_PPLN.EX_NO,TRN_PHYSCL_PPLN.OCF_REGION_CD,TRN_PHYSCL_PPLN.LINE_CLASS, ENG_TYPE, ENG_NO,PRTYPE_VHCL_FLAG, INTRNL_OR_TRD_FLAG, TRN_PHYSCL_PPLN.PROD_MNTH, ")
			.append("TRN_PHYSCL_PPLN.PROD_WK_NO,	TRN_PHYSCL_PPLN.OFFLN_PLAN_DATE,PLNND_SETUP_DATE, PLNND_METAL_OK_DATE, PLNND_PAINT_IN_DATE,PLNND_PAINT_OK_DATE, PLNND_TRIM_IN_DATE,")
			.append("PLNND_FINAL_PASS_DATE,PLNND_FINAL_OK_DATE,PLNND_OFFLN_DATE,  PLNND_DLVRY_DATE, TRN_PHYSCL_PPLN.PLNND_PORT_IN_DATE ,PLNND_LDNG_DATE,")
			.append("ACTL_SETUP_DATE, ACTL_METAL_OK_DATE,ACTL_PAINT_IN_DATE, ACTL_PAINT_OK_DATE,ACTL_TRIM_IN_DATE,ACTL_OFFLN_DATE, ")
			.append("ACTL_FINAL_PASS_DATE ,ACTL_FINAL_OK_DATE,INSPCTN_DATE ,SCRPD_DATE, VIN_ALLCT_FLAG, MS_OFFLINE_DATE,")
			.append("ACTUAL_DELIVERY_DATE,PRODUCTION_FAMILY_CD,TRN_PHYSCL_PPLN.POT_CD,SALES_NOTE_NO")
			.append(" FROM TRN_PHYSCL_PPLN INNER JOIN TRN_LGCL_PPLN ON TRN_PHYSCL_PPLN.POR_CD = TRN_LGCL_PPLN.POR_CD INNER JOIN ")
			.append("MST_OSEI ON TRN_LGCL_PPLN.OSEI_ID =	MST_OSEI.OSEI_ID INNER JOIN MST_OEI_BUYER ON MST_OSEI.OEI_BUYER_ID = MST_OEI_BUYER.OEI_BUYER_ID INNER JOIN MST_OEI_SPEC ON MST_OEI_BUYER.OEI_SPEC_ID = MST_OEI_SPEC.OEI_SPEC_ID WHERE ");
	/** Extract all porcd data from physical pipeline */
	public static final StringBuilder porQuery = new StringBuilder()
			.append("SELECT POR_CD FROM TRN_PHYSCL_PPLN ");

	/*
	 * Private Constructor will prevent the instantiation of this class directly
	 */
	private I000101QueryConstants() {
		super();
	}
}
