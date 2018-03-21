/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000033.processor;

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

import com.nissangroups.pd.i000033.output.I000033OutputBean;
import com.nissangroups.pd.i000033.util.I000033QueryConstants;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to process the Monthly order error interface transaction
 * 
 * @author z014676
 */
public class I000033Processor implements
		ItemProcessor<I000033OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000033Processor.class);
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
	 * P0003 Insert monthly order interface data into common interface data
	 * 
	 * @param totalList
	 * @return
	 */
	@Override
	public CmnInterfaceData process(I000033OutputBean item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);

		if (item.getPor() == null || item.getCarSrs() == null
				|| item.getBuyerGrpCd() == null || item.getBuyerCd() == null
				|| item.getSpecDestnCd() == null) {

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

			cmnIfData.setCol1(item.getPor());
            if(item.getOrdrtkPrdType() == null){
            	cmnIfData.setCol2(I000033QueryConstants.ORDRTK_PRD_TYPE);
            }
            else{
            	cmnIfData.setCol2(item.getOrdrtkPrdType());
            }
			cmnIfData.setCol3(item.getOrdrtkPrd());
			if(item.getProdPrdType() == null){
				cmnIfData.setCol4(I000033QueryConstants.ORDRTK_PRD_TYPE);
			}
			else {
				cmnIfData.setCol4(item.getProdPrdType());
			}
			
			cmnIfData.setCol5(item.getProdPrd());
			cmnIfData.setCol6(item.getCarSrs());
			cmnIfData.setCol7(item.getBuyerGrpCd());
			cmnIfData.setCol8(item.getBuyerCd());
			cmnIfData.setCol9(item.getSpecDestnCd());
			cmnIfData.setCol10(item.getOcfShrtDesc());
			cmnIfData.setCol11(item.getOcfDesc());
			cmnIfData.setCol12(item.getOcfFeatCd());
			cmnIfData.setCol13(item.getAdptDt());
			cmnIfData.setCol14(item.getAdptPrd());
			cmnIfData.setCol15(item.getAblshDt());
			cmnIfData.setCol16(item.getAblshPrd());
			cmnIfData.setCol17(item.getAblshMnth());
			cmnIfData.setCol18(item.getAppldMdlCd());
			cmnIfData.setCol19(item.getPackCd());
			cmnIfData.setCol20(item.getExtClrCd());
			cmnIfData.setCol21(item.getIntClrCd());
			cmnIfData.setCol22(item.getAddSpecCd());
			cmnIfData.setCol23(item.getProdOrdrNo());
			cmnIfData.setCol24(item.getQty());
			cmnIfData.setCol25(item.getExpctdQty());
			cmnIfData.setCol26(item.getOcfSign());
			cmnIfData.setCol27(item.getVariance());
			cmnIfData.setCol28(item.getOcfLmt());
			cmnIfData.setCol29(item.getOcfUsg());
			cmnIfData.setCol30(item.getOrdrSign());
			cmnIfData.setCol31(item.getDiff());
			cmnIfData.setCol32(item.getErrCd());
			cmnIfData.setCol33(item.getErrMsg());
			cmnIfData.setCol34(item.getLclProdOrdrNo());
			cmnIfData.setCol35(item.getLineClass());
			cmnIfData.setCol36(item.getPot());
			cmnIfData.setCol37(item.getProdPlantCd());

			commonutility.setRowCount(rowCount);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

			return cmnIfData;
		}
	}

	/**
	 * P0004 update the common interface data This method executed Each step
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
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113
					+ ", Total Inserted count is : "
					+ stepExecution.getWriteCount()
					+ ", Skipped records count is : "
					+ (stepExecution.getReadCount() - stepExecution
							.getWriteCount()));
			LOG.info(M00113);

		}
		//Else condition will not occur as the execution falls in anyone of the about condition

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
