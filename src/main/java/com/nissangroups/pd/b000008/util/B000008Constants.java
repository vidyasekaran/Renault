/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.util;

/**
 * The Class B000008Constants.
 *
 * @author z002548
 */
public class B000008Constants {

	
	 /** Constant BATCH_ID_B000008 */
	public static final String BATCH_ID_B000008 = "B000008";
	
	 /** Constant BATCH_ID_B000008_PROCESSOR_1 */
	public static final String BATCH_ID_B000008_PROCESSOR_1 = "B000008P1";
	
	 /** Constant BATCH_ID_B000008_PROCESSOR_2 */
	public static final String BATCH_ID_B000008_PROCESSOR_2 = "B000008P2";
	
	 /** Constant STAGE_CODE */
	public static final String STAGE_CODE = "stageCode";
	
	 /** Constant PRMTR_POT_CD */
	public static final String PRMTR_POT_CD = "POT_CD";
	
	 /** Constant AUTO_ADJUST_ORDER_QTY */
	public static final String AUTO_ADJUST_ORDER_QTY = "autoAdjustOrdrQty";
	
	 /** Constant SUSPENDED_ORDER_FLAG */
	public static final String SUSPENDED_ORDER_FLAG = "suspendedOrdrFlag";
	
	
	 /** Constant PRMTR_PRODUCTION_STAGE_CD */
	public static final String PRMTR_PRODUCTION_STAGE_CD = "PRODUCTION_STAGE_CD";
	
	 /** Constant MAXIMUM_PRODUCTION_MONTH */
	public static final String MAXIMUM_PRODUCTION_MONTH = "MaxProdMnth";
	
	 /** Constant BUYER_GROUP_CD */
	public static final String BUYER_GROUP_CD = "buyerGrpCd";
	
	 /** Constant CAR_SERIES_HORIZON */
	public static final String CAR_SERIES_HORIZON = "Car Series horizon";
		
	/** Constants INPUT_PARAM_FAILURE_1*/
	public static final String INPUT_PARAM_FAILURE_1="PRODUCTION_STAGE_CODE cannot be null to the UPDATE_ONLY_FLAG 'N'";
		
	/** Constants INPUT_PARAM_FAILURE_2*/
	public static final String INPUT_PARAM_FAILURE_2="PRODUCTION_STAGE_CODE should be 10 or 20";
	
	/** Constants INPUT_PARAM_FAILURE_3*/
	public static final String INPUT_PARAM_FAILURE_3="OVERLAP_MS_QTY_FLAG should be Y or N";
	
	/** Constants INPUT_PARAM_FAILURE_4*/
	public static final String INPUT_PARAM_FAILURE_4="UPDATE_ONLY_FLAG should be Y or N";
	
	/** Constants INPUT_PARAM_FAILURE_5*/
	public static final String INPUT_PARAM_FAILURE_5="Four Arguments expected, [POR_CD] , [UPDATE_ONLY_FLAG], [OVERLAP_MS_QTY_FLAG], [PRODUCTION_STAGE_CODE]";
	
	/** Constants STAGE_MESSAGE_1*/
	public static final String STAGE_MESSAGE_1=" in ('D1','D2')";
	
	/** Constants STAGE_MESSAGE_2*/
	public static final String STAGE_MESSAGE_2=" in ('F1','F2')";
	
	/** Constants STAGE_MESSAGE_3 */
	public static final String STAGE_MESSAGE_3=" != 'SC' ";
	
	/** Constants CONSTANT_V1 */
	public static final String CONSTANT_V1= "v1";
	
	/** Constants CONSTANT_V2 */
	public static final String CONSTANT_V2= "v2";
	
	/** Constants CONSTANT_V3 */
	public static final String CONSTANT_V3= "v3";
	
	/** Constants CONSTANT_V4 */
	public static final String CONSTANT_V4= "v4";
	
	/** Constants CONSTANT_V5 */
	public static final String CONSTANT_V5= "v5";
	
	/** Constants CONSTANT_V6 */
	public static final String CONSTANT_V6= "v6";
	
	/** Constants CONSTANT_V7 */
	public static final String CONSTANT_V7= "v7";
	
	/** Constants PROCESS_P6 */
	public static final String PROCESS_P6= "P006";
	
	/** Constants PROCESS_P6 */
	public static final String PROCESS_P6_4= "P006.4";
	
	/** Constants PROCESS_P3 */
	public static final String PROCESS_P3= "P0003.2";
	
	/** Constants PROCESS_P10 */
	public static final String PROCESS_P10= "P0010";

	/** Constants PROCESS14 */
	public static final String PROCESS14 = "P0014";

	/** Constants INPUT_PARAM_FAILURE_6 */
	public static final String INPUT_PARAM_FAILURE_6 = "No Car Series Horizon available. Batch Stopped. ";
	
	
	
	
	private B000008Constants() {
	}
	
}
