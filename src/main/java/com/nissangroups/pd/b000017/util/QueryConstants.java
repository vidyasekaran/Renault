/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000017.util;

public class QueryConstants {
		/** Constant extract Order_Take_Base_Month query when Production Order Stage Code is 10*/
		public static final StringBuilder OTBM_Query_PO_STG_CD_10 = new StringBuilder()
		.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd AND (STAGE_CD = 'D1' OR STAGE_CD = 'D2')");
		
		/** Constant extract Order_Take_Base_Month query when Production Order Stage Code is 20*/
		public static final StringBuilder OTBM_Query_PO_STG_CD_20 = new StringBuilder()
		.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd  AND (STAGE_CD = 'F1' OR STAGE_CD = 'F2')");
		
		/** Constant extract Order_Take_Base_Month query when UPDATE_ONLY_FLAG = 1 */
		public static final StringBuilder OTBM_Query_PO_STG_CD_NOT_SO_SC = new StringBuilder()
		.append("SELECT POR_CD,STAGE_CD,ORDR_TAKE_BASE_MNTH from MST_MNTH_ORDR_TAKE_BASE_PD where POR_CD = :porCd  AND (STAGE_CD <> 'SO' AND STAGE_CD <> 'SC')");
		
		/** Constant extract Updated OSEI Id In Spec Master  P2.1a */
		public static final StringBuilder UPDATED_OSEI_ID_IN_SPEC_MASTER = new StringBuilder()
		.append("SELECT osei_dtl.OSEI_ID,osei.OEI_BUYER_ID from MST_OSEI_DTL osei_dtl,SPEC_REEXECUTE_STATUS srsts,TRN_MNTHLY_ORDR tmnodr,MST_OSEI osei WHERE " +
	            "tmnodr.POR_CD = :porCd AND tmnodr.ORDRTK_BASE_MNTH = :otbm AND osei_dtl.POR_CD = :porCd AND " +
	            "osei_dtl.OSEI_ID = tmnodr.OSEI_ID AND srsts.POR = :porCd AND srsts.TABLE_NAME = 'MST_OSEI_DTL' AND srsts.BATCH_ID='B000017' " + 
	            "AND osei_dtl.UPDTD_DT > srsts.REFERENCE_TIME  AND osei.OSEI_ID = osei_dtl.OSEI_ID AND osei.POR_CD = :porCd");
		
		/** Constant extract Feature Updated OSEI Id in MST_OSEI_FEAT P2.1.b.1*/
		public static final StringBuilder UPDATED_FEATURE_OSEI_ID_IN_ORDRLE_SLS_FEAT = new StringBuilder()
		.append("SELECT osei_feat.OSEI_ID from MST_OSEI_FEAT osei_feat,SPEC_REEXECUTE_STATUS srsts,TRN_MNTHLY_ORDR tmnodr WHERE " + 
	     "tmnodr.POR_CD = :porCd AND tmnodr.ORDRTK_BASE_MNTH = :otbm  AND osei_feat.OSEI_ID = tmnodr.OSEI_ID AND osei_feat.POR_CD = :porCd " +
	     "AND osei_feat.UPDTD_DT > srsts.REFERENCE_TIME  AND srsts.BATCH_ID='B000017' AND srsts.TABLE_NAME = 'MST_OSEI_FEAT' AND srsts.POR = :porCd");
		
		/** Constant extract Feature updated BUYER Id in MST_OEI_FEAT P2.1.b.2*/
		public static final StringBuilder UPDATED_FEATURE_OEI_BUYER_ID_IN_MST_OEI_FEAT = new StringBuilder()
		.append("SELECT oei_feat.OEI_BUYER_ID from MST_OEI_FEAT oei_feat, SPEC_REEXECUTE_STATUS srsts, TRN_MNTHLY_ORDR tmnodr,MST_OSEI osei WHERE " +
		"tmnodr.POR_CD = :porCd AND tmnodr.ORDRTK_BASE_MNTH = :otbm " +
		"AND tmnodr.OSEI_ID = osei.OSEI_ID " +
		"AND oei_feat.POR_CD = :porCd AND oei_feat.UPDTD_DT > srsts.REFERENCE_TIME AND srsts.BATCH_ID = 'B000017' AND srsts.TABLE_NAME = 'MST_OEI_FEAT' "+
		"AND srsts.POR = :porCd AND oei_feat.OEI_BUYER_ID = osei.OEI_BUYER_ID"); 
		
		/** Constant extract Order Details P2.2 - Update Flag*/
		
		public static final StringBuilder ORDER_DETAILS_UPT_FLG = new StringBuilder()
		.append("SELECT oei_spec.CAR_SRS,osei_dtl.OSEI_ID,osei.OEI_BUYER_ID,tmo.PROD_MNTH,buyer.BUYER_GRP_CD,tmo.SUSPENDED_ORDR_FLAG,tmo.MS_QTY,tmo.ORDR_QTY,osei_dtl.UPDTD_DT from TRN_MNTHLY_ORDR tmo,MST_OSEI osei,MST_OEI_BUYER oei_buyer,MST_OEI_SPEC oei_spec,SPEC_REEXECUTE_STATUS srsts,MST_OSEI_DTL osei_dtl, "+
		"MST_POR por,MST_BUYER buyer , MST_OEI_BUYER buyer_oei where tmo.POR_CD = :porCd AND tmo.ORDRTK_BASE_MNTH = :otbm "+
		"AND osei_dtl.POR_CD = :porCd  AND osei_dtl.OSEI_ID = tmo.OSEI_ID AND osei.OSEI_ID = osei_dtl.OSEI_ID  AND  osei.OEI_BUYER_ID = oei_buyer.OEI_BUYER_ID "+
		"AND por.POR_CD = :porCd AND buyer.PROD_REGION_CD = por.PROD_REGION_CD AND buyer.BUYER_CD = buyer_oei.BUYER_CD "+
		"AND oei_spec.OEI_SPEC_ID = buyer_oei.OEI_SPEC_ID AND  oei_spec.POR_CD = :porCd AND srsts.BATCH_ID = 'B000017' "+
		"AND srsts.TABLE_NAME = 'MST_OSEI_DTL' AND srsts.POR = :porCd AND osei_dtl.UPDTD_DT > srsts.REFERENCE_TIME");
		
		/** Constant extract Order Details P2.2 */
		public static final StringBuilder ORDER_DETAILS = new StringBuilder()
		.append("SELECT oei_spec.CAR_SRS,osei_dtl.OSEI_ID,osei.OEI_BUYER_ID,tmo.PROD_MNTH,buyer.BUYER_GRP_CD,tmo.SUSPENDED_ORDR_FLAG,tmo.MS_QTY,tmo.ORDR_QTY,osei_dtl.UPDTD_DT from TRN_MNTHLY_ORDR tmo,MST_OSEI osei,MST_OEI_BUYER oei_buyer,MST_OEI_SPEC oei_spec,SPEC_REEXECUTE_STATUS srsts,MST_OSEI_DTL osei_dtl, "+
		"MST_POR por,MST_BUYER buyer , MST_OEI_BUYER buyer_oei where tmo.POR_CD = :porCd AND tmo.ORDRTK_BASE_MNTH = :otbm "+
		"AND osei_dtl.POR_CD = :porCd  AND osei_dtl.OSEI_ID = tmo.OSEI_ID AND osei.OSEI_ID = osei_dtl.OSEI_ID  AND  osei.OEI_BUYER_ID = oei_buyer.OEI_BUYER_ID "+
		"AND por.POR_CD = :porCd AND buyer.PROD_REGION_CD = por.PROD_REGION_CD AND buyer.BUYER_CD = buyer_oei.BUYER_CD "+
		"AND oei_spec.OEI_SPEC_ID = buyer_oei.OEI_SPEC_ID AND  oei_spec.POR_CD = :porCd AND srsts.BATCH_ID = 'B000017' "+
		"AND srsts.TABLE_NAME = 'MST_OSEI_DTL' AND srsts.POR = :porCd");
		
		/** Constant extract CCF feature P3.1 */
		public static final StringBuilder CCF_DETAILS = new StringBuilder()
		.append("SELECT osfeat.FEAT_CD,osfeat.OCF_FRME_CD,tmo.ORDR_QTY from MST_OSEI_FEAT osfeat,TRN_MNTHLY_ORDR tmo where osfeat.POR_CD = :porCd  AND (osfeat.FEAT_TYPE_CD = '40' OR osfeat.FEAT_TYPE_CD = '50' "+
		" OR osfeat.FEAT_TYPE_CD = '80') AND   (osfeat.OSEI_ID = :oseiID) AND (tmo.OSEI_ID = :oseiID)"+
		"AND ( osfeat.OSEIF_ADPT_DATE <= :prdMnth) AND (osfeat.OSEIF_ABLSH_DATE > :prdMnth) ");
		
		/** Constant extract OCF feature P3.2 */
		public static final StringBuilder OCF_DETAILS = new StringBuilder()
		.append("SELECT OEIF.FEAT_CD , OEIF.OCF_FRME_CD, OEIF.FEAT_TYPE_CD FROM  "+ 
		"MST_OSEI OSEI " +
		"Join MST_OEI_BUYER OEIB on OEIB.OEI_BUYER_ID= OSEI.OEI_BUYER_ID "+
		"Join MST_OEI_FEAT OEIF on OEIF.OEI_BUYER_ID =OEIB.OEI_BUYER_ID "+
		"where OSEI.POR_CD = :porCd AND (OEIF.FEAT_TYPE_CD = '10' OR OEIF.FEAT_TYPE_CD = '20' OR OEIF.FEAT_TYPE_CD = '70') "+
		"AND OSEI.OEI_BUYER_ID = :byrID  AND (OEIF.OEIF_ADPT_DATE <= :prdMnth " +
		"AND OEIF.OEIF_ABLSH_DATE > :prdMnth)  AND OSEI.OSEI_ID = :oseiID");
		
		/** Constant extract OCF feature P3.2 */
		public static final StringBuilder OCF_DETAILS1 = new StringBuilder()
		.append("SELECT oei_feat.FEAT_CD,oei_feat.OCF_FRME_CD,oei_feat.FEAT_TYPE_CD,tmo.ORDR_QTY from MST_OSEI osei, MST_OEI_BUYER oei_buyer, MST_OEI_FEAT oei_feat,TRN_MNTHLY_ORDR tmo where oei_feat.POR_CD = :porCd AND "+
		"(oei_feat.FEAT_TYPE_CD = '10' OR oei_feat.FEAT_TYPE_CD = '20' OR oei_feat.FEAT_TYPE_CD = '70') "+
		" AND (oei_feat.OEI_BUYER_ID = :byrID) "+		
		" AND (oei_feat.OEIF_ADPT_DATE <= :prdMnth AND oei_feat.OEIF_ABLSH_DATE > :prdMnth) AND (osei.OEI_BUYER_ID = oei_buyer.OEI_BUYER_ID) AND (tmo.OSEI_ID = :oseiID)");	
				
		
		/** Constant delete from TRN_BUYER_MNTHLY_OCF_USG P4.1 */ 
		public static final StringBuilder DELETE_BUYER_MNTHLY_OCF_USG_DETAILS = new StringBuilder()
		.append("DELETE from TRN_BUYER_MNTHLY_OCF_USG bmoutrn where bmoutrn.POR_CD = :porCd AND bmoutrn.ORDR_TAKE_BASE_MNTH = :otbm");
		
		/** Constant delete from TRN_BUYER_MNTHLY_OCF_USG P4.1  - Update Only Flag Not Zero */ 
		public static final StringBuilder DELETE_BUYER_MNTHLY_OCF_USG_DETAILS_UPT_FLG = new StringBuilder()
		.append("DELETE from TRN_BUYER_MNTHLY_OCF_USG bmoutrn where bmoutrn.POR_CD = :porCd AND bmoutrn.ORDR_TAKE_BASE_MNTH = :otbm AND bmoutrn.OSEI_ID = :oseiID");
		
		
		/** Constant insert into TRN_BUYER_MNTHLY_OCF_USG P4.2 */
		public static final StringBuilder INSERT_BUYER_MNTHLY_OCF_USG_DETAILS = new StringBuilder()
		.append("INSERT into  TRN_BUYER_MNTHLY_OCF_USG (POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,OSEI_ID,FEAT_CD,OCF_FRME_CD,CAR_SRS,BUYER_GRP_CD,BUYER_OCF_USG_QTY)"+ 
		"VALUES(:porCd,:otbm,:prdMnth,:oseiID,:featCd,:ocfFrmCd,:carSrs,:byrGrpCd,:byrOCFUsgQty)");
		
		/** Constant summarize the Buyer Group OCF Usage volume P5 */
		public static final StringBuilder SUMMARIZE_BUYER_GROUP_OCF_USG_DETAILS_UPDATE_FLAG = new StringBuilder()
		.append("SELECT trnbmou.POR_CD,SUM(trnbmou.BUYER_OCF_USG_QTY),trnbmou.FEAT_CD,trnbmou.OCF_FRME_CD,MIN(trnbmou.FEAT_CD), "
				+ "trnbmou.ORDR_TAKE_BASE_MNTH,trnbmou.PROD_MNTH from TRN_BUYER_MNTHLY_OCF_USG  trnbmou where trnbmou.POR_CD = :porCd AND "
				+ "trnbmou.ORDR_TAKE_BASE_MNTH = :otbm  "
		        +  "trnbmou.BUYER_GRP_CD = :byrGrpCd GROUPBY trnbmou.POR_CD,trnbmou.ORDR_TAKE_BASE_MNTH,trnbmou.PROD_MNTH, "
		        + "trnbmou.CAR_SRS,trnbmou.BUYER_GRP_CD,trnbmou.FEAT_CD,trnbmou.OCF_FRME_CD");
		
		/** Constant summarize the Buyer Group OCF Usage volume P5 without Update Flag*/
		public static final StringBuilder SUMMARIZE_BUYER_GROUP_OCF_USG_DETAILS = new StringBuilder()
		.append("SELECT trnbmou.POR_CD,SUM(trnbmou.BUYER_OCF_USG_QTY),trnbmou.FEAT_CD,trnbmou.OCF_FRME_CD,MIN(trnbmou.FEAT_CD), "
				+ "trnbmou.ORDR_TAKE_BASE_MNTH,trnbmou.PROD_MNTH from TRN_BUYER_MNTHLY_OCF_USG  trnbmou where trnbmou.POR_CD = :porCd AND "
				+ "trnbmou.ORDR_TAKE_BASE_MNTH = :otbm  "
		        +  " GROUP BY trnbmou.POR_CD,trnbmou.ORDR_TAKE_BASE_MNTH,trnbmou.PROD_MNTH, "
		        + "trnbmou.CAR_SRS,trnbmou.BUYER_GRP_CD,trnbmou.FEAT_CD,trnbmou.OCF_FRME_CD");
		
		/** Constant update the Buyer Group Monthly OCF Limit P61a */
		public static final StringBuilder Init_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
		.append("UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT trnbgmol SET trnbgmol.BUYER_GRP_OCF_USG_QTY = 0,trnbgmol.FEAT_TYPE_CD = ' ' WHERE "+
				" trnbgmol.POR_CD = :porCd AND trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnbgmol.BUYER_GRP_CD = :byrGrpCd ");

		/** Constant update the Buyer Group Monthly OCF Limit P61a - Update Only Flag Zero */
		public static final StringBuilder Init_BUYER_GRP_MNTHLY_OCF_LMT_UPT_FLG_ZERO = new StringBuilder()
		.append("UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT trnbgmol SET trnbgmol.BUYER_GRP_OCF_USG_QTY = 0,trnbgmol.FEAT_TYPE_CD = ' ' WHERE "+
				" trnbgmol.POR_CD = :porCd AND trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm");

		
		/** Constant select Buyer Group Monthly OCF Usage Quantity P61b */
		public static final StringBuilder SELECT_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
		.append("SELECT * FROM TRN_BUYER_GRP_MNTHLY_OCF_LMT  WHERE "+
				"trnbgmol.POR_CD = :porCd AND trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnbgmol.PROD_MNTH = :prdMnth AND "+  
				"trnbgmol.CAR_SRS = :carSrs AND  trnbgmol.BUYER_GRP_CD = :byrGrpCd AND  trnbgmol.OCF_FRME_CD = :ocfFrmCd AND "+
				"trnbgmol.FEAT_CD = :featureCd");
		
		/** Constant update Buyer Group Monthly OCF Usage Quantity P61b */
		public static final StringBuilder UPDATE_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
		.append("UPDATE TRN_BUYER_GRP_MNTHLY_OCF_LMT trnbgmol SET trnbgmol.BUYER_GRP_OCF_USG_QTY = :byrGrpOCFUsgQty,trnbgmol.FEAT_TYPE_CD = '' WHERE "+
				"trnbgmol.POR_CD = :porCd AND trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnbgmol.PROD_MNTH = :prdMnth AND "+  
				"trnbgmol.CAR_SRS = :crSrs AND  trnbgmol.BUYER_GRP_CD = :byrGrpCd AND  trnbgmol.OCF_FRME_CD = :ocfFrmCd AND "+
				"trnbgmol.FEAT_CD = :featureCd");
		
		
		/** Constant insert Buyer Group Monthly OCF Usage Quantity P62 */
		public static final StringBuilder INSERT_BUYER_GRP_MNTHLY_OCF_LMT = new StringBuilder()
		.append("INSERT INTO TRN_BUYER_GRP_MNTHLY_OCF_LMT (POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,CAR_SRS,BUYER_GRP_CD,FEAT_CD,OCF_FRME_CD, "+
				"FEAT_TYPE_CD,BUYER_GRP_OCF_USG_QTY,BUYER_GRP_OCF_LMT_QTY) VALUES (:porCd,:otbm,:prdMnth,:carSrs,:byrGrpCd,:featCd,:ocfFrmCd,:featureTypeCd,:byrGrpOCFUsgQty,:byrGrpOCFUsgQty)");

	   /** Constant summarize Order Volume and OCF Limit by Region level P7 */ 	  
		public static final StringBuilder SUMMARIZE_ORDER_VOLUME_REGION_LEVEL = new StringBuilder()		 
		.append("SELECT SUM(trnbgmol.BUYER_GRP_OCF_USG_QTY),trnbgmol.FEAT_CD,trnbgmol.OCF_FRME_CD,MIN(trnbgmol.FEAT_TYPE_CD), "+
		"trnbgmol.ORDR_TAKE_BASE_MNTH,trnbgmol.PROD_MNTH,buyer.OCF_REGION_CD,buyer.OCF_BUYER_GRP_CD "+
		"from TRN_BUYER_GRP_MNTHLY_OCF_LMT trnbgmol,MST_BUYER buyer where trnbgmol.POR_CD = :porCd AND "+ 
		"trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm  GROUP BY trnbgmol.POR_CD,trnbgmol.ORDR_TAKE_BASE_MNTH, "+
		"trnbgmol.PROD_MNTH,trnbgmol.CAR_SRS,buyer.OCF_REGION_CD,buyer.OCF_BUYER_GRP_CD,trnbgmol.FEAT_TYPE_CD,trnbgmol.OCF_FRME_CD, "+
		 "trnbgmol.FEAT_CD");
		
		/** Constant summarize Order Volume and OCF Limit by Region level P7 - Update Flg*/ 	  
		public static final StringBuilder SUMMARIZE_ORDER_VOLUME_REGION_LEVEL_UPT_FLG = new StringBuilder()		 
		.append("SELECT SUM(trnbgmol.BUYER_GRP_OCF_USG_QTY),trnbgmol.FEAT_CD,trnbgmol.OCF_FRME_CD,MIN(trnbgmol.FEAT_TYPE_CD), "+
		"trnbgmol.ORDR_TAKE_BASE_MNTH,trnbgmol.PROD_MNTH,buyer.OCF_REGION_CD,buyer.OCF_BUYER_GRP_CD "+
		"from TRN_BUYER_GRP_MNTHLY_OCF_LMT trnbgmol,MST_BUYER buyer where trnbgmol.POR_CD = :porCd AND "+ 
		"trnbgmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnbgmol.CAR_SRS = :carSrs GROUP BY trnbgmol.POR_CD,trnbgmol.ORDR_TAKE_BASE_MNTH, "+
		"trnbgmol.PROD_MNTH,trnbgmol.CAR_SRS,buyer.OCF_REGION_CD,buyer.OCF_BUYER_GRP_CD,trnbgmol.FEAT_TYPE_CD,trnbgmol.OCF_FRME_CD, "+
		 "trnbgmol.FEAT_CD");
		
		/** Constant update Regional Monthly OCF Limit P8.1a */
		public static final StringBuilder UPDATE_TRN_REGIONAL_MNTHLY_OCF_LMT = new StringBuilder()
		.append("UPDATE TRN_REGIONAL_MNTHLY_OCF_LMT trnrmol SET trnrmol.REGIONAL_OCF_USG_QTY = 0,trnrmol.FEAT_TYPE_CD = ' ' WHERE "+
		" trnrmol.POR_CD = :porCd AND trnrmol.ORDR_TAKE_BASE_MNTH = :otbm");
		
		/** Constant update Regional Monthly OCF Limit P8.1a - Update Flag */
		public static final StringBuilder UPDATE_TRN_REGIONAL_MNTHLY_OCF_LMT_UPT_FLG = new StringBuilder()
		.append("UPDATE TRN_REGIONAL_MNTHLY_OCF_LMT trnrmol SET trnrmol.REGIONAL_OCF_USG_QTY = 0,trnrmol.FEAT_TYPE_CD = ' ' WHERE "+
		" trnrmol.POR_CD = :porCd AND trnrmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnrmol.CAR_SRS = :carSrs");
		
		/** Constant check update Existing records P8.1b */
		public static final StringBuilder SELECT_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT = new StringBuilder()
		.append("SELECT * from  WHERE "+
		"trnrmol.POR_CD = :porCd AND trnrmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnrmol.PROD_MNTH = :prdMnth AND " + 
		"trnrmol.CAR_SRS = :carSrs AND trnrmol.FEAT_CD = :featureCd AND trnrmol.OCF_FRME_CD = :ocfFrameCd AND " +
		"trnrmol.OCF_BUYER_GRP_CD = :ocfBuyerGrpCd AND trnrmol.OCF_REGION_CD = :ocfRegionCd");
		
		
		/** Constant update Existing records P8.1b */
		public static final StringBuilder UPDATE_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT = new StringBuilder()
		.append("UPDATE TRN_REGIONAL_MNTHLY_OCF_LMT trnrmol SET trnrmol.REGIONAL_OCF_USG_QTY = :regOCFUsgQty,trnrmol.FEAT_TYPE_CD = '' WHERE "+
		"trnrmol.POR_CD = :porCd AND trnrmol.ORDR_TAKE_BASE_MNTH = :otbm AND trnrmol.PROD_MNTH = :prdMnth AND " + 
		"trnrmol.CAR_SRS = :carSrs AND trnrmol.FEAT_CD = :featureCd AND trnrmol.OCF_FRME_CD = :ocfFrameCd AND " +
		"trnrmol.OCF_BUYER_GRP_CD = :ocfBuyerGrpCd AND trnrmol.OCF_REGION_CD = :ocfRegionCd");
		
		
		/** Constant insert into Regional Monthly OCF P8.2 */
		public static final StringBuilder INSERT_NOT_EXISTING_TRN_REGIONAL_MNTHLY_OCF_LMT = new StringBuilder()
		.append("INSERT INTO TRN_REGIONAL_MNTHLY_OCF_LMT (POR_CD,ORDR_TAKE_BASE_MNTH,PROD_MNTH,CAR_SRS,FEAT_CD,OCF_FRME_CD,FEAT_TYPE_CD, "+
		"REGIONAL_OCF_USG_QTY,REGIONAL_OCF_LMT_QTY,OCF_REGION_CD,OCF_BUYER_GRP_CD) VALUES (:porCd,:otbm,:prdMnth,:carSrs,:featCd,:ocfFrmCd,:featureTypeCd,:regOCFUsgQty,:regOCFUsgQty,:ocfRegionCd,:byrGrpCd)");
		
		/** Constant update Spec Reexecute Status P9 */
		public static final StringBuilder UPDATE_SPEC_REEXECUTE_STATUS = new StringBuilder()
		.append("UPDATE SPEC_REEXECUTE_STATUS srsts SET srsts.PROCESS_EXECUTED_TIME = :prsExeTime ,srsts.REFERENCE_TIME = :prsRefTime WHERE srsts.POR = :porCd AND "+ 
		" srsts.BATCH_ID = 'B000017' AND srsts.TABLE_NAME = :tblName");
		
		/** Constant Process 2  - Main Part */
		public static final StringBuilder EXT_MNTHLY_TRN_MAIN_PART = new StringBuilder()
		.append(" select  DISTINCT mst_osei.osei_id, mst_osei.oei_buyer_id,mst_oei_spec.CAR_SRS, " +
		" mst_buyer.buyer_grp_cd , FEATURE.FEAT_CD ,FEATURE.ocf_frme_cd, FEATURE.feat_type_cd , trn_mnthly_ordr.PROD_MNTH ,mst_buyer.OCF_BUYER_GRP_CD, mst_buyer.OCF_REGION_CD, " +
		" trn_mnthly_ordr.SUSPENDED_ORDR_FLAG, trn_mnthly_ordr.ORDR_QTY , trn_mnthly_ordr.MS_QTY "+
		" from trn_mnthly_ordr "	+
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
         " AND mst_oei_buyer.BUYER_CD = MST_BUYER.BUYER_CD  "
		);
		
		/** Constant Process 2  - CCF Part */
		public static final StringBuilder EXT_MNTHLY_TRN_CCF_PART = new StringBuilder()
		.append(" INNER JOIN MST_OSEI_FEAT  FEATURE ON "+
		" MST_OSEI.OSEI_ID = FEATURE.OSEI_id "+
		" AND MST_OSEI.POR_cd = FEATURE.POR_CD "+
		" AND FEATURE.FEAT_TYPE_CD IN ('40','50','80') "+
		" AND ( substr(FEATURE.OSEIF_ADPT_DATE,0,6) <= trn_mnthly_ordr.PROD_MNTH ) " +
		" AND ( substr(FEATURE.OSEIF_ABLSH_DATE,0,6) > trn_mnthly_ordr.PROD_MNTH ) ");
		
		/** Constant Process 2  - OCF Part */
		public static final StringBuilder EXT_MNTHLY_TRN_OCF_PART = new StringBuilder()
		.append(" INNER JOIN MST_OEI_FEAT  FEATURE ON "+
				" MST_OSEI.OEI_BUYER_ID = FEATURE.OEI_BUYER_ID "+
				" AND MST_OSEI.POR_cd = FEATURE.POR_CD "+
				" AND FEATURE.FEAT_TYPE_CD IN ('10','20','70') "+
				" AND ( substr(FEATURE.OEIF_ADPT_DATE,0,6) <= trn_mnthly_ordr.PROD_MNTH ) "+
				" AND ( substr(FEATURE.OEIF_ABLSH_DATE,0,6) > trn_mnthly_ordr.PROD_MNTH ) " );
		
		/** Constant Process 2  - Update Flag Part */
		public static final StringBuilder EXT_MNTHLY_TRN_UPT_FLG_PART = new StringBuilder()
		.append(" inner join spec_reexecute_status on ( "+
				" (mst_osei_dtl.updtd_dt > reference_time "+
				" and spec_reexecute_status.table_name  ='MST_OSEI_DTL' ) OR "+
				" (FEATURE.UPDTD_DT > reference_time "+
				" AND spec_reexecute_status.table_name = :featureTbl ) ) and ");
		
		/** Constant Process 2  - No Update Flag Part */
		public static final StringBuilder EXT_MNTHLY_TRN_NO_UPT_FLG_PART = new StringBuilder()
		.append(" inner join spec_reexecute_status on ");
		
		/** Constant Process 2  - End Part */
		
		public static final StringBuilder EXT_MNTHLY_TRN_CNDN_PART = new StringBuilder()
		.append("  mst_osei_dtl.por_cd = spec_reexecute_status.por "+
		" and spec_reexecute_status.por = :porCd "+
		" and spec_reexecute_status.BATCH_ID = 'B000017' "+
		" where trn_mnthly_ordr.por_cd = :porCd "+
		" AND trn_mnthly_ordr.ordrtk_base_mnth = :otbm ");
		
		/** Constant Process 2  - End Part - Update Flag No */
		//Redmine #3361
		public static final StringBuilder EXT_MNTHLY_TRN_CNDN_NO_UPT_FLG_PART = new StringBuilder()
		.append(" where trn_mnthly_ordr.por_cd = :porCd "+
		" AND trn_mnthly_ordr.ordrtk_base_mnth = :otbm ");
		
		
		//Constant Updated Time - MST_OSEI_DTL
		
		public static final StringBuilder UPT_TIME_MST_OSEI_DTL = new StringBuilder()
		.append("SELECT MAX(UPDTD_DT) from MST_OSEI_DTL where POR_CD = :porCd");
		
		//Constant Updated Time - MST_OSEI_FEAT
		
		public static final StringBuilder UPT_TIME_MST_OSEI_FEAT = new StringBuilder()
		.append("SELECT MAX(UPDTD_DT) from MST_OSEI_FEAT where POR_CD = :porCd");
				
		//Constant Updated Time - MST_OEI_FEAT
		
		public static final StringBuilder UPT_TIME_MST_OEI_FEAT = new StringBuilder()
		.append("SELECT MAX(UPDTD_DT) from MST_OEI_FEAT where POR_CD = :porCd");
		
private QueryConstants(){
	
}
				

	}



