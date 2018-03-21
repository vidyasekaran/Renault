/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-NoOpItemWriter
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000054.writer;

import static com.nissangroups.pd.util.PDConstants.B000020_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FILE_NAME;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class NoOpItemWriter.
 */
public class NoOpItemWriter implements ItemWriter {

	@Autowired(required = false)
	private B000020ParamOutput b000020ParamOutput;
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(NoOpItemWriter.class
			.getName());

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String errorPath = environment.getProperty(B000020_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		String fileName = errorPath.trim() + PDConstants.REPORT_SUFFIX_B20
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		// b000020ParamOutput.setErrorPath(fileName);
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext stepContext = jobExecution.getExecutionContext();
		stepContext.put(PRMTR_FILE_NAME, fileName);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	@Override
	public void write(List items) throws Exception {
		LOG.info("COMPLETED");
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			JobExecution jobExecution = stepExecution.getJobExecution();
			String batchScrFlg = jobExecution.getJobParameters().getString(
					PDConstants.BATCH_SCRN_FLG);
			if (batchScrFlg.equalsIgnoreCase(PDConstants.PRMTR_ZERO)) {
				CommonUtil.logMessage(
						PDMessageConsants.M00159,
						PDConstants.CONSTANT_V4,
						new String[] {
								PDConstants.BATCH_20_ID,
								PDConstants.BATCH_INPUT_DETAILS,
								stepExecution.getJobParameters().getString(
										PDConstants.BATCH_POR_CODE),
								PDConstants.POR_MST });
				stepExecution.setExitStatus(new ExitStatus(PDConstants.FAILED));
			} else {
				CommonUtil.logMessage(
						PDMessageConsants.M00190,
						PDConstants.CONSTANT_V4,
						new String[] {
								PDConstants.BATCH_20_ID,
								PDConstants.BATCH_INPUT_DETAILS,
								stepExecution.getJobParameters().getString(
										PDConstants.BATCH_POR_CODE),
								PDConstants.POR_MST });
				stepExecution.setExitStatus(new ExitStatus(PDConstants.FAILED));
			}
		}
	}
}
