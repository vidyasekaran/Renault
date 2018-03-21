/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PATTERN_MATCHING_RESULT_MSG;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.process.SlsNoteCalProcess;
import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.b000027.util.QueryConstants;
import com.nissangroups.pd.model.DevTrnMnthlyProdOrdr;
import com.nissangroups.pd.model.DevTrnMnthlyProdOrdrPK;
import com.nissangroups.pd.model.TrnMnthlyProdOrdr;
import com.nissangroups.pd.model.TrnMnthlyProdOrdrPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * @author z014433
 * 
 * Repository class for B000027
 *
 */
public class MnthlyPrdnOrdrTrnRepository {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MnthlyPrdnOrdrTrnRepository.class);
	
	  /** Variable existing production order details */
		List<Object[]> exstngPrdnOrdsLst = new ArrayList<Object[]>();
		
		List<Object[]> mnthlyOrdrDtlsRcdsLst = new ArrayList<Object[]>();
		
		List<TrnMnthlyProdOrdr> prdnOrdrDtlsLst = new ArrayList<TrnMnthlyProdOrdr>();
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable list Common Util. */
    private CommonUtil commonUtil;
    
public MnthlyPrdnOrdrTrnRepository(){
		
	}
    
    /**
     * @param porCd
     * @param ordrTkBsMnth
     * @return car series horizon details
     * 
     * This method is used to extract the car series horizon details based on por cd and order take base month from por car series mst table
     */
    public List<Object[]> chkHrzn(String porCd, String ordrTkBsMnth) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	
		LOG.info("POR is : "+porCd+" , Order take base Month is " +ordrTkBsMnth);
    	
		Query carSrsQuery = entityManager.createQuery(QueryConstants.getCarSeriesHorizon.toString());
		
		carSrsQuery.setParameter(PDConstants.PRMTR_PORCD, porCd);
		carSrsQuery.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		
		LOG.info("Car series Details Extraction Query String is : "+ carSrsQuery);
		
		List<Object[]> results = carSrsQuery.getResultList();
		
		if (results.isEmpty()) {
			String[] messageParams = {B000027Constants.BATCH_27_ID,porCd,PDConstants.POR_CAR_SERIES_MST};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00146, B000027Constants.P0003_1, messageParams);
			B000027CommonUtil.stopBatch();
		}
		return results;
	}
    
    
    /**
     * @param jobParamPor
     * @param ordrQtyVal
     * @param stgCdVal
     * @param item
     * @param carSrsObj
     * @param prmtrOrdrHrznVal
     * @param sndSuspPrmtrVal
     * * @param prmtrExNoVal
     * @param prmtrSrvcPrmtrVal
     * @return order details
     * 
     * This method is used to extract the order details from monthly order trn table
     */
    public List<Object[]> extractOrdrDtls(String jobParamPor, String ordrQtyVal,String stgCdVal,OrdrTkBsPrdMstRowMapper item, Entry<Object, List<String>> carSrsObj, 
    		String prmtrOrdrHrznVal, String sndSuspPrmtrVal) {
    	LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
    	
    	LOG.info("Order Quantity Flag  is : "+ordrQtyVal+" , Send suspend order parameter value is " +sndSuspPrmtrVal);
		
		String extractOrdrDtlsQryStr = null;
		String maximumPrdnMnth = null;
		String ordrTkBsMnth = item.getId().getORDR_TAKE_BASE_MNTH();
		
		if(ordrQtyVal.equalsIgnoreCase(PDConstants.YES))
			extractOrdrDtlsQryStr = QueryConstants.EXT_MNTHLY_TRN_SEL_ORDR_QTY_Y.toString();
		else if(ordrQtyVal.equalsIgnoreCase(PDConstants.NO))
			extractOrdrDtlsQryStr = QueryConstants.EXT_MNTHLY_TRN_SEL_ORDR_QTY_N.toString();
		
		extractOrdrDtlsQryStr = extractOrdrDtlsQryStr+QueryConstants.EXT_MNTHLY_TRN_MAIN_PART.toString();
		
		if(sndSuspPrmtrVal.equalsIgnoreCase(PDConstants.YES))
			extractOrdrDtlsQryStr = extractOrdrDtlsQryStr+QueryConstants.EXT_MNTHLY_TRN_WHR_CLAUSE_Y.toString();
		 else if(sndSuspPrmtrVal.equalsIgnoreCase(PDConstants.NO)) 
			extractOrdrDtlsQryStr = extractOrdrDtlsQryStr+QueryConstants.EXT_MNTHLY_TRN_WHR_CLAUSE_N.toString();
		
		extractOrdrDtlsQryStr = extractOrdrDtlsQryStr+QueryConstants.EXT_MNTHLY_TRN_WHR_CLAUSE.toString();
		
		Query extractOrdrDtlsQry = entityManager.createNativeQuery(extractOrdrDtlsQryStr);
		
		try {
			maximumPrdnMnth = CommonUtil.prdMnthCal(ordrTkBsMnth, prmtrOrdrHrznVal);
		} catch (ParseException e) {
			LOG.error(e.toString());
			B000027CommonUtil.stopBatch();
		}
		if(sndSuspPrmtrVal.equalsIgnoreCase(PDConstants.NO)) {
			extractOrdrDtlsQry.setParameter(PDConstants.PRMTR_CARSRS, carSrsObj.getKey());
			extractOrdrDtlsQry.setParameter(PDConstants.PRODUCTION_MONTH, carSrsObj.getValue());
		}
		extractOrdrDtlsQry.setParameter(PDConstants.MAX_PROD_MONTH, maximumPrdnMnth);
		extractOrdrDtlsQry.setParameter(PDConstants.PRMTR_PRD_ORDR_STG_CD, CommonUtil.getPrdOrdrStgCd(stgCdVal)); 
		extractOrdrDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		extractOrdrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, jobParamPor);
		
		LOG.info("Monthly Order Trn Details Extraction Query String is : "+ extractOrdrDtlsQry);
		
		List<Object[]> results = extractOrdrDtlsQry.getResultList();
		
		if (results.isEmpty()) {
			String[] messageParams = {B000027Constants.BATCH_27_ID,ordrTkBsMnth,jobParamPor};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00339, B000027Constants.P0004, messageParams);
		} 
		LOG.info("Monthly Order Trn Details Extraction Query Result for car series : --> "+ carSrsObj.getKey()+" is "+results.size());

    	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
    	return results;
	} 
	

    public Map<String, List> saveMnthlyOrdrDtls(List<Object[]> mnthlyOrdrTrnDtls,String prmtrExNoVal, String prmtrSrvcPrmtrVal, String prdOrdrReuseFlg) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String ordrQtyVal = null;
		List<Object[]> mnthlyOrdrObjRcd = new ArrayList<Object[]>();
		Map<String, List> resultMap = new HashMap<String, List>();
		
		for(Object[] mnthlyOrdrObj : mnthlyOrdrTrnDtls) {
			if(mnthlyOrdrObj[14]!=null) {
			 ordrQtyVal = mnthlyOrdrObj[14].toString();
			 if(!ordrQtyVal.equalsIgnoreCase(PDConstants.CONSTANT_ZERO)) {
				 mnthlyOrdrObjRcd = populateBeanValues(mnthlyOrdrObj, prmtrExNoVal, prmtrSrvcPrmtrVal,prdOrdrReuseFlg);
				 mnthlyOrdrDtlsRcdsLst.addAll(mnthlyOrdrObjRcd);
			 }
			}
		}
		
		resultMap.put(B000027Constants.SRVC_ERR_RPT_MAP_VAL, mnthlyOrdrDtlsRcdsLst);
		resultMap.put(B000027Constants.MNTHLY_PRDN_TRN_VAL, prdnOrdrDtlsLst);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return resultMap;
		}


	/**
	 * @param mnthlyOrdrObj
	 * @param prmtrExNoVal
	 * @param prmtrSrvcPrmtrVal
	 * @param prdOrdrReuseFlg
	 * @return bean for Monthly Production Order Trn table
	 */
	private List<Object[]> populateBeanValues(Object[] mnthlyOrdrObj,String prmtrExNoVal, String prmtrSrvcPrmtrVal, String prdOrdrReuseFlg) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		TrnMnthlyProdOrdr prdnOrdrDtls = new TrnMnthlyProdOrdr();
		TrnMnthlyProdOrdrPK prdnOrdrDtlsPK =  new TrnMnthlyProdOrdrPK();
		
		commonUtil = new CommonUtil();
		BigDecimal ordrQty = CommonUtil.convertStringToBigDecimal(mnthlyOrdrObj[14].toString());
		int ordrDtls = CommonUtil.bigDecimaltoInt(ordrQty);
		List<Object[]> mnthlyOrdrObjSrvcErrRptRcdsLst = new ArrayList<Object[]>();
		Object[] mnthlyOrdrObjSrvcErrRptRcds = null;
		
		String ordrTkBsMnthStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[11]);
		String potCdStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[6]);
		String addSpecCdStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[2]);
		String porCdStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[7]);
		String prdnMnthStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[0]);
		String byrCdStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[1]);
		String eimStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[13]);
		String ukOseiIdStr = CommonUtil.convertObjectToString(mnthlyOrdrObj[10]);
		
		SlsNoteCalProcess attachSlsNt = new SlsNoteCalProcess();
		String calSlsNoteNo = attachSlsNt.calcSlsNoteNum(prdnMnthStr, potCdStr);
		
		exstngPrdnOrdsLst = extractExtngPrdnOrdrNos(porCdStr,ordrTkBsMnthStr);
		LOG.info("Extracted existing production order details list size is : " +exstngPrdnOrdsLst.size());
		
		for (int i=0;i < ordrDtls;i++){
		
		LOG.info("Iteration for Monthly Order Trn Qty Record Number :  "+i);	
		
		prdnOrdrDtlsPK.setOrdrtkBaseMnth(ordrTkBsMnthStr);
		prdnOrdrDtlsPK.setOseiId(ukOseiIdStr);
		prdnOrdrDtlsPK.setPorCd(porCdStr);
		prdnOrdrDtlsPK.setPotCd(potCdStr);
		prdnOrdrDtlsPK.setProdMnth(prdnMnthStr);
		
		LOG.info("Monthly Order Trn Details : UKOSEIId is "+ukOseiIdStr+" Prdn Mnth is : "+prdnMnthStr+" POT Cd is : "+potCdStr);
		
		boolean generateNewPrdnOrdrNo = false;
		
		/** Process Id - P0008 */
		for(Object[] extngPrdnOrd: exstngPrdnOrdsLst){
			LOG.info("Existing Prdn Order Details : UKOSEIId is "+extngPrdnOrd[2]+" Prdn Mnth is : "+extngPrdnOrd[3]+" POT Cd is : "+extngPrdnOrd[4]+" Sales note number is : "+extngPrdnOrd[5]);
			if (
					ukOseiIdStr.equalsIgnoreCase(extngPrdnOrd[2].toString()) &&
					prdnMnthStr.equalsIgnoreCase(extngPrdnOrd[3].toString()) &&
					potCdStr.equalsIgnoreCase((String) extngPrdnOrd[4].toString()) && 
					porCdStr.equalsIgnoreCase(extngPrdnOrd[0].toString())
					) {
				if(prdOrdrReuseFlg.equalsIgnoreCase(PDConstants.YES)) {
				LOG.info("MATCHES, Attaching Existing Production number to the order : "+extngPrdnOrd[1].toString());
				prdnOrdrDtlsPK.setProdOrdrNo(extngPrdnOrd[1].toString()); 
				} else if(prdOrdrReuseFlg.equalsIgnoreCase(PDConstants.NO)) {
					LOG.info("MATCHING, REUSE FLAG is NO, Generating new Production order number");
					generateNewPrdnOrdrNo = true;
					
				}
			
				/** Process Id - P0009 */
				if (extngPrdnOrd[5]!=null && extngPrdnOrd[5].toString().equalsIgnoreCase(calSlsNoteNo)){
					LOG.info("Order already has sales note number, hence assigning the same");
					prdnOrdrDtls.setSlsNoteNo(extngPrdnOrd[5].toString());
				} else
				{
					LOG.info("Order matches but doesn't have sales note number, hence attaching new Sales note number");
					prdnOrdrDtls.setSlsNoteNo(calSlsNoteNo);
				}
			}
		 else{
			LOG.info("NOT MATCHING, Generating new Production order number");
			generateNewPrdnOrdrNo = true;
			
			LOG.info("NOT MATCHING, attaching new Sales note number");
			prdnOrdrDtls.setSlsNoteNo(calSlsNoteNo);
		}
		}
		if(generateNewPrdnOrdrNo){
			String newPrdnOrdrNo = generatePrdnOrdrNo(prdnMnthStr,porCdStr);
			LOG.info("New Production Order number is : "+newPrdnOrdrNo);
			prdnOrdrDtlsPK.setProdOrdrNo(newPrdnOrdrNo);
		}
		prdnOrdrDtls.setId(prdnOrdrDtlsPK);
		
		prdnOrdrDtls.setOrdrQty(new BigDecimal(PDConstants.INT_ONE));
		
		/** Process Id - P0010.1 */
		if(prmtrExNoVal.equalsIgnoreCase(PDConstants.Y)){
			String exNoVal = extractExportNum(porCdStr,prdnMnthStr,CommonUtil.convertObjectToString(mnthlyOrdrObj[8]),potCdStr);
			prdnOrdrDtls.setExNo(exNoVal);
		}
	
		/** Process Id - P0010.2 */
		String mapsSymblVal = extractMapsSymbol(byrCdStr,porCdStr);
		
		/** Process Id - P0010.3 */
		if(prmtrSrvcPrmtrVal.equalsIgnoreCase(PDConstants.Y)){
		Object[] srvcPrmtrDtls = extractSrvcPrmtrDtls(porCdStr,CommonUtil.convertObjectToString(mnthlyOrdrObj[9]),byrCdStr,prdnMnthStr,eimStr,CommonUtil.convertObjectToString(mnthlyOrdrObj[5]));
		prdnOrdrDtls.setDealerLst(srvcPrmtrDtls[0].toString());
		prdnOrdrDtls.setOwnrMnl(srvcPrmtrDtls[1].toString());
		prdnOrdrDtls.setWrntyBklt(srvcPrmtrDtls[2].toString());
		/**PST-DRG-R000016 - Service Error Report **/
		LOG.info("Service Parameter Values are :"+srvcPrmtrDtls[0]+srvcPrmtrDtls[1]+srvcPrmtrDtls[2]);
		String srvcPrmVal = srvcPrmtrDtls[0].toString()+srvcPrmtrDtls[1].toString()+srvcPrmtrDtls[2].toString();
		if(srvcPrmVal.equalsIgnoreCase(B000027Constants.SERV_PRMTR_MMM_VAL)){
			mnthlyOrdrObjSrvcErrRptRcds = new Object[]{ mnthlyOrdrObj[11], mnthlyOrdrObj[0],mnthlyOrdrObj[9],mnthlyOrdrObj[1],mnthlyOrdrObj[5],mnthlyOrdrObj[13],mnthlyOrdrObj[2],prdnOrdrDtls.getSlsNoteNo(),prdnOrdrDtls.getExNo(),mnthlyOrdrObj[14]};
			mnthlyOrdrObjSrvcErrRptRcdsLst.add(mnthlyOrdrObjSrvcErrRptRcds); 
		}
		}
		
		/** Process Id - P0010.4 */
		String tyreMkrVal = extractTyreMkrCd(addSpecCdStr);
		
		/** Process Id - P0010.5 */
		String bdyPrtnCdVal = extractBdyPrtnrCd(addSpecCdStr);
		
		prdnOrdrDtls.setOcfRegionCd(mapsSymblVal); 
		prdnOrdrDtls.setTyreMkrCd(tyreMkrVal);
		prdnOrdrDtls.setBdyPrtctnCd(bdyPrtnCdVal);
		
		prdnOrdrDtls.setCrtdBy(B000027Constants.BATCH_27_ID); 
		prdnOrdrDtls.setUpdtdBy(B000027Constants.BATCH_27_ID);
	/*	try {
			entityManager.merge(prdnOrdrDtls);
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					ordrTkBsMnthStr,
					porCdStr, PDConstants.STEP_SUCCESS});
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e.getMessage());
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					ordrTkBsMnthStr,
					porCdStr, PDConstants.FAILED}); 
				B000027CommonUtil.stopBatch();
		} */
		prdnOrdrDtlsLst.add(prdnOrdrDtls);
		savePrdnOrdrDtlsAsDraft(prdnOrdrDtls,byrCdStr,eimStr.substring(0, 13),eimStr.substring(13, 18),mnthlyOrdrObj[5].toString(),mnthlyOrdrObj[3].toString(), 
				mnthlyOrdrObj[4].toString(),addSpecCdStr,mnthlyOrdrObj[15].toString(),mnthlyOrdrObj[8].toString(),mnthlyOrdrObj[12].toString(),mnthlyOrdrObj[9].toString(),mnthlyOrdrObj[16].toString()); 
		}
		
		LOG.info("Service Error Report Count with duplicates :" +mnthlyOrdrObjSrvcErrRptRcdsLst.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return mnthlyOrdrObjSrvcErrRptRcdsLst;

	}
	
	/** Process Id - P0011 */
	/**
	 * @param prdnOrdrDtls
	 */
	public void savePrdnOrdrDtls(TrnMnthlyProdOrdr prdnOrdrDtls) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		Query querySelect = entityManager.createNativeQuery(QueryConstants.fetchExtngRcds.toString());
		
		querySelect.setParameter(PDConstants.PRMTR_PORCD, prdnOrdrDtls.getId().getPorCd());
        querySelect.setParameter(PDConstants.ORDR_TK_BS_MNTH, prdnOrdrDtls.getId().getOrdrtkBaseMnth());
        querySelect.setParameter(PDConstants.PRODUCTION_MONTH, prdnOrdrDtls.getId().getProdMnth());
        querySelect.setParameter(PDConstants.PRMTR_UK_OSEI_ID, prdnOrdrDtls.getId().getOseiId());
        querySelect.setParameter(PDConstants.PRMTR_POT_CD, prdnOrdrDtls.getId().getPotCd());
        querySelect.setParameter(B000027Constants.PRDN_ORDR_NO,prdnOrdrDtls.getId().getProdOrdrNo());
		
		List<Object[]> resultList = querySelect.getResultList();
		
		if (!resultList.isEmpty()) {
			LOG.info("Executing UPDATE statement");
			updateMnthlyPrdnOrdrTrn(prdnOrdrDtls);
		} else {
			LOG.info("Executing INSERT statement");
			insertMnthlyPrdnOrdrTrn(prdnOrdrDtls);
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * @param prdnOrdrDtls
	 * 
	 * Updates records in Monthly Production order trn table
	 */
	private void updateMnthlyPrdnOrdrTrn(TrnMnthlyProdOrdr prdnOrdrDtls) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		commonUtil = new CommonUtil();
		
		Query queryUpdate= entityManager.createNativeQuery(QueryConstants.updateMnthlyPrdnOrdr.toString());
		
		queryUpdate.setParameter(PDConstants.PRMTR_PORCD, prdnOrdrDtls.getId().getPorCd());
		queryUpdate.setParameter(PDConstants.ORDR_TK_BS_MNTH, prdnOrdrDtls.getId().getOrdrtkBaseMnth());
		queryUpdate.setParameter(PDConstants.PRODUCTION_MONTH, prdnOrdrDtls.getId().getProdMnth());
		queryUpdate.setParameter(PDConstants.PRMTR_UK_OSEI_ID, prdnOrdrDtls.getId().getOseiId());
		queryUpdate.setParameter(PDConstants.PRMTR_POT_CD, prdnOrdrDtls.getId().getPotCd());
		queryUpdate.setParameter(B000027Constants.PRDN_ORDR_NO,prdnOrdrDtls.getId().getProdOrdrNo());
		
		queryUpdate.setParameter(PDConstants.PRMTR_ORDR_QTY,prdnOrdrDtls.getOrdrQty());
		queryUpdate.setParameter(B000027Constants.EX_NO,prdnOrdrDtls.getExNo());
		queryUpdate.setParameter(B000027Constants.SALES_NOTE_NO,prdnOrdrDtls.getSlsNoteNo());
		queryUpdate.setParameter(B000027Constants.TYRE_MKR,prdnOrdrDtls.getTyreMkrCd());
		queryUpdate.setParameter(B000027Constants.DEALER_LST,prdnOrdrDtls.getDealerLst());
		queryUpdate.setParameter(B000027Constants.OWNR_MNUL,prdnOrdrDtls.getOwnrMnl());
		queryUpdate.setParameter(B000027Constants.WRNTY_BLKT,prdnOrdrDtls.getWrntyBklt());
		queryUpdate.setParameter(B000027Constants.BDY_PRTN_CD,prdnOrdrDtls.getBdyPrtctnCd());
		queryUpdate.setParameter(PDConstants.PRMTR_OCFRGNCD,prdnOrdrDtls.getOcfRegionCd());
		
    	try {
    		queryUpdate.executeUpdate();
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					prdnOrdrDtls.getId().getOrdrtkBaseMnth(),
					prdnOrdrDtls.getId().getPorCd(), PDConstants.STEP_SUCCESS});
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					prdnOrdrDtls.getId().getOrdrtkBaseMnth(),
					prdnOrdrDtls.getId().getPorCd(), PDConstants.FAILED}); 
				B000027CommonUtil.stopBatch();
		} 
        
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}


	/**
	 * @param prdnOrdrDtls
	 * 
	 * Inserts records to Monthly Production order trn table
	 */
	private void insertMnthlyPrdnOrdrTrn(TrnMnthlyProdOrdr prdnOrdrDtls) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		commonUtil = new CommonUtil();
		
		Query queryInsert= entityManager.createNativeQuery(QueryConstants.insertMnthlyPrdnOrdr.toString());
		
		queryInsert.setParameter(PDConstants.PRMTR_PORCD, prdnOrdrDtls.getId().getPorCd());
		queryInsert.setParameter(PDConstants.ORDR_TK_BS_MNTH, prdnOrdrDtls.getId().getOrdrtkBaseMnth());
		queryInsert.setParameter(PDConstants.PRODUCTION_MONTH, prdnOrdrDtls.getId().getProdMnth());
		queryInsert.setParameter(PDConstants.PRMTR_UK_OSEI_ID, prdnOrdrDtls.getId().getOseiId());
		queryInsert.setParameter(PDConstants.PRMTR_POT_CD, prdnOrdrDtls.getId().getPotCd());
		queryInsert.setParameter(B000027Constants.PRDN_ORDR_NO,prdnOrdrDtls.getId().getProdOrdrNo());
		
		queryInsert.setParameter(PDConstants.PRMTR_ORDR_QTY,prdnOrdrDtls.getOrdrQty());
		queryInsert.setParameter(B000027Constants.EX_NO,prdnOrdrDtls.getExNo());
		queryInsert.setParameter(B000027Constants.SALES_NOTE_NO,prdnOrdrDtls.getSlsNoteNo());
		queryInsert.setParameter(B000027Constants.TYRE_MKR,prdnOrdrDtls.getTyreMkrCd());
		queryInsert.setParameter(B000027Constants.DEALER_LST,prdnOrdrDtls.getDealerLst());
		queryInsert.setParameter(B000027Constants.OWNR_MNUL,prdnOrdrDtls.getOwnrMnl());
		queryInsert.setParameter(B000027Constants.WRNTY_BLKT,prdnOrdrDtls.getWrntyBklt());
		queryInsert.setParameter(B000027Constants.BDY_PRTN_CD,prdnOrdrDtls.getBdyPrtctnCd());
		queryInsert.setParameter(PDConstants.PRMTR_OCFRGNCD,prdnOrdrDtls.getOcfRegionCd());
		
    	try {
    		queryInsert.executeUpdate();
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					prdnOrdrDtls.getId().getOrdrtkBaseMnth(),
					prdnOrdrDtls.getId().getPorCd(), PDConstants.STEP_SUCCESS});
		} catch (Exception e) {
			LOG.error(PDConstants.EXCEPTION+e);
			CommonUtil.logMessage(PDMessageConsants.M00177, 
					PDConstants.CONSTANT_V4, new String[] {
					B000027Constants.BATCH_27_ID,
					prdnOrdrDtls.getId().getOrdrtkBaseMnth(),
					prdnOrdrDtls.getId().getPorCd(), PDConstants.FAILED}); 
				B000027CommonUtil.stopBatch();
		} 
        
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}


	/**
	 * @param prdnOrdrDtls
	 * @param byrCdStr
	 * @param appldMdlCd
	 * @param pckCd
	 * @param specDestCd
	 * @param extClr
	 * @param intClr
	 * @param addSpecCdStr
	 * @param byrGrpCd
	 * @param oeiByrId
	 * @param crSrs
	 * @param prdFmlyCd
	 * @param OcfByrGrpCd
	 * 
	 * Saves information in DEV Monthly Production Order Trn (temp) table
	 */
	private void savePrdnOrdrDtlsAsDraft(TrnMnthlyProdOrdr prdnOrdrDtls, String byrCdStr, String appldMdlCd, String pckCd, String specDestCd, String extClr, 
			String intClr, String addSpecCdStr, String byrGrpCd, String oeiByrId, String crSrs, String prdFmlyCd, String OcfByrGrpCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		DevTrnMnthlyProdOrdr devB000027 = new DevTrnMnthlyProdOrdr();
		DevTrnMnthlyProdOrdrPK devB000027PK = new DevTrnMnthlyProdOrdrPK();
		commonUtil = new CommonUtil();
		
		devB000027PK.setOrdrtkBaseMnth(prdnOrdrDtls.getId().getOrdrtkBaseMnth());
		devB000027PK.setOseiId(prdnOrdrDtls.getId().getOseiId());
		devB000027PK.setPorCd(prdnOrdrDtls.getId().getPorCd());
		devB000027PK.setPotCd(prdnOrdrDtls.getId().getPotCd());
		devB000027PK.setProdMnth(prdnOrdrDtls.getId().getProdMnth());
		devB000027PK.setProdOrdrNo(prdnOrdrDtls.getId().getProdOrdrNo()); 
		devB000027.setId(devB000027PK);
		
		devB000027.setOrdrQty(new BigDecimal(PDConstants.INT_ONE));
		devB000027.setSlsNoteNo(prdnOrdrDtls.getSlsNoteNo());
		devB000027.setExNo(prdnOrdrDtls.getExNo());
		devB000027.setTyreMkrCd(prdnOrdrDtls.getTyreMkrCd());
		devB000027.setBdyPrtctnCd(prdnOrdrDtls.getBdyPrtctnCd());
		devB000027.setDealerLst(prdnOrdrDtls.getDealerLst());
		devB000027.setOwnrMnl(prdnOrdrDtls.getOwnrMnl());
		devB000027.setWrntyBklt(prdnOrdrDtls.getWrntyBklt());
		devB000027.setOcfRegionCd(prdnOrdrDtls.getOcfRegionCd()); 
		
		devB000027.setBuyerCd(byrCdStr);
		devB000027.setOeiBuyerId(oeiByrId);
		devB000027.setExtClrCd(extClr);
		devB000027.setIntClrCd(intClr);
		devB000027.setProdFmyCd(prdFmlyCd);
		devB000027.setAppldMdlCd(appldMdlCd);
		devB000027.setPckCd(pckCd);
		devB000027.setSpecDestnCd(specDestCd);
		devB000027.setAdtnlSpecCd(addSpecCdStr);
		devB000027.setCarSrs(crSrs);
		devB000027.setBuyerGrpCd(byrGrpCd);
		devB000027.setOcfBuyerGrpCd(OcfByrGrpCd);
		
		devB000027.setCrtdBy(B000027Constants.BATCH_27_ID);
		devB000027.setCrtdDt(commonUtil.currentDateTime());
		devB000027.setUpdtdBy(B000027Constants.BATCH_27_ID);
		devB000027.setUpdtdDt(commonUtil.currentDateTime());
		entityManager.merge(devB000027);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}


	/**
	 * @param porCd
	 * @param prdFmlyCd
	 * @param byrCd
	 * @param prdnMnth
	 * @param eim
	 * @param specDestCd
	 * @return service parameter details
	 */
	private Object[] extractSrvcPrmtrDtls(String porCd,String prdFmlyCd, String byrCd, String prdnMnth, String eim, String specDestCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		List<Object[]> srvcPrmtrDtls = new ArrayList<Object[]>();
		Object[] srvcPrmtrObj = null;
		int lowPrty = 0;
		
    	LOG.info("POR Cd  is : "+porCd+ ", production family cd is : "+prdFmlyCd+ ", Buyer cd is : "+byrCd+", production month is : "+prdnMnth+ ", EIM is : "+eim+ ", Spec Destination Code is : "+specDestCd);

    	Query getSrvcPrmtrDtlsQry = entityManager.createNativeQuery(QueryConstants.getSrvcPrmtrDtls.toString());
    	
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE, prdFmlyCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_BUYERCD, byrCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdnMnth);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PREFIX, eim.substring(0, 7));
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.SUFFIX, eim.substring(10, 18));
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.SPECDESTCD, specDestCd);
		
		LOG.info("Service Parameter Details Extraction Query String is : "+ getSrvcPrmtrDtlsQry);

		srvcPrmtrDtls = getSrvcPrmtrDtlsQry.getResultList();
		
		LOG.info(PATTERN_MATCHING_RESULT_MSG + srvcPrmtrDtls.size());
		
		if(srvcPrmtrDtls == null || srvcPrmtrDtls.isEmpty())
    	{
			LOG.info("No Matching record found, hence attaching constant value as 'M' ");
			srvcPrmtrObj = new Object[]{ PDConstants.CF_CONSTANT_M, PDConstants.CF_CONSTANT_M, PDConstants.CF_CONSTANT_M };
    	} else if (srvcPrmtrDtls.size() ==1) {
    		for (Object[] result : srvcPrmtrDtls) {
    			srvcPrmtrObj = new Object[]{ result[1], result[2], result[3] };
    		}
    	}else if (srvcPrmtrDtls.size() > 1){
    		for (Object[] result : srvcPrmtrDtls) {
    			if((byrCd.length() == PDConstants.INT_SIX) && (specDestCd!= null && !specDestCd.isEmpty())){
    				result[4] = result[4].toString()+PDConstants.CONSTANT_ONE;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			} else if((byrCd.length() == B000027Constants.INT_FOUR) && (specDestCd!= null && !specDestCd.isEmpty())){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_TWO;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			} if((byrCd.length() == PDConstants.INT_SIX) && (specDestCd== null)){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_THREE;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			}else if((byrCd.length() == B000027Constants.INT_FOUR) && (specDestCd== null)){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_FOUR;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			}
    		}
    		
    		LOG.info(" Initial Assumed Low priority Val is : "+lowPrty);
    		
    		for (Object[] result : srvcPrmtrDtls) {
    			LOG.info(result[4].toString());
    			Integer newLowPrty = Integer.parseInt(((String) result[4])	.trim());
    			if(newLowPrty < lowPrty){
    				lowPrty = newLowPrty;
    				srvcPrmtrObj = new Object[]{ result[1], result[2], result[3] };
    			}
    		}
    		
    		LOG.info(" Actual Low priority Val is : "+lowPrty);
    	}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return srvcPrmtrObj;
	}


	/**
	 * @param porCdStr
	 * @param ordrTkBsMnthStr
	 * @return extracted existing production orders
	 */
	private List<Object[]> extractExtngPrdnOrdrNos(String porCdStr,String ordrTkBsMnthStr) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		List<Object[]> prdnOrdrDtls  = new ArrayList<Object[]>();
		
    	LOG.info("Order take base month  is : "+ordrTkBsMnthStr+ ", por cd is : "+porCdStr);
    	
    	Query getExtngPrdnOrdrQry = entityManager.createNativeQuery(QueryConstants.getExtngPrdnOrdr.toString());
    	
    	getExtngPrdnOrdrQry.setParameter(PDConstants.PRMTR_PORCD, porCdStr);
    	getExtngPrdnOrdrQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnthStr);
		
		LOG.info("Existing Production Orders Extraction Query String is : "+ getExtngPrdnOrdrQry);

		prdnOrdrDtls = getExtngPrdnOrdrQry.getResultList();
		
		if(prdnOrdrDtls == null || prdnOrdrDtls.isEmpty())
    	{
        	String[] messageParams = {B000027Constants.BATCH_27_ID,ordrTkBsMnthStr,porCdStr};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00172, B000027Constants.P0007, messageParams);
    	} 
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return prdnOrdrDtls;
	}


	/**
	 * @param buyerCd
	 * @param porCd
	 * @return Maps symbol
	 */
	public String extractMapsSymbol(String buyerCd, String porCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String mapsVal = null;
		
    	LOG.info("Buyer code  is : "+buyerCd+ ", por cd is : "+porCd);
    	
    	Query mapsSymblDetailsQry = entityManager.createNativeQuery(QueryConstants.getMapsSymbol.toString());
    	
    	mapsSymblDetailsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	mapsSymblDetailsQry.setParameter(PDConstants.PRMTR_BUYERCD, buyerCd);
		
		LOG.info("Maps symbol Extraction Query String is : "+ mapsSymblDetailsQry);
		
		List<Object[]> resultList = mapsSymblDetailsQry.getResultList();
		
		if(resultList != null && !(resultList.isEmpty())){
				Object[] ocfRgnObj = resultList.get(0);
				mapsVal = CommonUtil.convertObjectToString(ocfRgnObj[1]);
		} else if (resultList == null || resultList.isEmpty()){
		 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00176, B000027Constants.P0010_2, messageParams);
	 }
    	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return mapsVal;
	}
	
	/**
	 * @param prdnMnth
	 * @param porCd
	 * @return newly generated production order number
	 */
	private String generatePrdnOrdrNo(String prdnMnth,String porCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		String firstTwoDgts = CommonUtil.generatePrdnOrdrNoTwoDgts(prdnMnth);
		String fveToTenDgts = CommonUtil.zeroPadding(Integer.parseInt(getTrnMnthlyPrdnOrdrSeqNo()),PDConstants.MONTH_END_INDEX);

		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return firstTwoDgts+porCd+fveToTenDgts;
	}
	
	/**
	 * This method is used to get the Monthly Production order trn Latest Sequence Id.
	 * 
	 * @return String Seq no.
	 */
	public String getTrnMnthlyPrdnOrdrSeqNo() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);

		String mnthlyPrdnOrdrTrnSeqStr = QueryConstants.TRN_MNTHLY_PRDN_ORDR_SEQ_ID.toString();
		Query mnthlyPrdnOrdrSqId = entityManager.createNativeQuery(mnthlyPrdnOrdrTrnSeqStr);
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = mnthlyPrdnOrdrSqId.getResultList();
		
		String seqNo = CommonUtil.bigDecimaltoString(result.get(0));
		if (result.isEmpty()) {
			return null;
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return seqNo;
	}


	/**
	 * @param addSpecCd
	 * @return body protection code
	 */
	public String extractBdyPrtnrCd(String addSpecCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String bdyPrtnCdVal = null;
		
    	LOG.info("Additional Spec Code  is : "+addSpecCd);
    	
    	Query bdyPrtnCdDetailsQry = entityManager.createQuery(QueryConstants.getBdyPrtnCdDtls.toString());
    	
    	bdyPrtnCdDetailsQry.setParameter(PDConstants.PRMTR_ADDITIONALSPECCD, addSpecCd.substring(2, 4));
		
		LOG.info("Body protection Code Extraction Query String is : "+ bdyPrtnCdDetailsQry);
		
		List<String> resultList = bdyPrtnCdDetailsQry.getResultList();
		
		if(resultList != null && !(resultList.isEmpty())){
			bdyPrtnCdVal = resultList.get(0);
		} else if (resultList == null || resultList.isEmpty()){
		 	String[] messageParams = {B000027Constants.BATCH_27_ID,addSpecCd};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00181, B000027Constants.P0010_5, messageParams);
	 }
    	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return bdyPrtnCdVal;
	}
	
	
	/**
	 * @param addSpecCd
	 * @return tyre maker codes
	 */
	public String extractTyreMkrCd(String addSpecCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String tyrMkrCdVal = null;
		
    	LOG.info("Additional Spec Code  is : "+addSpecCd);
    	
    	Query tyreMkrDetailsQry = entityManager.createQuery(QueryConstants.getTyreMkrDtls.toString());
    	
    	tyreMkrDetailsQry.setParameter(PDConstants.PRMTR_ADDITIONALSPECCD, addSpecCd.substring(0, 2));
		
		LOG.info("Tyre Maker Code Extraction Query String is : "+ tyreMkrDetailsQry);
		
		List<String> resultList = tyreMkrDetailsQry.getResultList();
		
		if(resultList != null && !(resultList.isEmpty())){
			tyrMkrCdVal = resultList.get(0);
		} else if (resultList == null || resultList.isEmpty()){
		 	String[] messageParams = {B000027Constants.BATCH_27_ID,addSpecCd};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00180, B000027Constants.P0010_4, messageParams);
	 }
    	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return tyrMkrCdVal;
	}


	/**
	 * @param porCd
	 * @param prdnMnth
	 * @param ukOeiBuyerId
	 * @param potCd
	 * @return export number
	 * 
	 * This method extracts the export number from EX-NO MST table based on the input parameters
	 */
	public String extractExportNum(String porCd, String prdnMnth, String ukOeiBuyerId, String potCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		String exNoVal = null;
		
    	LOG.info("Production month  is : "+prdnMnth+" , POT value is " +potCd+" , UK OEI BUYER ID value is " +ukOeiBuyerId+" , POR value is " +porCd);
    	
    	Query exNoDetailsQry = entityManager.createQuery(QueryConstants.getExNoDtls.toString());
    	
    	exNoDetailsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	exNoDetailsQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdnMnth);
    	exNoDetailsQry.setParameter(PDConstants.PRMTR_UK_OEI_BUYEER_ID, ukOeiBuyerId);
    	exNoDetailsQry.setParameter(PDConstants.PRMTR_POT_CD, potCd);
		
		LOG.info("Export Number Extraction Query String is : "+ exNoDetailsQry);
		
		List<String> resultList = exNoDetailsQry.getResultList();
		
		if(resultList != null && !(resultList.isEmpty())){
			exNoVal = resultList.get(0);
		} else if (resultList == null || resultList.isEmpty()){
		 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00343, B000027Constants.P0010_1_2, messageParams);
	 }
    	
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return exNoVal;
	}
	
	/**
	 * @param porCd
	 * @param ordrTkBsMnth
	 * 
	 * This method is used to delete the data based on por code and order take base month
	 */
	public void deleteExtngData( String porCd, String ordrTkBsMnth) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd);
    	
    	Query deleteQry = entityManager.createNativeQuery(QueryConstants.deleteExtngOrdrDtls.toString());
    	
    	deleteQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	deleteQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		
		LOG.info("Mnthly Production Order Deletion Query String is : "+ deleteQry);

		int count = deleteQry.executeUpdate(); 
		LOG.info("Delete Mnthly Production Order Count  : " + count); 
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * @param porCd
	 * @param ordrTkBsMnth
	 * 
	 * This method is used to delete the data based on por code and order take base month in temporary table
	 */
	public void deleteExtngTmpTblData( String porCd, String ordrTkBsMnth) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd);
    	
		Query deleteTmpTblQry = entityManager.createNativeQuery(QueryConstants.deleteTempTbl.toString());
		
		LOG.info("TEMP Mnthly Production Order Deletion Query String is : "+ deleteTmpTblQry);
		
			int delCount = deleteTmpTblQry.executeUpdate(); 
			LOG.info("Delete DEV_TRN_MNTHLY_PROD_ORDR Count  : " + delCount); 
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @return Monthly order detail report records
	 */
	public List<Object[]> fetchPrdnOrdrDtlRptRcrds(String ordrTkBsMnth, String porCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd);
    	
    	Query ordrDtlsQry = entityManager.createNativeQuery(QueryConstants.getMnthlyPrdnOrdrRptDtls.toString()); 
    	
    	ordrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	ordrDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		
		LOG.info("Extraction Monthly Production Order details Query String is : "+ ordrDtlsQry);
		
		List<Object[]> result = ordrDtlsQry.getResultList();
		
		LOG.info("Extracted Monthly order details report count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
	}
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @param crSrs
	 * @param vldPrdnMnthsLst
	 * @param allCrSrs
	 * @return order spec report details
	 */ 
	public List<Object[]> extractOrdrSpecRptDtls(String ordrTkBsMnth,String porCd, Object crSrs, List<String> vldPrdnMnthsLst, Map<Object, List<String>> allCrSrs) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd+" , Car Series is " +crSrs);
		
		Query ordrSpecRptDtlsQry = entityManager.createNativeQuery(QueryConstants.ordrSpecRptRcds.toString()); 
		
		ordrSpecRptDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		ordrSpecRptDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		ordrSpecRptDtlsQry.setParameter(PDConstants.PRMTR_CARSRS, allCrSrs.keySet());
		ordrSpecRptDtlsQry.setParameter(PDConstants.CARSERIES, crSrs);
		ordrSpecRptDtlsQry.setParameter(PDConstants.PRODUCTION_MONTH, vldPrdnMnthsLst);
    	
		LOG.info("Extraction Order Spec error suspended/non suspended Order details Query String is : "+ ordrSpecRptDtlsQry);
		
		List<Object[]> result = ordrSpecRptDtlsQry.getResultList();
		
		LOG.info("Extracted Order Spec error suspended/non suspended Order details report count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
	}
	
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @return Extracted Order details from DEV_TRN_MNTHLY_PROD_ORDR details
	 */
	public List<Object[]> extractDraftPrdnOrdrDtls(String ordrTkBsMnth,String porCd) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd);
		
		Query drftOrdrDtlsQry = entityManager.createNativeQuery(QueryConstants.getDraftPrdnOrdrDtls.toString()); 
		
		drftOrdrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		drftOrdrDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
    	
		LOG.info("Extraction Order details from DEV_TRN_MNTHLY_PROD_ORDR Query String is : "+ drftOrdrDtlsQry);
		
		List<Object[]> result = drftOrdrDtlsQry.getResultList();
		
		LOG.info("Extracted Order details from DEV_TRN_MNTHLY_PROD_ORDR count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
	}
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @param prodMnth
	 * @param oseiid
	 * @param byrGrpCd
	 * @param carSeries
	 * @return Feature and usage details
	 */
	public List<Object[]> getFtreOCFUsgDtls(String ordrTkBsMnth,String porCd, String prodMnth, String oseiid, String byrGrpCd, String carSeries) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd+" , Prodn Month value is " +prodMnth+" , OSEI Id value is " +oseiid+" , Buyer Group Code value is " +byrGrpCd+" , Car Series value is " +carSeries); 
		
		Query ftreOCFUsgQry = entityManager.createNativeQuery(QueryConstants.getFtreCdUsageDtls.toString()); 
		
		ftreOCFUsgQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		ftreOCFUsgQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth); 
		ftreOCFUsgQry.setParameter(PDConstants.PRODUCTION_MONTH, prodMnth); 
		ftreOCFUsgQry.setParameter(PDConstants.PRMTR_UK_OSEI_ID, oseiid); 
		ftreOCFUsgQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCd); 
		ftreOCFUsgQry.setParameter(PDConstants.PRMTR_CAR_SRS, carSeries); 
    	
		LOG.info("Extraction Feature and OCF Usage Details Query String is : "+ ftreOCFUsgQry);
		
		List<Object[]> result = ftreOCFUsgQry.getResultList();
		
		LOG.info("Extracted Feature and OCF Usage Details  count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
	}
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @param prdnMnthStr
	 * @param crSrsStr
	 * @param byrGrpCdStr
	 * @param ocfRgnCdStr
	 * @param ocfByrGrpCdStr
	 * @param unqFrmCdRcds
	 * @param unqFtreCdRcds
	 * @return OCF Limit details
	 */
	public List<Object[]> getOCFLmtDtls(String ordrTkBsMnth,String porCd, String prdnMnthStr, String crSrsStr, String byrGrpCdStr, String ocfRgnCdStr, 
			String ocfByrGrpCdStr, List<String> unqFrmCdRcds, List<String> unqFtreCdRcds) { 
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+ordrTkBsMnth+" , POR value is " +porCd+" , Frame code size is " +unqFrmCdRcds.size()+" , Feature code value is " +unqFtreCdRcds.size()); 
		
		String OCfLmtDtlsQryStr = QueryConstants.fetchOcfBrchRcrds.toString();
		
		if(unqFtreCdRcds.size() > 0 && unqFrmCdRcds.size() > 0 )
			OCfLmtDtlsQryStr = OCfLmtDtlsQryStr+QueryConstants.fetchOcfBrchRcrdsApndFtreCd.toString()+QueryConstants.fetchOcfBrchRcrdsApndFrmeCd.toString();
		else if(unqFtreCdRcds.size() > 0 && unqFrmCdRcds.size() == 0)
			OCfLmtDtlsQryStr = OCfLmtDtlsQryStr+QueryConstants.fetchOcfBrchRcrdsApndFtreCd.toString();
		else if(unqFrmCdRcds.size() > 0 && unqFtreCdRcds.size() == 0)
			OCfLmtDtlsQryStr = OCfLmtDtlsQryStr+QueryConstants.fetchOcfBrchRcrdsApndFrmeCd.toString();
		
		Query OCfLmtDtlsQry = entityManager.createNativeQuery(OCfLmtDtlsQryStr); 
		
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		OCfLmtDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, ordrTkBsMnth); 
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdnMnthStr);
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_CARSRS, crSrsStr);
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_BYR_GRP_CD, byrGrpCdStr);
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_OCFRGNCD, ocfRgnCdStr);
		OCfLmtDtlsQry.setParameter(PDConstants.PRMTR_OCFBYRGRPCD, ocfByrGrpCdStr); 
		if(unqFtreCdRcds.size() > 0 && unqFrmCdRcds.size() > 0 ){
			OCfLmtDtlsQry.setParameter(B000027Constants.PRMTR_FRM_CD, unqFrmCdRcds);
			OCfLmtDtlsQry.setParameter(B000027Constants.PRMTR_FTRE_CD, unqFtreCdRcds);
		}
		else if(unqFrmCdRcds.size() > 0 && unqFtreCdRcds.size() == 0)
		OCfLmtDtlsQry.setParameter(B000027Constants.PRMTR_FRM_CD, unqFrmCdRcds);
		else if(unqFtreCdRcds.size() > 0 && unqFrmCdRcds.size() == 0)
		OCfLmtDtlsQry.setParameter(B000027Constants.PRMTR_FTRE_CD, unqFtreCdRcds);
    	
		LOG.info("Extraction Buyer Group OCF Limit Details Query String is : "+ OCfLmtDtlsQry);
		
		List<Object[]> result = OCfLmtDtlsQry.getResultList();
		
		LOG.info("Extracted Buyer Group OCF Limit Details  count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
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
