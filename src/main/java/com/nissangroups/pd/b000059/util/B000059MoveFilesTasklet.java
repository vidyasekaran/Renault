/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program is used to move files from staging to processed directory
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileProcessingStatusVO;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000059MoveFilesTasklet implements Tasklet {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059MoveFilesTasklet.class.getName());

	/**
	 * common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/**
	 * custom log message for B000059
	 */
	CustomLogMessage clm = null;

	String interfaceFileIdStr = "Interface File ID: ";

	/**
	 * Moves the files from local to processed directory as per the folder
	 * detail set in Parameter Master table.
	 * 
	 * @param contribution
	 *            Contribution object
	 * @param chunkContext
	 *            ChunkContext object
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		StepContext stepContext = chunkContext.getStepContext();
		String interfaceFileID = (String) stepContext.getJobParameters().get(
				B000059Constants.INTERFACE_FILE_ID);

		LOG.info("B000059MoveFilesTasklet : InterfaceFileId :"
				+ interfaceFileID);

		MstInterface mstInterface = (MstInterface) commonutility
				.fetchInterfaceMasterData().get(interfaceFileID);

		String localPath = "";
		File dir = null;
		File[] files = null;

		/*
		 * Fetch Single file - Filename is fetched exactly as mentioned in
		 * mstInterface.getFilenameFormat Assumption is that the
		 * mstInterface.getFilenameFormat should have Pattern like sample.txt or
		 * based on mstInterface.getFilenameChecktype() 1 or 2. We need to
		 * change this logic based on condition.
		 */

		if (mstInterface.getFilenameChecktype().equalsIgnoreCase(
				B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_1)) { // no need to
																	// check the
																	// Multiple_Files
																	// check
			localPath = mstInterface.getLocalPath() + File.separator
					+ mstInterface.getFilenameFormat();
			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_1 : localPath :"
					+ localPath);
			if (mstInterface.getFileType().equalsIgnoreCase(
					B000059Constants.PARAM_FILE_TYPE_2)) {
				localPath = mstInterface.getLocalPath() + File.separator
						+ B000059Constants.EOLPREFIX
						+ mstInterface.getFilenameFormat();
				LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_TYPE_2 : localPath :"
						.intern() + localPath);
			}

			files = new File[1];
			files[0] = new File(localPath);

			if (!(files[0].exists() && files[0].isFile())) {
				String errMsg = (String) CommonUtil.replacePrmtWithMsg(
						PDMessageConsants.M00109, new String[] { "&1" },
						new String[] { interfaceFileIdStr + interfaceFileID
								+ "[P0001]-[Local_system_path] = ".intern()
								+ mstInterface.getLocalPath()
								+ " [P0001]-[FileName_Format] = ".intern()
								+ mstInterface.getFilenameFormat() });
				LOG.error(errMsg);
				throw new Exception(errMsg);
			}
		} else if (mstInterface.getFilenameChecktype().equalsIgnoreCase(
				B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2)
				&& mstInterface.getMultipleFiles().equalsIgnoreCase(
						B000059Constants.MULTFILE_TWO)) {
			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : MULTFILE_TWO "
					.intern());

			FileFilter fileFilter = getFileFiler(mstInterface);

			localPath = mstInterface.getLocalPath();// + File.separator +

			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : localPath "
					.intern() + localPath);

			dir = new File(localPath);

			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : fileFilter "
					.intern() + fileFilter.toString());

			LOG.info("canExecute : ".intern() + dir.canExecute());
			LOG.info("canRead : ".intern() + dir.canRead());

			if (!(dir.canExecute() && dir.canRead())) {
				throw new Exception(new String(
						"Permission issues from reading above folder"));
			}

			files = dir.listFiles(fileFilter);

			if (null != files && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					LOG.info("**********File : ".intern() + files[i]);
				}
				// If processing order =1 means FIFO AND If processing order =2
				// means LIFO

				Arrays.sort(files, new Comparator<File>() {
					@Override
					public int compare(File f1, File f2) {
						return Long.valueOf(f1.lastModified()).compareTo(
								f2.lastModified());
					}
				});

				LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  :  FIFO List "
						+ files.length);
				for (int i = 0; i < files.length; i++) {
					LOG.info("**********File : ".intern() + files[i]
							+ " -  File last modified time "
							+ files[i].lastModified());
				}

			} else {
				LOG.error(CommonUtil.replacePrmtWithMsg(
						PDMessageConsants.M00109, new String[] { "&1" },
						new String[] { interfaceFileIdStr + interfaceFileID
								+ "[P0001]-[Local_system_path] = "
								+ mstInterface.getLocalPath()
								+ " [P0001]-[FileName_Format] = "
								+ mstInterface.getFilenameFormat() }));
				throw new Exception(new String("No files found"));
			}

		} else if (mstInterface.getFilenameChecktype().equalsIgnoreCase(
				B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2)
				&& mstInterface.getMultipleFiles().equalsIgnoreCase(
						B000059Constants.MULTFILE_ONE)) {
			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  and MULTFILE_ONE "
					.intern());

			FileFilter fileFilter = getFileFiler(mstInterface);

			localPath = mstInterface.getLocalPath();
			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : localPath "
					.intern() + localPath);

			dir = new File(localPath);

			LOG.info("canExecute : ".intern() + dir.canExecute());
			LOG.info("canRead : ".intern() + dir.canRead());

			if (!(dir.canExecute() && dir.canRead())) {
				throw new Exception(new String(
						"Permission issues from reading above folder"));
			}

			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : fileFilter "
					.intern() + fileFilter.toString());

			File[] listLIFO = dir.listFiles(fileFilter);

			if (null != listLIFO && listLIFO.length > 0) {
				LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  :  LIFOList "
						.intern() + listLIFO.length);
				for (int i = 0; i < listLIFO.length; i++) {
					LOG.info("**********File : ".intern() + listLIFO[i]
							+ " -  File last modified time ".intern()
							+ listLIFO[i].lastModified());
				}

				Arrays.sort(listLIFO, new Comparator<File>() {
					@Override
					public int compare(File f1, File f2) {

						return Long.valueOf(f2.lastModified()).compareTo(
								f1.lastModified());
					}
				});

				files = new File[1];
				files[0] = listLIFO[0];

			} else {
				LOG.error(CommonUtil.replacePrmtWithMsg(
						PDMessageConsants.M00109, new String[] { "&1" },
						new String[] { interfaceFileIdStr + interfaceFileID
								+ "[P0001]-[Local_system_path] = "
								+ mstInterface.getLocalPath()
								+ " [P0001]-[FileName_Format] = "
								+ mstInterface.getFilenameFormat() }));
				throw new Exception(new String("No files found"));
			}

		} else if (mstInterface.getFilenameChecktype().equalsIgnoreCase(
				B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2)
				&& mstInterface.getMultipleFiles().equalsIgnoreCase(
						B000059Constants.MULTFILE_ZERO)) {

			FileFilter fileFilter = getFileFiler(mstInterface);

			localPath = mstInterface.getLocalPath();// + File.separator +
			// mstInterface.getFilenameFormat();

			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : localPath "
					.intern() + localPath);

			dir = new File(localPath);

			LOG.info("canExecute : ".intern() + dir.canExecute());
			LOG.info("canRead : ".intern() + dir.canRead());

			if (!(dir.canExecute() && dir.canRead())) {
				throw new Exception(
						new String(
								"Permission issues from reading and executing above folder"));
			}

			LOG.info("B000059MoveFilesTasklet in B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2  : fileFilter "
					.intern() + fileFilter.toString());

			files = dir.listFiles(fileFilter);

			StringBuilder fileList = new StringBuilder();

			for (int i = 0; i < files.length; i++) {
				fileList.append(files[i].getName()).append("; ");
			}
			String errorInfo = (String) CommonUtil.replacePrmtWithMsg(
					PDMessageConsants.M00132, new String[] { "&1" },
					new String[] { interfaceFileIdStr + interfaceFileID
							+ "[File Names:] = " + fileList.toString() });
			LOG.info(errorInfo);
			throw new Exception(errorInfo);
		}
		/*
		 * Based on number of files make an entry in CMN HEADER and get the list
		 * of sequence numbers and set it in
		 * B000059FileSpecVO.FileProcessingStatusVO.processingStatus
		 */
		List<B000059FileProcessingStatusVO> fileProcessingVO = (ArrayList<B000059FileProcessingStatusVO>) getFileSequence(
				files, interfaceFileID);

		// Setting the FileProcessingStatusVO which has the list of files with
		// status
		commonutility.getFileSpecVO().setFilesToProcessList(fileProcessingVO);

		String processPath = commonutility.getFileSpecVO()
				.getPrmtrMasterDetails()
				.get(B000059Constants.B000059_PROCESS_PATH).getVal1();

		File[] procFileList = new File[files.length];
		for (int i = 0; i < files.length; i++) {
			String destPath = processPath + files[i].getName();

			File destFile = new File(destPath);

			boolean moved = files[i].renameTo(destFile);
			// Store Files to Move to Success or Failure folder
			procFileList[i] = destFile;

			LOG.info("File Processing Path ".intern() + processPath
					+ files[i].getName() + " Flag ==".intern() + moved);

			if (!moved) {
				clm = CustomLogMessage.M00109;
				clm.fetchLogMessage(interfaceFileID, "");
				throw new Exception(new String("Could not move file ".intern()
						+ files[i].getPath()));

			} else {
				LOG.info(files[i].getPath()
						+ " moved sucessfully to Processing folder!");
			}
		}
		commonutility.storeFilesToMove(procFileList);
		return RepeatStatus.FINISHED;
	}

	private FileFilter getFileFiler(MstInterface mstInterface) {
		String fileName = mstInterface.getFilenameFormat();
		String fileNamewithoutExt = fileName.substring(0,
				fileName.lastIndexOf("."));
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());

		LOG.info("File : " + fileName);
		LOG.info("fileNamewithoutExt : " + fileNamewithoutExt);
		LOG.info("fileExt : " + fileExt);
		// Added to form EOL filename
		if (mstInterface.getFileType().equalsIgnoreCase(
				B000059Constants.PARAM_FILE_TYPE_2)) {
			fileNamewithoutExt = B000059Constants.EOLPREFIX
					+ fileName.substring(0, fileName.lastIndexOf("."));
		}

		return (new WildcardFileFilter(fileNamewithoutExt + "*" + fileExt));
	}

	/**
	 * method used to find latest file to process
	 * 
	 * @param filePath
	 * @param ext
	 *            extension
	 * @return File
	 */
	public File getTheNewestFile(String filePath, String ext) {
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}

		return theNewestFile;
	}

	/**
	 * Preparing a list to sequencing the no. of files using sequence number and
	 * file name to process
	 * 
	 * @param file
	 * @param fileInterfaceID
	 *            interface file id
	 * @return list of files
	 */
	public List<B000059FileProcessingStatusVO> getFileSequence(File[] file,
			String fileInterfaceID) {

		List<B000059FileProcessingStatusVO> tmpProcessingStatusVO = new ArrayList<B000059FileProcessingStatusVO>();

		StringBuilder insertCmnHeaderInitial = new StringBuilder().append(
				"insert into CMN_FILE_HDR (IF_FILE_ID,SEQ_NO,FILE_NAME)")
				.append(" values (:IF_FILE_ID,:SEQ_NO,:FILE_NAME)");

		Query queryInserthdr = commonutility.getEntityManager()
				.createNativeQuery(insertCmnHeaderInitial.toString());
		long preSeq = 0L;
		long seq = 0L;

		try {
			seq = commonutility.getSequenceNoForInterfaceId(fileInterfaceID);
		} catch (PdApplicationException e) {

			LOG.error("PdApplicationException ", e);
		}
		preSeq = seq;
		for (int i = 0; i < file.length; i++) {
			LOG.info("Inserting into ...File Length is **********"
					+ file.length + "Filename is " + file[i].getAbsolutePath());

			queryInserthdr.setParameter("IF_FILE_ID", fileInterfaceID);
			queryInserthdr.setParameter("SEQ_NO", seq++);
			queryInserthdr.setParameter("FILE_NAME", file[i].getName());
			queryInserthdr.executeUpdate();
		}

		List<CmnFileHdr> cmnFileHdrList = null;
		StringBuilder selQuery = null;

		for (int i = 0; i < file.length; i++) {
			LOG.info("Fetching from ...File Length is **********" + file.length
					+ "Filename is " + file[i].getAbsolutePath());
			selQuery = new StringBuilder(
					"SELECT c FROM CmnFileHdr c where c.id.ifFileId='");
			selQuery.append(fileInterfaceID).append("' AND c.fileName ='")
					.append(file[i].getName()).append("'")
					.append(" AND SEQ_NO=").append(preSeq + i);

			cmnFileHdrList = (ArrayList<CmnFileHdr>) commonutility
					.getEntityManager().createQuery(selQuery.toString())
					.getResultList();

			for (Iterator iterator = cmnFileHdrList.iterator(); iterator
					.hasNext();) {
				LOG.info("Setting Id and Filename");
				CmnFileHdr cmnFileHdr = (CmnFileHdr) iterator.next();
				LOG.info(cmnFileHdr.getId().getSeqNo());
				LOG.info(cmnFileHdr.getFileName());
				B000059FileProcessingStatusVO fileProcessStatus = new B000059FileProcessingStatusVO();
				fileProcessStatus.setSeqNo((int) cmnFileHdr.getId().getSeqNo());
				fileProcessStatus.setFileName(cmnFileHdr.getFileName());
				tmpProcessingStatusVO.add(fileProcessStatus);
			}
		}
		return tmpProcessingStatusVO;

	}

}
