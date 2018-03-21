/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : I000062
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the 
 * extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000062.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000062
 * 
 * @author z015895
 *
 */
public class I000062QueryConstants {
	/**
	 * Instantiates a new I000062 query constants.
	 */
	private I000062QueryConstants() {
		super();
	}

	/** P00001 - I000062 Constant Extract condition */
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append("CLD.IF_FILE_ID = '" + IFConstants.param_IfFileID
					+ "' AND ")
			.append("CLD.SEQ_NO = (SELECT MIN(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = '"
					+ IFConstants.param_IfFileID
					+ "' AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "'))");
	// .append("CLD.Col1 IN ("+IFConstants.porCd_Param+")");

	/** P00001 - I000062 Constant Extract condition */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM,CLD.IF_FILE_ID,CFH.SEQ_NO,CLD.ROW_NO, CLD.Col1 ,CLD.Col2,CLD.Col3,CLD.Col4,CLD.Col5,CLD.Col6,CLD.Col7,CLD.Col8,")
			.append("CLD.Col9,CLD.Col10,CLD.Col11,CLD.Col12,CLD.Col13,CLD.Col14,CLD.Col15,CLD.Col16,CLD.Col17,CLD.Col18,CLD.Col19,")
			.append("CLD.Col20,CLD.Col21,CLD.Col22,CLD.Col23,CLD.Col24,CLD.Col25,CLD.Col26,CLD.Col27 FROM ")
			.append("CMN_FILE_HDR CFH INNER JOIN CMN_INTERFACE_DATA CLD ON CFH.SEQ_NO = CLD.SEQ_NO AND CFH.IF_FILE_ID = CLD.IF_FILE_ID WHERE ");

	/** P00003 - I000062 Constant Extract condition */
	public static final StringBuilder getMinSeqNoFromHdr = new StringBuilder()
			.append("SELECT MIN(CFH.SEQ_NO) FROM CMN_FILE_HDR CFH WHERE CFH.IF_FILE_ID = "
					+ IFConstants.param_IfFileID
					+ " AND CFH.STTS IN ('"
					+ IFConstants.hdrStatus + "')");

	/** P00001 - I000062 Constant Extract condition */
	public static final StringBuilder deleteWklyOrdrIfTrn = new StringBuilder()
			.append("DELETE FROM TRN_WKLY_ORDR_IF WK WHERE WK.FILE_ID = :ifFileId AND WK.FILE_SEQ = :seqNumber ");

}
