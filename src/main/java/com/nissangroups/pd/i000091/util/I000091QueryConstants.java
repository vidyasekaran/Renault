/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000091.util;

import com.nissangroups.pd.util.IFConstants;

/**
 * Constant file to keep the queries related to the Interface I000091
 * @author z016127
 *
 */
public class I000091QueryConstants 
{

	/** P0002.1 Constant to extract  max of Order take base month from WEEKLY_ORDER_TAKE_BASE_PERIOD_MST */
	public static final StringBuilder getOrderTakeBasePeriod = new StringBuilder()
		.append("SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE WHERE TRIM(POR) = :porCd AND STAGE_CD ='WK' AND STAGE_STTS_CD='O'");
	
	/** P0002.2 Constant to Extract the OCF_AUTO_ALLOCATION_FLAG from Master tables*/
	public static final StringBuilder getOcfAutoAllctnFlg = new StringBuilder()
		.append("SELECT MST_OCF_REGION.OCF_AUTO_ALLCTN_FLAG,MST_BUYER.OCF_REGION_CD,MST_BUYER.OCF_BUYER_GRP_CD FROM MST_BUYER INNER JOIN MST_OCF_REGION  ON (MST_BUYER.PROD_REGION_CD = MST_OCF_REGION.PROD_REGION_CD ")
		.append("AND MST_BUYER.OCF_REGION_CD = MST_OCF_REGION.OCF_REGION_CD AND MST_BUYER.OCF_BUYER_GRP_CD = MST_OCF_REGION.OCF_BUYER_GRP_CD) INNER JOIN MST_POR  ON (MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD) WHERE");
		
	/** P0002.3 Constant to extract the BUYER_GROUP_CD from Master tables*/
	public static final StringBuilder extractBuyGrpCd = new StringBuilder()
		.append("SELECT MST_BUYER.BUYER_GRP_CD,MST_PRMTR.VAL1 FROM MST_BUYER  INNER JOIN MST_BUYER_GRP  ON (MST_BUYER_GRP.BUYER_GRP_CD = MST_BUYER.BUYER_GRP_CD ) ")
		.append("INNER JOIN MST_PRMTR ON (MST_PRMTR.KEY1 = MST_BUYER.BUYER_GRP_CD ) WHERE MST_PRMTR.PRMTR_CD ='PATTERN FLAG' AND ");
	
	/** P0002.4.1 Constant to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 1 */
	public static final StringBuilder regnalWklyPtrn1 = new StringBuilder()
		.append("SELECT TRN_REGIONAL_WKLY_OCF_LMT.POR_CD, TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO, ")
		.append("'' AS PROD_DAY_NO, '' AS LINE_CLASS, '' AS PLANT_CD, SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_LMT_QTY),  SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_USG_QTY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_BY), ")
		.append("MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_BY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_DT)  FROM TRN_REGIONAL_WKLY_OCF_LMT  WHERE ");
	
	/** Add group by to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 1*/
	public static final StringBuilder regnalWklyPtrn1Condtn = new StringBuilder()	
		.append("GROUP BY TRN_REGIONAL_WKLY_OCF_LMT.POR_CD,TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS,TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD,TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD,TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH,TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO ");
	
	/** Construction of additional conditions for where clause*/
	public static final StringBuilder rgnlWklyCondition = new StringBuilder()
		.append(""+IFConstants.param_RgnlWkly_Por+"")
		.append(""+IFConstants.param_RgnlWkly_OcfRgnCd+"")
		.append(""+IFConstants.param_RgnlWkly_OcfByrGrp+"")
		.append(""+IFConstants.param_RgnlWklyPrdMnth+"");
	
	/** Construction of additional conditions for where clause*/
	public static final StringBuilder byrGrpWklyCondition = new StringBuilder()
		.append(" TRN_BUYER_GRP_WKLY_OCF_LMT.OCF_FRME_CD = '00' AND ")
		.append(""+IFConstants.param_ByrGrpWkly_PorCd+"")
		.append(""+IFConstants.param_ByrGrpWkly_ByrGrpCd+"")
		.append(""+IFConstants.param_ByrGrpWkly_PrdMnth+"");
	
	/** Construction of additional conditions for where clause*/
	public static final StringBuilder baseQueryCondition = new StringBuilder()
	.append(""+IFConstants.param_porCd+"")
	.append(""+IFConstants.param_OcfRgnCd+"")
	.append(""+IFConstants.param_OcfByrGrpCd+"")
	.append(""+IFConstants.param_buyer_buyerGrpCD+"");
	
	/** Construction of additional conditions for where clause*/
	public static final StringBuilder whereCondition = new StringBuilder()
	.append(""+IFConstants.param_ocfRegionCd+"")
	.append(""+IFConstants.param_ocfBuyerGrpCd+"");
	
	/** P0002.4.2 Constant to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 2 */
	public static final StringBuilder regnalWklyPtrn2 = new StringBuilder()
		.append("SELECT TRN_REGIONAL_WKLY_OCF_LMT.POR_CD, TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO, ")
		.append("'' AS PROD_DAY_NO, TRN_REGIONAL_WKLY_OCF_LMT.LINE_CLASS, TRN_REGIONAL_WKLY_OCF_LMT.PLANT_CD, SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_LMT_QTY), SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_USG_QTY), ")
		.append("MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_BY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_BY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_DT)  FROM TRN_REGIONAL_WKLY_OCF_LMT  WHERE ");
	
	/** Add group by to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 2*/
	public static final StringBuilder regnalWklyPtrn2Conditn = new StringBuilder()	
		.append("GROUP BY TRN_REGIONAL_WKLY_OCF_LMT.POR_CD, TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO, TRN_REGIONAL_WKLY_OCF_LMT.LINE_CLASS, TRN_REGIONAL_WKLY_OCF_LMT.PLANT_CD ");
	   
	/** P0002.4.3 Constant to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 3 */ 
	public static final StringBuilder regnalWklyPtrn3 = new StringBuilder()
		.append("SELECT TRN_REGIONAL_WKLY_OCF_LMT.POR_CD,TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS,TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD,TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD,TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO,TRN_REGIONAL_WKLY_OCF_LMT.PROD_DAY_NO, TRN_REGIONAL_WKLY_OCF_LMT.LINE_CLASS, TRN_REGIONAL_WKLY_OCF_LMT.PLANT_CD, TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_LMT_QTY, ")
		.append("TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_USG_QTY, TRN_REGIONAL_WKLY_OCF_LMT.CRTD_BY, TRN_REGIONAL_WKLY_OCF_LMT.CRTD_DT, TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_BY, TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_DT FROM ")
		.append("TRN_REGIONAL_WKLY_OCF_LMT WHERE ");

	/** P0002.4.4 Constant to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 4 */
	public static final StringBuilder regnalWklyPtrn4 = new StringBuilder()
	 	.append("SELECT TRN_REGIONAL_WKLY_OCF_LMT.POR_CD, TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, ")
	 	.append("TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO, TRN_REGIONAL_WKLY_OCF_LMT.PROD_DAY_NO,'' AS LINE_CLASS, '' AS PLANT_CD, SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_LMT_QTY), SUM(TRN_REGIONAL_WKLY_OCF_LMT.REGIONAL_OCF_USG_QTY),  ")
	 	.append("MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_BY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_BY), MAX(TRN_REGIONAL_WKLY_OCF_LMT.UPDTD_DT) FROM ")
	 	.append("TRN_REGIONAL_WKLY_OCF_LMT WHERE ");
	
	/** Add group by to extract the REGIONAL WEEKLY OCF from Trn Regional Weekly Table for Pattern 4*/
	public static final StringBuilder regnalWklyPtrn4Cndtn = new StringBuilder() 	
	 	.append("GROUP BY TRN_REGIONAL_WKLY_OCF_LMT.POR_CD, TRN_REGIONAL_WKLY_OCF_LMT.CAR_SRS, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_CD, TRN_REGIONAL_WKLY_OCF_LMT.FEAT_TYPE_CD, ")
	 	.append("TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH, TRN_REGIONAL_WKLY_OCF_LMT.PROD_WK_NO, TRN_REGIONAL_WKLY_OCF_LMT.PROD_DAY_NO ");

	/** P0002.5.1 Constant to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 1*/
	public static final StringBuilder byrGrpWklyPtrn1 = new StringBuilder()  
		.append("SELECT TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO,'' AS PROD_DAY_NO, '' AS LINE_CLASS, '' AS PLANT_CD,SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY), SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY), ")
		.append("MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_BY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_BY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_DT) FROM TRN_BUYER_GRP_WKLY_OCF_LMT WHERE ");
	
	/** Add group by to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 1*/
	public static final StringBuilder byrGrpWklyPtrn1Cndtn = new StringBuilder()	
		.append("GROUP BY TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO ");
	
	/** P0002.5.2 Constant to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 1*/
	public static final StringBuilder byrGrpWklyPtrn2 = new StringBuilder()
		.append("SELECT TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO,'' AS PROD_DAY_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.LINE_CLASS, TRN_BUYER_GRP_WKLY_OCF_LMT.PLANT_CD, SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY), ")
		.append("SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_BY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_BY), ")
		.append("MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_DT) FROM TRN_BUYER_GRP_WKLY_OCF_LMT WHERE ");
	
	/** Add group by to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 2*/
	public static final StringBuilder byrGrpWklyPtrn2Cndtn = new StringBuilder()	
		.append("GROUP BY TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.LINE_CLASS, TRN_BUYER_GRP_WKLY_OCF_LMT.PLANT_CD");
	
	/** P0002.5.3 Constant to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 3*/
	public static final StringBuilder byrGrpWklyPtrn3 = new StringBuilder()
		.append("SELECT TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_DAY_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.LINE_CLASS,TRN_BUYER_GRP_WKLY_OCF_LMT.PLANT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY, TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_BY, TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_DT, TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_BY, TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_DT FROM ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT WHERE ");
	
	/** P0002.5.4 Constant to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 4*/
	public static final StringBuilder byrGrpWklyPtrn4 = new StringBuilder()
		.append("SELECT TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_DAY_NO, '' AS LINE_CLASS, '' AS PLANT_CD, SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY), SUM(TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY),  ")
		.append("MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_BY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.CRTD_DT), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_BY), MAX(TRN_BUYER_GRP_WKLY_OCF_LMT.UPDTD_DT) FROM TRN_BUYER_GRP_WKLY_OCF_LMT WHERE ");
	
	/** Add group by to extract the BUYER GROUP WEEKLY OCF from Trn Buyer Group Weekly Ocf Limit table for Pattern 4*/
	public static final StringBuilder byrGrpWklyPtrn4Cndtn = new StringBuilder()	
		.append("GROUP BY TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.CAR_SRS, ")
		.append("TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.FEAT_TYPE_CD, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_WK_NO, TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_DAY_NO");
	
	/** P0002.6.1 Constant to extract Order Take Period Type from Master tables */
	public static final StringBuilder getOrdrTkePrdTyp = new StringBuilder()
		.append("SELECT MST_PRMTR.VAL1 FROM MST_PRMTR  WHERE  MST_PRMTR.PRMTR_CD ='ORDERTAKE_PERIOD_TYPE_CODE' AND MST_PRMTR.KEY1 = :porCd AND MST_PRMTR.KEY2 = :buyerGrpCd ");
	
	/**P0002.6.2 Constant to extract Constraint Period Type from Master tables */
	public static final StringBuilder getCnstrntPrdTyp = new StringBuilder()
		.append("SELECT MST_PRMTR.VAL1 FROM MST_PRMTR  WHERE MST_PRMTR.PRMTR_CD ='CONSTRAINT_PERIOD_TYPE_CD' AND MST_PRMTR.KEY1 = :porCd AND MST_PRMTR.KEY2 = :buyerGrpCd ");
	
	/**P0002.3 Constant to extract Pattern Flag from Master tables */
	public static final StringBuilder getPatrnFlg = new StringBuilder()
	.append("SELECT MST_PRMTR.KEY2 FROM MST_PRMTR  WHERE  MST_PRMTR.PRMTR_CD ='PatternFlag' AND MST_PRMTR.KEY1 = :buyerGrpCd ");
	
	/**
	 * Instantiates a new I000091 query constants.
	 */
	private I000091QueryConstants() {
		super();		
	}	
}
