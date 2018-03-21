package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TRN_JOB_EXEC database table.
 * 
 */
@Entity
@Table(name="TRN_JOB_EXEC")
@NamedQuery(name="TrnJobExec.findAll", query="SELECT t FROM TrnJobExec t")
public class TrnJobExec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="JOB_EXEC_SEQ_ID")
	private long jobExecSeqId;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	 
	@Column(name="END_DT")
	private Date endDt;

	@Column(name="END_TIME")
	private Timestamp endTime;

	@Column(name="JOB_EXEC_STTS")
	private String jobExecStts;

	@Column(name="JOB_SEQ_ID")
	private BigDecimal jobSeqId;

	@Column(name="JOBSTRM_EXEC_SEQ_ID")
	private BigDecimal jobstrmExecSeqId;

	@Column(name="LOG_FILE_NAME")
	private String logFileName;

	@Column(name="LOG_FILE_PATH")
	private String logFilePath;

	 
	@Column(name="ST_DT")
	private Date stDt;

	
	@Column(name="ST_TIME")
	private Timestamp stTime;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnJobExec() {
	}

	public long getJobExecSeqId() {
		return this.jobExecSeqId;
	}

	public void setJobExecSeqId(long jobExecSeqId) {
		this.jobExecSeqId = jobExecSeqId;
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

	public String getJobExecStts() {
		return this.jobExecStts;
	}

	public void setJobExecStts(String jobExecStts) {
		this.jobExecStts = jobExecStts;
	}

	public BigDecimal getJobSeqId() {
		return this.jobSeqId;
	}

	public void setJobSeqId(BigDecimal jobSeqId) {
		this.jobSeqId = jobSeqId;
	}

	public BigDecimal getJobstrmExecSeqId() {
		return this.jobstrmExecSeqId;
	}

	public void setJobstrmExecSeqId(BigDecimal jobstrmExecSeqId) {
		this.jobstrmExecSeqId = jobstrmExecSeqId;
	}

	public String getLogFileName() {
		return this.logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getLogFilePath() {
		return this.logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
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