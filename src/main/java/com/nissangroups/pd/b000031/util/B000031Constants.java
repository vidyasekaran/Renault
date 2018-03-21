/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000031.util;

/**
*  Constants for B000031
*
* @author z015399
*/
public class B000031Constants {
	
	public static final String SC = "SC";
	public static final String SO = "SO";
	 
	public static final String PROD_ORDER_STAGE_CD_10 = "10";
	public static final String PROD_ORDER_STAGE_CD_20 = "20";
	
	public static final String PARAM_PORCD = "porCd";
	public static final String PARAM_OTBM = "otbm";
	public static final String PARAM_PRDMNTH = "prdMnth";
	public static final String PARAM_OSEIID = "oseiID";
	public static final String PARAM_BYRID = "byrID";
	public static final String PARAM_FEATURECD = "featCd";
	public static final String PARAM_OCFFRMCD = "ocfFrmCd";
	public static final String PARAM_CARSRS = "carSrs";
	public static final String PARAM_BYRGRPCD = "byrGrpCd";
	public static final String PARAM_BYROCFUSGQTY = "byrOCFUsgQty";
	public static final String PARAM_BYRGRPOCFUSGQTY = "byrGrpOCFUsgQty";
	public static final String PARAM_REGIONCD = "ocfRegionCd";
	public static final String PARAM_OCFBYRGRPCD = "ocfByrGrpCd";
	public static final String PARAM_TBLNAME = "tblName";
	public static final String PARAM_FEATURETYPECD = "featureTypeCd";
	public static final String PARAM_REGOCFUSGQTY = "regOCFUsgQty";
	public static final String PARAM_REF_TIME = "prsRefTime";	
	public static final String PARAM_PROCESS_EXE_TIME = "prsExeTime";
	public static final String PARAM_FEATURE_TBL ="featureTbl";
	public static final String PARAM_OTBM_PLUS_OTBWN = "otbmPlusOtbw";
	public static final String PARAM_PROD_PLNT_CD = "prdPlntCd";
	public static final String PARAM_LINE_CLASS = "lnClass";
	public static final String PARAM_PROD_DAY_NO = "prdDayNo";
	public static final String BATCH_CFG =  "B000031/B000031_Batch_Config.xml";
	public static final String BEANVAL = "b000031";
	public static final String BATCHID = "B000031";
	public static final String WKLY_TBL_NM = "MST_WKLY_ORDR_TAKE_BASE";
	public static final String WKLY_SMRY = "WKLY OCF SUMRY";
	public static final String PLNT_SMRY = "PLT LN SUMRY";
	public static final String PRMTR1 = "B000031-Create Weekly OCF Limit and auto allocation";
	public static final String PLNT_LN_SMRY = "PLANT LINE SUMMARY";
	public static final String PRMTR_TBL_NM = "MST_PRMTR";
	public static final String CNST_LN_CLS = "CNST_PLT_LN_CLS";
	public static final String PLNT_LN_CLS = "PLANT CD & LINE CLASS";
	public static final String CNST_DY_NO = "CNST_DAY_NO";
	public static final String CONST_DAY_NO = "CONSTANT_DAY_NO";
	public static final String TRN_LMT_TBL_NM = "TRN_DAILY_OCF_LMT";
	public static final String RGNL_WKLY_TBL = "TRN_REGIONAL_WKLY_OCF_LMT";
	public static final String BYR_GRP_CD = "Buyer Group Code";
	
	private B000031Constants(){
		
	}


}
