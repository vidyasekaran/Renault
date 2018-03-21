/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 * This Class performs all the necessary validations related to Frozen Order Check.  
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_OCF_LMT_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_OCF_SIM_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_OCF_USAGE_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_TYPE_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCF_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFRGNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_RGNL_MNTHLY_OCF_USAGE_QTY;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmtPK;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnRegionalMnthlyOcfLmtPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.QueryConstants;


public class RegionalMnthlyOcfLimitTrnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Autowired(required=false)
	private ByrGrpMnthlyOcfLimitTrnRepository byrGrpMnthlyOcfLimitTrnRepositoryObj;
	
	
	
	public void initialisation(InputBean input,List<Object[]> distinctByrGrpCarSrsList)
			throws PdApplicationException, ParseException {

		for(Object[] obj : distinctByrGrpCarSrsList){
			
			String byrGrpCd =  CommonUtil.convertObjectToString(obj[0]);
			String carSrs =  CommonUtil.convertObjectToString(obj[1]);
			String ordrTkBsMnth =  CommonUtil.convertObjectToString(obj[2]).trim();
			String prdMnth =  CommonUtil.convertObjectToString(obj[3]).trim();
			String ocfByrGrpCd =  CommonUtil.convertObjectToString(obj[4]).trim();
			String ocfRegionCd =  CommonUtil.convertObjectToString(obj[5]).trim();
			String byrGrpHrzn =  CommonUtil.convertObjectToString(obj[6]).trim();
			String carSrsHrzn =  CommonUtil.convertObjectToString(obj[7]).trim();
		
			int horizon = 3;
			if(byrGrpHrzn.equalsIgnoreCase("") || byrGrpHrzn.equalsIgnoreCase("F")){
				 horizon = Integer.parseInt(carSrsHrzn);	 
			} else {
				horizon = 3;
			}
				
			if(ordrTkBsMnth.length()>6){
				ordrTkBsMnth = ordrTkBsMnth.substring(0, 6);
			}
				
			Date ordrTkMnth = CommonUtil.convertStringToDate(ordrTkBsMnth);
			ordrTkBsMnth = CommonUtil.convertDateToString(ordrTkMnth,DATE_FORMAT_MONTH);
			Query query = buildQueryFrUpdate();
			
			
			TrnRegionalMnthlyOcfLmt rgnlMnthlyOcfLmt = new TrnRegionalMnthlyOcfLmt();
			TrnRegionalMnthlyOcfLmtPK rgnlMnthlyOcfLmtPk = new TrnRegionalMnthlyOcfLmtPK();
			
			
			rgnlMnthlyOcfLmtPk.setPorCd(input.getPorCd());
			rgnlMnthlyOcfLmtPk.setOcfRegionCd(ocfRegionCd);
			rgnlMnthlyOcfLmtPk.setCarSrs(carSrs);
			rgnlMnthlyOcfLmtPk.setOcfBuyerGrpCd(ocfByrGrpCd);
			rgnlMnthlyOcfLmtPk.setOrdrTakeBaseMnth(ordrTkBsMnth);
			rgnlMnthlyOcfLmtPk.setProdMnth(ordrTkBsMnth);
			
			rgnlMnthlyOcfLmt.setId(rgnlMnthlyOcfLmtPk);
			
			rgnlMnthlyOcfLmt.setRegionalOcfUsgQty(BigDecimal.valueOf(0));
			
			
			executeUpdateQuery(query,rgnlMnthlyOcfLmt);
			for(int i =1;i<horizon;i++){
				Date prdMnthDate = CommonUtil.addMonthToDate(ordrTkMnth,i);
				prdMnth = CommonUtil.convertDateToString(prdMnthDate,DATE_FORMAT_MONTH);
				rgnlMnthlyOcfLmt.getId().setProdMnth(prdMnth);
				executeUpdateQuery(query, rgnlMnthlyOcfLmt);
			}
			
		}
		
		
	}

	public void executeUpdateQuery(Query query,TrnRegionalMnthlyOcfLmt rgnlMnthlyOcfLmt){
	
		query.setParameter(PRMTR_RGNL_MNTHLY_OCF_USAGE_QTY, rgnlMnthlyOcfLmt.getRegionalOcfUsgQty());
		query.setParameter(PRMTR_CAR_SRS,rgnlMnthlyOcfLmt.getId().getCarSrs());
		query.setParameter(PRMTR_PORCD, rgnlMnthlyOcfLmt.getId().getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, rgnlMnthlyOcfLmt.getId().getOrdrTakeBaseMnth());
		query.setParameter(PRMTR_PRD_MNTH,rgnlMnthlyOcfLmt.getId().getProdMnth());
		query.setParameter(PRMTR_OCF_BYR_GRP_CD,rgnlMnthlyOcfLmt.getId().getOcfBuyerGrpCd());
		query.setParameter(PRMTR_OCFRGNCD,rgnlMnthlyOcfLmt.getId().getOcfRegionCd());
	    
		
		
		query.executeUpdate();
	}

	
	public Query  buildQueryFrUpdate(){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.updateRgnlUsgLmtTrn);
		dynamicQuery.append(QueryConstants.updateRgnlUsgLmtTrnCnd);
		
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		return query;
	}

	public void updateNewOrdrDtls(String porCd, String prdMnth, String ordrTkBsMnth, String carSrs, String byrGrpCd,String ocfByrGrpCd,String ocfRegionCd)
			throws PdApplicationException, ParseException {
		
			
			List<Object[]> sumFeatUsageDtls = byrGrpMnthlyOcfLimitTrnRepositoryObj.fetchSumFeatUsage(porCd, prdMnth, ordrTkBsMnth, carSrs, byrGrpCd,ocfRegionCd,ocfByrGrpCd);
			
			for(Object[] featObj : sumFeatUsageDtls){
				String sumFeatUsageQty = CommonUtil.convertObjectToString(featObj[0]);
				String featureCd = CommonUtil.convertObjectToString(featObj[1]);
				String ocfFrameCd = CommonUtil.convertObjectToString(featObj[2]);
				String featTypeCd = CommonUtil.convertObjectToString(featObj[3]);
				
				merge(porCd, prdMnth, ordrTkBsMnth, carSrs, ocfRegionCd,ocfByrGrpCd,featureCd,ocfFrameCd,featTypeCd,sumFeatUsageQty);
				
							
			}
			
		
	}

	
	public void merge(String porCd, String prdMnth, String ordrTkBsMnth, String carSrs, String ocfRegionCd,String ocfByrGrpCd,String featureCd,String ocfFrameCd,String featTypeCd,String sumFeatUsageQty)
			throws PdApplicationException {
		TrnRegionalMnthlyOcfLmt rgnlMnthlyOcfLmt  = populateToBean(porCd, prdMnth, ordrTkBsMnth, carSrs, ocfRegionCd,ocfByrGrpCd,featureCd,ocfFrameCd,featTypeCd,sumFeatUsageQty,null);
		rgnlMnthlyOcfLmt.setCrtdBy("B000018");
		rgnlMnthlyOcfLmt.setUpdtdBy("B000018");
		eMgr.merge(rgnlMnthlyOcfLmt);		
	}
	
	
	public TrnRegionalMnthlyOcfLmt populateToBean(String porCd, String prdMnth, String ordrTkBsMnth, String carSrs, String ocfRegionCd,String ocfByrGrpCd,String featureCd,String ocfFrameCd,String featTypeCd,String sumFeatUsageQty,String lmtQty){
		

		
		TrnRegionalMnthlyOcfLmt rgnlMnthlyOcfLmt = new TrnRegionalMnthlyOcfLmt();
		
		TrnRegionalMnthlyOcfLmtPK rgnlMnthlyOcfLmtPk = new TrnRegionalMnthlyOcfLmtPK();
		
		
		rgnlMnthlyOcfLmtPk.setPorCd(porCd);
		rgnlMnthlyOcfLmtPk.setOcfRegionCd(ocfRegionCd);
		rgnlMnthlyOcfLmtPk.setCarSrs(carSrs);
		rgnlMnthlyOcfLmtPk.setOcfBuyerGrpCd(ocfByrGrpCd);
		rgnlMnthlyOcfLmtPk.setOrdrTakeBaseMnth(ordrTkBsMnth);
		rgnlMnthlyOcfLmtPk.setProdMnth(prdMnth);
		rgnlMnthlyOcfLmtPk.setFeatCd(featureCd);
		rgnlMnthlyOcfLmt.setId(rgnlMnthlyOcfLmtPk);
		
		
		TrnRegionalMnthlyOcfLmt rgnlMnthlyOcfLmtOld = eMgr.find(TrnRegionalMnthlyOcfLmt.class, rgnlMnthlyOcfLmtPk);
		
		if(rgnlMnthlyOcfLmtOld!=null){
			rgnlMnthlyOcfLmt =  rgnlMnthlyOcfLmtOld;
		} else {
			if(lmtQty!=null){
				BigDecimal ocflmtQty = 	BigDecimal.valueOf(Integer.parseInt(lmtQty));
				rgnlMnthlyOcfLmt.setRegionalOcfLmtQty(ocflmtQty);	
			} else {
				if(ocfFrameCd!=null && ocfFrameCd.equalsIgnoreCase("00")){
					lmtQty = "0";
					BigDecimal ocflmtQty = 	BigDecimal.valueOf(Integer.parseInt(lmtQty));
					rgnlMnthlyOcfLmt.setRegionalOcfLmtQty(ocflmtQty);
				} 
			}	
		}
		
		
		if(sumFeatUsageQty!=null){
			rgnlMnthlyOcfLmt.setRegionalOcfUsgQty(BigDecimal.valueOf(Integer.parseInt(sumFeatUsageQty)));
		}
		
		
		
		
		rgnlMnthlyOcfLmt.setFeatTypeCd(featTypeCd);
		
		rgnlMnthlyOcfLmt.setOcfFrmeCd(ocfFrameCd);
		
		return rgnlMnthlyOcfLmt;
		
	}

	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
