package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TRN_DAILY_OCF_LMT_IF database table.
 * 
 */
@Entity
@Table(name="TRN_DAILY_OCF_LMT_IF")
@NamedQuery(name="TrnDailyOcfLmtIf.findAll", query="SELECT t FROM TrnDailyOcfLmtIf t")
public class TrnDailyOcfLmtIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnDailyOcfLmtIfPK id;

	@Column(name="ERROR_FLG")
	private String errorFlg;

	@Column(name="ERROR_MSG")
	private String errorMsg;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="OCF_DATE")
	private String ocfDate;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_LIMIT")
	private String ocfLimit;

	public TrnDailyOcfLmtIf() {
	}

	public TrnDailyOcfLmtIfPK getId() {
		return this.id;
	}

	public void setId(TrnDailyOcfLmtIfPK id) {
		this.id = id;
	}

	public String getErrorFlg() {
		return this.errorFlg;
	}

	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFeatCd() {
		return this.featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public String getIfFileId() {
		return this.ifFileId;
	}

	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}

	public String getOcfDate() {
		return this.ocfDate;
	}

	public void setOcfDate(String ocfDate) {
		this.ocfDate = ocfDate;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public String getOcfLimit() {
		return this.ocfLimit;
	}

	public void setOcfLimit(String ocfLimit) {
		this.ocfLimit = ocfLimit;
	}

}