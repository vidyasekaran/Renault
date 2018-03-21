package com.nissangroups.pd.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TMP_JOB_SCHEDULE database table.
 * 
 */
@Entity
@Table(name="TMP_JOB_SCHEDULE")
@NamedQuery(name="TmpJobSchedule.findAll", query="SELECT t FROM TmpJobSchedule t")
public class TmpJobSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BASE_ST_DATE")
	private Date baseStDate;

	@Column(name="BATCH_STATUS")
	private String batchStatus;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TMP_JOBS_SHDL_SEQ_GEN")
	@SequenceGenerator(name="TMP_JOBS_SHDL_SEQ_GEN", sequenceName="TMP_JOB_SCHEDULE_SEQ_ID",allocationSize=1)
	@Column(name="ID")
	private long id;

	@Column(name="JOBSTRM_SEQ_ID")
	private BigDecimal jobstrmSeqId;

	@Column(name="ORDR_TK_BS_MNTH")
	private String ordrTkBsMnth;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="WK_NUMBER")
	private String wkNumber;

	public TmpJobSchedule() {
	}

	public Date getBaseStDate() {
		return this.baseStDate;
	}

	public void setBaseStDate(Date baseStDate) {
		this.baseStDate = baseStDate;
	}

	public String getBatchStatus() {
		return this.batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getJobstrmSeqId() {
		return this.jobstrmSeqId;
	}

	public void setJobstrmSeqId(BigDecimal jobstrmSeqId) {
		this.jobstrmSeqId = jobstrmSeqId;
	}

	public String getOrdrTkBsMnth() {
		return this.ordrTkBsMnth;
	}

	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getWkNumber() {
		return this.wkNumber;
	}

	public void setWkNumber(String wkNumber) {
		this.wkNumber = wkNumber;
	}

}