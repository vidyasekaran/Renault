package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the DEV_TRN_MNTHLY_PROD_ORDR database table.
 * 
 */
@Entity
@Table(name="DEV_TRN_MNTHLY_PROD_ORDR")
@NamedQuery(name="DevTrnMnthlyProdOrdr.findAll", query="SELECT d FROM DevTrnMnthlyProdOrdr d")
public class DevTrnMnthlyProdOrdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DevTrnMnthlyProdOrdrPK id;

	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAL_OCF_USG_QTY")
	private BigDecimal calOcfUsgQty;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DEALER_LST")
	private String dealerLst;

	private BigDecimal diff;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_LMT_QTY")
	private BigDecimal ocfLmtQty;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="OWNR_MNL")
	private String ownrMnl;

	@Column(name="PCK_CD")
	private String pckCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="SLS_NOTE_NO")
	private String slsNoteNo;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="TYRE_MKR_CD")
	private String tyreMkrCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="WRNTY_BKLT")
	private String wrntyBklt;

	public DevTrnMnthlyProdOrdr() {
	}

	public DevTrnMnthlyProdOrdrPK getId() {
		return this.id;
	}

	public void setId(DevTrnMnthlyProdOrdrPK id) {
		this.id = id;
	}

	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}

	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}

	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getBdyPrtctnCd() {
		return this.bdyPrtctnCd;
	}

	public void setBdyPrtctnCd(String bdyPrtctnCd) {
		this.bdyPrtctnCd = bdyPrtctnCd;
	}

	public String getBuyerCd() {
		return this.buyerCd;
	}

	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public BigDecimal getCalOcfUsgQty() {
		return this.calOcfUsgQty;
	}

	public void setCalOcfUsgQty(BigDecimal calOcfUsgQty) {
		this.calOcfUsgQty = calOcfUsgQty;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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

	public BigDecimal getDiff() {
		return this.diff;
	}

	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getExtClrCd() {
		return this.extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getFeatCd() {
		return this.featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}

	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public BigDecimal getOcfLmtQty() {
		return this.ocfLmtQty;
	}

	public void setOcfLmtQty(BigDecimal ocfLmtQty) {
		this.ocfLmtQty = ocfLmtQty;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}

	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
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

	public String getPckCd() {
		return this.pckCd;
	}

	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	public String getProdFmyCd() {
		return this.prodFmyCd;
	}

	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}

	public String getSlsNoteNo() {
		return this.slsNoteNo;
	}

	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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