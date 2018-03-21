/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000003EndItemSpecReader
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
import static com.nissangroups.pd.util.PDConstants.B3_DRAGON_JPA_PAGING_ITEM_READER;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import static com.nissangroups.pd.util.PDConstants.END_ITEM_SPEC_MSG;
import static com.nissangroups.pd.util.PDConstants.READING_EXCEPTION_MSG;

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
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nissangroups.pd.mapper.EndItemMapper;
import com.nissangroups.pd.util.B000003QueryConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;




/**
 * Custom reader class to read the Master spec details. 
 *
 * @author z011479
 * @param <T> the generic type
 */
@Component(B3_DRAGON_JPA_PAGING_ITEM_READER)
public class B000003EndItemSpecReader<T> extends AbstractPagingItemReader<T> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000003EndItemSpecReader.class);

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
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<EndItemMapper>();

	/** Variable parameter values. */
	private Map<String, Object> parameterValues;
	
	/** Variable transacted. */
	private boolean transacted = true;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	
	
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
	 * Sets the entity manager factory.
	 *
	 * @param entityManagerFactory the new entity manager factory
	 */
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManagerFactory = entityManagerFactory;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();

		if (queryProvider == null) {
			Assert.notNull(entityManagerFactory);
			Assert.hasLength(queryString);
		}
		/** making sure that the appropriate (JPA) query provider is set */
		else {
			Assert.isTrue(queryProvider != null, JPA_QUERY_PROVIDER_SET_MSG);
		}
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
		ExecutionContext stepContext =  jobExecution.getExecutionContext();
		String minimumCarSeriesPeriod = (String) stepContext.get(PDConstants.MINIMUM_CAR_SERIES_PERIOD);
		
		
		String updateFlag = jobInputs.getString(PDConstants.UPDATE_ONLY_FLAG);
		String por = jobInputs.getString(PDConstants.PRMTR_POR);
		if(updateFlag.equals(PDConstants.PRMTR_ZERO)){
			String entItemSpec=B000003QueryConstants.endItemExtractionOnZero.toString();
			LOG.info(END_ITEM_SPEC_MSG+entItemSpec);
			if(entItemSpec != null){
			((JpaNativeQueryProvider<EndItemMapper>) queryProvider).setSqlQuery(entItemSpec);
			}
			else {
				 LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.BATCH_3_ID)
	        			 .replace(PDConstants.ERROR_MESSAGE_2, PDConstants.MESSAGE_DATA)
	        			 .replace(PDConstants.ERROR_MESSAGE_3, PDConstants.MESSAGE_POR_CD)
	        			 .replace( PDConstants.MESSAGE_POR_CD,stepExecution.getJobParameters().getString(
	        							 PDConstants.PRMTR_POR))
	        			 .replace(PDConstants.ERROR_MESSAGE_4,  PDConstants.ORDERABLE_END_ITEM_SPEC_MST) );
			}
		}
		else	
		{
			((JpaNativeQueryProvider<EndItemMapper>) queryProvider).setSqlQuery(B000003QueryConstants.endItemExtractionOnOne.toString());
		}
		((JpaNativeQueryProvider<EndItemMapper>) queryProvider).setEntityClass(EndItemMapper.class);
		try {
			((JpaNativeQueryProvider<EndItemMapper>) queryProvider).afterPropertiesSet();
		} catch (Exception e) {
			LOG.error(READING_EXCEPTION_MSG +e);
		}
		
		
		Query query = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());
		
		if(updateFlag.equals(PDConstants.PRMTR_ZERO)){
			query.setParameter(PDConstants.PRMTR_PORCD, por);
			query.setParameter(PDConstants.PRMTR_MINIMUM_CAR_SERIES_PERIOD, minimumCarSeriesPeriod);
		}else {
			query.setParameter(PDConstants.PRMTR_PORCD, por);
			query.setParameter(PDConstants.PRMTR_MINIMUM_CAR_SERIES_PERIOD, minimumCarSeriesPeriod);
			query.setParameter(PDConstants.PRMTR_BATCH_ID, PDConstants.BATCH_3_ID);
		}
		
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
		
		if (!transacted) {
			List<T> queryResult = query.getResultList();
			for (T entity : queryResult) {
				entityManager.detach(entity);
				results.add(entity);
			}
		} else {
			results.addAll(query.getResultList());
			tx.commit();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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


