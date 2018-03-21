/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000044
 * Module                 : OR Ordering		
 * Process Outline     	  : SEND MONTHLY PRODUCTION SCHEDULE TO NSC(STANDARD)															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000044.bean;

import java.io.Serializable;

/**
 * This Class I000044InputParam is used to store arguments from the command line which is used by query as input.
 * 
 * @author z016127
 *
 */
public class I000044InputParam implements Serializable
{
	/**
	 * Constant Serial Version Id
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Input parameter POR CD */
	private String porCd;
	
	/** The Input parameter Order Take Base Month */
	private String ordrTakeBaseMnth;
	
	/** The Input parameter Ocf Region Code */
	private String ocfRegionCd;
	
	/**The Input parameter Ocf Buyer Code */
	private String ocfBuyerCd;
	
	/**The Input parameter RHQ Code */
	private String rhqCd;
	
	/**The Input parameter Buyer Group Code */
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
	 * Sets the porCd
	 *
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
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
	 * @param ordrTakeBaseMnth the ordrTakeBaseMnth to set
	 */
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
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
	 * Sets the ocfRegionCd
	 *
	 * @param ocfRegionCd the ocfRegionCd to set
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
	 * Sets the ocfBuyerCd
	 *
	 * @param ocfBuyerCd the ocfBuyerCd to set
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
	 * Sets the rhqCd
	 *
	 * @param rhqCd the rhqCd to set
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
	 * Sets the buyerGrpCd
	 *
	 * @param buyerGrpCd the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}
	
}
