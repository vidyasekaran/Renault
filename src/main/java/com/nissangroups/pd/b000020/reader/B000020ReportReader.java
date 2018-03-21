/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-B000020
 * Module          :Monthly
 * Process Outline :Receive Vehicle Production Type Master from Plant																	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-2015  	  z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.reader;


import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.REPORT_SIZE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ReportDao;


/**
 * This class is used for the B000007 Report Headers.
 *
 * @author z011479
 */
public class B000020ReportReader implements ItemReader<B000020ReportDao> {

	/** Variable b000007 report dao. */
	@Autowired
	private B000020ReportDao b000020ReportDao;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant logger. */
	private static final Log LOG = LogFactory.getLog
			(B000020ReportReader.class);
	

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public B000020ReportDao read() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(REPORT_SIZE +b000020ReportDao.getReportList().size());
		if(currentIndex < b000020ReportDao.getReportList().size()){
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return b000020ReportDao.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}
}