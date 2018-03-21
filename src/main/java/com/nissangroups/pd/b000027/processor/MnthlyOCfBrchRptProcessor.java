/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000013  
 * Module          :O Ordering
 * Process Outline :MONTHLY OCF BREACH REPORT
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 16-12-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.b000027.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.b000027.util.B000027Constants.CREATE_MNTHLY_OCF_BRCH_RPT;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.process.CreateMnthlyOCFBreachRptProcess;
import com.nissangroups.pd.repository.MnthlyPrdnOrdrTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This is the class to generate the Monthly OCF Breach report -- PST-DRG-R000013 
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(CREATE_MNTHLY_OCF_BRCH_RPT)
public class MnthlyOCfBrchRptProcessor implements ItemProcessor<OrdrTkBsPrdMstRowMapper, OrdrTkBsPrdMstRowMapper> { 
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MnthlyOCfBrchRptProcessor.class);
	

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param order quantity. */
	String jobParamOrdrQty = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private MnthlyPrdnOrdrTrnRepository mnthPrdnOrdrRepo;
	
	@Autowired(required = false)
	private CreateMnthlyOCFBreachRptProcess ocfBrchRpt;
	
	List prdnMnthsLst = new ArrayList<>();
	
	String maxPrdnMnth = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public OrdrTkBsPrdMstRowMapper process(OrdrTkBsPrdMstRowMapper item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<Object[]> ftreUsgDtls = new ArrayList<Object[]>();
		List<Object[]> lmtDtlsLst = new ArrayList<Object[]>();
		Set<String> unqFrmCdSt = new HashSet<String>();
		Set<String> unqFtrCdSt= new HashSet<String>();
		List<String> unqFrmCdRcds = new ArrayList<String>();
		List<String> unqFtreCdRcds = new ArrayList<String>();
		
		LOG.info("Job inputs --> POR Cd is   : "+jobParamPor+ " and Stage Code  is :" +jobParamStgCd +" and order quantity flag is : "+jobParamOrdrQty);
		
		String ordrTkBsMnth = item.getId().getORDR_TAKE_BASE_MNTH();
		String porCd = item.getId().getPOR_CD();
		
		LOG.info("READER values --> POR Cd is   : "+porCd+ " and order take base month  is :" +ordrTkBsMnth);
		
		List<Object[]> ordrDtls = mnthPrdnOrdrRepo.extractDraftPrdnOrdrDtls(ordrTkBsMnth,porCd); 
		
		
		if(ordrDtls.isEmpty()) {
			//Error details not in design doc
		} else
		{
			for(Object[] ordrDtlsObj : ordrDtls){
				String prdnMnthStr = CommonUtil.convertObjectToString(ordrDtlsObj[0]);
				String oseiIdStr = CommonUtil.convertObjectToString(ordrDtlsObj[9]);
				String byrGrpCdStr = CommonUtil.convertObjectToString(ordrDtlsObj[8]);
				String crSrsStr = CommonUtil.convertObjectToString(ordrDtlsObj[10]);
				
				ftreUsgDtls = mnthPrdnOrdrRepo.getFtreOCFUsgDtls(ordrTkBsMnth,porCd,prdnMnthStr,oseiIdStr,byrGrpCdStr,crSrsStr);
				
				if(ftreUsgDtls.size() > 0)
				{
				for (Object[] ftreUsgObj : ftreUsgDtls){
					
					String frmCdkeyStr = byrGrpCdStr+prdnMnthStr+crSrsStr+ftreUsgObj[3].toString();
					String ftrCdkeyStr = byrGrpCdStr+prdnMnthStr+crSrsStr+ftreUsgObj[4].toString();
					
					boolean frmCdFlg = unqFrmCdSt.add(frmCdkeyStr);
					if(frmCdFlg) {
						unqFrmCdRcds.add(ftreUsgObj[3].toString());
					}
					
					boolean ftreCdFlg = unqFtrCdSt.add(ftrCdkeyStr);
					if(ftreCdFlg) {
						unqFtreCdRcds.add(ftreUsgObj[4].toString());
					}
				}
				}
			}
			
			lmtDtlsLst = fetchOCFLmtDtls(ordrDtls,ordrTkBsMnth,porCd,unqFrmCdRcds,unqFtreCdRcds);
			
		}
		
		LOG.info("Record Count in lmtDtlsLst is :"+lmtDtlsLst.size());
		
		List<Object[]> brchRcdsLst = getBrchNonBrchRcds(lmtDtlsLst);
		
	    Collections.sort(prdnMnthsLst); 
		if(!prdnMnthsLst.isEmpty() && prdnMnthsLst.size() > 0) {
		maxPrdnMnth = prdnMnthsLst.get(prdnMnthsLst.size() - 1).toString();
		}
	
	   ocfBrchRpt.generateHrzntlBrchReport(brchRcdsLst,maxPrdnMnth,ordrTkBsMnth); 
		
		ocfBrchRpt.generateVrtlBrchReport(brchRcdsLst,maxPrdnMnth,ordrTkBsMnth); 
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return item; 
	}
	
	/**
	 * @param ordrDtls
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @param unqFrmCdRcds
	 * @param unqFtreCdRcds
	 * @return ocf limit details
	 */
	private List<Object[]> fetchOCFLmtDtls(List<Object[]> ordrDtls, String ordrTkBsMnth, String porCd, List<String> unqFrmCdRcds, List<String> unqFtreCdRcds) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<Object[]> lmtDtlsValLst = new ArrayList<Object[]>();
		
		for(Object[] ordrDtlsObj : ordrDtls){
			String prdnMnthStr = CommonUtil.convertObjectToString(ordrDtlsObj[0]);
			String byrGrpCdStr = CommonUtil.convertObjectToString(ordrDtlsObj[8]);
			String crSrsStr = CommonUtil.convertObjectToString(ordrDtlsObj[10]);
			String ocfRgnCdStr = CommonUtil.convertObjectToString(ordrDtlsObj[12]);
			String ocfByrGrpCdStr = CommonUtil.convertObjectToString(ordrDtlsObj[13]); 
			
				List<Object[]> oCFLmtDtls = mnthPrdnOrdrRepo.getOCFLmtDtls(ordrTkBsMnth, porCd,prdnMnthStr,crSrsStr,byrGrpCdStr,ocfRgnCdStr,ocfByrGrpCdStr,unqFrmCdRcds,unqFtreCdRcds); 
				lmtDtlsValLst.addAll(oCFLmtDtls);
				
				prdnMnthsLst.add(prdnMnthStr);
				
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return lmtDtlsValLst;
		
	}

	/**
	 * @param lmtDtlsLst
	 * @return breach and non breach record details
	 */
	private List<Object[]> getBrchNonBrchRcds(List<Object[]> lmtDtlsLst) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<Object[]> brchRcdsLstVal = new ArrayList<Object[]>();
	    Object[] brchRcdObj = null; 
	    List<Object[]> nonBrchRcdsLst = new ArrayList<Object[]>();
	    Object[] nonBrchRcdObj = null; 
	    int lmtVal = 0;
    	int usgVal = 0;
	    
	    for(Object[] lmtObj : lmtDtlsLst){
	    	if(lmtObj[10]!=null)
	    	 lmtVal = CommonUtil.stringtoInt(lmtObj[10].toString());
	    	if(lmtObj[11]!=null)
	    	 usgVal = CommonUtil.stringtoInt(lmtObj[11].toString());
	    	
	    	LOG.info("Frame Code is :"+lmtObj[6]+" and Limit Value is :"+lmtVal+" and Usage Value is :"+usgVal+" and Prod month is :"+lmtObj[2]);
	    	
	    	if(lmtObj[6].toString().equalsIgnoreCase(PDConstants.FEATURE_CODE_00)) {
	    		if (lmtObj[12] == null || (usgVal > lmtVal) || (usgVal < lmtVal)){
	    			//OTBM, Prod Month, Car series, Buyer group code, ocf region cd, frame code, feature code, car series desc, car series, limit, usage, diff
	    			brchRcdObj = new Object[]{lmtObj[1],lmtObj[2],lmtObj[3],lmtObj[4],lmtObj[5],lmtObj[6],lmtObj[7],lmtObj[9],lmtObj[8],lmtObj[10],lmtObj[11],lmtObj[12]};
	    			brchRcdsLstVal.add(brchRcdObj);
	    		}
	    		} else if(!lmtObj[6].toString().equalsIgnoreCase(PDConstants.FEATURE_CODE_00)) {
		    		if (lmtObj[12] == null || (usgVal > lmtVal)){
		    			brchRcdObj = new Object[]{lmtObj[1],lmtObj[2],lmtObj[3],lmtObj[4],lmtObj[5],lmtObj[6],lmtObj[7],lmtObj[9],lmtObj[8],lmtObj[10],lmtObj[11],lmtObj[12]};
		    			brchRcdsLstVal.add(brchRcdObj);
		    		} else if (lmtVal > usgVal || lmtVal == usgVal) {
		    			nonBrchRcdObj = new Object[]{lmtObj[1],lmtObj[2],lmtObj[3],lmtObj[4],lmtObj[5],lmtObj[6],lmtObj[7],lmtObj[9],lmtObj[8],lmtObj[10],lmtObj[11],lmtObj[12]};
		    			nonBrchRcdsLst.add(nonBrchRcdObj);
		    		}
		    		}
			}
	    
	    LOG.info("Record Count in brchRcdsLstVal is :"+brchRcdsLstVal.size());
	    LOG.info("Record Count in nonBrchRcdsLst is :"+nonBrchRcdsLst.size());
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return brchRcdsLstVal;
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
