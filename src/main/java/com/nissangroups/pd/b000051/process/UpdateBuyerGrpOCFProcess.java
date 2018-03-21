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
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmtPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.WeeklyOrderRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class UpdateBuyerGrpOCFProcess.
 *
 * @author z015060
 */
public class UpdateBuyerGrpOCFProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(UpdateBuyerGrpOCFProcess.class.getName());

	CommonUtil cmnUtl = new CommonUtil();

	QueryParamBean qryParamBean = new QueryParamBean();
	
	Map<String, Integer> newHash = new HashMap<String, Integer>();
	

	
	@Autowired(required = false)
	private WeeklyOrderRepository wklyRep;

	@PersistenceContext(name = PDConstants.PERSISTENCE_NAME)
	private EntityManager entityMngr;

	/** Constructor with UpdateWklyOrdrTrnProcess */
	public UpdateBuyerGrpOCFProcess() {

	}

	public B000051Output executeProcess(B000051Output object) {

		String porCd = object.getObjB000051Param().getPorCd();
		qryParamBean.setPorCd(porCd);
		List<Object[]> usgByrFrpLvl = new ArrayList<Object[]>();
		List<Object[]> usgByrOcfLmtInsrt = new ArrayList<Object[]>();
		
		for (String key : object.getProdMnthWkNum().keySet()) {
			qryParamBean.setPrdMnth(key);
			String getUsgQtyp1 = B000051QueryConstants.getUsgQtyByrGrpLvlp1
					.toString();
			String getUsgQtyp2 = B000051QueryConstants.getUsgQtyByrGrpLvlp2
					.toString();
			String getUsgQtyp3 = B000051QueryConstants.getUsgQtyByrGrpLvlp3
					.toString();
			if (object.getPlntLneSummary().equals(PDConstants.N)) {
				getUsgQtyp1 += getUsgQtyp2;
			}
			getUsgQtyp1 += getUsgQtyp3;
			if (object.getPlntLneSummary().equals(PDConstants.N)) {
				getUsgQtyp1 += getUsgQtyp2;
			}

			List<String> prdMnthWkNo = object.getProdMnthWkNum().get(key);
			for (int i = 0; i < prdMnthWkNo.size(); i++) {
				qryParamBean.setPrdMnthWkNo(prdMnthWkNo.get(i));
				List<Object[]> logiclLst = wklyRep.getUsgQtyByrLvl(
						qryParamBean, getUsgQtyp1);
				for (Object[] temp : logiclLst) {
					/*
					 * select
					 * tl.POR_CD,tl.PROD_MNTH,tl.PROD_WK_NO,tl.OSEI_ID,tl.
					 * PROD_ORDR_NO,tl.SLS_NOTE_NO,
					 * tl.EX_NO,count(tl.VHCL_SEQ_ID) as
					 * vqty,mf.FEAT_TYPE_CD,mf.FEAT_CD,mf.OCF_FRME_CD,
					 * ms.CAR_SRS,mbb.BUYER_GRP_CD ");
					 * ,tl.LINE_CLASS,tl.PROD_PLNT_CD,tl.OFFLN_PLAN_DATE 
					 */

					if (object.getPlntLneSummary().equals(PDConstants.N)) {
						usgByrFrpLvl.add(new Object[] { temp[0], temp[1],
								temp[2], temp[3], temp[4], temp[5], temp[6],
								temp[7], temp[8], temp[9], temp[10], temp[11],
								temp[12] });
					} else {
						usgByrFrpLvl.add(new Object[] { temp[0], temp[1],
								temp[2], temp[3], temp[4], temp[5], temp[6],
								temp[7], temp[8], temp[9], object.getLineClass(),
								object.getPlntCd(), object.getCnstDayNo() });
					}
				}

			}

		}

		if (usgByrFrpLvl.isEmpty()) {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", B000051Constants.ERROR_MESSAGE_ID4);
			errPrm.put("3", qryParamBean.getPorCd());
			errPrm.put("4", PDConstants.LOGICAL_PIPELINE_TRN_TABLE);
			LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			cmnUtl.stopBatch();
		}

		for (Object[] temp : usgByrFrpLvl) {

			
			String prodMnth = temp[1].toString();
			String prdWkno = temp[2].toString();
			qryParamBean.setPrdMnth(prodMnth);
			qryParamBean.setPrdMnthWkNo(prdWkno);
				if (newHash.get(prodMnth + prdWkno) == null) {
					wklyRep.UpdtByrWklyOcfLmt(qryParamBean);
					newHash.put(prodMnth + prdWkno, 1);
				}

				insetByrGrpOcfLmt(temp);
		}
		
		

		for (Object[] data : usgByrFrpLvl) {
			
			List<String> lstPrdDay= new ArrayList<String>();
			qryParamBean.setPorCd(porCd);
			qryParamBean.setPrdMnth(data[1].toString());
			qryParamBean.setPrdMnthWkNo(data[2].toString());
			qryParamBean.setCarSrs(data[8].toString());
			qryParamBean.setByrGrpCd(data[9].toString());
			qryParamBean.setFeatTypCd(data[5].toString());
			qryParamBean.setPlntCd(data[11].toString());
			qryParamBean.setLineClass(data[10].toString());
			
			List<Object[]> results = wklyRep.getUsgByrOcfLmtInsrt(qryParamBean);
			for(Object[] temp:results){
				lstPrdDay.add(temp[0].toString());
	
			}
			
			for (int i = 1; i <= 7; i++) {
				if (!lstPrdDay.contains(i + " ")) {
					data[12]=i;
					insetByrGrpOcfLmt(data);
				}
			}
			usgByrOcfLmtInsrt.addAll(results);
		}

		object.setLglPlnLst(usgByrFrpLvl);
		return object;
	}

	private void insetByrGrpOcfLmt(Object[] temp) {

			TrnBuyerGrpWklyOcfLmt data = new TrnBuyerGrpWklyOcfLmt();
			TrnBuyerGrpWklyOcfLmtPK dataPk = new TrnBuyerGrpWklyOcfLmtPK();

			String day = temp[12].toString();
			if(day.length()<2){
				day = day+" ";
			}
			
			dataPk.setPorCd(temp[0].toString());
			dataPk.setProdMnth(temp[1].toString());
			dataPk.setProdWkNo(temp[2].toString());
			dataPk.setCarSrs(temp[8].toString());
			dataPk.setFeatCd(temp[6].toString());
			dataPk.setBuyerGrpCd(temp[9].toString());
			dataPk.setProdDayNo(day);
			dataPk.setLineClass(temp[10].toString());
			dataPk.setPlantCd(temp[11].toString());


			data.setId(dataPk);
			data.setFeatTypeCd(temp[5].toString());
			data.setOcfFrmeCd(temp[7].toString());
			if(data.getOcfFrmeCd().equalsIgnoreCase(PDConstants.FEATURE_CODE_00)){
				data.setBuyerGrpOcfLmtQty(new BigDecimal(PDConstants.PRMTR_ZERO));
			}
			data.setBuyerGrpOcfUsgQty(new BigDecimal(temp[4].toString()));
			data.setUpdtdBy(B000051Constants.BATCH_ID_B000051);
			data.setCrtdBy(B000051Constants.BATCH_ID_B000051);
			
			try {
			entityMngr.merge(data);

			
		} catch (Exception e) {
			LOG.error(e);
			String errMsg = PDMessageConsants.M00164;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", B000051Constants.BATCH_ID_B000051);
			errPrm.put("2", PDConstants.INSERTION);
			errPrm.put("3", PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT);
			LOG.error(cmnUtl.getlogErrorMessage(errMsg, errPrm));
			cmnUtl.stopBatch();
		}
		String errMsg = PDMessageConsants.M00163;
		Map<String, String> errPrm = new HashMap<String, String>();
		errPrm.put("1", B000051Constants.BATCH_ID_B000051);
		errPrm.put("2", PDConstants.INSERTED);
		errPrm.put("3", PDConstants.TRN_BUYER_GRP_WKLY_OCF_LMT);
		LOG.info(cmnUtl.getlogErrorMessage(errMsg, errPrm));
	}

}
