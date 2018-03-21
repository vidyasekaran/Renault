/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.output;

/**
 * The Class ReaderOutput
 * @author z015060
 *
 */
public class ReaderOutput {

	private String porCd	;								
	private String ordrTkBsMnth;
	private String stgCd;
	private String prdStgCd;
	

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
	public String getPrdStgCd() {
		return prdStgCd;
	}
	public void setPrdStgCd(String prdStgCd) {
		this.prdStgCd = prdStgCd;
	}
	

}
