/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000047.output.B000047QueryOutputVO;
import com.nissangroups.pd.b000047.output.B000047ReportDao;
import com.nissangroups.pd.b000047.util.B000047CommonUtiltyService;
import com.nissangroups.pd.b000047.util.B000047Constants;
import com.nissangroups.pd.model.TrnPhysclPpln;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class B000047RmngVinLstProcessor is to get the Remaining Vin List not allocated to Logical Pipeline Trn from Physical Pipeline Trn Table
 *
 * @author z016127
 */
public class B000047RmngVinLstProcessor implements ItemProcessor<TrnPhysclPpln, String> { 
	
	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(B000047RmngVinLstProcessor.class);
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable job execution. */
	private JobExecution jobExecution;
	
	/** B000047CommonUtiltyService bean injection */
	@Autowired(required = false)
	private B000047CommonUtiltyService commonUtil;
	
	/** Variable trn pipeline records */
	String trnPplnrecrds ="";
	
	/** B000047QueryOutputVO bean injection */
	@Autowired(required = false)
	B000047QueryOutputVO queryOutputVO;
	
	/** B000047ReportDao bean injection */
	@Autowired(required = false)
	B000047ReportDao listReportDao;
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	/**
	 * This method will be called just before each step execution
	 * Get stepExecution and assign into instance variable
	 * 
	 * @param stepExecution 
	 * 					the step execution
	 */
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		this.stepExecution = stepExecution;
        jobExecution= this.stepExecution.getJobExecution(); 
        
        JobParameters jobParameters = jobExecution.getJobParameters();
		jobParamPor = jobParameters.getString(PDConstants.PRMTR_PORCD);
        
		/**P0005.5.3 Report path and Report File name */
		String errorPath = environment
				.getProperty(B000047Constants.B000047_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(
				PDConstants.DATE_TIME_FORMAT);
		String errFileName = errorPath.trim() + B000047Constants.UNMATHD_VIN_REPORT_SUFFIX
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		jobExecution.getExecutionContext().put(B000047Constants.UNMTCHD_VIN_ERR_FLENME,
				errFileName);
		
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/** 
	 * P0005.5.3 This method is to process the extracted logical pipeline records 
	 * and generate report.
	 * 
	 * @param TrnLgclPpln 
	 * 						the logical pipeline
	 * @return string
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public String process(TrnPhysclPpln lgclPplnRcrd) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/**P0005.5.4 To get the auto sequence number of LOGICAL_PIPE_TRN(VEHICLE_SEQ_ID)) */
		commonUtil.updateSeqIdInPhsclPipLnTrn(lgclPplnRcrd);
		
		/**P0005.5.5 Report generation */
		generateReportList(lgclPplnRcrd);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return trnPplnrecrds; 
	}
	
	/**
	 * P0005.5.5 Method to get the Remaining Vin list from Physical Pipeline Trn Table and generate report.
	 * 
	 * @param pplnTrn
	 * 					TrnPhysclPpln
	 * @return B000047ReportDao
	 */
	public B000047ReportDao generateReportList(TrnPhysclPpln pplnTrn){
		
		B000047ReportDao reportDao = new B000047ReportDao();
		
		reportDao.setWarngCd(B000047Constants.wrngCd10);
		reportDao.setPorCd(jobParamPor);
		reportDao.setOcfRgnCd(pplnTrn.getOcfRegionCd());
		reportDao.setProdMnth(pplnTrn.getProdMnth());
		reportDao.setCarSrs(pplnTrn.getCarSrs());
		//reportDao.setBuyerCd(pplnTrn.getBuyerCd());
		reportDao.setOrdrProdWkNo(pplnTrn.getProdWkNo());
		reportDao.setVinProdWkNo("");
		reportDao.setProdOrdrNo("");
		//reportDao.setVinNo(pplnTrn.get);
		reportDao.setEndItem(pplnTrn.getAppldMdlCd() + pplnTrn.getPckCd());
		reportDao.setColorCd(pplnTrn.getExtClrCd() + pplnTrn.getIntClrCd());
		reportDao.setSpecDestnCd(pplnTrn.getSpecDestnCd());
		reportDao.setSalesNteNo("");
		reportDao.setExNo(pplnTrn.getExNo());
		reportDao.setOrdrOfflnDte_YYYY_MM_DD(pplnTrn.getPlnndOfflnDate());
		reportDao.setVinOfflnDte_YYYY_MM_DD("");
		reportDao.setOrdrPlntCd(pplnTrn.getProdPlntCd());
		reportDao.setVinPlntCd("");
		reportDao.setWarngMsg(B000047Constants.wrngMsg10);
		
		listReportDao.getVinsReportList().add(reportDao);
		
		return reportDao;
		
	}
	
	/**
	 * This method gets executed after each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  write the Log.
	 *
	 * @param stepExecution the step execution
	 */
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