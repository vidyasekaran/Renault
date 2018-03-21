/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000051
 * Module          :Weekly Ordering					
 * Process Outline :Create Weekly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-12-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000051.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class B000051Output.
 *
 * @author z015060
 */
public class B000051Output {	

	B000051ParamOutput objB000051Param;
	
	Map<String, List<String>> prodMnthWkNum= new HashMap<String, List<String>>();
	
	private List<Object[]> ordrInfoMstSchd;
	
	private List<Object[]> ordrInfo;
	
	private List<Object[]> lglPlnLst;
	
	private String plntLneSummary;

	private String plntCd;

	private String lineClass;

	private String cnstDayNo;

	private String FeaturetypeCd;

	

	public String getPlntLneSummary() {
		return plntLneSummary;
	}

	public void setPlntLneSummary(String plntLneSummary) {
		this.plntLneSummary = plntLneSummary;
	}

	public String getPlntCd() {
		return plntCd;
	}

	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}

	public String getLineClass() {
		return lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getCnstDayNo() {
		return cnstDayNo;
	}

	public void setCnstDayNo(String cnstDayNo) {
		this.cnstDayNo = cnstDayNo;
	}

	public String getFeaturetypeCd() {
		return FeaturetypeCd;
	}

	public void setFeaturetypeCd(String featuretypeCd) {
		FeaturetypeCd = featuretypeCd;
	}

	public List<Object[]> getLglPlnLst() {
		return lglPlnLst;
	}

	public void setLglPlnLst(List<Object[]> lglPlnLst) {
		this.lglPlnLst = lglPlnLst;
	}

	public List<Object[]> getOrdrInfo() {
		return ordrInfo;
	}

	public void setOrdrInfo(List<Object[]> ordrInfo) {
		this.ordrInfo = ordrInfo;
	}

	public List<Object[]> getOrdrInfoMstSchd() {
		return ordrInfoMstSchd;
	}

	public void setOrdrInfoMstSchd(List<Object[]> ordrInfoMstSchd) {
		this.ordrInfoMstSchd = ordrInfoMstSchd;
	}

	public Map<String, List<String>> getProdMnthWkNum() {
		return prodMnthWkNum;
	}

	public void setProdMnthWkNum(Map<String, List<String>> prodMnthWkNum) {
		this.prodMnthWkNum = prodMnthWkNum;
	}

	public B000051ParamOutput getObjB000051Param() {
		return objB000051Param;
	}

	public void setObjB000051Param(B000051ParamOutput objB000051Param) {
		this.objB000051Param = objB000051Param;
	}
	
	
	}
	
