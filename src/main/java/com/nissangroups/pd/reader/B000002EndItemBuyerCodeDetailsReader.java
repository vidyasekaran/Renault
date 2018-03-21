/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemBuyerCodeDetailsReader
 * Module          :@Create Spec masters
 * Process Outline :@Reading unique buyer code details in End Item & Buyer Code level
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @08 July 2015  	  @author(z013576)              New Creation
 *
 */
package com.nissangroups.pd.reader;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_03_Fetch_EI_BuyerCode_details;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Non Processed End Item Spec Details Reader Class Process Id : P0003.
 * Process Id - P0003
 * @version 1.0
 */
public class B000002EndItemBuyerCodeDetailsReader implements
		ItemReader<List<Object[]>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000002EndItemBuyerCodeDetailsReader.class);

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable max index. */
	private int maxIndex;
	
	/** Variable current index count. */
	private int currentIndexCount;
	
	/** Variable empty list. */
	List<Object[]> emptyList = null;
	
	/** Variable ei buyer code details list. */
	List<Object[]> eiBuyerCodeDetailsList = new ArrayList<Object[]>();
	
	/** Variable fetch buyer code details. */
	String fetchBuyerCodeDetails = Query_03_Fetch_EI_BuyerCode_details.toString();

	/** Variable job execution. */
	/* Job execution Object creation to get JobParameters */
	private JobExecution jobExecution;

	/**
	 * Before Step method to set the stepExecution to jobExecution Object
	 * Setting the Jobexecution details to the stepExecution object This is to
	 * get the Job Parameters declared in the Main Class.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExecution = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Read EndItem Buyer Code details.
	 *
	 * @return the list
	 * @throws PdApplicationNonFatalException the pd application non fatal exception
	 */
	@Override
	public List<Object[]> read() throws PdApplicationNonFatalException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String jobPorCd = jobExecution.getJobParameters()
				.getString(PRMTR_PORCD);
		
		Query result = eMgr.createNativeQuery(fetchBuyerCodeDetails)
				.setParameter(PRMTR_PORCD, jobPorCd);

		eiBuyerCodeDetailsList = result.getResultList();

		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
			LOG.info(READER_INFO+currentIndexCount);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return emptyList;
		}/* Current Index Count Check Ends*/

		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning the Fetched List */
		return eiBuyerCodeDetailsList;

	}

	/**
	 * Gets the max index.
	 * @return the max index
	 */
	public int getMaxIndex() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return maxIndex;
	}

	/**
	 * Sets the max index.
	 * @param maxIndex the new max index
	 */
	public void setMaxIndex(int maxIndex) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.maxIndex = maxIndex;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
