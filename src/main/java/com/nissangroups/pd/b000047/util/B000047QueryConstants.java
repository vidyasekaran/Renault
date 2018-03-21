/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.util;

/**
 * Constant file to keep the queries related to the batch B000047
 * 
 * @author z016127
 */
public class B000047QueryConstants {
	
	/** P0001 Constant to extract the VIN No not assigned Logical Pipeline records */
	public static final StringBuilder getLgclPipLnTrnDtlsMain = new StringBuilder()
		.append(" SELECT POR_CD,PROD_MNTH,PROD_WK_NO,OSEI_ID,POT_CD,PROD_ORDR_NO,ORDR_DEL_FLAG,FRZN_TYPE_CD,MS_FXD_FLAG,OFFLN_PLAN_DATE,PROD_PLNT_CD, ")
		.append(" PROD_MTHD_CD,EX_NO,SLS_NOTE_NO,LGCL_PPLN_STAGE_CD,VHCL_SEQ_ID,LINE_CLASS,VIN_NO,PROD_DAY_NO FROM TRN_LGCL_PPLN ")
		.append(" WHERE POR_CD = "); 
	
	/** Add where condition to  extract logical pipeline records*/
	public static final StringBuilder getLgclPipLnTrnDtlsWhr = new StringBuilder()
		.append(" AND MS_FXD_FLAG =1 AND ORDR_DEL_FLAG=0 AND VIN_NO <> NULL "); 
	
	/** Add order by clause to extract logical pipeline records*/
	public static final StringBuilder orderByClause = new StringBuilder()
		.append(" ORDER BY POR_CD,PROD_MNTH,PROD_WK_NO,OSEI_ID,PROD_PLNT_CD,LINE_CLASS ");
	
	/** P0002 Constant to extract the Logical Pipeline not attached VIN No records from PHYSICAL PIPELINE TRN */
	public static final StringBuilder getPhysclPpLnTrnDtls = new StringBuilder()
		.append(" SELECT POR_CD,VIN_NO,PRODUCTION_FAMILY_CD,BUYER_CD,ADD_SPEC_CD,SALES_NOTE_NO,CAR_SRS,APPLD_MDL_CD,PCK_CD,SPEC_DESTN_CD,EXT_CLR_CD,INT_CLR_CD,EX_NO,OCF_REGION_CD,PROD_PLNT_CD, ")
		.append(" LINE_CLASS, ENG_TYPE,ENG_NO,PRTYPE_VHCL_FLAG,INTRNL_OR_TRD_FLAG,PROD_MNTH,PROD_WK_NO,MS_OFFLINE_DATE,LGCL_PPLN_SEQ_ID,VIN_ALLCT_FLAG,OSEI_ID FROM TRN_PHYSCL_PPLN ")
		.append(" WHERE POR_CD =:porCd AND LGCL_PPLN_SEQ_ID = NULL AND PRTYPE_VHCL_FLAG <> '*' AND INTRNL_OR_TRD_FLAG = 1 AND SCRPD_DATE = NULL AND VIN_ALLCT_FLAG = 0 AND TRNSFR_DATE = NULL") 
		.append(" AND PROD_MNTH = :prodMnth AND POT_CD = :potCd AND OSEI_ID= :oseiId AND POR_CD = :porCd ");
	
	/** P0002 Constant to extract PHYSICAL PIPELINE TRN records based on case*/
	public static final StringBuilder getPhysclPplnTrnDtlsCse3 = new StringBuilder()
		.append(" AND PROD_PLNT_CD =:prodPlntCd ");
	
	/** P0002 Constant to extract PHYSICAL PIPELINE TRN records based on case*/
	public static final StringBuilder getPhysclPplnTrnDtlsCse2 = new StringBuilder()
		.append(" AND PROD_WK_NO = :prodWkNo ");
	
	/** P0003 Constant to extract the Parameter Master Table for VIN Allocation to Logical Pipeline */
	public static final StringBuilder getPrmtrMstrDtls = new StringBuilder()
		.append("SELECT KEY1,KEY2,VAL1,VAL2 FROM MST_PRMTR WHERE PRMTR_CD='VIN ALLOCATION TO LOGICAL PIPELINE' AND KEY1= :porCD ");

	/**p0005.5.4 Constant to get the SEQUENCE(LOGICAL_PIPE_TRN(VEHICLE_SEQ_ID)) */
	public static final String TRN_LGCL_PIPELN_VHCL_SEQ_ID = "select TRN_LGCL_PIPELN_VHCL_SEQ_ID.nextval from dual";  
	
	/** P0005.5.1 Update the VIN NO to Logical Pipeline Trn */
	public static final StringBuilder updateLgclPpln = new StringBuilder()
		.append("UPDATE TRN_LGCL_PPLN SET VIN_NO = :vinNo WHERE VHCL_SEQ_ID = :vhclSeqId AND PROD_MNTH = :prodMnth AND PROD_WK_NO = :prodWkNo ")
		.append("AND POT_CD = :potCd AND OSEI_ID = :oseiId AND POR_CD = :porCd ");

	/** P0005.5.2 Update Physical Pipeline Trn */
	public static final StringBuilder updatePhysclPpln = new StringBuilder()
		.append("UPDATE TRN_PHYSCL_PPLN SET LGCL_PPLN_SEQ_ID = :vhclSeqId ,VIN_ALLCT_FLAG='1' WHERE VIN_NO = :vinNo AND POR_CD = :porCd ");	 

	/** P0005.5.3 Remaining Vin List not allocated to Logical Pipeline Trn from Physical Pipeline Trn Table */
	public static final StringBuilder remaingVinLst = new StringBuilder()
		.append("SELECT OCF_REGION_CD,PROD_MNTH,CAR_SRS,BUYER_CD,PROD_WK_NO,VIN_NO,CONCAT(APPLD_MDL_CD,PCK_CD),CONCAT(EXT_CLR_CD,INT_CLR_CD),SPEC_DESTN_CD,EX_NO, ")
		.append("PLNND_OFFLN_DATE, PROD_PLNT_CD,OSEI_ID,POT_CD  FROM TRN_PHYSCL_PPLN WHERE LGCL_PPLN_SEQ_ID = NULL AND PRTYPE_VHCL_FLAG <> * ") 
		.append("AND INTRNL_OR_TRD_FLAG = '1' AND SCRPD_DATE = NULL AND VIN_ALLCT_FLAG = 0 AND TRNSFR_DATE = NULL AND POR_CD = ");
	 
	/** P0005.5.4 Update Sequence ID in PHYSICAL PIPELINE TRN for not allocated Vin to Logical Pipeline Trn */
	public static final StringBuilder updateSeqId = new StringBuilder()
		.append("UPDATE TRN_PHYSCL_PPLN SET LGCL_PPLN_SEQ_ID = :vhclSeqId WHERE POR_CD = :porCd AND OSEI_ID= :oseiId  AND PROD_MNTH = :prodMnth AND PROD_WK_NO = :prodWkNo "); 
	
	/**P0006.1 Check the Parameter Master for extracting Latest Offline date */
	public static final StringBuilder extractPrmtrMst = new StringBuilder()
		.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD='VIN_ALLOCATION_OFFLINE_EXTRACTION' AND KEY1= :porCD "); 
	
	/** P0006.2 Extract Latest MS Offline Date / Planned Offline Date */
	public static final StringBuilder extctOfflineDate = new StringBuilder()
		.append("SELECT POR_CD,MAX(");
	
	/** P0006.2 Extract Latest MS Offline Date / Planned Offline Date */
	public static final StringBuilder extctOfflineDateCndtn = new StringBuilder()
		.append(") FROM TRN_PHYSCL_PPLN WHERE POR_CD = :porCd  AND PRTYPE_VHCL_FLAG <> '*' AND INTRNL_OR_TRD_FLAG ='1' AND SCRPD_DATE = NULL GROUP BY POR_CD ");
	
	/** P0006.3 Offline Date calculation based on Parameter Mst */
	public static final StringBuilder extctRmngLgclPpln = new StringBuilder()
		.append("SELECT KEY2,VAL1,VAL2 FROM MST_PRMTR WHERE PRMTR_CD = 'VIN ALLOCATION -  REMAING LOGICAL PIPELINE ERROR CASE' AND KEY1= :porCD ");
	
	/** P0007 Extract Records from Logical Pipeline Trn where VIN_NO not allocated */
	public static final StringBuilder extctRmngOrdrs = new StringBuilder()
		.append("SELECT TLP.POR_CD,MOR.OCF_REGION_CD,TLP.PROD_MNTH,MPCS.CAR_SRS,MB.BUYER_CD,TLP.PROD_WK_NO,TLP.PROD_ORDR_NO,MOS.APPLD_MDL_CD,MOS.PCK_CD, ")
		.append("MOSEI.EXT_CLR_CD,MOSEI.INT_CLR_CD,MOS.SPEC_DESTN_CD,TLP.SLS_NOTE_NO,EXNO.EX_NO,TLP.OFFLN_PLAN_DATE,TLP.PROD_PLNT_CD ")
		.append("FROM MST_OSEI MOSEI INNER JOIN TRN_LGCL_PPLN TLP ON (TLP.OSEI_ID = MOSEI.OSEI_ID AND TLP.POR_CD = MOSEI.POR_CD) ")
		.append("INNER JOIN MST_EX_NO EXNO ON (EXNO.POT_CD = TLP.POT_CD AND EXNO.PROD_MNTH = TLP.PROD_MNTH AND EXNO.OEI_BUYER_ID = MOSEI.OEI_BUYER_ID ) ")
		.append("INNER JOIN MST_OEI_SPEC MOS ON (MOS.POR_CD = EXNO.POR_CD) INNER JOIN MST_POR_CAR_SRS MPCS ON (MPCS.PROD_FMY_CD = MOS.PROD_FMY_CD AND MPCS.POR_CD = MOS.POR_CD) ")
		.append("INNER JOIN MST_POR MP ON (MP.POR_CD = MOS.POR_CD) INNER JOIN MST_OEI_BUYER MOB ON (MOB.POR_CD = MOS.POR_CD AND MOB.OEI_SPEC_ID = MOS.OEI_SPEC_ID " )
		.append("AND MOB.OEI_BUYER_ID = MOSEI.OEI_BUYER_ID AND MOB.POR_CD = MOSEI.POR_CD) INNER JOIN MST_BUYER MB ON (MB.BUYER_CD = MOB.BUYER_CD AND MB.PROD_REGION_CD  = MP.PROD_REGION_CD) ")
		.append("INNER JOIN MST_OCF_REGION MOR ON (MOR.PROD_REGION_CD = MB.PROD_REGION_CD) WHERE TLP.VIN_NO = NULL AND TLP.POR_CD =  ");
	
	/** Add where condition to extract Records from Logical Pipeline Trn where VIN_NO not allocated */
	public static final StringBuilder extctRmngOrdrsWhrCndtn1 = new StringBuilder()
		.append("TLP.PROD_MNTH");
	
	/** Add where condition to extract Records from Logical Pipeline Trn where VIN_NO not allocated */
	public static final StringBuilder extctRmngOrdrsWhrCndtn2 = new StringBuilder()
		.append("|| TLP.PROD_WK_NO");
	
	/** Add where condition to extract Records from Logical Pipeline Trn where VIN_NO not allocated */
	public static final StringBuilder extctRmngOrdrsWhrCndtn3 = new StringBuilder()
		.append("|| TLP.PROD_DAY_NO");
	
	/**
	 * Instantiates a new B000047 query constants.
	 */
	private B000047QueryConstants() {
		
	}

}


