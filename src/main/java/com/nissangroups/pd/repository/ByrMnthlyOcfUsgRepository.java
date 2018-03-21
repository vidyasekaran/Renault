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

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADTNL_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_APPLD_MDL_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_EXT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FRZN_TYPE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_INT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OSEI_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PCK_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_POT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_ORDR_STG_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SPEC_DEST_CD;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_OCF_USAGE_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UPDT_BY;

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
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.QueryConstants;


public class ByrMnthlyOcfUsgRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	
	public List<String> fetchFeatureCdForOseiId(String porCd,String oseiId,String prdMnth,String ordrTkBsMnth){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchFtrCdFrOseiId);
		dynamicQuery.append(QueryConstants.fetchByOseiIdCnd);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_OSEI_ID,oseiId);
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		
		List<String> result = query.getResultList();
		return result;
		
	}
	
	
	
	
	
	public void initialisation(InputBean input,List<Object[]> distinctOseiIdList)
			throws PdApplicationException, ParseException {



		
		for(Object[] obj : distinctOseiIdList){
			String oseiID = CommonUtil.convertObjectToString(obj[0]);
			String byrGrpHrzn = CommonUtil.convertObjectToString(obj[1]);
			String ordrTkMnthString = CommonUtil.convertObjectToString(obj[2]);
			String carSrsHrzn = CommonUtil.convertObjectToString(obj[3]);
			int horizon = 3;
			if(byrGrpHrzn.equalsIgnoreCase("") || byrGrpHrzn.equalsIgnoreCase("F")){
				 horizon = Integer.parseInt(carSrsHrzn);	 
			} else {
				horizon = 3;
			}
				
			if(ordrTkMnthString.length()>6){
				ordrTkMnthString = ordrTkMnthString.substring(0, 6);
			}
				
			
			Date ordrTkMnth = CommonUtil.convertStringToDate(ordrTkMnthString);
			ordrTkMnthString = CommonUtil.convertDateToString(ordrTkMnth,DATE_FORMAT_MONTH);
			Query query = buildQueryFrUpdate();
			executeUpdateQuery(query, 0, input.getPorCd(), oseiID, ordrTkMnthString, ordrTkMnthString);
			for(int i =1;i<horizon;i++){
				Date prdMnthDate = CommonUtil.addMonthToDate(ordrTkMnth,i);
				String prdMnth = CommonUtil.convertDateToString(prdMnthDate,DATE_FORMAT_MONTH);
				executeUpdateQuery(query, 0, input.getPorCd(), oseiID, ordrTkMnthString, prdMnth);
			}
			
		}
		
		
	}

	public void executeUpdateQuery(Query query,int byrUsgQty,String porCd,String oseiId,String ordrTkBsMnth,String prdMnth){
	
		query.setParameter(PRMTR_BYR_OCF_USAGE_QTY, byrUsgQty);
		query.setParameter(PRMTR_OSEI_ID,oseiId);
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		query.setParameter(PRMTR_UPDT_BY,"B000018");
		query.setParameter("updtdDt",CommonUtil.createTimeStamp());
		
		query.executeUpdate();
	}

	
	public Query  buildQueryFrUpdate(){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.updateFeaturUsgFrOseiId);
		dynamicQuery.append(QueryConstants.fetchByOseiIdCnd);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		return query;
		
		
		
	}

	public void updateFeatureUsgFrOseiId(String porCd,String oseiId,String prdMnth,String ordrTkBsMnth,int byrUsgQty){
		
		
		Query query = buildQueryFrUpdate();
		
		executeUpdateQuery(query, byrUsgQty, porCd, oseiId, ordrTkBsMnth, prdMnth);
		
		
	}
	
	
public List<Object[]> fetchSumFeatUsage(String porCd,String prdMnth,String ordrTkBsMnth,String carSrs,String byrGrpCd){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchSumFeatUsage);
		dynamicQuery.append(QueryConstants.fetchSumFeatUsageCnd);
		dynamicQuery.append(QueryConstants.fetchSumFeatUsageGrpBy);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		

		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(PRMTR_BYR_GRP_CD, byrGrpCd);
		
		List<Object[]> result = query.getResultList();
		return result;
		
	}
	

	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
