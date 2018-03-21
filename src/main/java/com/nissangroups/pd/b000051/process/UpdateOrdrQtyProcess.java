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
import java.util.Date;
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
 * The Class UpdateOrdrQtyProcess.
 *
 * @author z015060
 */
public class UpdateOrdrQtyProcess {
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;
	
	private static final Log LOG = LogFactory.getLog(UpdateOrdrQtyProcess.class
			.getName());
	
	QueryParamBean qryParamBean = new QueryParamBean();
	
	CommonUtil cmnUtil= new CommonUtil();
	
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	
	/** Constructor of UpdateOrdrQtyProcess */
	public UpdateOrdrQtyProcess() {
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public B000051Output executeProcess(B000051Output object) {
		LOG.info("Process P0005");
		String porCd=object.getObjB000051Param().getPorCd();
		String ordrTkBsMnth=object.getObjB000051Param().getOrdrTkBsMnth();
		String errMsg = PDMessageConsants.M00163;
		
		qryParamBean.setPorCd(porCd);
		qryParamBean.setOrdrTkBsMnth(ordrTkBsMnth);
		
		if(object.getObjB000051Param().getReRunFlg().equals(PDConstants.N)){
			for (String key : object.getProdMnthWkNum().keySet()) {
				qryParamBean.setPrdMnth(key);
				 List<String> prdMnthWkNo=object.getProdMnthWkNum().get(key);
				wklyRep.updtOrdrInfo(qryParamBean,prdMnthWkNo);
			}
			
			Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.UPDATED);
				errPrm.put("3",PDConstants.WEEKLY_ORDER_TRN);
			LOG.info(cmnUtil.getlogErrorMessage(errMsg, errPrm));
		}
		
		LOG.info("Process P0005");
		//POR_CD,PROD_ORDR_NO,PROD_MNTH,PROD_WK_NO,OSEI_ID,POT_CD,FRZN_TYPE_CD,PROD_MTHD_CD,count(*) as msQty
		try{
		for(Object[] ordrInfo: object.getOrdrInfoMstSchd()){
			TrnWklyOrdr trnWkly = new TrnWklyOrdr();
			TrnWklyOrdrPK trnWklyPk = new TrnWklyOrdrPK();
			trnWklyPk.setPorCd(ordrInfo[0].toString());
			trnWklyPk.setProdMnth(ordrInfo[2].toString());
			trnWklyPk.setProdWkNo(ordrInfo[3].toString());
			trnWklyPk.setOseiId(ordrInfo[4].toString());
			trnWklyPk.setPotCd(ordrInfo[5].toString());
			trnWkly.setId(trnWklyPk);
			trnWkly.setFrznTypeCd(ordrInfo[6].toString());
			trnWkly.setProdMthdCd(ordrInfo[7].toString());
			trnWkly.setSuspendedOrdrFlag(PDConstants.PRMTR_ZERO);
			BigDecimal msQty = (BigDecimal) ordrInfo[8];
			trnWkly.setReqtdOrdrQty(msQty);
			trnWkly.setSimuOrdrQty(msQty);
			trnWkly.setCrtdBy(B000051Constants.BATCH_ID_B000051);
			trnWkly.setUpdtdBy(B000051Constants.BATCH_ID_B000051);
			
			TrnWklyOrdr trnWklyOrdrOld = entityMngr.find(TrnWklyOrdr.class, trnWklyPk);
			if(trnWklyOrdrOld!=null){
				trnWkly = trnWklyOrdrOld; 
			}	
			
			trnWkly.setOrgnlOrdrQty(msQty);
			if(object.getObjB000051Param().getReRunFlg().equals(PDConstants.N)){
			trnWkly.setAccptdOrdrQty(msQty);
			}
			entityMngr.merge(trnWkly);
		}
		}catch(Exception e){
			LOG.error(e);
			String logErrMsg = PDMessageConsants.M00164;
			Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.INSERTION);
				errPrm.put("3",PDConstants.WEEKLY_ORDER_TRN);
			LOG.error(cmnUtil.getlogErrorMessage(logErrMsg, errPrm));
			cmnUtil.stopBatch();
		}
		
		Map<String, String> errPrm = new HashMap<String, String>();
		errPrm.put("1", B000051Constants.BATCH_ID_B000051);
		errPrm.put("2", PDConstants.INSERTED);
		errPrm.put("3",PDConstants.WEEKLY_ORDER_TRN);
		LOG.info(cmnUtil.getlogErrorMessage(errMsg, errPrm));
	
		
		return object;
		
	
	}

	

}
