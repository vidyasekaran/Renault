/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000102
 * Module          : CM COMMON					
 * Process Outline : Send Logical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 23-12-2014  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000102.output;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is a persistent class which maps to Common interface data table.
 * 
 */
@Entity
public class I000102OutputBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** output parameter Row num */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** output parameter ADTNL_SPEC_CD */
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	/** output parameter APPLD_MDL_CD */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;

	/** output parameter BUYER_CD */
	@Column(name = "BUYER_CD")
	private String buyerCd;

	/** output parameter CAR_SRS */
	@Column(name = "CAR_SRS")
	private String carSrs;

	/** output parameter ETA_ADJUST_FLAG */
	@Column(name = "ETA_ADJUST_FLAG")
	private BigDecimal etaAdjustFlag;

	/** output parameter EX_NO */
	@Column(name = "EX_NO")
	private String exNo;

	/** output parameter EXT_CLR_CD */
	@Column(name = "EXT_CLR_CD")
	private String extClrCd;

	/** output parameter FRZN_TYPE_CD */
	@Column(name = "FRZN_TYPE_CD")
	private String frznTypeCd;

	/** output parameter INT_CLR_CD */
	@Column(name = "INT_CLR_CD")
	private String intClrCd;

	/** output parameter LGCL_PPLN_STAGE_CD */
	@Column(name = "LGCL_PPLN_STAGE_CD")
	private String lgclPplnStageCd;

	/** output parameter LINE_CLASS */
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/** output parameter MS_FXD_FLAG */
	@Column(name = "MS_FXD_FLAG")
	private String msFxdFlag;

	/** output parameter OFFLN_PLAN_DATE */
	@Column(name = "OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	/** output parameter ORDR_DEL_FLAG */
	@Column(name = "ORDR_DEL_FLAG")
	private String ordrDelFlag;

	/** output parameter PCK_CD */
	@Column(name = "PCK_CD")
	private String pckCd;

	/** output parameter POR_CD */
	@Column(name = "POR_CD")
	private String porCd;

	/** output parameter POT_CD */
	@Column(name = "POT_CD")
	private String potCd;

	/** output parameter PROD_FMY_CD */
	@Column(name = "PROD_FMY_CD")
	private String prodFmyCd;

	/** output parameter PROD_MNTH */
	@Column(name = "PROD_MNTH")
	private String prodMnth;

	/** output parameter PROD_MTHD_CD */
	@Column(name = "PROD_MTHD_CD")
	private String prodMthdCd;

	/** output parameter PROD_ORDR_NO */
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;

	/** output parameter PROD_PLNT_CD */
	@Column(name = "PROD_PLNT_CD")
	private String prodPlntCd;

	/** output parameter SLS_NOTE_NO */
	@Column(name = "SLS_NOTE_NO")
	private String slsNoteNo;

	/** output parameter SPEC_DESTN_CD */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestnCd;

	/** output parameter VHCL_SEQ_ID */
	@Column(name = "VHCL_SEQ_ID")
	private String vhclSeqId;

	/** output parameter VIN_NO */
	@Column(name = "VIN_NO")
	private String vinNo;

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
	 * Get the adtnlSpecCd
	 *
	 * @return the adtnlSpecCd
	 */
	public String getAdtnlSpecCd() {
		return adtnlSpecCd;
	}

	/**
	 * Set the adtnlSpecCd
	 *
	 * @param adtnlSpecCd the adtnlSpecCd to set
	 */
	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
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
	 * Get the etaAdjustFlag
	 *
	 * @return the etaAdjustFlag
	 */
	public BigDecimal getEtaAdjustFlag() {
		return etaAdjustFlag;
	}

	/**
	 * Set the etaAdjustFlag
	 *
	 * @param etaAdjustFlag the etaAdjustFlag to set
	 */
	public void setEtaAdjustFlag(BigDecimal etaAdjustFlag) {
		this.etaAdjustFlag = etaAdjustFlag;
	}

	/**
	 * Get the exNo
	 *
	 * @return the exNo
	 */
	public String getExNo() {
		return exNo;
	}

	/**
	 * Set the exNo
	 *
	 * @param exNo the exNo to set
	 */
	public void setExNo(String exNo) {
		this.exNo = exNo;
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
	 * Get the frznTypeCd
	 *
	 * @return the frznTypeCd
	 */
	public String getFrznTypeCd() {
		return frznTypeCd;
	}

	/**
	 * Set the frznTypeCd
	 *
	 * @param frznTypeCd the frznTypeCd to set
	 */
	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
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
	 * Get the lgclPplnStageCd
	 *
	 * @return the lgclPplnStageCd
	 */
	public String getLgclPplnStageCd() {
		return lgclPplnStageCd;
	}

	/**
	 * Set the lgclPplnStageCd
	 *
	 * @param lgclPplnStageCd the lgclPplnStageCd to set
	 */
	public void setLgclPplnStageCd(String lgclPplnStageCd) {
		this.lgclPplnStageCd = lgclPplnStageCd;
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
	 * Get the msFxdFlag
	 *
	 * @return the msFxdFlag
	 */
	public String getMsFxdFlag() {
		return msFxdFlag;
	}

	/**
	 * Set the msFxdFlag
	 *
	 * @param msFxdFlag the msFxdFlag to set
	 */
	public void setMsFxdFlag(String msFxdFlag) {
		this.msFxdFlag = msFxdFlag;
	}

	/**
	 * Get the offlnPlanDate
	 *
	 * @return the offlnPlanDate
	 */
	public String getOfflnPlanDate() {
		return offlnPlanDate;
	}

	/**
	 * Set the offlnPlanDate
	 *
	 * @param offlnPlanDate the offlnPlanDate to set
	 */
	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	/**
	 * Get the ordrDelFlag
	 *
	 * @return the ordrDelFlag
	 */
	public String getOrdrDelFlag() {
		return ordrDelFlag;
	}

	/**
	 * Set the ordrDelFlag
	 *
	 * @param ordrDelFlag the ordrDelFlag to set
	 */
	public void setOrdrDelFlag(String ordrDelFlag) {
		this.ordrDelFlag = ordrDelFlag;
	}

	/**
	 * Get the pckCd
	 *
	 * @return the pckCd
	 */
	public String getPckCd() {
		return pckCd;
	}

	/**
	 * Set the pckCd
	 *
	 * @param pckCd the pckCd to set
	 */
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
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
	 * Get the potCd
	 *
	 * @return the potCd
	 */
	public String getPotCd() {
		return potCd;
	}

	/**
	 * Set the potCd
	 *
	 * @param potCd the potCd to set
	 */
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	/**
	 * Get the prodFmyCd
	 *
	 * @return the prodFmyCd
	 */
	public String getProdFmyCd() {
		return prodFmyCd;
	}

	/**
	 * Set the prodFmyCd
	 *
	 * @param prodFmyCd the prodFmyCd to set
	 */
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}

	/**
	 * Get the prodMnth
	 *
	 * @return the prodMnth
	 */
	public String getProdMnth() {
		return prodMnth;
	}

	/**
	 * Set the prodMnth
	 *
	 * @param prodMnth the prodMnth to set
	 */
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	/**
	 * Get the prodMthdCd
	 *
	 * @return the prodMthdCd
	 */
	public String getProdMthdCd() {
		return prodMthdCd;
	}

	/**
	 * Set the prodMthdCd
	 *
	 * @param prodMthdCd the prodMthdCd to set
	 */
	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
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
	 * Get the prodPlntCd
	 *
	 * @return the prodPlntCd
	 */
	public String getProdPlntCd() {
		return prodPlntCd;
	}

	/**
	 * Set the prodPlntCd
	 *
	 * @param prodPlntCd the prodPlntCd to set
	 */
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	/**
	 * Get the slsNoteNo
	 *
	 * @return the slsNoteNo
	 */
	public String getSlsNoteNo() {
		return slsNoteNo;
	}

	/**
	 * Set the slsNoteNo
	 *
	 * @param slsNoteNo the slsNoteNo to set
	 */
	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
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
	 * Get the vhclSeqId
	 *
	 * @return the vhclSeqId
	 */
	public String getVhclSeqId() {
		return vhclSeqId;
	}

	/**
	 * Set the vhclSeqId
	 *
	 * @param vhclSeqId the vhclSeqId to set
	 */
	public void setVhclSeqId(String vhclSeqId) {
		this.vhclSeqId = vhclSeqId;
	}

	/**
	 * Get the vinNo
	 *
	 * @return the vinNo
	 */
	public String getVinNo() {
		return vinNo;
	}

	/**
	 * Set the vinNo
	 *
	 * @param vinNo the vinNo to set
	 */
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	

}