/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.output;

public class FeatureInfo {
	
	private String strOCFFrmCd;
	private String strFeatureCd;
	
	public String getStrOCFFrmCd() {
		return strOCFFrmCd;
	}
	public void setStrOCFFrmCd(String strOCFFrmCd) {
		this.strOCFFrmCd = strOCFFrmCd;
	}
	public String getStrFeatureCd() {
		return strFeatureCd;
	}
	public void setStrFeatureCd(String strFeatureCd) {
		this.strFeatureCd = strFeatureCd;
	}	

}
