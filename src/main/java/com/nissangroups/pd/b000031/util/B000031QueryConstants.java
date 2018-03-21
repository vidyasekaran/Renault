/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000031.util;

/**
* Query Constants for B000031
*
* @author z015399
*/

public class B000031QueryConstants {
	
	// Process 1.1
	public static final StringBuilder EXT_ORDR_TAKE_BASE_MNTH_MAIN_PART = new StringBuilder()
	.append("SELECT POR,MAX(ORDR_TAKE_BASE_MNTH) AS ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO from MST_WKLY_ORDR_TAKE_BASE where POR = :porCd ");
	
	public static final StringBuilder NOT_EQUAL_SC_CNDN = new StringBuilder()
	.append(" and STAGE_CD <> 'SC' GROUP BY POR,ORDR_TAKE_BASE_WK_NO");
	
	public static final StringBuilder EQUAL_SC_CNDN = new StringBuilder()
	.append(" and STAGE_CD = 'SC' GROUP BY POR,ORDR_TAKE_BASE_WK_NO");
	
	// Process 1.2 - PLANT_LINE_SUMMARY extraction
	public static final StringBuilder EXT_PLANT_LINE_SUMMARY = new StringBuilder()
	.append("SELECT VAL2 AS PLANT_LINE_SUMMARY from MST_PRMTR where KEY1 = :porCd  " +
			 " and KEY2 = 'PLT LN SUMRY' and PRMTR_CD = 'WKLY OCF SUMRY'");
	
	// Process 1.2 - PLANT CD and LINE CLASS extraction
	
	public static final StringBuilder EXT_PLANT_CD_LINE_CLASS = new StringBuilder()
	.append("SELECT VAL1 as PLANT_CD , VAL2 as LINE_CLASS from MST_PRMTR where KEY1 = :porCd " +
			" and KEY2 = 'CNST_PLT_LN_CLS' and PRMTR_CD = 'WKLY OCF SUMRY' ");
	
	
	public static final StringBuilder EXT_CNST_DAY_NO = new StringBuilder()
	.append("SELECT VAL2 as CNST_DAY_NO from MST_PRMTR where KEY1 = '09' "
			+ " and KEY2 = 'CNST_DAY_NO' and PRMTR_CD = 'WKLY OCF SUMRY' ");
	
	
	// Process 2.1 - Extract OCF Limit & Summarize OCF Limit
	
	public static final StringBuilder EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_MAIN_PART = new StringBuilder()
	.append("SELECT DISTINCT SUM(OCF_LMT_QTY), PROD_MNTH , PROD_WK_NO , " +
	" CAR_SRS ,OCF_REGION_CD , "+
	" OCF_BUYER_GRP_CD ,FEAT_CD ,OCF_FRME_CD " );
	
	public static final StringBuilder  EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_YES_MAIN_PART = new StringBuilder()
	.append(" ,LINE_CLASS , PROD_PLNT_CD , PROD_DAY_NO ");
	
	public static final StringBuilder  EXT_WKLY_OCF_LIMIT_PLT_LN_SUM_NO_MAIN_PART = new StringBuilder()
	.append(" ,LINE_CLASS , PROD_PLNT_CD , PROD_DAY_NO ");
	
	public static final StringBuilder EXT_WKLY_OCF_LIMIT_PLT_CMN_CND = new StringBuilder()
	.append(
	" from TRN_DAILY_OCF_LMT "+
	" where "+
	" CONCAT(PROD_MNTH,PROD_WK_NO) >= :otbmPlusOtbw "+
	" and POR_CD = :porCd " +
	" and OCF_REGION_CD = :ocfRegionCd ");
	
	public static final StringBuilder FRM_CD_00_CND = new StringBuilder()
	.append(" and OCF_FRME_CD = '00' ");
	
	public static final StringBuilder EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_YES_PART = new StringBuilder()
	.append(
	" and PROD_PLNT_CD = :prdPlntCd "+
	" and LINE_CLASS = :lnClass " +
	" and PROD_DAY_NO = :prdDayNo "
	);
	
	public static final StringBuilder EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_YES_GRP_BY_PART = new StringBuilder()
	.append(
	" GROUP BY "+
	" PROD_MNTH , PROD_WK_NO , CAR_SRS ,OCF_REGION_CD , "+
	" OCF_BUYER_GRP_CD ,FEAT_CD ,OCF_FRME_CD ,LINE_CLASS ,PROD_PLNT_CD ,PROD_DAY_NO ");
	
	public static final StringBuilder EXT_WKLY_OCF_LIMIT_PLT_CND_LN_SUM_NO_GRP_BY_PART = new StringBuilder()
	.append(
	" GROUP BY "+
	" PROD_MNTH , PROD_WK_NO , CAR_SRS ,OCF_REGION_CD , "+
	" OCF_BUYER_GRP_CD ,FEAT_CD ,OCF_FRME_CD ,LINE_CLASS ,PROD_PLNT_CD ,PROD_DAY_NO ");
	
	
	// Process 4.1
	
	public static final StringBuilder EXT_BYR_GRP_CD = new StringBuilder()
	.append(
	" SELECT DISTINCT MST_BUYER.BUYER_GRP_CD , "+
	" MST_OCF_REGION.OCF_REGION_CD, "+
	" MST_OCF_REGION.OCF_BUYER_GRP_CD, "+
	" MST_OCF_REGION.OCF_AUTO_ALLCTN_FLAG , COUNT(MST_BUYER.BUYER_GRP_CD) "+
	" from MST_BUYER "+
	" INNER JOIN MST_OCF_REGION on MST_OCF_REGION.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD "+
	" AND MST_OCF_REGION.OCF_BUYER_GRP_CD = MST_BUYER.OCF_BUYER_GRP_CD "+
	" INNER JOIN MST_POR on MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD AND MST_POR.PROD_REGION_CD = MST_OCF_REGION.PROD_REGION_CD "+
	" where mst_por.POR_CD = :porCd and MST_OCF_REGION.OCF_BUYER_GRP_CD = :ocfByrGrpCd and MST_OCF_REGION.OCF_REGION_CD = :ocfRegionCd"+
	" GROUP BY MST_BUYER.BUYER_GRP_CD ,  MST_OCF_REGION.OCF_REGION_CD,  MST_OCF_REGION.OCF_BUYER_GRP_CD,  "+
	" MST_OCF_REGION.OCF_AUTO_ALLCTN_FLAG"
	);
	
	
	//Process 6.1
	
	public static final StringBuilder EXT_RGN_WKLY_OCF_LMT = new StringBuilder()
	.append(
	" SELECT REGIONAL_OCF_LMT_QTY , PROD_MNTH , CAR_SRS , " +
	" PROD_WK_NO , OCF_REGION_CD , OCF_BUYER_GRP_CD , " +
	" OCF_FRME_CD , FEAT_CD " +
	" FROM TRN_REGIONAL_WKLY_OCF_LMT WHERE "+
	" CONCAT(PROD_MNTH,PROD_WK_NO) >= :otbmPlusOtbw AND "+
	" POR_CD = :porCd AND"+
	" OCF_REGION_CD = :ocfRegionCd AND "+
	" OCF_BUYER_GRP_CD = :ocfByrGrpCd AND "+
	" LINE_CLASS = :lnClass AND "+
	" PLANT_CD = :prdPlntCd AND "+
	" PROD_DAY_NO = :prdDayNo "
	);
	
	private B000031QueryConstants(){
		
	}
	
}
