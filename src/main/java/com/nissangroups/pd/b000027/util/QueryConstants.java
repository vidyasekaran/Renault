/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000027.util;

/**
 * Constant file to keep the queries related to the batch B000027. 
 * @author z014433
 *
 */
public class QueryConstants {

	/** Constant Extract Order take base month  */
	public static final StringBuilder extractOrdrTkBsMnth = new StringBuilder()
		.append("select POR_CD, ORDR_TAKE_BASE_MNTH,STAGE_CD,STAGE_STTS_CD,SYS_LCK_STTS_CD from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd and STAGE_CD = :stageCd and STAGE_STTS_CD = 'C'  order by ORDR_TAKE_BASE_MNTH ");
	
	/** Constant Extract car series horizon details  */
	public static final StringBuilder getCarSeriesHorizon = new StringBuilder()
	.append("select m.id.porCd, m.id.carSrs, m.carSrsOrdrHrzn, m.carSrsAdptDate, m.carSrsAblshDate FROM MstPorCarSr m WHERE m.id.porCd= :porCd and m.carSrsAblshDate> :OTBM");
	
	/** Constant Extract order information  */
	public static final StringBuilder EXT_MNTHLY_TRN_SEL_ORDR_QTY_Y = new StringBuilder()
	.append(" select  DISTINCT trn_mnthly_ordr.PROD_MNTH, mst_oei_buyer.BUYER_CD, " +
			"mst_oei_spec.ADTNL_SPEC_CD,MST_OSEI.EXT_CLR_CD,MST_OSEI.INT_CLR_CD,mst_oei_spec.SPEC_DESTN_CD,trn_mnthly_ordr.POT_CD," +
			"trn_mnthly_ordr.POR_CD,MST_OSEI.OEI_BUYER_ID,mst_oei_spec.PROD_FMY_CD,trn_mnthly_ordr.OSEI_ID,trn_mnthly_ordr.ORDRTK_BASE_MNTH," +
			"mst_oei_spec.CAR_SRS,concat(mst_oei_spec.APPLD_MDL_CD,mst_oei_spec.PCK_CD) as END_ITEM," +
			"trn_mnthly_ordr.AUTO_ADJST_ORDR_QTY as QTY,byrgrp.BUYER_GRP_CD,MST_BUYER.OCF_BUYER_GRP_CD");
	
	public static final StringBuilder EXT_MNTHLY_TRN_SEL_ORDR_QTY_N = new StringBuilder()
	.append(" select  DISTINCT trn_mnthly_ordr.PROD_MNTH, mst_oei_buyer.BUYER_CD, " +
			"mst_oei_spec.ADTNL_SPEC_CD,MST_OSEI.EXT_CLR_CD,MST_OSEI.INT_CLR_CD,mst_oei_spec.SPEC_DESTN_CD,trn_mnthly_ordr.POT_CD," +
			"trn_mnthly_ordr.POR_CD,MST_OSEI.OEI_BUYER_ID,mst_oei_spec.PROD_FMY_CD,trn_mnthly_ordr.OSEI_ID,trn_mnthly_ordr.ORDRTK_BASE_MNTH," +
			"mst_oei_spec.CAR_SRS,concat(mst_oei_spec.APPLD_MDL_CD,mst_oei_spec.PCK_CD) as END_ITEM," +
			" trn_mnthly_ordr.ORDR_QTY as QTY,byrgrp.BUYER_GRP_CD,MST_BUYER.OCF_BUYER_GRP_CD");
	
	public static final StringBuilder EXT_MNTHLY_TRN_MAIN_PART = new StringBuilder()
 	.append(" from trn_mnthly_ordr "	+
	" INNER join mst_osei_dtl on trn_mnthly_ordr.osei_id = mst_osei_dtl.osei_id " +
    " and trn_mnthly_ordr.por_cd = mst_osei_dtl.por_cd " +
	" inner join MST_OSEI on  mst_osei_dtl.osei_id = MST_OSEI.osei_id " +
	" and mst_osei_dtl.POR_CD = MST_OSEI.POR_CD "+
	" inner join mst_oei_buyer on  MST_OSEI.por_cd = mst_oei_buyer.por_cd "+
     " and MST_OSEI.oei_buyer_id = mst_oei_buyer.oei_buyer_id "+ 
     " INNER join mst_oei_spec on mst_oei_buyer.por_cd = mst_oei_spec.por_cd "+
     " and mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id "+
     " INNER JOIN  MST_POR ON mst_oei_buyer.por_cd = MST_POR.POR_CD "+
     " INNER JOIN MST_BUYER ON MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD "+
     " AND mst_oei_buyer.BUYER_CD = MST_BUYER.BUYER_CD  "+
     "INNER JOIN MST_BUYER_GRP byrgrp ON byrgrp.BUYER_GRP_CD = MST_BUYER.BUYER_GRP_CD "
	);
	
	public static final StringBuilder EXT_MNTHLY_TRN_WHR_CLAUSE_Y = new StringBuilder()
	.append(" where trn_mnthly_ordr.SUSPENDED_ORDR_FLAG in('0','1') ");
	
	public static final StringBuilder EXT_MNTHLY_TRN_WHR_CLAUSE_N = new StringBuilder()
	.append(" where trn_mnthly_ordr.SUSPENDED_ORDR_FLAG = '0'  "+
			"and mst_oei_spec.CAR_SRS = :carSrs "+
			"and trn_mnthly_ordr.PROD_MNTH in (:ProdMonth)"+
			"and trn_mnthly_ordr.PROD_MNTH >= substr(MST_OSEI_DTL.OSEI_ADPT_DATE,0,6)"+
			"and trn_mnthly_ordr.PROD_MNTH < mst_osei_dtl.OSEI_SUSPENDED_DATE "
			);
	
	public static final StringBuilder EXT_MNTHLY_TRN_WHR_CLAUSE = new StringBuilder()
	.append(" and trn_mnthly_ordr.PROD_MNTH <= :maxprodMonth and trn_mnthly_ordr.PROD_ORDR_STAGE_CD = :prdOrdrStgCd and trn_mnthly_ordr.ORDRTK_BASE_MNTH = :OTBM and trn_mnthly_ordr.POR_CD = :porCd ");
	
	/** Constant Extract Export number details  */
	public static final StringBuilder getExNoDtls = new StringBuilder()
	.append("select m.exNo FROM MstExNo m WHERE m.id.porCd= :porCd and m.id.prodMnth= :prdMnth and m.id.oeiBuyerId= :ukOeiBuyerID and m.id.potCd= :potCd");
	
	/** Constant Extract Tyre Maker details  */
	public static final StringBuilder getTyreMkrDtls = new StringBuilder()
	.append("select m.tyreMkrCd FROM MstTypeMkrConv m WHERE m.adtnlSpecCd= :additionalSpecCd");
	
	/** Constant Extract Body protection code details  */
	public static final StringBuilder getBdyPrtnCdDtls = new StringBuilder()
	.append("select m.bdyPrtctnCd FROM MstBdyPrtctnConv m WHERE m.adtnlSpecCd= :additionalSpecCd");
	
	public static final String TRN_MNTHLY_PRDN_ORDR_SEQ_ID = "select TRN_MNTHLY_PROD_ORDR_SEQ_ID.nextval from dual"; 
	
	public static final StringBuilder getMapsSymbol = new StringBuilder()
	.append( " SELECT MST_BUYER.BUYER_GRP_CD ")
	.append( " , OCF_REGION_CD ")
	.append( " FROM MST_BUYER, MST_POR ")
	.append( " WHERE MST_POR.POR_CD = :porCd ") 
	.append( " AND MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD ")
	.append( " AND MST_BUYER.BUYER_CD = :buyerCd ");
	
	/** Constant Extract Existing Production order Details  */
	public static final StringBuilder getExtngPrdnOrdr = new StringBuilder()
	.append( " select POR_CD,PROD_ORDR_NO,OSEI_ID,PROD_MNTH,POT_CD,SLS_NOTE_NO ")
	.append( " from TRN_MNTHLY_PROD_ORDR where por_cd = :porCd and ORDRTK_BASE_MNTH=:OTBM ")
	.append( "union")
	.append( " select POR_CD,PROD_ORDR_NO,OSEI_ID,PROD_MNTH,POT_CD,SLS_NOTE_NO  ") 
	.append( " from TRN_LTST_MST_SHDL  where por_cd = :porCd and PROD_MNTH>=:OTBM ");
	
	/** Constant Extract Service Parameter details  */
	public static final StringBuilder getSrvcPrmtrDtls = new StringBuilder()
	.append( "SELECT SPEC_DESTN_CD,DEALER_LST,OWNR_MNL,WRNTY_BKLT,trim(UNQUE_SEQ_CD) FROM MST_SERV_PRMTR ")
	.append( "where trim(POR_CD) = trim(:porCd) AND trim(PROD_FMY_CD) = trim(:prodFmyCd) ")
	.append( "and (:buyerCd in (trim(BUYER_CD_CNDTN))  or (trim(BUYER_CD_CNDTN)) is null) ")
	.append( "and PROD_MNTH_FRM < = :prdMnth and ")
	.append( "(PROD_MNTH_TO >= :prdMnth or PROD_MNTH_TO is null) ")
	.append( "and :prefix not like NVL2(REPLACE(PRFX_NO, ' ', ''), REPLACE(PRFX_NO, ' ', '_'), ' ') ")
	.append( "AND  ( (:prefix like REPLACE(PRFX_YES, ' ', '_') or trim(PRFX_YES) is NULL) ")
	.append( "and  (:suffix like REPLACE(SFFX_YES, ' ', '_') or trim(SFFX_YES) is null)) ") 
	.append( "AND   :suffix not like NVL2(REPLACE(SFFX_NO, ' ', ''), REPLACE(SFFX_NO, ' ', '_'), ' ') ")
	.append( "and (trim(:specDestinationCd) in (trim(SPEC_DESTN_CD))  or (trim(SPEC_DESTN_CD)) is null)");
	
	/** Constant delete existing order informations  */
	public static final StringBuilder deleteExtngOrdrDtls = new StringBuilder()
	.append(" DELETE FROM TRN_MNTHLY_PROD_ORDR where por_cd= :porCd and ORDRTK_BASE_MNTH=:OTBM ");
	
	/** Constant Extract Production order Details for Monthly Production order detail report*/
	public static final StringBuilder getMnthlyPrdnOrdrRptDtls = new StringBuilder()
	.append( "SELECT tmpo.ORDRTK_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,")
	.append( "TMPO.PROD_MNTH,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,")
	.append( "mpcs.CAR_GRP,oeispec.CAR_SRS,oeispec.SPEC_DESTN_CD,")
	.append( "concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),TMPO.EX_NO,")
	.append( "oeispec.ADTNL_SPEC_CD,TMPO.POT_CD,TMPO.SLS_NOTE_NO,")
	.append( "concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),SUM(TMPO.ORDR_QTY),")
	.append( "TMPO.TYRE_MKR_CD,concat(concat(TMPO.DEALER_LST,TMPO.OWNR_MNL),TMPO.WRNTY_BKLT),")
	.append( "TMPO.BDY_PRTCTN_CD,LISTAGG(optn.OPTN_SPEC_CODE, ' ') WITHIN GROUP (ORDER BY optn.OPTN_SPEC_CODE) AS optionCd ")
	.append( "FROM TRN_MNTHLY_PROD_ORDR TMPO ")
	.append( "INNER JOIN MST_OSEI osei ON TMPO.osei_id = osei.osei_id AND TMPO.POR_CD = osei.POR_CD ")
	.append( "INNER JOIN mst_oei_buyer oeibyr ON osei.por_cd = oeibyr.por_cd AND osei.oei_buyer_id = oeibyr.oei_buyer_id ")
	.append( "INNER JOIN mst_oei_spec oeispec ON oeibyr.por_cd = oeispec.por_cd AND oeibyr.oei_spec_id = oeispec.oei_spec_id ")
	.append( "INNER JOIN MST_POR por ON oeibyr.por_cd = por.POR_CD ")
	.append( "INNER JOIN MST_BUYER byr ON byr.PROD_REGION_CD = por.PROD_REGION_CD AND oeibyr.BUYER_CD   = byr.BUYER_CD ")
	.append( "INNER JOIN MST_BUYER_GRP byrgrp ON byrgrp.BUYER_GRP_CD = byr.BUYER_GRP_CD ")
	.append( "INNER JOIN MST_POR_CAR_SRS mpcs ON oeispec.CAR_SRS = mpcs.CAR_SRS AND mpcs.POR_CD = oeispec.POR_CD AND mpcs.PROD_FMY_CD = oeispec.PROD_FMY_CD ")
	.append( "INNER JOIN MST_OEI_BUYER_OPTN_SPEC_CD optn ON optn.OEI_BUYER_ID = oeibyr.OEI_BUYER_ID ")
	.append( "WHERE TMPO.POR_CD = :porCd AND TMPO.ORDRTK_BASE_MNTH = :OTBM ")
	.append( "GROUP BY TMPO.POR_CD,tmpo.ORDRTK_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD, ")
	.append( "byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,oeispec.CAR_SRS, ")
	.append( "oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD), ")
	.append( "oeispec.ADTNL_SPEC_CD,TMPO.POT_CD,concat(osei.EXT_CLR_CD, osei.INT_CLR_CD), ")
	.append( "TMPO.PROD_MNTH,TMPO.EX_NO,TMPO.SLS_NOTE_NO,TMPO.ORDR_QTY,")
	.append( "TMPO.TYRE_MKR_CD,concat(concat(TMPO.DEALER_LST,TMPO.OWNR_MNL),TMPO.WRNTY_BKLT), ")
	.append( " TMPO.BDY_PRTCTN_CD,mpcs.CAR_GRP,TMPO.OSEI_ID ");
	
	
	/** Constant Extract Production order Details for Monthly Production order detail report*/
	public static final StringBuilder ordrSpecRptRcds = new StringBuilder()
	.append( "SELECT TMO.ORDRTK_BASE_MNTH, byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,")
	.append( "oeispec.CAR_SRS,mpcs.CAR_GRP,oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),oeispec.ADTNL_SPEC_CD,")
	.append( "TMO.POT_CD,concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),TMO.PROD_MNTH,TMO.OSEI_ID,SUM(TMO.ORDR_QTY) ")
	.append( "FROM TRN_MNTHLY_ORDR TMO ")
	.append( "INNER join mst_osei_dtl dtl on TMO.osei_id = dtl.osei_id and TMO.por_cd = dtl.por_cd ")
	.append( "INNER JOIN MST_OSEI osei ON TMO.osei_id = osei.osei_id AND TMO.POR_CD = osei.POR_CD ")
	.append( "INNER JOIN mst_oei_buyer oeibyr ON osei.por_cd = oeibyr.por_cd AND osei.oei_buyer_id = oeibyr.oei_buyer_id ")
	.append( "INNER JOIN mst_oei_spec oeispec ON oeibyr.por_cd = oeispec.por_cd AND oeibyr.oei_spec_id = oeispec.oei_spec_id ")
	.append( "INNER JOIN MST_POR por ON oeibyr.por_cd = por.POR_CD ")
	.append( "INNER JOIN MST_BUYER byr ON byr.PROD_REGION_CD = por.PROD_REGION_CD AND oeibyr.BUYER_CD   = byr.BUYER_CD ")
	.append( "INNER JOIN MST_BUYER_GRP byrgrp ON byrgrp.BUYER_GRP_CD = byr.BUYER_GRP_CD ")
	.append( "INNER JOIN MST_POR_CAR_SRS mpcs ON oeispec.CAR_SRS = mpcs.CAR_SRS AND mpcs.POR_CD = oeispec.POR_CD AND mpcs.PROD_FMY_CD = oeispec.PROD_FMY_CD ")
	.append( "WHERE TMO.POR_CD = :porCd AND TMO.ORDRTK_BASE_MNTH = :OTBM and TMO.ORDR_QTY > 0 and ")
	.append( "((TMO.SUSPENDED_ORDR_FLAG = 1) or (mpcs.CAR_SRS not in (:carSrs) and TMO.SUSPENDED_ORDR_FLAG = 0)) ")
	.append( "GROUP BY tmo.ORDRTK_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,oeispec.CAR_SRS,")
	.append( "mpcs.CAR_GRP,oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),oeispec.ADTNL_SPEC_CD,TMO.POT_CD,")
	.append( "concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),TMO.PROD_MNTH,TMO.OSEI_ID ")
	.append("union ")
	.append( "SELECT TMO.ORDRTK_BASE_MNTH, byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,")
	.append( "oeispec.CAR_SRS,mpcs.CAR_GRP,oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),oeispec.ADTNL_SPEC_CD,")
	.append( "TMO.POT_CD,concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),TMO.PROD_MNTH,TMO.OSEI_ID,SUM(TMO.ORDR_QTY) ")
	.append( "FROM TRN_MNTHLY_ORDR TMO ")
	.append( "INNER join mst_osei_dtl dtl on TMO.osei_id = dtl.osei_id and TMO.por_cd = dtl.por_cd ")
	.append( "INNER JOIN MST_OSEI osei ON TMO.osei_id = osei.osei_id AND TMO.POR_CD = osei.POR_CD ")
	.append( "INNER JOIN mst_oei_buyer oeibyr ON osei.por_cd = oeibyr.por_cd AND osei.oei_buyer_id = oeibyr.oei_buyer_id ")
	.append( "INNER JOIN mst_oei_spec oeispec ON oeibyr.por_cd = oeispec.por_cd AND oeibyr.oei_spec_id = oeispec.oei_spec_id ")
	.append( "INNER JOIN MST_POR por ON oeibyr.por_cd = por.POR_CD ")
	.append( "INNER JOIN MST_BUYER byr ON byr.PROD_REGION_CD = por.PROD_REGION_CD AND oeibyr.BUYER_CD   = byr.BUYER_CD ")
	.append( "INNER JOIN MST_BUYER_GRP byrgrp ON byrgrp.BUYER_GRP_CD = byr.BUYER_GRP_CD ")
	.append( "INNER JOIN MST_POR_CAR_SRS mpcs ON oeispec.CAR_SRS = mpcs.CAR_SRS AND mpcs.POR_CD = oeispec.POR_CD AND mpcs.PROD_FMY_CD = oeispec.PROD_FMY_CD ")
	.append("WHERE TMO.POR_CD = :porCd AND TMO.ORDRTK_BASE_MNTH = :OTBM and TMO.ORDR_QTY > 0 and TMO.SUSPENDED_ORDR_FLAG = 0 and ")
	.append("(TMO.PROD_MNTH not in (:ProdMonth) and mpcs.CAR_SRS = :carSeries and oeispec.CAR_SRS= :carSeries) ")
	.append( "GROUP BY tmo.ORDRTK_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,oeispec.CAR_SRS,")
	.append( "mpcs.CAR_GRP,oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),oeispec.ADTNL_SPEC_CD,TMO.POT_CD,")
	.append( "concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),TMO.PROD_MNTH,TMO.OSEI_ID ");
	
	
	/** Constant delete temp table */
	public static final StringBuilder deleteTempTbl = new StringBuilder()
	.append(" truncate table DEV_TRN_MNTHLY_PROD_ORDR ");

	/** Constant Get Production order details from DEV table */
	public static final StringBuilder getDraftPrdnOrdrDtls = new StringBuilder()
	.append(" select distinct PROD_MNTH, BUYER_CD,concat(APPLD_MDL_CD,pck_cd),SPEC_DESTN_CD,concat(EXT_CLR_CD,int_clr_cd),"
			+ "ADTNL_SPEC_CD,POT_CD,POR_CD,BUYER_GRP_CD,OSEI_ID,CAR_SRS,ORDR_QTY,OCF_REGION_CD,OCF_BUYER_GRP_CD from DEV_TRN_MNTHLY_PROD_ORDR where por_cd= :porCd and ORDRTK_BASE_MNTH= :OTBM "); 
	
	/** Constant attach feature code, usage for given buyer group code */
	public static final StringBuilder getFtreCdUsageDtls = new StringBuilder()
	.append(" select BUYER_GRP_CD,PROD_MNTH,CAR_SRS,ocf_frme_cd,feat_cd,buyer_ocf_usg_qty from trn_buyer_mnthly_ocf_usg where por_cd= :porCd and ordr_take_base_mnth= :OTBM and prod_mnth= :ProdMonth "
			+ "and osei_id= :ukOseiID and buyer_grp_cd= :byrGrpCd and car_srs = :carSrs");
	
	/** query Constant update feature code . */
	public static final StringBuilder updateFtreCdInDrft = new StringBuilder()
			.append("UPDATE DEV_TRN_MNTHLY_PROD_ORDR set OCF_FRME_CD = :frameCd, FEAT_CD= :ftreCd ")
			.append(" where ORDRTK_BASE_MNTH= :OTBM and POR_CD= :porCd and PROD_MNTH= :ProdMonth and osei_id= :ukOseiID and pot_cd= :potCd and BUYER_GRP_CD= :byrGrpCd "); 
	
	/** Constant get buyer group ocf limit details */
	public static final StringBuilder getByrGrpOCFLmtDtls = new StringBuilder()
	.append( "SELECT LMT.ORDR_TAKE_BASE_MNTH, LMT.PROD_MNTH,LMT.CAR_SRS,LMT.BUYER_GRP_CD, byr.OCF_REGION_CD,LMT.OCF_FRME_CD,LMT.FEAT_CD,"
			+ "ftre.FEAT_LNG_DESC,ftre.FEAT_SHRT_DESC,LMT.BUYER_GRP_OCF_LMT_QTY ")
	.append("FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT LMT ")
	.append("INNER JOIN MST_POR por ON lmt.por_cd = por.POR_CD ")
	.append("INNER JOIN MST_BUYER byr ON byr.PROD_REGION_CD = por.PROD_REGION_CD and LMT.BUYER_GRP_CD=byr.BUYER_GRP_CD ") 
	.append("INNER JOIN MST_FEAT ftre ON LMT.FEAT_CD = ftre.FEAT_CD and LMT.FEAT_TYPE_CD=ftre.FEAT_TYPE_CD and LMT.POR_CD=ftre.POR_CD and LMT.CAR_SRS=ftre.CAR_SRS and LMT.OCF_FRME_CD=ftre.OCF_FRME_CD ")
	.append("and ftre.OCF_BUYER_GRP_CD=byr.OCF_BUYER_GRP_CD and ftre.OCF_REGION_CD=byr.OCF_REGION_CD ")
	.append( "where lmt.por_cd= :porCd and LMT.ORDR_TAKE_BASE_MNTH= :OTBM ");
	

	/** Constant get buyer group ocf limit details */
	public static final StringBuilder getOCFUsgDtls = new StringBuilder()
	.append("select ORDRTK_BASE_MNTH,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OCF_REGION_CD,OCF_FRME_CD,FEAT_CD,sum(ORDR_QTY) ")
	.append("from DEV_TRN_MNTHLY_PROD_ORDR where por_cd= :porCd and ORDRTK_BASE_MNTH= :OTBM ")
	.append( "group by ORDRTK_BASE_MNTH,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OCF_REGION_CD,OCF_FRME_CD,FEAT_CD ");
	
	
	public static final StringBuilder fetchOcfBrchRcrds = new StringBuilder()
	.append( " SELECT TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD ")
	.append( " , MST_FEAT.OCF_REGION_CD ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD ")
	.append( " , MST_FEAT.FEAT_CD ")
	.append( " , MST_FEAT.FEAT_SHRT_DESC ")
	.append( " , MST_FEAT.FEAT_LNG_DESC ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY ")
	.append( " , TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY ")
	.append( " , (TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_LMT_QTY - TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_OCF_USG_QTY) as DIFFERENCE ")
	.append( " FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT, MST_FEAT ")
	.append( " WHERE TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD = :porCd ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = :OTBM ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH = :prdMnth ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = :carSrs ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD = :byrGrpCd ")
	.append( " AND MST_FEAT.POR_CD = TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS = MST_FEAT.CAR_SRS ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD = MST_FEAT.FEAT_CD ")
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_TYPE_CD = MST_FEAT.FEAT_TYPE_CD ")
	.append( " AND MST_FEAT.OCF_REGION_CD = :ocfRgnCd ")
	.append( " AND MST_FEAT.OCF_BUYER_GRP_CD = :ocfByrGrpCd ") ;
	
	public static final StringBuilder fetchOcfBrchRcrdsApndFtreCd = new StringBuilder()
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.FEAT_CD in (:ftreCd) ");
	
	public static final StringBuilder fetchOcfBrchRcrdsApndFrmeCd = new StringBuilder()
	.append( " AND TRN_BUYER_GRP_MNTHLY_OCF_LMT.OCF_FRME_CD in (:frameCd) ");
	
	public static final StringBuilder fetchExtngRcds = new StringBuilder()
	.append( " select * from TRN_MNTHLY_PROD_ORDR where POR_CD = :porCd and ORDRTK_BASE_MNTH = :OTBM and PROD_MNTH = :ProdMonth and OSEI_ID = :ukOseiID and POT_CD = :potCd and PROD_ORDR_NO = :prdnOrdrNo ");
	
	
	public static final StringBuilder insertMnthlyPrdnOrdr = new StringBuilder()
	.append("insert into TRN_MNTHLY_PROD_ORDR values (:porCd,:OTBM,:ProdMonth,:ukOseiID,:potCd,:prdnOrdrNo,:ordrQty,:exNo,:slsNoteNo,:tyrMkr,:dlrLst,:ownrMnl,:wrntyBklt,:bdyPrtnCd,:ocfRgnCd,'b000027',sysdate,'b000027',sysdate)");
	
	public static final StringBuilder updateMnthlyPrdnOrdr = new StringBuilder()
	.append( " update TRN_MNTHLY_PROD_ORDR set ORDR_QTY = :ordrQty,EX_NO=:exNo,SLS_NOTE_NO = :slsNoteNo,TYRE_MKR_CD=:tyrMkr,DEALER_LST = :dlrLst,OWNR_MNL=:ownrMnl,WRNTY_BKLT = :wrntyBklt,BDY_PRTCTN_CD=:bdyPrtnCd,OCF_REGION_CD = :ocfRgnCd,UPDTD_BY='b000027',UPDTD_DT=sysdate where POR_CD= :porCd and ORDRTK_BASE_MNTH= :OTBM and PROD_MNTH= :ProdMonth and OSEI_ID= :ukOseiID and POT_CD= :potCd and PROD_ORDR_NO= :prdnOrdrNo ");
	
/**
 * Instantiates a new b000027 query constants.
 */
private QueryConstants() {
	
}

}


