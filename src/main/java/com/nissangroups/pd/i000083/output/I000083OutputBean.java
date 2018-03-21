/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000083
 * Module          :OR ORDERING
 * Process Outline :Send Weekly Production Order to Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000083.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class I000083OutputBean is used to set and retrieve the output parameter
 * value and allow control over the values passed
 * 
 * @author z015895
 *
 */
@Entity
public class I000083OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The row num. */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** The car srs. */
	@Column(name = "CAR_SRS")
	private String carSeries;

	/** The production family cd. */
	@Column(name = "PROD_FMY_CD")
	private String prodFmyCd;

	/** The appld mdl cd. */
	@Column(name = "APPLD_MDL_CD")
	private String appliedModelCd;

	/** The pck cd. */
	@Column(name = "PCK_CD")
	private String packCD;

	/** The exterior color cd. */
	@Column(name = "EXT_CLR_CD")
	private String exteriorColor;

	/** The interior color cd. */
	@Column(name = "INT_CLR_CD")
	private String interiorColor;

	/** The ex no. */
	@Column(name = "EX_NO")
	private String exNo;

	/** The spec destination cd. */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestination;

	/** The production method cd. */
	@Column(name = "PROD_MTHD_CD")
	private String prodMethodCd;

	/** The order take base month. */
	@Column(name = "ORDR_TAKE_BASE_MNTH")
	private String ordTakBaseMon;

	/** The ocf region cd. */
	@Column(name = "OCF_REGION_CD")
	private String ocfRegionCd;

	/** The typr maker cd. */
	@Column(name = "TYRE_MKR_CD")
	private String tyreMkrCd;

	/** The dealer lst. */
	@Column(name = "DEALER_LST")
	private String dealerLst;

	/** The owner manual. */
	@Column(name = "OWNR_MNL")
	private String ownrMnl;

	/** The warranty booklet. */
	@Column(name = "WRNTY_BKLT")
	private String wrntyBklt;

	/** The body protection cd. */
	@Column(name = "BDY_PRTCTN_CD")
	private String bdyPrtctnCd;

	/** The order quantity. */
	@Column(name = "ORDR_QTY")
	private String ordrQty;

	/** The production order no. */
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;

	/** The sls note no. */
	@Column(name = "SLS_NOTE_NO")
	private String slsNoteNo;

	/** The additional spec cd. */
	@Column(name = "ADTNL_SPEC_CD")
	private String additionalSpecCd;

	/** The buyer cd. */
	@Column(name = "BUYER_CD")
	private String buyerCD;

	/** The production week no. */
	@Column(name = "PROD_WK_NO")
	private String prodWkNo;

	/** The production day no. */
	@Column(name = "PROD_DAY_NO")
	private String prodDayNo;

	/** The order fixed flag. */
	@Column(name = "ORDR_FXD_FLAG")
	private String ordrFxdFlag;

	/** The plant cd. */
	@Column(name = "PLNT_CD")
	private String plntCd;

	/** The line class. */
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/**
	 * Gets the rowNum
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
	 * Gets the carSeries
	 *
	 * @return the carSeries
	 */
	public String getCarSeries() {
		return carSeries;
	}

	/**
	 * Sets the carSeries
	 *
	 * @param carSeries
	 *            the carSeries to set
	 */
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	/**
	 * Gets the prodFmyCd
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
	 * Gets the appliedModelCd
	 *
	 * @return the appliedModelCd
	 */
	public String getAppliedModelCd() {
		return appliedModelCd;
	}

	/**
	 * Sets the appliedModelCd
	 *
	 * @param appliedModelCd
	 *            the appliedModelCd to set
	 */
	public void setAppliedModelCd(String appliedModelCd) {
		this.appliedModelCd = appliedModelCd;
	}

	/**
	 * Gets the packCD
	 *
	 * @return the packCD
	 */
	public String getPackCD() {
		return packCD;
	}

	/**
	 * Sets the packCD
	 *
	 * @param packCD
	 *            the packCD to set
	 */
	public void setPackCD(String packCD) {
		this.packCD = packCD;
	}

	/**
	 * Gets the exteriorColor
	 *
	 * @return the exteriorColor
	 */
	public String getExteriorColor() {
		return exteriorColor;
	}

	/**
	 * Sets the exteriorColor
	 *
	 * @param exteriorColor
	 *            the exteriorColor to set
	 */
	public void setExteriorColor(String exteriorColor) {
		this.exteriorColor = exteriorColor;
	}

	/**
	 * Gets the interiorColor
	 *
	 * @return the interiorColor
	 */
	public String getInteriorColor() {
		return interiorColor;
	}

	/**
	 * Sets the interiorColor
	 *
	 * @param interiorColor
	 *            the interiorColor to set
	 */
	public void setInteriorColor(String interiorColor) {
		this.interiorColor = interiorColor;
	}

	/**
	 * Gets the exNo
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
	 * Gets the specDestination
	 *
	 * @return the specDestination
	 */
	public String getSpecDestination() {
		return specDestination;
	}

	/**
	 * Sets the specDestination
	 *
	 * @param specDestination
	 *            the specDestination to set
	 */
	public void setSpecDestination(String specDestination) {
		this.specDestination = specDestination;
	}

	/**
	 * Gets the prodMethodCd
	 *
	 * @return the prodMethodCd
	 */
	public String getProdMethodCd() {
		return prodMethodCd;
	}

	/**
	 * Sets the prodMethodCd
	 *
	 * @param prodMethodCd
	 *            the prodMethodCd to set
	 */
	public void setProdMethodCd(String prodMethodCd) {
		this.prodMethodCd = prodMethodCd;
	}

	/**
	 * Gets the ordTakBaseMon
	 *
	 * @return the ordTakBaseMon
	 */
	public String getOrdTakBaseMon() {
		return ordTakBaseMon;
	}

	/**
	 * Sets the ordTakBaseMon
	 *
	 * @param ordTakBaseMon
	 *            the ordTakBaseMon to set
	 */
	public void setOrdTakBaseMon(String ordTakBaseMon) {
		this.ordTakBaseMon = ordTakBaseMon;
	}

	/**
	 * Gets the ocfRegionCd
	 *
	 * @return the ocfRegionCd
	 */
	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	/**
	 * Sets the ocfRegionCd
	 *
	 * @param ocfRegionCd
	 *            the ocfRegionCd to set
	 */
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	/**
	 * Gets the tyreMkrCd
	 *
	 * @return the tyreMkrCd
	 */
	public String getTyreMkrCd() {
		return tyreMkrCd;
	}

	/**
	 * Sets the tyreMkrCd
	 *
	 * @param tyreMkrCd
	 *            the tyreMkrCd to set
	 */
	public void setTyreMkrCd(String tyreMkrCd) {
		this.tyreMkrCd = tyreMkrCd;
	}

	/**
	 * Gets the dealerLst
	 *
	 * @return the dealerLst
	 */
	public String getDealerLst() {
		return dealerLst;
	}

	/**
	 * Sets the dealerLst
	 *
	 * @param dealerLst
	 *            the dealerLst to set
	 */
	public void setDealerLst(String dealerLst) {
		this.dealerLst = dealerLst;
	}

	/**
	 * Gets the ownrMnl
	 *
	 * @return the ownrMnl
	 */
	public String getOwnrMnl() {
		return ownrMnl;
	}

	/**
	 * Sets the ownrMnl
	 *
	 * @param ownrMnl
	 *            the ownrMnl to set
	 */
	public void setOwnrMnl(String ownrMnl) {
		this.ownrMnl = ownrMnl;
	}

	/**
	 * Gets the wrntyBklt
	 *
	 * @return the wrntyBklt
	 */
	public String getWrntyBklt() {
		return wrntyBklt;
	}

	/**
	 * Sets the wrntyBklt
	 *
	 * @param wrntyBklt
	 *            the wrntyBklt to set
	 */
	public void setWrntyBklt(String wrntyBklt) {
		this.wrntyBklt = wrntyBklt;
	}

	/**
	 * Gets the bdyPrtctnCd
	 *
	 * @return the bdyPrtctnCd
	 */
	public String getBdyPrtctnCd() {
		return bdyPrtctnCd;
	}

	/**
	 * Sets the bdyPrtctnCd
	 *
	 * @param bdyPrtctnCd
	 *            the bdyPrtctnCd to set
	 */
	public void setBdyPrtctnCd(String bdyPrtctnCd) {
		this.bdyPrtctnCd = bdyPrtctnCd;
	}

	/**
	 * Gets the ordrQty
	 *
	 * @return the ordrQty
	 */
	public String getOrdrQty() {
		return ordrQty;
	}

	/**
	 * Sets the ordrQty
	 *
	 * @param ordrQty
	 *            the ordrQty to set
	 */
	public void setOrdrQty(String ordrQty) {
		this.ordrQty = ordrQty;
	}

	/**
	 * Gets the prodOrdrNo
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
	 * Gets the slsNoteNo
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
	 * Gets the additionalSpecCd
	 *
	 * @return the additionalSpecCd
	 */
	public String getAdditionalSpecCd() {
		return additionalSpecCd;
	}

	/**
	 * Sets the additionalSpecCd
	 *
	 * @param additionalSpecCd
	 *            the additionalSpecCd to set
	 */
	public void setAdditionalSpecCd(String additionalSpecCd) {
		this.additionalSpecCd = additionalSpecCd;
	}

	/**
	 * Gets the buyerCD
	 *
	 * @return the buyerCD
	 */
	public String getBuyerCD() {
		return buyerCD;
	}

	/**
	 * Sets the buyerCD
	 *
	 * @param buyerCD
	 *            the buyerCD to set
	 */
	public void setBuyerCD(String buyerCD) {
		this.buyerCD = buyerCD;
	}

	/**
	 * Gets the prodWkNo
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
	 * Gets the prodDayNo
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

	/**
	 * Gets the ordrFxdFlag
	 *
	 * @return the ordrFxdFlag
	 */
	public String getOrdrFxdFlag() {
		return ordrFxdFlag;
	}

	/**
	 * Sets the ordrFxdFlag
	 *
	 * @param ordrFxdFlag
	 *            the ordrFxdFlag to set
	 */
	public void setOrdrFxdFlag(String ordrFxdFlag) {
		this.ordrFxdFlag = ordrFxdFlag;
	}

	/**
	 * Gets the plntCd
	 *
	 * @return the plntCd
	 */
	public String getPlntCd() {
		return plntCd;
	}

	/**
	 * Sets the plntCd
	 *
	 * @param plntCd
	 *            the plntCd to set
	 */
	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}

	/**
	 * Gets the lineClass
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

}
