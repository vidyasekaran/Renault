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
import com.nissangroups.pd.b000047.output.B000047RmngOrdrsOutputBean;
import com.nissangroups.pd.b000047.util.B000047CommonUtiltyService;
import com.nissangroups.pd.b000047.util.B000047Constants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class B000047RmngVinLstProcessor is to process the extracted records from Logical Pipeline Trn where VIN_NO not allocated and generate report
 *
 * @author z016127
 */
public class B000047RmngOrdrsLstProcessor implements ItemProcessor<B000047RmngOrdrsOutputBean, String> { 
	
	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(B000047RmngOrdrsLstProcessor.class);
	
	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable job execution. */
	private JobExecution jobExecution;
	
	@Autowired(required = false)
	private B000047CommonUtiltyService commonUtil;
	
	private String ordrTkBsPrd = "";
	/**
	 * B000047QueryOutputVO bean injection
	 */
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
        
		String errorPath = environment
				.getProperty(B000047Constants.B000047_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(
				PDConstants.DATE_TIME_FORMAT);
		String errFileName = errorPath.trim() + B000047Constants.VIN_ERROR_REPORT_SUFFIX
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		jobExecution.getExecutionContext().put(B000047Constants.VIN_ERROR_FILENAME,
				errFileName);
		
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/** 
	 * P0007 This method is to process the extracted records from Logical Pipeline Trn where VIN_NO not allocated
	 * and generate report
	 * 
	 * @param rmngOrdrs 
	 * 					B000047RmngOrdrsOutputBean
	 * @return string
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public String process(B000047RmngOrdrsOutputBean rmngOrdrs) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/**P0008 Report generation */
		generateReportList(rmngOrdrs);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return ordrTkBsPrd; 
	}
	
	/**
	 * P0008 Method to extract records from Logical Pipeline Trn where VIN_NO not allocated and generate report.
	 * 
	 * @param rmngOrdrs
	 * 					B000047RmngOrdrsOutputBean
	 * @return B000047ReportDao
	 */
	public B000047ReportDao generateReportList(B000047RmngOrdrsOutputBean rmngOrdrs){
		
		B000047ReportDao reportDao = new B000047ReportDao();
		
		reportDao.setWarngCd(B000047Constants.warngCd);
		reportDao.setPorCd(rmngOrdrs.getPorCd());
		reportDao.setOcfRgnCd(rmngOrdrs.getOcfRegionCd());
		reportDao.setProdMnth(rmngOrdrs.getProdMnth());
		reportDao.setCarSrs(rmngOrdrs.getCarSrs());
		reportDao.setBuyerCd(rmngOrdrs.getBuyerCd());
		reportDao.setOrdrProdWkNo(rmngOrdrs.getProdWkNo());
		reportDao.setVinProdWkNo("");
		reportDao.setProdOrdrNo(rmngOrdrs.getProdOrdrNo());
		reportDao.setVinNo("");
		reportDao.setEndItem(rmngOrdrs.getAppldMdlCd() + rmngOrdrs.getPckCd());
		reportDao.setColorCd(rmngOrdrs.getExtClrCd() + rmngOrdrs.getIntClrCd());
		reportDao.setSpecDestnCd(rmngOrdrs.getSpecDestnCd());
		reportDao.setSalesNteNo("");
		reportDao.setExNo(rmngOrdrs.getExNo());
		reportDao.setOrdrOfflnDte_YYYY_MM_DD(rmngOrdrs.getOfflnPlanDate());
		reportDao.setVinOfflnDte_YYYY_MM_DD("");
		reportDao.setOrdrPlntCd(rmngOrdrs.getProdPlntCd());
		reportDao.setVinPlntCd("");
		reportDao.setWarngMsg(B000047Constants.warngMsg +queryOutputVO.getOfflnDate() + B000047Constants.warngMsg2 + queryOutputVO.getCalculatdDate());
		
		listReportDao.getRmngOrdrsReportList().add(reportDao);
		
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