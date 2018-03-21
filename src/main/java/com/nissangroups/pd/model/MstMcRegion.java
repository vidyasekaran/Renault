package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_MC_REGION database table.
 * 
 */
@Entity
@Table(name="MST_MC_REGION")
@NamedQuery(name="MstMcRegion.findAll", query="SELECT m FROM MstMcRegion m")
public class MstMcRegion implements Serializable {
	

	@Id
	@Column(name="MC_REGION_CD")
	private String mcRegionCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="MC_REGION_DESC")
	private String mcRegionDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyerGrp
	@OneToMany(mappedBy="mstMcRegion")
	private List<MstBuyerGrp> mstBuyerGrps;

	//bi-directional many-to-one association to MstMcSubRegion
	@OneToMany(mappedBy="mstMcRegion")
	private List<MstMcSubRegion> mstMcSubRegions;

	public MstMcRegion() {
	}

	public String getMcRegionCd() {
		return this.mcRegionCd;
	}

	public void setMcRegionCd(String mcRegionCd) {
		this.mcRegionCd = mcRegionCd;
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

	public String getMcRegionDesc() {
		return this.mcRegionDesc;
	}

	public void setMcRegionDesc(String mcRegionDesc) {
		this.mcRegionDesc = mcRegionDesc;
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
		mstBuyerGrp.setMstMcRegion(this);

		return mstBuyerGrp;
	}

	public MstBuyerGrp removeMstBuyerGrp(MstBuyerGrp mstBuyerGrp) {
		getMstBuyerGrps().remove(mstBuyerGrp);
		mstBuyerGrp.setMstMcRegion(null);

		return mstBuyerGrp;
	}

	public List<MstMcSubRegion> getMstMcSubRegions() {
		return this.mstMcSubRegions;
	}

	public void setMstMcSubRegions(List<MstMcSubRegion> mstMcSubRegions) {
		this.mstMcSubRegions = mstMcSubRegions;
	}

	public MstMcSubRegion addMstMcSubRegion(MstMcSubRegion mstMcSubRegion) {
		getMstMcSubRegions().add(mstMcSubRegion);
		mstMcSubRegion.setMstMcRegion(this);

		return mstMcSubRegion;
	}

	public MstMcSubRegion removeMstMcSubRegion(MstMcSubRegion mstMcSubRegion) {
		getMstMcSubRegions().remove(mstMcSubRegion);
		mstMcSubRegion.setMstMcRegion(null);

		return mstMcSubRegion;
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