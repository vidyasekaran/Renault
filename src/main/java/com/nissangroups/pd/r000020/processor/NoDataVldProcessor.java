package com.nissangroups.pd.r000020.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class NoDataVldProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {
	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(NoDataVldProcessor.class);

	
	private String porCd;
	private String ocfRgnCd;
	private String tableName;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws PdApplicationException {
		QueryParamBean qryParamBn = new QueryParamBean();
		qryParamBn.setPorCd(input.getPorCd());
		qryParamBn.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		qryParamBn.setOcfRgnCd(input.getOcfRgnCd());
		CommonUtil cmnUtl = new CommonUtil();
		List<Object[]> vldData = vldnRepositoryObj.getVldData(qryParamBn,
				input.getTableName());
		if (vldData.isEmpty()) {
			String errorMessage = PDMessageConsants.M00302;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", input.getPorCd());
			errPrm.put("2", input.getOrdrTkBsMnth());
			if(input.getOcfRgnCd() != null){
			errPrm.put("3", input.getOcfRgnCd());}
			else{
				errPrm.put("3", "All");	
			}
			errPrm.put("4", PDConstants.MONTHLY_PRODUCTION_SCHEDULE_TRN_IF);
			LOG.error(cmnUtl.getlogErrorMessage(errorMessage, errPrm));
			CommonUtil.stopBatch();
		}else{
			vldnRepositoryObj.deleteOldData(qryParamBn,
					"TRN_MNTH_PROD_SHDL");
		}

		return input;
	}

	/**
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * @return the ocfRgnCd
	 */
	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	/**
	 * @param ocfRgnCd the ocfRgnCd to set
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	

}
