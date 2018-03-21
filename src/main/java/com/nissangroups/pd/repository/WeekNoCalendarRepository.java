/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline : Repository/Business Layer Class to Co-Ordinate with DB.
 *   
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.repository;

import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PRD_MNTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_WK_NO;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_YYYYMMDD_DATE;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.QueryConstants;

public class WeekNoCalendarRepository {

	/** Object emgr. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;


	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(WeekNoCalendarRepository.class);
	
	public String convertYYYYMMDDtoYYYYMMWD(String date,String porCd) throws ParseException{
		String result ="";
		StringBuilder dynamicQuery = new StringBuilder();
		dynamicQuery.append(QueryConstants.fetchWkNoCalendarData);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_YYYYMMDD_DATE, date);
		
		
		List<Object[]> resultList = query.getResultList();
		if(resultList != null && !(resultList.isEmpty())){
			
			Object[] obj = resultList.get(0);
			String prdMnth = CommonUtil.convertObjectToString(obj[0]);
			String wkNo = CommonUtil.convertObjectToString(obj[1]);
			String wkStartDateStr = CommonUtil.convertObjectToString(obj[2]);
			
			
			//int day = (Integer.parseInt(date) - Integer.parseInt(wkStartDateStr)) + 1;
			
			Date dateFormatter = CommonUtil.convertStringToDate(date);
			
			Date wkStartDate = CommonUtil.convertStringToDate(wkStartDateStr);
			
			long diff = dateFormatter.getTime() - wkStartDate.getTime();
			 long day = diff / 1000 / 60 / 60 / 24;
			 day = day+1;
			 LOG.info ("Days: " +day);
			
			
			
			result = prdMnth + wkNo + String.valueOf(day);
			
		} 
		
		return result;
	}
	

	public String fetchEIMDate(String porCd,String periodDateStr,String format)
			throws PdApplicationNonFatalException, ParseException {
		String result = "";
		StringBuilder dynamicQuery = new StringBuilder();
		String prdMnth = "";
		String wkNo = "";
		String dayStr = "";
		LOG.info("Abolish Period : "+periodDateStr);
		if(periodDateStr.length()>6){
			 prdMnth = periodDateStr.substring(0, 6);
			 wkNo = String.valueOf(periodDateStr.charAt(6));
			 dayStr = String.valueOf(periodDateStr.charAt(7));	
		} else {
			 prdMnth = periodDateStr;
			 wkNo = "1";
			 dayStr = "1";
		}
		
		int day = Integer.parseInt(dayStr);
		int weekNo = Integer.parseInt(wkNo);
		
		Calendar c = Calendar.getInstance();
		Date periodDate = CommonUtil.convertStringToDate(prdMnth);
		c.setTime(periodDate); 
		c.add(Calendar.MONTH, 1);
		Date prdMnthDate = c.getTime();
		String calculatedPrdMnth = CommonUtil.convertDateToString(prdMnthDate, DATE_FORMAT_MONTH);
		
		dynamicQuery.append(QueryConstants.fetchWkStartDate);
		
		String queryStr = dynamicQuery.toString();
		Query query = eMgr.createNativeQuery(queryStr.toString());
		
		query.setParameter(PRMTR_PORCD, porCd);
		query.setParameter(PRMTR_PRD_MNTH, calculatedPrdMnth);
		query.setParameter(PRMTR_WK_NO, wkNo);
		
		
		List<String> resultList = query.getResultList();
		if(resultList != null && !(resultList.isEmpty())){
			String wkStartDateStr = resultList.get(0);
			result = wkStartDateStr;
			
			LOG.info("wkStartDateStr :"+wkStartDateStr);
			
			Date wkStartDate = CommonUtil.convertStringToDate(wkStartDateStr);
			 
			c.setTime(wkStartDate); 
			c.add(Calendar.DATE, (day - 1 ));
			wkStartDate = c.getTime();
			
			LOG.info("wkStartDate :"+wkStartDate);
			
			result = CommonUtil.convertDateToString(wkStartDate, DATE_FORMAT);
			
			if(format.equalsIgnoreCase(DATE_FORMAT_MONTH)){
				if(weekNo > 1 || day>1){
					c.setTime(prdMnthDate); 
					c.add(Calendar.MONTH, 1);
					prdMnthDate = c.getTime();
					LOG.info("WeekNo > 1 or dayNO > 1, hence prod month is result");
					result = CommonUtil.convertDateToString(prdMnthDate, DATE_FORMAT_MONTH);
				} else {
					LOG.info("WeekNo < 1 or dayNO < 1, hence weekstart date is result");
					result = CommonUtil.convertDateToString(wkStartDate, DATE_FORMAT_MONTH);
				}
			} 
			
		 
			
			
			
		}
		
		
		return result;
		
	}
	
	public EntityManager geteMgr() {
		return eMgr;
	}


	public void seteMgr(EntityManager eMgr) {
		this.eMgr = eMgr;
	}



}
