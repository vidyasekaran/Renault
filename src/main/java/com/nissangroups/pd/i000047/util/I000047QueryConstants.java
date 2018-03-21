/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000047
 * Module          :SP SPEC MASTER
 *  
 * Process Outline :Receive Week No Calendar Interface from Plant. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-01-2016  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000047.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * The Class I000047QueryConstants.
 * 
 * @author z014029
 */
public class I000047QueryConstants {

	/**
	 * Instantiates a new I000047QueryConstants
	 */
	private I000047QueryConstants() {
		super();
	}

	/**
	 * P0001 - Extracting the Maximum Sequence Number with Unprocessed Status
	 * from Common File Header
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
	 * P0001 - Extract the details of the Interface file with the given File Id
	 * which has maximum Sequence No.
	 */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM,CLD.IF_FILE_ID,CFH.SEQ_NO,CLD.ROW_NO, CLD.Col1 ,CLD.Col2,CLD.Col3,CLD.Col4,CLD.Col5,CLD.Col6,CLD.Col7,CLD.Col8,")
			.append("CLD.Col9,CLD.Col10,CLD.Col11,CLD.Col12,CLD.Col13,CLD.Col14,CLD.Col15,CLD.Col16,CLD.Col17,CLD.Col18,CLD.Col19,")
			.append("CLD.Col20,CLD.Col21,CLD.Col22,CLD.Col23 FROM ")
			.append("CMN_FILE_HDR CFH INNER JOIN CMN_INTERFACE_DATA CLD ON CFH.SEQ_NO = CLD.SEQ_NO AND CFH.IF_FILE_ID = CLD.IF_FILE_ID WHERE ");

	/** P0001 - Check the POR Code is exist in the POR_MST table. */
	public static final StringBuilder porCdChk = new StringBuilder()
			.append("SELECT POR_CD FROM MST_POR WHERE TRIM(POR_CD) = :porCd");

	public static final StringBuilder getMaxSeqNoFromHdr = new StringBuilder()
			.append("SELECT MAX(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = "
					+ IFConstants.param_IfFileID
					+ " AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "')");

	/**
	 * P0002.1 - Delete the existing data from the week no calendar mst against
	 * the given input POR CD.
	 */
	public static final StringBuilder deleteWKNoClndrMst = new StringBuilder()
				.append("DELETE FROM MST_WK_NO_CLNDR WHERE POR_CD = :porCd ");
			//.append("DELETE FROM MST_WK_NO_CLNDR WHERE POR_CD = ':porCd' ");

	/**
	 * P0002.2 Insert the extracted data into week no calendar mst against the
	 * given input POR CD.
	 */
	public static final StringBuilder insertMstWkNoClndr = new StringBuilder()
			.append("INSERT INTO MST_WK_NO_CLNDR (POR_CD,PROD_MNTH,PROD_WK_NO,WK_NO_YEAR,WK_STRT_DATE,WK_END_DATE,NON_OPRTNL_FLAG,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) ")
			.append("VALUES(:porCd,:prodMnth,:prodWkNo,:wkNoYr,:wkStrtDt,:wkEndDt,:nonOprtnlFlg,:crtdBy,SYSDATE,:updtdBy,SYSDATE)");

}