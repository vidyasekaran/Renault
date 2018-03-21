/* System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This is the common utility service which is used to load static data to B000059FileSpecVO from database 
 *                  it also contain other method which needs to do some computation and data conversion to java 
 *                  collection store the value to B000059FileSpecVO.
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
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileProcessingStatusVO;
import com.nissangroups.pd.b000059.bean.B000059FileSpecVO;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000059CommonUtilityService {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());

	/**
	 *  custom log message for B000059
	 */
	CustomLogMessage clm = null;

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000059FileSpecVO fileSpecVO;

	/**
	 * This is used to initialize data required for B000059 batch process by
	 * calling all other methods
	 * 
	 * @param interface file id
	 * @throws PdApplicationException 
	 * @throws Exception 
	 */
	public void initializeData(String interfaceFileID) throws PdApplicationException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		fileSpecVO.setInterfaceFileId(interfaceFileID);
		try {
			storeInterfaceMasterData(interfaceFileID);
			storeInterfaceLayoutDetail(interfaceFileID);
			storeParameterMasterData();
		} catch (PdApplicationException e) {	
			
			LOG.error(e);
			
			throw new PdApplicationException(e.getMessage());
		}
		
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Query and store Interface Master Data
	 * 
	 * @param interface file id
	 */
	@SuppressWarnings("unchecked")
	public void storeInterfaceMasterData(String interfaceFileID)throws PdApplicationException {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {

			List<MstInterface> interfaceMstList = (List<MstInterface>) getEntityManager().createQuery(
					"SELECT  f FROM MstInterface  f where f.ifFileId='"
							+ interfaceFileID + "'").getResultList();

			// START:: Load Interface Master into hashmap - key as fileid and
			// value as entrie row of that file id.

			if (null != interfaceMstList && !interfaceMstList.isEmpty()) {
				for (Iterator<MstInterface> iterator = interfaceMstList
						.iterator(); iterator.hasNext();) {
					MstInterface interfaceMst =  iterator.next();
					fileSpecVO.getInterfaceMaster().put(
							interfaceMst.getIfFileId(), interfaceMst);

				}
			}else{				
				LOG.info(PDMessageConsants.M00003 + "- Interface File ID : " + interfaceFileID + " on Interface Master table") ;				
				throw new PdApplicationException("No data found in Interface Master table");
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			clm = CustomLogMessage.M00003_1;
			clm.fetchLogMessage(interfaceFileID, "");
			throw new PdApplicationException(e.getMessage());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * @return Interface Master Data
	 */
	public Map fetchInterfaceMasterData() {

		return fileSpecVO.getInterfaceMaster();
	}

	/**
	 * Query interface layout table and store it in interfaceLayoutList
	 * 
	 * @param interface file id
	 */
	@SuppressWarnings("unchecked")
	public void storeInterfaceLayoutDetail(String interfaceFileID)throws PdApplicationException {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			List<MstIfLayout> mstIfLayoutList = (ArrayList<MstIfLayout>) getEntityManager()
					.createQuery(
							"SELECT  f FROM MstIfLayout  f where f.id.ifFileId='"
									+ interfaceFileID
									+ "' order by f.columnOrdr")
					.getResultList();

			// START:: Load Interface Master into hashmap - key as fileid and
			// value as all row of that file id.

			if (null != mstIfLayoutList && !mstIfLayoutList.isEmpty()) {

				fileSpecVO.getInterfaceLayoutByOrder().put(interfaceFileID,
						mstIfLayoutList);
				fileSpecVO.setTotalOrderCols(mstIfLayoutList.size());
				
				StringBuilder query1 = B000059QueryConstants.insertCmnInterfaceDataSpecificFields_1;
				StringBuilder query2 = B000059QueryConstants.insertCmnInterfaceDataSpecificFields_2;
				
				for(int i=1; i<=mstIfLayoutList.size(); i++){
					query1.append("COL").append(i).append(",");					
					query2.append("?").append(",");
				}
				
				StringBuilder combineQuery = new StringBuilder(query1.toString().replaceFirst(".$", ""));
				
				combineQuery.append(" ) ");
				
				combineQuery.append(query2.toString().replaceFirst(".$", ""));
				
				combineQuery.append(" ) ");
				
				LOG.info("Combined Query : " + combineQuery);				
								
				fileSpecVO.setCmnIFInsertQuery(combineQuery.toString());
				
				
			}else{				
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00003, new String[]{"&1", "&2"}, new String[]{ "Interface File ID: " + interfaceFileID," on Interface_Layout table"}) );
				throw new PdApplicationException("No data found on Interface_Layout table ");
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			clm = CustomLogMessage.M00003_2;
			clm.fetchLogMessage(interfaceFileID, "");
			throw new PdApplicationException(e.getMessage());
			
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * @return interfaceLayoutList
	 */
	public Map fetchInterfaceLayoutDetail() {

		return fileSpecVO.getInterfaceLayoutByOrder();
	}

	/**
	 * Query Parameter master table and store it in var object
	 */
	@SuppressWarnings("unchecked")
	public void storeParameterMasterData() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		Map<String, MstPrmtr> details = new HashMap<String, MstPrmtr>();
		
		List<MstPrmtr> mstPrmtrLst = getEntityManager().createQuery(
				new StringBuilder("SELECT PM FROM MstPrmtr PM WHERE PM.id.prmtrCd IN ('")
				.append(B000059Constants.B000059_RECIEV_DATE_FRMT)
				.append( "','")				
				.append(B000059Constants.B000059_PROCESS_PATH)
				.append( "','")
				.append(B000059Constants.B000059_SUCCESS_FOLDER)
				.append( "','")
				.append(B000059Constants.B000059_FAILURE_FOLDER)
				.append("') order by id.seqNo").toString()).getResultList();
		
		for (MstPrmtr prmtr : mstPrmtrLst) {
			details.put(prmtr.getId().getPrmtrCd(), prmtr);
		}
		fileSpecVO.setPrmtrMasterDetails(details);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * @return parameterMaster data
	 * 
	 * @param parameter
	 *            code
	 */
	public MstPrmtr fetchParameterMasterData(String prmtrCd) {
		return fileSpecVO.getPrmtrMasterDetails().get(prmtrCd);
	}


	/**
	 * Delete common Pool Data with seq_no and File name.
	 * 
	 * @param fileInterfaceName
	 *            filter interface name
	 * @param seqNo
	 *            squence number
	 * @param fileName
	 *            filename
	 */
	public void deleteFromCommonPool(String fileInterfaceName, int seqNo) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query queryDelete = getEntityManager()
					.createNativeQuery(B000059QueryConstants.DELETE_CMN_INTERFACE_DATA
							.toString());
			queryDelete.setParameter("IF_FILE_ID", fileInterfaceName);
			queryDelete.setParameter("SEQ_NO", new Integer(seqNo));			
			queryDelete.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * Setting files to move in B000059FileSpecVO
	 * 
	 * @param files
	 */
	public void storeFilesToMove(File[] files) {
		List<String> arList = new ArrayList<String>();

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (int i = 0; i < files.length; i++) {
			arList.add(files[i].getAbsoluteFile().toString());

		}
		getFileSpecVO().setFilesToMove(arList);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 *  update common file header
	 *  
	 * @param interfaceFileID interface file id
	 */
	@SuppressWarnings("unchecked")
	public void updateCommonFileHdr(String interfaceFileID) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		try {

			List<B000059FileProcessingStatusVO> filesToProcessList = fileSpecVO
					.getFilesToProcessList();

			for (B000059FileProcessingStatusVO statusVO : filesToProcessList) {

				Query cmnHdrInsert = getEntityManager()
						.createNativeQuery(B000059QueryConstants.insertCmnHeader
								.toString());
				cmnHdrInsert.setParameter("IF_FILE_ID", interfaceFileID);
				cmnHdrInsert.setParameter("SEQ_NO",
						new Integer(statusVO.getSeqNo()));
				cmnHdrInsert.setParameter("FILE_NAME", statusVO.getFileName());
				cmnHdrInsert.setParameter("REC_COUNT",
						statusVO.getFilerowCount());
				cmnHdrInsert.setParameter("TRN_TYPE", null);
				String status = (statusVO.isProcessingStatus()) ? PDConstants.COMM_HEADER_STATUS_SUC
						: PDConstants.COMM_HEADER_STATUS_FAIL;
				String remarks = (statusVO.isProcessingStatus()) ? PDConstants.REMARKS_SUC
						: PDConstants.REMARKS_FAIL;
				cmnHdrInsert.setParameter("STTS", status);
				cmnHdrInsert.setParameter("REMARKS", interfaceFileID + " : "
						+ remarks);
				cmnHdrInsert.executeUpdate();
			}

		} catch (Exception e) {
			
			LOG.error(ERROR_MESSAGE, e);
			clm = CustomLogMessage.M00043_1;
			clm.fetchLogMessage(interfaceFileID, e.toString());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * This method used to move processed files into success or failure folder
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void moveProcessedFiles() {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		try {
			List<B000059FileProcessingStatusVO> filesToProcessList = fileSpecVO
					.getFilesToProcessList();
			String processPath = fileSpecVO.getPrmtrMasterDetails()
					.get(B000059Constants.B000059_PROCESS_PATH).getVal1();
			String successPath = fileSpecVO.getPrmtrMasterDetails()
					.get(B000059Constants.B000059_SUCCESS_FOLDER).getVal1();
			String failurePath = fileSpecVO.getPrmtrMasterDetails()
					.get(B000059Constants.B000059_FAILURE_FOLDER).getVal1();
						
			for (B000059FileProcessingStatusVO fileList : filesToProcessList) {

				File fileToMove = new File(processPath + fileList.getFileName());
				boolean moved = false;				

				if (fileList.isError()) {
					File destFile = new File(failurePath + File.separator	+ fileList.getFileName());					
					moved = fileToMove.renameTo(destFile);					
					CommonUtil.isFileRenamedTo(moved,fileToMove,destFile);										
				} else {
					File destFile = new File(successPath + File.separator + fileList.getFileName());
					moved = fileToMove.renameTo(destFile);					
					CommonUtil.isFileRenamedTo(moved,fileToMove,destFile);				
				}								
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Find the max of end positon
	 * 
	 * @param mstIfLayout
	 *            interface master layout list
	 */
	public Integer getMaxEndPosition(List<MstIfLayout> mstIfLayout) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Integer max = 0;
		Integer value = 0;
		MstIfLayout msgLayout = null;
		try {
			for (Iterator<MstIfLayout> iterator = mstIfLayout.iterator(); iterator
					.hasNext();) {
				msgLayout = iterator.next();
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

	/**
	 * Converting as Fixed Length Format by adding eol on after every bytes of
	 * max end position
	 * 
	 * @param maxEndPosition
	 *            maximum end position
	 * @param localPath
	 *            local directory path
	 * @param interfaceFileID
	 *            interface file id
	 */
	public void convertFixedLengthFormat(Integer maxEndPosition,String localPath, String interfaceFileID) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		File dir = null;
		File[] files = null;

		try {
			dir = new File(localPath);
			MstInterface interfaceMaster = (MstInterface) getFileSpecVO()
					.getInterfaceMaster().get(interfaceFileID);
			FileFilter fileFilter = null;

			String fileName = interfaceMaster.getFilenameFormat();
			String fileNamewithoutExt = fileName.substring(0,
					fileName.lastIndexOf("."));
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
			String tmplocalPath = null;

			if (interfaceMaster.getFilenameChecktype().equalsIgnoreCase(
					B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_1)) {
				tmplocalPath = interfaceMaster.getLocalPath() + File.separator
						+ interfaceMaster.getFilenameFormat();
				files = new File[1];
				files[0] = new File(tmplocalPath);

				if (interfaceMaster.getFileType().equalsIgnoreCase(
						B000059Constants.PARAM_FILE_TYPE_2)) {
					tmplocalPath = interfaceMaster.getLocalPath()
							+ File.separator + B000059Constants.EOLPREFIX
							+ interfaceMaster.getFilenameFormat();
				}
				
			} else if (interfaceMaster.getFilenameChecktype().equalsIgnoreCase(B000059Constants.PARAM_FILE_NAME_CHECK_TYPE_2)) {				
				fileFilter = new WildcardFileFilter(fileNamewithoutExt + "*."
						+ fileExt);																		
				dir = new File(interfaceMaster.getLocalPath());
				files = dir.listFiles(fileFilter);
			}
						
			for (File file : files) {
				if (file.isFile()) {					
					addEOLInFile(localPath, file, maxEndPosition);																				
				}
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		} finally {
			dir = null;
			files = null;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
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
	public Boolean addEOLInFile(String localPath, File newFile,
			Integer maxEndPosition) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		RandomAccessFile randAccessFile = null;
		FileChannel inChannel = null;
		StringBuilder sb = null;
		ByteBuffer buf = null;
		int bytesRead;
		String tmpFileName = null;

		try {
			tmpFileName = new StringBuilder(localPath).append(File.separator)
					.append(B000059Constants.EOLPREFIX)
					.append(newFile.getName()).toString();
			LOG.info("Temp File Name :" + tmpFileName);
			file = new File(tmpFileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			randAccessFile = new RandomAccessFile(newFile, "rw");

			inChannel = randAccessFile.getChannel();

			// create buffer with capacity of 48 bytes
			buf = ByteBuffer.allocate(maxEndPosition);
			bytesRead = inChannel.read(buf);
			// read into buffer.
			while (bytesRead != -1) {
				sb = new StringBuilder();
				buf.flip(); // make buffer ready for read
				while (buf.hasRemaining()) {
					sb.append((char) buf.get()); // read 1 byte at a time
				}
				buf.clear(); // make buffer ready for writing
				bytesRead = inChannel.read(buf);
				
				if(bytesRead !=-1 )
					sb.append(System.lineSeparator());
				
				bw.write(sb.toString());
				bw.flush();
			}
			
			String successPath = fileSpecVO.getPrmtrMasterDetails().get(B000059Constants.B000059_SUCCESS_FOLDER).getVal1();
			if(null != successPath){
				File destFile = new File(successPath + File.separator	+ file.getName());
				Boolean moved = file.renameTo(destFile);
				LOG.info("Non EOL File Move Operation");
				CommonUtil.isFileRenamedTo(moved, file, destFile);								
			}

		} catch (IOException e) {
			LOG.error(ERROR_MESSAGE, e);
			return false;
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}
			try {
				fw.close();
			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}

			try {
				randAccessFile.close();

			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE, e);
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		
		
		return true;
	}

	/**
	 * update common file header with row count for the file.
	 * 
	 * @param ifFileId
	 *            interface file id
	 * @param seqNo
	 *            sequence number
	 * @param fileName
	 *            filename
	 * @param recCount
	 *            record count
	 */
	public void updateCommonFileHdr(String ifFileId, Integer seqNo,
			String fileName, Long recCount) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query updateHdrInsert = getEntityManager()
					.createNativeQuery(B000059QueryConstants.updateCmnHeader
							.toString());
			updateHdrInsert.setParameter("IF_FILE_ID", ifFileId);
			updateHdrInsert.setParameter("SEQ_NO", seqNo);
			updateHdrInsert.setParameter("FILE_NAME", fileName);
			updateHdrInsert.setParameter("REC_COUNT", recCount);
			updateHdrInsert.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * update file status on common file header
	 * 
	 * @param ifFileId
	 *            interface file id
	 * @param seqNo
	 *            sequence number
	 * @param fileName
	 *            filename
	 * @param status
	 */
	public void updateFileStatusCommonFileHdr(String ifFileId, Integer seqNo,
			String fileName, String status, String trnType) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query updateHdrInsert = getEntityManager()
					.createNativeQuery(B000059QueryConstants.updateFileStatusCmnHeader
							.toString());
			updateHdrInsert.setParameter("IF_FILE_ID", ifFileId);
			updateHdrInsert.setParameter("SEQ_NO", seqNo);
			updateHdrInsert.setParameter("FILE_NAME", fileName);
			updateHdrInsert.setParameter("STTS", status);
			updateHdrInsert.setParameter("TRN_TYPE", trnType);
			updateHdrInsert.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * update file status as true or false based on the counter in which record is fail to validate
	 * 
	 * @param counter
	 */
	@SuppressWarnings("unchecked")
	public void updateFileStatusOnBean(long counter) {
		List<B000059FileProcessingStatusVO> listStatus = fileSpecVO
				.getFilesToProcessList();
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Long start = 0L;
		Long end = 0L;
		Long fileRecordCount = 0L;
		for (int i = 0; i < listStatus.size(); i++) {
			B000059FileProcessingStatusVO fileStatusVO = listStatus.get(i);
			if (!fileStatusVO.isError()) {
				fileRecordCount = Long.parseLong(fileStatusVO.getFilerowCount()
						.toString());
				if (i == 0) {
					start = 1L;
					end = fileRecordCount;
				} else {
					start = end + 1;
					end = fileRecordCount + end;
				}
				LOG.info(start + "\t" + end + "\t   Total : " +(end-start+1));
				if (start <= counter && counter <= end) {
					fileStatusVO.setError(true);
				}
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method used to generate the sequence number
	 * 
	 * @param writerCount
	 *            writer count
	 * @return sequence number
	 */
	@SuppressWarnings("unchecked")
	public Integer getSeqNumber(Integer writerCount) {
		List<B000059FileProcessingStatusVO> listStatus = fileSpecVO.getFilesToProcessList();
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int start = 1;
		int end = 0;
		int fileRecordCount = 0;

		for (int i = 0; i < listStatus.size(); i++) {

			B000059FileProcessingStatusVO fileStatusVO = listStatus.get(i);

			fileRecordCount = Integer.parseInt(fileStatusVO.getFilerowCount()
					.toString());
			if (i == 0) {
				start = 1;
				end = fileRecordCount;
			} else {
				start = end + 1;
				end = fileRecordCount + end;
			}
			LOG.info(start + "\t" + end + "\t   Total : "	+ (end - start + 1));
			if (start <= writerCount && writerCount <= end) {
				return fileStatusVO.getSeqNo();
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return null;
	}

	/**
	 * Get the record count
	 * 
	 * @param file
	 * @return record count
	 */
	public Long getRecordCount(File file) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Long recordCount = 0L;
		long fileLength = file.length();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));					
			String text = reader.readLine();
			
			if(null != text){
				recordCount = fileLength / text.length();
			}
			
		} catch (IOException e) {
			LOG.error(ERROR_MESSAGE, e);			
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOG.error(ERROR_MESSAGE, e);
				}
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return recordCount;
	}
	
	/**
	 * Get the length value from Interface Layout and set it to
	 * FormatterLineAggregator same as we did for FlatFileItemReader where we
	 * used B000059ColumnRangeTasklet
	 * 
	 * @param interfaceFileID
	 *            interface file id
	 * @return length
	 */
	@SuppressWarnings("unused")
	public StringBuilder setContextFixedLengthRange() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		List interfaceLayout = (ArrayList) fileSpecVO.getInterfaceLayoutList();
		StringBuilder lengthBuilder = new StringBuilder();

		for (Iterator iterator = interfaceLayout.iterator(); iterator.hasNext();) {
			MstIfLayout mstIfLayout = (MstIfLayout) iterator.next();
			lengthBuilder.append("%-").append(mstIfLayout.getLngth()).append("s");

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return lengthBuilder;
	}

	/**
	 * Fetch and returns the Control_File_Flag value is if value is 1 then
	 * Control file will be generated based on the generated Send Interface File
	 * data.
	 * 
	 * @param interfaceFileID
	 *            interface file id
	 * @return control file flag
	 */
	public String extractControlFileFlag(String interfaceFileID) {
		MstInterface interfaceMaster = null;

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Map interfaceMap = (HashMap) getFileSpecVO().getInterfaceMaster();
		interfaceMaster = (MstInterface) interfaceMap.get(interfaceFileID);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return interfaceMaster.getControlFileFlag();
	}

	/**
	 * Gets the entityManager
	 * 
	 * @return the entityManager
	 */

	public EntityManager getEntityManager() {						
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 * 
	 * @param entityManager
	 *            the entityManager to set
	 */

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * 
	 * @return B000059FileSpecVO object
	 */
	public B000059FileSpecVO getFileSpecVO() {
		return fileSpecVO;
	}

	/**
	 * Sets the filespecVO
	 * 
	 * @param fileSpecVO
	 *            B000059FileSpecVO object
	 */
	public void setFileSpecVO(B000059FileSpecVO fileSpecVO) {
		this.fileSpecVO = fileSpecVO;
	}

	/*
	 *Get the sequence number for provided interface id 
	 */
	public long getSequenceNoForInterfaceId(String fileInterfaceID) throws PdApplicationException{

		Long seqNo =0L;
		List<Object> result = null;
		try	{				
			LOG.info(new StringBuilder("Starint....LOG ").append(this.getClass().getName()).append(" in getSequenceNoForInterfaceId").toString());
			
			if(null != fileInterfaceID){								
				result = (List<Object>) getEntityManager().createQuery("SELECT MAX(f.id.seqNo) FROM CmnFileHdr  f where f.id.ifFileId='"+fileInterfaceID+"'").getResultList();					
				seqNo = ((Long) result.get(0) == null) ? 0L : (Long) result.get(0);  
				seqNo++;										
			} 
			else{
				LOG.info("Interface id should not be null");				
			}
								
		} 
		catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
			
		}
				
		LOG.info(new StringBuilder("END....getSequenceNoForInterfaceId").toString());
		return seqNo;	
	
	}
	
	/* Check the record length validation
	 * @param file, max of end position (length of the record )
	 * @return record count
	 */
	public boolean checkRecordLengthVal(File file, Integer maxOfEndPosition) {
		
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Long recordCount = 0L;
		
		long fileLength = file.length();

		BufferedReader reader = null;
		
		long lengthOfLine = 0L;
		long actualCharsCount = 0L;
		long expectedCharsCount = 0L;
		
		try {
			reader = new BufferedReader(new FileReader(file));			
			
			String text = reader.readLine();			
			
			LOG.info("Total File length : " + fileLength);
			
			if(text == null){
				return false;
			}
			lengthOfLine = text.length();
			
			recordCount = fileLength / lengthOfLine; 
			
			LOG.info("Length of Record : " +lengthOfLine);
			LOG.info("Total record count :" + recordCount);

			actualCharsCount = ( fileLength ) -  ( (recordCount - 1L) * System.lineSeparator().length() ); // after reducing CRLF  			
			expectedCharsCount =  maxOfEndPosition * recordCount;			
			LOG.info("Actual chars count : " + actualCharsCount);
			LOG.info("Expected chars count : " + expectedCharsCount);
			
		} catch (IOException e) {
			LOG.error(ERROR_MESSAGE, e);			
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOG.error(ERROR_MESSAGE, e);
				}
			}
		
		}
		return actualCharsCount == expectedCharsCount;
	}

}
