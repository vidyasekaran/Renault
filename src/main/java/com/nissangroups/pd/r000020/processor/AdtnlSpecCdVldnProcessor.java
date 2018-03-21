/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :validate the Additional Spec Code present in Monthly Order Interface TRN with Master Table
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
import static com.nissangroups.pd.util.PDMessageConsants.M00059;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;

public class AdtnlSpecCdVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(AdtnlSpecCdVldnProcessor.class);

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
		queryParamBean.setPrdStgCd(input.getPrdStgCd());
		List<Integer> rowNo = vldnRepositoryObj.fetchInVldAddtnlSpecCdR000020(
				input.getPorCd(), input.getTableName(),queryParamBean);
		if (rowNo != null && !(rowNo.isEmpty())) {
			String errorMessage = M00059.replaceAll("&1",
					"Additional Spec Code");
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(rowNo, errorCd,
					errorMessage, input);
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
