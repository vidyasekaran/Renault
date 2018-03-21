package com.nissangroups.pd.b000061.processor;

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

import com.nissangroups.pd.b000061.util.B000061CommonUtilityService;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.PDConstants;

public class B000061CtrlFileDecider implements JobExecutionDecider {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;

	/**
	 * Variable job execution.
	 */
	private JobExecution jobExecution;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000061CtrlFileDecider.class.getName());

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

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ctrlFileFlag = null;		
		String nextBatch = null;

		jobExecution = stepExecution.getJobExecution();
		JobParameters jobInputs = jobExecution.getJobParameters();
		String interfaceID = jobInputs
				.getString(PDConstants.S_INTERFACE_FILE_ID);

		try {
			
			MstInterface mstInterface = (MstInterface) B61commonutility.getInterfaceMaster().get(interfaceID);
			
			ctrlFileFlag =  mstInterface.getControlFileFlag();
			
			LOG.info("Control File Flag : " + ctrlFileFlag);
			if (null != ctrlFileFlag && ctrlFileFlag.equalsIgnoreCase(PDConstants.CONTROL_FILE_FLAG_YES)) {
				nextBatch = "yesCtrlFile";
			} else if (null != ctrlFileFlag	&& ctrlFileFlag.equalsIgnoreCase(PDConstants.CONTROL_FILE_FLAG_NO)) {
				nextBatch = "noCtrlFile";
			} else {
				nextBatch = "noCtrlFile";
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.info("Exception in  decide method "
					+ e);
		} 
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return new FlowExecutionStatus(nextBatch);
	}

}
