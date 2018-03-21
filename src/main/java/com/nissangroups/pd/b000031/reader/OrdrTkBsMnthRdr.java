/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000031
 * Module                  : Ordering		
 * Process Outline     : Create Weekly OCF Limit and auto allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 09-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000031.reader;

import static com.nissangroups.pd.util.PDConstants.CONSTANT_V3;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READING_EXCEPTION_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.b000031.util.B000031QueryConstants.EXT_ORDR_TAKE_BASE_MNTH_MAIN_PART;
import static com.nissangroups.pd.b000031.util.B000031QueryConstants.NOT_EQUAL_SC_CNDN;
import static com.nissangroups.pd.b000031.util.B000031QueryConstants.EQUAL_SC_CNDN;

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

import com.nissangroups.pd.b000031.mapper.B000031InputMapper;
import com.nissangroups.pd.mapper.EndItemMapper;
import com.nissangroups.pd.b000031.util.B000031Constants;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * Custom reader class for B000031
 *
 * @author z015399
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
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<B000031InputMapper>();
	
	/** Variable parameter values. */
	private Map<String, Object> parameterValues;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable transacted. */
	private boolean transacted = true;
	
	/** Variable por. */
	String por = null;
	
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
		por = jobInputs.getString(PDConstants.PRMTR_POR);
		
		queryString = EXT_ORDR_TAKE_BASE_MNTH_MAIN_PART.toString();
		queryString += NOT_EQUAL_SC_CNDN.toString();
		
		LOG.info("Query String "+ queryString);
		
		((JpaNativeQueryProvider<B000031InputMapper>) queryProvider).setSqlQuery(queryString);
		((JpaNativeQueryProvider<B000031InputMapper>) queryProvider).setEntityClass(B000031InputMapper.class);
		
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
		
		if (!transacted) {
			LOG.info("**** Inside not transacted ******");
			for (T entity : queryResult) {
				entityManager.detach(entity);
				results.add(entity);
			}
		} else {
			boolean resVal = checkRslts(queryResult);
			if(resVal)
				tx.commit();
		}
		
	}
	
	/**
	 * @param queryResult
	 * @return validated query results
	 */
	private boolean checkRslts(List<T> queryResult) {
		
		boolean trnsVal = false;
		
		if ( (queryResult != null) && queryResult.isEmpty()){
			LOG.info("Update Flag parameter One");
			String queryString1 = null;				
			queryString1 = EXT_ORDR_TAKE_BASE_MNTH_MAIN_PART.toString();
			queryString1 += EQUAL_SC_CNDN.toString();
			
			LOG.info("Query String1 "+ queryString1);
			
			((JpaNativeQueryProvider<B000031InputMapper>) queryProvider).setSqlQuery(queryString1);
			((JpaNativeQueryProvider<B000031InputMapper>) queryProvider).setEntityClass(B000031InputMapper.class);
			
			Query query1 = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());
			
			query1.setParameter(PDConstants.PRMTR_PORCD, por);
			
			if ( (query1.getResultList() != null) && query1.getResultList().isEmpty()){
				
				String[] strMsgParams = {B000031Constants.BATCHID,por,B000031Constants.WKLY_TBL_NM};
				CommonUtil.logMessage(PDMessageConsants.M00308, CONSTANT_V3 , strMsgParams);			
				CommonUtil.stopBatch();

			}
			
			else {
				
				LOG.info("**********************Before adding to result");
				 results.addAll(query1.getResultList());
				 LOG.info("Result size ....."+ results.size());
				 trnsVal = true;
				
			}
			
		}
		else{
		  LOG.info("**********************Before adding to result");
		  results.addAll(queryResult);
		  LOG.info("Result size ....."+ results.size());
		  trnsVal = true;
		}		
		
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
