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

import java.util.HashMap;

import javax.persistence.Parameter;

/**
 * @author z015883
 *
 */
public class B000055Constants {

	public static final String B000055_BATCH="B000055";
	public static final String B000055_XML="B000055/B000055_Batch_Config.xml";
	public static final String POR_CD="porCd";
	public static final String ORDR_TK_BS_PRD_TYPE_CD="ordrTkBsPrdTypeCd";
	public static final String FRM_MNTH="frmMnth";
	public static final String TO_MNTH="toMnth";
	public static final String FRM_WEEK="frmWk";
	public static final String TO_WEEK="toWk";
	public static final String START_DATE="strtDt";
	public static final String END_DATE="endDt";
	public static final String WRK_DAY_FLG="wrkDyFlg";
	public static final String JOB_STRM_SEQ_ID="jobStrmSeqId";
	public static final String B000055_Processor = "b00055Processor";
	public static final String MNTHLY = "M";
	public static final String WEEKLY = "W";
	public static final String BLANK = "";
	public static final String STTS = "stts";
	public static final String WAITING = "waiting";
	public static final int INT_ONE = 1;
	public static final int INT_ZERO=0;
	public static final String YES = "Y";
	public static final String NO="N";
	public static final String TIMESTAMP_FORMAT = "dd-MM-yy HH:mm:ss";
	public static final int FIFTEEN = 15;
	public static final String ZERO = "0";
	public static final String ZERO_ONE = "01";
	public static final String ZERO_TWO = "02";
	public static final String ONE = "1";
	public static final int MINUS_ONE = -1;
	public static final String TWO = "2";
	public static final String ORDR_TK_BS_MNTH="ordrTkBsMnth";
	public static final String WK_NO="wkNo";
	public static final String B = "B";
	public static final String F="F";
	public static final String SPACE=" ";
	public static final String SUCCESS = "success";
	public static final HashMap<String, String> param=new HashMap<>();
	public static final String DD_MM_YY_FORMAT = "dd-MM-yy";
	public static final String PROD_WK_NO = "prodWkNo";
	
}
