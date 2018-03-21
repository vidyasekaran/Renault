/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015895(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000113
 * 
 * @author z015895
 */
public class I000113QueryConstants {

	/**
	 * Instantiates a new I000113 query constants.
	 */
	private I000113QueryConstants() {
		super();
	}

	/**
	 * Add where condition to Extract interface file id and sequence number data
	 */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			// .append("CFH.IF_FILE_ID = '"+IFConstants.param_IfFileID+"' AND CFH.STTS IN ('"+IFConstants.hdrStatus+"') AND CLD.IF_FILE_ID = '"+IFConstants.param_IfFileID+"' AND ")
			.append("CLD.IF_FILE_ID = '" + IFConstants.param_IfFileID
					+ "' AND ")
			.append("CLD.SEQ_NO = (SELECT MIN(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = '"
					+ IFConstants.param_IfFileID
					+ "' AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "'))  AND ")
			.append("CLD.Col2 IN (" + IFConstants.porCd_Param + ")");

	/**
	 * P0001 Extract Common layer data form common file header and cmn interface
	 * data tables
	 */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM, CLD.IF_FILE_ID,CFH.SEQ_NO,CLD.Col1 ,CLD.Col2,CLD.Col3,CLD.Col4,CLD.Col5,CLD.Col6,CLD.Col7,CLD.Col8,")
			.append("CLD.Col9,CLD.Col10,CLD.Col11,CLD.Col12,CLD.Col13,CLD.Col14,CLD.Col15,CLD.Col16,CLD.Col17,CLD.Col18,CLD.Col19,")
			.append("CLD.Col20,CLD.Col21,CLD.Col22,CLD.Col23,CLD.Col24,CLD.Col25 FROM ")
			.append("CMN_FILE_HDR CFH INNER JOIN CMN_INTERFACE_DATA CLD ON CFH.SEQ_NO = CLD.SEQ_NO AND CFH.IF_FILE_ID = CLD.IF_FILE_ID WHERE ");

	/** P0001 Extract OSEI ID from MST_OSEI table based on input parameters */
	public static final StringBuilder oseiIdQuery = new StringBuilder()
			.append("SELECT OSEI_ID FROM MST_OSEI WHERE EXT_CLR_CD = :"
					+ IFConstants.osei_extClrCD + " AND INT_CLR_CD = :"
					+ IFConstants.osei_intClrCD + " ")
			.append("AND OEI_BUYER_ID =(SELECT OEI_BUYER_ID FROM MST_OEI_BUYER WHERE BUYER_CD = :"
					+ IFConstants.oeiBuyer_buyerCD + " ")
			.append("AND OEI_SPEC_ID =(SELECT OEI_SPEC_ID FROM MST_OEI_SPEC WHERE SPEC_DESTN_CD = :"
					+ IFConstants.oeiSpec_specDestnCD + " ")
			.append("AND CAR_SRS= :" + IFConstants.oeiSpec_carSrs
					+ " AND ADTNL_SPEC_CD = :"
					+ IFConstants.oeiSpec_adtnlSpecCD + " AND PCK_CD= :"
					+ IFConstants.oeiSpec_pckCD + " ")
			.append("AND PROD_FMY_CD = :" + IFConstants.oeiSpec_prodFamilyCD
					+ " AND APPLD_MDL_CD = :" + IFConstants.oeiSpec_appldMdlCD
					+ " ))");

	/**
	 * P001 Extract the minimum seq number from cmn file hdr based on conditions
	 */
	public static final StringBuilder getMinSeqNoFromHdr = new StringBuilder()
			.append("SELECT MIN(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = "
					+ IFConstants.param_IfFileID
					+ " AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "')");

	/** P001 Extract the vehicle sequence id from TRN_LGCL_PPLN */
	public static final StringBuilder getLgclPplnTrnById = new StringBuilder()
			.append("SELECT LP FROM TrnLgclPpln LP WHERE LP.vhclSeqId = :vhclSeqId");

	/** P002 insert the data into TRN_LGCL_PPLN table */
	public static final StringBuilder insertLgclPplTrn = new StringBuilder()
			.append("INSERT INTO TRN_LGCL_PPLN (VHCL_SEQ_ID,POR_CD,PROD_PLNT_CD,OFFLN_PLAN_DATE,LGCL_PPLN_STAGE_CD,SLS_NOTE_NO,EX_NO,PROD_MNTH,POT_CD,PROD_ORDR_NO,ORDR_DEL_FLAG,MS_FXD_FLAG,LINE_CLASS,FRZN_TYPE_CD,PROD_MTHD_CD,VIN_NO,OSEI_ID,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT)")
			.append(" values (:vhclSeqId,:porCd,:prodPlntCd,:offlnPlanDate,:lgclPplnStageCd,:slsNoteNo,:exNo,:prodMnth,:potCd,:prodOrdrNo,:ordrDelFlag,:msFxdFlag,:lineClass,:frznTypeCd,:prodMthdCd,:vinNo,:oseiId,:crtdBy,SYSDATE,:updtdBy,SYSDATE)");

	/** P002 update the data into TRN_LGCL_PPLN table based on conditions */
	public static final StringBuilder updateLgclPplTrn = new StringBuilder()
			.append("UPDATE TRN_LGCL_PPLN SET PROD_PLNT_CD = :prodPlntCd ,OFFLN_PLAN_DATE = :offlnPlanDate,LGCL_PPLN_STAGE_CD = :lgclPplnStageCd,SLS_NOTE_NO = :slsNoteNo,EX_NO = :exNo,PROD_MNTH = :prodMnth,POT_CD = :potCd,PROD_ORDR_NO = :prodOrdrNo,ORDR_DEL_FLAG = :ordrDelFlag,MS_FXD_FLAG = :msFxdFlag,LINE_CLASS = :lineClass,FRZN_TYPE_CD = :frznTypeCd,PROD_MTHD_CD = :prodMthdCd,VIN_NO = :vinNo,OSEI_ID = :oseiId,UPDTD_BY = :updtdBy,UPDTD_DT = SYSDATE WHERE VHCL_SEQ_ID = :vhclSeqId");

	/** P002 update the VIN ALLCT FLAG and LGCL PPLN SEQ ID based on condition */
	public static final StringBuilder updateFlgInPhysclPplTrn = new StringBuilder()
			.append("UPDATE TRN_PHYSCL_PPLN SET VIN_ALLCT_FLAG = '1' WHERE VIN_ALLCT_FLAG = '0' AND LGCL_PPLN_SEQ_ID = ':vhclSeqId'");

}
