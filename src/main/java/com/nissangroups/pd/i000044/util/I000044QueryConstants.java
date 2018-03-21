/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000044
 * @author z016127
 *
 */
public class I000044QueryConstants {

	/** P0002 Constant to extract Order take base month  for Monthly*/
	public static final StringBuilder getOrderTakeBasePeriod = new StringBuilder()																									
			.append("SELECT  Max(ORDR_TAKE_BASE_MNTH) from MST_MNTH_ORDR_TAKE_BASE_PD WHERE MST_MNTH_ORDR_TAKE_BASE_PD.POR_CD = :POR_CD AND MST_MNTH_ORDR_TAKE_BASE_PD.STAGE_CD in ('F1','F2') AND MST_MNTH_ORDR_TAKE_BASE_PD.STAGE_STTS_CD = 'C' ");
	
	/**P0001 INSERTING MONTHLY SCHEDULE  INTERFACE FILE DETAIL DATA INTO COMMON FILE HEADER MST */
	public static final StringBuilder insertCmnHeader = new StringBuilder()
			.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,TRN_TYPE)")
			.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:TRN_TYPE)"); 
	
	/** P0003 Extract Weekly Schedule data if Summarize Order Flag equal to 1*/
	public static final StringBuilder extractWeeklyScheduleData = new StringBuilder()
	
			.append("SELECT TRN_WKLY_PROD_SHDL.POR_CD, TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH, TRN_WKLY_PROD_SHDL.PROD_MNTH, MAX(TRN_WKLY_PROD_SHDL.PROD_PLNT_CD), MAX(TRN_WKLY_PROD_SHDL.LINE_CLASS), ")
			.append("MAX(TRN_WKLY_PROD_SHDL.SLS_NOTE_NO), MAX(TRN_WKLY_PROD_SHDL.EX_NO), MAX(TRN_WKLY_PROD_SHDL.CAR_SRS), MAX(TRN_WKLY_PROD_SHDL.FRZN_TYPE_CD),	MAX(TRN_WKLY_PROD_SHDL.FXD_SYMBL), MAX(TRN_WKLY_PROD_SHDL.PROD_MTHD_CD), ") 																								
			.append("TRN_WKLY_PROD_SHDL.PROD_WK_NO, MAX(TRN_WKLY_PROD_SHDL.PROD_DAY_NO),TRN_WKLY_PROD_SHDL.BUYER_CD, TRN_WKLY_PROD_SHDL.APPLD_MDL_CD, TRN_WKLY_PROD_SHDL.PCK_CD, TRN_WKLY_PROD_SHDL.SPEC_DESTN_CD, ")
			.append("MAX(TRN_WKLY_PROD_SHDL.ADD_SPEC_CD), TRN_WKLY_PROD_SHDL.EXT_CLR_CD, TRN_WKLY_PROD_SHDL.INT_CLR_CD, MAX(TRN_WKLY_PROD_SHDL.PROD_FMY_CD), MAX(TRN_WKLY_PROD_SHDL.WK_NO_OF_YEAR), TRN_WKLY_PROD_SHDL.POT_CD, SUM(TRN_WKLY_PROD_SHDL.ORDR_QTY), " )
			.append("MAX(TRN_WKLY_PROD_SHDL.OFFLN_PLAN_DATE), MAX(TRN_WKLY_PROD_SHDL.CRTD_BY), MAX(TRN_WKLY_PROD_SHDL.CRTD_DT), MAX(TRN_WKLY_PROD_SHDL.UPDTD_BY),MAX(TRN_WKLY_PROD_SHDL.UPDTD_DT), ")
			.append("MAX(TRN_WKLY_PROD_SHDL.PROD_ORDR_NO), MAX(TRN_WKLY_PROD_SHDL.LOCAL_PROD_ORDR_NO)  FROM TRN_WKLY_PROD_SHDL INNER JOIN  MST_BUYER ON (TRN_WKLY_PROD_SHDL.BUYER_CD =	MST_BUYER.BUYER_CD AND TRN_WKLY_PROD_SHDL.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD ) ")
			.append("INNER JOIN MST_BUYER_GRP ON (MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD)  WHERE ");
	
	/** P0003 Extract Weekly Schedule data if Summarize Order Flag not equal to 1*/
	public static final StringBuilder extractWeeklyScheduleData2 = new StringBuilder()
	
			.append("SELECT TRN_WKLY_PROD_SHDL.POR_CD, TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH, TRN_WKLY_PROD_SHDL.PROD_MNTH, TRN_WKLY_PROD_SHDL.PROD_PLNT_CD, TRN_WKLY_PROD_SHDL.LINE_CLASS, ")
			.append("TRN_WKLY_PROD_SHDL.SLS_NOTE_NO, TRN_WKLY_PROD_SHDL.EX_NO, TRN_WKLY_PROD_SHDL.CAR_SRS, TRN_WKLY_PROD_SHDL.FRZN_TYPE_CD,	TRN_WKLY_PROD_SHDL.FXD_SYMBL, TRN_WKLY_PROD_SHDL.PROD_MTHD_CD, ") 																								
			.append("TRN_WKLY_PROD_SHDL.PROD_WK_NO, TRN_WKLY_PROD_SHDL.PROD_DAY_NO,TRN_WKLY_PROD_SHDL.BUYER_CD, TRN_WKLY_PROD_SHDL.APPLD_MDL_CD, TRN_WKLY_PROD_SHDL.PCK_CD, TRN_WKLY_PROD_SHDL.SPEC_DESTN_CD, ")
			.append("TRN_WKLY_PROD_SHDL.ADD_SPEC_CD, TRN_WKLY_PROD_SHDL.EXT_CLR_CD, TRN_WKLY_PROD_SHDL.INT_CLR_CD, TRN_WKLY_PROD_SHDL.PROD_FMY_CD, TRN_WKLY_PROD_SHDL.WK_NO_OF_YEAR, TRN_WKLY_PROD_SHDL.POT_CD, TRN_WKLY_PROD_SHDL.ORDR_QTY, " )
			.append("TRN_WKLY_PROD_SHDL.OFFLN_PLAN_DATE, TRN_WKLY_PROD_SHDL.CRTD_BY, TRN_WKLY_PROD_SHDL.CRTD_DT, TRN_WKLY_PROD_SHDL.UPDTD_BY,TRN_WKLY_PROD_SHDL.UPDTD_DT, ")
			.append("TRN_WKLY_PROD_SHDL.PROD_ORDR_NO, TRN_WKLY_PROD_SHDL.LOCAL_PROD_ORDR_NO  FROM TRN_WKLY_PROD_SHDL INNER JOIN  MST_BUYER ON (TRN_WKLY_PROD_SHDL.BUYER_CD =	MST_BUYER.BUYER_CD AND TRN_WKLY_PROD_SHDL.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD ) ")
			.append("INNER JOIN MST_BUYER_GRP ON (MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD)  WHERE ");
			
	/** Add where condition to Extract Weekly Schedule data */
	public static final StringBuilder whereConditionForWeeklyData = new StringBuilder()
			.append("TRN_WKLY_PROD_SHDL.POR_CD	= '"+IFConstants.porCd_Param+"' AND TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH = '"+IFConstants.ordrTkBsMnth_Param+"' AND	TRN_WKLY_PROD_SHDL.PROD_MNTH =	'"+IFConstants.ordrTkBsMnth_Param+"' ");	
	
	/**sql construction for where Clause   **/ 
	public static final StringBuilder baseQueryCondition = new StringBuilder()
			.append(""+IFConstants.param_WklyPorCD+"")
			.append(""+IFConstants.param_WklyOrdrTake+"")
			.append(""+IFConstants.param_trnWklyOrdrTake+"")
			.append(""+IFConstants.param_buyer_buyerGrpCD+"")
			.append(""+IFConstants.param_ocfRegionCd+"")
			.append(""+IFConstants.param_ocfBuyerGrpCd+"")
			.append(""+IFConstants.param_rhqCd+"");
	
	/**sql construction for where Clause   **/
	public static final StringBuilder baseQueryCondition1 = new StringBuilder()
			.append(""+IFConstants.param_MnthPorCD+"")
			.append(""+IFConstants.param_MnthOrdrTake+"")
			.append(""+IFConstants.param_trnMnthOrdrTake+"")
			.append(""+IFConstants.param_buyer_buyerGrpCD+"")
			.append(""+IFConstants.param_ocfRegionCd+"")
			.append(""+IFConstants.param_ocfBuyerGrpCd+"")
			.append(""+IFConstants.param_rhqCd+"");
	
	/** Add Group by to Extract Weekly Schedule data */
	public static final StringBuilder summarizeOrderQtyFlgForWeeklyData = new StringBuilder()
			.append("GROUP BY TRN_WKLY_PROD_SHDL.POR_CD, TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH, TRN_WKLY_PROD_SHDL.PROD_MNTH, TRN_WKLY_PROD_SHDL.PROD_WK_NO, TRN_WKLY_PROD_SHDL.OCF_REGION_CD, ")
			.append("TRN_WKLY_PROD_SHDL.BUYER_CD, TRN_WKLY_PROD_SHDL.APPLD_MDL_CD, TRN_WKLY_PROD_SHDL.PCK_CD, TRN_WKLY_PROD_SHDL.CAR_SRS, TRN_WKLY_PROD_SHDL.SPEC_DESTN_CD,	")																						
			.append("TRN_WKLY_PROD_SHDL.EXT_CLR_CD, TRN_WKLY_PROD_SHDL.INT_CLR_CD, TRN_WKLY_PROD_SHDL.POT_CD ");																									
												
	/** P0003 Constant to extract Monthly Schedule data if Summarize Order Flag equal to 1*/
	public static final StringBuilder extractMonthlyScheduleData = new StringBuilder()
			.append("SELECT TRN_MNTH_PROD_SHDL.POR_CD,TRN_MNTH_PROD_SHDL.ORDRTK_BASE_MNTH, TRN_MNTH_PROD_SHDL.PROD_MNTH, MAX(TRN_MNTH_PROD_SHDL.PROD_PLNT_CD), MAX(TRN_MNTH_PROD_SHDL.LINE_CLASS), MAX(TRN_MNTH_PROD_SHDL.SLS_NOTE_NO), ")
			.append("MAX(TRN_MNTH_PROD_SHDL.EX_NO), MAX(TRN_MNTH_PROD_SHDL.CAR_SRS), MAX(TRN_MNTH_PROD_SHDL.FRZN_TYPE_CD),	MAX(TRN_MNTH_PROD_SHDL.FXD_SYMBL), MAX(TRN_MNTH_PROD_SHDL.PROD_MTHD_CD), TRN_MNTH_PROD_SHDL.PROD_WK_NO, ")
			.append("MAX(TRN_MNTH_PROD_SHDL.PROD_DAY_NO), TRN_MNTH_PROD_SHDL.BUYER_CD, TRN_MNTH_PROD_SHDL.APPLD_MDL_CD, TRN_MNTH_PROD_SHDL.PACK_CD, TRN_MNTH_PROD_SHDL.SPEC_DESTN_CD, MAX(TRN_MNTH_PROD_SHDL.ADD_SPEC_CD), ")
			.append("TRN_MNTH_PROD_SHDL.EXT_CLR_CD, TRN_MNTH_PROD_SHDL.INT_CLR_CD, MAX(TRN_MNTH_PROD_SHDL.PROD_FMLY_CD), MAX(TRN_MNTH_PROD_SHDL.WK_NO_OF_YEAR), TRN_MNTH_PROD_SHDL.POT_CD, SUM(TRN_MNTH_PROD_SHDL.ORDR_QTY),")
			.append("MAX(TRN_MNTH_PROD_SHDL.OFFLN_PLAN_DATE), MAX(TRN_MNTH_PROD_SHDL.CRTD_BY), MAX(TRN_MNTH_PROD_SHDL.CRTD_DT), MAX(TRN_MNTH_PROD_SHDL.UPDTD_BY), MAX(TRN_MNTH_PROD_SHDL.UPDTD_DT), MAX(TRN_MNTH_PROD_SHDL.PROD_ORDR_NO), ")
			.append("MAX(TRN_MNTH_PROD_SHDL.LOCAL_PROD_ORDR_NO) FROM TRN_MNTH_PROD_SHDL INNER JOIN  MST_BUYER ON (TRN_MNTH_PROD_SHDL.BUYER_CD =	MST_BUYER.BUYER_CD AND TRN_MNTH_PROD_SHDL.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD ) ")
			.append("INNER JOIN MST_BUYER_GRP ON (MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD)  WHERE ");
	
	/** P0003 Constant to extract Monthly Schedule data if Summarize Order Flag not equal to 1*/
	public static final StringBuilder extractMonthlyScheduleData2 = new StringBuilder()
			.append("SELECT TRN_MNTH_PROD_SHDL.POR_CD,TRN_MNTH_PROD_SHDL.ORDRTK_BASE_MNTH, TRN_MNTH_PROD_SHDL.PROD_MNTH, TRN_MNTH_PROD_SHDL.PROD_PLNT_CD, TRN_MNTH_PROD_SHDL.LINE_CLASS, TRN_MNTH_PROD_SHDL.SLS_NOTE_NO, ")
			.append("TRN_MNTH_PROD_SHDL.EX_NO, TRN_MNTH_PROD_SHDL.CAR_SRS, TRN_MNTH_PROD_SHDL.FRZN_TYPE_CD,	TRN_MNTH_PROD_SHDL.FXD_SYMBL, TRN_MNTH_PROD_SHDL.PROD_MTHD_CD, TRN_MNTH_PROD_SHDL.PROD_WK_NO, ")
			.append("TRN_MNTH_PROD_SHDL.PROD_DAY_NO, TRN_MNTH_PROD_SHDL.BUYER_CD, TRN_MNTH_PROD_SHDL.APPLD_MDL_CD, TRN_MNTH_PROD_SHDL.PACK_CD, TRN_MNTH_PROD_SHDL.SPEC_DESTN_CD, TRN_MNTH_PROD_SHDL.ADD_SPEC_CD, ")
			.append("TRN_MNTH_PROD_SHDL.EXT_CLR_CD, TRN_MNTH_PROD_SHDL.INT_CLR_CD, TRN_MNTH_PROD_SHDL.PROD_FMLY_CD, TRN_MNTH_PROD_SHDL.WK_NO_OF_YEAR, TRN_MNTH_PROD_SHDL.POT_CD, TRN_MNTH_PROD_SHDL.ORDR_QTY,")
			.append("TRN_MNTH_PROD_SHDL.OFFLN_PLAN_DATE, TRN_MNTH_PROD_SHDL.CRTD_BY, TRN_MNTH_PROD_SHDL.CRTD_DT, TRN_MNTH_PROD_SHDL.UPDTD_BY, TRN_MNTH_PROD_SHDL.UPDTD_DT, TRN_MNTH_PROD_SHDL.PROD_ORDR_NO, ")
			.append("TRN_MNTH_PROD_SHDL.LOCAL_PROD_ORDR_NO FROM TRN_MNTH_PROD_SHDL INNER JOIN  MST_BUYER ON (TRN_MNTH_PROD_SHDL.BUYER_CD =	MST_BUYER.BUYER_CD AND TRN_MNTH_PROD_SHDL.OCF_REGION_CD = MST_BUYER.OCF_REGION_CD ) ")
			.append("INNER JOIN MST_BUYER_GRP ON (MST_BUYER.BUYER_GRP_CD = MST_BUYER_GRP.BUYER_GRP_CD)  WHERE ");
	
	/** Add where condition to Extract Monthly Schedule data */
	public static final StringBuilder whereConditionForMonthlyData = new StringBuilder()
			.append("TRN_MNTH_PROD_SHDL.POR_CD	= '"+IFConstants.porCd_Param+"' AND TRN_MNTH_PROD_SHDL.ORDRTK_BASE_MNTH = '"+IFConstants.ordrTkBsMnth_Param+"' AND TRN_MNTH_PROD_SHDL.PROD_MNTH	!= '"+IFConstants.ordrTkBsMnth_Param+"' ");																																	
	
	/** Add Group by to Extract Monthly Schedule data */
    public static final StringBuilder summarizeOrderQtyFlgForMonthlyData = new StringBuilder()	 
 			.append("GROUP BY TRN_MNTH_PROD_SHDL.POR_CD, TRN_MNTH_PROD_SHDL.ORDRTK_BASE_MNTH, TRN_MNTH_PROD_SHDL.PROD_MNTH, TRN_MNTH_PROD_SHDL.PROD_WK_NO, ")
 			.append("TRN_MNTH_PROD_SHDL.OCF_REGION_CD, TRN_MNTH_PROD_SHDL.BUYER_CD, TRN_MNTH_PROD_SHDL.APPLD_MDL_CD, TRN_MNTH_PROD_SHDL.PACK_CD, TRN_MNTH_PROD_SHDL.CAR_SRS, ")
 			.append("TRN_MNTH_PROD_SHDL.SPEC_DESTN_CD, TRN_MNTH_PROD_SHDL.EXT_CLR_CD, TRN_MNTH_PROD_SHDL.INT_CLR_CD, TRN_MNTH_PROD_SHDL.POT_CD	");																																					
	
    /**
	 * Instantiates a new I000044 query constants.
	 */
    private I000044QueryConstants() {
		super();		
	} 
}
