/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000043
 * Module          :Ordering		
 * Process Outline :Create_Weekly_Order_Take_Base_Period																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000043.util;

public class B000043QueryConstants {

	/** Constant DeleteQuery. */
	public static final StringBuilder getJobSchdlStrmData = new StringBuilder()
			.append(" SELECT MIN(Concat(ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO)) from TRN_JOBSTRM_SHDL where JOBSTRM_SEQ_ID in (select JOBSTRM_SEQ_ID from MST_JOB_STERAM")
			.append("  where POR = :porCd ) and ST_DT is not null and END_DT is not null and Concat(ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO) > Concat(:ordrTkBsMnth,:ordrTkBsWkNo) ");

private B000043QueryConstants(){
	
}
}
