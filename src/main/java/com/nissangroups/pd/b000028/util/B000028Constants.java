/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.util;

/**
 * The Class B000008Constants.
 *
 * @author z002548
 */
public class B000028Constants {

	
	 /** Constant BATCH_ID_B000008 */
	public static final String BATCH_ID_B000028 = "PST-DRG-B000028. Automatic_order_Adjustment_to_OCF_Limit. ";
	
	/** Constant BATCH_B000028 */
	public static final String BATCH_B000028 = "B000028";
	
	 /** Constant BATCH_ID_B000008_PROCESSOR_1 */
	public static final String BATCH_ID_B000028_PROCESSOR_1 = "B000028P1";
	
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
	public static final String INPUT_PARAM_FAILURE_1="Two Arguments Expected : [POR_CD] , [ORDER_STAGE_CODE]";

	public static final String MESSAGE_REPORT_10 = " NO ORDERABLE SALES END ITEM FOUND, AUTO ADJUSTMENT FAILED.";
	public static final String MESSAGE_REPORT_20 ="ALL ORDERABLE SALES END ITEM FROZEN, AUTO ADJUSTMENT FAILED.";
	public static final String MESSAGE_REPORT_30 ="FROZEN ORDER - CANNOT BE ADJUSTED";

	public static final String RECORD_TYPE_10 = " 10 - BUYER GROUP LEVEL";

	public static final String RECORD_TYPE_20 = " 20 - BUYER LEVEL";
	public static final String RECORD_TYPE_30 = " 30 - SPEC LEVEL";

	
	
	private B000028Constants() {
	}
	
}
