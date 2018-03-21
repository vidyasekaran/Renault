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
 * Class B000047Constants to keep the Constants related to the Batch B000047
 *
 * @author z016127
 *
 */
public final class B000047Constants {
	
	/** Constant BATCH_47_ID. */
	public static final String BATCH_47_ID = "b000047";
	
	/** Constant BATCH_47_ID_MSG. */
	public static final String BATCH_47_ID_MSG = "B000047";

	/** Constant BATCH_47_CONFIG_PATH. */
	public static final String BATCH_47_CONFIG_PATH = "B000047/B000047_Batch_Config.xml";
	
	/** Constant BATCH_ID_B000047. */
	public static final String BATCH_ID_B000047 = "B000047";
	
	/** Constant argument length. */
	public static final int ARGSLENGTH = 1;
	
	/** Constant Allocation Options */
	public static final String ALLOCATION_OPTIONS = "ALLOCATION OPTIONS";
	
	/** Constant Error Case Options */
	public static final String ERROR_CASE_OPTIONS = "ERROR CASE OPTIONS";
	
	/** Constant delimiter */
	public static final String DELIMTR = ",";
	
	/** Constant dynaQuery*/
	public static final String CONSTANT_DYNAQRY = "dynaQuery";
	
	/** Constant b000047 Logical Pipeline Processor*/
	public static final String B000047_LGCL_PIPLNE_PROCESSOR = "B000047LgclPipLnProcessor";
	
	/** Constant b000047 Remaining orders list Processor*/
	public static final String B000047_RMNG_ORDRSLST_PROCESSOR = "B000047RmngOrdrsLstProcessor";
	
	/** Constant b000047 Vins list Processor*/
	public static final String B000047_VINSLST_PROCESSOR = "B000047VinsLstReportReader";
	
	/** Constant Yes*/
	public static final String YES = "Yes";
	
	/** Constant warning code 30 2 */
	public static final String wrngCd30_2 = "30-2 - VIN Allocated with Warning";
	
	/** Constant warning message 30 2 */
	public static final String wrngMsg30_2 = "Order is allocated to Different Production Week VIN";
	
	/** Constant warning code 30 3 */
	public static final String wrngCd30_3 = "30-3 - VIN Allocated with Warning";
	
	/** Constant warning message 30 3 */
	public static final String wrngMsg30_3 = "Order is allocated to Different Production Plant VIN";
	
	/** Constant warning code 30 4 */
	public static final String wrngCd30_4 = "30-4 - VIN Allocated with Warning";
	
	/** Constant warning message 30 4 */
	public static final String wrngMsg30_4 = "Order is allocated to Different Production Plant or Different Producton Week VIN";
	
	/** Constant two*/
	public static final String TWO ="2";
	
	/** Constant three */
	public static final String THREE = "3";
	
	/** Constant physclPplnQuery*/
	public static final String PHYSCL_PPLN_DYNAQRY = "physclPplnQuery";
	
	/** Constant rmngOrdrsQuery*/
	public static final String RMNG_ORDRS_DYNAQRY = "rmngOrdrsQuery";
	
	/** Constant vin number */
	public static final String VINNO ="vinNo";
	
	/** Constant vehicle sequence id */
	public static final String VCHLSEQID = "vhclSeqId";
	
	/** Constant warning code 10*/
	public static final String wrngCd10 = "10 - Remaining VIN";
	
	/** Constant warning message 10 */
	public static final String wrngMsg10 = "VIN No is not allocated to Logical Pipeline";
	
	/** Constant month */
	public static final String MONTH ="Month";
	
	/** Constant day */
	public static final String DAY = "Day";
	
	/** Constant week */
	public static final String WEEK ="Week";
	
	/** Constant no */
	public static final String No ="No";
	
	/** Constant null */
	public static final String NULL = "";
	
	/** Constant operator OR */
	public static final String OR = "||";
	
	/** Constant operator less than*/
	public static final String LESS = "<";
	
	/** Constant warning code */
	public static final String warngCd = "20 - Remaning Order";
	
	/** Constant warning message */
	public static final String warngMsg = "Order is not allocated to VIN. (VIN's Latest offline Date:";
	
	/** Constant warning message */
	public static final String warngMsg2 = ", Latest Offline Period:";
	
	/** Constant error warning message */
	public static final String ERROR_WARNING_MSG = "ERROR / WARNING TYPE";
	
	/** Constant ocf region cd */
	public static final String OCF_REGION_CD = "OCF REGION CD";
	
	/** Constant production month */
	public static final String PRODUCTION_MONTH = "PRODUCTION MONTH";
	
	/** Constant buyer code*/
	public static final String BUYER_CD = "BUYER CD";
	
	/** Constant order production week no */
	public static final String ORDER_PROD_WEEK_NO = "ORDER PRODUCTION WEEK NO";
	
	/** Constant vin production week no */
	public static final String VIN_PROD_WEEK_NO = "VIN PRODUCTION WEEK NO";
	
	/** Constant production order number */
	public static final String PROD_ORDR_NO = "PRODUCTION ORDER NUMBER#";
	
	/** Constant vin number */
	public static final String VIN_NO = "VIN NO";
	
	/** Constant sales note number */
	public static final String SALES_NOTE_NO = "SALES NOTE NO";
	
	/** Constant ex number */
	public static final String EX_NO = "EX-NO";
	
	/** Constant order offline date */
	public static final String ORDR_OFFLINE_DATE = "ORDER OFFLINE DATE(YYYY-MM-DD)";
	
	/** Constant vin offline date */
	public static final String VIN_OFFLINE_DATE = "VIN OFFLINE DATE(YYYY-MM-DD)";
	
	/** Constant order plant code */
	public static final String ORDER_PLANT_CD = "ORDER PLANT CD";
	
	/** Constant vin plant code */
	public static final String VIN_PLANT_CD = "VIN PLANT CD";
	
	/** Constant warning message */
	public static final String WARNING_MSG = "WARNING MSG";
	
	/** Constant b000047 report path */
	public static final String B000047_REPORT_PATH = "B000047.Report.Path";
	
	/** Constant b000047 report suffix */
	public static final String VIN_PPLN_DIFF_REPORT_SUFFIX = "VIN - Pipeline Difference Report";
	
	/** Constant b000047 report suffix */
	public static final String UNMATHD_VIN_REPORT_SUFFIX = "Unmatched VIN Report";
	
	/** Constant b000047 report suffix */
	public static final String VIN_ERROR_REPORT_SUFFIX = "VIN Error Report (Unmatched with Pipeline)";
	
	/** Constant b000047 report file name */
	public static final String VIN_PPLN_DFF_ERR_FLENME = "vinPplnDffErrFleNme";
	
	/** Constant b000047 report file name */
	public static final String UNMTCHD_VIN_ERR_FLENME = "UnmtchdVinErrFleNme";
	
	/** Constant b000047 report file name  */
	public static final String VIN_ERROR_FILENAME = "vinErrFileName";
	
	/** Constant error message */
	public static final String ERR_MSG = "No Case Matches";
	
	/** Constant error message */
	public static final String M00325 = "&1: No &3 pipline records exists to allocate to VIN :&2 ";
	
	/** Constant error message */
	public static final String LOGICAL = "Logical";
	
	/** Constant error message */
	public static final String PHYSICAL = "Physical";
	
	/** Constant error message */
	public static final String M00003 = "&1 No &2 Parameter value found for the given POR_CD &3 in &4 Table";
	
	/** Constant error message */
	public static final String VIN_ALLCTN_LGCL_PPLN = "VIN ALLOCATION TO LOGICAL PIPELINE ";
	
	/** Constant parameter mst */
	public static final String PARAMETER_MST = "PARAMETER_MST";
	
	/** Constant error message */
	public static final String M00164 = "&1: &2  Failed in &3";
	
	/** Constant update */
	public static final String UPDATE = "UPDATE";
	
	/** Constant logical pipeline transaction */
	public static final String LOGICAL_PIPELINE_TRN = "LOGICAL_PIPELINE_TRN";
	
	/** Constant physical pipeline transaction */
	public static final String PHYSICAL_PIPELINE_TRN = "PHYSICAL_PIPELINE_TRN";
	
	/** Constant error message */
	public static final String M00004 = "&1 No Data Found for the given POR_CD &2 in &3 Table";
	
	/** Constant updated */
	public static final String UPDATED ="UPDATED";
	
	/** Constant vin allocation offline extraction */
	public static final String VIN_ALLCTN_OFFLN_EXTRCTN= "VIN ALLOCATION OFFLINE EXTRACTION";
	
	/** Constant week no calendar */
	public static final String WEEK_NO_CALENDAR = "WEEK_NO_CALENDAR";
 	
 	
	/**
	 * Instantiates a new B000047 query constants.
	 */
	private B000047Constants() {
	    
	}

}
