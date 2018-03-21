
/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.util;

public class B000040Constants 
{
	/** Constant BATCH_ID_B000040 */
	public static final String BATCH_ID_B000040 = "B000040";
	
	/** Constant BATCH_27_ID. */
	public static final String BATCH_40_ID = "b000040";
	
	/** Constant EXIT_STATUS. */
	public static final String EXIT_STATUS = "Exit Status";	
	/** Constant INTERFACE_FILE_ID. */
	public static final String POR_CD = "POR_CD";
	
	public static final String PRODUCTION_CAL_VALUE = "Y";
	
	public static final String param_ORDR_TK_PROD_WK_NO = ":ordrTkProdWkNo";	
	
	public static final String WKLY_ORDR_HRZN = "WKLY_ORDR_HRZN";	
	
	public static final String MNTHLY_FXD_ORDR_RQD = "MNTHLY_FXD_ORDR_RQRD";	
	
	public static final String ORGNL_LNE_CLS_PLNT_CD = "ORGNL_LN_CLS_PLNT_CD";	
	
	public static final String CNSTNT_LNE_CLS_PLNT_CD = "CNSNT_LN_CLS_PLNT_CD";	
	
	public static final String CNSTNT_DAY_NO = "CONSTANT_DAY_NO";	
	
	public static final String SUSPND_ORDR_RQD = "SUSPENDED_ORDER_RQRD";	
	
	public static final String N_MNTH_SUSPND_ORDR_RQD = "NMNTH_SPND_ORDR_RQRD";	
	
	public static final String WKLY_ORDR_PLNT = "WKLY_ORDR_TO_PLNT";
	
	public static final String param_OSEI_ID = ":oseiId";
	
	public static final String FRCST_MNTH_SPNDED_ORDR_RQD = "FC_MNTH_SPD_ORDR_RQD";
	
	public static final String param_PROD_WK_NO = ":prodWkNo";
	
	public static final String FRZN_SYMBL_RQD ="FRZEN_SYMBL_REQUIRED";
	
	public static final String PROD_MTHD_CD_RQD="PROD_MTHD_CD_REQUIRD";
	
	public static final String CNSTNT_PROD_MTHD_CD ="CNSTNT_PROD_MTHOD_CD";
	
	public static final String param_PROD_PLNT_CD = ":prodPlntCd";
	
	public static final String ATTACH_EX_NO = "ATTACH EX-NO";
	
	public static final String SRVCE_PRMR_RQD ="SERVICE PARAMETERS REQUIRED";
	
	public static final String ABLSH_DT_DSPLY_FRMT ="ABLSH_DTE_DSPLY_FRMT";
	
	public static final String PLNT_LNE_SMRY ="PLANT LINE SUMMARY";
	
	public static final String WKLY_OCF_SMARY =" WEEKLY OCF SUMMARY";
	
	public static final String CNSTNT_PLNT_LNE_CLSS ="CNSTNT_PLNT_LNE_CLS";
	
	
	
	public static final String M00119 ="&1 NoProduction Week Number details for given Production Month &2";
	
	public static final String M00090 ="&1 No Logical Pipeline Trn Order Informations for given POR &2 and Production Month &3 and Production Week No. &4";
	
	public static final String M00165 ="&1 No Frozen Type Cd for the given POR &2";
	
	public static final String M00166 ="&1 NoProduction method Cd for the given POR &2";
	
	public static final String M00167 ="&1 No EX-No details present for the given POR &2";
	
	public static final String M00163 ="&1: Records are successfully &2  in &3 for the given POR &4";
	
	public static final String M00164 ="&1: &2  Failed in &3 for the given POR &4";
	
	public static final String M00160 ="&1: There is no &2  for  POR_CD =&3  in &4-Table. So Batch process Stopped";
	
	public static final String M00315 ="&1: There is no &2  for  POR_CD =&3  in &4-Table. ";
	
	public static final String M00165_1 ="No Feature Cd , Buyer Group Cd related details for given POR -&1 in given production month &2 and production week no &3 for the UKOSEI ID -&4";
	
	public static final String B000040_REPORT_PATH = "B000040.Report.Path";
	
	public static final String B000040_ORDR_SPEC_ERR_RPT_NM = "B000040_WEEKLY_PRODUCTION_ORDER_SPEC_ERROR";
	
	public static final String B000040_MNTHLY_ORDR_DTL_RPT_NM = "B000040_MONTHLY_ORDER_DETAIL";

	public static final String STAGE_CD = "WK";

	public static final String STAGE_STTS_CD = "SC";

	public static final String PROD_CMPTED_SC = "There is no data for completed stage code is 'SC' for constructed production month.";
	
	//Batch 40 Error Message
	public static final String M00250 = "M00250 : &1 No Weekly Order Take Base Period Details available  for given POR &2, STAGE CD &3 and STAGE STAUS CD &4";
		
	public static final String M00091 ="&1 No Weekly Order Horizon available for given POR &2 in Parameter Mst.";
		

}
