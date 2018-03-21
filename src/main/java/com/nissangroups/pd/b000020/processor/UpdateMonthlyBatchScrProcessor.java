/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.STEP_FAIL;
import static com.nissangroups.pd.util.PDConstants.STEP_SUCCESS;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.MnthlyBatchProcessStt;
import com.nissangroups.pd.model.MnthlyBatchProcessSttPK;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to update the batch status.
 * 
 * @author z011479
 *
 */
public class UpdateMonthlyBatchScrProcessor implements
		ItemProcessor<MnthlyBatchProcessStt, MnthlyBatchProcessStt> {

	private static final Log LOG = LogFactory
			.getLog(UpdateMonthlyBatchScrProcessor.class.getName());
	@Autowired(required = false)
	private CommonRepository cmnRep;

	String porCd = null;
	String batchId = null;
	String seqId = null;

	Date date = new Date();
	private String stepExecutionID;
	Timestamp createDate = new Timestamp(date.getTime());

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		stepExecutionID = stepExecution.getStepName();
		porCd = stepExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public MnthlyBatchProcessStt process(
			MnthlyBatchProcessStt mnthlyBatchProcessStt) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		MnthlyBatchProcessStt mnthBatch = new MnthlyBatchProcessStt();
		MnthlyBatchProcessSttPK mnthBatchPk = new MnthlyBatchProcessSttPK();

		mnthBatchPk.setPorCd(mnthlyBatchProcessStt.getId().getPorCd());
		mnthBatchPk.setOrdrtkBaseMnth(mnthlyBatchProcessStt.getId()
				.getOrdrtkBaseMnth());
		mnthBatchPk.setBatchId(mnthlyBatchProcessStt.getId().getBatchId());
		mnthBatchPk.setSeqId(mnthlyBatchProcessStt.getId().getSeqId());
		mnthBatch.setBuyerGrpCd(mnthlyBatchProcessStt.getBuyerGrpCd());
		mnthBatch.setCarSrs(mnthlyBatchProcessStt.getCarSrs());
		mnthBatch.setOcfRegionCd(mnthlyBatchProcessStt.getOcfRegionCd());
		mnthBatch.setProdMnthFrm(mnthlyBatchProcessStt.getProdMnthFrm());
		mnthBatch.setProdMnthTo(mnthlyBatchProcessStt.getProdMnthTo());
		mnthBatch.setOcfBuyerGrpCd(mnthlyBatchProcessStt.getOcfBuyerGrpCd());
		mnthBatch.setPrmtr1(mnthlyBatchProcessStt.getPrmtr1());
		mnthBatch.setPrmtr2(mnthlyBatchProcessStt.getPrmtr2());
		mnthBatch.setPrmtr3(mnthlyBatchProcessStt.getPrmtr3());
		mnthBatch.setPrmtr4(mnthlyBatchProcessStt.getPrmtr4());
		mnthBatch.setPrmtr5(mnthlyBatchProcessStt.getPrmtr5());
		// b000020ParamOutput
		mnthBatch.setCrtdBy(PDConstants.BATCH_20_ID);
		mnthBatch.setCrtdDt(mnthlyBatchProcessStt.getCrtdDt());
		mnthBatch.setUpdtdBy(PDConstants.BATCH_20_ID);
		mnthBatch.setUpdtdDt(createDate);
		mnthBatch.setPrmtr1(mnthlyBatchProcessStt.getPrmtr1());
		if (stepExecutionID.equals(STEP_FAIL)) {
			mnthBatch.setProcessSttsFlag(PDConstants.BATCH_FAILURE_STTS_CD);
			mnthBatch.setErrFlag(PDConstants.BATCH_ERROR_Y_STTS_CD);
		} else if (stepExecutionID.equals(STEP_SUCCESS)) {
			mnthBatch.setProcessSttsFlag(PDConstants.BATCH_SUCCESS_STTS_CD);
			mnthBatch.setErrFlag(PDConstants.BATCH_ERROR_N_STTS_CD);
		}
		mnthBatch.setId(mnthBatchPk);
		cmnRep.deleteTempData();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return mnthBatch;

	}

}
