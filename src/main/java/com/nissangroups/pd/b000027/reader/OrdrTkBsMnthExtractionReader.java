/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */  

package com.nissangroups.pd.b000027.reader;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READING_EXCEPTION_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;

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
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.b000027.util.QueryConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * @author z014433
 *
 * @param <T> Custom reader class for B000027
 */
public class OrdrTkBsMnthExtractionReader<T> extends AbstractPagingItemReader<T>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(OrdrTkBsMnthExtractionReader.class);
	
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
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<OrdrTkBsPrdMstRowMapper>();
	
	/** Variable parameter values. */
	private Map<String, Object> parameterValues;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable transacted. */
	private boolean transacted = true;

	/** Variable update Only flag. */
	String porCd = null;
	
	/** Variable stage code. */
	String stgCd = null;
	
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
		
		porCd = jobInputs.getString(PDConstants.PRMTR_PORCD);
		stgCd = jobInputs.getString(PDConstants.PRMTR_STAGE_CD);
		String ordrTkBsMnthQry = null;
		
		LOG.info(" Job Params --> Por Cd is :" +porCd +" and stage code is : "+stgCd);
		
		/** Process Id - P0001 */
			ordrTkBsMnthQry = QueryConstants.extractOrdrTkBsMnth.toString();
		
		LOG.info("Order Take Base Month Extraction Query String : "+ ordrTkBsMnthQry);
		
		((JpaNativeQueryProvider<OrdrTkBsPrdMstRowMapper>) queryProvider).setSqlQuery(ordrTkBsMnthQry);
		((JpaNativeQueryProvider<OrdrTkBsPrdMstRowMapper>) queryProvider).setEntityClass(OrdrTkBsPrdMstRowMapper.class);
		
		try {
			((JpaNativeQueryProvider<OrdrTkBsPrdMstRowMapper>) queryProvider).afterPropertiesSet();
		} catch (Exception e) {
			LOG.error(READING_EXCEPTION_MSG +e);
		}
		Query query = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());
		query.setParameter(PDConstants.PRMTR_PORCD, porCd);
		query.setParameter(PDConstants.PRMTR_STAGE_CD, stgCd);
		
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
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	private boolean validateRslts(List<T> queryResult) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		boolean trnsVal = false;
		
		if (!transacted) {
			for (T entity : queryResult) {
				entityManager.detach(entity);
				results.add(entity);
			}
		} else {
			if (queryResult == null || queryResult.isEmpty()){
				String[] messageParams = {B000027Constants.BATCH_27_ID,porCd,stgCd,PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD};
            	B000027CommonUtil.logMessage(PDMessageConsants.M00230, PDConstants.P0001, messageParams);
            	stepExecution.setExitStatus(ExitStatus.STOPPED); 
			} 
			
			else if (queryResult.size() > 1){
				String[] messageParams = {B000027Constants.BATCH_27_ID,porCd,stgCd,PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD};
            	B000027CommonUtil.logMessage(PDMessageConsants.M00231, PDConstants.P0001, messageParams);
            	stepExecution.setExitStatus(ExitStatus.STOPPED); 
			}
			else{
			    LOG.info(PDConstants.EXTRACTED_ORDER_TAKE_BASE_MONTH_SIZE + queryResult.size());
				results.addAll(queryResult);
				trnsVal = true;
			}
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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
	
	/**
	 * After step.
	 *
	 * @param stepExecution the step execution
	 * @return the exit status
	 */
	@AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR + stgCd);
		
		int readCnt = stepExecution.getReadCount();
		JobParameters jobInputs = jobExecution.getJobParameters();

            if( readCnt == 0 ) {
            	String[] messageParams = {B000027Constants.BATCH_27_ID,jobInputs.getString(PDConstants.PRMTR_PORCD),jobInputs.getString(PDConstants.PRMTR_STAGE_CD),PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD};
            	B000027CommonUtil.logMessage(PDMessageConsants.M00230, PDConstants.P0001, messageParams);
            	stepExecution.setExitStatus(ExitStatus.STOPPED); 
            } 
            
            LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
           return ExitStatus.COMPLETED;
    } 
	
}
