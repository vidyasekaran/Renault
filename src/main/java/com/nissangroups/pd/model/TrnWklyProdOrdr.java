package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_WKLY_PROD_ORDR database table.
 * 
 */
@Entity
@Table(name="TRN_WKLY_PROD_ORDR")
@NamedQuery(name="TrnWklyProdOrdr.findAll", query="SELECT t FROM TrnWklyProdOrdr t")
public class TrnWklyProdOrdr implements Serializable {
	

	@EmbeddedId
	private TrnWklyProdOrdrPK id;

	@Column(name="BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DEALER_LST")
	private String dealerLst;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="ORDR_FXD_FLAG")
	private String ordrFxdFlag;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="OWNR_MNL")
	private String ownrMnl;

	@Column(name="PROD_DAY_NO")
	private String prodDayNo;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="SLS_NOTE_NO")
	private String slsNoteNo;

	@Column(name="TYRE_MKR_CD")
	private String tyreMkrCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="WRNTY_BKLT")
	private String wrntyBklt;

	public TrnWklyProdOrdr() {
	}

	public TrnWklyProdOrdrPK getId() {
		return this.id;
	}

	public void setId(TrnWklyProdOrdrPK id) {
		this.id = id;
	}

	public String getBdyPrtctnCd() {
		return this.bdyPrtctnCd;
	}

	public void setBdyPrtctnCd(String bdyPrtctnCd) {
		this.bdyPrtctnCd = bdyPrtctnCd;
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

	public String getDealerLst() {
		return this.dealerLst;
	}

	public void setDealerLst(String dealerLst) {
		this.dealerLst = dealerLst;
	}

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getOrdrFxdFlag() {
		return this.ordrFxdFlag;
	}

	public void setOrdrFxdFlag(String ordrFxdFlag) {
		this.ordrFxdFlag = ordrFxdFlag;
	}

	public BigDecimal getOrdrQty() {
		return this.ordrQty;
	}

	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getOwnrMnl() {
		return this.ownrMnl;
	}

	public void setOwnrMnl(String ownrMnl) {
		this.ownrMnl = ownrMnl;
	}

	public String getProdDayNo() {
		return this.prodDayNo;
	}

	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getSlsNoteNo() {
		return this.slsNoteNo;
	}

	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
	}

	public String getTyreMkrCd() {
		return this.tyreMkrCd;
	}

	public void setTyreMkrCd(String tyreMkrCd) {
		this.tyreMkrCd = tyreMkrCd;
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

	public String getWrntyBklt() {
		return this.wrntyBklt;
	}

	public void setWrntyBklt(String wrntyBklt) {
		this.wrntyBklt = wrntyBklt;
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