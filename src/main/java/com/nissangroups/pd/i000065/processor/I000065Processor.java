/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.processor;

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

import com.nissangroups.pd.i000065.output.I000065OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
/**
 * This class is used to process the Weekly order error interface transaction
 * 
 * @author z014676
 */
public class I000065Processor implements
		ItemProcessor<I000065OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000065Processor.class);
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;
	//Batch domain object representing execution of a job
	private JobExecution jobExec;

	/** Variable Common Interface data */
	private CmnInterfaceData cmnIfData;
	
	/*
	 * Marks a method to be called before a Step is executed
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	/**
	 * P0003 Insert Weekly order interface data into common interface data
	 * 
	 * @param totalList
	 * @return
	 */
	@Override
	public CmnInterfaceData process(I000065OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);

		if (item.getOrdertkBasePrdType() == null || item.getCarSeries() == null
				|| item.getBuyerCD() == null
				|| item.getSpecDestination() == null) {
			LOG.info(" Record skipped as mandatory fileds have null value");
			return null;
		} else {
			/** Variable rowCount */
			long rowCount = commonutility.getRowCount() + 1;
			
			cmnIfData = new CmnInterfaceData();
			cmnIfData.setId(new CmnInterfaceDataPK());
			cmnIfData.getId().setSeqNo(seqNo);

			cmnIfData.getId().setIfFileId(ifFileId);
			CmnFileHdr fileHdr = new CmnFileHdr();
			commonutility.setRowCount(rowCount);
			fileHdr.setId(new CmnFileHdrPK());
			fileHdr.getId().setIfFileId(ifFileId);
			fileHdr.getId().setSeqNo(seqNo);
			cmnIfData.setCmnFileHdr(fileHdr);
			cmnIfData.getId().setRowNo(rowCount);

			cmnIfData.setCol1(item.getPorCd());

			cmnIfData.setCol2(item.getOrdertkBasePrdType());

			cmnIfData.setCol3(item.getOrdertkBasePrd());

			cmnIfData.setCol4(item.getProdType());

			cmnIfData.setCol5(item.getProdPrd());

			cmnIfData.setCol6(item.getCarSeries());

			cmnIfData.setCol7(item.getBuyerGrpCD());

			cmnIfData.setCol8(item.getBuyerCD());

			cmnIfData.setCol9(item.getSpecDestination());

			cmnIfData.setCol10(item.getFeatureShortDescription());

			cmnIfData.setCol11(item.getFeatLngDesc());

			cmnIfData.setCol12(item.getFeatureCd());

			cmnIfData.setCol13(item.getAdoptDate());

			cmnIfData.setCol14(item.getAdoptPrd());

			cmnIfData.setCol15(item.getAbolishDate());

			cmnIfData.setCol16(item.getAbolishPrd());

			cmnIfData.setCol17(item.getAbolishMnth());

			cmnIfData.setCol18(item.getAppliedModelCd());

			cmnIfData.setCol19(item.getPackCD());

			cmnIfData.setCol20(item.getExtclrCD());

			cmnIfData.setCol21(item.getIntClrCD());

			cmnIfData.setCol22(item.getAddSpecCD());

			cmnIfData.setCol23(item.getPotCD());

			cmnIfData.setCol24(item.getProdPlantCD());

			cmnIfData.setCol25(item.getLineClass());

			cmnIfData.setCol26(item.getLclProdOrdrNo());

			cmnIfData.setCol27(item.getProdOrdrNo());

			cmnIfData.setCol28(item.getOrderQty());
			cmnIfData.setCol29(item.getExpQty());
			cmnIfData.setCol30(item.getSign());
			cmnIfData.setCol31(item.getVariance());
			cmnIfData.setCol32(item.getOcfLmt());
			cmnIfData.setCol33(item.getOcfUsg());

			cmnIfData.setCol34(item.getSign());

			cmnIfData.setCol35(item.getDiff());
			cmnIfData.setCol36(item.getErrCD());
			cmnIfData.setCol37(item.getErrMsg());

			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

			return cmnIfData;
		}

	}

	/**
	 * P0004 Updates the common interface data table. This method executes once for each step to get the 
	 * count of Reader,Writer Based on the count values and write the Log.
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

		if (stepExecution.getReadCount() == 0) 
		{
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		} 
		else if (FAILED.equals(FAILED)) 
		{

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(PDMessageConsants.M00076.replace(
					PDConstants.ERROR_MESSAGE_1, stepExecution
							.getFailureExceptions().toString()));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
		} 
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		} 
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {

			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113
					+ ", Total Inserted count is : "
					+ stepExecution.getWriteCount()
					+ ", Skipped records count is : "
					+ (stepExecution.getReadCount() - stepExecution
							.getWriteCount()));
			LOG.error(M00113);
		}
		//The condition for else will be covered in any one of the above used if -else block

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

}
