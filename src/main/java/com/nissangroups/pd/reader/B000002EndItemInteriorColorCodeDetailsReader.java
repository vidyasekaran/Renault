/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemInteriorColorCodeDetailsReader
 * Module          :@Create Spec Masters
 * Process Outline :@Reading End item Interior Color details 
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @10 July 2015  	  @author(z013576)              New Creation
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

import static com.nissangroups.pd.util.B000002QueryConstants.Query_10_Fetch_EI_InteriorColorCode_details;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Reader Class to fetch the interior Colour Code Details
 * Process ID - P0007.
 * @version V1.0
 */
public class B000002EndItemInteriorColorCodeDetailsReader implements
		ItemReader<List<Object[]>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002EndItemInteriorColorCodeDetailsReader.class);
	
	/** Variable eMgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Variable jobExecution. */
	private JobExecution jobExecution;

	/** Variable maxindex. */
	/* Declaring Variables */
	private int maxIndex;
	
	/** Variable currentindexcount. */
	private int currentIndexCount;
	
	/** Variable empty list. */
	private List<Object[]> emptyList = null;
	
	/** Variable ei interior color code details list. */
	private List<Object[]> eiInteriorColorCodeDetailsList = new ArrayList<Object[]>();
	
	/** Variable fetch interior color code details. */
	private String fetchInteriorColorCodeDetails = Query_10_Fetch_EI_InteriorColorCode_details.toString();

	/**
	 * Before Stpe method to set the stepExecution to jobExecution Object.
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
	 * Non Processed End Item Spec Details Reader Class
	 * Process Id : P0008.
	 *
	 * @author z013576
	 * @version 1.0
	 * @return the list
	 * @throws PdApplicationNonFatalException the pd application non fatal exception
	 * @since 08 July 2015
	 */
	@Override
	public List<Object[]> read() throws PdApplicationNonFatalException{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porCd = jobExecution.getJobParameters().getString(PRMTR_PORCD);

		/* Query Execution */
		eiInteriorColorCodeDetailsList = eMgr.createNativeQuery(fetchInteriorColorCodeDetails).setParameter(PRMTR_PORCD, porCd)
											.getResultList();
			
		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
			LOG.info(READER_INFO+currentIndexCount);
			return emptyList;
			}/* Current Index Count Check Ends*/
			
		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning the fetched List */
		return eiInteriorColorCodeDetailsList;

	}
	
	/**
	 * Gets the max index.
	 *
	 * @return the max index
	 */
	public int getMaxIndex() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return maxIndex;
	}
	
	/**
	 * Sets the max index.
	 *
	 * @param maxIndex the new max index
	 */
	public void setMaxIndex(int maxIndex) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.maxIndex = maxIndex;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}