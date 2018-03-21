package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_AUTO_DUE_DATE_PRMTR_IF database table.
 * 
 */
@Entity
@Table(name="TRN_AUTO_DUE_DATE_PRMTR_IF")
@NamedQuery(name="TrnAutoDueDatePrmtrIf.findAll", query="SELECT t FROM TrnAutoDueDatePrmtrIf t")
public class TrnAutoDueDatePrmtrIf implements Serializable {
	

	@EmbeddedId
	private TrnAutoDueDatePrmtrIfPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DUE_DATE_TO")
	private String dueDateTo;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnAutoDueDatePrmtrIf() {
	}

	public TrnAutoDueDatePrmtrIfPK getId() {
		return this.id;
	}

	public void setId(TrnAutoDueDatePrmtrIfPK id) {
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

	public String getDueDateTo() {
		return this.dueDateTo;
	}

	public void setDueDateTo(String dueDateTo) {
		this.dueDateTo = dueDateTo;
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