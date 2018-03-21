/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000102
 * Module          : CM COMMON					
 * Process Outline : Send Logical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-12-2014  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000102.processor;

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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000102.output.I000102OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is to process the extracted Buyer and Buyer Group data 
 *  from Reader and insert the data into Common Interface data
 * @author z015847
 *
 */
public class I000102Processor implements
		ItemProcessor<I000102OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000102Processor.class);

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable jobExec. */
	private JobExecution jobExec;
	
	

	/** Variable spec mst. */
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
	 * P0003 This method processes insert into Common Interface data VO object
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public CmnInterfaceData process(I000102OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		long seqNo = (long) jobExec.getExecutionContext().get(IFConstants.SEQ_NO);

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
		
		cmnIfData.setCol1(item.getVhclSeqId());
		cmnIfData.setCol2(item.getPorCd());
		cmnIfData.setCol3(item.getProdPlntCd());
		cmnIfData.setCol4(item.getCarSrs());
		cmnIfData.setCol5(item.getAppldMdlCd());
		cmnIfData.setCol6(item.getPckCd());
		cmnIfData.setCol7(item.getExtClrCd());
		cmnIfData.setCol8(item.getIntClrCd());
		cmnIfData.setCol9(item.getAdtnlSpecCd()); // TO DO:: Need clarification - NML_SPEC_CODE  Additional
		cmnIfData.setCol10(item.getOfflnPlanDate());
		cmnIfData.setCol11(item.getBuyerCd());		
		cmnIfData.setCol12(item.getSpecDestnCd()); //TO DO : Need clarification - SPEC_DESTINATION_CD
		cmnIfData.setCol13(item.getLgclPplnStageCd());
		cmnIfData.setCol14(item.getSlsNoteNo());
		cmnIfData.setCol15(item.getExNo());
		cmnIfData.setCol16(item.getProdMnth());
		cmnIfData.setCol17(item.getPotCd());
		cmnIfData.setCol18(item.getProdOrdrNo());
		cmnIfData.setCol19(item.getOrdrDelFlag());
		cmnIfData.setCol20(item.getMsFxdFlag());
		cmnIfData.setCol21(item.getProdFmyCd());
		
		cmnIfData.setCol22(item.getLineClass());
		cmnIfData.setCol23(item.getFrznTypeCd());
		cmnIfData.setCol24(item.getProdMthdCd());
		cmnIfData.setCol25(item.getVinNo());
		cmnIfData.setCol26(null!=item.getEtaAdjustFlag() ? item.getEtaAdjustFlag().toString() : null);
		

		commonutility.setRowCount(rowCount);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}
	
	/**
	 * P0004 update the cmn file header
	 * This method executed Each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1,""));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			//write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}

}
