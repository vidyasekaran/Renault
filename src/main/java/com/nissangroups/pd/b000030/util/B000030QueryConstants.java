/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000030
 * Module          : Ordering					
 * Process Outline :Create Monthly Order Take Base Period
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000030.util;

/**
 * Constant file to keep the queries related to the batch B000008.
 * 
 * @author z015060
 *
 */
public class B000030QueryConstants {

	/** query Constant extractOrdrFrErr . */
	public static final StringBuilder extractOrdrFrErr =  new StringBuilder("select ORDR_TAKE_BASE_MNTH,STAGE_CD ")
	.append(" from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD= :porCd and STAGE_STTS_CD='O' ");
	
	
	/** query Constant extrxctOrdrTkBsMnthQry . */
	public static final StringBuilder extrxctOrdrTkBsMnthQry = new StringBuilder("select MAX(ORDR_TAKE_BASE_MNTH) as ordrTkBsMnth ")
	.append(" from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD= :porCd  and (STAGE_CD='F1' or STAGE_CD='F2' or STAGE_CD='SC') ");

	
	
	/** query Constant extrxctOrdrSttsQry . */
	public static final StringBuilder extrxctOrdrSttsQry =  new StringBuilder("select ORDR_TAKE_BASE_MNTH,STAGE_CD,  ")
	.append("STAGE_STTS_CD  from MST_MNTH_ORDR_TAKE_BASE_PD where ORDR_TAKE_BASE_MNTH= ( ")
	.append(" select MAX(ORDR_TAKE_BASE_MNTH)  from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD= :porCd ) and POR_CD= :porCd  ");

	private B000030QueryConstants() {

	}
	
}
