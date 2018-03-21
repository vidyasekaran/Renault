/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
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
import com.nissangroups.pd.b000008.output.B000008P2Output;
import com.nissangroups.pd.b000008.process.ExtractEndItmProcess;
import com.nissangroups.pd.b000008.process.ExtractMsQtyProcess;
import com.nissangroups.pd.b000008.process.UpdatePrdStageCdProcess;
import com.nissangroups.pd.b000008.process.InsertMnthlyordrTrnProcess;
import com.nissangroups.pd.b000008.util.B000008Constants;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class InsertMnthlyOrdrProcessor.
 *
 * @author z015060
 */

@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
@Component(B000008Constants.BATCH_ID_B000008_PROCESSOR_1)
public class InsertMnthlyOrdrProcessor  implements
ItemProcessor<B000008Output, B000008Output> {
	
	/** Variable LOG */
	private static final Log LOG = LogFactory.getLog(InsertMnthlyOrdrProcessor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	@Autowired(required = false)
	private ExtractMsQtyProcess objExtractMsQtyProcess;
	
	@Autowired(required = false)
	private UpdatePrdStageCdProcess objUpdatePrdStageCdProcess;
	
	@Autowired(required = false)
	private ExtractEndItmProcess objExtractEndItmProcess;
	
	@Autowired(required = false)
	private InsertMnthlyordrTrnProcess objInsertMnthlyordrTrnProcess;
	
	/**
	 * This method is to extraction ofMS_QTY and ORDER_QTY based on Batch trigger information,
	 * Extract Feature attached End Items and Insert Into Month Order Trn
	 * ProcessId : P0004.1,P0004.2,P0004.3,P0005.1.a, P0005.1.b, P0005.2
	 * P0006.1,P0006.2 , P0006.3, P0006.4 and P0007
	 * @param B000008Output the item
	 * @return the B000008Output class
	 * @throws Exception the exception
	 */
	@Override
	public B000008Output process(B000008Output objOutput) throws Exception {
		LOG.info("INSIDE InsertMnthlyOrdrProcessor processor");
		B000008P2Output objB000008P2Output = new B000008P2Output();
		
		if(objOutput.getObjB000008ParamOutput().getUpdateOnlyFlg().equals(PDConstants.N)){
			
			/** Process Id P0004.1,P0004.2,P0004.3 Extraction of MS_QTY and ORDER_QTY based on Batch trigger information */
			objB000008P2Output=objExtractMsQtyProcess.executeProcess(objOutput,objB000008P2Output);
			
			/** Process Id P0005.1.a, P0005.1.b, P0005.2 Insert Into Monthly order Trn */
			objUpdatePrdStageCdProcess.executeProcess(objOutput,objB000008P2Output);
		}
		
		/** Process Id P0006.1, P0006.2, P0006.3, P0006.4  Extract Feature attached End Items */
		objB000008P2Output=objExtractEndItmProcess.executeProcess(objOutput,objB000008P2Output);
		
		/** Process Id P0007  Insert Into Month Order Trn */
		objInsertMnthlyordrTrnProcess.executeProcess(objOutput,objB000008P2Output);
		
		objOutput.setObjB000008P2Output(objB000008P2Output);
		return objOutput;
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
