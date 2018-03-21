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

public class ExtOrdDetailsOutput {
	
	private List<MnthlyOrder> lstMnthlyOrder;
	private List<TableDetails> lstTableDetails;
	private int intSumMSQty;
	private int intSumOrdQty;
	
	
	public int getIntSumOrdQty() {
		return intSumOrdQty;
	}
	public void setIntSumOrdQty(int intSumOrdQty) {
		this.intSumOrdQty = intSumOrdQty;
	}
	public int getIntSumMSQty() {
		return intSumMSQty;
	}
	public void setIntSumMSQty(int intSumMSQty) {
		this.intSumMSQty = intSumMSQty;
	}
	public List<MnthlyOrder> getLstMnthlyOrder() {
		return lstMnthlyOrder;
	}
	public void setLstMnthlyOrder(List<MnthlyOrder> lstMnthlyOrder) {
		this.lstMnthlyOrder = lstMnthlyOrder;
	}
	public List<TableDetails> getLstTableDetails() {
		return lstTableDetails;
	}
	public void setLstTableDetails(List<TableDetails> lstTableDetails) {
		this.lstTableDetails = lstTableDetails;
	}

}
