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

import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.PDMessageConsants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The Class OverLapPeriodProcess.
 *
 * @author z015060
 */
public class UpdateWklyOrdrTrnProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UpdateWklyOrdrTrnProcess.class
			.getName());

	QueryParamBean qryParamBean = new QueryParamBean();
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	/** Constructor UpdateWklyOrdrTrnProcess */
	public UpdateWklyOrdrTrnProcess (){
	}
	
    public  B000051Output executeProcess(B000051Output object) {
    	LOG.info(" UpdateWklyOrdrTrnProcess ");
    	List<String> prdMnthWkNoConcat = new ArrayList<String>();
	
    	String porCd=object.getObjB000051Param().getPorCd();
		String ordrTkBsMnth=object.getObjB000051Param().getOrdrTkBsMnth();
		String ordrTkBsWkNo=object.getObjB000051Param().getOrdrTkWkNo();
		
		qryParamBean.setPorCd(porCd);
		qryParamBean.setOrdrTkBsMnth(ordrTkBsMnth);
		qryParamBean.setOrdrTkBsWkNo(ordrTkBsWkNo);
		
		LOG.info(" Process 7.1");
		String prdMnthWk= wklyRep.getPrePrdMnthWK(qryParamBean);
		
		if(prdMnthWk != null){
		qryParamBean.setPrdMnth(prdMnthWk.substring(0,6));
		qryParamBean.setPrdMnthWkNo(prdMnthWk.substring(6,7));
		}else {
			LOG.error(PDMessageConsants.M00003+B000051Constants.ERROR_MESSAGE_ID7);
			CommonUtil.stopBatch();
		}
		
		LOG.info(" Process 7.2");
		List<Object[]> ordrInfoPreMstSchdl= wklyRep.getOrdrInfoPreMstSchdl(qryParamBean);
		
		LOG.info(" Process 7.3");
		for(Object[] ordrInfo: ordrInfoPreMstSchdl){
			qryParamBean.setOseiId(ordrInfo[4].toString());
			qryParamBean.setPotCd(ordrInfo[5].toString());
			qryParamBean.setAccptOrdrQty(ordrInfo[8].toString());
			wklyRep.updateAccQty(qryParamBean);
		}

		
		// por_cd, production_month, production_week_no , buyer_cd, pot_cd, adopt_Date, abolish_date,Osei_id
		LOG.info(" Process 8.2");
		for (Object [] temp: object.getOrdrInfo()){
			qryParamBean.setPrdMnth(temp[1].toString());
			qryParamBean.setPrdMnthWkNo(temp[2].toString());
			qryParamBean.setOseiId(temp[7].toString());
			qryParamBean.setAdptDt(temp[5].toString());
			qryParamBean.setAblshDt(temp[6].toString());
			prdMnthWkNoConcat.add(temp[1].toString()+temp[2].toString());
			wklyRep.updateSuspendedFlagZero(qryParamBean);	
		}
		
		LOG.info(" Process 8.1");
		List<Object[]> prdMnthWkNotIn= wklyRep.gePrdMnthWkNot(qryParamBean,prdMnthWkNoConcat);
		
		for(Object[] temp :prdMnthWkNotIn){
		qryParamBean.setPrdMnth(temp[0].toString().substring(0,6));
		qryParamBean.setPrdMnthWkNo(temp[0].toString().substring(6,7));
		wklyRep.updateSuspendedFlagOne(qryParamBean);
		}
		
		return object;
		
	}
    

}

