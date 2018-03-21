/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.util;

/**
 * Constant file to keep the queries related to the Interface I0000103
 * 
 * @author z016127
 *
 */
public class I000103QueryConstants {

	/* P0001 Constant to extract the record from CMN_INTERFACE_DATA, Please note the * in a.* returns only the columns selected in the subquery 
	 * so it does not return all the columns in this case */
	public static final StringBuilder baseQuery = new StringBuilder()	
			.append("SELECT ROWNUM, a.* FROM ( ")
			.append("SELECT CFH.SEQ_NO,CID.COL1,CID.COL2,CID.COL3,CID.COL4,CID.COL5,CID.COL6 FROM CMN_FILE_HDR CFH  INNER JOIN CMN_INTERFACE_DATA CID ON(  CFH.IF_FILE_ID = CID.IF_FILE_ID AND CFH.SEQ_NO = CID.SEQ_NO) ")
			.append("WHERE CID.IF_FILE_ID = :ifFileId AND CID.SEQ_NO = (SELECT MAX(SEQ_NO) from CMN_FILE_HDR CFH where CFH.IF_FILE_ID = :ifFileId and CFH.STTS IN ('U'))");
	
	/** P0001 Constant to extract the maximum of sequence number from CMN_FILE_HDR */
	public static final StringBuilder baseQuery1 = new StringBuilder()	
		.append("(SELECT MAX(SEQ_NO) from CMN_FILE_HDR CFH where CFH.IF_FILE_ID = :ifFileId and CFH.STTS IN ('U'))");		
	
	/** P0001 Constant to extract the por code from  MST_POR */
	public static final StringBuilder condition1 = new StringBuilder()
			.append("SELECT POR_CD FROM MST_POR WHERE TRIM(POR_CD) = :porCd");
	
	/** P0001 Constant to extract the buyer code from  MST_BUYER */
	public static final StringBuilder condition2 = new StringBuilder()
			.append("SELECT BUYER_CD FROM MST_BUYER WHERE TRIM(BUYER_CD)= :buyerCd");
	
	/** P0001 Constant to extract the rhq code from  MST_REGIONAL_HEAD_QRTR */
	public static final StringBuilder condition3 = new StringBuilder()
			.append("SELECT RHQ_CD FROM MST_REGIONAL_HEAD_QRTR WHERE TRIM(RHQ_CD) =:rhqCd");
	
	/** P0001 Constant to extract the user id from  MST_USER */
	public static final StringBuilder condition4 = new StringBuilder()	
			.append("SELECT USER_ID FROM MST_USER WHERE TRIM(USER_ID) =:userId");

	/** P0001.A Constant to extract the User Role ID is already part of any user role group in the Post Dragon database */
	public static final StringBuilder verifyUserRoleId = new StringBuilder()
	 		.append("SELECT CLD.COL4 FROM CMN_INTERFACE_DATA CLD WHERE CLD.IF_FILE_ID = :ifFileId AND CLD.SEQ_NO = :seqNo ") 
			.append("AND CLD.COL6 NOT IN (SELECT ROLE_ID FROM MST_ROLEGRP_MAPPING) AND CLD.COL1 ='ZVMS_AROLEASGN' ");

	/** P0001.B Constant to extract the Importer - user does not map with any buyer group code */
	public static final StringBuilder verifybuyerGrp = new StringBuilder()
			.append("SELECT CLD.COL3 FROM MST_ROLEGRP MRG INNER JOIN MST_ROLEGRP_MAPPING MRGM ON (MRG.ROLEGRP_ID = MRGM.ROLEGRP_ID) INNER JOIN CMN_INTERFACE_DATA CLD ON (CLD.COL3 = MRGM.ROLE_ID) ") 																														
			.append("WHERE MRGM.ROLE_ID NOT IN (SELECT COL3 FROM CMN_INTERFACE_DATA  WHERE COL1 ='ZVMS_ACUSBYRGRPASGN' AND IF_FILE_ID = :ifFileId  AND SEQ_NO  = :seqNo ) ")																														
			.append("AND MRGM.ROLE_ID NOT IN (SELECT USER_ID FROM MST_USER_BUYERGRP_MAPPING) AND MRG.ROLEGRP_DESC ='Importer(NSC)' ")  
			.append("AND CLD.COL1 ='ZVMS_AROLEASGN' AND CLD.IF_FILE_ID = :ifFileId  AND CLD.SEQ_NO  = :seqNo ");
  
	/** P0001.C Constant to extract  the Importer (RHQ) - user does not map with any RHQ code */
	public static final StringBuilder verifyRhq = new StringBuilder()
			.append("SELECT CLD.COL3 FROM MST_ROLEGRP MRG INNER JOIN MST_ROLEGRP_MAPPING MRGM  ON (MRG.ROLEGRP_ID = MRGM.ROLEGRP_ID) INNER JOIN CMN_INTERFACE_DATA CLD ON (CLD.COL3 = MRGM.ROLE_ID) ")																														
			.append("WHERE MRGM.ROLE_ID NOT IN (SELECT COL3 FROM CMN_INTERFACE_DATA  WHERE COL1 ='ZVMS_ACUSRHQASGN' AND IF_FILE_ID = :ifFileId  AND SEQ_NO  = :seqNo )	")																													
			.append("AND MRGM.ROLE_ID NOT IN (SELECT USER_ID FROM MST_USER_RHQ_MAPPING)	 AND MRG.ROLEGRP_DESC ='Importer(RHQ)' ")  
			.append("AND CLD.COL1 ='ZVMS_AROLEASGN'  AND CLD.IF_FILE_ID = :ifFileId  AND CLD.SEQ_NO  = :seqNo ");
  
	/** P0001.D Constant to extract the Exporter / after sales - user does not map with any POR code  */
	public static final StringBuilder verifyPor = new StringBuilder()
			.append("SELECT CLD.COL3 FROM MST_ROLEGRP MRG INNER JOIN MST_ROLEGRP_MAPPING MRGM ON (MRG.ROLEGRP_ID = MRGM.ROLEGRP_ID)) INNER JOIN CMN_INTERFACE_DATA CLD ON (CLD.COL3 = MRGM.ROLE_ID) ")																														
			.append("WHERE MRGM.ROLE_ID NOT IN (SELECT COL3 FROM CMN_INTERFACE_DATA  WHERE COL1 ='ZVMS_ACUSPORASGN' AND IF_FILE_ID = :ifFileId  AND SEQ_NO  = :seqNo )	")																													
			.append("AND MRGM.ROLE_ID NOT IN (SELECT USER_ID FROM MST_USER_POR_MAPPING)	 AND MRG.ROLEGRP_DESC)='Exporter or After Sales' ")  
			.append("AND CLD.COL1 ='ZVMS_AROLEASGN'   AND CLD.IF_FILE_ID = :ifFileId  AND CLD.SEQ_NO  = :seqNo");
	
	/** P0003 Constant to insert the record into  MST_USER */
	public static final StringBuilder insertUserData = new StringBuilder()
			.append("INSERT INTO MST_USER(USER_ID,USER_NAME,TIMEZONE,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG)")
			.append("VALUES (:userId, :userName, :timeZone, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag) ");
	
	/** P0003 Constant to update the record into  MST_USER */
	public static final StringBuilder updateUserData = new StringBuilder()
			.append("UPDATE MST_USER SET USER_ID=:userId, USER_NAME =:userName ,TIMEZONE =:timeZone,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE USER_ID=:userId ");
		
	/** P0003 Constant to insert the record into  MST_USER_ROLE */
	public static final StringBuilder insertUserRole = new StringBuilder()
			.append("INSERT INTO MST_USER_ROLE(USER_ID,ROLE_ID,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG) ")
			.append("VALUES (:userId, :roleId, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag) ");
	
	/** P0003 Constant to extract the record into  MST_USER_ROLE */
	public static final StringBuilder selectUserRole = new StringBuilder()
			.append("SELECT USER_ID,ROLE_ID FROM MST_USER_ROLE WHERE TRIM(USER_ID) =:userId AND TRIM(ROLE_ID) =:roleId");
	
	/** P0003 Constant to update the record into  MST_USER_ROLE */
	public static final StringBuilder updateUserRole = new StringBuilder()
			.append("UPDATE MST_USER_ROLE SET USER_ID=:userId, ROLE_ID =:roleId ,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE USER_ID=:userId AND ROLE_ID =:roleId");
	
	/** P0004 Constant to insert the record into  MST_USER_POR_MAPPING */
	public static final StringBuilder insertUserPorMapping = new StringBuilder()
			.append("INSERT INTO MST_USER_POR_MAPPING(USER_ID,POR_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG)  ")
			.append("VALUES (:userId, :porCd, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag) ");
	
	/** P0004 Constant to extract the record into  MST_USER_POR_MAPPING */
	public static final StringBuilder selectUserPorMapping = new StringBuilder()
			.append("SELECT USER_ID FROM MST_USER_POR_MAPPING WHERE TRIM(USER_ID) = :userId AND TRIM(POR_CD) = :porCd");
	
	/** P0004 Constant to update the record into  MST_USER_POR_MAPPING */
	public static final StringBuilder updateUserPorMapping = new StringBuilder()
			.append("UPDATE MST_USER_POR_MAPPING SET USER_ID=:userId, POR_CD =:porCd ,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE USER_ID=:userId AND POR_CD =:porCd");
	
	/** P0005 Constant to insert the record into  MST_USER_RHQ_MAPPING */
	public static final StringBuilder insertUserRhqMapping = new StringBuilder()
			.append("INSERT INTO MST_USER_RHQ_MAPPING(USER_ID,RHQ_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG) ")
			.append("VALUES (:userId, :rhqCd, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag ) ");
	
	/** P0005 Constant to update the record into  MST_USER_RHQ_MAPPING */
	public static final StringBuilder updateUserRhqMapping = new StringBuilder()
			.append("UPDATE MST_USER_RHQ_MAPPING SET USER_ID=:userId, RHQ_CD =:rhqCd ,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE USER_ID=:userId AND RHQ_CD =:rhqCd");
	
	/** P0005 Constant to extract the record into  MST_USER_RHQ_MAPPING */
	public static final StringBuilder selectUserRhqMapping = new StringBuilder()
			.append("SELECT USER_ID,RHQ_CD FROM MST_USER_RHQ_MAPPING WHERE TRIM(USER_ID) = :userId AND TRIM(RHQ_CD) = :rhqCd");
	
	/** P0006 Constant to insert the record into  MST_USER_BUYERGRP_MAPPING */
	public static final StringBuilder insertUserBuyerGrpMapping = new StringBuilder()
			.append("INSERT INTO MST_USER_BUYERGRP_MAPPING(USER_ID,BUYERGRP_CD,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG) ")
			.append("VALUES (:userId, :buyerGrpCd, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag ) ");
	
	/** P0006 Constant to update the record into  MST_USER_BUYERGRP_MAPPING */
	public static final StringBuilder updateUserBuyerGrpMapping = new StringBuilder()
			.append("UPDATE MST_USER_BUYERGRP_MAPPING SET USER_ID=:userId, BUYERGRP_CD =:buyerGrpCd ,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE USER_ID=:userId AND BUYERGRP_CD =:buyerGrpCd");
	
	/** P0006 Constant to extract the record into  MST_USER_BUYERGRP_MAPPING */
	public static final StringBuilder selectUserBuyerGrpMapping = new StringBuilder()
			.append("SELECT USER_ID,BUYERGRP_CD FROM MST_USER_BUYERGRP_MAPPING WHERE TRIM(USER_ID) = :userId AND TRIM(BUYERGRP_CD) = :buyerGrpCd");
	
	/** P0007 Constant to insert the record into  MST_ROLE */
	public static final StringBuilder insertRole = new StringBuilder()
			.append("INSERT INTO MST_ROLE(ROLE_ID,ROLE_DESC,CRTD_BY,CRTD_DT,UPDTD_BY,UPDTD_DT,ACTV_FLG) ")
			.append("VALUES (:roleId, :roleDesc, 'I000103' , sysdate, :updtdBy, sysdate, :prcsFlag ) ");
	
	/** P0007 Constant to update the record into  MST_ROLE */
	public static final StringBuilder updateRole = new StringBuilder()
		.append("UPDATE MST_ROLE SET ROLE_ID=:roleId, ROLE_DESC =:roleDesc ,UPDTD_BY =:updtdBy,UPDTD_DT =sysdate,ACTV_FLG =:prcsFlag WHERE ROLE_ID=:roleId");
	
	/** P0007 Constant to extract the record into  MST_ROLE */
	public static final StringBuilder selectRole = new StringBuilder()
		.append("SELECT ROLE_ID FROM MST_ROLE WHERE TRIM(ROLE_ID) = :roleId");
	
	/**
	 * Instantiates a new I000103QueryConstants
	 */
	private I000103QueryConstants() {
			super();		
		} 
}
