/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-10-2015      z014433(RNTBCI)               Initial Version
 * 12-11-2015		  z014433(RNTBCI)				 Updated to fix UT - Black box defects
 * 18-11-2015		  z014433(RNTBCI)				 Updated to fix JT Defect # 1981
 * 12-12-2015	      z014433(RNTBCI)               Updated to fix JT Defect # 2323
 *
 */   
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000021.bean.MstLckCnfgFlgDtls;
import com.nissangroups.pd.b000021.mapper.MstMnthlyOrdrTkBsPrdRowMapper;
import com.nissangroups.pd.b000021.util.B000021CommonUtil;
import com.nissangroups.pd.b000021.util.QueryConstants;
import com.nissangroups.pd.model.MstMnthlyOrdrTakeLck;
import com.nissangroups.pd.model.MstMnthlyOrdrTakeLckPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class MnthlyStgOpnClsRepository {
	
	public MnthlyStgOpnClsRepository(){
		
	}
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MnthlyStgOpnClsRepository.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/**
	 * @param item
	 * @param updtOnlyFlgDtl
	 * @param stgCdDtl
	 * @param stgStsCdDtl
	 * @return List<Object[]>
	 * 
	 * This method is used to extract NSC, RHQ, Exporter Flag details
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNscRhqExpFlgDtls(MstMnthlyOrdrTkBsPrdRowMapper item,String updtOnlyFlgDtl, String stgCdDtl, String stgStsCdDtl) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String stgCdVal = item.getSTAGE_CD();
		String stgStsCdVal = item.getSTAGE_STTS_CD();
		String porCdVal = item.getId().getPOR_CD();
		
		String flagDetailsQryStgCd = null;
		String flagDetailsQryStgStsCd = null;
		
		LOG.info("Job inputs Update Only Flag is : "+updtOnlyFlgDtl+ " and Stage Code  is :" +stgCdDtl +" and stage status code is : "+stgStsCdDtl);
    	LOG.info("Extracted Item value , por cd is "+porCdVal+" and stage code: "+stgCdVal +" and stage status code is : "+stgStsCdVal);
		
    	Query flagDetailsQry = entityManager.createQuery(QueryConstants.getNSCRHQEXPFlagDtls.toString());

		if (updtOnlyFlgDtl.equalsIgnoreCase(PDConstants.YES)) {
			LOG.info("Update Flag is YES, hence setting EXTRACTED ITEM stage code and stage status code");
			flagDetailsQryStgCd = stgCdVal;
			flagDetailsQryStgStsCd = stgStsCdVal;
		}
		else if (updtOnlyFlgDtl.equalsIgnoreCase(PDConstants.NO)) 
		{
			LOG.info("Update Flag is NO, hence setting JOB INPUT stage code and stage status code");
			flagDetailsQryStgCd = stgCdDtl;
			flagDetailsQryStgStsCd = stgStsCdDtl;
		}
		
		flagDetailsQry.setParameter(PDConstants.PRMTR_PORCD, porCdVal);
		flagDetailsQry.setParameter(PDConstants.PRMTR_STAGE_CD, flagDetailsQryStgCd);
		flagDetailsQry.setParameter(PDConstants.PRMTR_STAGE_STATUS_CD, flagDetailsQryStgStsCd);
		
		LOG.info("Lock Configuration Mst Flag Extraction Query String is : "+ flagDetailsQry);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return flagDetailsQry.getResultList();
	}
	
	
	/**
	 * @param crSrsObj
	 * @param maxPrdMnth
	 * @param ordrTkBsMnth
	 * @param prdStgCdDtl
	 * @return Buyer group and car series details
	 * 
	 * This method id used to Extract Car Series and Buyer Group for the calculated Production Months
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCarSeriesBuyerGrpDtls(Object[] crSrsObj, String maxPrdMnth, String ordrTkBsMnth, List<String> prdStgCdDtl) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> byrGrpDtls = null;
		
		LOG.info("POR is : "+crSrsObj[0].toString()+" , Car series is : " +crSrsObj[2].toString()+" Max Prod Month is " +maxPrdMnth+" Order take base Month is " +ordrTkBsMnth+" Prod Stage Code is " +prdStgCdDtl);

    	Query getByrGrpCdQry = entityManager.createNativeQuery(QueryConstants.getBuyerGrpCodeDtls.toString());
		
		getByrGrpCdQry.setParameter(PDConstants.PRMTR_PORCD, crSrsObj[0].toString());
		getByrGrpCdQry.setParameter(PDConstants.PRMTR_CARSRS, crSrsObj[2].toString());
		getByrGrpCdQry.setParameter(PDConstants.MAX_PROD_MONTH, maxPrdMnth+PDConstants.WEEK1);
		getByrGrpCdQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth+PDConstants.WEEK1);
		getByrGrpCdQry.setParameter(PDConstants.PRMTR_PRDSTGCD, prdStgCdDtl); 
		
		LOG.info("Buyer Group Details Extraction Query String is : "+ getByrGrpCdQry);
		
		byrGrpDtls = getByrGrpCdQry.getResultList();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return byrGrpDtls;
	}
	
	/**
	 * @param item
	 * @param crSrsByrGrpDtls
	 * @param jobParamUpdtOnlyFlg
	 * @param jobParamStgCd
	 * 
	 * This method is to save the extracted lock configuration table information to monthly order take lock mast table
	 */
	public void saveLckFlgDtls(MstLckCnfgFlgDtls item,List<Object[]> crSrsByrGrpDtls, String jobParamUpdtOnlyFlg, String jobParamStgCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		for(Object[] carSrsByrGrpObj : crSrsByrGrpDtls) {
			
			saveExtractedLckDtls(item,carSrsByrGrpObj, jobParamStgCd, jobParamUpdtOnlyFlg);
			
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	
	/**
	 * @param item
	 * @param carSrsByrGrpObj
	 * @param jobParamStgCd
	 * @param jobParamUpdtOnlyFlg
	 * 
	 * This method actually the extracted lock configuration table information to monthly order take lock mast table using JPA 
	 */
	private void saveExtractedLckDtls(MstLckCnfgFlgDtls item,	Object[] carSrsByrGrpObj, String jobParamStgCd, String jobParamUpdtOnlyFlg) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		MstMnthlyOrdrTakeLck ordrLck = populateToBean(item,carSrsByrGrpObj, jobParamStgCd);
		
		LOG.info(" jobParamUpdtOnlyFlg value is : "+jobParamUpdtOnlyFlg);	
		
		if (jobParamUpdtOnlyFlg.equalsIgnoreCase(PDConstants.NO)){
		LOG.info(" Flag is N, hence calling JPA MERGE method");		
		try {
			entityManager.merge(ordrLck);
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00164, 
					PDConstants.CONSTANT_V3, new String[] {
					PDConstants.BATCH_21_ID,
					PDConstants.INSERTION,
					PDConstants.TBL_NM_MST_MNTHLY_ORDR_TAKE_LCK });
				B000021CommonUtil.stopBatch();
		}
		}
		else {
			LOG.info(" Flag is Y, hence calling JPA PERSIST method");		
			MstMnthlyOrdrTakeLckPK ordrLckPK = new MstMnthlyOrdrTakeLckPK();
			
			ordrLckPK.setPorCd(item.getPorCd());
			ordrLckPK.setOrdrTakeBaseMnth(item.getOrdrTkBsMnth());
			ordrLckPK.setBuyerGrpCd(CommonUtil.convertObjectToString(carSrsByrGrpObj[1]));
			ordrLckPK.setCarSrs(CommonUtil.convertObjectToString(carSrsByrGrpObj[0]));
			ordrLckPK.setProdOrdrStageCd(CommonUtil.getPrdOrdrStgCd(jobParamStgCd));
			
		Object obj = entityManager.find(MstMnthlyOrdrTakeLck.class, ordrLckPK);
		
		LOG.info("Whether composite key already exist in monthly order take lock mst table: "+obj);
		
		if (obj instanceof MstMnthlyOrdrTakeLck)
			LOG.info("Composite key already exist, hence not inserting");
		else
		{
			LOG.info("Composite key NOT existS, hence going to INSERT to monthly order take lock mst table");
			try {
				entityManager.persist(ordrLck);
			} catch (Exception e) {
				LOG.error(e);
				CommonUtil.logMessage(PDMessageConsants.M00164, 
						PDConstants.CONSTANT_V3, new String[] {
						PDConstants.BATCH_21_ID,
						PDConstants.INSERTION,
						PDConstants.TBL_NM_MST_MNTHLY_ORDR_TAKE_LCK });
					B000021CommonUtil.stopBatch();
			}
		}
		} 
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}


	/**
	 * @param item
	 * @param carSrsByrGrpObj
	 * @param jobParamStgCd
	 * @return monthly order take lock mst entity class
	 * 
	 * This method is to populate the entity class to save the extracted lock configuration table information to monthly order take lock mst table
	 */
	private MstMnthlyOrdrTakeLck populateToBean(MstLckCnfgFlgDtls item, Object[] carSrsByrGrpObj, String jobParamStgCd) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		MstMnthlyOrdrTakeLck mstOrdrLck = new MstMnthlyOrdrTakeLck();
		MstMnthlyOrdrTakeLckPK mstOrdrLckPK = new MstMnthlyOrdrTakeLckPK();
		
		String prdOrdrStgCd = CommonUtil.getPrdOrdrStgCd(jobParamStgCd);
		
		mstOrdrLckPK.setPorCd(item.getPorCd());
		mstOrdrLckPK.setOrdrTakeBaseMnth(item.getOrdrTkBsMnth());
		mstOrdrLckPK.setBuyerGrpCd(CommonUtil.convertObjectToString(carSrsByrGrpObj[1]));
		mstOrdrLckPK.setCarSrs(CommonUtil.convertObjectToString(carSrsByrGrpObj[0]));
		mstOrdrLckPK.setProdOrdrStageCd(prdOrdrStgCd);
		mstOrdrLck.setId(mstOrdrLckPK);
		
		mstOrdrLck.setExptrLckFlag( item.getExpFlg());
		mstOrdrLck.setNscLckFlag( item.getnSCFlg());
		mstOrdrLck.setOrdrTransFlag(item.getOrdrTsmnFlg());
		mstOrdrLck.setRhqLckFlag(item.getrHQFlg());
		mstOrdrLck.setFrznLckFlag(PDConstants.CONSTANT_ZERO);
		
		//Updated to fix JT Defect # 2323
		mstOrdrLck.setCrtdBy(PDConstants.BATCH_21_ID);
		mstOrdrLck.setUpdtdBy(PDConstants.BATCH_21_ID);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return mstOrdrLck;
	}

	/**
	 * @param ordrTkBsMnth
	 * @param porCdVal
	 * @param jobParamStgCd
	 * @param jobParamStgStsCd
	 * 
	 * This method is used to update the system lock flag in MONTHLY_ORDER_TAKE_BASE_PERIOD_MST table.
	 */
	public void updateSysLockFlag(String ordrTkBsMnth, String porCdVal, String jobParamStgCd, String jobParamStgStsCd) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
		
		LOG.info("Extracted Item Values are : "+porCdVal+ " and Order take base month  is :" +ordrTkBsMnth + " and Stage Code  is :" +jobParamStgCd +" and stage status code is : "+jobParamStgStsCd);

		Query queryUpdtsysLck= entityManager.createQuery(QueryConstants.updateSysLckMntlyOrdrTkBsPrd.toString());
		
		queryUpdtsysLck.setParameter(PDConstants.PRMTR_STAGE_STATUS_CD, jobParamStgStsCd);
		queryUpdtsysLck.setParameter(PDConstants.PRMTR_PORCD, porCdVal);
		queryUpdtsysLck.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		queryUpdtsysLck.setParameter(PDConstants.PRMTR_STAGE_CD, jobParamStgCd);
		queryUpdtsysLck.setParameter(PDConstants.PRMTR_UPDT_BY, PDConstants.BATCH_21_ID);
		
		 LOG.info(" Order Take Base Period Lock Flag Updation Query String is : "+ queryUpdtsysLck);
		
		try {
			queryUpdtsysLck.executeUpdate();
		} catch (Exception e) {
			LOG.error(e);
			CommonUtil.logMessage(PDMessageConsants.M00164, 
					PDConstants.CONSTANT_V3, new String[] {
					PDConstants.BATCH_21_ID,
					PDConstants.UPDATION,
					PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD });
				B000021CommonUtil.stopBatch();
		}  
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCdVal
	 * @param jobParamStgCd
	 * @param jobParamStgStsCd
	 * 
	 * This method is used to update the stage and stage status code in MONTHLY_ORDER_TAKE_BASE_PERIOD_MST table.
	 */
	public void updateStgStsCdDtls(String ordrTkBsMnth, String porCdVal, String jobParamStgCd, String jobParamStgStsCd) {
		
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR );
			
			Query queryUpdtstgStsCd= entityManager.createQuery(QueryConstants.updateStgStsCdMntlyOrdrTkBsPrd.toString());
			
			queryUpdtstgStsCd.setParameter(PDConstants.PRMTR_STAGE_CD, jobParamStgCd);
			queryUpdtstgStsCd.setParameter(PDConstants.PRMTR_STAGE_STATUS_CD, jobParamStgStsCd);
			queryUpdtstgStsCd.setParameter(PDConstants.PRMTR_PORCD, porCdVal);
			queryUpdtstgStsCd.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
			queryUpdtstgStsCd.setParameter(PDConstants.PRMTR_UPDT_BY, PDConstants.BATCH_21_ID);
			
			 LOG.info(" Order Take Base Period stage and status code Flag Updation Query String is : "+ queryUpdtstgStsCd);
			 
			try {
				queryUpdtstgStsCd.executeUpdate();
			} catch (Exception e) {
				LOG.error(e);
				CommonUtil.logMessage(PDMessageConsants.M00164, 
						PDConstants.CONSTANT_V3, new String[] {
						PDConstants.BATCH_21_ID,
						PDConstants.UPDATION,
						PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD });
					B000021CommonUtil.stopBatch();
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
