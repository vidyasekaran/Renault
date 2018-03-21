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
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.b000047.util.B000047Constants.M00325;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.nissangroups.pd.b000047.output.B000047PipelineTrnDtls;
import com.nissangroups.pd.b000047.output.B000047QueryOutputVO;
import com.nissangroups.pd.b000047.util.B000047CommonUtiltyService;
import com.nissangroups.pd.b000047.util.B000047Constants;
import com.nissangroups.pd.model.TrnLgclPpln;
import com.nissangroups.pd.model.TrnPhysclPpln;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class B000047LgclPipLnProcessor is used to get the pipeline transaction records and add it to the list.
 *
 * @author z016127
 */
public class B000047LgclPipLnProcessor implements ItemProcessor<TrnLgclPpln, String> { 
	
	/** Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(B000047LgclPipLnProcessor.class);
	
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
	
	/** Environment bean injection. */
	@Autowired(required = false)
	Environment environment;
	
	/** Variable pipeline transaction details */
	private List<B000047PipelineTrnDtls> pplnTrnDtlsList;  
	
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
		
		/**P0005.4 Report path and Report File name */
		String errorPath = environment
				.getProperty(B000047Constants.B000047_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(
				PDConstants.DATE_TIME_FORMAT);
		String errFileName = errorPath.trim() + B000047Constants.VIN_PPLN_DIFF_REPORT_SUFFIX
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		jobExecution.getExecutionContext().put(B000047Constants.VIN_PPLN_DFF_ERR_FLENME,
				errFileName);
        
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/** 
	 * P0005.2 This method is to process the extracted logical pipeline records 
	 * and generate new list based on the case.
	 * 
	 * @param TrnLgclPpln 
	 * 						the logical pipeline
	 * @return string
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public String process(TrnLgclPpln lgclPplnRcrd) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<String> allctnOptns = new ArrayList<String>();
		TrnPhysclPpln trnPhysclPpln = null;
		
		/** P0004 To get the Allocation/Error list from Parameter Values */
		commonUtil.getAllctnOrErrorLst();

		/** Allocation Options */
		allctnOptns = queryOutputVO.getAllocationOptions();
		
		for(String optns : allctnOptns){
			/** P0005.2 Add pipeline data based on the case*/
			switch(optns){
			
			case "1":
				trnPhysclPpln = commonUtil.getPhsclPplnTrnDtlsForCase1(lgclPplnRcrd);
				addPipelineTrn(lgclPplnRcrd,trnPhysclPpln);
				break;
				
			case "2":
				trnPhysclPpln = commonUtil.getPhsclPplnTrnDtlsForCase2(lgclPplnRcrd);
				addPipelineTrn(lgclPplnRcrd,trnPhysclPpln);
				break;
			
			case "3":
				trnPhysclPpln = commonUtil.getPhsclPplnTrnDtlsForCase3(lgclPplnRcrd);
				addPipelineTrn(lgclPplnRcrd,trnPhysclPpln);
				break;
				
			case "4":
				trnPhysclPpln = commonUtil.getPhsclPplnTrnDtlsForCase4(lgclPplnRcrd);
				addPipelineTrn(lgclPplnRcrd,trnPhysclPpln);
				break;
				
			default:
				LOG.error(B000047Constants.ERR_MSG);
				break;
			}
		}
		queryOutputVO.setPplnTrnDtls(pplnTrnDtlsList);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return trnPplnrecrds; 
	}
	
	/**
	 * P0005.2 Add Pipeline data to the list based on the case
	 * 
	 * @param lgclPplnRcrd
	 * 					TrnLgclPpln
	 * @param trnPhysclPpln
	 * 					TrnPhysclPpln
	 */
	private void addPipelineTrn(TrnLgclPpln lgclPplnRcrd,TrnPhysclPpln trnPhysclPpln){
		B000047PipelineTrnDtls pplnTrnDtls= new B000047PipelineTrnDtls();
			pplnTrnDtls.setOcfRegionCd(trnPhysclPpln.getOcfRegionCd());
			pplnTrnDtls.setProdMnth(trnPhysclPpln.getProdMnth());
			pplnTrnDtls.setCarSrs(trnPhysclPpln.getCarSrs());
			
			//pplnTrnDtls.setBuyerCd(trnPhysclPpln.getBuyerCd());
			pplnTrnDtls.setProdWkNo(trnPhysclPpln.getProdWkNo());
			pplnTrnDtls.setProdWeekNo(lgclPplnRcrd.getProdWkNo());
			pplnTrnDtls.setProdOrdrNo(lgclPplnRcrd.getProdOrdrNo());
			pplnTrnDtls.setVinNo(trnPhysclPpln.getId().getVinNo());
			pplnTrnDtls.setEndItem(trnPhysclPpln.getAppldMdlCd() + trnPhysclPpln.getPckCd());
			pplnTrnDtls.setClrCd(trnPhysclPpln.getExtClrCd() + trnPhysclPpln.getIntClrCd());
			pplnTrnDtls.setSpecDestnCd(trnPhysclPpln.getSpecDestnCd());
			pplnTrnDtls.setSlsNteNo(lgclPplnRcrd.getSlsNoteNo());
			pplnTrnDtls.setExNo(trnPhysclPpln.getExNo());
			pplnTrnDtls.setPlndOfflnDate(trnPhysclPpln.getPlnndOfflnDate());
			pplnTrnDtls.setOffLnPlndDate(trnPhysclPpln.getOfflnPlanDate());
			pplnTrnDtls.setProdPlntCd(trnPhysclPpln.getProdPlntCd());
			pplnTrnDtls.setProdPlantCd(lgclPplnRcrd.getProdPlntCd());
			pplnTrnDtls.setOseiId(lgclPplnRcrd.getOseiId());
			pplnTrnDtls.setVhcleSeqId(lgclPplnRcrd.getVhclSeqId());
			pplnTrnDtls.setPotCd(lgclPplnRcrd.getPotCd());
			pplnTrnDtlsList.add(pplnTrnDtls);
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
		 
		if (stepExecution.getReadCount() == 0) {
				LOG.error(M00325.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
								.replace(PDConstants.ERROR_MESSAGE_2, jobParamPor)
								.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.LOGICAL));
		}else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
}