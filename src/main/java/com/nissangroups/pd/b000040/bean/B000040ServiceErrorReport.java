/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-B000040
 * Module          :
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000040.bean;

/*
 * This class is used to hold the attributes required to generate the service error report - 
 */
public class B000040ServiceErrorReport 
{
		
	/**
	 * P0004.6 Production Month
	 */
	private String prodMnth;
	
	/**
	 * P0004.6 Production Week No
	 */
	private String prodWeekNo;
	
	/**
	 * P0004.6 EX No 
	 */
	private String exNo;
	
	/**
	 * P0004.6 Sales Note
	 */
	private String slsNt;
	
	/**
	 * P0004.6 Applied Mode Code
	 */
	private String appliedModeCD;
	
	/**
	 * P0004.6 Quantity
	 */
	private String qty;
	
	/**
	 * P0004.6 Additional Spec Code 
	 */
	private String addtnlSpcCd;
	
	/**
	 * P0004.6 Spec Destination Code 
	 */
	private String specDestnCd;
	
	/**
	 *P0004.6 Buyer Code 
	 */
	private String byrCd;

	public String getProdMnth() {
		return prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public String getProdWeekNo() {
		return prodWeekNo;
	}

	public void setProdWeekNo(String prodWeekNo) {
		this.prodWeekNo = prodWeekNo;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getSlsNt() {
		return slsNt;
	}

	public void setSlsNt(String slsNt) {
		this.slsNt = slsNt;
	}

	public String getAppliedModeCD() {
		return appliedModeCD;
	}

	public void setAppliedModeCD(String appliedModeCD) {
		this.appliedModeCD = appliedModeCD;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getAddtnlSpcCd() {
		return addtnlSpcCd;
	}

	public void setAddtnlSpcCd(String addtnlSpcCd) {
		this.addtnlSpcCd = addtnlSpcCd;
	}

	public String getSpecDestnCd() {
		return specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	public String getByrCd() {
		return byrCd;
	}

	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}
	
}