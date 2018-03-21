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

public class FeatureInfo {
	
	private String strProductionMonth;
	private String strUK_OSEI_ID;
	private String strFeatureCode;
	private String strFeatureTypeCode;
	private String strOCFFrameCode;
	private String strCarSeries;
	private String strBuyerGroupCode;
	private int intOrdrQty;
	
	
	
	public int getIntOrdrQty() {
		return intOrdrQty;
	}
	public void setIntOrdrQty(int intOrdrQty) {
		this.intOrdrQty = intOrdrQty;
	}
	public String getStrProductionMonth() {
		return strProductionMonth;
	}
	public void setStrProductionMonth(String strProductionMonth) {
		this.strProductionMonth = strProductionMonth;
	}
	public String getStrUK_OSEI_ID() {
		return strUK_OSEI_ID;
	}
	public void setStrUK_OSEI_ID(String strUK_OSEI_ID) {
		this.strUK_OSEI_ID = strUK_OSEI_ID;
	}
	public String getStrFeatureCode() {
		return strFeatureCode;
	}
	public void setStrFeatureCode(String strFeatureCode) {
		this.strFeatureCode = strFeatureCode;
	}
	public String getStrFeatureTypeCode() {
		return strFeatureTypeCode;
	}
	public void setStrFeatureTypeCode(String strFeatureTypeCode) {
		this.strFeatureTypeCode = strFeatureTypeCode;
	}
	public String getStrOCFFrameCode() {
		return strOCFFrameCode;
	}
	public void setStrOCFFrameCode(String strOCFFrameCode) {
		this.strOCFFrameCode = strOCFFrameCode;
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
	
	
}
