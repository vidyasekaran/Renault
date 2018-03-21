/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-10-2015  	  z014433(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.b000021.writer;

import static com.nissangroups.pd.util.PDConstants.*;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000021.bean.MstLckCnfgFlgDtls;
import com.nissangroups.pd.repository.MnthlyStgOpnClsRepository;
import com.nissangroups.pd.util.PDConstants;


/**
 * The Class B000021Writer.
 */
@Component(B000021WRITER)
public class MstMnthlyOrdrTkBsPrdWriter implements ItemWriter<MstLckCnfgFlgDtls> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(MstMnthlyOrdrTkBsPrdWriter.class);
	
	@PersistenceContext(unitName = PERSISTENCE_NAME)
    private EntityManager entityManager;

    private JobExecution jobExecution;
    
    private StepExecution stepExecution; 
    
    /** Variable job por code */
	String jobParamPor = null;
	
    /** Variable job param stage update flag. */
	String jobParamStgUpdtFlg = null;
	
	/** Variable job param update flag. */
	String jobParamUpdtOnlyFlg = null;
	
	/** Variable job param system lock flag. */
	String jobParamSysLckFlg = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable job param stage status code. */
	String jobParamStgStsCd = null;
	
	@Autowired(required = false)
	private MnthlyStgOpnClsRepository mnthOpnClsRepo;
	
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
			jobParamUpdtOnlyFlg = jobParameters.getString(PDConstants.PRMTR_UPDATE_FLAG);
			jobParamSysLckFlg = jobParameters.getString(PDConstants.PRMTR_SYSTEM_LOCK_FLAG);
			jobParamStgUpdtFlg = jobParameters.getString(PDConstants.PRMTR_STG_UPDT_FLAG);
			jobParamStgCd = jobParameters.getString(PDConstants.PRMTR_STAGE_CD);
			jobParamStgStsCd = jobParameters.getString(PDConstants.PRMTR_STAGE_STATUS_CD);
	        
	   }
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
		public void write(List<? extends MstLckCnfgFlgDtls> items) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		/** Process Id - P0005 - Update to monthly order take base period mst*/
		
			if (!items.isEmpty()) {
			
			String ordrTkBsMnthDtl = items.get(0).getOrdrTkBsMnth();
				
		   LOG.info("Job inputs Update Only Flags are  : "+jobParamUpdtOnlyFlg+ " and Stage Code  is :" +jobParamStgCd +" and stage status code is : "+jobParamStgStsCd
				+ " and system lock flag  is :" +jobParamSysLckFlg +" and stage update flag is : "+jobParamStgUpdtFlg);

		/** Process Id - P0005.2 - Update system lock flag to monthly order take base period mst*/
		if(jobParamStgUpdtFlg.equalsIgnoreCase(PDConstants.YES) && jobParamSysLckFlg.equalsIgnoreCase(PDConstants.YES))
		mnthOpnClsRepo.updateSysLockFlag(ordrTkBsMnthDtl,jobParamPor,jobParamStgCd, jobParamStgStsCd);
		
		/** Process Id - P0005.3 - Update stage and stage status code to monthly order take base period mst*/
		else if(jobParamStgUpdtFlg.equalsIgnoreCase(PDConstants.YES) && jobParamSysLckFlg.equalsIgnoreCase(PDConstants.NO) && jobParamUpdtOnlyFlg.equalsIgnoreCase(PDConstants.NO))
			mnthOpnClsRepo.updateStgStsCdDtls(ordrTkBsMnthDtl,jobParamPor,jobParamStgCd, jobParamStgStsCd);
		 }
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		//TO do error case M00164 Transaction logging - R & D needed -- Hint : batch common failure log listener exception has to be captured

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
