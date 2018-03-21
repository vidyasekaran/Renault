/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000005
 * Module          :SP Spec Master				
 * Process Outline :Receive Exterior Color master Interface from DRG-VSM													
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDConstants.DATA_INSERTED_MSG;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstExtClr;
import com.nissangroups.pd.model.MstExtClrPK;
import com.nissangroups.pd.util.PDConstants;




/**
 * 
 * This is the processor class for interface I000005 using the extracted common layer data.
 * Data manipulation will be done.
 * 
 * @author z011479
 *
 */
public class I000005Processor implements ItemProcessor<CmnInterfaceData, MstExtClr>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000005Processor.class
			.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Variable interface id. */
	private String interfaceId = null;
	
	/** Variable seq no. */
	private String seqNo = null;

	
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(PDConstants.INTERFACE_FILE_ID);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 * To Process the COMMON_INTERFACE data
	 */
	@Override
	public MstExtClr process(CmnInterfaceData cmnInterfaceData) throws Exception {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		seqNo = String.valueOf(cmnInterfaceData.getId().getSeqNo());
		MstExtClr extClr = new MstExtClr();
		MstExtClrPK extClrPK = new MstExtClrPK();
		extClrPK.setExtClrCd(cmnInterfaceData.getCol1());
		extClr.setExtClrDesc(cmnInterfaceData.getCol2());
		extClrPK.setProdFmyCd(cmnInterfaceData.getCol3());
		extClrPK.setGsisRegionGrnd(cmnInterfaceData.getCol4());
		extClrPK.setProdStageCd(cmnInterfaceData.getCol5());
		extClr.setId(extClrPK);
		LOG.info(DATA_INSERTED_MSG);
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return extClr;
	}
	
	
	/**
	 * To get the count of row affected in Each Step.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	

	
	

}
