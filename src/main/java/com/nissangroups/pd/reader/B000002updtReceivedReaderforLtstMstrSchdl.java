/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002updtReceivedReaderforLtstMstrSchdl
 * Module          :@Create Spec Masters
 * Process Outline :@Reading updated OSEI details 
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
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_30_FetchOSEIUpdtdUkOseiiddetails;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Reader Class to fetch the OSEI details.
 * Process Id - P0012
 * @version V1.0
 */
public class B000002updtReceivedReaderforLtstMstrSchdl implements ItemReader<List<Object[]>>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002updtReceivedReaderforLtstMstrSchdl.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Objetc jobexecution. */
	private JobExecution jobExecution;

	/** Variable maxindex. */
	private int maxIndex;
	
	/** Variable currentindex count. */
	private int currentIndexCount;
	
	/** Variable emptylist. */
	private List<Object[]> emptyList = null;
	
	/** Variable eiupdtdukoseidetailslist. */
	private List<Object[]> eiUpdtdUkOseiDetailsList = new ArrayList<Object[]>();
	
	/** Variable fetchupdatedoseidetails. */
	private String fetchUpdatedOSEIDetails = Query_30_FetchOSEIUpdtdUkOseiiddetails.toString();
	
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
	 * Read OEI_SPEC updated details.
	 *
	 * @return the list
	 * @throws ParseException the parse exception
	 * @throws UnexpectedInputException the unexpected input exception
	 * @throws NonTransientResourceException the non transient resource exception
	 */
	@Override
	public List<Object[]> read(){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String jobPorCd = jobExecution.getJobParameters().getString(PRMTR_PORCD);

		/* Query Execution */
		eiUpdtdUkOseiDetailsList = eMgr.createNativeQuery(fetchUpdatedOSEIDetails).setParameter(PRMTR_PORCD, jobPorCd).getResultList();
		
		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
			LOG.info(READER_INFO+currentIndexCount);
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return emptyList;
		}/* Current Index Count Check Ends*/
		
		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning fetched List */
		return eiUpdtdUkOseiDetailsList;
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

