/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000103.bean;


/**
 * This Class I000103UserMstParam is to set the extracted column values from MST_USER database table values.
 * 
 * @author z016127
 *
 */
public class I000103UserMstParam 
{
	/**The input parameter user Id */
	private String userId;
	
	/**The input parameter user name */
	private String userName;
	
	/**The input parameter timezone */
	private String timeZone;
	
	/**The input parameter created by */
	private String crtdBy;
	
	/**The input parameter updated by */
	private String updtdBy;
	
	/**The input parameter process flag */
	private String procsFlg;
	
	/**
	 * Get the userId
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 *
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Get the userName
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Sets the userName
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Get the timeZone
	 *
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}
	/**
	 * Sets the timeZone
	 *
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	/**
	 * Get the crtdBy
	 *
	 * @return the crtdBy
	 */
	public String getCrtdBy() {
		return crtdBy;
	}
	/**
	 * Sets the crtdBy
	 *
	 * @param crtdBy the crtdBy to set
	 */
	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}
	/**
	 * Get the updtdBy
	 *
	 * @return the updtdBy
	 */
	public String getUpdtdBy() {
		return updtdBy;
	}
	/**
	 * Sets the updtdBy
	 *
	 * @param updtdBy the updtdBy to set
	 */
	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}
	/**
	 * Get the procsFlg
	 *
	 * @return the procsFlg
	 */
	public String getProcsFlg() {
		return procsFlg;
	}
	/**
	 * Sets the procsFlg
	 *
	 * @param procsFlg the procsFlg to set
	 */
	public void setProcsFlg(String procsFlg) {
		this.procsFlg = procsFlg;
	}
	
}
