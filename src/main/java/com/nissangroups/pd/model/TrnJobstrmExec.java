package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
 


/**
 * The persistent class for the TRN_JOBSTRM_EXEC database table.
 * 
 */
@Entity
@Table(name="TRN_JOBSTRM_EXEC")
@NamedQuery(name="TrnJobstrmExec.findAll", query="SELECT t FROM TrnJobstrmExec t")
public class TrnJobstrmExec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="JOBSTRM_EXEC_SEQ_ID")
	private long jobstrmExecSeqId;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	
	@Column(name="END_DT")
	private Date endDt;

	@Column(name="END_TIME")
	private Timestamp endTime;

	@Column(name="JOBSTRM_SHDL_SEQ_ID")
	private java.math.BigDecimal jobstrmShdlSeqId;

	@Column(name="JS_EXECUTION_STATUS")
	private String jsExecutionStatus;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PRODUCTION_WKNO")
	private String productionWkno;

	
	@Column(name="ST_DT")
	private Date stDt;

	
	@Column(name="ST_TIME")
	private Timestamp stTime;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnJobstrmExec() {
	}

	public long getJobstrmExecSeqId() {
		return this.jobstrmExecSeqId;
	}

	public void setJobstrmExecSeqId(long jobstrmExecSeqId) {
		this.jobstrmExecSeqId = jobstrmExecSeqId;
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

	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public java.math.BigDecimal getJobstrmShdlSeqId() {
		return this.jobstrmShdlSeqId;
	}

	public void setJobstrmShdlSeqId(java.math.BigDecimal jobstrmShdlSeqId) {
		this.jobstrmShdlSeqId = jobstrmShdlSeqId;
	}

	public String getJsExecutionStatus() {
		return this.jsExecutionStatus;
	}

	public void setJsExecutionStatus(String jsExecutionStatus) {
		this.jsExecutionStatus = jsExecutionStatus;
	}

	public String getOrdrtkBaseMnth() {
		return this.ordrtkBaseMnth;
	}

	public void setOrdrtkBaseMnth(String ordrtkBaseMnth) {
		this.ordrtkBaseMnth = ordrtkBaseMnth;
	}

	public String getProductionWkno() {
		return this.productionWkno;
	}

	public void setProductionWkno(String productionWkno) {
		this.productionWkno = productionWkno;
	}

	public Date getStDt() {
		return this.stDt;
	}

	public void setStDt(Date stDt) {
		this.stDt = stDt;
	}

	public Timestamp getStTime() {
		return this.stTime;
	}

	public void setStTime(Timestamp stTime) {
		this.stTime = stTime;
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