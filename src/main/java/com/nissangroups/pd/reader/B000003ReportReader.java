/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000003ReportReader
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.reader;


import static com.nissangroups.pd.util.PDConstants.DOLLAR;

import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import static com.nissangroups.pd.util.PDConstants.REPORT_READER_FOR_SORT_MSG;
import java.util.Collections;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.dao.B000003ReportDao;




/**
 * The Class B000003ReportReader.
 */
public class B000003ReportReader implements ItemReader<B000003ReportDao>{

	/** Variable report dao. */
	@Autowired
	public B000003ReportDao reportDao;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000003ReportReader.class);
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	 public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String stepName = stepExecution.getStepName();
		LOG.info(REPORT_READER_FOR_SORT_MSG+stepName);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	 }
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public B000003ReportDao read() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		if(currentIndex < reportDao.getReportList().size()){
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return reportDao.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}
	

}
