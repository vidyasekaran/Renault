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
package com.nissangroups.pd.i000020.bean;

/**
 * This Class is used to store arguments from the command line which is used by
 * query as input.
 * 
 * @author z015895
 *
 */
public class I000020InputParam {

	/** The por cd. */
	private String porCd;

	/** The ocf region cd. */
	private String ocfRegionCd;

	/** The ocf buyer cd. */
	private String ocfBuyerCd;

	/** The rhq cd. */
	private String rhqCd;

	/** The buyer grp cd. */
	private String buyerGrpCd;

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
	 * Gets the ocfBuyerCd
	 *
	 * @return the ocfBuyerCd
	 */
	public String getOcfBuyerCd() {
		return ocfBuyerCd;
	}

	/**
	 * Sets the ocfBuyerCd
	 *
	 * @param ocfBuyerCd
	 *            the ocfBuyerCd to set
	 */
	public void setOcfBuyerCd(String ocfBuyerCd) {
		this.ocfBuyerCd = ocfBuyerCd;
	}

	/**
	 * Gets the rhqCd
	 *
	 * @return the rhqCd
	 */
	public String getRhqCd() {
		return rhqCd;
	}

	/**
	 * Sets the rhqCd
	 *
	 * @param rhqCd
	 *            the rhqCd to set
	 */
	public void setRhqCd(String rhqCd) {
		this.rhqCd = rhqCd;
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
}
