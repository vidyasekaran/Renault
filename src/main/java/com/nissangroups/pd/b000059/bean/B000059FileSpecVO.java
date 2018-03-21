/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This stores all static data required to process the files.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nissangroups.pd.model.MstPrmtr;

public class B000059FileSpecVO {

	private String interfaceFileId;
	/**
	 * file decider flag to decide single or multiple file
	 */
	private static boolean fileDecider = true;

	/**
	 * This will be stored in files to process
	 */
	private B000059FileProcessingStatusVO fileProcessingVO = new B000059FileProcessingStatusVO();

	/**
	 * Stores file processing folder
	 */
	private String processingFolder;

	/**
	 * Stores interface master detail
	 */
	private Map interfaceMaster = new HashMap();

	/**
	 * Stores list of files to move to success or failure folder
	 */
	private List<String> filesToMove = new ArrayList<String>();
	
	/**
	 * Stores list of files to move to success or failure folder
	 */
	private List<String> inValidRecordLengthFile = new ArrayList<String>();
	
	

	/**
	 * Stores files to process
	 */
	private List<B000059FileProcessingStatusVO> filesToProcessList = new ArrayList<B000059FileProcessingStatusVO>();

	/**
	 * Stores interface layout detail
	 */
	private List interfaceLayoutList = new ArrayList();

	/**
	 * stores order as key and list of interfaceLayoutVO as value
	 */
	private Map interfaceLayoutByOrder = new HashMap();

	/**
	 * Store Parameter Master
	 */
	private Object var;

	/**
	 * Parameter Master Map
	 */
	private Map<String, MstPrmtr> prmtrMasterDetails;

	/**
	 * Stores interface filter detail
	 */
	private List interfaceFilterList = new ArrayList();

	/**
	 * Store Sequence numbers of files processed
	 */
	private List fileSequenceList = new ArrayList();

	/**
	 * Validation error flag
	 */
	private static boolean validationFlag = false;
	
	private boolean recordLengthValidationFlag = false;

	/**
	 * Store Validation error message
	 */
	private static StringBuilder validationError = new StringBuilder();
	
	private Integer totalOrderCols = null;
	
	
	private String cmnIFInsertQuery = null;


	/**
	 * @return processing folder path
	 */
	public String getProcessingFolder() {
		return processingFolder;
	}

	/**
	 * sets the processing folder path
	 * 
	 * @param processingFolder
	 *            processing folder
	 */
	public void setProcessingFolder(String processingFolder) {
		this.processingFolder = processingFolder;
	}

	/**
	 * @return interface master data
	 */
	public Map getInterfaceMaster() {
		return interfaceMaster;
	}

	/**
	 * Sets the interface master data
	 * 
	 * @param interfaceMaster
	 *            interface master
	 */
	public void setInterfaceMaster(Map interfaceMaster) {
		this.interfaceMaster = interfaceMaster;
	}

	/**
	 * @return list of files to process
	 */
	public List getFilesToProcessList() {
		return filesToProcessList;
	}

	/**
	 * Sets the files for process
	 * 
	 * @param filesToProcessList
	 *            files list to process
	 */
	public void setFilesToProcessList(List filesToProcessList) {
		this.filesToProcessList = filesToProcessList;
	}

	/**
	 * @return list of interface layout data
	 */
	public List getInterfaceLayoutList() {
		return interfaceLayoutList;
	}

	/**
	 * Sets list of interface layout data
	 * 
	 * @param interfaceLayoutList
	 *            interface layout
	 */
	public void setInterfaceLayoutList(List interfaceLayoutList) {
		this.interfaceLayoutList = interfaceLayoutList;
	}

	/**
	 * @return interface layout
	 */
	public Map getInterfaceLayoutByOrder() {
		return interfaceLayoutByOrder;
	}

	/**
	 * Sets the ordered interface layout data
	 * 
	 * @param interfaceLayoutByOrder
	 *            ordered interface layout
	 */
	public void setInterfaceLayoutByOrder(Map interfaceLayoutByOrder) {
		this.interfaceLayoutByOrder = interfaceLayoutByOrder;
	}

	/**
	 * @return
	 */
	public Object getVar() {
		return var;
	}

	/**
	 * @param var
	 */
	public void setVar(Object var) {
		this.var = var;
	}

	/**
	 * @return interface filter list
	 */
	public List getInterfaceFilterList() {
		return interfaceFilterList;
	}

	/**
	 * Sets the interface filter list
	 * 
	 * @param interfaceFilterList
	 *            interface filter list
	 */
	public void setInterfaceFilterList(List interfaceFilterList) {
		this.interfaceFilterList = interfaceFilterList;
	}

	/**
	 * @return file sequence list to process
	 */
	public List getFileSequenceList() {
		return fileSequenceList;
	}

	/**
	 * Sets the file sequence
	 * 
	 * @param fileSequenceList
	 *            file sequence list
	 */
	public void setFileSequenceList(List fileSequenceList) {
		this.fileSequenceList = fileSequenceList;
	}

	/**
	 * @return validation status as true or false
	 */
	public boolean isValidationFlag() {
		return validationFlag;
	}

	/**
	 * Sets validation status
	 * 
	 * @param validationFlag
	 *            validation flag
	 */
	public void setValidationFlag(boolean validationFlag) {
		this.validationFlag = validationFlag;
	}

	/**
	 * @return validation error message
	 */
	public StringBuilder getValidationError() {
		return validationError;
	}

	/**
	 * Sets the validation error message
	 * 
	 * @param validationError
	 *            validation error
	 */
	public void setValidationError(StringBuilder validationError) {
		this.validationError = validationError;
	}

	/**
	 * @return is file decider is true or false
	 */
	public static boolean isFileDecider() {
		return fileDecider;
	}

	/**
	 * Sets the file decider status
	 * 
	 * @param fileDecider
	 *            file decider flag
	 */
	public static void setFileDecider(boolean fileDecider) {
		B000059FileSpecVO.fileDecider = fileDecider;
	}

	/**
	 * @return File processing status VO
	 */
	public B000059FileProcessingStatusVO getFileProcessingVO() {
		return fileProcessingVO;
	}

	/**
	 * Sets the file processing VO
	 * 
	 * @param fileProcessingVO
	 *            file processing VO
	 */
	public void setFileProcessingVO(
			B000059FileProcessingStatusVO fileProcessingVO) {
		this.fileProcessingVO = fileProcessingVO;
	}

	/**
	 * @return list of files to move
	 */
	public List<String> getFilesToMove() {
		return filesToMove;
	}

	/**
	 * Sets the list of files to move
	 * 
	 * @param filesToMove
	 *            list of files
	 */
	public void setFilesToMove(List<String> filesToMove) {
		this.filesToMove = filesToMove;
	}

	/**
	 * @return parameter master
	 */
	public Map<String, MstPrmtr> getPrmtrMasterDetails() {
		return prmtrMasterDetails;
	}

	/**
	 * Sets the parameter master data
	 * 
	 * @param prmtrMasterDetails
	 *            parameter master details
	 */
	public void setPrmtrMasterDetails(Map<String, MstPrmtr> prmtrMasterDetails) {
		this.prmtrMasterDetails = prmtrMasterDetails;
	}

	public String getInterfaceFileId() {
		return interfaceFileId;
	}

	public void setInterfaceFileId(String interfaceFileId) {
		this.interfaceFileId = interfaceFileId;
	}

	public boolean isRecordLengthValidationFlag() {
		return recordLengthValidationFlag;
	}

	public void setRecordLengthValidationFlag(boolean recordLengthValidationFlag) {
		this.recordLengthValidationFlag = recordLengthValidationFlag;
	}

	public List<String> getInValidRecordLengthFile() {
		return inValidRecordLengthFile;
	}

	public void setInValidRecordLengthFile(List<String> inValidRecordLengthFile) {
		this.inValidRecordLengthFile = inValidRecordLengthFile;
	}
	
	public Integer getTotalOrderCols() {
		return totalOrderCols;
	}

	public void setTotalOrderCols(Integer totalOrderCols) {
		this.totalOrderCols = totalOrderCols;
	}

	public String getCmnIFInsertQuery() {
		return cmnIFInsertQuery;
	}

	public void setCmnIFInsertQuery(String cmnIFInsertQuery) {
		this.cmnIFInsertQuery = cmnIFInsertQuery;
	}
		
}
