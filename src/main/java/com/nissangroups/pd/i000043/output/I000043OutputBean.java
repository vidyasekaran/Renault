/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000043
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly OCF from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000043.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class I000043OutputBean.
 *
 * @author z014029
 */
@Entity
public class I000043OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Output parameter Por cd
	 */
	@Id
	@Column(name = "POR_CD")
	private String porCd;
	
	/**
	 * Output parameter car series
	 */
	@Column(name = "CAR_SRS")
	private String crSrs;
	
	/**
	 * Output Parameter Buyer cd
	 */
	@Column(name = "BUYER_CD")
	private String byrCd;
	
	/**
	 * Output Parameter Application Model cd
	 */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;
	
	/**
	 * Output Parameter Pcked CD
	 */
	@Column(name = "PCK_CD")
	private String pckCd;
	
	/**
	 * Output Parameter Specification Dest
	 */
	@Column(name = "SPEC_DESTN_CD")
	private String specDest;
	
	/**
	 * Output parameter Exterior color
	 */
	@Column(name = "EXT_CLR_CD")
	private String extClr;
	
	/**
	 * Output Parameter Interior Color
	 */
	@Column(name = "INT_CLR_CD")
	private String intClr;
	
	/**
	 * Output Parameter Additional spec
	 */
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpecCd;
	
	/**
	 * Output Parameter Osei adpt date
	 */
	@Column(name = "OSEI_ADPT_DATE")
	private String oseiAdptDt;
	
	/**
	 * Output Parameter Osei ablsh date
	 */
	@Column(name = "OSEI_ABLSH_DATE")
	private String oseiAblshDt;
	
	/**
	 * Output Parameter osei suspended date
	 */
	@Column(name = "OSEI_SUSPENDED_DATE")
	private String oseiSuspndAblshDt;
	
	/**
	 * Output Parameter production family cd
	 */
	@Column(name = "PROD_FMY_CD")
	private String prdFmlyCd; 															
	
	/**
	 * Output Parameter Local note
	 */
	@Column(name = "LCL_NOTE")
	private String lclNote; 
	
	/**
	 * Output Parameter Package name
	 */
	@Column(name = "PCKGE_NAME")
	private String pckgName;	
	
	/**
	 * Output Parameter optional spec code
	 */
	@Column(name = "OPTN_SPEC_CODE")
	private String optnlSpecCd;
	
	/**
	 * Output Parameter end item status 
	 */
	@Column(name = "END_ITM_STTS_CD")
	private String endItmSttsCd;
	
	/**
	 * Output Parameter created by 
	 */
	@Column(name = "CRTD_BY")
	private String crtdUsrId;
	
	/**
	 * Output Parameter created date
	 */
	@Column(name = "CRTD_DT")
	private String crtdDtTime;
	
	/**
	 * Output Parameter update by
	 */
	@Column(name = "UPDTD_BY")
	private String updtdUsrId;
	
	/**
	 * Output Parameter update date
	 */
	@Column(name = "UPDTD_DT")
	private String updtDtTime;

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
	 * Get the crSrs
	 *
	 * @return the crSrs
	 */
	public String getCrSrs() {
		return crSrs;
	}

	/**
	 * Set the crSrs
	 *
	 * @param crSrs the crSrs to set
	 */
	public void setCrSrs(String crSrs) {
		this.crSrs = crSrs;
	}

	/**
	 * Get the byrCd
	 *
	 * @return the byrCd
	 */
	public String getByrCd() {
		return byrCd;
	}

	/**
	 * Set the byrCd
	 *
	 * @param byrCd the byrCd to set
	 */
	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
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
	 * Get the specDest
	 *
	 * @return the specDest
	 */
	public String getSpecDest() {
		return specDest;
	}

	/**
	 * Set the specDest
	 *
	 * @param specDest the specDest to set
	 */
	public void setSpecDest(String specDest) {
		this.specDest = specDest;
	}

	/**
	 * Get the extClr
	 *
	 * @return the extClr
	 */
	public String getExtClr() {
		return extClr;
	}

	/**
	 * Set the extClr
	 *
	 * @param extClr the extClr to set
	 */
	public void setExtClr(String extClr) {
		this.extClr = extClr;
	}

	/**
	 * Get the intClr
	 *
	 * @return the intClr
	 */
	public String getIntClr() {
		return intClr;
	}

	/**
	 * Set the intClr
	 *
	 * @param intClr the intClr to set
	 */
	public void setIntClr(String intClr) {
		this.intClr = intClr;
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
	 * Get the oseiAdptDt
	 *
	 * @return the oseiAdptDt
	 */
	public String getOseiAdptDt() {
		return oseiAdptDt;
	}

	/**
	 * Set the oseiAdptDt
	 *
	 * @param oseiAdptDt the oseiAdptDt to set
	 */
	public void setOseiAdptDt(String oseiAdptDt) {
		this.oseiAdptDt = oseiAdptDt;
	}

	/**
	 * Get the oseiAblshDt
	 *
	 * @return the oseiAblshDt
	 */
	public String getOseiAblshDt() {
		return oseiAblshDt;
	}

	/**
	 * Set the oseiAblshDt
	 *
	 * @param oseiAblshDt the oseiAblshDt to set
	 */
	public void setOseiAblshDt(String oseiAblshDt) {
		this.oseiAblshDt = oseiAblshDt;
	}

	/**
	 * Get the oseiSuspndAblshDt
	 *
	 * @return the oseiSuspndAblshDt
	 */
	public String getOseiSuspndAblshDt() {
		return oseiSuspndAblshDt;
	}

	/**
	 * Set the oseiSuspndAblshDt
	 *
	 * @param oseiSuspndAblshDt the oseiSuspndAblshDt to set
	 */
	public void setOseiSuspndAblshDt(String oseiSuspndAblshDt) {
		this.oseiSuspndAblshDt = oseiSuspndAblshDt;
	}

	/**
	 * Get the prdFmlyCd
	 *
	 * @return the prdFmlyCd
	 */
	public String getPrdFmlyCd() {
		return prdFmlyCd;
	}

	/**
	 * Set the prdFmlyCd
	 *
	 * @param prdFmlyCd the prdFmlyCd to set
	 */
	public void setPrdFmlyCd(String prdFmlyCd) {
		this.prdFmlyCd = prdFmlyCd;
	}

	/**
	 * Get the lclNote
	 *
	 * @return the lclNote
	 */
	public String getLclNote() {
		return lclNote;
	}

	/**
	 * Set the lclNote
	 *
	 * @param lclNote the lclNote to set
	 */
	public void setLclNote(String lclNote) {
		this.lclNote = lclNote;
	}

	/**
	 * Get the pckgName
	 *
	 * @return the pckgName
	 */
	public String getPckgName() {
		return pckgName;
	}

	/**
	 * Set the pckgName
	 *
	 * @param pckgName the pckgName to set
	 */
	public void setPckgName(String pckgName) {
		this.pckgName = pckgName;
	}

	/**
	 * Get the optnlSpecCd
	 *
	 * @return the optnlSpecCd
	 */
	public String getOptnlSpecCd() {
		return optnlSpecCd;
	}

	/**
	 * Set the optnlSpecCd
	 *
	 * @param optnlSpecCd the optnlSpecCd to set
	 */
	public void setOptnlSpecCd(String optnlSpecCd) {
		this.optnlSpecCd = optnlSpecCd;
	}

	/**
	 * Get the endItmSttsCd
	 *
	 * @return the endItmSttsCd
	 */
	public String getEndItmSttsCd() {
		return endItmSttsCd;
	}

	/**
	 * Set the endItmSttsCd
	 *
	 * @param endItmSttsCd the endItmSttsCd to set
	 */
	public void setEndItmSttsCd(String endItmSttsCd) {
		this.endItmSttsCd = endItmSttsCd;
	}

	/**
	 * Get the crtdUsrId
	 *
	 * @return the crtdUsrId
	 */
	public String getCrtdUsrId() {
		return crtdUsrId;
	}

	/**
	 * Set the crtdUsrId
	 *
	 * @param crtdUsrId the crtdUsrId to set
	 */
	public void setCrtdUsrId(String crtdUsrId) {
		this.crtdUsrId = crtdUsrId;
	}

	/**
	 * Get the crtdDtTime
	 *
	 * @return the crtdDtTime
	 */
	public String getCrtdDtTime() {
		return crtdDtTime;
	}

	/**
	 * Set the crtdDtTime
	 *
	 * @param crtdDtTime the crtdDtTime to set
	 */
	public void setCrtdDtTime(String crtdDtTime) {
		this.crtdDtTime = crtdDtTime;
	}

	/**
	 * Get the updtdUsrId
	 *
	 * @return the updtdUsrId
	 */
	public String getUpdtdUsrId() {
		return updtdUsrId;
	}

	/**
	 * Set the updtdUsrId
	 *
	 * @param updtdUsrId the updtdUsrId to set
	 */
	public void setUpdtdUsrId(String updtdUsrId) {
		this.updtdUsrId = updtdUsrId;
	}

	/**
	 * Get the updtDtTime
	 *
	 * @return the updtDtTime
	 */
	public String getUpdtDtTime() {
		return updtDtTime;
	}

	/**
	 * Set the updtDtTime
	 *
	 * @param updtDtTime the updtDtTime to set
	 */
	public void setUpdtDtTime(String updtDtTime) {
		this.updtDtTime = updtDtTime;
	}  	
	
	
}