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

/** P3 Output */
public class ExtByrInfoOutput {
	
	private ExtMnthlyBtchPrsSttsTblOutput objExtMnthlyBtchPrsSttsTblOutput;
	private String strByrGrpCd;
	private String strByrCd;
	private String strOEIByrID;
	private String strOSEIID;
	private int intMSQty;
	private int intOrdrQty;
	
	public ExtMnthlyBtchPrsSttsTblOutput getObjExtMnthlyBtchPrsSttsTblOutput() {
		return objExtMnthlyBtchPrsSttsTblOutput;
	}
	public void setObjExtMnthlyBtchPrsSttsTblOutput(
			ExtMnthlyBtchPrsSttsTblOutput objExtMnthlyBtchPrsSttsTblOutput) {
		this.objExtMnthlyBtchPrsSttsTblOutput = objExtMnthlyBtchPrsSttsTblOutput;
	}
	public String getStrByrGrpCd() {
		return strByrGrpCd;
	}
	public void setStrByrGrpCd(String strByrGrpCd) {
		this.strByrGrpCd = strByrGrpCd;
	}
	public String getStrByrCd() {
		return strByrCd;
	}
	public void setStrByrCd(String strByrCd) {
		this.strByrCd = strByrCd;
	}
	public String getStrOEIByrID() {
		return strOEIByrID;
	}
	public void setStrOEIByrID(String strOEIByrID) {
		this.strOEIByrID = strOEIByrID;
	}
	public String getStrOSEIID() {
		return strOSEIID;
	}
	public void setStrOSEIID(String strOSEIID) {
		this.strOSEIID = strOSEIID;
	}
	public int getIntMSQty() {
		return intMSQty;
	}
	public void setIntMSQty(int intMSQty) {
		this.intMSQty = intMSQty;
	}
	public int getIntOrdrQty() {
		return intOrdrQty;
	}
	public void setIntOrdrQty(int intOrdrQty) {
		this.intOrdrQty = intOrdrQty;
	}
	
	
	

}
