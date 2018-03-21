/*
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
 * This Class I000103RoleMstrParam is to set the extracted column values from MST_ROLE database table values.
 * 
 * @author z016127
 *
 */
public class I000103RoleMstrParam 
{
	/**The input parameter role Id */
	private String roleId;
	
	/**The input parameter role description */
	private String roleDesc;
	
	/**The input parameter created by */
	private String crtdBy;
	
	/**The input parameter updated by */
	private String updtdBy;
	
	/**The input parameter process flag */
	private String procsFlg;

	/**
	 * Get the roleId
	 *
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * Sets the roleId
	 *
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * Get the roleDesc
	 *
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * Sets the roleDesc
	 *
	 * @param roleDesc the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
