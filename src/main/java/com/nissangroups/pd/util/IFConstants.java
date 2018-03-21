package com.nissangroups.pd.util;

public class IFConstants {

	/** Constant INTERFACE_FILE_ID. */
	public static final String INTERFACE_FILE_ID = "interfaceFileId";

	/** Constant Sequence_Number. */
	public static final String SEQ_NO = "seqNumber";

	/** Constant File_Name. */
	public static final String FILE_NAME = "fileName";

	/** Constant File_Name. */
	public static final String STAGE_CODE = "stageCd";
	

	/** Constant File_Name. */
	public static final String PRMTR_CD = "prmtrCd";
	
	/** Constant File_Name. */
	public static final String MAX_ORDR_TAKBAS_MNTH = "maxOrdrTakMnth";

	/** Constant INPUT_PARAM. */
	public static final String INPUT_PARAM = "inputParam";

	/** Constant INPUT_PARAM. */
	public static final String param_IfFileID = ":ifFileId";

	/** Constant INPUT_PARAM. */
	public static final String ifFileID_Param = "ifFileId";

	/** Constant INPUT_PARAM. */
	public static final String param_porCdLst = "porCds";

	/** Constant INPUT_PARAM. */
	public static final String param_buyerCD = "MST_BUYER_GRP.BUYER_GRP_CD = :buyerCD";

	/** Constant INPUT_PARAM. */
	public static final String buyerCD_Param = ":buyerCD";

	/** Constant INPUT_PARAM. */
	public static final String param_buyer_buyerGrpCD = "MST_BUYER.BUYER_GRP_CD = :buyerGrpCD";

	/** Constant INPUT_PARAM. */
	public static final String buyer_buyerGrpCD_Param = ":buyerGrpCD";

	/** Constant INPUT_PARAM. */
	public static final String param_rhqCd = "MST_BUYER_GRP.RHQ_CD = :rhqCD";

	/** Constant INPUT_PARAM. */
	public static final String rhqCd_Param = ":rhqCD";

	/** Constant INPUT_PARAM. */
	public static final String param_ocfRegionCd = "MST_BUYER.OCF_REGION_CD = :ocfRegionCD";

	/** Constant INPUT_PARAM. */
	public static final String ocfRegionCd_Param = ":ocfRegionCD";

	/** Constant INPUT_PARAM. */
	public static final String param_ocfBuyerGrpCd = "MST_BUYER.OCF_BUYER_GRP_CD = :ocfBuyerGrpCD";
	
	/** Constant INPUT_PARAM. */
	public static final String param_mstFeat_ocfBuyerGrpCd = "MST_FEAT.OCF_BUYER_GRP_CD = :ocfBuyerGrpCD";

	/** Constant INPUT_PARAM. */
	public static final String ocfBuyerGrpCd_Param = ":ocfBuyerGrpCD";

	/** Constant INPUT_PARAM. */
	public static final String param_porCd = "MST_POR.POR_CD = :porCD";
	
	/** Constant INPUT_PARAM. */
	public static final String param_mstFeat_porCd = "MST_FEAT.POR_CD = :porCD";

	/** Constant INPUT_PARAM. */
	public static final String porCd_Param = ":porCD";

	/** Constant INPUT_PARAM. */
	public static final String param_ordrTkBsMnth = "MST_MNTH_ORDR_TAKE_BASE_PD.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth";

	/** Constant INPUT_PARAM. */
	public static final String ordrTkBsMnth_Param = ":ordrTkBsMnth";
	
	/** Constant INPUT_PARAM. */
	public static final String param_OCF_ordrTkBsMnth = "TRN_BUYER_GRP_MNTHLY_OCF_LMT.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth";

	/** Constant INPUT_PARAM. */
	public static final String ordrTkBsMnth_OCF_Param = ":ordrTkBsMnth";

	/** Constant INPUT_PARAM. */
	public static final String param_prdLmt = "MST_OSEI_DTL.OSEI_ABLSH_DATE >= :oseiAblshDt";

	/** Constant INPUT_PARAM. */
	public static final String prdLmt_Param = ":oseiAblshDt";

	/** Constant INPUT_PARAM. */
	public static final String param_endItmSttsCd = "MST_OSEI_DTL.END_ITM_STTS_CD IN :endItmSttsCD";

	/** Constant INPUT_PARAM. */
	public static final String endItmSttsCd_Param = ":endItmSttsCD";

	/** Constant UPDATED_DATA. */
	public static final String UPDATED_DATA = "updatedData";

	public static final String INTERFACE_FILE_HEADER = "INTERFACE_FILE_ID.INTERFACE_FILE_ID = :interfaceFileId";

	public static final String CMN_FILE_HDR_STATUS = "COMMON_FILE_HEADER.STATUS ='UP'";

	public static final String CMN_HDR_LAYER_SEQ = "COMMON_LAYER_DATA.SEQUENCE_NO=	COMMON_FILE_HEADER.SEQUENCE_NO AND";

	public static final String INTERFACE_CMN_LAYER = "COMMON_LAYER_DATA.INTERFACE_FILE_ID = :cmninterfaceFileId";
	public static final String porCDList = "COMMON_LAYER_DATA.Col2 IN (:porList)";

	public static final String osei_extClrCD = "extClrCd";

	public static final String osei_intClrCD = "intClrCd";

	public static final String oeiBuyer_buyerCD = "buyerCd";

	public static final String oeiSpec_specDestnCD = "specDestnCd";

	public static final String oeiSpec_carSrs = "carSrs";

	public static final String oeiSpec_adtnlSpecCD = "adtnlSpecCd";

	public static final String oeiSpec_pckCD = "pckCd";
	
	public static final String oeiSpec_prodFamilyCD = "prodFamilyCd";
	
	public static final String oeiSpec_appldMdlCD = "appldMdlCd";
	
	public static final String oeiSpec_porCD = "porCd";	

	public static final String hdrStatus = "U";

	public static final String SUMMARIZE_ORDER_QTY_FLAG = "summarizeOrderQtyFlag";

	public static final String param_MnthPorCD = "TRN_MNTH_PROD_SHDL.POR_CD	= :porCD";

	public static final String param_MnthOrdrTake = "TRN_MNTH_PROD_SHDL.ORDRTK_BASE_MNTH = :ordrTkBsMnth";

	public static final String param_trnMnthOrdrTake = "TRN_MNTH_PROD_SHDL.PROD_MNTH != :ordrTkBsMnth";

	public static final String param_WklyPorCD = "TRN_WKLY_PROD_SHDL.POR_CD	= :porCD";

	public static final String param_WklyOrdrTake = "TRN_WKLY_PROD_SHDL.ORDR_TAKE_BASE_MNTH = :ordrTkBsMnth";

	public static final String param_trnWklyOrdrTake = "TRN_WKLY_PROD_SHDL.PROD_MNTH = :ordrTkBsMnth";

	public static final String VHCL_SEQ_ID = "vhclSeqId";
	public static final String POR_CD = "porCd";
	public static final String PROD_PLNT_CD = "prodPlntCd";
	public static final String OFFLN_PLAN_DATE = "offlnPlanDate";
	public static final String LGCL_PPLN_STAGE_CD = "lgclPplnStageCd";
	public static final String SLS_NOTE_NO = "slsNoteNo";
	public static final String EX_NO = "exNo";
	public static final String PROD_MNTH = "prodMnth";
	public static final String POT_CD = "potCd";
	public static final String PROD_ORDR_NO = "prodOrdrNo";
	public static final String ORDR_DEL_FLAG = "ordrDelFlag";
	public static final String MS_FXD_FLAG = "msFxdFlag";
	public static final String LINE_CLASS = "lineClass";
	public static final String FRZN_TYPE_CD = "frznTypeCd";
	public static final String PROD_MNTH_CD = "prodMthdCd";
	public static final String VIN_NO = "vinNo";
	public static final String OSEI_ID = "oseiId";
	public static final String CRTD_BY = "crtdBy";	
	
	public static final String USER_ID= "userId";
	
	public static final String ROLE_ID="roleId";
	public static final String USER_NAME ="userName";
	public static final String TIME_ZONE="timeZone";
	public static final String UPDTD_BY ="updtdBy";
	public static final String ROLE_DESC ="roleDesc";
	
	public static final String BUYER_GRP_CD = "buyerGrpCd";
	public static final String OCF_BUYER_GRP_CD = "ocfBuyerGrpCd";
	public static final String OCF_REGION_CD = "ocfRegionCd";
	public static final String RHQ_CD = "rhqCd";

	public static final String ProdMnth_param = ":prodMnth";
	public static final String param_OcfRgnCd = "MST_OCF_REGION.OCF_REGION_CD = :ocfRegionCD";
	public static final String param_OcfByrGrpCd ="MST_OCF_REGION.OCF_BUYER_GRP_CD = :ocfBuyerGrpCD";
	public static final String param_RgnlWkly_Por = "TRN_REGIONAL_WKLY_OCF_LMT.POR_CD = :porCD";
	public static final String param_RgnlWkly_OcfRgnCd ="TRN_REGIONAL_WKLY_OCF_LMT.OCF_REGION_CD = :ocfRegionCD";
	public static final String param_RgnlWkly_OcfByrGrp ="TRN_REGIONAL_WKLY_OCF_LMT.OCF_BUYER_GRP_CD = :ocfBuyerGrpCD";
	public static final String param_RgnlWklyPrdMnth ="TRN_REGIONAL_WKLY_OCF_LMT.PROD_MNTH = :prodMnth";
	
	public static final String param_ByrGrpWkly_PorCd="TRN_BUYER_GRP_WKLY_OCF_LMT.POR_CD = :porCD";
	public static final String param_ByrGrpWkly_ByrGrpCd ="TRN_BUYER_GRP_WKLY_OCF_LMT.BUYER_GRP_CD = :buyerGrpCD";
	public static final String param_ByrGrpWkly_PrdMnth="TRN_BUYER_GRP_WKLY_OCF_LMT.PROD_MNTH = :prodMnth";
	
	public static final String ablshDate_Param = ":ablshDate";
	
	public static final String nscFeatAblhLmt = "NSC_FEATURE_ABOLISH_LIMIT";
	
	public static final String ERROR_FILENAME = "errFileName";
	
	public static final String I000055_REPORT_PATH = "I000055.Report.Path";
	
	public static final String REPORT_SUFFIX_I55 = "I000055-WEEKLY_OCF_LIMIT_FEATURE_ERROR_REPORT";	
	
	public static final String CAR_SRS = "carSrs";	
	public static final String OCF_FRME_SRT_CD = "ocfFrmeSrtCd";	
	public static final String OCF_MDL_GRP = "ocfMdlGrp";	
	public static final String OCF_IDNTCTN_CD = "ocfIdntctnCd";	
	public static final String PROD_DAY_NO = "prodDayNo";	
	public static final String OCF_FRME_CD = "ocfFrmeCd";	
	public static final String OCF_DATE = "ocfDate";	
	public static final String FEAT_CD = "featCd";	
	public static final String OCF_LMT_QTY = "ocfLmtQty";	
	public static final String STD_QTY = "stdQty";	
	public static final String PROD_WK_NO = "prodWkNo";	
	public static final String ORDR_TAK_BAS_MNTH = "ordrTakBasMnth";	
	public static final String ORDR_TAK_BAS_WK_NO = "ordrTakBasWkNo";	
	public static final String LAST_WK_SYMBL = "lastWkSymbl";
	public static final String PROCESS_STTS_CD = "processSttsCd";
	
	public static final String 	WK_NO_YEAR ="wkNoYr";
	public static final String 	WK_STRT_DATE ="wkStrtDt";
	public static final String 	WK_END_DATE ="wkEndDt";
	public static final String 	NON_OPRTNL_FLAG ="nonOprtnlFlg";
	public static final String  POR_CD_WILD = ",*";
	public static final String  POR_CD_WILD_ALL = "all";
	
	
	public static final int PROD_MNTH_CHAR_LENGTH = 6;	
	public static final int PROD_MNTH_START_INDEX = 5;	
	public static final int PROD_MNTH_END_INDEX = 6;
	public static final int PROD_MNTH_YEAR_INDEX = 3;
	public static final String INIT_SEQ_NO = "000001";
	
	public static final String ALPHABET_A  = "A";
	public static final String ALPHABET_B  = "B";
	public static final String ALPHABET_C  = "C";
	public static final String ALPHABET_D  = "D";
	public static final String ALPHABET_E  = "E";
	public static final String ALPHABET_F  = "F";
	public static final String ALPHABET_G  = "G";
	public static final String ALPHABET_H  = "H";
	public static final String ALPHABET_I  = "I";
	public static final String ALPHABET_J  = "J";
	public static final String ALPHABET_K  = "K";
	public static final String ALPHABET_L  = "L";
	
	public static final String ZERO  = "0";
	public static final String DOUBLE_ZERO  = "00";
	public static final String TRI_ZERO  = "000";
	public static final String FOUR_ZERO  = "0000";
	public static final String FIVE_ZERO  = "00000";
	
	/** Constant AMPERSAND. */
	public static final String AMPERSAND ="&";
	
	/**Constant input FORMAT */
	public static final String FORMAT = "@@";
	
	/**Constant ASTRIECK */
	public static final String ASTRIECK = "*";
	
	/**Constant Query string */
	public static final String QRYSTRNG = "' AND ";
	
	public static final int ARGS_LENGTH = 5;
	
	/** Constant I43 */
	public static final String DATAPORCD = "Data for POR_CD : ";
	public static final String PORCARSRSMST = "POR_CAR_SRS_MST"; 	
	public static final String POOO6 = "P0006";
	public static final String FEATMST =  "FEAT_MST";

	public static final int SPLTLNGTH = 10;
	public static final int MXQTYSTART = 0;
	public static final int MXQTYEND = 5;
	public static final int STNDRSTART = 5;
	public static final int STNDREND = 10;
	public static final int PRDWEEKNO = 1;
	public static final int PRDDAY = 1;
	public static final int NXTCOUNT = 10;
	public static final int COUNT = 1 ;

	public static final int ADPTDT = 0;
	public static final int ABLSDT = 0;
	public static final int PRDMNTH = 0;
	public static final int PRODDAYNO = 7;

	public static final char SEND_TRANSACTION_TYPE = 'S';
	public static final char RECEIVE_TRANSACTION_TYPE = 'R';
	public static final String EMPTY_FILENAME = "";
	
	public static final String PRMTR_VAL_YES = "YES";
}
