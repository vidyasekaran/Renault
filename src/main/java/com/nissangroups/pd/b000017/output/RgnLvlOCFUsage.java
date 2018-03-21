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

public class RgnLvlOCFUsage {
	
	private String strPorCd;
	private String strOCFRgnCd;
	private String strOCFByrGrpCd;
	private String strPrdMnth;
	private String strOrdrTkBsMnth;
	private String strCrSrs;
	private String strFeatureTypCd;
	private String strOCFFrmCd;
	private String strFeatureCd;
	private int intSumRgnOCFUsageQty;
	
	public String getStrPorCd() {
		return strPorCd;
	}
	public void setStrPorCd(String strPorCd) {
		this.strPorCd = strPorCd;
	}
	public String getStrOCFRgnCd() {
		return strOCFRgnCd;
	}
	public void setStrOCFRgnCd(String strOCFRgnCd) {
		this.strOCFRgnCd = strOCFRgnCd;
	}
	public String getStrOCFByrGrpCd() {
		return strOCFByrGrpCd;
	}
	public void setStrOCFByrGrpCd(String strOCFByrGrpCd) {
		this.strOCFByrGrpCd = strOCFByrGrpCd;
	}
	public String getStrPrdMnth() {
		return strPrdMnth;
	}
	public void setStrPrdMnth(String strPrdMnth) {
		this.strPrdMnth = strPrdMnth;
	}
	public String getStrOrdrTkBsMnth() {
		return strOrdrTkBsMnth;
	}
	public void setStrOrdrTkBsMnth(String strOrdrTkBsMnth) {
		this.strOrdrTkBsMnth = strOrdrTkBsMnth;
	}
	public String getStrCrSrs() {
		return strCrSrs;
	}
	public void setStrCrSrs(String strCrSrs) {
		this.strCrSrs = strCrSrs;
	}
	public String getStrFeatureTypCd() {
		return strFeatureTypCd;
	}
	public void setStrFeatureTypCd(String strFeatureTypCd) {
		this.strFeatureTypCd = strFeatureTypCd;
	}
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
	public int getIntSumRgnOCFUsageQty() {
		return intSumRgnOCFUsageQty;
	}
	public void setIntSumRgnOCFUsageQty(int intSumRgnOCFUsageQty) {
		this.intSumRgnOCFUsageQty = intSumRgnOCFUsageQty;
	}
	
	
	

}
