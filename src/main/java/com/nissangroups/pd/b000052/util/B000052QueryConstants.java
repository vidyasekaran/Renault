package com.nissangroups.pd.b000052.util;

public class B000052QueryConstants {

	public static final String WHERECLAUSE = " where ";
	public static final String PORCD = "POR_CD=";
	public static final String PORCDPARAM = ":porCd";
	public static final String STAGECD = "STAGE_CD=";
	public static final String STGCDPRAM = ":stgCd";
	public static final String STAGESTTS = "STAGE_STTS_CD=";
	public static final String STGSTSPARM = ":stgSttsCd";
	public static final String ANDSTMT = " AND ";
	public static final String KEY2 = " AND key2= ";
	public static final String PLNTLNSMRY = " 'PLANT_LINE_SUMMARY' ";
	public static final String PLNTLNCLS = " 'PLANT_AND_LINE_CLASS' ";
	public static final String CNSTNTDAYNO = " 'CONSTANT_DAY_NO' ";
	public static final String MNTHLY = " MONTHLY ";
	public static final String WKLY = " WEEKLY ";

	public static final String PLANTCD = "plantCD";
	public static final String LINECLS = "lineClass";
	public static final String CNSTDAY = "cnsntDayNo";
	public static final String POR = "porCd";
	public static final String ORDRTKBSPRD = "ordrTkBsPd";
	public static final String PRODMNTH = "prodMnth";
	public static final String PRODWK = "prodWk";
	public static final String QTY = "qty";
	public static final String LQTY = "lqty";
	public static final String CARSRS = "carSrs";
	public static final String BYRGRPCD = "byrGrpCD";
	public static final String FEATCD = "featCd";
	public static final String OCFFRAMECD = "ocfFrmCd";
	public static final String FEATTYPECD = "featTypCd";
	public static final String PRODDAYNO = "prodDayNo";

	public static final String OCFBYRGRP = "ocfByrGrp";
	public static final String OCFRGNCD = "ocfRgnCd";

	public static final String PRCSTYPE = "prcssType";
	public static final String WKNO = "weekNo";
	public static final String ORDRTKBSMNTH = "ordrTkBsMnth";

	public static final StringBuilder fetchMnthlyOrdrtk = new StringBuilder()
			.append("SELECT m.id.ordrTakeBaseMnth FROM MstMnthOrdrTakeBasePd m where  m.id.porCd=  ");

	public static final StringBuilder fetchMnthlyOrdrtk2 = new StringBuilder()
			.append(" and m.stageCd='F2' and m.stageSttsCd='C' ");

	public static final StringBuilder fetchWklyOrdrtk = new StringBuilder()
			.append("SELECT m FROM MstWklyOrdrTakeBase m where  m.id.por= ");

	public static final StringBuilder fetchWklyOrdrtk2 = new StringBuilder()
			.append(" and m.stageCd='WK' and m.stageSttsCd='C' ");

	// FOR PLANT SUMMARY AND CONSTANT DAY NO
	public static final StringBuilder fetchWklySmryVal1 = new StringBuilder()
			.append("select VAL2 from MST_PRMTR where PRMTR_CD='WEEKLY_OCF_SUMMARY' and KEY1=");

	public static final StringBuilder fetchWklySmryVal1n2 = new StringBuilder()
			.append("select VAL1,VAL2 from MST_PRMTR where PRMTR_CD='WEEKLY_OCF_SUMMARY' and KEY1=");

	public static final StringBuilder fetchFeatCdVal1 = new StringBuilder()
			.append("select VAL1 from MST_PRMTR where PRMTR_CD='FEATURE_TYPE_CODE' and KEY1=");

	// Need to be addressed.
	public static final StringBuilder fetchFeatCdVal2 = new StringBuilder()
			.append("select VAL2 from MST_PRMTR where PRMTR_CD='FEATURE_TYPE_CODE' and KEY1=");

	public static final StringBuilder fetchByrLvlFtCdMnthlyY1 = new StringBuilder()
			.append(" SELECT DISTINCT tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD,'10,40,50' as FEAT_type,:plantCD as PLANT_CD,:lineClass as LINE_CLASS,:cnsntDayNo as CONSTANT_DAY_NO from TRN_LTST_MST_SHDL tlms ");

	public static final StringBuilder fetchByrLvlFtCdMnthlyN1 = new StringBuilder()
			.append(" SELECT DISTINCT tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD,'10,40,50' as FEAT_type,tlms.LINE_CLASS,tlms.PROD_PLNT_CD,tlms.PROD_DAY_NO from TRN_LTST_MST_SHDL tlms ");

	public static final StringBuilder fetchByrLvlFtCdWklyY1 = new StringBuilder()
			.append(" SELECT DISTINCT tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD,'10,40,50' as FEAT_type,:plantCD as PLANT_CD,:lineClass as LINE_CLASS,:cnsntDayNo as CONSTANT_DAY_NO from TRN_LTST_MST_SHDL tlms ");

	public static final StringBuilder fetchByrLvlFtCdWklyN1 = new StringBuilder()
			.append(" SELECT DISTINCT tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD,'10,40,50' as FEAT_type,tlms.LINE_CLASS,tlms.PROD_PLNT_CD,tlms.PROD_DAY_NO from TRN_LTST_MST_SHDL tlms");

	public static final StringBuilder fetchByrLvlFtCdMnthly2 = new StringBuilder()
			.append(" INNER JOIN MST_OSEI mosei on mosei.OSEI_ID = tlms.OSEI_ID ")
			.append(" INNER JOIN MST_OEI_BUYER mob on mob.OEI_BUYER_ID = mosei.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_OEI_SPEC mspec on mspec.OEI_SPEC_ID = mob.OEI_SPEC_ID ")
			.append(" LEFT JOIN MST_OEI_FEAT mof on mof.OEI_BUYER_ID = mob.OEI_BUYER_ID and mof.OEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and mof.OEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO)  ")
			.append(" LEFT JOIN MST_OSEI_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO)  ")

			// .append(" LEFT JOIN MST_ORDRLE_SLS_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO)  ")

			.append(" INNER JOIN MST_POR mp on mp.POR_CD = tlms.POR_CD  ")
			.append(" INNER JOIN MST_BUYER mb on mb.BUYER_CD = mob.BUYER_CD and mb.PROD_REGION_CD = mp.PROD_REGION_CD  ")
			.append(" INNER JOIN MST_OCF_REGION mor on mor.OCF_REGION_CD = mb.OCF_REGION_CD and mor.OCF_BUYER_GRP_CD = mb.OCF_BUYER_GRP_CD and mor.PROD_REGION_CD = mb.PROD_REGION_CD ");

	public static final StringBuilder fetchByrLvlFtCdMnthly3 = new StringBuilder()
			.append(" where tlms.POR_CD= ");
	public static final StringBuilder fetchByrLvlFtCdMnthly4 = new StringBuilder()
			.append(" and tlms.ORDR_BASE_BASE_PRD_TYPE='M' and tlms.ORDR_TAKE_BASE_MNTH= ");

	public static final StringBuilder fetchByrLvlFtCdWkly4 = new StringBuilder()
			.append(" and tlms.ORDR_BASE_BASE_PRD_TYPE='W' and tlms.ORDR_TAKE_BASE_MNTH= ");

	public static final StringBuilder fetchByrLvlFtCdMnthly5 = new StringBuilder()
			.append(" and tlms.PROD_MNTH >=  ");

	public static final StringBuilder fetchByrLvlFtCdWkly6 = new StringBuilder()
			.append(" and tlms.PROD_WK_NO >=  ");

	public static final StringBuilder fetchByrLvlFtCdMnthlySGrpBy = new StringBuilder()
			.append(" GROUP BY (tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD )");

	public static final StringBuilder fetchByrLvlFtCdMnthlyNGrpBy = new StringBuilder()
			.append(" GROUP BY (tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" mb.BUYER_GRP_CD,tlms.LINE_CLASS,tlms.PROD_PLNT_CD,tlms.PROD_DAY_NO) ");

	public static final StringBuilder fetchWklyocfUsgTrn = new StringBuilder()
			.append(" select * from TRN_BUYER_WKLY_OCF_USG where POR_CD=:porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWkNo and OSEI_ID=:oseiID and FEAT_CD=:featCd ")
			// .append(" and OCF_FRME_CD=:ocfFrmCd and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCd ");
			.append(" and OCF_FRME_CD=:ocfFrmCd ");

	public static final StringBuilder fetchWklyocfUsgTrnTest = new StringBuilder()
			.append(" select * from TRN_BUYER_WKLY_OCF_USG where POR_CD=:porCd and PROD_MNTH=:prodMnth ");

	public static final StringBuilder insrtWklyocfUsgTrnTest = new StringBuilder()
			.append(" INSERT INTO TRN_BUYER_WKLY_OCF_USG (CRTD_BY,CRTD_DT,POR_CD,PROD_MNTH ,PROD_WK_NO ,OSEI_ID ,FEAT_CD ,CAR_SRS ,BUYER_GRP_CD ,OCF_FRME_CD ) VALUES('B000052', sysdate,:porCd,:prodMnth,:prodWkNo,:oseiID,:featCd,:carSrs,:byrGrpCd,:ocfFrmCd) ");

	// P0005.1
	public static final StringBuilder byrGrpUsgQty1 = new StringBuilder()
			.append(" SELECT tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" '10,40,50' as FEAT_type,:plantCD as PLANT_CD,:lineClass as LINE_CLASS,:cnsntDayNo as CONSTANT_DAY_NO,tlms.POT_CD,count(tlms.PROD_ORDR_NO) as QTY ,mor.OCF_AUTO_ALLCTN_FLAG,mor.OCF_REGION_CD,mb.BUYER_GRP_CD,mor.OCF_BUYER_GRP_CD ")

			.append(" from TRN_LTST_MST_SHDL tlms ")
			.append(" INNER JOIN MST_OSEI mosei on mosei.OSEI_ID = tlms.OSEI_ID ")
			.append(" INNER JOIN MST_OEI_BUYER mob on mob.OEI_BUYER_ID = mosei.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_OEI_SPEC mspec on mspec.OEI_SPEC_ID = mob.OEI_SPEC_ID ")
			.append("  LEFT JOIN MST_OEI_FEAT mof on mof.OEI_BUYER_ID = mob.OEI_BUYER_ID and mof.OEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and mof.OEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")
			.append(" LEFT JOIN MST_OSEI_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")

			// .append(" LEFT JOIN MST_ORDRLE_SLS_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")
			.append(" INNER JOIN MST_POR mp on mp.POR_CD = tlms.POR_CD ")
			.append(" INNER JOIN MST_BUYER mb on mb.BUYER_CD = mob.BUYER_CD and mb.PROD_REGION_CD = mp.PROD_REGION_CD ")
			.append(" INNER JOIN MST_OCF_REGION mor on mor.OCF_REGION_CD = mb.OCF_REGION_CD and mor.OCF_BUYER_GRP_CD = mb.OCF_BUYER_GRP_CD and mor.PROD_REGION_CD = mb.PROD_REGION_CD ");

	public static final String byrGrpUsgQtyMnthly1 = " where tlms.POR_CD=:porCd and tlms.ORDR_BASE_BASE_PRD_TYPE=:prcssType  and tlms.ORDR_TAKE_BASE_MNTH=:ordrTkBsPd and tlms.PROD_MNTH > :ordrTkBsPd ";
	public static final String byrGrpUsgQtyWkly1 = " where tlms.POR_CD=:porCd and tlms.ORDR_BASE_BASE_PRD_TYPE=:prcssType  and tlms.ORDR_TAKE_BASE_MNTH=:ordrTkBsPd and tlms.PROD_MNTH >= :ordrTkBsPd and tlms.PROD_WK_NO>=:prodWk ";
	public static final String byrGrpUsgQtyGrpBy = " GROUP BY (tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS,tlms.POT_CD,mor.OCF_AUTO_ALLCTN_FLAG,mor.OCF_REGION_CD,mb.BUYER_GRP_CD,mor.OCF_BUYER_GRP_CD) ";
	public static final String byrGrpUsgQtyGrpByN1 = " GROUP BY (tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ";
	public static final String byrGrpUsgQtyGrpByN2 = " tlms.LINE_CLASS,tlms.PROD_PLNT_CD,tlms.PROD_DAY_NO,tlms.POT_CD,mor.OCF_AUTO_ALLCTN_FLAG,mor.OCF_REGION_CD,mb.BUYER_GRP_CD,mor.OCF_BUYER_GRP_CD) ";

	public static final StringBuilder byrGrpUsgQty2 = new StringBuilder()
			.append(" SELECT  tlms.POR_CD,tlms.PROD_MNTH,tlms.PROD_WK_NO,tlms.OSEI_ID,mof.FEAT_CD,mof.FEAT_TYPE_CD,mof.OCF_FRME_CD,mspec.CAR_SRS, ")
			.append(" '10,40,50' as FEAT_type,tlms.PROD_PLNT_CD,tlms.LINE_CLASS,tlms.PROD_DAY_NO,tlms.POT_CD,count(tlms.PROD_ORDR_NO) as QTY ,mor.OCF_AUTO_ALLCTN_FLAG,mor.OCF_REGION_CD,mb.BUYER_GRP_CD,mor.OCF_BUYER_GRP_CD ")

			.append(" from TRN_LTST_MST_SHDL tlms ")
			.append(" INNER JOIN MST_OSEI mosei on mosei.OSEI_ID = tlms.OSEI_ID ")
			.append(" INNER JOIN MST_OEI_BUYER mob on mob.OEI_BUYER_ID = mosei.OEI_BUYER_ID ")
			.append(" INNER JOIN MST_OEI_SPEC mspec on mspec.OEI_SPEC_ID = mob.OEI_SPEC_ID ")
			.append(" LEFT JOIN MST_OEI_FEAT mof on mof.OEI_BUYER_ID = mob.OEI_BUYER_ID and mof.OEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and mof.OEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")
			.append(" LEFT JOIN MST_OSEI_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")

			// .append(" LEFT JOIN MST_ORDRLE_SLS_FEAT moseif on moseif.OSEI_ID = mosei.OSEI_ID and moseif.OSEIF_ADPT_DATE <= CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) and moseif.OSEIF_ABLSH_DATE > CONCAT(tlms.PROD_MNTH,tlms.PROD_WK_NO) ")
			.append(" INNER JOIN MST_POR mp on mp.POR_CD = tlms.POR_CD ")
			.append(" INNER JOIN MST_BUYER mb on mb.BUYER_CD = mob.BUYER_CD and mb.PROD_REGION_CD = mp.PROD_REGION_CD ")
			.append(" INNER JOIN MST_OCF_REGION mor on mor.OCF_REGION_CD = mb.OCF_REGION_CD and mor.OCF_BUYER_GRP_CD = mb.OCF_BUYER_GRP_CD and mor.PROD_REGION_CD = mb.PROD_REGION_CD ");

	public static final StringBuilder initZero = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_LMT_QTY = '0' where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ");

	public static final StringBuilder defaultUpdate = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :qty  where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and OCF_FRME_CD=:ocfFrmCd and FEAT_TYPE_CD=:featTypCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	
	public static final StringBuilder update1 = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :qty,BUYER_GRP_OCF_LMT_QTY = :lqty,UPDTD_BY = 'B000052', UPDTD_DT = sysdate where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and OCF_FRME_CD=:ocfFrmCd and FEAT_TYPE_CD=:featTypCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder update2 = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :qty,BUYER_GRP_OCF_LMT_QTY = '0',UPDTD_BY = 'B000052', UPDTD_DT = sysdate where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and OCF_FRME_CD=:ocfFrmCd and FEAT_TYPE_CD=:featTypCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder update3 = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :qty,BUYER_GRP_OCF_LMT_QTY = '',UPDTD_BY = 'B000052', UPDTD_DT = sysdate where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and OCF_FRME_CD=:ocfFrmCd and FEAT_TYPE_CD=:featTypCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder update4 = new StringBuilder()
			.append(" UPDATE TRN_BUYER_GRP_WKLY_OCF_LMT SET BUYER_GRP_OCF_USG_QTY = :qty,BUYER_GRP_OCF_LMT_QTY = '',UPDTD_BY = 'B000052', UPDTD_DT = sysdate where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and OCF_FRME_CD=:ocfFrmCd and FEAT_TYPE_CD=:featTypCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");
	public static final StringBuilder ocftrnlimitSelect = new StringBuilder()
			.append(" select * from TRN_BUYER_GRP_WKLY_OCF_LMT where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder ocftrnlimitDaySelect = new StringBuilder()
			.append(" select PROD_DAY_NO from TRN_BUYER_GRP_WKLY_OCF_LMT where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk ")
			.append(" and CAR_SRS=:carSrs and BUYER_GRP_CD=:byrGrpCD and FEAT_CD=:featCd and PLANT_CD=:plantCD ")
			.append(" and LINE_CLASS=:lineClass");

	public static final StringBuilder ocftrnlimitinsert = new StringBuilder()
			.append(" INSERT INTO TRN_BUYER_GRP_WKLY_OCF_LMT ")
			.append(" (CRTD_BY, CRTD_DT, POR_CD,PROD_MNTH,PROD_WK_NO,CAR_SRS,BUYER_GRP_CD,FEAT_CD ,OCF_FRME_CD,FEAT_TYPE_CD,BUYER_GRP_OCF_USG_QTY, BUYER_GRP_OCF_LMT_QTY,LINE_CLASS,PLANT_CD,PROD_DAY_NO)  ")
			.append(" VALUES ('B000052', sysdate,:porCd,:prodMnth,:prodWk,:carSrs,:byrGrpCD,:featCd,:ocfFrmCd,:featTypCd,:qty,:lqty,:lineClass,:plantCD,:prodDayNo) ");

	public static final StringBuilder fetchRnglLvlUsgY = new StringBuilder()
			.append(" SELECT bwol.POR_CD, bwol.PROD_MNTH, bwol.PROD_WK_NO, bwol.CAR_SRS, mb.OCF_REGION_CD, mb.OCF_BUYER_GRP_CD, bwol.FEAT_CD, bwol.OCF_FRME_CD, sum(bwol.BUYER_GRP_OCF_USG_QTY) as Qty,bwol.FEAT_TYPE_CD, ")
			.append(" :plantCD as PLANT_CD,:lineClass as LINE_CLASS,:cnsntDayNo as CONSTANT_DAY_NO,bwol.BUYER_GRP_OCF_LMT_QTY as BUYER_LIMIT,mb.BUYER_GRP_CD ")
			.append(" from TRN_BUYER_GRP_WKLY_OCF_LMT  bwol ")
			.append(" INNER JOIN MST_BUYER mb on mb.BUYER_GRP_CD = bwol.BUYER_GRP_CD ")
			.append(" where bwol.POR_CD=:porCd ")
			.append(" and bwol.PROD_MNTH >=:ordrTkBsPd ");

	public static final String rgnlUsgQtyWkly1 = " and bwol.PROD_WK_NO>=:prodWk ";
	public static final String rgnlUsgQtyGrpByY = " group by bwol.POR_CD, bwol.PROD_MNTH, bwol.PROD_WK_NO, bwol.CAR_SRS, mb.OCF_REGION_CD, mb.OCF_BUYER_GRP_CD, bwol.FEAT_CD, bwol.OCF_FRME_CD,bwol.BUYER_GRP_OCF_USG_QTY,bwol.FEAT_TYPE_CD,bwol.BUYER_GRP_OCF_LMT_QTY,mb.BUYER_GRP_CD ";
	public static final String rgnlUsgQtyGrpByN1 = " group by bwol.POR_CD, bwol.PROD_MNTH, bwol.PROD_WK_NO, bwol.CAR_SRS, mb.OCF_REGION_CD, mb.OCF_BUYER_GRP_CD, bwol.FEAT_CD, bwol.OCF_FRME_CD,bwol.BUYER_GRP_OCF_USG_QTY,bwol.FEAT_TYPE_CD,mb.BUYER_GRP_CD, ";
	public static final String rnglUsgQtyGrpByN2 = " bwol.LINE_CLASS,bwol.PLANT_CD,bwol.PROD_DAY_NO,bwol.BUYER_GRP_OCF_LMT_QTY ";

	public static final StringBuilder fetchRnglLvlUsgN = new StringBuilder()
			.append(" SELECT bwol.POR_CD, bwol.PROD_MNTH, bwol.PROD_WK_NO, bwol.CAR_SRS, mb.OCF_REGION_CD, mb.OCF_BUYER_GRP_CD, bwol.FEAT_CD, bwol.OCF_FRME_CD, sum(bwol.BUYER_GRP_OCF_USG_QTY) as Qty,bwol.FEAT_TYPE_CD, ")
			.append(" bwol.PLANT_CD,bwol.LINE_CLASS,bwol.PROD_DAY_NO,bwol.BUYER_GRP_OCF_LMT_QTY as BUYER_LIMIT,mb.BUYER_GRP_CD ")
			.append(" from TRN_BUYER_GRP_WKLY_OCF_LMT  bwol ")
			.append(" INNER JOIN MST_BUYER mb on mb.BUYER_GRP_CD = bwol.BUYER_GRP_CD ")
			.append(" where bwol.POR_CD=:porCd ")
			.append(" and bwol.PROD_MNTH >=:ordrTkBsPd ");

	public static final StringBuilder rngLvlInit = new StringBuilder()
			.append(" UPDATE TRN_REGIONAL_WKLY_OCF_LMT SET REGIONAL_OCF_USG_QTY = '0' where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk and OCF_BUYER_GRP_CD=:ocfByrGrp ")
			.append(" and OCF_REGION_CD=:ocfRgnCd and CAR_SRS=:carSrs and PLANT_CD=:plantCD and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder rngLvlSelect = new StringBuilder()
			.append(" SELECT * FROM TRN_REGIONAL_WKLY_OCF_LMT where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk and OCF_BUYER_GRP_CD=:ocfByrGrp ")
			.append(" and OCF_REGION_CD=:ocfRgnCd and CAR_SRS=:carSrs and PLANT_CD=:plantCD and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder rngLvlUpdate = new StringBuilder()
			.append(" UPDATE TRN_REGIONAL_WKLY_OCF_LMT SET REGIONAL_OCF_USG_QTY =:qty, UPDTD_BY='B000052',UPDTD_DT= sysdate where POR_CD = :porCd and PROD_MNTH=:prodMnth and PROD_WK_NO=:prodWk and OCF_BUYER_GRP_CD=:ocfByrGrp ")
			.append(" and OCF_REGION_CD=:ocfRgnCd and CAR_SRS=:carSrs and PLANT_CD=:plantCD and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo ");

	public static final StringBuilder rngLvlInsert = new StringBuilder()
			.append(" INSERT INTO TRN_REGIONAL_WKLY_OCF_LMT ")
			.append(" (CRTD_BY,CRTD_DT,POR_CD,PROD_MNTH,PROD_WK_NO,CAR_SRS,OCF_BUYER_GRP_CD,OCF_REGION_CD,FEAT_CD,OCF_FRME_CD,FEAT_TYPE_CD,REGIONAL_OCF_USG_QTY,REGIONAL_OCF_LMT_QTY,LINE_CLASS,PLANT_CD,PROD_DAY_NO) ")
			.append(" VALUES ('B000052',sysdate, :porCd,:prodMnth,:prodWk,:carSrs,:ocfByrGrp,:ocfRgnCd,:featCd,:ocfFrmCd,:featTypCd,:qty,:lqty,:lineClass,:plantCD,:prodDayNo) ");

	public static final StringBuilder breachRprtLnSmryY = new StringBuilder()
			.append(" select trwol.POR_CD,trwol.PROD_MNTH,trwol.PROD_WK_NO,trwol.OCF_REGION_CD,mf.OCF_BUYER_GRP_CD,trwol.CAR_SRS,'' as PLANT_CD,'' as LINE_CLASS,trwol.FEAT_CD,trwol.OCF_FRME_CD,mf.FEAT_SHRT_DESC,mf.FEAT_LNG_DESC, SUM(trwol.REGIONAL_OCF_LMT_QTY) as OCF_LIMIT,sum(trwol.REGIONAL_OCF_USG_QTY) as OCF_USG,trwol.FEAT_TYPE_CD from TRN_REGIONAL_WKLY_OCF_LMT trwol ");

	public static final StringBuilder breachRprtLnSmryN = new StringBuilder()
			.append(" select trwol.POR_CD,trwol.PROD_MNTH,trwol.PROD_WK_NO,trwol.OCF_REGION_CD,mf.OCF_BUYER_GRP_CD,trwol.CAR_SRS,trwol.PLANT_CD,trwol.LINE_CLASS,trwol.FEAT_CD,trwol.OCF_FRME_CD,mf.FEAT_SHRT_DESC,mf.FEAT_LNG_DESC, SUM(trwol.REGIONAL_OCF_LMT_QTY) as OCF_LIMIT,sum(trwol.REGIONAL_OCF_USG_QTY) as OCF_USG,trwol.FEAT_TYPE_CD from TRN_REGIONAL_WKLY_OCF_LMT trwol ");

	public static final StringBuilder breachRprtGrpByY = new StringBuilder()
			.append(" group by trwol.POR_CD,trwol.PROD_MNTH,trwol.PROD_WK_NO,trwol.OCF_REGION_CD,mf.OCF_BUYER_GRP_CD,trwol.CAR_SRS,trwol.FEAT_CD,mf.FEAT_SHRT_DESC,mf.FEAT_LNG_DESC,trwol.OCF_FRME_CD,trwol.FEAT_TYPE_CD ");

	public static final StringBuilder breachRprtGrpByN = new StringBuilder()
			.append(" group by trwol.POR_CD,trwol.PROD_MNTH,trwol.PROD_WK_NO,trwol.OCF_REGION_CD,mf.OCF_BUYER_GRP_CD,trwol.CAR_SRS,trwol.FEAT_CD,mf.FEAT_SHRT_DESC,mf.FEAT_LNG_DESC,trwol.OCF_FRME_CD,trwol.FEAT_TYPE_CD,trwol.PLANT_CD,trwol.LINE_CLASS ");

	public static final StringBuilder breachRprtMnthly = new StringBuilder()
			.append(" INNER JOIN MST_FEAT mf on mf.POR_CD = trwol.POR_CD and mf.CAR_SRS=trwol.CAR_SRS and mf.FEAT_CD=trwol.FEAT_CD and mf.FEAT_TYPE_CD = trwol.FEAT_TYPE_CD and mf.OCF_FRME_CD =trwol.OCF_FRME_CD and mf.OCF_REGION_CD =trwol.OCF_REGION_CD and mf.OCF_BUYER_GRP_CD =trwol.OCF_BUYER_GRP_CD ")

			.append(" where trwol.POR_CD=:porCd ")

			.append(" and trwol.PROD_MNTH >= :ordrTkBsMnth ")
			.append("AND trwol.OCF_FRME_CD = :ocfFrmCd AND trwol.FEAT_CD = :featCd")

			.append(" and trwol.PLANT_CD=:plantCD and trwol.LINE_CLASS=:lineClass ");

	public static final StringBuilder breachRprtWkly = new StringBuilder()
			.append(" INNER JOIN MST_FEAT mf on mf.POR_CD = trwol.POR_CD and mf.CAR_SRS=trwol.CAR_SRS and mf.FEAT_CD=trwol.FEAT_CD and mf.FEAT_TYPE_CD = trwol.FEAT_TYPE_CD and mf.OCF_FRME_CD =trwol.OCF_FRME_CD and mf.OCF_REGION_CD =trwol.OCF_REGION_CD and mf.OCF_BUYER_GRP_CD =trwol.OCF_BUYER_GRP_CD ")
			.append(" where trwol.POR_CD=:porCd ")

			.append(" and trwol.PROD_MNTH>= :ordrTkBsMnth ")
			.append(" and trwol.PROD_WK_NO >= :weekNo ")

			.append(" and trwol.PROD_MNTH <=:maxProdMnth and trwol.PROD_WK_NO<= :maxProdWk ")
			.append("AND trwol.OCF_FRME_CD = :ocfFrmCd AND trwol.FEAT_CD = :featCd")


			.append(" and trwol.PLANT_CD=:plantCD and trwol.LINE_CLASS=:lineClass ");

	/*
	 * public static final StringBuilder breachSubY = new StringBuilder()
	 * .append(b)
	 */

	public static final StringBuilder extraFetch = new StringBuilder()
			.append("select POR_CD,PROD_MNTH,PROD_WK_NO,CAR_SRS,BUYER_GRP_CD,OCF_FRME_CD,BUYER_GRP_OCF_LMT_QTY,BUYER_GRP_OCF_USG_QTY,PLANT_CD,LINE_CLASS,PROD_DAY_NO,FEAT_CD from TRN_BUYER_GRP_WKLY_OCF_LMT where POR_CD=:porCd and PROD_MNTH=:prodMnth and OCF_FRME_CD='00' and PLANT_CD=:plantCD ")
			.append("and LINE_CLASS=:lineClass and PROD_DAY_NO=:prodDayNo and CAR_SRS=:carSrs ");

	public static final StringBuilder rnglUsgQtyN = new StringBuilder()
			.append(" SELECT BGWL.POR_CD ,BGWL.PROD_MNTH ,BGWL.PROD_WK_NO, BGWL.CAR_SRS , ")
			.append(" BGWL.FEAT_CD , BGWL.OCF_FRME_CD  ,BGWL.BUYER_GRP_CD, BGWL.BUYER_GRP_OCF_LMT_QTY, SUM(BGWL.BUYER_GRP_OCF_USG_QTY ) as QTY, ")
			.append(" BGWL.FEAT_TYPE_CD , ")

			.append(" BGWL.PLANT_CD,BGWL.LINE_CLASS,BGWL.PROD_DAY_NO, ")

			.append("  MB.OCF_REGION_CD , MB.OCF_BUYER_GRP_CD ")
			.append(" FROM TRN_BUYER_GRP_WKLY_OCF_LMT BGWL, (select distinct PROD_REGION_CD, OCF_REGION_CD, OCF_BUYER_GRP_CD ,BUYER_GRP_CD from   MST_BUYER )  MB , MST_POR  ")

			.append("  WHERE BGWL.POR_CD            = :porCd ")
			.append("  AND BGWL.CAR_SRS             = :carSrs  ")
			.append("  AND BGWL.PROD_MNTH           >= :ordrTkBsPd  ")
			.append("  AND BGWL.OCF_FRME_CD = :ocfFrmCd ")
			.append("  AND MST_POR.POR_CD        = BGWL.POR_CD  ")
			.append("  AND MB.PROD_REGION_CD     = MST_POR.PROD_REGION_CD  ")
			.append("  AND MB.BUYER_GRP_CD        = BGWL.BUYER_GRP_CD  ")
			.append(" AND MB.OCF_REGION_CD        = :ocfRgnCd ")
			.append(" AND MB.OCF_BUYER_GRP_CD      = :ocfByrGrpCd ");

	public static final StringBuilder rnglUsgQtyN2 = new StringBuilder()

			.append("   GROUP BY BGWL.POR_CD ,BGWL.PROD_MNTH ,BGWL.PROD_WK_NO, BGWL.PROD_DAY_NO, BGWL.CAR_SRS ,  ")
			.append("  BGWL.FEAT_CD , BGWL.OCF_FRME_CD ,BGWL.BUYER_GRP_CD, BGWL.BUYER_GRP_OCF_LMT_QTY,  ")
			.append("  BGWL.FEAT_TYPE_CD ,BGWL.PLANT_CD, BGWL.LINE_CLASS, MB.OCF_REGION_CD , MB.OCF_BUYER_GRP_CD ");

	public static final String rgnlUsgQtyWkly = " and BGWL.prod_wk_no = :prodWk ";

	public static final StringBuilder rnglUsgQtyY = new StringBuilder()
			.append(" SELECT BGWL.POR_CD ,BGWL.PROD_MNTH ,BGWL.PROD_WK_NO, BGWL.CAR_SRS ,   ")
			.append("  BGWL.FEAT_CD , BGWL.OCF_FRME_CD ,BGWL.BUYER_GRP_CD, BGWL.BUYER_GRP_OCF_LMT_QTY ,SUM(BGWL.BUYER_GRP_OCF_USG_QTY ) as QTY,   ")
			.append("  BGWL.FEAT_TYPE_CD ,  ")

			.append(" BGWL.PLANT_CD,BGWL.LINE_CLASS,BGWL.PROD_DAY_NO, ")

			.append("  MB.OCF_REGION_CD , MB.OCF_BUYER_GRP_CD ")
			.append("  FROM   ")
			.append("    TRN_BUYER_GRP_WKLY_OCF_LMT BGWL, (select distinct PROD_REGION_CD, OCF_REGION_CD, OCF_BUYER_GRP_CD ,BUYER_GRP_CD from   MST_BUYER )  MB ,   ")
			.append("      MST_POR   ")
			.append("     WHERE BGWL.POR_CD            = :porCd   ")
			.append("     AND BGWL.CAR_SRS             = :carSrs    ")
			.append("   AND BGWL.PROD_MNTH           >= :ordrTkBsPd   ")
			.append("    AND BGWL.OCF_FRME_CD = :ocfFrmCd  ")
			.append("   AND MST_POR.POR_CD        = BGWL.POR_CD   ")
			.append("   AND MB.PROD_REGION_CD     = MST_POR.PROD_REGION_CD   ")
			.append("   AND MB.BUYER_GRP_CD        = BGWL.BUYER_GRP_CD   ")
			.append("  AND MB.OCF_REGION_CD        = :ocfRgnCd  ")
			.append("  AND MB.OCF_BUYER_GRP_CD      = :ocfByrGrpCd   ");

	public static final StringBuilder rnglUsgQtyY2 = new StringBuilder()

			.append("   GROUP BY BGWL.POR_CD ,BGWL.PROD_MNTH ,BGWL.PROD_WK_NO, BGWL.PROD_DAY_NO, BGWL.CAR_SRS ,  ")
			.append("  BGWL.FEAT_CD , BGWL.OCF_FRME_CD ,BGWL.BUYER_GRP_CD, BGWL.BUYER_GRP_OCF_LMT_QTY,  ")
			.append("  BGWL.FEAT_TYPE_CD ,BGWL.PLANT_CD, BGWL.LINE_CLASS, MB.OCF_REGION_CD , MB.OCF_BUYER_GRP_CD ");

private B000052QueryConstants(){
	
}

}
