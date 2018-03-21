/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */  
package com.nissangroups.pd.b000051.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.b000051.util.B000051Constants.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.process.InsertFeatDetailsProcess;
import com.nissangroups.pd.b000051.process.UpdateBuyerGrpOCFProcess;
import com.nissangroups.pd.b000051.process.UpdateRegionalOCFProcess;


/**
 * The Class UpdateSuspendedFlgProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(BATCH_ID_B000051_PROCESSOR_2)
public class FeatureCodeProcessor  implements
ItemProcessor<B000051Output, B000051Output>{
	
	/** Variable LOG */
	private static final Log LOG = LogFactory.getLog(FeatureCodeProcessor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private InsertFeatDetailsProcess objInsertFeatDetails;
	
	@Autowired(required = false)
	private UpdateBuyerGrpOCFProcess objUpdateBuyerGrpOCF;
	
	@Autowired(required = false)
	private UpdateRegionalOCFProcess objUpdateRegionalOCF;
	
	@Override
	public B000051Output process(B000051Output item) throws Exception {
		LOG.info("FeatureCodeProcessor");
		
		item =objInsertFeatDetails.executeProcess(item);
		
		//Process P0012
		item =objUpdateBuyerGrpOCF.executeProcess(item);
		
		//Process P0013
		item =objUpdateRegionalOCF.executeProcess(item);
		
	return null;
	}
	
	/** gets the entity manager and @return the entity manager */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/** sets the entity manager and @return the entity manager */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	

}
