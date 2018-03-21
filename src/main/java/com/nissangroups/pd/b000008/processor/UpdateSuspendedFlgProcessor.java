/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000008.processor;

import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000008.output.B000008Output;
import com.nissangroups.pd.b000008.output.B000008P3Output;
import com.nissangroups.pd.b000008.process.UpdateSuspendedProcess;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.b000008.process.NscForecstVolProcess;


/**
 * The Class UpdateSuspendedFlgProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000008Constants.BATCH_ID_B000008_PROCESSOR_2)
public class UpdateSuspendedFlgProcessor  implements
ItemProcessor<B000008Output, B000008Output>{
	
	/** Variable LOG */
	private static final Log LOG = LogFactory.getLog(UpdateSuspendedFlgProcessor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	@Autowired(required = false)
	private NscForecstVolProcess objNscForecstVolProcess;
	
	@Autowired(required = false)
	private UpdateSuspendedProcess objUpdateSuspendedProcess;
	
	
	/**
	 * This method is to Extract Monthly order Trn for updating NSC_FORCAST_VOLUME, Extract Monthly order Trn for updating 
	 * NSC_CONFIRMATION_MONTHLY_TRN , Suspended /non suspended Data Extraction ,update suspended and non-suspended and
	 * Update the Spec Reference Time
	 * ProcessId : P0008,P0009,P0010,P0011,P0012,P0013,P0014,P0015,P0016
	 * @param MstMnthOrdrTakeBasePd the item
	 * @return the null
	 * @throws Exception the exception
	 */
	@Override
	public B000008Output process(B000008Output item) throws Exception {
		LOG.info(" inside UpdateSuspendedFlgProcessor ");
		B000008P3Output objB000008P3Output = new B000008P3Output();
		
		/** Process Id P0008,P0009,P0010,P0011 Extract Monthly order Trn  for updating NSC_FORCAST_VOLUME
		 * and NSC_CONFIRMATION_MONTHLY_TRN*/
		objB000008P3Output=objNscForecstVolProcess.executeProcess(item,objB000008P3Output);
		
		/** Process Id P0012,P0013,P0014,P0015,P0016  Suspended /non suspended Data Extraction and updation, update SpecRef time */
		objB000008P3Output=objUpdateSuspendedProcess.executeProcess(item,objB000008P3Output);
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
