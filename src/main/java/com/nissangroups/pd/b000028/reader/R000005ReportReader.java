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
import com.nissangroups.pd.b000028.output.R000005ReportDao;


/**
 * This class is used for the R000005 Report Headers.
 *
 * @author z015060
 */
public class R000005ReportReader implements ItemReader<R000005ReportDao> {

	/** Variable b000007 report dao. */
	@Autowired
	private R000005ReportDao objR000005Report;
	
	/** Variable current index. */
	private int currentIndex = 0;
	
	/** Constant logger. */
	private static final Log LOG = LogFactory.getLog
			(R000005ReportReader.class);
	

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public R000005ReportDao read() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(REPORT_SIZE +objR000005Report.getReportList().size());
		if(currentIndex < objR000005Report.getReportList().size()){
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return objR000005Report.getReportList().get(currentIndex++);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}
}