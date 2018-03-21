package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_PLNT_CD database table.
 * 
 */
@Entity
@Table(name="MST_PLNT_CD")
@NamedQuery(name="MstPlntCd.findAll", query="SELECT m FROM MstPlntCd m")
public class MstPlntCd implements Serializable {
	

	@EmbeddedId
	private MstPlntCdPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="PLNT_DESC")
	private String plntDesc;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstPlntCd() {
	}

	public MstPlntCdPK getId() {
		return this.id;
	}

	public void setId(MstPlntCdPK id) {
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

	public String getPlntDesc() {
		return this.plntDesc;
	}

	public void setPlntDesc(String plntDesc) {
		this.plntDesc = plntDesc;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
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