package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_EX_NO database table.
 * 
 */
@Entity
@Table(name="MST_EX_NO")
@NamedQuery(name="MstExNo.findAll", query="SELECT m FROM MstExNo m")
public class MstExNo implements Serializable {
	

	@EmbeddedId
	private MstExNoPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="MAX_INDCTR")
	private String maxIndctr;

	@Column(name="PRPSE_CD")
	private String prpseCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstExNo() {
	}

	public MstExNoPK getId() {
		return this.id;
	}

	public void setId(MstExNoPK id) {
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

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getMaxIndctr() {
		return this.maxIndctr;
	}

	public void setMaxIndctr(String maxIndctr) {
		this.maxIndctr = maxIndctr;
	}

	public String getPrpseCd() {
		return this.prpseCd;
	}

	public void setPrpseCd(String prpseCd) {
		this.prpseCd = prpseCd;
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