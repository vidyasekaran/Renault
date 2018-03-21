/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Batch
 * Function ID            : PST-DRG-I000106
 * Module                 : CM Common		
 * Process Outline     	  : Interface for Sending  Buyer Master to SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000106.processor;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000106.output.I000106OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 *  This class I000106Processor is to process the extracted Buyer and Buyer Group data 
 *  from Reader and insert the data into Common Interface data
 *
 * @author z016127
 */
public class I000106Processor implements
		ItemProcessor<I000106OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000106Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable JobExecution */
	private JobExecution jobExec;
	
	/** Variable Common Interface Data */
	private CmnInterfaceData cmnIfData;
	
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
		/** Variable Job Execution */
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/** 
	 * This method is to process the extracted Buyer and Buyer Group details
	 * from Master tables and insert the list into Common Interface data.
	 * Fetch the rowcount, sequence number, Interface file id from the context
	 * P0003
	 * 
	 * @param item 
	 * 				I000106OutputBean
	 * @return the list of CmnInterfaceData 
	 * 						the class
	 * @throws Exception the exception
	 */
	@Override
	public CmnInterfaceData process(I000106OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(IFConstants.SEQ_NO);

		long rowCount = commonutility.getRowCount() + 1;

		/**P0003 INSERT THE BUYER / BUYER GROUP MASTER DATA INTO THE COMMON LAYER DATA. */
		cmnIfData = new CmnInterfaceData();		
		cmnIfData.setId(new CmnInterfaceDataPK());		
		cmnIfData.getId().setSeqNo(seqNo);		
		cmnIfData.getId().setRowNo(rowCount);	
		cmnIfData.getId().setIfFileId(ifFileId);
		CmnFileHdr fileHdr = new CmnFileHdr();		
		fileHdr.setId(new CmnFileHdrPK());
		fileHdr.getId().setIfFileId(ifFileId);
		fileHdr.getId().setSeqNo(seqNo);
		cmnIfData.setCmnFileHdr(fileHdr);
		
		cmnIfData.setCol1(item.getCol1());
		cmnIfData.setCol2(item.getCol2());
		cmnIfData.setCol3(item.getCol3());
		cmnIfData.setCol4(item.getCol4());
		cmnIfData.setCol5(item.getCol5());
		cmnIfData.setCol6(item.getCol6());
		cmnIfData.setCol7(item.getCol7());

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}
	
	/**
	 * This method to be executed after each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values write the Log.
	 *
	 * @param stepExecution 
	 * 					the step execution
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
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			//write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}

}
