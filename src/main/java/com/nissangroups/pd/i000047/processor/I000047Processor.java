/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000047
 * Module          :SP SPEC MASTER
 *  
 * Process Outline :Receive Week No Calendar Interface from Plant. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-01-2016  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000047.processor;

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
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00160;
import static com.nissangroups.pd.util.PDMessageConsants.M00356;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.i000047.output.I000047OutputBean;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This method is to process the Output Bean values.
 * 
 * @author z014029
 * 
 */
public class I000047Processor implements
		ItemProcessor<I000047OutputBean, I000047OutputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000047Processor.class);

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable Job Execution */
	private JobExecution jobExec;

	/** Variable Interface File ID */
	private String ifFileId;

	/** Variable porCd */
	private String porCd;

	/** Environment Class injection */
	@Autowired(required = false)
	Environment environment;

	/**
	 * This method will be called just before each step execution Get
	 * stepExecution and assign into instance variable
	 * 
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		String porCd = jobExec.getJobParameters().getString(IFConstants.POR_CD);

		this.ifFileId = ifFileId;
		this.porCd = porCd;

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is to process all the extracted Common Interface Data records
	 * except the error records and insert / update into different tables
	 * 
	 * @param item
	 *            I000047OutputBean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public I000047OutputBean process(I000047OutputBean item) throws Exception {

		return item;
	}

	/**
	 * This method executed Each step Execution To get the count of Reader,
	 * Writer Based on the count values and write the Log.
	 * 
	 * @param stepExecution
	 *            the step execution
	 * @throws Exception
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			if (commonutility.getRemarks() == null
					|| ("").equals(commonutility.getRemarks())) {
				commonutility.setRemarks(M00076.replace(
						PDConstants.ERROR_MESSAGE_1, ""));
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,
						stepExecution.getFailureExceptions().toString()));
			} else {
				LOG.error(commonutility.getRemarks());
			}
		} else if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			String remarks = M00160
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_DATA)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, PDConstants.TBL_CMN_INTRFC_DATA);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
			stepExecution.setExitStatus(new ExitStatus(FAILED));
		}

		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			//commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			String remarks = M00356
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MST_WK_NO_CLNDR)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd);
			commonutility.setRemarks(remarks);
			LOG.info(remarks);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
}
