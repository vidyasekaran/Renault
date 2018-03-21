/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000016
 * Module          :O Ordering
 * Process Outline : Service Error Report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 25-11-2015      z014433(RNTBCI)               Initial Version
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
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRDN_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * The Class CreateSrvcErrRptProcess.
 *
 * @author z014433
 */
public class CreateSrvcErrRptProcess {
	
	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(CreateSrvcErrRptProcess.class
			.getName());
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	/** Constructor  */
	public CreateSrvcErrRptProcess() {
	}

	/**
	 * @param items
	 * 
	 * This method generates the service error report for B000027
	 */
	public void generateSrvcErrReport(List<Object[]> items )  {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> vldRcds = new ArrayList<Object[]>();
		
		   String reportPath = environment.getProperty(B000027Constants.B000027_REPORT_PATH);
				 
				 DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
				 String dirPath = reportPath;
				 
				 String fileName = B000027Constants.B000027_SRVC_ERR_RPT_NM+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
				 
				 LOG.info("Report path is   : " + reportPath + " and file name is "+ fileName);
				 
				 File dir = new File(dirPath);
				    if(!dir.exists()) {
				        dir.mkdir();
				    }
				    CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
				    excelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
				    
				    excelItemWriter.setHeaders(new String[] {REPORT_HEADER_ORDR_TK_BS_PRD, REPORT_HEADER_PRD_MNTH,REPORT_HEADER_PRDN_FMLY_CD,
				    		REPORT_HEADER_BYR_CD,B000027Constants.REPORT_HEADER_SPEC_DEST_CD,B000027Constants.REPORT_HEADER_MDL_CD,
				    		B000027Constants.REPORT_HEADER_SPEC_CD,B000027Constants.REPORT_HEADER_SLS_NOTE,B000027Constants.REPORT_HEADER_EX_NO, 
				    		B000027Constants.REPORT_HEADER_QTY}); 
				    
				    if(!items.isEmpty() && items.size() > 0) {
				    	 vldRcds = getUnqData(items);
				    }
				    else {
				    	 vldRcds = items;
				    }
				    
				    		 try {
				    		    	Map<String,String> formatMap = new HashMap<String,String>();
				    		    	formatMap.put(PDConstants.CONSTANT_ZERO,B000027Constants.B000027_REPORT_DATE_FORMAT);
				    		    	formatMap.put(PDConstants.CONSTANT_ONE,B000027Constants.B000027_REPORT_DATE_FORMAT);
				    		    	excelItemWriter.createReport(vldRcds,formatMap,B000027Constants.B000027_SRVC_ERR_RPT_SHEET_NM); 
				    		    } catch (IOException e) {
				    		        LOG.error(EXCEPTION+e);
				    		        
				    		    }
				 
				 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	/**
	 * @param items
	 * @return unique records 
	 */
	private List<Object[]> getUnqData(List<Object[]> items) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Set<String> unqSt = new HashSet<String>();
		List<Object[]> unqRcds = new ArrayList<Object[]>();
		Object[] unqObj = null;
		for(Object[] obj : items) {
			
			String ordrTkBsMnthStr = CommonUtil.convertObjectToString(obj[0]);
			String prdnMnthStr = CommonUtil.convertObjectToString(obj[1]);
			String prdnFmlyCdStr = CommonUtil.convertObjectToString(obj[2]);
			String byrCdStr = CommonUtil.convertObjectToString(obj[3]);
			String spcDstCdStr = CommonUtil.convertObjectToString(obj[4]);
			String mdlCdStr = CommonUtil.convertObjectToString(obj[5]);
			String spcCdStr = CommonUtil.convertObjectToString(obj[6]);
			String slsNoteStr = CommonUtil.convertObjectToString(obj[7]);
			String exNoStr = CommonUtil.convertObjectToString(obj[8]);
			String qtyStr = CommonUtil.convertObjectToString(obj[9]);
			
			String keyStr = ordrTkBsMnthStr+prdnMnthStr+prdnFmlyCdStr+byrCdStr+spcDstCdStr+mdlCdStr+spcCdStr+slsNoteStr+exNoStr+qtyStr;
			boolean flag = unqSt.add(keyStr);
			if(flag) {
				unqObj = new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[6],obj[7],obj[8],obj[9]};
				unqRcds.add(unqObj);
			}
		} 
		LOG.info("Unique records for report is (Set) "+unqSt.size());
		LOG.info("Unique records to be written Service error report :: "+unqRcds.size());
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return unqRcds;
	}
}
