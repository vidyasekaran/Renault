/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000105
 * Module          :CM Common
 * Process Outline :Interface_Send Organization Master Interface to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000105.util;

public class I000105QueryConstants {

	/**
	 * Instantiates a new I000105 query constants.
	 */
	private I000105QueryConstants() {
		super();
	}

	public static final StringBuilder baseQuery = new StringBuilder()
			.append("Select 'ZVMS_APOR' AS Col1,POR_CD AS Col2,PROD_REGION_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_POR ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_APORT' AS Col1, POR_CD AS Col2,'EN' AS Col3, POR_DESC AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_POR ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_APREGIO' AS Col1, PROD_REGION_CD AS Col2, '' AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_PROD_REGION_CODE ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_APREGIOT' AS Col1, PROD_REGION_CD AS Col2, 'EN' AS Col3, PROD_REGION_DESC AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_PROD_REGION_CODE ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_AFPLANT'  AS Col1,PLNT_CD AS Col2, POR_CD  AS Col3, PROD_PLNT_CD  AS Col4, ''  AS Col5, ''  AS Col6, ''  AS Col7, ''  AS Col8 from MST_PLNT_CD ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_AFPLANTT'  AS Col1, PLNT_CD  AS Col2, 'EN'  AS Col3, PLNT_DESC  AS Col4, ''  AS Col5, ''  AS Col6, ''  AS Col7, ''  AS Col8 from MST_PLNT_CD ")
			.append("UNION ALL ")
			.append("Select distinct 'ZVMS_AFPLCAL' AS Col1, '0'  AS Col2, SUBSTR(PROD_MNTH,0,4) AS Col3, SUBSTR(PROD_MNTH,5,6)  AS Col4, PROD_WK_NO  AS Col5, WK_NO_YEAR  AS Col6, WK_STRT_DATE  AS Col7, WK_END_DATE  AS Col8 ")
			.append("from MST_WK_NO_CLNDR where POR_CD in (select POR_CD from MST_WK_NO_CLNDR where ROWNUM=1 ) AND UPDTD_DT >= (select PROCESS_EXECUTED_TIME from SPEC_REEXECUTE_STATUS where table_name ='MST_WK_NO_CLNDR') ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_AMCREGIO' AS  Col1, MC_REGION_CD AS  Col2, '' AS  Col3, '' AS  Col4, '' AS  Col5, '' AS  Col6, '' AS  Col7, '' AS  Col8   from MST_MC_REGION ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_AMCREGIOT' AS  Col1, MC_REGION_CD AS  Col2, 'EN' AS  Col3, MC_REGION_DESC AS  Col4, '' AS  Col5, '' AS  Col6, '' AS  Col7, '' AS  Col8   from MST_MC_REGION ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_AMCSUBREG' AS Col1, MC_REGION_CD AS Col2, SUB_REGION_CD AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_MC_SUB_REGION ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_ARHQ' AS Col1, RHQ_CD AS Col2, '' AS Col3, '' AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_REGIONAL_HEAD_QRTR ")
			.append("UNION ALL ")
			.append("Select 'ZVMS_ARHQT' AS Col1, RHQ_CD AS Col2, 'EN' AS Col3, RHQ_DESC AS Col4, '' AS Col5, '' AS Col6, '' AS Col7, '' AS Col8 from MST_REGIONAL_HEAD_QRTR");

}
