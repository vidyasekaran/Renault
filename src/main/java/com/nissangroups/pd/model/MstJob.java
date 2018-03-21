package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_JOB database table.
 * 
 */
@Entity
@Table(name="MST_JOB")
@NamedQuery(name="MstJob.findAll", query="SELECT m FROM MstJob m")
public class MstJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="JOB_SEQ_ID")
	private long jobSeqId;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="JOB_DESC")
	private String jobDesc;

	@Column(name="SHELL_PATH")
	private String shellPath;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Transient
	private String inputParam;
	public MstJob() {
	}

	public long getJobSeqId() {
		return this.jobSeqId;
	}

	public void setJobSeqId(long jobSeqId) {
		this.jobSeqId = jobSeqId;
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

	public String getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getShellPath() {
		return this.shellPath;
	}

	public void setShellPath(String shellPath) {
		this.shellPath = shellPath;
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
	 * @return the inputParam
	 */
	public String getInputParam() {
		return inputParam;
	}

	/**
	 * @param inputParam the inputParam to set
	 */
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
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