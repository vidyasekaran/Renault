/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : I000062
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000062.processor;

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

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000062.output.I000062OutputBean;
import com.nissangroups.pd.model.TrnWklyOrdrIf;
import com.nissangroups.pd.model.TrnWklyOrdrIfPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This Class extract data from CMN_FILE_HDR, COMMON LAYER DATA table and insert
 * the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 * 
 * @author z015847
 *
 */
public class I000062Processor implements
		ItemProcessor<I000062OutputBean, TrnWklyOrdrIf> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000062Processor.class);

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable job Execution . */
	private JobExecution jobExec;

	/** Variable interface file id . */
	private String ifFileId;

	/** Variable sequence number . */
	private long seqNo;

	/** Variable spec mst. */
	private TrnWklyOrdrIf trnWklyOrdrIf;

	/**
	 * This method will be called just before each step execution Get
	 * stepExecution and assign into instance variable
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		Object sequenceNo = jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);
		this.ifFileId = ifFileId;
		this.seqNo = Long.valueOf(sequenceNo + "");
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method maps the data from CMN_FILE_HDR, COMMON LAYER DATA table and
	 * maps the same to WEEKLY ORDER INTERFACE VO to insert it into WEEKLY ORDER
	 * INTERFACE TRN table.
	 */
	@Override
	public TrnWklyOrdrIf process(I000062OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Date date = new Date();
		long rowCount = commonutility.getRowCount() + 1;

		// Need to get the values from I000062OutputBean item and set it in
		// TrnWklyOrdr
		trnWklyOrdrIf = new TrnWklyOrdrIf();
		trnWklyOrdrIf.setId(new TrnWklyOrdrIfPK());
		trnWklyOrdrIf.getId().setFileId(ifFileId);
		trnWklyOrdrIf.getId().setFileSeq(Long.valueOf(seqNo + ""));
		trnWklyOrdrIf.getId().setRowNo(rowCount);

		trnWklyOrdrIf.setPorCd(item.getCol1());
		trnWklyOrdrIf.setCarSrs(item.getCol2());
		trnWklyOrdrIf.setBuyerCd(item.getCol3());
		trnWklyOrdrIf.setAppliedModelCd(item.getCol4());
		trnWklyOrdrIf.setPackCd(item.getCol5());
		trnWklyOrdrIf.setSpecDestinationCd(item.getCol6());
		trnWklyOrdrIf.setAddSpecCd(item.getCol7());
		trnWklyOrdrIf.setExteriorColorCd(item.getCol8());
		trnWklyOrdrIf.setInteriorColorCd(item.getCol9());
		trnWklyOrdrIf.setOrderTakeBasePeriodType(item.getCol10());
		trnWklyOrdrIf.setOrderTakeBasePeriod(item.getCol11());
		trnWklyOrdrIf.setProductionPeriodType(item.getCol12());
		trnWklyOrdrIf.setProductionPeriod(item.getCol13());
		trnWklyOrdrIf.setOfflnPlanDate(item.getCol14());
		trnWklyOrdrIf.setProductionOrderStageCd(item.getCol15());
		trnWklyOrdrIf.setPotCd(item.getCol16());
		trnWklyOrdrIf.setProdPlantCd(item.getCol17());
		trnWklyOrdrIf.setLineClass(item.getCol18());
		long ordrQty = (item.getCol19() == null || ("").equals(item.getCol19())) ? 0
				: Long.valueOf(item.getCol19() + "");
		trnWklyOrdrIf.setOrderQty(ordrQty);
		trnWklyOrdrIf.setDueDateFrom(item.getCol20());
		trnWklyOrdrIf.setDueDateTo(item.getCol21());
		trnWklyOrdrIf.setLocalProdOrderNo(item.getCol22());
		trnWklyOrdrIf.setProdOrderNo(item.getCol23());
		trnWklyOrdrIf.setCrtdBy(ifFileId);
		trnWklyOrdrIf.setCrtdDt(new Timestamp(date.getTime()));
		trnWklyOrdrIf.setUpdtdBy(ifFileId);
		trnWklyOrdrIf.setUpdtdDt(new Timestamp(date.getTime()));

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return trnWklyOrdrIf;
	}

	/**
	 * This method executed Each step Execution to get the count of Reader,
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

		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(
					PDConstants.ERROR_MESSAGE_1, ""));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
		} else if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}

		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		// Else condition will not occur as the execution falls in anyone of the
		// about conditions
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
