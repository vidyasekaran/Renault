package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.FILE_ID;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ABOLISH_DATE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ABOLISH_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ABOLISH_PRD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADOPT_DATE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADOPT_PRD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ADTNL_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_APPLD_MDL_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_DATA_SKIPPED;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ERROR_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.PRMTR_FRZN_TYPE;
import static com.nissangroups.pd.util.PDConstants.PRMTR_HORIZON;
import static com.nissangroups.pd.util.PDConstants.PRMTR_NSC_EIM_ODER_HRZN_FLAG;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFBYRGRPCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFRGNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OEI_BYR_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OSEI_ID;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PCK_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_POT_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ROW_NO;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SEQ_NO;
import static com.nissangroups.pd.util.PDConstants.PRMTR_SPEC_DEST_CD;
import static com.nissangroups.pd.util.PDConstants.PROCESSED;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ABOLISH_DATE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ADD_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ADOPT_DATE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_APPlD_MDL;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_DATA_SKIPPED;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_DIFFERENCE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_DUE_DATE_FRM;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_DUE_DATE_TO;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_EXT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_FRZN_TYPE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_INT_CLR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_MS_QTY;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_QTY;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PCK_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POR;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_QTY;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_SPEC_DEST;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;
import static com.nissangroups.pd.util.PDConstants.SKIPPED;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.MstOseiDtl;
import com.nissangroups.pd.model.TrnMnthlyOrdrErrIf;
import com.nissangroups.pd.model.TrnMnthlyOrdrErrIfPK;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;

public class MnthlyOrdrIfTrnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	private List<Object[]> itemChkErrRcrds = new ArrayList<Object[]>();
	
	@Autowired(required=false)
	private WeekNoCalendarRepository wkNoCalendarRepositoryObj ;
	
	
	private static final Log LOG = LogFactory.getLog
			(MnthlyOrdrIfTrnRepository.class);


	/**
	 * 
	 * @param porCd
	 * @param prdOrdrStgCd
	 * @return - List of Row No's having POR CD present in  TRN MONTHLY ORDER INTERFACE and not present in POR MST
	 * @throws PdApplicationNonFatalException
	 */
	public void updateErrorCd(List<Integer> rowNo,String errorCd,String errorMessage, InputBean input)
			throws PdApplicationException {

		if(rowNo!=null && !(rowNo.isEmpty())) {
			StringBuilder dynamicQuery = new StringBuilder();
			
			dynamicQuery.append(QueryConstants.updateErrorCd);
			if(errorCd.equalsIgnoreCase("01")) {
				dynamicQuery.append(QueryConstants.updateInvldPorCdCnd);
			} else {
				dynamicQuery.append(QueryConstants.updateCnd);
			}
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
			query.setParameter(PRMTR_ERROR_CD, errorCd);
			query.setParameter(FILE_ID, input.getFileId());
			query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
			query.setParameter(PRMTR_ERROR_MSG, errorMessage);
			query.setParameter(PRMTR_DATA_SKIPPED, SKIPPED);
			if(!(errorCd.equalsIgnoreCase("01"))) {
				query.setParameter(PRMTR_PORCD, input.getPorCd());
			}
			
			int count = 10;
			List rowList = new ArrayList<>();
			int i =0;
			for(int j = 0 ; j < rowNo.size();j++){
				if(i<count){
					rowList.add(rowNo.get(j));
					i++;
				} else {
					i = 0;
					
					query.setParameter("rowNos", rowList);
					query.executeUpdate();
					rowList = new ArrayList<>();
				}
				
			}
			if(rowList!=null && !(rowList.isEmpty())){
				query.setParameter("rowNos", rowList);
				query.executeUpdate();
			}
		
			
		}

		
	}
	
	
	public void insrtIntoDevMnthlyOrdrIf(InputBean input)
			throws PdApplicationException {

		
			StringBuilder dynamicQuery = new StringBuilder();
			
			dynamicQuery.append(QueryConstants.insertIntoDevMnthlyOrdrIf);
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
			
			query.setParameter(FILE_ID, input.getFileId());
			query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
			query.setParameter(PRMTR_PORCD, input.getPorCd());
			
			query.executeUpdate();
		}

		
	

	
	public void updateErrorCd(String errorCd,String errorMessage,InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth)
			throws PdApplicationException {

		
			
			StringBuilder dynamicQuery = new StringBuilder(); 
			
			dynamicQuery.append(QueryConstants.updateErrorCdByDtls);
			
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
			query.setParameter(PRMTR_ERROR_CD, errorCd);
			query.setParameter(PRMTR_ERROR_MSG, errorMessage);
			query.setParameter(PRMTR_DATA_SKIPPED, SKIPPED);
			
			query.setParameter(PRMTR_PORCD, input.getPorCd());
			query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PRMTR_BYR_GRP_CD, byrGrpCd);
			query.setParameter(PRMTR_CAR_SRS, carSrs);
			query.setParameter(FILE_ID, input.getFileId());
			query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
				
		
			query.executeUpdate();
		}
		
	
	public void updateDueDate(List<Object[]> diffDueDateRcrds,String errorCd,InputBean input,String errorMessage)
			throws PdApplicationNonFatalException {
		
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		
		dynamicQuery.append(QueryConstants.updateDueDateErrorCd);
		dynamicQuery.append(QueryConstants.updateCndAll);
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		if(diffDueDateRcrds!=null && !(diffDueDateRcrds.isEmpty())) {
			
			for(Object[] obj:diffDueDateRcrds){
				
				//split list in to 10 size lists
				query.setParameter(PRMTR_ERROR_MSG, errorMessage);
				query.setParameter(PRMTR_DATA_SKIPPED, PROCESSED);
				query.setParameter(PRMTR_ERROR_CD, errorCd);
				query.setParameter(FILE_ID, input.getFileId());
				query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
				query.setParameter(PRMTR_PORCD, input.getPorCd());
				query.setParameter(PRMTR_POT_CD, obj[1].toString());
				query.setParameter(PRMTR_APPLD_MDL_CD, obj[2].toString());
				query.setParameter(PRMTR_PCK_CD, obj[3].toString());
				query.setParameter(PRMTR_CAR_SRS, obj[4].toString());
				query.setParameter(PRMTR_ADTNL_SPEC_CD, obj[5].toString());
				query.setParameter(PRMTR_SPEC_DEST_CD, obj[6].toString());
				query.setParameter(PRMTR_BYR_CD, obj[7].toString());
				query.setParameter(PRMTR_ORDR_TK_BS_MNTH, obj[8].toString());
				query.setParameter(PRMTR_PRD_MNTH, obj[9].toString());
				query.executeUpdate();				
			}
			
		
		}

		
	}


	
	public void updateDueDate(String errorCd,String errorMessage,List<Integer> rowNo,InputBean input)
			throws PdApplicationNonFatalException {

		if(rowNo!=null && !(rowNo.isEmpty())) {
			
			StringBuilder dynamicQuery = new StringBuilder(); 
			
			dynamicQuery.append(QueryConstants.updateDueDateErrorCd);
			dynamicQuery.append(QueryConstants.updateCnd);
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
			query.setParameter(PRMTR_ERROR_CD, errorCd);
			query.setParameter(PRMTR_ERROR_MSG, errorMessage);
			query.setParameter(PRMTR_DATA_SKIPPED, PROCESSED);
			
			query.setParameter(FILE_ID, input.getFileId());
			query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
			query.setParameter(PRMTR_PORCD, input.getPorCd());
			int i =0;
			int count = 10;
			List rowList = new ArrayList<>();
			
			for(int j = 0 ; j < rowNo.size();j++){
				if(i<count){
					rowList.add(rowNo.get(j));
					i++;
				} else {
					i = 0;
					
					query.setParameter("rowNos", rowList);
					query.executeUpdate();
					rowList = new ArrayList<>();
				}
				
			}
			if(rowList!=null && !(rowList.isEmpty())){
				query.setParameter("rowNos", rowList);
				query.executeUpdate();
			}		
		}

		
	}
	
	
	public void updateFrznType(List<Object[]> frznTypeList,InputBean input)
			throws PdApplicationNonFatalException {

			StringBuilder dynamicQuery = new StringBuilder();
			dynamicQuery.append(QueryConstants.updateFrznType);
			dynamicQuery.append(QueryConstants.updateCndbyRowNo);
		
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
		
			if(frznTypeList!=null && !(frznTypeList.isEmpty())) {
				for(Object[] obj:frznTypeList){
			
					query.setParameter(FILE_ID, input.getFileId());
					query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
					query.setParameter(PRMTR_PORCD, input.getPorCd());
					query.setParameter("rowNo", obj[0].toString());
					query.setParameter(PRMTR_OSEI_ID, obj[1].toString());
					query.setParameter(PRMTR_FRZN_TYPE, obj[2].toString());
					query.setParameter(PRMTR_OEI_BYR_ID, obj[3].toString());
					query.executeUpdate();
				}
			
			}
		
			
	}
	
	

	public void updateOseiId(List<Object[]> oseiDtlsList,InputBean input)
			throws PdApplicationNonFatalException {

			StringBuilder dynamicQuery = new StringBuilder();
			dynamicQuery.append(QueryConstants.updateOseiId);
			dynamicQuery.append(QueryConstants.updateCndbyRowNo);
		
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			//split list in to 10 size lists
			Query query = eMgr.createNativeQuery(queryStr.toString());
		
			if(oseiDtlsList!=null && !(oseiDtlsList.isEmpty())) {
				for(Object[] obj:oseiDtlsList){
			
					query.setParameter(FILE_ID, input.getFileId());
					query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
					query.setParameter(PRMTR_PORCD, input.getPorCd());
					query.setParameter(PRMTR_ROW_NO, obj[0].toString());
					query.setParameter(PRMTR_OSEI_ID, obj[1].toString());
					query.setParameter(PRMTR_OEI_BYR_ID, obj[2].toString());
					query.executeUpdate();
				}
			
			}
		
			
	}

	
	public List<Object[]> fetchDueDateNullRcrds(InputBean input)
			throws PdApplicationNonFatalException {

		
		StringBuilder dynamicQuery = new StringBuilder();
		
		
		
		//dynamicQuery.append(QueryConstants.insrtManuelDueDatePrmtr);
		
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		Query query = eMgr.createNativeQuery(queryStr);
		
		List<Object[]> result = query.getResultList();
		
		return result;
		
	}
	
	
	
public void generateFrznOrdrErrReport(InputBean input,String reportPath){
		
		
		createFrznChkErrReport(reportPath, input.getFrznOrdrErrRcrds(), input.getPorCd(),input);		
	}


public void createDataFrMnthlyOrdrErrIf(InputBean input,List<String> errorCdList){
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	dynamicQuery.append(QueryConstants.fetchDistinctByrGrpCdAndCarSrs);
	
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());

	List<Object[]> result = query.getResultList();
	
	
			
	
}



public void deletePrcssedFile(InputBean input){
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	dynamicQuery.append(QueryConstants.deleteProccessedFile);
	
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());

	query.executeUpdate();
	
}


public void updateByrGrpCd(InputBean input,String byrCd){
	
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	List<Object[]> byrGrpCdList =  fetchByrGrpCdForByrCdAndPorCd(input, byrCd);
	
	dynamicQuery.append(QueryConstants.updateByrGrpCd);
	
	String queryStr = dynamicQuery.toString();
	
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	Query query = eMgr.createNativeQuery(queryStr);
	
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());
	if(byrGrpCdList!=null && !(byrGrpCdList.isEmpty())){
		Object[] byrGrp = byrGrpCdList.get(0);
		query.setParameter(PRMTR_BYR_CD, byrCd);
		query.setParameter(PRMTR_BYR_GRP_CD,byrGrp[0]);
		query.setParameter(PRMTR_NSC_EIM_ODER_HRZN_FLAG,byrGrp[1]);
		query.setParameter(PRMTR_OCFRGNCD,byrGrp[2]);
		query.setParameter(PRMTR_OCFBYRGRPCD,byrGrp[3]);
		
		query.executeUpdate();
	}
	
	
	
}


public List<Object[]> fetchDistinctByrGrpCarSrs(String porCd,String fileId,String tableName,String seqNo)
		throws PdApplicationNonFatalException {

	
	StringBuilder dynamicQuery = new StringBuilder();
	dynamicQuery.append(QueryConstants.fetchDistinctByrGrpCdAndCarSrs);
		
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, tableName);
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, fileId);
	query.setParameter(PRMTR_SEQ_NO, seqNo);
	query.setParameter(PRMTR_PORCD, porCd);

	List<Object[]> distinctByrGrpCarSrsList = query.getResultList();

	
	return distinctByrGrpCarSrsList;
}





public List<String> fetchDistinctByrCd(InputBean input){
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	dynamicQuery.append(QueryConstants.fetchDistinctByrCd);
	
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());
	
	List<String> result = query.getResultList();
	
	return result;
	
}


public List<Object[]> fetchDistinctCarSrs(InputBean input){
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	dynamicQuery.append(QueryConstants.fetchDistinctCarSrs);
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());
	
	List<Object[]> result = query.getResultList();
	
	return result;
	
}


public List<Object[]> fetchByrGrpCdForByrCdAndPorCd(InputBean input,String byrCd){
	
	StringBuilder dynamicQuery = new StringBuilder();
	
	dynamicQuery.append(QueryConstants.fetchByrGrpCdForByrCdAndPorCd);
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(PRMTR_BYR_CD, byrCd);
	query.setParameter(PRMTR_PORCD, input.getPorCd());
	
	List<Object[]> result = query.getResultList();
	
	return result;
	
}

public List<Object[]> fetchOseiIdForEI(InputBean input)
		throws PdApplicationException {

	StringBuilder dynamicQuery = new StringBuilder();
	dynamicQuery.append(QueryConstants.fetchOseiIdForEI);
	dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdJoin);
	
	dynamicQuery.append(QueryConstants.fetchVldEIwithClrCdCnd);
	
	
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());

	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());
	
	List<Object[]> result = query.getResultList();

	return result;
}


public void updateInVldPrdMnth(String errorCd,String errorMessage,InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth,String prdMnth,String dateDtls,
		Object[] obj)
		throws PdApplicationException {

	
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		if(dateDtls.equalsIgnoreCase("true")){
			dynamicQuery.append(QueryConstants.updateErrorCdDtlsFrInVldPrdMnth);
		} else {
			dynamicQuery.append(QueryConstants.updateErrorCdFrInVldPrdMnth);
		}
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		Query query = eMgr.createNativeQuery(queryStr.toString());
		query.setParameter(PRMTR_ERROR_CD, errorCd);
		query.setParameter(PRMTR_ERROR_MSG, errorMessage);
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_BYR_GRP_CD, byrGrpCd);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PRD_MNTH, prdMnth);
		
		
		query.setParameter(PRMTR_DATA_SKIPPED, SKIPPED);
		if(dateDtls.equalsIgnoreCase("true")){
			////Object[] obj = {"false",nearstAdptDate,nearstAdptPrd,nearstAblshDate,nearstAblshPrd,ablshMnth};		
			query.setParameter(PRMTR_ADOPT_PRD, obj[2]);
			query.setParameter(PRMTR_ABOLISH_PRD, obj[4]);
			query.setParameter(PRMTR_ADOPT_DATE, obj[1]);
			query.setParameter(PRMTR_ABOLISH_DATE, obj[3]);
			query.setParameter(PRMTR_ABOLISH_MNTH, obj[5]);	
		
		}
		
		query.executeUpdate();
	}

public void updateHorizon(int horizon,InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth,String prdMnth)
		throws PdApplicationException {

	
		
		StringBuilder dynamicQuery = new StringBuilder(); 
		
		dynamicQuery.append(QueryConstants.updateHorizon);
		
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		Query query = eMgr.createNativeQuery(queryStr.toString());
		query.setParameter(PRMTR_HORIZON, horizon);
		query.setParameter(PRMTR_PORCD, input.getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_BYR_GRP_CD, byrGrpCd);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PRD_MNTH, prdMnth);	
	
		query.executeUpdate();
	}

public List<Object[]> fetchDistinctOseiId(InputBean input,String withPotCd)
		throws PdApplicationNonFatalException {

	
	StringBuilder dynamicQuery = new StringBuilder();
	
	if(withPotCd.equalsIgnoreCase("true")){
		dynamicQuery.append(QueryConstants.fetchDistinctOseiIdWithPotCd);
	} else {
		dynamicQuery.append(QueryConstants.fetchDistinctOseiId);
	}
		
	dynamicQuery.append(QueryConstants.fetchDistinctOseiIdCnd);
	String queryStr = dynamicQuery.toString();
	queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
	
	Query query = eMgr.createNativeQuery(queryStr);
	query.setParameter(FILE_ID, input.getFileId());
	query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
	query.setParameter(PRMTR_PORCD, input.getPorCd());

	List<Object[]> distinctOseiIdList = query.getResultList();

	
	return distinctOseiIdList;
}


	public void generateItmChkErrReport(InputBean input,String reportPath)
			throws PdApplicationException, ParseException {
	
		List<Object[]> itemChkErrRcrds = fetchItemChckErrRcrds(input);
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.insertMnthlyOrdrErrIf);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PORCD, input.getPorCd());

		query.executeUpdate();
		
		createItmChkErrReport(reportPath, itemChkErrRcrds, input.getPorCd());
		
		
		
	}

	
	public void populateMnthlyOrdrErrIf(List<Object[]> obj){
/*		REPORT_HEADER_POR, REPORT_HEADER_ORDR_TK_BS_PRD,
		REPORT_HEADER_PRD_MNTH,REPORT_HEADER_CAR_SRS,REPORT_HEADER_BYR_GRP,
		REPORT_HEADER_BYR_CD,REPORT_HEADER_SPEC_DEST,REPORT_HEADER_APPlD_MDL,
		REPORT_HEADER_PCK_CD,REPORT_HEADER_EXT_CLR_CD,REPORT_HEADER_INT_CLR_CD,
		REPORT_HEADER_ADD_SPEC_CD,REPORT_HEADER_POT,REPORT_HEADER_QTY,REPORT_HEADER_DUE_DATE_FRM,
		REPORT_HEADER_DUE_DATE_TO,REPORT_HEADER_ADOPT_DATE,REPORT_HEADER_ABOLISH_DATE,
		REPORT_HEADER_ERROR_MSG,REPORT_HEADER_DATA_SKIPPED
*/	}



/**
 * Excel based Error Report.
 *
 * @param porstr1 the porstr1
 * @param items the items
 */
public void createItmChkErrReport(String reportPath, List<Object[]> items,String porCd)  {
	
    
    
    DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    //reportPath = "D:\\Public\\B000018\\";
    String dirPath = reportPath;
    String fileName = "B000018_ITMCHKERR_"+porCd+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
    
    File dir = new File(dirPath);
    if(!dir.exists()) {
        dir.mkdir();
    }
    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
    
    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_POR, REPORT_HEADER_ORDR_TK_BS_PRD,
    		REPORT_HEADER_PRD_MNTH,REPORT_HEADER_CAR_SRS,REPORT_HEADER_BYR_GRP,
    		REPORT_HEADER_BYR_CD,REPORT_HEADER_SPEC_DEST,REPORT_HEADER_APPlD_MDL,
    		REPORT_HEADER_PCK_CD,REPORT_HEADER_EXT_CLR_CD,REPORT_HEADER_INT_CLR_CD,
    		REPORT_HEADER_ADD_SPEC_CD,REPORT_HEADER_POT,REPORT_HEADER_QTY,REPORT_HEADER_DUE_DATE_FRM,
    		REPORT_HEADER_DUE_DATE_TO,REPORT_HEADER_ADOPT_DATE,REPORT_HEADER_ABOLISH_DATE,
    		REPORT_HEADER_ERROR_MSG,REPORT_HEADER_DATA_SKIPPED});
    
    try {
    	Map<String,String> formatMap = new HashMap<String,String>();
    	formatMap.put("1","YYYY-MM");
    	formatMap.put("2","YYYY-MM");
    	excelItemWriter.createReport(items,formatMap,"Error Report");
    } catch (IOException e) {
        LOG.error(EXCEPTION+e);
        
    }
    
    LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
}



	private List<Object[]> fetchItemChckErrRcrds(InputBean input) {
		
		StringBuilder dynamicQuery = new StringBuilder();
			
		dynamicQuery.append(QueryConstants.fetchItmChkErrRcrds);
		String queryStr = dynamicQuery.toString();
		queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
		
		Query query = eMgr.createNativeQuery(queryStr);
		query.setParameter(FILE_ID, input.getFileId());
		query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
		query.setParameter(PRMTR_PORCD, input.getPorCd());

		List<Object[]> result = query.getResultList();

		
		return result;
		
	}



	
	public void createFrznChkErrReport(String reportPath, List<Object[]> items,String porCd,InputBean input)  {
		
	    
	    
	    DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	    //reportPath = "D:\\Public\\B000018\\";
	    String dirPath = reportPath;
	    String fileName = "B000018_FRZN_"+porCd+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
	    
	    File dir = new File(dirPath);
	    if(!dir.exists()) {
	        dir.mkdir();
	    }
	    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
	    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
	    
	    
	    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_POR, REPORT_HEADER_ORDR_TK_BS_PRD,
	    		REPORT_HEADER_PRD_MNTH,REPORT_HEADER_CAR_SRS,REPORT_HEADER_BYR_GRP,
	    		REPORT_HEADER_BYR_CD,REPORT_HEADER_SPEC_DEST,REPORT_HEADER_APPlD_MDL,
	    		REPORT_HEADER_PCK_CD,REPORT_HEADER_EXT_CLR_CD,REPORT_HEADER_INT_CLR_CD,
	    		REPORT_HEADER_ADD_SPEC_CD,REPORT_HEADER_POT,REPORT_HEADER_FRZN_TYPE,REPORT_HEADER_ORDR_QTY
	    		,REPORT_HEADER_MS_QTY,REPORT_HEADER_DIFFERENCE,  		REPORT_HEADER_ERROR_MSG});
	    
	    try {
	    	Map<String,String> formatMap = new HashMap<String,String>();
	    	formatMap.put("1","YYYY-MM");
	    	formatMap.put("2","YYYY-MM");
	    	excelItemWriter.createReport(items,formatMap,"Error Report");
	    } catch (IOException e) {
	        LOG.error(EXCEPTION+e);
	        
	    }
	    
	    updateFrznOrdrErrDataToMnthlyOrdrErrIf(items,input);
	    LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	public void updateFrznOrdrErrDataToMnthlyOrdrErrIf(List<Object[]> items,InputBean input){
		TrnMnthlyOrdrErrIf errIf ;
		TrnMnthlyOrdrErrIfPK errIfPk ;
		Query query = eMgr.createNativeQuery(QueryConstants.trn_mnthly_ordr_ERR_if_ROWNO);
		for(Object[] item:items){
			errIf = new TrnMnthlyOrdrErrIf();
			errIfPk  = new TrnMnthlyOrdrErrIfPK();
			errIfPk.setIfFileId(input.getFileId());
			errIfPk.setSeqNo(Long.parseLong(input.getSeqNo()));
		
			BigDecimal rowNo = (BigDecimal)query.getSingleResult();
			errIfPk.setRowNo(rowNo.longValue());
			errIf.setId(errIfPk);
			int i = 0;
			errIf.setPor(CommonUtil.convertObjectToString(item[i++]));
			errIf.setOrdrtkPrd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setProdPrd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setCarSrs(CommonUtil.convertObjectToString(item[i++]));
			errIf.setBuyerGrpCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setBuyerCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setSpecDestnCd(CommonUtil.convertObjectToString(item[i++]).trim());
			errIf.setAppldMdlCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setPackCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setExtClrCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setIntClrCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setAddSpecCd(CommonUtil.convertObjectToString(item[i++]));
					    
			errIf.setPotCd(CommonUtil.convertObjectToString(item[i++]));
			//skip frzn type 
			i++;
			String qty = CommonUtil.convertObjectToString(item[i++]);
			errIf.setQty(qty);
			String msQty = CommonUtil.convertObjectToString(item[i++]);
			errIf.setExpctdQty(msQty);
			String variance = CommonUtil.convertObjectToString(item[i++]);
			int var = Math.abs(Integer.parseInt(variance));
			
			errIf.setVariance(String.valueOf(var));
			String sign ="+";
			int ordrQty = Integer.parseInt(qty);
			int msVal = Integer.parseInt(msQty);
			if(ordrQty > msVal){
				sign ="-";
			}
			errIf.setOrdrSign(sign);
			errIf.setErrMsg(CommonUtil.convertObjectToString(item[i++]));
			//errIf.setErrCd(errCd);
			if(!(errIf.getAddSpecCd().equalsIgnoreCase("-ALL-"))){
				eMgr.merge(errIf);
			}
			
			
		}
		
	}

	
	public void updateOcfBrchErrDataToMnthlyOrdrErrIf(List<Object[]> items,InputBean input){
		TrnMnthlyOrdrErrIf errIf ;
		TrnMnthlyOrdrErrIfPK errIfPk ;
		Query query = eMgr.createNativeQuery(QueryConstants.trn_mnthly_ordr_ERR_if_ROWNO);
		
		
		for(Object[] item:items){
			errIf = new TrnMnthlyOrdrErrIf();
			errIfPk  = new TrnMnthlyOrdrErrIfPK();
			errIfPk.setIfFileId(input.getFileId());
			errIfPk.setSeqNo(Long.parseLong(input.getSeqNo()));
		
			BigDecimal rowNo = (BigDecimal)query.getSingleResult();
			errIfPk.setRowNo(rowNo.longValue());
			errIf.setId(errIfPk);
			int i = 0;
			errIf.setPor(CommonUtil.convertObjectToString(item[i++]));
			errIf.setOrdrtkPrd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setProdPrd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setCarSrs(CommonUtil.convertObjectToString(item[i++]));
			errIf.setBuyerGrpCd(CommonUtil.convertObjectToString(item[i++]));
			//skip ocf frame cd
			i++;
			errIf.setOcfFeatCd(CommonUtil.convertObjectToString(item[i++]));
			errIf.setOcfShrtDesc(CommonUtil.convertObjectToString(item[i++]).trim());
			errIf.setOcfDesc(CommonUtil.convertObjectToString(item[i++]));
			
			
			String lmt = CommonUtil.convertObjectToString(item[i++]);
			errIf.setOcfLmt(lmt);
			String usg = CommonUtil.convertObjectToString(item[i++]);
			errIf.setOcfUsg(usg);
			
			String diff = CommonUtil.convertObjectToString(item[i++]);
			int var = Math.abs(Integer.parseInt(diff));
			
			errIf.setDiff(String.valueOf(var));
			String sign ="+";
			int lmtQty = Integer.parseInt(lmt);
			int usgQty = Integer.parseInt(usg);
			if(lmtQty > usgQty){
				sign ="-";
			}
			errIf.setOcfSign(sign);
			errIf.setErrMsg(CommonUtil.convertObjectToString(item[i++]));
			//errIf.setErrCd(errCd);
			
			eMgr.merge(errIf);
			
			
			
		}
		
	}

	
	public Object[] fetchNearestAbolishAdoptDates(InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth,String prdMnth,String oseiId,String carSrsAdptDate,String carSrsAblshDate)
			throws PdApplicationNonFatalException, ParseException {

		prdMnth = prdMnth+"11";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchAdoptAbolishDatesByOseiId);

		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr,MstOseiDtl.class);
		
		String nearstAdptPrd="",nearstAblshPrd="";
	
		query.setParameter(PRMTR_OSEI_ID, oseiId);
		List<MstOseiDtl> distinctOseiDtl = query.getResultList();
		int flag = 0;
		/*
		Adopt Date				YYYYMMDD
		Adopt Period			YYYYMMWD
		Abolish Date			YYYYMMDD
		Abolish Period				YYYYMMWD
		Abolish Month				YYYYMM
		*/
		Date prdMnthDate = CommonUtil.convertStringToDate(prdMnth);
		long min = 1000000000;
		for(MstOseiDtl oseiDtl :distinctOseiDtl){
			
			if(prdMnth.compareTo(oseiDtl.getId().getOseiAdptDate()) >= 0 
					&& prdMnth.compareTo(oseiDtl.getId().getOseiAblshDate()) <= 0 ) {
				flag = 1;
				break;
			} else {
				if(nearstAdptPrd.equalsIgnoreCase("")){
					nearstAdptPrd = oseiDtl.getId().getOseiAdptDate();
				}
				if(nearstAblshPrd.equalsIgnoreCase("")){
					nearstAblshPrd = oseiDtl.getId().getOseiAblshDate();
				}
				Date oseiAdptPrdDate = CommonUtil.convertStringToDate(oseiDtl.getId().getOseiAdptDate());
				Date oseiAblshPrdDate = CommonUtil.convertStringToDate(oseiDtl.getId().getOseiAblshDate());
				Date nearstAdptPrdDate = CommonUtil.convertStringToDate(nearstAdptPrd);
				Date nearstAblshPrdDate = CommonUtil.convertStringToDate(nearstAblshPrd);
				
				long diffInOseiAdptDate = prdMnthDate.getTime() - oseiAdptPrdDate.getTime();
				//long diffInNearestAdptDate = prdMnthDate.getTime() - nearstAdptPrdDate.getTime();
				long diffInOseiAblshDate = oseiAblshPrdDate.getTime() - prdMnthDate.getTime();
				//long diffInNearestAblshDate = nearstAblshPrdDate.getTime() - prdMnthDate.getTime();
				
				if(diffInOseiAdptDate < min || diffInOseiAblshDate < min){
					nearstAdptPrdDate = oseiAdptPrdDate;
					nearstAblshPrdDate = oseiAblshPrdDate;
					if(diffInOseiAdptDate < diffInOseiAblshDate){
						min = diffInOseiAdptDate;
					} else {
						min = diffInOseiAblshDate;
					}
				}
				
			}
		
		}
		
	
		if(flag == 1){
			Object[] obj = {"true"};
			return obj;
		}
		
		
		String ablshMnth = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAblshPrd, DATE_FORMAT_MONTH);
		String nearstAblshDate = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAblshPrd, DATE_FORMAT);
		String nearstAdptDate = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAdptPrd, DATE_FORMAT);
		
		Object[] obj = {"false",nearstAdptDate,nearstAdptPrd,nearstAblshDate,nearstAblshPrd,ablshMnth};
		return obj;
	}	
	
	
	/** Added method getNearestAbolishAdoptDates for B000027 **/
	public Object[] getNearestAbolishAdoptDates(InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth,String prdMnth,String oseiId,String carSrsAdptDate,String carSrsAblshDate)
			throws PdApplicationNonFatalException, ParseException {

		prdMnth = prdMnth+"11";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchAdoptAbolishDatesByOseiId);

		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr,MstOseiDtl.class);
		
		String nearstAdptPrd="",nearstAblshPrd="";
	
		query.setParameter(PRMTR_OSEI_ID, oseiId);
		List<MstOseiDtl> distinctOseiDtl = query.getResultList();
		/*
		Adopt Date				YYYYMMDD
		Adopt Period			YYYYMMWD
		Abolish Date			YYYYMMDD
		Abolish Period				YYYYMMWD
		Abolish Month				YYYYMM
		*/
		Date prdMnthDate = CommonUtil.convertStringToDate(prdMnth);
		long min = 1000000000;
		for(MstOseiDtl oseiDtl :distinctOseiDtl){
			
				if(nearstAdptPrd.equalsIgnoreCase("")){
					nearstAdptPrd = oseiDtl.getId().getOseiAdptDate();
				}
				LOG.info("nearstAblshPrd is "+nearstAblshPrd +" and osei abolish date :"+oseiDtl.getId().getOseiAblshDate());
				if(nearstAblshPrd.equalsIgnoreCase("")){
					nearstAblshPrd = oseiDtl.getId().getOseiAblshDate();
				}
				Date oseiAdptPrdDate = CommonUtil.convertStringToDate(oseiDtl.getId().getOseiAdptDate());
				Date oseiAblshPrdDate = CommonUtil.convertStringToDate(oseiDtl.getId().getOseiAblshDate());
				Date nearstAdptPrdDate = CommonUtil.convertStringToDate(nearstAdptPrd);
				Date nearstAblshPrdDate = CommonUtil.convertStringToDate(nearstAblshPrd);
				
				long diffInOseiAdptDate = prdMnthDate.getTime() - oseiAdptPrdDate.getTime();
				//long diffInNearestAdptDate = prdMnthDate.getTime() - nearstAdptPrdDate.getTime();
				long diffInOseiAblshDate = oseiAblshPrdDate.getTime() - prdMnthDate.getTime();
				//long diffInNearestAblshDate = nearstAblshPrdDate.getTime() - prdMnthDate.getTime();
				
				if(diffInOseiAdptDate < min || diffInOseiAblshDate < min){
					nearstAdptPrdDate = oseiAdptPrdDate;
					nearstAblshPrdDate = oseiAblshPrdDate;
					if(diffInOseiAdptDate < diffInOseiAblshDate){
						min = diffInOseiAdptDate;
					} else {
						min = diffInOseiAblshDate;
					}
				}
		}
		
		String ablshMnth = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAblshPrd, DATE_FORMAT_MONTH);
		LOG.info("ablshMnth is "+ablshMnth);
		String nearstAblshDate = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAblshPrd, DATE_FORMAT);
		String nearstAdptDate = wkNoCalendarRepositoryObj.fetchEIMDate(input.getPorCd(), nearstAdptPrd, DATE_FORMAT);
		
		Object[] obj = {"false",nearstAdptDate,nearstAdptPrd,nearstAblshDate,nearstAblshPrd,ablshMnth};
		return obj;
	}

	
	public List<String> fetchOseiIdFrInVldPrdMnth(InputBean input,String byrGrpCd,String carSrs,String ordrTkBsMnth,String prdMnth)
			throws PdApplicationException {

		
			
			StringBuilder dynamicQuery = new StringBuilder(); 
			
			dynamicQuery.append(QueryConstants.fetchOseiIdFrInVldPrdMnth);
			
			String queryStr = dynamicQuery.toString();
			queryStr = queryStr.replaceAll(PDConstants.TABLE_NAME, input.getTableName());
			
			Query query = eMgr.createNativeQuery(queryStr.toString());
		
			query.setParameter(PRMTR_PORCD, input.getPorCd());
			query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
			query.setParameter(PRMTR_BYR_GRP_CD, byrGrpCd);
			query.setParameter(PRMTR_CAR_SRS, carSrs);
			query.setParameter(FILE_ID, input.getFileId());
			query.setParameter(PRMTR_SEQ_NO, input.getSeqNo());
			query.setParameter(PRMTR_PRD_MNTH, prdMnth);	
		
			List<String> result = query.getResultList();
			
			return result;
		}

	
}
