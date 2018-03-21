package com.nissangroups.pd.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author z015883
 *
 */
public class LatestLogFile {
 
	private static final Log LOG = LogFactory.getLog(LatestLogFile.class);
	public static String getLatestFile(String path) throws Exception
	{
		 
		File f=new File(path);
	 	File latestFile=null;
	 	String ext="log";
	 	FileFilter fileFilter=new WildcardFileFilter("*.*");
	 	File[] files=f.listFiles(fileFilter);
	 	 
	 	if(files.length>0)
	 	{
	 		Arrays.sort(files,LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	 		latestFile=files[0];
	 		 LOG.info("latest file "+latestFile.getName());
	 		return latestFile.getName();
	 	}
	 	else
	 	{
	 		LOG.info("There are No log files in given Directory");
	 		return "no Files";
	 	}
		
		 
		 
		 
	}
}
