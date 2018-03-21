/*
 * System Name     :Post Dragon 
 * Sub system Name :I Interface
 * Function ID     :PST_DRG_I000087
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly Production Schedule from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000087.util;

/**
 * Constant file to keep the queries related to the Interface I000087
 * 
 * @author z016127
 *
 */
public class I000087QueryConstants 
{
	/** P0001 Constant to extract data from CMN_INTERFACE_DATA */
	public static final StringBuilder baseQuery = new StringBuilder()
		.append("SELECT CLD.COL1,CLD.COL3,CLD.COL4,CLD.COL5,CLD.COL6,CLD.COL7,CLD.COL8,CLD.COL9,CLD.COL10,CLD.COL11,CLD.COL14,CLD.COL16,CLD.COL18,CLD.COL19,CLD.COL20, ")
		.append("CLD.COL23,CLD.COL24,CLD.COL25,CLD.COL26,CLD.COL30,M.VAL1, MO.OSEI_ID FROM MST_PRMTR M, CMN_INTERFACE_DATA CLD , MST_PLNT_CD MP,MST_OEI_SPEC MOS ")
		.append("INNER JOIN MST_OEI_BUYER MOB ON (MOS.OEI_SPEC_ID =	MOB.OEI_SPEC_ID AND MOS.POR_CD	= MOB.POR_CD) ")
		.append("INNER JOIN MST_OSEI MO ON (  MOB.OEI_BUYER_ID = MO.OEI_BUYER_ID	AND MOB.POR_CD = MO.POR_CD) ")
		.append("WHERE CLD.IF_FILE_ID= :interfaceId  and CLD.SEQ_NO = (SELECT min(CM.SEQ_NO) FROM CMN_FILE_HDR CM WHERE CM.IF_FILE_ID= :interfaceId ")
		.append("AND CM.STTS='U' ) AND M.KEY1	= :porCd AND M.PRMTR_CD =	:potCd AND MOS.POR_CD	= :porCd AND MOS.APPLD_MDL_CD =	SUBSTR(CLD.Col6,1,13) ")
		.append("AND MOS.PCK_CD	= SUBSTR(CLD.Col6,14,5)	AND MOS.SPEC_DESTN_CD =	CASE LENGTH(CLD.Col8)  WHEN 3 THEN CONCAT(CLD.Col8,' ')   WHEN 2 THEN CONCAT(CLD.COL8,'  ') ")
		.append("WHEN 1 THEN CONCAT(CLD.COL8,'   ')  ELSE CLD.Col8 END AND MP.POR_CD = :porCd AND MP.PROD_PLNT_CD = CASE LENGTH(SUBSTR(CLD.Col1,7,1)) ")
		.append("WHEN 1 THEN CONCAT(SUBSTR(CLD.Col1,7,1),' ') ELSE SUBSTR(CLD.Col1,7,1) END ");
	
	/** P0001 Constant to extract production month  from CMN_INTERFACE_DATA */
	public static final StringBuilder prdMnthQuery = new StringBuilder()
		.append("select distinct(substr((m.col1),0,6)) from CmnInterfaceData m  where m.cmnFileHdr.id.ifFileId= :interfaceId  ")
		.append("and m.cmnFileHdr.id.seqNo=(Select min(cm.id.seqNo) from CmnFileHdr cm where cm.cmnInterfaceMst.ifFileId= :interfaceId  ")
		.append("and cm.stts=:interfaceStatus  )");
	
	/** P0002 Constant to delete records from TRN_WKLY_PROD_SHDL_IF */
	public static final StringBuilder deleteWklySchdlIf = new StringBuilder()
		.append(" DELETE FROM TRN_WKLY_PROD_SHDL_IF WHERE POR_CD = :porCd  ");
	
	/** P0003 Constant to extract maximum of sequence number from TRN_WKLY_PROD_SHDL_IF */
	public static final String TRN_WKLY_PROD_SCHDL_SEQ_ID = "select MAX(SEQ_ID) from TRN_WKLY_PROD_SHDL_IF";
	
	/**
	 * Instantiates a new I000087QueryConstants
	 */
	private I000087QueryConstants() {
		super();		
	}	
}
