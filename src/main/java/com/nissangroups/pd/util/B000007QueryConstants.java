/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :O Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;



/**
 * Constant file to keep the queries related to the batch B000007. 
 * @author z011479
 *
 */
public class B000007QueryConstants {

	/**
	 * Instantiates a new b000007 query constants.
	 */
	private B000007QueryConstants() {
		
	}

	/** Constant B00007PatternMatching. */
	public static final StringBuilder B00007PatternMatching = new StringBuilder()
	.append(" SELECT  oeiSpec.POR_CD as POR_CD,oeiSpec.APPLD_MDL_CD,")
	.append("oeiSpec.SPEC_DESTNN_CD,oeiSpec.CAR_SRS,frzMst.PRFX_YES ,frzMst.PRFX_NO,")
	.append("frzMst.SFFX_YES ,frzMst.SFFX_NO,frzMst.FRZN_PROD_MNTH,")
	.append("LEAST((SELECT min(ODM1.OSEI_ABLSH_DATE) FRM MST_OSEI_DTL ODM1 WHERE ODM.OSEI_ADPT_DATE=ODM1.OSEI_ADPT_DATE and ODM1.UK_OSEI_ID = ODM.UK_OSEI_ID AND ODM1.END_ITM_STTS_CD =ODM.END_ITM_STTS_CD),(SELECT MIN(PCSM1.CAR_SRS_ABLSH_DATE) FRM MST_POR_CAR_SRS PCSM1 WHERE PCSM1.CAR_SRS = PCSM.CAR_SRS AND PCSM1.POR_CD = PCSM.POR_CD)) AS ABLSH_DATE")
	.append(",buymst.OCF_REGION_CD,OSEIM.UK_OSEI_ID,frzMst.FRZN_TYPE_CD,")
	.append("OSEIM.EXT_CLR_CD,OSEIM.INT_CLR_CD ")
	.append(" FRM MST_OEI_SPEC oeiSpec")
	.append(" INNER JOIN MST_POR pormst ON")
	.append(" pormst.POR_CD = oeiSpec.POR_CD")
	.append(" INNER JOIN MST_OEI_BUYER OEIBM ON")
	.append(" oeiSpec.POR_CD = OEIBM.POR_CD AND")
	.append(" oeiSpec.UK_OEI_SPEC_ID = OEIBM.UK_OEI_SPEC_ID")
	.append(" INNER JOIN MST_OSEI OSEIM ON")
	.append(" OEIBM.POR_CD = OSEIM.POR_CD AND")
	.append(" OEIBM.UK_OEI_BUYER_ID = OSEIM.UK_OEI_BUYER_ID")
	.append("  INNER JOIN MST_POR_CAR_SRS PCSM ON oeiSpec.CAR_SRS = PCSM.CAR_SRS AND PCSM.POR_CD = oeiSpec.POR_CD")
	.append(" INNER JOIN MST_OSEI_DTL ODM ON OSEIM.UK_OSEI_ID = ODM.UK_OSEI_ID")
	.append(" INNER JOIN MST_BUYER buymst ON")
	.append(" buymst.PROD_REGION_CD = pormst.PROD_REGION_CD")
	.append(" and OEIBM.BUYER_CD = buymst.BUYER_CD")
	.append("  INNER JOIN MST_FRZN frzMst ON")
	.append("  oeiSpec.POR_CD = frzMst.POR_CD AND")
	.append(" oeiSpec.CAR_SRS = frzMst.CAR_SRS AND")
	.append(" buymst.OCF_REGION_CD = frzMst.OCF_REGION_CD")
	.append(" where (SUBSTR(oeiSpec.APPLD_MDL_CD, 0, 7) not like NVL2(REPLACE(frzMst.PRFX_NO, ' ', ''), REPLACE(frzMst.PRFX_NO, ' ', '_'), ' ')")
	.append(" AND   SUBSTR(oeiSpec.APPLD_MDL_CD, 0, 7) like REPLACE(frzMst.PRFX_YES, ' ', '_'))")
	.append(" AND   (SUBSTR(oeiSpec.APPLD_MDL_CD || oeiSpec.PCK_CD , 11, 8) not like NVL2(REPLACE(frzMst.SFFX_NO, ' ', ''), REPLACE(frzMst.SFFX_NO, ' ', '_'), ' ')")
	.append(" AND   (SUBSTR(oeiSpec.APPLD_MDL_CD || oeiSpec.PCK_CD , 11, 8) like REPLACE(frzMst.SFFX_YES, ' ', '_'))) ")
	.append(" AND   (TRIM(oeiSpec.SPEC_DESTNN_CD)) IN (NULL,")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTNN_CD_CNDTN,1,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTNN_CD_CNDTN,6,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTNN_CD_CNDTN,11,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTNN_CD_CNDTN,16,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTNN_CD_CNDTN,21,5))))")
	.append(" AND ((TRIM(OSEIM.EXT_CLR_CD)) || (TRIM(OSEIM.INT_CLR_CD))) IN (NULL,")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,1,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,5,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,11,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,16,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,21,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,26,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,35,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,40,5))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,45,5))))");

/** Constant ExtractEndItem. */
public static final StringBuilder ExtractEndItem = new StringBuilder()
	.append(" SELECT  oeiSpec.POR_CD as POR_CD,oeiSpec.APPLD_MDL_CD ,")
	.append("oeiSpec.SPEC_DESTN_CD ,oeiSpec.CAR_SRS as  CAR_SRS,oeiSpec.PCK_CD ,oeiSpec.PROD_FMY_CD , ")
	.append("buymst.OCF_REGION_CD,OEIBM.BUYER_CD ,OSEIM.EXT_CLR_CD ,OSEIM.INT_CLR_CD ,OSEIM.OSEI_ID ,max(ODM.OSEI_SUSPENDED_DATE) as OSEI_SUSPENDED_DATE  ")
	.append("FROM MST_OEI_SPEC oeiSpec ")
	.append("INNER JOIN MST_POR pormst ON ")
	.append("pormst.POR_CD = oeiSpec.POR_CD ")
	.append("INNER JOIN MST_OEI_BUYER OEIBM ON ")
	.append(" oeiSpec.POR_CD = OEIBM.POR_CD AND ")
	.append(" oeiSpec.OEI_SPEC_ID = OEIBM.OEI_SPEC_ID ")
	.append(" INNER JOIN MST_OSEI OSEIM ON ")
	.append(" OEIBM.POR_CD = OSEIM.POR_CD AND ")
	.append(" OEIBM.OEI_BUYER_ID = OSEIM.OEI_BUYER_ID ")
	.append(" INNER JOIN MST_POR_CAR_SRS PCSM ON oeiSpec.CAR_SRS = PCSM.CAR_SRS AND PCSM.POR_CD = oeiSpec.POR_CD AND PCSM.PROD_FMY_CD = oeiSpec.PROD_FMY_CD ")
	.append(" INNER JOIN MST_OSEI_DTL ODM ON OSEIM.OSEI_ID = ODM.OSEI_ID ")
	.append(" INNER JOIN MST_BUYER buymst ON ")
	.append(" buymst.PROD_REGION_CD = pormst.PROD_REGION_CD ")
	.append(" and OEIBM.BUYER_CD = buymst.BUYER_CD ");

/** Constant ExtractEndItemWhereCondition. */
public static final StringBuilder ExtractEndItemWhereCondition = new StringBuilder()
.append(" WHERE oeiSpec.POR_CD = :porCd AND ODM.OSEI_SUSPENDED_DATE > :minimumCarSeriesLimit  ")
.append(" group by oeiSpec.POR_CD,oeiSpec.APPLD_MDL_CD,oeiSpec.SPEC_DESTN_CD ,oeiSpec.CAR_SRS ,oeiSpec.PCK_CD ,oeiSpec.PROD_FMY_CD ,  ")
.append(" buymst.OCF_REGION_CD,OEIBM.BUYER_CD ,OSEIM.EXT_CLR_CD ,OSEIM.INT_CLR_CD ,OSEIM.OSEI_ID   ");


/** Constant reExecuteStssInnrJoin. */
public static final StringBuilder reExecuteStssInnrJoin = new StringBuilder(" INNER JOIN SPEC_REEXECUTE_STATUS SRS ON ODM.UPDTD_DT > SRS.REFERENCE_TIME and SRS.POR = :porCd and trim(SRS.TABLE_NAME) = :tableName  and trim(SRS.BATCH_ID) = :batchId");

/** Constant B00007PatternMatchingNew. */
public static final StringBuilder B00007PatternMatchingNew = new StringBuilder()
	.append(" SELECT  frzMst.POR_CD,frzMst.CAR_SRS, frzMst.PRFX_YES ,frzMst.PRFX_NO,frzMst.FRZN_ORDR_TAKE_BASE_MNTH,")
	.append("frzMst.SFFX_YES ,frzMst.SFFX_NO,frzMst.FRZN_PROD_MNTH,frzMst.OCF_REGION_CD,frzMst.FRZN_TYPE_CD,frzMst.FRZN_PRITY_CD,frznTyp.FRZN_TYPE_SHRT_DESC,frzMst.FRZN_CLR_CODE_CNDTN,frzMst.FRZN_SPEC_DESTN_CD_CNDTN FROM MST_FRZN frzMst INNER JOIN   ")
	.append(" FRZN_TYPE frznTyp ON frzMst.FRZN_TYPE_CD  = frznTyp.FRZN_TYPE_CD ")
	.append(" where :prefix not like NVL2(REPLACE(frzMst.PRFX_NO, ' ', ''), REPLACE(frzMst.PRFX_NO, ' ', '_'), ' ')")
	.append(" AND  ( (:prefix like REPLACE(frzMst.PRFX_YES, ' ', '_') or trim(frzMst.PRFX_YES) is NULL) and  (:suffix like REPLACE(frzMst.SFFX_YES, ' ', '_') or trim(frzMst.SFFX_YES) is null)) ")
	.append(" AND   :suffix not like NVL2(REPLACE(frzMst.SFFX_NO, ' ', ''), REPLACE(frzMst.SFFX_NO, ' ', '_'), ' ')")
	.append(" AND  ( :specDestinationCd IN (")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTN_CD_CNDTN,1,4))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTN_CD_CNDTN,5,4))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTN_CD_CNDTN,9,4))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTN_CD_CNDTN,13,4))),")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_SPEC_DESTN_CD_CNDTN,17,4)))) or trim(frzMst.FRZN_SPEC_DESTN_CD_CNDTN) is null ) ")
	.append(" AND ( :exteriorColor || :interirorColor IN (")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,1,5))), ")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,6,5))),  ")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,11,5))), ")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,16,5))), ")
	.append(" (TRIM(SUBSTR( frzMst.FRZN_CLR_CODE_CNDTN,21,5)))) or trim(frzMst.FRZN_CLR_CODE_CNDTN) is null) ")
	.append(" AND frzMst.FRZN_ORDR_TAKE_BASE_MNTH = :orderTakeBaseMonth ")
	.append(" AND TRIM(frzMst.POR_CD) = :POR_CODE ")
	.append(" AND TRIM(frzMst.PROD_FMY_CD) = :PRODUCTION_FAMILY_CODE ")
	.append(" AND frzMst.CAR_SRS = :carSeries ")
	.append(" AND TRIM(frzMst.FRZN_DEL_FLAG) = :frznDelFlg ");

/** Constant minimumHorizon. */
public static final StringBuilder minimumHorizon = new StringBuilder()
	.append("SELECT LEAST(NVL((SELECT VAL1 FROM MST_PRMTR PM WHERE PM.PRMTR_CD = 'FROZEN_HORIZON_LIMIT' and trim(PM.KEY1) = :porCd),PCSM.CAR_SRS_ORDR_HRZN ),PCSM.CAR_SRS_ORDR_HRZN ) AS MIN_VALUE from MST_POR_CAR_SRS PCSM WHERE trim(PCSM.POR_CD) = :porCd AND trim(PCSM.PROD_FMY_CD) = :prod_fmly_cd AND PCSM.CAR_SRS = :carSrs");

/** Constant deleteOldFrozenData. */
public static final StringBuilder deleteOldFrozenData = new StringBuilder()
	.append("DELETE FROM MST_OSEI_FRZN WHERE POR_CD=:porCd ");

/** Constant getMaxSpecUpdatedTime. */
public static final StringBuilder getMaxSpecUpdatedTime = new StringBuilder()
	.append("select MAX(updtd_dt) from mst_osei_dtl");

/** Constant insertBatchUpdatedTime. */
public static final StringBuilder insertBatchUpdatedTime = new StringBuilder()
	.append("merge into SPEC_REEXECUTE_STATUS sh using (SELECT 1 FROM DUAL) s  on (sh.por = :porCd and  TRIM(sh.BATCH_ID) = 'B000007' ")
	.append(" and trim(sh.TABLE_NAME) = :tableName ) when matched then  update set sh.PROCESS_EXECUTED_TIME = sysdate, sh.REFERENCE_TIME = (select MAX(updtd_dt) from mst_osei_dtl where POR_CD = :porCd),")
	.append("sh.UPDTD_BY= 'B000007' ,sh.UPDTD_DT = sysdate when not matched then insert ")
	.append("(POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME , CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) values (:porCd,'B000007',:tableName,sysdate,(select MAX(updtd_dt) from mst_osei_dtl where POR_CD = :porCd),'B000007',sysdate,'B000007',sysdate)");



/** Constant INSERT_B000003_BATCH_UPDATED_TIME. */
public static final StringBuilder INSERT_B000003_BATCH_UPDATED_TIME = new StringBuilder()
	.append("Insert into SPEC_REEXECUTE_STATUS (POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME,CRTD_DT,CRTD_BY,UPDTD_BY,UPDTD_DT) VALUES")
	.append("(:porCd,'B000003','ORDERABLE_SALES_END_ITEM_DETAIL_MST',:currentTime,:masterUptdTime,:currentTime,'B000003','B000003',:currentTime) ");


/** Constant getMinimumCarSeriesLimitQuery. */
public static final StringBuilder getMinimumCarSeriesLimitQuery = new StringBuilder()
.append("select trim(p.val1) FROM MstPrmtr p WHERE trim(p.id.key1) = :porCd AND  p.id.prmtrCd = 'FROZEN_CREATION_LIMIT'");



}
