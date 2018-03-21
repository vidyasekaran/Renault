/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Validate the End Item for the Corresponding 
 * POR,Buyer from Monthly Order Interface TRN with Master Tables. 
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

import java.util.Arrays;
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
import com.nissangroups.pd.util.PDConstants;

public class EndItmVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(EndItmVldnProcessor.class);

	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	private String errorCd;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		QueryParamBean qryPrmBean = new QueryParamBean();
		qryPrmBean.setPrmtrCd(PDConstants.PRMTR_PRD_STG_CD);
		qryPrmBean.setKey1(input.getPorCd());
		qryPrmBean.setKey2(PDConstants.PRMTR_KEY2_RGLR_PROD_STAGE_CD);
		String prdStgCd = mnthlySchdlIfTrnRepositoryObj
				.getProdStgCdList(qryPrmBean);
		List<String> prdStgCdLst =  Arrays.asList(prdStgCd.split(",")); 
		qryPrmBean.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		qryPrmBean.setOcfRgnCd(input.getOcfRgnCd());
		qryPrmBean.setPrdStgCd(prdStgCdLst);
		List<Integer> seqNo = vldnRepositoryObj.fetchInVldEndItmR000020(
				input.getPorCd(), input.getTableName(), qryPrmBean);
		if (seqNo != null && !(seqNo.isEmpty())) {
			String errorMessage = M00059.replaceAll("&1", "End Item ");
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(seqNo, errorCd,
					errorMessage, input);
		}
		input.setPrdStgCd(prdStgCdLst);
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
