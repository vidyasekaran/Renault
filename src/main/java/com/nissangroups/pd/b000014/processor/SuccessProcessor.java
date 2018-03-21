/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 12-12-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.processor;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import com.nissangroups.pd.b000014.mapper.B000014InputMapper;
import com.nissangroups.pd.b000014.output.B000014Output;
import com.nissangroups.pd.repository.PlantOCFRepository;



/**
* SuccessProcessor class for B000014
*
* @author z015399
*/

public class SuccessProcessor implements
	ItemProcessor<B000014InputMapper,B000014Output>{
	
	private static final Log LOG = LogFactory.getLog(SuccessProcessor.class.getName());
	
	/** Variable entity manager. */
	@PersistenceContext(name=PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000014Output process(B000014InputMapper objInputMapper) throws Exception{
		
		B000014Output objB000014Output = new B000014Output();
		LOG.info("POR_CD "+ objInputMapper.getId().getPOR_CD() + 
				 "STAGE_CD" + objInputMapper.getSTAGE_CD()+
				"ORDER_TAKE_BASE_MONTH "+ objInputMapper.getId().getORDR_TAKE_BASE_MNTH());
		
		String otbm = objInputMapper.getId().getORDR_TAKE_BASE_MNTH();
		String porCd = objInputMapper.getId().getPOR_CD();
		
		PlantOCFRepository objPlantOCFRepository = new PlantOCFRepository();
		
		objPlantOCFRepository.updateSuccessStatus(porCd, otbm, entityManager);
		
		return objB000014Output;
		
		
	}

}
