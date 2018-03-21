/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000020
 * Module          :CM Common
 * Process Outline :Send OSEI Production Type Master Interface to NSC(Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000020.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class I000020OutputBean is used to set and retrieve the output parameter
 * value and allow control over the values passed
 * 
 * @author z015895
 *
 */
@Entity
public class I000020OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The por cd. */
	@Id
	@Column(name = "POR_CD")
	private String porCd;

	/** The order take base month. */
	@Id
	@Column(name = "ORDR_TAKE_BASE_MNTH")
	private String ordTakBaseMon;

	/** The production month. */
	@Id
	@Column(name = "PROD_MNTH")
	private String prodMon;

	/** The car srs. */
	@Column(name = "CAR_SRS")
	private String carSeries;

	/** The buyer cd. */
	@Column(name = "BUYER_CD")
	private String buyerCD;

	/** The applied model cd. */
	@Column(name = "APPLD_MDL_CD")
	private String appliedModelCd;

	/** The pack cd. */
	@Column(name = "PCK_CD")
	private String packCD;

	/** The spec destination cd. */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestination;

	/** The additional spec cd. */
	@Column(name = "ADTNL_SPEC_CD")
	private String additionalSpecCd;

	/** The exterior color cd. */
	@Column(name = "EXT_CLR_CD")
	private String exteriorColor;

	/** The interior color cd. */
	@Column(name = "INT_CLR_CD")
	private String interiorColor;

	/** The production plant cd. */
	@Column(name = "PROD_PLNT_CD")
	private String prodPlantCd;

	/** The production week no. */
	@Column(name = "PROD_WK_NO")
	private String prodWeekNo;

	/** The production method cd. */
	@Column(name = "PROD_MTHD_CD")
	private String prodMethodCd;

	/** The created by. */
	@Column(name = "CRTD_BY")
	private String createUserID;

	/** The created date time. */
	@Column(name = "CRTD_DT")
	private String createDateTime;

	/** The updated by. */
	@Column(name = "UPDTD_BY")
	private String updateUserID;

	/** The updated date time. */
	@Column(name = "UPDTD_DT")
	private String updateDateTime;

	/**
	 * Gets the porCd
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
	 * Gets the prodMon
	 *
	 * @return the prodMon
	 */
	public String getProdMon() {
		return prodMon;
	}

	/**
	 * Sets the prodMon
	 *
	 * @param prodMon
	 *            the prodMon to set
	 */
	public void setProdMon(String prodMon) {
		this.prodMon = prodMon;
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
	 * Gets the prodPlantCd
	 *
	 * @return the prodPlantCd
	 */
	public String getProdPlantCd() {
		return prodPlantCd;
	}

	/**
	 * Sets the prodPlantCd
	 *
	 * @param prodPlantCd
	 *            the prodPlantCd to set
	 */
	public void setProdPlantCd(String prodPlantCd) {
		this.prodPlantCd = prodPlantCd;
	}

	/**
	 * Gets the prodWeekNo
	 *
	 * @return the prodWeekNo
	 */
	public String getProdWeekNo() {
		return prodWeekNo;
	}

	/**
	 * Sets the prodWeekNo
	 *
	 * @param prodWeekNo
	 *            the prodWeekNo to set
	 */
	public void setProdWeekNo(String prodWeekNo) {
		this.prodWeekNo = prodWeekNo;
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
	 * Gets the createUserID
	 *
	 * @return the createUserID
	 */
	public String getCreateUserID() {
		return createUserID;
	}

	/**
	 * Sets the createUserID
	 *
	 * @param createUserID
	 *            the createUserID to set
	 */
	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}

	/**
	 * Gets the createDateTime
	 *
	 * @return the createDateTime
	 */
	public String getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * Sets the createDateTime
	 *
	 * @param createDateTime
	 *            the createDateTime to set
	 */
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * Gets the updateUserID
	 *
	 * @return the updateUserID
	 */
	public String getUpdateUserID() {
		return updateUserID;
	}

	/**
	 * Sets the updateUserID
	 *
	 * @param updateUserID
	 *            the updateUserID to set
	 */
	public void setUpdateUserID(String updateUserID) {
		this.updateUserID = updateUserID;
	}

	/**
	 * Gets the updateDateTime
	 *
	 * @return the updateDateTime
	 */
	public String getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * Sets the updateDateTime
	 *
	 * @param updateDateTime
	 *            the updateDateTime to set
	 */
	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
