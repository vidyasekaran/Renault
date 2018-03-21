/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000110
 * Module          :CM Common
 * Process Outline :This Interface extracts Feature data from the master tables and stores it in the Common Layer Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-01-2016  	  z015895(RNTBCI)               Initial Version
 * 
 */

package com.nissangroups.pd.i000110.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class I000110OutputBean is used to set and retrieve the output parameter
 * value and allow control over the values passed
 * 
 * @author z015895
 *
 */

@Entity
public class I000110OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	@Column(name = "POR_CD")
	private String porCd;

	@Column(name = "BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name = "CAR_SRS")
	private String carSeries;

	@Column(name = "FEAT_CD")
	private String featureCd;

	@Column(name = "FEAT_TYPE_CD")
	private String featureTypeCd;

	@Column(name = "FEAT_SHRT_DESC")
	private String featShortDescription;

	@Column(name = "FEAT_LNG_DESC")
	private String featLongDescription;

	@Column(name = "OCF_FRME_CD")
	private String ocfFrameCd;

	@Column(name = "OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name = "OCF_BUYER_GRP_CD")
	private String ocfBuyerGroupCd;

	@Column(name = "FEAT_ADPT_DATE")
	private String featAdptDate;

	@Column(name = "FEAT_ABLSH_DATE")
	private String featAblshDate;

	@Column(name = "CRTD_BY")
	private String createUserID;

	@Column(name = "CRTD_DT")
	private String createDateTime;

	@Column(name = "UPDTD_BY")
	private String updateUserID;

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
	 * Gets the buyerGrpCd
	 *
	 * @return the buyerGrpCd
	 */
	public String getBuyerGrpCd() {
		return buyerGrpCd;
	}

	/**
	 * Sets the buyerGrpCd
	 *
	 * @param buyerGrpCd
	 *            the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
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
	 * Gets the featShortDescription
	 *
	 * @return the featShortDescription
	 */
	public String getFeatShortDescription() {
		return featShortDescription;
	}

	/**
	 * Sets the featShortDescription
	 *
	 * @param featShortDescription
	 *            the featShortDescription to set
	 */
	public void setFeatShortDescription(String featShortDescription) {
		this.featShortDescription = featShortDescription;
	}

	/**
	 * Gets the featLongDescription
	 *
	 * @return the featLongDescription
	 */
	public String getFeatLongDescription() {
		return featLongDescription;
	}

	/**
	 * Sets the featLongDescription
	 *
	 * @param featLongDescription
	 *            the featLongDescription to set
	 */
	public void setFeatLongDescription(String featLongDescription) {
		this.featLongDescription = featLongDescription;
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
	 * Gets the featAdptDate
	 *
	 * @return the featAdptDate
	 */
	public String getFeatAdptDate() {
		return featAdptDate;
	}

	/**
	 * Sets the featAdptDate
	 *
	 * @param featAdptDate
	 *            the featAdptDate to set
	 */
	public void setFeatAdptDate(String featAdptDate) {
		this.featAdptDate = featAdptDate;
	}

	/**
	 * Gets the featAblshDate
	 *
	 * @return the featAblshDate
	 */
	public String getFeatAblshDate() {
		return featAblshDate;
	}

	/**
	 * Sets the featAblshDate
	 *
	 * @param featAblshDate
	 *            the featAblshDate to set
	 */
	public void setFeatAblshDate(String featAblshDate) {
		this.featAblshDate = featAblshDate;
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

	/**
	 * Gets the serialversionuid
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
