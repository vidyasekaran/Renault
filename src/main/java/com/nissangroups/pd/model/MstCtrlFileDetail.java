package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MST_CTRL_FILE_DETAILS database table.
 * 
 */
@Entity
@Table(name="MST_CTRL_FILE_DETAILS")
@NamedQuery(name="MstCtrlFileDetail.findAll", query="SELECT m FROM MstCtrlFileDetail m")
public class MstCtrlFileDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="CMPRSSN_CLASS")
	private String cmprssnClass;

	private String code;

	@Column(name="CTRL_FILE_CHAR_CODE")
	private String ctrlFileCharCode;

	@Column(name="DATE_ATTRI")
	private String dateAttri;

	@Column(name="\"DESC\"")
	private String desc;

	@Column(name="FILE_RCGNZ_CODE")
	private String fileRcgnzCode;

	@Column(name="INFO_TYPE")
	private String infoType;

	private String kanji;

	@Column(name="NEW_LINE_CODE")
	private String newLineCode;

	@Column(name="PROCESS_CLASS")
	private String processClass;

	private String rcevr;

	@Column(name="\"RECRD _FRMT_NEW_LINE_CODE\"")
	private String recrd_FrmtNewLineCode;

	@Column(name="RECRD_FRMT_FIX_VARIABLE")
	private String recrdFrmtFixVariable;

	@Column(name="RSRV_AREA")
	private String rsrvArea;

	private String sndr;

	@Column(name="TIME_STAMP")
	private String timeStamp;

	public MstCtrlFileDetail() {
	}

	public String getIfFileId() {
		return this.ifFileId;
	}

	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}

	public String getCmprssnClass() {
		return this.cmprssnClass;
	}

	public void setCmprssnClass(String cmprssnClass) {
		this.cmprssnClass = cmprssnClass;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCtrlFileCharCode() {
		return this.ctrlFileCharCode;
	}

	public void setCtrlFileCharCode(String ctrlFileCharCode) {
		this.ctrlFileCharCode = ctrlFileCharCode;
	}

	public String getDateAttri() {
		return this.dateAttri;
	}

	public void setDateAttri(String dateAttri) {
		this.dateAttri = dateAttri;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFileRcgnzCode() {
		return this.fileRcgnzCode;
	}

	public void setFileRcgnzCode(String fileRcgnzCode) {
		this.fileRcgnzCode = fileRcgnzCode;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getKanji() {
		return this.kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public String getNewLineCode() {
		return this.newLineCode;
	}

	public void setNewLineCode(String newLineCode) {
		this.newLineCode = newLineCode;
	}

	public String getProcessClass() {
		return this.processClass;
	}

	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}

	public String getRcevr() {
		return this.rcevr;
	}

	public void setRcevr(String rcevr) {
		this.rcevr = rcevr;
	}

	public String getRecrd_FrmtNewLineCode() {
		return this.recrd_FrmtNewLineCode;
	}

	public void setRecrd_FrmtNewLineCode(String recrd_FrmtNewLineCode) {
		this.recrd_FrmtNewLineCode = recrd_FrmtNewLineCode;
	}

	public String getRecrdFrmtFixVariable() {
		return this.recrdFrmtFixVariable;
	}

	public void setRecrdFrmtFixVariable(String recrdFrmtFixVariable) {
		this.recrdFrmtFixVariable = recrdFrmtFixVariable;
	}

	public String getRsrvArea() {
		return this.rsrvArea;
	}

	public void setRsrvArea(String rsrvArea) {
		this.rsrvArea = rsrvArea;
	}

	public String getSndr() {
		return this.sndr;
	}

	public void setSndr(String sndr) {
		this.sndr = sndr;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}