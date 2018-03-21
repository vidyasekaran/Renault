package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_END_ITM_SPEC database table.
 * 
 */
@Entity
@Table(name="MST_END_ITM_SPEC")
@NamedQuery(name="MstEndItmSpec.findAll", query="SELECT m FROM MstEndItmSpec m")
public class MstEndItmSpec implements Serializable {
	
	@EmbeddedId
	private MstEndItmSpecPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="EI_SPEC_ERR_FLAG")
	private String eiSpecErrFlag;

	@Column(name="EI_SPEC_INT_ERR_CD")
	private String eiSpecIntErrCd;

	@Column(name="LCL_NOTE")
	private String lclNote;

	@Column(name="MDFD_FLAG")
	private String mdfdFlag;

	@Column(name="OPTNL_SPEC_CD")
	private String optnlSpecCd;

	@Column(name="PCKGE_NAME")
	private String pckgeName;

	@Column(name="TOKUSO_NAME")
	private String tokusoName;

	@Column(name="UPDTD_BY")
	private String updtdBy;


	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstEndItmSpec() {
	}

	public MstEndItmSpecPK getId() {
		return this.id;
	}

	public void setId(MstEndItmSpecPK id) {
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

	public String getEiSpecErrFlag() {
		return this.eiSpecErrFlag;
	}

	public void setEiSpecErrFlag(String eiSpecErrFlag) {
		this.eiSpecErrFlag = eiSpecErrFlag;
	}

	public String getEiSpecIntErrCd() {
		return this.eiSpecIntErrCd;
	}

	public void setEiSpecIntErrCd(String eiSpecIntErrCd) {
		this.eiSpecIntErrCd = eiSpecIntErrCd;
	}

	public String getLclNote() {
		return this.lclNote;
	}

	public void setLclNote(String lclNote) {
		this.lclNote = lclNote;
	}

	public String getMdfdFlag() {
		return this.mdfdFlag;
	}

	public void setMdfdFlag(String mdfdFlag) {
		this.mdfdFlag = mdfdFlag;
	}

	public String getOptnlSpecCd() {
		return this.optnlSpecCd;
	}

	public void setOptnlSpecCd(String optnlSpecCd) {
		this.optnlSpecCd = optnlSpecCd;
	}

	public String getPckgeName() {
		return this.pckgeName;
	}

	public void setPckgeName(String pckgeName) {
		this.pckgeName = pckgeName;
	}

	public String getTokusoName() {
		return this.tokusoName;
	}

	public void setTokusoName(String tokusoName) {
		this.tokusoName = tokusoName;
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