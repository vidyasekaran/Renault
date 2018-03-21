/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000050.util;

/**
 * Constant file to keep the queries related to the batch B000050 
 * @author z014433
 *
 */
public class QueryConstants {
	
	public static  final StringBuilder getCntSelClause = new StringBuilder()
	.append(" SELECT count(m.id.ordrTakeBaseMnth) ");
	
	public static  final StringBuilder getMnthlySelClause = new StringBuilder()
	.append(" SELECT m.id.ordrTakeBaseMnth ");
	
	/** Constant Extract Order take base month  for Monthly*/
	public static  final StringBuilder getOrdrTkBsMnthMnthlyMain = new StringBuilder()
	.append("FROM MstMnthOrdrTakeBasePd m where  m.stageSttsCd='C' and m.id.porCd=  ");
	
	public static  final StringBuilder getOrdrTkBsMnthMnthlyWhr = new StringBuilder()
	.append(" and m.stageCd=  ");
	
	/** Constant Extract Order take base month  for Weekly*/
	public static  final StringBuilder getWklySelClause = new StringBuilder()
	.append(" SELECT concat(m.id.ordrTakeBaseMnth,m.id.ordrTakeBaseWkNo) ");
	
	public static  final StringBuilder getOrdrTkBsMnthWklyMain = new StringBuilder()
	.append("FROM MstWklyOrdrTakeBase m where  m.id.por= ");
	
	public static  final StringBuilder getOrdrTkBsMnthWklyWhr = new StringBuilder()	
	.append(" and m.stageCd='WK' and m.stageSttsCd='C' ");
	
	public static  final StringBuilder getOrdrTkBsMnthCls = new StringBuilder()	
	.append(" select max(ORDR_TAKE_BASE_MNTH) from MST_MNTH_ORDR_TAKE_BASE_PD where  POR_CD= :porCd and STAGE_CD='SC' ");
	
	/** Constant Extract number of weeks  for given production month*/
	public static final StringBuilder getWkNumDtlsCalenderQry = new StringBuilder()
	.append(" select concat(PROD_MNTH,PROD_WK_NO) from MST_WK_NO_CLNDR where PROD_MNTH= :ProdMonth and POR_CD= :porCd ");
	
	/** Constant Extract Latest Master Schedule Trn Details for Horizon = Infinity*/
	public static final StringBuilder getLtstMstTrnDtls = new StringBuilder()
	.append("  select * from TRN_LTST_MST_SHDL where ORDR_TAKE_BASE_MNTH= :OTBM and ORDR_BASE_BASE_WK_NO= :OTWN and POR_CD= :porCd ");
	
	/** Constant Extract Latest Master Schedule Trn Details for Horizon NOT equals Infinity*/
	public static final StringBuilder getLtstMstTrnDtlsNonInfiHrzn = new StringBuilder()
	.append("and CONCAT(PROD_MNTH,PROD_WK_NO) in (:prdnPrdLst) "); 
	
	
	/** Constant Extract Logical pipeline Trn Details P0004.1*/
	public static final StringBuilder getLgclPipLnTrnDtls = new StringBuilder()
	.append(" select PROD_ORDR_NO from TRN_LGCL_PPLN where por_cd= :porCd and PROD_ORDR_NO not in (:prdnOrdrNoLst) and concat(PROD_MNTH,PROD_WK_NO) >= :basePeriod and PROD_MNTH <= :ProdMonth and ORDR_DEL_FLAG='0' "); 
	
	/** Constant Update Order Delete Flag*/
	public static final StringBuilder updtOrdrDelFlg = new StringBuilder()
	.append(" update TRN_LGCL_PPLN set ORDR_DEL_FLAG='1',UPDTD_BY='B000050',UPDTD_DT=sysdate where por_cd= :porCd and PROD_ORDR_NO in (:prdnOrdrNoLst) and concat(PROD_MNTH,PROD_WK_NO) >= :basePeriod and PROD_MNTH <= :ProdMonth "); 
	
	/** Constant Update Stage Completed Status for Monthly*/
	public static final StringBuilder updtStgCdMnthly = new StringBuilder()
	.append(" update MST_MNTH_ORDR_TAKE_BASE_PD set STAGE_CD='SC',UPDTD_BY='B000050',UPDTD_DT=sysdate where por_cd= :porCd and ORDR_TAKE_BASE_MNTH= :OTBM "); 
	
	/** Constant Update Stage Completed Status for Weekly*/
	public static final StringBuilder updtStgCdWkly = new StringBuilder()
	.append(" update MST_WKLY_ORDR_TAKE_BASE set STAGE_CD='SC',UPDTD_BY='B000050',UPDTD_DT=sysdate where por= :porCd and ORDR_TAKE_BASE_MNTH= :OTBM and ORDR_TAKE_BASE_WK_NO= :OTWN "); 
	
	public static final String TRN_LGCL_PIPELN_VHCL_SEQ_ID = "select TRN_LGCL_PIPELN_VHCL_SEQ_ID.nextval from dual";  
	
	public static final StringBuilder fetchExtngLgclRcds = new StringBuilder()
	.append( " select count(*) from TRN_LGCL_PPLN where por_cd= :porCd and PROD_ORDR_NO = :prdnOrdrNo ");
	
	public static final StringBuilder insertLgclPipLnTrn = new StringBuilder()
	.append("insert into TRN_LGCL_PPLN values (:vhclSeqId, :porCd,:prdnOrdrNo,:ProdMonth,:ProdWk,:ukOseiID,:potCd,:offlnPlnDt,:prdPlntCd,:lnClass,:prdnMthdCd,:frznTypCd,:exNo,:slsNoteNo,:vinNo,:ordrDelFlg,:msFxdFlg,:lgclPipLnStgCd,'B000050',sysdate,'B000050',sysdate,:pdnDayNo)");
	
	public static final StringBuilder updateLgclPipLnTrn = new StringBuilder()
	.append( " update TRN_LGCL_PPLN set PROD_MNTH = :ProdMonth,PROD_WK_NO=:ProdWk,POT_CD = :potCd,FRZN_TYPE_CD=:frznTypCd,MS_FXD_FLAG = :msFxdFlg,OFFLN_PLAN_DATE=:offlnPlnDt,PROD_PLNT_CD = :prdPlntCd,PROD_MTHD_CD=:prdnMthdCd,EX_NO = :exNo,SLS_NOTE_NO=:slsNoteNo,OSEI_ID = :ukOseiID,LINE_CLASS=:lnClass,LGCL_PPLN_STAGE_CD = :lgclPipLnStgCd,UPDTD_BY='B000050',UPDTD_DT=sysdate,PROD_DAY_NO=:pdnDayNo where POR_CD= :porCd and PROD_ORDR_NO= :prdnOrdrNo ");
	
	
/**
 * Instantiates a new b000050 query constants.
 */
private QueryConstants() {
	
}

}


