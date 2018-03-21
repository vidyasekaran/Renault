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
@Table(name="TRN_MNTH_PROD_SHDL_IF")
@NamedQuery(name="TrnMnthProdShdlIf.findAll", query="SELECT t FROM TrnMnthProdShdlIf t")
public class TrnMnthProdShdlIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnMnthProdShdlIfPK id;

	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DEALER_LST")
	private String dealerLst;

	@Column(name="ERR_STTS_CD")
	private String errSttsCd;

	private String errormessage;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="FXD_SYMBL")
	private String fxdSymbl;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="INTRNL_OR_TRD_FLAG")
	private String intrnlOrTrdFlag;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="LOCAL_PROD_ORDR_NO")
	private String localProdOrdrNo;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="OWNR_MNL")
	private String ownrMnl;

	@Column(name="PACK_CD")
	private String packCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_DAY_NO")
	private String prodDayNo;

	@Column(name="PROD_FMLY_CD")
	private String prodFmlyCd;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="PRTYPE_FLAG")
	private String prtypeFlag;

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

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="WK_NO_OF_YEAR")
	private String wkNoOfYear;

	@Column(name="WRNTY_BKLT")
	private String wrntyBklt;

	public TrnMnthProdShdlIf() {
	}

	public TrnMnthProdShdlIfPK getId() {
		return this.id;
	}

	public void setId(TrnMnthProdShdlIfPK id) {
		this.id = id;
	}

	public String getAddSpecCd() {
		return this.addSpecCd;
	}

	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
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

	public String getErrSttsCd() {
		return this.errSttsCd;
	}

	public void setErrSttsCd(String errSttsCd) {
		this.errSttsCd = errSttsCd;
	}

	public String getErrormessage() {
		return this.errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
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

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public String getFxdSymbl() {
		return this.fxdSymbl;
	}

	public void setFxdSymbl(String fxdSymbl) {
		this.fxdSymbl = fxdSymbl;
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getIntrnlOrTrdFlag() {
		return this.intrnlOrTrdFlag;
	}

	public void setIntrnlOrTrdFlag(String intrnlOrTrdFlag) {
		this.intrnlOrTrdFlag = intrnlOrTrdFlag;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getLocalProdOrdrNo() {
		return this.localProdOrdrNo;
	}

	public void setLocalProdOrdrNo(String localProdOrdrNo) {
		this.localProdOrdrNo = localProdOrdrNo;
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

	public BigDecimal getOrdrQty() {
		return this.ordrQty;
	}

	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getOseiId() {
		return this.oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getOwnrMnl() {
		return this.ownrMnl;
	}

	public void setOwnrMnl(String ownrMnl) {
		this.ownrMnl = ownrMnl;
	}

	public String getPackCd() {
		return this.packCd;
	}

	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdDayNo() {
		return this.prodDayNo;
	}

	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}

	public String getProdFmlyCd() {
		return this.prodFmlyCd;
	}

	public void setProdFmlyCd(String prodFmlyCd) {
		this.prodFmlyCd = prodFmlyCd;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getProdWkNo() {
		return this.prodWkNo;
	}

	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	public String getPrtypeFlag() {
		return this.prtypeFlag;
	}

	public void setPrtypeFlag(String prtypeFlag) {
		this.prtypeFlag = prtypeFlag;
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

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getWkNoOfYear() {
		return this.wkNoOfYear;
	}

	public void setWkNoOfYear(String wkNoOfYear) {
		this.wkNoOfYear = wkNoOfYear;
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