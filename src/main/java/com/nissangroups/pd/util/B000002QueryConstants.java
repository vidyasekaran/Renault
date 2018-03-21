/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002QueryConstants
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

/**
 * The Class B000002QueryConstants.
 */
/*
 * Query Constants Class
 * 
 * @author z013576
 */
public class B000002QueryConstants {

	/** Constant Query_01_Fetch_EI_Spec_details. */
	// END ITEM SPEC DETAILS Fetch Query
	public static final StringBuilder Query_01_Fetch_EI_Spec_details = new StringBuilder()
			.append("SELECT DISTINCT APPLD_MDL_CD,POR_CD,PROD_FMY_CD,PROD_STAGE_CD,"
					+ " GSIS_REGION_GRND,PCK_CD,SPEC_DESTN_CD,ADTNL_SPEC_CD,SEQ_ID"
					+ " FROM MST_END_ITM_SPEC"
					+ " WHERE POR_CD = :porCd"
					+ " AND EI_SPEC_ERR_FLAG in (2,4)" + " ORDER BY SEQ_ID");

	/** Constant Query_02_OEISpec_Insrt. */
	// MST_OEI_SPEC Insert Query
	public static final StringBuilder Query_02_OEISpec_Insrt = new StringBuilder()
			.append("INSERT INTO MST_OEI_SPEC"
					+ " (POR_CD, PROD_FMY_CD,PROD_STAGE_CD,APPLD_MDL_CD,"
					+ " PCK_CD,SPEC_DESTN_CD,ADTNL_SPEC_CD,CAR_SRS,OEI_SPEC_ID)"
					+ " values(:porCd, :prdctnFamilyCd, :prdctnStgCd, :appldMdlCd, :packCd, :specDestCd, :addtnlSpecCd, :crsSrs, :oeiSpecId)");

	/** Constant Query_03_Fetch_EI_BuyerCode_details. */
	// Query to fetch the Buyer Code Details
	public static final StringBuilder Query_03_Fetch_EI_BuyerCode_details = new StringBuilder()
			.append("SELECT DISTINCT ei.BUYER_CD, oeiSpecMst.OEI_SPEC_ID, ei.POR_CD"
					+ " FROM MST_END_ITM_SPEC ei"
					+ " JOIN MST_OEI_SPEC oeiSpecMst"
					+ " ON concat(ei.APPLD_MDL_CD, ei.PCK_CD) = concat(oeiSpecMSt.APPLD_MDL_CD, oeiSpecMst.PCK_CD)"
					+ " AND ei.POR_CD = oeiSpecMst.POR_CD "
					+ " AND ei.PROD_FMY_CD = oeiSpecMst.PROD_FMY_CD "
					+ " AND ei.PROD_STAGE_CD = oeiSpecMst.PROD_STAGE_CD "
					+ " AND ei.SPEC_DESTN_CD = oeiSpecMst.SPEC_DESTN_CD "
					+ " AND ei.ADTNL_SPEC_CD = oeiSpecMst.ADTNL_SPEC_CD "
					+ " WHERE "
					//+ " concat(ei.APPLD_MDL_CD, ei.PCK_CD) in"
					//+ " (SELECT DISTINCT(concat(oeiSpecMSt.APPLD_MDL_CD, oeiSpecMst.PCK_CD)) FROM MST_OEI_SPEC oeiSpecMst) AND"
					+ "  ei.POR_CD = :porCd"
					+ " and ei.EI_SPEC_ERR_FLAG in (2,4)"
					+ " ORDER by ei.BUYER_CD,oeiSpecMst.OEI_SPEC_ID");

	/** Constant Query_04_OEIBuyer_Insrt. */
	// Query to insert data in MST_OEI_BUYER Table
	public static final StringBuilder Query_04_OEIBuyer_Insrt = new StringBuilder()
			.append("INSERT INTO MST_OEI_BUYER"
					+ " (OEI_SPEC_ID,BUYER_CD,OEI_BUYER_ID,POR_CD)"
					+ " values(:OeiSpecID, :buyerCd, :oeiBuyerId, :porCd)");

	/** Constant Query_05_FetchMaxUkOeiSpecId. */
	public static final StringBuilder Query_05_FetchMaxUkOeiSpecId = new StringBuilder()
			.append("select MAX(OEI_SPEC_ID) from MST_OEI_SPEC");

	/** Constant Query_06_FetchMaxUkOeiBuyerId. */
	// Query to Fetch the Max OEI BUYER ID
	public static final StringBuilder Query_06_FetchMaxUkOeiBuyerId = new StringBuilder()
			.append("SELECT MAX(TRIM(OEI_BUYER_ID)) FROM MST_OEI_BUYER");

	/** Constant Query_07_Fetch_EI_ColorCode_details. */
	// Query to Fetch the Colur Details from MST_OEI_SPEC
	public static final StringBuilder Query_07_Fetch_EI_ColorCode_details = new StringBuilder()
			.append("SELECT oeib.OEI_BUYER_ID,ei.EXT_CLR_CD,ei.INT_CLR_CD,ei.POR_CD"
					+ " FROM MST_OEI_BUYER oeib"
					+ " JOIN MST_OEI_SPEC oeis "
					+ " ON oeis.OEI_SPEC_ID = oeib.OEI_SPEC_ID"
					+ " JOIN MST_END_ITM_SPEC ei"
					+ " ON concat(ei.APPLD_MDL_CD,ei.PCK_CD) = concat(oeis.APPLD_MDL_CD,oeis.PCK_CD)"
					+ " AND oeib.BUYER_CD = ei.BUYER_CD"
					+ " and ei.PROD_FMY_CD = oeis.PROD_FMY_CD"
					+ " and ei.SPEC_DESTN_CD = oeis.SPEC_DESTN_CD"
					+ " and ei.PROD_STAGE_CD = oeis.PROD_STAGE_CD "
					+ " and ei.ADTNL_SPEC_CD = oeis.ADTNL_SPEC_CD"
					+ " and ei.por_cd = oeis.por_cd"
					+ " WHERE ei.EI_SPEC_ERR_FLAG in (2,4)"
					+ " AND ei.POR_CD = :porCd"
					+ " ORDER BY oeib.OEI_BUYER_ID,oeib.OEI_SPEC_ID");

	/** Constant Query_08_FetchMaxUkOseiId. */
	// Query to Fetch the Max OSEI ID from MST_OSEI
	public static final StringBuilder Query_08_FetchMaxUkOseiId = new StringBuilder()
			.append("select MAX(TRIM(OSEI_ID)) from MST_OSEI");

	/** Constant Query_09_OSEIMst_Insrt. */
	// Query to insert data in MST_OSEI Table
	public static final StringBuilder Query_09_OSEIMst_Insrt = new StringBuilder()
			.append("INSERT INTO MST_OSEI"
					+ " (OEI_BUYER_ID,EXT_CLR_CD,INT_CLR_CD,OSEI_ID,POR_CD)"
					+ " values(:oeiBuyerId, :extClrCd, :intClrCd, :oseiId, :porCd)");

	/** Constant Query_10_Fetch_EI_InteriorColorCode_details. */
	// Query to fetch the Interior Color Details
	public static final StringBuilder Query_10_Fetch_EI_InteriorColorCode_details = new StringBuilder()
			.append(" select distinct INT_CLR_CD, INT_CLR_CD as description from MST_END_ITM_SPEC"
					+ " where POR_CD = :porCd and EI_SPEC_ERR_FLAG in (2,4) Order by INT_CLR_CD");

	/** Constant Query_11_InteriorColorCode_Insrt. */
	// Query to insert interior Color Details
	public static final StringBuilder Query_11_InteriorColorCode_Insrt = new StringBuilder()
			.append("insert into MST_INT_CLR"
					+ " (INT_CLR_CD,INT_CLR_DESC) values(:intClr, :intClrdesc)");

	/** Constant Query_12_Fetch_EI_OSEI_details. */
	// Query to fetch End Item Details from MST_OEI_SPEC
	public static final StringBuilder Query_12_Fetch_EI_OSEI_details = new StringBuilder()
	
	
			.append(" select oseimst.OSEI_ID,ei.EIM_SPEC_ADPT_DATE,ei.EIM_SPEC_ABLSH_DATE, ")
			.append("  ei.TOKUSO_NAME,ei.PCKGE_NAME,ei.LCL_NOTE,ei.MDFD_FLAG,ei.POR_CD , ")
			.append("  ei.GSIS_REGION_GRND , ei.GSIS_APPLD_MDL_NO ")
			.append("  from MST_OSEI oseimst ")
			.append("  inner join MST_OEI_BUYER oeib on oseimst.OEI_BUYER_ID = oeib.OEI_BUYER_ID ")
			.append(" inner join MST_OEI_SPEC oeis on oeis.OEI_SPEC_ID = oeib.OEI_SPEC_ID ")
    		.append(" inner join MST_END_ITM_SPEC ei  ")
    		.append(" on concat(ei.APPLD_MDL_CD,ei.PCK_CD) = concat(oeis.APPLD_MDL_CD,oeis.PCK_CD) ")
    		.append(" and ei.SPEC_DESTN_CD = oeis.SPEC_DESTN_CD ")
    		.append(" and ei.PROD_FMY_CD = oeis.PROD_FMY_CD ")
    		.append(" and ei.por_cd = oeis.por_cd ")
    		.append(" and ei.ADTNL_SPEC_CD = oeis.ADTNL_SPEC_CD ")
    		.append(" and ei.PROD_STAGE_CD = oeis.PROD_STAGE_CD ")
    		.append(" and oeib.BUYER_CD = ei.BUYER_CD ")
    		.append(" and oeib.por_cd = ei.por_cd ")
    		.append(" and oseimst.EXT_CLR_CD = ei.EXT_CLR_CD ")
    		.append(" and oseimst.INT_CLR_CD = ei.INT_CLR_CD and oseimst.por_cd = ei.por_cd ")
      
			.append(" where ei.EI_SPEC_ERR_FLAG in (2,4) and ei.POR_CD = :porCd " )
			.append(" order by oseimst.OSEI_ID,ei.EIM_SPEC_ADPT_DATE,ei.EIM_SPEC_ABLSH_DATE ");

	/** Constant Query_13_OSEIDetails_Insrt. */
	// Query to insert OSEI details in nMST_OSEI_DTL Table
	public static final StringBuilder Query_13_OSEIDetails_Insrt = new StringBuilder()
			.append("Insert into MST_OSEI_DTL"
					+ " (OSEI_ID,OSEI_ADPT_DATE,"
					+ " OSEI_SUSPENDED_DATE,OSEI_ABLSH_DATE,"
					+ " TOSUKO_BASE_PCK_CD,END_ITM_STTS_CD,"
					+ " PCKGE_NAME,LCL_NOTE,MDFD_FLAG,"
					+ " POR_CD,MDL_YEAR , GSIS_REGION_GRND , GSIS_APPLD_MDL_NO )"
					+ " values(:oseiId, :oseiAdptDt, :oseiSusDt, :oseiAblshDt, :tkusoBse, :eiSttsCd, :pckgNm, :lclNt, :mdfdFlg, :porCd , :mdlYr , :gsisRgnCd , :gsisMdlNo )");

	/** Constant Query_14_getEiSpecRecordCount. */
	// Fetching Record Count from OEI_SPEC_MST
	public static final StringBuilder Query_14_getEiSpecRecordCount = new StringBuilder()
			.append("SELECT Count(*) FROM MST_OEI_SPEC"
					+ " WHERE POR_CD = :porCd and PROD_FMY_CD = :prod_fmly_cd"
					+ " AND PROD_STAGE_CD = :productionStageCode"
					+ " AND APPLD_MDL_CD = :appldMdlCd and PCK_CD = :packCd and SPEC_DESTN_CD = :specDstnCd"
					+ " AND ADTNL_SPEC_CD = :additionalSpecCd");

	/** Constant Query_15_getEiBuyerCdRecordCount. */
	// Fetchin Record Count frfom MST_OEI_BUYER Table
	public static final StringBuilder Query_15_getEiBuyerCdRecordCount = new StringBuilder()
			.append("SELECT Count(*) from MST_OEI_BUYER"
					+ " WHERE OEI_SPEC_ID= :oeiSpecId"
					+ " AND BUYER_CD = :buyerCd");

	/** Constant Query_16_getEiColorCdRecordCount. */
	// Query to get the Record Count
	public static final StringBuilder Query_16_getEiColorCdRecordCount = new StringBuilder()
			.append("SELECT Count(*) from MST_OSEI"
					+ " WHERE OEI_BUYER_ID= :ukOeiBuyerID"
					+ " AND EXT_CLR_CD = :extrClrCd"
					+ " AND INT_CLR_CD = :intrClrCd" + " AND POR_CD = :porCd");

	/** Constant Query_17_Fetch_EI_OptionalSpecCode_details. */
	// Query to Fetch the OEI Option Spec Code Details
	public static final StringBuilder Query_17_Fetch_EI_OptionalSpecCode_details = new StringBuilder()
			.append("select distinct RTRIM(ei.OPTNL_SPEC_CD) ,oeib.OEI_BUYER_ID,ei.POR_CD from MST_OEI_BUYER oeib join MST_OEI_SPEC oeis on oeis.OEI_SPEC_ID = oeib.OEI_SPEC_ID " )
					.append(" join MST_END_ITM_SPEC ei on concat(ei.APPLD_MDL_CD,ei.PCK_CD) = concat(oeis.APPLD_MDL_CD,oeis.PCK_CD) ")
		    		.append(" and ei.SPEC_DESTN_CD = oeis.SPEC_DESTN_CD ")
		    		.append(" and ei.PROD_FMY_CD = oeis.PROD_FMY_CD ")
		    		.append(" and ei.por_cd = oeis.por_cd ")
		    		.append(" and ei.ADTNL_SPEC_CD = oeis.ADTNL_SPEC_CD ")
		    		.append(" and ei.PROD_STAGE_CD = oeis.PROD_STAGE_CD "
					
					 + " and oeib.BUYER_CD = ei.BUYER_CD"
					+ " and ei.por_cd = oeib.por_cd where ei.EI_SPEC_ERR_FLAG in (2,4) and ei.POR_CD = :porCd"
					+ " order by oeib.OEI_BUYER_ID");

	/** Constant Query_18_OeiByrOptionSpecDetails_Insrt. */
	// Query to insert option Spec Code Details
	public static final StringBuilder Query_18_OeiByrOptionSpecDetails_Insrt = new StringBuilder()
			.append("Insert into MST_OEI_BUYER_OPTN_SPEC_CD"
					+ " (OEI_BUYER_ID,OPTN_SPEC_CODE, POR_CD)"
					+ " values(:ukOeiBuyerId,:optSpcCd,:porCd)");

	/** Constant Query_19_getEiColorCdRecordCount. */
	// Query to get Colour Record Count
	public static final StringBuilder Query_19_getEiColorCdRecordCount = new StringBuilder()
			.append("select Count(*) from MST_INT_CLR"
					+ " where INT_CLR_CD = :intrClrCd");

	/** Constant Query_20_updateEitemErrorFlag. */
	// Query to update End ITem Spec
	public static final StringBuilder Query_20_updateEitemErrorFlag = new StringBuilder()
			.append("update MST_END_ITM_SPEC"
					+ " set UPDTD_DT = :currentTime, UPDTD_BY = :updtBy,EI_SPEC_ERR_FLAG = CASE"
					+ " WHEN (EI_SPEC_ERR_FLAG in (2)) THEN 3"
					+ " WHEN (EI_SPEC_ERR_FLAG in (4)) THEN 5" + " END"
					+ " where POR_CD = :porCd and EI_SPEC_ERR_FLAG in (2,4)");

	/** Constant Query_21_deleteOptionSpecCddetails. */
	// Query to delete OPtion Spec Dode Details
	public static final StringBuilder Query_21_deleteOptionSpecCddetails = new StringBuilder()
			.append("Delete from MST_OEI_BUYER_OPTN_SPEC_CD where POR_CD = :porCd");

	/** Constant Query_22_getOEISpecDetailsRecordCount. */
	// Query to fetch the Count from MST_OSEI_DTL
	public static final StringBuilder Query_22_getOEISpecDetailsRecordCount = new StringBuilder()
			.append("select Count(*) from MST_OSEI_DTL"
					+ " where OSEI_ID = :oeiSpecId and POR_CD = :porCd");

	/** Constant Query_23_getOEISpecDetails. */
	public static final StringBuilder Query_23_getOEISpecDetails = new StringBuilder()
			.append("select uk_osei_id,osei_adopt_date,osei_abolish_date,osei_suspended_date"
					+ " from osei_detail_mst where por_cd = 10");

	/** Constant Query_24_getOEISpecDetailsforUKOSEIID. */
	// Query to fetch the data from MST_OSEI_DTL based on UK OSEI ID
	public static final StringBuilder Query_24_getOEISpecDetailsforUKOSEIID = new StringBuilder()
			.append("select OSEI_ID,OSEI_ADPT_DATE,OSEI_ABLSH_DATE,OSEI_SUSPENDED_DATE"
					+ " from MST_OSEI_DTL where POR_CD = :porCd and OSEI_ID = :ukOseiID and OSEI_ADPT_DATE = :adptDate"
					+ " order by OSEI_ID,OSEI_ADPT_DATE,OSEI_ABLSH_DATE");

	/** Constant Query_25_OseiAblshdtUpdt. */
	// Query to Update the MST_OSEI_DTL Table
	public static final StringBuilder Query_25_OseiAblshdtUpdt = new StringBuilder()
			.append("update MST_OSEI_DTL"
					+ " set OSEI_SUSPENDED_DATE = :suspendedDate,END_ITM_STTS_CD = :sttsCd, UPDTD_BY=:updtBy, UPDTD_DT=:currentTime"
					+ " where OSEI_ID = :ukOseiID and OSEI_ADPT_DATE = :adptDate and POR_CD = :porCd");

	/** Constant Query_26_getCarSeriesforUkOseiId. */
	// Query to fetch the CAR SERIES & END ITEM STTS CD
	public static final StringBuilder Query_26_getCarSeriesforUkOseiId = new StringBuilder()
			.append("select oeis.CAR_SRS,MAX(oseiDetailMst.END_ITM_STTS_CD) as stsCd"
					+ " from MST_OEI_BUYER oeib"
					+ " join MST_OEI_SPEC oeis"
					+ " on oeis.OEI_SPEC_ID = oeib.OEI_SPEC_ID"
					+ " join MST_OSEI oseimst"
					+ " on oseimst.OEI_BUYER_ID = oeib.OEI_BUYER_ID"
					+ " join MST_OSEI_DTL oseiDetailMst on oseiDetailMst.OSEI_ID = oseimst.OSEI_ID"
					+ " join MST_END_ITM_SPEC ei"
					+ " on concat(ei.APPLD_MDL_CD,ei.PCK_CD) = concat(oeis.APPLD_MDL_CD,oeis.PCK_CD)")
					
					.append(" and ei.SPEC_DESTN_CD = oeis.SPEC_DESTN_CD ")
		    		.append(" and ei.PROD_FMY_CD = oeis.PROD_FMY_CD ")
		    		.append(" and ei.por_cd = oeis.por_cd ")
		    		.append(" and ei.ADTNL_SPEC_CD = oeis.ADTNL_SPEC_CD "
		    	
					+ " and ei.PROD_STAGE_CD = oeis.PROD_STAGE_CD "
					+ " and oeib.BUYER_CD = ei.BUYER_CD"
					+ " and oseimst.EXT_CLR_CD = ei.EXT_CLR_CD"
					+ " and oseimst.INT_CLR_CD = ei.INT_CLR_CD"
					+ " where ei.EI_SPEC_ERR_FLAG in (2,4)"
					+ " and ei.POR_CD = :porCd"
					+ " and oseimst.OSEI_ID = :ukOseiID"
					+ " group by oeis.CAR_SRS");

	/** Constant Query_27_EI_BUYER_ADPT_ABLSH_Fetch. */
	// Query to fetch the OEIProdcution Buter Data
	public static final StringBuilder Query_27_EI_BUYER_ADPT_ABLSH_Fetch = new StringBuilder()
			.append("select oeib.OEI_BUYER_ID,ei.EIM_SPEC_ADPT_DATE as min_adopt_date,"
					+ " ei.EIM_SPEC_ABLSH_DATE as max_abolish_dt,"
					+ " oseiDet.OSEI_SUSPENDED_DATE as max_suspended_date,"
					+ " ei.POR_CD"
					+ " from MST_OEI_BUYER oeib"
					+ " join MST_OSEI oseiMst"
					+ " on oseiMSt.OEI_BUYER_ID = oeib.OEI_BUYER_ID"
					+ " join MST_OSEI_DTL oseiDet"
					+ " on oseiDet.OSEI_ID = oseiMst.OSEI_ID"
					+ " join MST_OEI_SPEC oeis"
					+ " on oeis.OEI_SPEC_ID = oeib.OEI_SPEC_ID"
					+ " join MST_END_ITM_SPEC ei"
					+ " on concat(ei.APPLD_MDL_CD,ei.PCK_CD) = concat(oeis.APPLD_MDL_CD,oeis.PCK_CD) ")
					
					.append(" and ei.SPEC_DESTN_CD = oeis.SPEC_DESTN_CD ")
		    		.append(" and ei.PROD_FMY_CD = oeis.PROD_FMY_CD ")
		    		.append(" and ei.por_cd = oeis.por_cd ")
		    		.append(" and ei.ADTNL_SPEC_CD = oeis.ADTNL_SPEC_CD "
		    	
					+ " and ei.PROD_STAGE_CD = oeis.PROD_STAGE_CD "
					+ " and oeib.BUYER_CD = ei.BUYER_CD"
					+ " and ei.por_cd = oeib.por_cd"
					+ " and oseiMst.EXT_CLR_CD = ei.EXT_CLR_CD"
					+ " and oseiMst.INT_CLR_CD = ei.INT_CLR_CD"
					+ " and oseiDet.OSEI_ADPT_DATE = ei.EIM_SPEC_ADPT_DATE"
					+ " and oseiDet.OSEI_SUSPENDED_DATE = ei.EIM_SPEC_ABLSH_DATE"
					+ " where ei.EI_SPEC_ERR_FLAG in (2,4)"
					+ " and ei.POR_CD = :porCd"
					+ " order by oeib.OEI_BUYER_ID,min_adopt_date,max_abolish_dt");

	/** Constant Query_28_EI_BUYER_ADPT_ABLSH_Insrt. */
	// Query to insert OEI BUYER PRDCTN Data
	public static final StringBuilder Query_28_EI_BUYER_ADPT_ABLSH_Insrt = new StringBuilder()
			.append("insert into MST_OEI_BUYER_PRD"
					+ " (OEI_BUYER_ID,EIM_MIN_ADPT_DATE,EIM_MAX_ABLSH_DATE,POR_CD)"
					+ " values (:ukOeiByrId, :eimMinAdptDt, :eimMaxAblshDt, :porCd)");

	/** Constant Query_29_deleteOEIBuyerAdptAblshDatedetails. */
	// Query to delete the OEI BUYER PROD details
	public static final StringBuilder Query_29_deleteOEIBuyerAdptAblshDatedetails = new StringBuilder()
			.append("Delete from MST_OEI_BUYER_PRD where POR_CD = :porCd");

	/** Constant Query_30_FetchOSEIUpdtdUkOseiiddetails. */
	// Query to fetch the updated OSEID Detail
	public static final StringBuilder Query_30_FetchOSEIUpdtdUkOseiiddetails = new StringBuilder()
			.append("select OSEI_ID,OSEI_ADPT_DATE,OSEI_SUSPENDED_DATE,OSEI_ABLSH_DATE,POR_CD"
					+ " from MST_OSEI_DTL"
					+ " where OSEI_SUSPENDED_DATE <> OSEI_ABLSH_DATE"
					+ " and POR_CD =:porCd"
					+ " ORDER BY OSEI_ID,OSEI_ADPT_DATE,OSEI_SUSPENDED_DATE,OSEI_ABLSH_DATE");

	/** Constant Query_31_FetchOngoingOrderTakeBaseMonth. */
	// Query to fetch the latest Order Take Base Month
	public static final StringBuilder Query_31_FetchOngoingOrderTakeBaseMonth = new StringBuilder()
			.append("select MAX(ORDR_TAKE_BASE_MNTH)"
					+ " from MST_MNTH_ORDR_TAKE_BASE_PD where STAGE_CD in ('SO','D1','D2','F1','F2') and POR_CD = :porCd");

	/** Constant Query_32_FetchMonthlyOrderTrnData. */
	// Query to fetch the TRN_MNTHLY_ORDR data Count
	public static final StringBuilder Query_32_FetchMonthlyOrderTrnData = new StringBuilder()
			.append("select count(*) from TRN_MNTHLY_ORDR where POR_CD = :porCd"
					+ " and ORDRTK_BASE_MNTH = :ordrTkeBsMnth"
					+ " and PROD_MNTH > = :ablshDate"
					+ " and OSEI_ID = :ukOseiID"
					+ " and MS_QTY > 0"
					+ " and ORDR_QTY > 0");

	/** Constant Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn. */
	// Query to Update the MST_OSEI_DTL
	public static final StringBuilder Query_33_UpdateOseiDetailMstBasedonMnthlyOrdrTrn = new StringBuilder()
			.append("update MST_OSEI_DTL"
					+ " set OSEI_ABLSH_DATE = :suspendedDate"
					+ " where OSEI_ID = :ukOseiID"
					+ " and OSEI_ADPT_DATE = :adptDate and OSEI_SUSPENDED_DATE = :suspendedDate and POR_CD = :porCd");

	/** Constant Query_34_FetchOngoingOrderTakeBasePeriod. */
	// Query to fetch the Ongoing Order Take Base Period
	public static final StringBuilder Query_34_FetchOngoingOrderTakeBasePeriod = new StringBuilder()
			.append("select MAX(concat(ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO))"
					+ " from MST_WKLY_ORDR_TAKE_BASE"
					+ " where POR = :porCd and" + " STAGE_CD in ('WK','SO')");

	/** Constant Query_35_FetchWklyOrderTrnData. */
	// Query to fetch the TRN_Weekly_ORDR data Count
	public static final StringBuilder Query_35_FetchWklyOrderTrnData = new StringBuilder()
			.append("select count(*) from TRN_WKLY_ORDR"
					+ " where POR_CD = :porCd"
					+ " and CONCAT(PROD_MNTH,PROD_WK_NO) >= :orderTakeBasePeriodWeekly"
					+ " and PROD_MNTH > :ProdMonth"
					+ " and OSEI_ID = :ukOseiID"
					+ " and ORGNL_ORDR_QTY >0 and REQTD_ORDR_QTY > 0");

	// Query to fetch the Latest MSt Schdl Data
	/** Constant Query_36_FetchLtstMStSchdlTrnData. */
	// Query to fetch the TRN_Weekly_ORDR data Count
	public static final StringBuilder Query_36_FetchLtstMStSchdlTrnData = new StringBuilder()
			.append("select count(*) from TRN_LTST_MST_SHDL"
					+ " where POR_CD = :porCd"
					+ " and CONCAT(PROD_MNTH,PROD_WK_NO) >= :orderTakeBasePeriodWeekly"
					+ " and PROD_MNTH > :ProdMonth"
					+ " and OSEI_ID = :ukOseiID");

	/** Constant Query_37_deleteOeiBuyrDetails. */
	// OEI_BUYER_PRD Delete Details
	// public static final StringBuilder Query_37_deleteOeiBuyrDetails = new
	// StringBuilder()
	// .append("DELETE FROM MST_OEI_BUYER_PRD WHERE OEI_BUYER_ID IN ("
	// +
	// " SELECT REGEXP_SUBSTR(:oeiBtobeDeleted, '[^,]+', 1, rownum) as OEI_BUYER_ID FROM DUAL CONNECT BY rownum <= length(:oeiBtobeDeleted) - length(replace(:oeiBtobeDeleted, ',')) + 1)"
	// + " and POR_CD = :porCd");
	public static final StringBuilder Query_37_deleteOeiBuyrDetails = new StringBuilder()
			.append("DELETE FROM MST_OEI_BUYER_PRD WHERE OEI_BUYER_ID = :ukOeiBuyerID"
					+ " and POR_CD = :porCd");

	/** Constant Query_38_updateOeiBuyrDetails. */
	// OEI_BUYER_PRD Update Details
	public static final StringBuilder Query_38_updateOeiBuyrDetails = new StringBuilder()
			.append("UPDATE MST_OEI_BUYER_PRD"
					+ " SET EIM_MAX_ABLSH_DATE = :maxAblshDt"
					+ " WHERE OEI_BUYER_ID = :ukOeiBuyerID"
					+ " AND POR_CD = :porCd");

	/** Constant Query_39_getOEISpecDetails. */
	// Query to fetch the Count from MST_OSEI_DTL
	public static final StringBuilder Query_39_getOEISpecDetails = new StringBuilder()
			.append("select OSEI_ID,OSEI_ADPT_DATE,OSEI_ABLSH_DATE,END_ITM_STTS_CD"
					+ " FROM MST_OSEI_DTL"
					+ " WHERE OSEI_ID = :oeiSpecId"
					+ " and POR_CD = :porCd"
					+ " ORDER BY OSEI_ID,OSEI_ADPT_DATE,OSEI_ABLSH_DATE,END_ITM_STTS_CD");

	/** Constant Query_40_getAblshDt. */
	// Query to fetch the Count from MST_OSEI_DTL
	public static final StringBuilder Query_40_getAblshDt = new StringBuilder()
			.append("select MAX(OSEI_ABLSH_DATE) from MST_OSEI_DTL"
					+ " where OSEI_ID = :oeiSpecId" + " and POR_CD = :porCd");

	/** Constant Query_41_deleteOeioptionSpecDetails. */
	// OEI_BUYER_PRD Delete Details
	// public static final StringBuilder Query_41_deleteOeioptionSpecDetails =
	// new StringBuilder()
	// .append("DELETE FROM MST_OEI_BUYER_OPTN_SPEC_CD WHERE OEI_BUYER_ID IN ("
	// +
	// " SELECT REGEXP_SUBSTR(:tobedeleted, '[^,]+', 1, rownum) as OEI_BUYER_ID FROM DUAL CONNECT BY rownum <= length(:tobedeleted) - length(replace(:tobedeleted, ',')) + 1)"
	// + " and POR_CD = :porCd");
	/** This query has been changed due to the oracle Parameter size limit constraint  */
	public static final StringBuilder Query_41_deleteOeioptionSpecDetails = new StringBuilder()
			.append("DELETE FROM MST_OEI_BUYER_OPTN_SPEC_CD WHERE OEI_BUYER_ID  = :ukOeiBuyerID "
					+ " and POR_CD = :porCd");

	/** Constant Query_42_FetchMaxOrderTakeBasePeriod. */
	// Query to fetch the Ongoing Order Take Base Period
	public static final StringBuilder Query_42_FetchMaxOrderTakeBasePeriod = new StringBuilder()
			.append("select MAX(concat(ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO))"
					+ " from MST_WKLY_ORDR_TAKE_BASE"
					+ " where POR = :porCd and" + " STAGE_CD in ('SC')");
	
	
	public static final String Query_42_InsertIntoOSEIDTL = 
	" INSERT INTO MST_OSEI_DTL (OSEI_ID, OSEI_ADPT_DATE, OSEI_SUSPENDED_DATE, OSEI_ABLSH_DATE, END_ITM_STTS_CD, PCKGE_NAME, LCL_NOTE,TOSUKO_BASE_PCK_CD "
	+ " , MDFD_FLAG,MDL_YEAR ,POR_CD,  GSIS_REGION_GRND , GSIS_APPLD_MDL_NO ,CRTD_BY, CRTD_DT, UPDTD_BY, UPDTD_DT) VALUES (?, ?, ?, ?, " +
					" ?, ? , ? , ?,?,?,?,?,?, 'B000002', "+ 
					" ? , "+
					" 'B000002', ? )";



	/**
	 * Instantiates a new b000002 query constants.
	 */
	private B000002QueryConstants() {

	}

}
