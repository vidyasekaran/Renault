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

public class B000061P0002Listener implements StepExecutionListener {
	
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000061CommonUtilityService.class.getName());
	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.info("############### Before Step B000061Listener");		
		// Extracts the Filter Criteria based on the Interface_file_Id		
				
		String interfaceId =  (String) stepExecution.getJobExecution().getExecutionContext().get(PDConstants.R_INTERFACE_FILE_ID);
		// Extracts the Filter Criteria based on the Interface_file_Id		
		String filterCriteria = B61commonutility.generateFilterCriteria(interfaceId);		
		
		// Extracts the Sort order clause based on the Interface_file_Id
		String sortOrderCriteria = B61commonutility.generateSortOrderCriteria(interfaceId);
				
		StringBuilder dynaQuery = new StringBuilder("SELECT c FROM CmnInterfaceData c where ( c.cmnFileHdr.id.ifFileId='");
		
		dynaQuery.append(interfaceId).append("'")
		.append("AND c.id.seqNo IN (SELECT MAX(d.id.seqNo) from CmnInterfaceData d where d.cmnFileHdr.id.ifFileId='")
		.append(interfaceId).append("') )");
		
		if(null != filterCriteria && !filterCriteria.isEmpty()){
			dynaQuery.append(" AND (").append(filterCriteria).append(")");
		}
		
		if(null != sortOrderCriteria && !sortOrderCriteria.isEmpty()){
			dynaQuery.append(sortOrderCriteria);
		}
		
		if(null != sortOrderCriteria && !sortOrderCriteria.isEmpty()){
			stepExecution.getJobExecution().getExecutionContext().put(PDConstants.DYNAQUERY, dynaQuery.toString());
		}
		
		LOG.info("Dynamic Query : " + dynaQuery.toString());
		
		StringBuilder range = new StringBuilder("id.seqNo,");
		StringBuilder fixLengthFormat = new StringBuilder("%-10s");
		List<MstIfLayout> mstIfLayout = (List<MstIfLayout>) B61commonutility.getInterfaceLayoutByOrder().get(interfaceId);
		for (Iterator<MstIfLayout> iterator = mstIfLayout.iterator(); iterator.hasNext();) {
			MstIfLayout mstLayout = iterator.next();
			range.append(PDConstants.COLNAME).append(mstLayout.getColumnOrdr()).append(",");		
			fixLengthFormat.append("%-").append(mstLayout.getLngth()).append("s");
		}
		String trimRange = range.toString().replaceFirst(".$", "");
		stepExecution.getJobExecution().getExecutionContext().put("COLS", trimRange);
		stepExecution.getJobExecution().getExecutionContext().put("FIX_LENTH_FORMAT", fixLengthFormat.toString());
		
		LOG.info("############### Columns " + trimRange);
		LOG.info("############### Fix Length Format " + fixLengthFormat);
	}	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {				
		return stepExecution.getExitStatus();
	}	

}
