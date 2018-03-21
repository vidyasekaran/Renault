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

import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_CD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_BYR_GRP_OCF_USAGE_QTY;
import static com.nissangroups.pd.util.PDConstants.PRMTR_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFBYRGRPCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_OCFRGNCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_ORDR_TK_BS_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_DIFFERENCE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_FEAT_DESC_LONG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_FEAT_DESC_SHRT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_FEAT_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_FRAME_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_LMT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_USAGE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POR;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpMnthlyOcfLmtPK;
import com.nissangroups.pd.model.TrnNscCnfrmtnMnthly;
import com.nissangroups.pd.model.TrnNscCnfrmtnMnthlyPK;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.QueryConstants;


public class ByrGrpMnthlyOcfLimitTrnRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Autowired(required=false)
	private ByrMnthlyOcfUsgRepository byrMnthlyOcfUsgRepositoryObj ;
	
	private List<Object[]> ocfBrchRcrds = new ArrayList<Object[]>();
	
	private static final Log LOG = LogFactory.getLog
			(ByrGrpMnthlyOcfLimitTrnRepository.class);
	
	public void initialisation(InputBean input,List<Object[]> distinctByrGrpCarSrsList)
			throws PdApplicationException, ParseException {


		
		
		for(Object[] obj : distinctByrGrpCarSrsList){
			String byrGrpCd =  CommonUtil.convertObjectToString(obj[0]);
			String carSrs =  CommonUtil.convertObjectToString(obj[1]);
			String ordrTkMnthString =  CommonUtil.convertObjectToString(obj[2]).trim();
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
				
			if(ordrTkMnthString.length()>6){
				ordrTkMnthString = ordrTkMnthString.substring(0, 6);
			}
				
			Date ordrTkMnth = CommonUtil.convertStringToDate(ordrTkMnthString);
			ordrTkMnthString = CommonUtil.convertDateToString(ordrTkMnth,DATE_FORMAT_MONTH);
			Query query = buildQueryFrUpdate();
			
			TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt = new TrnBuyerGrpMnthlyOcfLmt();
			TrnBuyerGrpMnthlyOcfLmtPK byrGrpOcfLmtPk = new TrnBuyerGrpMnthlyOcfLmtPK();
			
			byrGrpOcfLmtPk.setPorCd(input.getPorCd());
			byrGrpOcfLmtPk.setBuyerGrpCd(byrGrpCd);
			byrGrpOcfLmtPk.setCarSrs(carSrs);
			
			byrGrpOcfLmtPk.setOrdrTakeBaseMnth(ordrTkMnthString);
			byrGrpOcfLmtPk.setProdMnth(ordrTkMnthString);
			
			byrGrpOcfLmt.setId(byrGrpOcfLmtPk);
			
			byrGrpOcfLmt.setBuyerGrpOcfUsgQty(BigDecimal.valueOf(0));
			
			
			executeUpdateQuery(query,byrGrpOcfLmt);
			for(int i =1;i<horizon;i++){
				Date prdMnthDate = CommonUtil.addMonthToDate(ordrTkMnth,i);
				prdMnth = CommonUtil.convertDateToString(prdMnthDate,DATE_FORMAT_MONTH);
				byrGrpOcfLmt.getId().setProdMnth(prdMnth);
				executeUpdateQuery(query, byrGrpOcfLmt);
			}
			
		}
		
		
	}

	public void executeUpdateQuery(Query query,TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt){
	
		query.setParameter(PRMTR_BYR_GRP_OCF_USAGE_QTY, byrGrpOcfLmt.getBuyerGrpOcfUsgQty());
		query.setParameter(PRMTR_CAR_SRS,byrGrpOcfLmt.getId().getCarSrs());
		query.setParameter(PRMTR_PORCD, byrGrpOcfLmt.getId().getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, byrGrpOcfLmt.getId().getOrdrTakeBaseMnth());
		query.setParameter(PRMTR_PRD_MNTH,byrGrpOcfLmt.getId().getProdMnth());
		query.setParameter(PRMTR_BYR_GRP_CD,byrGrpOcfLmt.getId().getBuyerGrpCd());
		
				
		query.executeUpdate();
	}

	
	public List<Object[]> getBrchRcrds(Query query,TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt,String ocfByrGrpCd,String ocfRegionCd){
		
		query.setParameter(PRMTR_CAR_SRS,byrGrpOcfLmt.getId().getCarSrs());
		query.setParameter(PRMTR_PORCD, byrGrpOcfLmt.getId().getPorCd());
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, byrGrpOcfLmt.getId().getOrdrTakeBaseMnth());
		query.setParameter(PRMTR_PRD_MNTH,byrGrpOcfLmt.getId().getProdMnth());
		query.setParameter(PRMTR_BYR_GRP_CD,byrGrpOcfLmt.getId().getBuyerGrpCd());
		query.setParameter(PRMTR_OCFBYRGRPCD,ocfByrGrpCd);
		query.setParameter(PRMTR_OCFRGNCD,ocfRegionCd);
				
		List<Object[]> result = query.getResultList();
		return result;
	}
	
	
	public Query  buildQueryFrUpdate(){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.updateByrGrpLmt);
		dynamicQuery.append(QueryConstants.byrGrpLmtCnd);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		return query;
	}
	
	
	public Query  buildQueryFrBrchReport(){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchOcfBrchRcrds);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		return query;
	}

	
	
	public void updateNewOrdrDtls(String porCd, String prdMnth, String ordrTkBsMnth, String carSrs, String byrGrpCd)
			throws PdApplicationException, ParseException {
		
			
			List<Object[]> sumFeatUsageDtls = byrMnthlyOcfUsgRepositoryObj.fetchSumFeatUsage(porCd, prdMnth, ordrTkBsMnth, carSrs, byrGrpCd);
			
			for(Object[] featObj : sumFeatUsageDtls){
				String sumFeatUsageQty = CommonUtil.convertObjectToString(featObj[0]);
				String featureCd = CommonUtil.convertObjectToString(featObj[1]);
				String ocfFrameCd = CommonUtil.convertObjectToString(featObj[2]);
				String featTypeCd = CommonUtil.convertObjectToString(featObj[3]);
				
				merge(porCd, prdMnth, ordrTkBsMnth, carSrs, byrGrpCd,featureCd,ocfFrameCd,sumFeatUsageQty,featTypeCd);
				
							
			}
			
		
	}

	
	public void merge(String porCd,String prdMnth, String ordrTkBsMnth,String carSrs, String byrGrpCd,String featureCd,String ocfFrameCd,String sumFeatUsgQty,String featTypeCd )
			throws PdApplicationNonFatalException {
		TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt  = populateToBean(porCd,prdMnth, ordrTkBsMnth,carSrs, byrGrpCd,featureCd,ocfFrameCd,sumFeatUsgQty,null,featTypeCd);
		byrGrpOcfLmt.setCrtdBy("B000018");
		byrGrpOcfLmt.setUpdtdBy("B000018");
		eMgr.merge(byrGrpOcfLmt);		
	}
	
	
	public TrnBuyerGrpMnthlyOcfLmt populateToBean(String porCd,String prdMnth, String ordrTkBsMnth,String carSrs, String byrGrpCd,String featureCd,String ocfFrameCd,String sumFeatUsgQty,String lmtQty,String featTypeCd){
		TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt = new TrnBuyerGrpMnthlyOcfLmt();
		
		TrnBuyerGrpMnthlyOcfLmtPK byrGrpOcfLmtPk = new TrnBuyerGrpMnthlyOcfLmtPK();
		
		byrGrpOcfLmtPk.setPorCd(porCd);
		byrGrpOcfLmtPk.setBuyerGrpCd(byrGrpCd);
		byrGrpOcfLmtPk.setCarSrs(carSrs);
		
		byrGrpOcfLmtPk.setOrdrTakeBaseMnth(ordrTkBsMnth);
		byrGrpOcfLmtPk.setProdMnth(prdMnth);
		byrGrpOcfLmtPk.setFeatCd(featureCd);
		
		byrGrpOcfLmt.setId(byrGrpOcfLmtPk);
		
		TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmtOld = eMgr.find(TrnBuyerGrpMnthlyOcfLmt.class, byrGrpOcfLmtPk);
		
		
		if(byrGrpOcfLmtOld!=null){
			byrGrpOcfLmt =  byrGrpOcfLmtOld;
		} else {

			if(lmtQty!=null){
				BigDecimal ocflmtQty = 	BigDecimal.valueOf(Integer.parseInt(lmtQty));
				byrGrpOcfLmt.setBuyerGrpOcfLmtQty(ocflmtQty);	
			} else {
				if(ocfFrameCd!=null && ocfFrameCd.equalsIgnoreCase("00")){
					lmtQty = "0";
					BigDecimal ocflmtQty = 	BigDecimal.valueOf(Integer.parseInt(lmtQty));
					byrGrpOcfLmt.setBuyerGrpOcfLmtQty(ocflmtQty);
				} 
			}
		}
		
		
		if(sumFeatUsgQty!=null){
			BigDecimal usgQty = 	BigDecimal.valueOf(Integer.parseInt(sumFeatUsgQty));
			byrGrpOcfLmt.setBuyerGrpOcfUsgQty(usgQty);
		}
		
		
		
		byrGrpOcfLmt.setFeatTypeCd(featTypeCd);
		
		byrGrpOcfLmt.setOcfFrmeCd(ocfFrameCd);
		
		return byrGrpOcfLmt;
		
	}
	
public List<Object[]> fetchSumFeatUsage(String porCd,String prdMnth,String ordrTkBsMnth,String carSrs,String byrGrpCd,String ocfRegionCd,String ocfByrGrpCd){
		
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchSumFeatUsageByRgnLvl);
		dynamicQuery.append(QueryConstants.fetchSumFeatUsageByRgnLvlCnd);
		dynamicQuery.append(QueryConstants.fetchSumFeatUsageByRgnLvlGrpBy);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		

		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_ORDR_TK_BS_MNTH, ordrTkBsMnth);
		query.setParameter(PRMTR_PRD_MNTH,prdMnth);
		query.setParameter(PRMTR_CAR_SRS, carSrs);
		query.setParameter(PRMTR_OCFBYRGRPCD, ocfByrGrpCd);
		query.setParameter(PRMTR_OCFRGNCD, ocfRegionCd);
		
		List<Object[]> result = query.getResultList();
		return result;
		
	}
	
public void generateOcfBrchReport(InputBean input,List<Object[]> distinctByrGrpCarSrsList,String reportPath)
		throws PdApplicationException, ParseException {

	
	for(Object[] obj : distinctByrGrpCarSrsList){
		
		String byrGrpCd =  CommonUtil.convertObjectToString(obj[0]);
		String carSrs =  CommonUtil.convertObjectToString(obj[1]);
		String ordrTkMnthString =  CommonUtil.convertObjectToString(obj[2]).trim();
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
			
		if(ordrTkMnthString.length()>6){
			ordrTkMnthString = ordrTkMnthString.substring(0, 6);
		}
			
		
		
		Date ordrTkMnth = CommonUtil.convertStringToDate(ordrTkMnthString);
		ordrTkMnthString = CommonUtil.convertDateToString(ordrTkMnth,DATE_FORMAT_MONTH);
		
		
		
		Query query = buildQueryFrBrchReport();
		
		TrnBuyerGrpMnthlyOcfLmt byrGrpOcfLmt = new TrnBuyerGrpMnthlyOcfLmt();
		TrnBuyerGrpMnthlyOcfLmtPK byrGrpOcfLmtPk = new TrnBuyerGrpMnthlyOcfLmtPK();
		
		byrGrpOcfLmtPk.setPorCd(input.getPorCd());
		byrGrpOcfLmtPk.setBuyerGrpCd(byrGrpCd);
		byrGrpOcfLmtPk.setCarSrs(carSrs);
		
		byrGrpOcfLmtPk.setOrdrTakeBaseMnth(ordrTkMnthString);
		byrGrpOcfLmtPk.setProdMnth(ordrTkMnthString);
		
		byrGrpOcfLmt.setId(byrGrpOcfLmtPk);
		
		byrGrpOcfLmt.setBuyerGrpOcfUsgQty(BigDecimal.valueOf(0));
		String completeflg = "0";
		String nscCmpltFlag = "0";
		 MstMnthOrdrTakeBasePd mnthOrdrTakeBasePdObj = input.getOrdrTkBsMnthList().get(ordrTkMnthString);
		if(mnthOrdrTakeBasePdObj.getStageCd().contains("F")){
			List<Object[]> brchRecords = getBrchRcrds(query,byrGrpOcfLmt,ocfByrGrpCd,ocfRegionCd);
			nscCmpltFlag = addToOcfBrchReport(brchRecords);
			if(nscCmpltFlag.equalsIgnoreCase("1")){
				completeflg = "1";
			}
			for(int i =1;i<horizon;i++){
				Date prdMnthDate = CommonUtil.addMonthToDate(ordrTkMnth,i);
				prdMnth = CommonUtil.convertDateToString(prdMnthDate,DATE_FORMAT_MONTH);
				byrGrpOcfLmt.getId().setProdMnth(prdMnth);
				brchRecords = getBrchRcrds(query,byrGrpOcfLmt,ocfByrGrpCd,ocfRegionCd);
				nscCmpltFlag = addToOcfBrchReport(brchRecords);
				if(completeflg.equalsIgnoreCase("0")){
					completeflg = nscCmpltFlag; 
				}
				
			}
		}
		updateNscCnfrmMnthly(byrGrpCd,carSrs,ordrTkMnthString,input.getPrdOrdrStgCd(),input.getPorCd(),completeflg);
		
	}
	
	
	createOcfBrchReport(reportPath, ocfBrchRcrds, input.getPorCd(),input);	
}




/**
 * Excel based Error Report.
 *
 * @param porstr1 the porstr1
 * @param items the items
 */
public void createOcfBrchReport(String reportPath, List<Object[]> items,String porCd,InputBean input)  {
	
    
    
    DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    //reportPath = "D:\\Public\\B000018\\";
    String dirPath = reportPath;
    String fileName = "B000018_OCF_"+porCd+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
    
    File dir = new File(dirPath);
    if(!dir.exists()) {
        dir.mkdir();
    }
    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
    

    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_POR, REPORT_HEADER_ORDR_TK_BS_PRD,
    		REPORT_HEADER_PRD_MNTH,REPORT_HEADER_CAR_SRS,REPORT_HEADER_BYR_GRP,REPORT_HEADER_OCF_FRAME_CD,
    		REPORT_HEADER_OCF_FEAT_CD,REPORT_HEADER_FEAT_DESC_SHRT,REPORT_HEADER_FEAT_DESC_LONG,
    		REPORT_HEADER_OCF_LMT,REPORT_HEADER_OCF_USAGE,REPORT_HEADER_DIFFERENCE,REPORT_HEADER_ERROR_MSG});
    
    try {
    	Map<String,String> formatMap = new HashMap<String,String>();
    	formatMap.put("1","YYYY-MM");
    	formatMap.put("2","YYYY-MM");
    	excelItemWriter.createReport(items,formatMap,"Error Report");
    } catch (IOException e) {
        LOG.error(EXCEPTION+e);
        
    }
    mnthlyOrdrIfTrnRepositoryObj.updateOcfBrchErrDataToMnthlyOrdrErrIf(items,input);
    LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
}



	private String addToOcfBrchReport(List<Object[]> brchRecords) {
		String nscCmpltFlag = "0";
		for(Object[] obj : brchRecords){
			if(obj[11]!=null){
				
				if(obj[11]!=null && obj[5].toString().equalsIgnoreCase("00")){
					String diffString = obj[11].toString();
					int diff = Integer.parseInt(diffString);
					int N = obj.length;
					Object[] newObject = Arrays.copyOf(obj, N + 1);
					
					if(diff<0){
						nscCmpltFlag = "1";
						newObject[12] = PDMessageConsants.M00063;	
						ocfBrchRcrds.add(newObject);			
					}
					if(diff>0){
						nscCmpltFlag = "1";
						newObject[12] = PDMessageConsants.M00064;
						ocfBrchRcrds.add(newObject);
					}
					
				} else if ( obj[11]!=null && !(obj[5].toString().equalsIgnoreCase("00"))) {
					String diffString = obj[11].toString();
					int diff = Integer.parseInt(diffString);
					int N = obj.length;
					Object[] newObject = Arrays.copyOf(obj, N + 1);
					
					newObject[12] = PDMessageConsants.M00063;
					if(diff<0){
						nscCmpltFlag = "1";
						ocfBrchRcrds.add(newObject);			
					}
				}
				
				
			}
			
			
		}
		
		return nscCmpltFlag ;
	
	}
	
	
	public void updateNscCnfrmMnthly(String byrGrpCd,String carSrs,String ordrTkBsMnth,String prodOrdrStageCd,String porCd,String nscCmpltFlag){
		TrnNscCnfrmtnMnthly nscCnfrmtnMnthly = new TrnNscCnfrmtnMnthly();
		
		TrnNscCnfrmtnMnthlyPK nscCnfrmtnMnthlyPk = new TrnNscCnfrmtnMnthlyPK();
		nscCnfrmtnMnthlyPk.setBuyerGrpCd(byrGrpCd);
		nscCnfrmtnMnthlyPk.setCarSrs(carSrs);
		nscCnfrmtnMnthlyPk.setOrdrTakeBaseMnth(ordrTkBsMnth);
		nscCnfrmtnMnthlyPk.setPorCd(porCd);
		nscCnfrmtnMnthlyPk.setProdOrdrStageCd(prodOrdrStageCd);
		nscCnfrmtnMnthly.setId(nscCnfrmtnMnthlyPk);
		
		TrnNscCnfrmtnMnthly nscCnfrmtnMnthlyOld = eMgr.find(TrnNscCnfrmtnMnthly.class, nscCnfrmtnMnthlyPk);
		if(nscCnfrmtnMnthlyOld!=null){
			nscCnfrmtnMnthly = nscCnfrmtnMnthlyOld;
		}
		nscCnfrmtnMnthly.setCrtdBy("B000018");
		nscCnfrmtnMnthly.setUpdtdBy("B000018");
		nscCnfrmtnMnthly.setNscCmpltFlag(nscCmpltFlag);
		
		eMgr.merge(nscCnfrmtnMnthly);
	}

	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
