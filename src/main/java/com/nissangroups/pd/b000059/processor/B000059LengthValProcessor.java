/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program validates the length of incoming data.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.b000059.util.B000059DataValidatorService;
import com.nissangroups.pd.exception.PdApplicationException;

public class B000059LengthValProcessor implements
		ItemProcessor<B000059FileVO, B000059FileVO> {
	
	private static final Log LOG = LogFactory.getLog(B000059LengthValProcessor.class.getName());

	/**
	 * Data validator service bean injection
	 */
	@Autowired(required = false)
	B000059DataValidatorService dataValService;
	
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/**
	 * method used to call performLengthValidation to validate the length
	 * 
	 * @param item
	 *            B000059FileVO object
	 * @return B000059FileVO object
	 */
	@Override
	public B000059FileVO process(B000059FileVO item)
			throws PdApplicationException {
		LOG.info("Length....Validation ::::");
		dataValService.performLengthValidation(item, commonutility.getFileSpecVO().getInterfaceFileId());

		return item;
	}

	/**
	 * This method is used to get interface file id before process method
	 * execution.
	 * 
	 * @param stepExecution
	 *            StepExecution object
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {

		JobParameters jobInputs = stepExecution.getJobExecution()
				.getJobParameters();
		jobInputs
				.getString(B000059Constants.INTERFACE_FILE_ID);

	}

}
