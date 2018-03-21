package com.nissangroups.pd.b000040.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.output.B000040OrdrDtlsOutputBean;
import com.nissangroups.pd.b000040.util.B000040QueryDataService;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;


public class B000040Processor implements ItemProcessor<B000040OrdrDtlsOutputBean,B000040OrdrDtlsOutputBean >{
	

	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000040Processor.class);
	
	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000040QueryDataService queryDataService;
	
	@Override
	public B000040OrdrDtlsOutputBean process(B000040OrdrDtlsOutputBean item)
			throws Exception {		
		return item;
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
	LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	
}
	
}
