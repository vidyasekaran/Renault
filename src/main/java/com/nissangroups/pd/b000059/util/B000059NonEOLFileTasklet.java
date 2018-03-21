/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : Split the non end of line files / adding the eol code  
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstInterface;

public class B000059NonEOLFileTasklet implements Tasklet {
	
	private static final Log LOG = LogFactory.getLog(B000059MoveFilesTasklet.class
			.getName());
	
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info("Non EOL File Tasklet process....");

		MstInterface interfaceMaster = null;
		List<MstIfLayout> mstIfLayoutList = null;
		String localPath = null;
		Integer maxEndPosition = null;
		
		StepContext stepContext = chunkContext.getStepContext();		
        String interfaceFileID = (String) stepContext.getJobParameters().get(B000059Constants.INTERFACE_FILE_ID);
		
		try{
			interfaceMaster = (MstInterface) commonutility.getFileSpecVO().getInterfaceMaster().get(interfaceFileID);			
			mstIfLayoutList = (List<MstIfLayout>) commonutility.getFileSpecVO().getInterfaceLayoutByOrder().get(interfaceFileID);	
						 
			maxEndPosition = commonutility.getMaxEndPosition(mstIfLayoutList);
			localPath = interfaceMaster.getLocalPath();			
			if(null != localPath){			
				LOG.info(new StringBuilder("LocalPath :").append(localPath).append("MAX Position : ").append(maxEndPosition).toString());				
				commonutility.convertFixedLengthFormat(maxEndPosition, localPath, interfaceFileID);							
			}else{
				LOG.info(new StringBuilder("No Local Path found...").toString());		
			}						
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return RepeatStatus.FINISHED;
	
	}
}
