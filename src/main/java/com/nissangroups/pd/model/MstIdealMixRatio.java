package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_IDEAL_MIX_RATIO database table.
 * 
 */
@Entity
@Table(name="MST_IDEAL_MIX_RATIO")
@NamedQuery(name="MstIdealMixRatio.findAll", query="SELECT m FROM MstIdealMixRatio m")
public class MstIdealMixRatio implements Serializable {
	

	@EmbeddedId
	private MstIdealMixRatioPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="IDEAL_MIX_FRCST_RATIO")
	private String idealMixFrcstRatio;

	@Column(name="IDEAL_MIX_FRCST_VOL")
	private String idealMixFrcstVol;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIdealMixRatio() {
	}

	public MstIdealMixRatioPK getId() {
		return this.id;
	}

	public void setId(MstIdealMixRatioPK id) {
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

	public String getIdealMixFrcstRatio() {
		return this.idealMixFrcstRatio;
	}

	public void setIdealMixFrcstRatio(String idealMixFrcstRatio) {
		this.idealMixFrcstRatio = idealMixFrcstRatio;
	}

	public String getIdealMixFrcstVol() {
		return this.idealMixFrcstVol;
	}

	public void setIdealMixFrcstVol(String idealMixFrcstVol) {
		this.idealMixFrcstVol = idealMixFrcstVol;
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