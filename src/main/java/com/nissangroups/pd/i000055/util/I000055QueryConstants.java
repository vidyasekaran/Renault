/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000055
 * 
 * @author z015895
 */
public class I000055QueryConstants {

	/**
	 * Instantiates a new I000055 query constants.
	 */
	private I000055QueryConstants() {
		super();
	}

	/**
	 * P0001 Add where condition to Extract interface file id and sequence
	 * number data
	 */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append("CLD.IF_FILE_ID = '" + IFConstants.param_IfFileID
					+ "' AND ")
			.append("CLD.SEQ_NO = (SELECT MAX(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = '"
					+ IFConstants.param_IfFileID
					+ "' AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "'))");
	// .append("CLD.Col1 IN ("+IFConstants.porCd_Param+")");

	/**
	 * P0001 Extract Common layer data form common file header and cmn interface
	 * data tables
	 */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM,CLD.IF_FILE_ID,CFH.SEQ_NO,CLD.ROW_NO, CLD.Col1 ,CLD.Col2,CLD.Col3,CLD.Col4,CLD.Col5,CLD.Col6,CLD.Col7,CLD.Col8,")
			.append("CLD.Col9,CLD.Col10,CLD.Col11,CLD.Col12,CLD.Col13,CLD.Col14,CLD.Col15,CLD.Col16,CLD.Col17,CLD.Col18,CLD.Col19 FROM ")
			.append("CMN_FILE_HDR CFH INNER JOIN CMN_INTERFACE_DATA CLD ON CFH.SEQ_NO = CLD.SEQ_NO AND CFH.IF_FILE_ID = CLD.IF_FILE_ID WHERE ");

	/**
	 * P0001 Extract the Maximum seq number from cmn file hdr based on
	 * conditions
	 */
	public static final StringBuilder getMaxSeqNoFromHdr = new StringBuilder()
			.append("SELECT MAX(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = "
					+ IFConstants.param_IfFileID
					+ " AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "')");

	/**
	 * P0002 Delete all the records from the DAILY_OCF_LIMIT_IF_TRN table based
	 * on the given POR Code
	 */
	public static final StringBuilder deleteDailyOcfLmtIfTrn = new StringBuilder()
			.append("DELETE FROM TMP_DAILY_OCF_LMT_IF WHERE POR_CD = :porCd ");

	/**
	 * P0005.2 Query to extract data from MST_POR_CAR_SRS table based on
	 * conditions
	 */
	public static final StringBuilder carGroupQuery = new StringBuilder()
			.append("SELECT CAR_GRP, CAR_SRS_ADPT_DATE, CAR_SRS_ABLSH_DATE FROM MST_POR_CAR_SRS WHERE POR_CD=:porCd AND CAR_SRS=:carSrs");

	/** P0005.4 Query to extract data from MST_FEAT table. */
	public static final StringBuilder featureDtlsQuery = new StringBuilder()
			.append("SELECT FEAT_CD, FEAT_ADPT_DATE, FEAT_ABLSH_DATE,FEAT_TYPE_CD FROM MST_FEAT WHERE POR_CD=:porCd AND CAR_SRS=:carSrs AND OCF_REGION_CD =:ocfRgnCd "
					+ "AND OCF_BUYER_GRP_CD=:byrGrpCd AND OCF_FRME_CD=:ocfFrameCD AND FEAT_SHRT_DESC=:shrtDesc AND FEAT_TYPE_CD not in ('10','20','40','50','70','80')");
	/**
	 * P0003 Insert the extracted list of data by iteration which fetched from
	 * the common pool
	 */
	public static final StringBuilder insertDailyOcfLmtIfTrn = new StringBuilder()
			.append("INSERT INTO TRN_DAILY_OCF_LMT (POR_CD,PROD_MNTH,CAR_SRS,OCF_REGION_CD,OCF_BUYER_GRP_CD,PROD_PLNT_CD,LINE_CLASS,OCF_FRME_SRT_CD,OCF_MDL_GRP,OCF_IDNTCTN_CODE,PROD_DAY_NO,OCF_FRME_CD,OCF_DATE,FEAT_CD,OCF_LMT_QTY,STND_QTY,PROD_WK_NO,ORDR_TAKE_BASE_MNTH,ORDR_BASE_BASE_WK_NO,LAST_WK_SYMBL,PROCESS_STTS_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) ")
			.append("VALUES(:porCd,:prodMnth,:carSrs,:ocfRegionCd,:ocfBuyerGrpCd,:prodPlntCd,:lineClass,:ocfFrmeSrtCd,:ocfMdlGrp,:ocfIdntctnCd,:prodDayNo,:ocfFrmeCd,:ocfDate,:featCd,:ocfLmtQty,:stdQty,:prodWkNo,:ordrTakBasMnth,:ordrTakBasWkNo,:lastWkSymbl,:processSttsCd,:crtdBy,SYSDATE,:updtdBy,SYSDATE)");

	/**
	 * P0005.5 Delete all the records from the DAILY_OCF_LIMIT_TRN table based
	 * on the given POR Code & Production Month
	 */
	public static final StringBuilder deleteDailyOcfLmtTrn = new StringBuilder()
			.append("DELETE FROM TRN_DAILY_OCF_LMT WHERE POR_CD = :porCd AND PROD_MNTH = :prodMnth ");

}
