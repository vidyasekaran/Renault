/*
 * System Name     : Post Dragon 
 * Sub system Name : Batch
 * Function ID     : B000059/B000062
 * Module          :
 * Process Outline : This program rearranges the file resources attached to MultiResourceItemReader in LIFO or FIFO order based on interface master processing order col value
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.PDConstants;

public class B000059MultipleFileAdditionListener implements StepExecutionListener,
		ApplicationContextAware {
	
	/**
	 *  Constant LOG 
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059MultipleFileAdditionListener.class.getName());
	
	/** 
	 * custom log message for B000059
	 */
	CustomLogMessage clm = null;

	/**
	 * Stores the resources
	 */
	private Resource[] resources;
	
	/**
	 * Get application context
	 */
	private ApplicationContext applicationContext;

	
	/**
	 * common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	MstInterface interfaceMaster = null;

	/**
	 *Sets the application context
	 *
	 *@param arg0 ApplicationContext object
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {		
		this.applicationContext = arg0;
		LOG.info("setApplicationContext");
	}

	/**
	 * 
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		
		List<String> fileToMove = (ArrayList<String>) commonutility.getFileSpecVO().getFilesToMove();
		List<String> inValidFile = (ArrayList<String>) commonutility.getFileSpecVO().getInValidRecordLengthFile();
		
		fileToMove.removeAll(inValidFile);

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		// Moving files from main to processed folder before processing
		MultiResourceItemReader<?> reader = (MultiResourceItemReader<?>) applicationContext
				.getBean("multifileReader");		
				
		JobParameters jobInputs = stepExecution.getJobExecution().getJobParameters();
		String interfaceFileID = jobInputs.getString(B000059Constants.INTERFACE_FILE_ID);

		interfaceMaster = (MstInterface) commonutility.getFileSpecVO()
				.getInterfaceMaster().get(interfaceFileID);
		String fileNameFormat = interfaceMaster.getFilenameFormat();
		String fileType = interfaceMaster.getFileType();
		String processingFolder = commonutility.getFileSpecVO().getPrmtrMasterDetails().get(B000059Constants.B000059_PROCESS_PATH).getVal1();
		
		
		if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_TWO)) 
		{
			
			fileNameFormat = B000059Constants.EOLPREFIX+ fileNameFormat;
		}
		
	
		
		
        String fileNamewithoutExt = fileNameFormat.substring(0, fileNameFormat.lastIndexOf("."));
        String fileExt = fileNameFormat.substring( fileNameFormat.lastIndexOf(".")+1, fileNameFormat.length() );
		
		String resourceName="";
		
		
		String fileNameExtn = fileNameFormat.substring(
				fileNameFormat.lastIndexOf("."), fileNameFormat.length());
	
		try 
		{
			// If fileType is 1 EOL is present 
			if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_ONE)) 
			{
				resourceName = B000059Constants.FILE_PREFIX + processingFolder	+ fileNamewithoutExt +B000059Constants.FILE_PATTERN+fileExt;
				
			}
			else if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_TWO))  ////Added to pickup EOL files
			{
				resourceName = B000059Constants.FILE_PREFIX + processingFolder	+ B000059Constants.EOLPREFIX + fileNamewithoutExt +B000059Constants.FILE_PATTERN+fileExt;
				
			}
			
			resources = applicationContext.getResources(resourceName);
			 

		} catch (IOException e) {			
			LOG.error(ERROR_MESSAGE, e);						
		}

		reader.setResources(resources);

		// MST_INTERFACE.multipleFiles ==1 is Single file
		if (interfaceMaster.getMultipleFiles().equalsIgnoreCase(PDConstants.CONVERSION_LAYER_MULTIPLE_FILES_1)) {
			// Only one file present so no FIFO/LIFO ordering required			
			getTheNewestFile(fileNameFormat, processingFolder, fileNameExtn,
					reader);

		} else if(PDConstants.CONVERSION_LAYER_MULTIPLE_FILES_2.equalsIgnoreCase(interfaceMaster.getMultipleFiles()) ) {
			// MST_INTERFACE.PROCESSING_ORDER =1 FIFO , 2=LIFO
			if (PDConstants.CONVERSION_LAYER_PROCESS_ORDER_1.equalsIgnoreCase(interfaceMaster.getProcessingOrder())) {
				addFilesToResource(reader, fileToMove);
			} else if (PDConstants.CONVERSION_LAYER_PROCESS_ORDER_2.equalsIgnoreCase(interfaceMaster.getProcessingOrder())) {
				addFilesToResource(reader, fileToMove);
			} 
		} else if (PDConstants.CONVERSION_LAYER_MULTIPLE_FILES_0.equalsIgnoreCase(interfaceMaster.getMultipleFiles())) {
			// Check Requirement Doc
			clm = CustomLogMessage.M00132;
			clm.fetchLogMessage(interfaceFileID ,"File Not Found");
			LOG.info("More than one file found for processing");
		} 
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	public void addFilesToResource(MultiResourceItemReader<?> reader,
			List fileToMove) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Resource[] resource = new Resource[fileToMove.size()];
		int i = 0;

		for (Iterator iterator = fileToMove.iterator(); iterator.hasNext();) {
			String path = (String) iterator.next();
			resource[i++] = new FileSystemResource(path);
		}
		reader.setResources(resource);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	public void setLIFOrder(MultiResourceItemReader<?> reader) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		reader.setComparator(new Comparator<Resource>() {
			@Override
			public int compare(Resource r1, Resource r2) {
				int compValue = 0;
				try {
					compValue = Long.valueOf(r1.getFile().lastModified())
							.compareTo(r2.getFile().lastModified());
				} catch (IOException ex) {
					LOG.error(ERROR_MESSAGE, ex);
					LOG.info("IOException in setLIFOrder" + ex.getStackTrace());
				}
				LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
				return compValue;
			}
		});

	}


	public void getTheNewestFile(String filenameFormat, String filePath,
			String ext, MultiResourceItemReader<?> reader) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter(
				filenameFormat.substring(0, filenameFormat.lastIndexOf("."))
						+ "*" + ext);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);			
		}
		
		Resource[] resource = new Resource[1];

		resource[0] = new FileSystemResource(files[0]);

		reader.setResources(resource);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}

}
