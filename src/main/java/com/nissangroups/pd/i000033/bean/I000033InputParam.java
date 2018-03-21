/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000033.bean;

/**
 * This Class is used to store arguments from the command line which is used by query as input.
 * 
 * @author z014676
 *
 */
public class I000033InputParam 
{
	
	/** The por cd. */
	private String porCd;

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

	

	
	

}
