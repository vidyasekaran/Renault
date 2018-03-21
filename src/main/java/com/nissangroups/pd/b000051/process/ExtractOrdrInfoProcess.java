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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class ExtractOrdrInfoProcess.
 *
 * @author z015060
 */
public class ExtractOrdrInfoProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ExtractOrdrInfoProcess.class
			.getName());
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	QueryParamBean qryParamBean = new QueryParamBean();
	CommonUtil cmnUtil=new CommonUtil();
	
	/** Constructor with B000008Output and EntityManager  parameters */
	public ExtractOrdrInfoProcess() {
	}
	

	public B000051Output executeProcess(B000051Output object) {
		LOG.info(" Process 4");
		List<Object[]> ordrInfo = new ArrayList<Object[]>();
		String porCd=object.getObjB000051Param().getPorCd();
		String ordrTkBsMnth=object.getObjB000051Param().getOrdrTkBsMnth();
		qryParamBean.setPorCd(porCd);
		qryParamBean.setOrdrTkBsMnth(ordrTkBsMnth);
		
		for (String key : object.getProdMnthWkNum().keySet()) {
			qryParamBean.setPrdMnth(key);
			   List<String> prdMnthWkNo=object.getProdMnthWkNum().get(key);
			   List<Object[]> results=wklyRep.getOrdrInfoMstSchdl(qryParamBean,prdMnthWkNo);
			   ordrInfo.addAll(results);
			}
		if(ordrInfo.isEmpty()){
			LOG.error(PDMessageConsants.M00003+B000051Constants.ERROR_MESSAGE_ID3 +qryParamBean.getPorCd());
			cmnUtil.stopBatch();
		}
		object.setOrdrInfoMstSchd(ordrInfo);
		return object;
	}


}
