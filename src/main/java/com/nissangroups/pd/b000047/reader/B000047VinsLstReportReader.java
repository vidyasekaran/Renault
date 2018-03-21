/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.REPORT_SIZE;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000047.output.B000047ReportDao;
import com.nissangroups.pd.util.PDConstants;


/**
* The Class B000047VinsLstReportReader read the data from B000047 error report class and process .
* 
* @author z016127
*/
public class B000047VinsLstReportReader implements ItemReader<B000047ReportDao> {

		/** B000047ReportDao bean injection */	
		@Autowired(required = false)
		B000047ReportDao b000047ReportDao;
		
		/** Variable current index. */
		private int currentIndex = 0;
		
		/** Constant logger. */
		private static final Log LOG = LogFactory.getLog
				(B000047VinsLstReportReader.class);
		

		/**
		 * Method to read the data from B000047 error report class.
		 * 
		 * @return B000047ReportDao
		 * 					class
		 * @throws Exception the exception
		 */
		@Override
		public B000047ReportDao read() throws Exception {
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			LOG.info(REPORT_SIZE +b000047ReportDao.getVinsReportList().size());
			if(currentIndex < b000047ReportDao.getVinsReportList().size()){
				LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				return b000047ReportDao.getVinsReportList().get(currentIndex++);
			}
			else{
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return null;
			}
		}
		
		
		/**
		 * This method executed Each step Execution To get the count of Reader,
		 * Writer Based on the count values and write the Log.
		 *
		 * @param stepExecution
		 *            the step execution
		 * @throws Exception
		 */
		@AfterStep
		public void afterStep(StepExecution stepExecution) throws Exception {
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
			LOG.info(READ_COUNT + stepExecution.getReadCount());
			LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
			LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
			LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

			if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {				
				LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1,
							stepExecution.getFailureExceptions().toString()));										
			} 
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		}		
		
	}
