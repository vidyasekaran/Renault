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

public class UK_OSEI {
	
	private String strUK_OSEI_ID;
	private String strOEI_BUYER_ID;
	
	public String getStrUK_OSEI_ID() {
		return strUK_OSEI_ID;
	}
	public void setStrUK_OSEI_ID(String strUK_OSEI_ID) {
		this.strUK_OSEI_ID = strUK_OSEI_ID;
	}
	public String getStrOEI_BUYER_ID() {
		return strOEI_BUYER_ID;
	}
	public void setStrOEI_BUYER_ID(String strOEI_BUYER_ID) {
		this.strOEI_BUYER_ID = strOEI_BUYER_ID;
	}
	
	

}
