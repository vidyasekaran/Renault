/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Check whether the system lock is locked for teh corresponding Por Cd,Order Take Base Month 
 * or the Por Cd,Buyer Group cd,car series 
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
import static com.nissangroups.pd.util.PDMessageConsants.M00206;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;



public class SysLockChkProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(SysLockChkProcessor.class);

	@Autowired(required=false)
	private VldnRepository vldnRepositoryObj;
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	private String errorCd;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

			LOG.info(STEP_AFTER_START);
			
			List<Object[]>  distinctByrGrpCarSrsList =  mnthlyOrdrIfTrnRepositoryObj.fetchDistinctByrGrpCarSrs(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
		
			for(Object[] distinctByrGrpCarSrs:distinctByrGrpCarSrsList){
				if(distinctByrGrpCarSrs!=null){
					String byrGrpCd = CommonUtil.convertObjectToString(distinctByrGrpCarSrs[0]);
					String carSrs = CommonUtil.convertObjectToString(distinctByrGrpCarSrs[1]);
					String ordrTkBsMnth = distinctByrGrpCarSrs[2].toString().trim();
					String ordrTkBsPrd = distinctByrGrpCarSrs[2].toString();
					MstMnthOrdrTakeBasePd mnthlyOrdrTkBsPdObj =input.getOrdrTkBsMnthList().get(ordrTkBsMnth);
					
					String errorMessage = M00206;
					
					if(null!=mnthlyOrdrTkBsPdObj.getSysLckSttsCd() && PDConstants.CONSTANT_ONE.equalsIgnoreCase(mnthlyOrdrTkBsPdObj.getSysLckSttsCd())){
						mnthlyOrdrIfTrnRepositoryObj.updateErrorCd(errorCd,errorMessage,input,byrGrpCd,carSrs,ordrTkBsPrd);
					} else {
						String lckFlg =  vldnRepositoryObj.fetchLockStatus(input.getPorCd(), ordrTkBsMnth, byrGrpCd, carSrs, input.getPrdOrdrStgCd());
							if(PDConstants.CONSTANT_ONE.equalsIgnoreCase(lckFlg)){
								mnthlyOrdrIfTrnRepositoryObj.updateErrorCd(errorCd,errorMessage,input,byrGrpCd,carSrs,ordrTkBsPrd);				
							}
					}
				}
				
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
