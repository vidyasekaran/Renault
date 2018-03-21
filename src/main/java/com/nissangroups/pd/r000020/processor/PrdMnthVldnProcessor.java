/*

 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/25  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;
import static com.nissangroups.pd.util.PDMessageConsants.M00068;
import static com.nissangroups.pd.util.PDMessageConsants.M00139;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class PrdMnthVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(PrdMnthVldnProcessor.class);

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	private String errorCd;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws Exception {

		LOG.info(STEP_AFTER_START);

		List<Object[]> distinctCarSrsdtls = mnthlySchdlIfTrnRepositoryObj
				.fetchDistinctCarSrs(input);

		for (Object[] obj : distinctCarSrsdtls) {
			int horizon;

			String carSrs = CommonUtil.convertObjectToString(obj[0]);
			String ordrTkMnthString = CommonUtil.convertObjectToString(obj[1]);
			String carSrsHrzn = CommonUtil.convertObjectToString(obj[2]);
			String prdMnthString = CommonUtil.convertObjectToString(obj[3]);
			String carSrsAdptDateStr = CommonUtil.convertObjectToString(obj[4]);
			String carSrsAblshDateStr = CommonUtil
					.convertObjectToString(obj[5]);

			horizon = Integer.parseInt(carSrsHrzn);

			Date prdMnth = CommonUtil.convertStringToDate(prdMnthString);
			Date ordrTkMnth = CommonUtil.convertStringToDate(ordrTkMnthString);
			int prdMnthInt = CommonUtil.stringtoInt(prdMnthString);
			int ordrTkMnthInt = CommonUtil.stringtoInt(ordrTkMnthString);

			Date carSrsAdptDate = CommonUtil
					.convertStringToDate(carSrsAdptDateStr.substring(0, 6));
			Date carSrsAblshDate = CommonUtil
					.convertStringToDate(carSrsAblshDateStr.substring(0, 6));
			CommonUtil cmnUtl = new CommonUtil();
			String wrnCd = PDConstants.PRMTR_R20_WRN_CD;
			if (ordrTkMnthInt > prdMnthInt) {
				String wrnCdSkip = PDConstants.PRMTR_R20_WRN_CD_SKIP;
				String errMsg = PDMessageConsants.M00268;
				Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", ordrTkMnthString);
				errPrm.put("2", "11");
				errPrm.put("3", prdMnthString);
				errPrm.put("4", "11");
				errPrm.put("5", input.getPorCd());
				errMsg = cmnUtl.getlogErrorMessage(errMsg, errPrm);
				mnthlySchdlIfTrnRepositoryObj.updateInVldPrdMnth(wrnCdSkip,
						errMsg, input, carSrs, ordrTkMnthString, prdMnthString,
						"false", null);

			}
			if (prdMnth.compareTo(carSrsAdptDate) >= 0
					&& prdMnth.compareTo(carSrsAblshDate) <= 0
					&& ordrTkMnth.compareTo(carSrsAblshDate) <= 0) {
				List<String> oseiIdList = mnthlySchdlIfTrnRepositoryObj
						.fetchOseiIdFrInVldPrdMnth(input, carSrs,
								ordrTkMnthString, prdMnthString);
				for (String oseiId : oseiIdList) {

					Object[] nearestAblshAdptDates = mnthlySchdlIfTrnRepositoryObj
							.fetchNearestAbolishAdoptDates(input, carSrs,
									ordrTkMnthString, prdMnthString, oseiId,
									carSrsAdptDateStr, carSrsAblshDateStr);
					if (nearestAblshAdptDates[0].toString().equalsIgnoreCase(
							"false")) {
						mnthlySchdlIfTrnRepositoryObj.updateInVldPrdMnth(
								errorCd, M00068, input, carSrs,
								ordrTkMnthString, prdMnthString, "true",
								nearestAblshAdptDates);
					}

				}

			} else {
				mnthlySchdlIfTrnRepositoryObj.updateInVldPrdMnth(errorCd,
						M00139, input, carSrs, ordrTkMnthString, prdMnthString,
						"false", null);
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
