/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.util;

import com.nissangroups.pd.util.IFConstants;

public class B000040QueryConstants 
{
	private B000040QueryConstants() {
		super();
		
	}
	
	//PreparedStmt Batch Limit
	public static int maxLimit = 100;

	public static final StringBuilder insertCmnHeader = new StringBuilder()
		.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,REC_COUNT,TRN_TYPE,STTS,REMARKS)")
		.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:REC_COUNT,:TRN_TYPE,:STTS,:REMARKS)"); 
	
	public static final StringBuilder updateCmnHeader = new StringBuilder()
		.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO "); 
	
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
		.append("UPDATE CMN_FILE_HDR SET STTS = :STTS,TRN_TYPE = :TRN_TYPE  WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO "); 

	// P0001 : WEEKLY ORDER TAKE BASE PERIOD MST (Extract Order Take Base Period )
	public static final StringBuilder extrctOrdrTakBsePrd = new StringBuilder()
		.append("SELECT ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO FROM MST_WKLY_ORDR_TAKE_BASE WHERE POR= "+IFConstants.porCd_Param+" AND STAGE_CD='WK' AND STAGE_STTS_CD='SC' ");
	
	//P0002 : PARAMETER MST (Extract Order Production Period Horizon)
	public static final StringBuilder extrctOrdrPrdHorizn = new StringBuilder()
		.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.WKLY_ORDR_HRZN+"' ");
	
	//P0003 : Cong1:  PARAMETER MST (MONTHLY FIXED ORDER REQUIRED)
	public static final StringBuilder extrctParamtrzdConfg = new StringBuilder()
		.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.MNTHLY_FXD_ORDR_RQD+"' ");
		
	/*P0004 : P0004.1 PARAMTER MST (USE ORIGINAL LINE CLASS AND PLANT CD )
	 * set LineClassandPlntCdFlag
	*/
	public static final StringBuilder extrctlneClsPlntCd = new StringBuilder()
		.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.ORGNL_LNE_CLS_PLNT_CD+"' ");
	
	public static final StringBuilder cnstntlneClsPlntCd = new StringBuilder()
		.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.CNSTNT_LNE_CLS_PLNT_CD+"' ");
	
	public static final StringBuilder extrctCnstDyFlg = new StringBuilder()
		.append("SELECT VAL1,VAL2 FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.CNSTNT_DAY_NO+"' ");
	
	/*P0004 : P0004.1 PARAMTER MST (SUSPENDED ORDER REQUIRED*/
	public static final StringBuilder extrctSuspndedFlg = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.SUSPND_ORDR_RQD+"' ");
	
	public static final StringBuilder extrctNMnthSuspndedFlg = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.N_MNTH_SUSPND_ORDR_RQD+"' ");
	
	public static final StringBuilder extrctNMnthOrdrDtls = new StringBuilder()
		.append("SELECT DISTINCT TLP.POR_CD ,TLP.PROD_MNTH , TLP.PROD_WK_NO ,TLP.OSEI_ID ,TLP.POT_CD ,TLP.PROD_ORDR_NO, TLP.OFFLN_PLAN_DATE, null \"ROW_NO\", null \"FROZENTYPECD\",null \"PROD_MTHD_CD\", null \"OCF_REGION_CD\", null \"EX_NO\", null \"END_ITEM\",null \"PROD_FMY_CD\",null \"ADTNL_SPEC_CD\",null \"SPEC_DESTN_CD\",null \"BUYER_CD\",null \"BUYER_GRP_CD\",null \"CAR_SRS\",null \"SALES_NOTE_NO\",null \"DEALER_LIST\",null \"OWNER_MANUAL\",null \"WARRANTY_BOOKLET\" ");
	
	public static final StringBuilder lneClsPlntCd_True = new StringBuilder()	
		.append(",TLP.PROD_PLNT_CD, TLP.LINE_CLASS ");
		
	public static final StringBuilder CnstDyFlg_False = new StringBuilder()	
		.append(",TLP.PROD_DAY_NO ");
	
	public static final StringBuilder extrctNMnth_From_Cndtn = new StringBuilder()	
		.append("FROM TRN_LGCL_PPLN TLP ");
	
	public static final StringBuilder extrct_OrderDetails_Join_Cndtn = new StringBuilder()	
		.append("INNER JOIN MST_OSEI MOSI ON (MOSI.OSEI_ID = TLP.OSEI_ID) INNER JOIN MST_OEI_BUYER MOB ON(MOSI.OEI_BUYER_ID = MOB.OEI_BUYER_ID) ")
		.append("INNER JOIN MST_OEI_SPEC MOS ON(TLP.POR_CD = MOS.POR_CD) INNER JOIN MST_POR_CAR_SRS MPCS ON(MPCS.CAR_SRS = MOS.CAR_SRS) ");
	
	public static final StringBuilder extrctNMnth_Cndtn_SuspendedTrue = new StringBuilder()
		.append("WHERE TLP.POR_CD = '"+IFConstants.porCd_Param+"' AND TLP.PROD_MNTH = '"+IFConstants.ordrTkBsMnth_Param+"' AND TLP.PROD_WK_NO >= '"+B000040Constants.param_ORDR_TK_PROD_WK_NO+"' ");
	
	public static final StringBuilder extrct_OrderDetails_Cndtn_SuspendedFalse = new StringBuilder()	
		.append(" TLP.OSEI_ID IN ("+B000040Constants.param_OSEI_ID +") AND MPCS.CAR_SRS_ABLSH_DATE > '"+IFConstants.ordrTkBsMnth_Param+B000040Constants.param_ORDR_TK_PROD_WK_NO+"1"+"' ");

	public static final StringBuilder extrctOseiId = new StringBuilder()
		.append("SELECT OSEI_ID FROM TRN_WKLY_ORDR WHERE POR_CD = "+IFConstants.porCd_Param+" AND ( PROD_MNTH = "+IFConstants.ordrTkBsMnth_Param+" AND PROD_WK_NO >= "+B000040Constants.param_ORDR_TK_PROD_WK_NO+" AND SUSPENDED_ORDR_FLAG = 0 )");
	
	public static final StringBuilder extrctfrcstMnth_Ordr = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.FRCST_MNTH_SPNDED_ORDR_RQD+"' ");
	
	/*public static final StringBuilder extrctFreCstMnth_SuspndOrdr_True = new StringBuilder()
		.append("WHERE TLP.POR_CD = "+IFConstants.porCd_Param+" AND TLP.PROD_MNTH IN ["+IFConstants.ProdMnth_param+"] AND TLP.PROD_WK_NO IN ["+B000040Constants.param_ORDR_TK_PROD_WK_NO+"] AND TLP.PROD_MNTH = "+IFConstants.ordrTkBsMnth_Param+" ");
	*/
	
	public static final StringBuilder extrctFreCstMnth_SuspndOrdr_True = new StringBuilder()
	.append("WHERE TLP.POR_CD = '" + IFConstants.porCd_Param+"' AND TLP.PROD_MNTH = '" + IFConstants.ordrTkBsMnth_Param +"' ");

	
	
	public static final StringBuilder extrctFreCstOseiId = new StringBuilder()
		.append("SELECT DISTINCT OSEI_ID FROM TRN_WKLY_ORDR WHERE POR_CD = '"+IFConstants.porCd_Param+"' AND SUSPENDED_ORDR_FLAG = 0 AND ");
	
	/*public static final StringBuilder extrctFreCstMnth_SuspndOrdr_False = new StringBuilder()
		.append("WHERE TLP.POR_CD = "+IFConstants.porCd_Param+" AND TLP.PROD_MNTH > "+IFConstants.ordrTkBsMnth_Param+" AND TLP.PROD_MNTH IN ["+IFConstants.ProdMnth_param+"] AND TLP.PROD_WK_NO IN ["+B000040Constants.param_ORDR_TK_PROD_WK_NO+"] ");
	*/
	
	public static final StringBuilder extrctFreCstMnth_SuspndOrdr_False = new StringBuilder()
	.append("WHERE TLP.POR_CD = "+IFConstants.porCd_Param+" AND TLP.PROD_MNTH > "+IFConstants.ordrTkBsMnth_Param);

	
	public static final StringBuilder suspnded_ordr_False = new StringBuilder()
		.append("WHERE TLP.POR_CD = "+IFConstants.porCd_Param+" AND TLP.PROD_MNTH IN ["+IFConstants.ProdMnth_param+"] AND TLP.PROD_WK_NO IN ["+B000040Constants.param_PROD_WK_NO+"] AND TLP.OSEI_ID IN ["+B000040Constants.param_OSEI_ID +"] ");

	//first check whether, the POR is needed Frozen symbol or not,as follows
	public static final StringBuilder extrctFrznSymbl = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.FRZN_SYMBL_RQD+"' ");
	
	// Extract Latest Stage Completed Monthly Order Take Base Month as follows
	public static final StringBuilder extrctStgCmpltdOrdr = new StringBuilder()
		.append("SELECT MAX(ORDR_TAKE_BASE_MNTH)  FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = "+IFConstants.porCd_Param+" AND STAGE_CD = 'SC' ");
	
	// After the extraction latest stage Completed Order Take Base month, do as follows -- OSEI FROZEN MST
	public static final StringBuilder extrctFrznValue = new StringBuilder()
		.append("SELECT FRZN_TYPE_CD  FROM MST_OSEI_FRZN WHERE POR_CD = "+IFConstants.porCd_Param+" and FRZN_ORDR_TAKE_BASE_MNTH < "+IFConstants.ordrTkBsMnth_Param+" and FRZN_PROD_MNTH = "+IFConstants.ProdMnth_param+" and OSEI_ID = "+B000040Constants.param_OSEI_ID +" ");
		
	// Attach Production method Code with the extracted Orders
	public static final StringBuilder extrctProdMthdCd = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.PROD_MTHD_CD_RQD+"' ");
	
	// First extract the constant value for Production method Code from PARAMETER MST 
	public static final StringBuilder extrctCnstProdMthdCd = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.CNSTNT_PROD_MTHD_CD+"' ");
	
	// IFproduction month code if production plant code is empty , get the Attach Production method Code with the extracted Orders
	public static final StringBuilder prodMthdCd = new StringBuilder()
		.append("SELECT MAX(PROD_MTHD_CD) FROM MST_OSEI_PROD_TYPE WHERE POR_CD ="+IFConstants.porCd_Param+" AND OSEI_ID ="+B000040Constants.param_OSEI_ID +" AND ORDR_TAKE_BASE_MNTH="+IFConstants.ordrTkBsMnth_Param+" ")
		.append("AND PROD_MNTH="+IFConstants.ProdMnth_param+" AND PROD_WK_NO ="+B000040Constants.param_PROD_WK_NO+" AND PROD_PLNT_CD = "+B000040Constants.param_PROD_PLNT_CD+" ");
	
	public static final StringBuilder extrctExNo = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.ATTACH_EX_NO+"' ");
	
	public static final StringBuilder mnthlyPrcsChk = new StringBuilder()
		.append("SELECT STAGE_CD , ORDR_TAKE_BASE_MNTH FROM MST_MNTH_ORDR_TAKE_BASE_PD WHERE POR_CD = ':porCd' AND STAGE_CD = 'SC' AND ORDR_TAKE_BASE_MNTH IN (:ordrTkBsePrd)");

	//P0003 : WEEK NO CALENDAR MST ( FROM WEEK and TO WEEK details )
	public static final StringBuilder extrctProdWkNo = new StringBuilder()
		.append("SELECT PROD_MNTH, MAX(PROD_WK_NO) FROM MST_WK_NO_CLNDR WHERE POR_CD = ':porCd' AND PROD_MNTH IN (:prodMnth) GROUP BY PROD_MNTH");

	public static final StringBuilder extrctServicePrmtr = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.SRVCE_PRMR_RQD+"' ");
	
	public static final StringBuilder extrctDtDsplyFrmt = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_ORDR_PLNT+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.ABLSH_DT_DSPLY_FRMT+"' ");
	
	public static final StringBuilder extrctPlntLnSmry = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_OCF_SMARY+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.PLNT_LNE_SMRY+"' ");
	
	public static final StringBuilder extrctCnstPlntAndLn = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_OCF_SMARY+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.CNSTNT_PLNT_LNE_CLSS+"' ");
	
	public static final StringBuilder extrctCnstntDyNo = new StringBuilder()
		.append("SELECT VAL1  FROM MST_PRMTR WHERE PRMTR_CD='"+B000040Constants.WKLY_OCF_SMARY+"' AND KEY1 = "+IFConstants.porCd_Param+" AND KEY2 = '"+B000040Constants.CNSTNT_DAY_NO+"' ");
	
	
	/*suppose any order changes in select extraction fields, should modify the code accordingly, where the below reference is used */
	public static final StringBuilder extrctExNo_True = new StringBuilder()
		.append("SELECT MB.OCF_REGION_CD AS \"OCF REGION CODE\",MEXNO.EX_NO ,CONCAT(MOS.APPLD_MDL_CD , MOS.PCK_CD) AS \"End Item\",MOS.PROD_FMY_CD AS \"PRODUCTION FAMILY CODE\",MOS.ADTNL_SPEC_CD AS \"ADDITIONAL SPEC CODE\" ,MOS.SPEC_DESTN_CD AS \"SPEC DESTIANTION CODE\",MOB.BUYER_CD AS \"BUYER CODE\" ,MB.BUYER_GRP_CD AS \"BUYER GROUP CODE\" ,MOS.CAR_SRS AS \"CAR SERIES\"	")  
		.append("FROM MST_OSEI MOSEI INNER JOIN MST_OEI_BUYER MOB ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID) INNER JOIN MST_OEI_SPEC MOS ON (MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID )") 
		.append("INNER JOIN MST_BUYER MB ON (MOB.BUYER_CD = MB.BUYER_CD) INNER JOIN MST_POR MP ON (MP.POR_CD = MOB.POR_CD AND MP.PROD_REGION_CD = MB.PROD_REGION_CD )") 
		.append("INNER JOIN MST_EX_NO MEXNO ON  (MEXNO.OEI_BUYER_ID = MOSEI.OEI_BUYER_ID AND MEXNO.POR_CD = MP.POR_CD) WHERE  MEXNO.PROD_MNTH = :prodMnth AND MOSEI.OSEI_ID = :oseiId ");
	
	/*suppose any order changes in select extraction fields, should modify the code accordingly, where the below reference is used */	
	public static final StringBuilder extrctExNo_False = new StringBuilder()
		.append("SELECT MB.OCF_REGION_CD,CONCAT(MOS.APPLD_MDL_CD + MOS.PCK_CD),MOS.PROD_FMY_CD,MOS.ADTNL_SPEC_CD,MOS.SPEC_DESTN_CD,MOB.BUYER_CD,MB.BUYER_GRP_CD,MOS.CAR_SRS ") 
		.append("FROM MST_OSEI MOSEI INNER JOIN MST_OEI_BUYER MOB ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID) INNER JOIN MST_OEI_SPEC MOS ON (MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID) ") 
		.append("INNER JOIN MST_BUYER MB ON (MOB.BUYER_CD = MB.BUYER_CD) INNER JOIN MST_POR MP ON (MP.POR_CD = MOB.POR_CD )") 
		.append("WHERE MOSEI.OSEI_ID IN (:oseiId)  ");
	
	public static final StringBuilder updateExNoMapSymbolTrue = new StringBuilder()
	.append("UPDATE B000040OrdrDtlsOutputBean SET EX_NO = ? , OCF_REGION_CD= ?, END_ITEM = ?, PROD_FMY_CD=? , ADTNL_SPEC_CD =?, SPEC_DESTN_CD=?, BUYER_CD =?, BUYER_GRP_CD= ?, CAR_SRS = ?, SALES_NOTE_NO=? WHERE POR_CD = ? AND OSEI_ID = ?");
	
	public static final StringBuilder updateExNoMapSymbolFalse = new StringBuilder()
	.append("UPDATE B000040OrdrDtlsOutputBean SET OCF_REGION_CD= ?, END_ITEM = ?, PROD_FMY_CD=? , ADTNL_SPEC_CD =?, SPEC_DESTN_CD=?, BUYER_CD =?, BUYER_GRP_CD= ?, CAR_SRS = ?, SALES_NOTE_NO WHERE POR_CD = ? AND OSEI_ID = ?");
	
	public static final StringBuilder updateSrvcePrmtr = new StringBuilder()
	.append("UPDATE B000040OrdrDtlsOutputBean SET DEALER_LIST= ?, OWNER_MANUAL = ?, WARRANTY_BOOKLET=? WHERE POR_CD = ? AND OSEI_ID = ?");


	public static final StringBuilder deleteTrnWklyPrdOrdr = new StringBuilder()
		.append("DELETE FROM TRN_WKLY_PROD_ORDR WHERE POR_CD =':porCD' AND ORDR_TAKE_BASE_MNTH = ':ordrTkBsMnth' AND ORDR_TAKE_BASE_WK_NO = ':ordrTkProdWkNo' ");
	
	public static final StringBuilder exctSuspndOrdrOseiDtls = new StringBuilder()
		.append("SELECT OSEI_ID,PROD_MNTH,PROD_WK_NO FROM TRN_WKLY_ORDR WHERE OSEI_ID IN (:oseiId)  AND PROD_MNTH IN (:prodMnth) AND PROD_WK_NO IN (:prodWkNo) AND POR_CD = :porCD AND SUSPENDED_ORDR_FLAG = '1' ");
	
	public static final StringBuilder crteSpecErRprt = new StringBuilder()
	.append("SELECT MB.OCF_REGION_CD,MB.OCF_BUYER_GRP_CD,MBG.MC_REGION_CD,MB.BUYER_GRP_CD,MB.BUYER_CD,MOS.PROD_FMY_CD,MPCS.CAR_SRS,MPCS.CAR_GRP,MOS.SPEC_DESTN_CD,")
	.append("CONCAT(MOS.APPLD_MDL_CD,MOS.PCK_CD),MOS.ADTNL_SPEC_CD,TLP.POT_CD,TLP.SLS_NOTE_NO, ")
	.append("CONCAT(MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD),TLP.PROD_MNTH,TLP.PROD_WK_NO,TLP.OSEI_ID,COUNT(TLP.OSEI_ID),MOB.OEI_BUYER_ID  ")
    .append("FROM TRN_LGCL_PPLN TLP ,MST_OSEI MOSEI INNER JOIN MST_OEI_BUYER MOB ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID)  INNER JOIN MST_OEI_SPEC MOS ON (MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID) ")
	.append("INNER JOIN MST_POR_CAR_SRS MPCS ON (MOS.CAR_SRS = MPCS.CAR_SRS AND MOS.POR_CD = MPCS.POR_CD AND MOS.PROD_FMY_CD  = MPCS.PROD_FMY_CD) ")
	.append("INNER JOIN MST_POR MP ON (MP.POR_CD = MOSEI.POR_CD ) INNER JOIN MST_BUYER MB ON (MOB.BUYER_CD = MB.BUYER_CD AND MP.PROD_REGION_CD = MB.PROD_REGION_CD) ")
	.append("INNER JOIN MST_BUYER_GRP MBG ON (MB.BUYER_GRP_CD = MBG.BUYER_GRP_CD) INNER JOIN MST_OEI_BUYER_PRD MOBP ON (MOBP.OEI_BUYER_ID = MOSEI.OEI_BUYER_ID) ")
	.append("WHERE TLP.OSEI_ID IN (:oseiId) AND TLP.PROD_MNTH IN (:prodMnth) AND TLP.PROD_WK_NO IN (:prodWkNo) ")
    .append("GROUP BY MB.OCF_REGION_CD, MB.OCF_BUYER_GRP_CD, MBG.MC_REGION_CD, MB.BUYER_GRP_CD, MB.BUYER_CD, ")
    .append("MOS.PROD_FMY_CD, MPCS.CAR_SRS, MPCS.CAR_GRP, MOS.SPEC_DESTN_CD, CONCAT(MOS.APPLD_MDL_CD,MOS.PCK_CD), ")
    .append("MOS.ADTNL_SPEC_CD, TLP.POT_CD, TLP.SLS_NOTE_NO, CONCAT(MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD), TLP.PROD_MNTH, ")
    .append("TLP.PROD_WK_NO, TLP.OSEI_ID, MOB.OEI_BUYER_ID ")
	.append("UNION ")
	.append("SELECT MB.OCF_REGION_CD,MB.OCF_BUYER_GRP_CD,MBG.MC_REGION_CD,MB.BUYER_GRP_CD,MB.BUYER_CD,MOS.PROD_FMY_CD,MPCS.CAR_SRS,MPCS.CAR_GRP,MOS.SPEC_DESTN_CD, ")
	.append("CONCAT(MOS.APPLD_MDL_CD,MOS.PCK_CD),MOS.ADTNL_SPEC_CD,TLP.POT_CD,TLP.SLS_NOTE_NO, ")
	.append("CONCAT(MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD),TLP.PROD_MNTH,TLP.PROD_WK_NO,TLP.OSEI_ID,COUNT(TLP.OSEI_ID),MOB.OEI_BUYER_ID ")
	.append("FROM MST_OSEI_DTL MOSD,TRN_LGCL_PPLN TLP INNER JOIN MST_OSEI MOSEI ON(MOSEI.OSEI_ID = TLP.OSEI_ID) INNER JOIN MST_OEI_BUYER MOB ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID) ")
	.append("INNER JOIN MST_BUYER MB ON (MOB.BUYER_CD = MB.BUYER_CD) INNER JOIN MST_BUYER_GRP MBG ON (MB.BUYER_GRP_CD = MBG.BUYER_GRP_CD) ")
	.append("INNER JOIN MST_POR MP ON (MP.PROD_REGION_CD = MB.PROD_REGION_CD) INNER JOIN MST_OEI_SPEC MOS ON (MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID)  ")
	.append("INNER JOIN MST_POR_CAR_SRS MPCS ON (MOS.CAR_SRS = MPCS.CAR_SRS AND MOS.PROD_FMY_CD  = MPCS.PROD_FMY_CD ) ")
	.append("WHERE TLP.POR_CD = :porCD AND TLP.OSEI_ID IN (:oseiId) AND MP.POR_CD = :porCD AND MPCS.POR_CD = :porCD AND MOB.POR_CD = :porCD AND MOSEI.POR_CD = :porCD AND MOS.POR_CD= :porCD AND MOSD.POR_CD= :porCD ")
	.append("AND TLP.PROD_MNTH NOT IN(:prodMnth) AND MPCS.CAR_SRS IN (:carSrs) ")
    .append("GROUP BY MB.OCF_REGION_CD, MB.OCF_BUYER_GRP_CD, MBG.MC_REGION_CD, MB.BUYER_GRP_CD, MB.BUYER_CD, ")
    .append("MOS.PROD_FMY_CD, MPCS.CAR_SRS, MPCS.CAR_GRP, MOS.SPEC_DESTN_CD, CONCAT(MOS.APPLD_MDL_CD,MOS.PCK_CD), ")
    .append("MOS.ADTNL_SPEC_CD, TLP.POT_CD, TLP.SLS_NOTE_NO, CONCAT(MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD), TLP.PROD_MNTH, ")
    .append("TLP.PROD_WK_NO, TLP.OSEI_ID, MOB.OEI_BUYER_ID ");
	
	public static final StringBuilder exctAdbptAbolshPrd = new StringBuilder()
		.append("SELECT MOSEI.OSEI_ID ,MOB.OEI_BUYER_ID,MOSD.OSEI_ADPT_DATE,MPCS.CAR_SRS_ADPT_DATE,MOSD.OSEI_ABLSH_DATE, MPCS.CAR_SRS_ABLSH_DATE ")
		.append("FROM MST_OEI_SPEC MOS INNER JOIN MST_OEI_BUYER MOB ON(MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID) ") 
		.append("INNER JOIN MST_OSEI MOSEI ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID AND MOSEI.POR_CD = MOB.POR_CD) ")
		.append("INNER JOIN MST_OSEI_DTL MOSD ON (MOSD.OSEI_ID = MOSEI.OSEI_ID AND MOSD.POR_CD =  MOSEI.POR_CD) ")
		.append("INNER JOIN MST_POR_CAR_SRS MPCS ON (MPCS.CAR_SRS = MOS.CAR_SRS AND MPCS.CAR_SRS = MOS.CAR_SRS AND MPCS.PROD_FMY_CD = MOS.PROD_FMY_CD) ")
		.append("WHERE MOS.POR_CD IN (:porCD) AND MOS.CAR_SRS IN (:carSrs) "); 
	
	public static final StringBuilder extctWkStrtDt = new StringBuilder()
		.append("SELECT WK_STRT_DATE FROM MST_WK_NO_CLNDR WHERE POR_CD = :porCD AND PROD_MNTH = :prodMnth AND PROD_WK_NO = :prodWkNo ");
	
	public static final StringBuilder crteProdOrdrDtlRprt = new StringBuilder()
		.append("SELECT TWP.POR_CD,CONCAT(TWP.ORDR_TAKE_BASE_MNTH,TWP.ORDR_TAKE_BASE_WK_NO),TWP.PROD_MNTH,TWP.PROD_WK_NO,TWP.ORDR_QTY,TWP.EX_NO,TWP.SLS_NOTE_NO,TWP.TYRE_MKR_CD, ")
		.append("TWP.DEALER_LST,TWP.OWNR_MNL,TWP.WRNTY_BKLT,TWP.OCF_REGION_CD,TWP.PROD_MTHD_CD, TWP.FRZN_TYPE_CD,MPCS.CAR_SRS,MPCS.PROD_FMY_CD,MB.OCF_BUYER_GRP_CD,MB.BUYER_CD, ")
		.append("MPCS.CAR_GRP,MBG.MC_REGION_CD,MOS.SPEC_DESTN_CD,MOS.ADTNL_SPEC_CD,CONCAT (MOS.APPLD_MDL_CD,MOS.PCK_CD),CONCAT(MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD),TWP.BDY_PRTCTN_CD ")
		.append("FROM TRN_WKLY_PROD_ORDR TWP ")
		.append("INNER JOIN MST_OSEI MOSEI ON (TWP.OSEI_ID = MOSEI.OSEI_ID ) ")
		.append("INNER JOIN MST_OEI_BUYER MOB ON (MOSEI.OEI_BUYER_ID = MOB.OEI_BUYER_ID) ")
		.append("INNER JOIN MST_OEI_SPEC MOS ON (MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID) ")
		.append("INNER JOIN MST_POR_CAR_SRS MPCS ON (MOS.CAR_SRS = MPCS.CAR_SRS AND MOS.PROD_FMY_CD  = MPCS.PROD_FMY_CD AND MOS.POR_CD = MPCS.POR_CD) ")
		.append("INNER JOIN MST_POR MP ON (MP.POR_CD = MPCS.POR_CD AND MP.POR_CD = TWP.POR_CD) ")
		.append("INNER JOIN MST_BUYER MB ON (MB.PROD_REGION_CD = MP.PROD_REGION_CD) ")
		.append("INNER JOIN MST_BUYER_GRP MBG ON (MBG.BUYER_GRP_CD = MB.BUYER_GRP_CD) ")
		.append("WHERE TWP.POR_CD = :porCD  AND TWP.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth  AND TWP.ORDR_TAKE_BASE_WK_NO = :ordrTkProdWkNo ");
	
	/** Constant Extract Production order Details for Monthly Production order detail report*/
	public static final StringBuilder getWklyPrdnOrdrRptDtls = new StringBuilder()
	.append( "SELECT tmpo.ORDR_TAKE_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD,")
	.append( "TMPO.PROD_MNTH,byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,")
	.append( "mpcs.CAR_GRP,oeispec.CAR_SRS,oeispec.SPEC_DESTN_CD,")
	.append( "concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD),TMPO.EX_NO,")
	.append( "oeispec.ADTNL_SPEC_CD,TMPO.POT_CD,TMPO.SLS_NOTE_NO,")
	.append( "concat(osei.EXT_CLR_CD, osei.INT_CLR_CD),SUM(TMPO.ORDR_QTY),")
	.append( "TMPO.TYRE_MKR_CD,concat(concat(TMPO.DEALER_LST,TMPO.OWNR_MNL),TMPO.WRNTY_BKLT),")
	.append( "TMPO.BDY_PRTCTN_CD,LISTAGG(optn.OPTN_SPEC_CODE, ' ') WITHIN GROUP (ORDER BY optn.OPTN_SPEC_CODE) AS optionCd ")
	.append( "FROM TRN_WKLY_PROD_ORDR TMPO ")
	.append( "INNER JOIN MST_OSEI osei ON TMPO.osei_id = osei.osei_id AND TMPO.POR_CD = osei.POR_CD ")
	.append( "INNER JOIN mst_oei_buyer oeibyr ON osei.por_cd = oeibyr.por_cd AND osei.oei_buyer_id = oeibyr.oei_buyer_id ")
	.append( "INNER JOIN mst_oei_spec oeispec ON oeibyr.por_cd = oeispec.por_cd AND oeibyr.oei_spec_id = oeispec.oei_spec_id ")
	.append( "INNER JOIN MST_POR por ON oeibyr.por_cd = por.POR_CD ")
	.append( "INNER JOIN MST_BUYER byr ON byr.PROD_REGION_CD = por.PROD_REGION_CD AND oeibyr.BUYER_CD   = byr.BUYER_CD ")
	.append( "INNER JOIN MST_BUYER_GRP byrgrp ON byrgrp.BUYER_GRP_CD = byr.BUYER_GRP_CD ")
	.append( "INNER JOIN MST_POR_CAR_SRS mpcs ON oeispec.CAR_SRS = mpcs.CAR_SRS AND mpcs.POR_CD = oeispec.POR_CD AND mpcs.PROD_FMY_CD = oeispec.PROD_FMY_CD ")
	.append( "INNER JOIN MST_OEI_BUYER_OPTN_SPEC_CD optn ON optn.OEI_BUYER_ID = oeibyr.OEI_BUYER_ID ")
	.append( "WHERE TMPO.POR_CD = :porCd AND TMPO.ORDR_TAKE_BASE_MNTH = :OTBM ")
	.append( "GROUP BY TMPO.POR_CD,tmpo.ORDR_TAKE_BASE_MNTH,byr.OCF_REGION_CD,byrgrp.MC_REGION_CD, ")
	.append( "byr.BUYER_GRP_CD,byr.BUYER_CD,oeispec.PROD_FMY_CD,oeispec.CAR_SRS, ")
	.append( "oeispec.SPEC_DESTN_CD,concat(oeispec.APPLD_MDL_CD, oeispec.PCK_CD), ")
	.append( "oeispec.ADTNL_SPEC_CD,TMPO.POT_CD,concat(osei.EXT_CLR_CD, osei.INT_CLR_CD), ")
	.append( "TMPO.PROD_MNTH,TMPO.EX_NO,TMPO.SLS_NOTE_NO,TMPO.ORDR_QTY,")
	.append( "TMPO.TYRE_MKR_CD,concat(concat(TMPO.DEALER_LST,TMPO.OWNR_MNL),TMPO.WRNTY_BKLT), ")
	.append( "TMPO.BDY_PRTCTN_CD,mpcs.CAR_GRP,TMPO.OSEI_ID ");
	
	public static final StringBuilder crteBreachRprt = new StringBuilder()
		.append("SELECT MF.FEAT_CD, MF.FEAT_SHRT_DESC, MF.FEAT_LNG_DESC,TBW.PROD_MNTH,TBW.POR_CD,TBW.CAR_SRS,TBW.PROD_WK_NO,TBW.OCF_FRME_CD,MB.BUYER_CD,MB.BUYER_GRP_CD,MB.OCF_REGION_CD,MB.OCF_BUYER_GRP_CD ")
		.append("FROM TRN_BUYER_WKLY_OCF_USG TBW INNER JOIN MST_POR MP ON (MP.POR_CD = TBW.POR_CD) INNER JOIN MST_BUYER MB ON(MB.PROD_REGION_CD = MP.PROD_REGION_CD AND MB.BUYER_GRP_CD = TBW.BUYER_GRP_CD ) ")
		.append("INNER JOIN MST_FEAT MF ON(MF.FEAT_CD = TBW.FEAT_CD AND MF.CAR_SRS = TBW.CAR_SRS AND MF.OCF_FRME_CD = TBW.OCF_FRME_CD ) ")
		.append("WHERE TBW.POR_CD IN (:porCD) AND TBW.PROD_WK_NO IN (:prodWkNo) AND TBW.PROD_MNTH IN (:prodMnth) AND TBW.OSEI_ID IN (:oseiId) AND TBW.BUYER_GRP_CD IN (:byrGrpCd) ");
	
	public static final StringBuilder extctOcfLmtDtls = new StringBuilder()
		.append("SELECT TRW.POR_CD,TRW.PROD_MNTH,TRW.PROD_WK_NO,TRW.CAR_SRS,TRW.OCF_BUYER_GRP_CD,MB.OCF_REGION_CD,MB.OCF_BUYER_GRP_CD,TRW.OCF_FRME_CD,MF.FEAT_CD,MF.FEAT_SHRT_DESC,MF.FEAT_LNG_DESC, ")
		.append("SUM(TRW.REGIONAL_OCF_LMT_QTY),TRW.LINE_CLASS,TRW.PLANT_CD FROM TRN_REGIONAL_WKLY_OCF_LMT TRW INNER JOIN MST_FEAT MF ON (MF.POR_CD = TRW.POR_CD AND MF.CAR_SRS = TRW.CAR_SRS AND MF.OCF_FRME_CD = TRW.OCF_FRME_CD) ")
		.append("AND MF.FEAT_CD =TRW.FEAT_CD AND MF.FEAT_TYPE_CD =TRW.FEAT_TYPE_CD )  INNER JOIN MST_BUYER MB ON (MB.OCF_BUYER_GRP_CD = TRW.OCF_BUYER_GRP_CD AND MB.OCF_REGION_CD = TRW.OCF_REGION_CD) ")
		.append("INNER JOIN MST_POR MP ON (MP.PROD_REGION_CD = MB.PROD_REGION_CD AND MP.POR_CD = TRW.POR_CD) WHERE TRW.POR_CD IN (:porCD) ")
		.append("AND TRW.PROD_MNTH IN (:prodMnth) AND TRW.PROD_WK_NO IN (:prodWkNo) AND TRW.CAR_SRS IN (:carSrs) AND TRW.OCF_REGION_CD IN (:ocfRgnCd) ");
	
	public static final StringBuilder exctOcfLmtDtls_GroupBy = new StringBuilder()
		.append("group by TRW.POR_CD, TRW.PROD_MNTH, TRW.PROD_WK_NO, TRW.CAR_SRS, TRW.OCF_BUYER_GRP_CD, MB.OCF_REGION_CD, MB.OCF_BUYER_GRP_CD, TRW.OCF_FRME_CD, MF.FEAT_CD, MF.FEAT_SHRT_DESC,MF.FEAT_LNG_DESC, TRW.LINE_CLASS, TRW.PLANT_CD");
	
	public static final StringBuilder insertOrderDetailsTmp = new StringBuilder()
	.append("INSERT INTO B000040OrdrDtlsOutputBean(POR_CD,PROD_MNTH,PROD_WK_NO,OSEI_ID,POT_CD,PROD_ORDR_NO,OFFLN_PLAN_DATE,PROD_PLNT_CD,LINE_CLASS,PROD_DAY_NO,ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO) values(?,?,?,?,?,?,?,?,?,?,?,?)");
	
	public static final StringBuilder updateFrozenSymbol = new StringBuilder()
	.append("UPDATE B000040OrdrDtlsOutputBean SET FROZENTYPECD = ? WHERE POR_CD = ? AND OSEI_ID = ?");
	
	public static final StringBuilder updateProductionMonthCode = new StringBuilder()
	.append("UPDATE B000040OrdrDtlsOutputBean SET PROD_MTHD_CD = ? WHERE POR_CD = ? AND OSEI_ID = ?");
	
	public static final StringBuilder b000040Ordrdtlsoutputbean = new StringBuilder()
    .append("DELETE FROM B000040ORDRDTLSOUTPUTBEAN");

	public static final StringBuilder insertTRN_WKLY_PROD_ORDER = new StringBuilder()
	  .append("INSERT INTO TRN_WKLY_PROD_ORDR (POR_CD,ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO,PROD_MNTH,PROD_WK_NO,PROD_DAY_NO,OSEI_ID,POT_CD,PROD_ORDR_NO,EX_NO,SLS_NOTE_NO,")
	  .append(" DEALER_LST, OWNR_MNL,    WRNTY_BKLT,      OCF_REGION_CD,  PROD_MTHD_CD,FRZN_TYPE_CD,LINE_CLASS,PLNT_CD)")
	  .append(" SELECT POR_CD,ORDR_TAKE_BASE_MNTH,ORDR_TAKE_BASE_WK_NO,PROD_MNTH,PROD_WK_NO,PROD_DAY_NO,OSEI_ID,POT_CD,PROD_ORDR_NO,EX_NO,SALES_NOTE_NO,DEALER_LIST,OWNER_MANUAL,WARRANTY_BOOKLET,OFFLN_PLAN_DATE,PROD_MTHD_CD,FROZENTYPECD,LINE_CLASS, PROD_PLNT_CD from B000040ORDRDTLSOUTPUTBEAN");

	
	

}


