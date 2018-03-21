/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :Roll back data if any validation or excepiton occures 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileProcessingStatusVO;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.PDConstants;

public class B000059ExceptionRollback implements Tasklet
{

	/**
	 * common logger
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059ExceptionRollback.class.getName());

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;


	/**
	 * This method used to check the error status of the processed  files and update the results in common file header.
	 * If there is any error occured this method will rollback the data from common pool
	 * 
	 * @param contributon Contribution object
	 * @param chunkContext ChunkContext object
	 * @return RepeatStatus
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info("B000059ExceptionRollback" + DOLLAR + INSIDE_METHOD + DOLLAR);
		
		List<B000059FileProcessingStatusVO> listStatus = commonutility
				.getFileSpecVO().getFilesToProcessList();
		int seqNo = 0;
		String fileName = null;
		String status;
		StepContext stepContext = chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(
				B000059Constants.INTERFACE_FILE_ID);

		MstInterface mstInterface = (MstInterface) commonutility
				.fetchInterfaceMasterData().get(interfaceFileID);
		String skipIfError = mstInterface.getSkipIfError();

		for (Iterator iterator = listStatus.iterator(); iterator.hasNext();) {
			B000059FileProcessingStatusVO b000059FileProcessingStatusVO = (B000059FileProcessingStatusVO) iterator
					.next();
			seqNo = b000059FileProcessingStatusVO.getSeqNo();
			fileName = b000059FileProcessingStatusVO.getFileName();

			if (b000059FileProcessingStatusVO.isError()) {
				status = "F";
			} else {
				status = "U";
			}

			commonutility.updateFileStatusCommonFileHdr(interfaceFileID, seqNo,
					fileName, status, PDConstants.RECIEVE_MSG_CHAR);

		}

		if (null != skipIfError && skipIfError.equalsIgnoreCase(PDConstants.CONVERSION_LAYER_SKIPLEVEL_YES)) {

			for (Iterator<B000059FileProcessingStatusVO> iterator = listStatus
					.iterator(); iterator.hasNext();) {
				B000059FileProcessingStatusVO fileProcessingStatusVO = (B000059FileProcessingStatusVO) iterator
						.next();
				seqNo = fileProcessingStatusVO.getSeqNo();
				fileName = fileProcessingStatusVO.getFileName();

				boolean isFailure = fileProcessingStatusVO.isError(); // checkCmnFileHdr(interfaceFileID,
																		// seqNo,
																		// fileName);

				if (isFailure) {
					commonutility.deleteFromCommonPool(interfaceFileID, seqNo);					
				}
			}
		} else if (null != skipIfError && skipIfError.equalsIgnoreCase(PDConstants.CONVERSION_LAYER_SKIPLEVEL_NO)) {

			for (Iterator<B000059FileProcessingStatusVO> iterator = listStatus
					.iterator(); iterator.hasNext();) {
				B000059FileProcessingStatusVO fileProcessingStatusVO =  iterator
						.next();
				seqNo = fileProcessingStatusVO.getSeqNo();
				fileName = fileProcessingStatusVO.getFileName();

				boolean isFailure = fileProcessingStatusVO.isError();

				if (isFailure || commonutility.getFileSpecVO().isRecordLengthValidationFlag()) {

					callDeleteFromCommonPool(listStatus,interfaceFileID,fileName);

					break;
				}
			}

		}
		commonutility.moveProcessedFiles();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}


	private void callDeleteFromCommonPool(
			List<B000059FileProcessingStatusVO> listStatus,
			String interfaceFileID, String fileName) {
		for (Iterator<B000059FileProcessingStatusVO> iterator2 = listStatus.iterator(); iterator2
				.hasNext();) {
			B000059FileProcessingStatusVO fileProcessingStatusVO1 = iterator2
					.next();
			commonutility.deleteFromCommonPool(interfaceFileID,
					fileProcessingStatusVO1.getSeqNo());					
			}
		}
	}
