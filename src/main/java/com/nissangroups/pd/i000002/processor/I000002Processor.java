/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-I000002
 * Module          :CM Common
 * Process Outline :
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *19-11-2015      z015887(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000002.processor;

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

import com.nissangroups.pd.i000002.output.I000002OutputBean;

import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 *  This class I000002Processor is to process the extracted Monthly & Weekly Schedule data 
 *  from Reader and insert the data into Common Interface data
 * @author z015847
 *
 */
public class I000002Processor implements
		ItemProcessor<I000002OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000002Processor.class);
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	// Batch domain object representing execution of a job
	private JobExecution jobExec;

	/** Variable spec mst. */
	private CmnInterfaceData cmnIfData;

	/*
	 * Marks a method to be called before a Step is executed, which comes after
	 * a StepExecution is created and persisted, but before the first item is
	 * read.
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0003: Inserting Buyer master data into the common interface data
	 * 
	 * @param totalList
	 * @return
	 */
	@Override
	public CmnInterfaceData process(I000002OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		final int buyerDescriptionLength = 7;

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);

		/** Variable row count */
		long rowCount = commonutility.getRowCount() + 1;

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

		cmnIfData.setCol1(item.getProductionRegionCD());
		cmnIfData.setCol2(item.getBuyerCD());
		cmnIfData.setCol3(item.getSpecDestinationCD());
		if ((item.getBuyerDescription() != null && item.getBuyerDescription()
				.trim().length() > buyerDescriptionLength)) {
			cmnIfData
					.setCol4(item.getBuyerDescription().trim().substring(0, (buyerDescriptionLength-1)));
		} else {
			cmnIfData.setCol4(item.getBuyerDescription());
		}
		cmnIfData.setCol5(item.getOcfRegionCD());
		cmnIfData.setCol6(item.getOcfBuyerGroupCD());
		cmnIfData.setCol7(item.getNscEimOrderHorizon());
		cmnIfData.setCol8(item.getEndOfPiplineAchievementPoint());
		cmnIfData.setCol9(item.getPreShipmentInspectionSymbol());
		cmnIfData.setCol10(item.getBuyerGroupCD());
		cmnIfData.setCol11(item.getCreateUserID());
		cmnIfData.setCol12(item.getCreateDateTime());
		cmnIfData.setCol13(item.getUpdateUserID());
		cmnIfData.setCol14(item.getUpdateDateTime());

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}

	/**
	 * P004: update the common interface data. This method executed Each step
	 * Execution To get the count of Reader, Writer Based on the count values
	 * and write the Log.
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(
					PDConstants.ERROR_MESSAGE_1, stepExecution
							.getFailureExceptions().toString()));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
