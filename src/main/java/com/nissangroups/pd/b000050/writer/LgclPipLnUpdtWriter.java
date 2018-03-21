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

package com.nissangroups.pd.b000050.writer;

import static com.nissangroups.pd.util.PDConstants.*;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.model.MstWklyOrdrTakeBase;
import com.nissangroups.pd.repository.UpdateLgclPipelineRepository;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.b000050.util.B000050Constants;

/**
 * The Class B000050Writer.
 */
@Component(B000050Constants.B000050WRITER)
public class LgclPipLnUpdtWriter implements ItemWriter<MstWklyOrdrTakeBase> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(LgclPipLnUpdtWriter.class);
	
	/** Variable jobExecution. */
    private JobExecution jobExecution;
    
    /** Variable stepExecution. */
    private StepExecution stepExecution; 
    
    /** Variable job por code */
	String jobParamPor = null;
	
	/** Variable job param process type. */
	String jobParamPrcsTyp = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable table name. */
	String tblNmVal = null;
	
	@Autowired(required = false)
	private UpdateLgclPipelineRepository updtPipLnRepo;
	
	
	 /**
	 * @param stepExecution
	 * @throws ParseException
	 * 
	 * This method is to retrieve the batch inputs from step execution
	 */
	@BeforeStep
	    public void retrieveInterstepData(StepExecution stepExecution)
	            throws ParseException {
		   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

	        this.stepExecution = stepExecution;
	        
	        jobExecution= this.stepExecution.getJobExecution(); 
	       JobParameters jobParameters = jobExecution.getJobParameters();
	       
	        jobParamPor = jobParameters.getString(PDConstants.PRMTR_PORCD);
	        jobParamPrcsTyp = jobParameters.getString(B000050Constants.PRMTR_PRCS_TYP);
	        jobParamStgCd = jobParameters.getString(B000050Constants.PRMTR_STAGE_CD);
	   }
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
		public void write(List ordrTkBsPrd) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			
		LOG.info("Job inputs --> POR Cd is   : "+jobParamPor+ " and Stage Code  is :" +jobParamStgCd +" and process type flag is : "+jobParamPrcsTyp);
		
		LOG.info("The value from: processor to  Writer:"+ordrTkBsPrd);
		
		/** Process Id - P0005 */
		updtPipLnRepo.updtStgComplSts(jobParamPor,jobParamPrcsTyp, ordrTkBsPrd.get(0));
			
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		}
		
}
