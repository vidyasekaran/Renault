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
import com.nissangroups.pd.b000051.util.B000051QueryConstants;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalWklyOcfLmtPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


/**
 * The Class UpdateRegionalOCFProcess.
 *
 * @author z015060
 */
public class UpdateRegionalOCFProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(UpdateRegionalOCFProcess.class
			.getName());
	
	QueryParamBean qryParamBean = new QueryParamBean();
	
	Map<String, Integer> newRgnlHash = new HashMap<String, Integer>();

	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;

	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;
	
	CommonUtil cmnUtl= new CommonUtil();
	
	/** Constructor for UpdateRegionalOCFProcess */
	public UpdateRegionalOCFProcess() {
	}
	
		
	public B000051Output executeProcess(B000051Output object) {
		
		String porCd = object.getObjB000051Param().getPorCd();
		qryParamBean.setPorCd(porCd);
		List<Object[]> ocfRegDetails = new ArrayList<Object[]>();
		
		for (String key : object.getProdMnthWkNum().keySet()) {
			qryParamBean.setPrdMnth(key);
			
			String getUsgQtyRgnp1 = B000051QueryConstants.getUsgQtyRgnLvlp1.toString();
			String getUsgQtyRgnp2 = B000051QueryConstants.getUsgQtyRgnLvlp2.toString();
			String getUsgQtyRgnp3 = B000051QueryConstants.getUsgQtyRgnLvlp3.toString();
			if (object.getPlntLneSummary().equals(PDConstants.N)) {
				getUsgQtyRgnp1 += getUsgQtyRgnp2;
			}
			getUsgQtyRgnp1 += getUsgQtyRgnp3;
			if (object.getPlntLneSummary().equals(PDConstants.N)) {
				getUsgQtyRgnp1 += getUsgQtyRgnp2;
			}
			 List<String> prdMnthWkNo=object.getProdMnthWkNum().get(key);
			
			for(int i=0;i<prdMnthWkNo.size();i++){
				qryParamBean.setPrdMnthWkNo(prdMnthWkNo.get(i));
			   List<Object[]> results=wklyRep.getRgnlUsgQty(qryParamBean,prdMnthWkNo,getUsgQtyRgnp1);
			   for (Object[] temp : results) {

					if (object.getPlntLneSummary().equals(PDConstants.N)) {
						ocfRegDetails.add(new Object[] { temp[0], temp[1],
								temp[2], temp[3], temp[4], temp[5], temp[6],
								temp[7], temp[8], temp[9], temp[10], temp[11],
								temp[12] ,temp[13]});
					} else {
						ocfRegDetails.add(new Object[] { temp[0], temp[1],
								temp[2], temp[3], temp[4], temp[5], temp[6],
								temp[7], temp[8], temp[9],temp[10], object.getPlntCd(),
								object.getLineClass(),
								 object.getCnstDayNo() });
					}
				}
			}
		}
		
		if(ocfRegDetails.isEmpty()){
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", B000051Constants.ERROR_MESSAGE_ID5);
			errPrm.put("3", qryParamBean.getPorCd());
			errPrm.put("4", PDConstants.BUYER_GROUP_WEEKLY_OCF_LIMIT_TRN);
			LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			cmnUtl.stopBatch();
		}
		
		for(Object[] data :  ocfRegDetails){
			try{
				String day = data[13].toString();
				if(day.length()<2){
					day = day+" ";
				}
				
			
				if (newRgnlHash.get(data[0].toString() + data[1].toString()+ data[2].toString()+
						data[5].toString()+data[4].toString()+data[3].toString()+data[11].toString()+
						data[12].toString()+day) == null) {
					qryParamBean.setPorCd(data[0].toString());
					qryParamBean.setPrdMnth(data[1].toString());
					qryParamBean.setPrdMnthWkNo(data[2].toString());
					qryParamBean.setByrGrpCd(data[5].toString());
					qryParamBean.setOcfRgnCd(data[4].toString());
					qryParamBean.setCarSrs(data[3].toString());
					qryParamBean.setPlntCd(data[11].toString());
					qryParamBean.setLineClass(data[12].toString());
					qryParamBean.setPrdDayNo(day);
					
					wklyRep.UpdtRgnlWklyOcfLmt(qryParamBean);
					newRgnlHash.put(data[0].toString() + data[1].toString()+ data[2].toString()+
							data[5].toString()+data[4].toString()+data[3].toString()+data[11].toString()+
							data[12].toString()+day, 1);
				}
				
			TrnRegionalWklyOcfLmt rgnlWkly= new TrnRegionalWklyOcfLmt();
			TrnRegionalWklyOcfLmtPK rgnlPk= new TrnRegionalWklyOcfLmtPK();
			rgnlPk.setPorCd(data[0].toString());
			rgnlPk.setProdMnth(data[1].toString());
			rgnlPk.setProdWkNo(data[2].toString());
			rgnlPk.setCarSrs(data[3].toString());
			rgnlPk.setOcfRegionCd(data[4].toString());
			rgnlPk.setOcfBuyerGrpCd(data[5].toString());
			rgnlPk.setFeatCd(data[6].toString());
			rgnlPk.setLineClass(data[12].toString());
			rgnlPk.setPlantCd(data[11].toString());
			
			
			rgnlPk.setProdDayNo(day);
			
			rgnlWkly.setId(rgnlPk);
			rgnlWkly.setFeatTypeCd(data[9].toString());
			rgnlWkly.setCrtdBy(B000051Constants.BATCH_ID_B000051);
			rgnlWkly.setUpdtdBy(B000051Constants.BATCH_ID_B000051);
			rgnlWkly.setOcfFrmeCd(data[7].toString());
			rgnlWkly.setRegionalOcfUsgQty(new BigDecimal(data[8].toString()));
			if(rgnlWkly.getOcfFrmeCd().equals(PDConstants.FEATURE_CODE_00)){
				rgnlWkly.setRegionalOcfLmtQty(new BigDecimal(PDConstants.PRMTR_ZERO));
			}

			entityMngr.merge(rgnlWkly);

			}catch(Exception e){
				LOG.error(e);
				String errMsg = PDMessageConsants.M00164;
				Map<String, String> errPrm = new HashMap<String, String>();
				errPrm.put("1", B000051Constants.BATCH_ID_B000051);
				errPrm.put("2", PDConstants.INSERTION);
				errPrm.put("3", PDConstants.REGIONAL_WEEKLY_OCF_LIMIT_TRN);
				LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
				cmnUtl.stopBatch();
			}
			String errMsg = PDMessageConsants.M00163;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.INSERTED);
			errPrm.put("3", PDConstants.REGIONAL_WEEKLY_OCF_LIMIT_TRN);
			LOG.info(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			
		}

		
		
		return object;
	}

}
