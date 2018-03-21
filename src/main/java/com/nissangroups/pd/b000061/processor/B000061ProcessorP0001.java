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
package com.nissangroups.pd.b000061.processor;

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
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000061.util.B000061CommonUtilityService;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class B000061ProcessorP0001 implements ItemProcessor<CmnInterfaceData, CmnInterfaceData> {

	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	/**Constant LOG*/
	private static final Log LOG = LogFactory.getLog
			(B000061ProcessorP0001.class);

	@SuppressWarnings("deprecation")
	@Override
	public CmnInterfaceData process(CmnInterfaceData item) throws Exception {		
		
		List<MstIfLayout> mstIfLayoutList = (ArrayList<MstIfLayout>) B61commonutility.getInterfaceLayoutByOrder().get(B61commonutility.getInterfaceId());
		
		CmnInterfaceData cmnIfData = new CmnInterfaceData();		
		cmnIfData.setId(new CmnInterfaceDataPK());		
		cmnIfData.getId().setSeqNo(item.getId().getSeqNo());		
		cmnIfData.getId().setRowNo(item.getId().getRowNo());	
		cmnIfData.getId().setIfFileId(item.getId().getIfFileId());
		
		CmnFileHdr fileHdr = new CmnFileHdr();		
		fileHdr.setId(new CmnFileHdrPK());
		fileHdr.getId().setIfFileId(item.getId().getIfFileId());
		fileHdr.getId().setSeqNo(item.getId().getSeqNo());
		cmnIfData.setCmnFileHdr(fileHdr);
		
		for (Iterator<MstIfLayout> iterator = mstIfLayoutList.iterator(); iterator.hasNext();) {
			
			MstIfLayout msit = iterator.next();
			
			String setVal = null;
			
			setVal = CommonUtil.getBeanValue(item,msit.getColumnOrdr().intValue());						
			CommonUtil.setBeanValue(cmnIfData, msit.getColumnOrdr().intValue(), setVal );
			
		}		
		return cmnIfData;
	}
	
	/**
	 * This method executed Each step Execution
	 * To get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		LOG.info(STAR+STEP_ID + stepExecution.getId()+STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		}
		else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,stepExecution.getFailureExceptions().toString()));						
		}
		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			LOG.info(M00113);
		}
		else if (stepExecution.getReadCount() != stepExecution.getWriteCount()) {
			LOG.info(M00043);
		}
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);		
	}
}
