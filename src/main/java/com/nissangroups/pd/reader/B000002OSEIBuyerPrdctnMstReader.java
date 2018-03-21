/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002OSEIBuyerPrdctnMstReader
 * Module          :@Create Spec Masters
 * Process Outline :@Reading End item Adopt &  Abolish date details 
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

import static com.nissangroups.pd.util.B000002QueryConstants.Query_27_EI_BUYER_ADPT_ABLSH_Fetch;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Reader Class to fetch the OEI details.
 * Process Id - P0008
 * @version V1.0
 */
public class B000002OSEIBuyerPrdctnMstReader implements
		ItemReader<List<Object[]>> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000002OSEIBuyerPrdctnMstReader.class);
	
	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	/** Obejct jobexecution. */
	private JobExecution jobExecution;

	/** Variable max index. */
	private int maxIndex;
	
	/** Variable currentindexcount. */
	private int currentIndexCount;
	
	/** Variable emptylist. */
	private List<Object[]> emptyList = null;
	
	/** Variable eibuyercodeadptablshlist. */
	private List<Object[]> eiBuyerCodeAdptablshList = new ArrayList<Object[]>();
	
	/** Variable fetchbuyercodedetails. */
	private String fetchBuyerCodeDetails = Query_27_EI_BUYER_ADPT_ABLSH_Fetch.toString();

	/**
	 * Before Step method to set the stepExecution to jobExecution Object.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	/**
	 * Non Processed End Item Spec Details Reader Class
	 * Process Id : P0006.
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
		
		eiBuyerCodeAdptablshList = eMgr.createNativeQuery(fetchBuyerCodeDetails).setParameter(PRMTR_PORCD, porCd).getResultList();
		
		/* Current Index Count Check Begins*/
		if (currentIndexCount > getMaxIndex()) {
			LOG.info(READER_INFO+currentIndexCount);
			return emptyList;
		}/* Current Index Count Check Ends*/
		
		currentIndexCount++;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		/* returning the fetched List */
		return eiBuyerCodeAdptablshList;
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
