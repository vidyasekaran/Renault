/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-10-2015      z014433(RNTBCI)               Initial Version
 *
 */  

package com.nissangroups.pd.b000021.reader;

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

import com.nissangroups.pd.b000021.mapper.MstMnthlyOrdrTkBsPrdRowMapper;
import com.nissangroups.pd.b000021.util.B000021CommonUtil;
import com.nissangroups.pd.b000021.util.QueryConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class CustomOrdrTkBsMnthReader<T> extends AbstractPagingItemReader<T>{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CustomOrdrTkBsMnthReader.class);
	
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
	private JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<MstMnthlyOrdrTkBsPrdRowMapper>();
	
	/** Variable parameter values. */
	private Map<String, Object> parameterValues;
	
	/** Variable step execution. */
	private StepExecution stepExecution;
	
	/** Variable transacted. */
	private boolean transacted = true;

	/** Variable update Only flag. */
	String updtOnlyFlg = null;
	
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
		
		updtOnlyFlg = jobInputs.getString(PDConstants.PRMTR_UPDATE_FLAG);
		stgCd = jobInputs.getString(PDConstants.PRMTR_STAGE_CD);
		String ordrTkBsMnthQry = null;
		
		LOG.info(" Job Params --> Update Only Flag is :" +updtOnlyFlg +" and stage code is : "+stgCd);
		
		/** Process Id - P0001.1 */
		if (updtOnlyFlg.equals(PDConstants.YES)) {
			ordrTkBsMnthQry = QueryConstants.getOrdrTkBsMnth.toString()
											+QueryConstants.getOrdrTkBsMnthForUpdtFlagY.toString();
		} 
		/** Process Id - P0001.2 */
		else {
			ordrTkBsMnthQry = getOrdrTkBsMnthQry(stgCd);
		}
		
		LOG.info("Order Take Base Month Extraction Query String : "+ ordrTkBsMnthQry);
		
		((JpaNativeQueryProvider<MstMnthlyOrdrTkBsPrdRowMapper>) queryProvider).setSqlQuery(ordrTkBsMnthQry);
		((JpaNativeQueryProvider<MstMnthlyOrdrTkBsPrdRowMapper>) queryProvider).setEntityClass(MstMnthlyOrdrTkBsPrdRowMapper.class);
		
		try {
			((JpaNativeQueryProvider<MstMnthlyOrdrTkBsPrdRowMapper>) queryProvider).afterPropertiesSet();
		} catch (Exception e) {
			LOG.error(READING_EXCEPTION_MSG +e);
		}
		
		Query query = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());
		query.setParameter(PDConstants.PRMTR_PORCD, jobInputs.getString(PDConstants.PRMTR_PORCD));
		
		LOG.info("Order Take Base Month Extraction Query String after setting params : "+ query.toString());
		
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
			LOG.info(PDConstants.EXTRACTED_ORDER_TAKE_BASE_MONTH_SIZE + query.getResultList().size());
			results.addAll(query.getResultList());
			LOG.info(PDConstants.EXTRACTED_AFTER_GET_RESULT	+ query.getResultList().size());
			tx.commit();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	/**
	 * @param stgCdVal
	 * @return order take base month query
	 */
	private String getOrdrTkBsMnthQry(String stgCdVal) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ordrTkBsMnthQry = null;
		
		switch (stgCdVal) {
		
		case PDConstants.DRAFT_D1:
			ordrTkBsMnthQry = QueryConstants.getOrdrTkBsMnth.toString()
					+QueryConstants.getOrdrTkBsMnthForUpdtFlagNStgD1.toString();
			break;
			
		case PDConstants.DRAFT_D2:
			ordrTkBsMnthQry = QueryConstants.getOrdrTkBsMnth.toString()
					+QueryConstants.getOrdrTkBsMnthForUpdtFlagNStgD2.toString();
			break;
			
		case PDConstants.FINAL_F1:
			ordrTkBsMnthQry = QueryConstants.getOrdrTkBsMnth.toString()
					+QueryConstants.getOrdrTkBsMnthForUpdtFlagNStgF1.toString();
			break;
			
		case PDConstants.FINAL_F2:
			ordrTkBsMnthQry = QueryConstants.getOrdrTkBsMnth.toString()
					+QueryConstants.getOrdrTkBsMnthForUpdtFlagNStgF2.toString();
			break;

		default:
			break;
		}	
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return ordrTkBsMnthQry;
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
		
		boolean isDraft = isDraftOrdr();
		boolean isFinal = isFinalOrdr();
		
		int readCnt = stepExecution.getReadCount();
		JobParameters jobInputs = jobExecution.getJobParameters();

            if( readCnt == 0 && (isDraft || isFinal)) {
            	
            	String[] messageParams = {PDConstants.BATCH_21_ID,jobInputs.getString(PDConstants.PRMTR_STAGE_CD),jobInputs.getString(PDConstants.PRMTR_PORCD),PDConstants.TBL_NM_MONTHLY_ORDER_TAKE_BASE_PERIOD};
            	
            	B000021CommonUtil.logMessage(PDMessageConsants.M00159, PDConstants.P0001, messageParams);
            	stepExecution.setExitStatus(ExitStatus.STOPPED); 
            	
            } 
            
            LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
           return ExitStatus.COMPLETED;
    } 
	
	public boolean isDraftOrdr()
	{
		return (stgCd.equals(PDConstants.DRAFT_D1)||stgCd.equals(PDConstants.DRAFT_D2)) ?  true : false;
	}

	public boolean isFinalOrdr()
	{
		return (stgCd.equals(PDConstants.FINAL_F1)||stgCd.equals(PDConstants.FINAL_F2)) ?  true : false;
	}

}
