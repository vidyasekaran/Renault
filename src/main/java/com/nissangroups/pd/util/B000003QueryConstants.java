/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000003QueryConstants
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
 * Constant file to keep the queries related to the batch B000003.
 * 
 * @author z011479
 *
 */
public class B000003QueryConstants {

	/** Constant endItemExtractionOnZero. */
	public static final StringBuilder endItemExtractionOnZero = new StringBuilder()
			.append("select oeism.POR_CD,oeism.CAR_SRS,oeism.OEI_SPEC_ID,oseidm.END_ITM_STTS_CD,oeism.PROD_STAGE_CD,oeibm.BUYER_CD,")
			.append("oeism.APPLD_MDL_CD,oeism.PCK_CD,oseim.EXT_CLR_CD,oseim.INT_CLR_CD,oeism.SPEC_DESTN_CD,oseidm.OSEI_ADPT_DATE,")
			.append("oseidm.OSEI_ABLSH_DATE,oeism.ADTNL_SPEC_CD,oeism.PROD_FMY_CD,bm.OCF_REGION_CD,oseim.OSEI_ID from ")
			.append("MST_OEI_SPEC oeism,MST_OEI_BUYER oeibm,MST_OSEI oseim,MST_OSEI_DTL oseidm,MST_BUYER bm,MST_POR pm where ")
			.append("oeism.POR_CD = :porCd and	pm.POR_CD= :porCd and pm.PROD_REGION_CD  =	bm.PROD_REGION_CD	 and	")
			.append("oeism.OEI_SPEC_ID  =	oeibm.OEI_SPEC_ID	 and	oeibm.OEI_BUYER_ID =	oseim.OEI_BUYER_ID	")
			.append(" and oeibm.BUYER_CD = bm.BUYER_CD	and oseim.OSEI_ID =	oseidm.OSEI_ID and")
			.append(" SUBSTR(oseidm.OSEI_ABLSH_DATE,1,6) >=  :minimumCarSeriesPeriod ");

	/** Constant endItemExtractionOnOne. */
	public static final StringBuilder endItemExtractionOnOne = new StringBuilder()
			.append("select oeism.POR_CD,oeism.CAR_SRS,oeism.OEI_SPEC_ID,oseidm.END_ITM_STTS_CD,oeism.PROD_STAGE_CD,oeibm.BUYER_CD,oeism.APPLD_MDL_CD,oeism.PCK_CD,oseim.EXT_CLR_CD,oseim.INT_CLR_CD, ")
			.append(" oeism.SPEC_DESTN_CD,oseidm.OSEI_ADPT_DATE,oseidm.OSEI_ABLSH_DATE,oeism.ADTNL_SPEC_CD,oeism.PROD_FMY_CD,bm.OCF_REGION_CD,oseim.OSEI_ID ")
			.append(" from MST_OEI_SPEC oeism,MST_OEI_BUYER oeibm,MST_OSEI oseim,MST_OSEI_DTL oseidm,MST_BUYER bm,MST_POR pm ,SPEC_REEXECUTE_STATUS srs where oeism.POR_CD = :porCd and	pm.POR_CD=:porCd ")
			.append(" and pm.PROD_REGION_CD  =	bm.PROD_REGION_CD	 and	oeism.OEI_SPEC_ID  =	oeibm.OEI_SPEC_ID	 and	oeibm.OEI_BUYER_ID =	oseim.OEI_BUYER_ID	")
			.append(" and oeibm.BUYER_CD = bm.BUYER_CD and oseim.OSEI_ID =	oseidm.OSEI_ID and SUBSTR(oseidm.OSEI_ABLSH_DATE,1,6) >= :minimumCarSeriesPeriod and oseidm.UPDTD_DT > srs.REFERENCE_TIME and srs.CRTD_BY= :batchId ");

	/** Constant getMultipleCarSrsChck. */
	public static final StringBuilder getMultipleCarSrsChck = new StringBuilder()
			.append("SELECT DISTINCT(CAR_SRS) FROM MST_PROD_TYPE WHERE POR_CD = :porCd AND ")
			.append(" TRIM(MST_PROD_TYPE.PROD_FMY_CD) = :prdFmlyCd AND (MST_PROD_TYPE.PRFX_YES LIKE :Prefix_Yes OR TRIM(MST_PROD_TYPE.PRFX_YES) is NULL) AND (MST_PROD_TYPE.SFFX_YES LIKE :Suffix_Yes OR TRIM(MST_PROD_TYPE.SFFX_YES) is NULL)")
			.append(" AND ordr_take_base_mnth > :startOrderTakeBaseMonth and ordr_take_base_mnth < :endOrderTakeBaseMonth  ");

	public static final StringBuilder prefixNoCheck = new StringBuilder()
			.append(" AND (MST_PROD_TYPE.PRFX_NO NOT LIKE :Prefix_Yes )");

	public static final StringBuilder suffixNoCheck = new StringBuilder()
			.append(" AND (MST_PROD_TYPE.SFFX_NO NOT LIKE :Suffix_Yes ) ");

	/** Constant getMultipleCarSeriesAtColorLevel. */
	public static final StringBuilder getMultipleCarSeriesAtColorLevel = new StringBuilder()
			// .append("select oei.OSEI_ID,oei.EXT_CLR_CD,oei.INT_CLR_CD,oseid.OSEI_ADPT_DATE,oseid.OSEI_ABLSH_DATE from MST_OSEI oei,MST_OSEI_DTL oseid ")
			// .append(" where oei.OSEI_ID in ( SELECT OSEI_ID FROM MST_OSEI_DTL,MST_OEI_SPEC oeism WHERE OSEI_ID in (SELECT OSEI_ID FROM MST_OSEI WHERE OEI_BUYER_ID in ( select OEI_BUYER_ID ")
			// .append(" from MST_OEI_BUYER WHERE TRIM(OEI_SPEC_ID)= :oeiSpecId AND POR_CD= :porCd ) AND POR_CD= :porCd)")
			// .append(" AND OSEI_ADPT_DATE >= :oseiAdptDt AND OSEI_ABLSH_DATE <= :oseiAblshDt ) and oei.OSEI_ID = oseid.OSEI_ID ");
			.append("SELECT osei.OSEI_ID,  osei.EXT_CLR_CD, osei.INT_CLR_CD, oseid.OSEI_ADPT_DATE, oseid.OSEI_ABLSH_DATE ")
			.append(" FROM MST_OEI_SPEC oei ")
			.append(" INNER JOIN MST_OEI_BUYER mob ON oei.POR_CD = mob.POR_CD AND oei.OEI_SPEC_ID = mob.OEI_SPEC_ID ")
			.append(" INNER JOIN MST_OSEI osei ON osei.POR_CD    = mob.POR_CD AND osei.OEI_BUYER_ID = mob.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_OSEI_DTL oseid ON oseid.OSEI_ID  = osei.OSEI_ID AND oseid.POR_CD = osei.POR_CD ")
			.append(" WHERE oei.POR_CD = :porCd AND oseid.OSEI_ADPT_DATE  >= :oseiAdptDt AND oseid.OSEI_ABLSH_DATE <= :oseiAblshDt ")
			.append(" AND oei.OEI_SPEC_ID = :oeiSpecId");

	/** Constant getDistinctCarSeries. */
	public static final StringBuilder getDistinctCarSeries = new StringBuilder()
			.append("SELECT DISTINCT(CAR_SRS) FROM MST_PROD_TYPE WHERE POR_CD = :porCd AND ")
			.append(" TRIM(MST_PROD_TYPE.PROD_FMY_CD) = :prdFmlyCd AND (MST_PROD_TYPE.PRFX_YES LIKE :Prefix_Yes OR TRIM(MST_PROD_TYPE.PRFX_YES) is NULL) AND (MST_PROD_TYPE.SFFX_YES LIKE :Suffix_Yes OR TRIM(MST_PROD_TYPE.SFFX_YES) is NULL)")
			.append(" AND ordr_take_base_mnth > :startOrderTakeBaseMonth and ordr_take_base_mnth < :endOrderTakeBaseMonth  ");

	/** Constant andColorCondition. */
	public static final StringBuilder andColorCondition = new StringBuilder()
			.append("AND  MST_PROD_TYPE.CLR_CODE_CNDTN LIKE :color  ");

	/** Constant checkForMultipleCarSeriesAtColorLevel. */
	public static final StringBuilder checkForMultipleCarSeriesAtColorLevel = new StringBuilder()
	.append("SELECT osei.OSEI_ID,  osei.EXT_CLR_CD, osei.INT_CLR_CD, oseid.OSEI_ADPT_DATE, oseid.OSEI_ABLSH_DATE ")
	.append(" FROM MST_OEI_SPEC oei ")
	.append(" INNER JOIN MST_OEI_BUYER mob ON oei.POR_CD = mob.POR_CD AND oei.OEI_SPEC_ID = mob.OEI_SPEC_ID ")
	.append(" INNER JOIN MST_OSEI osei ON osei.POR_CD    = mob.POR_CD AND osei.OEI_BUYER_ID = mob.OEI_BUYER_ID ")
	.append(" INNER JOIN MST_OSEI_DTL oseid ON oseid.OSEI_ID  = osei.OSEI_ID AND oseid.POR_CD = osei.POR_CD ")
	.append(" WHERE oei.POR_CD = :porCd AND oseid.OSEI_ADPT_DATE  >= :oseiAdptDt AND oseid.OSEI_ABLSH_DATE <= :oseiAblshDt ")
	.append(" AND oei.OEI_SPEC_ID = :oeiSpecId and oseid.END_ITM_STTS_CD IN (:eimSttsCd1,:eimSttsCd2)  " );
	// .append("Select oei.OSEI_ID,oei.EXT_CLR_CD,oei.INT_CLR_CD,oseid.OSEI_ADPT_DATE,oseid.OSEI_ABLSH_DATE ")
	// .append("from MST_OSEI oei,MST_OSEI_DTL oseid where oei.OSEI_ID in ( SELECT OSEI_ID FROM MST_OSEI_DTL,MST_OEI_SPEC oeism WHERE oei.OSEI_ID in ( ")
	// .append("SELECT OSEI_ID FROM MST_OSEI WHERE OEI_BUYER_ID in ( select OEI_BUYER_ID from MST_OEI_BUYER WHERE TRIM(OEI_SPEC_ID)=  :oeiSpecId AND POR_CD= :porCd ")
	// .append(") AND POR_CD = :porCd ) AND OSEI_ADPT_DATE >=  :oseiAdptDt AND OSEI_ABLSH_DATE <= :oseiAblshDt ) and oei.OSEI_ID = oseid.OSEI_ID and oseid.END_ITM_STTS_CD IN (:eimSttsCd1,:eimSttsCd2) ");

	/** Constant checkClrQry. */
	// Update the color condtion to IN
	public static final StringBuilder checkClrQry = new StringBuilder()
			// .append("SELECT DISTINCT(CAR_SRS) FROM MST_PROD_TYPE  WHERE POR_CD = :porCd AND ")
			// .append(" trim(MST_PROD_TYPE.PROD_FMY_CD) = :prdFmlyCd AND MST_PROD_TYPE.PRFX_YES LIKE :Prefix_Yes AND ")
			// .append("MST_PROD_TYPE.SFFX_YES LIKE :Suffix_Yes AND  ( MST_PROD_TYPE.CLR_CODE_CNDTN) is null ")
			// .append("OR (SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,1,4)) like  :color ");
			//

			.append("SELECT DISTINCT(CAR_SRS) FROM MST_PROD_TYPE  WHERE POR_CD = :porCd AND ")
			.append(" trim(MST_PROD_TYPE.PROD_FMY_CD) = :prdFmlyCd  ")
			.append(" AND :Prefix_Yes not like NVL2(REPLACE(MST_PROD_TYPE.PRFX_NO, ' ', ''), REPLACE(MST_PROD_TYPE.PRFX_NO, ' ', '_'), ' ')")
			.append(" AND  ( (:Prefix_Yes like REPLACE(MST_PROD_TYPE.PRFX_YES, ' ', '_') or trim(MST_PROD_TYPE.PRFX_YES) is NULL) and  (:Suffix_Yes like REPLACE(MST_PROD_TYPE.SFFX_YES, ' ', '_') or trim(MST_PROD_TYPE.SFFX_YES) is null)) ")
			.append(" AND   :Suffix_Yes not like NVL2(REPLACE(MST_PROD_TYPE.SFFX_NO, ' ', ''), REPLACE(MST_PROD_TYPE.SFFX_NO, ' ', '_'), ' ')")
			.append(" AND ( :color IN (")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,1,5))), ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,5,5))),  ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,11,5))), ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,16,5))), ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,21,5)))) or trim(MST_PROD_TYPE.CLR_CODE_CNDTN) is null) ")
			.append(" AND ordr_take_base_mnth > :startOrderTakeBaseMonth and ordr_take_base_mnth < :endOrderTakeBaseMonth  ");

	/** Constant getEmptyCarSrs. */
	public static final StringBuilder getEmptyCarSrs = new StringBuilder()
			.append("SELECT CAR_SRS,ORDR_TAKE_BASE_MNTH FROM MST_PROD_TYPE WHERE POR_CD = :porCd AND  ")
			.append(" trim(MST_PROD_TYPE.PROD_FMY_CD) = :prdFmlyCd AND MST_PROD_TYPE.PRFX_YES LIKE :Prefix_Yes AND  ")
			.append(" MST_PROD_TYPE.SFFX_YES LIKE :Suffix_Yes AND  ( MST_PROD_TYPE.CLR_CODE_CNDTN) is null OR (SUBSTR( MST_PROD_TYPE.CLR_CODE_CNDTN,1,4)) like :color ");

	/** Constant getProdFmlyCdCount. */
	public static final StringBuilder getProdFmlyCdCount = new StringBuilder()
			// .append("select COUNT(DISTINCT PROD_FMY_CD),CAR_SRS from MST_PROD_TYPE WHERE MST_PROD_TYPE.POR_CD = :porCd  AND ")
			// .append(" TRIM(MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN) = :specDestinationCd AND MST_PROD_TYPE.PRFX_YES LIKE :Prefix_Yes AND ")
			// .append(" MST_PROD_TYPE.SFFX_YES LIKE :Suffix_Yes  GROUP BY CAR_SRS  ");

			.append("select COUNT(DISTINCT PROD_FMY_CD),CAR_SRS from MST_PROD_TYPE WHERE MST_PROD_TYPE.POR_CD = :porCd  and   ")
			.append(" TRIM(MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN) = :specDestinationCd   ")
			.append(" AND  ( :specDestinationCd IN ( ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN,1,4))),")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN,5,4))), ")
			.append(" (TRIM(SUBSTR( MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN,9,4)))) or trim(MST_PROD_TYPE.SPEC_DESTN_CD_CNDTN) is null )  ")
			.append(" AND :Prefix_Yes not like NVL2(REPLACE(MST_PROD_TYPE.PRFX_NO, ' ', ''), REPLACE(MST_PROD_TYPE.PRFX_NO, ' ', '_'), ' ') ")
			.append(" AND  ( (:Prefix_Yes like REPLACE(MST_PROD_TYPE.PRFX_YES, ' ', '_') or trim(MST_PROD_TYPE.PRFX_YES) is NULL) and  (:Suffix_Yes like REPLACE(MST_PROD_TYPE.SFFX_YES, ' ', '_') or trim(MST_PROD_TYPE.SFFX_YES) is null))   ")
			.append("  AND   :Suffix_Yes not like NVL2(REPLACE(MST_PROD_TYPE.SFFX_NO, ' ', ''), REPLACE(MST_PROD_TYPE.SFFX_NO, ' ', '_'), ' ') ")
			.append(" AND ordr_take_base_mnth > :startOrderTakeBaseMonth and ordr_take_base_mnth < :endOrderTakeBaseMonth  ")
			.append("  GROUP BY CAR_SRS  ");

	/** Constant getextractBuilder. */
	public static final StringBuilder getextractBuilder = new StringBuilder()
			.append("SELECT  distinct prodtypeMst.* from MST_PROD_TYPE prodtypeMst,MST_OEI_SPEC oeiSpec WHERE (SUBSTR(:appldMdlCd, 0, 7) not like NVL2(REPLACE( prodTypeMst.PRFX_NO, ' ', ''), REPLACE( prodTypeMst.PRFX_NO, ' ', '_'), ' ')")
			.append(" AND (SUBSTR( :appldMdlCd, 0, 7) like REPLACE( prodTypeMst.PRFX_YES, ' ', '_') OR TRIM(prodTypeMst.PRFX_YES) is NULL) )")
			.append(" AND ((SUBSTR(:appldMdlCd || :packCd , 11, 8) not like NVL2(REPLACE( prodTypeMst.SFFX_NO, ' ', ''), REPLACE( prodTypeMst.SFFX_NO, ' ', '_'), ' ')")
			.append(" AND ((SUBSTR(:appldMdlCd || :packCd , 11, 8) like REPLACE( prodTypeMst.SFFX_YES, ' ', '_')) OR TRIM(prodTypeMst.SFFX_YES ) is NULL )))")
			.append(" AND ((trim( prodtypeMst.SPEC_DESTN_CD_CNDTN) is null) OR")
			.append(" (SUBSTR( prodtypeMst.SPEC_DESTN_CD_CNDTN,1, :specDetsLen )= :specDstnCd ) OR")
			.append(" (SUBSTR( prodtypeMst.SPEC_DESTN_CD_CNDTN,5, :specDetsLen)= :specDstnCd  ) OR")
			.append(" (SUBSTR( prodtypeMst.SPEC_DESTN_CD_CNDTN,9, :specDetsLen)= :specDstnCd )")
			.append(" )")
			.append("AND  prodTypeMst.POR_CD= :porCd ")
			.append("AND  prodTypeMst.PROD_FMY_CD= :prdFmlyCd ")
			.append("AND  prodTypeMst.OCF_REGION_CD= :OcfRgn ")
			.append(" AND (TRIM( prodtypeMst.CLR_CODE_CNDTN) is null ")
			.append(" OR (SUBSTR( prodtypeMst.CLR_CODE_CNDTN,1,3)= :extrClrCd ")
			.append(" AND TRIM(SUBSTR( prodtypeMst.CLR_CODE_CNDTN,4,2))= :intrClrCd) OR (SUBSTR( prodtypeMst.CLR_CODE_CNDTN,6,3)= :extrClrCd ")
			.append(" AND TRIM(SUBSTR( prodtypeMst.CLR_CODE_CNDTN,9,2))= :intrClrCd) OR (SUBSTR( prodtypeMst.CLR_CODE_CNDTN,11,3)= :extrClrCd  ")
			.append(" AND TRIM(SUBSTR( prodtypeMst.CLR_CODE_CNDTN,14,2))= :intrClrCd) OR (SUBSTR( prodtypeMst.CLR_CODE_CNDTN,16,3)= :extrClrCd ")
			.append(" AND TRIM(SUBSTR( prodtypeMst.CLR_CODE_CNDTN,19,2))= :intrClrCd) OR (SUBSTR( prodtypeMst.CLR_CODE_CNDTN,21,3)= :extrClrCd ")
			.append(" AND TRIM(SUBSTR( prodtypeMst.CLR_CODE_CNDTN,24,2))= :intrClrCd))");

	/** Constant getminrecords. */
	public static final StringBuilder getminrecords = new StringBuilder()
			.append("SELECT p1.POR_CD,p1.ORDR_TAKE_BASE_MNTH,p1.PROD_MTHD_CD,length(p1.PROD_MTHD_CD), ")
			.append("(months_between(to_date(:ablshDate,'YYYYMM'),to_date(:MinYrMnth,'YYYYMM'))+1)*5 ,p1.CAR_SRS,p1.CAR_SRS_PRITY_CD,p1.PROD_PLNT_CD, ")
			.append(" p1.PROD_FMY_CD,p1.PRFX_YES,p1.SFFX_YES,p1.SPEC_DESTN_CD_CNDTN")
			.append(" FROM MST_PROD_TYPE p1 ")
			.append(" where p1.POR_CD = :porCd and p1.ORDR_TAKE_BASE_MNTH = (select max(ORDR_TAKE_BASE_MNTH) ")
			.append(" from MST_PROD_TYPE where ORDR_TAKE_BASE_MNTH <= :MinYrMnth) ")
			.append(" order by p1.CAR_SRS_PRITY_CD DESC");

	/** Constant deleteOSEI. */
	public static final StringBuilder deleteOSEI = new StringBuilder()
			.append("DELETE FROM MST_OSEI_PROD_TYPE WHERE POR_CD = :POR_CD")
			.append(" AND OSEI_ID = :ukOseiID ")
			.append(" AND ORDR_TAKE_BASE_MNTH = :orderTakeBaseMonth ")
			.append(" AND PROD_MNTH = :ProdMonth ");

	/** Constant oseiprodtypemst. */
	public static final StringBuilder oseiprodtypemst = new StringBuilder()
			.append("Insert into MST_OSEI_PROD_TYPE ")
			.append(" (POR_CD,OSEI_ID,PROD_PLNT_CD,ORDR_TAKE_BASE_MNTH, ")
			.append(" PROD_MNTH,PROD_WK_NO,PROD_MTHD_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) ")
			.append(" values ( :POR_CD ,:ukOseiID")
			.append(", :PRODUCTION_PLANT_CD , :orderTakeBaseMonth , :ProdMonth ")
			.append(", :PROD_WK_NO , :PRODUCTION_METHOD_CD,'B000003',sysdate,'B000003',sysdate)");

	/** Constant checkforexistingcarseries. */
	public static final StringBuilder checkforexistingcarseries = new StringBuilder()
			.append("SELECT DISTINCT(CAR_SRS) FROM MST_OEI_SPEC WHERE POR_CD = :POR_CD  AND ")
			.append(" MST_OEI_SPEC.SPEC_DESTN_CD = :specDestinationCd AND ")
			.append(" MST_OEI_SPEC.APPLD_MDL_CD LIKE :appldMdlCd AND ")
			.append(" MST_OEI_SPEC.PCK_CD LIKE :packCd ");

	/** Constant updateEndItemStatusCode. */
	public static final StringBuilder updateEndItemStatusCode = new StringBuilder()
			.append("UPDATE MST_OSEI_DTL set END_ITM_STTS_CD = :END_ITM_STTS_CD ,UPDTD_BY = 'B000003',UPDTD_DT = SYSDATE ")
			.append(" WHERE MST_OSEI_DTL.POR_CD = :POR_CD AND ")
			.append(" MST_OSEI_DTL.OSEI_ID = :ukOseiID AND ")
			.append("  MST_OSEI_DTL.OSEI_ADPT_DATE = :oseiAdptDt AND ")
			.append(" MST_OSEI_DTL.OSEI_ABLSH_DATE = :oseiAblshDt ");

	/** Constant porcarseriesmst. */
	public static final StringBuilder porcarseriesmst = new StringBuilder()
			.append("select * FROM MST_POR_CAR_SRS porcarseriesmst ")
			.append(" where porcarseriesmst.POR_CD = :POR_CD AND ")
			.append(" porcarseriesmst.PROD_FMY_CD = :PRODUCTION_FAMILY_CD AND ")
			.append(" porcarseriesmst.CAR_SRS = :CAR_SERIES ");

	/** Constant porHorizon. */
	public static final StringBuilder porHorizon = new StringBuilder()
			.append("SELECT m.porHrzn FROM MstPor m WHERE m.porCd= :POR_CD ");

	/** Constant insertporcarseriesmst. */
	public static final StringBuilder insertporcarseriesmst = new StringBuilder()
			.append(" Insert into MST_POR_CAR_SRS ")
			.append(" (POR_CD,PROD_FMY_CD,CAR_SRS,CAR_SRS_DESC,CAR_SRS_ADPT_DATE,CAR_SRS_ABLSH_DATE, ")
			.append("	CAR_SRS_ORDR_HRZN,BRND_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,CAR_GRP) ")
			.append(" values ( :POR_CD, :PRODUCTION_FAMILY_CD ,")
			.append(":CAR_SERIES , :CAR_SERIES")
			.append(",to_char(SYSDATE,'YYYYMM') || 11, :MaxAblshDate , :CarSrsOderHrzn ")
			.append(",NULL,'B000003',SYSDATE,'B000003',SYSDATE, :CarSrsSizeTrim ) ");

	/** Constant getHorizonfromParameterMst. */
	public static final StringBuilder getHorizonfromParameterMst = new StringBuilder()
			.append(" SELECT VAL1 FROM MST_PRMTR WHERE TRIM(PRMTR_CD) ='HORIZON' and TRIM(KEY1) = :POR_CD ");

	/** Constant updateCarseries. */
	public static final StringBuilder updateCarseries = new StringBuilder()
			.append("UPDATE MST_OEI_SPEC SET MST_OEI_SPEC.CAR_SRS = :CAR_SERIES,UPDTD_BY = 'B000003',UPDTD_DT = SYSDATE ")
			.append(" WHERE  MST_OEI_SPEC.POR_CD= :POR_CD AND  ")
			.append(" MST_OEI_SPEC.PROD_FMY_CD = :PRODUCTION_FAMILY_CD AND ")
			.append(" MST_OEI_SPEC.APPLD_MDL_CD LIKE REPLACE(:appldMdlCd, ' ', '_') or trim(:appldMdlCd) is NULL AND ")

			.append(" MST_OEI_SPEC.PCK_CD LIKE REPLACE(:packCd, ' ', '_') or trim(:packCd) is NULL AND  ")
			.append(" ((trim(:specDstnCd) is null) OR")
			.append(" (SUBSTR( :specDstnCd,1, 4 )= MST_OEI_SPEC.SPEC_DESTN_CD ) OR")
			.append(" (SUBSTR( :specDstnCd,5, 4)= MST_OEI_SPEC.SPEC_DESTN_CD  ) OR")
			.append(" (SUBSTR( :specDstnCd,9, 4)= MST_OEI_SPEC.SPEC_DESTN_CD )")

			.append(" )");

	/** Constant maxukoseiid. */
	public static final StringBuilder maxukoseiid = new StringBuilder()
			.append(" SELECT (MAX(osei.OSEI_ID)) AS MAX_VAL FROM MST_OSEI_PROD_TYPE osei");

	/** Constant getweekno. */
	public static final StringBuilder getweekno = new StringBuilder()
			.append("select * from MST_WK_NO_CLNDR where ")
			.append(" MST_WK_NO_CLNDR.POR_CD= :POR_CD AND ")
			.append(" MST_WK_NO_CLNDR.PROD_MNTH = :ProdMonth ")
			.append(" AND MST_WK_NO_CLNDR.NON_OPRTNL_FLAG ='0' ");

	/** Constant maxabolishdate. */
	public static final StringBuilder maxabolishdate = new StringBuilder()
			.append("SELECT VAL1 FROM MST_PRMTR WHERE TRIM(PRMTR_CD) ='MAXIMUM_ABOLISHED_DATE' and TRIM(KEY1)='MAX' ");

	/** Constant insertBatchUpdatedTime. */
	public static final StringBuilder insertBatchUpdatedTime = new StringBuilder()
			.append("merge into SPEC_REEXECUTE_STATUS sh using (SELECT 1 FROM DUAL) s  on (sh.por = :porCd and  TRIM(sh.BATCH_ID) = 'B000003' ")
			.append(" and trim(sh.TABLE_NAME) = :tableName ) when matched then  update set sh.PROCESS_EXECUTED_TIME = sysdate, sh.REFERENCE_TIME = (select MAX(updtd_dt) from mst_osei_dtl where POR_CD = :porCd),")
			.append("sh.UPDTD_BY= 'B000003' ,sh.UPDTD_DT = sysdate when not matched then insert ")
			.append("(POR,BATCH_ID,TABLE_NAME,PROCESS_EXECUTED_TIME,REFERENCE_TIME , CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT) values (:porCd,'B000003',:tableName,sysdate,(select MAX(updtd_dt) from mst_osei_dtl where POR_CD = :porCd),'B000003',sysdate,'B000003',sysdate)");

	public static final StringBuilder getProductionMethodCode = new StringBuilder()
			.append(" SELECT p1.POR_CD,p1.ORDR_TAKE_BASE_MNTH,p1.PROD_MTHD_CD,length(p1.PROD_MTHD_CD),  ")
			.append(" (months_between(to_date( :maxAblshDt,'YYYYMM'),to_date(:ordrTkeBsMnth,'YYYYMM'))+1)*5 ,p1.CAR_SRS,p1.CAR_SRS_PRITY_CD,p1.PROD_PLNT_CD,  ")
			.append(" p1.PROD_FMY_CD,p1.PRFX_YES,p1.SFFX_YES,p1.SPEC_DESTN_CD_CNDTN FROM MST_PROD_TYPE p1 where p1.POR_CD = :porCd and p1.ORDR_TAKE_BASE_MNTH =  ")
			.append(" (select max(ORDR_TAKE_BASE_MNTH) from MST_PROD_TYPE where ORDR_TAKE_BASE_MNTH <= :ordrTkeBsMnth and POR_CD = :porCd )   ")
			.append(" AND PROD_FMY_CD = :prdFmlyCd    ")
			.append(" AND :Prefix_Yes not like NVL2(REPLACE(PRFX_NO, ' ', ''), REPLACE(PRFX_NO, ' ', '_'), ' ')  ")
			.append(" AND  ( (:Prefix_Yes like REPLACE(PRFX_YES, ' ', '_') or trim(PRFX_YES) is NULL) and  (:Suffix_Yes like REPLACE(SFFX_YES, ' ', '_') or trim(SFFX_YES) is null))   ")
			.append(" AND   :Suffix_Yes not like NVL2(REPLACE(SFFX_NO, ' ', ''), REPLACE(SFFX_NO, ' ', '_'), ' ')  ")
			.append(" AND ( :color IN ( (TRIM(SUBSTR( CLR_CODE_CNDTN,1,5))),  ")
			.append(" (TRIM(SUBSTR( CLR_CODE_CNDTN,5,5))),    ")
			.append(" (TRIM(SUBSTR( CLR_CODE_CNDTN,11,5))),   ")
			.append(" (TRIM(SUBSTR( CLR_CODE_CNDTN,16,5))),  ")
			.append(" (TRIM(SUBSTR( CLR_CODE_CNDTN,21,5)))) or trim(CLR_CODE_CNDTN) is null)   ")
			.append(" order by p1.CAR_SRS_PRITY_CD DESC  ");

	/**
	 * Instantiates a new b000003 query constants.
	 */
	private B000003QueryConstants() {
	}
}
