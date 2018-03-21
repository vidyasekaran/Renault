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


/**
 * This class is used to hold the attributes required to generate Weekly Order Spec Error Report
 * @author z015847
 *
 */
public class B000040WklyOrderSpecErrorReport 
{

	/**
	 * P0005.1 Order Take Base Period
	 */
	private String orderTakeBasePeriod;
	
	/**
	 * P0005.1 Production Month
	 */
	private String prodMnth;
	
	/**
	 * P0005.1 OCF Region Code 
	 */
	private String ocfRgnCd;
	
	/**
	 * P0005.1 MC Region CD
	 */
	private String mcRgnCd;
	
	/**
	 * P0005.1 Buyer Code Group
	 */
	private String byrGrpCd;
	
	
	/**
	 * P0005.1 Buyer Code 
	 */
	private String byrCd;
	
	
	/**
	 * P0005.1 Production Family Code 
	 */
	private String prodFmlyCd;
	
	/**
	 * P0005.1 Car Groupd Code
	 */
	private String carGrpCd;
	
	/**
	 * P0005.1 Car Series
	 */
	private String carSrs;
	
	/**
	 * P0005.1 End Item 
	 */
	private String endItem;
	
	/**
	 * P0005.1 Spec Destination Code 
	 */
	private String specDestnCd;
	
	/**
	 * P0005.1 Additional Spec Code 
	 */
	private String addtnlSpcCd;
	
	/**
	 * P0005.1 Pot Cd
	 */
	private String potCd;
	
	/**
	 *P0005.1 Sales Note
	 */
	private String slsNt;
	
	/**
	 * P0005.1 Color
	 */
	private String color;
	
	/**
	 * P0005.1 EIM Adopt Date
	 */
	private String eimAdptDate;
	

	/**
	 * P0005.1 EIM Abolish Date
	 */
	private String eimAbolishDate;
	
	
	/**
	 * P0005.1 Production Week No
	 */
	private String prodWeekNo;


	public String getOrderTakeBasePeriod() {
		return orderTakeBasePeriod;
	}


	public void setOrderTakeBasePeriod(String orderTakeBasePeriod) {
		this.orderTakeBasePeriod = orderTakeBasePeriod;
	}


	public String getProdMnth() {
		return prodMnth;
	}


	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}


	public String getOcfRgnCd() {
		return ocfRgnCd;
	}


	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}


	public String getMcRgnCd() {
		return mcRgnCd;
	}


	public void setMcRgnCd(String mcRgnCd) {
		this.mcRgnCd = mcRgnCd;
	}


	public String getByrGrpCd() {
		return byrGrpCd;
	}


	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}


	public String getByrCd() {
		return byrCd;
	}


	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}


	public String getProdFmlyCd() {
		return prodFmlyCd;
	}


	public void setProdFmlyCd(String prodFmlyCd) {
		this.prodFmlyCd = prodFmlyCd;
	}


	public String getCarGrpCd() {
		return carGrpCd;
	}


	public void setCarGrpCd(String carGrpCd) {
		this.carGrpCd = carGrpCd;
	}


	public String getCarSrs() {
		return carSrs;
	}


	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}


	public String getEndItem() {
		return endItem;
	}


	public void setEndItem(String endItem) {
		this.endItem = endItem;
	}


	public String getSpecDestnCd() {
		return specDestnCd;
	}


	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}


	public String getAddtnlSpcCd() {
		return addtnlSpcCd;
	}


	public void setAddtnlSpcCd(String addtnlSpcCd) {
		this.addtnlSpcCd = addtnlSpcCd;
	}


	public String getPotCd() {
		return potCd;
	}


	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}


	public String getSlsNt() {
		return slsNt;
	}


	public void setSlsNt(String slsNt) {
		this.slsNt = slsNt;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getEimAdptDate() {
		return eimAdptDate;
	}


	public void setEimAdptDate(String eimAdptDate) {
		this.eimAdptDate = eimAdptDate;
	}


	public String getEimAbolishDate() {
		return eimAbolishDate;
	}


	public void setEimAbolishDate(String eimAbolishDate) {
		this.eimAbolishDate = eimAbolishDate;
	}


	public String getProdWeekNo() {
		return prodWeekNo;
	}


	public void setProdWeekNo(String prodWeekNo) {
		this.prodWeekNo = prodWeekNo;
	}
	
}