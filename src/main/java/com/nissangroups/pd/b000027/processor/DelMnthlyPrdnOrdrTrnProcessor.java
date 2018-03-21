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
package com.nissangroups.pd.b000027.processor;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.repository.MnthlyPrdnOrdrTrnRepository;


import static com.nissangroups.pd.util.PDConstants.*;

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.util.B000027Constants;

/**
 * This class is used to delete the existing monthly production order data  in temp table for the batch B000027.
 *
 * @author z014433
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000027Constants.DEL_MONTHLY_PRDN_ORDER_DETAILS_PROCESSOR)
public class DelMnthlyPrdnOrdrTrnProcessor implements ItemProcessor<OrdrTkBsPrdMstRowMapper, OrdrTkBsPrdMstRowMapper> { 
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(DelMnthlyPrdnOrdrTrnProcessor.class);
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;

	/** Variable job param por. */
	String jobParamPor = null;
	
	/** Variable job param order quantity. */
	String jobParamOrdrQty = null;
	
	/** Variable job param stage code. */
	String jobParamStgCd = null;
	
	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private MnthlyPrdnOrdrTrnRepository mnthPrdnOrdrRepo;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public OrdrTkBsPrdMstRowMapper process(OrdrTkBsPrdMstRowMapper item) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		LOG.info("Job inputs --> POR Cd is   : "+jobParamPor+ " and Stage Code  is :" +jobParamStgCd +" and order quantity flag is : "+jobParamOrdrQty);
		
		String ordrTkBsMnth = item.getId().getORDR_TAKE_BASE_MNTH();
		String porCd = item.getId().getPOR_CD();
		
		LOG.info("READER values in DelMnthlyPrdnOrdrTrnProcessor --> POR Cd is   : "+porCd+ " and order take base month  is :" +ordrTkBsMnth);
		
		mnthPrdnOrdrRepo.deleteExtngTmpTblData(porCd,ordrTkBsMnth);
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return item; 
	}
	
	/**
	 * Gets the jobParamPor
	 *
	 * @return the jobParamPor
	 */
	
	public String getJobParamPor() {
		return jobParamPor;
	}

	/**
	 * Sets the jobParamPor
	 *
	 * @param jobParamPor the jobParamPor to set
	 */
	
	public void setJobParamPor(String jobParamPor) {
		this.jobParamPor = jobParamPor;
	}

	/**
	 * Gets the jobParamOrdrQty
	 *
	 * @return the jobParamOrdrQty
	 */
	
	public String getJobParamOrdrQty() {
		return jobParamOrdrQty;
	}

	/**
	 * Sets the jobParamOrdrQty
	 *
	 * @param jobParamOrdrQty the jobParamOrdrQty to set
	 */
	
	public void setJobParamOrdrQty(String jobParamOrdrQty) {
		this.jobParamOrdrQty = jobParamOrdrQty;
	}

	/**
	 * Gets the jobParamStgCd
	 *
	 * @return the jobParamStgCd
	 */
	
	public String getJobParamStgCd() {
		return jobParamStgCd;
	}

	/**
	 * Sets the jobParamStgCd
	 *
	 * @param jobParamStgCd the jobParamStgCd to set
	 */
	
	public void setJobParamStgCd(String jobParamStgCd) {
		this.jobParamStgCd = jobParamStgCd;
	}

	/**
	 * Gets the entityManager
	 *
	 * @return the entityManager
	 */
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}