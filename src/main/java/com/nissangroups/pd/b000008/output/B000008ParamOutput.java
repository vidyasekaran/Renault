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
 * The Class B000008ParamOutput.
 * @author z015060
 *
 */
public class B000008ParamOutput {

	private String porCd;
	private String updateOnlyFlg;
	private String overlapMsQtyFlg;
	private String prdStgCd;
	private String stgCd;
	
	
	public String getPorCd() {
		return porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	
	public String getPrdStgCd() {
		return prdStgCd;
	}
	public void setPrdStgCd(String prdStgCd) {
		this.prdStgCd = prdStgCd;
	}
	public String getStgCd() {
		return stgCd;
	}
	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}
	public String getUpdateOnlyFlg() {
		return updateOnlyFlg;
	}
	public void setUpdateOnlyFlg(String updateOnlyFlg) {
		this.updateOnlyFlg = updateOnlyFlg;
	}
	public String getOverlapMsQtyFlg() {
		return overlapMsQtyFlg;
	}
	public void setOverlapMsQtyFlg(String overlapMsQtyFlg) {
		this.overlapMsQtyFlg = overlapMsQtyFlg;
	}
	
	
	
}
