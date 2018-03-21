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
 * This class is used to hold the attributes required to generate Weekly Ocf Breach Report
 * @author z015847
 *
 */
public class B000040WklyOcfBreachReport 
{

	/**
	 * P0005.3.1 Por Cd
	 */
	private String porCd;
		
	/**
	 * P0005.3.1 Production Month
	 */
	private String prodMnth;
	
	/**
	 * P0005.3.1 Production Week No
	 */
	private String prodWeekNo;
	
	/**
	 * P0005.3.1 Car Series
	 */
	private String carSrs;
	
	/**
	 * P0005.3.1 Buyer Code Group
	 */
	private String byrGrpCd;
	
	
	/**
	 * P0005.3.1 OCF Region Code 
	 */
	private String ocfRgnCd;

	
	/**
	 * P0005.3.1 OCF Region Code 
	 */
	private String ocfByrGrpCd;
	
	/**
	 * P0005.3.1 OCF Freame Code
	 */
	private String ocfFrameCd;
	
	/**
	 * P0005.3.1 Feature Code
	 */
	private String featureCd;
	
	/**
	 * P0005.3.1 Feature Long Description
	 */
	private String featureLongDesc;
	
	/**
	 * P0005.3.1 Feature Short Description
	 */
	private String featureShortDesc;
	
	/**
	 * P0005.3.1 OCF Usage Quantity
	 */
	private String ocfUsageQty;
	
	/**
	 * P0005.3.1 Plant Code
	 */
	private String plantCd;
	
	/**
	 * P0005.3.1 Line Code
	 */
	private String lineClass;

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

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

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getByrGrpCd() {
		return byrGrpCd;
	}

	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	public String getOcfByrGrpCd() {
		return ocfByrGrpCd;
	}

	public void setOcfByrGrpCd(String ocfByrGrpCd) {
		this.ocfByrGrpCd = ocfByrGrpCd;
	}

	public String getOcfFrameCd() {
		return ocfFrameCd;
	}

	public void setOcfFrameCd(String ocfFrameCd) {
		this.ocfFrameCd = ocfFrameCd;
	}

	public String getFeatureCd() {
		return featureCd;
	}

	public void setFeatureCd(String featureCd) {
		this.featureCd = featureCd;
	}

	public String getFeatureLongDesc() {
		return featureLongDesc;
	}

	public void setFeatureLongDesc(String featureLongDesc) {
		this.featureLongDesc = featureLongDesc;
	}

	public String getFeatureShortDesc() {
		return featureShortDesc;
	}

	public void setFeatureShortDesc(String featureShortDesc) {
		this.featureShortDesc = featureShortDesc;
	}

	public String getOcfUsageQty() {
		return ocfUsageQty;
	}

	public void setOcfUsageQty(String ocfUsageQty) {
		this.ocfUsageQty = ocfUsageQty;
	}

	public String getPlantCd() {
		return plantCd;
	}

	public void setPlantCd(String plantCd) {
		this.plantCd = plantCd;
	}

	public String getLineClass() {
		return lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}
	

}