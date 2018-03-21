/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the Por CD from Monthly Order Interface TRN with Master Tables. 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00056;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;

public class PorVldnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(PorVldnProcessor.class);
	
	@Autowired(required=false)
	private VldnRepository vldnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	
	private String errorCd;

	@Override
	public InputBean process(InputBean input) throws PdApplicationException {
		LOG.info(STEP_AFTER_START);
		
		List<Integer> rowNo =  vldnRepositoryObj.fetchInVldPorCd(input.getFileId(),input.getTableName(),input.getSeqNo());
		if(rowNo!=null && !(rowNo.isEmpty())){
			mnthlyOrdrIfTrnRepositoryObj.updateErrorCd(rowNo, errorCd ,M00056,input);
		}
		
		LOG.info(STEP_AFTER_END);
	
		return input;
	}
		

	public VldnRepository getVldnRepositoryObj() {
		return vldnRepositoryObj;
	}

	public void setVldnRepositoryObj(VldnRepository vldnRepositoryObj) {
		this.vldnRepositoryObj = vldnRepositoryObj;
	}

	public MnthlyOrdrIfTrnRepository getMnthlyOrdrIfTrnRepositoryObj() {
		return mnthlyOrdrIfTrnRepositoryObj;
	}

	public void setMnthlyOrdrIfTrnRepositoryObj(
			MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj) {
		this.mnthlyOrdrIfTrnRepositoryObj = mnthlyOrdrIfTrnRepositoryObj;
	}

	

	public String getErrorCd() {
		return errorCd;
	}


	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	
}
