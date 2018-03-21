/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-CustomLog
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.util;

import java.io.File;
import java.text.SimpleDateFormat;

import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.DOT;

import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class CustomLog.
 */
public class CustomLog extends FileAppender{
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CustomLog.class);
	
	/**
	 *  
	 * To Set the Log File Name based on Environment argument value.
	 *
	 * @param fileName the new file
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	@Override
	public void setFile(String fileName) {
		String logFile = "";
		String dirPath = fileName.substring(0, fileName.lastIndexOf(BACK_SLASH));
		String dirName = fileName.substring(fileName.lastIndexOf(BACK_SLASH)+1,fileName.indexOf(DOT));
		String dirPathName = dirPath+BACK_SLASH+dirName+BACK_SLASH;
		String logFileName = fileName.substring(fileName.lastIndexOf(BACK_SLASH)+1);
		File file = new File(dirPathName);
		if(!file.exists()) {
			file.mkdir();
		}
		String  logFilePath = dirPathName+logFileName;
		if(logFilePath.indexOf("%timestamp") > 0 ){
			Date logDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
			logFile = logFilePath.replaceAll("%timestamp", format.format(logDate));
		}
		
		LOG.info("Log File Creation"+logFile);
		super.setFile(logFile);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#setLayout(org.apache.log4j.Layout)
	 */
	@Override
	public void setLayout(Layout layout) {
		
		super.setLayout(layout);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.log4j.FileAppender#setBufferSize(int)
	 */
	@Override
	public void setBufferSize(int bufferSize) {
		
		super.setBufferSize(bufferSize);
	}
	

}
