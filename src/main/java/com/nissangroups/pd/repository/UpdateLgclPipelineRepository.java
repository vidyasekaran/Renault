/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000050
 * Module                  : Ordering		
 * Process Outline     : Update Logical Pipeline															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z014433(RNTBCI)               Initial Version
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
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000050.util.B000050CommonUtil;
import com.nissangroups.pd.b000050.util.B000050Constants;
import com.nissangroups.pd.b000050.util.QueryConstants;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * @author z014433
 * Repository class for B000050
 */

public class UpdateLgclPipelineRepository {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(UpdateLgclPipelineRepository.class);
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
    @Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;
    
public UpdateLgclPipelineRepository(){
		
	}
	
    /**
     * @param porCd
     * @param prdnMnth
     * @return week details for given month
     */
    public List<String> getWeekNumDtls(String porCd,String prdnMnth) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	
		Query wkDtlsQry = entityManager.createNativeQuery(QueryConstants.getWkNumDtlsCalenderQry.toString());
		
		wkDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		wkDtlsQry.setParameter(PDConstants.PRODUCTION_MONTH, prdnMnth);
		
		LOG.info("Week Details Extraction Query String is : "+ wkDtlsQry);
		
		List<String> wkNumLst= wkDtlsQry.getResultList();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return wkNumLst;
	}
    
    /**
     * @param porCd
     * @param ordrTkBsMnth
     * @param ordrTkBsWkNo
     * @param ordrHrzn
     * @param prdnPrdDtls
     * @return Latest Master schedule Trn records
     */
    public List<Object[]> fetchTrnMstShdDtls(String porCd, String ordrTkBsMnth,String ordrTkBsWkNo, String ordrHrzn,List<String> prdnPrdDtls) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	
    	String extractTrnDtlsQryStr = QueryConstants.getLtstMstTrnDtls.toString();
    	
		if(!ordrHrzn.equalsIgnoreCase(B000050Constants.HORIZON_INFINITY)) 
			extractTrnDtlsQryStr = extractTrnDtlsQryStr+QueryConstants.getLtstMstTrnDtlsNonInfiHrzn.toString(); 
		
		Query extractTrnDtlsQry = entityManager.createNativeQuery(extractTrnDtlsQryStr);
    	
		extractTrnDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		extractTrnDtlsQry.setParameter(B000050Constants.ORDR_TK_BS_WK_NO, ordrTkBsWkNo);
		extractTrnDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		
		if(!ordrHrzn.equalsIgnoreCase(B000050Constants.HORIZON_INFINITY)){
			extractTrnDtlsQry.setParameter(B000050Constants.PRDN_PRD_LST, prdnPrdDtls);
		}
		
		//Results will have all column values from TRN_LTST_MST_SHDL table
		List<Object[]> results = extractTrnDtlsQry.getResultList();
		
		if (results.isEmpty()) {
			String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,ordrTkBsMnth,porCd,B000050Constants.TBL_NM_LATEST_MASTER_SCHEDULE_TRN};
        	B000050CommonUtil.logMessage(PDMessageConsants.M00355, PDConstants.P0003, messageParams); 
		} else
			LOG.info("Latest Master Schedule Trn Record Count : "+results.size());
    	
    	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    	
    	return results;
	}
    
    
    /**
     * @param porCd
     * @param prdnOrds
     * @param ordrTkBsPrd
     * @param maxPrdMnth
     * @return Logical Pipeline Trn records
     */
    public List<Object> fetchTrnLgclPipeLnDtls(String porCd, List<Object> prdnOrds, String ordrTkBsPrd, String maxPrdMnth) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	
		Query getLgclPipLnTrnDtlsQry = entityManager.createNativeQuery(QueryConstants.getLgclPipLnTrnDtls.toString());
    	
		getLgclPipLnTrnDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		getLgclPipLnTrnDtlsQry.setParameter(B000050Constants.PRDN_ORDR_NO_LST, prdnOrds);
		getLgclPipLnTrnDtlsQry.setParameter(PDConstants.PRMTR_BASEPERIOD, ordrTkBsPrd);
		getLgclPipLnTrnDtlsQry.setParameter(PDConstants.PRODUCTION_MONTH, maxPrdMnth);
		
		List<Object> results = getLgclPipLnTrnDtlsQry.getResultList();
		//Results will have PROD_ORDR_NO column values from TRN_LGCL_PPLN table
			if (results.isEmpty()) {
			String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,ordrTkBsPrd.substring(0, 6),porCd,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN};
        	B000050CommonUtil.logMessage(PDMessageConsants.M00355, B000050Constants.P0004_1, messageParams);
		} else
			LOG.info("Logical Pipeline Trn Record Count : "+results.size());
    	
    	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
    	
    	return results;
	}
    
    /**
     * @param porCd
     * @param prdnOrds
     * @param ordrTkBsPrd
     * @param maxPrdMnth
     * 
     * Updates the Order delete flag in Logical pipeline Trn table
     */
    public void updateOrdrDelFlagVal(String porCd, List<Object> prdnOrds, String ordrTkBsPrd, String maxPrdMnth) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		Query queryUpdate= entityManager.createNativeQuery(QueryConstants.updtOrdrDelFlg.toString());
		
		queryUpdate.setParameter(PDConstants.PRMTR_PORCD, porCd);
		queryUpdate.setParameter(B000050Constants.PRDN_ORDR_NO_LST, prdnOrds);
		queryUpdate.setParameter(PDConstants.PRMTR_BASEPERIOD, ordrTkBsPrd);
		queryUpdate.setParameter(PDConstants.PRODUCTION_MONTH, maxPrdMnth);
		
		try {
    		queryUpdate.executeUpdate();
    		logStsMsg(PDMessageConsants.M00163,PDConstants.UPDATED,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
		} catch (Exception e) {
			LOG.info(PDConstants.EXCEPTION+e);
			logStsMsg(PDMessageConsants.M00164,PDConstants.UPDATION,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
			B000050CommonUtil.stopBatch();
		} 
        
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    }
    
    /**
     * @param msgId
     * @param oprtn
     * @param tblNm
     * 
     * Logs transaction success or failure message
     */
    private void logStsMsg(String msgId, String oprtn, String tblNm) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	CommonUtil.logMessage(msgId, 
				PDConstants.CONSTANT_V3, new String[] {
				B000050Constants.BATCH_50_ID_MSG,
				oprtn,
				tblNm });
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}

    /**
     * @param por
     * @param jobParamPrcsTyp
     * @param ordrTkBsPrd
     * 
     * Update stage code in MOnthly/weekly order take base period tables
     */
	public void updtStgComplSts(String por, String jobParamPrcsTyp, Object ordrTkBsPrd) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	
    	String tblNmVal = null;
    	Query updtQry= null;
    	
    	if (jobParamPrcsTyp.equalsIgnoreCase(PDConstants.MONTHLY)) {
			tblNmVal = PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD;
			 updtQry= entityManager.createNativeQuery(QueryConstants.updtStgCdMnthly.toString());
		} else if (jobParamPrcsTyp.equalsIgnoreCase(PDConstants.WEEKLY)) {
			tblNmVal = B000050Constants.TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD;
			updtQry= entityManager.createNativeQuery(QueryConstants.updtStgCdWkly.toString());
			updtQry.setParameter(B000050Constants.ORDR_TK_BS_WK_NO, ordrTkBsPrd.toString().substring(6, 7));
		}
    	
    	updtQry.setParameter(PDConstants.PRMTR_PORCD, por);
    	updtQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsPrd.toString().substring(0, 6));
		
		try {
			updtQry.executeUpdate();
    		logStsMsg(PDMessageConsants.M00163,PDConstants.UPDATED,tblNmVal);
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			logStsMsg(PDMessageConsants.M00164,PDConstants.UPDATION,tblNmVal);
			B000050CommonUtil.stopBatch();
		} 
		
    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
    
    /**
     * @param mstSchdlRcd
     * @param ordrTkBsMnth
     * @param OrdrTkBsWk
     * @param prcsTy
     * @param stgClsOrdrTkBsMnthDtl
     * @return
     * @throws PdApplicationException
     * 
     * Checks for  record in Logical Pipeline Trn table
     */
    public boolean saveLgclTrnRcds(Object[] mstSchdlRcd, String ordrTkBsMnth, String ordrTkBsWk, String prcsTy, String stgClsOrdrTkBsMnthDtl) throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		boolean isUpdt = false;
		
		Query querySel = entityManager.createNativeQuery(QueryConstants.fetchExtngLgclRcds.toString());
		
		querySel.setParameter(PDConstants.PRMTR_PORCD, CommonUtil.convertObjectToString(mstSchdlRcd[0]));
		querySel.setParameter(B000050Constants.PRDN_ORDR_NO,CommonUtil.convertObjectToString(mstSchdlRcd[5]));
		
		String reslt = CommonUtil.convertObjectToString(querySel.getSingleResult());
		
		LOG.info("reslt value : "+reslt);
		
		if (CommonUtil.stringtoInt(reslt) > 0) {
			isUpdt = true;
			LOG.info("Executing UPDATE statement");
			updateLgclPipLnTrn(mstSchdlRcd,ordrTkBsMnth,ordrTkBsWk,prcsTy,stgClsOrdrTkBsMnthDtl);
		} 
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return isUpdt;
	}
    
    /**
     * @param mstSchdlRcd
     * @param ordrTkBsMnth
     * @param OrdrTkBsWk
     * @param prcsTy
     * @param stgClsOrdrTkBsMnthDtl
     * @throws PdApplicationException
     * 
     * Inserts recored in Logical Pipeline Trn table
     */
    public void insertLgclPipLnTrn(Object[] mstSchdlRcd,String ordrTkBsMnth, String ordrTkBsWk,String prcsTy,String stgClsOrdrTkBsMnthDtl) throws PdApplicationException {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);

		String prdnMnthStr = CommonUtil.convertObjectToString(mstSchdlRcd[1]);
		String prdnWkStr = CommonUtil.convertObjectToString(mstSchdlRcd[2]);
		String msFxdFlgStr = CommonUtil.convertObjectToString(mstSchdlRcd[20]);
		
		String lgckStgCdKy = getLgclStgCdKyDtls(ordrTkBsMnth,ordrTkBsWk,prdnMnthStr,prdnWkStr,prcsTy,msFxdFlgStr,stgClsOrdrTkBsMnthDtl);
		
		Query queryInsert= entityManager.createNativeQuery(QueryConstants.insertLgclPipLnTrn.toString());
		
		queryInsert.setParameter(B000050Constants.VHCL_SEQ_ID,getTrnLgclPplnSeqNo());
		queryInsert.setParameter(PDConstants.PRMTR_PORCD, CommonUtil.convertObjectToString(mstSchdlRcd[0]));
		queryInsert.setParameter(B000050Constants.PRDN_ORDR_NO,CommonUtil.convertObjectToString(mstSchdlRcd[5]));
		queryInsert.setParameter(PDConstants.PRODUCTION_MONTH, prdnMnthStr);
		queryInsert.setParameter(B000050Constants.PRODUCTION_WEEK, prdnWkStr);
		queryInsert.setParameter(PDConstants.PRMTR_UK_OSEI_ID, CommonUtil.convertObjectToString(mstSchdlRcd[3]));
		queryInsert.setParameter(PDConstants.PRMTR_POT_CD, CommonUtil.convertObjectToString(mstSchdlRcd[4]));
		queryInsert.setParameter(B000050Constants.OFFLINE_PLAN_DATE,CommonUtil.convertObjectToString(mstSchdlRcd[6]));
		queryInsert.setParameter(PDConstants.PRMTR_PRODUCTION_PLANT_CD,CommonUtil.convertObjectToString(mstSchdlRcd[7]));
		queryInsert.setParameter(B000050Constants.LINE_CLASS,CommonUtil.convertObjectToString(mstSchdlRcd[8]));
		queryInsert.setParameter(B000050Constants.PRDN_METHOD_CD,CommonUtil.convertObjectToString(mstSchdlRcd[10]));
		queryInsert.setParameter(B000050Constants.FRZN_TYPE_CD,CommonUtil.convertObjectToString(mstSchdlRcd[9]));
		queryInsert.setParameter(B000050Constants.EX_NO,CommonUtil.convertObjectToString(mstSchdlRcd[11]));
		queryInsert.setParameter(B000050Constants.SALES_NOTE_NO,CommonUtil.convertObjectToString(mstSchdlRcd[12]));
		queryInsert.setParameter(B000050Constants.VIN_NO,PDConstants.EMPTY_STRING);
		queryInsert.setParameter(B000050Constants.ORDR_DEL_FLG,PDConstants.CONSTANT_ZERO); 
		queryInsert.setParameter(B000050Constants.MS_FXD_FLG,msFxdFlgStr);
		queryInsert.setParameter(B000050Constants.LGCL_PIPLN_STG_CD,extractPrmtrPipLnStgCd(lgckStgCdKy));
		queryInsert.setParameter(B000050Constants.PROD_DAY_NO,CommonUtil.convertObjectToString(mstSchdlRcd[21]));
		
    	try {
    		queryInsert.executeUpdate();
    		logStsMsg(PDMessageConsants.M00163,PDConstants.INSERTED,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			logStsMsg(PDMessageConsants.M00164,PDConstants.INSERTION,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
			B000050CommonUtil.stopBatch();
		} 
        
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
    
    
    /**
     * @param mstSchdlRcd
     * @param ordrTkBsMnth
     * @param OrdrTkBsWk
     * @param prcsTy
     * @param stgClsOrdrTkBsMnthDtl
     * @throws PdApplicationException
     * 
     * Updates record in Logical Pipeline Trn table
     */
    private void updateLgclPipLnTrn(Object[] mstSchdlRcd,String ordrTkBsMnth, String ordrTkBsWk,String prcsTy, String stgClsOrdrTkBsMnthDtl) throws PdApplicationException {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		String prdnMnthStr = CommonUtil.convertObjectToString(mstSchdlRcd[1]);
		String prdnWkStr = CommonUtil.convertObjectToString(mstSchdlRcd[2]);
		String msFxdFlgStr = CommonUtil.convertObjectToString(mstSchdlRcd[20]);
		
		String lgckStgCdKy = getLgclStgCdKyDtls(ordrTkBsMnth,ordrTkBsWk,prdnMnthStr,prdnWkStr,prcsTy,msFxdFlgStr,stgClsOrdrTkBsMnthDtl);
		
		Query queryUpdate= entityManager.createNativeQuery(QueryConstants.updateLgclPipLnTrn.toString());
		
		queryUpdate.setParameter(PDConstants.PRMTR_PORCD, CommonUtil.convertObjectToString(mstSchdlRcd[0]));
		queryUpdate.setParameter(B000050Constants.PRDN_ORDR_NO,CommonUtil.convertObjectToString(mstSchdlRcd[5]));
		queryUpdate.setParameter(PDConstants.PRODUCTION_MONTH, prdnMnthStr);
		queryUpdate.setParameter(B000050Constants.PRODUCTION_WEEK, prdnWkStr);
		queryUpdate.setParameter(PDConstants.PRMTR_POT_CD, CommonUtil.convertObjectToString(mstSchdlRcd[4]));
		queryUpdate.setParameter(B000050Constants.FRZN_TYPE_CD,CommonUtil.convertObjectToString(mstSchdlRcd[9]));
		queryUpdate.setParameter(B000050Constants.MS_FXD_FLG,msFxdFlgStr);
		queryUpdate.setParameter(B000050Constants.OFFLINE_PLAN_DATE,CommonUtil.convertObjectToString(mstSchdlRcd[6]));
		queryUpdate.setParameter(PDConstants.PRMTR_PRODUCTION_PLANT_CD,CommonUtil.convertObjectToString(mstSchdlRcd[7]));
		queryUpdate.setParameter(B000050Constants.PRDN_METHOD_CD,CommonUtil.convertObjectToString(mstSchdlRcd[10]));
		queryUpdate.setParameter(B000050Constants.EX_NO,CommonUtil.convertObjectToString(mstSchdlRcd[11]));
		queryUpdate.setParameter(B000050Constants.SALES_NOTE_NO,CommonUtil.convertObjectToString(mstSchdlRcd[12]));
		queryUpdate.setParameter(PDConstants.PRMTR_UK_OSEI_ID, CommonUtil.convertObjectToString(mstSchdlRcd[3]));
		queryUpdate.setParameter(B000050Constants.LINE_CLASS,CommonUtil.convertObjectToString(mstSchdlRcd[8]));
		queryUpdate.setParameter(B000050Constants.LGCL_PIPLN_STG_CD,extractPrmtrPipLnStgCd(lgckStgCdKy));
		queryUpdate.setParameter(B000050Constants.PROD_DAY_NO,CommonUtil.convertObjectToString(mstSchdlRcd[21]));
		
		try {
			queryUpdate.executeUpdate();
    		logStsMsg(PDMessageConsants.M00163,PDConstants.UPDATED,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			logStsMsg(PDMessageConsants.M00164,PDConstants.UPDATION,B000050Constants.TBL_NM_LOGICAL_PIP_LN_TRN);
			B000050CommonUtil.stopBatch();
		} 
        
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

    /**
     * @param ordrTkBsMnth
     * @param ordrTkBsWk
     * @param prdnMnth
     * @param prdnWk
     * @param prcsTy
     * @param msFxd
     * @param stgClsOrdrTkBsMnthDtl
     * @return Logical stage code key
     */
    private String getLgclStgCdKyDtls(String ordrTkBsMnth, String ordrTkBsWk,String prdnMnth, String prdnWk, String prcsTy, String msFxd, String stgClsOrdrTkBsMnthDtl) {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	
    	String stgCdKy = null;
    	
    	if(prdnMnth.compareTo(ordrTkBsMnth) == 0) {
    		if (prcsTy.equalsIgnoreCase(PDConstants.MONTHLY)) {
    			stgCdKy = B000050Constants.N_PRDSCDL_MS2;
    		} else if (prcsTy.equalsIgnoreCase(PDConstants.WEEKLY)) {
    			if((ordrTkBsWk.compareTo(prdnWk) == 0) || (msFxd.equalsIgnoreCase(PDConstants.CONSTANT_ONE))) {
    				stgCdKy = B000050Constants.N_PRDSCDL_MS3_FXD;
    			} else
    				stgCdKy = B000050Constants.N_PRDSCDL_MS2_NONFXD;
    		}
    	}else if(prdnMnth.compareTo(ordrTkBsMnth) >= 1) {
    		if(stgClsOrdrTkBsMnthDtl.compareTo(prdnMnth) == 0){
    			stgCdKy = B000050Constants.N_PRDSCDL_MS2;
    		}else
    			stgCdKy = B000050Constants.N_1_FORECAST;
    	}
    	
    	LOG.info("Logical Stage Code Key is :"+stgCdKy);

    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return stgCdKy;
	}

	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to get the logical stage code value. 
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrPipLnStgCd(String key) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
			String lgclStgCdVal = prmtrMstRepo.fetchValue1(B000050Constants.LOGICAL_STAGE_CD, key," ");
			
			if(lgclStgCdVal!= null && !lgclStgCdVal.isEmpty())
			 LOG.info("Extracted Logical Stage Code value is  : " +lgclStgCdVal);
			else{
				String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,B000050Constants.LOGICAL_STAGE_CD,PDConstants.MESSAGE_MST_PARAMETER,key};
	        	B000050CommonUtil.logMessage(PDMessageConsants.M00190, B000050Constants.P0004_1, messageParams);
			}
			
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return lgclStgCdVal;
	}
    
    
    /**
	 * This method is used to get the Logical pipeline trn Latest Sequence Id.
	 * 
	 * @return String Seq no.
	 */
	public String getTrnLgclPplnSeqNo() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);

		Query lgclPipLnSqId = entityManager.createNativeQuery(QueryConstants.TRN_LGCL_PIPELN_VHCL_SEQ_ID.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = lgclPipLnSqId.getResultList();
		
		String seqNo = CommonUtil.bigDecimaltoString(result.get(0));
		if (result.isEmpty()) {
			return null;
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return seqNo;
	}
	
	/**
	 * @param porCd
	 * @param stgCd
	 * @param prcsTyp
	 * @return Order take base month Extraction Record  
	 */
	public String extractOrdrTkBsMnthDtl(String porCd,String stgCd, String prcsTyp) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String ordrTkBsMnthRdrQryStr = null;
		
		if (prcsTyp.equalsIgnoreCase(PDConstants.MONTHLY)) {
			ordrTkBsMnthRdrQryStr = fetchMnthlyOrdrTkBs(porCd,stgCd);
		} 
		else if (prcsTyp.equalsIgnoreCase(PDConstants.WEEKLY)) {
			ordrTkBsMnthRdrQryStr = fetchWklyOrdrTkBs(porCd);
		} 
		
		Query ordrTkBsMnthRdrQry = entityManager.createQuery(ordrTkBsMnthRdrQryStr);
		
		Object results = ordrTkBsMnthRdrQry.getSingleResult();
		
		String ordrTkBsMnthVal = CommonUtil.convertObjectToString(results);
		
		LOG.info("Order Take Base Month Extraction Result: "+ordrTkBsMnthVal);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ordrTkBsMnthVal;
	}
	
	
	/**
	 * @param porCd
	 * @return Stage close Order take base month
	 */
	public String extractClsOrdrTkBsMnthDtl(String porCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		Query clsOTBMQry = entityManager.createNativeQuery(QueryConstants.getOrdrTkBsMnthCls.toString());
		
		clsOTBMQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		
		Object results = clsOTBMQry.getSingleResult();
		
		String stgClsOTBM = CommonUtil.convertObjectToString(results);
		
		LOG.info("Close Order Take Base Month Extraction Record Value : "+stgClsOTBM);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return stgClsOTBM;
	}
	
	
	/**
	 * @param porCd
	 * @param stgCd
	 * @return Monthly Extraction Query String
	 */
public String fetchMnthlyOrdrTkBs(String porCd, String stgCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(QueryConstants.getMnthlySelClause)
 		.append(QueryConstants.getOrdrTkBsMnthMnthlyMain)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(porCd)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(QueryConstants.getOrdrTkBsMnthMnthlyWhr)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(stgCd)
 		.append(PDConstants.SINGLE_QUOTE);
 		
 		LOG.info("Monthly Extraction Query String : "+ queryString);
 		
 		return queryString.toString();
 	}

/**
 * @param porCd
 * @return Weekly Extraction Query String
 */
public String fetchWklyOrdrTkBs(String porCd){
 		
 		StringBuilder queryString = new StringBuilder()
 		.append(QueryConstants.getWklySelClause)
 		.append(QueryConstants.getOrdrTkBsMnthWklyMain)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(porCd)
 		.append(PDConstants.SINGLE_QUOTE)
 		.append(QueryConstants.getOrdrTkBsMnthWklyWhr);
 		
 		LOG.info("Weekly Extraction Query String : "+ queryString);
 		
 		return queryString.toString();
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
