/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000102
 * Module          : CM COMMON					
 * Process Outline : Send Logical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-12-2014  	  z015895(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000102.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface
 * I000102QueryConstants
 * 
 * @author z014676-
 * 
 */
public class I000102QueryConstants {

	/**
	 * P0003: Extract the Logical pipeline trn data from the mentioned tables
	 * based on the input parameters
	 */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT DISTINCT LOGICAL_PIPELINE_TRN.VHCL_SEQ_ID, LOGICAL_PIPELINE_TRN.POR_CD, LOGICAL_PIPELINE_TRN.PROD_PLNT_CD, ")
			.append("ORDERABLE_END_ITEM_SPEC_MST.CAR_SRS, ORDERABLE_END_ITEM_SPEC_MST.APPLD_MDL_CD, ORDERABLE_END_ITEM_SPEC_MST.PCK_CD, ")
			.append("ORDERABLE_SALES_END_ITEM_MST.EXT_CLR_CD, ORDERABLE_SALES_END_ITEM_MST.INT_CLR_CD, ORDERABLE_END_ITEM_SPEC_MST.ADTNL_SPEC_CD, ")
			.append("LOGICAL_PIPELINE_TRN.OFFLN_PLAN_DATE, ORDERABLE_END_ITEM_BUYER_MST.BUYER_CD, ORDERABLE_END_ITEM_SPEC_MST.SPEC_DESTN_CD, ")
			.append("LOGICAL_PIPELINE_TRN.LGCL_PPLN_STAGE_CD, LOGICAL_PIPELINE_TRN.SLS_NOTE_NO, LOGICAL_PIPELINE_TRN.EX_NO, ")
			.append("LOGICAL_PIPELINE_TRN.PROD_MNTH, LOGICAL_PIPELINE_TRN.POT_CD, LOGICAL_PIPELINE_TRN.PROD_ORDR_NO, LOGICAL_PIPELINE_TRN.ORDR_DEL_FLAG, ")
			.append("LOGICAL_PIPELINE_TRN.MS_FXD_FLAG, ORDERABLE_END_ITEM_SPEC_MST.PROD_FMY_CD, LOGICAL_PIPELINE_TRN.LINE_CLASS, ")
			.append("LOGICAL_PIPELINE_TRN.FRZN_TYPE_CD, LOGICAL_PIPELINE_TRN.PROD_MTHD_CD, LOGICAL_PIPELINE_TRN.VIN_NO, ")
			.append(" (SELECT  CASE   WHEN COUNT(*) = 0   THEN 0   ELSE 1  END FROM MST_OSEI_DTL , TRN_LGCL_PPLN WHERE TRN_LGCL_PPLN.POT_CD = MST_OSEI_DTL.OSEI_ADPT_DATE ) ETA_ADJUST_FLAG ")
			.append("FROM MST_POR INNER JOIN  TRN_LGCL_PPLN LOGICAL_PIPELINE_TRN ON MST_POR.POR_CD = LOGICAL_PIPELINE_TRN.POR_CD ")
			.append("INNER JOIN  MST_OEI_SPEC ORDERABLE_END_ITEM_SPEC_MST ON MST_POR.POR_CD = ORDERABLE_END_ITEM_SPEC_MST.POR_CD ")
			.append("INNER JOIN  MST_OEI_BUYER ORDERABLE_END_ITEM_BUYER_MST ON ORDERABLE_END_ITEM_BUYER_MST.OEI_SPEC_ID = ORDERABLE_END_ITEM_SPEC_MST.OEI_SPEC_ID ")
			.append("INNER JOIN  MST_OSEI ORDERABLE_SALES_END_ITEM_MST ON ORDERABLE_SALES_END_ITEM_MST.OEI_BUYER_ID = ORDERABLE_END_ITEM_BUYER_MST.OEI_BUYER_ID ")
			.append("INNER JOIN  MST_OSEI_DTL ORDERABLE_SALES_END_ITEM_DTL ON ORDERABLE_SALES_END_ITEM_DTL.OSEI_ID = ORDERABLE_SALES_END_ITEM_MST.OSEI_ID ")
			.append("INNER JOIN TRN_LGCL_PPLN LOGICAL_PIPELINE_TRN ON LOGICAL_PIPELINE_TRN.OSEI_ID = ORDERABLE_SALES_END_ITEM_DTL.OSEI_ID");

	/**
	 * P0003: Adding base conditions
	 */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append(" WHERE ")
			.append("ORDERABLE_SALES_END_ITEM_MST.OSEI_ID = LOGICAL_PIPELINE_TRN.OSEI_ID AND ")
			.append("ORDERABLE_END_ITEM_BUYER_MST.OEI_BUYER_ID = ORDERABLE_SALES_END_ITEM_MST.OEI_BUYER_ID AND ")
			.append("ORDERABLE_END_ITEM_SPEC_MST.OEI_SPEC_ID = ORDERABLE_END_ITEM_BUYER_MST.OEI_SPEC_ID AND ")
			.append("LOGICAL_PIPELINE_TRN.UPDTD_DT >= ( select process_executed_time from spec_reexecute_status where table_name ='LOGICAL_PIPELINE_TRN')");

	/**
	 * P0003 passing input parameter like POR_CD
	 */
	public static final StringBuilder inQueryCondition = new StringBuilder()
			.append(" AND LOGICAL_PIPELINE_TRN.POR_CD IN ("
					+ IFConstants.porCd_Param + ")");

	/**
	 * Instantiates a new I000102 query constants.
	 */
	private I000102QueryConstants() {
		super();
	}

}
