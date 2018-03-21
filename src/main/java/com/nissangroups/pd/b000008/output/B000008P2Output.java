/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          :Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000008.output;

import java.util.List;

/**
 * The Class B000008P2Output.
 *
 * @author z015060
 */
public class B000008P2Output {
	
	private List<Object[]> mnthlyOrdrTrnList;
	private List<Object[]> ordrInfo;
	private List<String> porPrdStgeCd;
	private String preStgflg;
	private List<Object[]> orderableEndItm;
	private List<Object[]> potOrderableEndItm;
	private List<Object[]> dupInsrtMnthOrdrTrnList;
	
	public List<Object[]> getDupInsrtMnthOrdrTrnList() {
		return dupInsrtMnthOrdrTrnList;
	}
	public void setDupInsrtMnthOrdrTrnList(List<Object[]> dupInsrtMnthOrdrTrnList) {
		this.dupInsrtMnthOrdrTrnList = dupInsrtMnthOrdrTrnList;
	}
	public List<Object[]> getPotOrderableEndItm() {
		return potOrderableEndItm;
	}
	public void setPotOrderableEndItm(List<Object[]> potOrderableEndItm) {
		this.potOrderableEndItm = potOrderableEndItm;
	}
	public List<Object[]> getOrderableEndItm() {
		return orderableEndItm;
	}
	public void setOrderableEndItm(List<Object[]> orderableEndItm) {
		this.orderableEndItm = orderableEndItm;
	}
	
	public List<String> getPorPrdStgeCd() {
		return porPrdStgeCd;
	}
	public void setPorPrdStgeCd(List<String> porPrdStgeCd) {
		this.porPrdStgeCd = porPrdStgeCd;
	}
	public List<Object[]> getMnthlyOrdrTrnList() {
		return mnthlyOrdrTrnList;
	}
	public void setMnthlyOrdrTrnList(List<Object[]> mnthlyOrdrTrnList) {
		this.mnthlyOrdrTrnList = mnthlyOrdrTrnList;
	}
	public List<Object[]> getOrdrInfo() {
		return ordrInfo;
	}
	public void setOrdrInfo(List<Object[]> ordrInfo) {
		this.ordrInfo = ordrInfo;
	}
	public String getPreStgflg() {
		return preStgflg;
	}
	public void setPreStgflg(String preStgflg) {
		this.preStgflg = preStgflg;
	}

	
	
	

}
