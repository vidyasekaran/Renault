/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000040.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000040.bean.B000040InputParam;
import com.nissangroups.pd.i000044.bean.I000044InputParam;
import com.nissangroups.pd.util.IfCommonUtil;

/**
 * This class is used to set the query to a reader to generate Production Order Detail Report
 * 
 * @author z016127
 *
 */
public class B000040WklyOrdOcfBreachReportQuerySetTasklet implements Tasklet, InitializingBean {

	private static final Log LOG = LogFactory
			.getLog(B000040WklyOrdOcfBreachReportQuerySetTasklet.class.getName());

	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManagerFactory;

	/**
	 * Indicates that a method declaration is intended to override a method
	 * declaration in a supertype.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * P0001 Generate the sequence number and insert a record in Common File Header
	 * Create the custom query and store in ChunkContext
	 * 
	 * @param contribution
	 *            StepContribution object
	 * @param chunkContext
	 *            ChunkContext object
	 *            
	 * @return RepeatStatus object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		// Getting job parameter
		JobParameters jobParameters = chunkContext.getStepContext()
				.getStepExecution().getJobParameters();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return RepeatStatus.FINISHED;
	}
	
	/**
	 * P0002 Creates the custom query to extract the order take base period
	 * based on the input parameter
	 * 
	 * @param paramList 
	 * @param ifFileId 
	 * @param updatedData 
	 * 
	 * @return the list of I000044InputParam
	 */
	private List<B000040InputParam> createCustomQuery() 
	{

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<B000040InputParam> inputParamList = new ArrayList<B000040InputParam>();
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return inputParamList;

	}
	
	/**
	 * Format the input values and store in list
	 * @param inputStr
	 * @return
	 */
	private List<B000040InputParam> deformatInputs(String inputStr) 
	{

		
		return null;
	}
	
	/**
	 * Format the input values and store in I000044InputParam
	 * @param str
	 * @return
	 */
	private B000040InputParam getInputParam(String str)
	{
		
		String[] val = str.split("&");
		
		return null;
	}
	

	public EntityManager getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManager entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	
}
