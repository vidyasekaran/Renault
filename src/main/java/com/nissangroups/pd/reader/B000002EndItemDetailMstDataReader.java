/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemDetailMstDataReader
 * Module          :@Create Spec Masters
 * Process Outline :@Reading End item details 
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @09 July 2015  	  @author(z013576)              New Creation
 *
 */
package com.nissangroups.pd.reader;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_12_Fetch_EI_OSEI_details;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Non Processed End Item Spec Details Reader Class
 * Process Id - P0005.
 * @version 1.0
  */
public class B000002EndItemDetailMstDataReader implements
		ItemReader<List<Object[]>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemDetailMstDataReader.class);
	
	/** Variable eMgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable jobExecution. */
	private JobExecution jobExecution;

	/** Variable maxindex. */
	private int maxIndex;
	
	/** Variable currentindexcount. */
	private int currentIndexCount;
	
	/** Variable emptylist. */
	private List<Object[]> emptyList = null;
	
	/** Variable eioseidetailslist. */
	private List<Object[]> eiOSEIDetailsList = new ArrayList<Object[]>();
	
	/** Variable fetchoseidetails. */
	private String fetchOSEIDetails = Query_12_Fetch_EI_OSEI_details.toString();
	
	
	/**
	 * Before Stpe method to set the stepExecution to jobExecution Object
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExecution = stepExecution.getJobExecution();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Reader Method to Fetch the OSEI details
	 * Process Id : P0005.
	 * @return Ei_OSEI_Details_List
	 * @throws PdApplicationNonFatalException the pd application non fatal exception
	 */
	@Override
	public List<Object[]> read() throws PdApplicationNonFatalException{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = jobExecution.getJobParameters().getString(PRMTR_PORCD);
		
		/* Query Execution */
		eiOSEIDetailsList = eMgr.createNativeQuery(fetchOSEIDetails).setParameter(PRMTR_PORCD, porCd).getResultList();
		
		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
			LOG.info(READER_INFO+currentIndexCount);
			return emptyList;
			}/* Current Index Count Check Ends*/
		
		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);	
		/* returning  the fetched List */
		return eiOSEIDetailsList;

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
