/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000055.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;
import static com.nissangroups.pd.util.PDMessageConsants.M00169;
import static com.nissangroups.pd.util.PDMessageConsants.M00171;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000055.output.I000055OutputBean;
import com.nissangroups.pd.model.TmpDailyOcfLmtIf;
import com.nissangroups.pd.model.TmpDailyOcfLmtIfPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This method is to process all the extracted common interface data records
 * and insert into TmpDailyOcfLmtIf
 * 
 * @author z015895
 * 
 */
public class I000055Processor implements
		ItemProcessor<I000055OutputBean, TmpDailyOcfLmtIf> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000055Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution */
	private JobExecution jobExec;

	/** Variable Interface file Id */
	private String ifFileId;

	/** Variable PorCd String */
	private String porCd;

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

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
		this.ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		this.porCd = jobExec.getJobParameters().getString(IFConstants.POR_CD);

		String errorPath = environment
				.getProperty(IFConstants.I000055_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(
				PDConstants.DATE_TIME_FORMAT);
		String errFileName = errorPath.trim() + IFConstants.REPORT_SUFFIX_I55
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		jobExec.getExecutionContext().put(IFConstants.ERROR_FILENAME,
				errFileName);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0001,P0003,P0004 This method is to process all the extract common interface data records
	 * and set TmpDailyOcfLmtIfPK class and fetched and set to TmpDailyOcfLmtIf
	 * 
	 * @param item
	 * @return dailyOcfLmtIf
	 * @throws PdApplicationException
	 */
	@Override
	public TmpDailyOcfLmtIf process(I000055OutputBean item)
			throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Date date = new Date();
		long rowCount = commonutility.getRowCount() + 1;

		// Need to get the values from I000055OutputBean item and set it in
		// TmpDailyOcfLmtIf
		TmpDailyOcfLmtIf dailyOcfLmtIf = new TmpDailyOcfLmtIf();
		dailyOcfLmtIf.setId(new TmpDailyOcfLmtIfPK());
		dailyOcfLmtIf.getId().setPorCd(porCd);
		dailyOcfLmtIf.getId().setPlntCd(item.getCol1());
		dailyOcfLmtIf.getId().setPeriod(item.getCol2());
		dailyOcfLmtIf.getId().setCarSrs(item.getCol3());
		dailyOcfLmtIf.getId().setLineClass(item.getCol4());
		dailyOcfLmtIf.getId().setOcfIdntctnCd(item.getCol6());
		dailyOcfLmtIf.getId().setOcfRegIdntctnCd(item.getCol7());
		dailyOcfLmtIf.getId().setOcfMdlGrp(item.getCol8());				
		dailyOcfLmtIf.getId().setFrmeSrtCd(item.getCol14());	
		dailyOcfLmtIf.getId().setMapsSymbl(item.getCol15());

		dailyOcfLmtIf.setIfFileId(ifFileId);
		dailyOcfLmtIf.setFrmeCd(item.getCol5());	
		dailyOcfLmtIf.setOcfLimit(item.getCol9());		
		dailyOcfLmtIf.setIntgrtdProdCtgry(item.getCol10());
		dailyOcfLmtIf.setTargetPrd(item.getCol11());
		dailyOcfLmtIf.setMnthInWk(item.getCol12());
		dailyOcfLmtIf.setLastWkSymbl(item.getCol13());
		dailyOcfLmtIf.setPreliminary(item.getCol16());
		dailyOcfLmtIf.setUpdtdBy(ifFileId);
		dailyOcfLmtIf.setTerminalId(item.getCol18());
		dailyOcfLmtIf.setMaintainUpdDt(new Timestamp(date.getTime()));

		// check null values
		boolean result = checkNullVal(dailyOcfLmtIf);
		if (result) {
			String errMsg = M00169
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							" not null data for " + porCd)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.TBL_CMN_INTRFC_DATA);
			LOG.error(errMsg);
			commonutility.setRemarks(errMsg);
			throw new PdApplicationException(errMsg);
		}
		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return dailyOcfLmtIf;
	}

	/**
	 * 
	 * P0006 update the overall status on Common file header This method
	 * executed Each step Execution To get the count of Reader, Writer Based on
	 * the count values and write the Log.
	 * 
	 * @param stepExecution
	 *            the step execution
	 * @throws Exception
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			if (commonutility.getRemarks() == null
					|| ("").equals(commonutility.getRemarks())) {
				commonutility.setRemarks(M00076.replace(
						PDConstants.ERROR_MESSAGE_1, ""));
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,
						stepExecution.getFailureExceptions().toString()));
			} else {
				LOG.error(commonutility.getRemarks());
			}
		} else if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			String remarks = M00171
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, "data for " + porCd)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.TBL_CMN_INTRFC_DATA)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.P0006);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
			stepExecution.setExitStatus(new ExitStatus(FAILED));
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
		}// Else condition will not occur as the execution falls in anyone of
		// the about condition
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * this method used for check null values on TmpDailyOcfLmtIf
	 */
	private boolean checkNullVal(TmpDailyOcfLmtIf dailyOcfLmtIf) {

		if (dailyOcfLmtIf.getId().getPlntCd() == null
				|| dailyOcfLmtIf.getId().getPeriod() == null
				|| dailyOcfLmtIf.getId().getCarSrs() == null
				|| dailyOcfLmtIf.getId().getLineClass() == null
				|| dailyOcfLmtIf.getFrmeCd() == null
				|| dailyOcfLmtIf.getId().getOcfIdntctnCd() == null
				|| dailyOcfLmtIf.getId().getOcfRegIdntctnCd() == null
				|| dailyOcfLmtIf.getId().getOcfMdlGrp() == null
				|| dailyOcfLmtIf.getOcfLimit() == null
				|| dailyOcfLmtIf.getId().getMapsSymbl() == null
				|| dailyOcfLmtIf.getId().getFrmeSrtCd() == null				
				|| dailyOcfLmtIf.getId().getPlntCd().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getPeriod().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getCarSrs().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getLineClass().trim().isEmpty()
				|| dailyOcfLmtIf.getFrmeCd().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getOcfIdntctnCd().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getOcfRegIdntctnCd().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getOcfMdlGrp().trim().isEmpty()
				|| dailyOcfLmtIf.getOcfLimit().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getMapsSymbl().trim().isEmpty()
				|| dailyOcfLmtIf.getId().getFrmeSrtCd().trim().isEmpty()) {
			return true;
		}
		return false;
	}

}
