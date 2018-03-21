/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000051.output;

/**
 * The Class B000008ParamOutput.
 * @author z015060
 *
 */
public class B000051ParamOutput {
	
	
	private String porCd;
	private String ordrTkBsMnth;
	private String ordrTkWkNo;
	private String reRunFlg;
	
	
	
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
	public String getOrdrTkWkNo() {
		return ordrTkWkNo;
	}
	public void setOrdrTkWkNo(String ordrTkWkNo) {
		this.ordrTkWkNo = ordrTkWkNo;
	}
	public String getReRunFlg() {
		return reRunFlg;
	}
	public void setReRunFlg(String reRunFlg) {
		this.reRunFlg = reRunFlg;
	}
	
	
	
}
