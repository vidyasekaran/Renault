package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_REGIONAL_HEAD_QRTR database table.
 * 
 */
@Entity
@Table(name="MST_REGIONAL_HEAD_QRTR")
@NamedQuery(name="MstRegionalHeadQrtr.findAll", query="SELECT m FROM MstRegionalHeadQrtr m")
public class MstRegionalHeadQrtr implements Serializable {
	

	@Id
	@Column(name="RHQ_CD")
	private String rhqCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="RHQ_DESC")
	private String rhqDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyerGrp
	@OneToMany(mappedBy="mstRegionalHeadQrtr")
	private List<MstBuyerGrp> mstBuyerGrps;

	public MstRegionalHeadQrtr() {
	}

	public String getRhqCd() {
		return this.rhqCd;
	}

	public void setRhqCd(String rhqCd) {
		this.rhqCd = rhqCd;
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

	public String getRhqDesc() {
		return this.rhqDesc;
	}

	public void setRhqDesc(String rhqDesc) {
		this.rhqDesc = rhqDesc;
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

	public List<MstBuyerGrp> getMstBuyerGrps() {
		return this.mstBuyerGrps;
	}

	public void setMstBuyerGrps(List<MstBuyerGrp> mstBuyerGrps) {
		this.mstBuyerGrps = mstBuyerGrps;
	}

	public MstBuyerGrp addMstBuyerGrp(MstBuyerGrp mstBuyerGrp) {
		getMstBuyerGrps().add(mstBuyerGrp);
		mstBuyerGrp.setMstRegionalHeadQrtr(this);

		return mstBuyerGrp;
	}

	public MstBuyerGrp removeMstBuyerGrp(MstBuyerGrp mstBuyerGrp) {
		getMstBuyerGrps().remove(mstBuyerGrp);
		mstBuyerGrp.setMstRegionalHeadQrtr(null);

		return mstBuyerGrp;
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