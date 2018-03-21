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
package com.nissangroups.pd.b000050.processor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.repository.UpdateLgclPipelineRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

import static com.nissangroups.pd.util.PDConstants.*;

import com.nissangroups.pd.b000050.util.B000050CommonUtil;
import com.nissangroups.pd.b000050.util.B000050Constants;
import com.nissangroups.pd.exception.PdApplicationException;

/**
 * This class is used to update logical pipeline details  for the batch B000050.
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000050Constants.UPDT_LGCL_PIPLNE_PROCESSOR)
public class UpdateLgclPipLnProcessor implements ItemProcessor<Long, String> { 
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(UpdateLgclPipLnProcessor.class);
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param process type. */
	String jobParamPrcsTyp = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable table name. */
	String tblNm = null;
	
	/** Variable parameterised pipeline order horizon value */
	String prmtrPipLnOrdrHrznVal = null;
	
	/** Order take base period related variables */
	String ordrTkBsMnthDtl = null;
	String ordrTkBsWkNoDtl = null;
	String ordrTkBsPrd = null;
	String stgClsOrdrTkBsMnthDtl = null;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable job execution. */
	private JobExecution jobExecution;
	
	@Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;
	
	@Autowired(required = false)
	private UpdateLgclPipelineRepository updtPipLnRepo;
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		this.stepExecution = stepExecution;
        jobExecution= this.stepExecution.getJobExecution(); 
        
        JobParameters jobParameters = jobExecution.getJobParameters();
		jobParamPor = jobParameters.getString(PDConstants.PRMTR_PORCD);
        jobParamPrcsTyp = jobParameters.getString(B000050Constants.PRMTR_PRCS_TYP);
        jobParamStgCd = jobParameters.getString(B000050Constants.PRMTR_STAGE_CD);
        
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public String process(Long rcdCnt) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		validateOrdrTkBsMnthCnt(rcdCnt);
		
		List<String> prdnPrdDtls = new ArrayList<String>();
		List<Object> trnLtstMstSchPrdnOrdNos = new ArrayList<Object>();
		List<Object> trnLgclPipLnPrdnOrdNos = new ArrayList<Object>();
		List trnLtstMstSchPrdnMnths = new ArrayList();
		String maxPrdnMnth = null;
		
		LOG.info("Job inputs --> POR Cd is   : "+jobParamPor+ " and Stage Code  is :" +jobParamStgCd +" and process type flag is : "+jobParamPrcsTyp);
		
		/** Process Id - P0002 */
		prmtrPipLnOrdrHrznVal = extractPrmtrPipLnOrdrHrznVal(jobParamPor);
		
		calculateOrdTkBsDtls();
		
		/** Process Id - P0002.1 */
		if(!prmtrPipLnOrdrHrznVal.equalsIgnoreCase(B000050Constants.HORIZON_INFINITY)) 
		prdnPrdDtls = calculatePrdnPrd(prmtrPipLnOrdrHrznVal);
		
		/** Process Id - P0003 */
		List<Object[]> trnLtstMstSchdlDtls = updtPipLnRepo.fetchTrnMstShdDtls(jobParamPor,ordrTkBsMnthDtl,ordrTkBsWkNoDtl,prmtrPipLnOrdrHrznVal,prdnPrdDtls);
		
		for(Object[] obj : trnLtstMstSchdlDtls){
			trnLtstMstSchPrdnOrdNos.add(obj[5]);
			trnLtstMstSchPrdnMnths.add(obj[1]);
		}
		
		if(!trnLtstMstSchPrdnMnths.isEmpty() && trnLtstMstSchPrdnMnths.size() > 0) {
			Collections.sort(trnLtstMstSchPrdnMnths);
			maxPrdnMnth = trnLtstMstSchPrdnMnths.get(trnLtstMstSchPrdnMnths.size() - 1).toString();
			}
		
		LOG.info("maxPrdnMnth is : "+maxPrdnMnth);
		
		/** Process Id - P0004.2.1 */
		for(Object[] trnLtstMstSchdlDtlsRcd : trnLtstMstSchdlDtls) {
			boolean updtdPrdnOrdr = updtPipLnRepo.saveLgclTrnRcds(trnLtstMstSchdlDtlsRcd,ordrTkBsMnthDtl,ordrTkBsWkNoDtl,jobParamPrcsTyp,stgClsOrdrTkBsMnthDtl); 
			/** Process Id - P0004.2.3 */
			if(!updtdPrdnOrdr)
			updtPipLnRepo.insertLgclPipLnTrn(trnLtstMstSchdlDtlsRcd, ordrTkBsMnthDtl, ordrTkBsWkNoDtl, jobParamPrcsTyp,stgClsOrdrTkBsMnthDtl);
		}
		
		/** Process Id - P0004.1 */
		 trnLgclPipLnPrdnOrdNos = updtPipLnRepo.fetchTrnLgclPipeLnDtls(jobParamPor,trnLtstMstSchPrdnOrdNos,ordrTkBsPrd,maxPrdnMnth);
		
		List<Object> ordrDelUpdt = new ArrayList<>(trnLgclPipLnPrdnOrdNos);
		ordrDelUpdt.removeAll(trnLtstMstSchPrdnOrdNos);
		
		/** Process Id - P0004.2.2 */
		for(int i = 0 ; i < ordrDelUpdt.size(); i ++) {
			updtPipLnRepo.updateOrdrDelFlagVal(jobParamPor,ordrDelUpdt,ordrTkBsPrd,maxPrdnMnth);
		} 
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return ordrTkBsPrd; 
	}
	
	/**
	 * @param rcdCnt
	 * 
	 * If extracted Order take base month count is not equals one, batch will be stopped
	 */
	private void validateOrdrTkBsMnthCnt(Long rcdCnt) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (jobParamPrcsTyp.equalsIgnoreCase(PDConstants.MONTHLY)) {
			tblNm = PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD;
		} else if (jobParamPrcsTyp.equalsIgnoreCase(PDConstants.WEEKLY)) {
			tblNm = B000050Constants.TBL_NM_WKLY_ORDER_TAKE_BASE_PERIOD;
		}
		
		if (rcdCnt.intValue() == 0) {
			String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,jobParamStgCd,jobParamPor,tblNm};
        	B000050CommonUtil.logMessage(PDMessageConsants.M00159, B000050Constants.P0001_1, messageParams);
		} else if (rcdCnt.intValue() > 1) {
			String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,jobParamPor,jobParamStgCd,PDConstants.CF_CONSTANT_C};
        	B000050CommonUtil.logMessage(PDMessageConsants.M00231, B000050Constants.P0001_2, messageParams);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 *  This method id used to calculate the order take base period 
	 */
	private void calculateOrdTkBsDtls() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/** Process Id - P0001.1, 1.3 */
		String extOrdrTkBsMnth = updtPipLnRepo.extractOrdrTkBsMnthDtl(jobParamPor,jobParamStgCd,jobParamPrcsTyp);
		
		/** Process Id - P0001.2 */
		stgClsOrdrTkBsMnthDtl = updtPipLnRepo.extractClsOrdrTkBsMnthDtl(jobParamPor);
		
		// Modified for Defect # 3493
		if(stgClsOrdrTkBsMnthDtl == null||stgClsOrdrTkBsMnthDtl==""){
			String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,PDConstants.CONSTANT_SC,jobParamPor,PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD};
        	B000050CommonUtil.logMessage(PDMessageConsants.M00159, B000050Constants.P0001_1, messageParams);
		}
		
		if(extOrdrTkBsMnth.length() == 6) {
			ordrTkBsMnthDtl = extOrdrTkBsMnth;
			ordrTkBsWkNoDtl = PDConstants.WEEK_SUFFIX;
			ordrTkBsPrd = ordrTkBsMnthDtl.concat(ordrTkBsWkNoDtl);
		}
			else {
				ordrTkBsMnthDtl = extOrdrTkBsMnth.substring(0, 6);
				ordrTkBsWkNoDtl = extOrdrTkBsMnth.substring(6, 7);
				ordrTkBsPrd = extOrdrTkBsMnth;
			}
		
		LOG.info("ordrTkBsMnthDtl : "+ordrTkBsMnthDtl+ " and ordrTkBsWkNoDtl  is : " +ordrTkBsWkNoDtl +" and ordrTkBsPrd is : "+ordrTkBsPrd+" and stgClsOrdrTkBsMnthDtl is : "+stgClsOrdrTkBsMnthDtl);
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}


	/**
	 * @param pipLnOrdrHrzn
	 * @return
	 * @throws ParseException
	 * 
	 * This method is used to calculate the production period
	 */
	private List<String> calculatePrdnPrd(String pipLnOrdrHrzn) throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<String> prdnMnthsList = new ArrayList<String>();
		List<String> pdMnthWkDtlsLst = new ArrayList<String>();
		
		// Modified for Defect # 3501
		if(pipLnOrdrHrzn.equalsIgnoreCase(PDConstants.CONSTANT_ONE))
			pipLnOrdrHrzn = PDConstants.CONSTANT_TWO;
		
		prdnMnthsList = CommonUtil.getProductionMonths(pipLnOrdrHrzn, ordrTkBsMnthDtl); 
		
		for(String prdMnth : prdnMnthsList){
			List<String> prdnMnthWkDtls=updtPipLnRepo.getWeekNumDtls(jobParamPor,prdMnth);
			if (!prdnMnthWkDtls.isEmpty()){
			LOG.info("Production Month : "+prdMnth+ " has "+prdnMnthWkDtls.size() + " weeks");
			pdMnthWkDtlsLst.addAll(prdnMnthWkDtls);
			} else
				LOG.info("Production Month : "+prdMnth+ " has NO week");
		}
		
		Iterator<String> iterMnth = pdMnthWkDtlsLst.iterator();
		
		while(iterMnth.hasNext()){
			String prdnPrdDtl = iterMnth.next();
			
			Date ordrTkBsPrdMnth = CommonUtil.convertStringToDate(ordrTkBsPrd);
			Date prdnPrdPrdMnth = CommonUtil.convertStringToDate( prdnPrdDtl);
			
			if(prdnPrdPrdMnth.compareTo(ordrTkBsPrdMnth) < 0)
				iterMnth.remove();
		}
		
		LOG.info("VALID production period list size is  :"+pdMnthWkDtlsLst.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return pdMnthWkDtlsLst;
	}


	/**
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to get the pipeline order horizon value. 
	 * @throws PdApplicationException 
	 */
	private String extractPrmtrPipLnOrdrHrznVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		/** Process Id - P0002  Extract Order horizon details*/
		
		if(prmtrPipLnOrdrHrznVal== null){
			
			prmtrPipLnOrdrHrznVal = prmtrMstRepo.fetchValue1(B000050Constants.PIPELINE_ORDER_HRZN, porCd, jobParamPrcsTyp.toUpperCase());
			
			if(prmtrPipLnOrdrHrznVal!= null && !prmtrPipLnOrdrHrznVal.isEmpty())
			 LOG.info("Extracted Pipeline Parameter Order horizon value is  : " +prmtrPipLnOrdrHrznVal);
			 else {
				 	String[] messageParams = {B000050Constants.BATCH_50_ID_MSG,porCd};
	            	B000050CommonUtil.logMessage(PDMessageConsants.M00090, PDConstants.P0002, messageParams);
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return prmtrPipLnOrdrHrznVal;
	}
	
	@AfterStep
	public void afterStep (StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info(READ_COUNT + stepExecution.getReadCount());
	
		 if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			LOG.error(PDMessageConsants.M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
}