/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000023
 * Module          :Send_OSEI_Frozen_Interface_to_NSC
 
 * Process Outline : Send the OSEI frozen master details to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000023.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000023
 * 
 * @author z014029
 */
public class I000023QueryConstants {

	/**
	 * Instantiates a new I000023 query constants.
	 */
	private I000023QueryConstants() {
		super();

	}

	/**
	 * P0001 Inserting the OSEI interface file header data into common file
	 * header master table
	 */	
	public static final StringBuilder insertCmnHeader = new StringBuilder()
	.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,REC_COUNT,TRN_TYPE,STTS,REMARKS)")
	.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:REC_COUNT,:TRN_TYPE,:STTS,:REMARKS)");
	
	/** P0002  Extraction of  OSEI Frozen master Data for base query */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM, MST_OSEI_FRZN.POR_CD,MST_OEI_SPEC.CAR_SRS,MST_OEI_SPEC.APPLD_MDL_CD,MST_OEI_SPEC.PCK_CD,MST_OEI_BUYER.BUYER_CD,MST_OEI_SPEC.SPEC_DESTN_CD,MST_OEI_SPEC.ADTNL_SPEC_CD,MST_OSEI.EXT_CLR_CD, ")
			.append("MST_OSEI.INT_CLR_CD,MST_OSEI_FRZN.FRZN_ORDR_TAKE_BASE_MNTH,MST_OSEI_FRZN.FRZN_PROD_MNTH,MST_OSEI_FRZN.FRZN_TYPE_CD,MST_OSEI_FRZN.CRTD_BY,MST_OSEI_FRZN.CRTD_DT,MST_OSEI_FRZN.UPDTD_BY,MST_OSEI_FRZN.UPDTD_DT ")
			.append("FROM MST_BUYER, MST_POR, MST_OEI_SPEC, MST_OEI_BUYER, MST_OSEI, MST_OSEI_FRZN, MST_BUYER_GRP WHERE ");

	/** P0002  Extraction of  OSEI Frozen master Data base query condition1  */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append("" + IFConstants.param_buyer_buyerGrpCD + "")
			.append("" + IFConstants.param_rhqCd + "")
			.append("" + IFConstants.param_ocfRegionCd + "")
			.append("" + IFConstants.param_ocfBuyerGrpCd + "")
			.append("" + IFConstants.param_porCd + "");

	/** P0002  Extraction of  OSEI Frozen master Data base query condition2 */
	public static final StringBuilder baseQueryCondition2 = new StringBuilder()
			.append("MST_BUYER.BUYER_GRP_CD	= MST_BUYER_GRP.BUYER_GRP_CD AND ")
			.append("MST_BUYER.PROD_REGION_CD =	MST_POR.PROD_REGION_CD AND ")
			.append("MST_POR.POR_CD	= MST_OEI_SPEC.POR_CD AND ")
			.append("MST_OEI_SPEC.OEI_SPEC_ID =	MST_OEI_BUYER.OEI_SPEC_ID AND ")
			.append("MST_OEI_BUYER.BUYER_CD	= MST_BUYER.BUYER_CD AND ")
			.append("MST_OEI_BUYER.OEI_BUYER_ID	= MST_OSEI.OEI_BUYER_ID AND ")
			.append("MST_OSEI.POR_CD = MST_OSEI_FRZN.POR_CD AND ")
			.append("MST_OSEI.OSEI_ID =	MST_OSEI_FRZN.OSEI_ID AND ")
			.append("MST_OEI_BUYER.POR_CD =	MST_POR.POR_CD ");


	/** P0003  INSERTING OSEI FROZEN INTERFACE FILE DATA INTO THE COMMON LAYER DATA MST */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
			.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19)")
			.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16, :COL17,:COL18,:COL19)");

	/** P0004  UPDATE THE MESSAGE STATUS INTO COMMON FILE HEADER */
	public static final StringBuilder updateCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/** P0004  UPDATE THE MESSAGE STATUS INTO COMMON FILE HEADER */
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/** P0004  UPDATE THE MESSAGE STATUS INTO COMMON FILE HEADER */
	public static final StringBuilder updateStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS ,REC_COUNT =:REC_COUNT, REMARKS =:REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

}
