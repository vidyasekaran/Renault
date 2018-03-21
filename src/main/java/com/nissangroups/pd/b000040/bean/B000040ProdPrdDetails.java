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

import java.util.List;

public class B000040ProdPrdDetails {
	
	/**
	 * P03 Production Month
	 */
	private String prodMnth;
	
	/**
	 * P03 Production Week No
	 */
	private List<String> prodWkNo;

	public String getProdMnth() {
		return prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public List<String> getProdWkNo() {
		return prodWkNo;
	}

	public void setProdWkNo(List<String> prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	
	
}