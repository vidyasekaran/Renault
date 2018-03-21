package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CMN_INTERFACE_MST database table.
 * 
 */
@Entity
@Table(name="CMN_INTERFACE_MST")
@NamedQuery(name="CmnInterfaceMst.findAll", query="SELECT c FROM CmnInterfaceMst c")
public class CmnInterfaceMst implements Serializable {
	

	@Id
	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CNTRL_FILE_FLAG")
	private String cntrlFileFlag;

	@Column(name="FILE_NAME_FRMT")
	private String fileNameFrmt;

	@Column(name="FTP_FILE_PATH")
	private String ftpFilePath;

	@Column(name="FTP_PWD")
	private String ftpPwd;

	@Column(name="FTP_SRVR")
	private String ftpSrvr;

	@Column(name="FTP_USR_NAME")
	private String ftpUsrName;

	@Column(name="IF_NAME")
	private String ifName;

	@Column(name="LOCAL_PATH")
	private String localPath;

	@Column(name="MULTIPLE_FILES")
	private String multipleFiles;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="TRN_TYPE")
	private String trnType;

	//bi-directional many-to-one association to CmnFileHdr
	@OneToMany(mappedBy="cmnInterfaceMst")
	private List<CmnFileHdr> cmnFileHdrs;

	public CmnInterfaceMst() {
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

	public String getCntrlFileFlag() {
		return this.cntrlFileFlag;
	}

	public void setCntrlFileFlag(String cntrlFileFlag) {
		this.cntrlFileFlag = cntrlFileFlag;
	}

	public String getFileNameFrmt() {
		return this.fileNameFrmt;
	}

	public void setFileNameFrmt(String fileNameFrmt) {
		this.fileNameFrmt = fileNameFrmt;
	}

	public String getFtpFilePath() {
		return this.ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	public String getFtpPwd() {
		return this.ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getFtpSrvr() {
		return this.ftpSrvr;
	}

	public void setFtpSrvr(String ftpSrvr) {
		this.ftpSrvr = ftpSrvr;
	}

	public String getFtpUsrName() {
		return this.ftpUsrName;
	}

	public void setFtpUsrName(String ftpUsrName) {
		this.ftpUsrName = ftpUsrName;
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

	public String getTrnType() {
		return this.trnType;
	}

	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}

	public List<CmnFileHdr> getCmnFileHdrs() {
		return this.cmnFileHdrs;
	}

	public void setCmnFileHdrs(List<CmnFileHdr> cmnFileHdrs) {
		this.cmnFileHdrs = cmnFileHdrs;
	}

	public CmnFileHdr addCmnFileHdr(CmnFileHdr cmnFileHdr) {
		getCmnFileHdrs().add(cmnFileHdr);
		cmnFileHdr.setCmnInterfaceMst(this);

		return cmnFileHdr;
	}

	public CmnFileHdr removeCmnFileHdr(CmnFileHdr cmnFileHdr) {
		getCmnFileHdrs().remove(cmnFileHdr);
		cmnFileHdr.setCmnInterfaceMst(null);

		return cmnFileHdr;
	}

}