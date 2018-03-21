/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 12-11-2015      z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000027.util;

/**
 * @author z014433
 *
 */
public final class B000027Constants {
	
	/** Constant BATCH_27_ID. */
	public static final String BATCH_27_ID = "b000027";

	/** Constant BATCH_27_CONFIG_PATH. */
	public static final String BATCH_27_CONFIG_PATH = "B000027/B000027_Batch_Config.xml";
	
	public static final String GET_MONTHLY_ORDER_DETAILS_PROCESSOR = "mnthlyOrdrDtlsProcessor";
	
	public static final String DEL_MONTHLY_PRDN_ORDER_DETAILS_PROCESSOR = "delMnthlyPrdnOrdrTrn";
	
	public static final String CREATE_MNTHLY_ORDR_DTL_RPT = "mnthlyOrdrDtlRptProcessor";
	
	public static final String CREATE_MNTHLY_OCF_BRCH_RPT = "mnthlyOCFBrchRptProcessor";
	
	/** Constant Process 3.1 */
	public static final String P0003_1 = "P0003.1";
	
	/** Constant Process 3.2 */
	public static final String P0003_2 = "P0003.2";
	
	/** Constant Process 3.2 */
	public static final String P0003_2_VALIDATION = "P0003.2.1";
	
	/** Constant Process 4.2 */
	public static final String P0004_2 = "P0004.2";
	
	/** Constant Process 4*/
	public static final String P0004 = "P0004";
	
	/** Constant Process 10.1.1*/
	public static final String P0010_1_1 = "P0010.1.1";
	
	/** Constant Process 10.1.2*/
	public static final String P0010_1_2 = "P0010.1.2";
	
	/** Constant Process 10.4.1*/
	public static final String P0010_4_1 = "P0010.4.1";
	
	/** Constant Process 10.4*/
	public static final String P0010_4 = "P0010.4";
	
	/** Constant Process 10.5*/
	public static final String P0010_5 = "P0010.5";
	
	/** Constant Process 10.2*/
	public static final String P0010_2 = "P0010.2";
	
	/** Constant Process 7*/
	public static final String P0007 = "P0007";
	
	/** Constant Process 12*/
	public static final String P0012 = "P0012";
	
	/** Constant MONTHLY_ORDER_TO_PLANT */
	public static final String MONTHLY_ORDER_TO_PLANT = "MONTHLY_ORDER_TO_PLANT";
	
	/** Constant REUSE_PRODUCTION_ORDER_NO */
	public static final String REUSE_PRODUCTION_ORDER_NO = "REUSE_PRDN_ORDER_NO";
	
	/** Constant ORDER_HORIZON */
	public static final String ORDER_HORIZON = "ORDER_HORIZON";
	
	/** Constant SEND_SUSPENDED_ORDER */
	public static final String SEND_SUSPENDED_ORDER = "SEND_SUSPENDED_ORDER";
	
	/** Constant ATTACH_EX_NO */
	public static final String ATTACH_EX_NO = "ATTACH_EX_NO";
	
	/** Constant ATTACH_SERVICE_PRMTR */
	public static final String ATTACH_SERVICE_PRMTR = "ATTACH_SERVICE_PRMTR";
	
	/** Constant EIM_DISPLAY_FORMAT */
	public static final String ABOLISH_DATE_DISPLAY_FORMAT = "ABLSH DT DIS FORMAT";
	
	/** Constant FULL_HORIZON */
	public static final String HORIZON_FULL = "FULL";
	
	/** Constant FULL_HORIZON */
	public static final String HORIZON_INFINITY = "INFINITY";
	
	public static final String ONE = "01";
	public static final String TWO = "02";
	public static final String THREE = "03";
	public static final String FOUR = "04";
	public static final String FIVE = "05";
	public static final String SIX = "06";
	public static final String SEVEN = "07";
	public static final String EIGHT = "08";
	public static final String NINE = "09";
	public static final String TEN = "10";
	public static final String ELEVEN = "11";
	public static final String TWELVE = "12";
	
	public static final String JAN_01 = "A";
	public static final String FEB_02 = "B";
	public static final String MAR_03 = "C";
	public static final String APR_04 = "D";
	public static final String MAY_05 = "E";
	public static final String JUN_06 = "F";
	public static final String JUL_07 = "G";
	public static final String AUG_08 = "H";
	public static final String SEP_09 = "I";
	public static final String OCT_10 = "J";
	public static final String NOV_11 = "K";
	public static final String DEC_12 = "L";

	public static final String TBL_NM_MST_SERV_PRMTR = "MST_SERV_PRMTR";
	
	public static final String SERV_PRMTR_MMM_VAL = "MMM";


	/** Constant INT_FOUR. */
	public static final int INT_FOUR = 4;
	
	/** Constant CONSTANT_TWO. */
	public static final String CONSTANT_TWO= "2";
	
	/** Constant CONSTANT_THREE. */
	public static final String CONSTANT_THREE= "3";
	
	/** Constant CONSTANT_FOUR. */
	public static final String CONSTANT_FOUR= "4";
	
	public static final String B000027_REPORT_PATH = "B000027.Report.Path";
	
	/** Added for Monthy order details report */
	
	public static final String B000027_MNTHLY_ORDR_DTL_RPT_NM = "B000027_MONTHLY_ORDER_DETAIL";
	
	public static final String B000027_MNTHLY_ORDR_DTL_RPT_SHEET_NM = "MONTHLY ORDER DETAIL";

	public static final String B000027_REPORT_DATE_FORMAT = "YYYY-MM";
	
	public static final String REPORT_HEADER_MC_REGION = "MC Region" ;
	
	public static final String REPORT_HEADER_BYR_GRP_CD = "Buyer Group Code";
	
	public static final String REPORT_HEADER_SPEC_DEST_CD = "SpecDestinaton";
	
	public static final String REPORT_HEADER_END_ITEM_MDL = "End item Model";
	
	public static final String REPORT_HEADER_EX_NO = "EX-No";
	
	public static final String REPORT_HEADER_ADD_SPEC_CD = "Additional Spec Code";

	public static final String REPORT_HEADER_SLS_NOTE_NO = "Sales Note Number";
	
	public static final String REPORT_HEADER_COLOR = "Color";
	
	public static final String REPORT_HEADER_QTY = "Quantity";
	
	public static final String REPORT_HEADER_TYR_MKR = "Tyre Maker";
	
	public static final String REPORT_HEADER_TYR_SRVC = "Service";
	
	public static final String REPORT_HEADER_BDY_PRTN = "Body Protection";
	
	public static final String REPORT_HEADER_OPTN_SPC_CD = "Option Spec Code";
	
	public static final String B000027_SRVC_ERR_RPT_NM = "B000027_SERVICE_ERROR";
	
	public static final String B000027_SRVC_ERR_RPT_SHEET_NM = "SERVICE ERROR REPORT";
	
	public static final String REPORT_HEADER_MDL_CD = "Model Code";
	
	public static final String REPORT_HEADER_SPEC_CD = "Spec Code";
	
	public static final String REPORT_HEADER_SLS_NOTE = "Sales Note";
	
	public static final String B000027_ORDR_SPEC_ERR_RPT_NM = "B000027_MONTHLY_PRODUCTION_ORDER_SPEC_ERROR";
	
	public static final String B000027_ORDR_SPEC_ERR_RPT_SHEET_NM = "SPEC ERROR REPORT";
	
	public static final String REPORT_HEADER_ABOLISH_MONTH = "EIM Abolish Month";
	
	public static final String CONSTANT_FOURTEEN = "14";
	
	public static final String CONSTANT_FALSE = "false";
	
	public static final String REPORT_HEADER_SLS_NOTE_NUMBER = "Sales Note Number";
	
	public static final String B000027_OCF_BRCH_RPT_NM = "B000027_MONTHLY_PRODUCTION_ORDER_OCF_BREACH";
	
	public static final String B000027_OCF_BRCH_ALL_RPT_NM = "B000027_MONTHLY_PRODUCTION_ORDER_OCF_BREACH_ALL";
	
	public static final String B000027_OCF_BRCH_HRZNTL_SHEET_NM = "MNTHLY OCF BREACH REPORT";
	
	public static final String B000027_OCF_BRCH_VRTL_SHEET_NM = "MNTHLY OCF BREACH REPORT ALL";
	
	public static final String REPORT_HEADER_OCF_FRAME_CD = "Frame Cd";
	
	public static final String REPORT_HEADER_OCF_FEAT_CD = "Feature";
	
	public static final String REPORT_HEADER_SHRT_DESC = "Short Description";
	
	public static final String REPORT_HEADER_LONG_DESC = "Long Description";
	
	public static final String REPORT_HEADER_LMT = "Limit";
	
	public static final String REPORT_HEADER_USG = "Usage";
	
	public static final String REPORT_HEADER_BRCH = "Breach";
	
	public static final String REPORT_HEADER_R_C = "R/C";
	
	public static final String REPORT_HEADER_OCF_FRAME_CODE = "Frame Code";
	
	public static final String PRMTR_FRM_CD = "frameCd";
	
	public static final String PRMTR_FTRE_CD = "ftreCd";
	
	
	/** Constant INT_FIFTEEN. */
	public static final int INT_FIFTEEN = 15;
	
	/** Constant INT_EIGHT. */
	public static final int INT_EIGHT = 8;
	
	/** Constant INT_TWO. */
	public static final int INT_TWO = 2;
	
	/** Constant INT_ONE. */
	public static final int INT_ONE = 1;
	
	public static final String HYPHEN = "-";
	
	public static final String CONSTANT_MAX_VAL = "9999999";
	
	public static final String CONSTANT_RC_LMT = "M-OCF";
	
	public static final String CONSTANT_RC_USG = "USAGE";
	
	public static final String CONSTANT_RC_DIFF = "Breach";
	
	public static final String PRDN_ORDR_NO = "prdnOrdrNo";
	
	public static final String EX_NO = "exNo";
	
	public static final String SALES_NOTE_NO = "slsNoteNo";
	
	public static final String TYRE_MKR = "tyrMkr";
	
	public static final String DEALER_LST = "dlrLst";
	
	public static final String OWNR_MNUL = "ownrMnl";
	
	public static final String WRNTY_BLKT = "wrntyBklt";
	
	public static final String BDY_PRTN_CD = "bdyPrtnCd";
	
	public static final String SRVC_ERR_RPT_MAP_VAL = "ServiceError";
	
	public static final String MNTHLY_PRDN_TRN_VAL = "PrdnOrdrDtls";
	
private B000027Constants() {
    
}

}
