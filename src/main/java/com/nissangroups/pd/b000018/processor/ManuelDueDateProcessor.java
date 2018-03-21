/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Initialise and Update teh Manuel Due Date Paramter Table for the valid records present in the Monthly Order Interface TRN 
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.ManuelDueDateRepository;



public class ManuelDueDateProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ManuelDueDateProcessor.class);


	@Autowired(required=false)
	private ManuelDueDateRepository manuelDueDateRepositoryObj;
	
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		
		manuelDueDateRepositoryObj.delete(input);
		manuelDueDateRepositoryObj.insert(input);
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	

	
}
