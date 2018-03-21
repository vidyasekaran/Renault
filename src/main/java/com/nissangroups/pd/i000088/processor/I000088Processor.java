/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000088
 * Module                 : Ordering		
 * Process Outline     	  : Send Weekly Production Schedule Interface to NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014029(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000088.processor;

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
import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00214;
import static com.nissangroups.pd.util.PDMessageConsants.M00225;
import static com.nissangroups.pd.util.PDMessageConsants.M00226;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000088.output.I000088OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class I000088Processor is to process extracted details of the
 * I000088OutputBean file with the given POR CD and insert into common interface
 * data table P0003,P0004,P0005
 * 
 * @author z014029
 */
public class I000088Processor implements
		ItemProcessor<I000088OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000088Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	private JobExecution jobExec;

	/** Variable OCF Limit Check */
	String ocfLmtChk = null;

	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	/** Variable spec mst. */
	private CmnInterfaceData cmnIfData;

	/**
	 * This method will be called just before each step execution Get interface
	 * id and por code from context and assign into instance variable
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		Object maxOdrTk = stepExecution.getJobExecution().getExecutionContext()
				.get(IFConstants.MAX_ORDR_TAKBAS_MNTH);
		if (("").equals(maxOdrTk)) {
			LOG.error(commonutility.getRemarks());
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is process extracted the weekly production schedule data and
	 * insert into common interface data P0003,P0004
	 * 
	 * @param item
	 * @return cmnIfData
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public CmnInterfaceData process(I000088OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);
		// Adding the Row Count Value
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
		cmnIfData.setCol2(item.getCarSrs());
		cmnIfData.setCol3(item.getBuyerCd());
		cmnIfData.setCol4(item.getAppldMdlCd());
		cmnIfData.setCol5(item.getPckCd());
		cmnIfData.setCol6(item.getSpecDestnCd());
		cmnIfData.setCol7(item.getAddSpecCd());
		cmnIfData.setCol8(item.getExtClrCd());
		cmnIfData.setCol9(item.getIntClrCd());
		cmnIfData.setCol10(item.getPotCd());
		cmnIfData.setCol11(item.getProdPlntCd());
		cmnIfData.setCol12(item.getLineClass());

		// Calculating Order Take Base Period Type and setting into column 13
		if ("B".equals(item.getProdMthdCd())) {
			cmnIfData.setCol13(PDConstants.CF_CONSTANT_W);
		} else if ("C".equals(item.getProdMthdCd())) {
			cmnIfData.setCol13(PDConstants.CF_CONSTANT_M);
		}

		cmnIfData.setCol14(item.getOrdrTakeBaseMnth()
				+ item.getOrdrTakeBaseWkNo());

		// Calculating Production Period Type and setting into column 15
		if ("B".equals(item.getProdMthdCd())) {
			cmnIfData.setCol15(PDConstants.CF_CONSTANT_W);
		} else if ("C".equals(item.getProdMthdCd())) {
			cmnIfData.setCol15(PDConstants.CF_CONSTANT_M);
		}

		cmnIfData.setCol16(item.getProdMnth() + item.getProdWkNo());
		cmnIfData.setCol17(item.getOfflnPlanDate());
		cmnIfData.setCol18(CommonUtil.bigDecimaltoString(item.getOrdrQty()));

		// Calculating Monthly Fixed Flag and setting into column 19
		if ("B".equals(item.getProdMthdCd())) {
			cmnIfData.setCol19(PDConstants.CONSTANT_ZERO);
		} else if ("C".equals(item.getProdMthdCd())) {
			cmnIfData.setCol19(PDConstants.CONSTANT_ONE);
		}

		cmnIfData.setCol20(item.getProdMthdCd());
		cmnIfData.setCol21(item.getFrznTypeCd());
		cmnIfData.setCol22(item.getLocalProdOrdrNo());
		cmnIfData.setCol23(item.getProdOrdrNo());
		cmnIfData.setCol24(item.getExNo());
		cmnIfData.setCol25(item.getProdFmyCd());
		cmnIfData.setCol26(item.getSlsNoteNo());
		cmnIfData.setCol27(item.getFxdSymbl());
		cmnIfData.setCol28(item.getWkNoOfYear());

		// Calculating Next Order Take Period Type and setting into column 29
		if (item.getOrdrTakeBaseMnth().equals(item.getProdMnth())) {
			cmnIfData.setCol29(PDConstants.CF_CONSTANT_W);
		} else {
			cmnIfData.setCol29(PDConstants.CF_CONSTANT_M);
		}

		cmnIfData.setCol30(item.getOrdrTakeBaseMnth()
				+ item.getOrdrTakeBaseWkNo());

		// Calculating Production Day No
		String prdDyNo = String.valueOf((((CommonUtil.stringtoInt(item
				.getProdWkNo()) - 1) * 7) + CommonUtil.stringtoInt(item
				.getProdDayNo())));
		// Setting the Production Day No
		cmnIfData.setCol31(prdDyNo);
		
		/*
		 * cmnIfData.setCol32(ifFileId);
		 * cmnIfData.setCol33(CommonUtil.converDateToFormat(new
		 * Timestamp(date.getTime()),PDConstants.DATE_TIME_FORMAT));
		 * cmnIfData.setCol34(ifFileId);
		 * cmnIfData.setCol35(CommonUtil.converDateToFormat(new
		 * Timestamp(date.getTime()),PDConstants.DATE_TIME_FORMAT));
		 */
		cmnIfData.setCol32(item.getCrtdBy());
		if (item.getCrtdDt() != null) {
			cmnIfData.setCol33(CommonUtil.converDateToFormat(item.getCrtdDt(),
					PDConstants.DATE_TIME_FORMAT));
		}
		cmnIfData.setCol34(item.getUpdtdBy());
		if (item.getUpdtdDt() != null) {
			cmnIfData.setCol35(CommonUtil.converDateToFormat(item.getUpdtdDt(),
					PDConstants.DATE_TIME_FORMAT));
		}

		commonutility.setRowCount(rowCount);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}

	/**
	 * P0005 update the overall status in common file header This method will be
	 * called just before each process execution.
	 * 
	 * @throws ParseException
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		String remarks = commonutility.getRemarks();
		String ifFileId = stepExecution.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		String porCdStr = stepExecution.getJobExecution().getExecutionContext()
				.get("porCdStr")
				+ "";
		String buyerGrpCdStr = stepExecution.getJobExecution()
				.getExecutionContext().get("buyerGrpCdStr")
				+ "";

		if (stepExecution.getReadCount() == 0) {
			remarks = M00214
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.SEND_WEEKLY_PRODUCTION_SCHEDULE)
					.replace(PDConstants.ERROR_MESSAGE_3, porCdStr)
					.replace(PDConstants.ERROR_MESSAGE_4, buyerGrpCdStr);
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(remarks);
			LOG.info(M00003);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			if (("").equals(remarks) || remarks == null) {
				remarks = M00226.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
						.replace(PDConstants.ERROR_MESSAGE_2, porCdStr)
						.replace(PDConstants.ERROR_MESSAGE_3, buyerGrpCdStr);
			}
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
		// Else condition will not occur as the execution falls in anyone of the
		// about condition

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
