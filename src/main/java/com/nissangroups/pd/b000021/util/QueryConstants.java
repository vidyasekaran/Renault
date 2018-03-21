/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *28-10-2015      z014433(RNTBCI)               Initial Version
 * 12-11-2015		 z014433(RNTBCI)				 Updated to fix UT - Black box defects
 *
 */
package com.nissangroups.pd.b000021.util;

/**
 * Constant file to keep the queries related to the batch B000021. 
 * @author z014433
 *
 */
public class QueryConstants {

	/** Constant Extract Order take base month  */
	public static final StringBuilder getOrdrTkBsMnth = new StringBuilder()
		.append("select POR_CD, ORDR_TAKE_BASE_MNTH,STAGE_CD,STAGE_STTS_CD,SYS_LCK_STTS_CD from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd ");
	
/** Constant Extract Order take base month for Update Flag = Y */
public static final StringBuilder getOrdrTkBsMnthForUpdtFlagY = new StringBuilder()
.append("and STAGE_CD <> 'SO' and STAGE_CD <> 'SC'");

/** Constant Extract Order take base month for Update Flag = N, stage code = D1 */
public static final StringBuilder getOrdrTkBsMnthForUpdtFlagNStgD1 = new StringBuilder()
.append("and (STAGE_CD = 'D1' or STAGE_CD = 'SO')");

/** Constant Extract Order take base month for Update Flag = N, stage code = D2 */
public static final StringBuilder getOrdrTkBsMnthForUpdtFlagNStgD2 = new StringBuilder()
.append("and (STAGE_CD = 'D2' or ( STAGE_CD = 'D1' and STAGE_STTS_CD = 'C'))");

/** Constant Extract Order take base month for Update Flag = N, stage code = F1 */
public static final StringBuilder getOrdrTkBsMnthForUpdtFlagNStgF1 = new StringBuilder()
.append("and (STAGE_CD = 'F1' or ( STAGE_CD = 'D2' and STAGE_STTS_CD = 'C'))");

/** Constant Extract Order take base month for Update Flag = N, stage code = F2 */
public static final StringBuilder getOrdrTkBsMnthForUpdtFlagNStgF2 = new StringBuilder()
.append("and (STAGE_CD = 'F2' or ( STAGE_CD = 'F1' and STAGE_STTS_CD = 'C'))");

/** Constant Extract Car Series and Buyer Group Code . */
public static final StringBuilder getBuyerGrpCodeDtls = new StringBuilder()
.append(" select distinct spec.CAR_SRS, buyer.BUYER_GRP_CD from ")
.append("MST_OEI_SPEC spec, MST_OEI_BUYER oeibuyer, MST_OSEI_DTL dtl, MST_OSEI osei, MST_BUYER buyer, MST_POR por where ")
.append("por.por_cd = :porCd and spec.POR_CD = :porCd and oeibuyer.POR_CD = :porCd ")
.append("and osei.POR_CD = :porCd and spec.CAR_SRS = :carSrs ") 
.append("and oeibuyer.OEI_SPEC_ID = spec.OEI_SPEC_ID and osei.OEI_BUYER_ID = oeibuyer.OEI_BUYER_ID ")
.append("and buyer.BUYER_CD = oeibuyer.BUYER_CD and buyer.PROD_REGION_CD = por.PROD_REGION_CD ")
.append("and dtl.POR_CD = :porCd and spec.PROD_STAGE_CD IN (:prdStgCd) ") 
.append("and dtl.END_ITM_STTS_CD IN ('20','30') ") 
.append("and dtl.OSEI_ADPT_DATE <= :maxprodMonth ") 
.append("and dtl.OSEI_SUSPENDED_DATE > :OTBM") ; 

/** Constant Extract NSC, RHQ, Exporter flags for given POR */
public static final StringBuilder getNSCRHQEXPFlagDtls = new StringBuilder()
      .append("SELECT m.porCd,m.exptrFlg, m.nscFlg,m.ordrTrnsmssnFlg,m.rhqFlg,m.stgCd,m.stgSttsCd FROM MstLckCnfgurtn m where m.porCd = :porCd and m.stgCd = :stageCd and m.stgSttsCd= :stageStatusCd");

/** Constant update system lock Flag details in Monthly order take base period Mst table */
public static final StringBuilder updateSysLckMntlyOrdrTkBsPrd = new StringBuilder()
     .append("update MstMnthOrdrTakeBasePd m set m.sysLckSttsCd = :stageStatusCd, m.updtdBy=:updtBy, m.updtdDt=sysdate where m.id.porCd = :porCd and m.id.ordrTakeBaseMnth=:OTBM and m.stageCd=:stageCd");

/** Constant update stage code and stage status code details in Monthly order take base period Mst table */
public static final StringBuilder updateStgStsCdMntlyOrdrTkBsPrd = new StringBuilder()
       .append("update MstMnthOrdrTakeBasePd m set m.stageCd = :stageCd, m.stageSttsCd=:stageStatusCd, m.updtdBy=:updtBy, m.updtdDt=sysdate  where m.id.porCd = :porCd and m.id.ordrTakeBaseMnth=:OTBM");

/**
 * Instantiates a new b000021 query constants.
 */
private QueryConstants() {
	
}

}


