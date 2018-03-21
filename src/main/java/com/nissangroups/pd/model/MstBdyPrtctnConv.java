package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_BDY_PRTCTN_CONV database table.
 * 
 */
@Entity
@Table(name="MST_BDY_PRTCTN_CONV")
@NamedQuery(name="MstBdyPrtctnConv.findAll", query="SELECT m FROM MstBdyPrtctnConv m")
public class MstBdyPrtctnConv implements Serializable {
	

	@Id
	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	@Column(name="BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

	@Column(name="BDY_PRTCTN_DESC")
	private String bdyPrtctnDesc;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstBdyPrtctnConv() {
	}

	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}

	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}

	public String getBdyPrtctnCd() {
		return this.bdyPrtctnCd;
	}

	public void setBdyPrtctnCd(String bdyPrtctnCd) {
		this.bdyPrtctnCd = bdyPrtctnCd;
	}

	public String getBdyPrtctnDesc() {
		return this.bdyPrtctnDesc;
	}

	public void setBdyPrtctnDesc(String bdyPrtctnDesc) {
		this.bdyPrtctnDesc = bdyPrtctnDesc;
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