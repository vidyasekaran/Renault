package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;

public class AttachOseiIdProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(R000020Processor.class);

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepository;


	private String porCd;
	private String ocfRgnCd;
	List<Object[]> errorRprtLst = new ArrayList<>();
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

	@Override
	public R000020InputParamBean process(R000020InputParamBean item)
			throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> oseiDtls = extractAllValidOseiId();
		for (Object[] oseiFtlsAry : oseiDtls) {
			QueryParamBean qryPrmBean = new QueryParamBean();
			qryPrmBean.setPorCd((String) oseiFtlsAry[0]);
			qryPrmBean.setOseiId((String) oseiFtlsAry[10]);
			qryPrmBean.setProdFmlyCd((String) oseiFtlsAry[1]);
			qryPrmBean.setCarSrs((String) oseiFtlsAry[2]);
			qryPrmBean.setByrCd((String) oseiFtlsAry[3]);
			qryPrmBean.setAppldMdlCd((String) oseiFtlsAry[4]);
			qryPrmBean.setPckCd((String) oseiFtlsAry[5]);
			qryPrmBean.setSpecDstnCd((String) oseiFtlsAry[6]);
			qryPrmBean.setAdntlSpecCd((String) oseiFtlsAry[7]);
			qryPrmBean.setExtClrCd((String) oseiFtlsAry[8]);
			qryPrmBean.setIntClrCd((String) oseiFtlsAry[9]);
			mnthlySchdlIfTrnRepository.attachOseiId(qryPrmBean);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return item;
	}

	public List<Object[]> extractAllValidOseiId() {
		List<Object[]> oseiDtls = mnthlySchdlIfTrnRepository
				.fetchValidOseiID(porCd);
		return oseiDtls;
	}


	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

}
