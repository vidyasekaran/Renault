package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TRN_MNTHLY_ORDR_ERR_IF database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_ORDR_ERR_IF")
@NamedQuery(name="TrnMnthlyOrdrErrIf.findAll", query="SELECT t FROM TrnMnthlyOrdrErrIf t")
public class TrnMnthlyOrdrErrIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnMnthlyOrdrErrIfPK id;

	@Column(name="ABLSH_DT")
	private String ablshDt;

	@Column(name="ABLSH_MNTH")
	private String ablshMnth;

	@Column(name="ABLSH_PRD")
	private String ablshPrd;

	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;

	@Column(name="ADPT_DT")
	private String adptDt;

	@Column(name="ADPT_PRD")
	private String adptPrd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	private String diff;

	@Column(name="ERR_CD")
	private String errCd;

	@Column(name="ERR_MSG")
	private String errMsg;

	@Column(name="EXPCTD_QTY")
	private String expctdQty;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="OCF_DESC")
	private String ocfDesc;

	@Column(name="OCF_FEAT_CD")
	private String ocfFeatCd;

	@Column(name="OCF_LMT")
	private String ocfLmt;

	@Column(name="OCF_SHRT_DESC")
	private String ocfShrtDesc;

	@Column(name="OCF_SIGN")
	private String ocfSign;

	@Column(name="OCF_USG")
	private String ocfUsg;

	@Column(name="ORDR_SIGN")
	private String ordrSign;

	@Column(name="ORDRTK_PRD")
	private String ordrtkPrd;

	@Column(name="ORDRTK_PRD_TYPE")
	private String ordrtkPrdType;

	@Column(name="PACK_CD")
	private String packCd;

	private String por;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="PROD_PRD")
	private String prodPrd;

	@Column(name="PROD_PRD_TYPE")
	private String prodPrdType;

	private String qty;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	private String variance;

	public TrnMnthlyOrdrErrIf() {
	}

	public TrnMnthlyOrdrErrIfPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyOrdrErrIfPK id) {
		this.id = id;
	}

	public String getAblshDt() {
		return this.ablshDt;
	}

	public void setAblshDt(String ablshDt) {
		this.ablshDt = ablshDt;
	}

	public String getAblshMnth() {
		return this.ablshMnth;
	}

	public void setAblshMnth(String ablshMnth) {
		this.ablshMnth = ablshMnth;
	}

	public String getAblshPrd() {
		return this.ablshPrd;
	}

	public void setAblshPrd(String ablshPrd) {
		this.ablshPrd = ablshPrd;
	}

	public String getAddSpecCd() {
		return this.addSpecCd;
	}

	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	public String getAdptDt() {
		return this.adptDt;
	}

	public void setAdptDt(String adptDt) {
		this.adptDt = adptDt;
	}

	public String getAdptPrd() {
		return this.adptPrd;
	}

	public void setAdptPrd(String adptPrd) {
		this.adptPrd = adptPrd;
	}

	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getBuyerCd() {
		return this.buyerCd;
	}

	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getDiff() {
		return this.diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getErrCd() {
		return this.errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getExpctdQty() {
		return this.expctdQty;
	}

	public void setExpctdQty(String expctdQty) {
		this.expctdQty = expctdQty;
	}

	public String getExtClrCd() {
		return this.extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getOcfDesc() {
		return this.ocfDesc;
	}

	public void setOcfDesc(String ocfDesc) {
		this.ocfDesc = ocfDesc;
	}

	public String getOcfFeatCd() {
		return this.ocfFeatCd;
	}

	public void setOcfFeatCd(String ocfFeatCd) {
		this.ocfFeatCd = ocfFeatCd;
	}

	public String getOcfLmt() {
		return this.ocfLmt;
	}

	public void setOcfLmt(String ocfLmt) {
		this.ocfLmt = ocfLmt;
	}

	public String getOcfShrtDesc() {
		return this.ocfShrtDesc;
	}

	public void setOcfShrtDesc(String ocfShrtDesc) {
		this.ocfShrtDesc = ocfShrtDesc;
	}

	public String getOcfSign() {
		return this.ocfSign;
	}

	public void setOcfSign(String ocfSign) {
		this.ocfSign = ocfSign;
	}

	public String getOcfUsg() {
		return this.ocfUsg;
	}

	public void setOcfUsg(String ocfUsg) {
		this.ocfUsg = ocfUsg;
	}

	public String getOrdrSign() {
		return this.ordrSign;
	}

	public void setOrdrSign(String ordrSign) {
		this.ordrSign = ordrSign;
	}

	public String getOrdrtkPrd() {
		return this.ordrtkPrd;
	}

	public void setOrdrtkPrd(String ordrtkPrd) {
		this.ordrtkPrd = ordrtkPrd;
	}

	public String getOrdrtkPrdType() {
		return this.ordrtkPrdType;
	}

	public void setOrdrtkPrdType(String ordrtkPrdType) {
		this.ordrtkPrdType = ordrtkPrdType;
	}

	public String getPackCd() {
		return this.packCd;
	}

	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	public String getPor() {
		return this.por;
	}

	public void setPor(String por) {
		this.por = por;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public String getProdPrd() {
		return this.prodPrd;
	}

	public void setProdPrd(String prodPrd) {
		this.prodPrd = prodPrd;
	}

	public String getProdPrdType() {
		return this.prodPrdType;
	}

	public void setProdPrdType(String prodPrdType) {
		this.prodPrdType = prodPrdType;
	}

	public String getQty() {
		return this.qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	public String getVariance() {
		return this.variance;
	}

	public void setVariance(String variance) {
		this.variance = variance;
	}

}