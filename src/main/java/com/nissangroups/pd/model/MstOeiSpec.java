package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_OEI_SPEC database table.
 * 
 */
@Entity
@Table(name="MST_OEI_SPEC")
@NamedQuery(name="MstOeiSpec.findAll", query="SELECT m FROM MstOeiSpec m")
public class MstOeiSpec implements Serializable {
	

	@EmbeddedId
	private MstOeiSpecPK id;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="OEI_SPEC_ID")
	private String oeiSpecId;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstOeiSpec() {
	}

	public MstOeiSpecPK getId() {
		return this.id;
	}

	public void setId(MstOeiSpecPK id) {
		this.id = id;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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

	public String getOeiSpecId() {
		return this.oeiSpecId;
	}

	public void setOeiSpecId(String oeiSpecId) {
		this.oeiSpecId = oeiSpecId;
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