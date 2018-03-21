/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the Order Take Base Month from Monthly Order Interface TRN with Master Tables. 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00057;
import static com.nissangroups.pd.util.PDMessageConsants.M00229;



import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.OrdrTkBsPrdMstRepository;
import com.nissangroups.pd.repository.VldnRepository;



public class OrdrTkBsMnthVldnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(OrdrTkBsMnthVldnProcessor.class);

	@Autowired(required=false)
	private OrdrTkBsPrdMstRepository ordrTkBsPrdMstRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;

	@Autowired(required=false)
	private VldnRepository vldnRepositoryObj;

	private String errorCd;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {
		
		LOG.info(STEP_AFTER_START);	
		
		List<MstMnthOrdrTakeBasePd> orderTkBsMnthList = ordrTkBsPrdMstRepositoryObj.fetchOrdrTkBsOrdData(input.getPorCd(),input.getPrdOrdrStgCd());
		
	
		Map<String,MstMnthOrdrTakeBasePd> ordrTkBsMnthMap = new HashMap<String,MstMnthOrdrTakeBasePd>();
		Set<String> ordrTkBsMnthSet = new HashSet<String>();
		if(orderTkBsMnthList == null || orderTkBsMnthList.isEmpty()){
			String message = M00229.replaceAll("&1", input.getPrdOrdrStgCd()).replaceAll("&2", input.getPorCd());
			LOG.info(message)	;
			return null;
		}
		for(MstMnthOrdrTakeBasePd ordrTkBsMnth:orderTkBsMnthList){
			ordrTkBsMnthMap.put(ordrTkBsMnth.getId().getOrdrTakeBaseMnth(), ordrTkBsMnth);
			ordrTkBsMnthSet.add(ordrTkBsMnth.getId().getOrdrTakeBaseMnth()+"  ");
			
		}
		
		input.setOrdrTkBsMnthList(ordrTkBsMnthMap);
		List<Integer> rowNo = vldnRepositoryObj.fetchInVldOrdrTkBsMnth(input.getPorCd(),input.getFileId(),input.getTableName(),ordrTkBsMnthSet,input.getSeqNo());
		if(rowNo!=null && !(rowNo.isEmpty())){	
			String errorMessage = M00057;
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
