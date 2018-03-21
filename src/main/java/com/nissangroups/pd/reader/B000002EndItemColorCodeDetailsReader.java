/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemColorCodeDetailsReader
 * Module          :@Create Spec Masters
 * Process Outline :@Reading End item Colour Code details
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_07_Fetch_EI_ColorCode_details;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Non Processed End Item Spec Details Reader Class
 * Process Id - P0004.
 * @version 1.0
 */
public class B000002EndItemColorCodeDetailsReader implements
		ItemReader<List<Object[]>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemColorCodeDetailsReader.class);
	
	/** Variable eMgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable jobexecution. */
	private JobExecution jobExecution;
	
	/** Variable maxindex. */
	private int maxIndex;
	
	/** Variable currentindexcount. */
	private int currentIndexCount;
	
	/** Variable emptylist. */
	List<Object[]> emptyList = null;
	
	/** Variable eicolorcodedetailslist. */
	List<Object[]> eiColorCodeDetailsList = new ArrayList<Object[]>();
	
	/** Variable fetchcolorcodedetails. */
	String fetchcolorCodeDetails = Query_07_Fetch_EI_ColorCode_details.toString();
	
	/* Variables Declaration Ends */

	/**
	 * Before Step method to set the stepExecution to jobExecution Object
	 * Setting the Jobexecution details to the stepExecution object
	 * This is to get the Job Parameters declared in the Main Class.
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
	 * Reads Non Processed Colour Code EndItemSpecDetails.
	 *
	 * @return the list
	 * @throws PdApplicationNonFatalException the pd application non fatal exception
	 * @throws UnexpectedInputException the unexpected input exception
	 */
	@Override
	public List<Object[]> read() throws PdApplicationNonFatalException{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = jobExecution.getJobParameters().getString(PRMTR_PORCD);
				
		/* Query Execution */
		eiColorCodeDetailsList = eMgr.createNativeQuery(fetchcolorCodeDetails).setParameter(PRMTR_PORCD, porCd).getResultList();
		
		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
				LOG.info(READER_INFO+currentIndexCount);
				return emptyList;
			}/* Current Index Count Check Ends*/
		
		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		// returning the fetched details
		return eiColorCodeDetailsList;

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
