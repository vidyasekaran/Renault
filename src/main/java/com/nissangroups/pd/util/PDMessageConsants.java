/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :All
 * Module          :All
 * Process Outline :All
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015 	  z013576               New Creation
 *
 */
package com.nissangroups.pd.util;

/**
 * Having all the Message Details used in PD Batches & Interfaces.
 *
 * @author z013576
 * @version V1.0
 */
public class PDMessageConsants {

	public static final String M00160 = "M00160 : &1: There is no &2 for POR_CD =&3 in &4-Table.So Batch Stopped ";

	/** Constant M00003. */
	public static final String M00003 = "M00003 : NO DATA FOUND";

	/** Constant M00076. */
	public static final String M00076 = "M00076 : &1 Batch Process Failed.Please contact the Administrator";

	/** Constant M00163. */
	public static final String M00163 = "M00163 : &1 Records are Successfully &2 in &3 table";

	/** Constant M00113. */
	public static final String M00113 = "M00113 : Successfully Completed";

	/** Constant M00043. */
	public static final String M00043 = "M00043 : Insertion Failed";

	/** Constant M00164. */
	public static final String M00164 = "M00164 : &1 Records are Failed &2 in &3 table ";

	/** Constant M00135. */
	public static final String M00135 = "M00135 : &1Local path is not exists &2  ";

	/** Constant M00107 */
	public static final String M00107 = "M00107 : &1 cannot be null";

	/** Constant M00179 */
	public static final String M00179 = "M00179 : &1:There is no matched records for  POR &2 in &3.";

	/** Constant M00159 */
	public static final String M00159 = "M00159 : &1: There is no Order Take Base Period in &2  Stage  for  POR =&3  in &4 -Table. "
			+ " So Batch  Stopped";

	public static final String M00158 = "M00158 : &1 : For this  POR &2 and  Latest Order Take Base Month "
			+ " &3 ,  Stage Code does not belongs to 'SC'  in WEEKLY_ORDER_TAKE_BASE_PERIOD_MST. "
			+ "So batch process stopped ";

	public static final String M00221 = "M00221 : &1: Same Production stage will not be available for different  open stage order take base month for "
			+ " any POR_CD. Same PRODUCTION_STAGE_CD=&2 is present for POR_CD=&3 and Order Take Base Month =&4 and &5 in &6. "
			+ " Check the master data .Batch will be stopped.";

	public static final String M00161 = " M00161 :  &1: There is no &2   for  POR_CD =&3  and CAR_SERIES = &4 in &5-Table.  So proceed with  the process - &6";

	

	/** Constant M00190. */
	public static final String M00190 = "M00190 : &1: There is no &2 available in &3-Table for the &4";

	/** Constant M00201. */
	public static final String M00201 = "M00201 : &1:Do not give input stage code,Other than (D1,D2,F1,F2).";

	public static final String M00162 = " M00162 : &1: There is no &2   for  POR_CD =&3  and Production Order Stage Code  :&4  in &5-Table.  So proceed with  the process - &6";

	public static final String M00169 = "M00169 : &1: There is no &2 available   in &3-Table. So Batch  Stopped";

	public static final String M00170 = "M00170 : &1: There is no &2   for  POR_CD =&3  and BUYER_GROUP_CD :&4  in &5-Table.  So proceed with  the process - &6";

	public static final String M00171 = "M00171 : &1: There is no &2 available   in &3-Table. So Proceed with the process  &4";

	public static final String M00228 = "M00228 : &1: There is no &2 available   in &3-Table. So Proceed with the process  &4 .All orders are suspended for the given POR_CD=&5 and"
			+ " Order take abse month =&6.Please contact the administrator.";

	/* Master Validation Error Message */
	
	public static final String M00056 = " M00056 : POR is not exist in POST Dragon System. ";
	public static final String M00057 = " M00057 : Ordertake period does not match with Post DRAGON.";
	public static final String M00058 = " M00058 : This record has been skipped because there are duplicate records in a file. ";
	public static final String M00059 = " M00059: Order has been eliminated because &1 does not exist in DRAGON. ";
	public static final String M00060 = " M00060: Due Date From or To Not Spedified ";
	public static final String M00061 = " M00061: Due Date From To is not matched with production month calendar";
	public static final String M00062 = " M00062: Due Date From is Greater than Due Date To ";
	public static final String M00063 = " M00063: Order received is exceeding OCF Limit.";
	public static final String M00064 = " M00064: Order received does not match Car Series total volume. ";
	public static final String M00065 = " M00065: Full Frozen Total and vehicle spec Can not be changed. ";
	public static final String M00066 = " M00066: Destination Frozen Total Volume Can not be changed. ";
	public static final String M00067 = " M00067: &1 were fixed.";
	public static final String M00139 = " M00139: Order has been eliminated because the Production Period is not a valid production month. ";
	public static final String M00068 = " M00068: Order has been eliminated because Adapt/Abolish date for this Orderable EIM & Color is not valid.";
	public static final String M00069 = " M00069: Ordertake Base Type is not equal to M. ";
	public static final String M00174 = " M00174: There is no features available in Dragon for the UK_OSEI_ID &1 .";
	public static final String M00193 = " M00193: POT is not exist in the Master table for the given POR .";
	public static final String M00206 = " M00206: System is in Lock Status";
	public static final String M00207 = " M00207: Due Date From Should not be greater than due date to";
	public static final String M00208 = " M00208: Production Period Type is not equal to M ";
	public static final String M00209 = " M00209: Production month is not between due date from and due date to.";
	public static final String M00210 = " M00210: Different due date from/due date to is available for the combination POR CD,ORDER TAKE BASE MONTH,PRODUCTION MONTH,UK OEI BUYER ID,POT CD. ";
	public static final String M00211 = " M00211: Due Date From and Due date to is null for the end item.";
	public static final String M00229 = " M00229: There is no Order Take Base Period in &1 Stage for this POR &2 ";
	
	public static final String M00041 = " M00041: No Order Take Base Period details for given POR  &1 ";

	public static final String M00149 = "M00149 : Currently, for given POR - &1, Monthly Process is in OPEN STAGE for more than One Month."
			+ "  So, new ORDER TAKE BASE MONTH will not be created. The  Open status Monthly details are ";

	public static final String M00344 = " &2 OrderTakeBaseMonth Monthly Process is in &3 Stage Cd stage and in Open status. ";

	public static final String M00150 = " M00150 : No Order Take Base month for given POR - &1 is in Final Order #1 or Final Order #2 "
			+ "and in completed status. Current Order take base month &2 is in &3 stage and &4 in status.";

	public static final String M00151 = " M00151: Monthly Cycle - New Order Take Base month: &1 is successfully created for POR - &2 ";

	/* B000059 starts here */

	/** Constant M00109. */
	public static final String M00109 = "M00109 : File Not Exists &1 ";

	/** Constant M00132. */
	public static final String M00132 = "M00132 : &1 More than one file found for processing ";

	/** Constant M00167. */
	public static final String M00335 = "M00335 : &1 Length of the record exceeds the maximum length ";

	/** Constant M00168. */
	public static final String M00336 = "M00336 : &1 Data Type Mismatch &2 ";

	/** Constant M00025. */
	public static final String M00025 = "M00025 : &1 Fixed Length issue &2 ";

	/** Constant M00133. */
	public static final String M00337 = "M00337 : &1 Data Conversion failed &2 ";

	public static final String M00333 = " &1: More than 1 BUYER_GROUP exists for the  particular OCF_REGION_CD & BUYER_GROUP_CD. So Batch stopped ";

	public static final String M00236 = " &1:  Due to No Ratio equally Split has been done for the &2 ";

	public static final String M00235 = " &1:Allocation not done for this &2";

	public static final String M00196 = " &1:  &2 is zero or not available   in &3-Table for the &4.So Batch Process Stopped";

	public static final String M00219 = " &1: '00' Frame Code having More than &2: OCF  limit in &3: for the following combination &4:";

	public static final String M00231 = " M00231 : &1 More than One Order Take Base Month is available for given POR CD &2 for the given stage CD &3 ";

	public static final String M00230 = " M00230 : &1:No Order Take Base Period details for POR CD &2, Stage CD : &3";

	public static final String M00146 = " M00146 : &1:No car series horizon details available for given POR - &2 in &3 table";

	public static final String M00182 = " M00182 : &1:No order horizon details for Monthly order exists in PARAMETER MST for the POR CD : &2";

	public static final String M00338 = " M00338 : &1:No 'send suspend order' details for Monthly order exists in PARAMETER MST for the POR CD : &2";

	public static final String M00245 = " M00245 : &1: Batch process stopped for the POR CD : &2. Due to unexpected calculation error. Please contact the administrator";

	public static final String M00339 = " M00339 : &1: No order informations available for given order take base month &2 for the given POR CD : &3";

	public static final String M00340 = " M00340 : &1:No Production order number resuse flag exists in PARAMETER MST for the POR CD &2";

	public static final String M00341 = " M00341 : &1:No 'attach ex-no' details for Monthly order exists in PARAMETER MST for the POR CD : &2";

	public static final String M00342 = " M00342 : &1:No 'attach service parameter' details for Monthly order exists in PARAMETER MST for the POR CD : &2";

	public static final String M00343 = " M00343 : &1:No 'ex-no' informations found for the POR CD : &2";

	public static final String M00180 = " M00180 : &1:No 'tyre maker' information found for the additional spec code : &2";

	public static final String M00181 = " M00181 : &1:No 'body protection' information found for the additional spec code : &2";

	public static final String M00176 = " M00176 : &1:No 'maps symbol' information found for the POR CD : &2";

	public static final String M00172 = " M00172 : &1 No M/S data informations available for given Order take base month &2 for the given POR - &3 in Latest Master Schedule TRN table";

	public static final String M00189 = "M00189 : Records are successfully inserted";

	public static final String M00116 = "M00116 : &1 : FTP file path not exists ,  FTP Path: [ &2 ] : &3 ";

	public static final String M00191 = "M00191 : &1 : No Order take base month for given POR_CD : &2  in  Draft Order  D1 or Draft Order  D2 or Final Order F1 or Final Order F2 ."
			+ " so Batch process Stopped.";

	public static final String M00214 = "M00214 : &1: No records found in &2 for POR=&3 & BUYER CD=&4 ";

	public static final String M00225 = "M00225 : &1 Successfully Completed for POR CD &2, BUYER GROUP CD &3 ";

	public static final String M00226 = "M00226 : Run time Error message for &1 Batch Process Failed for POR CD &2, BUYER GROUP CD &3.Please contact the Administrator.";

	public static final String M00178 = "M00178 : &1 No Monthly order informations available for given order take base month &2 for the given POR - &3";

	public static final String M00177 = "M00177 : &1 Monthly order informations for given order take base month &2 for the given POR - &3 insertion &4";

	public static final String M00334 = " &1: More than 1 BUYER_GROUP belongs to More than one OCF-Region and OCF_Buyer_Group Combination. So Batch Stopped. ";

	public static final String M00197 = "M00197 : &1 : Report Creation Failed";

	public static final String M00081 = "M00081 : &1 : Zero rows updated to &2";

	public static final String M00092 = " M000092 : &1 : Orders updated Successfully";

	public static final String M00026 = "&1 key value can't be null";
	public static final String M00075 = "M00075 : &1; POR Code is not exist in the master table.";
	public static final String M00103 = "M00103 : &1; Buyer Code is not exist in the master table.";
	public static final String M00104 = "M00104 : &1; RHQ Code is not exist in the master table";
	public static final String M00105 = "M00105 : &1; User ID is not exist in the master table.";
	public static final String M00325 = "M00325 : &1: Role Id & Role Group mapping is not available.";
	public static final String M00326 = "M00326 : &1: Importer User doesnot map with any Buyer Group Code";
	public static final String M00327 = "M00327 : &1: Importer (RHQ) User doesnot map with any RHQ Code";
	public static final String M00328 = "M00328 : &1: Exporter User doesnot map with any POR Code";

	public static final String M00114 = "M00114 : FTP Server Connection Fails : &1";

	public static final String M00117 = "M00117 : No send Interface file exists at the local file path. &1";

	public static final String M00119 = "M00119 : Control File is not exists &1 ";

	public static final String M00173 = "M00173 : &1 path is not exists  &2";

	public static final String M00329 = "M00329 : &1: M/S_SCHEDULE_FIXED_FLAG Value is 0 for Vehicle Sequence ID : &2 ";

	public static final String M00330 = "M00330 : &1: Duplicate Production Number found for different Vehicle Sequence ID : &2 ";

	public static final String M00331 = "M00331 : &1: VIN No recevied Null for Vehicle Sequence ID : &2 ";

	public static final String M00120 = "M00120 : No Production Week Number details for given Production Month &1 ";

	// for B000017

	/** Constant M00174 */
	public static final String M00332 = "M00174 : &1: There is no 00 Frame Code available in &2 - Table for the UK_OSEI_ID &3";
	
	//Added by z014433 for B000035, B000036
	
	public static final String M00071  = "M00071 : &1 There is no &2  for  given POR &3. Batch Stopped ";

	public static final String M00122  = "M00122 : &1 Weekly order stage is &2 for given &3, &4 and &5 ";
	
	public static final String M00353  = "M00353 : &1 More than one ORDER_TAKE_BASE_PERIOD exist for the given POR_CD &2 ";

	//z014433 changes for B000035, B000036 ends here
	public static final String M00354 = "M00354 : &1 : No record found in &2";

	public static final String M00270  = "M00270 : Order has been eliminated, because mismatch in the received EX-NO and the EX-NO available in the MONTHLY PRODUCTION ORDER TRN and EX-NO_MST";
	
	public static final String M00157="M00157 : &1  : There is no Next Order Take Base Period in JOB_SCHEDULE_MST  for  current Order Take Base Month &2 , Order Take Base Week No &3 in POR &4 . "
			+ "So batch process stopped";

	public static final String M00121 = "M00121 : &1 : Weekly Order Take Base Period &2-&3 is created for this POR &4 ";
	
	public static final String M00254= "M00254 : Order has been eliminated because &1 does not exist in PARAMETER MST ";
	
	public static final String M00252= "M00252 : EX-NO &1 Sucessfully Inserted In EX-NO_MST ";
	
	public static final String M00253= "M00253 : EX-NO &1 Sucessfully updated In EX-NO_MST ";
	
	public static final String M00261= "M00261: Production Method Cd &1 has been updated to &2 in table &3";
	
	public static final String M00260="M00260 : Frozen Type Cd &1 has been updated to &2 in table &3";
	
	public static final String M00302="M00302 : For POR : &1, Order Take Base Month : &2, OCG Region Code : &3, No records found in &4";
	
	public static final String M00269="M00269 : Order has been eliminated because &1:POT and &2:SALES_NOTE_NO are not equal &1:POT &2: SALES_NOTE_NO";
	
	public static final String M00268="M00268 : &1: PRODUCTION_MONTH &2 AND PRODUCTION WEEK &3 is less than order_take_base_month &4 and order_take_base_week_no &5 for the given POR_CD";
	
	public static final String M00356="M00356 : &1: successfully received from plant and inserted into &2  table for the given POR_CD=&3 ";
	 
    public static final String M00090  = "M00090 : &1: Pipeline Order Horizon not found for given POR &2 ";
    
    public static final String M00355  = "M00355 : &1 No Data Found for the ORDER_TAKE_BASE_MONTH &2 for the given POR_CD &3  in &4";

    public static final String M00098  = "M00098 : &1:Incorrect Non Operational Flag value for the POR=&2,PRODUCTION_MONTH=&3,PRODUCTION_WEEK_NO=&4.So Process stopped.";
    
    public static final String M00306  = "M00306 : &1:Non Operational Flag should not be &2 for the POR=&3,PRODUCTION_MONTH=&4,PRODUCTION_WEEK_NO=&5.So Process stopped.";
    
    public static final String M00141  = "M00141 : &1:Production Month value should be in YYYYMM format";
    
    //Batch 55 Error Message
    public static final String M00088="Auto Schedule Calculation cannot be done.Since scheduled job streams have already started running.";
    
    public static final String M00357 = "M00357 : &1:There is no Order Take Base Period in &2 Stage for POR =&3 and Buyer Group =&4 in &5-Table.So Batch process Stopped.";

	public static final String M00358 = "M00358 : &1:Batch cannot be triggered as there is no data in start status for the POR &2";
	
	public static final String M00359 = "M00359  : &1 No Data Found for the given combination in &2 for POR &3 in production stage cd &4";
	
	public static final String M00308 = "M00308 : &1 There is no Weekly Cycle found for POR = &2 in &3. So Batch process Stopped";
	
	public static final String M00165 = "M00165 :  &1 OCF Limit details does not exist for the Frame Code for this POR &2 and for this Order Take Base Month &3 and for this "
			+ "Order Take Base Week No &4 in the Table &5.So batch process stopped.";
	
	public static final String M00168 = "M00168 : &1: OCF Limit details not available in the table &2 for this POR &3, OCF Region Code &4, OCF Buyer Group Code &5. SO batch process stopped.";
	
	
	
}
