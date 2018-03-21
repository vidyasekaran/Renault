/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Initialise and Update the Montly Order TRN Table for the valid records present in the Monthly Order Interface TRN 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.MnthlyOrdrTrnRepository;



public class MnthlyOrdrTrnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MnthlyOrdrTrnProcessor.class);

	
	@Autowired(required=false)
	private MnthlyOrdrTrnRepository mnthlyOrdrTrnRepositoryObj;
	
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException, ParseException {

		LOG.info(STEP_AFTER_START);
		
		mnthlyOrdrTrnRepositoryObj.initialisation(input);
		mnthlyOrdrTrnRepositoryObj.updateNewOrdrDtls(input);
		
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	


}
