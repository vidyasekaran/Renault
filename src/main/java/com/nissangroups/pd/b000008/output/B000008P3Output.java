/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000008.output;

import java.util.List;

/**
 * The Class B000008P3Output.
 *
 * @author z015060
 */
public class B000008P3Output {

	private List<Object[]> nscFrcstVol;
	private List<Object[]> nscConf;
	private List<Object[]> nonSuspndData;
	
	
	public List<Object[]> getNscFrcstVol() {
		return nscFrcstVol;
	}
	public void setNscFrcstVol(List<Object[]> nscFrcstVol) {
		this.nscFrcstVol = nscFrcstVol;
	}
	public List<Object[]> getNscConf() {
		return nscConf;
	}
	public void setNscConf(List<Object[]> nscConf) {
		this.nscConf = nscConf;
	}
	public List<Object[]> getNonSuspndData() {
		return nonSuspndData;
	}
	public void setNonSuspndData(List<Object[]> nonSuspndData) {
		this.nonSuspndData = nonSuspndData;
	}
	

	
	
	
	
}
