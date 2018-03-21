package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import com.nissangroups.pd.util.CommonUtil;

/**
 * The persistent class for the TRN_MNTHLY_PROD_ORDR database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_PROD_ORDR")
@NamedQuery(name="TrnMnthlyProdOrdr.findAll", query="SELECT t FROM TrnMnthlyProdOrdr t")
public class TrnMnthlyProdOrdr implements Serializable {
	

	@EmbeddedId
	private TrnMnthlyProdOrdrPK id;

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

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="OWNR_MNL")
	private String ownrMnl;

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

	public TrnMnthlyProdOrdr() {
	}

	public TrnMnthlyProdOrdrPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyProdOrdrPK id) {
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

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
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