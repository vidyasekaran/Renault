/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-DragonJpaPagingItemReader
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.JPA_QUERY_PROVIDER_SET_MSG;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_ENTITY_MANAGER_MSG;
import static com.nissangroups.pd.util.PDConstants.UNCHECKED;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.nissangroups.pd.processor.B000003Processor;



/**
 * The Class DragonJpaPagingItemReader.
 *
 * @param <T> the generic type
 */
public class DragonJpaPagingItemReader<T> extends AbstractPagingItemReader<T> {

    /** Variable entity manager factory. */
    private EntityManagerFactory entityManagerFactory;

    /** Variable entity manager. */
    private EntityManager entityManager;

    /** Variable jpa property map. */
    private final Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();

    /** Variable query string. */
    private String queryString;

    /** Variable query provider. */
    private JpaQueryProvider queryProvider;

    /** Variable parameter values. */
    private Map<String, Object> parameterValues;

    /**
     * Instantiates a new dragon jpa paging item reader.
     */
    private static final Log LOG = LogFactory.getLog
			(DragonJpaPagingItemReader.class);
    
    public DragonJpaPagingItemReader() {
        setName(ClassUtils.getShortName(DragonJpaPagingItemReader.class));
    }



    /**
     * Creates the query.
     *
     * @return the query
     */
    protected Query createQuery() {
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

    /* (non-Javadoc)
     * @see org.springframework.batch.item.database.AbstractPagingItemReader#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        super.afterPropertiesSet();

        if (queryProvider == null) {
            Assert.notNull(entityManagerFactory);
            Assert.hasLength(queryString);
        }
        /** making sure that the appropriate (JPA) query provider is set */
        else {
            Assert.isTrue(queryProvider != null, JPA_QUERY_PROVIDER_SET_MSG);
        }
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
    public void setQueryProvider(JpaQueryProvider queryProvider) {
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
        /** set entityManager to queryProvider, so it participates */
        /** in JpaPagingItemReader's managed transaction */
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
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        entityManager.flush();
        entityManager.clear();

        Query query = createQuery().setFirstResult(getPage() * getPageSize()).setMaxResults(getPageSize());

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
        results.addAll(query.getResultList());

        tx.commit();
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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



    /* (non-Javadoc)
     * @see org.springframework.batch.item.database.AbstractPagingItemReader#doJumpToPage(int)
     */
    /* Empty Method
     * @see org.springframework.batch.item.database.AbstractPagingItemReader#doJumpToPage(int)
     */
    @Override
    protected void doJumpToPage(int arg0) {
        throw new UnsupportedOperationException();   
    }

}

 

 

 

 

 

 

