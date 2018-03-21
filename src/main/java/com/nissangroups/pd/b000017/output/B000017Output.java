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

import com.nissangroups.pd.framework.CommonBaseProcessOutput;

public class B000017Output extends CommonBaseProcessOutput{
	
	/** P1 */
	private ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput;
	/** P2 */
	private ExtOrdrMnthlyTrnOutput objExtOrdrMnthlyTrnOutput;
	
	/** P9 */
	private UptSpecTimeOutput objUptSpecTimeOutput;
	public ExtOrdrTkBsMnthOutput getObjExtOrdrTkBsMnthOutput() {
		return objExtOrdrTkBsMnthOutput;
	}
	public void setObjExtOrdrTkBsMnthOutput(
			ExtOrdrTkBsMnthOutput objExtOrdrTkBsMnthOutput) {
		this.objExtOrdrTkBsMnthOutput = objExtOrdrTkBsMnthOutput;
	}
	public ExtOrdrMnthlyTrnOutput getObjExtOrdrMnthlyTrnOutput() {
		return objExtOrdrMnthlyTrnOutput;
	}
	public void setObjExtOrdrMnthlyTrnOutput(
			ExtOrdrMnthlyTrnOutput objExtOrdrMnthlyTrnOutput) {
		this.objExtOrdrMnthlyTrnOutput = objExtOrdrMnthlyTrnOutput;
	}
	
	public UptSpecTimeOutput getObjUptSpecTimeOutput() {
		return objUptSpecTimeOutput;
	}
	public void setObjUptSpecTimeOutput(UptSpecTimeOutput objUptSpecTimeOutput) {
		this.objUptSpecTimeOutput = objUptSpecTimeOutput;
	}
	
	

}
