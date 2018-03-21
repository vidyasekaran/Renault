package com.nissangroups.pd.b000061.processor;

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
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.PDConstants;

public class B000061ProcessorP0000 implements ItemProcessor<CmnInterfaceData, CmnInterfaceData> {

	private CmnInterfaceData cmnIfData;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000061ProcessorP0000.class);
	
	private String sendInterfaceId = null;
	private Integer maxSeqNo = null;

	@Override
	public CmnInterfaceData process(CmnInterfaceData item) throws Exception {							

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		LOG.info("MaxSeqNo : " + maxSeqNo);
		
		if(null != maxSeqNo){
			cmnIfData = new CmnInterfaceData();		
			cmnIfData.setId(new CmnInterfaceDataPK());		
			cmnIfData.getId().setSeqNo(maxSeqNo);		
			cmnIfData.getId().setRowNo(item.getId().getRowNo());	
			cmnIfData.getId().setIfFileId(sendInterfaceId);
			
			CmnFileHdr fileHdr = new CmnFileHdr();		
			fileHdr.setId(new CmnFileHdrPK());
			fileHdr.getId().setIfFileId(sendInterfaceId);
			fileHdr.getId().setSeqNo(maxSeqNo);
			cmnIfData.setCmnFileHdr(fileHdr);
			
			cmnIfData.setCol1(item.getCol1());
			cmnIfData.setCol2(item.getCol2());
			cmnIfData.setCol3(item.getCol3());
			cmnIfData.setCol4(item.getCol4());
			cmnIfData.setCol5(item.getCol5());
			cmnIfData.setCol6(item.getCol6());
			cmnIfData.setCol7(item.getCol7());
			cmnIfData.setCol8(item.getCol8());
			cmnIfData.setCol9(item.getCol9());
			cmnIfData.setCol10(item.getCol10());
			cmnIfData.setCol11(item.getCol11());
			cmnIfData.setCol12(item.getCol12());
			cmnIfData.setCol13(item.getCol13());
			cmnIfData.setCol14(item.getCol14());
			cmnIfData.setCol15(item.getCol15());
			cmnIfData.setCol16(item.getCol16());
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;

	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));
						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}
	
	public String getSendInterfaceId() {
		return sendInterfaceId;
	}

	public void setSendInterfaceId(String sendInterfaceId) {
		this.sendInterfaceId = sendInterfaceId;
	}

	public Integer getMaxSeqNo() {
		return maxSeqNo;
	}

	public void setMaxSeqNo(Integer maxSeqNo) {
		this.maxSeqNo = maxSeqNo;
	}
	
}
