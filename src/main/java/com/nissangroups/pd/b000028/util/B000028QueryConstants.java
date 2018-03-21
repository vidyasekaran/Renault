/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.util;

/**
 * Constant file to keep the queries related to the batch B000028.
 * 
 * @author z015060
 *
 */
public class B000028QueryConstants {

	public static final StringBuilder getCrSrsHrznQry = new StringBuilder()
			.append("select mp.CAR_SRS,mp.CAR_SRS_ORDR_HRZN,mp.CAR_SRS_ADPT_DATE,mp.CAR_SRS_ABLSH_DATE from TRN_MNTHLY_ORDR tn ")
			.append(" inner join MST_OSEI mo on(tn.OSEI_ID=mo.OSEI_ID) inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID)")
			.append(" inner join MST_POR_CAR_SRS mp on (mp.CAR_SRS=ms.CAR_SRS and mp.PROD_FMY_CD=ms.PROD_FMY_CD and mp.POR_CD=tn.POR_CD)")
			.append(" where tn.POR_CD= :porCd and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth and mp.CAR_SRS_ADPT_DATE is not null and mp.CAR_SRS_ABLSH_DATE is not null")
			.append(" group by mp.CAR_SRS,mp.CAR_SRS_ORDR_HRZN,mp.CAR_SRS_ADPT_DATE,mp.CAR_SRS_ABLSH_DATE ");

	public static final StringBuilder getbyrGrpLvlSelQry = new StringBuilder()
			.append("select mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD ");

	public static final StringBuilder getbyrGrpLvlMainQry = new StringBuilder()
			.append(" from TRN_MNTHLY_ORDR tn   inner join MST_OSEI mo on(tn.OSEI_ID=mo.OSEI_ID)")
			.append(" inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID) inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID)")
			.append(" inner join MST_POR_CAR_SRS mp on (mp.CAR_SRS=ms.CAR_SRS and mp.PROD_FMY_CD=ms.PROD_FMY_CD and mp.POR_CD=tn.POR_CD)")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD) inner join MST_POR mpp on (mpp.PROD_REGION_CD=mbb.PROD_REGION_CD and mpp.POR_CD=tn.POR_CD)");

	public static final StringBuilder getbyrGrpLvlEndQry = new StringBuilder()
			.append(" where tn.POR_CD= :porCd and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth and mp.CAR_SRS= :carSrs and tn.SUSPENDED_ORDR_FLAG != 1 ")
			.append(" and tn.PROD_MNTH >= :prdMnthFrm and tn.PROD_MNTH <= :prdMnthTo group by mp.CAR_SRS,tn.PROD_MNTH,mbb.BUYER_GRP_CD ");

	public static final StringBuilder getbyrGrpOCfLmtQry = new StringBuilder()
			.append(" select tb.CAR_SRS,mb.OCF_REGION_CD,tb.PROD_MNTH,tb.BUYER_GRP_CD,NVL(MAX(tb.BUYER_GRP_OCF_LMT_QTY),0) as ordrQty  from TRN_BUYER_GRP_MNTHLY_OCF_LMT tb ")
			.append(" inner join  MST_BUYER mb on (mb.BUYER_GRP_CD=tb.BUYER_GRP_CD) inner join MST_por mp on (mp.PROD_REGION_CD=mb.PROD_REGION_CD and mp.por_cd=tb.POR_CD) ")
			.append(" where tb.OCF_FRME_CD='00' and tb.ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and  tb.POR_CD= :porCd  ")
			.append(" group by tb.CAR_SRS,mb.OCF_REGION_CD,tb.PROD_MNTH,tb.BUYER_GRP_CD,tb.BUYER_GRP_OCF_LMT_QTY ");

	public static final StringBuilder getByrGrpCdLvlDataQry = new StringBuilder()
			.append("  select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD ");

	public static final StringBuilder getByrGrpCdLvlEndDataQry = new StringBuilder()
			.append(" where tn.por_Cd= :porCd and tn.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd and tn.SUSPENDED_ORDR_FLAG != 1 and mp.CAR_SRS= :carSrs and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth")
			.append(" and tn.OSEI_ID not in ( select OSEI_ID from MST_OSEI_FRZN where por_cd= :porCd and FRZN_ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth and FRZN_PROD_MNTH= :prdMnth ) ")
			.append(" group by tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,mbb.BUYER_CD,mbb.BUYER_GRP_CD");

	public static final StringBuilder getOeiByrIDLvlQry = new StringBuilder()
			.append("select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID ");

	public static final StringBuilder getOeiByrIDLvlEndQry = new StringBuilder()
			.append(" where tn.por_Cd= :porCd and tn.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd  and mbb.BUYER_CD= :byrCd and tn.SUSPENDED_ORDR_FLAG != 1 and mp.CAR_SRS= :carSrs and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth")
			.append(" and tn.OSEI_ID not in ( select OSEI_ID from MST_OSEI_FRZN where por_cd= :porCd and FRZN_ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth and FRZN_PROD_MNTH= :prdMnth ) ")
			.append(" group by tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID");

	public static final StringBuilder insertAdjstAlloc = new StringBuilder()
			.append(" insert into TMP_AUTO_ADJUST_ORDR_ALLOC (POR_CD, CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,RATIO,ALLOCATION,PRCSS_FLG,CRTD_BY,CRTD_DT,OEI_BUYER_ID,OSEI_ID,POT_CD)")
			.append(" values (:porCd, :carSrs, :prdMnth, :byrGrpCd, :byrCd, :ratio, :alloc, :prcsFlag, :crtdBy , sysdate, :oeiByrId ,:oseiId, :potCd ) ");

	public static final StringBuilder getMnthlyOCFTrn = new StringBuilder()
			.append(" select tb.POR_CD,tb.ORDR_TAKE_BASE_MNTH,tb.CAR_SRS,tb.BUYER_GRP_CD,tb.PROD_MNTH,tb.FEAT_CD,tb.FEAT_TYPE_CD,tb.OCF_FRME_CD,")
			.append(" sum(tm.AUTO_ADJST_ORDR_QTY) as auto_adjusted_order_qty ,tm.OSEI_ID from TRN_BUYER_MNTHLY_OCF_USG tb")
			.append(" inner join TRN_MNTHLY_ORDR tm on (tm.OSEI_ID=tb.OSEI_ID and tm.POR_CD=tb.POR_CD)")
			.append(" where tb.POR_CD= :porCd and tb.ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and tb.PROD_MNTH= :prdMnth")
			.append(" and tb.CAR_SRS= :carSrs and tb.BUYER_GRP_CD= :byrGrpCd and tm.PROD_MNTH= :prdMnth ")
			.append(" and tm.ORDRTK_BASE_MNTH= :ordrTkBsMnth group by tb.POR_CD,tb.BUYER_GRP_CD,tb.FEAT_CD,tb.FEAT_TYPE_CD,tb.CAR_SRS,tb.OCF_FRME_CD,tb.PROD_MNTH,")
			.append(" tb.ORDR_TAKE_BASE_MNTH, tm.OSEI_ID ");

	public static final StringBuilder delAutoAdjust = new StringBuilder()
			.append(" delete from TMP_AUTO_ADJUST_ORDR_ALLOC ");

	public static final StringBuilder getdatafromTempBuyrCD = new StringBuilder()
			.append(" select POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,allocation from TMP_AUTO_ADJUST_ORDR_ALLOC ")
			.append(" where BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and CAR_SRS= :carSrs  and PRCSS_FLG='1'")
			.append(" group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,allocation")
			.append(" order by allocation DESC");

	public static final StringBuilder getRoundedAllocByrGrp = new StringBuilder()
			.append(" Select POR_CD,sum(allocation) from TMP_AUTO_ADJUST_ORDR_ALLOC")
			.append(" where CAR_SRS= :carSrs and BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and PRCSS_FLG='1' ")
			.append(" group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD");

	public static final StringBuilder getByrMnthlyTemp = new StringBuilder()
			.append("select POR_CD,ORDR_TAKE_BASE_MNTH,CAR_SRS,BUYER_GRP_CD,PROD_MNTH,OCF_FRME_CD,SUM( BUYER_OCF_USG_QTY) ")
			.append(" from TMP_TRN_BUYER_MNTHLY_OCF_USG WHERE POR_CD= :porCd and ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth ")
			.append(" group by POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,OCF_FRME_CD ");

	public static final StringBuilder getBreachOCFCheck = new StringBuilder()
			.append("  select tb.POR_CD,tb.ORDR_TAKE_BASE_MNTH,tb.CAR_SRS,tb.PROD_MNTH,tb.BUYER_GRP_CD, :ocfUsgQty as auto_Adjusted_qty,sum(tb.BUYER_GRP_OCF_LMT_QTY),mf.FEAT_CD,")
			.append(" sum(tb.BUYER_GRP_OCF_LMT_QTY)- :ocfUsgQty as diff,mf.FEAT_SHRT_DESC,")
			.append(" mf.FEAT_LNG_DESC,mb.OCF_REGION_CD from TRN_BUYER_GRP_MNTHLY_OCF_LMT tb inner join MST_FEAT mf on(mf.FEAT_CD=tb.FEAT_CD and mf.FEAT_TYPE_CD=tb.FEAT_TYPE_CD and mf.OCF_FRME_CD= tb.OCF_FRME_CD)")
			.append(" inner join MST_BUYER mb on (mb.OCF_REGION_CD=mf.OCF_REGION_CD and mb.OCF_BUYER_GRP_CD=mf.OCF_BUYER_GRP_CD) inner join MST_POR mp on (mp.PROD_REGION_CD=mb.PROD_REGION_CD and mp.POR_CD=tb.POR_CD)")
			.append(" where tb.POR_CD= :porCd and tb.ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and tb.PROD_MNTH= :prdMnth and tb.CAR_SRS= :carSrs ")
			.append(" and tb.BUYER_GRP_CD= :byrGrpCd and mf.POR_CD= :porCd and mf.CAR_SRS= :carSrs ");
	

	public static final StringBuilder getBreachOCFCheckC1 = new StringBuilder()
			.append(" and tb.OCF_FRME_CD= '00' and tb.BUYER_GRP_OCF_LMT_QTY != :ocfUsgQty");

	public static final StringBuilder getBreachOCFCheckC2 = new StringBuilder()
			.append(" and tb.BUYER_GRP_OCF_LMT_QTY < :ocfUsgQty ");
	
	public static final StringBuilder getBreachOCFCheckC3= new StringBuilder()
	.append("  group by tb.POR_CD,tb.ORDR_TAKE_BASE_MNTH,tb.CAR_SRS,tb.PROD_MNTH,tb.BUYER_GRP_CD,mf.FEAT_CD,mf.FEAT_SHRT_DESC, mf.FEAT_LNG_DESC,mb.OCF_REGION_CD");

	public static final StringBuilder getByrCdActQry = new StringBuilder()
			.append(" select POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,ALLOCATION from TMP_AUTO_ADJUST_ORDR_ALLOC where PRCSS_FLG='2' ");

	public static final StringBuilder getdatafromTempOeiBuyerId = new StringBuilder()
			.append(" select POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,allocation from TMP_AUTO_ADJUST_ORDR_ALLOC ")
			.append(" where BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and CAR_SRS= :carSrs and BUYER_CD= :byrCd and PRCSS_FLG='3' ")
			.append(" group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,allocation")
			.append(" order by allocation DESC");

	public static final StringBuilder getRoundedAllocOeiBuyer = new StringBuilder()
			.append(" Select POR_CD,sum(allocation) from TMP_AUTO_ADJUST_ORDR_ALLOC")
			.append(" where CAR_SRS= :carSrs and BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and BUYER_CD= :byrCd and PRCSS_FLG='3' ")
			.append(" group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD");

	public static final StringBuilder getClrLvlQry = new StringBuilder()
			.append("select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID ");

	public static final StringBuilder getClrLvlEndQry = new StringBuilder()
			.append(" where tn.por_Cd= :porCd and tn.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd  and mbb.BUYER_CD= :byrCd ")
			.append("and tn.SUSPENDED_ORDR_FLAG != 1 and mp.CAR_SRS= :carSrs and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth and mb.OEI_BUYER_ID = :oeiByrId")
			.append(" and tn.OSEI_ID not in ( select OSEI_ID from MST_OSEI_FRZN where por_cd= :porCd and FRZN_ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth and FRZN_PROD_MNTH= :prdMnth ) ")
			.append(" group by tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID");

	public static final StringBuilder getOeiByrIdActQry = new StringBuilder()
			.append(" select POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,ALLOCATION from TMP_AUTO_ADJUST_ORDR_ALLOC where PRCSS_FLG='4' ");

	public static final StringBuilder getdataClrLvlAutoAlloc = new StringBuilder()
			.append(" select POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,allocation from TMP_AUTO_ADJUST_ORDR_ALLOC ")
			.append(" where BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and CAR_SRS= :carSrs and BUYER_CD= :byrCd and OEI_BUYER_ID= :oeiByrId and PRCSS_FLG='5' ")
			.append(" group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,allocation")
			.append(" order by allocation DESC");

	public static final StringBuilder getRoundedAllocClrLvl = new StringBuilder()
			.append(" Select POR_CD,sum(allocation) from TMP_AUTO_ADJUST_ORDR_ALLOC")
			.append(" where CAR_SRS= :carSrs and BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and BUYER_CD= :byrCd and PRCSS_FLG='5'")
			.append(" and OEI_BUYER_ID= :oeiByrId group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD");

	public static final StringBuilder getPotLvlQry = new StringBuilder()
			.append("select tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,sum(tn.ORDR_QTY),mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID,tn.pot_cd ");

	public static final StringBuilder getPotLvlEndQry = new StringBuilder()
			.append(" where tn.por_Cd= :porCd and tn.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd  and mbb.BUYER_CD= :byrCd ")
			.append("and tn.SUSPENDED_ORDR_FLAG != 1 and mp.CAR_SRS= :carSrs and tn.ORDRTK_BASE_MNTH= :ordrTkBsMnth and mb.OEI_BUYER_ID = :oeiByrId and mo.OSEI_ID= :oseiId")
			.append(" and tn.OSEI_ID not in ( select OSEI_ID from MST_OSEI_FRZN where por_cd= :porCd and FRZN_ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth and FRZN_PROD_MNTH= :prdMnth ) ")
			.append(" group by tn.por_Cd,mp.CAR_SRS,tn.PROD_MNTH,mbb.BUYER_GRP_CD,mbb.BUYER_CD,mb.OEI_BUYER_ID,mo.OSEI_ID,tn.pot_cd");

	public static final StringBuilder getClrActQry = new StringBuilder()
			.append(" select POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,ALLOCATION from TMP_AUTO_ADJUST_ORDR_ALLOC where PRCSS_FLG='6' ");

	public static final StringBuilder getdataPotLvlAutoAlloc = new StringBuilder()
			.append(" select POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,POT_cd,allocation from TMP_AUTO_ADJUST_ORDR_ALLOC ")
			.append(" where BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and CAR_SRS= :carSrs and BUYER_CD= :byrCd and OEI_BUYER_ID= :oeiByrId and PRCSS_FLG='7' ")
			.append(" and OSEI_ID= :oseiId group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,POT_cd,allocation")
			.append(" order by allocation DESC");

	public static final StringBuilder getRoundedAllocPotLvl = new StringBuilder()
			.append(" Select POR_CD,sum(allocation) from TMP_AUTO_ADJUST_ORDR_ALLOC")
			.append(" where CAR_SRS= :carSrs and BUYER_GRP_CD= :byrGrpCd and PROD_MNTH= :prdMnth and BUYER_CD= :byrCd and PRCSS_FLG='7'")
			.append(" and OEI_BUYER_ID= :oeiByrId  and OSEI_ID= :oseiId group by POR_CD,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID");

	public static final StringBuilder getPotLvlActQry = new StringBuilder()
			.append(" select POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD,BUYER_CD,OEI_BUYER_ID,OSEI_ID,POT_CD,ALLOCATION from TMP_AUTO_ADJUST_ORDR_ALLOC where PRCSS_FLG='8' ");

	public static final StringBuilder updateMnthOrdrTrnInitQry = new StringBuilder()
			.append("  Update TRN_MNTHLY_ORDR set AUTO_ADJST_ORDR_QTY='0', UPDTD_BY= :updtBy , UPDTD_DT= sysdate  where POR_CD= :porCd and ORDRTK_BASE_MNTH= :ordrTkBsMnth ");

	public static final StringBuilder updateMnthOrdrTrnOrdrQtyQry = new StringBuilder()
			.append(" Update TRN_MNTHLY_ORDR set AUTO_ADJST_ORDR_QTY=ORDR_QTY where POR_CD= :porCd and ORDRTK_BASE_MNTH= :ordrTkBsMnth ")
			.append(" and PROD_MNTH between :prdMnthFrm and :prdMnthTo  and SUSPENDED_ORDR_FLAG != 1 and OSEI_ID in ( ")
			.append(" select mo.OSEI_ID from MST_OSEI mo  inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID) ")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD ) inner join MST_POR mp on (mp.PROD_REGION_CD=mbb.PROD_REGION_CD and mp.POR_CD=mb.POR_CD) ")
			.append(" inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID) ")
			.append(" inner join MST_POR_CAR_SRS mc on (mc.CAR_SRS=ms.CAR_SRS and mc.PROD_FMY_CD=ms.PROD_FMY_CD) where mc.CAR_SRS= :carSrs) ");

	public static final StringBuilder updateMnthOrdrTrnQry = new StringBuilder()
			.append(" Update TRN_MNTHLY_ORDR set AUTO_ADJST_ORDR_QTY=AUTO_ADJST_ORDR_QTY + :autoAdjstQty ")
			.append(" where POR_CD= :porCd and ORDRTK_BASE_MNTH= :ordrTkBsMnth and PROD_MNTH = :prdMnth and OSEI_ID = :oseiId and POT_CD= :potCd ");

	public static final StringBuilder getAutoAdjustDataQry = new StringBuilder()
			.append(" select tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as ordrQty, mbb.BUYER_GRP_CD,mbb.OCF_REGION_CD,tb.CAR_SRS,")
			.append(" NVL(tb.BUYER_GRP_OCF_LMT_QTY,0) as allocation, sum(tr.AUTO_ADJST_ORDR_QTY) as ordrQtyToPlnt, sum(tr.AUTO_ADJST_ORDR_QTY) + sum(tr.ORDR_QTY)")
			.append(" as adjustOrdrQty from TRN_MNTHLY_ORDR tr")
			.append(" inner join MST_OSEI mo on(tr.OSEI_ID=mo.OSEI_ID) inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append(" inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID and ms.POR_CD=tr.POR_CD)")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD) ")
			.append(" inner join MST_POR mpp on (mpp.PROD_REGION_CD=mbb.PROD_REGION_CD and mpp.POR_CD=tr.POR_CD) ")
			.append(" inner join TRN_BUYER_GRP_MNTHLY_OCF_LMT tb on (tb.PROD_MNTH=tr.PROD_MNTH and tb.CAR_SRS=ms.CAR_SRS and tb.POR_CD=tr.POR_CD) ")
			.append(" where tr.POR_CD= :porCd and tr.PROD_MNTH= :prdMnth and tb.BUYER_GRP_CD= :byrGrpCd and tr.ORDRTK_BASE_MNTH= :ordrTkBsMnth and tb.OCF_FRME_CD='00' ")
			.append(" and mbb.BUYER_GRP_CD= :byrGrpCd and tb.ORDR_TAKE_BASE_MNTH= :ordrTkBsMnth and ms.car_SRS= :carSrs and tr.SUSPENDED_ORDR_FLAG != 1")
			.append(" group by tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,mbb.BUYER_GRP_CD,mbb.OCF_REGION_CD,tb.CAR_SRS,tb.BUYER_GRP_OCF_LMT_QTY ");

	public static final StringBuilder getAutoAdjustDataByrCdQry = new StringBuilder()
			.append("select tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			.append(" mbb.OCF_REGION_CD,ms.CAR_SRS, sum(tr.AUTO_ADJST_ORDR_QTY) as autoAdjust, sum(tr.AUTO_ADJST_ORDR_QTY) + sum(tr.ORDR_QTY) as adjustOrdrQty ");

	public static final StringBuilder getAutoAdjustMainCase20 = new StringBuilder()
			.append("from TRN_MNTHLY_ORDR tr inner join MST_OSEI mo on(tr.OSEI_ID=mo.OSEI_ID) inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID)")
			.append("inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID and ms.POR_CD=tr.POR_CD) inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD)")
			.append("inner join MST_POR mpp on (mpp.PROD_REGION_CD=mbb.PROD_REGION_CD and mpp.POR_CD=tr.POR_CD) ")
			.append(" where tr.POR_CD= :porCd and tr.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd and tr.ORDRTK_BASE_MNTH= :ordrTkBsMnth ")
			.append(" and mbb.BUYER_GRP_CD= :byrGrpCd and tr.ORDRTK_BASE_MNTH= :ordrTkBsMnth and ms.car_SRS= :carSrs and tr.SUSPENDED_ORDR_FLAG != 1")
			.append(" group by tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,mbb.BUYER_GRP_CD,mbb.BUYER_CD,mbb.OCF_REGION_CD,ms.CAR_SRS ");

	public static final StringBuilder getAutoAdjustDataClrvlQry = new StringBuilder()
			.append(" select tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			.append(" mbb.OCF_REGION_CD,ms.CAR_SRS,ms.APPLD_MDL_CD,ms.PCK_CD,ms.ADTNL_SPEC_CD, mo.EXT_CLR_CD,mo.INT_CLR_CD,tr.POT_CD,")
			.append(" me.EX_NO,sum(tr.AUTO_ADJST_ORDR_QTY) as orderQtyToPlant, sum(tr.AUTO_ADJST_ORDR_QTY) - sum(tr.ORDR_QTY) as  adjustOrdrQty ");

	public static final StringBuilder getAutoAdjustMainCase30 = new StringBuilder()
			.append(" from TRN_MNTHLY_ORDR tr inner join MST_OSEI mo on(tr.OSEI_ID=mo.OSEI_ID) inner join MST_OEI_BUYER mb on (mb.OEI_BUYER_ID=mo.OEI_BUYER_ID) ")
			.append(" inner join MST_OEI_SPEC ms on (ms.OEI_SPEC_ID=mb.OEI_SPEC_ID and ms.POR_CD=tr.POR_CD) ")
			.append(" inner join MST_BUYER mbb on (mbb.BUYER_CD=mb.BUYER_CD) inner join MST_POR mpp on (mpp.PROD_REGION_CD=mbb.PROD_REGION_CD and mpp.POR_CD=tr.POR_CD)")
			.append(" left join MST_EX_NO me on (me.POR_CD=tr.POR_CD and me.OEI_BUYER_ID=mo.OEI_BUYER_ID and tr.PROD_MNTH=me.PROD_MNTH) ")
			.append(" where tr.POR_CD= :porCd and tr.PROD_MNTH= :prdMnth and mbb.BUYER_GRP_CD= :byrGrpCd and tr.ORDRTK_BASE_MNTH= :ordrTkBsMnth ")
			.append(" and mbb.BUYER_GRP_CD= :byrGrpCd and tr.ORDRTK_BASE_MNTH= :ordrTkBsMnth and ms.CAR_SRS= :carSrs and mbb.BUYER_CD= :byrCd")
			.append(" and tr.PROD_MNTH= :prdMnth and tr.SUSPENDED_ORDR_FLAG != 1 group by tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			.append(" mbb.OCF_REGION_CD,ms.CAR_SRS,ms.APPLD_MDL_CD,ms.PCK_CD,ms.ADTNL_SPEC_CD, mo.EXT_CLR_CD,mo.INT_CLR_CD,tr.POT_CD,me.EX_NO");

	public static final StringBuilder getbyrGrpCaseTwoQry = new StringBuilder()
			.append("select tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			.append(" mbb.OCF_REGION_CD,ms.CAR_SRS ");

	public static final StringBuilder getbyrGrpCaseCLrLvlQry = new StringBuilder()
	.append(" select tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
	.append(" mbb.OCF_REGION_CD,ms.CAR_SRS,ms.APPLD_MDL_CD,ms.PCK_CD,ms.ADTNL_SPEC_CD, mo.EXT_CLR_CD,mo.INT_CLR_CD,tr.POT_CD, me.EX_NO ");
	
	
	public static final StringBuilder getPotLvlByrGrp = new StringBuilder()
	.append(" select POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD from TMP_AUTO_ADJUST_ORDR_ALLOC where PRCSS_FLG='8' group by  POR_CD,CAR_SRS,PROD_MNTH,BUYER_GRP_CD ");

	public static final StringBuilder delTmpByrMnthOcfUsg = new StringBuilder()
	.append(" delete from TMP_TRN_BUYER_MNTHLY_OCF_USG ");

	public static final StringBuilder getOrdTkBsMnthQry = new StringBuilder()
	.append("select POR_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where STAGE_CD in ('F1','F1') and POR_CD= :porCd and STAGE_STTS_CD='C' and STAGE_CD= :prdStgCd  ");
	
	private B000028QueryConstants() {

	}

}
