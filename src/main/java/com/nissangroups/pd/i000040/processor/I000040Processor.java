/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000040
 * Module                 : OR Ordering		
 * Process Outline     	  : Send A0 ETA Designated parameter to PCS															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000040.processor;

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
import static com.nissangroups.pd.i000040.util.I000040QueryConstants.M00003;
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

import com.nissangroups.pd.i000040.output.I000040OutputBean;
import com.nissangroups.pd.i000040.util.I000040QueryConstants;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 *  This class I000040Processor is to process the extracted A0 ETA Designated Parameter data 
 *  from Reader and insert the data into Common Interface data
 *
 * @author Z014676
 */

public class I000040Processor implements
		ItemProcessor<I000040OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000040Processor.class);

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
	 * P0003 Insert the extracted A0 ETA DESIGNATION PARAMETER data to given POR
	 * should be inserted in to the common layer Fetch the rowcount, sequence
	 * number, Interface file id from the context
	 * 
	 * @param item
	 * @exception Exception
	 * @return List of CmnInterfaceData
	 */
	@Override
	public CmnInterfaceData process(I000040OutputBean item) throws Exception {

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

		cmnIfData.setCol1(I000040QueryConstants.SPACE); // Factory code PCS SIDE WILL SET FACTORY CODE.	SET CONSTANT SPACE ON POST DRAGON
		cmnIfData.setCol2(item.getProdMnth());
		cmnIfData.setCol3(I000040QueryConstants.RC); // R/C Default value A0
		cmnIfData.setCol4(I000040QueryConstants.SPACE); // Country Minuts default CONSTANT value SPACE
		cmnIfData.setCol5(item.getCarSrs());
		cmnIfData.setCol6(I000040QueryConstants.SPACE); //Product Segmentation default CONSTANT value SPACE
		cmnIfData.setCol7(I000040QueryConstants.SPEC_DEFAULT_VALUE); // Specification Method default value 1
		cmnIfData.setCol8(item.getAppldMdlCd() + item.getPckCd());
		cmnIfData.setCol9(I000040QueryConstants.SPACE); // Exterior color default CONSTANT value SPACE
		cmnIfData.setCol10(I000040QueryConstants.SPACE); //Interior color default CONSTANT value SPACE
		cmnIfData.setCol11(item.getExNo());
		cmnIfData.setCol12(item.getSpecDstnCd());
		cmnIfData.setCol13(I000040QueryConstants.SPACE); //Preliminary default CONSTANT value SPACE
		if (item.getDateFrm1() == null
				|| item.getDateFrm1().equalsIgnoreCase("null")) {
			item.setDateFrm1("");
		}// else no need
		if (item.getDateTo1() == null
				|| item.getDateTo1().equalsIgnoreCase("null")) {
			item.setDateTo1("");
		}
		if (item.getQty1() == null || item.getQty1().equalsIgnoreCase("null")) {
			item.setDateFrm1("");
		}
		
		cmnIfData.setCol14(item.getDateFrm1() + item.getDateTo1()
				+ item.getQty1());

		cmnIfData.setCol15(I000040QueryConstants.PREF_ORDER_DEFAULT_VALUE); // Preferential order default value 1
		cmnIfData.setCol16(I000040QueryConstants.REASON_CODE_DEFAULT_VALUE); // reason code default value 1
		if (item.getPrdFmlyCd() == null
				|| item.getPrdFmlyCd().equalsIgnoreCase("null")) {
			item.setPrdFmlyCd("");                                           //Set empty space for Prod Family Code if value is null
		}
		cmnIfData.setCol17(item.getPrdFmlyCd());
		if (item.getOcfRegion() == null
				|| item.getOcfRegion().equalsIgnoreCase("null")) {
			item.setOcfRegion("");											//Set empty space for OCF Region if value is null
		}

		cmnIfData.setCol18(item.getOcfRegion());
		cmnIfData.setCol19(I000040QueryConstants.SPACE); // Prototype Symbol default CONSTANT value SPACE
		cmnIfData.setCol20(I000040QueryConstants.SPACE); // Order symbol default CONSTANT value SPACE
		cmnIfData.setCol21(I000040QueryConstants.SPACE); // Preliminary default CONSTANT value SPACE

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}

	/**
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
		}// Else condition will not occur as the execution falls in anyone of the about condition
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
