/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000035
 * Module                  : Ordering		
 * Process Outline     : Create Weekly order stage open															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-12-2015  	  z014433(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.b000035.writer;

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

import com.nissangroups.pd.repository.MstWklyOrdrTkBsPrdRepository;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.b000035.util.B000035Constants;


/**
 * The Class B000035Writer.
 */
@Component(B000035Constants.B000035WRITER)
public class MstWklyOrdrTkBsPrdWriter implements ItemWriter<Object> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MstWklyOrdrTkBsPrdWriter.class);
	
    private JobExecution jobExecution;
    
    private StepExecution stepExecution; 
    
    /** Variable job por code */
	String jobParamPor = null;
	
	@Autowired(required = false)
	private MstWklyOrdrTkBsPrdRepository wkOrdrTkBsPrdRepo;
	
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
	   }
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
		public void write(List itemDtl) throws Exception {
			
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String ordrTkBsMnthDtl = null;
		String ordrTkBsWkNoDtl = null;
		
		/** Process Id - P0002 - Update to monthly order take base period mst*/
		
			if (!itemDtl.isEmpty()) {
				
				for(Object objVal : itemDtl){
					Object [] wklyDtl = (Object[])objVal;
					 ordrTkBsMnthDtl = wklyDtl[0].toString();
					 ordrTkBsWkNoDtl = wklyDtl[1].toString();
				}
				
			wkOrdrTkBsPrdRepo.updateStgStsCd(jobParamPor,ordrTkBsMnthDtl,ordrTkBsWkNoDtl);
		
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
			
		}

}
