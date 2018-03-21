/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program maintains the status of the files being processed.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.bean;

public class B000059FileProcessingStatusVO {

	/**
	 * Stores sequence number
	 */
	private int seqNo;
	/**
	 * Stores filename
	 */
	private String fileName;
	/**
	 * Stores status of the processing file
	 */
	private boolean processingStatus;
	/**
	 * Stores row count
	 */
	private Long filerowCount;
	/**
	 * Stores error flag true or false
	 */
	private boolean isError;

	/**
	 * @return file sequence number
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * Sets the sequence number
	 * 
	 * @param seqNo
	 *            sequence number
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return error status as true or false
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * Sets the error status
	 * 
	 * @param isError
	 *            error status
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}

	/**
	 * @return filename
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the filename
	 * 
	 * @param fileName
	 *            filename
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return processing status
	 */
	public boolean isProcessingStatus() {
		return processingStatus;
	}

	/**
	 * Sets the processing status as true or false
	 * 
	 * @param processingStatus
	 *            processing status
	 */
	public void setProcessingStatus(boolean processingStatus) {
		this.processingStatus = processingStatus;
	}

	/**
	 * @return file row count
	 */
	public Long getFilerowCount() {
		return filerowCount;
	}

	/**
	 * Sets the file row count
	 * 
	 * @param filerowCount
	 *            file row count
	 */
	public void setFilerowCount(Long filerowCount) {
		this.filerowCount = filerowCount;
	}

}
