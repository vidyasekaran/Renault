/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :OR Ordering
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.util;

/**
 * Constant file to keep the queries related to the batch B000008.
 * 
 * @author z015060
 *
 */
public class B000008QueryConstants {

	/** query Constant checkOverlapPeriodExists . */
	public static final StringBuilder checkOverlapPeriodExists = new StringBuilder()
			.append("select  m.id.ordrTakeBaseMnth,m.stageCd FROM MstMnthOrdrTakeBasePd m where m.id.porCd= :porCd and m.id.ordrTakeBaseMnth= :ordrTkBsMnth and m.stageCd != :stageCode ");

	/** query Constant getCarSeriesHorizon . */
	public static final StringBuilder getCarSeriesHorizon = new StringBuilder()
			.append("select m.id.porCd, m.id.prodFmyCd, m.id.carSrs, m.carSrsOrdrHrzn FROM MstPorCarSr m WHERE m.id.porCd= :porCd ");

	/** query Constant getPorHorizon . */
	public static final StringBuilder getPorHorizon = new StringBuilder()
			.append("select m.porHrzn,m.porCd  FROM MstPor m WHERE m.porCd= :porCd ");

	/** query Constant getPreStageOrdrQty . */
	public static final StringBuilder getPreStageOrdrQtyQuery = new StringBuilder()
			.append("select POR_CD,PROD_MNTH,OSEI_ID,POT_CD,ORDR_QTY,ORDRTK_BASE_MNTH from TRN_MNTHLY_ORDR where POR_CD= :porCd  ")
			.append("and ORDRTK_BASE_MNTH= :ordrTkBsMnth AND PROD_MNTH > :ordrTkBsMnth and PROD_ORDR_STAGE_CD= :productionStageCode ");

	/** query Constant frznTypeCdQuery . */
	public static final StringBuilder getfrznTypeCdQty = new StringBuilder()
			.append("select FRZN_TYPE_CD from MST_OSEI_FRZN where por_cd= :porCd and osei_id= :OSEI and FRZN_ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth ")
			.append(" and FRZN_PROD_MNTH= :Frozen_Production_Month  ");

	/** query Constant getmasterTrnOrdrQuery . */
	public static final StringBuilder getMasterTrnOrdrQuery = new StringBuilder()
			.append("select t.POR_CD,t.PROD_MNTH,count(t.PROD_ORDR_NO),t.OSEI_ID,t.POT_CD,m.FRZN_TYPE_CD from TRN_LTST_MST_SHDL t")
			.append(" left join MST_OSEI_FRZN m on (m.FRZN_PROD_MNTH=t.PROD_MNTH  and m.POR_CD=t.POR_CD) ")
			.append(" where t.PROD_MNTH >= :ordrTkBsMnth and m.POR_CD= :porCd and m.FRZN_ORDR_TAKE_BASE_MNTH < :ordrTkBsMnth ")
			.append(" group by  t.POR_CD,t.PROD_MNTH,t.OSEI_ID,t.POT_CD,m.FRZN_TYPE_CD ");

	/** query Constant updateProdStageCdQuery . */
	public static final StringBuilder updateProdStageCdQuery = new StringBuilder()
			.append("UPDATE TRN_MNTHLY_ORDR set DRAFT_QTY=ORDR_QTY, PROD_ORDR_STAGE_CD= :productionStageCode, UPDTD_BY= :updtBy , UPDTD_DT= sysdate ")
			.append(" where ORDRTK_BASE_MNTH= :ordrTkBsMnth and POR_CD= :porCd and PROD_ORDR_STAGE_CD='10' ");

	/** query Constant chekDupinMnthOrdrQuery . */
	public static final StringBuilder chekDupinMnthOrdrQuery = new StringBuilder()
			.append("select count(*) from TRN_MNTHLY_ORDR")
			.append(" where POR_CD= :porCd and ORDRTK_BASE_MNTH= :ordrTkBsMnth and PROD_MNTH= :ProdMonth ")
			.append(" and PROD_ORDR_STAGE_CD= :productionStageCode and OSEI_ID= :OSEI and POT_CD= :potCd ");

	/** query Constant insertMonthlyOrderTrnQuery . */
	public static final StringBuilder insertMonthlyOrderTrnQuery = new StringBuilder()
			.append(" insert into TRN_MNTHLY_ORDR (POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,OSEI_ID,POT_CD,")
			.append(" PROD_ORDR_STAGE_CD,DRAFT_QTY , MS_QTY, ORDR_QTY, SIMU_QTY, AUTO_ADJST_ORDR_QTY, SUSPENDED_ORDR_FLAG, CRTD_BY , CRTD_DT)")
			.append(" VALUES ( :porCd, :ordrTkBsMnth, :ProdMonth, :OSEI, :potCd , :productionStageCode,")
			.append(" :draftQty, :msQty, :ordrQty,  :simuQty , :autoAdjustOrdrQty , :suspendedOrdrFlag, :crtdBy , sysdate ) ");

	/** query Constant insertMonthlyOrderTrnQuery . */
	public static final StringBuilder updateMonthlyOrderTrnQuery = new StringBuilder()
			.append(" update TRN_MNTHLY_ORDR set MS_QTY= :msQty, ORDR_QTY= :ordrQty, UPDTD_BY= :updtBy , UPDTD_DT= sysdate ")
			.append(" where POR_CD= :porCd and ORDRTK_BASE_MNTH= :ordrTkBsMnth and PROD_MNTH= :ProdMonth ")
			.append(" and PROD_ORDR_STAGE_CD= :productionStageCode and OSEI_ID= :OSEI and POT_CD= :potCd ");

	/** query constant selectMonthlyOrderTrn */
	public static final StringBuilder selectMnthlyOrdrTrn = new StringBuilder()
			.append(" select POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,PROD_ORDR_STAGE_CD,OSEI_ID,POT_CD from trn_mnthly_ordr")
			.append(" where POR_CD= :porCd AND ORDRTK_BASE_MNTH= :ordrTkBsMnth  and PROD_MNTH= :ProdMonth")
			.append(" and PROD_ORDR_STAGE_CD= :productionStageCode and OSEI_ID= :OSEI and POT_CD= :potCd");

	/** query Constant extractFrmMstPrmtr . */
	public static final StringBuilder extractFrmMstPrmtr = new StringBuilder()
			.append("select VAL1,VAL2 from Mst_prmtr where PRMTR_CD= :parameterCd and KEY1= :key1 ");

	/** query Constant getOrdrableEndItemQuery . */
	public static final StringBuilder getOrdrableEndItemQuery = new StringBuilder()
			.append("select ms.por_cd,mo.OSEI_ID, mbb.BUYER_GRP_CD from MST_OEI_SPEC  ms")
			.append(" LEFT JOIN MST_OEI_BUYER mb on (mb.OEI_SPEC_ID=ms.OEI_SPEC_ID)")
			.append(" LEFT JOIN MST_OSEI mo on (mo.OEI_BUYER_ID=mb.OEI_BUYER_ID)")
			.append(" LEFT JOIN MST_OSEI_DTL md on (md.OSEI_ID=mo.OSEI_ID)")
			.append(" LEFT JOIN MST_POR mp on (mp.POR_CD=mo.POR_CD)")
			.append(" LEFT JOIN MST_BUYER mbb on (mbb.PROD_REGION_CD=mp.PROD_REGION_CD and mbb.buyer_cd=mb.buyer_cd)");

	public static final StringBuilder getOrdrableEndItemQueryp1 = new StringBuilder()
			.append(" LEFT JOIN SPEC_REEXECUTE_STATUS se on (se.POR=md.POR_CD) ");

	public static final StringBuilder getOrdrableEndItemQueryp2 = new StringBuilder()
			.append(" where ms.POR_CD= :porCd and ms.PROD_STAGE_CD in ( :productionStageCode) and ms.CAR_SRS= :carSrs ")
			.append(" and md.POR_CD= :porCd and md.END_ITM_STTS_CD='30' and md.OSEI_ADPT_DATE <= :ProdMonth ")
			.append(" and md.OSEI_SUSPENDED_DATE > :ProdMonth and ms.PROD_FMY_CD =:prodFmyCd and mbb.BUYER_GRP_CD is not null ");

	public static final StringBuilder getOrdrableEndItemQueryp3 = new StringBuilder()
			.append(" and se.POR= :porCd  and md.UPDTD_DT > se.REFERENCE_TIME and se.TABLE_NAME= :tableName and TRIM(se.BATCH_ID)= :batchId ");

	public static final StringBuilder getOrdrableEndItemQueryp4 = new StringBuilder()
	.append(" group by ms.POR_CD,mo.OSEI_ID,mbb.BUYER_GRP_CD ");
	
		/** query Constant getPotCdQuery . */
	public static final StringBuilder getPotCdQuery = new StringBuilder()
			.append("select val1,val2 from MST_PRMTR where PRMTR_CD= :parameterCd and KEY1= :key1 and key2= :key2 ");

	/** query Constant insertMonthlyOrderTrnfromOEIQuery . */
	public static final StringBuilder insertMonthlyOrderTrnfromOEIQuery = new StringBuilder()
			.append(" insert into TRN_MNTHLY_ORDR (POR_CD,ORDRTK_BASE_MNTH,PROD_MNTH,OSEI_ID,POT_CD,")
			.append(" PROD_ORDR_STAGE_CD,DRAFT_QTY , MS_QTY, ORDR_QTY, SIMU_QTY, AUTO_ADJST_ORDR_QTY, SUSPENDED_ORDR_FLAG ,  CRTD_BY , CRTD_DT)")
			.append(" VALUES ( :porCd, :ordrTkBsMnth, :ProdMonth, :OSEI, :potCd , :productionStageCode,")
			.append(" :draftQty, :msQty, :ordrQty,  :simuQty , :autoAdjustOrdrQty , :suspendedOrdrFlag, :crtdBy , sysdate ) ");

	/** query Constant NscFrcstQuery . */
	public static final StringBuilder NscFrcstQuery = new StringBuilder()
			.append("select tm.POR_CD,tm.ORDRTK_BASE_MNTH,tm.PROD_MNTH,mbb.BUYER_GRP_CD,ms.CAR_SRS")
			.append(" from TRN_MNTHLY_ORDR tm left join MST_OSEI mo on (tm.OSEI_ID=mo.OSEI_ID)")
			.append(" left join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" left join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID)")
			.append(" left join MST_POR mp on (mp.POR_CD=ms.POR_CD)")
			.append(" left join MST_BUYER mbb on (mbb.PROD_REGION_CD=mp.PROD_REGION_CD and mbb.BUYER_CD=mb.BUYER_CD)")
			.append(" where tm.POR_CD= :porCd and tm.ORDRTK_BASE_MNTH= :ordrTkBsMnth ")
			.append(" and tm.PROD_MNTH >= :ordrTkBsMnth and tm.PROD_MNTH <= :MaxProdMnth and tm.SUSPENDED_ORDR_FLAG='0' ")
			.append(" and ms.CAR_SRS= :carSrs and ms.PROD_FMY_CD= :prodFmyCd  and ms.POR_CD= :porCd")
			.append(" and mbb.BUYER_GRP_CD is not null  and ms.CAR_SRS is not null ")
			.append(" GROUP BY tm.POR_CD,tm.ORDRTK_BASE_MNTH,tm.PROD_MNTH, mbb.BUYER_GRP_CD,ms.CAR_SRS ");

	/** query Constant chekDupNscFrcstVolQuery . */
	public static final StringBuilder chekDupNscFrcstVolQuery = new StringBuilder()
			.append("select count(*) from TRN_NSC_FRCST_VOL ")
			.append(" where POR_CD= :porCd and ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and PROD_MNTH= :ProdMonth ")
			.append(" and BUYER_GRP_CD= :buyerGrpCd and CAR_SRS= :carSrs  ");

	/** query Constant insertNscFrcstVolQuery . */
	public static final StringBuilder insertNscFrcstVolQuery = new StringBuilder()
			.append("insert  into TRN_NSC_FRCST_VOL (POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,BUYER_GRP_CD,CAR_SRS,FRCST_VOL ,CRTD_BY , CRTD_DT) ")
			.append(" VALUES (:porCd , :ordrTkBsMnth , :ProdMonth , :buyerGrpCd , :carSrs ,:frcstVol,:crtdBy , sysdate) ");

	/** query Constant NscConfQuery . */
	public static final StringBuilder NscConfQuery = new StringBuilder()
			.append("select tm.POR_CD,tm.ORDRTK_BASE_MNTH,mbb.BUYER_GRP_CD,ms.CAR_SRS")
			.append(" from TRN_MNTHLY_ORDR tm left join MST_OSEI mo on (tm.OSEI_ID=mo.OSEI_ID)")
			.append(" left join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" left join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID)")
			.append(" left join MST_POR mp on (mp.POR_CD=ms.POR_CD)")
			.append(" left join MST_BUYER mbb on (mbb.PROD_REGION_CD=mp.PROD_REGION_CD and mbb.BUYER_CD=mb.BUYER_CD)")
			.append(" where tm.POR_CD= :porCd and tm.ORDRTK_BASE_MNTH= :ordrTkBsMnth  and tm.SUSPENDED_ORDR_FLAG='0' ")
			.append(" and ms.CAR_SRS= :carSrs and ms.PROD_FMY_CD= :prodFmyCd  and ms.POR_CD= :porCd")
			.append(" and  mbb.BUYER_GRP_CD is not null  and ms.CAR_SRS is not null GROUP BY tm.POR_CD,tm.ORDRTK_BASE_MNTH,tm.PROD_MNTH,")
			.append(" mbb.BUYER_GRP_CD,ms.CAR_SRS ");

	/** query Constant chekDupNscConfQuery . */
	public static final StringBuilder chekDupNscConfQuery = new StringBuilder()
			.append("select count(*) from TRN_NSC_CNFRMTN_MNTHLY ")
			.append(" where POR_CD= :porCd and ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and PROD_ORDR_STAGE_CD= :productionStageCode ")
			.append(" and BUYER_GRP_CD= :buyerGrpCd and CAR_SRS= :carSrs and PROD_ORDR_STAGE_CD= :productionStageCode  ");

	/** query Constant insertNscConfQuery . */
	public static final StringBuilder insertNscConfQuery = new StringBuilder()
			.append("insert  into TRN_NSC_CNFRMTN_MNTHLY (POR_CD,ORDR_TAKE_BASE_MNTH,PROD_ORDR_STAGE_CD,BUYER_GRP_CD,CAR_SRS,NSC_CMPLT_FLAG,CRTD_BY , CRTD_DT) ")
			.append(" VALUES (:porCd , :ordrTkBsMnth , :productionStageCode , :buyerGrpCd , :carSrs ,:nscCmptFlg,:crtdBy , sysdate ) ");

	/** query Constant selectSuspndDataQuery . */
	public static final StringBuilder selectSuspndDataQuery = new StringBuilder()
			.append("select tm.POR_CD,tm.ORDRTK_BASE_MNTH,tm.PROD_MNTH,tm.POT_CD,tm.OSEI_ID,tm.PROD_ORDR_STAGE_CD from TRN_MNTHLY_ORDR tm ")
			.append(" left join MST_OSEI mo on (tm.OSEI_ID=mo.OSEI_ID) ")
			.append(" left join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" left join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID)")
			.append(" LEFT JOIN MST_OSEI_DTL md on (md.OSEI_ID=mo.OSEI_ID)")
			.append(" where tm.POR_CD= :porCd and tm.ORDRTK_BASE_MNTH= :ordrTkBsMnth AND tm.prod_mnth >= :ordrTkBsMnth")
			.append(" and ms.CAR_SRS= :carSrs and ms.PROD_FMY_CD= :prodFmyCd and tm.PROD_ORDR_STAGE_CD= :productionStageCode ")
			.append(" and md.OSEI_ADPT_DATE <= tm.prod_mnth and md.OSEI_SUSPENDED_DATE > tm.prod_mnth ");

	/** query Constant updateNonSuspndQuery . */
	public static final StringBuilder updateNonSuspndQuery = new StringBuilder()
			.append("update TRN_MNTHLY_ORDR set SUSPENDED_ORDR_FLAG='0', UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR_CD= :porCd ")
			.append(" and ORDRTK_BASE_MNTH= :ordrTkBsMnth and PROD_MNTH= :ProdMonth ")
			.append(" and OSEI_ID = :OSEI and PROD_ORDR_STAGE_CD= :productionStageCode  ");

	/** query Constant updateSuspndQuery . */
	public static final StringBuilder updateSuspndQuery = new StringBuilder()
			.append("update TRN_MNTHLY_ORDR set SUSPENDED_ORDR_FLAG='1' , UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR_CD= :porCd ")
			.append(" and ORDRTK_BASE_MNTH= :ordrTkBsMnth and PROD_ORDR_STAGE_CD= :productionStageCode  ");


	/** query Constant updateSpecRefQuery . */
	public static final StringBuilder updateSpecRefQuery = new StringBuilder()
			.append(" UPDATE SPEC_REEXECUTE_STATUS SET PROCESS_EXECUTED_TIME= :currentTime , ")
			.append(" REFERENCE_TIME = :refTme, UPDTD_BY= :updtBy , UPDTD_DT= sysdate where POR= :porCd and TRIM(BATCH_ID)= :batchId ")
			.append(" and TABLE_NAME= :tableName ");

	/**
	 * Instantiates a new B000008 query constants.
	 */
	private B000008QueryConstants() {

	}

}
