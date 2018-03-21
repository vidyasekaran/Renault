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

import java.util.List;

public class ExtUptSpecMstOutput {
	
	private List<UK_OSEI> lstUK_OSEI;

	public List<UK_OSEI> getLstUK_OSEI() {
		return lstUK_OSEI;
	}

	public void setLstUK_OSEI(List<UK_OSEI> lstUK_OSEI) {
		this.lstUK_OSEI = lstUK_OSEI;
	}
	
	

}
