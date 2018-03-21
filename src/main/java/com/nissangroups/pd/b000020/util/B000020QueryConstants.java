/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.util;

/**
 * This is class is used to have all the Query Strings for the batch B000020.
 * 
 * @author z011479
 *
 */
public class B000020QueryConstants {

	/**
	 * Constant extract Order_Take_Base_Month query when Production Order Stage
	 */
	public static final StringBuilder extractByrGrpQry = new StringBuilder()
			.append(" SELECT MB.OCF_BUYER_GRP_CD,MB.OCF_REGION_CD,MB.BUYER_GRP_CD FROM MST_BUYER MB ")
			.append(" INNER JOIN MST_POR MP ON MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg AND MP.POR_CD = :porCd");

	public static final StringBuilder andOcfByrCdQry = new StringBuilder()
			.append(" AND MB.OCF_BUYER_GRP_CD = :ocfByrGrpCd ");

	public static final StringBuilder andOcfRgnCdQry = new StringBuilder()
			.append("  AND MB.OCF_REGION_CD = :ocfRgnCd  ");

	public static final StringBuilder andByrGrpCdQry = new StringBuilder()
			.append("  AND MB.BUYER_GRP_CD  = :byrGrpCd");

	public static final StringBuilder extractCrSrsAndHrznQry = new StringBuilder()
			.append(" SELECT DISTINCT MPCS.CAR_SRS, MPCS.CAR_SRS_ORDR_HRZN,MP.POR_HRZN,MPCS.CAR_SRS_ADPT_DATE,MPCS.CAR_SRS_ABLSH_DATE,MB.BUYER_GRP_CD,MO.OSEI_ID")
			.append(",MB.OCF_BUYER_GRP_CD,MB.OCF_REGION_CD FROM MST_POR_CAR_SRS MPCS   ")
			.append(" INNER JOIN MST_POR MP ON MPCS.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.POR_CD =  MP.POR_CD ")
			.append(" AND MPCS.CAR_SRS = MOS.CAR_SRS AND MPCS.PROD_FMY_CD = MOS.PROD_FMY_CD ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID ")
			.append(" INNER JOIN MST_OSEI MO ON MO.POR_CD = MOB.POR_CD AND MO.OEI_BUYER_ID = MOB.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" INNER JOIN TRN_MNTHLY_ORDR TMO ON TMO.POR_CD =  MOS.POR_CD AND MO.OSEI_ID = TMO.OSEI_ID")
			.append(" WHERE MP.POR_CD = :porCd  AND TMO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append("  AND MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg  AND MPCS.CAR_SRS_ADPT_DATE <= :prdMnthfrm ");

	public static final StringBuilder andHrznQryAndCarSrs = new StringBuilder()
			.append("AND MPCS.CAR_SRS = :carSrs ");

	public static final StringBuilder extractMstSpecDtls = new StringBuilder()
			.append(" SELECT  DISTINCT TMO.POR_CD,TMO.OSEI_ID,MB.OCF_REGION_CD,TMO.PROD_MNTH,MB.BUYER_GRP_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID,")
			.append(" MOB.OEI_SPEC_ID,MO.EXT_CLR_CD,MO.INT_CLR_CD,MB.BUYER_CD,MP.PROD_REGION_CD,MB.OCF_BUYER_GRP_CD FROM  MST_OEI_SPEC MOS  ")
			.append(" INNER JOIN MST_POR MP ON MOS.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_POR_CAR_SRS MPCS  ON MPCS.POR_CD = MP.POR_CD AND MOS.CAR_SRS =  MPCS.CAR_SRS ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID ")
			.append(" INNER JOIN MST_OSEI MO ON MO.POR_CD = MOB.POR_CD AND MO.OEI_BUYER_ID = MOB.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" INNER JOIN TRN_MNTHLY_ORDR TMO ON TMO.POR_CD =  MOS.POR_CD AND MO.OSEI_ID = TMO.OSEI_ID")
			.append(" WHERE  TMO.PROD_MNTH >= :prdMnthfrm AND TMO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMO.PROD_ORDR_STAGE_CD  = :prdStgCd ")
			.append(" AND TMO.SUSPENDED_ORDR_FLAG = :suspndOrdFlg AND MOS.POR_CD = :porCd")
			.append(" AND TMO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append(" AND MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg ");
	public static final StringBuilder extractMstSpecDtlsPrdMnthTo = new StringBuilder()
			.append(" AND TMO.PROD_MNTH <= :prdMnthTo");

	public static final StringBuilder andExtractMstSpecDtlsHrznQryAndCarSrs = new StringBuilder()
			.append("AND MOS.CAR_SRS = :carSrs ");

	public static final StringBuilder extractNscFrcstVolDtls = new StringBuilder()
			.append(" SELECT TMFV.POR_CD,TMFV.PROD_MNTH,TMFV.BUYER_GRP_CD,TMFV.CAR_SRS,TMFV.ORDR_TAKE_BASE_MNTH,TMFV.FRCST_VOL FROM TRN_NSC_FRCST_VOL TMFV ")
			.append(" WHERE TMFV.POR_CD = :porCd AND TMFV.BUYER_GRP_CD = :byrGrpCd AND TMFV.CAR_SRS = :carSrs ")
			.append(" AND TMFV.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth AND TMFV.PROD_MNTH = :prdMnth ");

	public static final StringBuilder extractByrGrpOcfDtls = new StringBuilder()
			.append(" SELECT TBGM.POR_CD,TBGM.BUYER_GRP_CD,TBGM.CAR_SRS,TBGM.PROD_MNTH,TBGM.BUYER_GRP_OCF_LMT_QTY FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT TBGM  ")
			.append(" WHERE TBGM.POR_CD = :porCd AND TBGM.BUYER_GRP_CD = :byrGrpCd AND TBGM.CAR_SRS = :carSrs ")
			.append(" AND TBGM.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth AND TBGM.PROD_MNTH = :prdMnth ")
			.append(" AND TBGM.OCF_FRME_CD = :ocfFrameCD ");

	public static final StringBuilder extractIdlMxPr = new StringBuilder()
			.append(" SELECT MIMP.POR_CD,MIMP.CAR_SRS,MIMP.BUYER_GRP_CD,")
			.append("MIMP.CLR_RATIO_PRITY_FLAG,MIMP.EIM_RATIO_PRITY_FLAG")
			.append(" FROM  MST_IDEAL_MIX_PRITY MIMP")
			.append(" WHERE MIMP.POR_CD = :porCd  ")
			.append(" AND   MIMP.BUYER_GRP_CD = :byrGrpCd ")
			.append(" AND   MIMP.CAR_SRS = :carSrs ");

	public static final StringBuilder extractIdlMxFrcstVol = new StringBuilder()
			.append(" SELECT MIMFR.POR_CD,MIMFR.PROD_MNTH,MIMFR.OEI_BUYER_ID,MIMFR.IDEAL_MIX_FRCST_VOL ")
			.append(" FROM MST_IDEAL_MIX_FRCST_RATIO MIMFR  ")
			.append(" WHERE MIMFR.POR_CD = :porCd AND MIMFR.PROD_MNTH = :prdMnth ")
			.append(" AND MIMFR.OEI_BUYER_ID = :oeiByrId");

	public static final StringBuilder extractIdlMxVol = new StringBuilder()
			.append(" SELECT MIMR.POR_CD,MIMR.OEI_BUYER_ID,MIMR.IDEAL_MIX_FRCST_VOL ")
			.append(" FROM MST_IDEAL_MIX_RATIO MIMR  ")
			.append(" WHERE MIMR.POR_CD = :porCd  ")
			.append(" AND MIMR.OEI_BUYER_ID = :oeiByrId");

	/**
	 * 
	 */
	public static final StringBuilder extractIdlMxFrcstByrGrpCdLvl = new StringBuilder()
			.append(" select SUM(MIMFR.IDEAL_MIX_FRCST_VOL) from MST_IDEAL_MIX_FRCST_RATIO MIMFR where MIMFR.POR_CD = :porCd AND MIMFR.PROD_MNTH = :prdMnth AND MIMFR.OEI_BUYER_ID in  ")
			.append(" (select OEI_BUYER_ID from MST_OEI_BUYER MOB where MOB.POR_CD =:porCd AND  MOB.BUYER_CD in   ")
			.append(" (select MB.BUYER_CD from mst_buyer MB where MB.buyer_grp_cd = :byrGrpCd AND MB.PROD_REGION_CD = :prdRgnCd)) ");
	
	/**
	 * 
	 */
	public static final StringBuilder extractIdlMxByrGrpCdLvl = new StringBuilder()
			.append(" select SUM(MIMFR.IDEAL_MIX_FRCST_VOL) from MST_IDEAL_MIX_RATIO MIMFR where MIMFR.POR_CD = :porCd  AND MIMFR.OEI_BUYER_ID in  ")
			.append(" (select OEI_BUYER_ID from MST_OEI_BUYER MOB where MOB.POR_CD =:porCd AND  MOB.BUYER_CD in   ")
			.append(" (select MB.BUYER_CD from mst_buyer MB where MB.buyer_grp_cd = :byrGrpCd AND MB.PROD_REGION_CD = :prdRgnCd)) ");

	/**
	 * 
	 */
	public static final StringBuilder extractNMnthprdMnthQty = new StringBuilder()
			.append(" SELECT SUM(TMPO.&1) AS ORDR_QTY,MO.POR_CD,MO.OEI_BUYER_ID,MOS.CAR_SRS,MB.BUYER_GRP_CD FROM TRN_MNTHLY_ORDR TMPO  ")
			.append(" INNER JOIN MST_OSEI MO ON TMPO.POR_CD = MO.POR_CD    ")
			.append(" AND TMPO.OSEI_ID = MO.OSEI_ID  ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID AND MOB.POR_CD = MO.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.OEI_SPEC_ID = MOB.OEI_SPEC_ID AND MOS.POR_CD = MOB.POR_CD ")
			.append(" INNER JOIN MST_POR MP ON MOB.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TMPO.POR_CD = :porCd ")
			.append(" AND TMPO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMPO.PROD_MNTH <= :prdMnth AND MO.OEI_BUYER_ID   = :oeiByrId AND MB.BUYER_GRP_CD  = :byrGrpCd ")
			.append(" AND PROD_ORDR_STAGE_CD =:prdStgCd AND MOS.CAR_SRS = :carSrs GROUP BY MO.POR_CD,MO.OEI_BUYER_ID,MOS.CAR_SRS,MB.BUYER_GRP_CD ");

	public static final StringBuilder extractCountOeiByrIdLvl = new StringBuilder()
			.append(" SELECT  DISTINCT TMO.POR_CD,TMO.OSEI_ID,MB.OCF_REGION_CD,TMO.PROD_MNTH,MB.BUYER_GRP_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID,")
			.append(" MOB.OEI_SPEC_ID,MO.EXT_CLR_CD,MO.INT_CLR_CD,MB.BUYER_CD,MP.PROD_REGION_CD FROM  MST_OEI_SPEC MOS  ")
			.append(" INNER JOIN MST_POR MP ON MOS.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_POR_CAR_SRS MPCS  ON MPCS.POR_CD = MP.POR_CD AND MOS.CAR_SRS =  MPCS.CAR_SRS ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID ")
			.append(" INNER JOIN MST_OSEI MO ON MO.POR_CD = MOB.POR_CD AND MO.OEI_BUYER_ID = MOB.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" INNER JOIN TRN_MNTHLY_ORDR TMO ON TMO.POR_CD =  MOS.POR_CD AND MO.OSEI_ID = TMO.OSEI_ID")
			.append(" WHERE  TMO.PROD_MNTH = :prdMnth AND TMO.PROD_ORDR_STAGE_CD  = :prdStgCd ")
			.append(" AND TMO.SUSPENDED_ORDR_FLAG = :suspndOrdFlg AND MOS.POR_CD = :porCd")
			.append(" AND TMO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append(" AND MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg ");

	public static final StringBuilder extractAllByrGrpFrmTempTbl = new StringBuilder()
			.append(" SELECT  POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,SUM(ALLOCATED_ORDR_QTY) ")
			.append(" FROM DEV_B000020 DB WHERE ")
			.append(" DB.PRCSS_FLG IN (:param1,:param2) GROUP BY ")
			.append(" POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD ");

	public static final StringBuilder extractAllcordrQtyByrGrpCdLVl = new StringBuilder()
			.append(" SELECT POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,ALLOCATED_ORDR_QTY,OEI_BUYER_ID,IDEAL_MIX_RATIO,PRCSS_FLG ")
			.append(" FROM DEV_B000020 WHERE POR_CD = :porCd  ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS =:carSrs  ")
			.append(" AND BUYER_GRP_CD = :byrGrpCd ORDER BY ALLOCATED_ORDR_QTY DESC ");

	public static final StringBuilder extractIdlMxClrRatio = new StringBuilder()
			.append(" SELECT MIMCR.POR_CD,MIMCR.IDEAL_MIX_CLR_RATIO,(MIMCR.IDEAL_MIX_CLR_RATIO*1000) AS VOLUME, ")
			.append(" MIMCR.EXT_CLR_CD,MIMCR.INT_CLR_CD,MIMCR.BUYER_GRP_CD FROM MST_IDEAL_MIX_CLR_RATIO MIMCR  ")
			.append(" WHERE MIMCR.POR_CD = :porCd  ")
			.append(" AND MIMCR.EXT_CLR_CD = :extClr")
			.append(" AND MIMCR.INT_CLR_CD = :intClrCd")
			.append(" AND MIMCR.BUYER_GRP_CD = :byrGrpCd")
			.append(" AND MIMCR.CAR_SRS = :carSrs");
	public static final StringBuilder extractIdlMxFrcstByrGrpCdClrLvl = new StringBuilder()
			.append(" SELECT SUM(IDEAL_MIX_CLR_RATIO *1000)  FROM MST_IDEAL_MIX_CLR_RATIO MCR   ")
			.append(" WHERE MCR.POR_CD =:porCd AND MCR.CAR_SRS =:carSrs AND MCR.BUYER_GRP_CD = :byrGrpCd   AND CONCAT(MCR.EXT_CLR_CD, MCR.INT_CLR_CD) IN ")
			.append(" (SELECT CONCAT(OSEI.EXT_CLR_CD, OSEI.INT_CLR_CD) FROM MST_OSEI OSEI  ")
			.append("WHERE OSEI.OEI_BUYER_ID =  :oeiByrId ) ");

	public static final StringBuilder extractoeiByrIdLvlAllcOrdQty = new StringBuilder()
			.append(" SELECT DB.ALLOCATED_ORDR_QTY FROM DEV_B000020 DB ")
			.append(" WHERE POR_CD = :porCd AND DB.CAR_SRS = :carSrs AND DB.BUYER_GRP_CD = :byrGrpCd  ")
			.append(" AND DB.OEI_BUYER_ID = :oeiByrId AND DB.PROD_MNTH = :prdMnth ");

	public static final StringBuilder extractNMnthprdMnthClrLvlQty = new StringBuilder()
			.append(" SELECT SUM(TMPO.&1) AS ORDR_QTY,MO.POR_CD,MO.OEI_BUYER_ID FROM TRN_MNTHLY_ORDR TMPO  ")
			.append(" INNER JOIN MST_OSEI MO ON TMPO.POR_CD = MO.POR_CD    ")
			.append(" AND TMPO.OSEI_ID = MO.OSEI_ID  ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID AND MOB.POR_CD = MO.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.OEI_SPEC_ID = MOB.OEI_SPEC_ID AND MOS.POR_CD = MOB.POR_CD ")
			.append(" INNER JOIN MST_POR MP ON MOB.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TMPO.POR_CD = :porCd AND TMPO.PROD_ORDR_STAGE_CD = :prdStgCd  AND MOS.CAR_SRS = :carSrs  ")
			.append(" AND TMPO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMPO.PROD_MNTH <= :prdMnth AND MO.OSEI_ID   = :oseiId AND MB.BUYER_GRP_CD =  :byrGrpCd ")
			.append(" GROUP BY MO.POR_CD,MO.OEI_BUYER_ID");

	public static final StringBuilder extractNMnthprdMnthClrLvlAndCarSrsQty = new StringBuilder()
			.append(" SELECT SUM(TMPO.&1) AS ORDR_QTY,MO.POR_CD,MOS.CAR_SRS,MB.BUYER_GRP_CD,MO.OSEI_ID  FROM TRN_MNTHLY_ORDR TMPO  ")
			.append(" INNER JOIN MST_OSEI MO ON TMPO.POR_CD = MO.POR_CD    ")
			.append(" AND TMPO.OSEI_ID = MO.OSEI_ID  ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID AND MOB.POR_CD = MO.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.OEI_SPEC_ID = MOB.OEI_SPEC_ID AND MOS.POR_CD = MOB.POR_CD ")
			.append(" INNER JOIN MST_POR MP ON MOB.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TMPO.POR_CD = :porCd AND TMPO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append(" AND TMPO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMPO.PROD_MNTH <= :prdMnth AND TMPO.OSEI_ID = :oseiId ")
			.append(" AND MOS.CAR_SRS = :carSrs AND  MOS.OEI_SPEC_ID = :oeiSpecId ")
			.append(" GROUP BY MO.POR_CD,MOS.CAR_SRS,MB.BUYER_GRP_CD,MO.OSEI_ID ");

	public static final StringBuilder extractNMnthprdMnthClrAndOeiByrIdLvlQty = new StringBuilder()
			.append(" SELECT SUM(TMPO.ORDR_QTY) AS ORDR_QTY,MO.POR_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID FROM TRN_MNTHLY_ORDR TMPO    ")
			.append(" INNER JOIN MST_OSEI MO ON TMPO.POR_CD = MO.POR_CD    ")
			.append(" AND TMPO.OSEI_ID = MO.OSEI_ID  ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID AND MOB.POR_CD = MO.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.OEI_SPEC_ID = MOB.OEI_SPEC_ID AND MOS.POR_CD = MOB.POR_CD ")
			.append(" INNER JOIN MST_POR MP ON MOB.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TMPO.POR_CD = :porCd AND TMPO.PROD_ORDR_STAGE_CD = :prdStgCd ")
			.append(" AND TMPO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMPO.PROD_MNTH <= :prdMnth AND MOS.CAR_SRS = :carSrs AND   MB.BUYER_GRP_CD = :byrGrpCd AND  MoB.OEI_BUYER_ID   = :oeiByrId ")
			.append(" GROUP BY  MO.POR_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID");

	public static final StringBuilder extractNMnthprdMnthByrGrpLvlQty = new StringBuilder()
			.append(" SELECT SUM(TMPO.&1) AS ORDR_QTY,MO.POR_CD,MOS.CAR_SRS,MB.BUYER_GRP_CD FROM TRN_MNTHLY_ORDR TMPO  ")
			.append(" INNER JOIN MST_OSEI MO ON TMPO.POR_CD = MO.POR_CD    ")
			.append(" AND TMPO.OSEI_ID = MO.OSEI_ID  ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID AND MOB.POR_CD = MO.POR_CD ")
			.append(" INNER JOIN MST_OEI_SPEC MOS ON MOS.OEI_SPEC_ID = MOB.OEI_SPEC_ID AND MOS.POR_CD = MOB.POR_CD ")
			.append(" INNER JOIN MST_POR MP ON MOB.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TMPO.POR_CD = :porCd AND TMPO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append(" AND TMPO.ORDRTK_BASE_MNTH = :ordrTkBsMnth AND TMPO.PROD_MNTH <= :prdMnth AND MB.BUYER_GRP_CD   = :byrGrpCd AND MOS.CAR_SRS = :carSrs ")
			.append(" GROUP BY MO.POR_CD,MOS.CAR_SRS,MB.BUYER_GRP_CD");

	public static final StringBuilder extractAllOseiIdFrmTempTbl = new StringBuilder()
			.append(" SELECT  POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OEI_BUYER_ID,SUM(ALLOCATED_ORDR_QTY) ")
			.append(" FROM DEV_B000020_CLR_LVL DB WHERE ")
			.append(" DB.PRCSS_FLG IN (:param1,:param2) GROUP BY ")
			.append(" POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OEI_BUYER_ID ");

	public static final StringBuilder extractOeiByrIdLvlAllcQty = new StringBuilder()
			.append(" SELECT  SUM(ALLOCATED_ORDR_QTY),POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OEI_BUYER_ID ")
			.append(" FROM DEV_B000020 DB WHERE ")
			.append(" POR_CD = :porCd AND PROD_MNTH = :prdMnth AND CAR_SRS =:carSrs AND BUYER_GRP_CD = :byrGrpCd AND OEI_BUYER_ID = :oeiByrId  ")
			.append(" GROUP BY POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OEI_BUYER_ID ");

	public static final StringBuilder extractAllcordrQtyOeiByrIdLVl = new StringBuilder()
			.append(" SELECT POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,ALLOCATED_ORDR_QTY,OEI_BUYER_ID,IDEAL_MIX_RATIO,PRCSS_FLG,OSEI_ID ")
			.append(" ,OCF_REGION_CD,OCF_BUYER_GRP_CD FROM DEV_B000020_CLR_LVL WHERE POR_CD = :porCd  ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS =:carSrs AND OEI_BUYER_ID = :oeiByrId ")
			.append(" AND BUYER_GRP_CD = :byrGrpCd ORDER BY ALLOCATED_ORDR_QTY DESC ");

	public static final StringBuilder extractCountOseiIdIdLvl = new StringBuilder()
			.append(" SELECT  DISTINCT TMO.POR_CD,TMO.OSEI_ID,MB.OCF_REGION_CD,TMO.PROD_MNTH,MB.BUYER_GRP_CD,MOS.CAR_SRS,MOB.OEI_BUYER_ID,")
			.append(" MOB.OEI_SPEC_ID,MO.EXT_CLR_CD,MO.INT_CLR_CD,MB.BUYER_CD,MP.PROD_REGION_CD FROM  MST_OEI_SPEC MOS  ")
			.append(" INNER JOIN MST_POR MP ON MOS.POR_CD = MP.POR_CD ")
			.append(" INNER JOIN MST_POR_CAR_SRS MPCS  ON MPCS.POR_CD = MP.POR_CD AND MOS.CAR_SRS =  MPCS.CAR_SRS ")
			.append(" INNER JOIN MST_OEI_BUYER MOB ON MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID ")
			.append(" INNER JOIN MST_OSEI MO ON MO.POR_CD = MOB.POR_CD AND MO.OEI_BUYER_ID = MOB.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_BUYER MB ON MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" INNER JOIN TRN_MNTHLY_ORDR TMO ON TMO.POR_CD =  MOS.POR_CD AND MO.OSEI_ID = TMO.OSEI_ID")
			.append(" WHERE  TMO.PROD_MNTH = :prdMnth AND TMO.PROD_ORDR_STAGE_CD  = :prdStgCd ")
			.append(" AND TMO.SUSPENDED_ORDR_FLAG = :suspndOrdFlg AND MOS.POR_CD = :porCd")
			.append(" AND TMO.PROD_ORDR_STAGE_CD = :prdStgCd  ")
			.append(" AND MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg AND MOB.OEI_BYR_ID =:oeiByrId ");

	public static final StringBuilder extractOeiByrIdLvlAllcOrdDtls = new StringBuilder()
			.append(" SELECT SUM()  ")
			.append(" AND MB.NSC_EIM_ODER_HRZN_FLAG !=  :nscEimOrdHrznFlg AND MOB.OEI_BYR_ID =:oeiByrId ");

	public static final StringBuilder extractOseiLvlAllcOrdQty = new StringBuilder()
			.append(" SELECT  POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OEI_BUYER_ID,OSEI_ID,ALLOCATED_ORDR_QTY ")
			.append(" FROM DEV_B000020_CLR_LVL DB ");

	public static final StringBuilder extractAllPotCd = new StringBuilder()
			.append(" SELECT  trim(KEY1),trim(VAL1) ")
			.append(" FROM MST_PRMTR MP ")
			.append(" WHERE trim(MP.PRMTR_CD) = :prmtrCd");

	public static final StringBuilder byrGrpLmtCnd = new StringBuilder()
			.append(" WHERE POR_CD = :porCd ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth ")
			.append(" AND CAR_SRS = :carSrs ")
			.append(" AND OSEI_ID = :oseiId ")
			.append(" AND BUYER_GRP_CD = :byrGrpCd ");

	public static final StringBuilder extractDistinctByrGrpCd = new StringBuilder()
			.append(" SELECT  POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OCF_REGION_CD,OCF_BUYER_GRP_CD ")
			.append(" FROM DEV_B000020_CLR_LVL DB  ")
			.append(" GROUP BY POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OCF_REGION_CD,OCF_BUYER_GRP_CD ");

	public static final StringBuilder extractbyrGrpLvlOcfUsg = new StringBuilder()
			.append(" SELECT SUM(BUYER_OCF_USG_QTY),POR_CD,ORDR_TAKE_BASE_MNTH,FEAT_CD,OCF_FRME_CD,BUYER_GRP_CD,CAR_SRS,FEAT_TYPE_CD,PROD_MNTH ")
			.append(" FROM TRN_BUYER_MNTHLY_OCF_USG  ")
			.append(" WHERE POR_CD =:porCd AND  ORDR_TAKE_BASE_MNTH  = :ordrTkBsMnth AND  CAR_SRS  = :carSrs AND PROD_MNTH = :prdMnth AND BUYER_GRP_CD = :byrGrpCd")
			.append(" GROUP BY POR_CD,ORDR_TAKE_BASE_MNTH,FEAT_CD,OCF_FRME_CD,BUYER_GRP_CD,CAR_SRS,FEAT_TYPE_CD,PROD_MNTH ");

	public static final StringBuilder updateByrGrpMnthlyOcfUsgLmt = new StringBuilder()
			.append(" Update TRN_BUYER_GRP_MNTHLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :byrGrpUsgQty,UPDTD_BY = 'B000020',UPDTD_DT = SYSDATE    ")
			.append(" WHERE POR_CD = :porCd AND  PROD_MNTH = :prdMnth  AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth AND   ")
			.append(" CAR_SRS = :carSrs AND OCF_FRME_CD =:ocfFrameCD AND  BUYER_GRP_CD = :byrGrpCd AND  FEAT_CD = :featCd");

	public static final StringBuilder extractByrGrpMnthlyOcfLmt = new StringBuilder()
			.append(" SELECT SUM(TBG.BUYER_GRP_OCF_USG_QTY),TBG.POR_CD,TBG.ORDR_TAKE_BASE_MNTH,TBG.CAR_SRS,TBG.PROD_MNTH,MB.OCF_REGION_CD   ")
			.append(" ,TBG.FEAT_TYPE_CD,TBG.OCF_FRME_CD,TBG.FEAT_CD,MB.OCF_BUYER_GRP_CD FROM ")
			.append(" TRN_BUYER_GRP_MNTHLY_OCF_LMT TBG   INNER JOIN   ")
			.append(" MST_POR MP ON MP.POR_CD = TBG.POR_CD INNER JOIN ")
			.append(" MST_BUYER MB ON TBG.BUYER_GRP_CD = MB.BUYER_GRP_CD AND MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append(" WHERE TBG.POR_CD = :porCd AND TBG.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth AND ")
			.append(" TBG.PROD_MNTH =:prdMnth  AND TBG.CAR_SRS = :carSrs AND TBG.BUYER_GRP_CD = :byrGrpCd ")
			.append(" GROUP BY TBG.POR_CD,TBG.ORDR_TAKE_BASE_MNTH,TBG.CAR_SRS,TBG.PROD_MNTH,MB.OCF_REGION_CD ")
			.append(" ,TBG.FEAT_TYPE_CD,TBG.OCF_FRME_CD,TBG.FEAT_CD,MB.OCF_BUYER_GRP_CD  ");

	public static final StringBuilder updateRgnlMnthlyLmt = new StringBuilder()
			.append(" UPDATE TRN_REGIONAL_MNTHLY_OCF_LMT SET REGIONAL_OCF_USG_QTY = :rgnlMnthlyOcfLmtQty,UPDTD_BY = 'B000020',UPDTD_DT = SYSDATE ")
			.append(" WHERE POR_CD = :porCd AND PROD_MNTH = :prdMnth AND  ")
			.append(" CAR_SRS = :carSrs AND OCF_REGION_CD =:ocfRgnCd   ")
			.append(" AND OCF_BUYER_GRP_CD = :ocfByrGrpCd AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append("AND OCF_FRME_CD = :ocfFrameCD AND FEAT_CD = :featCd ");

	public static final StringBuilder extractByrGrpMnthlyOcfLmtChck = new StringBuilder()
			.append(" SELECT * FROM  TRN_BUYER_GRP_MNTHLY_OCF_LMT ")
			.append(" WHERE BUYER_GRP_OCF_LMT_QTY <> BUYER_GRP_OCF_USG_QTY AND ")
			.append(" POR_CD = :porCd AND ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth ")
			.append(" AND PROD_MNTH = :prdMnth AND BUYER_GRP_CD = :byrGrpCd ")
			.append(" AND CAR_SRS = :carSrs AND OCF_FRME_CD  = '00' ");

	public static final StringBuilder extractNscFrcstLmtChck = new StringBuilder()
			.append(" SELECT BGM.POR_CD,BGM.CAR_SRS,BGM.ORDR_TAKE_BASE_MNTH, ")
			.append(" BGM.PROD_MNTH,BGM.BUYER_GRP_CD,BGM.FEAT_TYPE_CD,BGM.OCF_FRME_CD,")
			.append(" BGM.FEAT_CD,BGM.BUYER_GRP_OCF_USG_QTY,TNF.FRCST_VOL")
			.append(" FROM  TRN_BUYER_GRP_MNTHLY_OCF_LMT BGM INNER JOIN ")
			.append(" TRN_NSC_FRCST_VOL TNF ON ")
			.append(" BGM.POR_CD = TNF.POR_CD ")
			.append(" AND  BGM.ORDR_TAKE_BASE_MNTH = TNF.ORDR_TAKE_BASE_MNTH")
			.append(" AND  BGM.PROD_MNTH = TNF.PROD_MNTH")
			.append(" AND  BGM.CAR_SRS = TNF.CAR_SRS ")
			.append(" AND BGM.BUYER_GRP_CD = TNF.BUYER_GRP_CD")
			.append(" WHERE BGM.BUYER_GRP_OCF_USG_QTY <> TNF.FRCST_VOL AND")
			.append(" BGM.POR_CD = :porCd AND BGM.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth")
			.append(" AND BGM.PROD_MNTH = :prdMnth AND BGM.BUYER_GRP_CD = :byrGrpCd")
			.append(" AND BGM.CAR_SRS = :carSrs AND BGM.OCF_FRME_CD  = '00' ");
	public static final StringBuilder extractByrGrp = new StringBuilder()
			.append(" SELECT BGM.POR_CD,BGM.CAR_SRS,BGM.ORDR_TAKE_BASE_MNTH, ")
			.append(" BGM.PROD_MNTH,BGM.BUYER_GRP_CD,BGM.FEAT_TYPE_CD,BGM.OCF_FRME_CD,")
			.append(" BGM.FEAT_CD,BGM.BUYER_GRP_OCF_USG_QTY,TNF.FRCST_VOL")
			.append(" FROM  TRN_BUYER_GRP_MNTHLY_OCF_LMT BGM INNER JOIN ")
			.append(" TRN_NSC_FRCST_VOL TNF ON ")
			.append(" BGM.POR_CD = TNF.POR_CD ")
			.append(" AND  BGM.ORDR_TAKE_BASE_MNTH = TNF.ORDR_TAKE_BASE_MNTH")
			.append(" AND  BGM.PROD_MNTH = TNF.PROD_MNTH")
			.append(" AND  BGM.CAR_SRS = TNF.CAR_SRS ")
			.append(" AND BGM.BUYER_GRP_CD = TNF.BUYER_GRP_CD")
			.append(" WHERE BGM.BUYER_GRP_OCF_USG_QTY <> TNF.FRCST_VOL AND")
			.append(" BGM.POR_CD = :porCd AND BGM.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth")
			.append(" AND BGM.PROD_MNTH = :prdMnth AND BGM.BUYER_GRP_CD = :byrGrpCd")
			.append(" AND BGM.CAR_SRS = :carSrs AND BGM.OCF_FRME_CD  = '00' ");

	public static final String MNTHLY_BTCH_STTS_SEQ_ID = "select MNTHLY_BTCH_STTS_SEQ_ID.nextval from dual";

	public static final StringBuilder deleteTmpTbl = new StringBuilder()
			.append(" DELETE FROM DEV_B000020 ");

	public static final StringBuilder deleteTmpTblClrLvl = new StringBuilder()
			.append(" DELETE FROM DEV_B000020_CLR_LVL");

	public static final StringBuilder deleteDailyOcfIf = new StringBuilder()
			.append(" DELETE FROM TRN_DAILY_OCF_LMT_IF WHERE POR_CD = :porCd ");

	public static final StringBuilder deleteMnthlySchdlIf = new StringBuilder()
			.append(" DELETE FROM TRN_MNTH_PROD_SHDL_IF WHERE POR_CD = ?  ");

	public static final StringBuilder deleteDailyOcf = new StringBuilder()
			.append(" DELETE FROM TRN_DAILY_OCF_LMT WHERE POR_CD = :porCd  AND PROD_MNTH = :prdMnth");
	
	private B000020QueryConstants() {
	}

}
