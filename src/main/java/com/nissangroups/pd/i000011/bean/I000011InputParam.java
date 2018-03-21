/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000011
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Control the Order Take on Local Systems 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000011.bean;

/**
 *  This class is used to set and retrieve the value of an attribute
 * and allow control over the input values passed
 * 
 * @author z014029
 */

public class I000011InputParam {
	
	/**Interface input parameter POR Code*/ 
	private String porCd;
	
	/**Interface input parameter OCF Region Code*/
	private String ocfRgnCd;
	
	/**Interface input parameter OCF Buyer Code*/
	private String ocfByrCd;
	
	/**Interface input parameter RHQ Code*/
	private String rhqCd;
	
	/**Interface input parameter Buyer Group Code*/
	private String byrGrpCd;
	
	/**Interface input parameter Production Limit*/
	private String prdLmt;
	
	/**Interface input parameter End Item Status Code*/
	private String endItmSttsCd;
	
	
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
	 * Gets the ocfByrCd
	 *
	 * @return the ocfByrCd
	 */
	public String getOcfByrCd() {
		return ocfByrCd;
	}

	/**
	 * Sets the ocfByrCd
	 *
	 * @param ocfByrCd the ocfByrCd to set
	 */
	public void setOcfByrCd(String ocfByrCd) {
		this.ocfByrCd = ocfByrCd;
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
	 * Gets the prdLmt
	 *
	 * @return the prdLmt
	 */
	public String getPrdLmt() {
		return prdLmt;
	}

	/**
	 * Sets the prdLmt
	 *
	 * @param prdLmt the prdLmt to set
	 */
	public void setPrdLmt(String prdLmt) {
		this.prdLmt = prdLmt;
	}

	/**
	 * Gets the endItmSttsCd
	 *
	 * @return the endItmSttsCd
	 */
	public String getEndItmSttsCd() {
		return endItmSttsCd;
	}

	/**
	 * Sets the endItmSttsCd
	 *
	 * @param endItmSttsCd the endItmSttsCd to set
	 */
	public void setEndItmSttsCd(String endItmSttsCd) {
		this.endItmSttsCd = endItmSttsCd;
	}
}