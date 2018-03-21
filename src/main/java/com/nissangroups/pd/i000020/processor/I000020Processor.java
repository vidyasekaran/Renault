/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000020
 * Module          :CM Common
 * Process Outline :Send OSEI Production Type Master Interface to NSC(Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000020.processor;

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
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00170;
import static com.nissangroups.pd.util.PDMessageConsants.M00225;
import static com.nissangroups.pd.util.PDMessageConsants.M00226;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000020.output.I000020OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class the processes the OSEI Production Type data based on the given input parameters sets 
 * the value to Common Interface data VO which will be inserted into Common Interface data.
 * 
 * @author z015895
 *
 */
public class I000020Processor implements
		ItemProcessor<I000020OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000020Processor.class);

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable jobExec. */
	private JobExecution jobExec;

	/** Variable cmnIfData. */
	private CmnInterfaceData cmnIfData;


	/**
	 * This method is used to get stepExecution and assign into instance
	 * variable
	 * 
	 * @param stepExecution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/* 
	 * This method processes the OSEI Production Type data based on the given input parameters and  
     * sets it to  Common Interface data VO object.
     * 
	 * (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public CmnInterfaceData process(I000020OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);

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

		cmnIfData.setCol1(item.getPorCd());
		cmnIfData.setCol2(item.getCarSeries());
		cmnIfData.setCol3(item.getAppliedModelCd());
		cmnIfData.setCol4(item.getPackCD());
		cmnIfData.setCol5(item.getBuyerCD());
		cmnIfData.setCol6(item.getSpecDestination());
		cmnIfData.setCol7(item.getAdditionalSpecCd());
		cmnIfData.setCol8(item.getExteriorColor());
		cmnIfData.setCol9(item.getInteriorColor());
		cmnIfData.setCol10(item.getProdPlantCd());
		cmnIfData.setCol11(item.getOrdTakBaseMon());
		cmnIfData.setCol12(item.getProdMon());
		cmnIfData.setCol13(item.getProdWeekNo());
		cmnIfData.setCol14(item.getProdMethodCd());
		cmnIfData.setCol15(item.getCreateUserID());
		cmnIfData.setCol16(item.getCreateDateTime());
		cmnIfData.setCol17(item.getUpdateUserID());
		cmnIfData.setCol18(item.getUpdateDateTime());

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return cmnIfData;
	}

	
	/**
	 * This method executed Each step Execution To get the count of Reader,
	 * Writer Based on the count values and write the Log.
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

		String remarks = "";
		String ifFileId = stepExecution.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		String porCdStr = stepExecution.getJobExecution().getExecutionContext()
				.get("porCdStr")
				+ "";
		String buyerGrpCdStr = stepExecution.getJobExecution()
				.getExecutionContext().get("buyerGrpCdStr")
				+ "";

		if (stepExecution.getReadCount() == 0) {

			remarks = M00170
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(
							PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_OSEI_PRODUCTION_TYPE_MST
									+ " data")
					.replace(PDConstants.ERROR_MESSAGE_3, porCdStr)
					.replace(PDConstants.ERROR_MESSAGE_4, buyerGrpCdStr)
					.replace(PDConstants.ERROR_MESSAGE_5,
							PDConstants.MESSAGE_OSEI_PRODUCTION_TYPE_MST)
					.replace(PDConstants.ERROR_MESSAGE_6,
							PDConstants.P4_PROCESS_ID);
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			remarks = M00226.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
					.replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(remarks);
			LOG.error(remarks);

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {

			remarks = M00225.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
					.replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			remarks = M00164.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
					.replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
