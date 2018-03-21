/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000050.util;

/**
 * @author z014433
 *
 */
public final class B000050Constants {
	
	/** Constant BATCH_50_ID. */
	public static final String BATCH_50_ID = "b000050";
	
	/** Constant BATCH_50_ID_MSG. */
	public static final String BATCH_50_ID_MSG = "B000050";

	/** Constant BATCH_50_CONFIG_PATH. */
	public static final String BATCH_50_CONFIG_PATH = "B000050/B000050_Batch_Config.xml";
	
	/** Constant BATCH_ID_B000050. */
	public static final String BATCH_ID_B000050 = "B000050";
	
	public static final String PRMTR_PRCS_TYP	 = "prcsTyp";
	
	public static final String PRMTR_STAGE_CD	 = "stgCd";
	
	public static final String UPDT_LGCL_PIPLNE_PROCESSOR = "updateLgclPipLnProcessor";
	
	public static final String B000050WRITER = "b000050Writer";
	
	/** Constant ORDR_TK_BS_WEEK_NO. */
	public static final String ORDR_TK_BS_WK_NO = "OTWN";
	
	/** Constant WK*/
	public static final String CONSTANT_WK = "WK";
	
	/** Constant dynaQuery*/
	public static final String CONSTANT_DYNAQRY = "dynaQuery";
	
	/** Constant TBL_NM_MST_WKLY_ORDR_TAKE_BASE_PERIOD */
	public static final String TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD = "WEEKLY ORDER TAKE BASE PERIOD MST";
	
	public static final String TBL_NM_LATEST_MASTER_SCHEDULE_TRN = "LATEST_MASTER_SCHEDULE_TRN";
	
	public static final String TBL_NM_LOGICAL_PIP_LN_TRN = "LOGICAL_PIPELINE_TRN";
	
	public static final String P0001_1 = "P0001.1";
	
	public static final String P0001_2 = "P0001.2";
	
	/** Constant PIPELINE_ORDER_HRZN */
	public static final String PIPELINE_ORDER_HRZN = "PIPELINE_ORDER_HORIZON"; // Modified for Defect # 3499
	
	public static final String HORIZON_INFINITY = "INFINITY";
	
	public static final String PRDN_PRD_LST= "prdnPrdLst";
	
	public static final String PRDN_ORDR_NO_LST = "prdnOrdrNoLst";
	
	public static final String P0004_1 = "P0004.1";
	
	public static final String LOGICAL_STAGE_CD = "LOGICAL_STAGE_CD";
	
	public static final String N_1_FORECAST = "N+1 FORECAST";
	
	public static final String N_PRDSCDL_MS2 = "N PRDSCDL MS2";
	
	public static final String N_PRDSCDL_MS2_NONFXD= "N PRDSCDL MS2 NONFXD";
	
	public static final String N_PRDSCDL_MS3_FXD = "N PRDSCDL MS3 FXD";
	
	public static final String PRDN_ORDR_NO = "prdnOrdrNo";
	
	public static final String VHCL_SEQ_ID = "vhclSeqId";
	
	public static final String PRODUCTION_WEEK = "ProdWk";
	
	public static final String OFFLINE_PLAN_DATE = "offlnPlnDt";
	
	public static final String LINE_CLASS = "lnClass";
	
	public static final String PRDN_METHOD_CD = "prdnMthdCd";
	
	public static final String FRZN_TYPE_CD = "frznTypCd";
	
	public static final String VIN_NO = "vinNo";
	
	public static final String ORDR_DEL_FLG = "ordrDelFlg";
	
	public static final String MS_FXD_FLG = "msFxdFlg";
	
	public static final String LGCL_PIPLN_STG_CD = "lgclPipLnStgCd";
	
	public static final String EX_NO = "exNo";
	
	public static final String SALES_NOTE_NO = "slsNoteNo";
	
	public static final String PROD_DAY_NO = "pdnDayNo";
	
private B000050Constants() {
    
}

}
