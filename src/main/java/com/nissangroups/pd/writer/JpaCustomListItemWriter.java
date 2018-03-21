/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-JpaCustomListItemWriter
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.writer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.util.Assert;

import com.nissangroups.pd.r000020.processor.ClrCdVldnProcessor;

/**
 * This is customized ListItemWriter to insert the extracted frozen data list to
 * database PROCESS P0008.2
 *
 * @author z011479
 * @param <T>
 *            the generic type
 */
@Transactional
public class JpaCustomListItemWriter<T> extends JpaItemWriter<T> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(JpaCustomListItemWriter.class);

	/**
	 * Instantiates a new jpa custom list item writer.
	 */
	public JpaCustomListItemWriter() {
		super();
	}

	/** Variable entity manager factory. */
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Set the EntityManager to be used internally.
	 *
	 * @param entityManagerFactory
	 *            the entityManagerFactory to set
	 */
	@Override
	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * Check mandatory properties - there must be an entityManagerFactory.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityManagerFactory,
				"An EntityManagerFactory is required");
	}

	/**
	 * Merge all provided items that aren't already in the persistence context
	 * and then flush the entity manager.
	 *
	 * @param items
	 *            the items
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List items) {
		LOG.info("Inside Jpa Custom List Writer");
		// Unwrapping list
		List<T> unwrappedItems = new ArrayList<T>();
		List<List<T>> wrappedItems = (List<List<T>>) items;
		for (List<T> singleList : wrappedItems) {
			LOG.info("Unwrapping List before ");
			unwrappedItems.addAll(singleList);
			LOG.info("Unwrapping List After ");
		}

		EntityManager entityManager = EntityManagerFactoryUtils
				.getTransactionalEntityManager(entityManagerFactory);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					"Unable to obtain a transactional EntityManager");
		}
		doWrite(entityManager, unwrappedItems);
		entityManager.flush();
		LOG.info("Unwrapping List After ");
	}

}
