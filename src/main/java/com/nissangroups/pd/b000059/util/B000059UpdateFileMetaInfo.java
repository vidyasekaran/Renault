/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : Update the files ' meta data in common file hdr like no., records and seq no.
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;
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
import com.nissangroups.pd.b000059.processor.B000059ConversionTransformProcessor;

public class B000059UpdateFileMetaInfo implements Tasklet, InitializingBean {

	private static final Log LOG = LogFactory.getLog(B000059ConversionTransformProcessor.class
			.getName());
	
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info("B000059UpdateFileMetaInfo" + DOLLAR + INSIDE_METHOD + DOLLAR);		
		
		List <B000059FileProcessingStatusVO> listStatus = commonutility.getFileSpecVO().getFilesToProcessList();
		
		String processPath = commonutility.getFileSpecVO().getPrmtrMasterDetails().get(B000059Constants.B000059_PROCESS_PATH).getVal1();
		
        processPath = processPath+File.separator+File.separator;
        
        StringBuilder fullPathName = new StringBuilder(processPath);
        String fileName = null;
        Long recordCount = null;
        Integer seqNo = null;
              
        StepContext stepContext = (StepContext) chunkContext.getStepContext();		
        String interfaceFileID = (String) stepContext.getJobParameters().get(B000059Constants.INTERFACE_FILE_ID);
		
		for (Iterator<B000059FileProcessingStatusVO> iterator = listStatus.iterator(); iterator.hasNext();) {
			B000059FileProcessingStatusVO b000059FileProcessingStatusVO = (B000059FileProcessingStatusVO) iterator
					.next();
			fullPathName = new StringBuilder(processPath);
			fileName = b000059FileProcessingStatusVO.getFileName();					
			seqNo = b000059FileProcessingStatusVO.getSeqNo();
			fullPathName.append(b000059FileProcessingStatusVO.getFileName());
			recordCount = commonutility.getRecordCount(new File(fullPathName.toString()));			
			commonutility.updateCommonFileHdr(interfaceFileID,seqNo, fileName, recordCount);	
			b000059FileProcessingStatusVO.setFilerowCount(recordCount);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}
	
}
