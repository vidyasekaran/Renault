/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000060/B000061
 * Module          : 
 * Process Outline : 
 
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000061.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000061UpdateTasklet implements Tasklet {

	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000061CommonUtilityService.class.getName());
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		try {			
				B61commonutility.updateFileStatusCommonFileHdr();
				
				// Remove the new line chars at 
				String sendFileName = B61commonutility.getLocalPath() + File.separator + B61commonutility.getSendInterfaceFileName();
				
				File sendFile = new File(sendFileName);
				
				File modifiedFiled = CommonUtil.removeEOLInLastRecord(B61commonutility.getLocalPath(),sendFile,B61commonutility.getMaxEndPosition(),"tempEOL");
				
				if(sendFile.delete()){
					LOG.error("Successfully remvoed " + sendFile.getAbsolutePath());
				}else{
					LOG.error("Error on delete file : " + sendFile.getAbsolutePath());
				}
				
				if(modifiedFiled.renameTo(new File(sendFileName))){
					LOG.error("Successfully renamed to " + sendFile.getAbsolutePath());
				}else{
					LOG.error("Error on rename file to" + sendFile.getAbsolutePath());
				}
														
				LOG.info(PDMessageConsants.M00113 + "Interface File Id " + B61commonutility.getInterfaceId());
			} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	}
}
