/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035/36
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open	/ Update Weekly Order stage close														
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 * 05-01-2016		  z014433(RNTBCI)				 Updated for JT Defect # 3154	
 *
 */  
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000035.util.B000035CommonUtil;
import com.nissangroups.pd.b000035.util.B000035Constants;
import com.nissangroups.pd.b000035.util.QueryConstants;
import com.nissangroups.pd.b000036.util.B000036CommonUtil;
import com.nissangroups.pd.b000036.util.B000036Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class MstWklyOrdrTkBsPrdRepository {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MstWklyOrdrTkBsPrdRepository.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable Common Util. */
    private CommonUtil commonUtil;
    
    public MstWklyOrdrTkBsPrdRepository(){
		
	}
	

	/**
	 * @param porCdVal
	 * @param ordrTkBsMnth
	 * @param ordrTkBsWkNo
	 * 
	 * This method is used to update the stage code and stage status code for given por, order take base month and order take base week no for B000035
	 */
	public void updateStgStsCd(String porCdVal, String ordrTkBsMnth, String ordrTkBsWkNo) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
		commonUtil = new CommonUtil();
		
		LOG.info("Extracted Item Values are : "+porCdVal+ " and Order take base month  is :" +ordrTkBsMnth + " and Order take base week number  is :" +ordrTkBsWkNo);

		Query queryUpdtStgSts= entityManager.createQuery(QueryConstants.updateStgStsCdWklyOrdrTkBsPrd.toString());
		
		queryUpdtStgSts.setParameter(PDConstants.PRMTR_STAGE_CD, B000035Constants.CONSTANT_WK);
		queryUpdtStgSts.setParameter(PDConstants.PRMTR_STAGE_STATUS_CD, PDConstants.CONSTANT_O);
		queryUpdtStgSts.setParameter(PDConstants.PRMTR_UPDT_BY, B000035Constants.BATCH_35_ID);
		queryUpdtStgSts.setParameter(PDConstants.PRMTR_PORCD, porCdVal);
		queryUpdtStgSts.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		queryUpdtStgSts.setParameter(B000035Constants.ORDR_TK_BS_WK_NO, ordrTkBsWkNo);
		
		 LOG.info(" Weekly Order Take Base Period Status Flag Updation Query String is : "+ queryUpdtStgSts);
		 
		try {
			queryUpdtStgSts.executeUpdate(); 
			String[] messageParams = {B000035Constants.BATCH_35_ID,B000035Constants.OPENED_STS,porCdVal,ordrTkBsMnth,ordrTkBsWkNo};
        	B000035CommonUtil.logMessage(PDMessageConsants.M00122, PDConstants.P0002, messageParams);
		} catch (Exception e) {
			LOG.error(e);
			logUpdnMsg(B000035Constants.BATCH_ID_B000035);
				B000035CommonUtil.stopBatch();
		}  
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
     * @param batchId
     * 
     * Logs updation transaction success or failure message
     */
    private void logUpdnMsg(String batchId) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	CommonUtil.logMessage(PDMessageConsants.M00164, 
				PDConstants.CONSTANT_V3, new String[] {
    			batchId,
				PDConstants.UPDATION,
				B000035Constants.TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD });
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
	
	/**
	 * @param porCdVal
	 * @param ordrTkBsMnth
	 * @param ordrTkBsWkNo
	 * 
	 * This method is used to update the stage status code for given por, order take base month and order take base week no for B000036
	 */
	public void updateStsCdDtl(String porCdVal, String ordrTkBsMnth, String ordrTkBsWkNo) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
		commonUtil = new CommonUtil();
		
		LOG.info("Extracted Item Values are : "+porCdVal+ " and Order take base month  is :" +ordrTkBsMnth + " and Order take base week number  is :" +ordrTkBsWkNo);

		Query queryUpdtStsCd= entityManager.createQuery(QueryConstants.updateStsCdWklyOrdrTkBsPrd.toString());
		
		queryUpdtStsCd.setParameter(PDConstants.PRMTR_STAGE_STATUS_CD, B000036Constants.CONSTANT_CLOSE);
		queryUpdtStsCd.setParameter(PDConstants.PRMTR_UPDT_BY, B000036Constants.BATCH_36_ID);
		queryUpdtStsCd.setParameter(PDConstants.PRMTR_PORCD, porCdVal);
		queryUpdtStsCd.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		queryUpdtStsCd.setParameter(B000036Constants.ORDR_TK_BS_WK_NO, ordrTkBsWkNo);
		
		 LOG.info(" Weekly Order Take Base Period Stage Code Updation Query String is : "+ queryUpdtStsCd);
		 
		try {
			queryUpdtStsCd.executeUpdate(); 
			String[] messageParams = {B000036Constants.BATCH_36_ID,B000036Constants.CLOSED_STS,ordrTkBsMnth,ordrTkBsWkNo,porCdVal};
        	B000036CommonUtil.logMessage(PDMessageConsants.M00122, PDConstants.P0002, messageParams);
		} catch (Exception e) {
			LOG.error(e);
			logUpdnMsg(B000036Constants.BATCH_ID_B000036);
				B000036CommonUtil.stopBatch();
		}  
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Gets the entityManager
	 *
	 * @return the entityManager
	 */
	
	public EntityManager getEntityManager() {
		return entityManager;
	}


	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
