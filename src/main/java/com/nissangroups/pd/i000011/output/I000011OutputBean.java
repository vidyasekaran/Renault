/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000011
 * Module          :NSC OSEI SPEC MASTER
 
 * Process Outline :Control the Order Take on Local Systems 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000011.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from MST_BUYER AND MST_BUYER_GRP database table values.
 * 
 * @author z014029
 */
@Entity
public class I000011OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Interface Output Parameter ROW NUM */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Interface Output Parameter POR CD */
	@Column(name = "POR_CD")
	private String porCd;

	/** Interface Output Parameter CAR SERIES */
	@Column(name = "CAR_SRS")
	private String crSrs;

	/** Interface Output Parameter BUYER CODE */
	@Column(name = "BUYER_CD")
	private String byrCd;

	/** Interface Output Parameter APPLIED MODEL CODE */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;

	/** Interface Output Parameter PACK CODE */
	@Column(name = "PCK_CD")
	private String pckCd;

	/** Interface Output Parameter SPEC DESTINATION CODE */
	@Column(name = "SPEC_DESTN_CD")
	private String specDest;

	/** Interface Output EXTERNAL COLOR CODE */
	@Column(name = "EXT_CLR_CD")
	private String extClr;

	/** Interface Output INTERNAL COLOR CODE */
	@Column(name = "INT_CLR_CD")
	private String intClr;

	/** Interface Output ADDITIONAL SPEC CODE */
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	/** Interface Output OSEI ADOPT DATE */
	@Column(name = "OSEI_ADPT_DATE")
	private String oseiAdptDt;

	/** Interface Output OSEI ABOLISH DATE */
	@Column(name = "OSEI_ABLSH_DATE")
	private String oseiAblshDt;

	/** Interface Output OSEI SUSPENDED DATE */
	@Column(name = "OSEI_SUSPENDED_DATE")
	private String oseiSuspndAblshDt;

	/** Interface Output PRODUCTION FAMILY CODE */
	@Column(name = "PROD_FMY_CD")
	private String prdFmlyCd;

	/** Interface Output LOCAL NOTE */
	@Column(name = "LCL_NOTE")
	private String lclNote;

	/** Interface Output PACKAGE NAME */
	@Column(name = "PCKGE_NAME")
	private String pckgName;

	/** Interface Output OPTIONAL SPEC CODE */
	@Column(name = "OPTN_SPEC_CODE")
	private String optnlSpecCd;

	/** Interface Output END ITEM STATUS CODE */
	@Column(name = "END_ITM_STTS_CD")
	private String endItmSttsCd;

	/** Interface Output CREATED BY */
	@Column(name = "CRTD_BY")
	private String crtdUsrId;

	/** Interface Output CREATED DATE */
	@Column(name = "CRTD_DT")
	private String crtdDtTime;

	/** Interface Output UPDATED BY */
	@Column(name = "UPDTD_BY")
	private String updtdUsrId;

	/** Interface Output UPDATED DATE */
	@Column(name = "UPDTD_DT")
	private String updtDtTime;

	
	/**
	 * Gets the rowNum
	 *
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * Sets the rowNum
	 *
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * Gets the porCd
	 *
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}
	
	/**
	 * Sets the porCd
	 *
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * Gets the crSrs
	 *
	 * @return the crSrs
	 */
	public String getCrSrs() {
		return crSrs;
	}

	/**
	 * Sets the crSrs
	 *
	 * @param crSrs the crSrs to set
	 */
	public void setCrSrs(String crSrs) {
		this.crSrs = crSrs;
	}

	/**
	 * Gets the byrCd
	 *
	 * @return the byrCd
	 */
	public String getByrCd() {
		return byrCd;
	}

	/**
	 * Sets the byrCd
	 *
	 * @param byrCd the byrCd to set
	 */
	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}

	/**
	 * Gets the appldMdlCd
	 *
	 * @return the appldMdlCd
	 */
	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	/**
	 * Sets the appldMdlCd
	 *
	 * @param appldMdlCd the appldMdlCd to set
	 */
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	/**
	 * Gets the pckCd
	 *
	 * @return the pckCd
	 */
	public String getPckCd() {
		return pckCd;
	}

	/**
	 * Sets the pckCd
	 *
	 * @param pckCd the pckCd to set
	 */
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	/**
	 * Gets the specDest
	 *
	 * @return the specDest
	 */
	public String getSpecDest() {
		return specDest;
	}

	/**
	 * Sets the specDest
	 *
	 * @param specDest the specDest to set
	 */
	public void setSpecDest(String specDest) {
		this.specDest = specDest;
	}

	/**
	 * Gets the extClr
	 *
	 * @return the extClr
	 */
	public String getExtClr() {
		return extClr;
	}

	/**
	 * Sets the extClr
	 *
	 * @param extClr the extClr to set
	 */
	public void setExtClr(String extClr) {
		this.extClr = extClr;
	}

	/**
	 * Gets the intClr
	 *
	 * @return the intClr
	 */
	public String getIntClr() {
		return intClr;
	}

	/**
	 * Sets the intClr
	 *
	 * @param intClr the intClr to set
	 */
	public void setIntClr(String intClr) {
		this.intClr = intClr;
	}

	/**
	 * Gets the adtnlSpecCd
	 *
	 * @return the adtnlSpecCd
	 */
	public String getAdtnlSpecCd() {
		return adtnlSpecCd;
	}

	/**
	 * Sets the adtnlSpecCd
	 *
	 * @param adtnlSpecCd the adtnlSpecCd to set
	 */
	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}

	/**
	 * Gets the oseiAdptDt
	 *
	 * @return the oseiAdptDt
	 */
	public String getOseiAdptDt() {
		return oseiAdptDt;
	}

	/**
	 * Sets the oseiAdptDt
	 *
	 * @param oseiAdptDt the oseiAdptDt to set
	 */
	public void setOseiAdptDt(String oseiAdptDt) {
		this.oseiAdptDt = oseiAdptDt;
	}

	/**
	 * Gets the oseiAblshDt
	 *
	 * @return the oseiAblshDt
	 */
	public String getOseiAblshDt() {
		return oseiAblshDt;
	}

	/**
	 * Sets the oseiAblshDt
	 *
	 * @param oseiAblshDt the oseiAblshDt to set
	 */
	public void setOseiAblshDt(String oseiAblshDt) {
		this.oseiAblshDt = oseiAblshDt;
	}

	/**
	 * Gets the oseiSuspndAblshDt
	 *
	 * @return the oseiSuspndAblshDt
	 */
	public String getOseiSuspndAblshDt() {
		return oseiSuspndAblshDt;
	}

	/**
	 * Sets the oseiSuspndAblshDt
	 *
	 * @param oseiSuspndAblshDt the oseiSuspndAblshDt to set
	 */
	public void setOseiSuspndAblshDt(String oseiSuspndAblshDt) {
		this.oseiSuspndAblshDt = oseiSuspndAblshDt;
	}

	/**
	 * Gets the prdFmlyCd
	 *
	 * @return the prdFmlyCd
	 */
	public String getPrdFmlyCd() {
		return prdFmlyCd;
	}

	/**
	 * Sets the prdFmlyCd
	 *
	 * @param prdFmlyCd the prdFmlyCd to set
	 */
	public void setPrdFmlyCd(String prdFmlyCd) {
		this.prdFmlyCd = prdFmlyCd;
	}

	/**
	 * Gets the lclNote
	 *
	 * @return the lclNote
	 */
	public String getLclNote() {
		return lclNote;
	}

	/**
	 * Sets the lclNote
	 *
	 * @param lclNote the lclNote to set
	 */
	public void setLclNote(String lclNote) {
		this.lclNote = lclNote;
	}

	/**
	 * Gets the pckgName
	 *
	 * @return the pckgName
	 */
	public String getPckgName() {
		return pckgName;
	}

	/**
	 * Sets the pckgName
	 *
	 * @param pckgName the pckgName to set
	 */
	public void setPckgName(String pckgName) {
		this.pckgName = pckgName;
	}

	/**
	 * Gets the optnlSpecCd
	 *
	 * @return the optnlSpecCd
	 */
	public String getOptnlSpecCd() {
		return optnlSpecCd;
	}

	/**
	 * Sets the optnlSpecCd
	 *
	 * @param optnlSpecCd the optnlSpecCd to set
	 */
	public void setOptnlSpecCd(String optnlSpecCd) {
		this.optnlSpecCd = optnlSpecCd;
	}

	/**
	 * Gets the endItmSttsCd
	 *
	 * @return the endItmSttsCd
	 */
	public String getEndItmSttsCd() {
		return endItmSttsCd;
	}

	/**
	 * Sets the endItmSttsCd
	 *
	 * @param endItmSttsCd the endItmSttsCd to set
	 */
	public void setEndItmSttsCd(String endItmSttsCd) {
		this.endItmSttsCd = endItmSttsCd;
	}

	/**
	 * Gets the crtdUsrId
	 *
	 * @return the crtdUsrId
	 */
	public String getCrtdUsrId() {
		return crtdUsrId;
	}

	/**
	 * Sets the crtdUsrId
	 *
	 * @param crtdUsrId the crtdUsrId to set
	 */
	public void setCrtdUsrId(String crtdUsrId) {
		this.crtdUsrId = crtdUsrId;
	}

	/**
	 * Gets the crtdDtTime
	 *
	 * @return the crtdDtTime
	 */
	public String getCrtdDtTime() {
		return crtdDtTime;
	}

	/**
	 * Sets the crtdDtTime
	 *
	 * @param crtdDtTime the crtdDtTime to set
	 */
	public void setCrtdDtTime(String crtdDtTime) {
		this.crtdDtTime = crtdDtTime;
	}

	/**
	 * Gets the updtdUsrId
	 *
	 * @return the updtdUsrId
	 */
	public String getUpdtdUsrId() {
		return updtdUsrId;
	}

	/**
	 * Sets the updtdUsrId
	 *
	 * @param updtdUsrId the updtdUsrId to set
	 */
	public void setUpdtdUsrId(String updtdUsrId) {
		this.updtdUsrId = updtdUsrId;
	}

	/**
	 * Gets the updtDtTime
	 *
	 * @return the updtDtTime
	 */
	public String getUpdtDtTime() {
		return updtDtTime;
	}

	/**
	 * Sets the updtDtTime
	 *
	 * @param updtDtTime the updtDtTime to set
	 */
	public void setUpdtDtTime(String updtDtTime) {
		this.updtDtTime = updtDtTime;
	}
}