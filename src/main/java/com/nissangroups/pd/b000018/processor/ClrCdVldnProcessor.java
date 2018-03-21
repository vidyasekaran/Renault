/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the Exterior and Interior Color Cd for the Corresponding 
 * POR,EI,Additional Spec Cd,Spec Destination Cd,Buyer Cd from Monthly Order Interface TRN with Master Tables. 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00059;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;



public class ClrCdVldnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ClrCdVldnProcessor.class);

	@Autowired(required=false)
	private VldnRepository vldnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	private String errorCd;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
			
			List<Integer> rowNo =  vldnRepositoryObj.fetchInVldClrCd(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
			if(rowNo!=null && !(rowNo.isEmpty())){	
				String errorMessage = M00059.replaceAll("&1", "Exterior or Interior Color ");
				mnthlyOrdrIfTrnRepositoryObj.updateErrorCd(rowNo, errorCd,errorMessage,input);
			}
			
			LOG.info(STEP_AFTER_END);
		
		return input;
	}	


	public String getErrorCd() {
		return errorCd;
	}


	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	
}
