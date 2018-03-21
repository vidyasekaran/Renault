/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */  
package com.nissangroups.pd.b000052.util;

/**
 * The Class B000051Constants.
 *
 * @author z002548
 */
public class B000052Constants {

	
	public static final String BATCH_ID_WRGPRCSTYP = "Process type length must be 1 ";
	
	public static final String BATCH_ID_B000052 = "B000052";
	
	public static final String BATCH_ID_B000052_PROCESSOR_1 = "B000052P1";
	
	public static final String BATCH_ID_B000052_PROCESSOR_2 = "B000052P2";
	public static final String BREACH_REPORT_PATH = "B000052.Report.Path";
	public static final String REPORT_SUFFIX_BREACH_MONTHLY = "Monthly_Prdocution_Schedule(MsNo.2)_Breach_Report_";
	public static final String REPORT_SUFFIX_BREACH_WEEKLY = "Weekly_Prdocution_Schedule(MsNo.3)_Breach_Report_";

	public static final String ERROR_MESSAGE_ID1 = " Two Arguments Expected. [POR_CD] , [PROCESS_TYPE]";
	
	public static final String M00213 = "M00213 : &1:There is no &2 OrderTake Base Month is in closed Status in the table : &3 for given POR";
	public static final String M00315 = "M00315 : &1:There is no &2 for POR_CD =  &3 IN &4-Table";
	public static final String M00158= "M00158 : &1:There is no &2 for POR_CD =  &3 IN &4-Table, So Batch process Stopped";
	public static final String MNTHLY_TABLE ="MONTHLY_ORDER_TAKE_BASE_PERIOD";
	public static final String WKLY_TABLE ="WEEKLY_ORDER_TAKE_BASE_PERIOD";
	
	public static final String PLTNLINESUMMARY = "PLANT LINE SUMMARY";
	public static final String PLNTCDCLASS = "PLANT CD and LINE CLASS";
	public static final String CONSTANTDAY = "CONSTANT_DAY_NO";
	public static final String WKLYFEATMSG = "Weekly Feature Information";
	public static final String BYRGRPLVLUSAGE = "Buyer Group Level Usage";
	public static final String RGNLGRPLVLUSAGE = "Regional Level Usage";
	
	public static final String PARAMTRTABLE = "PARAMETER_MST"; 
	public static final String LATESTMASTER = "in LATEST MASTER SCHEDULE";
	public static final String BYRWKLYOCFUSAGE = "BUYER_WEEKLY_OCF_USAGE_TRN";
	public static final String RNGLVLOCFUSAGE = "REGIONAL_WEEKLY_OCF_LIMIT_TRN";
	public static final String BYRWKLYOCFLMT="TRN_BUYER_GRP_WKLY_OCF_LMT";
	
	public static final String OCFALLOCFLAGY = "0";
	public static final String OCFALLOCFLAGN = "1";
	
	public static final String BREACHINFO = "Breach Information";
	public static final String REPORTTABLE ="REGIONAL_WEEKLY_OCF_LIMT_TRN";
	
	public static final String UPDATE ="Update";
	
	public static final String PORCD ="PORCD";
	
	public static final String TYPE ="TYPE";
	
	
	public static final String porCd ="porCd";
	public static final String prodMnth ="prodMnth";
	public static final String prodWkNo = "prodWkNo";
	public static final String oseiID = "oseiID";
	public static final String featCd = "featCd";
	public static final String ocfFrmCd = "ocfFrmCd";
	public static final String carSrs = "carSrs";
	public static final String byrGrpCd ="byrGrpCd";
	public static final String ocfRgnCd = "ocfRgnCd";
	public static final String ocfByrGrpCd ="ocfByrGrpCd";
	
	public static final String dummyBreach = "DB";
	public static final String errorMsg1 = "Breach-Usage is greater than Limit";
	public static final String errorMsg2 = "00 FRAME USAGE IS NOT EQUAL TO LIMIT";
	public static final String errorMsg3 = "00 FRAME LIMIT equal to zero";
	
	private B000052Constants() {
	}
	
}
