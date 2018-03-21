/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000013  
 * Module          :O Ordering
 * Process Outline :MONTHLY OCF BREACH REPORT
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 16-12-2015      z014433(RNTBCI)               Initial Version
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
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_OCF_REGION;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_GRP;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class CreateMnthlyOCFBreachRptProcess.
 *
 * @author z014433
 */
public class CreateMnthlyOCFBreachRptProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(CreateMnthlyOCFBreachRptProcess.class
			.getName());
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	/** Constructor  */
	public CreateMnthlyOCFBreachRptProcess() {
	}

	/**
	 * @param items
	 * @param maxPrdnMnth
	 * @param orderTkBsMnth
	 * @throws ParseException
	 * 
	 * This method creates the breach report horizontal wise
	 */
	public void generateHrzntlBrchReport(List<Object[]> items , String maxPrdnMnth, String orderTkBsMnth) throws ParseException  {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		   String reportPath = environment.getProperty(B000027Constants.B000027_REPORT_PATH);
				 
				 DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
				 String dirPath = reportPath;
				 Long diff = 0L;
				 
				 String fileName = B000027Constants.B000027_OCF_BRCH_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
				 
				 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
				 
				 File dir = new File(dirPath);
				    if(!dir.exists()) {
				        dir.mkdir();
				    }
				    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
				    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
				    
				    if(maxPrdnMnth!=null){
				    Date maxPrdnMnthDate = CommonUtil.convertStringToDate(maxPrdnMnth);
			    	Date ordrTkBsMnth = CommonUtil.convertStringToDate(orderTkBsMnth);
			    	
			    	 diff = Math.abs(maxPrdnMnthDate.getTime()  - ordrTkBsMnth.getTime())/86400000/30;
				    }
			    	
			    	List prdnMnthsDtlsLst = CommonUtil.getProductionMonths(Integer.toString(diff.intValue()+1),orderTkBsMnth);
			    	int strArySize = B000027Constants.INT_EIGHT+((diff.intValue()+1)*3);
			    	int itrCnt = B000027Constants.INT_EIGHT;
			    	String[] frstHdrArray = new String[strArySize+1];
			    	String[] dynamicPrdnMnthArray = new String[diff.intValue()+1];
			    	String[] scndHdrArray = new String[strArySize+1];

	    			for(int i = 0; i <prdnMnthsDtlsLst.size(); i++) {
	    				dynamicPrdnMnthArray[i] = (String) prdnMnthsDtlsLst.get(i);
			    	}
	    			
	    			frstHdrArray[0]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[1]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[2]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[3]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[4]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[5]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[6]=PDConstants.EMPTY_STRING;
	    			frstHdrArray[7]=PDConstants.EMPTY_STRING;
	    			
	    			Map<String,String> prdnMntsIndxMap = new HashMap<String,String>();	
	    			
	    			while (itrCnt<strArySize){
		    			for(int i = 0; i < dynamicPrdnMnthArray.length;i++){
		    				frstHdrArray[itrCnt] = dynamicPrdnMnthArray[i].substring(0,4)+"-"+dynamicPrdnMnthArray[i].substring(4, 6);
		    				prdnMntsIndxMap.put(String.valueOf(itrCnt), dynamicPrdnMnthArray[i]);
		    				itrCnt++;
		    				frstHdrArray[itrCnt] = PDConstants.EMPTY_STRING;
		    				itrCnt++;
		    				frstHdrArray[itrCnt] = PDConstants.EMPTY_STRING;
		    				itrCnt++;
		    			}
					}
			    	
			    	excelItemWriter.setHeaders(frstHdrArray);
			    	
			    	itrCnt = B000027Constants.INT_EIGHT;
			    	scndHdrArray[0]=REPORT_HEADER_ORDR_TK_BS_PRD;
			    	scndHdrArray[1]=REPORT_HEADER_OCF_REGION;
			    	scndHdrArray[2]=REPORT_HEADER_BYR_GRP;
			    	scndHdrArray[3]=REPORT_HEADER_CAR_SRS;
			    	scndHdrArray[4]=B000027Constants.REPORT_HEADER_OCF_FRAME_CD;
			    	scndHdrArray[5]=B000027Constants.REPORT_HEADER_OCF_FEAT_CD;
			    	scndHdrArray[6]=B000027Constants.REPORT_HEADER_SHRT_DESC;
			    	scndHdrArray[7]=B000027Constants.REPORT_HEADER_LONG_DESC;
			    	
			    	while (itrCnt<strArySize){
		    			for(int i = 0; i < dynamicPrdnMnthArray.length;i++){
		    				scndHdrArray[itrCnt] = B000027Constants.REPORT_HEADER_LMT;
		    				itrCnt++;
		    				scndHdrArray[itrCnt] = B000027Constants.REPORT_HEADER_USG;
		    				itrCnt++;
		    				scndHdrArray[itrCnt] = B000027Constants.REPORT_HEADER_BRCH;
		    				itrCnt++;
		    			}
					}
			    	
			    	excelItemWriter.setHeadersScndRw(scndHdrArray);
			    	
			    	List<Object[]> fnlValues = createHrzntlFnlValues(items,strArySize,prdnMntsIndxMap);
			    	
				    		 try {
				    		    	Map<String,String> formatMap = new HashMap<String,String>();
				    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
				    		    	excelItemWriter.createTwoHdrReport(strArySize,fnlValues,formatMap,B000027Constants.B000027_OCF_BRCH_HRZNTL_SHEET_NM); 
				    		    } catch (IOException e) {
				    		        LOG.error(EXCEPTION+e);
				    		        
				    		    }
				 
				 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	/**
	 * @param items
	 * @param strArySize
	 * @param prdnMntsIndxMap
	 * @return record details for the horizontal wise report
	 */
	private List<Object[]> createHrzntlFnlValues(List<Object[]>items,int strArySize,Map<String,String> prdnMntsIndxMap) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		 List<Object[]> fnlValuesLst = new ArrayList<Object[]>();
			Object[] fnlValObj = null;
			
			for(Object[] obj : items){
				fnlValObj = new Object[strArySize+1];
				fnlValObj[0] = obj[0];
				fnlValObj[1] = obj[4];
				fnlValObj[2] = obj[3];
				fnlValObj[3] = obj[2];
				fnlValObj[4] = obj[5];
				fnlValObj[5] = obj[6];
				fnlValObj[6] = obj[8];
				fnlValObj[7] = obj[7];
				for (Map.Entry<String, String> entry : prdnMntsIndxMap.entrySet()) {
 			    if(obj[1].toString().equalsIgnoreCase(entry.getValue())){
 			    	int tempIdx = CommonUtil.stringtoInt(entry.getKey());
 			    	fnlValObj[tempIdx]=obj[9];
 			    	tempIdx++;
 			    	fnlValObj[tempIdx]=obj[10];
 			    	tempIdx++;
 			    	fnlValObj[tempIdx]=obj[11];
 				}
 			}
				fnlValuesLst.add(fnlValObj);
			}		
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			return fnlValuesLst;
	}

	/**
	 * @param items
	 * @param maxPrdnMnth
	 * @param orderTkBsMnth
	 * @throws ParseException
	 * 
	 * This method creates the breach report vertical wise
	 */
	public void generateVrtlBrchReport(List<Object[]> items, String maxPrdnMnth, String orderTkBsMnth ) throws ParseException  {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		   String reportPath = environment.getProperty(B000027Constants.B000027_REPORT_PATH);
				 
				 DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
				 String dirPath = reportPath;
				 Long diff = 0L;
				 
				 String fileName = B000027Constants.B000027_OCF_BRCH_ALL_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
				 
				 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
				 
				 File dir = new File(dirPath);
				    if(!dir.exists()) {
				        dir.mkdir();
				    }
				    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
				    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
				    
				    if(maxPrdnMnth!=null){
				    Date maxPrdnMnthDate = CommonUtil.convertStringToDate(maxPrdnMnth);
			    	Date ordrTkBsMnth = CommonUtil.convertStringToDate(orderTkBsMnth);
			    	
			    	diff = Math.abs(maxPrdnMnthDate.getTime()  - ordrTkBsMnth.getTime())/86400000/30;
				    }
			    	
			    	List prdnMnthsDtlsLst = CommonUtil.getProductionMonths(Integer.toString(diff.intValue()+1),orderTkBsMnth);
			    	int strArySize = B000027Constants.INT_EIGHT+(diff.intValue()+1);
			    	String[] finalArray = new String[strArySize+1];
			    	String[] dynamicArray = new String[diff.intValue()+1];
			    	int itrCnt = B000027Constants.INT_EIGHT;
			    	
			    	finalArray[0]=REPORT_HEADER_ORDR_TK_BS_PRD;
			    	finalArray[1]=REPORT_HEADER_OCF_REGION;
			    	finalArray[2]=REPORT_HEADER_CAR_SRS;
			    	finalArray[3]=B000027Constants.REPORT_HEADER_R_C;
			    	finalArray[4]=B000027Constants.REPORT_HEADER_OCF_FRAME_CODE;
			    	finalArray[5]=B000027Constants.REPORT_HEADER_OCF_FEAT_CD;
			    	finalArray[6]=B000027Constants.REPORT_HEADER_SHRT_DESC;
			    	finalArray[7]=B000027Constants.REPORT_HEADER_LONG_DESC;
			    	
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
			    	
			    	excelItemWriter.setHeaders(finalArray);
			    	
			    	List<Object[]> vrtclRcdValLst = createvrtlRcdsFnlLst(items,prdnMntsIndxMap,strArySize);
			    	
				    		 try {
				    		    	Map<String,String> formatMap = new HashMap<String,String>();
				    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
				    		    	excelItemWriter.createReport(vrtclRcdValLst,formatMap,B000027Constants.B000027_OCF_BRCH_VRTL_SHEET_NM); 
				    		    } catch (IOException e) {
				    		        LOG.error(EXCEPTION+e);
				    		        
				    		    }
				 
				 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	/**
	 * @param items
	 * @param prdnMntsIndxMap
	 * @param strArySize
	 * 
	 * * @return record details for the vertical wise report
	 */
	private List<Object[]> createvrtlRcdsFnlLst(List<Object[]> items,Map<String,String> prdnMntsIndxMap, int strArySize) {
		 LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		 
		 List<Object[]> vrtlRcdsFnlLst = new ArrayList<Object[]>();
		 Object[] vrtlRcdsFnl = null;
		 
		for(Object[] obj : items){
			for (Map.Entry<String, String> entry : prdnMntsIndxMap.entrySet()) {
			    if(obj[1].toString().equalsIgnoreCase(entry.getValue())){
			    	int tmpCnt = 0;
			    	for(int i = 0; i< 3; i++){
			    	vrtlRcdsFnl = new Object[strArySize+1];
			    	vrtlRcdsFnl[0] = obj[0];
	    			vrtlRcdsFnl[1] = obj[4];
	    			vrtlRcdsFnl[2] = obj[2];
	    			vrtlRcdsFnl[4] = obj[5];
	    			vrtlRcdsFnl[5] = obj[6];
	    			vrtlRcdsFnl[6] = obj[8];
	    			vrtlRcdsFnl[7] = obj[7];
	    			switch (tmpCnt) {
	    			case 0:
	    			vrtlRcdsFnl[3] = B000027Constants.CONSTANT_RC_LMT;
					vrtlRcdsFnl[CommonUtil.stringtoInt(entry.getKey())]=obj[9]; 
					break;
	    			case 1:
		    			vrtlRcdsFnl[3] = B000027Constants.CONSTANT_RC_USG;
    					vrtlRcdsFnl[CommonUtil.stringtoInt(entry.getKey())]=obj[10]; 
    					break;
	    			case 2:
		    			vrtlRcdsFnl[3] = B000027Constants.CONSTANT_RC_DIFF;
    					vrtlRcdsFnl[CommonUtil.stringtoInt(entry.getKey())]=obj[11]; 
    					break;
    					
	    			default:
	    				break;
	    			}
	    			vrtlRcdsFnlLst.add(vrtlRcdsFnl);
	    			tmpCnt++;
				}
			    }
		}
		}
		 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		 LOG.info("Record Count in vrtlRcdsFnlLst (ALL) is :"+vrtlRcdsFnlLst.size());
		 return vrtlRcdsFnlLst;
	}
	
}
