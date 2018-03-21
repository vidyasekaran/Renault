/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class B000028Output.
 *
 * @author z015060
 */
public class B000028Output {	
	
	B000028ParamOutput objB000028ParamOutput;
	private List<Object[]> carSrsHrzn;
	private List<Object[]> byrGrpLvl;
	private List<Object[]> byrGrpOCFLmt;
	private List<Object[]> byrGrpDiffList;
	private List<Object[]> byrCdLvl;
	private List<Object[]> potCdLvl;
	private List<Object[]> buyerMnthlyOCFUsage;
	private List<Object[]> breachOCFChk;
	private List<Object[]> oeiBuyerId;
	private List<Object[]> buyerMnthlyTemp;
	private List<Object[]> clrLvl;
	private List<Object[]> potLvl;
	private List<Object[]> potAdjustLvl;
	private List<Object[]> potLvlByrGrp;
	Map<String, String> byrGrpLvlMap = new HashMap<String, String>();
	Map<String, String> byrGrpLvlOCFMap = new HashMap<String, String>();
	Map<String, String> byrGrpDiffMap = new HashMap<String, String>();
	Map<String, String> byrGrpCFMap = new HashMap<String, String>();
	Map<String, String> oeiByrIdLvlMap= new HashMap<String,String>();
	Map<String, String> clrLvlMap= new HashMap<String,String>();

	
	
	public List<Object[]> getPotLvlByrGrp() {
		return potLvlByrGrp;
	}

	public void setPotLvlByrGrp(List<Object[]> potLvlByrGrp) {
		this.potLvlByrGrp = potLvlByrGrp;
	}

	public Map<String, String> getClrLvlMap() {
		return clrLvlMap;
	}

	public void setClrLvlMap(Map<String, String> clrLvlMap) {
		this.clrLvlMap = clrLvlMap;
	}

	public Map<String, String> getOeiByrIdLvlMap() {
		return oeiByrIdLvlMap;
	}

	public void setOeiByrIdLvlMap(Map<String, String> oeiByrIdLvlMap) {
		this.oeiByrIdLvlMap = oeiByrIdLvlMap;
	}

	public List<Object[]> getByrCdLvl() {
		return byrCdLvl;
	}

	public void setByrCdLvl(List<Object[]> byrCdLvl) {
		this.byrCdLvl = byrCdLvl;
	}

	public List<Object[]> getPotAdjustLvl() {
		return potAdjustLvl;
	}

	public void setPotAdjustLvl(List<Object[]> potAdjustLvl) {
		this.potAdjustLvl = potAdjustLvl;
	}

	public List<Object[]> getPotLvl() {
		return potLvl;
	}

	public void setPotLvl(List<Object[]> potLvl) {
		this.potLvl = potLvl;
	}

	public List<Object[]> getBuyerMnthlyTemp() {
		return buyerMnthlyTemp;
	}

	public void setBuyerMnthlyTemp(List<Object[]> buyerMnthlyTemp) {
		this.buyerMnthlyTemp = buyerMnthlyTemp;
	}

	public List<Object[]> getClrLvl() {
		return clrLvl;
	}

	public void setClrLvl(List<Object[]> clrLvl) {
		this.clrLvl = clrLvl;
	}

	public List<Object[]> getBuyerMnthlyOCFUsage() {
		return buyerMnthlyOCFUsage;
	}

	public void setBuyerMnthlyOCFUsage(List<Object[]> buyerMnthlyOCFUsage) {
		this.buyerMnthlyOCFUsage = buyerMnthlyOCFUsage;
	}

	public List<Object[]> getBreachOCFChk() {
		return breachOCFChk;
	}

	public void setBreachOCFChk(List<Object[]> breachOCFChk) {
		this.breachOCFChk = breachOCFChk;
	}

	public List<Object[]> getOeiBuyerId() {
		return oeiBuyerId;
	}

	public void setOeiBuyerId(List<Object[]> oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}

	public List<Object[]> getPotCdLvl() {
		return potCdLvl;
	}

	public void setPotCdLvl(List<Object[]> potCdLvl) {
		this.potCdLvl = potCdLvl;
	}

	public Map<String, String> getByrGrpCFMap() {
		return byrGrpCFMap;
	}

	public void setByrGrpCFMap(Map<String, String> byrGrpCFMap) {
		this.byrGrpCFMap = byrGrpCFMap;
	}

	public Map<String, String> getByrGrpDiffMap() {
		return byrGrpDiffMap;
	}

	public void setByrGrpDiffMap(Map<String, String> byrGrpDiffMap) {
		this.byrGrpDiffMap = byrGrpDiffMap;
	}

	public Map<String, String> getByrGrpLvlMap() {
		return byrGrpLvlMap;
	}

	public void setByrGrpLvlMap(Map<String, String> byrGrpLvlMap) {
		this.byrGrpLvlMap = byrGrpLvlMap;
	}

	public Map<String, String> getByrGrpLvlOCFMap() {
		return byrGrpLvlOCFMap;
	}

	public void setByrGrpLvlOCFMap(Map<String, String> byrGrpLvlOCFMap) {
		this.byrGrpLvlOCFMap = byrGrpLvlOCFMap;
	}

	public List<Object[]> getByrGrpDiffList() {
		return byrGrpDiffList;
	}

	public void setByrGrpDiffList(List<Object[]> byrGrpDiffList) {
		this.byrGrpDiffList = byrGrpDiffList;
	}

	public List<Object[]> getByrGrpOCFLmt() {
		return byrGrpOCFLmt;
	}

	public void setByrGrpOCFLmt(List<Object[]> byrGrpOCFLmt) {
		this.byrGrpOCFLmt = byrGrpOCFLmt;
	}

	public List<Object[]> getByrGrpLvl() {
		return byrGrpLvl;
	}

	public void setByrGrpLvl(List<Object[]> byrGrpLvl) {
		this.byrGrpLvl = byrGrpLvl;
	}

	public B000028ParamOutput getObjB000028ParamOutput() {
		return objB000028ParamOutput;
	}

	public void setObjB000028ParamOutput(B000028ParamOutput objB000028ParamOutput) {
		this.objB000028ParamOutput = objB000028ParamOutput;
	}

	public List<Object[]> getCarSrsHrzn() {
		return carSrsHrzn;
	}

	public void setCarSrsHrzn(List<Object[]> carSrsHrzn) {
		this.carSrsHrzn = carSrsHrzn;
	}
	
	
	
	}
	
