package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_NSC_CNFRMTN_MNTHLY database table.
 * 
 */
@Entity
@Table(name="TRN_NSC_CNFRMTN_MNTHLY")
@NamedQuery(name="TrnNscCnfrmtnMnthly.findAll", query="SELECT t FROM TrnNscCnfrmtnMnthly t")
public class TrnNscCnfrmtnMnthly implements Serializable {
	

	@EmbeddedId
	private TrnNscCnfrmtnMnthlyPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="NSC_CMPLT_FLAG")
	private String nscCmpltFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnNscCnfrmtnMnthly() {
	}

	public TrnNscCnfrmtnMnthlyPK getId() {
		return this.id;
	}

	public void setId(TrnNscCnfrmtnMnthlyPK id) {
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

	public String getNscCmpltFlag() {
		return this.nscCmpltFlag;
	}

	public void setNscCmpltFlag(String nscCmpltFlag) {
		this.nscCmpltFlag = nscCmpltFlag;
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