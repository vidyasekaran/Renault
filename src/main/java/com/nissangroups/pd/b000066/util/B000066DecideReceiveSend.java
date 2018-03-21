package com.nissangroups.pd.b000066.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.PDConstants;

public class B000066DecideReceiveSend implements JobExecutionDecider {


	/**
	 * Variable job execution.
	 */
	private JobExecution jobExecution;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000066DecideReceiveSend.class.getName());

	/**
	 * This method is used to decide whether the file is having end of file or
	 * not.
	 * 
	 * @param jobExecution
	 *            JobExecution object
	 * @param stepExecution
	 *            StepExecution object
	 * @return FlowExecutionStatus object
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {

		LOG.info("B000059EOLFileDecider" + DOLLAR + INSIDE_METHOD + DOLLAR);
		MstInterface interfaceMaster = null;
		String fileType = null;

		jobExecution = stepExecution.getJobExecution();
		JobParameters jobInputs = jobExecution.getJobParameters();
		String interfaceID = jobInputs
				.getString(PDConstants.BATCH_ID_B000066);

		try {
			

			fileType = "R"; //interfaceMaster.getFileType();

			LOG.info("File Type..... : " + fileType);
			/*
			 * 1- Fixed length with End of line character. 2- Fixed Length
			 * without End of Line character.
			 */
			if (null != fileType
					&& fileType
							.equalsIgnoreCase(PDConstants.RECEIVE_TRANSACTION_TYPE)) {
				return new FlowExecutionStatus("kickOFReceive");
			} else if (null != fileType
					&& fileType
							.equalsIgnoreCase(PDConstants.SEND_TRANSACTION_TYPE)) {
				return new FlowExecutionStatus("kickOFSend");
			} else {

			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.info("Exception in B000066DecideReceiveSend decide method "
					+ e.toString());
		} finally {
			interfaceMaster = null;
			fileType = null;
		}
		LOG.info("B000066DecideReceiveSend" + DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

}
