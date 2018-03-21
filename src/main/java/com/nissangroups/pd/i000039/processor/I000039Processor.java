/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.processor;

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

import com.nissangroups.pd.i000039.output.I000039OutputBean;

import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;

import com.nissangroups.pd.util.IFConstants;

import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class I000039Processor is to process the extracted Monthly & Weekly
 * Schedule data from Reader and insert the data into Common Interface data
 * 
 * @author z015895
 * 
 */
public class I000039Processor implements
		ItemProcessor<I000039OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000039Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private JobExecution jobExec;

	/** Variable spec mst. */
	private CmnInterfaceData cmnIfData;

	/**
	 * This method will be called just before each step execution Get
	 * stepExecution and assign into instance variable
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * P0003.2,P0004 Extraction of Monthly production order data from the
	 * MONTHLY_PRODUCTION_ORDER_TRN table and Inserting Monthly Production Order
	 * transaction master data into the Common Layer table.
	 * 
	 * @param item
	 * @throws Exception
	 */
	@Override
	public CmnInterfaceData process(I000039OutputBean item) throws Exception {

		LOG.info("HEllo inside process() :::::::::");
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);
		
		LOG.info("seqNo inside process() :" + seqNo);

		long rowCount = commonutility.getRowCount() + 1;

		cmnIfData = new CmnInterfaceData();
		CmnInterfaceDataPK cmnInterfaceDataPK = new CmnInterfaceDataPK();
		cmnInterfaceDataPK.setIfFileId(ifFileId);
		cmnInterfaceDataPK.setSeqNo(seqNo);
		cmnInterfaceDataPK.setRowNo(rowCount);
		cmnIfData.setId(cmnInterfaceDataPK);
		cmnIfData.setId(new CmnInterfaceDataPK());
		cmnIfData.getId().setSeqNo(seqNo);
		cmnIfData.getId().setRowNo(rowCount);
		cmnIfData.getId().setIfFileId(ifFileId);
		CmnFileHdr fileHdr = new CmnFileHdr();
		CmnFileHdrPK cmnFileHdrPK = new CmnFileHdrPK();
		cmnFileHdrPK.setIfFileId(ifFileId);
		cmnFileHdrPK.setSeqNo(seqNo);
		fileHdr.setId(cmnFileHdrPK);

		cmnIfData.setCmnFileHdr(fileHdr);

		// cmnIfData.setCol1(ordrTkMnth);
		cmnIfData.setCol1(item.getProdFamlyCd());
		cmnIfData.setCol2(item.getCarSrs());
		cmnIfData.setCol3(item.getModelCd());
		cmnIfData.setCol4(item.getExtClr());
		cmnIfData.setCol5(item.getIntClr());
		cmnIfData.setCol6(item.getExNo());
		cmnIfData.setCol7(item.getSpecDstnCd());
		cmnIfData.setCol8(" ");
		cmnIfData.setCol9(item.getPrdMnth());
		cmnIfData.setCol10(item.getOcfRegion());
		cmnIfData.setCol11(item.getProdSalesSpec());
		cmnIfData.setCol12(item.getOrdrQty());

		cmnIfData.setCol13(item.getProdOrdrNo());

		cmnIfData.setCol14(item.getSlsNote());
		cmnIfData.setCol15(item.getAdtnlSpec());
		cmnIfData.setCol16(item.getByrCd());
		cmnIfData.setCol17(" ");

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;

	}

	/**
	 * P0005 Update the Overall Status in Common File Header 
	 * This method gets executed after each step Execution To get the count of
	 * Reader, Writer Based on the count values write the Log.
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
					PDConstants.ERROR_MESSAGE_1, ""));
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
		}////Else condition will not occur as the execution falls in anyone of the about condition 
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
