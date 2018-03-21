/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.bean;

/**
 * This Class I000039InputParam is used to store arguments from the command line
 * which is used by query as input.
 * 
 * @author z014135
 * 
 */
public class I000039InputParam {
	
	/** The Input parameter porCd */
	private String porCd;
	
	/** The Input parameter buyerCd */
	private String buyerCd;
	
	
	public String getPorCd() {
		return porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	
	public String getBuyerCd() {
		return buyerCd;
	}
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}
	
	

}
