/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000113
 * Module          : CM COMMON
 * Process Outline : Logical Pipeline Update from SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015895(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000113.bean;

/**
 * This Class is used to store arguments from the command line which is used by
 * query as input.
 * 
 * @author z015895
 * 
 */
public class I000113InputParam {

	/** The Input parameter EXTERIOR_COLOR_CD */
	private String extClr;

	/** The Input parameter INTERIOR_COLOR_CD */
	private String intClr;

	/** The Input parameter BUYER_CD */
	private String buyerCd;

	/** The Input parameter SPEC_DESTINATION_CD */
	private String specDestnCd;

	/** The Input parameter CAR_SERIES */
	private String carSrs;

	/** The Input parameter ADDITIONAL_SPEC_CD */
	private String adtnSpecCd;

	/** The Input parameter PACK_CD */
	private String pckCd;

	/** The Input parameter PRODUCTION_FAMILY_CD. */
	private String prdFamilyCd;

	/** The Input parameter APPLIED_MODEL_CD */
	private String applMdlCd;

	/** The Input parameter POR_CD */
	private String porCd;

	/**
	 * Gets the extClr
	 *
	 * @return the extClr
	 */
	public String getExtClr() {
		return extClr;
	}

	/**
	 * Sets the extClr
	 *
	 * @param extClr
	 *            the extClr to set
	 */
	public void setExtClr(String extClr) {
		this.extClr = extClr;
	}

	/**
	 * Gets the intClr
	 *
	 * @return the intClr
	 */
	public String getIntClr() {
		return intClr;
	}

	/**
	 * Sets the intClr
	 *
	 * @param intClr
	 *            the intClr to set
	 */
	public void setIntClr(String intClr) {
		this.intClr = intClr;
	}

	/**
	 * Gets the buyerCd
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
	 * Gets the specDestnCd
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
	 * Gets the carSrs
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
	 * Gets the adtnSpecCd
	 *
	 * @return the adtnSpecCd
	 */
	public String getAdtnSpecCd() {
		return adtnSpecCd;
	}

	/**
	 * Sets the adtnSpecCd
	 *
	 * @param adtnSpecCd
	 *            the adtnSpecCd to set
	 */
	public void setAdtnSpecCd(String adtnSpecCd) {
		this.adtnSpecCd = adtnSpecCd;
	}

	/**
	 * Gets the pckCd
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
	 * Gets the prdFamilyCd
	 *
	 * @return the prdFamilyCd
	 */
	public String getPrdFamilyCd() {
		return prdFamilyCd;
	}

	/**
	 * Sets the prdFamilyCd
	 *
	 * @param prdFamilyCd
	 *            the prdFamilyCd to set
	 */
	public void setPrdFamilyCd(String prdFamilyCd) {
		this.prdFamilyCd = prdFamilyCd;
	}

	/**
	 * Gets the applMdlCd
	 *
	 * @return the applMdlCd
	 */
	public String getApplMdlCd() {
		return applMdlCd;
	}

	/**
	 * Sets the applMdlCd
	 *
	 * @param applMdlCd
	 *            the applMdlCd to set
	 */
	public void setApplMdlCd(String applMdlCd) {
		this.applMdlCd = applMdlCd;
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

}
