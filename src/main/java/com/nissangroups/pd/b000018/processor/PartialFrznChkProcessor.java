/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Validate the Partial Frozen Orders(Frozen Type - p) present in Monthly Order Interface TRN 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.processor;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.FrznVldnRepository;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;



public class PartialFrznChkProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(PartialFrznChkProcessor.class);



	@Autowired(required=false)
	private FrznVldnRepository frznVldnRepositoryObj;
	
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
			
		List<Object[]> partialFrznRcrds =  frznVldnRepositoryObj.fetchFrznSummrzdOrdrQty(input,"P","true",null,"false");
			
		frznVldnRepositoryObj.doFrznVldn(partialFrznRcrds, "20", input,"P");
			
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	



}
