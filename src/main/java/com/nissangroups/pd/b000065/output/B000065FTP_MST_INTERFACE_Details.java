/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000065 SEND FILE using FTP
 * Module          :
 * Process Outline :Send File details vo     
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000065.output;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class B000065FTP_MST_INTERFACE_Details {
	

	@Id
	@Column(name="IF_FILE_ID")
	private String interfaceFileId;
	
	@Column(name="SEQ_NO")
	private String seqNo;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="FILENAME_FORMAT")
	private String fileNameFormat;

	@Column(name="CONTROL_FILE_NAME")
	private String controlFileName;
	
	@Column(name="FTP_SERVER")
	private String ftpServer;
	
	@Column(name="FTP_PORT")
	private String ftpPort;	

	@Column(name="FTP_USERNAME")
	private String ftpUserName;
	
	@Column(name="FTP_PASSWORD")
	private String ftpPassword;
	
	@Column(name="FTP_FILEPATH")
	private String filePath;
	
	@Column(name="CONTROL_FILE_FLAG")
	private String ctrlFileFlag;
	
	@Column(name="LOCAL_PATH")
	private String localPath;
	
	@Column(name="IS_PROCESSED")
	private String isProcessed;
	
	
	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}
	

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getCtrlFileFlag() {
		return ctrlFileFlag;
	}

	public void setCtrlFileFlag(String ctrlFileFlag) {
		this.ctrlFileFlag = ctrlFileFlag;
	}
	public String getInterfaceFileId() {
		return interfaceFileId;
	}

	public void setInterfaceFileId(String interfaceFileId) {
		this.interfaceFileId = interfaceFileId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getControlFileName() {
		return controlFileName;
	}

	public void setControlFileName(String controlFileName) {
		this.controlFileName = controlFileName;
	}

	public String isProcessed() {
		return isProcessed;
	}

	public void setProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}
	public String getFileNameFormat() {
		return fileNameFormat;
	}

	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

}
