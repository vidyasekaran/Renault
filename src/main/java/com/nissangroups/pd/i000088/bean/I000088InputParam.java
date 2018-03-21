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
package com.nissangroups.pd.i000088.bean;

/**
 * This Class I000088InputParam is used to store arguments from the command line
 * which is used by query as input.
 * 
 * @author z014029
 * 
 */
public class I000088InputParam {

	/** The Input parameter porCd */
	private String porCd;

	/** The Input parameterocfRgnCd */
	private String ocfRgnCd;

	/** The Input parameter ocfByrGrp */
	private String ocfByrGrp;

	/** The Input parameter rhqCd */
	private String rhqCd;

	/** The Input parameter Buyer group CD */
	private String byrGrpCd;

	
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
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	
	/**
	 * Gets the ocfRgnCd
	 *
	 * @return the ocfRgnCd
	 */
	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	/**
	 * Sets the ocfRgnCd
	 *
	 * @param ocfRgnCd the ocfRgnCd to set
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}
	
	/**
	 * Gets the ocfByrGrp
	 *
	 * @return the ocfByrGrp
	 */
	public String getOcfByrGrp() {
		return ocfByrGrp;
	}

	/**
	 * Sets the ocfByrGrp
	 *
	 * @param ocfByrGrp the ocfByrGrp to set
	 */
	public void setOcfByrGrp(String ocfByrGrp) {
		this.ocfByrGrp = ocfByrGrp;
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
	 * @param rhqCd the rhqCd to set
	 */
	public void setRhqCd(String rhqCd) {
		this.rhqCd = rhqCd;
	}
	
	/**
	 * Gets the byrGrpCd
	 *
	 * @return the byrGrpCd
	 */
	public String getByrGrpCd() {
		return byrGrpCd;
	}

	/**
	 * Sets the byrGrpCd
	 *
	 * @param byrGrpCd the byrGrpCd to set
	 */
	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}
}