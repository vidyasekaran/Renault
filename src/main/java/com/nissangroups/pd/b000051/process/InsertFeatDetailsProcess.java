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
import com.nissangroups.pd.model.TrnBuyerWklyOcfUsg;
import com.nissangroups.pd.model.TrnBuyerWklyOcfUsgPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class NscForecstVolProcess.
 *
 * @author z015060
 */

public class InsertFeatDetailsProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(InsertFeatDetailsProcess.class
			.getName());

	QueryParamBean qryParamBean = new QueryParamBean();
	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;	
	
	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	
	CommonUtil cmnUtl= new CommonUtil();
	
	/** Constructor InsertFeatDetailsProcess */
	public InsertFeatDetailsProcess() {
	}
	
	
	public B000051Output executeProcess(B000051Output object) throws ParseException {
		LOG.info(" Process 9");
		List<Object[]> ocfDetails = new ArrayList<Object[]>();
		String porCd=object.getObjB000051Param().getPorCd();
		qryParamBean.setPorCd(porCd);
		
		String plantSum=wklyRep.getMsrtPrmtrVal(porCd,PDConstants.PLANT_SUMMARY_CONSTANT,
				PDConstants.WEEKLY_OCF_SUMMARY_CONSTANT);
		String cntDayNo= wklyRep.getMsrtPrmtrVal(porCd,PDConstants.CONSTANT_DAY_NO_CONSTANT,
				PDConstants.WEEKLY_OCF_SUMMARY_CONSTANT);
		String featTypCd= wklyRep.getMsrtPrmtrVal(porCd,PDConstants.WEEKLY,PDConstants.FEATURE_TYPE_CODE_CONSTANT);
		List<Object[]> lineClass= wklyRep.getMsrtPrmtrVal2(porCd,PDConstants.PLANT_LINE_CLASS_CONSTANT,PDConstants.WEEKLY_OCF_SUMMARY_CONSTANT);
		
		qryParamBean.setFeatTypCd(featTypCd);
		qryParamBean.setCnstDayNo(cntDayNo);
		qryParamBean.setPlntLneSummary(plantSum);
		qryParamBean.setPlntCd(lineClass.get(0)[0].toString());
		qryParamBean.setLineClass(lineClass.get(0)[1].toString());
		object.setPlntLneSummary(plantSum);
		object.setLineClass(lineClass.get(0)[1].toString());
		object.setFeaturetypeCd(featTypCd);
		object.setCnstDayNo(cntDayNo);
		object.setPlntCd(lineClass.get(0)[0].toString());
		
		LOG.info(" Process 10");
		for (String key : object.getProdMnthWkNum().keySet()) {
			qryParamBean.setPrdMnth(key);
			   List<String> prdMnthWkNo=object.getProdMnthWkNum().get(key);
			   List<Object[]> results=wklyRep.getOCFDetails(qryParamBean,prdMnthWkNo);
			   ocfDetails.addAll(results);
			   List<Object[]> resultsCCF=wklyRep.getCCFDetails(qryParamBean,prdMnthWkNo);
			   ocfDetails.addAll(resultsCCF);
			}
		
		if(ocfDetails.isEmpty()){
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.ORDERS);
				errPrm.put("3",porCd);
				errPrm.put("4",PDConstants.ORDERABLE_SALES_END_ITEM_DETAIL_MST);
			LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			cmnUtl.stopBatch();
			
		}
		
		LOG.info(" Process 11");
			for(Object[] temp:ocfDetails){
				
				//ms.CAR_SRS,mf.POR_CD,mbb.BUYER_GRP_CD,mf.FEAT_CD,mo.OSEI_ID,Producyion_month, production_week,
				//mf.FEAT_TYPE_CD,mf.OCF_FRME_CD
				Map<String,Integer> newHash= new HashMap<String,Integer>();
				String prodMnth=temp[5].toString();
				String prdWkno=temp[6].toString();
				if(newHash.get(prodMnth+prdWkno) == null){
					qryParamBean.setPrdMnth(prodMnth);
					qryParamBean.setPrdMnthWkNo(prdWkno);
					wklyRep.delByrWklyOcf(qryParamBean);
					newHash.put(prodMnth+prdWkno,1);
				}
				TrnBuyerWklyOcfUsg trnByr = new TrnBuyerWklyOcfUsg();
				TrnBuyerWklyOcfUsgPK trnByrPk = new TrnBuyerWklyOcfUsgPK();
				trnByrPk.setPorCd(temp[1].toString());
				trnByrPk.setFeatCd(temp[3].toString());
				trnByrPk.setOseiId(temp[4].toString());
				trnByrPk.setProdMnth(prodMnth);
				trnByrPk.setProdWkNo(prdWkno);
				trnByr.setId(trnByrPk);
				trnByr.setFeatTypeCd(temp[8].toString());
				trnByr.setOcfFrmeCd(temp[7].toString());
				trnByr.setCarSrs(temp[0].toString());
				trnByr.setBuyerGrpCd(temp[2].toString());
				trnByr.setUpdtdBy(B000051Constants.BATCH_ID_B000051);
				trnByr.setCrtdBy(B000051Constants.BATCH_ID_B000051);
				try{
				entityMngr.merge(trnByr);
				} catch(Exception e){
					LOG.error(e);
					String errMsg = PDMessageConsants.M00164;
					Map<String, String> errPrm = new HashMap<String, String>();
					errPrm.put("1", B000051Constants.BATCH_ID_B000051);
					errPrm.put("2", PDConstants.INSERTION);
					errPrm.put("3", PDConstants.TRN_BUYER_WKLY_OCF_USG);
					LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
					cmnUtl.stopBatch();
				}
				String errMsg = PDMessageConsants.M00163;
				Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.INSERTED);
				errPrm.put("3", PDConstants.TRN_BUYER_WKLY_OCF_USG);
				LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			}		
			
		return object;
	}

}
