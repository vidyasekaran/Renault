package com.nissangroups.pd.util;

public class IFQueryConstants {
	
	private IFQueryConstants() {
		super();		
	}

	public static final StringBuilder updateStatusCmnHeader = new StringBuilder()
	.append("UPDATE CMN_FILE_HDR SET STTS = :STTS ,REC_COUNT =:REC_COUNT, REMARKS =:REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO"); 
	
	public static final StringBuilder insertCmnHeader = new StringBuilder()
	.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME,TRN_TYPE)")
	.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME,:TRN_TYPE)"); 	
	
	public static final StringBuilder updateCmnHeader = new StringBuilder()
	.append("UPDATE CMN_FILE_HDR SET STTS = :STTS, REMARKS = :REMARKS ")
	.append("WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO = :SEQ_NO "); 	 			
	
	public static final StringBuilder updateFileStatusCmnHeader = new StringBuilder()
	.append("UPDATE CMN_FILE_HDR SET STTS = :STTS, REC_COUNT = :REC_COUNT, REMARKS = :REMARKS WHERE IF_FILE_ID = :IF_FILE_ID AND SEQ_NO =:SEQ_NO"); 
	
    /** Constant INSERT Flag details in Monthly order Lock Mst table */
	public static final StringBuilder insertCmnInterfaceData = new StringBuilder()
	.append("insert into CMN_INTERFACE_DATA (IF_FILE_ID,SEQ_NO,ROW_NO,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19)")
	.append(" values (:IF_FILE_ID, :SEQ_NO, :ROW_NO, :COL1, :COL2, :COL3, :COL4, :COL5, :COL6, :COL7, :COL8, :COL9, :COL10, :COL11, :COL12, :COL13, :COL14, :COL15, :COL16, :COL17,:COL18,:COL19)"); 
	
	public static final StringBuilder maxOrdrTakBasMon = new StringBuilder()
	.append("SELECT MAX(ORDR_TAKE_BASE_MNTH) FROM MST_WKLY_ORDR_TAKE_BASE");
	
	public static final StringBuilder prmMasVal1 = new StringBuilder()
	.append("SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD = :prmtrCd ");	
		
	public static final StringBuilder insertCmnHeader2arg = new StringBuilder()
	.append("insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO)")
	.append(" values (:IF_FILE_ID,:SEQ_NO)");
}
