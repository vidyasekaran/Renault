/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */  
package com.nissangroups.pd.b000011.output;


/**
 * The Class B000008ParamOutput.
 * @author z015060
 *
 */
public class B000011ParamOutput {

	private String porCd;
	private String prcsOlyFlg;
	private String ordrTkBsMnth;
	
	
	
	public String getPorCd() {
		return porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getPrcsOlyFlg() {
		return prcsOlyFlg;
	}
	public void setPrcsOlyFlg(String prcsOlyFlg) {
		this.prcsOlyFlg = prcsOlyFlg;
	}
	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}
	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}
	
	
	
	
	
}
