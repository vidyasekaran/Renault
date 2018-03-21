/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-R000036
 * Module          :Ordering
 * Process Outline :Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 02-Nov-2015  z014029(RNTBCI)              New Creation
 *
 */
package com.nissangroups.pd.r000036.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.REPORT_SIZE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.r000036.dao.R000036ReportDao;

/**
* The Class R000036ReportReader.
*/
public class R000036ReportReader implements ItemReader<R000036ReportDao> {

		/** Variable r000036 report dao. */
		@Autowired
		private R000036ReportDao r000036ReportDao;
		
		/** Variable current index. */
		private int currentIndex = 0;
		
		/** Constant logger. */
		private static final Log LOG = LogFactory.getLog
				(R000036ReportReader.class);
		

		/* (non-Javadoc)
		 * @see org.springframework.batch.item.ItemReader#read()
		 */
		@Override
		public R000036ReportDao read() throws Exception {
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			LOG.info(REPORT_SIZE +r000036ReportDao.getReportList().size());
			if(currentIndex < r000036ReportDao.getReportList().size()){
				LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				return r000036ReportDao.getReportList().get(currentIndex++);
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return null;
		}
	}