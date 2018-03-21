/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000011.output;

import java.util.List;


/**
 * The Class B000011Output.
 *
 * @author z015060
 */
public class B000011Output {	


	B000011ParamOutput objB000011ParamOutput;
	private List<Object[]> ocfLmt;
	private List<Object[]> buyerGrpUsge;
	private List<Object[]> ocfBuyerGrpCd;
	private List<Object[]> rgnlMnthlyOcfLst;
	private List<String> ordrTkBsMnthLst;
	private String rgnlMnthlyFlg;
	
	
	public List<Object[]> getRgnlMnthlyOcfLst() {
		return rgnlMnthlyOcfLst;
	}
	public void setRgnlMnthlyOcfLst(List<Object[]> rgnlMnthlyOcfLst) {
		this.rgnlMnthlyOcfLst = rgnlMnthlyOcfLst;
	}
	public List<Object[]> getOcfBuyerGrpCd() {
		return ocfBuyerGrpCd;
	}
	public void setOcfBuyerGrpCd(List<Object[]> ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}
	public List<String> getOrdrTkBsMnthLst() {
		return ordrTkBsMnthLst;
	}
	public void setOrdrTkBsMnthLst(List<String> ordrTkBsMnthLst) {
		this.ordrTkBsMnthLst = ordrTkBsMnthLst;
	}
	public List<Object[]> getBuyerGrpUsge() {
		return buyerGrpUsge;
	}
	public void setBuyerGrpUsge(List<Object[]> buyerGrpUsge) {
		this.buyerGrpUsge = buyerGrpUsge;
	}
	public B000011ParamOutput getObjB000011ParamOutput() {
		return objB000011ParamOutput;
	}
	public void setObjB000011ParamOutput(B000011ParamOutput objB000011ParamOutput) {
		this.objB000011ParamOutput = objB000011ParamOutput;
	}
	public List<Object[]> getOcfLmt() {
		return ocfLmt;
	}
	public void setOcfLmt(List<Object[]> ocfLmt) {
		this.ocfLmt = ocfLmt;
	}
	public String getRgnlMnthlyFlg() {
		return rgnlMnthlyFlg;
	}
	public void setRgnlMnthlyFlg(String rgnlMnthlyFlg) {
		this.rgnlMnthlyFlg = rgnlMnthlyFlg;
	}
	
	}
	
