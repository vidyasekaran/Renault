/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000055
 * Module          :CM Common		
 * Process Outline :Batch for Job Schedule Creation																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015883(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000055.repository;

import java.math.BigDecimal;
import java.text.ParseException;
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

import com.nissangroups.pd.b000055.util.B000055Constants;
import com.nissangroups.pd.b000055.util.B000055QueryConstants;
import com.nissangroups.pd.bean.TmpJobSchedule;
import com.nissangroups.pd.model.MstJobSteram;
import com.nissangroups.pd.model.TrnJobstrmShdl;
import com.nissangroups.pd.repository.JobStrmShdlTrnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * @author z015883
 *This class is repository class for B000055
 */

public class BatchScheduleRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private JobStrmShdlTrnRepository jobStrmShdlTrnRepository;
	
	private static final Log LOG = LogFactory.getLog(BatchScheduleRepository.class);

	private static final int YY_FORMAT = 0;

	private HashMap<String, String> params;
	
	/*
	 * utility method to organize map
	 */
	public HashMap<String, String> addToMap(String[] args)
	{
		params=new HashMap<>();
		params.put(B000055Constants.POR_CD, args[0]);
		params.put(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD, args[1]);
		params.put(B000055Constants.FRM_MNTH, args[2]);
		params.put(B000055Constants.TO_MNTH, args[3]);
		params.put(B000055Constants.FRM_WEEK, args[4]);
		params.put(B000055Constants.TO_WEEK, args[5]);
		return this.params;
	}
	
	
	/*use to get MnthlyExecutedJobstrems
	*@return List
	*/
	public List<TmpJobSchedule> getNonExecutedJobStrm() {
		Query query=null;
		if(this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.MNTHLY))
		{
			query=entityManager.createNativeQuery(B000055QueryConstants.GET_MNTHLY_JOB_STRMS.toString());
		}
		else if(this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.WEEKLY)) 
		{
			query=entityManager.createNativeQuery(B000055QueryConstants.GET_WEEKLY_JOBSTRMS.toString());
			query.setParameter(B000055Constants.FRM_WEEK, this.params.get(B000055Constants.FRM_WEEK));
			query.setParameter(B000055Constants.TO_WEEK,  this.params.get(B000055Constants.TO_WEEK));
			
		}
		query.setParameter(B000055Constants.FRM_MNTH, this.params.get(B000055Constants.FRM_MNTH));
		query.setParameter(B000055Constants.TO_MNTH, this.params.get(B000055Constants.TO_MNTH));
		query.setParameter(B000055Constants.POR_CD, this.params.get(B000055Constants.POR_CD));
		query.setParameter(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD, this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD));
		List<Object[]> result=query.getResultList();
		//object[] contains JOBSTRM_SEQ_ID,POR,ORDRTK_BASEPERIOD_TYPE_CD,ORDR_TAKE_BASE_MNTH,JOBSTRM_SHDL_SEQ_ID
		 List<TmpJobSchedule> jobs=getFilteredJobStrms(result, this.params);
		 if(jobs!=null)
			return jobs;
		 else
			 LOG.info("There are No jobstreams unexecuted for selected period of Months.");
		 	LOG.error(PDMessageConsants.M00003);
		 	CommonUtil.stopBatch();
		 	return jobs;
	}
	/*use to get WklyExecutedJobstrems
	*@return List
	*/
	public List<TmpJobSchedule> getAlrdyWklyExecutedJobStrms()
	{
		Query query=entityManager.createNativeQuery(B000055QueryConstants.GET_WEEKLY_JOBSTRMS.toString());
		query.setParameter(B000055Constants.FRM_WEEK, this.params.get(B000055Constants.FRM_WEEK));
		query.setParameter(B000055Constants.TO_WEEK,  this.params.get(B000055Constants.TO_WEEK));
		query.setParameter(B000055Constants.FRM_MNTH, this.params.get(B000055Constants.FRM_MNTH));
		query.setParameter(B000055Constants.TO_MNTH,  this.params.get(B000055Constants.TO_MNTH));
		query.setParameter(B000055Constants.POR_CD,   this.params.get(B000055Constants.POR_CD));
		query.setParameter(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD, B000055Constants.param.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD));
		List<Object[]> result=query.getResultList();
		//object[] contains JOBSTRM_SEQ_ID,POR,ORDRTK_BASEPERIOD_TYPE_CD,ORDR_TAKE_BASE_MNTH,JOBSTRM_SHDL_SEQ_ID,ORDR_TAKE_BASE_WK_NO
		List<TmpJobSchedule> jobs=getFilteredJobStrms(result, this.params);
		if(jobs!=null)
			return jobs;
		 else
			 LOG.info("There are No jobstreams unexecuted for selected period of Months & weeks.");
		 	CommonUtil.stopBatch();
		 	return jobs;
	}
	/*use to extract free or unexecuted jobstreams
	*@return List
	*/
	public List<TmpJobSchedule> getFilteredJobStrms(List<Object[]> result,Map<String, String> param)
	{
		LOG.info("Start filtering jobs");
		Query query2=entityManager.createNativeQuery(B000055QueryConstants.GET_TMP_JOBSHDL.toString());
		query2.setParameter(B000055Constants.STTS, B000055Constants.WAITING);
		List<Object[]> total_jobs=query2.getResultList();
		List<TmpJobSchedule> jobs = new ArrayList<TmpJobSchedule>();
		if(total_jobs.size() !=0)
		{
			LOG.info("total jobs from Tem table "+total_jobs.size());
			//object[] contains ID,JOBSTRM_SEQ_ID,POR_CD,ORDR_TK_BS_MNTH,WK_NUMBER,BASE_ST_DATE,BATCH_STATUS
			for (Object[] objects : total_jobs) {
				TmpJobSchedule schedule=entityManager.find(TmpJobSchedule.class, Long.parseLong(objects[0].toString()));
				jobs.add(schedule);
				LOG.info("Job added "+jobs.size());
			}
			if(result.size() !=0)
			{
				LOG.info("Total Jobs which are Already started Execution "+result.size());
				return compareJobs(result, jobs, param);
			}
			else
				return jobs;
		}
		return jobs;
	}
	
	/*
	 * use to compare and rerurn filtered list
	*@param result
	*@param jobs
	*@param param
	*@return List<TmpJobSchedule>
	 */
	public List<TmpJobSchedule> compareJobs(List<Object[]> result,List<TmpJobSchedule> jobs,Map<String, String> param)
	{

		for (Object[] obj : result)//all alrdy available jobs
		{
			LOG.info("Comparing Starts");
			if(param.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.WEEKLY))
			{
				if(obj[0]!=null && obj[3]!=null && obj[5]!=null)
					for (TmpJobSchedule tmp : jobs) //all waiting jobs from screen
					{
						if(tmp.getJobstrmSeqId().equals(BigDecimal.valueOf(Long.parseLong(obj[0].toString()))) && tmp.getOrdrTkBsMnth().equalsIgnoreCase(obj[3].toString()) && tmp.getWkNumber().equalsIgnoreCase(obj[5].toString()))
						{
							jobs.remove(tmp);
							LOG.info("jobstream with JobShdlId ["+obj[4]+"] got deleted from Execution List");
							LOG.error(PDMessageConsants.M00088);
							break;
						}
					}
			}
			else 
			{
				if(obj[0]!=null && obj[3]!=null)
					for (TmpJobSchedule tmp : jobs) //all waiting jobs from screen
					{
						//LOG.info("job id "+tmp.getJobstrmSeqId()+"& "+obj[0]+" mnth is "+tmp.getOrdrTkBsMnth()+" & "+obj[3]);
						if(tmp.getJobstrmSeqId().equals(BigDecimal.valueOf(Long.parseLong(obj[0].toString()))) && tmp.getOrdrTkBsMnth().equalsIgnoreCase(obj[3].toString().trim()))
						{
							jobs.remove(tmp);
							LOG.info("jobstream with JobShdlId ["+obj[4]+"] got deleted from Execution List");
							LOG.error(PDMessageConsants.M00088);
							break;
						}
					}
			}
		}
		LOG.info("Jobs after comparation "+jobs.size());
		return jobs;
	
	}
	/*use to extract jobStream details
	*@param job
	*@return MstJobSteram
	*/
	public MstJobSteram getJobStrms(TmpJobSchedule job) {
		MstJobSteram jobSteram=entityManager.find(MstJobSteram.class, job.getJobstrmSeqId().longValue());
		if(jobSteram!=null )
			return jobSteram;
		else
		{
			LOG.error(PDMessageConsants.M00003);
			CommonUtil.stopBatch();
		}
		return null;
	}
	/*it inserts the given data into Jobshdltrn table
	*@param jobstrm,job
	*@return int
	*/
	public int insertIntoJobShdltrn(MstJobSteram jobstrm, TmpJobSchedule job) {
		
		int result= jobStrmShdlTrnRepository.addSchedule(jobstrm,job);
		if(result!=B000055Constants.INT_ZERO)
			LOG.info(PDMessageConsants.M00189);
		else
			LOG.error(PDMessageConsants.M00043);
		return result;
	}
	/*use to extract lead time data for jobstreams
	*@param job
	*@returnList<Object[]>
	*/
	public List<Object[]> extractLeadTimeData(TmpJobSchedule job) {
		Query query=entityManager.createNativeQuery(B000055QueryConstants.GET_LEADTIME_DATA.toString());
		query.setParameter(B000055Constants.JOB_STRM_SEQ_ID, job.getJobstrmSeqId());
		List<Object[]> result=query.getResultList();
		//object[] contains ST_TIME,END_DT,PREV_JOBSTRM_SEQ_ID,NEXT_JOBSTRM_SEQ_ID,LEADTIME,DURTN,INC_HOLIDAY_FLG,SLIDE_TO
		if(result.size()!=0)
			return result;
		else 
		{
			LOG.error(PDMessageConsants.M00003);
			return null;
		}
		
	}
	/*Method to Insert schedule based on Lead time condition(P0004)
	*@param job
	*@param leadData
	*void
	*/
	public String insertIntoJobStrmShdl(TmpJobSchedule job, Object[] leadData) {
		String holidayFlg=leadData[6]!=null?leadData[6].toString():"";
		try
		{
		TrnJobstrmShdl exec=jobStrmShdlTrnRepository.getRecord(job);
		Date st=CommonUtil.converStringToDate(B000055Constants.TIMESTAMP_FORMAT,leadData[0].toString());//it is start time
		Date strt_date=CommonUtil.converStringToDate(B000055Constants.TIMESTAMP_FORMAT, leadData[1].toString());//lead[1]=end date
		LOG.info("start date before adding lead time "+strt_date);
		strt_date=CommonUtil.addMinutesToDate(Integer.parseInt(leadData[4].toString()), strt_date);//added lead time in end date=start date
		LOG.info("start date after adding lead time "+strt_date);
		strt_date=CommonUtil.swapTime(strt_date, st);
		LOG.info("start date after Swaping with start time "+strt_date);
		exec.setStDt(strt_date);
		exec.setStTime(commonUtil.convertDateToTimestamp(strt_date));//I thnk it is basestdate
		Date end_date=null;
		if(leadData[5]==null)//if duration is null
		{
			end_date=CommonUtil.addMinutesToDate(B000055Constants.FIFTEEN, strt_date);
			LOG.info("end date when duration is null "+end_date);
		}
		else// if duration is not null
		{
			end_date=CommonUtil.addMinutesToDate(Integer.parseInt(leadData[5].toString()), strt_date);
			LOG.info("end date when duration is Not null "+end_date);
		}
		if(holidayFlg.equalsIgnoreCase(B000055Constants.YES))//condition 1 in P0004
		{
			
			end_date=getWorkingDay(end_date,leadData[7].toString());
			LOG.info("end date after check for holiday "+end_date);
			exec.setEndDt(end_date);
			exec.setEndTime(commonUtil.convertDateToTimestamp(end_date));
		}
		else if(holidayFlg.equalsIgnoreCase(B000055Constants.NO))//condition 2 in P0004
		{
			end_date=excludeHolidays(strt_date,end_date);
			LOG.info("end date after Excluding for holidayList "+end_date);
			exec.setEndDt(end_date);
			exec.setEndTime(commonUtil.convertDateToTimestamp(end_date));
		}
		else
		{
			LOG.info("Holiday FLag value is Null cannot insert record");
			LOG.error(PDMessageConsants.M00043);
			return B000055Constants.BLANK;
		}
			jobStrmShdlTrnRepository.insertObject(exec);
			LOG.info("Records inserted for P0004 Case");
			return B000055Constants.SUCCESS;
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
		}
		return null;
	}
	
	
	/*
	*@param d
	*@param st
	*@return
	*Date
	*/
	public Date excludeHolidays(Date stDt, Date endDt) {
		try{
		Query query=entityManager.createNativeQuery(B000055QueryConstants.GET_HOLIDAYS_FRM_LIST.toString());
		query.setParameter(B000055Constants.POR_CD, params.get(B000055Constants.POR_CD));
		query.setParameter(B000055Constants.START_DATE, CommonUtil.converDateToFormat(stDt, B000055Constants.DD_MM_YY_FORMAT));
		query.setParameter(B000055Constants.END_DATE, CommonUtil.converDateToFormat(endDt, B000055Constants.DD_MM_YY_FORMAT));
		Object result=query.getSingleResult();
		int count=Integer.parseInt(result.toString());
		LOG.info(" count value is "+count);
		if(count!=B000055Constants.INT_ZERO)
		{
			Date new_date=CommonUtil.addDayToDate(endDt, count);
			endDt=excludeHolidays(endDt, new_date);
		}
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			return null;
		}
		return endDt;
	}
	/*
	 * method to get holiday from mst_por_wrkdy
	*@param date
	*@param flg
	*@param slideValue
	*@return Date
	 */
	public Date getWorkingDay(Date date,String slideValue)
	{
		 
		 Date d=date;
		Query query=entityManager.createNativeQuery(B000055QueryConstants.CHECK_HOLIDAY.toString());
		query.setParameter(B000055Constants.POR_CD, this.params.get(B000055Constants.POR_CD));
		try {
			query.setParameter(B000055Constants.END_DATE, CommonUtil.converDateToFormat(d, B000055Constants.DD_MM_YY_FORMAT));
		
		List<Object[]> object=query.getResultList();
		if(object.size()!=0)
		{
			  if(slideValue.equalsIgnoreCase(B000055Constants.ZERO_ONE))
			  {
				  d=getWorkingDay(CommonUtil.addDayToDate(d, B000055Constants.MINUS_ONE), slideValue);
			  }
			  else if(slideValue.equalsIgnoreCase(B000055Constants.ZERO_TWO))
			  {
				  d=getWorkingDay(CommonUtil.addDayToDate(d, B000055Constants.INT_ONE), slideValue);
			  }
		return d;
		}
		else
			 return d;
		} catch (ParseException e) {
			
			 LOG.error(PDConstants.EXCEPTION+e);
			 return null;
		}
	}


	/* use to get day wise data for calc flag "NO"(P0005)
	*@param jobstrmSeqId
	*@return List<Object[]>
	*/
	public List<Object[]> getDayBasedataMnthly(BigDecimal jobstrmSeqId) {
		Query query=entityManager.createNativeQuery(B000055QueryConstants.MNTHLY_DAY_BASE_DATA.toString());
		query.setParameter(B000055Constants.JOB_STRM_SEQ_ID, jobstrmSeqId);
		List<Object[]> result=query.getResultList();
		//object[] contains MINUS_NWK_FROM_BASEPRD,DAY,SLIDE_TO,DURTN,ST_TIME
		if(result.size()!=0)
		{
			LOG.info("Extracted DayBase data "+result.get(0));
			return result;
		}
		
		else
		{
			LOG.error(PDMessageConsants.M00003);
			return null;
		}
	}


	/*get start date from week_no_calender_mst table(P0005.a & P0006.a)
	*@param ordrTkBsMnth
	*@param string
	*@return Date
	*/
	public Date getStartDateFrmWkCalndr(String ordrTkBsMnth, String string) {
		int wk_no=0;
		Query query=entityManager.createNativeQuery(B000055QueryConstants.EXTRACT_WEEK_NO.toString());
		query.setParameter(B000055Constants.POR_CD,params.get(B000055Constants.POR_CD));
		query.setParameter(B000055Constants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
		if(this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.MNTHLY))
		{
			query.setParameter(B000055Constants.PROD_WK_NO, B000055Constants.ONE);
		}
		else if(this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.WEEKLY))
		{
			query.setParameter(B000055Constants.PROD_WK_NO, this.params.get(B000055Constants.WK_NO));
		}
		try{
		Object w=query.getSingleResult();
		
		wk_no=Integer.parseInt(w.toString());
		}catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
		}
		if(wk_no!=0)
		{
			int minus_nweek=Integer.parseInt(string);
			wk_no=wk_no-minus_nweek;
			
			Query query2=entityManager.createNativeQuery(B000055QueryConstants.EXTRACT_NON_OPERATION_FLAG_FROM_WEEK_CALENDER_MST.toString());
			query2.setParameter(B000055Constants.POR_CD, params.get(B000055Constants.POR_CD));
			query2.setParameter(B000055Constants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
			 query2.setParameter(B000055Constants.WK_NO, wk_no);
			 List<Object[]> flag=query2.getResultList();
			 //object[] returns non_operation_flag,porcd
			try {
				if(flag.size()!=0)
				{
					for (Object[] objects : flag) 
					if(this.params.get(B000055Constants.ORDR_TK_BS_PRD_TYPE_CD).equalsIgnoreCase(B000055Constants.WEEKLY))
					{
						Query q=null;
						if(objects[0].equals(B000055Constants.SPACE)||objects[0].equals(B000055Constants.B))
						{
							q=entityManager.createNativeQuery(B000055QueryConstants.CHECK_PREVIOUS_OPERATION_FLAG.toString());
						}
						else if(objects[0].equals(B000055Constants.F))
						{
							q=entityManager.createNativeQuery(B000055QueryConstants.CHECK_NEXT_OPERATION_FLAG.toString());
						}
							q.setParameter(B000055Constants.WK_NO, wk_no);
							Object o=q.getSingleResult();
							wk_no=Integer.parseInt(o.toString());
						}
				
					}
					Query query3=entityManager.createNativeQuery(B000055QueryConstants.EXTRACT_START_DATE_FROM_WEEK_CALENDER_MST.toString());
					query3.setParameter(B000055Constants.POR_CD, params.get(B000055Constants.POR_CD));
					query3.setParameter(B000055Constants.ORDR_TK_BS_MNTH, ordrTkBsMnth);
					query3.setParameter(B000055Constants.WK_NO, wk_no);
					List<Object[]> st_date=query3.getResultList();
					//array contains start_date and porcd
					if(st_date.size()!=0)
					for (Object[] objects : st_date) {
						Date d=CommonUtil.convertStringToDate(objects[0].toString());
						return d;
					}
					else
					{
						LOG.info("No Start date for Given Month and PorCd");
						LOG.error(PDMessageConsants.M00003);
						return null;
					}
					}
			 catch (Exception e) {
				
				LOG.error(PDConstants.EXCEPTION+e);
			}
		}
		return null;
	}


	/* use to insert according to P0005
	*@param job
	*@param day
	*@param objects
	*@param st_dt
	*void
	*/
	public void insertIntoJobStrmShdl(TmpJobSchedule job, Object[] day, Date st_dt) {
		try{
		TrnJobstrmShdl exec=jobStrmShdlTrnRepository.getRecord(job);
		//here check date with day but day is in char(2) format dont know what it is?
		String dayNo=CommonUtil.getDayNameFromDate(st_dt);
		//String dayNo="02";
		String dayFrmDb=(day[1].toString().trim());
		if(dayFrmDb!=dayNo)
		{
			st_dt=CommonUtil.addDayToDate(st_dt, 1);
		}
		st_dt=CommonUtil.swapTime(st_dt, job.getBaseStDate());
		exec.setStDt(st_dt);
		exec.setStTime(commonUtil.convertDateToTimestamp(st_dt));
		//it shd be start date 
		Date end=CommonUtil.addMinutesToDate(Integer.parseInt(day[3].toString()), job.getBaseStDate());
		end=CommonUtil.swapTime(st_dt, end);
		
		end=getWorkingDay(end, day[2].toString());
		exec.setEndDt(end);
		exec.setEndTime(commonUtil.convertDateToTimestamp(end));
		jobStrmShdlTrnRepository.insertObject(exec);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.error(PDConstants.EXCEPTION+e);
		}
	 
	}


	/*
	*@param jobstrmSeqId
	*@return
	*List<Object[]>
	*/
	public List<Object[]> getDayBasedataWkly(BigDecimal jobstrmSeqId) {
		
		Query query=entityManager.createNativeQuery(B000055QueryConstants.WEEKLY_DAY_BASE_DATA.toString());
		query.setParameter(B000055Constants.JOB_STRM_SEQ_ID, jobstrmSeqId);
		List<Object[]> result=query.getResultList();
		return result;
	}
	
}
