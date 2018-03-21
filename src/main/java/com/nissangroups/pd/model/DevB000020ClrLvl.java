package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the DEV_B000020_CLR_LVL database table.
 * 
 */
@Entity
@Table(name="DEV_B000020_CLR_LVL")
@NamedQuery(name="DevB000020ClrLvl.findAll", query="SELECT d FROM DevB000020ClrLvl d")
public class DevB000020ClrLvl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DevB000020ClrLvlPK id;

	@Column(name="ALLOCATED_ORDR_QTY")
	private BigDecimal allocatedOrdrQty;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="IDEAL_MIX_RATIO")
	private BigDecimal idealMixRatio;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="PRCSS_FLG")
	private String prcssFlg;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public DevB000020ClrLvl() {
	}

	public DevB000020ClrLvlPK getId() {
		return this.id;
	}

	public void setId(DevB000020ClrLvlPK id) {
		this.id = id;
	}

	public BigDecimal getAllocatedOrdrQty() {
		return this.allocatedOrdrQty;
	}

	public void setAllocatedOrdrQty(BigDecimal allocatedOrdrQty) {
		this.allocatedOrdrQty = allocatedOrdrQty;
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

	public BigDecimal getIdealMixRatio() {
		return this.idealMixRatio;
	}

	public void setIdealMixRatio(BigDecimal idealMixRatio) {
		this.idealMixRatio = idealMixRatio;
	}

	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}

	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getPrcssFlg() {
		return this.prcssFlg;
	}

	public void setPrcssFlg(String prcssFlg) {
		this.prcssFlg = prcssFlg;
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