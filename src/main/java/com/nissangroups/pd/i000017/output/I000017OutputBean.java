/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000017
 * Module          :SP SPEC MASTER
 * Process Outline :Send OSEI Feature CCF Interface to NSC (Standard Layout) 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000017.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class I000017OutputBean is used to set and retrieve the output parameter
 * value and allow control over the values passed
 * 
 * @author z015895
 *
 */
@Entity
public class I000017OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The row num. */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** The por cd. */
	@Column(name = "POR_CD")
	private String porCd;

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

	/** The oseif adopt date. */
	@Column(name = "OSEIF_ADPT_DATE")
	private String oeifAdoptDate;

	/** The osei abolish date. */
	@Column(name = "OSEIF_ABLSH_DATE")
	private String oeifAbolishDate;

	/** The feature type cd. */
	@Column(name = "FEAT_TYPE_CD")
	private String featureTypeCd;

	/** The feature cd. */
	@Column(name = "FEAT_CD")
	private String featureCd;

	/** The ocf frme cd. */
	@Column(name = "OCF_FRME_CD")
	private String ocfFrameCd;

	/** The feature short desc. */
	@Column(name = "FEAT_SHRT_DESC")
	private String featureShortDescription;

	/** The ocf buyer group cd. */
	@Column(name = "OCF_BUYER_GRP_CD")
	private String ocfBuyerGroupCd;

	/** The car group. */
	@Column(name = "CAR_GRP")
	private String carGroup;

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
	 * Gets the oeifAdoptDate
	 *
	 * @return the oeifAdoptDate
	 */
	public String getOeifAdoptDate() {
		return oeifAdoptDate;
	}

	/**
	 * Sets the oeifAdoptDate
	 *
	 * @param oeifAdoptDate
	 *            the oeifAdoptDate to set
	 */
	public void setOeifAdoptDate(String oeifAdoptDate) {
		this.oeifAdoptDate = oeifAdoptDate;
	}

	/**
	 * Gets the oeifAbolishDate
	 *
	 * @return the oeifAbolishDate
	 */
	public String getOeifAbolishDate() {
		return oeifAbolishDate;
	}

	/**
	 * Sets the oeifAbolishDate
	 *
	 * @param oeifAbolishDate
	 *            the oeifAbolishDate to set
	 */
	public void setOeifAbolishDate(String oeifAbolishDate) {
		this.oeifAbolishDate = oeifAbolishDate;
	}

	/**
	 * Gets the featureTypeCd
	 *
	 * @return the featureTypeCd
	 */
	public String getFeatureTypeCd() {
		return featureTypeCd;
	}

	/**
	 * Sets the featureTypeCd
	 *
	 * @param featureTypeCd
	 *            the featureTypeCd to set
	 */
	public void setFeatureTypeCd(String featureTypeCd) {
		this.featureTypeCd = featureTypeCd;
	}

	/**
	 * Gets the featureCd
	 *
	 * @return the featureCd
	 */
	public String getFeatureCd() {
		return featureCd;
	}

	/**
	 * Sets the featureCd
	 *
	 * @param featureCd
	 *            the featureCd to set
	 */
	public void setFeatureCd(String featureCd) {
		this.featureCd = featureCd;
	}

	/**
	 * Gets the ocfFrameCd
	 *
	 * @return the ocfFrameCd
	 */
	public String getOcfFrameCd() {
		return ocfFrameCd;
	}

	/**
	 * Sets the ocfFrameCd
	 *
	 * @param ocfFrameCd
	 *            the ocfFrameCd to set
	 */
	public void setOcfFrameCd(String ocfFrameCd) {
		this.ocfFrameCd = ocfFrameCd;
	}

	/**
	 * Gets the featureShortDescription
	 *
	 * @return the featureShortDescription
	 */
	public String getFeatureShortDescription() {
		return featureShortDescription;
	}

	/**
	 * Sets the featureShortDescription
	 *
	 * @param featureShortDescription
	 *            the featureShortDescription to set
	 */
	public void setFeatureShortDescription(String featureShortDescription) {
		this.featureShortDescription = featureShortDescription;
	}

	/**
	 * Gets the ocfBuyerGroupCd
	 *
	 * @return the ocfBuyerGroupCd
	 */
	public String getOcfBuyerGroupCd() {
		return ocfBuyerGroupCd;
	}

	/**
	 * Sets the ocfBuyerGroupCd
	 *
	 * @param ocfBuyerGroupCd
	 *            the ocfBuyerGroupCd to set
	 */
	public void setOcfBuyerGroupCd(String ocfBuyerGroupCd) {
		this.ocfBuyerGroupCd = ocfBuyerGroupCd;
	}

	/**
	 * Gets the carGroup
	 *
	 * @return the carGroup
	 */
	public String getCarGroup() {
		return carGroup;
	}

	/**
	 * Sets the carGroup
	 *
	 * @param carGroup
	 *            the carGroup to set
	 */
	public void setCarGroup(String carGroup) {
		this.carGroup = carGroup;
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
