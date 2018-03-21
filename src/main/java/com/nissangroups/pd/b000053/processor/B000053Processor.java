package com.nissangroups.pd.b000053.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000028.util.B000028Constants;
import com.nissangroups.pd.b000053.output.B000053Output;
import com.nissangroups.pd.b000053.output.B000053ParamOutput;
import com.nissangroups.pd.b000053.process.TriggerWklyBatchProcess;
import com.nissangroups.pd.b000053.util.B000053Constants;
import com.nissangroups.pd.repository.PipelineProcessRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;







public class B000053Processor implements ItemProcessor<Long,Long> {
	
	private static final Log LOG = LogFactory.getLog(B000053Processor.class
			.getName());
	

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	@Autowired(required = false)
	TriggerWklyBatchProcess extractWeeklyTrigger;
	
	@Autowired(required = false)/** Repository for executing queries. */
	PipelineProcessRepository PplProcessRepo;   
	
	
	
	private String porCd;
	private String ordrTakeBaseMonth;
	private String ordrTakeBaseWeekNo;
	private String batchId;
	private String seqId;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		
	}
	
	@Override
	public Long process(Long countOfData) throws Exception {
		
		LOG.info(countOfData);
		checkTriggerInformation(countOfData);/** check whelther data is extracted from P0001 Details exit if no data extracted */
		B000053Output output=new B000053Output();
		B000053ParamOutput param=new B000053ParamOutput();
		param.setPorCd(porCd);
		param.setOrdrTakeBaseMonth(ordrTakeBaseMonth);
		param.setOrdrTakeBaseWeekNo(ordrTakeBaseWeekNo);
		param.setBatchId(batchId);
		param.setSeqId(seqId);
		output.setObjB000053ParamOutput(param);
		output=extractWeeklyTrigger.extractTriggerWklyBatchProcess(output);
		
		return null;
	}
	
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOrdrTakeBaseMonth() {
		return ordrTakeBaseMonth;
	}

	public void setOrdrTakeBaseMonth(String ordrTakeBaseMonth) {
		this.ordrTakeBaseMonth = ordrTakeBaseMonth;
	}

	public String getOrdrTakeBaseWeekNo() {
		return ordrTakeBaseWeekNo;
	}

	public void setOrdrTakeBaseWeekNo(String ordrTakeBaseWeekNo) {
		this.ordrTakeBaseWeekNo = ordrTakeBaseWeekNo;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	public void checkTriggerInformation(Long countOfData) {
		if (countOfData < 1) {
			CommonUtil.logMessage(PDMessageConsants.M00190,
					PDConstants.CONSTANT_V4, new String[] {
							B000053Constants.BATCH_B000053,
							PDConstants.BATCH_INPUT_DETAILS,
							B000053Constants.WKLY_BATCH_STS_ERR_MSG,"SEQUENCE ID "+seqId });
			CommonUtil.stopBatch();

		}

	}
	
}
