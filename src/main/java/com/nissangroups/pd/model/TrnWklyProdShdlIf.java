package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_MNTH_PROD_SHDL_IF database table.
 * 
 */
@Entity
@Table(name="TRN_WKLY_PROD_SHDL_IF")
@NamedQuery(name="TrnWklyProdShdlIf.findAll", query="SELECT t FROM TrnWklyProdShdlIf t")
public class TrnWklyProdShdlIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnWklyProdShdlIfPK id;

	@Column(name="PROD_FMLY_CD")
	private String prodFmlyCd;
	
	@Column(name="CAR_SRS")
	private String carSrs;
	
	@Column(name="BUYER_CD")
	private String buyerCd;
	
	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;
	
	@Column(name="PACK_CD")
	private String packCd;
	
	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;
	
	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;
	
	@Column(name="EXT_CLR_CD")
	private String extClrCd;
	
	@Column(name="INT_CLR_CD")
	private String intClrCd;
	
	@Column(name="POT_CD")
	private String potCd;
	
	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;
	
	@Column(name="PROD_WK_NO")
	private String prodWkNo;
	
	@Column(name="PROD_DAY_NO")
	private String prodDayNo;
	
	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;
	
	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;
	
	@Column(name="LINE_CLASS")
	private String lineClass;
	
	@Column(name="EX_NO")
	private String exNo;
	
	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;
	
	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;
	
	@Column(name="SLS_NOTE_NO")
	private String slsNoteNo;
	
	@Column(name="TYRE_MKR_CD")
	private String tyreMkrCd;
	
	@Column(name="DEALER_LST")
	private String dealerLst;
	
	@Column(name="OWNR_MNL")
	private String ownrMnl;
	
	@Column(name="WRNTY_BKLT")
	private String wrntyBklt;
	
	@Column(name="BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="WK_NO_OF_YEAR")
	private String wkNoOfYear;
	
	@Column(name="FXD_SYMBL")
	private String fxdSymbl;

	@Column(name="WK_FIX_WK_NO")
	private String wkFixWkNo;

	@Column(name="WK_FIX_SYMBL")
	private String wkFixSymbl;

	@Column(name="INTRNL_OR_TRD_FLAG")
	private String intrnlOrTrdFlag;
	
	@Column(name="OSEI_ID")
	private String oseiId;
	
	@Column(name="UPD_DT_TM")
	private String upDtTm;
	
    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnWklyProdShdlIf() {
	}

	public TrnWklyProdShdlIfPK getId() {
		return id;
	}

	public void setId(TrnWklyProdShdlIfPK id) {
		this.id = id;
	}

	public String getProdFmlyCd() {
		return prodFmlyCd;
	}

	public void setProdFmlyCd(String prodFmlyCd) {
		this.prodFmlyCd = prodFmlyCd;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getBuyerCd() {
		return buyerCd;
	}

	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getPackCd() {
		return packCd;
	}

	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	public String getSpecDestnCd() {
		return specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	public String getAddSpecCd() {
		return addSpecCd;
	}

	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	public String getExtClrCd() {
		return extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getIntClrCd() {
		return intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getPotCd() {
		return potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getOfflnPlanDate() {
		return offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getProdWkNo() {
		return prodWkNo;
	}

	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	public String getProdDayNo() {
		return prodDayNo;
	}

	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}

	public BigDecimal getOrdrQty() {
		return ordrQty;
	}

	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getProdPlntCd() {
		return prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getLineClass() {
		return lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getProdMthdCd() {
		return prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getFrznTypeCd() {
		return frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public String getSlsNoteNo() {
		return slsNoteNo;
	}

	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
	}

	public String getTyreMkrCd() {
		return tyreMkrCd;
	}

	public void setTyreMkrCd(String tyreMkrCd) {
		this.tyreMkrCd = tyreMkrCd;
	}

	public String getDealerLst() {
		return dealerLst;
	}

	public void setDealerLst(String dealerLst) {
		this.dealerLst = dealerLst;
	}

	public String getOwnrMnl() {
		return ownrMnl;
	}

	public void setOwnrMnl(String ownrMnl) {
		this.ownrMnl = ownrMnl;
	}

	public String getWrntyBklt() {
		return wrntyBklt;
	}

	public void setWrntyBklt(String wrntyBklt) {
		this.wrntyBklt = wrntyBklt;
	}

	public String getBdyPrtctnCd() {
		return bdyPrtctnCd;
	}

	public void setBdyPrtctnCd(String bdyPrtctnCd) {
		this.bdyPrtctnCd = bdyPrtctnCd;
	}

	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getWkNoOfYear() {
		return wkNoOfYear;
	}

	public void setWkNoOfYear(String wkNoOfYear) {
		this.wkNoOfYear = wkNoOfYear;
	}

	public String getFxdSymbl() {
		return fxdSymbl;
	}

	public void setFxdSymbl(String fxdSymbl) {
		this.fxdSymbl = fxdSymbl;
	}

	public String getWkFixWkNo() {
		return wkFixWkNo;
	}

	public void setWkFixWkNo(String wkFixWkNo) {
		this.wkFixWkNo = wkFixWkNo;
	}

	public String getWkFixSymbl() {
		return wkFixSymbl;
	}

	public void setWkFixSymbl(String wkFixSymbl) {
		this.wkFixSymbl = wkFixSymbl;
	}

	public String getIntrnlOrTrdFlag() {
		return intrnlOrTrdFlag;
	}

	public void setIntrnlOrTrdFlag(String intrnlOrTrdFlag) {
		this.intrnlOrTrdFlag = intrnlOrTrdFlag;
	}

	public String getOseiId() {
		return oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getUpDtTm() {
		return upDtTm;
	}

	public void setUpDtTm(String upDtTm) {
		this.upDtTm = upDtTm;
	}

	public String getCrtdBy() {
		return crtdBy;
	}

	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	public Timestamp getCrtdDt() {
		return crtdDt;
	}

	public void setCrtdDt(Timestamp crtdDt) {
		this.crtdDt = crtdDt;
	}

	public String getUpdtdBy() {
		return updtdBy;
	}

	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	public Timestamp getUpdtdDt() {
		return updtdDt;
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