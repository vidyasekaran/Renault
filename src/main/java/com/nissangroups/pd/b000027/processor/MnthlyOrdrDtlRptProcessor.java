/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000010  
 * Module          :O Ordering
 * Process Outline :Monthly Production Order Detail
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 12-11-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.b000027.processor;

import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_REGION;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRDN_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;
import static com.nissangroups.pd.b000027.util.B000027Constants.CREATE_MNTHLY_ORDR_DTL_RPT;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.repository.MnthlyPrdnOrdrTrnRepository;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This is the class to generate the Monthly order detail report -- PST-DRG-R000010 
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(CREATE_MNTHLY_ORDR_DTL_RPT)
public class MnthlyOrdrDtlRptProcessor implements ItemProcessor<OrdrTkBsPrdMstRowMapper, OrdrTkBsPrdMstRowMapper> { 
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MnthlyOrdrDtlRptProcessor.class);
	

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
		
		 String reportPath = environment.getProperty(B000027Constants.B000027_REPORT_PATH);
		 
		 List<Object[]> rcds = mnthPrdnOrdrRepo.fetchPrdnOrdrDtlRptRcrds(ordrTkBsMnth,porCd);
		 
		 createPrdnOrdrDtlReport(reportPath,rcds);
		 
			 if(rcds.isEmpty()) {
				 	String[] messageParams = {B000027Constants.BATCH_27_ID,ordrTkBsMnth,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00178, B000027Constants.P0012, messageParams);
			 }
		 
		 LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return item;
	}
	
	/**
	 * @param reportPath
	 * @param itemPrdnOrdrDtlRcrds
	 * 
	 * Creates Monthly Order Detail Report for B000027
	 */
	private void createPrdnOrdrDtlReport(String reportPath,	List<Object[]> itemPrdnOrdrDtlRcrds) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		 String dirPath = reportPath;
		 
		 String fileName = B000027Constants.B000027_MNTHLY_ORDR_DTL_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
		 
		 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
		 
		 File dir = new File(dirPath);
		    if(!dir.exists()) {
		        dir.mkdir();
		    }
		    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
		    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
		    
		    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_ORDR_TK_BS_PRD, REPORT_HEADER_OCF_REGION, B000027Constants.REPORT_HEADER_MC_REGION,
		    		REPORT_HEADER_PRD_MNTH,B000027Constants.REPORT_HEADER_BYR_GRP_CD,
		    		REPORT_HEADER_BYR_CD,REPORT_HEADER_PRDN_FMLY_CD,REPORT_HEADER_CAR_GRP,REPORT_HEADER_CAR_SRS,
		    		B000027Constants.REPORT_HEADER_SPEC_DEST_CD,B000027Constants.REPORT_HEADER_END_ITEM_MDL, B000027Constants.REPORT_HEADER_EX_NO, 
		    		B000027Constants.REPORT_HEADER_ADD_SPEC_CD,REPORT_HEADER_POT,B000027Constants.REPORT_HEADER_SLS_NOTE_NO, 
		    		B000027Constants.REPORT_HEADER_COLOR,B000027Constants.REPORT_HEADER_QTY,
		    		B000027Constants.REPORT_HEADER_TYR_MKR, B000027Constants.REPORT_HEADER_TYR_SRVC, B000027Constants.REPORT_HEADER_BDY_PRTN,
		    		B000027Constants.REPORT_HEADER_OPTN_SPC_CD}); 
		    
		    		 try {
		    		    	Map<String,String> formatMap = new HashMap<String,String>();
		    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
		    		    	formatMap.put(B000027Constants.CONSTANT_THREE,B000027Constants.B000027_REPORT_DATE_FORMAT);
		    		    	excelItemWriter.createReport(itemPrdnOrdrDtlRcrds,formatMap,B000027Constants.B000027_MNTHLY_ORDR_DTL_RPT_SHEET_NM); 
		    		    } catch (IOException e) {
		    		        LOG.error(EXCEPTION+e);
		    		        
		    		    }
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
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
