package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the HIST_LGCL_PPLN database table.
 * 
 */
@Entity
@Table(name="HIST_LGCL_PPLN")
@NamedQuery(name="HistLgclPpln.findAll", query="SELECT h FROM HistLgclPpln h")
public class HistLgclPpln implements Serializable {
	

	@EmbeddedId
	private HistLgclPplnPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="NEW_OSEI_ID")
	private String newOseiId;

	@Column(name="NEW_POT_CD")
	private String newPotCd;

	@Column(name="ORDR_DEL_FLAG")
	private String ordrDelFlag;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="SUCCESS_FAIL_STTS")
	private String successFailStts;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public HistLgclPpln() {
	}

	public HistLgclPplnPK getId() {
		return this.id;
	}

	public void setId(HistLgclPplnPK id) {
		this.id = id;
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

	public String getNewOseiId() {
		return this.newOseiId;
	}

	public void setNewOseiId(String newOseiId) {
		this.newOseiId = newOseiId;
	}

	public String getNewPotCd() {
		return this.newPotCd;
	}

	public void setNewPotCd(String newPotCd) {
		this.newPotCd = newPotCd;
	}

	public String getOrdrDelFlag() {
		return this.ordrDelFlag;
	}

	public void setOrdrDelFlag(String ordrDelFlag) {
		this.ordrDelFlag = ordrDelFlag;
	}

	public String getOseiId() {
		return this.oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdMnth() {
		return this.prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public String getProdWkNo() {
		return this.prodWkNo;
	}

	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	public String getSuccessFailStts() {
		return this.successFailStts;
	}

	public void setSuccessFailStts(String successFailStts) {
		this.successFailStts = successFailStts;
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