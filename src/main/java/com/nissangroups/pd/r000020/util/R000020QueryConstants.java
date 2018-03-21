package com.nissangroups.pd.r000020.util;

public class R000020QueryConstants {

	public R000020QueryConstants() {
	}

	public static final StringBuilder fetchInVldCarSrs = new StringBuilder()
			.append(" SELECT TRN_MNTH_PROD_SHDL_IF.SEQ_ID  FROM TRN_MNTH_PROD_SHDL_IF   ")
			.append(" LEFT OUTER JOIN MST_POR_CAR_SRS ON TRN_MNTH_PROD_SHDL_IF.POR_CD = MST_POR_CAR_SRS.POR_CD AND   ")
			.append(" MST_POR_CAR_SRS.CAR_SRS = TRN_MNTH_PROD_SHDL_IF.CAR_SRS ")
			.append(" WHERE  TRN_MNTH_PROD_SHDL_IF.POR_CD = :porCd ")
			.append(" AND (MST_POR_CAR_SRS.CAR_SRS IS NULL AND MST_POR_CAR_SRS.POR_CD IS NULL) AND TRN_MNTH_PROD_SHDL_IF.ERR_STTS_CD IS NULL ");

	public static final StringBuilder fetchInVldEndItem = new StringBuilder()
			.append(" SELECT DISTINCT "
					+ "TABLE_NAME.SEQ_ID  FROM TABLE_NAME TABLE_NAME  ")
			.append(" LEFT JOIN " + "MST_POR " + "ON "
					+ "TABLE_NAME.POR_CD = MST_POR.POR_CD  ")
			.append(" INNER JOIN" + " MST_BUYER" + " ON "
					+ "(TABLE_NAME.BUYER_CD = MST_BUYER.BUYER_CD AND "
					+ "TABLE_NAME.POR_CD = MST_POR.POR_CD AND "
					+ "MST_POR.PROD_REGION_CD = MST_BUYER.PROD_REGION_CD )  ")
			.append(" LEFT JOIN " + "MST_OEI_SPEC" + " ON"
					+ " TABLE_NAME.POR_CD = MST_OEI_SPEC.POR_CD   ")
			.append(" AND TABLE_NAME.PACK_CD =  MST_OEI_SPEC.PCK_CD   ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_OEI_SPEC.CAR_SRS  ")
			.append(" AND TABLE_NAME.APPLD_MDL_CD = MST_OEI_SPEC.APPLD_MDL_CD  ");

	public static final StringBuilder fetchInVldEndItemCnd = new StringBuilder()
			.append(" WHERE  TABLE_NAME.POR_CD = :porCd "
					+ " AND TABLE_NAME.ERR_STTS_CD IS NULL  ")
			.append(" AND MST_OEI_SPEC.POR_CD IS NULL "
					+ " AND MST_OEI_SPEC.CAR_SRS IS NULL   ")
			.append(" AND MST_OEI_SPEC.APPLD_MDL_CD IS NULL "
					+ "AND MST_OEI_SPEC.PCK_CD IS NULL ");

	public static final StringBuilder fetchInVldEndItemforByr = new StringBuilder()
			.append(" SELECT DISTINCT " + " TABLE_NAME.SEQ_ID "
					+ " FROM TABLE_NAME ").append(
					"WHERE TABLE_NAME.SEQ_ID NOT IN ( ");

	public static final StringBuilder fetchVldEndItemforByr = new StringBuilder()
			.append(" SELECT DISTINCT " + "TABLE_NAME.SEQ_ID "
					+ "FROM TABLE_NAME , MST_OEI_BUYER,MST_OEI_SPEC ");

	public static final StringBuilder fetchVldEndItemforByrCnd = new StringBuilder()
			.append(" WHERE TABLE_NAME.POR_CD = MST_OEI_SPEC.POR_CD  ")
			.append(" AND TABLE_NAME.PACK_CD =  MST_OEI_SPEC.PCK_CD  ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_OEI_SPEC.CAR_SRS ")
			.append(" AND TABLE_NAME.APPLD_MDL_CD = MST_OEI_SPEC.APPLD_MDL_CD ")
			.append(" AND TABLE_NAME.por_cd = MST_OEI_BUYER.por_cd ")
			.append(" AND MST_OEI_BUYER.buyer_cd = TABLE_NAME.buyer_cd ")
			.append(" AND MST_OEI_BUYER.oei_spec_id = MST_OEI_SPEC.oei_spec_id ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL  AND MST_OEI_SPEC.PROD_STAGE_CD  IN (:prdStgCd) ");

	public static final StringBuilder fetchInVldEndItemforByrCnd = new StringBuilder()
			.append(" )  ").append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL ");

	public static final StringBuilder ocfRgnCdCndtn = new StringBuilder()
			.append(" AND TABLE_NAME.OCF_RGN_CD = :ocfRgnCd ");

	public static final StringBuilder fetchInVldClrCdPart2Cnd = new StringBuilder()
			.append(" where   MST_OEI_SPEC.PROD_STAGE_CD in (:prdStgCd)) and TABLE_NAME.por_cd=:porCd ")
			.append(" and TABLE_NAME.ERR_STTS_CD is NULL ");

	public static final StringBuilder fetchInVldPotCd = new StringBuilder()

	.append(" SELECT DISTINCT TABLE_NAME.SEQ_ID  ").append(" FROM  ")
			.append(" TABLE_NAME ").append(" LEFT JOIN MST_PROD_ORDR_TYPE ON ")
			.append(" TABLE_NAME.POR_CD = MST_PROD_ORDR_TYPE.POR_CD ")
			.append(" AND TABLE_NAME.POT_CD = MST_PROD_ORDR_TYPE.POT_CD ")
			.append(" WHERE MST_PROD_ORDR_TYPE.POR_CD IS NULL  ")
			.append(" AND MST_PROD_ORDR_TYPE.POT_CD IS NULL ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL ");

	public static final StringBuilder fetchInVldSalesNoteNo = new StringBuilder()

			.append("SELECT DISTINCT TABLE_NAME.SEQ_ID,(SUBSTR(TABLE_NAME.PROD_MNTH,5)|| TABLE_NAME.POT_CD) as org_sales_note,TABLE_NAME.SLS_NOTE_NO  ")
			.append("FROM TABLE_NAME  ")
			.append("WHERE  ")
			.append(" TABLE_NAME.SLS_NOTE_NO != SUBSTR(TABLE_NAME.PROD_MNTH,5) ")
			.append("  || TABLE_NAME.POT_CD ")
			.append(" AND TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL ");

	public static final StringBuilder fetchDistinctCarSrs = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.CAR_SRS ")
			.append(" ,TABLE_NAME.ORDRTK_BASE_MNTH ")
			.append(" , mst_por_car_srs.car_srs_ordr_hrzn ")
			.append(" , TABLE_NAME.PROD_MNTH ")
			.append(" , mst_por_car_srs.CAR_SRS_ADPT_DATE ")
			.append(" , mst_por_car_srs.CAR_SRS_ABLSH_DATE ").append(" FROM  ")
			.append(" TABLE_NAME")
			.append(" , mst_por_car_srs   ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.CAR_SRS = MST_POR_CAR_SRS.CAR_SRS ")
			.append(" AND TABLE_NAME.POR_CD = MST_POR_CAR_SRS.POR_CD ")
			// .append(" AND MST_POR_CAR_SRS.CAR_SRS_ABLSH_DATE > TABLE_NAME.ORDRTK_BASE_MNTH ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL AND trim(TABLE_NAME.PRTYPE_FLAG) is null ");

	public static final StringBuilder fetchInVldClrCdPart1Cnd = new StringBuilder()
			.append(" where TABLE_NAME.SEQ_ID not in ( ");

	public static final StringBuilder fetchOseiDtls = new StringBuilder()
			.append("SELECT DISTINCT mos.por_cd, ")
			.append("  mos.PROD_FMY_CD,  mos.CAR_SRS ,  mob.BUYER_CD,  mos.APPLD_MDL_CD ,  mos.PCK_CD,  ")
			.append("mos.SPEC_DESTN_CD,  mos.ADTNL_SPEC_CD,  mo.EXT_CLR_CD,  mo.INT_CLR_CD, mo.osei_id   ")
			.append("FROM mst_oei_spec mos ")
			.append("INNER JOIN mst_oei_buyer mob ")
			.append("ON mos.por_cd       = mos.por_cd ")
			.append("AND mos.oei_spec_id = mob.oei_spec_id ")
			.append("INNER JOIN mst_osei mo ")
			.append("ON mo.por_cd        = mos.por_cd ")
			.append("AND mo.oei_buyer_id = mob.oei_buyer_id ")
			.append("INNER JOIN trn_mnth_prod_shdl_if tmps ")
			.append("ON tmps.por_cd         = mos.por_cd ")
			.append("AND tmps.PROD_FMLY_CD  = mos.PROD_FMY_CD ")
			.append("AND tmps.CAR_SRS       = mos.CAR_SRS ")
			.append("AND tmps.BUYER_CD      = mob.BUYER_CD ")
			.append("AND tmps.APPLD_MDL_CD  = mos.APPLD_MDL_CD ")
			.append("AND tmps.PACK_CD       = mos.PCK_CD ")
			.append("AND tmps.SPEC_DESTN_CD = mos.SPEC_DESTN_CD ")
			.append("AND tmps.ADD_SPEC_CD   = mos.ADTNL_SPEC_CD ")
			.append("AND tmps.EXT_CLR_CD    = mo.EXT_CLR_CD ")
			.append("AND tmps.INT_CLR_CD    = mo.INT_CLR_CD ")
			.append("WHERE mos.por_cd       = :porCd ")
			.append("AND mos.car_srs       IS NOT NULL and tmps.ERR_STTS_CD IS NULL and trim(tmps.PRTYPE_FLAG) is null ");

	public static final StringBuilder attachOseiDtls = new StringBuilder()
			.append(" UPDATE TRN_MNTH_PROD_SHDL_IF SET OSEI_ID = :oseiId")
			.append(" where por_cd = :porCd AND ")
			.append(" PROD_FMLY_CD = :prodFmyCd AND  ")
			.append(" CAR_SRS = :carSrs  AND ")
			.append(" BUYER_CD = :byrCd AND ")
			.append(" APPLD_MDL_CD = :appldMdlCd AND ")
			.append(" PACK_CD = :pckCd AND ")
			.append(" SPEC_DESTN_CD = :specDestCd AND ")
			.append(" ADD_SPEC_CD = :adtnlSpecCd AND ")
			.append(" EXT_CLR_CD = :extrClrCd AND ")
			.append(" INT_CLR_CD = :intClrCd AND trim(PRTYPE_FLAG) is null ");

	public static final StringBuilder fetchInVldBuyerCd = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.SEQ_ID ")
			.append(" FROM TRN_MNTH_PROD_SHDL_IF ")
			.append(" WHERE POR_CD      = :porCd AND TABLE_NAME.ERR_STTS_CD IS NULL ")
			.append(" AND BUYER_CD NOT IN ").append("  (SELECT BUYER_CD ")
			.append("  FROM MST_BUYER MB ").append("  INNER JOIN MST_POR MP ")
			.append("  ON MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append("  WHERE POR_CD         = :porCd )");

	public static final StringBuilder fetchInVldOcfRgnCd = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.SEQ_ID ")
			.append(" FROM TRN_MNTH_PROD_SHDL_IF ")
			.append(" WHERE POR_CD      = :porCd AND TABLE_NAME.ERR_STTS_CD IS NULL ")
			.append(" AND OCF_REGION_CD NOT IN ")
			.append("  (SELECT OCF_REGION_CD ").append("  FROM MST_BUYER MB ")
			.append("  INNER JOIN MST_POR MP ")
			.append("  ON MB.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append("  WHERE POR_CD = :porCd  )");

	public static final StringBuilder fetchErrorData = new StringBuilder()

	.append("SELECT t.ORDRTK_BASE_MNTH, ").append("  t.ocf_region_cd, ")
			.append("  t.PROD_MNTH, ").append("  t.BUYER_CD, ")
			.append("  t.CAR_SRS, ").append("  t.PROD_FMLY_CD, ")
			.append("  t.SPEC_DESTN_CD, ").append("  t.APPLD_MDL_CD, ")
			.append("  t.PACK_CD, ").append("  t.ADD_SPEC_CD, ")
			.append("  t.POT_CD, ").append("  t.EXT_CLR_CD, ")
			.append("  t.INT_CLR_CD, ").append("  t.SLS_NOTE_NO, ")
			.append("  t.EX_NO, ").append("  t.PROD_WK_NO, ")
			.append("  SUM(t.ORDR_QTY), ").append("  t.ERRORMESSAGE ")
			.append("FROM Trn_Mnth_Prod_Shdl_If t ")
			.append("WHERE t.por_Cd     = :porCd ")
			.append("AND t.ERR_STTS_CD IS NOT NULL ")
			.append("GROUP BY t.ORDRTK_BASE_MNTH, ")
			.append("  t.ocf_region_cd, ").append("  t.PROD_MNTH, ")
			.append("  t.BUYER_CD, ").append("  t.CAR_SRS, ")
			.append("  t.PROD_FMLY_CD, ").append("  t.SPEC_DESTN_CD, ")
			.append("  t.APPLD_MDL_CD, ").append("  t.PACK_CD, ")
			.append("  t.ADD_SPEC_CD, ").append("  t.POT_CD, ")
			.append("  t.EXT_CLR_CD, ").append("  t.INT_CLR_CD, ")
			.append("  t.SLS_NOTE_NO, ").append("  t.EX_NO, ")
			.append("  t.PROD_WK_NO, ").append("  t.ORDR_QTY, ")
			.append("  t.ERRORMESSAGE ");
	
	

	public static final StringBuilder fetchErrorDataCount = new StringBuilder()

	.append("SELECT t.ORDRTK_BASE_MNTH, ").append("  t.ocf_region_cd, ")
			.append("  t.PROD_MNTH, ").append("  t.BUYER_CD, ")
			.append("  t.CAR_SRS, ").append("  t.PROD_FMLY_CD, ")
			.append("  t.SPEC_DESTN_CD, ").append("  t.APPLD_MDL_CD, ")
			.append("  t.PACK_CD, ").append("  t.ADD_SPEC_CD, ")
			.append("  t.POT_CD, ").append("  t.EXT_CLR_CD, ")
			.append("  t.INT_CLR_CD, ").append("  t.SLS_NOTE_NO, ")
			.append("  t.EX_NO, ").append("  t.PROD_WK_NO, ")
			.append("  SUM(t.ORDR_QTY), ").append("  t.ERRORMESSAGE ")
			.append("FROM Trn_Mnth_Prod_Shdl_If t ")
			.append("WHERE t.por_Cd     = :porCd ")
			.append("AND t.ERR_STTS_CD IS NOT NULL AND t.ERR_STTS_CD not in ('99','98') ")
			.append("GROUP BY t.ORDRTK_BASE_MNTH, ")
			.append("  t.ocf_region_cd, ").append("  t.PROD_MNTH, ")
			.append("  t.BUYER_CD, ").append("  t.CAR_SRS, ")
			.append("  t.PROD_FMLY_CD, ").append("  t.SPEC_DESTN_CD, ")
			.append("  t.APPLD_MDL_CD, ").append("  t.PACK_CD, ")
			.append("  t.ADD_SPEC_CD, ").append("  t.POT_CD, ")
			.append("  t.EXT_CLR_CD, ").append("  t.INT_CLR_CD, ")
			.append("  t.SLS_NOTE_NO, ").append("  t.EX_NO, ")
			.append("  t.PROD_WK_NO, ").append("  t.ORDR_QTY, ")
			.append("  t.ERRORMESSAGE ");

	public static final StringBuilder fetchOseiIdFrInVldPrdMnth = new StringBuilder()
			.append(" SELECT DISTINCT TABLE_NAME.OSEI_ID  ")
			.append(" FROM TABLE_NAME WHERE ")
			.append(" TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_MNTH = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PROD_MNTH = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL ")
			.append(" AND TABLE_NAME.OSEI_ID IS NOT NULL AND trim(TABLE_NAME.PRTYPE_FLAG) is null  ");

	public static final StringBuilder updateErrorCdFrInVldPrdMnth = new StringBuilder()
			.append(" UPDATE TABLE_NAME  ")
			.append(" SET TABLE_NAME.ERR_STTS_CD = :errorCd ")
			.append(" , TABLE_NAME.ERRORMESSAGE = :errorMsg, TABLE_NAME.UPDTD_BY = 'R000020', TABLE_NAME.UPDTD_DT =  sysdate  ")
			.append(" WHERE TABLE_NAME.POR_CD = :porCd ")
			.append(" AND TABLE_NAME.ORDRTK_BASE_MNTH = :ordrTkBsMnth   ")
			.append(" AND TABLE_NAME.PROD_MNTH = :prdMnth ")
			.append(" AND TABLE_NAME.CAR_SRS = :carSrs ")
			.append(" AND TABLE_NAME.ERR_STTS_CD IS NULL AND trim(TABLE_NAME.PRTYPE_FLAG) is null  ");

	public static final StringBuilder getPrdStgCd = new StringBuilder()
			.append(" SELECT  VAL1 from MST_PRMTR  ")
			.append(" WHERE MST_PRMTR.PRMTR_CD = :prmtrCd ")
			.append(" AND MST_PRMTR.KEY1 = :key1   ")
			.append(" AND MST_PRMTR.KEY2 = :key2 ");

	public static final StringBuilder wherePrdStgCd = new StringBuilder()
			.append(" AND  MST_OEI_SPEC.PROD_STAGE_CD in (:prdStgCd) ");

	public static final StringBuilder fetchFrznErrData = new StringBuilder()
			.append("SELECT DISTINCT TABLE_NAME.SEQ_ID,TABLE_NAME.FRZN_TYPE_CD ")
			.append("FROM TABLE_NAME  ")
			.append("LEFT JOIN MST_OSEI_FRZN MOF ")
			.append("ON MOF.POR_CD = TABLE_NAME.POR_CD  AND ")
			.append("  MOF.OSEI_ID = TABLE_NAME.OSEI_ID AND ")
			.append(" MOF.OCF_REGION_CD = TABLE_NAME.OCF_REGION_CD ")
			.append(" AND MOF.FRZN_PROD_MNTH = TABLE_NAME.PROD_MNTH ")
			.append(" AND MOF.FRZN_ORDR_TAKE_BASE_MNTH <= TABLE_NAME.ORDRTK_BASE_MNTH ")
			.append(" AND MOF.CAR_SRS  = TABLE_NAME.CAR_SRS ")
			.append(" AND MOF.FRZN_TYPE_CD  = TABLE_NAME.FRZN_TYPE_CD ")
			.append(" WHERE MOF.FRZN_TYPE_CD IS NULL   ")
			.append(" AND TABLE_NAME.POR_CD = :porCd AND (TABLE_NAME.ERR_STTS_CD IS NULL or TABLE_NAME.ERR_STTS_CD = '99' )  ");

	public static final StringBuilder fetchPrdMthdCdData = new StringBuilder()
			.append("SELECT DISTINCT TABLE_NAME.SEQ_ID,TABLE_NAME.PROD_MTHD_CD ")
			.append("FROM TABLE_NAME  ")
			.append("LEFT JOIN MST_OSEI_PROD_TYPE MOF ")
			.append("ON MOF.POR_CD = TABLE_NAME.POR_CD ")
			.append("  AND  MOF.OSEI_ID = TABLE_NAME.OSEI_ID ")
			.append("AND MOF.PROD_MTHD_CD        = TABLE_NAME.PROD_MTHD_CD ")
			.append("AND MOF.PROD_PLNT_CD        = TABLE_NAME.PROD_PLNT_CD ")
			.append("AND MOF.PROD_MNTH           = TABLE_NAME.PROD_MNTH ")
			.append("AND MOF.ORDR_TAKE_BASE_MNTH = TABLE_NAME.ORDRTK_BASE_MNTH ")
			.append("AND MOF.PROD_WK_NO          = TABLE_NAME.PROD_WK_NO ")
			.append(" WHERE  TABLE_NAME.POR_CD = :porCd AND MOF.PROD_MTHD_CD   IS NULL");

	public static final StringBuilder getFrznCdFlg = new StringBuilder()
			.append(" SELECT  * from MST_PRMTR  ")
			.append(" WHERE trim(MST_PRMTR.PRMTR_CD) = :prmtrCd ")
			.append(" AND trim(MST_PRMTR.KEY1) = :key1   ")
			.append(" AND trim(MST_PRMTR.KEY2) = :key2 ");

	public static final StringBuilder getExNoVldnFlg = new StringBuilder()
			.append(" SELECT  * from MST_PRMTR  ")
			.append(" WHERE trim(MST_PRMTR.PRMTR_CD) = :prmtrCd ")
			.append(" AND trim(MST_PRMTR.KEY1) = :key1   ");

	public static final StringBuilder fetchInvldExNo = new StringBuilder()
			.append("SELECT DISTINCT TABLE_NAME.SEQ_ID,TABLE_NAME.POR_CD,TABLE_NAME.PROD_MNTH,TABLE_NAME.OSEI_ID,MOB.OEI_BUYER_ID,TABLE_NAME.EX_NO,TABLE_NAME.ORDRTK_BASE_MNTH,TABLE_NAME.POT_CD    ")
			.append("FROM TRN_MNTH_PROD_SHDL_IF  ")
			.append("INNER JOIN MST_OEI_SPEC MOS ")
			.append("ON MOS.POR_CD         = TABLE_NAME.POR_CD ")
			.append("AND MOS.PROD_FMY_CD   = TABLE_NAME.PROD_FMLY_CD ")
			.append("AND MOS.CAR_SRS       = TABLE_NAME.CAR_SRS ")
			.append("AND MOS.APPLD_MDL_CD  = TABLE_NAME.APPLD_MDL_CD ")
			.append("AND MOS.PCK_CD        = TABLE_NAME.PACK_CD ")
			.append("AND MOS.ADTNL_SPEC_CD = TABLE_NAME.ADD_SPEC_CD ")
			.append("AND MOS.SPEC_DESTN_CD  = TABLE_NAME.SPEC_DESTN_CD  ")
			.append("INNER JOIN MST_OEI_BUYER MOB ")
			.append("ON MOB.POR_CD        = MOS.POR_CD ")
			.append("AND MOB.OEI_SPEC_ID  = MOS.OEI_SPEC_ID ")
			.append("AND MOB.BUYER_CD  = TABLE_NAME.BUYER_CD ")
			.append("LEFT JOIN MST_EX_NO ME ")
			.append("ON TABLE_NAME.POR_CD     = ME.POR_CD ")
			.append("AND TABLE_NAME.PROD_MNTH = ME.PROD_MNTH ")
			.append("AND TABLE_NAME.EX_NO     = ME.EX_NO ")
			.append("AND TABLE_NAME.POT_CD     = ME.POT_CD ")
			.append("AND MOB.OEI_BUYER_ID     = ME.OEI_BUYER_ID ")
			.append("WHERE TABLE_NAME.POR_CD     = :porCd ")
			.append("AND ME.EX_NO        IS NULL AND  TABLE_NAME.ERR_STTS_CD IS NULL ");

	public static final StringBuilder fetchInvldExNoWithDiffComb = new StringBuilder()
			.append("SELECT DISTINCT TABLE_NAME.SEQ_ID ")
			.append("FROM TRN_MNTH_PROD_SHDL_IF  ")
			.append("INNER JOIN MST_EX_NO ME ")
			.append("ON TABLE_NAME.POR_CD     = ME.POR_CD ")
			.append("AND TABLE_NAME.PROD_MNTH = ME.PROD_MNTH ")
			.append("AND TABLE_NAME.POT_CD = ME.POT_CD ")
			.append("INNER JOIN MST_OEI_SPEC MOS ")
			.append("ON MOS.POR_CD         = ME.POR_CD ")
			.append("AND MOS.PROD_FMY_CD   = TABLE_NAME.PROD_FMLY_CD ")
			.append("AND MOS.CAR_SRS       = TABLE_NAME.CAR_SRS ")
			.append("AND MOS.APPLD_MDL_CD  = TABLE_NAME.APPLD_MDL_CD ")
			.append("AND MOS.PCK_CD        = TABLE_NAME.PACK_CD ")
			.append("AND MOS.ADTNL_SPEC_CD = TABLE_NAME.ADD_SPEC_CD ")
			.append("INNER JOIN MST_OEI_BUYER MOB ")
			.append("ON MOB.POR_CD        = MOS.POR_CD ")
			.append("AND MOB.OEI_SPEC_ID  = MOS.OEI_SPEC_ID ")
			.append("AND MOB.OEI_BUYER_ID = ME.OEI_BUYER_ID ")
			.append("WHERE TABLE_NAME.POR_CD     = :porCd ");

	public static final StringBuilder fetchInVldExNoFrDiffCombn = new StringBuilder()
			.append("SELECT EX_NO FROM MST_EX_NO ")
			.append("WHERE POR_CD  = :porCd  ")
			.append("AND EX_NO     = :exNo ")
			.append("AND PROD_MNTH = :prdMnth  ");

	public static final StringBuilder chckSameCombtExistsInMnthPrdOrd = new StringBuilder()
			.append("SELECT * FROM TRN_MNTHLY_PROD_ORDR ")
			.append("WHERE POR_CD         = :porCd ")
			.append("AND ORDRTK_BASE_MNTH = :ordrTkBsMnth ")
			.append("AND PROD_MNTH        = :prdMnth ")
			.append("AND OSEI_ID          = :ukOseiID AND POT_CD = :potCd ");
	public static final StringBuilder chckExNoExistsFrDiffCombInMnthly = new StringBuilder()
			.append("SELECT * FROM TRN_MNTHLY_PROD_ORDR ")
			.append("WHERE POR_CD         = :porCd ")
			.append("AND ORDRTK_BASE_MNTH = :ordrTkBsMnth ")
			.append("AND PROD_MNTH        = :prdMnth ")
			.append("AND EX_NO          = :exNo  ");

	public static final StringBuilder getVldExNo = new StringBuilder()
			.append(" SELECT  * from MST_PRMTR  ")
			.append(" WHERE trim(MST_PRMTR.PRMTR_CD) = :prmtrCd ")
			.append(" AND trim(MST_PRMTR.KEY1) = :key1   ")
			.append(" AND trim(MST_PRMTR.VAL1) <= :val1   ")
			.append(" AND trim(MST_PRMTR.VAL2) >= :val2 ");

	public static final StringBuilder getExNoRng = new StringBuilder()
			.append(" SELECT  * from MST_PRMTR  ")
			.append(" WHERE trim(MST_PRMTR.PRMTR_CD) = :prmtrCd ")
			.append(" AND trim(MST_PRMTR.KEY1) = :key1   ")
			.append(" AND trim(MST_PRMTR.KEY2) in (:key2)   ");

	public static final StringBuilder dltOldExNo = new StringBuilder()
			.append(" DELETE MST_EX_NO WHERE   ")
			.append(" POR_CD = :porCd AND  PROD_MNTH = :prdMnth  ")
			.append(" AND OEI_BUYER_ID = :oeiByrId AND POT_CD = :potCd ");

	public static final StringBuilder initializeMxIndiCator = new StringBuilder()
			.append("  UPDATE MST_EX_NO  SET   MAX_INDCTR = '' ")
			.append(" WHERE POR_CD =:porCd  AND  PROD_MNTH = :prdMnth  ")
			.append("    AND EX_NO LIKE :exNo||'%'    ");

	public static final StringBuilder updateExno = new StringBuilder()
			.append("  UPDATE MST_EX_NO  SET   EX_NO = :exNo  , PRPSE_CD = :prpsCd , UPDTD_BY = 'R000020' , UPDTD_DT = sysdate ")
			.append(" WHERE POR_CD =:porCd  AND  PROD_MNTH = :prdMnth  ")
			.append("   AND OEI_BUYER_ID = :oeiByrId AND POT_CD = :potCd   ");

	public static final StringBuilder insertExno = new StringBuilder()
			.append("  INSERT INTO  MST_EX_NO (POR_CD,PROD_MNTH,OEI_BUYER_ID,POT_CD,EX_NO,PRPSE_CD,CRTD_BY,CRTD_DT, UPDTD_BY, UPDTD_DT)  ")
			.append(" VALUES (  :porCd , :prdMnth ,:oeiByrId ,  :potCd ,  ")
			.append("   :exNo , :prpsCd , 'R000020',   sysdate  ,'R000020' ,  sysdate)  ");

	public static final StringBuilder updateMnlMxIndicator = new StringBuilder()
			.append("  UPDATE MST_EX_NO ")
			.append(" SET MAX_INDCTR   =:maxIndctr, UPDTD_BY = 'R000020', UPDTD_DT =  sysdate ")
			.append(" WHERE  TRIM(PRPSE_CD) IN ('1','2') AND POR_CD     =:porCd  ")
			.append(" AND  PROD_MNTH = :prdMnth   AND EX_NO = :exNo||(SELECT MAX(SUBSTR(E.EX_NO,2))     ")
			.append(" FROM MST_EX_NO E ")
			.append(" WHERE E.POR_CD     =:porCd ")
			.append("AND E.EX_NO LIKE :exNo ||'%' AND TRIM(E.PRPSE_CD) IN ('1','2') AND E.PROD_MNTH = :prdMnth)");

	public static final StringBuilder updateAutoMxIndicator = new StringBuilder()
			.append("  UPDATE MST_EX_NO ")
			.append(" SET MAX_INDCTR   =:maxIndctr, UPDTD_BY = 'R000020', UPDTD_DT =  sysdate ")
			.append(" WHERE TRIM(PRPSE_CD) IN ('3') AND POR_CD     =:porCd  ")
			.append("   AND PROD_MNTH = :prdMnth AND EX_NO = :exNo||(SELECT MAX(SUBSTR(E.EX_NO,2))     ")
			.append(" FROM MST_EX_NO E ")
			.append(" WHERE E.POR_CD     =:porCd ")
			.append("AND E.EX_NO LIKE :exNo ||'%'  AND TRIM(PRPSE_CD) IN ('3') AND E.PROD_MNTH = :prdMnth)");
	public static final StringBuilder selectAutoMxIndicator = new StringBuilder()
			.append("(SELECT MAX(SUBSTR(E.EX_NO,2))     ")
			.append(" FROM MST_EX_NO E ")
			.append(" WHERE E.POR_CD     =:porCd ")
			.append("AND E.EX_NO LIKE :exNo ||'%'  AND TRIM(PRPSE_CD) IN ('3') AND E.PROD_MNTH = :prdMnth)");

	public static final StringBuilder fetchValidFrnCd = new StringBuilder()
			.append("SELECT DISTINCT mof.* ")
			.append("FROM MST_OSEI_FRZN mof ")
			.append("INNER JOIN TRN_MNTH_PROD_SHDL_IF tmpsf ")
			.append("ON mof.POR_CD                    = tmpsf.POR_CD ")
			.append("AND mof.FRZN_ORDR_TAKE_BASE_MNTH = tmpsf.ORDRTK_BASE_MNTH ")
			.append("AND mof.FRZN_PROD_MNTH           = tmpsf.PROD_MNTH ")
			.append("AND mof.CAR_SRS                  = tmpsf.CAR_SRS ")
			.append("AND mof.OCF_REGION_CD                  = tmpsf.OCF_REGION_CD ")
			.append("AND mof.OSEI_ID                  = tmpsf.OSEI_ID ")
			.append("WHERE tmpsf.SEQ_ID               = :seqNo ");

	public static final StringBuilder fetchValidPrdMthdCdData = new StringBuilder()
			.append("SELECT DISTINCT MOF.*   ")
			.append("FROM TRN_MNTH_PROD_SHDL_IF TMPF  ")
			.append("INNER JOIN MST_OSEI_PROD_TYPE MOF ")
			.append("ON MOF.POR_CD = TMPF.POR_CD  ")
			.append("AND  MOF.OSEI_ID = TMPF.OSEI_ID ")
			.append("AND MOF.PROD_PLNT_CD        = TMPF.PROD_PLNT_CD ")
			.append("AND MOF.PROD_MNTH           = TMPF.PROD_MNTH ")
			.append("AND MOF.ORDR_TAKE_BASE_MNTH = TMPF.ORDRTK_BASE_MNTH ")
			.append("AND MOF.PROD_WK_NO          = TMPF.PROD_WK_NO ")
			.append(" WHERE  TMPF.POR_CD = :porCd AND TMPF.SEQ_ID     = :seqNo  ");

	public static final StringBuilder updateFrznCd = new StringBuilder()
			.append(" update TRN_MNTH_PROD_SHDL_IF  ")
			.append(" set FRZN_TYPE_CD =  :frznType, UPDTD_BY = 'R000020', UPDTD_DT =  sysdate where POR_CD     =:porCd  ")
			.append(" AND ORDRTK_BASE_MNTH  = :ordrTkBsMnth ")
			.append(" AND SEQ_ID = :seqNo ");

	public static final StringBuilder updatePrdMthdCd = new StringBuilder()
			.append(" update TRN_MNTH_PROD_SHDL_IF  ")
			.append(" set PROD_MTHD_CD =  :prdMthdCd, UPDTD_BY = 'R000020', UPDTD_DT =  sysdate where POR_CD = :porCd  ")
			.append(" AND ORDRTK_BASE_MNTH  = :ordrTkBsMnth ")
			.append(" AND SEQ_ID = :seqNo ");

	public static final StringBuilder getVldRecds = new StringBuilder().append(
			" select * from TABLE_NAME  ").append(
			" where TABLE_NAME.POR_CD = :porCd  ");

	public static final StringBuilder deleteOldData = new StringBuilder()
			.append(" delete TABLE_NAME  ").append(
					" where TABLE_NAME.POR_CD = :porCd  ");

	public static final StringBuilder extractVldOcfCd = new StringBuilder()
			.append("SELECT MOR.OCF_REGION_CD_DESC ")
			.append("FROM MST_OCF_REGION MOR ")
			.append("INNER JOIN MST_POR MP ")
			.append("ON MOR.PROD_REGION_CD = MP.PROD_REGION_CD ")
			.append("WHERE MP.POR_CD       = :porCd ")
			.append("AND MOR.OCF_REGION_CD = :ocfRgnCd");

}