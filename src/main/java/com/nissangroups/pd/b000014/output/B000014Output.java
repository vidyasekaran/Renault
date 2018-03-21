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

import com.nissangroups.pd.framework.CommonBaseProcessOutput;

public class B000014Output extends CommonBaseProcessOutput{
	
	/** P1 */
	private ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput;
	/** P2.1 */
	private List<ExtMnthlyBtchPrsSttsTblOutput> lstExtMnthlyBtchPrsSttsTblOutput;
	
	/** P3 */
	private List<ExtByrInfoOutput> lstExtByrInfoOutput;
    
	/** P4 */
	private List<ExtOCFInfoOutput> lstExtOCFInfoOutput;
	
	public List<ExtOCFInfoOutput> getLstExtOCFInfoOutput() {
		return lstExtOCFInfoOutput;
	}

	public void setLstExtOCFInfoOutput(List<ExtOCFInfoOutput> lstExtOCFInfoOutput) {
		this.lstExtOCFInfoOutput = lstExtOCFInfoOutput;
	}

	public List<ExtByrInfoOutput> getLstExtByrInfoOutput() {
		return lstExtByrInfoOutput;
	}

	public void setLstExtByrInfoOutput(List<ExtByrInfoOutput> lstExtByrInfoOutput) {
		this.lstExtByrInfoOutput = lstExtByrInfoOutput;
	}

	public List<ExtMnthlyBtchPrsSttsTblOutput> getLstExtMnthlyBtchPrsSttsTblOutput() {
		return lstExtMnthlyBtchPrsSttsTblOutput;
	}

	public void setLstExtMnthlyBtchPrsSttsTblOutput(
			List<ExtMnthlyBtchPrsSttsTblOutput> lstExtMnthlyBtchPrsSttsTblOutput) {
		this.lstExtMnthlyBtchPrsSttsTblOutput = lstExtMnthlyBtchPrsSttsTblOutput;
	}

	public ExtOrdrTkBsMnthOutput getObjExtOrdrTkBsMnthOutput() {
		return objExtOrdrTkBsMnthOutput;
	}

	public void setObjExtOrdrTkBsMnthOutput(
			ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput) {
		this.objExtOrdrTkBsMnthOutput = objExtOrdrTkBsMnthOutput;
	}
		

}
