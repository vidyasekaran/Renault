/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000065
 * Module          : OR ORDERING					
 * Process Outline : Interface to Send Weekly Order Error Interface to NSC (Standard)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)              Initial Version
 *
 */
package com.nissangroups.pd.i000065.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the persistent class transfer data Model layer to database layer
 * (TRN_WEEKLY_ORDR_ERR_IF to Common interface data).
 * 
 */
@Entity
public class I000065OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Output variable rownum */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Output variable por cd */
	@Column(name = "POR_CD")
	private String porCd;

	/** Output variable order take production type */
	@Column(name = "ORDRTK_BASE_PRD_TYPE")
	private String ordertkBasePrdType;

	/** Output variable ordet take base production */
	@Column(name = "ORDRTK_BASE_PRD")
	private String ordertkBasePrd;

	/** Output variable production period type */
	@Column(name = "PROD_PRD_TYPE")
	private String prodType;

	/** Output variable production period*/
	@Column(name = "PROD_PRD")
	private String prodPrd;

	/** Output variable car series*/
	@Column(name = "CAR_SRS")
	private String carSeries;

	/** Output variable buyer group cd*/
	@Column(name = "BUYER_GRP_CD")
	private String buyerGrpCD;

	/** Output variable buyer cd*/
	@Column(name = "BUYER_CD")
	private String buyerCD;

	/** Output variable spel dest cd*/
	@Column(name = "SPEC_DESTN_CD")
	private String specDestination;

	/** Output variable feat lang desc */
	@Column(name = "FEAT_LNG_DESC")
	private String featLngDesc;

	/** Output variable external color cd */
	@Column(name = "EXT_CLR_CD")
	private String extclrCD;

	/** Output variable internal color cd */
	@Column(name = "INT_CLR_CD")
	private String intClrCD;

	/** Output variable add spec cd*/
	@Column(name = "ADD_SPEC_CD")
	private String addSpecCD;

	/** Output variable pot cd */
	@Column(name = "POT_CD")
	private String potCD;

	/** Output variable prod plant cd */
	@Column(name = "PROD_PLANT_CD")
	private String prodPlantCD;

	/** Output variable line class*/
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/** Output variable local prod order no*/
	@Column(name = "LCL_PROD_ORDR_NO")
	private String lclProdOrdrNo;

	/** Output variable prod ordr no*/
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;
	/** Output variable ordr qty*/
	@Column(name = "ORDR_QTY")
	private String orderQty;

	/** Output variable exp qty */
	@Column(name = "EXP_QTY")
	private String expQty;

	/** Output variable sign*/
	@Column(name = "SIGN")
	private String sign;

	/** Output variable variance*/
	@Column(name = "VARIANCE")
	private String variance;

	/** Output variable ocf lmt*/
	@Column(name = "OCF_LMT")
	private String ocfLmt;

	/** Output variable oc usg*/
	@Column(name = "OCF_USG")
	private String ocfUsg;

	/** Output variable diff */
	@Column(name = "DIFF")
	private String diff;

	/** Output variable err cd */
	@Column(name = "ERR_CD")
	private String errCD;

	/** Output variable err msg */
	@Column(name = "ERR_MSG")
	private String errMsg;

	/** Output variable appld mdl cd*/
	@Column(name = "APPLD_MDL_CD")
	private String appliedModelCd;

	/** Output variable pack cd */
	@Column(name = "PACK_CD")
	private String packCD;

	/** Output variable eat cd*/
	@Column(name = "FEAT_CD")
	private String featureCd;

	/** Output variable abolisddate */
	@Column(name = "ABOLISHDATE")
	private String abolishDate;

	/** Output variable adoptdate*/
	@Column(name = "ADOPTDATE")
	private String adoptDate;

	/** Output variable adoptprd */
	@Column(name = "ADOPTPRD")
	private String adoptPrd;

	/** Output variable abolishprd */
	@Column(name = "ABOLISHPRD")
	private String abolishPrd;

	/** Output variable abolishmnth */
	@Column(name = "ABOLISHMNTH")
	private String abolishMnth;

	/** Output variable feat shrt desc*/
	@Column(name = "FEAT_SHRT_DESC")
	private String featureShortDescription;

	public I000065OutputBean() {

	}

	/**
	 * Get the rowNum
	 *
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * Set the rowNum
	 *
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * Get the porCd
	 *
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * Set the porCd
	 *
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * Get the ordertkBasePrdType
	 *
	 * @return the ordertkBasePrdType
	 */
	public String getOrdertkBasePrdType() {
		return ordertkBasePrdType;
	}

	/**
	 * Set the ordertkBasePrdType
	 *
	 * @param ordertkBasePrdType the ordertkBasePrdType to set
	 */
	public void setOrdertkBasePrdType(String ordertkBasePrdType) {
		this.ordertkBasePrdType = ordertkBasePrdType;
	}

	/**
	 * Get the ordertkBasePrd
	 *
	 * @return the ordertkBasePrd
	 */
	public String getOrdertkBasePrd() {
		return ordertkBasePrd;
	}

	/**
	 * Set the ordertkBasePrd
	 *
	 * @param ordertkBasePrd the ordertkBasePrd to set
	 */
	public void setOrdertkBasePrd(String ordertkBasePrd) {
		this.ordertkBasePrd = ordertkBasePrd;
	}

	/**
	 * Get the prodType
	 *
	 * @return the prodType
	 */
	public String getProdType() {
		return prodType;
	}

	/**
	 * Set the prodType
	 *
	 * @param prodType the prodType to set
	 */
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	/**
	 * Get the prodPrd
	 *
	 * @return the prodPrd
	 */
	public String getProdPrd() {
		return prodPrd;
	}

	/**
	 * Set the prodPrd
	 *
	 * @param prodPrd the prodPrd to set
	 */
	public void setProdPrd(String prodPrd) {
		this.prodPrd = prodPrd;
	}

	/**
	 * Get the carSeries
	 *
	 * @return the carSeries
	 */
	public String getCarSeries() {
		return carSeries;
	}

	/**
	 * Set the carSeries
	 *
	 * @param carSeries the carSeries to set
	 */
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	/**
	 * Get the buyerGrpCD
	 *
	 * @return the buyerGrpCD
	 */
	public String getBuyerGrpCD() {
		return buyerGrpCD;
	}

	/**
	 * Set the buyerGrpCD
	 *
	 * @param buyerGrpCD the buyerGrpCD to set
	 */
	public void setBuyerGrpCD(String buyerGrpCD) {
		this.buyerGrpCD = buyerGrpCD;
	}

	/**
	 * Get the buyerCD
	 *
	 * @return the buyerCD
	 */
	public String getBuyerCD() {
		return buyerCD;
	}

	/**
	 * Set the buyerCD
	 *
	 * @param buyerCD the buyerCD to set
	 */
	public void setBuyerCD(String buyerCD) {
		this.buyerCD = buyerCD;
	}

	/**
	 * Get the specDestination
	 *
	 * @return the specDestination
	 */
	public String getSpecDestination() {
		return specDestination;
	}

	/**
	 * Set the specDestination
	 *
	 * @param specDestination the specDestination to set
	 */
	public void setSpecDestination(String specDestination) {
		this.specDestination = specDestination;
	}

	/**
	 * Get the featLngDesc
	 *
	 * @return the featLngDesc
	 */
	public String getFeatLngDesc() {
		return featLngDesc;
	}

	/**
	 * Set the featLngDesc
	 *
	 * @param featLngDesc the featLngDesc to set
	 */
	public void setFeatLngDesc(String featLngDesc) {
		this.featLngDesc = featLngDesc;
	}

	/**
	 * Get the extclrCD
	 *
	 * @return the extclrCD
	 */
	public String getExtclrCD() {
		return extclrCD;
	}

	/**
	 * Set the extclrCD
	 *
	 * @param extclrCD the extclrCD to set
	 */
	public void setExtclrCD(String extclrCD) {
		this.extclrCD = extclrCD;
	}

	/**
	 * Get the intClrCD
	 *
	 * @return the intClrCD
	 */
	public String getIntClrCD() {
		return intClrCD;
	}

	/**
	 * Set the intClrCD
	 *
	 * @param intClrCD the intClrCD to set
	 */
	public void setIntClrCD(String intClrCD) {
		this.intClrCD = intClrCD;
	}

	/**
	 * Get the addSpecCD
	 *
	 * @return the addSpecCD
	 */
	public String getAddSpecCD() {
		return addSpecCD;
	}

	/**
	 * Set the addSpecCD
	 *
	 * @param addSpecCD the addSpecCD to set
	 */
	public void setAddSpecCD(String addSpecCD) {
		this.addSpecCD = addSpecCD;
	}

	/**
	 * Get the potCD
	 *
	 * @return the potCD
	 */
	public String getPotCD() {
		return potCD;
	}

	/**
	 * Set the potCD
	 *
	 * @param potCD the potCD to set
	 */
	public void setPotCD(String potCD) {
		this.potCD = potCD;
	}

	/**
	 * Get the prodPlantCD
	 *
	 * @return the prodPlantCD
	 */
	public String getProdPlantCD() {
		return prodPlantCD;
	}

	/**
	 * Set the prodPlantCD
	 *
	 * @param prodPlantCD the prodPlantCD to set
	 */
	public void setProdPlantCD(String prodPlantCD) {
		this.prodPlantCD = prodPlantCD;
	}

	/**
	 * Get the lineClass
	 *
	 * @return the lineClass
	 */
	public String getLineClass() {
		return lineClass;
	}

	/**
	 * Set the lineClass
	 *
	 * @param lineClass the lineClass to set
	 */
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	/**
	 * Get the lclProdOrdrNo
	 *
	 * @return the lclProdOrdrNo
	 */
	public String getLclProdOrdrNo() {
		return lclProdOrdrNo;
	}

	/**
	 * Set the lclProdOrdrNo
	 *
	 * @param lclProdOrdrNo the lclProdOrdrNo to set
	 */
	public void setLclProdOrdrNo(String lclProdOrdrNo) {
		this.lclProdOrdrNo = lclProdOrdrNo;
	}

	/**
	 * Get the prodOrdrNo
	 *
	 * @return the prodOrdrNo
	 */
	public String getProdOrdrNo() {
		return prodOrdrNo;
	}

	/**
	 * Set the prodOrdrNo
	 *
	 * @param prodOrdrNo the prodOrdrNo to set
	 */
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	/**
	 * Get the orderQty
	 *
	 * @return the orderQty
	 */
	public String getOrderQty() {
		return orderQty;
	}

	/**
	 * Set the orderQty
	 *
	 * @param orderQty the orderQty to set
	 */
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	/**
	 * Get the expQty
	 *
	 * @return the expQty
	 */
	public String getExpQty() {
		return expQty;
	}

	/**
	 * Set the expQty
	 *
	 * @param expQty the expQty to set
	 */
	public void setExpQty(String expQty) {
		this.expQty = expQty;
	}

	/**
	 * Get the sign
	 *
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Set the sign
	 *
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * Get the variance
	 *
	 * @return the variance
	 */
	public String getVariance() {
		return variance;
	}

	/**
	 * Set the variance
	 *
	 * @param variance the variance to set
	 */
	public void setVariance(String variance) {
		this.variance = variance;
	}

	/**
	 * Get the ocfLmt
	 *
	 * @return the ocfLmt
	 */
	public String getOcfLmt() {
		return ocfLmt;
	}

	/**
	 * Set the ocfLmt
	 *
	 * @param ocfLmt the ocfLmt to set
	 */
	public void setOcfLmt(String ocfLmt) {
		this.ocfLmt = ocfLmt;
	}

	/**
	 * Get the ocfUsg
	 *
	 * @return the ocfUsg
	 */
	public String getOcfUsg() {
		return ocfUsg;
	}

	/**
	 * Set the ocfUsg
	 *
	 * @param ocfUsg the ocfUsg to set
	 */
	public void setOcfUsg(String ocfUsg) {
		this.ocfUsg = ocfUsg;
	}

	/**
	 * Get the diff
	 *
	 * @return the diff
	 */
	public String getDiff() {
		return diff;
	}

	/**
	 * Set the diff
	 *
	 * @param diff the diff to set
	 */
	public void setDiff(String diff) {
		this.diff = diff;
	}

	/**
	 * Get the errCD
	 *
	 * @return the errCD
	 */
	public String getErrCD() {
		return errCD;
	}

	/**
	 * Set the errCD
	 *
	 * @param errCD the errCD to set
	 */
	public void setErrCD(String errCD) {
		this.errCD = errCD;
	}

	/**
	 * Get the errMsg
	 *
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * Set the errMsg
	 *
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * Get the appliedModelCd
	 *
	 * @return the appliedModelCd
	 */
	public String getAppliedModelCd() {
		return appliedModelCd;
	}

	/**
	 * Set the appliedModelCd
	 *
	 * @param appliedModelCd the appliedModelCd to set
	 */
	public void setAppliedModelCd(String appliedModelCd) {
		this.appliedModelCd = appliedModelCd;
	}

	/**
	 * Get the packCD
	 *
	 * @return the packCD
	 */
	public String getPackCD() {
		return packCD;
	}

	/**
	 * Set the packCD
	 *
	 * @param packCD the packCD to set
	 */
	public void setPackCD(String packCD) {
		this.packCD = packCD;
	}

	/**
	 * Get the featureCd
	 *
	 * @return the featureCd
	 */
	public String getFeatureCd() {
		return featureCd;
	}

	/**
	 * Set the featureCd
	 *
	 * @param featureCd the featureCd to set
	 */
	public void setFeatureCd(String featureCd) {
		this.featureCd = featureCd;
	}

	/**
	 * Get the abolishDate
	 *
	 * @return the abolishDate
	 */
	public String getAbolishDate() {
		return abolishDate;
	}

	/**
	 * Set the abolishDate
	 *
	 * @param abolishDate the abolishDate to set
	 */
	public void setAbolishDate(String abolishDate) {
		this.abolishDate = abolishDate;
	}

	/**
	 * Get the adoptDate
	 *
	 * @return the adoptDate
	 */
	public String getAdoptDate() {
		return adoptDate;
	}

	/**
	 * Set the adoptDate
	 *
	 * @param adoptDate the adoptDate to set
	 */
	public void setAdoptDate(String adoptDate) {
		this.adoptDate = adoptDate;
	}

	/**
	 * Get the adoptPrd
	 *
	 * @return the adoptPrd
	 */
	public String getAdoptPrd() {
		return adoptPrd;
	}

	/**
	 * Set the adoptPrd
	 *
	 * @param adoptPrd the adoptPrd to set
	 */
	public void setAdoptPrd(String adoptPrd) {
		this.adoptPrd = adoptPrd;
	}

	/**
	 * Get the abolishPrd
	 *
	 * @return the abolishPrd
	 */
	public String getAbolishPrd() {
		return abolishPrd;
	}

	/**
	 * Set the abolishPrd
	 *
	 * @param abolishPrd the abolishPrd to set
	 */
	public void setAbolishPrd(String abolishPrd) {
		this.abolishPrd = abolishPrd;
	}

	/**
	 * Get the abolishMnth
	 *
	 * @return the abolishMnth
	 */
	public String getAbolishMnth() {
		return abolishMnth;
	}

	/**
	 * Set the abolishMnth
	 *
	 * @param abolishMnth the abolishMnth to set
	 */
	public void setAbolishMnth(String abolishMnth) {
		this.abolishMnth = abolishMnth;
	}

	/**
	 * Get the featureShortDescription
	 *
	 * @return the featureShortDescription
	 */
	public String getFeatureShortDescription() {
		return featureShortDescription;
	}

	/**
	 * Set the featureShortDescription
	 *
	 * @param featureShortDescription the featureShortDescription to set
	 */
	public void setFeatureShortDescription(String featureShortDescription) {
		this.featureShortDescription = featureShortDescription;
	}

	
}
