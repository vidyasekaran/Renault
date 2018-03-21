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
package com.nissangroups.pd.b000027.processor;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.model.TrnMnthlyProdOrdr;
import com.nissangroups.pd.repository.MnthlyPrdnOrdrTrnRepository;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.process.CreateOrdrSpecErrorRptProcess;
import com.nissangroups.pd.b000027.process.CreateSrvcErrRptProcess;
import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.exception.PdApplicationException;

/**
 * This class is used to extract the monthly order information  for the batch B000027.
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000027Constants.GET_MONTHLY_ORDER_DETAILS_PROCESSOR)
public class ExtractMnthlyOrdrDtlsProcessor implements ItemProcessor<OrdrTkBsPrdMstRowMapper, OrdrTkBsPrdMstRowMapper> { 
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ExtractMnthlyOrdrDtlsProcessor.class);
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param order quantity. */
	String jobParamOrdrQty = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable production order reuse flag value */
	String prdOrdrReuseFlg = null;
	
	/** Variable parameterised order horizon value */
	String prmtrOrdrHrznVal = null;
	
	/** Variable parameterised suspended orders value */
	String prmtrSendSuspOrdrVal = null;
	
	/** Variable parameterised attach export number flag value */
	String prmtrExNoVal = null;
	
	/** Variable parameterised attach service parameter value */
	String prmtrSrvcPrmtrVal = null;
	
	/** Variable car series horizon details. */
	List<Object[]> crSrsList = new ArrayList<Object[]>();
	
	Map<Object, List<String>> validCrSrsMap = new HashMap<>();
	
	Map<Object, List<String>> abolishCrSrsMap = new HashMap<>();
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private MnthlyPrdnOrdrTrnRepository mnthPrdnOrdrRepo;
	
	@Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;
	
	@Autowired(required = false)
	private CreateOrdrSpecErrorRptProcess ordrSpecErrRpt;
	
	@Autowired(required = false)
	private CreateSrvcErrRptProcess crtSrvcErrRpt;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public OrdrTkBsPrdMstRowMapper process(OrdrTkBsPrdMstRowMapper item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		LOG.info("Job inputs --> POR Cd is   : "+jobParamPor+ " and Stage Code  is :" +jobParamStgCd +" and order quantity flag is : "+jobParamOrdrQty);
		
		String ordrTkBsMnth = item.getId().getORDR_TAKE_BASE_MNTH();
		String porCd = item.getId().getPOR_CD();
		
		LOG.info("READER values --> POR Cd is   : "+porCd+ " and order take base month  is :" +ordrTkBsMnth);
		
		/** Process Id - P0002 */
		prdOrdrReuseFlg = extractPrdOrdrReuseFlgVal(jobParamPor);
		
		/** Process Id - P0003.1 */
		crSrsList = extractCarSrsDtls(jobParamPor,ordrTkBsMnth);
		
		/** Process Id - P0003.2 */
		prmtrOrdrHrznVal = extractPrmtrOrdrHrznVal(jobParamPor);
		
		prmtrSendSuspOrdrVal = extractPrmtrSendSuspOrdrVal(jobParamPor);
		prmtrExNoVal = extractPrmtrExNoVal(jobParamPor);
		prmtrSrvcPrmtrVal=extractPrmtrSrvcPrmtrVal(jobParamPor);
		
		calculateandValidatePrdnMnths(item, prmtrOrdrHrznVal);
		
		LOG.info("Car Series with valid Production Months is ::: "+validCrSrsMap.size()+"-->"+validCrSrsMap.keySet());
		LOG.info("Car Series with invalid Production Months  is ::: "+abolishCrSrsMap.size()+"-->"+abolishCrSrsMap.keySet());
		
		/** PST-DRG-R000011 -- P0005 **/
		ordrSpecErrRpt.generateOrdrSpecErrorReport(item,validCrSrsMap);
		
		/** Process Id - P0004 */
		fetchMnthlyOrdrDtls(jobParamPor,validCrSrsMap,prmtrOrdrHrznVal,item,prdOrdrReuseFlg);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return item; 
	}
	
	
	/**
	 * @param jobParamPor
	 * @param validCrSrsMap
	 * @param prmtrOrdrHrznVal
	 * @param item
	 * @param prdOrdrReuseFlg
	 * 
	 * This method fetches the Monthly order trn details and generates Service Error Report 
	 */
	private void fetchMnthlyOrdrDtls(String jobParamPor,Map<Object, List<String>> validCrSrsMap, String prmtrOrdrHrznVal,OrdrTkBsPrdMstRowMapper item, String prdOrdrReuseFlg) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		List<TrnMnthlyProdOrdr> mnthlyOrdrTrnDtls = new ArrayList<TrnMnthlyProdOrdr>();
		
		List<Object[]> ordrDtls = new ArrayList<Object[]>();
		List<Object[]> ordrDtlsLst = new ArrayList<Object[]>();
		Map<String, List> resultMapVal = new HashMap<String, List>();
		
		if(prmtrSendSuspOrdrVal.equalsIgnoreCase(PDConstants.NO)) {
		for (Map.Entry<Object, List<String>> entry : validCrSrsMap.entrySet()) {
			LOG.info("Processing for Valid Car Series Key and Production months is : "+entry.getKey()+"---->"+entry.getValue());
			ordrDtls = mnthPrdnOrdrRepo.extractOrdrDtls(jobParamPor, jobParamOrdrQty, jobParamStgCd,item, entry, prmtrOrdrHrznVal, prmtrSendSuspOrdrVal);
			ordrDtlsLst.addAll(ordrDtls);
		}
		} else if(prmtrSendSuspOrdrVal.equalsIgnoreCase(PDConstants.YES)) {
			ordrDtls = mnthPrdnOrdrRepo.extractOrdrDtls(jobParamPor, jobParamOrdrQty, jobParamStgCd,item,null, prmtrOrdrHrznVal, prmtrSendSuspOrdrVal);
			ordrDtlsLst.addAll(ordrDtls);
		}
		
		LOG.info("Monthly Order Trn Details Extraction Query Result is : --> "+ ordrDtlsLst.size());
		
		resultMapVal = mnthPrdnOrdrRepo.saveMnthlyOrdrDtls(ordrDtlsLst,prmtrExNoVal,prmtrSrvcPrmtrVal,prdOrdrReuseFlg);
		
		/**PST-DRG-R000016 - Service Error Report **/
		crtSrvcErrRpt.generateSrvcErrReport(resultMapVal.get(B000027Constants.SRVC_ERR_RPT_MAP_VAL));
		
		mnthlyOrdrTrnDtls = resultMapVal.get(B000027Constants.MNTHLY_PRDN_TRN_VAL);
		
		mnthPrdnOrdrRepo.deleteExtngData(jobParamPor, item.getId().getORDR_TAKE_BASE_MNTH());
		
		LOG.info("Record count in prdnOrdrDtlsLst list is :"+mnthlyOrdrTrnDtls.size());
		for(TrnMnthlyProdOrdr ordrDtl : mnthlyOrdrTrnDtls){
			mnthPrdnOrdrRepo.savePrdnOrdrDtls(ordrDtl);
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to find whether service parameter details has to be attached or not
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrSrvcPrmtrVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		if(prmtrSrvcPrmtrVal== null){
			
				prmtrSrvcPrmtrVal = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.ATTACH_SERVICE_PRMTR);

			if(!prmtrSrvcPrmtrVal.isEmpty())
			 LOG.info("Extracted Parameter Service Parameter value is  : " +prmtrSrvcPrmtrVal);
			 else {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00342, B000027Constants.P0010_4_1, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prmtrSrvcPrmtrVal;
	}
	
	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to find whether export number has to be attached or not
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrExNoVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		if(prmtrExNoVal== null){
			
				prmtrExNoVal = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.ATTACH_EX_NO);

			if(!prmtrExNoVal.isEmpty())
			 LOG.info("Extracted Parameter Export Number value is  : " +prmtrExNoVal);
			 else {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00343, B000027Constants.P0010_1_1, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prmtrExNoVal;
	}
	

	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to find whether suspended orders has to be sent or not
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrSendSuspOrdrVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		/** Process Id - P0004  Extract Send suspended orders or not value*/
		
		if(prmtrSendSuspOrdrVal== null){
			
				prmtrSendSuspOrdrVal = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.SEND_SUSPENDED_ORDER);

			if(!prmtrSendSuspOrdrVal.isEmpty())
			 LOG.info("Extracted Parameter Send suspended orders value is  : " +prmtrSendSuspOrdrVal);
			 else {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00338, B000027Constants.P0004_2, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prmtrSendSuspOrdrVal;
	}



	/**
	 * @param item
	 * @param crSrsDtls
	 * @param ordrHrzn
	 * 
	 * For each car series, This method is calculate and validate the production months
	 */
	private void calculateandValidatePrdnMnths(OrdrTkBsPrdMstRowMapper item , String ordrHrzn) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/** Temp variable get hold the car series record processing **/
		int crSrsRcdIterationCnt = 0; 
		List<String> prdnMnthsList = new ArrayList<String>();
		
		 if (!crSrsList.isEmpty()){
			 for (Object[] carSrsobj : crSrsList) {
					
				 LOG.info("Processing Car Series List - Record Number :: "+crSrsRcdIterationCnt);
					crSrsRcdIterationCnt++; 
					
					if (carSrsobj[2] == null) {
						
						String[] messageParams = {B000027Constants.BATCH_27_ID,carSrsobj[0].toString(),PDConstants.POR_CAR_SERIES_MST};
			        	B000027CommonUtil.logMessage(PDMessageConsants.M00146, B000027Constants.P0003_1, messageParams);
						
					} else  {
						
						prdnMnthsList = getPrdnMnthDtls(item, carSrsobj[2],ordrHrzn);
						
						validatePrdnMnths(carSrsobj,prdnMnthsList);
					
					}
					
		 }
		 }
		 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * @param carSrsObj
	 * @param prdnMnthsList
	 * 
	 * For each car series, This method is validate the production months with car series adopt & abolish dates
	 */
	private void validatePrdnMnths(Object[] carSrsObj ,List<String> prdnMnthsList) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String carSrsAdptDateStr = CommonUtil.convertObjectToString(carSrsObj[3]);
		String carSrsAblshDateStr = CommonUtil.convertObjectToString(carSrsObj[4]);
		
		List<String> validPrdnMnths =new ArrayList<String>();
		List<String> invalidPrdnMnths = new ArrayList<String>();
		
		for(String prdnMnthStr : prdnMnthsList) {
			
		try {
			Date prdMnth = CommonUtil.convertStringToDate(prdnMnthStr);
			
			Date carSrsAdptDate = CommonUtil.convertStringToDate(carSrsAdptDateStr.substring(0, 6));
			Date carSrsAblshDate = CommonUtil.convertStringToDate(carSrsAblshDateStr.substring(0, 6));
			
			LOG.info("Validating Production months for Car Series : "+carSrsObj[1]+" , Prdn Month : "+prdMnth+" Adopt Date : "+carSrsAdptDate+" Abolish date : "+carSrsAblshDate);
			
			if(prdMnth.compareTo(carSrsAdptDate) >= 0 && prdMnth.compareTo(carSrsAblshDate) <=0 ){ 
				validPrdnMnths.add(prdnMnthStr);
			} else 
				invalidPrdnMnths.add(prdnMnthStr);
			
		} catch (ParseException e) {
			LOG.error(e);
			String[] messageParams = {B000027Constants.BATCH_27_ID,carSrsObj[0].toString()};
        	B000027CommonUtil.logMessage(PDMessageConsants.M00245, B000027Constants.P0003_2_VALIDATION, messageParams);
		}
		
		}
		
		LOG.info ("Valid Prdn Mnths is :"+validPrdnMnths+ " and invalid prdn Mnths is : "+invalidPrdnMnths);
		
		if(validPrdnMnths!=null && validPrdnMnths.size() > 0)
		validCrSrsMap.put(carSrsObj[1], validPrdnMnths);
		else if(invalidPrdnMnths!=null && invalidPrdnMnths.size() > 0)
		abolishCrSrsMap.put(carSrsObj[1], invalidPrdnMnths);
		
		LOG.info("Valid Car Series Map Key & values : "+validCrSrsMap.entrySet()+" and Abolish Car Series Map Key & values : "+abolishCrSrsMap.entrySet());

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * @param item
	 * @param carSrsHrzn
	 * @param ordrHrznStr
	 * @return production months list
	 * 
	 *  This method will calculate the production months based on the horizon and order take base month.
	 */
	private List<String> getPrdnMnthDtls(OrdrTkBsPrdMstRowMapper item,Object carSrsHrzn, String ordrHrznStr) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		int ordrHrznVal = 0;
		int minHrznVal = 0;
		List<String> prdnMnthsDtlsList = new ArrayList<String>();
		
		int crSrsHrznIntVal = CommonUtil.bigDecimaltoInt(carSrsHrzn);
		
		if(!ordrHrznStr.equalsIgnoreCase(B000027Constants.HORIZON_FULL) && !ordrHrznStr.equalsIgnoreCase(B000027Constants.HORIZON_INFINITY)) {
		 ordrHrznVal = Integer.parseInt(ordrHrznStr);
		 minHrznVal = (crSrsHrznIntVal < ordrHrznVal) ? crSrsHrznIntVal : ordrHrznVal;
		}
		else{
			LOG.info("Paramter Hrzn value is either Full or Infinity, Hence minimum car series horizon = car series horizon ");
			minHrznVal = crSrsHrznIntVal;
		}
		
		 LOG.info("Minimum Hrzn value is : " +minHrznVal);

		/** calculate production months*/
		 prdnMnthsDtlsList = CommonUtil.getProductionMonths(Integer.toString(minHrznVal), item.getId().getORDR_TAKE_BASE_MNTH());
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return prdnMnthsDtlsList;
	}

	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to get the order horizon value. 
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrOrdrHrznVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		/** Process Id - P0003.2  Extract Order horizon details*/
		
		if(prmtrOrdrHrznVal== null){
			
				prmtrOrdrHrznVal = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.ORDER_HORIZON);

			if(!prmtrOrdrHrznVal.isEmpty())
			 LOG.info("Extracted Parameter Order horizon value is  : " +prmtrOrdrHrznVal);
			 else {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00182, B000027Constants.P0003_2, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prmtrOrdrHrznVal;
	}

	
	/**
	 * @param porCd, order take base month
	 * @return car series details
	 * 
	 * This method is used to extract the car series details from por car series mst table
	 */
	private List<Object[]> extractCarSrsDtls(String porCd, String ordrTkBsMnth) {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		 /** Process Id - P0003.1  Extract car series details */
		if(crSrsList== null || crSrsList.isEmpty()){
			crSrsList = mnthPrdnOrdrRepo.chkHrzn(porCd, ordrTkBsMnth+PDConstants.WEEK1); 
			LOG.info("Extracted Car series list size is : " +crSrsList.size());
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return crSrsList;
	}
	
	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to decide whether, along with the Order informations, whether the PO# should have to be sent or not. 
	 * @throws PdApplicationException 
	 */
	private String extractPrdOrdrReuseFlgVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		/** Process Id - P0002  Extract Production Order Reuse Parameter details*/
		
		if(prdOrdrReuseFlg== null){
			
				prdOrdrReuseFlg = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.REUSE_PRODUCTION_ORDER_NO);

			if(!prdOrdrReuseFlg.isEmpty())
			 LOG.info("Extracted Production Order Reuse Parameter value is  : " +prdOrdrReuseFlg);
			 else {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00340, PDConstants.P0002, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prdOrdrReuseFlg;
	}

	/**
	 * Gets the jobParamPor
	 *
	 * @return the jobParamPor
	 */
	
	public String getJobParamPor() {
		return jobParamPor;
	}

	/**
	 * Sets the jobParamPor
	 *
	 * @param jobParamPor the jobParamPor to set
	 */
	
	public void setJobParamPor(String jobParamPor) {
		this.jobParamPor = jobParamPor;
	}

	/**
	 * Gets the jobParamOrdrQty
	 *
	 * @return the jobParamOrdrQty
	 */
	
	public String getJobParamOrdrQty() {
		return jobParamOrdrQty;
	}

	/**
	 * Sets the jobParamOrdrQty
	 *
	 * @param jobParamOrdrQty the jobParamOrdrQty to set
	 */
	
	public void setJobParamOrdrQty(String jobParamOrdrQty) {
		this.jobParamOrdrQty = jobParamOrdrQty;
	}

	/**
	 * Gets the jobParamStgCd
	 *
	 * @return the jobParamStgCd
	 */
	
	public String getJobParamStgCd() {
		return jobParamStgCd;
	}

	/**
	 * Sets the jobParamStgCd
	 *
	 * @param jobParamStgCd the jobParamStgCd to set
	 */
	
	public void setJobParamStgCd(String jobParamStgCd) {
		this.jobParamStgCd = jobParamStgCd;
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