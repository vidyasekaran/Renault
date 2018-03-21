/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.process;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class CalPrdMnthProcess.
 *
 * @author z015060
 */
public class CalPrdMnthProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(CalPrdMnthProcess.class
			.getName());

	QueryParamBean qryParamBean = new QueryParamBean();

	Map<String, List<String>> pdMnthWk = new HashMap<String, List<String>>();
	DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	/** Constructor of CalPrdMnthProcess */
	public CalPrdMnthProcess() {
	}

	public B000051Output executeProcess(B000051Output obj)
			throws ParseException {
		LOG.info("Process P0002");
		String porCd = obj.getObjB000051Param().getPorCd();
		String ordrTkBsMnth = obj.getObjB000051Param().getOrdrTkBsMnth();
		Date ordrtkBsMnthDt = formatter.parse(ordrTkBsMnth);
		int ordrTkBsWkNo = Integer.parseInt(obj.getObjB000051Param()
				.getOrdrTkWkNo());

		qryParamBean.setPorCd(porCd);
		qryParamBean.setOrdrTkBsMnth(ordrTkBsMnth);

		getWeekNumCalculation(ordrTkBsMnth, ordrTkBsWkNo);
		String multipleWkCs = wklyRep.getWkFxCs(qryParamBean);

		if (multipleWkCs.equalsIgnoreCase(PDConstants.Y)) {
			String NxtPrdMnth = wklyRep.getPrdMnth(qryParamBean);
			if (NxtPrdMnth != null) {
				getWeekNumCalculation(NxtPrdMnth, PDConstants.INT_ZERO);
			}

		} else {
			int hrzn = Integer.parseInt(wklyRep.getWkHrzn(qryParamBean));
			for (int i = 0; i < hrzn; i++) {
				String maxMonthCal = formatter.format(CommonUtil
						.addMonthToDate(ordrtkBsMnthDt, i));
				getWeekNumCalculation(maxMonthCal, PDConstants.INT_ZERO);
			}
		}
		obj.setProdMnthWkNum(pdMnthWk);
		LOG.info(pdMnthWk.size());
		LOG.info(pdMnthWk.keySet());
		return obj;
	}

	public void getWeekNumCalculation(String prodMnth, int ordrWkNo) {
		List<String> wkNoNxtPrdMnth = wklyRep.getWeekNumCal(qryParamBean,
				prodMnth, ordrWkNo);
		if (!wkNoNxtPrdMnth.isEmpty()) {
			pdMnthWk.put(prodMnth, wkNoNxtPrdMnth);
		} else {
			LOG.error(PDMessageConsants.M00003+ B000051Constants.ERROR_MESSAGE_ID6);
			CommonUtil.stopBatch();
		}
	}
}
