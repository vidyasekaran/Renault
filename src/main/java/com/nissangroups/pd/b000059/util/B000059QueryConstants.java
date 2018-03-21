/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : This program stores the query to be used to perform CRUD operations.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

public class B000059QueryConstants 
{
	//PreparedStmt Batch Limit
	public static final int maxLimit = 100;

	public static final StringBuilder insertCmnHeader = new StringBuilder()
	.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,REC_COUNT,TRN_TYPE,STTS,REMARKS)")
	.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:REC_COUNT,:TRN_TYPE,:STTS,:REMARKS)"); 
	
	public static final StringBuilder updateCmnHeader = new StringBuilder()
	.append("UPDATE CMN_FILE_HDR SET REC_COUNT = :REC_COUNT WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO"); 
	
	
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
	.append("UPDATE CMN_FILE_HDR SET STTS = :STTS,TRN_TYPE = :TRN_TYPE  WHERE IF_FILE_ID = :IF_FILE_ID AND FILE_NAME=:FILE_NAME AND SEQ_NO =:SEQ_NO"); 
	

/** Constant INSERT Flag details in Monthly order Lock Mst table */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
	.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16)")
	.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16)");    

	public static final StringBuilder insertCmnInterfaceDataSpecificFields_1 = new StringBuilder()
	.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,");
		
	public static final StringBuilder insertCmnInterfaceDataSpecificFields_2 = new StringBuilder()
	.append(" values (?, ?, ?,");

	
	/** Constant delete records matching sequence number from  DELETE_CMN_INTERFACE_DATA*/ 
	public static final StringBuilder DELETE_CMN_INTERFACE_DATA = new StringBuilder()
	.append("DELETE from CMN_INTERFACE_DATA where IF_FILE_ID = :IF_FILE_ID AND SEQ_NO = :SEQ_NO");
	
	/** Constant delete records matching sequence number from  DELETE_CMN_INTERFACE_DATA*/ 
	public static final StringBuilder DELETE_CMN_HDR_DATA = new StringBuilder()
	.append("DELETE from CMN_FILE_HDR where IF_FILE_ID = :IF_FILE_ID AND SEQ_NO = :SEQ_NO  AND cmi.fileName =:FILENAME");

	/** query to fetch filter condition. */
	public static final StringBuilder getFilterQuery =  new StringBuilder()
	.append("select mf.id.fltrGrp,mf.id.colName, mf.fltrType, mf.fltrVal  FROM MstIfFilter mf where mf.id.ifFileId = :FILENAME");
	
	/*Extracts the Sort order clause based on the Interface_file_Id*/
	public static final StringBuilder getSortQuery = new StringBuilder()
	.append("select  ms.id.colName, ms.ordr,ms.prity from MstIfSorting ms where ms.id.ifFileId = :FILENAME");
	
	private B000059QueryConstants() {
		super();	
	}
	
	
}
