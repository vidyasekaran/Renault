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



import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000051.output.B000051Output;
import com.nissangroups.pd.b000051.util.B000051Constants;
import com.nissangroups.pd.model.TrnWklyOrdr;
import com.nissangroups.pd.model.TrnWklyOrdrPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class InsertWklyOrdrTrnProcess.
 *
 * @author z015060
 */
public class InsertWklyOrdrTrnProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(InsertWklyOrdrTrnProcess.class
			.getName());
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	
	CommonUtil cmnUtil= new CommonUtil();
	QueryParamBean qryParamBean = new QueryParamBean();
	
	/** Constructor insertWklyOrdrTrnProcess */
	public InsertWklyOrdrTrnProcess() {
	}

	public B000051Output executeProcess(B000051Output object) throws ParseException {
		
		List<Object[]> ordrInfo = new ArrayList<Object[]>();
		String porCd=object.getObjB000051Param().getPorCd();
		String ordrTkBsMnth=object.getObjB000051Param().getOrdrTkBsMnth();
		
		qryParamBean.setPorCd(porCd);
		qryParamBean.setOrdrTkBsMnth(ordrTkBsMnth);
		
		LOG.info(" Process 6");
		for (String key : object.getProdMnthWkNum().keySet()) {
			qryParamBean.setPrdMnth(key);
			   List<String> prdMnthWkNo=object.getProdMnthWkNum().get(key);
			   List<Object[]> results=wklyRep.getOrdrInfoWklyOrdrTrn(qryParamBean,prdMnthWkNo);
			   ordrInfo.addAll(results);
			}
	
		if(ordrInfo.isEmpty()){
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.UPDATED_END_ITEM);
				errPrm.put("3", qryParamBean.getPorCd());
				errPrm.put("4",PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST);
			LOG.error(cmnUtil.getlogErrorMessage(errMsg, errPrm));
			cmnUtil.stopBatch();
		}
		
		// por_cd, production_month, production_week_no , buyer_cd, pot_cd, adopt_Date, abolish_date,Osei_id
		LOG.info(" Process 6.2");
		for(Object[] wkOrdr: ordrInfo){
			try{
			TrnWklyOrdr trnWkly = new TrnWklyOrdr();
			TrnWklyOrdrPK trnWklyPk = new TrnWklyOrdrPK();
			trnWklyPk.setPorCd(wkOrdr[0].toString());
			trnWklyPk.setProdMnth(wkOrdr[1].toString());
			trnWklyPk.setProdWkNo(wkOrdr[2].toString());
			trnWklyPk.setPotCd(wkOrdr[4].toString());
			trnWklyPk.setOseiId(wkOrdr[7].toString());
			trnWkly.setId(trnWklyPk);
			trnWkly.setOrgnlOrdrQty(new BigDecimal(0));
			trnWkly.setReqtdOrdrQty(new BigDecimal(0));
			trnWkly.setAccptdOrdrQty(new BigDecimal(0));
			trnWkly.setSimuOrdrQty(new BigDecimal(0));
			trnWkly.setSuspendedOrdrFlag(PDConstants.CONSTANT_ZERO);
			trnWkly.setProdMthdCd("B");
			trnWkly.setUpdtdBy(B000051Constants.BATCH_ID_B000051);
			trnWkly.setCrtdBy(B000051Constants.BATCH_ID_B000051);
			TrnWklyOrdr trnWklyOrdrOld = entityMngr.find(TrnWklyOrdr.class, trnWklyPk);
			if(trnWklyOrdrOld!=null){
				trnWkly = trnWklyOrdrOld; 
			}else {	
				entityMngr.persist(trnWkly);
			}
			}catch(Exception e){
				LOG.error(e);
				LOG.error(PDMessageConsants.M00043+B000051Constants.ERROR_MESSAGE_ID2);
			}
		}
		String errMsg = PDMessageConsants.M00163;
		Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.INSERTED);
			errPrm.put("3",PDConstants.WEEKLY_ORDER_TRN);
		LOG.info(cmnUtil.getlogErrorMessage(errMsg, errPrm));
		
		object.setOrdrInfo(ordrInfo);
	return object;
	}

}
