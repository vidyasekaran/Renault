/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
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

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.dao.B000001ReportDao;

/**
 * The Class B000001ReportReader.
 *
 * @author z002548
 */
public class B000001ReportReader implements ItemReader<B000001ReportDao> {

	/** Variable report dao. */
	@Autowired
	private B000001ReportDao reportDao;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000001ReportReader.class);

	/**
	 * Before step method to Sort the Collections.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (!reportDao.getReportList().isEmpty())
			Collections.sort(reportDao.getReportList());
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * In the read method get List size and increment the index value.
	 *
	 * @return the b000001 report dao
	 * @see org.springframework.batch.item.ItemReader#read() To Read the Report
	 *      Data and return to Processor or Writer
	 */
	@Override
	public B000001ReportDao read() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (currentIndex < reportDao.getReportList().size()) {
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return reportDao.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

}
