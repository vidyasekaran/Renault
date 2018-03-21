/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.bean;

/**
 * P0005.1 This Class IfErrorReport create error report header names
 * 
 * @author z015895
 */
public class IfErrorReport {

	/** The Input parameter POR_CD */
	private String porCd;

	/** The Input parameter ordrTakBasPrd */
	private String ordrTakBasPrd;

	/** The Input parameter prodPrd */
	private String prodPrd;

	/** The Input parameter carSrs */
	private String carSrs;

	/** The Input parameter ocfRegionCd */
	private String ocfRegionCd;

	/** The Input parameter ocfBuyerGrpCd */
	private String ocfBuyerGrpCd;

	/** The Input parameter ocfFrmeCd */
	private String ocfFrmeCd;

	/** The Input parameter featCd */
	private String featCd;

	/** The Input parameter ocfShortDesc */
	private String ocfShortDesc;

	/** The Input parameter ocfLmt */
	private String ocfLmt;

	/** The Input parameter errormsg */
	private String errorMsg;

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOrdrTakBasPrd() {
		return ordrTakBasPrd;
	}

	public void setOrdrTakBasPrd(String ordrTakBasPrd) {
		this.ordrTakBasPrd = ordrTakBasPrd;
	}

	public String getProdPrd() {
		return prodPrd;
	}

	public void setProdPrd(String prodPrd) {
		this.prodPrd = prodPrd;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOcfBuyerGrpCd() {
		return ocfBuyerGrpCd;
	}

	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	public String getOcfFrmeCd() {
		return ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public String getFeatCd() {
		return featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public String getOcfShortDesc() {
		return ocfShortDesc;
	}

	public void setOcfShortDesc(String ocfShortDesc) {
		this.ocfShortDesc = ocfShortDesc;
	}

	public String getOcfLmt() {
		return ocfLmt;
	}

	public void setOcfLmt(String ocfLmt) {
		this.ocfLmt = ocfLmt;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
