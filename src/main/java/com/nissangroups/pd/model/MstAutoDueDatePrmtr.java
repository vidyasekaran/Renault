package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_AUTO_DUE_DATE_PRMTR database table.
 * 
 */
@Entity
@Table(name="MST_AUTO_DUE_DATE_PRMTR")
@NamedQuery(name="MstAutoDueDatePrmtr.findAll", query="SELECT m FROM MstAutoDueDatePrmtr m")
public class MstAutoDueDatePrmtr implements Serializable {
	

	@EmbeddedId
	private MstAutoDueDatePrmtrPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstAutoDueDatePttrn
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="DUE_DATE_PTTRN_CD", referencedColumnName="DUE_DATE_PTTRN_CD",insertable=false,updatable=false),
		@JoinColumn(name="POR_CD", referencedColumnName="POR_CD",insertable=false,updatable=false)
		})
	private MstAutoDueDatePttrn mstAutoDueDatePttrn;

	public MstAutoDueDatePrmtr() {
	}

	public MstAutoDueDatePrmtrPK getId() {
		return this.id;
	}

	public void setId(MstAutoDueDatePrmtrPK id) {
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

	public MstAutoDueDatePttrn getMstAutoDueDatePttrn() {
		return this.mstAutoDueDatePttrn;
	}

	public void setMstAutoDueDatePttrn(MstAutoDueDatePttrn mstAutoDueDatePttrn) {
		this.mstAutoDueDatePttrn = mstAutoDueDatePttrn;
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