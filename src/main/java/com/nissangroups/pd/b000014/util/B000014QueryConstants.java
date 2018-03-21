/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.util;

public class B000014QueryConstants {
	
	/** Constant extract Order_Take_Base_Month query when Production Order Stage Code is 10 - P1*/
	public static final StringBuilder OTBM_Query_PO_STG_CD_10 = new StringBuilder()
	.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd AND (STAGE_CD = 'D1' OR STAGE_CD = 'D2')");
	
	/** Constant extract Order_Take_Base_Month query when Production Order Stage Code is 20 - P1*/
	public static final StringBuilder OTBM_Query_PO_STG_CD_20 = new StringBuilder()
	.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd  AND (STAGE_CD = 'F1' OR STAGE_CD = 'F2')");
	
	/** Constant extract Order_Take_Base_Month query when UPDATE_ONLY_FLAG = 1 P1 */
	public static final StringBuilder OTBM_Query_PO_STG_CD_NOT_SO_SC = new StringBuilder()
	.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd  AND (STAGE_CD <> 'SO' AND STAGE_CD <> 'SC')");
	
	/** Constant extract Details from Monthly Batch Process Status table P2.1 */
	public static final StringBuilder EXT_DTLS_MNTHLY_BTCH_PRCS_STTS_TBL = new StringBuilder()
	.append("select POR_CD,CAR_SRS,ORDRTK_BASE_MNTH,PROD_MNTH_FRM,PROD_MNTH_TO,OCF_REGION_CD,OCF_BUYER_GRP_CD,PRMTR_1,PRMTR_2,PRMTR_3,PRMTR_4,PRMTR_5,MIN(SEQ_ID) "+
			" from MNTHLY_BATCH_PROCESS_STTS mbps where mbps.POR_CD = :porCd AND mbps.ORDRTK_BASE_MNTH = :otbm AND mbps.PROCESS_STTS_FLAG = 'U ' " +
			" AND mbps.BATCH_ID = 'PST-DRG-B000014' "+
			" and mbps.seq_id = (select max(seq_id) from MNTHLY_BATCH_PROCESS_STTS mbps "+
			" where POR_CD = :porCd AND BATCH_ID = 'PST-DRG-B000014'  AND ORDRTK_BASE_MNTH = :otbm AND mbps.PROCESS_STTS_FLAG = 'U ' ) "+ // Redmine id # 4124
			" GROUP BY POR_CD,CAR_SRS,ORDRTK_BASE_MNTH,PROD_MNTH_FRM,PROD_MNTH_TO,OCF_REGION_CD,OCF_BUYER_GRP_CD,PRMTR_1,PRMTR_2,PRMTR_3,PRMTR_4,PRMTR_5");
	
	/** Constant Update Monthly Batch Process Status table P2.2*/
	public static final StringBuilder UPT_MNTHLY_BTCH_PRCS_STTS_TBL_IN_PROGRESS = new StringBuilder()
	.append("UPDATE MNTHLY_BATCH_PROCESS_STTS mbps SET mbps.PROCESS_STTS_FLAG = 'IP' WHERE mbps.POR_CD = :porCd AND mbps.BATCH_ID = 'PST-DRG-B000014' "+
			" AND mbps.ORDRTK_BASE_MNTH = :otbm " +
			" and mbps.seq_id = (select max(seq_id) from MNTHLY_BATCH_PROCESS_STTS "+
			" where POR_CD = :porCd AND BATCH_ID = 'PST-DRG-B000014'  AND ORDRTK_BASE_MNTH = :otbm AND PROCESS_STTS_FLAG = 'U ')");
	
	/** Constant extract Buyer Group, Buyer Code, Buyer ID, OSEI ID P3 */
	public static final StringBuilder EXT_BYRGRP_BYRCD_BYRID_OSEIID = new StringBuilder()
	.append("SELECT mstbyr.BUYER_CD,mstbyr.OCF_BUYER_GRP_CD,mstoeibyr.OEI_BUYER_ID,mstosei.OSEI_ID,trnmo.MS_QTY,trnmo.ORDR_QTY from MST_POR mstpor, " +
	"MST_BUYER mstbyr,MST_OEI_BUYER mstoeibyr,MST_OSEI mstosei,TRN_MNTHLY_ORDR trnmo WHERE "+
	"mstbyr.PROD_REGION_CD = mstpor.PROD_REGION_CD AND mstbyr.BUYER_CD = mstoeibyr.BUYER_CD AND  mstpor.POR_CD = :porCd "+
	"AND mstbyr.OCF_BUYER_GRP_CD = :byrGrpCd AND mstosei.OEI_BUYER_ID = mstoeibyr.OEI_BUYER_ID AND mstosei.POR_CD = :porCd "+
	"AND mstoeibyr.POR_CD = :porCd AND trnmo.POR_CD = :porCd AND trnmo.ORDRTK_BASE_MNTH = :otbm AND trnmo.PROD_MNTH > :prdMnthFrm  AND "+
	"trnmo.PROD_MNTH < :prdMnthTo AND trnmo.PROD_ORDR_STAGE_CD = :prdOrdStgCd AND trnmo.OSEI_ID = mstosei.OSEI_ID ");
	
	/** Constant extract Frame Code, Feature Code from Buyer_Monthly_OCF_Usage_Trn P4.1 */
	
	public static final StringBuilder EXT_FRMCD_FEATURECD_PRDMNTH = new StringBuilder()
	.append("SELECT tbmou.OCF_FRME_CD,tbmou.FEAT_CD,tbmou.PROD_MNTH from MST_POR mstpor, MST_BUYER mstbuyer, TRN_BUYER_MNTHLY_OCF_USG tbmou WHERE " +
	" tbmou.POR_CD = :porCd AND tbmou.ORDR_TAKE_BASE_MNTH = :otbm AND tbmou.PROD_MNTH > :prdMnthFrm AND tbmou.PROD_MNTH < :prdMnthTo AND " +
	" tbmou.OSEI_ID = :oseiID AND tbmou.CAR_SRS = :carSrs AND tbmou.BUYER_GRP_CD = :byrGrpCd AND mstbuyer.BUYER_GRP_CD = :byrGrpCd AND "+
	" mstbuyer.OCF_REGION_CD = :ocfRegionCd AND mstbuyer.BUYER_GRP_CD = :byrGrpCd AND mstbuyer.PROD_REGION_CD = mstpor.PROD_REGION_CD AND "+
	" mstpor.POR_CD = :porCd AND tbmou.FEAT_CD = :param5 AND tbmou.OCF_FRME_CD = '00'");
	
	/** Update - Initialization of Usage Qty P4.2 */
	
	public static final StringBuilder INIT_TRN_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
	.append("UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT tbgmol SET tbgmol.BUYER_GRP_OCF_USG_QTY = 0  WHERE tbgmol.POR_CD = :porCd AND "+
	"tbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND tbgmol.PROD_MNTH > :prdMnthFrm AND tbgmol.PROD_MNTH < :prdMnthTo AND "+
	"tbgmol.CAR_SRS = :carSrs AND tbgmol.BUYER_GRP_CD = :byrGrpCd AND MST_BUYER.BUYER_GRP_CD = :byrGrpCd AND "+
	"MST_BUYER.OCF_REGION_CD = :ocfRegionCd AND MST_BUYER.OCF_BUYER_GRP_CD = :ocfByrGrpCd AND MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD "+
	"AND MST_POR.POR_CD = :porCd AND tbgmol.FEAT_CD = :param5 AND tbgmol.OCF_FRME_CD = '00'");
	
	/** Extract Regional OCF Limit P6.1 */
	
	public static final StringBuilder EXT_REGIONAL_OCF_LIMIT = new StringBuilder()
	.append("SELECT * from TRN_REGIONAL_MNTHLY_OCF_LMT trmol WHERE trmol.POR_CD = :porCd AND trmol.ORDR_TAKE_BASE_MNTH = :otbm AND "+
	"trmol.PROD_MNTH = :prdMnth AND trmol.CAR_SRS = :carSrs AND trmol.OCF_REGION_CD = :ocfRegionCd AND trmol.OCF_BUYER_GRP_CD = :ocfByrGrpCd "+
	 "AND trmol.FEAT_CD = :featCd AND trmol.OCF_FRME_CD = :ocfFrmCd");
	
	
	/** Update - TRN_BUYER_GRP_MNTHLY_OCF_LMT P7.1 */
	
	public static final StringBuilder UPT_TRN_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
	.append("UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT tbgmol SET tbgmol.BUYER_GRP_SIMU_QTY = :simQty , UPDTD_BY = 'B000014', mbps.UPDTD_DT = sysdate WHERE tbgmol.POR_CD = :porCd AND "+
	"tbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND tbgmol.PROD_MNTH = :prdMnth AND tbgmol.CAR_SRS = :carSrs AND tbgmol.BUYER_GRP_CD = :byrGrpCd AND "+
	"tbgmol.FEAT_CD = :featCd AND tbgmol.OCF_FRME_CD = :ocfFrmCd");
	
	/** Constant Update Monthly Batch Process Status table P7.2 */
	public static final StringBuilder UPT_MNTHLY_BTCH_PRCS_STTS_TBL_SUCCESS = new StringBuilder()
	.append("UPDATE MNTHLY_BATCH_PROCESS_STTS mbps SET mbps.PROCESS_STTS_FLAG = 'S' , mbps.UPDTD_BY = 'B000014', mbps.UPDTD_DT = sysdate  WHERE mbps.POR_CD = :porCd AND mbps.BATCH_ID = 'PST-DRG-B000014' "+
			" AND mbps.ORDRTK_BASE_MNTH = :otbm and mbps.seq_id = (select max(seq_id) from MNTHLY_BATCH_PROCESS_STTS "+
			" where POR_CD = :porCd AND BATCH_ID = 'PST-DRG-B000014'  AND ORDRTK_BASE_MNTH = :otbm AND PROCESS_STTS_FLAG = 'IP')");
	
	/** Constant Update Monthly Batch Process Status table P7.3 */
	public static final StringBuilder UPT_MNTHLY_BTCH_PRCS_STTS_TBL_FAILURE = new StringBuilder()
	.append("UPDATE MNTHLY_BATCH_PROCESS_STTS mbps SET mbps.PROCESS_STTS_FLAG = 'F' , UPDTD_BY = 'B000014', mbps.UPDTD_DT = sysdate WHERE mbps.POR_CD = :porCd AND mbps.BATCH_ID = 'PST-DRG-B000014' "+
			" AND mbps.ORDRTK_BASE_MNTH = :otbm and mbps.seq_id = (select max(seq_id) from MNTHLY_BATCH_PROCESS_STTS "+
			" where POR_CD = :porCd AND BATCH_ID = 'PST-DRG-B000014'  AND ORDRTK_BASE_MNTH = :otbm AND PROCESS_STTS_FLAG = 'U ')");
	
	/** Constant Order Qty, MS Qty Extraction  - Main Part - Start*/
	
	public static final StringBuilder EXT_ORDR_QTY_MS_QTY = new StringBuilder()	
	.append(" select  mst_osei.oei_buyer_id, TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS, " +
	" TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD , TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ,mst_buyer.OCF_BUYER_GRP_CD, " + 
	" mst_buyer.OCF_REGION_CD, sum(trn_mnthly_ordr.ORDR_QTY) , sum(trn_mnthly_ordr.MS_QTY) , TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD, "+
	" TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH,FEATURE.FEAT_CD ,FEATURE.ocf_frme_cd, FEATURE.feat_type_cd "+
	" from trn_mnthly_ordr "+
	" INNER join mst_osei_dtl on trn_mnthly_ordr.osei_id = mst_osei_dtl.osei_id "+
	" and trn_mnthly_ordr.por_cd = mst_osei_dtl.por_cd "+ 
	" inner join MST_OSEI on  mst_osei_dtl.osei_id = MST_OSEI.osei_id "+
	" and mst_osei_dtl.POR_CD = MST_OSEI.POR_CD "+ 
	" inner join mst_oei_buyer on  MST_OSEI.por_cd = mst_oei_buyer.por_cd "+
	" and MST_OSEI.oei_buyer_id = mst_oei_buyer.oei_buyer_id "+  
	" INNER join mst_oei_spec on mst_oei_buyer.por_cd = mst_oei_spec.por_cd "+
	" and mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id "+
	" INNER JOIN  MST_POR ON mst_oei_buyer.por_cd = MST_POR.POR_CD "+ 
	" INNER JOIN MST_BUYER ON MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD ");
	
	// OCF Part
	public static final StringBuilder OCF_PART = new StringBuilder()	 
	.append(" INNER JOIN MST_OEI_FEAT  FEATURE ON "+
	" MST_OSEI.OEI_BUYER_ID = FEATURE.OEI_BUYER_ID "+
	" AND MST_OSEI.POR_cd = FEATURE.POR_CD "+
	" AND FEATURE.FEAT_TYPE_CD IN ('10','20','70') "+
	" AND ( substr(FEATURE.OEIF_ADPT_DATE,0,6) <= trn_mnthly_ordr.PROD_MNTH ) "+ 
	" AND ( substr(FEATURE.OEIF_ABLSH_DATE,0,6) > trn_mnthly_ordr.PROD_MNTH ) ");
	
	// CCF Part
	
	public static final StringBuilder CCF_PART = new StringBuilder()
	.append(" INNER JOIN MST_OSEI_FEAT  FEATURE ON "+
	" MST_OSEI.OSEI_ID = FEATURE.OSEI_id "+
	" AND MST_OSEI.POR_cd = FEATURE.POR_CD "+
	" AND FEATURE.FEAT_TYPE_CD IN ('40','50','80') "+
	" AND ( substr(FEATURE.OSEIF_ADPT_DATE,0,6) <= trn_mnthly_ordr.PROD_MNTH ) " +
	" AND ( substr(FEATURE.OSEIF_ABLSH_DATE,0,6) > trn_mnthly_ordr.PROD_MNTH ) ");
	
	// End Part
	public static final StringBuilder EXT_ORDR_QTY_MS_QTY_END = new StringBuilder()
	.append(
	" INNER JOIN TRN_BUYER_GRP_MNTHLY_OCF_LMT ON TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD = trn_mnthly_ordr.POR_CD "+
	" and TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = trn_mnthly_ordr.ORDRTK_BASE_MNTH "+
	" and TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH = trn_mnthly_ordr.PROD_MNTH "+
	" AND mst_oei_buyer.BUYER_CD = MST_BUYER.BUYER_CD where trn_mnthly_ordr.por_cd = :porCd "+
	" AND trn_mnthly_ordr.ordrtk_base_mnth = :otbm "+
	" AND trn_mnthly_ordr.PROD_MNTH BETWEEN :prdMnthFrm AND :prdMnthTo " +
	" GROUP BY  mst_osei.oei_buyer_id, TRN_BUYER_GRP_MNTHLY_OCF_LMT.CAR_SRS, "+
	" TRN_BUYER_GRP_MNTHLY_OCF_LMT.BUYER_GRP_CD , TRN_BUYER_GRP_MNTHLY_OCF_LMT.PROD_MNTH ,mst_buyer.OCF_BUYER_GRP_CD, "+
	" mst_buyer.OCF_REGION_CD,  TRN_BUYER_GRP_MNTHLY_OCF_LMT.POR_CD, "+
	" TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH,FEATURE.FEAT_CD ,FEATURE.ocf_frme_cd, FEATURE.feat_type_cd "); 
	
	// End Part
	public static final StringBuilder EXT_REG_MNTHLY_OCF_LMT = new StringBuilder()
	.append
	(" select REGIONAL_OCF_LMT_QTY from TRN_REGIONAL_MNTHLY_OCF_LMT where POR_CD = :porCd and ORDR_TAKE_BASE_MNTH= :otbm and "+
	" PROD_MNTH = :prdMnth and CAR_SRS = :carSrs and OCF_REGION_CD = :ocfRegionCd and OCF_BUYER_GRP_CD = :ocfByrGrpCd and FEAT_CD = :featCd ");
	
	// Reg Mnthly OCF Lmt Zero
	public static final StringBuilder EXT_REG_MNTHLY_OCF_LMT_FRAME_CODE_ZERO = new StringBuilder()
	.append(" and OCF_FRME_CD = '00' ");
	
	// Reg Mnthly OCF Lmt Not Zero
	public static final StringBuilder EXT_REG_MNTHLY_OCF_LMT_FRAME_CODE_NOT_ZERO = new StringBuilder()
	.append(" and OCF_FRME_CD != '00' ");
	
	// Constant Order Qty, MS Qty Extraction
	public static final StringBuilder EXT_ORDR_QTY_MS_QTY1_MAIN_PART = new StringBuilder()
	.append(" select  DISTINCT "+   
			" mst_oei_spec.CAR_SRS, "+			
			" trn_mnthly_ordr.PROD_MNTH ,"+
			" mst_buyer.OCF_BUYER_GRP_CD,"+ 
			" mst_buyer.OCF_REGION_CD,"+ 
			" sum(trn_mnthly_ordr.ORDR_QTY) , "+
			" sum(trn_mnthly_ordr.MS_QTY) , "+ 
			" trn_mnthly_ordr.POR_CD,"+
			" trn_mnthly_ordr.ORDRTK_BASE_MNTH,"+
			" TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD "+
			" from trn_mnthly_ordr "+
			" INNER join mst_osei_dtl on trn_mnthly_ordr.osei_id = mst_osei_dtl.osei_id "+ 
			" and trn_mnthly_ordr.por_cd = mst_osei_dtl.por_cd "+
			" inner join MST_OSEI on  mst_osei_dtl.osei_id = MST_OSEI.osei_id "+
			" and mst_osei_dtl.POR_CD = MST_OSEI.POR_CD "+
			" inner join mst_oei_buyer on  MST_OSEI.por_cd = mst_oei_buyer.por_cd "+
			" and MST_OSEI.oei_buyer_id = mst_oei_buyer.oei_buyer_id "+ 
			" INNER join mst_oei_spec on mst_oei_buyer.por_cd = mst_oei_spec.por_cd "+
			" and mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id "+
			" INNER JOIN  MST_POR ON mst_oei_buyer.por_cd = MST_POR.POR_CD "+
			" INNER JOIN MST_BUYER ON MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD "+
			" AND MST_BUYER.BUYER_CD =mst_oei_buyer.BUYER_CD "+
			" INNER JOIN TRN_BUYER_MNTHLY_OCF_USG on "+
			" TRN_BUYER_MNTHLY_OCF_USG.POR_CD =trn_mnthly_ordr.POR_CD "+
			" and TRN_BUYER_MNTHLY_OCF_USG.ORDR_TAKE_BASE_MNTH = trn_mnthly_ordr.ORDRTK_BASE_MNTH "+
			" and TRN_BUYER_MNTHLY_OCF_USG.PROD_MNTH =trn_mnthly_ordr.PROD_MNTH "+
			" and TRN_BUYER_MNTHLY_OCF_USG.OSEI_ID = trn_mnthly_ordr.OSEI_ID "+
			" where trn_mnthly_ordr.por_cd = :porCd and trn_mnthly_ordr.ordrtk_base_mnth = :otbm "+ 
			" AND trn_mnthly_ordr.PROD_MNTH BETWEEN :prdMnthFrm AND :prdMnthTo ");
	
	public static final StringBuilder FEATURE_CD_PART = new StringBuilder()
	.append(" AND TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD = :featCd ");
	
	public static final StringBuilder OCF_FRAME_CD_00_PART = new StringBuilder()
	.append(" AND TRN_BUYER_MNTHLY_OCF_USG.OCF_FRME_CD = '00' ");
	
	public static final StringBuilder OCF_FRAME_CD_NOT_00_PART = new StringBuilder()
	.append(" AND TRN_BUYER_MNTHLY_OCF_USG.OCF_FRME_CD != '00' ");
	
	public static final StringBuilder EXT_ORDR_QTY_MS_QTY1_GROUP_BY = new StringBuilder()
	.append(" GROUP BY  "+
			" mst_oei_spec.CAR_SRS, "+
			//" mst_buyer.BUYER_GRP_CD , "+
			" trn_mnthly_ordr.PROD_MNTH ,"+
			" mst_buyer.OCF_BUYER_GRP_CD, "+ 
			" mst_buyer.OCF_REGION_CD, "+ 
			" trn_mnthly_ordr.POR_CD, "+
			" trn_mnthly_ordr.ORDRTK_BASE_MNTH, "+
			" TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD ");
	
	
	

	public static final StringBuilder EXT_ORDR_QTY_MS_QTY2_MAIN_PART = new StringBuilder()
	.append(" select  "+  
	" mst_oei_spec.CAR_SRS,"+
	" mst_buyer.BUYER_GRP_CD ,"+ 
	" trn_mnthly_ordr.PROD_MNTH ,"+
	" mst_buyer.OCF_BUYER_GRP_CD, "+
	" mst_buyer.OCF_REGION_CD, "+
	" sum(trn_mnthly_ordr.ORDR_QTY) , "+
	" sum(trn_mnthly_ordr.MS_QTY) , "+ 
	" trn_mnthly_ordr.POR_CD, "+
	" trn_mnthly_ordr.ORDRTK_BASE_MNTH, "+
	" TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD "+
	" from trn_mnthly_ordr "+
	" INNER join mst_osei_dtl on trn_mnthly_ordr.osei_id = mst_osei_dtl.osei_id "+
	" and trn_mnthly_ordr.por_cd = mst_osei_dtl.por_cd "+
	" inner join MST_OSEI on  mst_osei_dtl.osei_id = MST_OSEI.osei_id "+
	" and mst_osei_dtl.POR_CD = MST_OSEI.POR_CD "+
	" inner join mst_oei_buyer on  MST_OSEI.por_cd = mst_oei_buyer.por_cd "+
	" and MST_OSEI.oei_buyer_id = mst_oei_buyer.oei_buyer_id  "+
	" INNER join mst_oei_spec on mst_oei_buyer.por_cd = mst_oei_spec.por_cd "+
	" and mst_oei_buyer.oei_spec_id = mst_oei_spec.oei_spec_id "+
	" INNER JOIN  MST_POR ON mst_oei_buyer.por_cd = MST_POR.POR_CD "+
	" INNER JOIN MST_BUYER ON MST_BUYER.PROD_REGION_CD = MST_POR.PROD_REGION_CD "+
	" AND MST_BUYER.BUYER_CD =mst_oei_buyer.BUYER_CD "+
	" INNER JOIN TRN_BUYER_MNTHLY_OCF_USG on "+
	" TRN_BUYER_MNTHLY_OCF_USG.POR_CD =trn_mnthly_ordr.POR_CD "+
	" and TRN_BUYER_MNTHLY_OCF_USG.ORDR_TAKE_BASE_MNTH = trn_mnthly_ordr.ORDRTK_BASE_MNTH "+
	" and TRN_BUYER_MNTHLY_OCF_USG.PROD_MNTH =trn_mnthly_ordr.PROD_MNTH "+
	" and TRN_BUYER_MNTHLY_OCF_USG.OSEI_ID = trn_mnthly_ordr.OSEI_ID "+
	" where trn_mnthly_ordr.por_cd = :porCd "+ 
	" AND trn_mnthly_ordr.ordrtk_base_mnth = :otbm "+ 
	" and MST_BUYER.OCF_BUYER_GRP_CD = :ocfByrGrpCd "+
	" and MST_BUYER.OCF_REGION_CD = :ocfRegionCd "+
	" and TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD = :featCd "+
	" and mst_oei_spec.CAR_SRS = :carSrs "+
	" AND trn_mnthly_ordr.PROD_MNTH = :prdMnth  ");
	
	
public static final StringBuilder EXT_ORDR_QTY_MS_QTY2_GROUP_BY = new StringBuilder()
.append(" GROUP BY  "+
	" mst_oei_spec.CAR_SRS, "+
	" mst_buyer.BUYER_GRP_CD , "+
	" trn_mnthly_ordr.PROD_MNTH ,"+
	" mst_buyer.OCF_BUYER_GRP_CD, "+ 
	" mst_buyer.OCF_REGION_CD, "+ 
	" trn_mnthly_ordr.POR_CD, "+
	" trn_mnthly_ordr.ORDRTK_BASE_MNTH, "+
	" TRN_BUYER_MNTHLY_OCF_USG.FEAT_CD ");
	
/**
* Instantiates a new b000014 query constants.
*/
private B000014QueryConstants() {
	
}
	
}
