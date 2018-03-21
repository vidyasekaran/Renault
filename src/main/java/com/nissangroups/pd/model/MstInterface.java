package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_INTERFACE database table.
 * 
 */
@Entity
@Table(name="MST_INTERFACE")
@NamedQuery(name="MstInterface.findAll", query="SELECT m FROM MstInterface m")
public class MstInterface implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CONTROL_FILE_FLAG")
	private String controlFileFlag;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FILE_TYPE")
	private String fileType;

	@Column(name="FILENAME_CHECKTYPE")
	private String filenameChecktype;

	@Column(name="FILENAME_FORMAT")
	private String filenameFormat;

	@Column(name="FTP_FILEPATH")
	private String ftpFilepath;

	@Column(name="FTP_PASSWORD")
	private String ftpPassword;

	@Column(name="FTP_PORT")
	private String ftpPort;

	@Column(name="FTP_SERVER")
	private String ftpServer;

	@Column(name="FTP_USERNAME")
	private String ftpUsername;

	@Column(name="IF_NAME")
	private String ifName;

	@Column(name="LOCAL_PATH")
	private String localPath;

	@Column(name="MULTIPLE_FILES")
	private String multipleFiles;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROCESSING_ORDER")
	private String processingOrder;

	@Column(name="RECEIVE_IF_FILE_ID")
	private String receiveIfFileId;

	@Column(name="SKIP_IF_ERROR")
	private String skipIfError;

	@Column(name="TRANSACTION_TYPE")
	private String transactionType;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstInterface() {
	}

	public String getIfFileId() {
		return this.ifFileId;
	}

	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getControlFileFlag() {
		return this.controlFileFlag;
	}

	public void setControlFileFlag(String controlFileFlag) {
		this.controlFileFlag = controlFileFlag;
	}

	public String getCrtdBy() {
		return this.crtdBy;
	}

	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	public Timestamp getCrtdDt() {
		return this.crtdDt;
	}

	public void setCrtdDt(Timestamp crtdDt) {
		this.crtdDt = crtdDt;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilenameChecktype() {
		return this.filenameChecktype;
	}

	public void setFilenameChecktype(String filenameChecktype) {
		this.filenameChecktype = filenameChecktype;
	}

	public String getFilenameFormat() {
		return this.filenameFormat;
	}

	public void setFilenameFormat(String filenameFormat) {
		this.filenameFormat = filenameFormat;
	}

	public String getFtpFilepath() {
		return this.ftpFilepath;
	}

	public void setFtpFilepath(String ftpFilepath) {
		this.ftpFilepath = ftpFilepath;
	}

	public String getFtpPassword() {
		return this.ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpPort() {
		return this.ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpServer() {
		return this.ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpUsername() {
		return this.ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getIfName() {
		return this.ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getLocalPath() {
		return this.localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getMultipleFiles() {
		return this.multipleFiles;
	}

	public void setMultipleFiles(String multipleFiles) {
		this.multipleFiles = multipleFiles;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getProcessingOrder() {
		return this.processingOrder;
	}

	public void setProcessingOrder(String processingOrder) {
		this.processingOrder = processingOrder;
	}

	public String getReceiveIfFileId() {
		return this.receiveIfFileId;
	}

	public void setReceiveIfFileId(String receiveIfFileId) {
		this.receiveIfFileId = receiveIfFileId;
	}

	public String getSkipIfError() {
		return this.skipIfError;
	}

	public void setSkipIfError(String skipIfError) {
		this.skipIfError = skipIfError;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUpdtdBy() {
		return this.updtdBy;
	}

	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	public Timestamp getUpdtdDt() {
		return this.updtdDt;
	}

	public void setUpdtdDt(Timestamp updtdDt) {
		this.updtdDt = updtdDt;
	}

	@PrePersist
    void onCreate() {
        this.setCrtdDt(CommonUtil.createTimeStamp());
        this.setUpdtdDt(CommonUtil.createTimeStamp());
    }
    
    @PreUpdate
    void onPersist() {
        this.setUpdtdDt(CommonUtil.createTimeStamp());
    }

}