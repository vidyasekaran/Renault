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

import java.util.List;

/** P4 Output */
public class ExtOCFInfoOutput {
	
	private ExtByrInfoOutput objExtByrInfoOutput;
	private String strPrdMnth;
	private List<FeatureInfo> lstFeatureInfo;
	
	public ExtByrInfoOutput getObjExtByrInfoOutput() {
		return objExtByrInfoOutput;
	}
	public void setObjExtByrInfoOutput(ExtByrInfoOutput objExtByrInfoOutput) {
		this.objExtByrInfoOutput = objExtByrInfoOutput;
	}
	public String getStrPrdMnth() {
		return strPrdMnth;
	}
	public void setStrPrdMnth(String strPrdMnth) {
		this.strPrdMnth = strPrdMnth;
	}
	public List<FeatureInfo> getLstFeatureInfo() {
		return lstFeatureInfo;
	}
	public void setLstFeatureInfo(List<FeatureInfo> lstFeatureInfo) {
		this.lstFeatureInfo = lstFeatureInfo;
	}
	
	
	
	

}
