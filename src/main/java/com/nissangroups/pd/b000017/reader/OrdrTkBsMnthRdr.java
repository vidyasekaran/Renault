/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.b000017.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READING_EXCEPTION_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDMessageConsants.M00159;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;

import com.nissangroups.pd.b000017.mapper.InputMapper;
import com.nissangroups.pd.mapper.EndItemMapper;
import com.nissangroups.pd.b000017.util.CommonUtil;
import com.nissangroups.pd.b000017.util.Constants;
import com.nissangroups.pd.b000017.util.QueryConstants;
import com.nissangroups.pd.util.PDConstants;


/**
 * @author z014433
 *
 *  Reader class for B000017
 */
public class OrdrTkBsMnthRdr<T> extends AbstractPagingItemReader<T>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(OrdrTkBsMnthRdr.class);
	
	/** Variable job execution. */
	private JobExecution jobExecution;
	
	/** Variable entity manager factory. */
	private EntityManagerFactory entityManagerFactory;
	
	/** Variable entity manager. */
	private EntityManager entityManager;
	
	/** Variable jpa property map. */
	private final Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();
	
	/** Variable query string. */
	private String queryString;
	
	/** Variable query provider. */
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<InputMapper>();
	
	/** Variable parameter values. */
	private Map<String, Object> parameterValues;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable transacted. */
	private boolean transacted = true;
	
	/** Job input parameters */
	String updateFlag = null;
	String por = null;
	String prodOrderStageCode = null;
	
	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        jobExecution = stepExecution.getJobExecution();
        this.stepExecution=stepExecution;
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	
	/**
	 * Creates the query.
	 *
	 * @return the query
	 */
	private Query createQuery() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (queryProvider == null) {
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return entityManager.createQuery(queryString);
		}
		else {
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return queryProvider.createQuery();
		}
	}
	
	/**
	 * Sets the transacted.
	 *
	 * @param transacted the new transacted
	 */
	public void setTransacted(boolean transacted) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.transacted = transacted;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Sets the entity manager factory.
	 *
	 * @param entityManagerFactory the new entity manager factory
	 */
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManagerFactory = entityManagerFactory;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (queryProvider == null) {
			Assert.notNull(entityManagerFactory);
			Assert.hasLength(queryString);
		}
		/** making sure that the appropriate (JPA) query provider is set */
		else {
			Assert.isTrue(queryProvider != null,
					JPA_QUERY_PROVIDER_SET_MSG);
		}
		
	}
	
	/**
	 * Sets the parameter values.
	 *
	 * @param parameterValues the parameter values
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.parameterValues = parameterValues;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the query string.
	 *
	 * @param queryString the new query string
	 */
	public void setQueryString(String queryString) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.queryString = queryString;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/**
	 * Sets the query provider.
	 *
	 * @param queryProvider the new query provider
	 */
	public void setQueryProvider(JpaNativeQueryProvider queryProvider) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.queryProvider = queryProvider;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#doOpen()
	 */
	@Override
	protected void doOpen() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		super.doOpen();

		entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG);
		}
		if (queryProvider != null) {
			queryProvider.setEntityManager(entityManager);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#doReadPage()
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	protected void doReadPage() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        EntityTransaction tx = null;
		
		if (transacted) {
			tx = entityManager.getTransaction();
			tx.begin();
			
			entityManager.flush();
			entityManager.clear();
		}
		
		JobParameters jobInputs = jobExecution.getJobParameters();
		 updateFlag = jobInputs.getString(PDConstants.UPDATE_ONLY_FLAG);
		 por = jobInputs.getString(PDConstants.PRMTR_POR);
		 prodOrderStageCode = jobInputs.getString(PDConstants.PRMTR_PRODUCTION_STAGE_CODE);
		 
		if(updateFlag.equals(PDConstants.PRMTR_ZERO)){
			queryString = constructQryStr(prodOrderStageCode); 
		}
		else{
			queryString = QueryConstants.OTBM_Query_PO_STG_CD_NOT_SO_SC.toString();
		}
		
		LOG.info("Query String "+ queryString);
		((JpaNativeQueryProvider<InputMapper>) queryProvider).setSqlQuery(queryString);
		((JpaNativeQueryProvider<InputMapper>) queryProvider).setEntityClass(InputMapper.class);
		
		try {
			((JpaNativeQueryProvider<EndItemMapper>) queryProvider).afterPropertiesSet();
		} catch (Exception e) {
			LOG.error(READING_EXCEPTION_MSG +e);
		}
		
		Query query = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());
		
		query.setParameter(PDConstants.PRMTR_PORCD, por);
		
		LOG.info("++++++++Print query+++++++" + query.toString());
		
		if (parameterValues != null) {
			for (Map.Entry<String, Object> me : parameterValues.entrySet()) {
				query.setParameter(me.getKey(), me.getValue());
			}
		}
		
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		
		List<T> queryResult = query.getResultList();
		boolean resVal = validateRslts(queryResult);
		if(resVal)
			tx.commit();
	}
	
	/**
	 * @param prodOrderStageCode
	 * @return constructed  query string
	 */
	private String constructQryStr(String prodOrderStageCode) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		String queryStringVal = null;
		
		if (prodOrderStageCode.equals(Constants.PROD_ORDER_STAGE_CD_10)){
			queryStringVal = QueryConstants.OTBM_Query_PO_STG_CD_10.toString();
		}
		else 
		if (prodOrderStageCode.equals(Constants.PROD_ORDER_STAGE_CD_20)){
			queryStringVal = QueryConstants.OTBM_Query_PO_STG_CD_20.toString();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return queryStringVal;
	}


	/**
	 * @param queryResult
	 * @return validation result of query
	 */
	private boolean validateRslts(List<T> queryResult) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		boolean trnsVal = false;
		
		if (!transacted) {
			LOG.info("**** Inside not transacted ******");
			for (T entity : queryResult) {
				entityManager.detach(entity);
				results.add(entity);
			}
		} else {
			if ((updateFlag.equals(PDConstants.PRMTR_ZERO))  && (queryResult != null) && (queryResult.size() != 1)){
				LOG.info("Update Flag parameter Zero");
				String[] messageParams = {Constants.BATCH_ID,prodOrderStageCode,por,"MONTHLY_ORDER_TAKE_BASE_PERIOD_MST"};
				CommonUtil.logMessage(M00159, Constants.P1, messageParams);
				com.nissangroups.pd.util.CommonUtil.stopBatch();
				
			} 
			
			else if ((updateFlag.equals(PDConstants.PRMTR_ONE)) && (queryResult != null) && queryResult.isEmpty()){
				LOG.info("Update Flag parameter One");
				String[] messageParams = {Constants.BATCH_ID,prodOrderStageCode,por,PDConstants.MONTHLY_ORDER_TAKE_BASE_PERIOD_MST};
				CommonUtil.logMessage(M00159, Constants.P1, messageParams);
				com.nissangroups.pd.util.CommonUtil.stopBatch();
			}
			else{
				LOG.info("**********************Before adding to result");
			  results.addAll(queryResult);
			  LOG.info("Result size ....."+ results.size());
			  trnsVal = true;
			}
		}
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return trnsVal;
	}


	/* (non-Javadoc)
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#doJumpToPage(int)
	 */
	@Override
	protected void doJumpToPage(int itemIndex) {
		/** Default JPA Reader method should not be removed */
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#doClose()
	 */
	@Override
	protected void doClose() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		entityManager.close();
		super.doClose();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
