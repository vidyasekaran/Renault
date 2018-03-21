/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.processor;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000101.output.I000101OutputBean;
import com.nissangroups.pd.i000101.processor.I000101Processor;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class processes the data received from outputbean and maps it to Common interface Data table.
 * 
 * @author z015847
 *
 */
public class I000101Processor implements org.springframework.batch.item.ItemProcessor<I000101OutputBean, CmnInterfaceData> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000101Processor.class);

	@Autowired(required = false)
	IfCommonUtil commonutility;

	private JobExecution jobExec;

	/** Variable spec mst. */
	private CmnInterfaceData cmnIfData;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * P0004:Inserting Physical pipeline data into the Common Layer data table
	 */
	@Override
	public CmnInterfaceData process(I000101OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);
		/** check null values or not below condition */
		if (item.getLgclPplnSeqId() == null || item.getVinNo() == null
				|| item.getPorCd() == null || item.getProdOrdrNo() == null
				|| item.getProdPlntCd() == null
				|| item.getProductionFamilyCd() == null
				|| item.getCarSrs() == null || item.getAppldMdlCd() == null
				|| item.getPckCd() == null || item.getExtClrCd() == null
				|| item.getIntClrCd() == null || item.getBuyerCd() == null
				|| item.getAdtnlSpecCd() == null
				|| item.getSpecDestnCd() == null || item.getPotCd() == null
				|| item.getSalesNoteNo() == null
				|| item.getMsOfflineDate() == null
				|| item.getLineClass() == null || item.getExNo() == null
				|| item.getPrtypeVhclFlag() == null
				|| item.getIntrnlOrTrdFlag() == null
				|| item.getProdMnth() == null || item.getVinAllctFlag() == null) {
			LOG.info(" Record skipped as mandatory fileds have null value");
			return null;
		} else {

			/** Variable rowCount */
			long rowCount = commonutility.getRowCount() + 1;
			/** Variable Common Interface data */
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

			cmnIfData.setCol1(item.getLgclPplnSeqId());
			cmnIfData.setCol2(item.getVinNo());
			cmnIfData.setCol3(item.getPorCd());
			cmnIfData.setCol4(item.getProdOrdrNo());
			cmnIfData.setCol5(item.getProdPlntCd());
			cmnIfData.setCol6(item.getProductionFamilyCd());
			cmnIfData.setCol7(item.getCarSrs());
			cmnIfData.setCol8(item.getAppldMdlCd());
			cmnIfData.setCol9(item.getPckCd());
			cmnIfData.setCol10(item.getExtClrCd());
			cmnIfData.setCol11(item.getIntClrCd());
			cmnIfData.setCol12(item.getBuyerCd());
			cmnIfData.setCol13(item.getAdtnlSpecCd());
			cmnIfData.setCol14(item.getSpecDestnCd());
			cmnIfData.setCol15(item.getPotCd());
			cmnIfData.setCol16(item.getSalesNoteNo());
			cmnIfData.setCol17(item.getMsOfflineDate());
			cmnIfData.setCol18(item.getPlnndSetupDate());
			cmnIfData.setCol19(item.getPlnndMetalOkDate());
			cmnIfData.setCol20(item.getPlnndPaintInDate());
			cmnIfData.setCol21(item.getPlnndFinalOkDate());
			cmnIfData.setCol22(item.getPlnndTrimInDate());
			cmnIfData.setCol23(item.getPlnndOfflnDate());
			cmnIfData.setCol24(item.getPlnndFinalPassDate());
			cmnIfData.setCol25(item.getPlnndFinalOkDate());
			cmnIfData.setCol26(item.getPlnndDlvryDate());
			cmnIfData.setCol27(item.getActlSetupDate());
			cmnIfData.setCol28(item.getActlMetalOkDate());
			cmnIfData.setCol29(item.getActlPaintInDate());
			cmnIfData.setCol30(item.getActlPaintOkDate());
			cmnIfData.setCol31(item.getActlTrimInDate());
			cmnIfData.setCol32(item.getActlOfflnDate());
			cmnIfData.setCol33(item.getActlFinalPassDate());
			cmnIfData.setCol34(item.getActlFinalOkDate());
			cmnIfData.setCol35(item.getActualDeliveryDate());
			cmnIfData.setCol36(item.getInspctnDate());
			cmnIfData.setCol37(item.getScrpdDate());
			cmnIfData.setCol38(item.getLineClass());
			cmnIfData.setCol39(item.getExNo());
			cmnIfData.setCol40(item.getEngType());
			cmnIfData.setCol41(item.getEngNo());
			cmnIfData.setCol42(item.getPrtypeVhclFlag());
			cmnIfData.setCol43(item.getIntrnlOrTrdFlag());
			cmnIfData.setCol44(item.getProdMnth());
			cmnIfData.setCol45(item.getVinAllctFlag());

			commonutility.setRowCount(rowCount);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

			return cmnIfData;
		}

	}

	/**
	 * P0005 update the common interface data This method executed Each step
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
		LOG.info(READ_SKIPPED_COUNT
				+ (stepExecution.getReadCount() - stepExecution.getWriteCount()));
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());
		// update the message status into common file header
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
			if (stepExecution.getReadCount() != 0
					&& stepExecution.getWriteCount() != 0) {
				commonutility
						.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
				commonutility.setRemarks(M00113
						+ ", Total Inserted count is : "
						+ stepExecution.getWriteCount()
						+ ", Skipped records count is : "
						+ (stepExecution.getReadCount() - stepExecution
								.getWriteCount()));
				LOG.info(M00113);
			} else {
				commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
				commonutility.setRemarks(M00043);
				LOG.error(M00043);
			}
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
