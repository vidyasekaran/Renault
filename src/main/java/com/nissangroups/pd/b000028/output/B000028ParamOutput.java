/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.output;

/**
 * The Class B000028ParamOutput.
 * @author z015060
 *
 */
public class B000028ParamOutput {

	private String porCd;
	private String ordrTkBsMnth;
	private String stgCd;
	
	
	
	public String getPorCd() {
		return porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}
	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}
	public String getStgCd() {
		return stgCd;
	}
	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}
	
	
	
	
	
	
}
