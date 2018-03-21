/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program performs Record Length validation 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileProcessingStatusVO;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000059RecordLengthTasklet implements Tasklet {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059ColumnRangeTasklet.class.getName());

	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		MstInterface interfaceMaster = null;
				
		
		String interfaceFileID = commonutility.getFileSpecVO().getInterfaceFileId();

		interfaceMaster = (MstInterface) commonutility.getFileSpecVO().getInterfaceMaster().get(interfaceFileID);
		
		String fileNameFormat = interfaceMaster.getFilenameFormat();
		
		String fileType = interfaceMaster.getFileType();
		
		String processingFolder = commonutility.getFileSpecVO().getPrmtrMasterDetails()
				.get(B000059Constants.B000059_PROCESS_PATH).getVal1();
		
		
		if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_TWO)){			
			fileNameFormat = B000059Constants.EOLPREFIX+ fileNameFormat;
		}

		String fileNamewithoutExt = fileNameFormat.substring(0, fileNameFormat.lastIndexOf("."));
        String fileExt = fileNameFormat.substring( fileNameFormat.lastIndexOf(".")+1, fileNameFormat.length() );
		
		String resourceName= "";
				
		// If fileType is 1 EOL is present 
		if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_ONE)){
			resourceName = fileNamewithoutExt +B000059Constants.FILE_PATTERN+fileExt;			
		}
		else if (fileType.equalsIgnoreCase(B000059Constants.FILE_TYPE_TWO))  ////Added to pickup EOL files
		{
			resourceName = B000059Constants.EOLPREFIX + fileNamewithoutExt +B000059Constants.FILE_PATTERN+fileExt;
			
		}
		
		File dir = new File(processingFolder);
		FileFilter fileFilter = new WildcardFileFilter(resourceName);
		File[] files = dir.listFiles(fileFilter);
						
		List<MstIfLayout> mstIfLayoutList = (List<MstIfLayout>) commonutility.getFileSpecVO().getInterfaceLayoutByOrder().get(interfaceFileID);
		
		ArrayList<Integer> fileList = new ArrayList<Integer>();
		
		Integer max = commonutility.getMaxEndPosition(mstIfLayoutList);
		
		String failurePath = commonutility.getFileSpecVO().getPrmtrMasterDetails().get(B000059Constants.B000059_FAILURE_FOLDER).getVal1();
		
		List <B000059FileProcessingStatusVO> listStatus = commonutility.getFileSpecVO().getFilesToProcessList();
		
		for(int i=0; i < files.length; i++){
			LOG.info("Resource [" + i + "] " + files[i].getPath());				
			if(commonutility.checkRecordLengthVal(files[i], max )){
				LOG.info(" Record Length Validation for File : " + files[i].getPath() + " is OK");
				fileList.add(i);					
			}else{
				LOG.info(" Record Length Validation for File : " + files[i].getPath() + " is inValid file");
				
				LOG.error(PDMessageConsants.M00335.replaceAll("&1", interfaceFileID));
				
				commonutility.getFileSpecVO().setRecordLengthValidationFlag(Boolean.TRUE);
				// Adding the invalid file name.
				commonutility.getFileSpecVO().getInValidRecordLengthFile().add(files[i].getPath());
				//Remove from the location .
				boolean moved = files[i].renameTo(new File(failurePath	+ files[i].getName()));				
				if(moved){
					LOG.info("File moved successfully in the failure location : " + failurePath + "-" + files[i].getName());
				}else{
					LOG.info("Error while move unsuccessfuly files into the failure location : " + failurePath + "-" + files[i].getName());
				}		
				
				
				for (Iterator<B000059FileProcessingStatusVO> iterator = listStatus.iterator(); iterator.hasNext();) {
					B000059FileProcessingStatusVO b000059FileProcessingStatusVO = (B000059FileProcessingStatusVO) iterator
							.next();
					if(b000059FileProcessingStatusVO.getFileName().equals(files[i].getName())){
						b000059FileProcessingStatusVO.setError(Boolean.TRUE);
					}
				}
			}
		}
						
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}	

}
