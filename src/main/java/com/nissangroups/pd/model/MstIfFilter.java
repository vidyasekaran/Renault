package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_IF_FILTER database table.
 * 
 */
@Entity
@Table(name="MST_IF_FILTER")
@NamedQuery(name="MstIfFilter.findAll", query="SELECT m FROM MstIfFilter m")
public class MstIfFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MstIfFilterPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FLTR_TYPE")
	private String fltrType;

	@Column(name="FLTR_VAL")
	private String fltrVal;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIfFilter() {
	}

	public MstIfFilterPK getId() {
		return this.id;
	}

	public void setId(MstIfFilterPK id) {
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

	public String getFltrType() {
		return this.fltrType;
	}

	public void setFltrType(String fltrType) {
		this.fltrType = fltrType;
	}

	public String getFltrVal() {
		return this.fltrVal;
	}

	public void setFltrVal(String fltrVal) {
		this.fltrVal = fltrVal;
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