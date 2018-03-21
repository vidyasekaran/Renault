/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Delete teh Processed File from the Monthly Order Interface TRN Table
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstOseiFrzn;
import com.nissangroups.pd.model.MstOseiProdType;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class FrznVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(FrznVldnProcessor.class);

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;

	private String errorCd;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		CommonUtil cmnUtl = new CommonUtil();
		QueryParamBean queryParamBean = new QueryParamBean();
		queryParamBean.setPrmtrCd(PDConstants.PRMTR_CD_MS_SCHEDULE_VALIDATON);
		queryParamBean.setKey1(input.getPorCd());
		queryParamBean
				.setKey2(PDConstants.PRMTR_CD_FROZEN_AND_PRODUCTION_TYPE_VALIDATION);
		queryParamBean.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		queryParamBean.setOcfRgnCd(input.getOcfRgnCd());
		List<MstPrmtr> frznFlg = mnthlySchdlIfTrnRepositoryObj
				.getFrznVldFlg(queryParamBean);
		String warnCd = PDConstants.PRMTR_R20_WRN_CD;
		if (frznFlg.isEmpty()) {
			LOG.error("There is no FROZEN CD for  POR_CD = "
					+ queryParamBean.getPorCd()
					+ "  in PARAMETER_MST -Table. So Batch process Stopped");
			CommonUtil.stopBatch();
		}
		if (frznFlg.get(0).getVal1().equalsIgnoreCase("1")) {
			List<Object[]> rowNo = vldnRepositoryObj.fetchInVldFrznCdR00020(
					input.getPorCd(), input.getTableName(), queryParamBean);

			if (rowNo != null && !(rowNo.isEmpty())) {
				String errorMessage = PDMessageConsants.M00260;
				Map<String, String> errPrm = new HashMap<String, String>();
				for (Object[] seqId : rowNo) {
					String seqIdstr = (String) seqId[0];
					List<MstOseiFrzn> frznCdLst = vldnRepositoryObj
							.fetchValidFrnCd(seqIdstr);
					MstOseiFrzn mstFrznCd = frznCdLst.get(0);
					String frznCd = mstFrznCd.getFrznTypeCd();
					errPrm.put("1", String.valueOf(seqId[1]));
					errPrm.put("2", frznCd);
					errPrm.put("3",
							PDConstants.MONTHLY_PRODUCTION_SCHEDULE_TRN_IF);
					errorMessage = cmnUtl.getlogErrorMessage(errorMessage,
							errPrm);
					vldnRepositoryObj.updateFrnCd(input.getPorCd(),
							input.getOrdrTkBsMnth(), frznCd,
							String.valueOf(seqIdstr));
					mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
							CommonUtil.stringtoInt(seqIdstr), warnCd,
							errorMessage, input);
				}
			}
		}
		// Production method code validation
		if (frznFlg.get(0).getVal2().equalsIgnoreCase("1")) {
			List<Object[]> rowNo = vldnRepositoryObj.fetchInVldPrdMthdCdR00020(
					input.getPorCd(), input.getTableName(), queryParamBean);
			if (rowNo != null && !(rowNo.isEmpty())) {
				String errorMessage = PDMessageConsants.M00261;
				Map<String, String> errPrm = new HashMap<String, String>();

				for (Object[] seqId : rowNo) {
					String seqIdstr = (String) seqId[0];
					MstOseiProdType mstOseiProdType = new MstOseiProdType();
					List<MstOseiProdType> prdMtdCdLst = vldnRepositoryObj
							.fetchValidPrdMtdCd(seqIdstr, input.getPorCd());
					mstOseiProdType = prdMtdCdLst.get(0);
					String prdMtdCd = mstOseiProdType.getProdMthdCd();
					errPrm.put("1", String.valueOf(seqId[1]));
					errPrm.put("2", prdMtdCd);
					errPrm.put("3",
							PDConstants.MONTHLY_PRODUCTION_SCHEDULE_TRN_IF);
					errorMessage = cmnUtl.getlogErrorMessage(errorMessage,
							errPrm);
					vldnRepositoryObj.updatePrdMthdCd(input.getPorCd(),
							input.getOrdrTkBsMnth(), prdMtdCd,
							String.valueOf(seqIdstr));
					mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
							CommonUtil.stringtoInt(seqIdstr), warnCd,
							errorMessage, input);
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
