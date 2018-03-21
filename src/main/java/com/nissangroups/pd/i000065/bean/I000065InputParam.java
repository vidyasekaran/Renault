/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.bean;

/**
 * This class is used to store the input parameters required which will be used in the SQL to 
 * fetch the data from the database.
 * 
 * @author z014676
 *
 */
public class I000065InputParam {
	/*
	 * Interface  parameter Por cd
	 */
	private String porCd;
	/*
	 * Interface parameter Buyer group cd
	 */
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
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
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
	 * @param buyerGrpCd the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	
}
