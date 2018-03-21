/* System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This is the common utility service which is used to load static data to B000059FileSpecVO from database 
 *                  it also contain other method which needs to do some computation and data conversion to java 
 *                  collection store the value to B000059FileSpecVO.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.util;
import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PATTERN_MATCHING_RESULT_MSG;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_REGION;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRDN_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000027.util.B000027CommonUtil;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.b000027.util.QueryConstants;
import com.nissangroups.pd.b000040.bean.B000040ProdPrdDetails;
import com.nissangroups.pd.b000040.output.B000040OrdrDtlsOutputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000040QueryDataService {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000040QueryDataService.class.getName());

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	
	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000040QueryInputVO queryInputVO;
	

	/*Based on the extracted Order Take base month ,Order take Base Week No. and Weekly Production Order Horizon
	 *the PRODUCTION Period details need to be defined
	 */
	
	public String constructWeekProPrdDetailsQuery() throws PdApplicationException{
		
		
		/*Delete the temporary data*/
		
		 deleteb000040OrdrdtlsTrn();
		
				 
		// queryInputVO.setPorCD("22");
		// queryInputVO.setOrdrTakBseMnth("201509");
		// queryInputVO.setOrdrTakBseWkNo("3");
		// queryInputVO.setOrdrProdPrdHorizn(3);		 
		//queryInputVO.setSuspendedFlag(true);
		// queryInputVO.setnMonthSuspendedFlag(true);
		 
		//MONTHLY FIXED ORDER REQUIRED
		//queryInputVO.setProdPeriodConfigValue("Y");
		 
		 
					
		/* Check whether the Monthly Process is completed for that month or not , using MONTHLY ORDER TAKE BASE PERIOD MST table as follows
		 if STAGE CD for the particular month (say 210506) is in SC status, then include the increased month order(retain that month, in previously calculated list) ,  Other wise not needed( remove from that list).			 
		 Query Construction for Monthly Process is completed for that month */	 			 		 
		 List<String> proMonthList = constructProductMonthList();

		 List<String> completedStgCdList = getCompletedStageCode(constructProductPeriodMonthQuery(proMonthList));	
		 
			 if(null != completedStgCdList){
				 proMonthList.retainAll(completedStgCdList);
			 }else{
				 LOG.error(B000040Constants.PROD_CMPTED_SC + " PORCD :" + queryInputVO.getPorCD());				 
				 throw new PdApplicationException(B000040Constants.PROD_CMPTED_SC);
			 }
		 
		 
		 //P0003 : WEEK NO CALENDAR MST ( FROM WEEK and TO WEEK details )
		 String query = constructProductPeriodWeekQuery(proMonthList);
		 
		 StringBuilder dynaQuery = new StringBuilder();		
		 
		 
		 List<B000040ProdPrdDetails> prdPrdMonthWeekList = getProductPeriodWeekCalculation(query,"N MONTH");		 		 		 		 
		 queryInputVO.setProdPrdDtls(prdPrdMonthWeekList);
		 
		  
		 dynaQuery.append(constructQueryForNMonthSuspendedOrder());
		 
		 
		 		
		 
		 	 		 		 		 
		 queryInputVO.setProdPrdDtls(prdPrdMonthWeekList);		 		
		 
		 if(null != proMonthList && proMonthList.size() > 1){
			
	     dynaQuery.append(" UNION ALL ");
		 proMonthList.remove(0);
		 
		 query = constructProductPeriodWeekQuery(proMonthList);
		 
		 prdPrdMonthWeekList = getProductPeriodWeekCalculation(query,"FCAST MONTH");	
		 queryInputVO.setProdPrdDtls(prdPrdMonthWeekList);	
		 
		 dynaQuery.append(constructQueryForAfterMonthSuspendedOrder());
		 }
		 
		 return dynaQuery.toString();
		 
	}
	
	
	/* P0003 : Create a list of productin month based on horizion values.
	 * Increase  the month volume by 1 and check whether the Monthly Order stage is completed or not using MONTHLY ORDER TAKE BASE PERIOD MST table.
	 */
	private List<String> constructProductMonthList(){
		String orderTakeBaseMonth = null;
		Integer horizonVal = null;
		List<String> productMonthList = null;
		try{
			
			orderTakeBaseMonth = queryInputVO.getOrdrTakBseMnth();
			horizonVal = queryInputVO.getOrdrProdPrdHorizn();	
			productMonthList = new ArrayList<String>();		
								
			// if values of MONTHLY FIXED ORDER REQUIRED is true, add the horizon values with current month, else keep alone the current month in list.
			if(null != queryInputVO.getProdPeriodConfigValue() && queryInputVO.getProdPeriodConfigValue().equalsIgnoreCase("Y")){
				for(int i=0;i<horizonVal;i++){					
					Integer s =  (new Integer(orderTakeBaseMonth) + i );
					productMonthList.add(s.toString());
				}
			}else{
					productMonthList.add(orderTakeBaseMonth);
			}
			
		}catch(Exception e){
			LOG.error("Error : " +  Arrays.asList(e));
		}
		return productMonthList;
	}
	
	/*
	 * Constructing query for production period month details
	 */
	private String constructProductPeriodMonthQuery(List<String> proMonthList) {
		
		String mnthlyPrcsChk = null;
		StringBuilder inClause = null;		
		try{													
			mnthlyPrcsChk = B000040QueryConstants.mnthlyPrcsChk.toString();	
			// Process to take the completed stage code 					
			inClause = formInClause(proMonthList);				
			mnthlyPrcsChk = mnthlyPrcsChk.replaceAll(":ordrTkBsePrd", inClause.toString()).replaceAll(":porCd", queryInputVO.getPorCD());																												
		}catch(Exception e){			
			LOG.error("Error : " +  Arrays.asList(e));
		}
		return mnthlyPrcsChk;
	}


	/*
	 * Construct the query for product week no for month list
	 * P0003 : WEEK NO CALENDAR MST ( FROM WEEK and TO WEEK details )
	 */
	public String  constructProductPeriodWeekQuery(List<String> prodPrdMonthList){
				
		String extrctProdWkNoQuery = null;
		String inClause = null;
		
		try{
			extrctProdWkNoQuery = B000040QueryConstants.extrctProdWkNo.toString();
			
			if(null != prodPrdMonthList){
				inClause = formInClause(prodPrdMonthList).toString();
				extrctProdWkNoQuery = extrctProdWkNoQuery.replaceAll(":porCd", queryInputVO.getPorCD()).replaceAll(":prodMnth", inClause);
				extrctProdWkNoQuery.replaceAll("", inClause);										
			}
			
		}catch(Exception e){
			LOG.error("Error : " +  Arrays.asList(e));
		}
		
		return extrctProdWkNoQuery;	
	}
	
	/*
	 * Get the Product Month & Week Calculation for given query.
	 */
	private List<B000040ProdPrdDetails> getProductPeriodWeekCalculation(
			String extrctProdWkNoQuery,String monthType) {
		
		List<B000040ProdPrdDetails> prodPrdDetailsList = null;
		Query query = null;
		List<Object[]> MstWkNoClndr = null;
		
		try{
			query = getEntityManager().createNativeQuery(extrctProdWkNoQuery);
			
			MstWkNoClndr = (List<Object[]>) query.getResultList();
			
			if(null != MstWkNoClndr && !MstWkNoClndr.isEmpty()){
				
				prodPrdDetailsList = new ArrayList<B000040ProdPrdDetails>();
				
				if(monthType.equalsIgnoreCase("N MONTH")){
					Object[] mstWkNoClndr = MstWkNoClndr.get(0);
					prodPrdDetailsList.add(addStartEndWeekNoForOrderMonth(mstWkNoClndr,monthType));
				}else{
					for (Iterator<Object[]> iterator = MstWkNoClndr.iterator(); iterator
							.hasNext();) {
						Object[] mstWkNoClndr = (Object[]) iterator
								.next();															
						prodPrdDetailsList.add(addStartEndWeekNoForOrderMonth(mstWkNoClndr,monthType));
					}
				}
				
				
			}						
		}catch(Exception e){
			LOG.error("Error : " +  Arrays.asList(e));
		}
		return prodPrdDetailsList;
	}
	
	/*
	 * Adding the start and end week for order take month
	 */
	private B000040ProdPrdDetails addStartEndWeekNoForOrderMonth(Object[] mstWkNoClndr,String monthType){
		
		B000040ProdPrdDetails proPrdDetails = new B000040ProdPrdDetails();			
		proPrdDetails.setProdMnth(mstWkNoClndr[0].toString());					
		proPrdDetails.setProdWkNo(new ArrayList<String>());		
		Integer start = Integer.parseInt(queryInputVO.getOrdrTakBseWkNo());
		
		if(monthType.equalsIgnoreCase("FCAST MONTH")){
			start = 1;
		}
		Integer end = Integer.parseInt(mstWkNoClndr[1].toString());
		for(Integer i = start; i<=end; i++){
			proPrdDetails.getProdWkNo().add(i.toString());
		}
												
		return proPrdDetails;
	}
	/*
	 * Construct the SQL - IN Clause - text for given list like IN('one','two','three')
	 */
	private StringBuilder formInClause(List<String> list){
		
		StringBuilder inClause = new StringBuilder();
		inClause.append("''");
		
		if(null != list && !list.isEmpty()){			
			inClause.append(",");
			for (Iterator<String> iterator = list.iterator(); iterator
					.hasNext();) {					
				inClause.append("'").append(iterator.next()).append("'");
				inClause.append(iterator.hasNext() ? "," : "");							
			}
		}		
		return inClause;
	}
	

	
	/*
	 * Get the Order Take Base Month for given query
	 */
	private List<String> getCompletedStageCode(String mnthlyPrcsChk) {
		
		List<String> completedStageCdMonthList = null;
		try{
			Query query = getEntityManager().createNativeQuery(mnthlyPrcsChk);
			
			List<Object[]> mstMonthOrderTakeBasePdList = (List<Object[]> )query.getResultList();
			
			if(null != mstMonthOrderTakeBasePdList && !mstMonthOrderTakeBasePdList.isEmpty()){
				
				completedStageCdMonthList = new ArrayList<String>();
				for (Iterator<Object[]> iterator = mstMonthOrderTakeBasePdList.iterator(); iterator
						.hasNext();) {
					Object[] mstMnthOrdrTakeBasePd = (Object[]) iterator.next();
					completedStageCdMonthList.add(mstMnthOrdrTakeBasePd[1].toString());
					
				}
			}						
		}catch(Exception e){
			LOG.error("Error : " +  Arrays.asList(e));
		}
		return completedStageCdMonthList;
	}
	
	/**
	 * P0001 : WEEKLY ORDER TAKE BASE PERIOD MST (Extract Order Take Base Period )
	 * @param porCD
	 */	
	public void storeOrderTakeBasePeriod(String porCD) throws PdApplicationException 
	{
		Query selectOrdrTakBsePrd = getEntityManager()
				.createNativeQuery(B000040QueryConstants.extrctOrdrTakBsePrd
						.toString());
		try{
			selectOrdrTakBsePrd.setParameter("porCD", porCD);
			
			List<?> ordrTakBsePrdList = selectOrdrTakBsePrd.getResultList();
			
			if(null != ordrTakBsePrdList && ordrTakBsePrdList.isEmpty()){
				
				String logErr = B000040Constants.M00250.replaceAll("&1", B000040Constants.BATCH_ID_B000040)
						.replaceAll("&2", porCD)
						.replaceAll("&3", B000040Constants.STAGE_CD)
						.replaceAll("&4", B000040Constants.STAGE_STTS_CD);
				LOG.error(logErr);
				throw new PdApplicationException(logErr);
			}
			queryInputVO.setOrdrTakBseMnth(ordrTakBsePrdList.get(0).toString());
			queryInputVO.setOrdrTakBseWkNo(ordrTakBsePrdList.get(1).toString());
		}catch(Exception e){
			LOG.error(e);
			throw new PdApplicationException(e.getStackTrace());
		}				
	}
	
	/**
	 * Extract Order Production Period Horizon
	 * P0002 : PARAMETER MST (Extract Order Production Period Horizon)
	 * @param porCD
	 */
	public void storeProdPeriodHorizon(String porCD)
			throws PdApplicationException {
		try {
			Query extrctOrdrPrdHorizn = getEntityManager().createNativeQuery(
					B000040QueryConstants.extrctOrdrPrdHorizn.toString());
			extrctOrdrPrdHorizn.setParameter("porCD", porCD);

			List<?> prdHoriznList = extrctOrdrPrdHorizn.getResultList();
			if (null != prdHoriznList && prdHoriznList.isEmpty()) {
				String errLog = B000040Constants.M00091.replaceAll("&1",
						B000040Constants.BATCH_ID_B000040).replaceAll("&2",
						porCD);
				LOG.error(errLog);
				throw new PdApplicationException(errLog);
			}
			int horizon = Integer.parseInt(prdHoriznList.get(0).toString());

			queryInputVO.setOrdrProdPrdHorizn(horizon);
		} catch (Exception e) {
			LOG.error(e);
			throw new PdApplicationException(e.getStackTrace());
		}
	}

	/*
	 * P0003 : Cong1:  PARAMETER MST (MONTHLY FIXED ORDER REQUIRED) ,Based on the value, getting in this method, Production Period Calculation will be performed.
	 */
	public void storeProdPeriodConfig(String porCD)throws PdApplicationException
	{
		try 
		{
			Query extrctProdPeriod = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctParamtrzdConfg
							.toString());
			extrctProdPeriod.setParameter("porCD", porCD);
			List<?> prodPeriodConfigValueList = extrctProdPeriod.getResultList();
			if(null != prodPeriodConfigValueList && prodPeriodConfigValueList.isEmpty()){
				LOG.warn("Congiuration Issues : " + B000040QueryConstants.extrctParamtrzdConfg);
			}
			queryInputVO.setProdPeriodConfigValue(prodPeriodConfigValueList.get(0).toString());						
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			throw new PdApplicationException(e.getStackTrace());
		}
		
	}
	
	/*P0004 : P0004.1 PARAMTER MST (USE ORIGINAL LINE CLASS AND PLANT CD )
	 * set LineClassandPlntCdFlag
	*/
	public void storeLineSegmentPlantCD(String porCD) {
		try {
			Query extrctlneClsPlntCd = getEntityManager().createNativeQuery(
					B000040QueryConstants.extrctlneClsPlntCd.toString());
			extrctlneClsPlntCd.setParameter("porCD", porCD);

			List<?> lneClsPlntCdList = extrctlneClsPlntCd.getResultList();
			
			if(null != lneClsPlntCdList && lneClsPlntCdList.isEmpty()){
				LOG.warn("Congiuration Issues : " + B000040QueryConstants.extrctlneClsPlntCd.toString());
			}
			if (extrctlneClsPlntCd.getResultList().get(0).equals("T")) {
				queryInputVO.setLneClsAndPlntCdFlg(true);
			} else if (extrctlneClsPlntCd.getResultList().get(0).equals("F")) {
				Query cnstSpaceValue = getEntityManager().createNativeQuery(
						B000040QueryConstants.cnstntlneClsPlntCd.toString());
				extrctlneClsPlntCd.setParameter("porCD", porCD);
				queryInputVO.setLneClsAndPlntCdFlg(false);
				queryInputVO.setCnstSpaceValue(""
						+ cnstSpaceValue.getResultList().get(0));
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}

	}

	
	public void extrctCnstDyFlg(String porCD)
	{
		try 
		{
			Query extrctCnstDyFlg = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctCnstDyFlg
							.toString());
			extrctCnstDyFlg.setParameter("porCD", porCD);
			
			if(extrctCnstDyFlg.getResultList().get(0).equals(true))
			{
				queryInputVO.setCnstDyFlg(true);	
				queryInputVO.setCnstDyValue(extrctCnstDyFlg.getResultList().get(1).toString());
				
				
			}
			else if(extrctCnstDyFlg.getResultList().get(0).equals(false))
			{
				queryInputVO.setCnstDyFlg(false);	
			}
						
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
	}
	
	/* P0004 : P0004.1 PARAMTER MST (SUSPENDED ORDER REQUIRED */
	public void storeSuspendedOrderFlg(String porCD) {
		Query extrctSuspndedFlg = getEntityManager().createNativeQuery(
				B000040QueryConstants.extrctSuspndedFlg.toString());
		extrctSuspndedFlg.setParameter("porCD", porCD);
		if (null != extrctSuspndedFlg.getResultList()
				&& extrctSuspndedFlg.getResultList().get(0).equals("T")) {
			queryInputVO.setSuspendedFlag(true);

			Query extrctNMnthSuspndedFlg = getEntityManager()
					.createNativeQuery(
							B000040QueryConstants.extrctNMnthSuspndedFlg
									.toString());
			extrctNMnthSuspndedFlg.setParameter("porCD", porCD);

			List<?> suspndedFlgList = extrctNMnthSuspndedFlg.getResultList();
			if (null != suspndedFlgList && suspndedFlgList.isEmpty()) {
				LOG.warn("Congiuration Issues : "
						+ B000040QueryConstants.extrctNMnthSuspndedFlg
								.toString());
			}
			if (extrctNMnthSuspndedFlg.getResultList().get(0).equals("T")) {
				queryInputVO.setnMonthSuspendedFlag(true);
			} else if (extrctNMnthSuspndedFlg.getResultList().get(0)
					.equals("F")) {
				queryInputVO.setnMonthSuspendedFlag(false);
			}

		} else {
			queryInputVO.setSuspendedFlag(false);
		}

	}

	/* Logic to form query below criteria 
	 * 1. SUSPENDED ORDER REQUIRED --True
	 * 2. N MONTH SUSPENDED ORDER REQUIRED --True/False
	 */
	
	public String constructQueryForNMonthSuspendedOrder() {
		
		StringBuilder query = getExtractionFields();

		// SUSPENDED ORDER REQUIRED -- True and N MONTH SUSPENDED ORDER REQUIRED  --True
		if (queryInputVO.isSuspendedFlag()&& queryInputVO.isnMonthSuspendedFlag()) {

			query.append(B000040QueryConstants.extrctNMnth_Cndtn_SuspendedTrue);

			return (query.toString().replaceAll(IFConstants.porCd_Param, queryInputVO.getPorCD()).replaceAll(IFConstants.ordrTkBsMnth_Param,
					queryInputVO.getOrdrTakBseMnth()).replaceAll(
					B000040Constants.param_ORDR_TK_PROD_WK_NO,
					queryInputVO.getOrdrTakBseWkNo()));

		} //N MONTH SUSPENDED ORDER REQUIRED  --false
		else if (queryInputVO.isSuspendedFlag()) {
			query.append(" WHERE ").append(B000040QueryConstants.extrct_OrderDetails_Cndtn_SuspendedFalse);
			
			// osei list (From week trn table)
			List<String>  oseiList = extrctOseiDtlsFrNMnth();
			String inClauseFormation = formInClause(oseiList).toString();
			return query.toString()
					.replaceAll(IFConstants.ordrTkBsMnth_Param,
							queryInputVO.getOrdrTakBseMnth())
					.replaceAll(B000040Constants.param_OSEI_ID,
							inClauseFormation)
					.replaceAll(B000040Constants.param_ORDR_TK_PROD_WK_NO,
							queryInputVO.getOrdrTakBseWkNo());
		}

		return null;
	}
		
	/* Logic to form query below criteria 
	 * 1. FORECAST MONTHS SUSPENDED ORDER REQUIRED -- True
	 * 2. After N MONTH SUSPENDED ORDER REQUIRED --True/False
	 */
	public String constructQueryForAfterMonthSuspendedOrder() {
		
		List<B000040ProdPrdDetails> prodPrdDetails = queryInputVO.getProdPrdDtls();						
		StringBuilder query = getExtractionFields();		
		StringBuilder monthWeekCombineQuery = new StringBuilder();
		for (Iterator<B000040ProdPrdDetails> iterator = prodPrdDetails.iterator(); iterator
				.hasNext();) {
			B000040ProdPrdDetails b000040ProdPrdDetails = iterator
					.next();						
			monthWeekCombineQuery.append(" ( TLP.PROD_MNTH = '").append(b000040ProdPrdDetails.getProdMnth()).append("' AND TLP.PROD_WK_NO IN(")						
			.append(formInClause(b000040ProdPrdDetails.getProdWkNo()))						
			.append(") ) ");						
			if(iterator.hasNext()){
				monthWeekCombineQuery.append(" OR ");							
			}																	
		}
		
		// FORECAST MONTHS SUSPENDED ORDER REQUIRED -- True and After N MONTH SUSPENDED ORDER REQUIRED  --True
		if (queryInputVO.isSuspendedFlag() && queryInputVO.isForeCastsuspendedFlag()) {
			query.append(B000040QueryConstants.extrctFreCstMnth_SuspndOrdr_True);								
					
			
						return (query.append(monthWeekCombineQuery).toString().replaceAll(IFConstants.ordrTkBsMnth_Param,
							queryInputVO.getOrdrTakBseMnth()).replaceAll(IFConstants.porCd_Param, queryInputVO.getPorCD()));
		}else if(queryInputVO.isSuspendedFlag()){
			query.append(" WHERE ").append("TLP.POR_CD = '").append(IFConstants.porCd_Param).append("' AND ").append(monthWeekCombineQuery).append(" AND ").append(B000040QueryConstants.extrct_OrderDetails_Cndtn_SuspendedFalse);		
			// osei list (From week trn table)
						List<String>  oseiList = extrctOseiDtlsFrAfterNMnth();
						String inClauseFormation = formInClause(oseiList).toString();
						
						
						return query.toString()
								.replaceAll(IFConstants.porCd_Param, queryInputVO.getPorCD())
								.replaceAll(IFConstants.ordrTkBsMnth_Param,
										queryInputVO.getOrdrTakBseMnth())
								.replaceAll(B000040Constants.param_OSEI_ID,
										inClauseFormation)
								.replaceAll(B000040Constants.param_ORDR_TK_PROD_WK_NO,
										queryInputVO.getOrdrTakBseWkNo())
								.replaceAll(IFConstants.porCd_Param, queryInputVO.getPorCD());															
		}
				
		
		return null;
		
	}
	
	
	/**
     * Extract the UK OSEI ID details After NMnth
     * @return
     */
     private List<String> extrctOseiDtlsFrAfterNMnth() {
            List<String> extrctOseiDtls =null;
                   
                   try 
                   {
                         List<B000040ProdPrdDetails> prodPrdDetails = queryInputVO.getProdPrdDtls();
                         
                        // prodPrdDetails.remove(0);
                         
                         StringBuilder oseiDtlsQry = new StringBuilder(B000040QueryConstants.extrctFreCstOseiId
                                       .toString());
                         for (Iterator<B000040ProdPrdDetails> iterator = prodPrdDetails.iterator(); iterator
                                       .hasNext();) {
                                B000040ProdPrdDetails b000040ProdPrdDetails = iterator
                                              .next();                                        
                                oseiDtlsQry.append(" ( PROD_MNTH = '").append(b000040ProdPrdDetails.getProdMnth()).append("' AND PROD_WK_NO IN(")                                   
                                .append(formInClause(b000040ProdPrdDetails.getProdWkNo()))                                      
                                .append(") ) ");                                       
                                if(iterator.hasNext()){
                                       oseiDtlsQry.append(" OR ");                                          
                                }                                                                                                                    
                         }
                        String  oseiDtlsQryModified = oseiDtlsQry.toString().replaceAll(IFConstants.porCd_Param, queryInputVO.getPorCD());
                         
                         Query extrctOseiDtlsAfrNMnth = getEntityManager()
                                       .createNativeQuery(oseiDtlsQryModified);
                         
                         extrctOseiDtls = extrctOseiDtlsAfrNMnth.getResultList();
                         if(extrctOseiDtls != null && !extrctOseiDtls.isEmpty()){
                                return extrctOseiDtls;
                         }
                   } 
                   catch (Exception e) 
                   {
                         LOG.error(ERROR_MESSAGE, e);
                         LOG.error(e);
                   }
                   return extrctOseiDtls;
     }




	private StringBuilder getExtractionFields() {
		StringBuilder query = new StringBuilder();		
		query.append(B000040QueryConstants.extrctNMnthOrdrDtls);
		if (queryInputVO.isLneClsAndPlntCdFlg()) {
			query.append(",").append(B000040QueryConstants.lneClsPlntCd_True);
		} else {
			query.append(",").append(queryInputVO.getCnstSpaceValue())
					.append(" AS PROD_PLNT_CD")
					.append(",")
					.append(queryInputVO.getCnstSpaceValue())
					.append(" AS LINE_CLASS");
		}
		if (queryInputVO.isCnstDyFlg()) {
			query.append(",").append(queryInputVO.getCnstDyValue())
					.append(" AS Original Day No");
		} else {
			query.append(B000040QueryConstants.CnstDyFlg_False);
		}
		query.append(B000040QueryConstants.extrctNMnth_From_Cndtn).append(B000040QueryConstants.extrct_OrderDetails_Join_Cndtn);
		
		return query;
	}
	
	/**
	 * Extract the UK OSEI ID details in the N month
	 * @param porCD
	 * @return
	 */
	public List<String> extrctOseiDtlsFrNMnth()
	{
		List<String> extrctOseiDtls =null;
		
		try 
		{
			Query extrctOseiDtlsInNMnth = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctOseiId
							.toString());
			extrctOseiDtlsInNMnth.setParameter("porCD", queryInputVO.getPorCD());
			extrctOseiDtlsInNMnth.setParameter("ordrTkBsMnth", queryInputVO.getOrdrTakBseMnth());
			extrctOseiDtlsInNMnth.setParameter("ordrTkProdWkNo", queryInputVO.getOrdrTakBseWkNo());
			
			 extrctOseiDtls = extrctOseiDtlsInNMnth.getResultList();
			
			if(extrctOseiDtls != null && !extrctOseiDtls.isEmpty()){
				return extrctOseiDtls;
			}
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
		return extrctOseiDtls;
	}


	public void extrctForeCastMonth(String porCD)
	{
		try 
		{
			Query extrctfrcstMnth_Ordr = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctfrcstMnth_Ordr
							.toString());
			extrctfrcstMnth_Ordr.setParameter("porCD", porCD);
			
			List<Object[]> extrctfrcstMnthList = extrctfrcstMnth_Ordr.getResultList();
			
			if(extrctfrcstMnthList != null && !extrctfrcstMnthList.isEmpty()){
				
				String extrctfrcstMnth = String.valueOf(extrctfrcstMnthList.get(0));
				
				if(extrctfrcstMnth.equals("T")){
					queryInputVO.setForeCastsuspendedFlag(true);
				}else if(extrctfrcstMnth.equals("F") ){
					queryInputVO.setForeCastsuspendedFlag(false);
				}
			}
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
	}
	
	
	public void extrctFrznSymbl(String porCD)
	{
		try 
		{
		Query extrctFrznSymbl = getEntityManager()
				.createNativeQuery(B000040QueryConstants.extrctFrznSymbl
						.toString());
		extrctFrznSymbl.setParameter("porCD", porCD);
		
		if(extrctFrznSymbl.getResultList().get(0).equals("T"))
		{ 
			queryInputVO.setFrozenSymbolRequiredFlag(true);
		}
		else if(extrctFrznSymbl.getResultList().get(0).equals("F") )
		{
			queryInputVO.setFrozenSymbolRequiredFlag(false);
		}
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
	
	}
	
	
	public void attachProductionMethodCodeFlag(String porCD) {
		try {
			Query extrctProdMthdCd = getEntityManager().createNativeQuery(
					B000040QueryConstants.extrctProdMthdCd.toString());
			extrctProdMthdCd.setParameter("porCD", porCD);

			if (extrctProdMthdCd.getResultList().get(0).equals("T")) {
				queryInputVO.setProductionMthdCd(true);

				Query extrctCnstProdMthdCd = getEntityManager()
						.createNativeQuery(
								B000040QueryConstants.extrctCnstProdMthdCd
										.toString());
				extrctCnstProdMthdCd.setParameter("porCD", porCD);

			} else if (extrctProdMthdCd.getResultList().get(0).equals("F")) {
				queryInputVO.setProductionMthdCd(false);
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
	}
	
	public void attachEXNoMapSymbols(String porCD) {
		try {
			Query extrctExNo = getEntityManager().createNativeQuery(
					B000040QueryConstants.extrctExNo.toString());
			extrctExNo.setParameter("porCD", porCD);

			if (extrctExNo.getResultList().isEmpty()) {
				LOG.error("Configuration Issues : "
						+ B000040Constants.WKLY_ORDR_PLNT
						+ " is should contains value");
				queryInputVO.setFrozenSymbolRequiredFlag(Boolean.FALSE);
			} else if (extrctExNo.getResultList().get(0).equals("T")) {
				queryInputVO.setAttachServiceParam(Boolean.TRUE);
			} else if (extrctExNo.getResultList().get(0).equals("F")) {
				queryInputVO.setAttachServiceParam(Boolean.FALSE);
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}

	}
	
	public void attachServicePrmtr(String porCD){
		try {
			Query extrctSrvcePrmtr = getEntityManager().createNativeQuery(
					B000040QueryConstants.extrctExNo.toString());
			extrctSrvcePrmtr.setParameter("porCD", porCD);

			if(extrctSrvcePrmtr.getResultList().isEmpty()){
				LOG.error("Configuration Issues : "
						+ B000040Constants.WKLY_ORDR_PLNT + ", " + B000040Constants.ATTACH_EX_NO
						+ " are should contains value");
				queryInputVO.setFrozenSymbolRequiredFlag(Boolean.FALSE);
			}else if (extrctSrvcePrmtr.getResultList().get(0).equals("T")) {
				queryInputVO.setAttachServiceParam(Boolean.TRUE);
			} else if (extrctSrvcePrmtr.getResultList().get(0).equals("F")) {
				queryInputVO.setFrozenSymbolRequiredFlag(Boolean.FALSE);
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}

	}
	
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public B000040QueryInputVO getQueryInputVO() {
		return queryInputVO;
	}

	public void setQueryInputVO(B000040QueryInputVO queryInputVO) {
		this.queryInputVO = queryInputVO;
	}

	public String getFrozenTypeCode(B000040OrdrDtlsOutputBean item) {
		
		String mxOrdrTkBsMnth = getMaxOfOrderTakeBaseMonth(item.getPorCd());
		
		String frznTyCd = getFrozenTypeCode(item,mxOrdrTkBsMnth);
		
		return frznTyCd;
	}
	
	
	public HashMap<String,String> getExNoMapSymbolsDtls(B000040OrdrDtlsOutputBean item,Boolean exNoMapVal){
		
		HashMap<String,String> exNoMapSymblHash = new HashMap<String,String>();
		
		//String query = (exNoMapVal) ? cnstrctQueryExNoMapTrue(B000040QueryConstants.extrctExNo_True) : cnstrctQueryExNoMapFalse(B000040QueryConstants.extrctExNo_True);
		
		Query natQuery = null;
		List<String> exNoMapSymbols = null;
		
		if(exNoMapVal){
			
			exNoMapSymbols = queryInputVO.getExNoMapTrueCols();
			natQuery = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctExNo_True.toString());
			
			natQuery.setParameter("prodMnth", item.getProdMnth());
			natQuery.setParameter("oseiId", item.getOseiId());
			
			List<String> resultList = natQuery.getResultList();
			
			for (int i = 0; i < resultList.size(); i++) {
				exNoMapSymblHash.put(exNoMapSymbols.get(i), resultList.get(i));
			}
			
			
		}else{
			exNoMapSymbols = queryInputVO.getExNoMapFalseCols();
			natQuery = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctExNo_False.toString());
			
			natQuery.setParameter("prodMnth", item.getProdMnth());
			natQuery.setParameter("oseiId", item.getOseiId());
			
			List<String> resultList = natQuery.getResultList();
			
			for (int i = 0; i < resultList.size(); i++) {
				exNoMapSymblHash.put(exNoMapSymbols.get(i), resultList.get(i));
			}
			
		}
		
		return exNoMapSymblHash;
		
	}
		
	public String getProductionMonthCode(B000040OrdrDtlsOutputBean item) {
		if(item.getProdPlntCd() == null){
			return queryInputVO.getProductionMthCnstValue();
		}else if(item.getProdPlntCd().isEmpty() || item.getProdPlntCd().equals("")){
			return queryInputVO.getProductionMthCnstValue();
		}else{
			return getMaxOfProductionMethodCode(item);
		}
	}

	public String getMaxOfProductionMethodCode(B000040OrdrDtlsOutputBean item){
		String maxPrdctnMthCode = null;
		try{
			Query maxOfProductionMthdCd = getEntityManager()
					.createNativeQuery(B000040QueryConstants.prodMthdCd
							.toString());
			maxOfProductionMthdCd.setParameter("porCD", item.getPorCd());
			maxOfProductionMthdCd.setParameter("oseiId", item.getOseiId());
			maxOfProductionMthdCd.setParameter("ordrTkBsMnth", queryInputVO.getOrdrTakBseMnth());
			maxOfProductionMthdCd.setParameter("prodMnth", item.getProdMnth());
			maxOfProductionMthdCd.setParameter("prodWkNo", item.getProdWkNo());
			maxOfProductionMthdCd.setParameter("prodPlntCd", item.getProdPlntCd());
			
			List<?> queryResultList = maxOfProductionMthdCd.getResultList();
			if(null != queryResultList && queryResultList.size() == 0){
				//TODO :: IF extraction Fails, log the below message and stop the batch process
			}else{
				maxPrdctnMthCode = (String) queryResultList.get(0);
			}
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
		}
		return maxPrdctnMthCode;		
	}
	public String storeCnstValProductionMonthCd(String portCd){
		String prdMnthCd = null;
		
		try{
			Query extrctCnstPrdMthCd = getEntityManager()
					.createNativeQuery(B000040QueryConstants.extrctCnstProdMthdCd
							.toString());
			extrctCnstPrdMthCd.setParameter("porCD", portCd);
			
			List<?> queryResutList = extrctCnstPrdMthCd.getResultList();
			
			if(queryResutList != null && queryResutList.size() == 0){
				queryInputVO.setProductionMthCnstValue(prdMnthCd); //TODO:: Throw errors ther no cnst value found 
			}else{
				prdMnthCd = (String) queryResutList.get(0);
				queryInputVO.setProductionMthCnstValue(prdMnthCd);
			}
			
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
		}
		
		return prdMnthCd;
	}

	
	// Frozen Symbol attachment.
	// Extract Latest Stage Completed Monthly Order Take Base Month as follows
	
	public String getMaxOfOrderTakeBaseMonth(String porCD)
	{
		String maxOrderTakeBaseMonth = null;
		try 
		{
		Query extrctCompletedStgOrderNo = getEntityManager()
				.createNativeQuery(B000040QueryConstants.extrctStgCmpltdOrdr
						.toString());
		extrctCompletedStgOrderNo.setParameter("porCD", porCD);

		maxOrderTakeBaseMonth =  (String) extrctCompletedStgOrderNo.getResultList().get(0);//TODO:: check if not records found
			
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
		return maxOrderTakeBaseMonth;
		
	}
	
	///After the extraction latest stage Completed Order Take Base month, do as follows
	// Extract -- FROZEN TYPE CD
	public String getFrozenTypeCode(B000040OrdrDtlsOutputBean item,String ordrTkBsMonth)
	{
		String frznTyCd = null;
		try 
		{
		Query extrctFrznTyCd = getEntityManager()
				.createNativeQuery(B000040QueryConstants.extrctFrznValue
						.toString());
		extrctFrznTyCd.setParameter("porCD", item.getPorCd());
		extrctFrznTyCd.setParameter("ordrTkBsMnth",ordrTkBsMonth);
		extrctFrznTyCd.setParameter("prodMnth",item.getProdMnth());
		extrctFrznTyCd.setParameter("oseiId",item.getOseiId());

		List frznTyCdList =  extrctFrznTyCd.getResultList();
		
		if(null != frznTyCdList && frznTyCdList.size() == 0){
			//TODO ::  throw to stop the batch.
			frznTyCd = "E";
		}else{
			frznTyCd =(String) frznTyCdList.get(0);
		}
		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e);
		}
		return frznTyCd;
		
	}



	public String getSalesNotesNumber(B000040OrdrDtlsOutputBean item) {
		
		String salesNtsNo = null;
		
		String prodMnth = item.getProdMnth();
		String potCd = item.getPotCd();
		
		if(null != prodMnth && null != potCd && prodMnth.length() == 6){
			
			salesNtsNo = prodMnth.substring(4,prodMnth.length()) + potCd;
		}
		return salesNtsNo;
	}
	
	public Object[] extractSrvcPrmtrDtls(String porCd,String prdFmlyCd, String byrCd, String prdnMnth, String eim, String specDestCd) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		List<Object[]> srvcPrmtrDtls = new ArrayList<Object[]>();
		Object[] srvcPrmtrObj = null;
		int lowPrty = 0;
		
    	LOG.info("POR Cd  is : "+porCd+ ", production family cd is : "+prdFmlyCd+ ", Buyer cd is : "+byrCd+", production month is : "+prdnMnth+ ", EIM is : "+eim+ ", Spec Destination Code is : "+specDestCd);

    	Query getSrvcPrmtrDtlsQry = entityManager.createNativeQuery(QueryConstants.getSrvcPrmtrDtls.toString());
    	
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PRODUCTION_FAMILY_CODE, prdFmlyCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_BUYERCD, byrCd);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PRMTR_PRD_MNTH, prdnMnth);
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.PREFIX, (null != eim) ? eim.substring(0, 7) : "");
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.SUFFIX, (null != eim) ? eim.substring(10, 18) : "");
    	getSrvcPrmtrDtlsQry.setParameter(PDConstants.SPECDESTCD, specDestCd);
		
		LOG.info("Service Parameter Details Extraction Query String is : "+ getSrvcPrmtrDtlsQry);

		srvcPrmtrDtls = getSrvcPrmtrDtlsQry.getResultList();
		
		LOG.info(PATTERN_MATCHING_RESULT_MSG + srvcPrmtrDtls.size());
		
		if(srvcPrmtrDtls == null || srvcPrmtrDtls.isEmpty())
    	{
			LOG.info("No Matching record found, hence attaching constant value as 'M' ");
			srvcPrmtrObj = new Object[]{ PDConstants.CF_CONSTANT_M, PDConstants.CF_CONSTANT_M, PDConstants.CF_CONSTANT_M };
    	} else if (srvcPrmtrDtls.size() ==1) {
    		for (Object[] result : srvcPrmtrDtls) {
    			srvcPrmtrObj = new Object[]{ result[1], result[2], result[3] };
    		}
    	}else if (srvcPrmtrDtls.size() > 1){
    		for (Object[] result : srvcPrmtrDtls) {
    			if((byrCd.length() == PDConstants.INT_SIX) && (specDestCd!= null && !specDestCd.isEmpty())){
    				result[4] = result[4].toString()+PDConstants.CONSTANT_ONE;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			} else if((byrCd.length() == B000027Constants.INT_FOUR) && (specDestCd!= null && !specDestCd.isEmpty())){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_TWO;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			} if((byrCd.length() == PDConstants.INT_SIX) && (specDestCd== null)){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_THREE;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			}else if((byrCd.length() == B000027Constants.INT_FOUR) && (specDestCd== null)){
    				result[4] = result[4].toString()+B000027Constants.CONSTANT_FOUR;
    				lowPrty = Integer.parseInt(((String) result[4]).trim());
    			}
    		}
    		
    		LOG.info(" Initial Assumed Low priority Val is : "+lowPrty);
    		
    		for (Object[] result : srvcPrmtrDtls) {
    			LOG.info(result[4].toString());
    			Integer newLowPrty = Integer.parseInt(((String) result[4])	.trim());
    			if(newLowPrty < lowPrty){
    				lowPrty = newLowPrty;
    				srvcPrmtrObj = new Object[]{ result[1], result[2], result[3] };
    			}
    		}
    		
    		LOG.info(" Actual Low priority Val is : "+lowPrty);
    	}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

		return srvcPrmtrObj;
	}
 
	public void deleteb000040OrdrdtlsTrn(){
                Query query = entityManager.createNativeQuery(B000040QueryConstants.b000040Ordrdtlsoutputbean.toString());                
                 LOG.info("***:Records got deleted*** : " + query.executeUpdate());
    }	

	public void insertb000040OrdrdtlsTrn() {
		try {
			String queryStr = B000040QueryConstants.insertTRN_WKLY_PROD_ORDER
					.toString();
			Query query = entityManager.createNativeQuery(queryStr);
			LOG.info("*** Records got Inserted : " + query.executeUpdate());
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
	}



	public void CallDeleteWEEKLY_PRODUCTION_ORDER_TRN() {		
		
		try {
			String queryStr = B000040QueryConstants.deleteTrnWklyPrdOrdr
					.toString().replaceAll(":porCD", queryInputVO.getPorCD()).replaceAll(":ordrTkBsMnth", queryInputVO.getOrdrTakBseMnth()).replaceAll(":ordrTkProdWkNo", queryInputVO.getOrdrTakBseWkNo());
			
			Query query = entityManager.createNativeQuery(queryStr);
			
			LOG.info("*** Records got deleted : " + query.executeUpdate());
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		
	}
	
	public void getWklyProdOrdrRprt()  {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		LOG.info("Order take base month  is : "+queryInputVO.getOrdrTakBseMnth()+" , POR value is " +queryInputVO.getPorCD());
		
		String ordrTkBsMnth = queryInputVO.getOrdrTakBseMnth();
		String porCd = queryInputVO.getPorCD();
		
		LOG.info("READER values --> POR Cd is   : "+porCd+ " and order take base month  is :" +ordrTkBsMnth);
		
		 String reportPath = "D:/public/";//environment.getProperty(B000040Constants.B000040_REPORT_PATH);
		 
		 List<Object[]> rcds = fetchPrdnOrdrDtlRptRcrds();
		 
		 createPrdnOrdrDtlReport(reportPath,rcds);
		 
			 if(rcds.isEmpty()) {
				 	String[] messageParams = {B000040Constants.BATCH_40_ID,ordrTkBsMnth,porCd};
	            	B000027CommonUtil.logMessage(PDMessageConsants.M00178, B000027Constants.P0012, messageParams);
			 }
		 
		 LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
	}
	
	/**
	 * @param ordrTkBsMnth
	 * @param porCd
	 * @return Monthly order detail report records
	 */
	public List<Object[]> fetchPrdnOrdrDtlRptRcrds() {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		LOG.info("Order take base month  is : "+queryInputVO.getOrdrTakBseMnth()+" , POR value is " +queryInputVO.getPorCD());
    	
    	Query ordrDtlsQry = entityManager.createNativeQuery(B000040QueryConstants.getWklyPrdnOrdrRptDtls.toString()); 
    	
    	ordrDtlsQry.setParameter(PDConstants.PRMTR_PORCD, queryInputVO.getPorCD());
    	ordrDtlsQry.setParameter(PDConstants.ORDR_TK_BS_MNTH, queryInputVO.getOrdrTakBseMnth());
		
		LOG.info("Extraction Monthly Production Order details Query String is : "+ ordrDtlsQry);
		
		List<Object[]> result = ordrDtlsQry.getResultList();
		
		LOG.info("Extracted Monthly order details report count  : " + result.size());
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		return result;
	}
	
	/**
	 * @param reportPath
	 * @param itemPrdnOrdrDtlRcrds
	 * 
	 * Creates Monthly Order Detail Report for B000027
	 */
	private void createPrdnOrdrDtlReport(String reportPath,	List<Object[]> itemPrdnOrdrDtlRcrds) {
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		 String dirPath = reportPath;
		 
		 String fileName = B000040Constants.B000040_MNTHLY_ORDR_DTL_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
		 
		 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
		 
		 File dir = new File(dirPath);
		    if(!dir.exists()) {
		        dir.mkdir();
		    }
		    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
		    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
		    
		    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_ORDR_TK_BS_PRD, REPORT_HEADER_OCF_REGION, B000027Constants.REPORT_HEADER_MC_REGION,
		    		REPORT_HEADER_PRD_MNTH,B000027Constants.REPORT_HEADER_BYR_GRP_CD,
		    		REPORT_HEADER_BYR_CD,REPORT_HEADER_PRDN_FMLY_CD,REPORT_HEADER_CAR_GRP,REPORT_HEADER_CAR_SRS,
		    		B000027Constants.REPORT_HEADER_SPEC_DEST_CD,B000027Constants.REPORT_HEADER_END_ITEM_MDL, B000027Constants.REPORT_HEADER_EX_NO, 
		    		B000027Constants.REPORT_HEADER_ADD_SPEC_CD,REPORT_HEADER_POT,B000027Constants.REPORT_HEADER_SLS_NOTE_NO, 
		    		B000027Constants.REPORT_HEADER_COLOR,B000027Constants.REPORT_HEADER_QTY,
		    		B000027Constants.REPORT_HEADER_TYR_MKR, B000027Constants.REPORT_HEADER_TYR_SRVC, B000027Constants.REPORT_HEADER_BDY_PRTN,
		    		B000027Constants.REPORT_HEADER_OPTN_SPC_CD}); 
		    
		    		 try {
		    		    	Map<String,String> formatMap = new HashMap<String,String>();
		    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
		    		    	formatMap.put(B000027Constants.CONSTANT_THREE,B000027Constants.B000027_REPORT_DATE_FORMAT);
		    		    	excelItemWriter.createReport(itemPrdnOrdrDtlRcrds,formatMap,B000027Constants.B000027_MNTHLY_ORDR_DTL_RPT_SHEET_NM); 
		    		    } catch (IOException e) {
		    		        LOG.error(EXCEPTION+e);
		    		        
		    		    }
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
	}

}
