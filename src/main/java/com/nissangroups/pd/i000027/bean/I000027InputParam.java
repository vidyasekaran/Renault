/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send Monthly OCF Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.bean;

/**
 * This Class is used to store arguments from the command line which is used by query as input.
 * 
 * @author z014029
 */

public class I000027InputParam {
	
	/**Interface input parameter POR Code*/ 
	private String porCd;
	
	/**Interface input parameter OCF Region Code*/ 
	private String ocfRgnCd;
	
	/**Interface input parameter OCF Buyer Group Code*/ 
	private String ocfByrGrp;
	
	/**Interface input parameter RHQ Code*/ 
	private String rhqCd;
	
	/**Interface input parameter Buyer Group Code*/ 
	private String byrGrpCd;
	
	/**Interface input parameter Stage Code*/ 
	private String stgCd;

	
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

	/**
	 * Gets the stgCd
	 *
	 * @return the stgCd
	 */
	public String getStgCd() {
		return stgCd;
	}

	/**
	 * Sets the stgCd
	 *
	 * @param stgCd the stgCd to set
	 */
	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}
	
}