/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.bean;
/*
 * This class is used to store the input parameters required which will be used in the SQL to 
 * fetch the data from the database.
 * 
 * @author Z014676
 */
public class I000101InputParam {

    /*
     * interface Porcd value 
     */
	private String porCd;
	/**
	 * Instantiates a new I000101.
	 */
    public I000101InputParam(){
    	
    }
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
