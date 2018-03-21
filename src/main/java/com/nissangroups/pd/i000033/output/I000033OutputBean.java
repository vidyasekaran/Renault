/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000033
 * Module          : OR ORDERING					
 * Process Outline : Send Monthly Order Error Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000033.output;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the TRN_MNTHLY_ORDR_ERR_IF database table.
 * 
 */
@Entity
public class I000033OutputBean implements Serializable {
	private static final long serialVersionUID = 1L;

	 /** Output parameter ROW NUM */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/* Output parameter POR */
	@Column(name = "POR")
	private String por;

	/* Output parameter AblshDt */
	@Column(name = "ABLSH_DT")
	private String ablshDt;

	/* Output parameter Ablsh_mnth */
	@Column(name = "ABLSH_MNTH")
	private String ablshMnth;

	/* Output parameter ablsh prd */
	@Column(name = "ABLSH_PRD")
	private String ablshPrd;

	/* Output parameter add spec cd */
	@Column(name = "ADD_SPEC_CD")
	private String addSpecCd;

	/* Output parameter adpt dt*/
	@Column(name = "ADPT_DT")
	private String adptDt;

	/* Output parameter Adpt prd*/
	@Column(name = "ADPT_PRD")
	private String adptPrd;

	/* Output parameter appld mdl cd*/
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;

	/* Output parameter buyer cd*/
	@Column(name = "BUYER_CD")
	private String buyerCd;

	/* Output parameter buyer grp cd*/
	@Column(name = "BUYER_GRP_CD")
	private String buyerGrpCd;

	/* Output parameter car srs */
	@Column(name = "CAR_SRS")
	private String carSrs;

	private String diff;

	/* Output parameter err cd */
	@Column(name = "ERR_CD")
	private String errCd;

	/* Output parameter err msg */
	@Column(name = "ERR_MSG")
	private String errMsg;

	/* Output parameter expctd qty */
	@Column(name = "EXPCTD_QTY")
	private String expctdQty;

	/* Output parameter ext clr cd*/
	@Column(name = "EXT_CLR_CD")
	private String extClrCd;

	/* Output parameter int clr cd*/
	@Column(name = "INT_CLR_CD")
	private String intClrCd;

	/* Output parameter lcl prod ordr no*/
	@Column(name = "LCL_PROD_ORDR_NO")
	private String lclProdOrdrNo;

	/* Output parameter line class */
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/* Output parameter ocf desc*/
	@Column(name = "OCF_DESC")
	private String ocfDesc;

	/* Output parameter ocf feat cd */
	@Column(name = "OCF_FEAT_CD")
	private String ocfFeatCd;

	/* Output parameter ofc lmt */
	@Column(name = "OCF_LMT")
	private String ocfLmt;

	/* Output parameter ocf shrt desc*/
	@Column(name = "OCF_SHRT_DESC")
	private String ocfShrtDesc;

	/* Output parameter ocf sign*/
	@Column(name = "OCF_SIGN")
	private String ocfSign;

	/* Output parameter ocf usg*/
	@Column(name = "OCF_USG")
	private String ocfUsg;

	/* Output parameter ordr sign*/
	@Column(name = "ORDR_SIGN")
	private String ordrSign;

	/* Output parameter ordrtk prd*/
	@Column(name = "ORDRTK_PRD")
	private String ordrtkPrd;

	/* Output parameter ordrtk prd type*/
	@Column(name = "ORDRTK_PRD_TYPE")
	private String ordrtkPrdType;

	/* Output parameter pack cd*/
	@Column(name = "PACK_CD")
	private String packCd;

	/* Output parameter Prod plant cd*/
	@Column(name = "PROD_PLNT_CD")
	private String prodPlantCd;

	/* Output parameter POt */
	private String pot;

	/* Output parameter Prod ordr no*/
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;

	/* Output parameter prod prd*/
	@Column(name = "PROD_PRD")
	private String prodPrd;

	/* Output parameter prod prd type*/
	@Column(name = "PROD_PRD_TYPE")
	private String prodPrdType;

	/* Output parameter qty */
	private String qty;
	/* Output parameter spec destn cd*/
	@Column(name = "SPEC_DESTN_CD")
	private String specDestnCd;

	/* Output parameter variance*/
	private String variance;

	public I000033OutputBean() {
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
	 * Get the por
	 *
	 * @return the por
	 */
	public String getPor() {
		return por;
	}

	/**
	 * Set the por
	 *
	 * @param por the por to set
	 */
	public void setPor(String por) {
		this.por = por;
	}

	/**
	 * Get the ablshDt
	 *
	 * @return the ablshDt
	 */
	public String getAblshDt() {
		return ablshDt;
	}

	/**
	 * Set the ablshDt
	 *
	 * @param ablshDt the ablshDt to set
	 */
	public void setAblshDt(String ablshDt) {
		this.ablshDt = ablshDt;
	}

	/**
	 * Get the ablshMnth
	 *
	 * @return the ablshMnth
	 */
	public String getAblshMnth() {
		return ablshMnth;
	}

	/**
	 * Set the ablshMnth
	 *
	 * @param ablshMnth the ablshMnth to set
	 */
	public void setAblshMnth(String ablshMnth) {
		this.ablshMnth = ablshMnth;
	}

	/**
	 * Get the ablshPrd
	 *
	 * @return the ablshPrd
	 */
	public String getAblshPrd() {
		return ablshPrd;
	}

	/**
	 * Set the ablshPrd
	 *
	 * @param ablshPrd the ablshPrd to set
	 */
	public void setAblshPrd(String ablshPrd) {
		this.ablshPrd = ablshPrd;
	}

	/**
	 * Get the addSpecCd
	 *
	 * @return the addSpecCd
	 */
	public String getAddSpecCd() {
		return addSpecCd;
	}

	/**
	 * Set the addSpecCd
	 *
	 * @param addSpecCd the addSpecCd to set
	 */
	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	/**
	 * Get the adptDt
	 *
	 * @return the adptDt
	 */
	public String getAdptDt() {
		return adptDt;
	}

	/**
	 * Set the adptDt
	 *
	 * @param adptDt the adptDt to set
	 */
	public void setAdptDt(String adptDt) {
		this.adptDt = adptDt;
	}

	/**
	 * Get the adptPrd
	 *
	 * @return the adptPrd
	 */
	public String getAdptPrd() {
		return adptPrd;
	}

	/**
	 * Set the adptPrd
	 *
	 * @param adptPrd the adptPrd to set
	 */
	public void setAdptPrd(String adptPrd) {
		this.adptPrd = adptPrd;
	}

	/**
	 * Get the appldMdlCd
	 *
	 * @return the appldMdlCd
	 */
	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	/**
	 * Set the appldMdlCd
	 *
	 * @param appldMdlCd the appldMdlCd to set
	 */
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	/**
	 * Get the buyerCd
	 *
	 * @return the buyerCd
	 */
	public String getBuyerCd() {
		return buyerCd;
	}

	/**
	 * Set the buyerCd
	 *
	 * @param buyerCd the buyerCd to set
	 */
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	/**
	 * Get the buyerGrpCd
	 *
	 * @return the buyerGrpCd
	 */
	public String getBuyerGrpCd() {
		return buyerGrpCd;
	}

	/**
	 * Set the buyerGrpCd
	 *
	 * @param buyerGrpCd the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	/**
	 * Get the carSrs
	 *
	 * @return the carSrs
	 */
	public String getCarSrs() {
		return carSrs;
	}

	/**
	 * Set the carSrs
	 *
	 * @param carSrs the carSrs to set
	 */
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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
	 * Get the errCd
	 *
	 * @return the errCd
	 */
	public String getErrCd() {
		return errCd;
	}

	/**
	 * Set the errCd
	 *
	 * @param errCd the errCd to set
	 */
	public void setErrCd(String errCd) {
		this.errCd = errCd;
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
	 * Get the expctdQty
	 *
	 * @return the expctdQty
	 */
	public String getExpctdQty() {
		return expctdQty;
	}

	/**
	 * Set the expctdQty
	 *
	 * @param expctdQty the expctdQty to set
	 */
	public void setExpctdQty(String expctdQty) {
		this.expctdQty = expctdQty;
	}

	/**
	 * Get the extClrCd
	 *
	 * @return the extClrCd
	 */
	public String getExtClrCd() {
		return extClrCd;
	}

	/**
	 * Set the extClrCd
	 *
	 * @param extClrCd the extClrCd to set
	 */
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	/**
	 * Get the intClrCd
	 *
	 * @return the intClrCd
	 */
	public String getIntClrCd() {
		return intClrCd;
	}

	/**
	 * Set the intClrCd
	 *
	 * @param intClrCd the intClrCd to set
	 */
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
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
	 * Get the ocfDesc
	 *
	 * @return the ocfDesc
	 */
	public String getOcfDesc() {
		return ocfDesc;
	}

	/**
	 * Set the ocfDesc
	 *
	 * @param ocfDesc the ocfDesc to set
	 */
	public void setOcfDesc(String ocfDesc) {
		this.ocfDesc = ocfDesc;
	}

	/**
	 * Get the ocfFeatCd
	 *
	 * @return the ocfFeatCd
	 */
	public String getOcfFeatCd() {
		return ocfFeatCd;
	}

	/**
	 * Set the ocfFeatCd
	 *
	 * @param ocfFeatCd the ocfFeatCd to set
	 */
	public void setOcfFeatCd(String ocfFeatCd) {
		this.ocfFeatCd = ocfFeatCd;
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
	 * Get the ocfShrtDesc
	 *
	 * @return the ocfShrtDesc
	 */
	public String getOcfShrtDesc() {
		return ocfShrtDesc;
	}

	/**
	 * Set the ocfShrtDesc
	 *
	 * @param ocfShrtDesc the ocfShrtDesc to set
	 */
	public void setOcfShrtDesc(String ocfShrtDesc) {
		this.ocfShrtDesc = ocfShrtDesc;
	}

	/**
	 * Get the ocfSign
	 *
	 * @return the ocfSign
	 */
	public String getOcfSign() {
		return ocfSign;
	}

	/**
	 * Set the ocfSign
	 *
	 * @param ocfSign the ocfSign to set
	 */
	public void setOcfSign(String ocfSign) {
		this.ocfSign = ocfSign;
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
	 * Get the ordrSign
	 *
	 * @return the ordrSign
	 */
	public String getOrdrSign() {
		return ordrSign;
	}

	/**
	 * Set the ordrSign
	 *
	 * @param ordrSign the ordrSign to set
	 */
	public void setOrdrSign(String ordrSign) {
		this.ordrSign = ordrSign;
	}

	/**
	 * Get the ordrtkPrd
	 *
	 * @return the ordrtkPrd
	 */
	public String getOrdrtkPrd() {
		return ordrtkPrd;
	}

	/**
	 * Set the ordrtkPrd
	 *
	 * @param ordrtkPrd the ordrtkPrd to set
	 */
	public void setOrdrtkPrd(String ordrtkPrd) {
		this.ordrtkPrd = ordrtkPrd;
	}

	/**
	 * Get the ordrtkPrdType
	 *
	 * @return the ordrtkPrdType
	 */
	public String getOrdrtkPrdType() {
		return ordrtkPrdType;
	}

	/**
	 * Set the ordrtkPrdType
	 *
	 * @param ordrtkPrdType the ordrtkPrdType to set
	 */
	public void setOrdrtkPrdType(String ordrtkPrdType) {
		this.ordrtkPrdType = ordrtkPrdType;
	}

	/**
	 * Get the packCd
	 *
	 * @return the packCd
	 */
	public String getPackCd() {
		return packCd;
	}

	/**
	 * Set the packCd
	 *
	 * @param packCd the packCd to set
	 */
	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	/**
	 * Get the prodPlantCd
	 *
	 * @return the prodPlantCd
	 */
	public String getProdPlantCd() {
		return prodPlantCd;
	}

	/**
	 * Set the prodPlantCd
	 *
	 * @param prodPlantCd the prodPlantCd to set
	 */
	public void setProdPlantCd(String prodPlantCd) {
		this.prodPlantCd = prodPlantCd;
	}

	/**
	 * Get the pot
	 *
	 * @return the pot
	 */
	public String getPot() {
		return pot;
	}

	/**
	 * Set the pot
	 *
	 * @param pot the pot to set
	 */
	public void setPot(String pot) {
		this.pot = pot;
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
	 * Get the prodPrdType
	 *
	 * @return the prodPrdType
	 */
	public String getProdPrdType() {
		return prodPrdType;
	}

	/**
	 * Set the prodPrdType
	 *
	 * @param prodPrdType the prodPrdType to set
	 */
	public void setProdPrdType(String prodPrdType) {
		this.prodPrdType = prodPrdType;
	}

	/**
	 * Get the qty
	 *
	 * @return the qty
	 */
	public String getQty() {
		return qty;
	}

	/**
	 * Set the qty
	 *
	 * @param qty the qty to set
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

	/**
	 * Get the specDestnCd
	 *
	 * @return the specDestnCd
	 */
	public String getSpecDestnCd() {
		return specDestnCd;
	}

	/**
	 * Set the specDestnCd
	 *
	 * @param specDestnCd the specDestnCd to set
	 */
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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

	

}