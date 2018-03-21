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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.util.PDConstants;

public class B000061P0001Listener implements StepExecutionListener {
	
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000061P0001Listener.class.getName());
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.info("############### Before Step B000061Listener");		
		// Extracts the Filter Criteria based on the Interface_file_Id		
				
		String interfaceId =  stepExecution.getJobExecution().getJobParameters().getString(PDConstants.S_INTERFACE_FILE_ID);
		// Extracts the Filter Criteria based on the Interface_file_Id		
		String filterCriteria = B61commonutility.generateFilterCriteria(interfaceId);		
		
		String status =  (String) stepExecution.getJobExecution().getExecutionContext().get("CMN_FILE_STTS");
		
		// Extracts the Sort order clause based on the Interface_file_Id
		String sortOrderCriteria = B61commonutility.generateSortOrderCriteria(interfaceId);
				
		StringBuilder dynaQuery = new StringBuilder("SELECT c FROM CmnInterfaceData c where ( c.cmnFileHdr.id.ifFileId='");
		
		 dynaQuery.append(interfaceId).append("'")
		.append("AND c.id.seqNo IN ( ")
		.append(" SELECT MAX(d.id.seqNo) from CmnFileHdr d where d.id.ifFileId='")
		.append(interfaceId)
		.append("' AND d.stts='")
		.append(status)
		.append("'")				
		.append(") )");
		
		if(null != filterCriteria && !filterCriteria.isEmpty()){
			dynaQuery.append(" AND (").append(filterCriteria).append(")");
		}
		
		if(null != sortOrderCriteria && !sortOrderCriteria.isEmpty()){
			dynaQuery.append(sortOrderCriteria);
		}
		
				
		stepExecution.getJobExecution().getExecutionContext().put(PDConstants.DYNAQUERY, dynaQuery.toString());
		
		LOG.info("Dynamic Query : " + dynaQuery.toString());
		
		
				
		StringBuilder range = new StringBuilder("");
		StringBuilder fixLengthFormat = new StringBuilder("");
		List<MstIfLayout> mstIfLayout = (List<MstIfLayout>) B61commonutility.getInterfaceLayoutByOrder().get(interfaceId);
		for (Iterator<MstIfLayout> iterator = mstIfLayout.iterator(); iterator.hasNext();) {
			MstIfLayout mstLayout =  iterator.next();
			range.append(PDConstants.COLNAME).append(mstLayout.getColumnOrdr()).append(",");		
			fixLengthFormat.append("%-").append(mstLayout.getLngth()).append(".").append(mstLayout.getLngth()).append("s");		
		}
		String trimRange = range.toString().replaceFirst(".$", "");
		stepExecution.getJobExecution().getExecutionContext().put("COLS", trimRange);
		stepExecution.getJobExecution().getExecutionContext().put("FIX_LENTH_FORMAT", fixLengthFormat.toString());
		
		String filePath = B61commonutility.getLocalPath() + File.separator + B61commonutility.getSendInterfaceFileName();
		
		LOG.info("File Path : " + filePath);
		
		try{
			File f1 = new File(B61commonutility.getLocalPath());
			
			if(f1.canRead() && f1.canWrite() && f1.isDirectory()){
				stepExecution.getJobExecution().getExecutionContext().put("FIX_LENTH_FILE_NAME",filePath);
			}else{
				LOG.info("Permission Issues on Local Path for interface file id : " +  interfaceId);
				LOG.info("Path Path : " + filePath);
			}
			
			
		}catch(Exception e){
			LOG.error("Error " + e);
		}
		
		
		LOG.info("Columns " + trimRange);
		LOG.info("FIX_LENTH_FORMAT " + fixLengthFormat);
		LOG.info("FIX_LENTH_FILE_NAME " + B61commonutility.getSendInterfaceFileName());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info("############### After Step B000061Listener");
		return null;
	}		
}
