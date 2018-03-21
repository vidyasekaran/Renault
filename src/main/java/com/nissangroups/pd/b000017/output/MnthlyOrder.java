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

public class MnthlyOrder {
	
	private String strUK_OSEI_ID;
	private String strUK_OEI_BUYER_ID;
	private String strCarSeries;
	private String strPrdMnth;
	private String strBuyerGroupCode;
	private String updateOnlyFlag;
	private int intMSQty;
	private String strUpdtDt;
	
	public String getStrUpdtDt() {
		return strUpdtDt;
	}
	public void setStrUpdtDt(String strUpdtDt) {
		this.strUpdtDt = strUpdtDt;
	}
	public int getIntOrdQty() {
		return intOrdQty;
	}
	public void setIntOrdQty(int intOrdQty) {
		this.intOrdQty = intOrdQty;
	}
	private int intOrdQty;
	
	public String getStrPrdMnth() {
		return strPrdMnth;
	}
	public void setStrPrdMnth(String strPrdMnth) {
		this.strPrdMnth = strPrdMnth;
	}
	public int getIntMSQty() {
		return intMSQty;
	}
	public void setIntMSQty(int intMSQty) {
		this.intMSQty = intMSQty;
	}
	public String getStrUK_OSEI_ID() {
		return strUK_OSEI_ID;
	}
	public void setStrUK_OSEI_ID(String strUK_OSEI_ID) {
		this.strUK_OSEI_ID = strUK_OSEI_ID;
	}
	public String getStrUK_OEI_BUYER_ID() {
		return strUK_OEI_BUYER_ID;
	}
	public void setStrUK_OEI_BUYER_ID(String strUK_OEI_BUYER_ID) {
		this.strUK_OEI_BUYER_ID = strUK_OEI_BUYER_ID;
	}
	public String getStrCarSeries() {
		return strCarSeries;
	}
	public void setStrCarSeries(String strCarSeries) {
		this.strCarSeries = strCarSeries;
	}
	public String getStrBuyerGroupCode() {
		return strBuyerGroupCode;
	}
	public void setStrBuyerGroupCode(String strBuyerGroupCode) {
		this.strBuyerGroupCode = strBuyerGroupCode;
	}
	public String getUpdateOnlyFlag() {
		return updateOnlyFlag;
	}
	public void setUpdateOnlyFlag(String updateOnlyFlag) {
		this.updateOnlyFlag = updateOnlyFlag;
	}
	
	

}
