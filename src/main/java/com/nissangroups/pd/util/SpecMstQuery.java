/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-SpecMstQuery
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import static com.nissangroups.pd.util.PDConstants.COMM_HEADER_STATUS_FAIL;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Class SpecMstQuery.
 *
 * @author z002548
 */
public class SpecMstQuery {

	/** Variable pattern match. */
	StringBuilder patternMatch;

	/** Variable entity manager. */
	private EntityManager entityManager;

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * To Update MST_END_ITM_SPEC based on availability of POR in the MST_POR.
	 *
	 * @return Update count
	 */

	public int porUpdateCheck() {

		String porSql = "UPDATE MST_END_ITM_SPEC SET EI_SPEC_ERR_FLAG = '1',EI_SPEC_INT_ERR_CD = '2',UPDTD_BY = 'B000001',UPDTD_DT = SYSDATE WHERE EI_SPEC_ERR_FLAG = '1' AND EI_SPEC_INT_ERR_CD = '1' AND POR_CD IN (SELECT POR_CD FROM MST_END_ITM_SPEC WHERE POR_CD IN (SELECT POR_CD FROM MST_POR) AND EI_SPEC_INT_ERR_CD = '1')";

		Query query = getEntityManager().createNativeQuery(porSql);
		int porupdate = query.executeUpdate();

		return porupdate;
	}

	/**
	 * To Update MST_END_ITM_SPEC based on availability of Buyer Code in the  BUYER_MST.
	 *
	 * @return updated count
	 */

	public int buyerUpdateCheck() {

		String buyerSql = "UPDATE MST_END_ITM_SPEC SET EI_SPEC_ERR_FLAG = '1',EI_SPEC_INT_ERR_CD = '3' ,UPDTD_BY = 'B000001',UPDTD_DT = SYSDATE WHERE EI_SPEC_ERR_FLAG = '1' AND EI_SPEC_INT_ERR_CD = '2' AND BUYER_CD IN (SELECT BUYER_CD FROM MST_END_ITM_SPEC E WHERE BUYER_CD IN  (SELECT BUYER_CD FROM MST_BUYER B, MST_POR P WHERE B.PROD_REGION_CD = P.PROD_REGION_CD AND P.POR_CD = E.POR_CD ) AND EI_SPEC_INT_ERR_CD = '2' )";

		Query query = getEntityManager().createNativeQuery(buyerSql);

		return query.executeUpdate();
	}

	/**
	 * To Update MST_END_ITM_SPEC based on availability of Spec Destination Code in the  BUYER_SPEC_DESTINATION_MST.
	 *
	 * @return updated count
	 */

	public int specBuyerUpdateCheck() {
		String specBuyerSql = "UPDATE MST_END_ITM_SPEC SET EI_SPEC_ERR_FLAG = '4',EI_SPEC_INT_ERR_CD = '4' ,UPDTD_BY = 'B000001',UPDTD_DT = SYSDATE WHERE EI_SPEC_ERR_FLAG = '1' AND EI_SPEC_INT_ERR_CD = '3' AND SPEC_DESTN_CD IN (SELECT SPEC_DESTN_CD FROM MST_END_ITM_SPEC E WHERE SPEC_DESTN_CD IN  (SELECT SPEC_DESTN_CD FROM MST_BUYER_SPEC_DESTN B, MST_POR P WHERE B.PROD_REGION_CD = P.PROD_REGION_CD AND P.POR_CD = E.POR_CD ) AND EI_SPEC_INT_ERR_CD = '3' )";
		Query query = getEntityManager().createNativeQuery(specBuyerSql);
		return query.executeUpdate();
	}

	/**
	 * To Update MST_END_ITM_SPEC based on availability of Color in the MST_EXT_CLR.
	 *
	 * @return updated count
	 */
	public int exteriorUpdateCheck() {
		String specClrSql = "UPDATE MST_END_ITM_SPEC SET EI_SPEC_ERR_FLAG = '2',EI_SPEC_INT_ERR_CD = '5' ,UPDTD_BY = 'B000001',UPDTD_DT = SYSDATE WHERE EI_SPEC_ERR_FLAG = '4' AND EI_SPEC_INT_ERR_CD = '4' AND EXT_CLR_CD IN (SELECT EXT_CLR_CD FROM MST_END_ITM_SPEC WHERE EXT_CLR_CD IN  (SELECT CONCAT(C.EXT_CLR_CD,C.PROD_FMY_CD) FROM MST_EXT_CLR C,MST_END_ITM_SPEC E WHERE C.PROD_FMY_CD = E.PROD_FMY_CD) AND EI_SPEC_INT_ERR_CD = '4' )";
		Query query = getEntityManager().createNativeQuery(specClrSql);
		return query.executeUpdate();
	}

	/**
	 * To get the Distinct List of POR in the END Item Spec Master table.
	 *
	 * @return List of POR
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getEismPorList() {

		String listPorSql = "SELECT DISTINCT POR_CD FROM MST_END_ITM_SPEC";
		Query query = getEntityManager().createNativeQuery(listPorSql);
		return query.getResultList();
	}

	
	/**
	 * To get the Maximum Sequence ID in the END ITEM SPEC Master table.
	 *
	 * @return Maximum Sequence ID
	 */
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getEISMMaxSeqID() {
		String maxSeqIDSql = "SELECT NVL(MAX(SEQ_ID),0) FROM MST_END_ITM_SPEC ";
		Query query = getEntityManager().createNativeQuery(maxSeqIDSql);
		return query.getResultList();
	}

	/**
	 * To update the Common File Header Table.
	 *
	 * @param fileID the file id
	 * @param sqlInSet the sql in set
	 * @param status the status
	 * @return the int
	 */
	public int updateCMNHeader(String fileID, String sqlInSet, String status) {

		String remark = "";
		StringBuilder cmnHeaderSql = new StringBuilder();
		if (status.equals(COMM_HEADER_STATUS_FAIL)) {
			remark = M00043;
		} else {
			remark = M00113;
		}
		cmnHeaderSql.append("UPDATE CMN_FILE_HDR SET STTS = '" + status
				+ "',REMARKS = '" + remark + "' WHERE IF_FILE_ID = '" + fileID
				+ "' AND SEQ_NO IN (" + sqlInSet + ")");
		Query query = entityManager.createNativeQuery(cmnHeaderSql.toString());
		return query.executeUpdate();
	}

}
