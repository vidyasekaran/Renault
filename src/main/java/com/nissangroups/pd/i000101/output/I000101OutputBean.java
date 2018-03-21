/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : PST-DRG-I000101
 * Module          : CM COMMON					
 * Process Outline : Send Physical Pipeline to SAP
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 30-12-2015  	  z014676(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000101.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * This is a persistent class which maps to Common interface data table.
 * author Z014676
 */
@Entity
public class I000101OutputBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Output Variable Row num */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Output Variable POR CD */
	@Column(name = "POR_CD")
	private String porCd;

	/** Output Variable POT_CD */
	@Column(name = "POT_CD")
	private String potCd;

	/** Output Variable ACTL_FINAL_OK_DATE */
	@Column(name = "ACTL_FINAL_OK_DATE")
	private String actlFinalOkDate;

	/** Output Variable ACTL_FINAL_PASS_DATE */
	@Column(name = "ACTL_FINAL_PASS_DATE")
	private String actlFinalPassDate;

	/** Output Variable ACTL_METAL_OK_DATE */
	@Column(name = "ACTL_METAL_OK_DATE")
	private String actlMetalOkDate;

	/** Output Variable ACTL_OFFLN_DATE */
	@Column(name = "ACTL_OFFLN_DATE")
	private String actlOfflnDate;

	/** Output Variable ACTL_PAINT_IN_DATE */
	@Column(name = "ACTL_PAINT_IN_DATE")
	private String actlPaintInDate;

	/** Output Variable ACTL_PAINT_OK_DATE */
	@Column(name = "ACTL_PAINT_OK_DATE")
	private String actlPaintOkDate;

	/** Output Variable ACTL_SETUP_DATE */
	@Column(name = "ACTL_SETUP_DATE")
	private String actlSetupDate;

	/** Output Variable ACTL_TRIM_IN_DATE */
	@Column(name = "ACTL_TRIM_IN_DATE")
	private String actlTrimInDate;

	/** Output Variable ACTUAL_DELIVERY_DATE */
	@Column(name = "ACTUAL_DELIVERY_DATE")
	private String actualDeliveryDate;

	/** Output Variable ADTNL_SPEC_CD */
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	/** Output Variable APPLD_MDL_CD */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;

	/** Output Variable BUYER_CD */
	@Column(name = "BUYER_CD")
	private String buyerCd;

	/** Output Variable CAR_SRS */
	@Column(name = "CAR_SRS")
	private String carSrs;

	/** Output Variable ENG_NO */
	@Column(name = "ENG_NO")
	private String engNo;

	/** Output Variable ENG_TYPE */
	@Column(name = "ENG_TYPE")
	private String engType;

	/** Output Variable EX_NO */
	@Column(name = "EX_NO")
	private String exNo;

	/** Output Variable EXT_CLR_CD */
	@Column(name = "EXT_CLR_CD")
	private String extClrCd;

	/** Output Variable INSPCTN_DATE */
	@Column(name = "INSPCTN_DATE")
	private String inspctnDate;

	/** Output Variable INT_CLR_CD */
	@Column(name = "INT_CLR_CD")
	private String intClrCd;

	/** Output Variable INTRNL_OR_TRD_FLAG */
	@Column(name = "INTRNL_OR_TRD_FLAG")
	private String intrnlOrTrdFlag;

	/** Output Variable LGCL_PPLN_SEQ_ID */
	@Column(name = "LGCL_PPLN_SEQ_ID")
	private String lgclPplnSeqId;

	/** Output Variable LINE_CLASS */
	@Column(name = "LINE_CLASS")
	private String lineClass;

	/** Output Variable MS_OFFLINE_DATE */
	@Column(name = "MS_OFFLINE_DATE")

	private String msOfflineDate;

	/** Output Variable OCF_REGION_CD */
	@Column(name = "OCF_REGION_CD")
	private String ocfRegionCd;

	/** Output Variable OFFLN_PLAN_DATE */
	@Column(name = "OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	/** Output Variable PCK_CD */
	@Column(name = "PCK_CD")
	private String pckCd;

	/** Output Variable PLNND_DLVRY_DATE */
	@Column(name = "PLNND_DLVRY_DATE")
	private String plnndDlvryDate;

	/** Output Variable PLNND_FINAL_OK_DATE */
	@Column(name = "PLNND_FINAL_OK_DATE")
	private String plnndFinalOkDate;

	/** Output Variable PLNND_FINAL_PASS_DATE */
	@Column(name = "PLNND_FINAL_PASS_DATE")
	private String plnndFinalPassDate;

	/** Output Variable PLNND_LDNG_DATE */
	@Column(name = "PLNND_LDNG_DATE")
	private String plnndLdngDate;

	/** Output Variable PLNND_METAL_OK_DATE */
	@Column(name = "PLNND_METAL_OK_DATE")
	private String plnndMetalOkDate;

	/** Output Variable PLNND_PAINT_IN_DATE */
	@Column(name = "PLNND_PAINT_IN_DATE")
	private String plnndPaintInDate;

	/** Output Variable PLNND_PAINT_OK_DATE */
	@Column(name = "PLNND_PAINT_OK_DATE")
	private String plnndPaintOkDate;

	/** Output Variable PLNND_PORT_IN_DATE */
	@Column(name = "PLNND_PORT_IN_DATE")
	private String plnndPortInDate;

	/** Output Variable PLNND_SETUP_DATE */
	@Column(name = "PLNND_SETUP_DATE")
	private String plnndSetupDate;

	/** Output Variable PLNND_TRIM_IN_DATE */
	@Column(name = "PLNND_TRIM_IN_DATE")
	private String plnndTrimInDate;

	/** Output Variable PLNND_OFFLN_DATE */
	@Column(name = "PLNND_OFFLN_DATE")
	private String plnndOfflnDate;

	/** Output Variable PROD_MNTH */
	@Column(name = "PROD_MNTH")
	private String prodMnth;

	/** Output Variable PROD_ORDR_NO */
	@Column(name = "PROD_ORDR_NO")
	private String prodOrdrNo;

	/** Output Variable PROD_PLNT_CD */
	@Column(name = "PROD_PLNT_CD")
	private String prodPlntCd;

	/** Output Variable PROD_WK_NO */
	@Column(name = "PROD_WK_NO")
	private String prodWkNo;

	/** Output Variable PRODUCTION_FAMILY_CD */
	@Column(name = "PRODUCTION_FAMILY_CD")
	private String productionFamilyCd;

	/** Output Variable PRTYPE_VHCL_FLAG */
	@Column(name = "PRTYPE_VHCL_FLAG")
	private String prtypeVhclFlag;

	/** Output Variable SALES_NOTE_NO */
	@Column(name = "SALES_NOTE_NO")
	private String salesNoteNo;

	/** Output Variable SCRPD_DATE */
	@Column(name = "SCRPD_DATE")
	private String scrpdDate;

	/** Output Variable SPEC_DESTN_CD */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestnCd;

	/** Output Variable VIN_ALLCT_FLAG */
	@Column(name = "VIN_ALLCT_FLAG")
	private String vinAllctFlag;

	/** Output Variable VIN_NO */
	@Column(name = "VIN_NO")
	private String vinNo;

	public I000101OutputBean() {

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
	 * Get the actlFinalOkDate
	 *
	 * @return the actlFinalOkDate
	 */
	public String getActlFinalOkDate() {
		return actlFinalOkDate;
	}

	/**
	 * Set the actlFinalOkDate
	 *
	 * @param actlFinalOkDate the actlFinalOkDate to set
	 */
	public void setActlFinalOkDate(String actlFinalOkDate) {
		this.actlFinalOkDate = actlFinalOkDate;
	}

	/**
	 * Get the actlFinalPassDate
	 *
	 * @return the actlFinalPassDate
	 */
	public String getActlFinalPassDate() {
		return actlFinalPassDate;
	}

	/**
	 * Set the actlFinalPassDate
	 *
	 * @param actlFinalPassDate the actlFinalPassDate to set
	 */
	public void setActlFinalPassDate(String actlFinalPassDate) {
		this.actlFinalPassDate = actlFinalPassDate;
	}

	/**
	 * Get the actlMetalOkDate
	 *
	 * @return the actlMetalOkDate
	 */
	public String getActlMetalOkDate() {
		return actlMetalOkDate;
	}

	/**
	 * Set the actlMetalOkDate
	 *
	 * @param actlMetalOkDate the actlMetalOkDate to set
	 */
	public void setActlMetalOkDate(String actlMetalOkDate) {
		this.actlMetalOkDate = actlMetalOkDate;
	}

	/**
	 * Get the actlOfflnDate
	 *
	 * @return the actlOfflnDate
	 */
	public String getActlOfflnDate() {
		return actlOfflnDate;
	}

	/**
	 * Set the actlOfflnDate
	 *
	 * @param actlOfflnDate the actlOfflnDate to set
	 */
	public void setActlOfflnDate(String actlOfflnDate) {
		this.actlOfflnDate = actlOfflnDate;
	}

	/**
	 * Get the actlPaintInDate
	 *
	 * @return the actlPaintInDate
	 */
	public String getActlPaintInDate() {
		return actlPaintInDate;
	}

	/**
	 * Set the actlPaintInDate
	 *
	 * @param actlPaintInDate the actlPaintInDate to set
	 */
	public void setActlPaintInDate(String actlPaintInDate) {
		this.actlPaintInDate = actlPaintInDate;
	}

	/**
	 * Get the actlPaintOkDate
	 *
	 * @return the actlPaintOkDate
	 */
	public String getActlPaintOkDate() {
		return actlPaintOkDate;
	}

	/**
	 * Set the actlPaintOkDate
	 *
	 * @param actlPaintOkDate the actlPaintOkDate to set
	 */
	public void setActlPaintOkDate(String actlPaintOkDate) {
		this.actlPaintOkDate = actlPaintOkDate;
	}

	/**
	 * Get the actlSetupDate
	 *
	 * @return the actlSetupDate
	 */
	public String getActlSetupDate() {
		return actlSetupDate;
	}

	/**
	 * Set the actlSetupDate
	 *
	 * @param actlSetupDate the actlSetupDate to set
	 */
	public void setActlSetupDate(String actlSetupDate) {
		this.actlSetupDate = actlSetupDate;
	}

	/**
	 * Get the actlTrimInDate
	 *
	 * @return the actlTrimInDate
	 */
	public String getActlTrimInDate() {
		return actlTrimInDate;
	}

	/**
	 * Set the actlTrimInDate
	 *
	 * @param actlTrimInDate the actlTrimInDate to set
	 */
	public void setActlTrimInDate(String actlTrimInDate) {
		this.actlTrimInDate = actlTrimInDate;
	}

	/**
	 * Get the actualDeliveryDate
	 *
	 * @return the actualDeliveryDate
	 */
	public String getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	/**
	 * Set the actualDeliveryDate
	 *
	 * @param actualDeliveryDate the actualDeliveryDate to set
	 */
	public void setActualDeliveryDate(String actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
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
	 * Get the engNo
	 *
	 * @return the engNo
	 */
	public String getEngNo() {
		return engNo;
	}

	/**
	 * Set the engNo
	 *
	 * @param engNo the engNo to set
	 */
	public void setEngNo(String engNo) {
		this.engNo = engNo;
	}

	/**
	 * Get the engType
	 *
	 * @return the engType
	 */
	public String getEngType() {
		return engType;
	}

	/**
	 * Set the engType
	 *
	 * @param engType the engType to set
	 */
	public void setEngType(String engType) {
		this.engType = engType;
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
	 * Get the inspctnDate
	 *
	 * @return the inspctnDate
	 */
	public String getInspctnDate() {
		return inspctnDate;
	}

	/**
	 * Set the inspctnDate
	 *
	 * @param inspctnDate the inspctnDate to set
	 */
	public void setInspctnDate(String inspctnDate) {
		this.inspctnDate = inspctnDate;
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
	 * Get the intrnlOrTrdFlag
	 *
	 * @return the intrnlOrTrdFlag
	 */
	public String getIntrnlOrTrdFlag() {
		return intrnlOrTrdFlag;
	}

	/**
	 * Set the intrnlOrTrdFlag
	 *
	 * @param intrnlOrTrdFlag the intrnlOrTrdFlag to set
	 */
	public void setIntrnlOrTrdFlag(String intrnlOrTrdFlag) {
		this.intrnlOrTrdFlag = intrnlOrTrdFlag;
	}

	/**
	 * Get the lgclPplnSeqId
	 *
	 * @return the lgclPplnSeqId
	 */
	public String getLgclPplnSeqId() {
		return lgclPplnSeqId;
	}

	/**
	 * Set the lgclPplnSeqId
	 *
	 * @param lgclPplnSeqId the lgclPplnSeqId to set
	 */
	public void setLgclPplnSeqId(String lgclPplnSeqId) {
		this.lgclPplnSeqId = lgclPplnSeqId;
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
	 * Get the msOfflineDate
	 *
	 * @return the msOfflineDate
	 */
	public String getMsOfflineDate() {
		return msOfflineDate;
	}

	/**
	 * Set the msOfflineDate
	 *
	 * @param msOfflineDate the msOfflineDate to set
	 */
	public void setMsOfflineDate(String msOfflineDate) {
		this.msOfflineDate = msOfflineDate;
	}

	/**
	 * Get the ocfRegionCd
	 *
	 * @return the ocfRegionCd
	 */
	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	/**
	 * Set the ocfRegionCd
	 *
	 * @param ocfRegionCd the ocfRegionCd to set
	 */
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
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
	 * Get the plnndDlvryDate
	 *
	 * @return the plnndDlvryDate
	 */
	public String getPlnndDlvryDate() {
		return plnndDlvryDate;
	}

	/**
	 * Set the plnndDlvryDate
	 *
	 * @param plnndDlvryDate the plnndDlvryDate to set
	 */
	public void setPlnndDlvryDate(String plnndDlvryDate) {
		this.plnndDlvryDate = plnndDlvryDate;
	}

	/**
	 * Get the plnndFinalOkDate
	 *
	 * @return the plnndFinalOkDate
	 */
	public String getPlnndFinalOkDate() {
		return plnndFinalOkDate;
	}

	/**
	 * Set the plnndFinalOkDate
	 *
	 * @param plnndFinalOkDate the plnndFinalOkDate to set
	 */
	public void setPlnndFinalOkDate(String plnndFinalOkDate) {
		this.plnndFinalOkDate = plnndFinalOkDate;
	}

	/**
	 * Get the plnndFinalPassDate
	 *
	 * @return the plnndFinalPassDate
	 */
	public String getPlnndFinalPassDate() {
		return plnndFinalPassDate;
	}

	/**
	 * Set the plnndFinalPassDate
	 *
	 * @param plnndFinalPassDate the plnndFinalPassDate to set
	 */
	public void setPlnndFinalPassDate(String plnndFinalPassDate) {
		this.plnndFinalPassDate = plnndFinalPassDate;
	}

	/**
	 * Get the plnndLdngDate
	 *
	 * @return the plnndLdngDate
	 */
	public String getPlnndLdngDate() {
		return plnndLdngDate;
	}

	/**
	 * Set the plnndLdngDate
	 *
	 * @param plnndLdngDate the plnndLdngDate to set
	 */
	public void setPlnndLdngDate(String plnndLdngDate) {
		this.plnndLdngDate = plnndLdngDate;
	}

	/**
	 * Get the plnndMetalOkDate
	 *
	 * @return the plnndMetalOkDate
	 */
	public String getPlnndMetalOkDate() {
		return plnndMetalOkDate;
	}

	/**
	 * Set the plnndMetalOkDate
	 *
	 * @param plnndMetalOkDate the plnndMetalOkDate to set
	 */
	public void setPlnndMetalOkDate(String plnndMetalOkDate) {
		this.plnndMetalOkDate = plnndMetalOkDate;
	}

	/**
	 * Get the plnndPaintInDate
	 *
	 * @return the plnndPaintInDate
	 */
	public String getPlnndPaintInDate() {
		return plnndPaintInDate;
	}

	/**
	 * Set the plnndPaintInDate
	 *
	 * @param plnndPaintInDate the plnndPaintInDate to set
	 */
	public void setPlnndPaintInDate(String plnndPaintInDate) {
		this.plnndPaintInDate = plnndPaintInDate;
	}

	/**
	 * Get the plnndPaintOkDate
	 *
	 * @return the plnndPaintOkDate
	 */
	public String getPlnndPaintOkDate() {
		return plnndPaintOkDate;
	}

	/**
	 * Set the plnndPaintOkDate
	 *
	 * @param plnndPaintOkDate the plnndPaintOkDate to set
	 */
	public void setPlnndPaintOkDate(String plnndPaintOkDate) {
		this.plnndPaintOkDate = plnndPaintOkDate;
	}

	/**
	 * Get the plnndPortInDate
	 *
	 * @return the plnndPortInDate
	 */
	public String getPlnndPortInDate() {
		return plnndPortInDate;
	}

	/**
	 * Set the plnndPortInDate
	 *
	 * @param plnndPortInDate the plnndPortInDate to set
	 */
	public void setPlnndPortInDate(String plnndPortInDate) {
		this.plnndPortInDate = plnndPortInDate;
	}

	/**
	 * Get the plnndSetupDate
	 *
	 * @return the plnndSetupDate
	 */
	public String getPlnndSetupDate() {
		return plnndSetupDate;
	}

	/**
	 * Set the plnndSetupDate
	 *
	 * @param plnndSetupDate the plnndSetupDate to set
	 */
	public void setPlnndSetupDate(String plnndSetupDate) {
		this.plnndSetupDate = plnndSetupDate;
	}

	/**
	 * Get the plnndTrimInDate
	 *
	 * @return the plnndTrimInDate
	 */
	public String getPlnndTrimInDate() {
		return plnndTrimInDate;
	}

	/**
	 * Set the plnndTrimInDate
	 *
	 * @param plnndTrimInDate the plnndTrimInDate to set
	 */
	public void setPlnndTrimInDate(String plnndTrimInDate) {
		this.plnndTrimInDate = plnndTrimInDate;
	}

	/**
	 * Get the plnndOfflnDate
	 *
	 * @return the plnndOfflnDate
	 */
	public String getPlnndOfflnDate() {
		return plnndOfflnDate;
	}

	/**
	 * Set the plnndOfflnDate
	 *
	 * @param plnndOfflnDate the plnndOfflnDate to set
	 */
	public void setPlnndOfflnDate(String plnndOfflnDate) {
		this.plnndOfflnDate = plnndOfflnDate;
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
	 * Get the prodWkNo
	 *
	 * @return the prodWkNo
	 */
	public String getProdWkNo() {
		return prodWkNo;
	}

	/**
	 * Set the prodWkNo
	 *
	 * @param prodWkNo the prodWkNo to set
	 */
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	/**
	 * Get the productionFamilyCd
	 *
	 * @return the productionFamilyCd
	 */
	public String getProductionFamilyCd() {
		return productionFamilyCd;
	}

	/**
	 * Set the productionFamilyCd
	 *
	 * @param productionFamilyCd the productionFamilyCd to set
	 */
	public void setProductionFamilyCd(String productionFamilyCd) {
		this.productionFamilyCd = productionFamilyCd;
	}

	/**
	 * Get the prtypeVhclFlag
	 *
	 * @return the prtypeVhclFlag
	 */
	public String getPrtypeVhclFlag() {
		return prtypeVhclFlag;
	}

	/**
	 * Set the prtypeVhclFlag
	 *
	 * @param prtypeVhclFlag the prtypeVhclFlag to set
	 */
	public void setPrtypeVhclFlag(String prtypeVhclFlag) {
		this.prtypeVhclFlag = prtypeVhclFlag;
	}

	/**
	 * Get the salesNoteNo
	 *
	 * @return the salesNoteNo
	 */
	public String getSalesNoteNo() {
		return salesNoteNo;
	}

	/**
	 * Set the salesNoteNo
	 *
	 * @param salesNoteNo the salesNoteNo to set
	 */
	public void setSalesNoteNo(String salesNoteNo) {
		this.salesNoteNo = salesNoteNo;
	}

	/**
	 * Get the scrpdDate
	 *
	 * @return the scrpdDate
	 */
	public String getScrpdDate() {
		return scrpdDate;
	}

	/**
	 * Set the scrpdDate
	 *
	 * @param scrpdDate the scrpdDate to set
	 */
	public void setScrpdDate(String scrpdDate) {
		this.scrpdDate = scrpdDate;
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
	 * Get the vinAllctFlag
	 *
	 * @return the vinAllctFlag
	 */
	public String getVinAllctFlag() {
		return vinAllctFlag;
	}

	/**
	 * Set the vinAllctFlag
	 *
	 * @param vinAllctFlag the vinAllctFlag to set
	 */
	public void setVinAllctFlag(String vinAllctFlag) {
		this.vinAllctFlag = vinAllctFlag;
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
