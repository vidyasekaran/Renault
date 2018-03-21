package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
 


/**
 * The persistent class for the TRN_JOBSTRM_SHDL database table.
 * 
 */
@Entity
@Table(name="TRN_JOBSTRM_SHDL")
@NamedQuery(name="TrnJobstrmShdl.findAll", query="SELECT t FROM TrnJobstrmShdl t")
public class TrnJobstrmShdl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="JOBSTRM_SHDL_SEQ_ID")
	private long jobstrmShdlSeqId;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	
	@Column(name="END_DT")
	private Date endDt;

	
	@Column(name="END_TIME")
	private Timestamp endTime;

	@Column(name="JOBSTRM_SEQ_ID")
	private java.math.BigDecimal jobstrmSeqId;

	
	@Column(name="ST_DT")
	private  Date stDt;

	
	@Column(name="ST_TIME")
	private Timestamp stTime;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;
	
	@Column(name="ORDR_TAKE_BASE_WK_NO")
	private String ordrTakeBaseWkNo;
	@Transient
	private long jobStrmExecSeqId;
	
	public TrnJobstrmShdl() {
	}

	public long getJobstrmShdlSeqId() {
		return this.jobstrmShdlSeqId;
	}

	public void setJobstrmShdlSeqId(long jobstrmShdlSeqId) {
		this.jobstrmShdlSeqId = jobstrmShdlSeqId;
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

	public java.math.BigDecimal getJobstrmSeqId() {
		return this.jobstrmSeqId;
	}

	public void setJobstrmSeqId(java.math.BigDecimal jobstrmSeqId) {
		this.jobstrmSeqId = jobstrmSeqId;
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

	/**
	 * @return the jobStrmExecSeqId
	 */
	public long getJobStrmExecSeqId() {
		return jobStrmExecSeqId;
	}

	/**
	 * @param jobStrmExecSeqId the jobStrmExecSeqId to set
	 */
	public void setJobStrmExecSeqId(long jobStrmExecSeqId) {
		this.jobStrmExecSeqId = jobStrmExecSeqId;
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

	/**
	 * @return the ordrTakeBaseMnth
	 */
	public String getOrdrTakeBaseMnth() {
		return ordrTakeBaseMnth;
	}

	/**
	 * @param ordrTakeBaseMnth the ordrTakeBaseMnth to set
	 */
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}

	/**
	 * @return the ordrTakeBaseWkNo
	 */
	public String getOrdrTakeBaseWkNo() {
		return ordrTakeBaseWkNo;
	}

	/**
	 * @param ordrTakeBaseWkNo the ordrTakeBaseWkNo to set
	 */
	public void setOrdrTakeBaseWkNo(String ordrTakeBaseWkNo) {
		this.ordrTakeBaseWkNo = ordrTakeBaseWkNo;
	}

}