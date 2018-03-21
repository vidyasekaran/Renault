/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B00007CustomReader
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.B000007_CUSTOM_READER;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.EXTRACTED_END_ITEM_SIZE;
import static com.nissangroups.pd.util.PDConstants.EXTRACTED_AFTER_GET_RESULT;

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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nissangroups.pd.mapper.B000007Dao;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.B000007QueryConstants;


/**
 * This class is used as a customized reader to read the data from the Spec
 * Master Tables.
 *
 * @author z011479
 * @param <T> the generic type
 */
@Component(B000007_CUSTOM_READER)
public class B000007CustomReader<T> extends DragonJpaPagingItemReader<T> {
	
	/** Constant LOG. */
	private final static Log LOG = LogFactory.getLog(B000007CustomReader.class
			.getName());
	
	/** Variable job execution. */
	private JobExecution jobExecution;

	/**
	 * Before step.
	 *
	 * @param stepExecution the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExecution = stepExecution.getJobExecution();
		this.stepExecution = stepExecution;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/** Variable entity manager factory. */
	private EntityManagerFactory entityManagerFactory;

	/** Variable update flag. */
	String updateFlag = null;

	/** Variable entity manager. */
	private EntityManager entityManager;

	/** Variable jpa property map. */
	private final Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();

	/** Variable query string. */
	private String queryString;

	/** Variable query provider. */
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<B000007Dao>();

	/** Variable parameter values. */
	private Map<String, Object> parameterValues;

	/** Variable transacted. */
	private boolean transacted = true;

	/** Variable step execution. */
	private StepExecution stepExecution;

	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#createQuery()
	 */
	@Override
	protected Query createQuery() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (queryProvider == null) {
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return entityManager.createQuery(queryString);
		} else {
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return queryProvider.createQuery();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#setEntityManagerFactory(javax.persistence.EntityManagerFactory)
	 */
	@Override
	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManagerFactory = entityManagerFactory;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#setParameterValues(java.util.Map)
	 */
	@Override
	public void setParameterValues(Map<String, Object> parameterValues) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.parameterValues = parameterValues;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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
	
	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#setQueryString(java.lang.String)
	 */
	@Override
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
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#doOpen()
	 */
	@Override
	protected void doOpen() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		entityManager = entityManagerFactory
				.createEntityManager(jpaPropertyMap);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG);
		}
		if (queryProvider != null) {
			queryProvider.setEntityManager(entityManager);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#doReadPage()
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
		Long stepId = stepExecution.getId();
		JobParameters jobInputs = jobExecution.getJobParameters();
		String endItemQuery = null;

		ExecutionContext stepContext = jobExecution.getExecutionContext();
		String minimumCarSeriesLimit = stepContext
				.getString(PDConstants.MINIMUM_CAR_SERIES_LIMIT);
		updateFlag = jobInputs.getString(PDConstants.UPDATE_ONLY_FLAG);
		if (updateFlag.equals(PDConstants.CONSTANT_ZERO)) {
			endItemQuery = B000007QueryConstants.ExtractEndItem.toString()
					+ B000007QueryConstants.ExtractEndItemWhereCondition.toString();
			((JpaNativeQueryProvider<B000007Dao>) queryProvider)
					.setSqlQuery(endItemQuery);
			LOG.info(endItemQuery);

		} else {
			endItemQuery = B000007QueryConstants.ExtractEndItem.toString()
					+ B000007QueryConstants.reExecuteStssInnrJoin.toString()
					+ B000007QueryConstants.ExtractEndItemWhereCondition.toString();
			((JpaNativeQueryProvider<B000007Dao>) queryProvider)
					.setSqlQuery(endItemQuery);
			LOG.info(endItemQuery);
		}
		((JpaNativeQueryProvider<B000007Dao>) queryProvider)
				.setEntityClass(B000007Dao.class);

		try {
			((JpaNativeQueryProvider<B000007Dao>) queryProvider)
					.afterPropertiesSet();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}

		Query query = createQuery().setFirstResult(getPage() * getPageSize())
				.setMaxResults(getPageSize());
		if (updateFlag.equals(PDConstants.CONSTANT_ZERO)) {
			query.setParameter(PDConstants.PRMTR_PORCD,
					jobInputs.getString(PDConstants.BATCH_POR_CODE));
			query.setParameter(PDConstants.MINIMUM_CAR_SERIES_LIMIT,
					minimumCarSeriesLimit);
		}

		else {
			query.setParameter(PDConstants.PRMTR_PORCD,
					jobInputs.getString(PDConstants.BATCH_POR_CODE));
			query.setParameter(PDConstants.MINIMUM_CAR_SERIES_LIMIT,
					minimumCarSeriesLimit);
			query.setParameter(PDConstants.PRMTR_BATCH_ID,
					PDConstants.BATCH_7_ID);
			query.setParameter(PDConstants.MASTER_TABLE,
					PDConstants.MASTER_TABLE_NAME);

		}
		if (parameterValues != null) {
			for (Map.Entry<String, Object> me : parameterValues.entrySet()) {
				query.setParameter(me.getKey(), me.getValue());
			}
		}

		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}

		if (!transacted) {
			List<T> queryResult = query.getResultList();
			for (T entity : queryResult) {
				entityManager.detach(entity);
				results.add(entity);
			}
		} else {
			LOG.info(EXTRACTED_END_ITEM_SIZE + query.getResultList().size());
			results.addAll(query.getResultList());
			LOG.info(EXTRACTED_AFTER_GET_RESULT
					+ query.getResultList().size());
			tx.commit();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/* (non-Javadoc)
	 * @see com.nissangroups.pd.reader.DragonJpaPagingItemReader#doClose()
	 */
	@Override
	protected void doClose() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		entityManager.close();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
