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

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.ByrGrpMnthlyOcfLimitTrnRepository;
import com.nissangroups.pd.repository.ByrMnthlyOcfUsgRepository;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.MnthlyOrdrTrnRepository;
import com.nissangroups.pd.util.CommonUtil;



public class ByrGrpMnthlyOcfLmtProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ByrGrpMnthlyOcfLmtProcessor.class);


	@Autowired(required=false)
	private ByrGrpMnthlyOcfLimitTrnRepository byrGrpMnthlyOcfLimitTrnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException, ParseException {

		LOG.info(STEP_AFTER_START);
		
		List<Object[]> distinctByrGrpCarSrsList = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctByrGrpCarSrs(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
		byrGrpMnthlyOcfLimitTrnRepositoryObj.initialisation(input,distinctByrGrpCarSrsList);
		
		
		for(Object[] obj : distinctByrGrpCarSrsList){
		
			String byrGrpCd =  CommonUtil.convertObjectToString(obj[0]);
			String carSrs =  CommonUtil.convertObjectToString(obj[1]);
			String ordrTkBsMnth =  CommonUtil.convertObjectToString(obj[2]).trim();
			String prdMnth =  CommonUtil.convertObjectToString(obj[3]).trim();
			byrGrpMnthlyOcfLimitTrnRepositoryObj.updateNewOrdrDtls(input.getPorCd(), prdMnth, ordrTkBsMnth,carSrs,byrGrpCd);
			
		
		}
		
		
		
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	


}
