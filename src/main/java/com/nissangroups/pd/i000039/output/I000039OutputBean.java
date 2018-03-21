/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000039
 * Module                 : OR Ordering		
 * Process Outline     	  : Send Monthly Production Order Interface to Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z014135(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000039.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
* The persistent class for Extract Monthly Production Order From Monthly Production Order Transaction table.
* 
 * @author z014135
 *
 */
@Entity
public class I000039OutputBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;
	
	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PROD_MNTH")
	private String prdMnth;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;
	

	@Column(name = "PROD_FMY_CD")
	private String prodFamlyCd;
	
	
	@Column(name = "CAR_SRS")
	private String carSrs;
	
	@Column(name = "Model_Code")
	private String modelCd;
	
	@Column(name = "EXT_CLR_CD")
	private String extClr;
	
	@Column(name = "INT_CLR_CD")
	private String intClr;
	
	@Column(name = "EX_NO")
	private String exNo;
	
	@Column(name = "SPEC_DESTN_CD")
	private String specDstnCd;
	
	
	@Column(name = "OCF_REGION_CD")
	private String ocfRegion;
	
	@Column(name = "PROD_SALES_SPEC")
	private String prodSalesSpec;
	
	@Column(name = "DEALER_LST")
	private String dealrLst;
	
	@Column(name = "ORDR_QTY")
	private String ordrQty;
	
	
	@Column(name = "SLS_NOTE_NO")
	private String slsNote;
	
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpec;
	
	@Column(name = "BUYER_CD")
	private String byrCd ;
	
	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOrdrtkBaseMnth() {
		return ordrtkBaseMnth;
	}

	public void setOrdrtkBaseMnth(String ordrtkBaseMnth) {
		this.ordrtkBaseMnth = ordrtkBaseMnth;
	}

	public String getPrdMnth() {
		return prdMnth;
	}

	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	public String getOseiId() {
		return oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getPotCd() {
		return potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdOrdrNo() {
		return prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}
		
	
	public String getProdFamlyCd() {
		return prodFamlyCd;
	}

	public void setProdFamlyCd(String prodFamlyCd) {
		this.prodFamlyCd = prodFamlyCd;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public String getExtClr() {
		return extClr;
	}

	public void setExtClr(String extClr) {
		this.extClr = extClr;
	}

	public String getIntClr() {
		return intClr;
	}

	public void setIntClr(String intClr) {
		this.intClr = intClr;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getSpecDstnCd() {
		return specDstnCd;
	}

	public void setSpecDstnCd(String specDstnCd) {
		this.specDstnCd = specDstnCd;
	}


	public String getOcfRegion() {
		return ocfRegion;
	}

	public void setOcfRegion(String ocfRegion) {
		this.ocfRegion = ocfRegion;
	}

	public String getProdSalesSpec() {
		return prodSalesSpec;
	}

	public void setProdSalesSpec(String prodSalesSpec) {
		this.prodSalesSpec = prodSalesSpec;
	}

	public String getDealrLst() {
		return dealrLst;
	}

	public void setDealrLst(String dealrLst) {
		this.dealrLst = dealrLst;
	}

	public String getOrdrQty() {
		return ordrQty;
	}

	public void setOrdrQty(String ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getSlsNote() {
		return slsNote;
	}

	public void setSlsNote(String slsNote) {
		this.slsNote = slsNote;
	}

	public String getAdtnlSpec() {
		return adtnlSpec;
	}

	public void setAdtnlSpec(String adtnlSpec) {
		this.adtnlSpec = adtnlSpec;
	}

	public String getByrCd() {
		return byrCd;
	}

	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}
	
	public long getRowNum() {
		return rowNum;
	}

	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}
	

}
