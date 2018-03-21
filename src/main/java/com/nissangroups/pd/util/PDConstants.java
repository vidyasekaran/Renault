/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-PDConstants
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

/**
 * The Class PDConstants.
 *
 * @author z002548
 */
public final class PDConstants {
	
	/** job related Tables*/
	
	public static final String TRN_JOBSTRM_EXEC="TRN_JOBSTRM_EXEC";
	
	public static final String TRN_JOBSTRM_SHDL="TRN_JOBSTRM_SHDL";
	
	public static final String MST_Job="MST_JOB";
	public static final String JOB_EXEC_TRN="JOB_EXEC_TRN";
	//public static final String LOG_FILES_PATH="D:/LocalData/z015883/appl-log/";
	public static final String LOG_FILES_PATH="/home/asp2manage/application/appl-log/";
	/*
	 * Batch-58 Email Constants
	 */
	public static final String JOB_EXEC_SEQ_ID="jobExecSeqId";
	public static final String JOB_EXEC_STTS="stts";
	public static final String EMAIL="email";
	public static final String DEFAULT_FRM_ADDRSS="defltFrmAddrss";

	/** Batch 1 and Interface 4 Constant Start. */
	public static final String STEP_ID_1 = "step1";

	/** Constant STEP_ID_2. */
	public static final String STEP_ID_2 = "step2";

	/** Constant STEP_ID_3. */
	public static final String STEP_ID_3 = "step3";

	/** Constant STEP_ID_4. */
	public static final String STEP_ID_4 = "step4";

	/** Constant STEP_ID_5. */
	public static final String STEP_ID_5 = "step5";

	/** Constant STEP_ID_6. */
	public static final String STEP_ID_6 = "step6";

	/** Constant STEP_ID. */
	public static final String STEP_ID = "Step ID :";

	/** Constant STEP_FAIL. */
	public static final String STEP_FAIL = "Fail";

	/** Constant STEP_SUCCESS. */
	public static final String STEP_SUCCESS = "Success";

	/** Constant ERROR_TYPE_CODE. */
	public static final String ERROR_TYPE_CODE = "ERROR TYPE CODE";

	/** Constant POR_CODE. */
	public static final String POR_CODE = "POR CODE";

	/** Constant PRODUCTION_FAMILY_CODE. */
	public static final String PRODUCTION_FAMILY_CODE = "PRODUCTION FAMILY CODE";

	/** Constant PRODUCTION_STAGE_CODE. */
	public static final String PRODUCTION_STAGE_CODE = "PRODUCTION STAGE CODE";

	/** Constant BUYER_CODE. */
	public static final String BUYER_CODE = "BUYER CODE";

	/** Constant END_ITEM_MODEL_CODE. */
	public static final String END_ITEM_MODEL_CODE = "END ITEM MODEL CODE";

	/** Constant COLOR_CODE. */
	public static final String COLOR_CODE = "COLOR CODE";

	/** Constant EXTERIOR_COLOR_CODE. */
	public static final String EXTERIOR_COLOR_CODE = "EXTERIOR COLOR CODE";

	/** Constant ADDITION_SPEC_CODE. */
	public static final String ADDITION_SPEC_CODE = "ADDITIONAL SPEC CODE";

	/** Constant SPEC_DESTINATION_CODE. */
	public static final String SPEC_DESTINATION_CODE = "SPEC DESTINATION CODE";

	/** Constant ADOPT_MONTH. */
	public static final String ADOPT_MONTH = "ADOPT MONTH";

	/** Constant ABOLISH_MONTH. */
	public static final String ABOLISH_MONTH = "ABOLISH MONTH";

	/** Constant COMMENTS. */
	public static final String COMMENTS = "COMMENTS";

	/** Constant DELIMITE_TAB. */
	public static final String DELIMITE_TAB = "\t";

	/** Constant ERROR_FLAG. */
	public static final String ERROR_FLAG = "1";

	/** Constant SUCCESS_FLAG. */
	public static final String SUCCESS_FLAG = "2";

	/** Constant WARNING_FLAG. */
	public static final String WARNING_FLAG = "4";

	/** Constant POR_ERROR_FLAG. */
	public static final String POR_ERROR_FLAG = "1";

	/** Constant BUYER_CODE_ERROR_FLAG. */
	public static final String BUYER_CODE_ERROR_FLAG = "2";

	/** Constant SPEC_DEST_ERROR_FLAG. */
	public static final String SPEC_DEST_ERROR_FLAG = "3";

	/** Constant COLOR_ERROR_FLAG. */
	public static final String COLOR_ERROR_FLAG = "4";

	/** Constant INTERNAL_SUCCESS. */
	public static final String INTERNAL_SUCCESS = "5";

	/** Constant ERROR_TYPE_ZERO. */
	public static final String ERROR_TYPE_ZERO = "10 - INITIAL";

	/** Constant ERROR_TYPE_ONE. */
	public static final String ERROR_TYPE_ONE = "20 - RETRY";

	/** Constant EI_SPEC_ERROR_FLAG. */
	public static final String EI_SPEC_ERROR_FLAG = "0";

	/** Constant EI_SPEC_INTERNAL_ERROR_FLAG. */
	public static final String EI_SPEC_INTERNAL_ERROR_FLAG = "0";

	/** Constant REMARKS_FAIL. */
	public static final String REMARKS_FAIL = "Insertion Failed";

	/** Constant REMARKS_SUC. */
	public static final String REMARKS_SUC = "Successfully Completed";

	/** Constant FAILED. */
	public static final String FAILED = "FAILED";

	/** Constant STEP_STATUS_FAIL. */
	public static final String STEP_STATUS_FAIL = "FAILED";

	/** Constant COMMENTS_1. */
	public static final String COMMENTS_1 = "Err: POR Code MISMATCH - Please check the Combination on POR Master Screen. (PST-DRG-S001)";

	/** Constant COMMENTS_2. */
	public static final String COMMENTS_2 = "Err: POR Code , BUYER CODE  MISMATCH - Please check the Combination on Buyer Master Screen. (PST-DRG-S008)";

	/** Constant COMMENTS_3. */
	public static final String COMMENTS_3 = "Err: POR Code ,BUYER CODE, SPEC DESTNN  MISMATCH - Please check the Combination on Buyer Master Screen. (PST-DRG-S008)";

	/** Constant COMMENTS_4. */
	public static final String COMMENTS_4 = "Wrn: EXT_CLR MISMATCH - Please Contact G-SIS Team to solve this issue.";

	/** Constant ERROR_CODE. */
	public static final String ERROR_CODE = "ERROR_CODE";

	/** Constant ERROR_CODE_10. */
	public static final String ERROR_CODE_10 = "10 - ERRROR - CAR SERIES NOT ATTACHED TO SPEC";

	/** Constant ERROR_CODE_20. */
	public static final String ERROR_CODE_20 = "20 - ERRROR -  MULTIPLE CAR SERIES FOUND";

	/** Constant ERROR_CODE_21. */
	public static final String ERROR_CODE_21 = "21  - ERRROR -  MULTIPLE CAR SERIES FOUND, CAR SERIES NOT ATTACHED";

	/** Constant ERROR_CODE_22. */
	public static final String ERROR_CODE_22 = "22  - ERRROR -  MULTIPLE CAR SERIES FOUND, OLD CAR SERIES MAINTAINED.";

	/** Constant ERROR_CODE_30. */
	public static final String ERROR_CODE_30 = "30  - WARNING -  CAR SERIES CHANGED";

	/** Constant ERROR_CODE_40. */
	public static final String ERROR_CODE_40 = "40  - WARNING -CAR SERIES NOT FOUND FOR PARTIAL PRODUCTION PERIOD";

	/** Constant ERROR_CODE_50. */
	public static final String ERROR_CODE_50 = "50 - WARNING - DIFFERENT FAMILY CODE WITH SAME CAR SERIES";

	/** Constant COMM_HEADER_STATUS_SUC. */
	public static final String COMM_HEADER_STATUS_SUC = "P";

	/** Constant COMM_HEADER_STATUS_FAIL. */
	public static final String COMM_HEADER_STATUS_FAIL = "F";

	/** Constant ERROR_REPORT_ALL. */
	public static final String ERROR_REPORT_ALL = "ALL";

	/** Constant CREATE_BY. */
	public static final String CREATE_BY = "I000004";

	/** Constant BATCH_1. */
	public static final String BATCH_1 = "B000001";

	/** Constant YEAR_START_INDEX. */
	public static final int YEAR_START_INDEX = 0;

	/** Constant YEAR_END_INDEX. */
	public static final int YEAR_END_INDEX = 4;

	/** Constant MONTH_START_INDEX. */
	public static final int MONTH_START_INDEX = 4;

	/** Constant MONTH_END_INDEX. */
	public static final int MONTH_END_INDEX = 6;

	/** END **************. */
	/** Custom Log **************************/
	// public static final String BACK_SLASH = "\\";
	public static final String BACK_SLASH = "/";

	/** Constant DOT. */
	public static final String DOT = ".";

	/** End *******************************. */

	public static final int INT_ONE = 1;

	/** Constant INT_FIVE. */
	public static final int INT_FIVE = 5;

	/** Constant INT_SIX. */
	public static final int INT_SIX = 6;

	/** Constant INT_TEN. */
	public static final int INT_TEN = 10;

	/** Constant INT_SIXTEEN. */
	public static final int INT_SIXTEEN = 16;

	/** Constant INT_SEVENTEEN. */
	public static final int INT_SEVENTEEN = 17;

	/** Constant INT_TWENTY. */
	public static final int INT_TWENTY = 20;

	/** Constant B4_OCF_BUYER_GRP_CODE. */
	public static final String B4_OCF_BUYER_GRP_CODE = "OCF BUYER GROUP CODE";

	/** Constant PRMTR_BUYERCD. */
	public static final String PRMTR_BUYERCD = "buyerCd";

	/** Constant PRMTR_OEISPECID. */
	public static final String PRMTR_OEISPECID = "oeiSpecId";

	/** Constant PRMTR_OEIB_TOBEDELETEDLIST. */
	public static final String PRMTR_OEIB_TOBEDELETEDLIST = "oeiBtobeDeleted";

	/** Constant PRMTR_SUSPNDED_DT. */
	public static final String PRMTR_SUSPNDED_DT = "suspendedDate";

	/** Constant PRMTR_STTS_CD. */
	public static final String PRMTR_STTS_CD = "sttsCd";

	/** Constant OEISPECID_PREFIX. */
	public static final String OEISPECID_PREFIX = "OEI";

	/** Constant OEIBUYERID_PREFIX. */
	public static final String OEIBUYERID_PREFIX = "OEIB";

	/** Constant OSEIID_PREFIX. */
	public static final String OSEIID_PREFIX = "OSEI";

	/** Constant WARNING_WEEKLY. */
	public static final String WARNING_WEEKLY = "WARNING :Order Take Base Month  Belongs to  WEEKLY, But only Monthly Feature Type 00 Frame (Feature code: ";

	/** Constant WARNING_MONTHLY. */
	public static final String WARNING_MONTHLY = "WARNING : Order Take Base Month  Belongs to  MONTHLY, But only Weekly Feature Type 00 Frame (Feature code: ";

	/** Constant WARNING_REPORT. */
	public static final String WARNING_REPORT_B4 = ") code Exists. So 00 frame code (Feature code: ";

	/** Constant WARNING_REPORT. */
	public static final String WARNING_REPORT_B4_temp = ") for Weekly Feature Type is automatcially attached for the period from ";

	/** Constant WARNING_ADOPT. */
	public static final String WARNING_ADOPT = "WARNING : For 00 FRAME CODE FEATURE ADOPT DATE is greater than min( CAR SERIES ADOPT DATE AND END ITEM ADOPT DATE ) So Feature can be attached only from ";

	/** Constant WARNING_ABOLISH. */
	public static final String WARNING_ABOLISH = "WARNING : For 00 FRAME CODE  feature ABOLISH DATE LESSER than min( CAR SERIES ABOLISH DATE AND END ITEM ABOLISH DATE )  So Feature can be attached only from ";

	/** Constant WARNING_ADOPT_ABOLISH. */
	public static final String WARNING_ADOPT_ABOLISH = ". Feature cannot be attached for the period from ";

	/** Constant WARNING_REPORT. */
	public static final String WARNING_REPORT = "-ALL-";

	/** Constant WARNING_REPORT_EXTCODE. */
	public static final String WARNING_REPORT_EXTCODE = "**";

	/** Constant EI_SPEC_INTERNAL_POR_FLAG. */
	public static final String EI_SPEC_INTERNAL_POR_FLAG = "1";

	/** Constant PRMTR_PROD_FMLY_CD. */
	public static final String PRMTR_PROD_FMLY_CD = "prod_fmly_cd";

	// I00008 Constants

	/** Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";

	/** Constant SINGLE_SPACE. */
	public static final String SINGLE_SPACE = " ";

	/** Constant CONSTANT_ZERO. */
	public static final String CONSTANT_ZERO = "0";

	/** Constant UPDATE_ONLY_FLAG. */
	public static final String UPDATE_ONLY_FLAG = "updateOnlyFlag";

	/** Constant MINIMUM_CAR_SERIES_LIMIT. */
	public static final String MINIMUM_CAR_SERIES_LIMIT = "minimumCarSeriesLimit";

	/** Constant PRMTRT_TABLE_NAME. */
	public static final String PRMTRT_TABLE_NAME = "tableName";

	/** Constant TABLE_NAME_ORDERABLE_SALES_END_ITEM_DETAIL_MST. */
	public static final String TABLE_NAME_ORDERABLE_SALES_END_ITEM_DETAIL_MST = "ORDERABLE_SALES_END_ITEM_DETAIL_MST";

	/** Constant BATCH_7_ID. */
	public static final String BATCH_7_ID = "B000007";

	/** Constant INTERFACE_5_ID. */
	public static final String INTERFACE_5_ID = "I000005";

	/** Constant PRMTR_BATCH_ID. */
	public static final String PRMTR_BATCH_ID = "batchId";

	/** Constant PARAMETER_CD. */
	public static final String PARAMETER_CD = "parameterCd";

	/** Constant WEEK1. */
	// Redmine issue # 1059
	public static final String WEEK1 = "11";

	public static final String WEEK_SUFFIX = "1";

	/** Constant PRMTR_REPORT_FILENAME. */
	// B000003
	public static final String PRMTR_REPORT_FILENAME = "REPORT_FILENAME";

	/** Constant MAX_ORDER_TAKE_BASE_MONTH. */
	public static final String MAX_ORDER_TAKE_BASE_MONTH = "MAX_ORDER_TAKE_BASE_MONTH";

	/** Constant MINIMUM_CAR_SERIES_PERIOD. */
	public static final String MINIMUM_CAR_SERIES_PERIOD = "MINIMUM_CAR_SERIES_PERIOD";

	/** Constant PRMTR_MINIMUM_CAR_SERIES_PERIOD. */
	public static final String PRMTR_MINIMUM_CAR_SERIES_PERIOD = "minimumCarSeriesPeriod";

	/** Constant BATCH_3_ID. */
	public static final String BATCH_3_ID = "B000003";

	/** Constant ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED. */
	public static final String ERROR_MESSAGE_CAR_SERIES_NOT_ATTACHED = "Car Series not attached to Spec.";

	/** Constant PRMTR_ORDER_TAKE_BASE_MONTH. */
	public static final String PRMTR_ORDER_TAKE_BASE_MONTH = "ORDER_TAKE_BASE_MONTH";

	/** Constant POR_CD. */
	public static final String POR_CD = "POR_CD";

	/** Constant PRODUCTION_PLANT_CD. */
	public static final String PRODUCTION_PLANT_CD = "PRODUCTION_PLANT_CD";

	/** Constant PRODUCTION_METHOD_CD. */
	public static final String PRODUCTION_METHOD_CD = "PRODUCTION_METHOD_CD";

	/** Constant ORDR_TK_BS_MNTH. */
	public static final String ORDR_TK_BS_MNTH = "OTBM";

	/** Constant PRODUCTION_FAMILY_CD. */
	public static final String PRODUCTION_FAMILY_CD = "PRODUCTION_FAMILY_CD";

	/** Constant SPEC_DESTINATION_CD_CONDITION. */
	public static final String SPEC_DESTINATION_CD_CONDITION = "SPEC_DESTINATION_CD_CONDITION";

	/** Constant CR_SRS. */
	public static final String CR_SRS = "CAR_SERIES";

	/** Constant PRMTR_CURRENT_TIME. */
	public static final String PRMTR_CURRENT_TIME = "currentTime";

	/** Constant PRMTR_Updt_BY. */
	public static final String PRMTR_UPDT_BY = "updtBy";

	/** Constant PRMTR_MST_UPTD_TIME. */
	public static final String PRMTR_MST_UPTD_TIME = "masterUptdTime";

	/** Constant PRMTR_OSEI_ADOPT_DT. */
	public static final String PRMTR_OSEI_ADOPT_DT = "oseiAdptDt";

	/** Constant PRMTR_OSEI_ABOLISH_DT. */
	public static final String PRMTR_OSEI_ABOLISH_DT = "oseiAblshDt";

	/** Constant TWENTYONE. */
	public static final String TWENTYONE = "21";

	/** Constant PRMTR_CLR. */
	public static final String PRMTR_CLR = "color";

	/** Constant PRMTR_OEI_SPEC_ID. */
	public static final String PRMTR_OEI_SPEC_ID = "oeiSpecId";

	/** Constant PRMTR_EIM_STTS_CD_1. */
	public static final String PRMTR_EIM_STTS_CD_1 = "eimSttsCd1";

	/** Constant PRMTR_EIM_STTS_CD_2. */
	public static final String PRMTR_EIM_STTS_CD_2 = "eimSttsCd2";

	/** Constant SYMBL_PERCENTAGE. */
	public static final String SYMBL_PERCENTAGE = "%";

	/** Constant CONSTANT_ALL. */
	public static final String CONSTANT_ALL = "-ALL-";

	/** Constant EXIT_STATUS. */
	public static final String EXIT_STATUS = "Exit Status";

	/** Constant MULTIPLE_CAR_SERIES. */
	public static final String MULTIPLE_CAR_SERIES = "Multiple car series";

	/** Constant PERSISTENCE_NAME. */
	public static final String PERSISTENCE_NAME = "default";

	/** Constant OEISPECID. */
	public static final String OEISPECID = "OEI_SPEC_ID";

	/** Constant OEIBUYERID. */
	public static final String OEIBUYERID = "OEI_BYR_ID";

	/** Constant MSTOEIBUYER. */
	public static final String MSTOEIBUYER = "MST_OEI_BUYER";

	/** Constant EI_STTS_CD. */
	public static final String EI_STTS_CD = "10";

	/** Constant PROCESSOR_SUCCESS. */
	public static final String PROCESSOR_SUCCESS = "is attached successfully with the EndItem Details";

	/** Constant WRITER_SUCCESS. */
	public static final String WRITER_SUCCESS = " Non Processed BuyerCode details are successfully inserted in";

	/** END **************. */
	/** Constant MONTHLY_OCF_IF_TRN Table. */
	public static final String MONTHLY_OCF_IF_TRN = "MONTHLY_OCF_IF_TRN";

	/** Constant POR_CAR_SERIES_MST Table. */
	public static final String POR_CAR_SERIES_MST = "POR_CAR_SERIES_MST";

	/** Constant FEATURE_MST Table. */
	public static final String FEATURE_MST = "FEATURE_MST";

	/** Constant INTERFACE26. */
	public static final String INTERFACE_26_ID = "I000026";

	/** Constant INTERFACE27. */
	public static final String INTERFACE_27_ID = "I000027";

	
	/** Constant INTERFACE88. */
	public static final String INTERFACE_88_ID = "I000088";
	
	
	/** Constant MONTHLY_ORDER_TO_PLANT */
	public static final String OCF_UNLIMITED_OCF_CHECK = "OCF_UNLIMITED_OCF_CHECK";

	/** Constant INTERFACE27. */
	public static final String PRMTR_OCF_LMT_QTY = "9999999";

	public static final String INTERFACE8 = "I000008";

	/** Constant INTERFACE11. */
	public static final String INTERFACE11 = "I000011";

	/** Constant CF_CONSTANT_M. */
	public static final String CF_CONSTANT_M = "M";
	
	/** Constant CF_CONSTANT_W. */
	public static final String CF_CONSTANT_W = "W";

	/** Constant CF_CONSTANT_C. */
	public static final String CF_CONSTANT_C = "C";

	/** Constant FEATURE_CODE_00. */
	public static final String FEATURE_CODE_00 = "00";

	/** Constant FEATURE_CODE_NOT00. */
	public static final String FEATURE_CODE_NOT00 = "01";

	/** End *******************************. */
	public static final String PRIORITY = "Priority";

	/** Constant FROZEN_PRODUCTION_MONTH. */
	public static final String FROZEN_PRODUCTION_MONTH = "Frozen_Production_Month";

	/** Constant FROZEN_TIMING. */
	public static final String FROZEN_TIMING = "Frozen_Timing";

	/** Constant FROZEN_ORDERETAKE_BASE_MONTH. */
	public static final String FROZEN_ORDERETAKE_BASE_MONTH = "Frozen_Order_Take_Base_Month";

	/** Constant FROZEN_TYPE. */
	public static final String FROZEN_TYPE = "Frozen_Type";

	/** Constant OCF_REGION_CODE. */
	public static final String OCF_REGION_CODE = "OCF_Region_Code";

	/** Constant PREFIX_YES. */
	public static final String PREFIX_YES = "Prefix_Yes";

	/** Constant PREFIX_NO. */
	public static final String PREFIX_NO = "Prefix_No";

	/** Constant SUFFIX_YES. */
	public static final String SUFFIX_YES = "Suffix_Yes";

	/** Constant SUFFIX_NO. */
	public static final String SUFFIX_NO = "Suffix_No";

	/** Constant EXT1. */
	public static final String EXT1 = "Ext1";

	/** Constant EXT2. */
	public static final String EXT2 = "Ext2";

	/** Constant EXT3. */
	public static final String EXT3 = "Ext3";

	/** Constant EXT4. */
	public static final String EXT4 = "Ext4";

	/** Constant EXT5. */
	public static final String EXT5 = "Ext5";

	/** Constant INT1. */
	public static final String INT1 = "Int1";

	/** Constant INT2. */
	public static final String INT2 = "Int2";

	/** Constant INT3. */
	public static final String INT3 = "Int3";

	/** Constant INT4. */
	public static final String INT4 = "Int4";

	/** Constant INT5. */
	public static final String INT5 = "Int5";

	/** Constant DEST1. */
	public static final String DEST1 = "Dest1";

	/** Constant DEST2. */
	public static final String DEST2 = "Dest2";

	/** Constant DEST3. */
	public static final String DEST3 = "Dest3";

	/** Constant DEST4. */
	public static final String DEST4 = "Dest4";

	/** Constant DEST5. */
	public static final String DEST5 = "Dest5";

	/** Constant ERROR_MESSAGE. */
	public static final String ERROR_MESSAGE = "Error Message";

	/** Constant CONSTANT_ONE. */
	public static final String CONSTANT_ONE = "1";
	/** Constant CONSTANT_TWO. */
	public static final String CONSTANT_TWO = "2";
	
	/** Constant CONSTANT_FIVE. */
	public static final String CONSTANT_FIVE = "5";

	/** Constant PREFIX. */
	public static final String PREFIX = "prefix";

	/** Constant SUFFIX. */
	public static final String SUFFIX = "suffix";

	/** Constant SPECDESTCD. */
	public static final String SPECDESTCD = "specDestinationCd";

	/** Constant EXTCOLOR. */
	public static final String EXTCOLOR = "exteriorColor";

	/** Constant INTCOLOR. */
	public static final String INTCOLOR = "interirorColor";

	/** Constant ORDERTAKEBASEMONTH. */
	public static final String ORDERTAKEBASEMONTH = "orderTakeBaseMonth";

	/** Constant CARSERIES. */
	public static final String CARSERIES = "carSeries";

	/** Constant N. */
	public static final String N = "N";

	/** Constant ADD_SYMBOL. */
	public static final String ADD_SYMBOL = "+";

	/** Constant OCF_FRAME_CODE_ZERO. */
	public static final String OCF_FRAME_CODE_ZERO = "00";

	/** Constant ZERO_ONE. */
	public static final String ZERO_ONE = "01";

	/** Constant POR. */
	public static final int POR = 0;

	/** Constant CAR_SRS. */
	public static final int CAR_SRS = 1;

	/** Constant SHRT_DESC. */
	public static final int SHRT_DESC = 2;

	/** Constant LNG_DESC. */
	public static final int LNG_DESC = 3;

	/** Constant OCF_FRME_CD. */
	public static final int OCF_FRME_CD = 4;

	/** Constant OCF_REGION_CD. */
	public static final int OCF_REGION_CD = 5;

	/** Constant OCF_BUYER_GRP_CD. */
	public static final int OCF_BUYER_GRP_CD = 6;

	/** Constant OCF_ADPT_DATE. */
	public static final int OCF_ADPT_DATE = 7;

	/** Constant OCF_ABLSH_DATE. */
	public static final int OCF_ABLSH_DATE = 8;

	/** Constant OPTN_SPEC_CD. */
	public static final int OPTN_SPEC_CD = 9;

	/** Constant FEAT_TYPE_CD. */
	public static final int FEAT_TYPE_CD = 10;

	/** Constant OCF_PRITY_CD. */
	public static final int OCF_PRITY_CD = 11;

	/** Constant OTBP_WKLY. */
	public static final int OTBP_WKLY = 0;

	/** Constant PROD_STAGE_CD. */
	public static final int PROD_STAGE_CD = 2;

	/** Constant PARAM_VALUE. */
	public static final int PARAM_VALUE = 1;

	/** Constant INT_ZERO. */
	public static final int INT_ZERO = 0;

	// Array Index outof bound exception so i modified the OTBP_Monthly
	/** Constant OTBP_MNTHLY. */
	// public static final int OTBP_MNTHLY = 3;
	public static final int OTBP_MNTHLY = 2;

	/** Constant OCF_SIZE. */
	public static final int OCF_SIZE = 14;

	/** Constant FEATURE_SIZE. */
	public static final int FEATURE_SIZE = 17;

	/** Constant DATE_FORMAT. */
	public static final String DATE_FORMAT = "yyyyMMdd";

	/** Constant PRMTR_PRODUCTION_STAGE_CODE. */
	public static final String PRMTR_PRODUCTION_STAGE_CODE = "productionStageCode";

	/** Constant PRMTR_BASE_PERIOD_WEEKLY. */
	public static final String PRMTR_BASE_PERIOD_WEEKLY = "orderTakeBasePeriodWeekly";

	/** Constant PRMTR_FTRE_ADPT_DATE. */
	public static final String PRMTR_FTRE_ADPT_DATE = "ftreAdoptDate";

	/** Constant PRMTR_FTRE_ABLSH_DATE. */
	public static final String PRMTR_FTRE_ABLSH_DATE = "ftreAbolishDate";

	/** Constant DATE_FORMAT_MONTH. */
	public static final String DATE_FORMAT_MONTH = "yyyyMM";

	/** Constant MONTHLY. */
	public static final String MONTHLY = "Monthly";

	/** Constant WEEKLY. */
	public static final String WEEKLY = "Weekly";

	/** Constant FEATURE_POR_CD. */
	public static final int FEATURE_POR_CD = 0;

	/** Constant FEATURE_CAR_SRS. */
	public static final int FEATURE_CAR_SRS = 1;

	/** Constant FEATURE_FEAT_CD. */
	public static final int FEATURE_FEAT_CD = 2;

	/** Constant FEATURE_OCF_FRME_CD. */
	public static final int FEATURE_OCF_FRME_CD = 3;

	/** Constant FEATURE_FEAT_TYPE_CD. */
	public static final int FEATURE_FEAT_TYPE_CD = 4;

	/** Constant FEATURE_FEAT_GRP_CD. */
	public static final int FEATURE_FEAT_GRP_CD = 5;

	/** Constant FEATURE_FEAT_SHRT_DESC. */
	public static final int FEATURE_FEAT_SHRT_DESC = 6;

	/** Constant FEATURE_FEAT_LNG_DESC. */
	public static final int FEATURE_FEAT_LNG_DESC = 7;

	/** Constant FEATURE_CRTD_BY. */
	public static final int FEATURE_CRTD_BY = 8;

	/** Constant FEATURE_CRTD_DT. */
	public static final int FEATURE_CRTD_DT = 9;

	/** Constant FEATURE_UPDTD_BY. */
	public static final int FEATURE_UPDTD_BY = 10;

	/** Constant FEATURE_UPDTD_DT. */
	public static final int FEATURE_UPDTD_DT = 11;

	/** Constant FEATURE_OCF_REGION_CD. */
	public static final int FEATURE_OCF_REGION_CD = 12;

	/** Constant FEATURE_OCF_BUYER_GRP_CD. */
	public static final int FEATURE_OCF_BUYER_GRP_CD = 13;

	/** Constant FEATURE_FEAT_ADPT_DATE. */
	public static final int FEATURE_FEAT_ADPT_DATE = 14;

	/** Constant FEATURE_FEAT_ABLSH_DATE. */
	public static final int FEATURE_FEAT_ABLSH_DATE = 15;

	/** Constant FEATURE_CAR_GRP. */
	public static final int FEATURE_CAR_GRP = 16;

	/** Constant FEATURE_OEI_ID. */
	public static final int FEATURE_OEI_ID = 17;

	/** Constant TEN. */
	public static final String TEN = "10";

	/** Constant TWENTY. */
	public static final String TWENTY = "20";

	/** Constant THIRTY. */
	public static final String THIRTY = "30";

	/** Constant FORTY. */
	public static final String FORTY = "40";

	/** Constant FIFTY. */
	public static final String FIFTY = "50";

	/** Constant ZERO. */
	public static final String ZERO = "00";

	/** Constant ONE. */
	public static final String ONE = "00001";

	/** Constant ELEVEN. */
	public static final String ELEVEN = "11";

	/** Constant BATCH000004. */
	public static final String BATCH000004 = "B4";

	/** Constant BATCH000005. */
	public static final String BATCH000005 = "B5";

	/** Constant DELIMITE_SPACE. */
	public static final String DELIMITE_SPACE = "\\s";

	/** Constant EXCEPTION. */
	public static final String EXCEPTION = "Exception";

	/** Constant B4_POR_CODE. */
	public static final String B4_POR_CODE = "POR CODE";

	/** Constant CAR_SERIES. */
	public static final String CAR_SERIES = "CAR SERIES";

	/** Constant B4_BUYER_CODE. */
	public static final String B4_BUYER_CODE = "BUYER CODE";

	/** Constant EI_MODEL_CODE. */
	public static final String EI_MODEL_CODE = "END ITEM MODEL CODE";

	/** Constant EI_COLOR_CODE. */
	public static final String EI_COLOR_CODE = "END ITEM COLOR CODE";

	/** Constant ADOPT_DATE. */
	public static final String ADOPT_DATE = "ADOPT DATE";

	/** Constant ABOLISH_DATE. */
	public static final String ABOLISH_DATE = "ABOLISH DATE";

	/** Constant ADD_SPEC_CODE. */
	public static final String ADD_SPEC_CODE = "ADD SPEC CODE";

	/** Constant SPEC_DESTINATION. */
	public static final String SPEC_DESTINATION = "SPEC DESTINATION";

	/** Constant B4_OCF_REGION_CODE. */
	public static final String B4_OCF_REGION_CODE = "OCF REGION CODE";

	/** Constant OCF_BUYER_GRP_CODE. */
	public static final String OCF_BUYER_GRP_CODE = "OCF BUYER GROUP CODE";

	/** Constant FEATURE_ERROR. */
	public static final String FEATURE_ERROR = "OCF BUYER GROUP CODE";

	/** Constant PRMTR_APPLDMDLCD. */
	public static final String PRMTR_APPLDMDLCD = "appldMdlCd";

	/** Constant PRMTR_PACKCD. */
	public static final String PRMTR_PACKCD = "packCd";

	/** Constant PRMTR_SPECDSTNCD. */
	public static final String PRMTR_SPECDSTNCD = "specDstnCd";

	/** Constant PRMTR_ADPTDATE. */
	public static final String PRMTR_ADPTDATE = "adptDate";

	/** Constant PRMTR_ABLSHDATE. */
	public static final String PRMTR_ABLSHDATE = "ablshDate";

	/** Constant PRMTR_EXTRCLRCD. */
	public static final String PRMTR_EXTRCLRCD = "extrClrCd";

	/** Constant PRMTR_INTRCLRCD. */
	public static final String PRMTR_INTRCLRCD = "intrClrCd";

	/** Constant PRMTR_PORCD. */
	public static final String PRMTR_PORCD = "porCd";

	/** Constant PRMTR_FEAT_CD. */
	public static final String PRMTR_FEAT_CD = "featCd";

	/** Constant PRMTR_FEAT_SHRT_DESC. */
	public static final String PRMTR_FEAT_SHRT_DESC = "shrtDesc";

	/** Constant PRMTR_FEAT_LONG_DESC. */
	public static final String PRMTR_FEAT_LONG_DESC = "longDesc";

	/** Constant PRMTR_FEAT_GRP_CD. */
	public static final String PRMTR_FEAT_GRP_CD = "featGrpCd";

	/** Constant PRMTR_EI_MIN_ADPT_DATE. */
	public static final String PRMTR_EI_MIN_ADPT_DATE = "EIM_MIN_ADPT_DATE";

	/** Constant PRMTR_ORACLE. */
	public static final String PRMTR_ORACLE = "ORACLE";

	/** Constant PRMTR_ERROR. */
	public static final String PRMTR_ERROR = "ERROR";

	/** Constant PRMTR_WARNING. */
	public static final String PRMTR_WARNING = "WARNING";

	/** Constant PRMTR_OSEI_ADPT_DATE. */
	public static final String PRMTR_OSEI_ADPT_DATE = "OSEI_ADPT_DATE";

	/** Constant PRMTR_FEAT_TYPE_CD. */
	public static final String PRMTR_FEAT_TYPE_CD = "featTypeCd";

	/** Constant PRMTR_SHORT_DSC. */
	public static final String PRMTR_SHORT_DSC = "shortDsc";

	/** Constant PRMTR_BUYER. */
	public static final String PRMTR_BUYER = "shortDsc";

	/** Constant PRMTR_OCF_FRAME_CD. */
	public static final String PRMTR_OCF_FRAME_CD = "ocfFrameCD";

	/** Constant PRMTR_UK_OSEI_ID. */
	public static final String PRMTR_UK_OSEI_ID = "ukOseiID";

	/** Constant PRMTR_UK_OEI_ID. */
	public static final String PRMTR_UK_OEI_ID = "ukOeiID";

	/** Constant PRMTR_UK_OEI_BUYEER_ID. */
	public static final String PRMTR_UK_OEI_BUYEER_ID = "ukOeiBuyerID";

	/** Constant PRMTR_BATCH. */
	public static final String PRMTR_BATCH = "batch";

	/** Constant PRMTR_POR. */
	public static final String PRMTR_POR = "por";

	/** Constant PRMTR_CARSRS. */
	public static final String PRMTR_CARSRS = "carSrs";

	/** Constant START_AFTER_VALUE. */
	public static final String START_AFTER_VALUE = "start.after";

	/** Constant PRMTR_UPDATE_FLAG. */
	public static final String PRMTR_UPDATE_FLAG = "updateOnlyFlag";

	/** Constant VALUE_NOT_SET. */
	public static final int VALUE_NOT_SET = -1;

	/** Constant PRMTR_fordrTkBsMnth. */
	public static final String PRMTR_OCFBYRGRPCD = "ocfByrGrpCd";

	/** Constant PRMTR_OCFRGNCD. */
	public static final String PRMTR_OCFRGNCD = "ocfRgnCd";

	/** Constant PRMTR_BASEPERIOD. */
	public static final String PRMTR_BASEPERIOD = "basePeriod";

	/** Constant PRMTR_PRDSTGCD. */
	public static final String PRMTR_PRDSTGCD = "prdStgCd";

	/** Constant PRMTR_ZERO. */
	public static final String PRMTR_ZERO = "0";

	/** Constant PRMTR_ONE. */
	public static final String PRMTR_ONE = "1";

	/** Constant WARNING. */
	public static final String WARNING = "WARNING : FEATURE CD = ";

	/** Constant ERROR. */
	public static final String ERROR = "ERROR : FEATURE CD = ";

	/** Constant ERROR1. */
	public static final String ERROR1 = " is created for 00  OCF_FRAME_CD automatically for the period from ";

	/** Constant DATE. */
	public static final String DATE = "DATE";

	/** Constant TO. */
	public static final String TO = " to ";

	/** Constant DELIMITE_HYPHEN. */
	public static final String DELIMITE_HYPHEN = "-";

	/** Constant BATCH_ID. */
	public static final String BATCH_ID = "BATCH_ID";

	/** Constant BATCH_4_ID. */
	public static final String BATCH_4_ID = "B000004";
	public static final String BATCH_5_ID = "B000005";

	/** Constant BATCH_4_MST_UPDATED_TIME. */
	public static final String BATCH_4_MST_UPDATED_TIME = "masterUptdTime";

	/** Constant UPDATE_FLG_NO. */
	public static final String UPDATE_FLG_NO = "0";

	/** Constant UPDATE_FLG_YES */
	public static final String UPDATE_FLG_YES = "1";

	/** Constant MASTER_TABLE. */
	public static final String MASTER_TABLE = "tableName";

	/** Constant MASTER_TABLE_NAME. */
	public static final String MASTER_TABLE_NAME = "MST_OSEI_DTL";

	/** Constant CONSTANT_N. */
	public static final String CONSTANT_N = "N";

	/** Constant PRMTR_FRZ_DEL_FLG. */
	public static final String PRMTR_FRZ_DEL_FLG = "frznDelFlg";

	/** Constant BATCH_POR_CODE. */
	public static final String BATCH_POR_CODE = "POR_CODE";

	/** Constant ORDER_TAKE_BASE_PERIOD. */
	public static final String ORDER_TAKE_BASE_PERIOD = "ORDER_TAKE_BASE_PERIOD";

	/** Constant ERROR_MESSAGE1. */
	public static final String ERROR_MESSAGE1 = "ERROR_MESSAGE";

	/** Constant FEATURE_CD. */
	public static final String FEATURE_CD = "FEATURE_CD";

	/** Constant OCF_BUYER_GROUP. */
	public static final String OCF_BUYER_GROUP = "OCF_BUYER_GROUP";

	/** Constant OCF_FRAME_CD. */
	public static final String OCF_FRAME_CD = "OCF_FRAME_CD";

	/** Constant OCF_REGION_CD. */
	public static final String REPORT_OCF_REGION_CD = "OCF_REGION_CD";

	/** Constant OCF_SHORT_DESCRIPTION. */
	public static final String OCF_SHORT_DESCRIPTION = "OCF_SHORT_DESCRIPTION";

	/** Constant PRODUCTION_PERIOD. */
	public static final String PRODUCTION_PERIOD = "PRODUCTION_PERIOD";

	/** Constant BATCH_PRODUCTION_FAMILY_CODE. */
	public static final String BATCH_PRODUCTION_FAMILY_CODE = "PRODUCTION_FAMILY_CODE";

	/** Constant MWCCF. */
	// I00008 Constants
	public static final String MWCCF = "40";

	/** Constant MWOCF. */
	public static final String MWOCF = "10";

	/** Constant WCCF. */
	public static final String WCCF = "60";

	/** Constant WOCF. */
	public static final String WOCF = "30";

	/** Constant PRMTRT_INTERFACE_FILE_ID. */
	public static final String PRMTRT_INTERFACE_FILE_ID = "interfaceId";

	/** Constant PRMTRT_INTERFACE_STATUS. */
	public static final String PRMTRT_INTERFACE_STATUS = "interfaceStatus";

	/** Constant PRMTRT_SEQ_NO. */
	public static final String PRMTRT_SEQ_NO = "seqNo";

	/** Constant INTERFACE_ID_I000005. */
	public static final String INTERFACE_ID_I000005 = "I000005";

	/** Constant INTERFACE_FILE_ID. */
	public static final String INTERFACE_FILE_ID = "INTERFACE_FILE_ID";

	/** Constant INPUT_PARAM. */
	public static final String INPUT_PARAM = "INPUT_PARAM";

	/** Constant INTERFACE_PROCESSED_STATUS. */
	public static final String INTERFACE_PROCESSED_STATUS = "P";

	/** Constant INTERFACE_UNPROCESSED_STATUS. */
	public static final String INTERFACE_UNPROCESSED_STATUS = "U";

	/** Constant INTERFACE_FAILURE_STATUS. */
	public static final String INTERFACE_FAILURE_STATUS = "F";

	/** Constant PRMTRT_PRD_FMLY_CD. */
	public static final String PRMTRT_PRD_FMLY_CD = "prdFmlyCd";

	/** Constant PRMTRT_EXT_CLR. */
	public static final String PRMTRT_EXT_CLR = "extClr";

	/** Constant PRMTRT_EXT_CLR_DESC. */
	public static final String PRMTRT_EXT_CLR_DESC = "extClrDesc";

	/** Constant INTERFACE_STATUS. */
	public static final String INTERFACE_STATUS = "INTERFACE_STATUS";

	/** Constant INTERFACE_ID_I000009. */
	public static final String INTERFACE_ID_I000009 = "I000009";

	/** Constant CONSTANT_SPACE. */
	public static final String CONSTANT_SPACE = " ";

	/** Constant PRMTRT_ORDER_TAKE_BASE_MONTH. */
	public static final String PRMTRT_ORDER_TAKE_BASE_MONTH = "ordrTkeBsMnth";

	/** Constant INTERFACE_ID_I000010. */
	public static final String INTERFACE_ID_I000010 = "I000010";

	/** Constant ORDER_TAKE_BASE_MONTH. */
	public static final String ORDER_TAKE_BASE_MONTH = "OrderTakeBaseMonth";

	/** Constant DEFAULT_FROZEN_DELETE_FLAG. */
	public static final String DEFAULT_FROZEN_DELETE_FLAG = "N";

	/** Constant DEFAULT_FROZEN_MANUAL_FLAG. */
	public static final String DEFAULT_FROZEN_MANUAL_FLAG = "N";

	/** Constant B000002_JOB_NM. */
	public static final String B000002_JOB_NM = "Create Spec Masters";

	/** Constant M76. */
	public static final String M76 = "M00076";

	/** Constant PRMTR_ADDITIONALSPECCD. */
	public static final String PRMTR_ADDITIONALSPECCD = "additionalSpecCd";

	/** Constant PRMTR_SPEC_DESTN_LEN. */
	public static final String PRMTR_SPEC_DESTN_LEN = "specDetsLen";

	/** Constant PRMTR_IntrClr. */
	public static final String PRMTR_IntrClr = "intrClrCd";

	/** Constant PRMTR_EXT_CLR_CD. */
	public static final String PRMTR_EXT_CLR_CD = "extrClrCd";

	/** Constant PRMTR_OCF_REGION. */
	public static final String PRMTR_OCF_REGION = "OcfRgn";

	/** Constant PRMTR_MIN_yr_MONTH. */
	public static final String PRMTR_MIN_yr_MONTH = "MinYrMnth";

	/** Constant CAR_SERIES_PRIORITY_CD. */
	public static final String CAR_SERIES_PRIORITY_CD = "CAR_SERIES_PRIORITY_CD";

	/** Constant PRMTR_PREFIX_YES. */
	public static final String PRMTR_PREFIX_YES = "PREFIX_YES";

	/** Constant PRMTR_SUFFIX_YES. */
	public static final String PRMTR_SUFFIX_YES = "SUFFIX_YES";

	/** Constant PRMTR_LENGTH_PRODUCTION_METHOD_CD. */
	public static final String PRMTR_LENGTH_PRODUCTION_METHOD_CD = "LENGTH_PRODUCTION_METHOD_CD";

	/** Constant DIFFERENCE_OF_MONTHS. */
	public static final String DIFFERENCE_OF_MONTHS = "DIFFERENCE OF MONTHS";

	/** Constant PRODUCTION_MONTH. */
	public static final String PRODUCTION_MONTH = "ProdMonth";

	/** Constant PRODUCTION_WEEK_NUMBER. */
	public static final String PRODUCTION_WEEK_NUMBER = "PROD_WK_NO";

	/** Constant END_ITEM_STATUS_CODE. */
	public static final String END_ITEM_STATUS_CODE = "END_ITM_STTS_CD";

	/** Constant MAXIMUM_ABOLISH_DATE. */
	public static final String MAXIMUM_ABOLISH_DATE = "MaxAblshDate";

	/** Constant CAR_SERIES_ORDER_HORIZON. */
	public static final String CAR_SERIES_ORDER_HORIZON = "CarSrsOderHrzn";

	/** Constant CAR_SERIES_SIZE_TRIM. */
	public static final String CAR_SERIES_SIZE_TRIM = "CarSrsSizeTrim";

	/** Constant PRMTR_MAX_ABLSH_DT. */
	public static final String PRMTR_MAX_ABLSH_DT = "maxAblshDt";

	/** Constant END_ITEM_COLOR_CODE. */
	public static final String END_ITEM_COLOR_CODE = "END ITEM COLOR CODE";

	/** Constant BATCHNM. */
	public static final String BATCHNM = "batchNm";

	/** Constant WRITER_START_MSG. */
	public static final String WRITER_START_MSG = "Data Population Initiated...";

	/** Constant WRITER_STOP_MSG. */
	public static final String WRITER_STOP_MSG = "Data Population completed...";

	/** Constant NOORDRTKEBASE. */
	public static final String NOORDRTKEBASE = "No Order Take Base Month in Open Stage";

	/** Constant ORDEREXISTS. */
	public static final String ORDEREXISTS = "Orders Exists in Monthly Order TRN table. So no updates in OSEI Details";

	/** Constant LTSTMSTRSCHDLORDEREXISTS. */
	public static final String LTSTMSTRSCHDLORDEREXISTS = "Orders Exists in Latest Master Schedule TRN table. So no updates in OSEI Details";

	/** Constant WKLYORDEREXISTS. */
	public static final String WKLYORDEREXISTS = "Orders Exists in Weekly Order TRN table. So no updates in OSEI Details";

	/** Constant NOORDRFOUND. */
	public static final String NOORDRFOUND = "No data found";
	public static final String NODATAFOUND = " No data found";

	/** Constant TOBEDELETED. */
	public static final String TOBEDELETED = "tobedeleted";

	/** Constant ERROR_MESSAGE_1. */
	public static final String ERROR_MESSAGE_1 = "&1";

	/** Constant ERROR_MESSAGE_2. */
	public static final String ERROR_MESSAGE_2 = "&2";

	/** Constant ERROR_MESSAGE_3. */
	public static final String ERROR_MESSAGE_3 = "&3";

	/** Constant ERROR_MESSAGE_4. */
	public static final String ERROR_MESSAGE_4 = "&4";

	/** Constant ERROR_MESSAGE_5. */
	public static final String ERROR_MESSAGE_5 = "&5";

	/** Constant ERROR_MESSAGE_6. */
	public static final String ERROR_MESSAGE_6 = "&6";

	/** Constant TBL_NM_WEEKLY_ORDER_TAKE_BASE_MONTH. */
	public static final String TBL_NM_WEEKLY_ORDER_TAKE_BASE_MONTH = "WEEKLY ORDER TAKE BASE MONTH";

	/** Constant TBL_NM_ORDERABLE_END_ITEM_SPEC_MST. */
	public static final String TBL_NM_ORDERABLE_END_ITEM_SPEC_MST = "ORDERABLE END ITEM SPEC MST";

	/** Constant MESSAGE_POR_CD_WEEKLY_TAKE_BASE. */
	public static final String MESSAGE_POR_CD_WEEKLY_TAKE_BASE = ":POR_CD &WEEKLY_ORDER_TAKE_BASE_PERIOD_MST";

	/** Constant MESSAGE_MINIMUM_CAR_SERIES_PERIOD. */
	public static final String MESSAGE_MINIMUM_CAR_SERIES_PERIOD = " MINIMUM CAR SERIES PERIOD";

	/** Constant MESSAGE_POR_CD_PARAMETER. */
	public static final String MESSAGE_POR_CD_PARAMETER = "POR_CD &PARAMETER_MST";

	/** Constant STEP_START. */
	public static final String STEP_START = "************Step Started  with *********************";

	/** Constant STEP_AFTER_START. */
	public static final String STEP_AFTER_START = "********************After Step Method Started***************************************";

	/** Constant BEFORE_PROCESS_START. */
	public static final String BEFORE_PROCESS_START = "********************Before Process Method Started***************************************";

	/** Constant AFTER_PROCESS_START. */
	public static final String AFTER_PROCESS_START = "********************After Process Method End***************************************";

	/** Constant STAR. */
	public static final String STAR = "*";

	/** Constant DOLLAR. */
	public static final String DOLLAR = "$";

	/** Constant READ_COUNT. */
	public static final String READ_COUNT = "Read Count : ";

	/** Constant READ_SKIPPED_COUNT. */
	public static final String READ_SKIPPED_COUNT = "Read Skipped Count : ";

	/** Constant WRITE_COUNT. */
	public static final String WRITE_COUNT = "Write Count : ";

	/** Constant WRITE_SKIPPED_COUNT. */
	public static final String WRITE_SKIPPED_COUNT = "Write Skipped Count : ";

	/** Constant STEP_EXECUTION_STATUS. */
	public static final String STEP_EXECUTION_STATUS = "Step Execution Status :";

	/** Constant STEP_AFTER_END. */
	public static final String STEP_AFTER_END = "********************After Step Method Ended***************************************";

	/** Constant BEFORE_CHUNK_METHOD_START. */
	public static final String BEFORE_CHUNK_METHOD_START = "Before Chunk Method Started";

	/** Constant BEFORE_CHUNK_METHOD_END. */
	public static final String BEFORE_CHUNK_METHOD_END = "****************Before Chunk Method Ended*******************";

	/** Constant INSIDE_METHOD. */
	public static final String INSIDE_METHOD = "Method In";

	/** Constant OUTSIDE_METHOD. */
	public static final String OUTSIDE_METHOD = "Method Out";

	/** Constant ERROR_REPORT. */
	public static final String ERROR_REPORT = "Error";

	/** Constant WARNING1. */
	public static final String WARNING1 = "Warning1";

	/** Constant WARNING2. */
	public static final String WARNING2 = "Warning2";

	/** Constant MIN_CAR_SERIES_LIMIT_MSG. */
	public static final String MIN_CAR_SERIES_LIMIT_MSG = "This is the minimum car series limit===============";

	/** Constant MIN_HORIZON_VALUE_MSG. */
	public static final String MIN_HORIZON_VALUE_MSG = "Minimum Horizon Value";

	/** Constant PATTERN_MATCHING_RESULT_MSG. */
	public static final String PATTERN_MATCHING_RESULT_MSG = "Pattern Matching Result";

	/** Constant MAX_PRODUCTION_MONTH_MSG. */
	public static final String MAX_PRODUCTION_MONTH_MSG = "Maximum Production month";

	/** Constant B000007_PROCESSOR. */
	public static final String B000007_PROCESSOR = "b000007Processor";

	/** Constant I000026_PROCESSOR. */
	public static final String I000026_PROCESSOR = "i000026Processor";

	/** Constant SINGLE_QUOTE. */
	public static final String SINGLE_QUOTE = "'";

	/** Constant SINGLE_HYPHEN. */
	public static final String SINGLE_HYPHEN = "-";

	/** Constant SINGLE_COMMA. */
	public static final String SINGLE_COMMA = ",";

	/** Constant OSEI_ID_CONDITION. */
	public static final String OSEI_ID_CONDITION = " and OSEI_ID = :ukOseiID";

	/** Constant ERROR_DATA_MSG. */
	public static final String ERROR_DATA_MSG = "Error Data :";
	/** Constant UNCHECKED. */
	public static final String UNCHECKED = "unchecked";

	/** Constant MESSAGE_WEEKLY_TAKE_BASE_PERIOD. */
	public static final String MESSAGE_WEEKLY_TAKE_BASE_PERIOD = "WEEKLY_ORDER_TAKE_BASE_PERIOD_MST";

	/** Constant MESSAGE_POR_CD. */
	public static final String MESSAGE_POR_CD = ":POR_CD";

	/** Constant MESSAGE_MST_PARAMETER. */
	public static final String MESSAGE_MST_PARAMETER = "PARAMETER_MST";

	/** Constant MESSAGE_MASTER_END_ITEM_SPEC. */
	public static final String MESSAGE_MASTER_END_ITEM_SPEC = "MST_END_ITM_SPEC";

	/** Constant MESSAGE_OSEI_PRODUCTION_TYPE_MST. */
	public static final String MESSAGE_OSEI_PRODUCTION_TYPE_MST = "OSEI_PRODUCTION_TYPE_MST";

	/** Constant MESSAGE_SPEC_REEXECUTE_STATTUS. */
	public static final String MESSAGE_SPEC_REEXECUTE_STATTUS = "SPEC_REEXECUTE_STATTUS";

	/** Constant MESSAGE_POR_CAR_SERIES_MST. */
	public static final String MESSAGE_POR_CAR_SERIES_MST = "POR_CAR_SERIES_MST";

	/** Constant MESSAGE_B000003_MESSAGE_OESI. */
	public static final String MESSAGE_B000003_MESSAGE_OESI = "ORDERABLE_END_ITEM_SPEC_MST AND ORDERABLE_SALES_END_ITEM_DETAIL_MST";

	/** Constant MESSAGE_DATA. */
	public static final String MESSAGE_DATA = "data";

	/** Constant ORDERABLE_END_ITEM_SPEC_MST. */
	public static final String ORDERABLE_END_ITEM_SPEC_MST = "ORDERABLE_END_ITEM_SPEC_MST";

	/** Constant MESSAGE_PRODUCTION_TYPE_MST. */
	public static final String MESSAGE_PRODUCTION_TYPE_MST = "PRODUCTION_TYPE_MST";

	/** Constant POR_PARAM. */
	public static final String POR_PARAM = "POR";

	/** Constant ORDER_TAKE_BASE_MONTH_PARAM. */
	public static final String ORDER_TAKE_BASE_MONTH_PARAM = "OrdertakeBaseMonth";

	/** Constant BATCH_2_ID. */
	public static final String BATCH_2_ID = "B000002";

	/** Constant I000008_ID. */
	public static final String I000008_ID = "I000008";

	/** Constant RAW_TYPES. */
	public static final String RAW_TYPES = "rawtypes";

	/** Constant FILE_ID. */
	public static final String FILE_ID = "FileID";

	/** Constant VALUES. */
	public static final String VALUES = "VALUES('";

	/** Constant COMMA_IN_QUERY. */
	public static final String COMMA_IN_QUERY = "','";

	/** Constant END_BRACKET. */
	public static final String END_BRACKET = "')";

	/** Constant DATA_INSERTED_MSG. */
	public static final String DATA_INSERTED_MSG = "Data Inserted";

	/** Constant I000009_END_MSG. */
	public static final String I000009_END_MSG = "I000009  End";

	/** Constant DATA_AVAILABLE_MSG. */
	public static final String DATA_AVAILABLE_MSG = "Data is available";

	/** Constant I000010_END_MSG. */
	public static final String I000010_END_MSG = "I0000010  End";

	/** Constant I000026_END_MSG. */
	public static final String I000026_END_MSG = "I0000026  End";

	/** Constant MIN_CAR_SERIES_PROCESSOR. */
	public static final String MIN_CAR_SERIES_PROCESSOR = "minimumCarSeriesProcessor";

	/** Constant ORDER_TAKE_BASE_MONTH_PROCESSOR. */
	public static final String ORDER_TAKE_BASE_MONTH_PROCESSOR = "ordertakebasemonthProcessor";
	public static final String B3_DRAGON_JPA_PAGING_ITEM_READER = "b3DragonJpaPagingItemReader";
	public static final String JPA_QUERY_PROVIDER_SET_MSG = "JPA query provider must be set";
	public static final String UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG = "Unable to obtain an EntityManager";
	public static final String END_ITEM_SPEC_MSG = "End Item Spec";
	public static final String READING_EXCEPTION_MSG = "Reading Exception";
	public static final String REPORT_READER_FOR_SORT_MSG = "The Report Reader for Sorting the Collections";

	public static final String SQL_USED_FIRST_PAGE_MSG = "SQL used for reading first page:";
	public static final String SQL_USED_REM_PAGES_MSG = "SQL used for reading remaining pages:";
	public static final String SQL_USED_FOR_JUMPING = "SQL used for jumping:";
	public static final String UNDER_SCORE = "_";
	public static final String USING_PARAMETER_MAP = "Using parameterMap:";
	public static final String USING_PARAMETEER_LIST = "Using parameterList:";
	public static final String REPORT_SIZE = "Report Size ";
	public static final String B000007_CUSTOM_READER = "b00007CustomReader";
	public static final String COMMON_INTERFACE_READER = "CommonInterfaceReader";
	public static final String EXTRACTED_END_ITEM_SIZE = "Extracted End Item Size";
	public static final String EXTRACTED_AFTER_GET_RESULT = "Extracted after get result : ";
	public static final String BATCH_CONFIG_CLASSPATH = "classpath:batch_config.properties";
	public static final String PRMTR_FILE_NAME = "fileName";

	public static final String B000001_REPORT_PATH = "B000001.Report.Path";
	public static final String B000004_REPORT_PATH = "B000004.Report.Path";
	public static final String B000007_REPORT_PATH = "B000007.Report.Path";
	public static final String B000003_REPORT_PATH = "B000003.Report.Path";
	public static final String B000020_REPORT_PATH = "B000020.Report.Path";
	public static final String R000004_REPORT_PATH = "R000004.Report.Path";
	public static final String R000005_REPORT_PATH = "R000005.Report.Path";
	public static final String R000036_REPORT_PATH = "R000036.Report.Path";
	public static final String REPORT_SUFFIX_R36 = "R000036-MONTHLY_OCF_LIMIT_FEATURE_ERROR_REPORT";

	public static final String REPORT_R36_ERROR_MESSAGE1 = "Feature Code Not Exist in Master for this OCF Combination.";
	public static final String REPORT_R36_ERROR_MESSAGE2 = "OCF Limit is eliminated, Because Feature Code Adopt & Abolish Period for this OCF is not valid.";

	public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	public static final String REPORT_SUFFIX = "_Report";
	public static final String REPORT_SUFFIX_B4 = "B4_Report_";
	public static final String REPORT_SUFFIX_B7 = "B7_Report_";
	public static final String REPORT_SUFFIX_B3 = "B3_Report_";
	public static final String REPORT_SUFFIX_B20 = "B20_Report_";
	public static final String REPORT_SUFFIX_R4 = "R4_Report_";
	public static final String REPORT_SUFFIX_R5 = "R5_Report_";

	public static final String FILE_EXT_TSV = ".tsv";
	public static final String FILE_EXT_XLS = ".xls";
	public static final String B1_ERROR_TYPE = "errorTypeCode";
	public static final String B1_POR_CODE = "porCode";
	public static final String B1_PROD_FAM_CODE = "productionFamilyCode";
	public static final String B1_PROD_STG_CODE = "productionStageCode";
	public static final String B1_BUYER_CODE = "buyerCode";
	public static final String B1_END_ITEM_MODEL_CODE = "endItemModelCode";
	public static final String B1_COLOR_CODE = "colorCode";
	public static final String B1_ADDITION_SPEC_CODE = "additionSpecCode";
	public static final String B1_SPEC_DEST_CODE = "specDestinationCode";
	public static final String B1_ADOPT_MONTH = "adoptMonth";
	public static final String B1_ABOLISH_MONTH = "abolistMonth";
	public static final String B1_COMMENTS = "comments";
	public static final String WRITER_NOT_OPEN_MSG = "Writer must be open before it can be written to";
	public static final String FILE_CORRUPT_MSG = "Could not write data.  The file may be corrupt.";
	public static final String FLAT_FILE_WRITE_ERROR_MSG = "Flat File Write Exceptions";
	public static final String B000003WRITER = "b000003Writer";
	public static final String FEATURE_MST_ABOLISH_UPDATE_SUCCESS_MSG = "Records are Successfully Updated in FEATURE MST";
	public static final String FEATURE_MST_INSERT_SUCCESS_MSG = "B000004 : Records are Successfully inserted in FEATURE_MST";
	public static final String ORDERABLE_SALES_END_ITEM_DETAIL_MST_UPDATE_SUCCESS_MSG = "Records are Successfully Updated in ORDERABLE_SALES_END_ITEM_DETAIL_MST";
	public static final String ORDERABLE_END_ITEM_FEATURE_MST_DELETE_SUCCESS_MSG = "Records are Successfully Deleted in ORDERABLE_END_ITEM_FEATURE_MST";
	public static final String ORDERABLE_SALES_END_ITEM_FEATURE_MST_DELETE_SUCCESS_MSG = "Records are Successfully Deleted in ORDERABLE_SALES_END_ITEM_FEATURE_MST";

	public static final String ORDERABLE_END_ITEM_FEATURE_MST_UPDTAE_SUCCESS_MSG = "Records are Successfully Updated in ORDERABLE_END_ITEM_FEATURE_MST";
	public static final String ORDERABLE_SALES_END_ITEM_FEATURE_MST_UPDATE_SUCCESS_MSG = "Records are Successfully Updated in ORDERABLE_SALES_END_ITEM_FEATURE_MST";

	public static final String ORDERABLE_END_ITEM_FEATURE_MST_INSERT_SUCCESS_MSG = "Records are Successfully inserted in ORDERABLE_END_ITEM_FEATURE_MST";
	public static final String ORDERABLE_SALES_END_ITEM_FEATURE_MST_INSERT_SUCCESS_MSG = "Records are Successfully inserted in ORDERABLE_SALES_END_ITEM_FEATURE_MST";

	public static final String UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG = "Unable to obtain a transactional Entity Manager";
	public static final String JPA_WRITE_ITEM_SIZE = "Writing to JPA with item size";
	public static final String NO_OP_ITEM_WRITER = "noOpItemWriter";
	public static final String IN_OUTPUT_WRITER_MSG = "In Output Writer";
	public static final String MST_FRZN_TABLE = "MST_FRZN";
	public static final String INSERTED = "INSERTED";
	public static final String OSEI_FROZEN_MST = "OSEI_FROZEN_MST";
	public static final String PATTERN_MATCHING = "No Matched Records";
	public static final String TBL_NAME_MST_PROD_TYPE = "MST_PROD_TYPE";
	public static final String FILE_ID_PARAM = "FileIDParam"; // Redmine issue
																// #951
	public static final String SEQ_NO_PARAM = "seqNo";// Redmine issue # 951

	public static final String INTERFACE_NAME = "InterfaceName";
	public static final String COMMON_INTERFACE_NAME_PARAMETER_CD = "INTERFACE_10_PRIMARY_KEY_LIST";

	public static final String START_ORDR_TAKE_BASE_MONTH = "startOrderTakeBaseMonth";
	public static final String END_ORDR_TAKE_BASE_MONTH = "endOrderTakeBaseMonth";

	public static final String Y = "Y";
	public static final String KEY_1 = "key1";
	public static final String KEY_2 = "key2";

	/** Constant PRMTR_SNGL_RCRD_FLG */
	public static final String PRMTR_SNGL_RCRD_FLG = "snglRcrdFlg";
	/** Constant PRMTR_PRD_ORDR_STGE_CD */
	public static final String PRMTR_PRD_ORDR_STGE_CD = "productionOrderStageCD";
	/** Constant PRMTR_ERROR_CD */
	public static final String PRMTR_ERROR_CD = "errorCd";

	public static final String IF_FILE_ID = "ifFileId";
	public static final String TRN_MNTHLY_ORDR_IF = "TRN_MNTHLY_ORDR_IF";
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String PRMTR_FRZN_TYPE = "frznType";
	public static final String PRMTR_OSEI_ID = "oseiId";

	public static final String PRMTR_OEI_BYR_ID = "oeiByrId";

	public static final String PRMTR_ORDR_TK_BS_MNTH = "ordrTkBsMnth";
	public static final String PRMTR_PRD_MNTH = "prdMnth";
	public static final String PRMTR_INT_CLR_CD = "intClrCd";
	public static final String PRMTR_BYR_CD = "byrCd";
	public static final String PRMTR_BYR_GRP_CD = "byrGrpCd";
	public static final String PRMTR_ADTNL_SPEC_CD = "adtnlSpecCd";
	public static final String PRMTR_SPEC_DEST_CD = "specDestCd";
	public static final String PRMTR_PCK_CD = "pckCd";
	public static final String PRMTR_APPLD_MDL_CD = "appldMdlCd";
	public static final String PRMTR_CAR_SRS = "carSrs";
	public static final String PRMTR_PRD_ORDR_STG_CD = "prdOrdrStgCd";
	public static final String PRMTR_POT_CD = "potCd";
	public static final String PRMTR_ORDR_QTY = "ordrQty";
	public static final String PRMTR_ROW_NO = "rowNo";
	public static final String PRMTR_HORIZON = "horizon";
	public static final String PRMTR_BYR_OCF_USAGE_QTY = "byrUsgQty";
	public static final String PRMTR_BYR_GRP_OCF_USAGE_QTY = "byrGrpUsgQty";
	public static final String PRMTR_BYR_GRP_OCF_LMT_QTY = "byrGrpLmtQty";
	public static final String PRMTR_BYR_GRP_OCF_SIM_QTY = "byrGrpSimQty";
	public static final String PRMTR_RGNL_MNTHLY_OCF_USAGE_QTY = "rgnlMnthlyOcfUsgQty";
	public static final String PRMTR_ERROR_MSG = "errorMsg";
	public static final String PRMTR_DATA_SKIPPED = "dataSkipped";
	public static final String SKIPPED = "SKIPPED";
	public static final String PROCESSED = "PROCESSED";
	public static final String PRMTR_WK_NO = "wkNo";
	public static final String PRMTR_YYYYMMDD_DATE = "yyymmddDate";

	public static final String PRMTR_ADOPT_PRD = "adptPrd";
	public static final String PRMTR_ABOLISH_PRD = "ablshPrd";
	public static final String PRMTR_ADOPT_DATE = "adptDate";
	public static final String PRMTR_ABOLISH_DATE = "ablshDate";
	public static final String PRMTR_ABOLISH_MNTH = "ablshMnth";

	public static final String PRMTR_PRMTR_CD = "prmtrCd";
	public static final String PRMTR_KEY1 = "key1";
	public static final String PRMTR_KEY2 = "key2";
	public static final String PRMTR_OCF_IDNTCTN_CD = "idntctnCd";
	public static final String PRMTR_OCF_MAX_QTY = "maxQty";
	public static final String PRMTR_OCF_STND_QTY = "stndQty";
	public static final String PRMTR_UPDTD_DT = "updtdDt";
	public static final String PRMTR_UPDTD_BY = "updtdBy";
	public static final String PRMTR_CRTD_DT = "crtdDt";
	public static final String PRMTR_CR_GRP = "crGrp";
	public static final String PRMTR_OCF_FRM_SRT_CD = "frmSrtCd";
	public static final String PRMTR_TERMINAL_ID = "trmnlId";
	public static final String PRMTR_MAINT_DT = "maintDt";
	public static final String PRMTR_MAINT_UPDTD_DT = "maintUpdtdDt";
	public static final String PRMTR_SHRT_DESC = "shrtDesc";
	public static final String PRMTR_USR_ID = "usrId";
	public static final String PRMTR_NOTES = "notes";
	public static final String PRMTR_PRODUCTION_PLANT_CD = "prdPlntCd";
	
	public static final String PRMTR_GSIS_REGION_CD = "gsisRgnCd";
	public static final String PRMTR_GSIS_MDL_NO = "gsisMdlNo";
	

	/** Constant STAGE CODE. */
	public static final String STG_CD = "STG_CD";

	/** Constant STAGE CODE D1. */
	public static final String STG_CD_D1 = "D1";

	/** Constant STAGE CODE D2. */
	public static final String STG_CD_D2 = "D2";

	/** Constant STAGE CODE F1. */
	public static final String STG_CD_F1 = "F1";

	/** Constant STAGE CODE F2. */
	public static final String STG_CD_F2 = "F2";

	public static final String CONSTANT_F = "F";

	/** Constant PRMTR_NSC_EIM_ODER_HRZN_FLAG. */
	public static final String PRMTR_NSC_EIM_ODER_HRZN_FLAG = "nscEimOrdHrznFlg";

	/** Constant PRMTR_NSC_EIM_ODER_HRZN_FLAG. */
	public static final String PRMTR_OCF_BYR_GRP_CD = "ocfByrGrpCd";

	/** Constant CONSTANT_SC */
	public static final String CONSTANT_SC = "SC";

	/**
	 * public static final String Instantiates a new PD constants.
	 */

	public static final String SC = "SC";
	public static final String SO = "SO";

	public static final String PROD_ORDER_STAGE_CODE_DRAFT = "10";
	public static final String PROD_ORDER_STAGE_CODE_FINAL = "20";

	private PDConstants() {

	}

	/* Added by z014433 for I000030 */

	/** Constant INTERFACE_ID_I000030. */
	public static final String INTERFACE_ID_I000030 = "I000030";

	/** Constant I000030_CONFIG_FILE */
	public static final String I000030_CONFIG_FILE = "I000030/I000030_IF_Config.xml";

	/** Constant I000030_END_MSG. */
	public static final String I000030_END_MSG = "I0000030  End";

	/** Constant JOBLAUNCHER. */
	public static final String jobLauncher = "jobLauncher";

	/* z014433 for I000030 changes ends */

	public static final String MONTHLY_ORDER_TAKE_BASE_PERIOD_MST = "MONTHLY_ORDER_TAKE_BASE_PERIOD_MST";
	public static final String OPEN_STAGE = "OPEN";
	public static final String PRMTR_ORDER = "order";
	public static final String CLOSED_STAGE = "CLOSED";

	/* Added by z014433 for B000021 */

	/** Constant PRMTR_SYSTEM_LOCK_FLAG. */
	public static final String PRMTR_SYSTEM_LOCK_FLAG = "systemLockFlag";

	/** Constant PRMTR_STAGE_CODE. */
	public static final String PRMTR_STAGE_CD = "stageCd";

	/** Constant PRMTR_STAGE_STATUS_CODE. */
	public static final String PRMTR_STAGE_STATUS_CD = "stageStatusCd";

	/** Constant PRMTR_STAGE_UPDATE_FLAG. */
	public static final String PRMTR_STG_UPDT_FLAG = "stageUpdateFlag";

	/** Constant BATCH_21_ID. */
	public static final String BATCH_21_ID = "b000021";

	/** Constant BATCH_21_CONFIG_PATH. */
	public static final String BATCH_21_CONFIG_PATH = "B000021/B000021_Batch_Config.xml";

	/** Constant TBL_NM_MONTHLY_ORDER_TAKE_BASE_MONTH. */
	public static final String TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD = "MONTHLY ORDER TAKE BASE PERIOD MST";

	/** Constant TBL_NM_MST_MNTHLY_ORDR_TAKE_LCK */
	public static final String TBL_NM_MST_MNTHLY_ORDR_TAKE_LCK = "MST MNTHLY ORDR TAKE LCK";

	/** Constant TBL_NM_LOCK CONFIGURATION MST */
	public static final String TBL_NM_LOCK_CONFIG_MST = "LOCK CONFIGURATION MST";

	public static final String B000021_CUSTOM_READER = "b00021CustomReader";

	public static final String YES = "Y";

	public static final String NO = "N";

	public static final String ORDR_TAKE_BASE_MNTH = "ORDR_TAKE_BASE_MNTH";

	public static final String STAGE_CD = "STAGE_CD";

	public static final String STAGE_STTS_CD = "STAGE_STTS_CD";

	public static final String SYS_LCK_STTS_CD = "SYS_LCK_STTS_CD";

	public static final String EXTRACTED_ORDER_TAKE_BASE_MONTH_SIZE = "Extracted Order Take Base Month Details Size : ";

	public static final String EXTRACTED_FLAG_DETAILS_SIZE = "Extracted Lock Configuration Mst Flag Details Size : ";

	/** Constant B000021_PROCESSOR. */
	public static final String B000021_PROCESSOR = "b000021Processor";

	public static final String GET_LOCK_FLAG_DETAILS_PROCESSOR = "nscRhqExpFlgDtlsProcessor";

	public static final String DRAFT_D1 = "D1";

	public static final String DRAFT_D2 = "D2";

	public static final String FINAL_F1 = "F1";

	public static final String FINAL_F2 = "F2";

	/** Constant Maximum Production Month */
	public static final String MAX_PROD_MONTH = "maxprodMonth";

	/** Constant Maximum Production Month */
	public static final String BUYER_GRP_CD = "byrGrpCd";

	/** Constant PRMTR_PRDORDRSTGCD. */
	public static final String PRMTR_PRD_ORDR_STGCD = "prdOrdrStgCd";

	/** Constant EXPTR_LCK_FLAG */
	public static final String EXPTR_LCK_FLAG = "expt_lck_flg";

	/** Constant RHQ_LCK_FLAG */
	public static final String RHQ_LCK_FLAG = "rhq_lck_flg";

	/** Constant NSC_LCK_FLAG */
	public static final String NSC_LCK_FLAG = "nsc_lck_flg";

	/** Constant ORDR_TRANS_FLAG */
	public static final String ORDR_TRANS_FLAG = "ordr_trns_flg";

	/** Constant PRODUCTION_STAGE_CD */
	public static final String PRODUCTION_STAGE_CD = "PRODUCTION_STAGE_CD";

	/** Constant Process 1 */
	public static final String P0001 = "P0001";

	/** Constant Process 2 */
	public static final String P0002 = "P0002";

	/** Constant Process 3 */
	public static final String P0003 = "P0003";

	public static final String B000021WRITER = "b000021Writer";

	/** Constant CAR_SERIES_HORIZON */
	public static final String CAR_SERIES_HORIZON = "Car Series horizon";

	/** Constant POR_CAR_SERIES_MST */
	public static final String TBL_NM_POR_CAR_SERIES_MST = " POR_CAR_SERIES_MST";

	/** Constant POR_HORIZON_EXTRACTION */
	public static final String POR_HORIZON_EXTRACTION = " POR_HORIZON_EXTRACTION";

	/** Constant CF_CONSTANT_O. */
	public static final String CONSTANT_O = "O";

	/* Z014433 changes for B000021 ends here */
	public static final String UPDATED = " updated";
	public static final String UPDATION = " updation";
	public static final String INSERTION = " insertion";
	/** Constant LATESET_MASTER_SCHDULE_TRN */
	public static final String LATESET_MASTER_SCHDULE_TRN = "LATESET_MASTER_SCHDULE_TRN";

	/** Constant MONTHLY_ORDER_TRN */
	public static final String MONTHLY_ORDER_TRN = "MONTHLY_ORDER_TRN";
	public static final String ORDERABLE_SALES_END_ITEM_DETAIL_MST = "ORDERABLE_SALES_END_ITEM_DETAIL_MST";
	public static final String END_ITEM = "end item ";
	public static final String UPDATED_END_ITEM = "updated end item ";
	public static final String NSC_FORECAST_DATA = "Nsc forecast data";
	public static final String NSC_FORCAST_VOLUME_TRN = "NSC_FORCAST_VOLUME_TRN";
	public static final String NSC_CONFIRMATION_DATA = "Nsc monthly confirmation data";
	public static final String NSC_CONFIRMATION_MONTHLY_TRN = "NSC_CONFIRMATION_MONTHLY_TRN";
	public static final String NON_SUSPENDED_DATA = "NON SUSPENDED DATA";

	public static final String BATCH_SCRN_FLG = "SCRN_FLG";
	public static final String PRMTR_SUSPND_ORD_FLG = "suspndOrdFlg";
	public static final String PRMTR_PRD_MNTH_FRM = "prdMnthfrm";
	public static final String PRMTR_PRD_MNTH_TO = "prdMnthTo";
	public static final String PRMTR_SEQ_NO = "seqNo";

	public static final String B000018_REPORT_PATH = "B000018.Report.Path";

	public static final String REPORT_HEADER_POR = "POR";
	public static final String REPORT_HEADER_ORDR_TK_BS_PRD = "Order Take Base Period";
	public static final String REPORT_HEADER_PRD_MNTH = "Production Month";
	public static final String REPORT_HEADER_WEEK_NO = "Week No";
	public static final String REPORT_HEADER_CAR_SRS = "Car Series";
	public static final String REPORT_HEADER_BYR_GRP = "Buyer Group";
	public static final String REPORT_HEADER_OCF_FRAME_CD = "OCF Frame Code";
	public static final String REPORT_HEADER_OCF_FEAT_CD = "OCF Feature Code";
	public static final String REPORT_HEADER_FEAT_DESC_SHRT = "OCF Description (Short) ";
	public static final String REPORT_HEADER_FEAT_DESC_LONG = "OCF Description (Long) ";
	public static final String REPORT_HEADER_OCF_LMT = "OCF Limit";
	public static final String REPORT_HEADER_OCF_USAGE = "OCF Usage";
	public static final String REPORT_HEADER_DIFFERENCE = "Difference";
	public static final String REPORT_HEADER_BREACH = "DIFF";
	public static final String REPORT_HEADER_ERROR_MSG = "ERROR MESSAGE";

	public static final String REPORT_HEADER_BYR_CD = "Buyer Code";
	public static final String REPORT_HEADER_SPEC_DEST = "Spec Dest";
	public static final String REPORT_HEADER_APPlD_MDL = "Applied Model";
	public static final String REPORT_HEADER_PCK_CD = "Pack Code";
	public static final String REPORT_HEADER_EXT_CLR_CD = "Ext";
	public static final String REPORT_HEADER_INT_CLR_CD = "Int";
	public static final String REPORT_HEADER_ADD_SPEC_CD = "Additional Spec";
	public static final String REPORT_HEADER_POT = "POT";
	public static final String REPORT_HEADER_QTY = "Qty";
	public static final String REPORT_HEADER_DUE_DATE_FRM = "Due Date From";
	public static final String REPORT_HEADER_DUE_DATE_TO = "Due Date To";
	public static final String REPORT_HEADER_ADOPT_DATE = "EIM AdoptDate";
	public static final String REPORT_HEADER_ABOLISH_DATE = "EIM AbolishDate";
	public static final String REPORT_HEADER_DATA_SKIPPED = "DATA SKIPPED/PROCESSED";

	public static final String REPORT_HEADER_FRZN_TYPE = "Frozen Type";
	public static final String REPORT_HEADER_ORDR_QTY = "Order Qty";
	public static final String REPORT_HEADER_MS_QTY = "M/S Qty";
	public static final String PROCESS_ONLY_FLAG = "prcsOlyFlg";
	public static final String PRMTR_PRD_RGN_CD = "prdRgnCd";
	public static final String DRAFT = "draft";
	public static final String FINAL = "final";
	public static final String AMPERSAND_ONE = "&1";
	public static final String ORDR_QTY = "ORDR_QTY";
	public static final String DRFT_ORDR_QTY = "DRAFT_QTY";

	/* B000059 starts here */

	/** SINGLE_FILE_TYPE CODE */
	public static final String SINGLE_FILE_TYPE = "1";
	/** MULTI_FILE_TYPE CODE */
	public static final String MULTI_FILE_TYPE = "2";
	/** FIFO PROCESSING_ORDER */
	public static final String PROCESSING_ORDER_ONE = "1";
	/** LIFO PROCESSING_ORDER */
	public static final String PROCESSING_ORDER_TWO = "2";
	public static final String MONTHLY_OCF_TRN = "MONTHLY_OCF_TRN";
	public static final String OCF_LIMIT = "OCF_LIMIT";
	public static final String BUYER_GROUP_CD = "BUYER_GROUP_CD";
	public static final String MST_BUYER = "MST_BUYER";
	public static final String BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN = "BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN";
	public static final String SEND_WEEKLY_PRODUCTION_SCHEDULE = "SEND_WEEKLY_PRODUCTION_SCHEDULE";
	public static final String REFERENCE_TIME = "refTme";

	public static final String POR_MST = " POR_MST";
	public static final String POR_HORIZON = " POR Horizon";
	public static final String FORCAST_VOLUME = "frcstVol";
	public static final String NSC_CMPLT_FLAG = "nscCmptFlg";
	public static final String PRMTR_PRODUCTION_FAMILY_CODE = "prodFmyCd";
	public static final String OVERLAP_MS_QTY_FLAG = "overlap_ms_qty_flag";
	public static final String PRMTR_DRAFT_QTY = "draftQty";
	public static final String PRMTR_MS_QTY = "msQty";
	public static final String PRMTR_SIMU_QTY = "simuQty";
	public static final String PRMTR_CRTD_BY = "crtdBy";

	/* B000059 ends here */

	public static final String AVG_PRCSS_FLG = "AM";
	public static final String IDL_PRCSS_FLG = "IM";
	public static final String EQL_PRCSS_FLG = "EQ";
	public static final String PARAM_1 = "param1";
	public static final String PARAM_2 = "param2";
	public static final String AVG_PRCSS_CLR_FLG = "AMC";
	public static final String IDL_PRCSS_CLR_FLG = "IMC";
	public static final String EQL_PRCSS_CLR_FLG = "EQC";
	public static final String DEFAULT_POT_CD = "DEFAULT_POT_CD";
	public static final String SERIAL_NO = "S.NO";
	public static final String REPORT_PRODUCTION_MONTH = "PRODUCTION_MONTH";
	public static final String REPORT_ORDER_STAGE = "ORDER_STAGE";
	public static final String REPORT_AVERAGE_CAL_BY = "AVERAGE_CAL_BY";
	public static final String REPORT_EI_BREAKDOWN_PRIORITY = "EI_BREAKDOWN_PRIORITY";
	public static final String REPORT_COLOR_BREAKDOWN_PRIORITY = "COLOR_BREAKDOWN_PRIORITY";
	public static final String REPORT_ERROR_TYPE = "ERROR_TYPE";
	public static final String REPORT_ERROR_MESSAGE = "ERROR_MESSAGE";
	public static final String REPORT_ERROR_ID = "ERROR_ID";
	public static final String REPORT_TIME = "TIME";
	public static final String CONSTANT_V1 = "v1";
	public static final String CONSTANT_V2 = "v2";
	public static final String CONSTANT_V3 = "v3";
	public static final String CONSTANT_V4 = "v4";
	public static final String CONSTANT_V5 = "v5";
	public static final String CONSTANT_V6 = "v6";
	public static final String CONSTANT_V7 = "v7";
	public static final int PROD_ORDER_STAGE_CD_DRAFT = 10;
	public static final int PROD_ORDER_STAGE_CD_FINAL = 20;
	public static final String BATCH_20_ID = "B000020";
	public static final String BREAKDOWN_PRIORITY_FLAG = "Breakdown_Priority_Flag";
	public static final String IDEAL_MIX_PRIORITY_MST = "Ideal_Mix_Priority_Mst";
	public static final String PRMTR_RGNL_MNTHLY_OCF_USAGE_LMT = "rgnlMnthlyOcfLmtQty";
	/** Constant LNG_DESC. */
	public static final int INT_3 = 3;
	public static final String CONSTANT_4 = "4";
	public static final String CONSTANT_3 = "3";
	public static final String BATCH_INPUT_DETAILS = " Batch Input Details";
	public static final String SCREEN_ALL = "0";
	public static final String NO_DATA_FOUND = "Error";
	public static final String FORECAST_VOLUME = "Forecast Volume";
	public static final String NSC_FORECAST_VOLUME_TRN = "Nsc_Forecast_Volume_Trn";
	public static final String IDEAL_MIX_VOLUME = "Ideal_Mix_Volume";
	public static final String COMBINATION_2 = "'POR_CD','Buyer_Group_Cd','Car_Series'";
	public static final String REGIONAL_MONTHLY_OCF_LIMIT_TRN = "REGIONAL_MONTHLY_OCF_LIMIT_TRN";
	public static final String TOTAL_ORDER_VOLUME = "Total Order Volume";
	public static final String BATCH_SUCCESS_STTS_CD = "S";
	public static final String BATCH_FAILURE_STTS_CD = "F";
	public static final String BATCH_ERROR_Y_STTS_CD = "Y";
	public static final String BATCH_ERROR_N_STTS_CD = "N";
	public static final String REPORT_OCF_REGION = "OCF_REGION";
	public static final String REPORT_BUYER_GROUP = "BUYER_GROUP";
	public static final String REPORT_BUYER_CODE = "BUYER_CODE";
	public static final String REPORT_VOLUME_ALLOCATION = "VOLUME_ALLOCATION";
	public static final String REPORT_ORDER_QTY = "ORDER_QTY";
	public static final String REPORT_DIFFERENCE = "DIFF";
	public static final String REPORT_AUTO_ADJUST = "AUTO_ADJUST";
	public static final String REPORT_ORDER_QUANTITY_TO_PLANT = "ORDER_QUANTITY_TO_PLANT";
	public static final String REPORT_END_ITEM_APP = "END_ITEM(APP)";
	public static final String REPORT_END_ITEM_PACK = "END_ITEM(PACK)";
	public static final String REPORT_SPEC_CODE = "SPEC_CODE";
	public static final String REPORT_EXT_COLOR = "EXT_COLOR";
	public static final String REPORT_INT_COLOR = "INT_COLOR";
	public static final String REPORT_RECORD_TYPE_CD = "RECORD_TYPE_CD";
	public static final String REPORT_EX_NO = "EX_NO";
	public static final String REPORT_OCF_FEATURE_CODE = "OCF_FEATURE_CODE";
	public static final String REPORT_OCF_DESCRIPTION_LONG = "OCF_DESCRIPTION(LONG)";
	public static final String REPORT_USAGE = "USAGE";
	public static final String REPORT_OCF_DESCRIPTION_SHORT = "OCF_DESCRIPTION(SHORT)";
	public static final String PRMTR_FILE_NAME_R5 = "fileNamer5";
	public static final String PRMTR_FILE_NAME_R4 = "fileNamer4";

	public static final String M196 = "M00196";
	public static final String CONSTANT_N_TO_N_PLUS_3 = "N~N=1";

	/* B000066 Starts Here */

	/** Constant BATCH_ID_B000059 */
	public static final String BATCH_ID_B000061 = "B000061";

	/** Constant INTERFACE_FILE_ID. */
	public static final String S_INTERFACE_FILE_ID = "S_INTERFACE_FILE_ID";

	public static final String R_INTERFACE_FILE_ID = "R_INTERFACE_FILE_ID";

	public static final StringBuilder updateCmnHeaderWithSeqNo = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	public static final StringBuilder updateCmnHeaderCtrl = new StringBuilder()
			.append("UPDATE CMN_FILE_HDR SET STTS = :STTS , FILE_NAME = :FILE_NAME, CONTROL_FILE_NAME = :CONTROL_FILE_NAME WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO");

	public static final Long FLTRGRP_1 = 1L;

	public static final Long FLTRGRP_2 = 2L;

	public static final String EQUALSTR = "=";

	public static final String LIKESTR = "LIKE";

	public static final String INSTR = "IN";

	public static final String BETWEENSTR = "BETWEEN";
	
	public static final String CURRENT_YM = "CURRENT YM";

	public static final String FITER_CRITERIA = "FILTER_QUERY";

	public static final String ORDER_BY_ASC = "A";

	public static final String ORDER_BY_DESC = "D";

	public static final String SORT_CRITERIA = "SORT_CRITERIA";

	public static final Object COLNAME = "col";

	public static final String DYNAQUERY = "DYNAMIC_QUERY";

	public static final String FAILURED = "F";

	public static final String CONTROL_FILE_FLAG_YES = "Y";
	public static final String CONTROL_FILE_FLAG_NO = "N";

	/* B000066 Here */
	public static final String UPDATION_FAILED = "Updation Failed";
	public static final String TRN_BUYER_MNTHLY_OCF_USG = "TRN_BUYER_MNTHLY_OCF_USG";
	public static final String TRN_BUYER_GRP_MNTHLY_OCF_LMT = "TRN_BUYER_GRP_MNTHLY_OCF_LMT";
	public static final String ALL = "0";
	public static final String PRMTR_PRODUCTION_MONTH_FROM = "prdMnthFrm";
	public static final String INTERFACE43 = "I000043";
	public static final String PRMTR_PRODUCTION_MONTH_TO = "prdMnthTo";
	public static final String MESSAGE_ORDER_QTY = " Order Quantity Details";
	public static final String MESSAGE_BUYER_GROUP_DETAILS = " Buyer Group details";

	/** Constant PRMTR_TARGET_MNTH. */
	public static final String PRMTR_TARGET_MNTH = "targetMnth";

	public static final String B000067_REPORT_PATH = "B000067.Report.Path";

	public static final String REPORT_HEADER_OCF_REGION = "OCF Region";
	public static final String REPORT_HEADER_MCREGION = "MC Region Code";
	public static final String REPORT_HEADER_CAR_GRP = "Car Group";
	public static final String REPORT_HEADER_PRDN_FMLY_CD = "Production Family";
	public static final String REPORT_HEADER_END_ITEM = "End Item";
	public static final String REPORT_HEADER_SALES_NOTE = "Sales Note No";
	public static final String REPORT_HEADER_EX_NO = "Ex No";
	public static final String REPORT_HEADER_ORDER_TOTAL = "Order Total";
	public static final String REPORT_HEADER_VOLUME = "Volume (Partial)";
	public static final String REPORT_HEADER_FRM_DATE = "From Date";
	public static final String REPORT_HEADER_TO_DATE = "To Date";
	public static final String REPORT_HEADER_WARNING = "WARNING";
	public static final String PRMTR_RATIO = "ratio";
	public static final String PRMTR_ALLOCATION = "alloc";
	public static final String PRMTR_PROCESS_FLAG = "prcsFlag";
	public static final String PRMTR_OCF_USAGE_QTY = "ocfUsgQty";

	public static final String B000065_SEND_SUCCESS_PATH = "CONVERSIONPROCESS_SEND_SUCCESSFOLDER";
	public static final String B000061_TIME_STAMP_FMT = "ConversionProcess_Send_File_Creation_TimeStamp";
	public static final String B000061_FILE_EXT = ".txt";
	public static final String B000061_CTRL_FILE = "_CTRL_FILE_";

	public static final int SFTP_PORT = 22;
	public static final String MESSAGE_BUYER_EI_LEVEL_DETAILS = " Buyer CD EI Level Order Qty details";
	public static final String MESSAGE_BUYER_COLOR_LEVEL_DETAILS = " Colour Code Level Order Qty details";
	public static final String MESSAGE_POT_LEVEL_DETAILS = " POT cd Level Order Qty details";
	public static final String PRMTR_AUTO_ADJUSTED_QTY = "autoAdjstQty";

	public static final String BATCH_ID_14 = "I000014";
	public static final String BATCH_ID_11 = "I000011";
	public static final String BATCH_ID_13 = "I000013";
	public static final String BATCH_ID_02 = "I000002";
	public static final String BATCH_ID_16 = "I000016";
	public static final String BATCH_ID_17 = "I000017";
	public static final String BATCH_ID_23 = "I000023";
	public static final String BATCH_ID_25 = "I000025";
	public static final String IF_ID_62 = "I000062";
	public static final String IF_ID_113 = "I000113";
	public static final String IF_ID_110 = "I000110";
	public static final String IF_ID_55 = "I000055";
	public static final String IF_ID_83 = "I000083";
	public static final String IF_ID_47 = "I000047";
	public static final String IF_ID_B = "B";
	public static final String IF_ID_F = "F";

	public static final String P4_PROCESS_ID = "P0004";

	public static final String REPORT_HEADER_PROD_PRD = "Production Period";
	public static final String REPORT_OCF_RGN_CD = "Ocf Region Code";
	public static final String REPORT_OCF_RGN = "Ocf Region";
	public static final String REPORT_OCF_BYR_GRP = "Ocf Buyer Group";
	public static final String REPORT_OCF_BYR = "Ocf Buyer";
	public static final String REPORT_FEAT_CD = "Feat Code";
	public static final String REPORT_PLANT_CD = "Plant Class";
	public static final String REPORT_LINE_CLASS = "Line Class";
	public static final String REPORT_OCF_FEAT_CD = "OCF Feature Code";
	
	public static final String REPORT_OCF_SHRT_DES = "Ocf Short Description";

	public static final String REPORT_SUFFIX_R37 = "R37_Report_";
	public static final String R000037_REPORT_PATH = "R000037.Report.Path";
	public static final String TBL_CMN_INTRFC_DATA = "Common Interface Data";
	public static final String INTERFACE42 = "I000042";
	public static final String POT_CD = "POT_CD";
	public static final String MST_WK_NO_CLNDR = "MST_WK_NO_CLNDR";

	public static final String BATCH_ID_B000066 = "B000066";
	public static final String SEND_TRANSACTION_TYPE = "S";
	public static final String RECEIVE_TRANSACTION_TYPE = "R";
	public static final String FIRST_WEEK = "1";
	public static final String INTERFACE_42_HRZN = "2";
	public static final String R000020 = "R000020";
	public static final String WEEK_NO_CALEDAR = "MST_WEEK_NO_CALENDAR";

	public static final String RE_RUN_FLAG = "RE_RUN_FLAG";
	public static final String RUN_TIME_ERROR = "Run time Error message";
	public static final String SKIPPED_STATUS = "S";
	public static final String ROLE_ID = "Role Id";
	public static final String USER_ID = "User Id";
	public static final String Interface_103_ID = "I000103";
	public static final String Interface_104_ID = "I000104";
	public static final String Interface_105_ID = "I000105";
	public static final String Interface_106_ID = "I000106";
	public static final String Interface_20_ID = "I000020";
	public static final String Interface_113_ID = "I000113";
	public static final String Interface_102_ID = "I000102";
	public static final String Interface_65_ID = "I000065";
	public static final String Interface_110_ID = "I00110";

	public static final String RECIEVE_MSG_CHAR = "R";
	public static final String SEND_MSG_CHAR = "S";
	public static final String MONTHLY_ORDER_ERROR = "MONTHLY ORDER ERROR INTERFACE TRN";
	public static final String Interface_44_ID = "I000044";
	public static final String Stage_status_CD = "F1,F2";
	public static final String Insert = "Insert";
	public static final String Common_data_layer = "Common Data Layer";
	public static final String MONTHLY_PRODUCTION_SCHEDULE_TRN = "MONTHLY PRODUCTION SCHEDULE TRN";
	public static final String FEATURE_CD_NT_EXISTS = "Feature Code Not Exist in Master for this OCF Combination";
	public static final String OCF_LMT_ELIMINATED = "OCF Limit is eliminated, Because Feature Code Adopt & Abolish Period for this OCF is not valid.";
	/** Constant OEISPECIDCREATIONSUCCESS. */
	public static final String OEISPECIDCREATIONSUCCESS = "OEI_SPEC_ID are created successfully for each End Items";

	/** Constant OSEIIDCREATIONSUCCESS. */
	public static final String OSEIIDCREATIONSUCCESS = "OSEI_ID are created successfully for each End Items";

	/** Constant STTSCDCREATIONSUCCESS. */
	public static final String STTSCDCREATIONSUCCESS = "For new insertion of End Items, EI Status Code is created successfully for each End Items";

	/** Constant EXISTINGCOLORREMOVAL. */
	public static final String EXISTINGCOLORREMOVAL = "Already existing Interior Color Codes are removed sucessfully";

	/** Constant SPLITSUCCESS. */
	public static final String SPLITSUCCESS = "End Item Option Spec Code are splitted successfully into Six Characters";

	/** Constant PARAMETER_MSG. */
	public static final String PARAMETER_MSG = " in Table PARAMETER_MST. So Batch process Stopped";

	/** Constant BATCH4_ID. */
	public static final String BATCH4_ID = "B000004 : ";

	/** Constant BATCH4_ID. */
	public static final String BATCH5_ID = "B000005 : ";

	/** Constant B4_EI_MES. */
	public static final String B4_EI_MES = "There is no End items  for  POR_CD =";

	/** Constant B4_EI_UPDATE_MES. */
	public static final String B4_EI_UPDATE_MES = "There is no Updated End items  for POR_CD =";

	/** Constant B4_BATCH_STOP. */
	public static final String B4_BATCH_STOP = " in ORDERABLE_SALES_END_ITEM_MST -Table. So Batch process Stopped";

	/** Constant B4_MIN_PARAMTER. */
	public static final String B4_MIN_PARAMTER = "There is no MINIMUM_PARAMETER_VALUE for POR_CD = ";

	/** Constant OCF_PARAMTER. */
	public static final String OCF_PARAMTER = "There is no OCF information for POR_CD = ";

	/** Constant B4_ABOLISH_DATE. */
	public static final String B4_ABOLISH_DATE = "There is no FEATURE_ABOLISH_DATE for POR_CD =";

	/** Constant B4_PRD_STAGE_CD. */
	public static final String B4_PRD_STAGE_CD = "There is no PRODUCTION_STAGE_CD for POR_CD = ";

	/** Constant OCF_PARAMTER_TABLE. */
	public static final String OCF_PARAMTER_TABLE = " in Table OCF_CLASSIFICATION_MST . So Batch process Stopped";

	/** Constant FTRE_PARAMTER. */
	public static final String FTRE_PARAMTER = "There is no FEATURE information for POR_CD = ";

	/** Constant FTRE_PARAMTER_TABLE. */
	public static final String FTRE_PARAMTER_TABLE = " in Table FEATURE_MST . So Batch process Stopped";

	/** Constant B4_OTBM. */
	public static final String B4_OTBM = "There is no ORDER_TAKE_ORDER_TAKE_BASE_MONTH for POR_CD = ";
	public static final String M000190_LOCK_INFO = "LOCK INFO";

	/** Constant B4_WEEKLY_MST. */
	public static final String B4_WEEKLY_MST = "in Table  WEEKLY_ORDER_TAKE_BASE_PERIOD_MST. So Batch process Stopped";

	/** Constant B4_MONTHLY_MST. */
	public static final String B4_MONTHLY_MST = " in Table MONTHLY_ORDER_TAKE_BASE_PERIOD_MST. So Batch process Stopped";

	public static final String MESSAGE_UPDATED = "updated";

	public static final String MESSAGE_INSERTED = "inserted";
	public static final String NO_00_OCF_FOUND = "00 Frame code OCF Volume is not available";
	public static final String CM_OFFLINE_WARN1 = " Order Total is not existing, Order has not  been transmitted to Plant System ";
	public static final String CM_OFFLINE_WARN2 = " Volume(Partial) is greater than Order Total, Order Total has been transmited to Plan System ";
	public static final String BATCH_FAIL_MSG = " Batch process Stopped. Please contact Administrator.";
	public static final String B000002_ARG_EXCEPTION_MSG = " Arguement POR_CD is missed. Please pass POR_CD as an argument";
	public static final String READER_INFO = "No. of End Items Fetched = ";
	public static final String NOWKLYORDRTKEBASE = "No Weekly Order Take Base Period present for given POR CD = ";
	public static final String NOWKLYOPENORDRTKEBASE = "No Weekly Order Take Base Period is in open stage. So Fetching Max Order Take Base period in Closed stage. ";
	public static final String REPORT_COLOR_CODE = "COLOR CODE";
	public static final String REPORT_PROD_WEEK_NO = "PRODUCTION WEEK NO";
	public static final String REPORT_QUANTITY = "QUANTITY";
	public static final String R000020_REPORT_PATH = "R000020.Report.Path";
	public static final String REPORT_SUFFIX_R20 = "R20_Report";
	public static final String MULTIPLE_WEEK_FIX_CASE="MULTIPLE_WEEK_FIX_CASE";
	public static final String WEEKLY_ORDER_HORIZON="WEEKLY_ORDER_HORIZON";
	public static final String PRMTR_PRD_STG_CD = "PRODUCTION_STAGE_CD";
	public static final String PRMTR_KEY2_RGLR_PROD_STAGE_CD = "RGLR_PROD_STAGE_CD";
	
	public static final String EXTRACT_PORCD = "I000091, For POR CD -";
	public static final String Interface_91_ID = "I000091";
	public static final String BUYER_MST = "BUYER_MST";
	public static final String OCF_REGION_MST = "OCF_REGION_MST";
	
	public static final String REGIONAL_WEEKLY_OCF_LIMIT_TRN = " REGIONAL_WEEKLY_OCF_LIMIT_TRN";
	public static final String BUYER_GROUP_WEEKLY_OCF_LIMIT_TRN ="BUYER_GROUP_WEEKLY_OCF_LIMIT_TRN";
	public static final String PARAMETER_MST = "PARAMETER MST";
	public static final String WEEKLY_ORDER_TAKE_BASE_PERIOD_MST = "WEEKLY_ORDER_TAKE_BASE_PERIOD_MST";
	public static final String B000043_ID = "B000043";
	public static final String PRMTR_ORDR_TAKE_BASE_WEEK_NO = "ordrTkBsWkNo";
	public static final String BUYER_MST_BUYER_GROUP_CD ="BUYER_MST.BUYER_GROUP_CD";
	
	public static final String PRMTR_CD_MS_SCHEDULE_VALIDATON = "MS_SCHEDULE_VALIDATON";
	public static final String PRMTR_CD_FROZEN_AND_PRODUCTION_TYPE_VALIDATION = "FRZN_AND_PRDN_TY_VLD";
	public static final String PRMTR_EX_NO = "exNo";
	public static final String PRMTR_VAL_1 = "val1";
	public static final String PRMTR_VAL_2 = "val2";
	public static final String PRMTR_MX_INDCTR = "maxIndctr";
	public static final String PRMTR_R20_EX_NO_VALIDATION = "R20_EX_NO_VALIDATION";
	public static final String PRMTR_CD_EX_NO_RANGE = "EX_NO_RANGE";
	public static final String MANUAL = "Manual";
	public static final String MANUAL_MX_INDCTR = "A";
	public static final String AUTO_MX_INDCTR = "M";
	public static final String PRMTR_CD_EX_NO_VALIDATION = "EX_NO_VALIDATION";
	public static final String PRMTR_CD_MST_EX_NO = "EX_NO_MST";
	public static final String PRMTR_CD_PRD_MTD_CD = "prdMthdCd";

	public static final String PROPERTIES_FILE_PATH = "D:LocalData/z015883/appl-pgm/resource/batchinputs.properties";
	public static final String STEP_ID_0 = "step0";
	public static final String PARAMETER_CD_I000004 = "INTERFACE_4_PRIMARY_KEY_LIST";

	public static final String MONTHLY_PRODUCTION_SCHEDULE_TRN_IF = "MONTHLY PRODUCTION SCHEDULE TRN IF";
	public static final String PRMTR_ACCEPTED_ORDER_QTY="accptOrdrqty";
	public static final String PLANT_SUMMARY_CONSTANT="PLANT_SUMMARY";
	public static final String WEEKLY_OCF_SUMMARY_CONSTANT="WEEKLY_OCF_SUMMARY";
	public static final String CONSTANT_DAY_NO_CONSTANT="CONSTANT_DAY_NO";
	public static final String FEATURE_TYPE_CODE_CONSTANT="FEATURE_TYPE_CODE";
	public static final String INTERFACE87 = "I000087";
	public static final String WEEKLY_ORDER_TRN = "WEEKLY_ORDER_TRN";
	
	/** Constant Process 6 */
	public static final String P0006 = "P0006";
	public static final String PRMTR_EX_NO_VALIDATION = "EX_NO_VALIDATION";

	public static final String DAILY_OCF_LIMIT_TRN = "DAILY_OCF_LIMIT_TRN";
	
	public static final String PRMTR_PRPS_CD = "prpsCd";
	
	public static final String LOGICAL_PIPELINE_TRN_TABLE = "LOGICAL_PIPELINE_TRN";
	public static final String R20_WARN = "WARN : ";
	public static final String R20_ERROR = "ERR : ";
	public static final String PRMTR_R20_WRN_CD = "99";
	public static final String PRMTR_R20_WRN_CD_SKIP = "98";
	
	/*
	 * z014135 for B052 
	 */
	public static final String REPORT_HEADER_PRD_MNTH_B52 = "Prod.Month";
	public static final String REPORT_OCF_RGN_B52 = "OCF Region";
	public static final String REPORT_OCF_BYR_GRP_B52 = "OCF BUYER GROUP";
	public static final String REPORT_PLANT_CD_B52 = "Plant cd";
	public static final String PLANT_LINE_CLASS_CONSTANT="PLANT_LINE_CLASS";

	public static final String CONVERSION_LAYER_FIX_LENGTH_2 = "2";

	public static final String CONVERSION_LAYER_FIX_LENGTH_1 = "1";

	public static final String CONVERSION_LAYER_SKIPLEVEL_YES = "Y";

	public static final String CONVERSION_LAYER_SKIPLEVEL_NO = "N";

	public static final String CONVERSION_LAYER_FIX_LENGTH_0 = "0";

	public static final String CONVERSION_LAYER_MULTIPLE_FILES_1 = "1";

	public static final String CONVERSION_LAYER_MULTIPLE_FILES_2 = "2";

	public static final String CONVERSION_LAYER_PROCESS_ORDER_1 = "1";

	public static final String CONVERSION_LAYER_PROCESS_ORDER_2 = "2";

	public static final String CONVERSION_LAYER_MULTIPLE_FILES_0 = "0";
	
	public static final String IDL_MX = "Ideal Mix";
	
	public static final String N_TO_N_PLUS_3 = "N ~ N+3";
	public static final String ERR_M00236 = "M00236";
	
	/** Constant BATCH_20_CONFIG_PATH. */
	public static final String BATCH_20_BATCH_CONFIG_PATH = "B000020/B000020_Batch_Config.xml";
	public static final String BATCH_20_SCREEN_CONFIG_PATH = "B000020/B000020_Screen_Batch_Config.xml";
	public static final String TRN_BUYER_WKLY_OCF_USG = "TRN_BUYER_WKLY_OCF_USG";
	public static final String TRN_BUYER_GRP_WKLY_OCF_LMT = "TRN_BUYER_GRP_WKLY_OCF_LMT";
	public static final String ORDERS = " orders";
	public static final String PRMTR_PRODUCTION_DAY_NO="prdDayNo";
	public static final String PRMTR_LINE_CLASS="lneClass";
	
	public static final String PROD_ORDR_NO_FLG = "PRODUCTION ORDER NUMBER FLAG";
	public static final String OCF_UNLMTD_OCF_CHK_FLG = "OCF UNLIMITED OCF CHECK";
	public static final String CONSTANT_R = "R";
	public static final String B000043_BATCH_CONFIG="B000043/B000043_Batch_Config.xml";
	
	public static final String VALUE_NOT_SET_STRING = "-1";
	public static final String B52ZERO = "0";
	public static final String B52DBLZERO = "00";
	public static final String B52BLANK = " ";
	public static final String B52ONE = "1";
	public static final String BATCHCONFIG1 = "B000052/B000052_Mnthly_Batch_Config.xml";
	public static final String BATCHCONFIG2 = "B000052/B000052_Wkly_Batch_Config.xml";
	public static final String B52WEEK ="W";
	public static final String B52MONTH ="M";
	public static final String B52YES="Y";
	public static final String B52NO="N";
	public static final String B000052="B000052";
	public static final String B52HASH ="#";
	
	
	public static final String GSIS_REG_GRND =  "gsisRegGrnd";
	
	public static final String PROD_STAG_CD = "prodStageCd";
	
	/** Constant BATCH_54_CONFIG_PATH. */
	public static final String BATCH_54_BATCH_CONFIG_PATH = "B000054/B000054_Batch_Config.xml";
	public static final String BATCH_54_ID = "B000054";
	
	/** Constant DAY_START_INDEX. */
	public static final int DAY_START_INDEX = 6;

	/** Constant DAY_END_INDEX. */
	public static final int DAY_END_INDEX = 8;

	/** Constant DATE_FORMAT. */
	public static final String DATE_FORMAT_YYYY_MM = "YYYY-MM";
	/** Constant DATE_FORMAT. */
	public static final String DATE_FORMAT_YYYY_MM_DD = "YYYY-MM-DD";
	
	public static final String ORDER_TAKE_BASE_WEEK = "ORDER_TAKE_BASE_WEEK";
	
	public static final String BATCH_SEQ_ID = "seqId";

	
	public static final String FULL_FRZN_TYPE = "F";
	public static final String PARTIAL_FRZN_TYPE = "P";
	public static final String EXT_CLR_FRZN_TYPE = "E";
	public static final String SPEC_DEST_FRZN_TYPE = "D";
	public static final String INT_CLR_FRZN_TYPE = "U";
	public static final String FALSE = "false";
	public static final String TRUE = "true";	
	
	
	public static final String BATCH_54_CUSTOM_QRY = "BATCH_54_CUSTOM_QRY";
	
	/** Constant BATCH_POR_CODE. */
	public static final String BATCH_CAR_SRS = "CAR_SRS";
	
	public static final String BATCH_BYR_GRP_CD = "BUYER_GRP_CD";
	/**INTERFACE CONFIG PATHS  */
	public static final String INTERFACE_I02_CONFIG_PATH = "I000002/I000002_IF_Config.xml";
	public static final String INTERFACE_I11_CONFIG_PATH = "I000011/I000011_IF_Config.xml";
	public static final String INTERFACE_I14_CONFIG_PATH = "I000014/I000014_IF_Config.xml";
	public static final String INTERFACE_I17_CONFIG_PATH = "I000017/I000017_IF_Config.xml";
	public static final String INTERFACE_I20_CONFIG_PATH = "I000020/I000020_IF_Config.xml";
	public static final String INTERFACE_I23_CONFIG_PATH = "I000023/I000023_IF_Config.xml"; 
	public static final String INTERFACE_I26_CONFIG_PATH = "I000026/I000026_IF_Config.xml";
	public static final String INTERFACE_I27_CONFIG_PATH = "I000027/I000027_IF_Config.xml";
	public static final String INTERFACE_I33_CONFIG_PATH = "I000033/I000033_IF_Config.xml";
	public static final String INTERFACE_I39_CONFIG_PATH = "I000036/I000036_IF_Config.xml";
	public static final String INTERFACE_I40_CONFIG_PATH = "I000040/I000040_IF_Config.xml";
	public static final String INTERFACE_I42_CONFIG_PATH = "I000042/I000042_IF_Config.xml";
	public static final String INTERFACE_I43_CONFIG_PATH = "I000043/I000043_IF_Config.xml";
	public static final String INTERFACE_I44_CONFIG_PATH = "I000044/I000044_IF_Config.xml";
	public static final String INTERFACE_I47_CONFIG_PATH = "I000047/I000047_IF_Config.xml";
	public static final String INTERFACE_I55_CONFIG_PATH = "I000055/I000055_IF_Config.xml";
	public static final String INTERFACE_I62_CONFIG_PATH = "I000062/I000062_IF_Config.xml";
	public static final String INTERFACE_I65_CONFIG_PATH = "I000065/I000065_IF_Config.xml";
	public static final String INTERFACE_I83_CONFIG_PATH = "I000083/I000083_IF_Config.xml";
	public static final String INTERFACE_I87_CONFIG_PATH = "I000087/I000087_IF_Config.xml";
	public static final String INTERFACE_I88_CONFIG_PATH = "I000088/I000088_IF_Config.xml";
	public static final String INTERFACE_I91_CONFIG_PATH = "I000091/I000091_IF_Config.xml";
	public static final String INTERFACE_I101_CONFIG_PATH = "I000101/I000101_IF_Config.xml";
	public static final String INTERFACE_I102_CONFIG_PATH = "I000102/I000102_IF_Config.xml";
	public static final String INTERFACE_I103_CONFIG_PATH = "I000103/I000103_IF_Config.xml";
	public static final String INTERFACE_I104_CONFIG_PATH = "I000104/I000104_IF_Config.xml";
	public static final String INTERFACE_I105_CONFIG_PATH = "I000105/I000105_IF_Config.xml";
	public static final String INTERFACE_I106_CONFIG_PATH = "I000106/I000106_IF_Config.xml";
	public static final String INTERFACE_I110_CONFIG_PATH = "I000110/I000110_IF_Config.xml";
	public static final String INTERFACE_I113_CONFIG_PATH = "I000113/I000113_IF_Config.xml";
	
}
