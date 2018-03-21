package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_BUYER_GRP database table.
 * 
 */
@Entity
@Table(name="MST_BUYER_GRP")
@NamedQuery(name="MstBuyerGrp.findAll", query="SELECT m FROM MstBuyerGrp m")
public class MstBuyerGrp implements Serializable {
	

	@Id
	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="BUYER_GRP_DESC")
	private String buyerGrpDesc;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="SUB_REGION_CD")
	private String subRegionCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyer
	@OneToMany(mappedBy="mstBuyerGrp")
	private List<MstBuyer> mstBuyers;

	//bi-directional many-to-one association to MstMcRegion
	@ManyToOne
	@JoinColumn(name="MC_REGION_CD")
	private MstMcRegion mstMcRegion;

	//bi-directional many-to-one association to MstRegionalHeadQrtr
	@ManyToOne
	@JoinColumn(name="RHQ_CD")
	private MstRegionalHeadQrtr mstRegionalHeadQrtr;

	public MstBuyerGrp() {
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getBuyerGrpDesc() {
		return this.buyerGrpDesc;
	}

	public void setBuyerGrpDesc(String buyerGrpDesc) {
		this.buyerGrpDesc = buyerGrpDesc;
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

	public String getSubRegionCd() {
		return this.subRegionCd;
	}

	public void setSubRegionCd(String subRegionCd) {
		this.subRegionCd = subRegionCd;
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

	public List<MstBuyer> getMstBuyers() {
		return this.mstBuyers;
	}

	public void setMstBuyers(List<MstBuyer> mstBuyers) {
		this.mstBuyers = mstBuyers;
	}

	public MstBuyer addMstBuyer(MstBuyer mstBuyer) {
		getMstBuyers().add(mstBuyer);
		mstBuyer.setMstBuyerGrp(this);

		return mstBuyer;
	}

	public MstBuyer removeMstBuyer(MstBuyer mstBuyer) {
		getMstBuyers().remove(mstBuyer);
		mstBuyer.setMstBuyerGrp(null);

		return mstBuyer;
	}

	public MstMcRegion getMstMcRegion() {
		return this.mstMcRegion;
	}

	public void setMstMcRegion(MstMcRegion mstMcRegion) {
		this.mstMcRegion = mstMcRegion;
	}

	public MstRegionalHeadQrtr getMstRegionalHeadQrtr() {
		return this.mstRegionalHeadQrtr;
	}

	public void setMstRegionalHeadQrtr(MstRegionalHeadQrtr mstRegionalHeadQrtr) {
		this.mstRegionalHeadQrtr = mstRegionalHeadQrtr;
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