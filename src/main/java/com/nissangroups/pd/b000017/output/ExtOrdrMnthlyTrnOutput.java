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

/** P00002 */

public class ExtOrdrMnthlyTrnOutput {
	
	private ExtUptSpecMstOutput objExtUptSpecMstOutput;
	private FeatureUptOSEIMstOutput objFeatureUptOSEIMstOutput;
	private FeatureUptOEIMstOutput objFeatureUptOEIMstOutput;
	private ExtOrdDetailsOutput objExtOrdDetailsOutput;
	
	public ExtUptSpecMstOutput getObjExtUptSpecMstOutput() {
		return objExtUptSpecMstOutput;
	}
	public void setObjExtUptSpecMstOutput(ExtUptSpecMstOutput objExtUptSpecMstOutput) {
		this.objExtUptSpecMstOutput = objExtUptSpecMstOutput;
	}
	public FeatureUptOSEIMstOutput getObjFeatureUptOSEIMstOutput() {
		return objFeatureUptOSEIMstOutput;
	}
	public void setObjFeatureUptOSEIMstOutput(
			FeatureUptOSEIMstOutput objFeatureUptOSEIMstOutput) {
		this.objFeatureUptOSEIMstOutput = objFeatureUptOSEIMstOutput;
	}
	public FeatureUptOEIMstOutput getObjFeatureUptOEIMstOutput() {
		return objFeatureUptOEIMstOutput;
	}
	public void setObjFeatureUptOEIMstOutput(
			FeatureUptOEIMstOutput objFeatureUptOEIMstOutput) {
		this.objFeatureUptOEIMstOutput = objFeatureUptOEIMstOutput;
	}
	public ExtOrdDetailsOutput getObjExtOrdDetailsOutput() {
		return objExtOrdDetailsOutput;
	}
	public void setObjExtOrdDetailsOutput(ExtOrdDetailsOutput objExtOrdDetailsOutput) {
		this.objExtOrdDetailsOutput = objExtOrdDetailsOutput;
	}

}
