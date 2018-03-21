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

public class ByrGrpFeatureUsage {
	
	private int intSumByrGrpFeatureUsageQty;
	private String strFeatureCd;
	private String strFeatureTypCd;
	private String strOCFFrmCd;
	private String strPorCd;
	private String strByrGrpCd;
	private String strOrdrTkBsMnth;
	private String strCrSrs;
	private String strPrdMnth;
	
	public int getIntSumByrGrpFeatureUsageQty() {
		return intSumByrGrpFeatureUsageQty;
	}
	public void setIntSumByrGrpFeatureUsageQty(int intSumByrGrpFeatureUsageQty) {
		this.intSumByrGrpFeatureUsageQty = intSumByrGrpFeatureUsageQty;
	}
	public String getStrFeatureCd() {
		return strFeatureCd;
	}
	public void setStrFeatureCd(String strFeatureCd) {
		this.strFeatureCd = strFeatureCd;
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
	public String getStrPorCd() {
		return strPorCd;
	}
	public void setStrPorCd(String strPorCd) {
		this.strPorCd = strPorCd;
	}
	public String getStrByrGrpCd() {
		return strByrGrpCd;
	}
	public void setStrByrGrpCd(String strByrGrpCd) {
		this.strByrGrpCd = strByrGrpCd;
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
	public String getStrPrdMnth() {
		return strPrdMnth;
	}
	public void setStrPrdMnth(String strPrdMnth) {
		this.strPrdMnth = strPrdMnth;
	}
	
	
}
