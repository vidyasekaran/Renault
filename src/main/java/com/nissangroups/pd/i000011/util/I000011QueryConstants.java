/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000011
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Control the Order Take on Local Systems 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000011.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000011.
 * 
 * @author z014029
 */
public class I000011QueryConstants {

	/**
	 * Instantiates a new I000011 query constants.
	 */
	private I000011QueryConstants() {
		super();
	}

	/**
	 * P0002 Inserting the OSEI interface file header data into common file
	 * header master table
	 */
	public static final StringBuilder insertCmnHeader = new StringBuilder()
			.append("INSERT into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,TRN_TYPE)")
			.append(" values (:IF_FILE_ID,:SEQ_NO,:TRN_TYPE)");

	/** P0003 Extracting the OSEI data */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT DISTINCT MST_OEI_SPEC.POR_CD,MST_OEI_SPEC.CAR_SRS,MST_OEI_BUYER.BUYER_CD,MST_OEI_SPEC.APPLD_MDL_CD,MST_OEI_SPEC.PCK_CD,MST_OEI_SPEC.SPEC_DESTN_CD,MST_OSEI.EXT_CLR_CD,")
			.append("MST_OSEI.INT_CLR_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,MST_OSEI_DTL.OSEI_ADPT_DATE,MST_OSEI_DTL.OSEI_ABLSH_DATE,MST_OSEI_DTL.OSEI_SUSPENDED_DATE,MST_OEI_SPEC.PROD_FMY_CD,")
			.append("MST_OSEI_DTL.LCL_NOTE,MST_OSEI_DTL.PCKGE_NAME,MST_OEI_BUYER_OPTN_SPEC_CD.OPTN_SPEC_CODE,MST_OSEI_DTL.END_ITM_STTS_CD,MST_OSEI_DTL.CRTD_BY,MST_OSEI_DTL.CRTD_DT,")
			.append("MST_OSEI_DTL.UPDTD_BY,MST_OSEI_DTL.UPDTD_DT FROM MST_BUYER INNER JOIN MST_BUYER_GRP ON  MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD ")
			.append("INNER JOIN MST_POR ON MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD INNER JOIN MST_OEI_BUYER ON MST_OEI_BUYER.POR_CD = MST_POR.POR_CD ")
			.append("INNER JOIN MST_OEI_SPEC ON MST_OEI_SPEC.OEI_SPEC_ID = MST_OEI_BUYER.OEI_SPEC_ID AND MST_POR.POR_CD = MST_OEI_SPEC.POR_CD ")
			.append("INNER JOIN MST_OSEI ON MST_OEI_BUYER.OEI_BUYER_ID = MST_OSEI.OEI_BUYER_ID INNER JOIN MST_OSEI_DTL ON  MST_OSEI.OSEI_ID = MST_OSEI_DTL.OSEI_ID ")
			.append("INNER JOIN MST_OEI_BUYER_OPTN_SPEC_CD ON MST_OEI_BUYER.OEI_BUYER_ID = MST_OEI_BUYER_OPTN_SPEC_CD.OEI_BUYER_ID WHERE ");

	/** P0003 Extracting the OSEI data based on the conditions */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append("" + IFConstants.param_buyer_buyerGrpCD + "")
			.append("" + IFConstants.param_rhqCd + "")
			.append("" + IFConstants.param_ocfRegionCd + "")
			.append("" + IFConstants.param_ocfBuyerGrpCd + "")
			.append("" + IFConstants.param_porCd + "")
			.append("" + IFConstants.param_prdLmt + "")
			.append("" + IFConstants.param_endItmSttsCd + "");

	/** P0003 Fetching the Maximum Order Take Base Month for against por code */
	public static final StringBuilder fetchLatestOrdrTkBsOrdData = new StringBuilder()
			.append("SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE WHERE POR = :porCd AND STAGE_CD = 'WK' ");

	/** P0004 Insert the data into CMN_INTERFACE_DATA table */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
			.append("INSERT into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19,COL20,COL21,COL22,COL23,COL24)")
			.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16 , :COL17 , :COL18 , :COL19 , :COL20 , :COL21 , :COL22 , :COL23 , :COL24 )");

	/** P0005 - Update the message status into common file header */
	public static final StringBuilder updateStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS ,REC_COUNT =:REC_COUNT, REMARKS =:REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	/** P0005 - Update the message status into common file header */
	public static final StringBuilder updateCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/** P0005 - Update the message status into common file header */
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS, REC_COUNT = :REC_COUNT, REMARKS = :REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");
	
	/** Input Argument Length */
	public static final int argLength=7;
	
	/** Input Argument Length */
	public static final int argCount=6;
	
	/** Replace Position Value */
	public static final int replacebyTwo=2;
	
	/** Replace Position Value */
	public static final int replacebyThree=3;
	
}