package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_IDEAL_MIX_PRITY database table.
 * 
 */
@Entity
@Table(name="MST_IDEAL_MIX_PRITY")
@NamedQuery(name="MstIdealMixPrity.findAll", query="SELECT m FROM MstIdealMixPrity m")
public class MstIdealMixPrity implements Serializable {
	


	@EmbeddedId
	private MstIdealMixPrityPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="EIM_RATIO_PRITY_FLAG")
	private String eimRatioPrityFlag;


	@Column(name="CLR_RATIO_PRITY_FLAG")
	private String clrRatioPrityFlag;
	
	
	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIdealMixPrity() {
	}

	public String getClrRatioPrityFlag() {
		return this.clrRatioPrityFlag;
	}

	public void setClrRatioPrityFlag(String clrRatioPrityFlag) {
		this.clrRatioPrityFlag = clrRatioPrityFlag;
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

	public String getEimRatioPrityFlag() {
		return this.eimRatioPrityFlag;
	}

	public void setEimRatioPrityFlag(String eimRatioPrityFlag) {
		this.eimRatioPrityFlag = eimRatioPrityFlag;
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

	public MstIdealMixPrityPK getId() {
		return id;
	}

	public void setId(MstIdealMixPrityPK id) {
		this.id = id;
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