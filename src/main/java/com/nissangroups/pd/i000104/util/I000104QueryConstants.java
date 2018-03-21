/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000104
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Send the OSEI Spec Master Data to SAP 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-01-2016  	  z014029(RNTBCI)               New Creation
 */
package com.nissangroups.pd.i000104.util;

/**
 * The Class I000104QueryConstants.
 * 
 * @author z014029
 */

public class I000104QueryConstants {

	/**
	 * Instantiates a new I000104QueryConstants
	 */
	private I000104QueryConstants() {
		super();
	}

	/**
	 * P0002 Extracting the OSEI Spec Master data for different tables, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case.
	 */
	public static final StringBuilder baseQuery = new StringBuilder()
			.append("SELECT ROWNUM, a.* FROM ( ")
			.append("SELECT 'ZVMS_ATRIM' AS Col1, '0' AS Col2, APPLD_MDL_CD || PCK_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 ")
			.append("FROM MST_OEI_SPEC ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_OEI_SPEC') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ATRIMT_PDD' AS Col1,'0' AS Col2,'D' AS Col3, OEISMST.POR_CD AS Col4, OEISMST.PROD_STAGE_CD AS Col5, OEISMST.CAR_SRS AS Col6, OEIBMST.BUYER_CD AS Col7, ")
			.append("OEISMST.APPLD_MDL_CD || OEISMST.PCK_CD AS Col8, OEISMST.SPEC_DESTN_CD AS Col9, OSEIMST.EXT_CLR_CD AS Col10, OSEIMST.INT_CLR_CD AS Col11, OEISMST.ADTNL_SPEC_CD AS Col12, ")
			.append("OSEIDMST.OSEI_ADPT_DATE AS Col13, OSEIDMST.OSEI_SUSPENDED_DATE AS Col14, OEISMST.PROD_FMY_CD AS Col15, OSEIDMST.LCL_NOTE AS Col16, OSEIDMST.PCKGE_NAME AS Col17, OSEIDMST.MDL_YEAR AS Col18 ")
			.append("FROM MST_OEI_SPEC OEISMST INNER JOIN MST_OEI_BUYER OEIBMST ON OEISMST.OEI_SPEC_ID = OEIBMST.OEI_SPEC_ID INNER JOIN MST_OSEI OSEIMST ON OEIBMST.OEI_BUYER_ID  = OSEIMST.OEI_BUYER_ID ")
			.append("INNER JOIN (SELECT * FROM MST_OSEI_DTL WHERE END_ITM_STTS_CD='30' and UPDTD_DT >= (SELECT max( PROCESS_EXECUTED_TIME) FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_OSEI_DTL')) OSEIDMST ON OSEIDMST.OSEI_ID = OSEIMST.OSEI_ID ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACSRS' AS Col1, '0' AS Col2, POR_CD AS Col3, PROD_FMY_CD AS Col4, CAR_SRS AS Col5, CAR_GRP AS Col6, BRND_CD AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_POR_CAR_SRS ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_POR_CAR_SRS') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACSRST' AS Col1, '0' AS Col2, POR_CD AS Col3, PROD_FMY_CD AS Col4, CAR_SRS AS Col5, 'EN' AS Col6, CAR_SRS_DESC AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_POR_CAR_SRS ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_POR_CAR_SRS') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_APFAM' AS Col1, '0' AS Col2, PROD_FMY_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_POR_CAR_SRS ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_POR_CAR_SRS') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACSG' AS Col1, '0' AS Col2, CAR_GRP AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_POR_CAR_SRS ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_POR_CAR_SRS') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ABRAND' AS Col1, '0' AS Col2, BRND_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_BRND ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_BRND') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ABRANDT' AS Col1, '0' AS Col2, BRND_CD AS Col3, 'EN' AS Col4, BRND_DESC AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_BRND ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_BRND') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACOLOR_EX' AS Col1, '0' AS Col2, EXT_CLR_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_EXT_CLR ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_EXT_CLR') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACOLOR_IN' AS Col1, '0' AS Col2, INT_CLR_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_INT_CLR ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_INT_CLR') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACOLOR_EXT' AS Col1, '0' AS Col2, PROD_FMY_CD AS Col3, EXT_CLR_CD AS Col4, 'EN' AS Col5, EXT_CLR_DESC AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_EXT_CLR ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_EXT_CLR') ")
			.append("UNION ALL ")
			.append("SELECT 'ZVMS_ACOLOR_INT' AS Col1, '0' AS Col2, INT_CLR_CD AS Col3, 'EN' AS Col4, INT_CLR_DESC AS Col5, '' AS Col6, '' AS Col7, '' AS Col8, '' AS Col9, '' AS Col10, '' AS Col11, '' AS Col12, '' AS Col13, '' AS Col14, '' AS Col15, '' AS Col16, '' AS Col17, '' AS Col18 FROM MST_INT_CLR ")
			.append("WHERE UPDTD_DT >= (SELECT PROCESS_EXECUTED_TIME FROM SPEC_REEXECUTE_STATUS WHERE table_name ='MST_INT_CLR'))  a");
}