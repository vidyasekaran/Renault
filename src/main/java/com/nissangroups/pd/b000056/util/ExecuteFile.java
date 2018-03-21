/**
  * System Name :Post Dragon
 * Sub system Name :Batch 
 * Function ID:PST-DRG-B000056
 * Module :CM Common
 * Process Outline :Main class which is used to execute .sh files.
 * <Revision History>
 *  Date 			Name(Company Name) 					Description 
 *  ------------------------------ --------------------- ----------
 *  18-11-15		z015883(RNTBCI)						New Creation
 *        
 *  */

package com.nissangroups.pd.b000056.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.model.MstJob;
import com.nissangroups.pd.util.PDConstants;

/**
 * @author z015883 this class is used to execute .sh files (B000056 batch
 *         -P0006)
 */
 
public class ExecuteFile {

	private static final Log LOG = LogFactory.getLog(ExecuteFile.class);
	public static String prcStatus = "";
	public static int exitcode = 1;
	public static String filename="";
	
	
	/*
	*@param job
	*@param intervalPeriod
	*@return int
	*/
	public static int executeSHFile(MstJob job, long intervalPeriod) {
		Process p;
		 
		exitcode = B000056Constants.INT_ONE;
		try {
			String path=job.getShellPath();
			LOG.info("Starting execution of .SH file " + path);
			filename=path.substring(path.lastIndexOf(B000056Constants.BACK_SLASH)+1, path.lastIndexOf(B000056Constants.DOT));
			String params=getInputParams(job.getInputParam());
			List<String>param=new ArrayList<String>();
			param.add(path);
			String str[]=params.split(B000056Constants.SPACE);
			for (String string : str) {
				param.add(string);
			}
			ProcessBuilder pb=new ProcessBuilder().inheritIO().command(param);
			p=pb.start();
			 
			prcStatus = B000056Constants.RUNNING;
			startTimer(intervalPeriod, p);
			p.waitFor();
			if (exitcode !=B000056Constants.INT_HUNDRED_ONE)
				exitcode = p.exitValue();
			prcStatus = "";

			

		} catch (IOException e) {
			LOG.info("SH File Not Found at Given path");
			LOG.error(PDConstants.EXCEPTION+e);
			return exitcode=B000056Constants.INT_HUNDRED_ONE;
		} catch (InterruptedException e) {
			LOG.info("SH file Not Executed Properly.");
			LOG.error(PDConstants.EXCEPTION+e.getMessage());
		}
		catch(Exception e)
		{
			LOG.error(e);
		}
		try{
		Thread.sleep(2000);}
		catch(Exception e)
		{
			LOG.error(e);
		}
		LOG.info("SH file Executed with Exitvalue " + exitcode);
		return exitcode;
	}

	/*
	*@param intervalPeriod
	*@param p
	*@return void
	*/
	public static void startTimer(long intervalPeriod, Process p) {
		final Process p1 = p;
		final Timer timer = new Timer();
		LOG.info("Timer Started for Job");
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				if (prcStatus.equalsIgnoreCase(B000056Constants.RUNNING)) {
					p1.destroy();
					LOG.info("Taking more than Specified Execution Time. So Interruptting the .SH file Execution");
					LOG.info("Process Terminated..");
					exitcode =B000056Constants.INT_HUNDRED_ONE;
					timer.cancel();
				} else {
					LOG.info("Allready Completed .SH file Execution");
					timer.cancel();

				}

			}
		};

		timer.schedule(task, intervalPeriod * 1000 * 60);// if period is in
															// minutes
	}
	
	//get the input param for respective batch using properties file
	
	/*
	*@param path
	*@return String
	*/
	public static String getInputParamsFromFile(String path)
	{
		Properties properties=new Properties();
		String params="";
		try {
			FileInputStream fis=new FileInputStream(new File(PDConstants.PROPERTIES_FILE_PATH));
			properties.load(fis);
			String filename=path.substring(path.lastIndexOf(B000056Constants.BACK_SLASH)+1, path.lastIndexOf(B000056Constants.DOT));
			params=properties.getProperty(filename);
			LOG.info("Input Parameters for [ "+filename+" ] are "+params);
			ExecuteFile.filename=filename;
			return params;
		} catch (FileNotFoundException e) {
			 
			LOG.error(e);

			

		} catch (IOException e) {
			 
			LOG.error(e);
			
		}
		return params;
		
	}
	
	/*
	 * get input parameter value from DB and replace dynamic values
	 */
	
	/*
	*@param param
	*@return String
	*/
	public static String getInputParams(String param)
	{
		LOG.info("Received Input Parameter from DB "+param);	 
		String str[]=param.split(" ");
		for(int i=0;i<str.length;i++)
		{
			if(str[i].contains(B000056Constants.AMPOCENT))
			{
				String temp=B000056Constants.inputParams.get(str[i].substring(1));
				param=param.replace(str[i], temp!=null?temp :B000056Constants.SPACE);
				
			}
			
			}
		LOG.info("Modified Input Parameter for Job "+param);
		return param;
		
	}

}
