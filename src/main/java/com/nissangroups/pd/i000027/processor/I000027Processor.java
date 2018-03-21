/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send Monthly OCF Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.processor;

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
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000027.output.I000027OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * This class is used to  process the extracted OSEI frozen master details 
 *  from Reader and insert the data into Common Interface data
 * 
 * @author z014029
 */
public class I000027Processor implements
		ItemProcessor<I000027OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000027Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution. */
	private JobExecution jobExec;

	/** Variable OCF Limit Check */
	String ocfLmtChk = null;

	/** Variable date. */
	Date date = new Date();

	/** Variable create date. */
	Timestamp createDate = new Timestamp(date.getTime());

	/** Variable Cmn Interface Data. */
	private CmnInterfaceData cmnIfData;

	/** Variable Parameter Repository. */
	@Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;

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
/*		Object maxOdrTk = stepExecution.getJobExecution().getExecutionContext()
				.get(IFConstants.MAX_ORDR_TAKBAS_MNTH);*/
		if (!("").equals(commonutility.getRemarks()) && commonutility.getRemarks() != null ) {
			LOG.error(commonutility.getRemarks());
			CommonUtil.stopBatch();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * This method is used to  process the extracted OSEI frozen master details 
     *  from Reader and sets it to Common Interface data VO which will be inserted
     *  into Common Interface data by writer
     *  
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public CmnInterfaceData process(I000027OutputBean item) throws Exception {

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
		cmnIfData.setCol2(item.getCrSrs());
		cmnIfData.setCol3(item.getFeatCd());
		cmnIfData.setCol4(item.getOcfFrmCd());
		cmnIfData.setCol5(item.getFeatShrtDesc());
		cmnIfData.setCol6(item.getOcfByrGrpCd());
		cmnIfData.setCol7(item.getCrGrp());
		cmnIfData.setCol8(item.getFeatTypCd());
		cmnIfData.setCol9(PDConstants.CF_CONSTANT_M);
		cmnIfData.setCol10(item.getOrdrTkBsMnth());
		cmnIfData.setCol11(PDConstants.CF_CONSTANT_M);
		cmnIfData.setCol12(item.getPrdMnth());
		cmnIfData.setCol13(item.getByrGrpCd());
		cmnIfData.setCol14(PDConstants.EMPTY_STRING);
		cmnIfData.setCol15(PDConstants.EMPTY_STRING);
		if ("00".equals(item.getOcfFrmCd())
				&& (item.getByrGrpOcfLmtQty() == null)) {
			cmnIfData.setCol16(PDConstants.PRMTR_ZERO);
		} else {
			cmnIfData.setCol16(item.getByrGrpOcfUsgQty());
		}
		if (item.getByrGrpOcfLmtQty() == null
				|| item.getByrGrpOcfLmtQty().isEmpty()) {
			cmnIfData.setCol17(PDConstants.PRMTR_OCF_LMT_QTY);
		} else {
			cmnIfData.setCol17(item.getByrGrpOcfLmtQty());
		}
		cmnIfData.setCol18(ifFileId);
		cmnIfData.setCol19((createDate).toString());
		cmnIfData.setCol20(ifFileId);
		cmnIfData.setCol21((createDate).toString());

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}

	/**
	 * This method is to extract value1 from parameter mst table to find
	 * the Buyer Group OCF Limit
	 * 
	 * @param porCd
	 * @return value1
	 * 
	 *         
	 * @throws Exception
	 *//*
	private String byrGrpOCFLmtChk(String porCd, long seqNo)
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		List<MstPrmtr> resultList = prmtrMstRepo.fetchValue(
				PDConstants.OCF_UNLIMITED_OCF_CHECK, porCd);

		if (resultList != null && (resultList.size() !=0)) {
			if ("false".equalsIgnoreCase(resultList.get(0).getVal1())) {
				throw new PdApplicationException("Records in false");
			}
		} else {
			String remarks = (PDMessageConsants.M00160
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.OCF_UNLMTD_OCF_CHK_FLG)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, 
							PDConstants.MESSAGE_MST_PARAMETER));
			commonutility.setRemarks(remarks);
			commonutility.updateCmnFileHdr(ifFileId, seqNo,
					PDConstants.INTERFACE_FAILURE_STATUS, remarks);
			throw new PdApplicationException("M00160 : I000027: There is no OCF UNLIMITED OCF CHECK for POR_CD =22 in PARAMETER_MST-Table.So Batch Stopped");
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return ocfLmtChk;
	}*/

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
							PDConstants.BUYER_GROUP_MONTHLY_OCF_LIMIT_TRN)
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
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}