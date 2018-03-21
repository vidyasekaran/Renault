/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.nissangroups.pd.b000011.output.B000011Output;
import com.nissangroups.pd.b000011.process.CpyRgnToMnthlyOCFLmtProcess;
import com.nissangroups.pd.b000011.process.ExtByrGrpCdAutoAllctnProcess;
import com.nissangroups.pd.b000011.util.B000011Constants;


/**
 * The Class CpyRgnToMnthlyOCFLmtProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000011Constants.BATCH_ID_B000011_PROCESSOR_2)
public class CpyRgnToMnthlyOCFLmtProcessor  implements
ItemProcessor<B000011Output, B000011Output>{

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private ExtByrGrpCdAutoAllctnProcess objExtByrGrpCdAutoAllctn;
	
	@Autowired(required = false)
	private CpyRgnToMnthlyOCFLmtProcess objCpyRgnToMnthlyOCFLmt;
	
	
	/**
	 * Extract summarized buyer Group level OCF limit and insert into the Buyer_group_monthly_ocf_limit
	 * Extract buyer_group with the auto_allocation, copy regional_monthly_ocf_lmt_trn to the buyer_monthly_ocf_lmt_trn
	 * and update the process flag in the Monthly_ocf_trn
	 * Process P0004, P0004, P0006, P0007 and P0008
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public B000011Output process(B000011Output object) throws Exception {
		
		// Process P0004 and P0005
		object=objExtByrGrpCdAutoAllctn.executeProcess(object);
		
		// Process P0006, P0007 and P0008
		objCpyRgnToMnthlyOCFLmt.executeProcess(object);
		
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
