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

import java.util.ArrayList;
import java.util.List;

/** P00009 */
public class UptSpecTimeOutput {
	
	private int intSpecReexecuteStatus;
	
	private List<TableDetails> lstTableDetails = new ArrayList<TableDetails>(); 
	
	public List<TableDetails> getLstTableDetails() {
		return lstTableDetails;
	}

	public void setLstTableDetails(List<TableDetails> lstTableDetails) {
		this.lstTableDetails = lstTableDetails;
	}

	public int getIntSpecReexecuteStatus() {
		return intSpecReexecuteStatus;
	}

	public void setIntSpecReexecuteStatus(int intSpecReexecuteStatus) {
		this.intSpecReexecuteStatus = intSpecReexecuteStatus;
	}
	
	

}
