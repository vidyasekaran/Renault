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
package com.nissangroups.pd.b000055.util;

/**
 * @author z015883
 *
 */
public class B000055QueryConstants {

	
	//SEQ-ID FOR ALL TABLES RELATED TO BATCH55
	 
		public static final String JOBSTRM_SHDL_SEQ_ID = "select TRN_JOBSTRM_EXEC_SEQ_ID.nextval from dual"; 
	
	/*
	 * Query to fetch monthly ordrTkBsMnth based jobstreams(P0001)
	 */
		public static final StringBuilder GET_MNTHLY_JOB_STRMS=new StringBuilder().
			append("select jsm.JOBSTRM_SEQ_ID,jsm.POR,jsm.ORDRTK_BASEPERIOD_TYPE_CD,jsst.ORDR_TAKE_BASE_MNTH,jset.JOBSTRM_SHDL_SEQ_ID "
					+ "from TRN_JOBSTRM_EXEC jset,TRN_JOBSTRM_SHDL jsst,MST_JOB_STERAM jsm "
					+ "where jsm.POR=:porCd and jsm.ORDRTK_BASEPERIOD_TYPE_CD=:ordrTkBsPrdTypeCd and "
					+ "jsst.JOBSTRM_SEQ_ID=jsm.JOBSTRM_SEQ_ID and jsst.ORDR_TAKE_BASE_MNTH between :frmMnth and :toMnth and "
					+ "jset.JOBSTRM_SHDL_SEQ_ID=jsst.JOBSTRM_SHDL_SEQ_ID");
	
	/*
	 * Query to fetch Weekly ordrTkBsMnth based jobstreams(P0001)
	 */
	
		public static final StringBuilder GET_WEEKLY_JOBSTRMS=new StringBuilder().
				append("select jsm.JOBSTRM_SEQ_ID,jsm.POR,jsm.ORDRTK_BASEPERIOD_TYPE_CD,jsst.ORDR_TAKE_BASE_MNTH,jset.JOBSTRM_SHDL_SEQ_ID,jsst.ORDR_TAKE_BASE_WK_NO "
						+ "from TRN_JOBSTRM_EXEC jset,TRN_JOBSTRM_SHDL jsst,MST_JOB_STERAM jsm "
						+ "where jsm.POR=:porCd and jsm.ORDRTK_BASEPERIOD_TYPE_CD=:ordrTkBsPrdTypeCd and "
						+ "jsst.JOBSTRM_SEQ_ID=jsm.JOBSTRM_SEQ_ID and jsst.ORDR_TAKE_BASE_MNTH between :frmMnth and :toMnth and "
						+ "jset.JOBSTRM_SHDL_SEQ_ID=jsst.JOBSTRM_SHDL_SEQ_ID and jsst.ORDR_TAKE_BASE_WK_NO between :frmWk and :toWk");
		
		
		
		
	/*
	 * Get Jobstrms from TmpJobShdl table to compare with process one
	 */
		public static final StringBuilder GET_TMP_JOBSHDL=new StringBuilder()
		.append("select t.ID, t.JOBSTRM_SEQ_ID, t.POR_CD, t.ORDR_TK_BS_MNTH, t.WK_NUMBER, t.BASE_ST_DATE, t.BATCH_STATUS "
				+ "from TMP_JOB_SCHEDULE t where t.BATCH_STATUS=:stts");
	
	/*
	 * Extract all jobstreams data based on the input to do scheduling(P0002)
	 */
	
		public static final StringBuilder GET_JOB_STRMS=new StringBuilder()
	.append("select m.JOBSTRM_SEQ_ID,m.POR,m.JOBSTRM_DESC,m.ORDRTK_JOBSTRM_FLG,m.CALC_BASEDATE_FLG, m.SHDL_CALC_PTTRN,"
			+ "m.ORDRTK_BASEPERIOD_TYPE_CD,m.ST_TIME "
			+ "from MST_JOB_STERAM m where m.POR=:porCd and m.ORDRTK_BASEPERIOD_TYPE_CD=:ordrTkBsPrdTypeCd");
	
	/*
	 * Extrct the Lead time data for jobstreams with Calc base flag=0(NO) and calc_patrn=1(P0004)
	 */
	
		public static final StringBuilder GET_LEADTIME_DATA=new StringBuilder()
	.append("select to_char(m.ST_TIME,'DD-MM-yy hh24:mi:ss'),to_char(s.END_DT,'DD-MM-yy hh24:mi:ss'),l.PREV_JOBSTRM_SEQ_ID,l.NEXT_JOBSTRM_SEQ_ID,"
			+ "l.LEADTIME,l.DURTN,l.INC_HOLIDAY_FLG,l.SLIDE_TO "
			+ "from MST_LEADTIME_BASE l,MST_JOB_STERAM m,TRN_JOBSTRM_SHDL s "
			+ "where l.PREV_JOBSTRM_SEQ_ID=:jobStrmSeqId and s.JOBSTRM_SEQ_ID=l.PREV_JOBSTRM_SEQ_ID "
			+ "and m.JOBSTRM_SEQ_ID=s.JOBSTRM_SEQ_ID and m.CALC_BASEDATE_FLG='0' and m.SHDL_CALC_PTTRN='1'");
	
	/*
	 * find holiday from mst_por_wrkdy table(P0004.a)
	 */
		public static final StringBuilder CHECK_HOLIDAY =new StringBuilder()
	.append("select TRIM(w.WORKDY_FLG) from MST_POR_WORKDY w where w.DT=:endDt and w.POR=:porCd and w.WORKDY_FLG='N'");
	
	/*
	 * find holidaylist from mst_por_wrkdy table(P0004.a)
	 */
		public static final StringBuilder GET_HOLIDAYS_FRM_LIST=new StringBuilder()
	.append("select count(w.DT) from MST_POR_WORKDY w where w.DT between :strtDt and :endDt and w.POR=:porCd and w.WORKDY_FLG='N'");
	
	/*
	 * Extract DaybaseMnthly data for jobstreams (P0005)
	 */
		public static final StringBuilder MNTHLY_DAY_BASE_DATA=new StringBuilder()
	.append("select d.MINUS_NWK_FROM_BASEPRD,d.DAY,d.SLIDE_TO,d.DURTN,to_char(m.ST_TIME,'DD-MM-yy hh24:mi:ss') from MST_JOB_STERAM m,MST_DAYBASE_MONTHLY d")
	.append(" where m.ORDRTK_BASEPERIOD_TYPE_CD='M' and m.SHDL_CALC_PTTRN='2' and m.CALC_BASEDATE_FLG='0' and ")
	.append("d.JOBSTRM_SEQ_ID=:jobStrmSeqId and m.JOBSTRM_SEQ_ID=:jobStrmSeqId");
	
	/*
	 * Extract week no of year(P0005.a)
	 */
		public static final StringBuilder EXTRACT_WEEK_NO=new StringBuilder()
		//.append("select w.WK_NO_YEAR,w.POR_CD from MST_WK_NO_CLNDR w where w.POR_CD=:porCd and w.PROD_MNTH=:ordrTkBsMnth and w.PROD_WK_NO=:prodWkNo");
		.append("select w.WK_NO_YEAR from MST_WK_NO_CLNDR w where w.POR_CD=:porCd and w.PROD_MNTH=:ordrTkBsMnth and w.PROD_WK_NO=:prodWkNo");
		
	/*
	 * Extract start_date from week_no_calender_mst	and w.PROD_WK_NO='1'
	 */
		public static final StringBuilder EXTRACT_START_DATE_FROM_WEEK_CALENDER_MST=new StringBuilder()
		.append("select w.WK_STRT_DATE,w.POR_CD from MST_WK_NO_CLNDR w where w.POR_CD=:porCd and w.PROD_MNTH=:ordrTkBsMnth and w.WK_NO_YEAR=:wkNo");

		/*
		 * Extract non_operationa_flag from week_no_calender_mst	
		 */
			public static final StringBuilder EXTRACT_NON_OPERATION_FLAG_FROM_WEEK_CALENDER_MST=new StringBuilder()
			.append("select w.NON_OPRTNL_FLAG,w.POR_CD from MST_WK_NO_CLNDR w where w.POR_CD=:porCd and w.PROD_MNTH=:ordrTkBsMnth and w.WK_NO_YEAR=:wkNo");
		
		/*
	 * Extract dayBaseWeekly data for jobstreams(P0006) 
	 */
		public static final StringBuilder WEEKLY_DAY_BASE_DATA=new StringBuilder()
	.append("select d.MINUS_NWK_FROM_BASEPRD,d.DAY,d.SLIDE_TO,d.DURTN,m.ST_TIME from MST_JOB_STERAM m,MST_DAYBASE_WEEKLY d "
			+ " where m.ORDRTK_BASEPERIOD_TYPE_CD='W' and m.SHDL_CALC_PTTRN='2' and m.CALC_BASEDATE_FLG='0' "
			+ " and d.JOBSTRM_SEQ_ID=:jobStrmSeqId and m.JOBSTRM_SEQ_ID=:jobStrmSeqId");

		/*
		 * Extract Previous week no for operational flag as *(P0006.a)
		 */
		public static final StringBuilder CHECK_PREVIOUS_OPERATION_FLAG = new StringBuilder()
		.append("select max(m.WK_NO_YEAR) from MST_WK_NO_CLNDR m where m.NON_OPRTNL_FLAG='*' and m.WK_NO_YEAR<(select (m.WK_NO_YEAR) from MST_WK_NO_CLNDR m where m.WK_NO_YEAR=:wkNo and rownum=1);");
		
		/*
		 * Extract Next week no for operational flag as *(P0006.a)
		 */
		public static final StringBuilder CHECK_NEXT_OPERATION_FLAG = new StringBuilder()
		.append("select min(m.WK_NO_YEAR) from MST_WK_NO_CLNDR m where m.NON_OPRTNL_FLAG='*' and m.WK_NO_YEAR>(select (m.WK_NO_YEAR) from MST_WK_NO_CLNDR m where m.WK_NO_YEAR=:wkNo and rownum=1);");
}
