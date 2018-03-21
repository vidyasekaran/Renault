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
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADTNL_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_APPLD_MDL_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
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
import com.nissangroups.pd.model.TrnMnthlyOrdr;
import com.nissangroups.pd.model.TrnMnthlyOrdrPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class MnthlyOrdrTrnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Autowired(required=false)
	private FrznVldnRepository	frznVldnRepositoryObj;
	

	
	
	

	public int fetchSumOrdrQty(String oseiId,String porCd,String ordrTkBsMnth,String prdMnth){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchSumOrdrQty);
		dynamicQuery.append(QueryConstants.grpByOseiIdCnd);
		dynamicQuery.append(QueryConstants.grpByToFetchSumOrdrQty);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_OSEI_ID,oseiId);
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		
		List<BigDecimal> result = query.getResultList();
		int sumOrdrQty = result.get(0).intValue();
		return sumOrdrQty;
		
	}
			
	public Query buildSummrzdMsFrznQuery(InputBean input,String frznType,String grpByCnd,String frznOrdrErrRprt){
		
		StringBuilder dynamicQuery = new StringBuilder();
		if(grpByCnd.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyPart1);
		} else {
			dynamicQuery.append(QueryConstants.selectPotOseiId);
		}
		dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyPart2);
		if(frznOrdrErrRprt.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.fetchExistingFrznOrdrErrDtls);
		}
		
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("E") || frznOrdrErrRprt.equalsIgnoreCase("true") ){
			dynamicQuery.append(QueryConstants.fetchOseiExtClrCd);	
		}
		
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("U")|| frznOrdrErrRprt.equalsIgnoreCase("true") ){
			dynamicQuery.append(QueryConstants.fetchOseiIntClrCd);	
		}
		
		if(!(frznType.equalsIgnoreCase("P")) || frznOrdrErrRprt.equalsIgnoreCase("true") ){
			dynamicQuery.append(QueryConstants.fetchOeiSpecSpecDestCd);	
		}
		
		
		dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyPart3);
		
		dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyJoin);
		
		if(frznOrdrErrRprt.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.fetchExistingFrznOrdrErrJoin);
		}
		
		dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyCnd);
		
		if(frznOrdrErrRprt.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.fetchExistingFrznOrdrErrCnd);
		}
		
		
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("E")){
			dynamicQuery.append(QueryConstants.grpByOseiExtClrCdCnd);	
		}
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("U")){
			dynamicQuery.append(QueryConstants.grpByOseiIntClrCdCnd);	
		}
		if(!(frznType.equalsIgnoreCase("P"))){
			dynamicQuery.append(QueryConstants.grpByOeiSpecSpecDestCdCnd);	
		}
		
		if(grpByCnd.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.fetchSmmrzdMsQtyGrpBy);
			if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("E")){
				dynamicQuery.append(QueryConstants.grpByOseiExtClrCd);	
			}
			if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("U")){
				dynamicQuery.append(QueryConstants.grpByOseiIntClrCd);	
			}
			if(!(frznType.equalsIgnoreCase("P"))){
				dynamicQuery.append(QueryConstants.grpByOeiSpecSpecDestCd);	
			}
		}
		
		
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		//split list in to 10 size lists
		Query query = eMgr.createNativeQuery(queryStr.toString());
		return query;
		
	}
	
	public List<Object[]> fetchSummrzdMsQtyFrznDtls(Query query,InputBean input,Object[] obj,String frznType,String grpByCnd) {
		
		
		int i = 2;
		
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, obj[i++].toString().substring(0, 6));
		query.setParameter(PRMTR_PRD_MNTH,obj[i++].toString().toString().substring(0, 6) );
		query.setParameter(PRMTR_FRZN_TYPE, obj[i++].toString());
		query.setParameter(PRMTR_CAR_SRS, obj[i++].toString());
		query.setParameter(PRMTR_APPLD_MDL_CD, obj[i++].toString());
		query.setParameter(PRMTR_PCK_CD, obj[i++].toString());
		//if(){
			//query.setParameter(PRMTR_BYR_CD,  obj[i++].toString());
		//}
		query.setParameter(PRMTR_BYR_GRP_CD,  obj[i++].toString());
		query.setParameter(PRMTR_PRD_ORDR_STG_CD,  input.getPrdOrdrStgCd());
		
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("E")){
			query.setParameter(PRMTR_EXT_CLR_CD, obj[i++].toString());
		}
		if(frznType.equalsIgnoreCase("F") || frznType.equalsIgnoreCase("U")){
			query.setParameter(PRMTR_INT_CLR_CD, obj[i++].toString());	
		}
		if(!(frznType.equalsIgnoreCase("P"))){
			query.setParameter(PRMTR_SPEC_DEST_CD, obj[i++].toString());
		}
		
		List<Object[]> summrzdMsQtyDtls = query.getResultList();
		
		return summrzdMsQtyDtls;
	
	}

	
	//obj,frznType,input,summrdMsQtyDtl
	public void processFrznRcrds(Object[] ifObj,String frznType,InputBean input,Object[] summrdMsQtyDtl) throws PdApplicationException{
		
		Query query = buildSummrzdMsFrznQuery(input,frznType,"false","false");
		List<Object[]> mnthlyOrdrTrnDtls = fetchSummrzdMsQtyFrznDtls(query,input,ifObj,frznType,"false");
		if(mnthlyOrdrTrnDtls!=null && !(mnthlyOrdrTrnDtls.isEmpty())){
			for(Object[] mnthlyOrdrTrn : mnthlyOrdrTrnDtls){
				
				
				updateMthlyOrdrTrn(mnthlyOrdrTrn, input,0,"true");
				
			}
		}
		
		List<Object[]> fullFrznRcrds =  frznVldnRepositoryObj.fetchFrznSummrzdOrdrQty(input,frznType,"false",ifObj,"false");
		if(fullFrznRcrds!=null && !(fullFrznRcrds.isEmpty())){
			for(Object[] mnthlyOrdrIfTrn : fullFrznRcrds){
				merge(mnthlyOrdrIfTrn,input,null,"false");
				
			}
		}		
		
		
		
	}
	
	
	public void initialisation(InputBean input)
			throws PdApplicationException, ParseException {

		
		
		StringBuilder dynamicQuery = new StringBuilder();
		
		dynamicQuery.append(QueryConstants.updateOrdrQtyInmnthlyOrderTrn);
		dynamicQuery.append(QueryConstants.mnthlyOrderTrnPkCnd);
		
		List<Object[]> distinctOseiIdList = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctOseiId(input,"false");
		
		Query query = buildUpdateQuery("false");
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
			executeUpdateQuery(query, 0, input.getPorCd(), oseiID, ordrTkMnthString, ordrTkMnthString, input.getPrdOrdrStgCd(), null);
			for(int i =1;i<horizon;i++){
				Date prdMnthDate = CommonUtil.addMonthToDate(ordrTkMnth,i);
				String prdMnth = CommonUtil.convertDateToString(prdMnthDate,DATE_FORMAT_MONTH);
				executeUpdateQuery(query, 0, input.getPorCd(), oseiID, ordrTkMnthString, prdMnth, input.getPrdOrdrStgCd(), null);
			}
			
		}
		
		
	}
	

	
	
	

	
	public void merge(Object[] mnthlyOrdrTrn,InputBean input,String ordrQty,String updtMsQty)
			throws PdApplicationNonFatalException {

		StringBuilder dynamicQuery = new StringBuilder();
		
		
		TrnMnthlyOrdr  mnthlyOrdrTrnObj = new TrnMnthlyOrdr();
		mnthlyOrdrTrnObj.setCrtdBy("B000018");
		mnthlyOrdrTrnObj.setUpdtdBy("B000018");
		TrnMnthlyOrdrPK mnthlyOrdrTrnPkObj = new TrnMnthlyOrdrPK(); 
		
		int i = 0;
		    
		
		mnthlyOrdrTrnPkObj.setProdOrdrStageCd(input.getPrdOrdrStgCd());
		BigDecimal ordrQtyDecimal = new BigDecimal(0);
		BigDecimal msQty = new BigDecimal(0); 
		if(ordrQty!=null){
			ordrQtyDecimal = BigDecimal.valueOf(Integer.parseInt(ordrQty));
		} else {
			String ordrQtyStr = CommonUtil.convertObjectToString(mnthlyOrdrTrn[i++]);
			if(!(ordrQtyStr.equalsIgnoreCase(""))){
				ordrQtyDecimal = BigDecimal.valueOf(Integer.parseInt(ordrQtyStr));	
			}
			
		}
		mnthlyOrdrTrnPkObj.setPotCd(mnthlyOrdrTrn[i++].toString());
		mnthlyOrdrTrnPkObj.setOseiId(mnthlyOrdrTrn[i++].toString());
		mnthlyOrdrTrnPkObj.setPorCd(mnthlyOrdrTrn[i++].toString());
		mnthlyOrdrTrnPkObj.setOrdrtkBaseMnth(mnthlyOrdrTrn[i++].toString().trim());
		mnthlyOrdrTrnPkObj.setProdMnth(mnthlyOrdrTrn[i++].toString().trim());
		
		
		mnthlyOrdrTrnObj.setId(mnthlyOrdrTrnPkObj);
		
		TrnMnthlyOrdr mnthlyOrdrTrnObjOld = eMgr.find(TrnMnthlyOrdr.class, mnthlyOrdrTrnPkObj);
		if(mnthlyOrdrTrnObjOld!=null){
			mnthlyOrdrTrnObj =  mnthlyOrdrTrnObjOld;
		}
		
		
		mnthlyOrdrTrnObj.setOrdrQty(ordrQtyDecimal);
		if(updtMsQty.equalsIgnoreCase("true")){
			String msQtyStr = CommonUtil.convertObjectToString(mnthlyOrdrTrn[i++]);
			if(!(msQtyStr.equalsIgnoreCase(""))){
				msQty = BigDecimal.valueOf(Integer.parseInt(msQtyStr));	
			}
			mnthlyOrdrTrnObj.setMsQty(msQty);
		}
		
		
		
		eMgr.merge(mnthlyOrdrTrnObj);
		
		
	}
	
	
	public Query buildUpdateQuery(String potCdreq){
		StringBuilder dynamicQuery = new StringBuilder();
		
		
		dynamicQuery.append(QueryConstants.updateOrdrQtyInmnthlyOrderTrn);
		dynamicQuery.append(QueryConstants.mnthlyOrderTrnPkCnd);
		if(potCdreq.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.mnthlyOrderTrnPotCnd);
			
		}
		String queryStr = dynamicQuery.toString();
		
		Query query = eMgr.createNativeQuery(queryStr);
		return query;
	}
	
	public void updateMthlyOrdrTrn(Object[] mnthlyOrdrTrn,InputBean input,int ordrQty,String potCdreq)
			throws PdApplicationNonFatalException {

		
		
		Query query = buildUpdateQuery(potCdreq);
		int i = 1;
		
		
		String prdOrdrStgCd = input.getPrdOrdrStgCd();
		String potCd = mnthlyOrdrTrn[i++].toString();
		String oseiId = mnthlyOrdrTrn[i++].toString();
		String porCd = mnthlyOrdrTrn[i++].toString();
		
		String ordrTkBsMnth = mnthlyOrdrTrn[i++].toString();
		if(ordrTkBsMnth.length()>6){
			ordrTkBsMnth = ordrTkBsMnth.substring(0, 6);
		}
		
		String prdMnth = mnthlyOrdrTrn[i++].toString();
		if(prdMnth.length()>6){
			prdMnth = prdMnth.substring(0, 6);
		}
		
		
		executeUpdateQuery(query,ordrQty,porCd,oseiId,ordrTkBsMnth,prdMnth,prdOrdrStgCd,potCd);
		
		
	}
	
	

	public void executeUpdateQuery(Query query,int ordrQty,String porCd,String oseiId,String ordrTkBsMnth,String prdMnth,String prdOrdrStgCd,String potCd){
		query.setParameter(PRMTR_ORDR_QTY, ordrQty);
		
		query.setParameter(PRMTR_PRD_ORDR_STG_CD,  prdOrdrStgCd);
		if(potCd!=null){
			query.setParameter(PRMTR_POT_CD,potCd);	
		}
		
		query.setParameter(PRMTR_OSEI_ID,oseiId);
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		
		query.executeUpdate();
	}
	
	
	public void updateNewOrdrDtls(InputBean input)
			throws PdApplicationException, ParseException {

		
		List<Object[]> distinctOseiIdList = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctOseiId(input,"true");
		
		for(Object[] obj : distinctOseiIdList){
			String oseiId = CommonUtil.convertObjectToString(obj[0]);
			String ordrTkMnthString = CommonUtil.convertObjectToString(obj[1]);
			String ordrQtyString = CommonUtil.convertObjectToString(obj[2]);
			String potCd = CommonUtil.convertObjectToString(obj[3]);
			String prdMnth = CommonUtil.convertObjectToString(obj[4]);
			
			Object[] mnthlyOrdrTrn = {ordrQtyString,potCd,oseiId,input.getPorCd(),ordrTkMnthString,prdMnth};
			
			merge(mnthlyOrdrTrn, input,null,"false");
			
						
		}
		
		
	}


	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
