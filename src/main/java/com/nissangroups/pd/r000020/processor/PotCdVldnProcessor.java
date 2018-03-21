/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate Pot CD from Monthly Order Interface TRN with Master Tables. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;
import static com.nissangroups.pd.util.PDMessageConsants.M00193;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;

public class PotCdVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(PotCdVldnProcessor.class);

	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	private String errorCd;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		QueryParamBean queryParamBean = new QueryParamBean();
		queryParamBean.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		queryParamBean.setOcfRgnCd(input.getOcfRgnCd());
		CommonUtil cmnUtl = new CommonUtil();
		List<Integer> seqId = vldnRepositoryObj.fetchInVldPotCdR000020(
				input.getPorCd(), input.getTableName(), queryParamBean);
		if (seqId != null && !(seqId.isEmpty())) {
			String errorMessage = M00193;
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(seqId, errorCd,
					errorMessage, input);
		}

		List<Object[]> seqIdSls = vldnRepositoryObj.fetchInVldSlsNoteR000020(
				input.getPorCd(), input.getTableName(), queryParamBean);
		if (seqIdSls != null && !(seqIdSls.isEmpty())) {
			for (Object[] slsNtArry : seqIdSls) {
				String errorMessage = "M00269 : Order has been eliminated because  Sales Note &1 are not equal &2 :POT & SALES_NOTE_NO ";
				Map<String,String> errPrm = new HashMap<String,String>();
				errPrm.put("1", (String) slsNtArry[1]);
				errPrm.put("2", (String) slsNtArry[2]);
				errorMessage = cmnUtl.getlogErrorMessage(errorMessage, errPrm);
				
				mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
						CommonUtil.stringtoInt((String)slsNtArry[0]), errorCd,
						errorMessage, input);
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
