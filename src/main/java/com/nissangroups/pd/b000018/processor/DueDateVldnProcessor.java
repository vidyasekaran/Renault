/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the Due Date From and Due Date To for Each Record from Monthly Order Interface TRN with Master Tables. 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00060;
import static com.nissangroups.pd.util.PDMessageConsants.M00061;
import static com.nissangroups.pd.util.PDMessageConsants.M00062;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;



public class DueDateVldnProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(DueDateVldnProcessor.class);
	
	@Autowired(required=false)
	private VldnRepository vldnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		
			
			List<Integer> rowNo =  vldnRepositoryObj.fetchDueDateNullRcrds(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
			
			if(rowNo!=null && !(rowNo.isEmpty())){				
				mnthlyOrdrIfTrnRepositoryObj.updateDueDate("15",M00060,rowNo,input);
			}
			rowNo =  vldnRepositoryObj.fetchDueDateFrmGrtrRcrds(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
			
			if(rowNo!=null && !(rowNo.isEmpty())){	
				mnthlyOrdrIfTrnRepositoryObj.updateDueDate("16",M00062,rowNo,input);
			}
			rowNo =  vldnRepositoryObj.fetchInVldDueDateRcrds(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
			
			if(rowNo!=null && !(rowNo.isEmpty())){	
				mnthlyOrdrIfTrnRepositoryObj.updateDueDate("17",M00061,rowNo,input);
			}

			LOG.info(STEP_AFTER_END);
		
		return input;
	}	

	
}

