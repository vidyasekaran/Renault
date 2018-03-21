/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000088
 * Module                 : Ordering		
 * Process Outline     	  : Send Weekly Production Schedule Interface to NSC(Standard)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014029(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000088.output;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from
 * WEEKLY_PRODUCTION_SCHEDULE_TRN database table values.
 * 
 * @author z014029
 * 
 */
@Entity
public class I000088OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Interface Output Parameter ROW NUM */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Interface Output Parameter POR CD */
	@Column(name = "POR_CD")
	private String porCd;

	/** Interface Output Parameter CAR SERIES */
	@Column(name = "CAR_SRS")
	private String carSrs;

	/** Interface Output Parameter BUYER CD */
	@Column(name = "BUYER_CD")
	private String buyerCd;

	/** Interface Output Parameter APPLIED MODEL CD */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;

	/** Interface Output Parameter PACK CD */
	@Column(name = "PCK_CD")
	private String pckCd;

	/** Interface Output Parameter SPEC DESTNINATION CD */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestnCd;

	/** Interface Output Parameter ADDITIONAL SPEC CD */
	@Column(name = "ADD_SPEC_CD")
	private String addSpecCd;

	/** Interface Output Parameter EXTERIOR COLOR CD */
	@Column(name = "EXT_CLR_CD")
	private String extClrCd;

	/** Interface Output Parameter INTERIOR COLOR CD */
	@Column(name = "INT_CLR_CD")
	private String intClrCd;

	/** Interface Output Parameter POT CD */
	@Column(name = "POT_CD")
	private String potCd;

	/** Interface Output Parameter PRODUCTION PLANT CD */
	@Column(name = "PROD_PLNT_CD")
	private String prodPlntCd;

	/** Interface Output Parameter LINE CLASS */
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/** Interface Output Parameter ORDER TAKE BASE MONTH */
	@Column(name = "ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	/** Interface Output Parameter PRODUCTION WEEK NO */
	@Column(name = "PROD_WK_NO")
	private String prodWkNo;

	/** Interface Output Parameter PRODUCTION MONTH */
	@Column(name = "PROD_MNTH")
	private String prodMnth;

	/** Interface Output Parameter ORDER TAKE BASE WEEK NO */
	@Column(name = "ORDR_TAKE_BASE_WK_NO")
	private String ordrTakeBaseWkNo;

	/** Interface Output Parameter OFFLINE PLAN DATE */
	@Column(name = "OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	/** Interface Output Parameter ORDER QTY */
	@Column(name = "ORDR_QTY")
	private BigDecimal ordrQty;

	/** Interface Output Parameter PRODUCTION METHOD CD */
	@Column(name = "PROD_MTHD_CD")
	private String prodMthdCd;

	/** Interface Output Parameter FROZEN TYPE CD */
	@Column(name = "FRZN_TYPE_CD")
	private String frznTypeCd;

	/** Interface Output Parameter LOCAL PRODUCTION ORDER NO */
	@Column(name = "LOCAL_PROD_ORDR_NO")
	private String localProdOrdrNo;

	/** Interface Output Parameter PRODUCTION ORDER NO */
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;

	/** Interface Output Parameter EX NO */
	@Column(name = "EX_NO")
	private String exNo;

	/** Interface Output Parameter PRODUCTION FAMILY CD */
	@Column(name = "PROD_FMY_CD")
	private String prodFmyCd;

	/** Interface Output Parameter SLS NOTE NO */
	@Column(name = "SLS_NOTE_NO")
	private String slsNoteNo;

	/** Interface Output Parameter FIXED SYMBOL */
	@Column(name = "FXD_SYMBL")
	private String fxdSymbl;

	/** Interface Output Parameter WEEK NO OF YEAR */
	@Column(name = "WK_NO_OF_YEAR")
	private String wkNoOfYear;

	/** Interface Output Parameter CREATED BY */
	@Column(name = "CRTD_BY")
	private String crtdBy;

	/** Interface Output Parameter CREATED DATE */
	@Column(name = "CRTD_DT")
	private Timestamp crtdDt;

	/** Interface Output Parameter UPDATED BY */
	@Column(name = "UPDTD_BY")
	private String updtdBy;

	/** Interface Output Parameter UPDATED DATE */
	@Column(name = "UPDTD_DT")
	private Timestamp updtdDt;

	/** Interface Output Parameter PRODUCTION DAY NO */
	@Column(name = "PROD_DAY_NO")
	private String prodDayNo;

	/**
	 * Get the rowNum
	 * 
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * Sets the rowNum
	 * 
	 * @param rowNum
	 *            the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * Get the porCd
	 * 
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * Sets the porCd
	 * 
	 * @param porCd
	 *            the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * Get the carSrs
	 * 
	 * @return the carSrs
	 */
	public String getCarSrs() {
		return carSrs;
	}

	/**
	 * Sets the carSrs
	 * 
	 * @param carSrs
	 *            the carSrs to set
	 */
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	/**
	 * Get the buyerCd
	 * 
	 * @return the buyerCd
	 */
	public String getBuyerCd() {
		return buyerCd;
	}

	/**
	 * Sets the buyerCd
	 * 
	 * @param buyerCd
	 *            the buyerCd to set
	 */
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	/**
	 * Get the appldMdlCd
	 * 
	 * @return the appldMdlCd
	 */
	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	/**
	 * Sets the appldMdlCd
	 * 
	 * @param appldMdlCd
	 *            the appldMdlCd to set
	 */
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	/**
	 * Get the pckCd
	 * 
	 * @return the pckCd
	 */
	public String getPckCd() {
		return pckCd;
	}

	/**
	 * Sets the pckCd
	 * 
	 * @param pckCd
	 *            the pckCd to set
	 */
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	/**
	 * Get the specDestnCd
	 * 
	 * @return the specDestnCd
	 */
	public String getSpecDestnCd() {
		return specDestnCd;
	}

	/**
	 * Sets the specDestnCd
	 * 
	 * @param specDestnCd
	 *            the specDestnCd to set
	 */
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	/**
	 * Get the addSpecCd
	 * 
	 * @return the addSpecCd
	 */
	public String getAddSpecCd() {
		return addSpecCd;
	}

	/**
	 * Sets the addSpecCd
	 * 
	 * @param addSpecCd
	 *            the addSpecCd to set
	 */
	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	/**
	 * Get the extClrCd
	 * 
	 * @return the extClrCd
	 */
	public String getExtClrCd() {
		return extClrCd;
	}

	/**
	 * Sets the extClrCd
	 * 
	 * @param extClrCd
	 *            the extClrCd to set
	 */
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	/**
	 * Get the intClrCd
	 * 
	 * @return the intClrCd
	 */
	public String getIntClrCd() {
		return intClrCd;
	}

	/**
	 * Sets the intClrCd
	 * 
	 * @param intClrCd
	 *            the intClrCd to set
	 */
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	/**
	 * Get the potCd
	 * 
	 * @return the potCd
	 */
	public String getPotCd() {
		return potCd;
	}

	/**
	 * Sets the potCd
	 * 
	 * @param potCd
	 *            the potCd to set
	 */
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	/**
	 * Get the prodPlntCd
	 * 
	 * @return the prodPlntCd
	 */
	public String getProdPlntCd() {
		return prodPlntCd;
	}

	/**
	 * Sets the prodPlntCd
	 * 
	 * @param prodPlntCd
	 *            the prodPlntCd to set
	 */
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	/**
	 * Get the lineClass
	 * 
	 * @return the lineClass
	 */
	public String getLineClass() {
		return lineClass;
	}

	/**
	 * Sets the lineClass
	 * 
	 * @param lineClass
	 *            the lineClass to set
	 */
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	/**
	 * Get the ordrTakeBaseMnth
	 * 
	 * @return the ordrTakeBaseMnth
	 */
	public String getOrdrTakeBaseMnth() {
		return ordrTakeBaseMnth;
	}

	/**
	 * Sets the ordrTakeBaseMnth
	 * 
	 * @param ordrTakeBaseMnth
	 *            the ordrTakeBaseMnth to set
	 */
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}

	/**
	 * Get the prodWkNo
	 * 
	 * @return the prodWkNo
	 */
	public String getProdWkNo() {
		return prodWkNo;
	}

	/**
	 * Sets the prodWkNo
	 * 
	 * @param prodWkNo
	 *            the prodWkNo to set
	 */
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	/**
	 * Get the prodMnth
	 * 
	 * @return the prodMnth
	 */
	public String getProdMnth() {
		return prodMnth;
	}

	/**
	 * Sets the prodMnth
	 * 
	 * @param prodMnth
	 *            the prodMnth to set
	 */
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	/**
	 * Get the ordrTakeBaseWkNo
	 * 
	 * @return the ordrTakeBaseWkNo
	 */
	public String getOrdrTakeBaseWkNo() {
		return ordrTakeBaseWkNo;
	}

	/**
	 * Sets the ordrTakeBaseWkNo
	 * 
	 * @param ordrTakeBaseWkNo
	 *            the ordrTakeBaseWkNo to set
	 */
	public void setOrdrTakeBaseWkNo(String ordrTakeBaseWkNo) {
		this.ordrTakeBaseWkNo = ordrTakeBaseWkNo;
	}

	/**
	 * Get the offlnPlanDate
	 * 
	 * @return the offlnPlanDate
	 */
	public String getOfflnPlanDate() {
		return offlnPlanDate;
	}

	/**
	 * Sets the offlnPlanDate
	 * 
	 * @param offlnPlanDate
	 *            the offlnPlanDate to set
	 */
	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	/**
	 * Get the ordrQty
	 * 
	 * @return the ordrQty
	 */
	public BigDecimal getOrdrQty() {
		return ordrQty;
	}

	/**
	 * Sets the ordrQty
	 * 
	 * @param ordrQty
	 *            the ordrQty to set
	 */
	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	/**
	 * Get the prodMthdCd
	 * 
	 * @return the prodMthdCd
	 */
	public String getProdMthdCd() {
		return prodMthdCd;
	}

	/**
	 * Sets the prodMthdCd
	 * 
	 * @param prodMthdCd
	 *            the prodMthdCd to set
	 */
	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	/**
	 * Get the frznTypeCd
	 * 
	 * @return the frznTypeCd
	 */
	public String getFrznTypeCd() {
		return frznTypeCd;
	}

	/**
	 * Sets the frznTypeCd
	 * 
	 * @param frznTypeCd
	 *            the frznTypeCd to set
	 */
	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	/**
	 * Get the localProdOrdrNo
	 * 
	 * @return the localProdOrdrNo
	 */
	public String getLocalProdOrdrNo() {
		return localProdOrdrNo;
	}

	/**
	 * Sets the localProdOrdrNo
	 * 
	 * @param localProdOrdrNo
	 *            the localProdOrdrNo to set
	 */
	public void setLocalProdOrdrNo(String localProdOrdrNo) {
		this.localProdOrdrNo = localProdOrdrNo;
	}

	/**
	 * Get the prodOrdrNo
	 * 
	 * @return the prodOrdrNo
	 */
	public String getProdOrdrNo() {
		return prodOrdrNo;
	}

	/**
	 * Sets the prodOrdrNo
	 * 
	 * @param prodOrdrNo
	 *            the prodOrdrNo to set
	 */
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	/**
	 * Get the exNo
	 * 
	 * @return the exNo
	 */
	public String getExNo() {
		return exNo;
	}

	/**
	 * Sets the exNo
	 * 
	 * @param exNo
	 *            the exNo to set
	 */
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	/**
	 * Get the prodFmyCd
	 * 
	 * @return the prodFmyCd
	 */
	public String getProdFmyCd() {
		return prodFmyCd;
	}

	/**
	 * Sets the prodFmyCd
	 * 
	 * @param prodFmyCd
	 *            the prodFmyCd to set
	 */
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}

	/**
	 * Get the slsNoteNo
	 * 
	 * @return the slsNoteNo
	 */
	public String getSlsNoteNo() {
		return slsNoteNo;
	}

	/**
	 * Sets the slsNoteNo
	 * 
	 * @param slsNoteNo
	 *            the slsNoteNo to set
	 */
	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
	}

	/**
	 * Get the fxdSymbl
	 * 
	 * @return the fxdSymbl
	 */
	public String getFxdSymbl() {
		return fxdSymbl;
	}

	/**
	 * Sets the fxdSymbl
	 * 
	 * @param fxdSymbl
	 *            the fxdSymbl to set
	 */
	public void setFxdSymbl(String fxdSymbl) {
		this.fxdSymbl = fxdSymbl;
	}

	/**
	 * Get the wkNoOfYear
	 * 
	 * @return the wkNoOfYear
	 */
	public String getWkNoOfYear() {
		return wkNoOfYear;
	}

	/**
	 * Sets the wkNoOfYear
	 * 
	 * @param wkNoOfYear
	 *            the wkNoOfYear to set
	 */
	public void setWkNoOfYear(String wkNoOfYear) {
		this.wkNoOfYear = wkNoOfYear;
	}

	/**
	 * Get the crtdBy
	 * 
	 * @return the crtdBy
	 */
	public String getCrtdBy() {
		return crtdBy;
	}

	/**
	 * Sets the crtdBy
	 * 
	 * @param crtdBy
	 *            the crtdBy to set
	 */
	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	/**
	 * Get the crtdDt
	 * 
	 * @return the crtdDt
	 */
	public Timestamp getCrtdDt() {
		return crtdDt;
	}

	/**
	 * Sets the crtdDt
	 * 
	 * @param crtdDt
	 *            the crtdDt to set
	 */
	public void setCrtdDt(Timestamp crtdDt) {
		this.crtdDt = crtdDt;
	}

	/**
	 * Get the updtdBy
	 * 
	 * @return the updtdBy
	 */
	public String getUpdtdBy() {
		return updtdBy;
	}

	/**
	 * Sets the updtdBy
	 * 
	 * @param updtdBy
	 *            the updtdBy to set
	 */
	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	/**
	 * Get the updtdDt
	 * 
	 * @return the updtdDt
	 */
	public Timestamp getUpdtdDt() {
		return updtdDt;
	}

	/**
	 * Sets the updtdDt
	 * 
	 * @param updtdDt
	 *            the updtdDt to set
	 */
	public void setUpdtdDt(Timestamp updtdDt) {
		this.updtdDt = updtdDt;
	}

	/**
	 * Get the prodDayNo
	 * 
	 * @return the prodDayNo
	 */
	public String getProdDayNo() {
		return prodDayNo;
	}

	/**
	 * Sets the prodDayNo
	 * 
	 * @param prodDayNo
	 *            the prodDayNo to set
	 */
	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}

}