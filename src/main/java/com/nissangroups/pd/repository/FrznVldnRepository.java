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

import static com.nissangroups.pd.util.PDConstants.FILE_ID;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADTNL_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_APPLD_MDL_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_EXT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FRZN_TYPE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_INT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PCK_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SPEC_DEST_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SEQ_NO;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.QueryConstants;

public class FrznVldnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	/** Variable order Take Base Period Mst list. */
	
	
	List<Object[]> frznTypeList = new ArrayList<Object[]>();
	

	@Autowired(required=false)
	private MnthlyOrdrTrnRepository mnthlyOrdrTrnRepositoryObj ;
	
	/**
	 * 
	 * @param porCd
	 * @param fileId
	 * @param tableName
	 * @return - List of Frozen Orders Object present in the MOnthly Order Interface TRN
	 * by comparing the orders with master tables and Osei Frozen MST Table
	 * @throws PdApplicationNonFatalException
	 * 
	 * 
	 */

	public List<Object[]> fetchFrznTypeForEI(String porCd,String fileId,String tableName,String seqNo)
			throws PdApplicationException {

		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchFrznTypFrOseiId);
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdJoin);
		dynamicQuery.append(QueryConstants.fetchFrznTypForOseiIdJoin);
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdCnd);
		
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
	
		frznTypeList = executeQuery(queryStr,fileId,porCd,seqNo);

		return frznTypeList;
	}
	

	/**
	 * 
	 * @param input
	 * @param frznType - holds the frozen type cd.
	 * @param grpByCnd  -true/false - whether group by function is required or not 
	 * @param frznOrdrErrRprt - flag to fetch the individual frozen order error records details(buyer cd,additional spec cd,ordrqty) 
	 * @return - query to summarize the order qty for each frozen type from MOnthly Order Interface TRN 
	 * It is a common method to build the query based on the different key combination for each frozen type
	 * Frozen Type - F (POR,Car Series ,End Item,Spec Destination cd,Buyer Group cd,Exterior Color cd,Interior Color Cd)
	 * Frozen Type - E (POR,Car Series ,End Item,Spec Destination cd,Buyer Group cd,Exterior Color cd)
	 * Frozen Type - U (POR,Car Series ,End Item,Spec Destination cd,Buyer Group cd,Interior Color Cd)
	 * Frozen Type - D (POR,Car Series ,End Item,Spec Destination cd,Buyer Group cd)
	 * Frozen Type - P (POR,Car Series ,End Item,Buyer Group cd)
	 * 
	 * 
	 */
	public String buildFrznSummrzdOrdrQtyQuery(InputBean input,String frznType,String grpByCnd,String frznOrdrErrRprt) throws PdApplicationException{
		
		StringBuilder dynamicQuery = new StringBuilder();
		
		if(grpByCnd.equalsIgnoreCase(PDConstants.TRUE)){
			dynamicQuery.append(QueryConstants.fetchSmmrzdOrdrQtyPart1);	
		} else {
			dynamicQuery.append(QueryConstants.fetchPotOseiId);
		}
		
		
		
		
		dynamicQuery.append(QueryConstants.fetchSmmrzdOrdrQtyPart2);
		if(frznOrdrErrRprt.equalsIgnoreCase(PDConstants.TRUE)){
			dynamicQuery.append(QueryConstants.fetchFrznOrdrErrDtls);
			
		}
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE) || frznOrdrErrRprt.equalsIgnoreCase(PDConstants.TRUE) ){
			dynamicQuery.append(QueryConstants.fetchExtClrCd);
		}
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE) || frznOrdrErrRprt.equalsIgnoreCase(PDConstants.TRUE) ){
			dynamicQuery.append(QueryConstants.fetchIntClrCd);
		}
		if(!(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE)) || frznOrdrErrRprt.equalsIgnoreCase(PDConstants.TRUE) ){
			dynamicQuery.append(QueryConstants.fetchSpecDestCd);
		}
		dynamicQuery.append(QueryConstants.fetchSmmrzdOrdrQtyPart3);
		
		
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdJoin);
		
		if(frznOrdrErrRprt.equalsIgnoreCase(PDConstants.TRUE)){
			dynamicQuery.append(QueryConstants.fetchOrdrMsQtyForOseiIdJoin);
		}
		//dynamicQuery.append(QueryConstants.fetchOrdrMsQtyForOseiIdJoin);
		
		
		dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdCnd);
		dynamicQuery.append(QueryConstants.frznTypCdCnd);
		
		if(grpByCnd.equalsIgnoreCase(PDConstants.FALSE)){
			dynamicQuery.append(QueryConstants.generalCnd);
			
			if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE)){
				dynamicQuery.append(QueryConstants.extClrCdCnd);
			}
			if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE)){
				dynamicQuery.append(QueryConstants.intClrCdCnd);
			}
			if(!(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE))){
				dynamicQuery.append(QueryConstants.specDestCdCnd);
			}
		}	
		
		
		if(grpByCnd.equalsIgnoreCase(PDConstants.TRUE)){
			dynamicQuery.append(QueryConstants.fetchSmmrzdOrdrQtyGrpBy);
			
			if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE)){
				dynamicQuery.append(QueryConstants.grpByExtClrCd);
			}
			if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE)){
				dynamicQuery.append(QueryConstants.grpByIntClrCd);
			}
			if(!(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE))){
				dynamicQuery.append(QueryConstants.grpBySpecDestCd);
			}
		}	
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		return queryStr;
	
	}
	/**
	 * 
	 * @param input
	 * @param frznType - holds the frozen type cd.
	 * @param grpByCnd  -true/false - whether group by function is required or not 
	 * @param frznOrdrErrRprt  -true/false - records for writting in the frozen order error report
	 * @param obj - null if grpByCnd is true or else fetch only the obj records from Monthly Order Interface TRN 
	 * @return - List of the summarized order details for each frozen type
	 * @throws PdApplicationNonFatalException
	 */
	public List<Object[]> fetchFrznSummrzdOrdrQty(InputBean input,String frznType,String grpByCnd,Object[] obj,String frznOrdrErrRprt)
			throws PdApplicationException {

		
		String queryStr = buildFrznSummrzdOrdrQtyQuery(input, frznType, grpByCnd, frznOrdrErrRprt);
		Query query = eMgr.createNativeQuery(queryStr);
		
		query.setParameter(PRMTR_FRZN_TYPE, frznType);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		
		if(grpByCnd.equalsIgnoreCase(PDConstants.FALSE) && obj != null){
			setParamsSummrzdOrdrQtyFrznDtls(query,input,obj,frznType);
		}
		
		List<Object[]> frznDataList = query.getResultList();
		
		return frznDataList;
	}
	
		
	
	/**
	 * 
	 * @param query
	 * @param input
	 * @param obj - 
	 * @param frznType
	 * Set the Parameters for the query from the object and input based on the frozen type
	 * Parameters will be dynamically changed based on each frozen type
	 * 
	 */
	public void setParamsSummrzdOrdrQtyFrznDtls(Query query,InputBean input,Object[] obj,String frznType) {
		int i = 2;
		
		
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, obj[i++].toString());
		query.setParameter(PRMTR_PRD_MNTH,obj[i++].toString());
		query.setParameter(PRMTR_FRZN_TYPE, obj[i++].toString());
		query.setParameter(PRMTR_CAR_SRS, obj[i++].toString());
		query.setParameter(PRMTR_APPLD_MDL_CD, obj[i++].toString());
		query.setParameter(PRMTR_PCK_CD, obj[i++].toString());
		query.setParameter(PRMTR_BYR_GRP_CD,  obj[i++].toString());
		
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE)){
			query.setParameter(PRMTR_EXT_CLR_CD, obj[i++].toString());
		}
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE)){
			query.setParameter(PRMTR_INT_CLR_CD, obj[i++].toString());	
		}
		if(!(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE))){
			query.setParameter(PRMTR_SPEC_DEST_CD, obj[i++].toString());
		}
	
	}


	/**
	 * 
	 * @param fullFrznRcrds
	 * @param errCd
	 * @param input
	 * @param frznType
	 * Perform the Frozen Validation for each Frozen Type Cd
	 * @throws PdApplicationException
	 */
	public void doFrznVldn(List<Object[]> frznRcrds,String errCd,InputBean input,String frznType)
			throws PdApplicationException {

			Query query = mnthlyOrdrTrnRepositoryObj.buildSummrzdMsFrznQuery(input,frznType,PDConstants.TRUE,PDConstants.FALSE);
			if(frznRcrds!=null && !(frznRcrds.isEmpty())) {
				for(Object[] obj:frznRcrds){
					
					int summrzdOrdrQty = Integer.parseInt(obj[0].toString());
					
					List<Object[]> summrzdMsQtyDtls = mnthlyOrdrTrnRepositoryObj.fetchSummrzdMsQtyFrznDtls(query,input,obj,frznType,PDConstants.TRUE);
					
						if(summrzdMsQtyDtls!=null && !(summrzdMsQtyDtls.isEmpty())){
							Object[] summrdMsQtyDtl = summrzdMsQtyDtls.get(0);
							int summrzdMsQty = Integer.parseInt(summrdMsQtyDtl[0].toString());
							
							if(summrzdOrdrQty!=summrzdMsQty){
								//For Each Record - Update the Error CD
								
								updateFrznOrdrErrorRcrds(obj,summrzdMsQty,input, frznType);
							} else {
								mnthlyOrdrTrnRepositoryObj.processFrznRcrds(obj,frznType,input,summrdMsQtyDtl);	
							}
							
						} else {
							int summrzdMsQty = 0;
							if(summrzdOrdrQty!=summrzdMsQty){
								//For Each Record - Update the Error CD
								updateFrznOrdrErrorRcrds(obj,summrzdMsQty,input, frznType);
							}
						}
						
							
					}
					
				}
			
		}
		
	/**
	 * 
	 * @param fullFrznRcrds
	 * @param errCd
	 * @param input
	 * @param frznType
	 * Update the Frozen Order Error Cd and Ms Qty Details in Monthly Order 
	 * @throws PdApplicationNonFatalException
	 */
	public void updateFrznOrdrErrorRcrds(Object[] frznRcrds,int sumMsQty,InputBean input,String frznType )
			throws PdApplicationException {
		
		Object[] obj = populateToArr(frznRcrds,sumMsQty,input,frznType,PDConstants.TRUE,null);
		input.getFrznOrdrErrRcrds().add(obj);
		
		List<Object[]> fullFrznRcrds =  fetchFrznSummrzdOrdrQty(input,frznType,PDConstants.FALSE,frznRcrds,PDConstants.TRUE);
		if(fullFrznRcrds!=null && !(fullFrznRcrds.isEmpty())){
			for(Object[] mnthlyOrdrIfTrn : fullFrznRcrds){
				obj = populateToArr(mnthlyOrdrIfTrn,0,input,frznType,PDConstants.FALSE,PDConstants.FALSE);
				input.getFrznOrdrErrRcrds().add(obj);
			}
		}
		
		
		Query query = mnthlyOrdrTrnRepositoryObj.buildSummrzdMsFrznQuery(input,frznType,PDConstants.FALSE,PDConstants.TRUE);
		List<Object[]> mnthlyOrdrTrnDtls = mnthlyOrdrTrnRepositoryObj.fetchSummrzdMsQtyFrznDtls(query,input,frznRcrds,frznType,PDConstants.FALSE);
		if(mnthlyOrdrTrnDtls!=null && !(mnthlyOrdrTrnDtls.isEmpty())){
			for(Object[] mnthlyOrdrTrn : mnthlyOrdrTrnDtls){
				mnthlyOrdrTrn[0] = "0";
				obj = populateToArr(mnthlyOrdrTrn,0,input,frznType,PDConstants.FALSE,PDConstants.TRUE);
				input.getFrznOrdrErrRcrds().add(obj);
				
			}
		}
		
		
	}
	
	
	public Object[] populateToArr(Object[] frznRcrds,int sumMsQty,InputBean input,String frznType,String sumrzdRcrd,String oldRcrd) throws PdApplicationException{
		 
		int i = 0;
		String sumOrdrQty,oseiId="";
		//[230, 07, 201511  , 201512  , F,  T325, ABCDEFGHIJK01, WRGL1, 1NIT01, AX7, E , ARB ]
		String extClrCd = "-ALL-";
		String intClrCd = "-ALL-";
		String specDestCd = "-ALL-";
		String byrCd = "-ALL-";
		String potCd = "-ALL-";
		String addSpecCd = "-ALL-";
		if(sumrzdRcrd.equalsIgnoreCase(PDConstants.TRUE)){
			sumOrdrQty = frznRcrds[i++].toString();
		} else {
			sumOrdrQty = frznRcrds[i++].toString();
			potCd = frznRcrds[i++].toString();
			oseiId = frznRcrds[i++].toString();
		}
		String porCd = frznRcrds[i++].toString();
		String ordrTkBsMnth = frznRcrds[i++].toString();
		String prdMnth = CommonUtil.convertObjectToString(frznRcrds[i++]);
		String frznTypeCd = frznRcrds[i++].toString();
		String carSrs = frznRcrds[i++].toString();
		String appldMdl = frznRcrds[i++].toString();
		String pckCd = frznRcrds[i++].toString();
		String byrGrpCd = frznRcrds[i++].toString();
		String msQtyStr = "";
		if(sumrzdRcrd.equalsIgnoreCase(PDConstants.FALSE)){
			byrCd = frznRcrds[i++].toString();
			addSpecCd = frznRcrds[i++].toString();
			
			msQtyStr = CommonUtil.convertObjectToString(frznRcrds[i++]);
			if(!msQtyStr.equalsIgnoreCase("")){
				sumMsQty = Integer.parseInt(msQtyStr);
			} else {
				sumMsQty = 0;
			}
			
			
		}
		
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE) || sumrzdRcrd.equalsIgnoreCase(PDConstants.FALSE)){
			 extClrCd = frznRcrds[i++].toString();
		}
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE) || frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE) || sumrzdRcrd.equalsIgnoreCase(PDConstants.FALSE)){
			 intClrCd = frznRcrds[i++].toString();	
		}
		if(!(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE)) || sumrzdRcrd.equalsIgnoreCase(PDConstants.FALSE)){
			 specDestCd = frznRcrds[i++].toString();
		}
		 
	
		String message ="";
		if(frznType.equalsIgnoreCase(PDConstants.FULL_FRZN_TYPE)){
			frznType = frznType + ": FULL";
			 message = PDMessageConsants.M00065;
		}
		if(frznType.equalsIgnoreCase(PDConstants.PARTIAL_FRZN_TYPE)){
			frznType = frznType + ": PARTIAL";
			message = PDMessageConsants.M00067.replaceAll("&1", "E/I");
		
		}
		if(frznType.equalsIgnoreCase(PDConstants.EXT_CLR_FRZN_TYPE)){
			frznType = frznType + ": EXTERIOR COLOR";
			message = PDMessageConsants.M00067.replaceAll("&1", "E/I, Exterior Color, Spec Destination");
			 
		}
		if(frznType.equalsIgnoreCase(PDConstants.SPEC_DEST_FRZN_TYPE)){
			frznType = frznType + ": SPEC DEST";
			message = PDMessageConsants.M00067.replaceAll("&1", "E/I, Spec Destination");
			 
		}
		if(frznType.equalsIgnoreCase(PDConstants.INT_CLR_FRZN_TYPE)){
			frznType = frznType + ": INTERIOR COLOR";
			message = PDMessageConsants.M00067.replaceAll("&1", "E/I, Interior Color, Spec Destination");
			
		}
		

		int ordrQty = Integer.parseInt(sumOrdrQty);
		int diff = sumMsQty - ordrQty ;
		if(sumrzdRcrd.equalsIgnoreCase(PDConstants.FALSE)){
			
			if(oldRcrd.equalsIgnoreCase(PDConstants.TRUE)){
				Object[] mnthlyOrdrTrn = {msQtyStr,potCd,oseiId,input.getPorCd(),ordrTkBsMnth,prdMnth,msQtyStr};
				mnthlyOrdrTrnRepositoryObj.merge(mnthlyOrdrTrn, input, null,PDConstants.TRUE);
			} else {
				
				Object[] mnthlyOrdrTrn = {msQtyStr,potCd,oseiId,input.getPorCd(),ordrTkBsMnth,prdMnth,msQtyStr};
				mnthlyOrdrTrnRepositoryObj.merge(mnthlyOrdrTrn, input,null,PDConstants.TRUE);
			}
			
		}
		
		Object[] obj ={porCd,ordrTkBsMnth,prdMnth,carSrs,byrGrpCd,byrCd,specDestCd,appldMdl,pckCd,extClrCd,intClrCd,addSpecCd,potCd,frznType,String.valueOf(sumOrdrQty),sumMsQty,String.valueOf(diff),message};
		
		return obj;
	}
			
	
	/**
	 * 
	 * @param queryString
	 * @param fileId
	 * @param porCd
	 * @param frznTypeCd
	 * Common Method to execute the query
	 * @return
	 */

	public List<Object[]> executeQuery(String queryString,String fileId,String porCd,String seqNo){
		
		
		Query query;
		
		query = eMgr.createNativeQuery(queryString);
		
		query.setParameter(FILE_ID, fileId);
		query.setParameter(PRMTR_SEQ_NO, seqNo);
		
		query.setParameter(PRMTR_PORCD, porCd);
		
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
