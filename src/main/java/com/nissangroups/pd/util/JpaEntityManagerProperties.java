/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-JpaEntityManagerProperties
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * The Class JpaEntityManagerProperties.
 */
public class JpaEntityManagerProperties {

/** Constant LOG. */
private static final Log LOG = LogFactory.getLog(JpaEntityManagerProperties.class);
	
	/** Variable em. */
	@PersistenceContext(name="PD-Persistence")
	private EntityManager em;

	/** Variable emf. */
	@PersistenceUnit(name="PD-Persistence")
	private EntityManagerFactory emf;
	
	
	
	/**
	 * Result extraction.
	 *
	 * @param Query the query
	 * @return Query ResultList
	 */
	
	public List<Object> ResultExtraction (String Query){
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		LOG.info("Add some string"+em.isOpen());
		@SuppressWarnings("unchecked")
		List<Object> resultList = em.createNativeQuery(Query).getResultList();
		return resultList;
		
		
	}
	
	

}
