package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_MNL_DUE_DATE_PRMTR database table.
 * 
 */
@Entity
@Table(name="TRN_MNL_DUE_DATE_PRMTR")
@NamedQuery(name="TrnMnlDueDatePrmtr.findAll", query="SELECT t FROM TrnMnlDueDatePrmtr t")
public class TrnMnlDueDatePrmtr implements Serializable {
	

	@EmbeddedId
	private TrnMnlDueDatePrmtrPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DUE_DATE_FRM")
	private String dueDateFrm;

	@Column(name="DUE_DATE_ORDR_QTY")
	private BigDecimal dueDateOrdrQty;

	@Column(name="DUE_DATE_TO")
	private String dueDateTo;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnMnlDueDatePrmtr() {
	}

	public TrnMnlDueDatePrmtrPK getId() {
		return this.id;
	}

	public void setId(TrnMnlDueDatePrmtrPK id) {
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

	public String getDueDateFrm() {
		return this.dueDateFrm;
	}

	public void setDueDateFrm(String dueDateFrm) {
		this.dueDateFrm = dueDateFrm;
	}

	public BigDecimal getDueDateOrdrQty() {
		return this.dueDateOrdrQty;
	}

	public void setDueDateOrdrQty(BigDecimal dueDateOrdrQty) {
		this.dueDateOrdrQty = dueDateOrdrQty;
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