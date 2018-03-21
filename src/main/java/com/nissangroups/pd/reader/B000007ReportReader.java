/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000007ReportReader
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
import static com.nissangroups.pd.util.PDConstants.REPORT_SIZE;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.dao.B000007ReportDao;


/**
 * This class is used for the B000007 Report Headers.
 *
 * @author z011479
 */
public class B000007ReportReader implements ItemReader<B000007ReportDao> {

	/** Variable b000007 report dao. */
	@Autowired
	private B000007ReportDao b000007ReportDao;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant logger. */
	private static final Log LOG = LogFactory.getLog
			(B000007ReportReader.class);
	

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public B000007ReportDao read() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(REPORT_SIZE +b000007ReportDao.getReportList().size());
		if(currentIndex < b000007ReportDao.getReportList().size()){
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return b000007ReportDao.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}
	
	

	

}