/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send Monthly OCF Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000027
 * 
 * @author z014029
 */
public class I000027QueryConstants {

	/**
	 * Instantiates a new I000027 query constants.
	 */
	private I000027QueryConstants() {
		super();
	}

	/**
	 * P0001 INSERTING MONTHLY SCHEDULE INTERFACE FILE DETAIL DATA INTO COMMON
	 * FILE HEADER MST
	 */
	public static final StringBuilder updateStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS ,REC_COUNT =:REC_COUNT, REMARKS =:REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	/** P0002 Extract the ORDER TAKE BASE MONTH based on the POR CD */
	public static final StringBuilder extractOrdrTkBsMnth = new StringBuilder()
			.append("SELECT MAX(MST_MNTH_ORDR_TAKE_BASE_PD.ORDR_TAKE_BASE_MNTH) FROM MST_MNTH_ORDR_TAKE_BASE_PD ")
			.append("WHERE MST_MNTH_ORDR_TAKE_BASE_PD.POR_CD IN (:porCd) AND ")
			.append("MST_MNTH_ORDR_TAKE_BASE_PD.STAGE_CD IN (:stageCd) ");

	/** P0003 Extract the Monthly OCF data for base query condition1 */
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append(" SELECT DISTINCT TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD,TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS,TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD, ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD,MST_FEAT.FEAT_SHRT_DESC,MST_FEAT.OCF_BUYER_GRP_CD,MST_FEAT.CAR_GRP,TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD, ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH,TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH,TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD, ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY,TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY,TRN_BUYER_GRP_MNTHLY_OCF_LMT.CRTD_BY, ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT.CRTD_DT,TRN_BUYER_GRP_MNTHLY_OCF_LMT.UPDTD_BY,TRN_BUYER_GRP_MNTHLY_OCF_LMT.UPDTD_DT ")
			.append(" FROM  MST_POR INNER JOIN TRN_BUYER_GRP_MNTHLY_OCF_LMT ON MST_POR.POR_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
			.append(" INNER JOIN MST_BUYER_GRP ON MST_BUYER_GRP.BUYER_GRP_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD ")
			.append(" INNER JOIN MST_BUYER ON MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD AND  MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD ")
			.append(" INNER JOIN MST_FEAT ON MST_FEAT.POR_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD  AND MST_FEAT.CAR_SRS = TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS ")
			.append(" AND MST_FEAT.FEAT_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD AND  MST_FEAT.FEAT_TYPE_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD ")
			.append(" AND MST_FEAT.OCF_FRME_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD AND MST_FEAT.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD WHERE ");
				
	
	/** P0003 Extract the Monthly OCF data for base query condition2 */
	public static final StringBuilder baseQueryCondition2 = new StringBuilder()
			.append("" + IFConstants.param_OCF_ordrTkBsMnth + "")
			.append("" + IFConstants.param_porCd + "")
			.append("" + IFConstants.param_ocfRegionCd + "")
			.append("" + IFConstants.param_ocfBuyerGrpCd + "")
			.append("" + IFConstants.param_rhqCd + "")
			.append("" + IFConstants.param_buyer_buyerGrpCD + "");

	/** P0003 Fetching the parameter Key and value */
	public static final StringBuilder byrGrpOcfLmtChk = new StringBuilder()
			.append("SELECT MST_PRMTR.VAL1 FROM MST_PRMTR WHERE MST_PRMTR.PRMTR_CD = '' AND MST_PRMTR.KEY1= '' ");

	/** P0004 Insert the data into CMN_INTERFACE_DATA table */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
			.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19,COL20,COL21)")
			.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16, :COL17, :COL18, :COL19, :COL20, :COL21)");

	/** P0005 Update the Message Status into Common File Header */
	public static final StringBuilder insertCmnHeader = new StringBuilder()
			.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,REC_COUNT,TRN_TYPE,STTS,REMARKS)")
			.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:REC_COUNT,:TRN_TYPE,:STTS,:REMARKS)");

	/** P0005 Update the Message Status into Common File Header */
	public static final StringBuilder updateCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");

	/** P0005 Update the Message Status into Common File Header */
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO");
}