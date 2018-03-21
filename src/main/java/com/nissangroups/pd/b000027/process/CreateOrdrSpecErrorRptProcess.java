/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000011
 * Module          :O Ordering
 * Process Outline : Monthly Production Order Spec Error Report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 12-12-2015      z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000027.process;

import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_GRP;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRDN_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_REGION;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapper;
import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;
import com.nissangroups.pd.repository.MnthlyPrdnOrdrTrnRepository;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class CreateOrdrSpecErrorRptProcess.
 *
 * @author z014433
 */

public class CreateOrdrSpecErrorRptProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(CreateOrdrSpecErrorRptProcess.class
			.getName());
	
	/** Variable environment. */
    @Autowired(required = false)
    Environment environment;
	
	/** Variable abolish month display format value */
	String eimAblshMnthFrmt = null;
	
	/** Variable abolish month display format value */
	String maxPrdnMnth = null;

	@Autowired(required = false)
	private MnthlyPrdnOrdrTrnRepository mnthPrdnOrdrRepo; 
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	
	@Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;
	
	/** Constructor of CreateOrdrSpecErrorRptProcess */
	public CreateOrdrSpecErrorRptProcess() {
	}

	
	/**
	 * @param item
	 * @param validCrSrsMap
	 * @throws ParseException
	 * @throws PdApplicationException
	 * 
	 * This method generates the order spec error report
	 */
	public void generateOrdrSpecErrorReport(OrdrTkBsPrdMstRowMapper item,Map<Object, List<String>> validCrSrsMap) throws Exception{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		   String reportPath = environment.getProperty(B000027Constants.B000027_REPORT_PATH);
				 
				 DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
				 String dirPath = reportPath;
				 
				 String fileName = B000027Constants.B000027_ORDR_SPEC_ERR_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
				 
				 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
				 
				 File dir = new File(dirPath);
				    if(!dir.exists()) {
				        dir.mkdir();
				    }
				    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
				    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
				    
				    eimAblshMnthFrmt = extractEimAblshMnthFrmtVal(item.getId().getPOR_CD());
			    	List<Object[]> ordrSpecRcds = fetchOrdrSpecRcds(item,validCrSrsMap);
			    	Long diff = 0L;
			    	
			    	
			    	if(maxPrdnMnth!=null){
			    	Date maxPrdnMnthDate = CommonUtil.convertStringToDate(maxPrdnMnth);
			    	Date ordrTkBsMnth = CommonUtil.convertStringToDate(item.getId().getORDR_TAKE_BASE_MNTH());
			    	
			    	 diff = Math.abs(maxPrdnMnthDate.getTime()  - ordrTkBsMnth.getTime())/86400000/30;
			    	}
			    	
			    	
			    	List prdnMnthsDtlsLst = CommonUtil.getProductionMonths(Integer.toString(diff.intValue()+1),item.getId().getORDR_TAKE_BASE_MNTH());
			    	int strArySize = B000027Constants.INT_FIFTEEN+(diff.intValue()+1);
			    	String[] finalArray = new String[strArySize+1];
			    	String[] dynamicArray = new String[diff.intValue()+1];
			    	int itrCnt = B000027Constants.INT_FIFTEEN;
			    	
			    	finalArray[0]=REPORT_HEADER_ORDR_TK_BS_PRD;
			    	finalArray[1]=REPORT_HEADER_OCF_REGION;
			    	finalArray[2]=B000027Constants.REPORT_HEADER_MC_REGION;
			    	finalArray[3]=B000027Constants.REPORT_HEADER_BYR_GRP_CD;
			    	finalArray[4]=REPORT_HEADER_BYR_CD;
			    	finalArray[5]=REPORT_HEADER_PRDN_FMLY_CD;
			    	finalArray[6]=REPORT_HEADER_CAR_GRP;
			    	finalArray[7]=REPORT_HEADER_CAR_SRS;
			    	finalArray[8]=B000027Constants.REPORT_HEADER_SPEC_DEST_CD;
			    	finalArray[9]=B000027Constants.REPORT_HEADER_END_ITEM_MDL;
			    	finalArray[10]=B000027Constants.REPORT_HEADER_ADD_SPEC_CD;
			    	finalArray[11]=REPORT_HEADER_POT;
			    	finalArray[12]=B000027Constants.REPORT_HEADER_SLS_NOTE_NUMBER;
			    	finalArray[13]=B000027Constants.REPORT_HEADER_COLOR;
			    	finalArray[14]= B000027Constants.REPORT_HEADER_ABOLISH_MONTH;
			    			
			    			for(int i = 0; i <prdnMnthsDtlsLst.size(); i++) {
					    		dynamicArray[i] = (String) prdnMnthsDtlsLst.get(i);
					    	}
			    			
			    		Map<String,String> prdnMntsIndxMap = new HashMap<String,String>();	
			    			
						while (itrCnt<strArySize){
			    			for(int i = 0; i < dynamicArray.length;i++){
			    				finalArray[itrCnt] = dynamicArray[i].substring(0,4)+"-"+dynamicArray[i].substring(4, 6);
			    				prdnMntsIndxMap.put(String.valueOf(itrCnt), dynamicArray[i]);
			    				itrCnt++;
			    			}
						}
						
						Object[] ordrSpecRcdFnl = null;
						List<Object[]> ordrSpecRcdFnlLst = new ArrayList<Object[]>();
						
				    		for(Object[] obj : ordrSpecRcds){
				    			ordrSpecRcdFnl = new Object[strArySize+1];
				    			ordrSpecRcdFnl[0]=obj[0];
				    			ordrSpecRcdFnl[1]=obj[1];
				    			ordrSpecRcdFnl[2]=obj[2];
				    			ordrSpecRcdFnl[3]=obj[3];
				    			ordrSpecRcdFnl[4]=obj[4];
				    			ordrSpecRcdFnl[5]=obj[5];
				    			ordrSpecRcdFnl[6]=obj[6];
				    			ordrSpecRcdFnl[7]=obj[7];
				    			ordrSpecRcdFnl[8]=obj[8];
				    			ordrSpecRcdFnl[9]=obj[9];
				    			ordrSpecRcdFnl[10]=obj[10];
				    			ordrSpecRcdFnl[11]=obj[11];
				    			ordrSpecRcdFnl[12]=obj[12];
				    			ordrSpecRcdFnl[13]=obj[13];
				    			ordrSpecRcdFnl[14]=obj[14];
				    			for (Map.Entry<String, String> entry : prdnMntsIndxMap.entrySet()) {
				    			    if(obj[15].toString().equalsIgnoreCase(entry.getValue())){
				    					ordrSpecRcdFnl[CommonUtil.stringtoInt(entry.getKey())]=obj[16];
				    				}
				    			}
				    			ordrSpecRcdFnlLst.add(ordrSpecRcdFnl);
				    		}
				    		
				    		excelItemWriter.setHeaders(finalArray);
				    
				    		 try {
				    		    	Map<String,String> formatMap = new HashMap<String,String>();
				    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
				    		    	formatMap.put(B000027Constants.CONSTANT_FOURTEEN,eimAblshMnthFrmt); // TO DO check - writeReport method will be modified by Tamil by 16/12 to validate format
				    		    	excelItemWriter.createReport(ordrSpecRcdFnlLst,formatMap,B000027Constants.B000027_ORDR_SPEC_ERR_RPT_SHEET_NM); 
				    		    } catch (IOException e) {
				    		        LOG.error(EXCEPTION+e);
				    		        
				    		    }
				 
				 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	/**	
	 * @param porCd
	 * @return value1
	 * 
	 * This method is to extract value1 from paramter mst table to find eim abolish month format
	 * @throws PdApplicationException 
	 */
	private String extractEimAblshMnthFrmtVal(String porCd) throws PdApplicationException {
		
		LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
		
		if(eimAblshMnthFrmt== null){
			
			eimAblshMnthFrmt = prmtrMstRepo.fetchValue1(B000027Constants.MONTHLY_ORDER_TO_PLANT, porCd, B000027Constants.ABOLISH_DATE_DISPLAY_FORMAT);

			if(!eimAblshMnthFrmt.isEmpty())
			 LOG.info("Extracted Abolish Month Display format is  : " +eimAblshMnthFrmt);
			 else {
				 	//Not mentioned in design document
			 }
		}
		
		LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		
		return eimAblshMnthFrmt;
	}
	
	
	/**
	 * @param item
	 * @param validCrSrsMap
	 * @return
	 * @throws PdApplicationNonFatalException
	 * @throws ParseException
	 * 
	 * This method extracts records for creating order spec error report
	 */
	private List<Object[]> fetchOrdrSpecRcds(OrdrTkBsPrdMstRowMapper item,Map<Object, List<String>> validCrSrsMap) throws PdApplicationNonFatalException, ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> ordrSpecRcd = new ArrayList<Object[]>();
		List<Object[]> ordrSpecRcdLst = new ArrayList<Object[]>();
		List prdnMnthsLst = new ArrayList<>();
		
		SlsNoteCalProcess slsNotePrcs = new SlsNoteCalProcess();
		InputBean input = new InputBean();
		input.setPorCd(item.getId().getPOR_CD());
		
		Object ablshMnth = null;
		Object[] finalObj = null;
		List<Object[]> finalObjLst = new ArrayList<Object[]>();
		

		for (Map.Entry<Object, List<String>> entry : validCrSrsMap.entrySet()) {
			LOG.info("Processing for Valid Car Series Key and Production months is : "+entry.getKey()+"---->"+entry.getValue());
			ordrSpecRcd = mnthPrdnOrdrRepo.extractOrdrSpecRptDtls(item.getId().getORDR_TAKE_BASE_MNTH(),item.getId().getPOR_CD(),entry.getKey(),entry.getValue(),validCrSrsMap); 
			ordrSpecRcdLst.addAll(ordrSpecRcd);
		}
		
		for(Object[] specRcds :  ordrSpecRcdLst) {
			Object[] nearestAblshAdptDates = mnthlyOrdrIfTrnRepositoryObj.getNearestAbolishAdoptDates(input,"","","",specRcds[13].toString(),specRcds[14].toString(),"","");
			if(nearestAblshAdptDates[0].toString().equalsIgnoreCase(B000027Constants.CONSTANT_FALSE)){
				 ablshMnth = nearestAblshAdptDates[5];
			} 
			finalObj = new Object[20];
			
			finalObj[0] = specRcds[0];
			finalObj[1] = specRcds[1];
			finalObj[2] = specRcds[2];
			finalObj[3] = specRcds[3];
			finalObj[4] = specRcds[4];
			finalObj[5] = specRcds[5];
			finalObj[6] = specRcds[7];
			finalObj[7] = specRcds[6];
			finalObj[8] = specRcds[8];
			finalObj[9] = specRcds[9];
			finalObj[10] = specRcds[10];
			finalObj[11] = specRcds[11];
			 finalObj[12] = slsNotePrcs.calcSlsNoteNum(specRcds[13].toString(), specRcds[11].toString());
			 finalObj[13] = specRcds[12];			
			 finalObj[14] = ablshMnth;
			 finalObj[15] = specRcds[13]; // Prod Month
			 finalObj[16]= specRcds[15];
			 prdnMnthsLst.add(specRcds[13]);
			 finalObjLst.add(finalObj);
		}
		
		Collections.sort(prdnMnthsLst); 
		if(!prdnMnthsLst.isEmpty() && prdnMnthsLst.size() > 0) {
		maxPrdnMnth = prdnMnthsLst.get(prdnMnthsLst.size() - 1).toString();
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		LOG.info("Records to be written in Order Spec Error Report : "+finalObjLst.size());
		LOG.info("Maximum Production Month is  : "+maxPrdnMnth);
		return finalObjLst;
	}

	
}