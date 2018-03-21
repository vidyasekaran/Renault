/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.reader;


import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.REPORT_SIZE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import com.nissangroups.pd.b000028.output.R000004ReportDao;


/**
 * This class is used for the B000028 Report Headers.
 *
 * @author z011479
 */
public class R000004ReportReader implements ItemReader<R000004ReportDao> {

	/** Variable b000007 report dao. */
	@Autowired
	private R000004ReportDao objR000004Report;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant logger. */
	private static final Log LOG = LogFactory.getLog
			(R000004ReportReader.class);
	

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public R000004ReportDao read() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(REPORT_SIZE +objR000004Report.getReportList().size());
		if(currentIndex < objR000004Report.getReportList().size()){
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return objR000004Report.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}
}