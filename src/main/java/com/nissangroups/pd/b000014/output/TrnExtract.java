/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.output;

public class TrnExtract {
	
	private String strOEIByrId;
	private String strCarSrs;
	private String strByrGrpCd;
	private String strPrdMnth;
	private String strOCFByrGrpCd;
	private String strOCFRgnCd;
	private int intOrdrQty;
	private int intMSQty;
	private String  strPorCd;
	private String strOtbm;
	private String strFeatureCd;
	private String strOCFFrmcd;
	private String strFeatureTypCd;
	
	public String getStrPorCd() {
		return strPorCd;
	}
	public void setStrPorCd(String strPorCd) {
		this.strPorCd = strPorCd;
	}
	public String getStrOtbm() {
		return strOtbm;
	}
	public void setStrOtbm(String strOtbm) {
		this.strOtbm = strOtbm;
	}
	public String getStrFeatureCd() {
		return strFeatureCd;
	}
	public void setStrFeatureCd(String strFeatureCd) {
		this.strFeatureCd = strFeatureCd;
	}
	public String getStrOCFFrmcd() {
		return strOCFFrmcd;
	}
	public void setStrOCFFrmcd(String strOCFFrmcd) {
		this.strOCFFrmcd = strOCFFrmcd;
	}
	public String getStrFeatureTypCd() {
		return strFeatureTypCd;
	}
	public void setStrFeatureTypCd(String strFeatureTypCd) {
		this.strFeatureTypCd = strFeatureTypCd;
	}
	public String getStrOEIByrId() {
		return strOEIByrId;
	}
	public void setStrOEIByrId(String strOEIByrId) {
		this.strOEIByrId = strOEIByrId;
	}
	public String getStrCarSrs() {
		return strCarSrs;
	}
	public void setStrCarSrs(String strCarSrs) {
		this.strCarSrs = strCarSrs;
	}
	public String getStrByrGrpCd() {
		return strByrGrpCd;
	}
	public void setStrByrGrpCd(String strByrGrpCd) {
		this.strByrGrpCd = strByrGrpCd;
	}
	public String getStrPrdMnth() {
		return strPrdMnth;
	}
	public void setStrPrdMnth(String strPrdMnth) {
		this.strPrdMnth = strPrdMnth;
	}
	public String getStrOCFByrGrpCd() {
		return strOCFByrGrpCd;
	}
	public void setStrOCFByrGrpCd(String strOCFByrGrpCd) {
		this.strOCFByrGrpCd = strOCFByrGrpCd;
	}
	public String getStrOCFRgnCd() {
		return strOCFRgnCd;
	}
	public void setStrOCFRgnCd(String strOCFRgnCd) {
		this.strOCFRgnCd = strOCFRgnCd;
	}
	public int getIntOrdrQty() {
		return intOrdrQty;
	}
	public void setIntOrdrQty(int intOrdrQty) {
		this.intOrdrQty = intOrdrQty;
	}
	public int getIntMSQty() {
		return intMSQty;
	}
	public void setIntMSQty(int intMSQty) {
		this.intMSQty = intMSQty;
	}
	
	

}
