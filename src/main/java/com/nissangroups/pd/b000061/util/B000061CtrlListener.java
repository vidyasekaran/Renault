/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000061.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000061CtrlListener implements StepExecutionListener {
	
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000061CommonUtilityService.class.getName());
	@Override
	public void beforeStep(StepExecution stepExecution) {		
		LOG.info("############### Before Step B000061CtrlListener");		
							
		String filePath = B61commonutility.getLocalPath() + File.separator + B61commonutility.getSendCtrlFileName();
				
		try{
			File f1 = new File(B61commonutility.getLocalPath());
			
			if(f1.canRead() && f1.canWrite() && f1.isDirectory()){
				stepExecution.getJobExecution().getExecutionContext().put("CTRL_FIX_LENTH_FILE_NAME",filePath);
			}else{
				LOG.info("Permission Issues on Local Path for interface file id : " + B61commonutility.getInterfaceId() );
				LOG.info("Path Path : " + filePath);
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00076, new String[]{"&1"}, new String[]{ "Interface File ID: " + B61commonutility.getInterfaceId()}) );
				CommonUtil.stopBatch();
			}
			
			
		}catch(Exception e){
			LOG.error("Error " + e);
		}	
		LOG.info("Local Path : : " + B61commonutility.getSendCtrlFileName());
		LOG.info("CTRL_CTRL_FIX_LENTH_FILE_NAME : " + B61commonutility.getSendCtrlFileName());
	}	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {				
		return stepExecution.getExitStatus();
	}	

}
