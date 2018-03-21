/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.b000017.output;

/** P00001 */
public class ExtOrdrTkBsMnthOutput {
	private String updateOnlyFlag;
	private String otbm;
	
	public String getUpdateOnlyFlag() {
		return updateOnlyFlag;
	}
	public void setUpdateOnlyFlag(String updateOnlyFlag) {
		this.updateOnlyFlag = updateOnlyFlag;
	}
	public String getOtbm() {
		return otbm;
	}
	public void setOtbm(String otbm) {
		this.otbm = otbm;
	}

}
