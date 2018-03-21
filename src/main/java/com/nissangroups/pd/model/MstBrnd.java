package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_BRND database table.
 * 
 */
@Entity
@Table(name="MST_BRND")
@NamedQuery(name="MstBrnd.findAll", query="SELECT m FROM MstBrnd m")
public class MstBrnd implements Serializable {
	

	@Id
	@Column(name="BRND_CD")
	private String brndCd;

	@Column(name="BRND_DESC")
	private String brndDesc;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstPorCarSr
	@OneToMany(mappedBy="mstBrnd")
	private List<MstPorCarSr> mstPorCarSrs;

	public MstBrnd() {
	}

	public String getBrndCd() {
		return this.brndCd;
	}

	public void setBrndCd(String brndCd) {
		this.brndCd = brndCd;
	}

	public String getBrndDesc() {
		return this.brndDesc;
	}

	public void setBrndDesc(String brndDesc) {
		this.brndDesc = brndDesc;
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

	public List<MstPorCarSr> getMstPorCarSrs() {
		return this.mstPorCarSrs;
	}

	public void setMstPorCarSrs(List<MstPorCarSr> mstPorCarSrs) {
		this.mstPorCarSrs = mstPorCarSrs;
	}

	public MstPorCarSr addMstPorCarSr(MstPorCarSr mstPorCarSr) {
		getMstPorCarSrs().add(mstPorCarSr);
		mstPorCarSr.setMstBrnd(this);

		return mstPorCarSr;
	}

	public MstPorCarSr removeMstPorCarSr(MstPorCarSr mstPorCarSr) {
		getMstPorCarSrs().remove(mstPorCarSr);
		mstPorCarSr.setMstBrnd(null);

		return mstPorCarSr;
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