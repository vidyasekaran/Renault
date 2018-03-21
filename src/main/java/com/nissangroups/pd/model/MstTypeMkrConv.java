package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_TYPE_MKR_CONV database table.
 * 
 */
@Entity
@Table(name="MST_TYPE_MKR_CONV")
@NamedQuery(name="MstTypeMkrConv.findAll", query="SELECT m FROM MstTypeMkrConv m")
public class MstTypeMkrConv implements Serializable {
	

	@Id
	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="TYRE_MKR_CD")
	private String tyreMkrCd;

	@Column(name="TYRE_MKR_DESC")
	private String tyreMkrDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstTypeMkrConv() {
	}

	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}

	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
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

	public String getTyreMkrCd() {
		return this.tyreMkrCd;
	}

	public void setTyreMkrCd(String tyreMkrCd) {
		this.tyreMkrCd = tyreMkrCd;
	}

	public String getTyreMkrDesc() {
		return this.tyreMkrDesc;
	}

	public void setTyreMkrDesc(String tyreMkrDesc) {
		this.tyreMkrDesc = tyreMkrDesc;
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