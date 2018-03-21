package com.nissangroups.pd.b000059.processor;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.model.MstInterface;

public class B000059EOLFileDecider implements JobExecutionDecider {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059EOLFileDecider.class.getName());

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

		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);
		MstInterface interfaceMaster = null;
		String fileType = null;

		JobParameters jobInputs = jobExecution.getJobParameters();
		String interfaceID = jobInputs
				.getString(B000059Constants.INTERFACE_FILE_ID);

		try {
			interfaceMaster = (MstInterface) commonutility.getFileSpecVO()
					.getInterfaceMaster().get(interfaceID);

			fileType = interfaceMaster.getFileType();

			LOG.info("File Type..... : " + fileType);
			/*
			 * 1- Fixed length with End of line character. 2- Fixed Length
			 * without End of Line character.
			 */
			if (null != fileType
					&& fileType
							.equalsIgnoreCase(B000059Constants.PARAM_FILE_TYPE_1)) {
				return new FlowExecutionStatus("EOLFile");
			} else if (null != fileType
					&& fileType
							.equalsIgnoreCase(B000059Constants.PARAM_FILE_TYPE_2)) {
				return new FlowExecutionStatus("nonEOLFile");
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.info("Exception in B000059EOLFileDecider decide method "
					+ e);
		} finally {
			interfaceMaster = null;
			fileType = null;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

}
