package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_OSEI database table.
 * 
 */
@Entity
@Table(name="MST_OSEI")
@NamedQuery(name="MstOsei.findAll", query="SELECT m FROM MstOsei m")
public class MstOsei implements Serializable {
	

	@EmbeddedId
	private MstOseiPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstOsei() {
	}

	public MstOseiPK getId() {
		return this.id;
	}

	public void setId(MstOseiPK id) {
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