/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :All
 * Module          :All
 * Process Outline :All
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-06-20155  	  z015060(RNTBCI)               New Creation
 * 27-10-2015  	  z014433(RNTBCI)               Added method convertStringToBigDecimal
 * 28-10-2015  	  z014433(RNTBCI)               Added method getPrdOrdrStgCd
 *
 */

package com.nissangroups.pd.util;

import static com.nissangroups.pd.b000008.util.B000008Constants.INPUT_PARAM_FAILURE_2;
import static com.nissangroups.pd.b000008.util.B000008Constants.STAGE_MESSAGE_1;
import static com.nissangroups.pd.b000008.util.B000008Constants.STAGE_MESSAGE_2;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V1;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V2;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V3;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V4;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V5;
import static com.nissangroups.pd.util.PDConstants.CONSTANT_V6;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DATE_FORMAT_MONTH;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_1;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_2;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_3;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_4;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_5;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE_6;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.MONTH_END_INDEX;
import static com.nissangroups.pd.util.PDConstants.MONTH_START_INDEX;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.TEN;
import static com.nissangroups.pd.util.PDConstants.TWENTY;
import static com.nissangroups.pd.util.PDConstants.YEAR_END_INDEX;
import static com.nissangroups.pd.util.PDConstants.YEAR_START_INDEX;
import static com.nissangroups.pd.util.PDConstants.DAY_START_INDEX;
import static com.nissangroups.pd.util.PDConstants.DAY_END_INDEX;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.b000059.util.B000059Constants;
import com.nissangroups.pd.b000065.output.B000065FTP_MST_INTERFACE_Details;
import com.nissangroups.pd.b000065.util.B000065CommonUtilityService;
import com.nissangroups.pd.bean.WeeklyClndrBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.model.MstWkNoClndr;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;

/**
 * The Class CommonUtil.
 */
public class CommonUtil {

	/** Variable current date. */
	private Date currentDate;

	/** Variable time stamp. */
	private Timestamp timeStamp;

	/** Variable LOG */
	private static final Log LOG = LogFactory.getLog(CommonUtil.class);

	/**
	 * Adds the month to date.
	 *
	 * @param date
	 *            the date
	 * @param addValue
	 *            the add value
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	@SuppressWarnings("static-access")
	public static Date addMonthToDate(Date date, int addValue)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, addValue);
		return calendar.getTime();
	}

	/**
	 * Convert string to date.
	 *
	 * @param convertToDate
	 *            the convert to date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date convertStringToDate(String convertToDate)
			throws ParseException {
		Date date;
		if (convertToDate.trim().length() > 6) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					DATE_FORMAT);
			date = simpleDateFormat.parse(convertToDate.trim());
		} else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					DATE_FORMAT_MONTH);
			date = simpleDateFormat.parse(convertToDate.trim());

		}
		return date;

	}

	public static Date converStringToDate(String fromFmt, String dateVal){
		SimpleDateFormat formatter = new SimpleDateFormat(fromFmt);
		String dateInString = dateVal;
		Date date = null;
		try {
			date = formatter.parse(dateInString);
			dateInString = (formatter.format(date));

		} catch (ParseException e) {
			LOG.error(PDConstants.EXCEPTION, e); 
		}
		return date;
	}
	
	public static String converDateToFormat(Date date, String toFmt) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat(toFmt);
		String dateInString = toFmt;
		dateInString = (formatter.format(date));
		return dateInString;

	}
	
	/**
	 * Date to string.
	 *
	 * @param covertToString
	 *            the covert to string
	 * @return the string
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String dateToString(Date covertToString)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return simpleDateFormat.format(covertToString);

	}

	/**
	 * Split string.
	 *
	 * @param str
	 *            the str
	 * @param length
	 *            the length
	 * @return the list
	 */
	public static List<String> splitString(String str, int length) {
		List<String> strings = new ArrayList<String>();
		int index = 0;
		while (index < str.length()) {
			strings.add(str.substring(index,
					Math.min(index + length, str.length())).trim());
			index += length;
		}
		return strings;
	}

	/**
	 * To Convert the Set Values to SQL IN String .
	 *
	 * @param seqNoSet
	 *            the seq no set
	 * @return the string
	 */
	public static String convertSetToSQLIn(Set<String> seqNoSet) {
		StringBuilder sqlIn = new StringBuilder();
		int count = 0;
		for (String seqNo : seqNoSet) {
			sqlIn.append("'" + seqNo + "'");
			if (seqNoSet.size() > ++count)
				sqlIn.append(",");
		}
		return sqlIn.toString();

	}

	/**
	 * To Convert the List Values to SQL IN String .
	 *
	 * @param lst
	 *            the lst
	 * @return the string
	 */
	public static String convertListToStringIn(List<Object> lst) {
		StringBuilder sqlIn = new StringBuilder();
		int count = 0;
		for (Object obj : lst) {
			String seqNo = (String) obj;
			sqlIn.append("'" + seqNo + "'");
			if (lst.size() > ++count)
				sqlIn.append(",");
		}
		return sqlIn.toString();

	}

	/**
	 * Common Function to add split and add space between a String based on the
	 * given length .
	 *
	 * @param str
	 *            the str
	 * @param length
	 *            the length
	 * @return String
	 */
	public static String splitStringWithSpace(String str, int length) {
		if (str == null) {
			return null;
		}
		String addedSpace = "";
		for (int i = 0; i < str.length(); i = i + length) {
			int endindex = Math.min(i + length, str.length());
			addedSpace += PDConstants.CONSTANT_SPACE
					+ str.substring(i, endindex);
		}
		return addedSpace.substring(1);
	}

	/**
	 * Method to append Object values to the Reader reult List data.
	 *
	 * @param val
	 *            the val
	 * @param a
	 *            the a
	 * @return Object[] temp
	 */

	public static Object[] appendvalue(Object[] val, Object a) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(val));
		temp.add(a);
		return temp.toArray();
	}

	/**
	 * Method to addZero to the given int.
	 *
	 * @param val
	 *            the val
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String zeroPadding(int val, int length) {
		return (String.format("%0" + length + "d", val));
	}

	/**
	 * Method to convert Big Decimal to int .
	 *
	 * @param objVal
	 *            the obj val
	 * @return the int
	 */
	public static int bigDecimaltoInt(Object objVal) {
		BigDecimal bVal = (BigDecimal) objVal;
		return (bVal.intValueExact());
	}

	/**
	 * Method to convert String to int .
	 *
	 * @param str
	 *            the str
	 * @return int
	 */
	public static int stringtoInt(String str) {
		return Integer.parseInt(str.trim());
	}

	/**
	 * Method to return Substring of a string.
	 *
	 * @param str
	 *            the str
	 * @param startingindex
	 *            the startingindex
	 * @param endingIndex
	 *            the ending index
	 * @return str
	 */
	public static String getSubstr(String str, int startingindex,
			int endingIndex) {
		return str.substring(startingindex, endingIndex);
	}

	/**
	 * Method the Split the passed String.
	 *
	 * @param str
	 *            the str
	 * @param len
	 *            the len
	 * @return Object[]
	 */
	public static Object[] getSplittedString(String str, int len) {

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < str.length(); i = i + len) {
			int endindex = Math.min(i + len, str.length());
			list.add(str.substring(i, endindex));
		}
		return list.toArray(new Object[list.size()]);
	}

	/**
	 * Convert a object to null.
	 *
	 * @param objArr
	 *            the obj arr
	 * @return converted string
	 */
	public static String convertObjectToString(Object objArr) {
		String convertedString = "";
		if (objArr != null) {
			convertedString = objArr.toString();
		}
		return convertedString;
	}

	/**
	 * Converting date to YYYY-MM format.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertYearMonth(String date) {
		String year = date.substring(YEAR_START_INDEX, YEAR_END_INDEX);
		String Month = date.substring(MONTH_START_INDEX, MONTH_END_INDEX);
		String yearMonth = year + "-" + Month;

		return yearMonth;
	}

	/**
	 * Converting date to YYYY-MM_WW format.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertYearMonthDay(String date) {
		String year = date.substring(YEAR_START_INDEX, YEAR_END_INDEX);
		String Month = date.substring(MONTH_START_INDEX, MONTH_END_INDEX);
		String day = date.substring(DAY_START_INDEX, DAY_END_INDEX);
		String yearMonth = year + "-" + Month + "-" +day;

		return yearMonth;
	}

	/**
	 * To get Current Data & Time in java.sql.Time Stamp
	 * 
	 * @return
	 */
	public Timestamp currentDateTime() {
		currentDate = new Date();
		timeStamp = new Timestamp(currentDate.getTime());
		return timeStamp;

	}
	
	/*
	 * convert date to timestamp
	 */
	
	public Timestamp convertDateToTimestamp(Date d) {
		currentDate = d;
		timeStamp = new Timestamp(currentDate.getTime());
		return timeStamp;

	}
	
	
	public static String currentDateTimeinFormat(String format){		
		try{
		 Date dNow = new Date( );
	     SimpleDateFormat ft =  new SimpleDateFormat (format); //yyyyMMddhhmmss
	     return (ft.format(dNow));
		}catch(Exception e){
			LOG.error("Exception in CommonUtil.currentDateTimeinFormat method : " + e);
		}
		return null;
	}
	
	/**
	 * To get Current Date only in java.sql.Date format
	 * 
	 * @return
	 */
	public java.sql.Date currentSQLDate() {
		currentDate=new Date();
		 java.sql.Date date = new java.sql.Date(currentDate.getTime());
		 
		return date;

	}
	
	/**
	 * To get Only Current Time in java.sql.Timeformat 
	 * 
	 * @return
	 */
	public Time currentSQLTime() {
		currentDate=new Date();
		Time time = new Time(currentDate.getTime());
		 
		return time;

	}
	

	/**
	 * 
	 * Convert a object to String.
	 * 
	 * @param objArr
	 *            the obj arr
	 * @return converted string
	 */

	public static String convertObjectToStringNull(Object objArr) {

		String convertedString = null;

		if (objArr != null) {
			convertedString = String.valueOf(objArr);
		}

		return convertedString;

	}

	/**
	 * 
	 * Convert a String to Bigdecimal.
	 * 
	 * @param str
	 *            the str
	 * @return converted big decimal value
	 */

	public static BigDecimal convertStringToBigDecimal(String str) {

		BigDecimal bigDecimalVal = null;

		if (!str.isEmpty()) {
			bigDecimalVal = new BigDecimal(str);
		}

		return bigDecimalVal;

	}

	/** Z014433 changes for B000021 starts here **/

	/**
	 * 
	 * Get Production Order Stage Code details based on stage code
	 * 
	 * @param stage
	 *            code
	 * @return Production Order Stage Code
	 */

	public static String getPrdOrdrStgCd(String stageCode) {

		String prdOrderStageCode = null;

		if (stageCode != null) {
			if (stageCode.equals(PDConstants.DRAFT_D1)
					|| stageCode.equals(PDConstants.DRAFT_D2))
				prdOrderStageCode = Integer.toString(PDConstants.INT_TEN);
			else if (stageCode.equals(PDConstants.FINAL_F1)
					|| stageCode.equals(PDConstants.FINAL_F2))
				prdOrderStageCode = Integer.toString(PDConstants.INT_TWENTY);
		}

		return prdOrderStageCode;

	}

	/** Z014433 changes for B000021 ends here **/

	/**
	 * 
	 * To calculate maximum production month
	 * 
	 * @param hrzn
	 * @param ordrTkBsMnth
	 * @return calculated maximum production month
	 * @throws ParseException
	 */
	public static String prdMnthCal(String ordrTkBsMnth, String hrzn)
			throws ParseException {
		DateFormat formatter = new SimpleDateFormat(
				PDConstants.DATE_FORMAT_MONTH);
		Date ordrtkBsMnth = formatter.parse(ordrTkBsMnth);
		int horizn = (Integer.parseInt(hrzn)) - 1;
		String maxMonth = formatter.format(CommonUtil.addMonthToDate(
				ordrtkBsMnth, horizn));
		return maxMonth;
	}

	public static String convertDateToString(Date covertToString, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(covertToString);
	}

	public static void logMessage(String messageID, String processID,
			String[] messageParams) {

		switch (processID) {

		case CONSTANT_V6:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4])
					.replace(ERROR_MESSAGE_6, messageParams[5]));
			break;

		case CONSTANT_V5:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4]));
			break;

		case CONSTANT_V4:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3]));
			break;

		case CONSTANT_V3:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2]));
			break;

		case CONSTANT_V2:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1]));
			break;

		case CONSTANT_V1:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0]));
			break;
		}
	}
	
	//for Error Types Log
	public static void logErrorMessage(String messageID, String processID,
			String[] messageParams) {

		switch (processID) {

		case CONSTANT_V6:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4])
					.replace(ERROR_MESSAGE_6, messageParams[5]));
			break;

		case CONSTANT_V5:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4]));
			break;

		case CONSTANT_V4:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3]));
			break;

		case CONSTANT_V3:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2]));
			break;

		case CONSTANT_V2:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1]));
			break;
			
		case CONSTANT_V1:
			LOG.info(messageID.replace(ERROR_MESSAGE_1, messageParams[0]));
			break;
		}
	}

	public static void stopBatch() {
		LOG.info("Batch Stopped.");
		System.exit(0);
		
		 
	}

	public static String getInputParamB8(String productionStageCd) {
		String stageCode = null;
		if (productionStageCd.equals(TEN)) {
			stageCode = STAGE_MESSAGE_1;
		} else if (productionStageCd.equals(TWENTY)) {
			stageCode = STAGE_MESSAGE_2;
		} else {
			LOG.error(INPUT_PARAM_FAILURE_2);
			stopBatch();
		}
		return stageCode;
	}

	/**
	 * Method to convert Big Decimal to String .
	 *
	 * @param objVal
	 *            the obj val
	 * @return the int
	 */
	public static String bigDecimaltoString(Object objVal) {
		BigDecimal bVal = (BigDecimal) objVal;
		return (String.valueOf(bVal));
	}

	/**
	 * @param prdnMnth
	 * @return first two digits for production order number
	 */
	public static String generatePrdnOrdrNoTwoDgts(String prdnMnth) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String firstDigit = findPrdnOrdrNoFirstDigit(prdnMnth.substring(4, 6));
		String secondDigit = prdnMnth.substring(0, 4).substring(3, 4);

		LOG.info("Production Order Number, Second digit value is : "
				+ secondDigit);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return firstDigit + secondDigit;
	}

	/**
	 * @param mnth
	 * @return first digit for production order number
	 */
	private static String findPrdnOrdrNoFirstDigit(String mnth) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		LOG.info("Production month -- MM is : " + mnth);
		String frstDgtVal = null;

		switch (mnth) {
		case B000027Constants.ONE:
			frstDgtVal = B000027Constants.JAN_01;
			break;
		case B000027Constants.TWO:
			frstDgtVal = B000027Constants.FEB_02;
			break;
		case B000027Constants.THREE:
			frstDgtVal = B000027Constants.MAR_03;
			break;
		case B000027Constants.FOUR:
			frstDgtVal = B000027Constants.APR_04;
			break;
		case B000027Constants.FIVE:
			frstDgtVal = B000027Constants.MAY_05;
			break;
		case B000027Constants.SIX:
			frstDgtVal = B000027Constants.JUN_06;
			break;
		case B000027Constants.SEVEN:
			frstDgtVal = B000027Constants.JUL_07;
			break;
		case B000027Constants.EIGHT:
			frstDgtVal = B000027Constants.AUG_08;
			break;
		case B000027Constants.NINE:
			frstDgtVal = B000027Constants.SEP_09;
			break;
		case B000027Constants.TEN:
			frstDgtVal = B000027Constants.OCT_10;
			break;
		case B000027Constants.ELEVEN:
			frstDgtVal = B000027Constants.NOV_11;
			break;
		case B000027Constants.TWELVE:
			frstDgtVal = B000027Constants.DEC_12;
			break;
		default:
			break;
		}

		LOG.info("Production Order Number, First digit value is : "
				+ frstDgtVal);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return frstDgtVal;
	}

	/**
	 * 
	 * To calculate production months list
	 * 
	 * @param hrzn
	 * @param ordrTkBsMnth
	 * @return production month list
	 */
	public static List<String> getProductionMonths(String carSeriesHrzn,
			String OrdrTakeBaseMnth) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		LOG.info("Production Months Calculation --> Horizon is   : "
				+ carSeriesHrzn + " and order take base month  is :"
				+ OrdrTakeBaseMnth);

		String prodMonth = null;

		Integer year = CommonUtil.stringtoInt(OrdrTakeBaseMnth.substring(0, 4));
		Integer month = CommonUtil.stringtoInt(OrdrTakeBaseMnth.substring(4));

		List<String> prodMnthList = new ArrayList<String>();

		if (!PDConstants.CONSTANT_ZERO.equals(carSeriesHrzn)) {
			for (int i = 0; i < CommonUtil.stringtoInt(carSeriesHrzn); i++) {
				if (month <= 12) {
					if (month < 10)
						prodMonth = "" + year + 0 + (month);
					else
						prodMonth = "" + year + (month);
					month = month + 1;
				} else {
					month = 1;
					year = year + 1;
					prodMonth = "" + year + 0 + 1;
					month = month + 1;
				}
				prodMnthList.add(prodMonth);
			}
		}
		LOG.info("Calculated Production Months List size is : "
				+ prodMnthList.size());

		return prodMnthList;
	}

	/*
	 * get the value of columns order from cmn interface date using reflection
	 */
	public static String getBeanValue(
			Object item, Integer order) {

		// no paramater
		Class noparams[] = {};

		String value = null;

		try {
			// loading the AppTest at runtime
			String className = item.getClass().getCanonicalName();
			Class cls = Class.forName(className);
			// Object obj = cls.getClass();
			String methodToGet = "getCol" + order;

			// call the getCol1 method
			Method method = cls.getDeclaredMethod(methodToGet, noparams);
			value = (String) method.invoke(item, null);

		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE, ex);
		}

		return value;
	}

	/*
	 * Set the converted date into curresponding setter method for the column
	 * order
	 */
	@SuppressWarnings("unchecked")
	public static void setBeanValue(
			Object item, Integer order,
			String defValue) {
		Class[] paramString = new Class[1];
		paramString[0] = String.class;
		try {
			// loading the AppTest at runtime
			String className = item.getClass().getCanonicalName();
			Class cls = Class.forName(className);
			// Object obj = cls.getClass();
			String methodToGet = "setCol" + order;
			// call the getCol1 method
			Method method = cls.getDeclaredMethod(methodToGet, paramString);
			method.invoke(item, defValue);

		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE, ex);
		}
	}
	
	/*
	 * set the value of columns order from B000059FileVO using reflection
	 */
	public static String getBeanValue(B000059FileVO item, Integer order) {
		// no paramater
		Class noparams[] = {};
		String value = null;

		try {
			// loading the AppTest at runtime
			String className = item.getClass().getCanonicalName();
			Class cls = Class.forName(className);
			String methodToGet = "getCol" + order;

			// call the getCol1 method
			Method method = cls.getDeclaredMethod(methodToGet, noparams);
			value = (String) method.invoke(item, null);
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE, ex);
			LOG.info("Exception in getBeanValue B000059ValidateUtility"
					+ ex.getStackTrace());
		}

		return value;
	}

	public String getlogErrorMessage(String message,
			Map<String, String> paramMap) {
		if (paramMap != null) {
			for (String key : paramMap.keySet()) {
				message = message.replaceAll("&" + key, paramMap.get(key)
						.toString());
			}
		}

		return message;
	}
	
	public static FTPClient openFtpConnection(String server, int port, String user, String pass){
		
		try{
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect(server, port);
	        ftpClient.login(user, pass);
	        ftpClient.enterLocalPassiveMode(); 
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        return ftpClient;
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
			return null;
		}
		
	}

public static SshClient openSFTPConnection(B000065FTP_MST_INTERFACE_Details ftpDetails, B000065CommonUtilityService commonutil){

		
	    SshClient ssh = null;
	    
        try {
            
        	String sftpHostName = ftpDetails.getFtpServer() ; //"10.244.24.90"; //propertyObj.getProperty(Commonconstants.FTP_HOST_NAME).toString();
        	//String sftpHostName =  "xxx";
			int sftpPortNum = Integer.parseInt(ftpDetails.getFtpPort());
            ssh = new SshClient();
            
            LOG.info("Server : " + sftpHostName);
            LOG.info("sftpPortNum : " + sftpPortNum);            
            ssh.connect(sftpHostName, sftpPortNum, new AlwaysAllowingConsoleKnownHostsKeyVerification());
            return ssh;
         }
        catch (Exception e) {
            LOG.error("Error : " , e);
            commonutil.getErrorMsgMap().put("SFTP_CONN_ERROR", Arrays.toString(e.getStackTrace()));
        }
		return ssh;   
	}
	public static SftpClient openSFTPClient(SshClient ssh, B000065FTP_MST_INTERFACE_Details ftpDetails, B000065CommonUtilityService commonutil) {

		SftpClient client = null;
		  try {
            
        	String sftpHostName = ftpDetails.getFtpServer() ; // propertyObj.getProperty(Commonconstants.FTP_HOST_NAME).toString();
			String sftpUserName = ftpDetails.getFtpUserName();  //propertyObj.getProperty(Commonconstants.FTP_USER_NAME).toString();
			String sftpPassword = ftpDetails.getFtpPassword(); //propertyObj.getProperty(Commonconstants.FTP_PASS_WORD).toString();
			int sftpPortNum = Integer.parseInt(ftpDetails.getFtpPort());//Integer.parseInt(propertyObj.getProperty(Commonconstants.FTP_PORT_NUM));
			
			LOG.info("sftpHostName : " + sftpHostName);
            LOG.info("sftpUserName : " + sftpUserName);   
            LOG.info("sftpPassword : " + sftpPassword);  
            LOG.info("sftpPortNum : " + sftpPortNum);  

			ssh = new SshClient();
            ssh.connect(sftpHostName, sftpPortNum, new AlwaysAllowingConsoleKnownHostsKeyVerification());

            boolean ss = ssh.isConnected();
            if (ss) {
                PasswordAuthenticationClient passwordAuthenticationClient = new PasswordAuthenticationClient();
                passwordAuthenticationClient.setUsername(sftpUserName);
                passwordAuthenticationClient.setPassword(sftpPassword);

                int result = ssh.authenticate(passwordAuthenticationClient);

                if (result != AuthenticationProtocolState.COMPLETE) {
                    throw new PdApplicationException();
                }
                client = ssh.openSftpClient();                                        
            }
        }
        catch (Exception e) {
        	LOG.info(e);
            commonutil.getErrorMsgMap().put("SFTP_CLIENT_ERROR", e.getMessage());
        }
		  return client;    
	}       

	@SuppressWarnings("unchecked")
	public static Map<String, MstPrmtr> getPrmtrMstDetails(
			EntityManager entityManager) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		Map<String, MstPrmtr> details = new HashMap<String, MstPrmtr>();

		List<MstPrmtr> mstPrmtrLst = entityManager.createQuery(
				new StringBuilder(
						"SELECT PM FROM MstPrmtr PM WHERE PM.id.prmtrCd IN ('")
						.append(B000059Constants.B000059_RECIEV_DATE_FRMT)
						.append("','")
						.append(B000059Constants.B000059_PROCESS_PATH)
						.append("','")
						.append(B000059Constants.B000059_SUCCESS_FOLDER)
						.append("','")
						.append(B000059Constants.B000059_FAILURE_FOLDER)
						.append("','")
						.append(PDConstants.B000065_SEND_SUCCESS_PATH)
						.append("','")
						.append(PDConstants.B000061_TIME_STAMP_FMT)
						.append("') order by id.seqNo").toString())
				.getResultList();

		for (MstPrmtr prmtr : mstPrmtrLst) {
			details.put(prmtr.getId().getPrmtrCd(), prmtr);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return details;

	}

	public static void moveFile(File file, String sendSuccessPath)
			throws Exception {

		File destPath = new File(sendSuccessPath);
		if (null != file && null != sendSuccessPath) {

			if (destPath.isDirectory()) {
				File dP = new File(destPath + File.separator + file.getName());
				if ( dP.canExecute() && dP.canRead() && dP.canWrite()){
					if(file.renameTo(dP)){
						LOG.error("File Renamed....Successfully....");
					}else{
						LOG.error("Not able to renamed file....");
					}
				}else{
					LOG.error(dP.getAbsolutePath() + " permission...issues");
				}
				
			}
		}

	}

	/*
	 * Common method for converting por codes as a string from command line
	 * arguments
	 */
	public static String getPorcodes(String args[]) {
		StringBuilder values = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			values.append("'"+args[i] + "',");			
		}
		int ind = values.toString().lastIndexOf(",");
		values = values.replace(ind, ind + 1, "");
		return values.toString();
	}
	
	public static String replacePrmtWithMsg(String msgId,String parameterId,String replaceMsg){
					
		return msgId.replaceAll(parameterId, replaceMsg);
		
	}

	public static Object replacePrmtWithMsg(String msgId, String[] strings,
			String[] strings2) {
		try{
			
			if(strings.length == strings2.length){
				for (int i = 0; i < strings2.length; i++) {
					msgId = msgId.replaceAll(strings[i], strings2[i]);
				}
			}
			
		}catch(Exception e){
			LOG.error(e);
		}
		
		return msgId;
	}
	

	public static Timestamp createTimeStamp() {
		return new Timestamp((new Date()).getTime());
	}

	public static String offlnDtCal(String ordrTkBsMnth, String hrzn)
			throws ParseException {
		DateFormat formatter = new SimpleDateFormat(PDConstants.DATE_FORMAT);
		Date ordrtkBsMnth = formatter.parse(ordrTkBsMnth);
		int horizn = (Integer.parseInt(hrzn)) - 1;
		String maxMonth = formatter.format(CommonUtil.addDateToDate(
				ordrtkBsMnth, horizn));
		return maxMonth;
	}

	/**
	 * Adds the Date to date.
	 *
	 * @param date
	 *            the date
	 * @param addValue
	 *            the add value
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	@SuppressWarnings("static-access")
	public static Date addDateToDate(Date date, int addValue)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, addValue);
		return calendar.getTime();
	}

	public static Date addDayToDate(Date date, int addValue)
			{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, addValue);
		return calendar.getTime();
	}
	@SuppressWarnings("static-access")
	public static Map<String, WeeklyClndrBean> getWeeklyOrdlsMap(
			MstWkNoClndr mstWkNoClndr) throws ParseException {

		Map<String, WeeklyClndrBean> weekNoDtlsMap = new HashMap<>();
		String startDate = mstWkNoClndr.getWkStrtDate();
		Date startDat;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				PDConstants.DATE_FORMAT);
		startDat = simpleDateFormat.parse(startDate.trim());
		calendar.setTime(startDat);
		for (int i = 1; i <= 7; i++) {
			WeeklyClndrBean weeklyClndrBean = new WeeklyClndrBean();
			startDate = simpleDateFormat.format(calendar.getTime());
			weeklyClndrBean.setOfflnDt(startDate);
			weeklyClndrBean.setProdMnth(mstWkNoClndr.getId().getProdMnth());
			weeklyClndrBean.setWkNoYr(mstWkNoClndr.getWkNoYear());
			weeklyClndrBean.setProdWkNo(mstWkNoClndr.getId().getProdWkNo());
			weeklyClndrBean.setWkStrdDt(mstWkNoClndr.getWkStrtDate());
			weeklyClndrBean.setWkEndDt(mstWkNoClndr.getWkEndDate());
			weekNoDtlsMap.put(startDate, weeklyClndrBean);
			calendar.add(calendar.DATE, 1);
		}
		return weekNoDtlsMap;
	}

	/**
	 * This method used to add end of line character
	 * 
	 * @param localPath
	 *            local directory path
	 * @param newFile
	 *            new filename
	 * @param maxEndPosition
	 *            maximum end position
	 * @return status as true or false
	 */
	public static File removeEOLInLastRecord(String localPath, File newFile,Integer maxEndPosition,String tempPrefix) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		RandomAccessFile randAccessFile = null;
		FileChannel inChannel = null;
		StringBuilder stringBuff = null;
		ByteBuffer buf = null;
		int bytesRead;
		String tempFileName = null;
		try {
			tempFileName = new StringBuilder(localPath).append(File.separator).append(tempPrefix).append(newFile.getName()).toString();
			LOG.info("Temp File Name :" + tempFileName);
			file = new File(tempFileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file.getAbsoluteFile());
			buffWriter = new BufferedWriter(fileWriter);

			randAccessFile = new RandomAccessFile(newFile, "rw");

			inChannel = randAccessFile.getChannel();

			// create buffer with capacity of maxEndPosition bytes
			buf = ByteBuffer.allocate(maxEndPosition+2);
			bytesRead = inChannel.read(buf);
			// read into buffer.
			while (bytesRead != -1) {
				stringBuff = new StringBuilder();
				buf.flip(); // make buffer ready for read
				while (buf.hasRemaining()) {
					stringBuff.append((char) buf.get()); // read 1 byte at a time
				}
				buf.clear(); // make buffer ready for writing
				bytesRead = inChannel.read(buf);
				
				if(bytesRead !=-1 ){
					//sb.append(System.lineSeparator());
					buffWriter.write(stringBuff.toString());
				}
				else{
					buffWriter.write(stringBuff.toString().replaceAll(System.lineSeparator(), ""));
				}
				
				
				buffWriter.flush();
			}

		} catch (IOException e) {
			LOG.error(ERROR_MESSAGE, e);
		} finally {
			try {
				randAccessFile.close();

			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}
			try {
				buffWriter.close();
			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}

			
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return file;
	}
	
	/**
	 * Find the max of end positon
	 * 
	 * @param mstIfLayout
	 *            interface master layout list
	 */
	public static Integer getMaxEndPosition(List<MstIfLayout> mstIfLayout) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Integer max = 0;
		Integer value = 0;
		MstIfLayout msgLayout = null;
		try {
			for (Iterator<MstIfLayout> iterator = mstIfLayout.iterator(); iterator
					.hasNext();) {
				msgLayout = (MstIfLayout) iterator.next();
				value = Integer.parseInt(msgLayout.getEndPosition()
						.toBigIntegerExact().toString());

				if (max < value) {
					max = value;
				}
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		} finally {
			value = null;
			msgLayout = null;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return max;
	}
	
	public static String getFirstDay(Date d) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat(PDConstants.DATE_FORMAT);
        return sdf1.format(dddd);
    }
 
    public static String getLastDay(Date d) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat(PDConstants.DATE_FORMAT);
        return sdf1.format(dddd);
    }
    
    public static void logWarningMessage(String messageID, String processID,
			String[] messageParams) {

		switch (processID) {

		case CONSTANT_V6:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4])
					.replace(ERROR_MESSAGE_6, messageParams[5]));
			break;

		case CONSTANT_V5:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3])
					.replace(ERROR_MESSAGE_5, messageParams[4]));
			break;

		case CONSTANT_V4:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2])
					.replace(ERROR_MESSAGE_4, messageParams[3]));
			break;

		case CONSTANT_V3:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1])
					.replace(ERROR_MESSAGE_3, messageParams[2]));
			break;

		case CONSTANT_V2:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0])
					.replace(ERROR_MESSAGE_2, messageParams[1]));
			break;

		case CONSTANT_V1:
			LOG.warn(messageID.replace(ERROR_MESSAGE_1, messageParams[0]));
			break;
		}
	}

	public static void isFileRenamedTo(boolean moved, File fileToMove,
			File destFile) {
			if(moved){
				LOG.info("File moved From : " + fileToMove.getAbsolutePath() + "; To : " + destFile.getAbsolutePath());
			}else{
				LOG.info("Error while File moved From : " + fileToMove.getAbsolutePath() + "; To : " + destFile.getAbsolutePath());
			}	
	}
	
	/*
	 * to add input minutes to given time
	 */
	public static Date addMinutesToDate(int minutes, Date date)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	
	/*
	 * to replace time of one date to other date
	 */
	public static Date swapTime(Date target,Date source)
	{
		try
		{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(target);
		Calendar s=Calendar.getInstance();
		s.setTime(source);
		calendar.set(Calendar.HOUR_OF_DAY, s.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, s.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, s.get(Calendar.SECOND));
		return calendar.getTime();
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			return null;
		}
		 
	}

	/*
	*@param st_dt
	*@return
	*String
	*/
	public static String getDayNameFromDate(Date st_dt) {
		try{
		 SimpleDateFormat smf2=new SimpleDateFormat("EEEE");
		return smf2.format(st_dt);
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
			return null;
		}
	}
	/*
	*@param st_dt
	*@return
	*String
	*/
	public static int getDayNoFromDate(Date st_dt) {
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(st_dt);
		 
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
}
