/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000088
 * Module                 : Ordering		
 * Process Outline     	  : Send Weekly Production Schedule Interface to NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014029(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000088.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000088
 * 
 * @author z014029
 * 
 */
public class I000088QueryConstants {

	public static final StringBuilder updateStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS ,REC_COUNT =:REC_COUNT, REMARKS =:REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	/**
	 * P0002 Extract Order take base period based on given POR CD
	 */
	public static final StringBuilder getOrdrTkBsPd = new StringBuilder()
			.append("SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE ")
			.append("WHERE MST_WKLY_ORDR_TAKE_BASE.POR IN (:POR_CD) AND ")
			.append("MST_WKLY_ORDR_TAKE_BASE.STAGE_CD = 'SC' AND ")
			.append("MST_WKLY_ORDR_TAKE_BASE.STAGE_STTS_CD = 'C' ");

	/**
	 * P0003 Extracting Weekly Production Schedule data from weekly production
	 * schedule trn, mst por ,mst buyer,mst buyer group
	 */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append(" SELECT  DISTINCT TRN_WKLY_PROD_SHDL.POR_CD, TRN_WKLY_PROD_SHDL.CAR_SRS, TRN_WKLY_PROD_SHDL.BUYER_CD, TRN_WKLY_PROD_SHDL.APPLD_MDL_CD, ")
			.append(" TRN_WKLY_PROD_SHDL.PCK_CD, TRN_WKLY_PROD_SHDL.SPEC_DESTN_CD, TRN_WKLY_PROD_SHDL.ADD_SPEC_CD, TRN_WKLY_PROD_SHDL.EXT_CLR_CD, ")
			.append(" TRN_WKLY_PROD_SHDL.INT_CLR_CD, TRN_WKLY_PROD_SHDL.POT_CD, TRN_WKLY_PROD_SHDL.PROD_PLNT_CD, TRN_WKLY_PROD_SHDL.LINE_CLASS, ")
			.append(" TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH, TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_WK_NO, TRN_WKLY_PROD_SHDL.PROD_MNTH, TRN_WKLY_PROD_SHDL.PROD_WK_NO, ")
			.append(" TRN_WKLY_PROD_SHDL.PROD_MTHD_CD, TRN_WKLY_PROD_SHDL.OFFLN_PLAN_DATE, TRN_WKLY_PROD_SHDL.ORDR_QTY, TRN_WKLY_PROD_SHDL.FRZN_TYPE_CD, ")
			.append(" TRN_WKLY_PROD_SHDL.LOCAL_PROD_ORDR_NO, TRN_WKLY_PROD_SHDL.PROD_ORDR_NO, TRN_WKLY_PROD_SHDL.EX_NO, TRN_WKLY_PROD_SHDL.PROD_FMY_CD, ")
			.append(" TRN_WKLY_PROD_SHDL.SLS_NOTE_NO, TRN_WKLY_PROD_SHDL.FXD_SYMBL, TRN_WKLY_PROD_SHDL.WK_NO_OF_YEAR, TRN_WKLY_PROD_SHDL.PROD_DAY_NO, ")
			.append(" TRN_WKLY_PROD_SHDL.CRTD_BY, TRN_WKLY_PROD_SHDL.CRTD_DT, TRN_WKLY_PROD_SHDL.UPDTD_BY, TRN_WKLY_PROD_SHDL.UPDTD_DT ")
			.append(" FROM   TRN_WKLY_PROD_SHDL, MST_POR, MST_BUYER, MST_BUYER_GRP WHERE ")
			.append(" TRN_WKLY_PROD_SHDL.POR_CD = MST_POR.POR_CD AND  MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD AND ")
			.append(" TRN_WKLY_PROD_SHDL.BUYER_CD = MST_BUYER.BUYER_CD AND MST_BUYER_GRP.BUYER_GRP_CD = MST_BUYER.BUYER_GRP_CD AND ");

	/**
	 * add  whereClause conditions
	 */
	public static final StringBuilder baseQueryCondition2 = new StringBuilder()
			.append("" + IFConstants.param_porCd + "")
			.append("" + IFConstants.param_ocfRegionCd + "")
			.append("" + IFConstants.param_ocfBuyerGrpCd + "")
			.append("" + IFConstants.param_rhqCd + "")
			.append("" + IFConstants.param_buyer_buyerGrpCD + "");
    /**
     * P0001 Inserting weekly schedule interface file detail data into common file header mst 
     */
	public static final StringBuilder insertCmnHeader = new StringBuilder()
			.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,REC_COUNT,TRN_TYPE,STTS,REMARKS)")
			.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:REC_COUNT,:TRN_TYPE,:STTS,:REMARKS)");

	/**
	 * P0005 update the common file header  
	 */
	public static final StringBuilder updateCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/**
	 * P0005 update the common file header  status
	 */
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/* P0004 Insert the data into CMN_INTERFACE_DATA table */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
			.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19,COL20,COL21,COL22,COL23,COL24,COL25,COL26,COL27,COL28,COL29,COL30,COL31,COL32,COL33,COL34,COL35 )")
			.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16, :COL17, :COL18, :COL19, :COL20, :COL21, :COL22, :COL23, :COL24, :COL25, :COL26, :COL27, :COL28, :COL29, :COL30, :COL31, :COL32, :COL33, :COL34, :COL35 )");

	/**
	 * Instantiates a new I000088QueryConstants
	 */
	private I000088QueryConstants() {
		super();
	}
}