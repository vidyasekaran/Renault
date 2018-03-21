/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.util;

/**
 * The Class B000011Constants.
 *
 * @author z002548
 */
public class B000011Constants {

	
	 /** Constant BATCH_ID_B000011 */
	public static final String BATCH_ID_B000011 = "B000011";
	
	 /** Constant BATCH_ID_B000011_PROCESSOR_1 */
	public static final String BATCH_ID_B000011_PROCESSOR_1 = "B000011P1";
	
	 /** Constant BATCH_ID_B000011_PROCESSOR_2 */
	public static final String BATCH_ID_B000011_PROCESSOR_2 = "B000011P2";
	
	
	 /** Constant STAGE_CODE */
	public static final String STAGE_CODE = "stageCode";
	
	
	
	/** Constants INPUT_PARAM_FAILURE_1*/
	public static final String INPUT_PARAM_FAILURE_1="Two Arguments required. [POR_CODE] and [PROCESS_ONLY_FLAG] ";
		
	/** Constants INPUT_PARAM_FAILURE_2*/
	public static final String INPUT_PARAM_FAILURE_2="PROCESS_ONLY_FLAG should be Y or N";
	
	/** Constants STAGE_MESSAGE_1 */
	public static final String STAGE_MESSAGE_1=" != 'SC' ";
	
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
	
	public static final String PROCESS_FLAG_QUERY = " and PROCESS_STTS_CD='0'  ";
	
	public static final String B000011_BATCH_CONFIG= "B000011/B000011_Batch_Config.xml";
	
	
	public static final String IN_ORDER_TAKE_BASE_MONTH= " in ( :ORDER_TAKE_BASE_MONTH)";

	public static final String GR_EQ_ORDER_TAKE_BASE_MONTH= " >= :ORDER_TAKE_BASE_MONTH ";
	
	
	
	
	private B000011Constants() {
	}
	
}
