/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-I000014
 * Module          :SP SPEC
 * ]
 *
 * <Revision History>
 * Date         Name(Company Name)           Description 
 * ------------ ---------------------------- ---------------------
 *              z015487(RNTBCI)              New Creation
 *
 */
package com.nissangroups.pd.i000102.bean;

/**
 * This class is used to store the input parameters required which will be used
 * in the SQL to fetch the data from the database.
 * 
 * @author z015847
 * 
 */
public class I000102InputParam {

	/** Input parameter Porcd */
	private String porCd;

	/** Input parameter ocfRegionCd */
	private String ocfRegionCd;

	/** Input parameter rhqCd */
	private String ocfBuyerCd;

	/** Input parameter rhqCd */
	private String rhqCd;

	/** Input parameter buyerGrpCd */
	private String buyerGrpCd;

	/**
	 * Get the porCd
	 * 
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * Set the porCd
	 * 
	 * @param porCd
	 *            the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * Get the ocfRegionCd
	 * 
	 * @return the ocfRegionCd
	 */
	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	/**
	 * Set the ocfRegionCd
	 * 
	 * @param ocfRegionCd
	 *            the ocfRegionCd to set
	 */
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	/**
	 * Get the ocfBuyerCd
	 * 
	 * @return the ocfBuyerCd
	 */
	public String getOcfBuyerCd() {
		return ocfBuyerCd;
	}

	/**
	 * Set the ocfBuyerCd
	 * 
	 * @param ocfBuyerCd
	 *            the ocfBuyerCd to set
	 */
	public void setOcfBuyerCd(String ocfBuyerCd) {
		this.ocfBuyerCd = ocfBuyerCd;
	}

	/**
	 * Get the rhqCd
	 * 
	 * @return the rhqCd
	 */
	public String getRhqCd() {
		return rhqCd;
	}

	/**
	 * Set the rhqCd
	 * 
	 * @param rhqCd
	 *            the rhqCd to set
	 */
	public void setRhqCd(String rhqCd) {
		this.rhqCd = rhqCd;
	}

	/**
	 * Get the buyerGrpCd
	 * 
	 * @return the buyerGrpCd
	 */
	public String getBuyerGrpCd() {
		return buyerGrpCd;
	}

	/**
	 * Set the buyerGrpCd
	 * 
	 * @param buyerGrpCd
	 *            the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

}
