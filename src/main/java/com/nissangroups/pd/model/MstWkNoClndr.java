package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_WK_NO_CLNDR database table.
 * 
 */
@Entity
@Table(name="MST_WK_NO_CLNDR")
@NamedQuery(name="MstWkNoClndr.findAll", query="SELECT m FROM MstWkNoClndr m")
public class MstWkNoClndr implements Serializable {
	

	@EmbeddedId
	private MstWkNoClndrPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="NON_OPRTNL_FLAG")
	private String nonOprtnlFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="WK_END_DATE")
	private String wkEndDate;

	@Column(name="WK_NO_YEAR")
	private String wkNoYear;

	@Column(name="WK_STRT_DATE")
	private String wkStrtDate;

	public MstWkNoClndr() {
	}

	public MstWkNoClndrPK getId() {
		return this.id;
	}

	public void setId(MstWkNoClndrPK id) {
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

	public String getNonOprtnlFlag() {
		return this.nonOprtnlFlag;
	}

	public void setNonOprtnlFlag(String nonOprtnlFlag) {
		this.nonOprtnlFlag = nonOprtnlFlag;
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

	public String getWkEndDate() {
		return this.wkEndDate;
	}

	public void setWkEndDate(String wkEndDate) {
		this.wkEndDate = wkEndDate;
	}

	public String getWkNoYear() {
		return this.wkNoYear;
	}

	public void setWkNoYear(String wkNoYear) {
		this.wkNoYear = wkNoYear;
	}

	public String getWkStrtDate() {
		return this.wkStrtDate;
	}

	public void setWkStrtDate(String wkStrtDate) {
		this.wkStrtDate = wkStrtDate;
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