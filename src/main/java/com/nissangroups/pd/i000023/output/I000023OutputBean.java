/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000023
 * Module          :Send_OSEI_Frozen_Interface_to_NSC
 
 * Process Outline : Send the OSEI frozen master details to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.i000023.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This Class is used to set and retrieve the output parameter value and allow control over the values passed
 *
 * @author z014029
 */
@Entity
public class I000023OutputBean implements Serializable {
			
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
	
	/** Interface Output Parameter APPLIED MODEL CD */
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;
	
	/** Interface Output Parameter PACK CD */
	@Column(name = "PCK_CD")
	private String pckCd;
	
	/** Interface Output Parameter BUYER CD */
	@Column(name = "BUYER_CD")
	private String byrCd;
	
	/** Interface Output Parameter SPEC DESTINATION CD */
	@Column(name = "SPEC_DESTN_CD")
	private String specDest;
	
	/** Interface Output Parameter ADDTIONAL SPEC CD */
	@Column(name = "ADTNL_SPEC_CD")
	private String adtnlSpecCd;
	
	/** Interface Output Parameter EXTERNAL COLOR CD */
	@Column(name = "EXT_CLR_CD")
	private String extClrCd;
	
	/** Interface Output Parameter INTERNAL COLOR CD */
	@Column(name = "INT_CLR_CD")
	private String intClrCd;
	
	/** Interface Output Parameter FROZEN ORDER TAKE BASE MONTH */
	@Column(name = "FRZN_ORDR_TAKE_BASE_MNTH")
	private String frznOrdrTkBsMnth;
	
	/** Interface Output Parameter FROZEN PRODUCTION MONTH */
	@Column(name = "FRZN_PROD_MNTH")
	private String frznPrdMnth;
	
	/** Interface Output Parameter FROZEN TYPE CD */
	@Column(name = "FRZN_TYPE_CD")
	private String frznTypCD; 	
	
	/** Interface Output Parameter CREATED BY */
	@Column(name = "CRTD_BY")
	private String crtdUsrId;
	
	/** Interface Output Parameter CREATED DATE */
	@Column(name = "CRTD_DT")
	private String crtdDtTime;
	
	/** Interface Output Parameter UPDATED BY */
	@Column(name = "UPDTD_BY")
	private String updtdUsrId;
	
	/** Interface Output Parameter UPDATED DATE */
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
	 * @param rowNum the adtnlSpecCd to set
	 */
	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}
	
	/**
	 * Gets the extClrCd
	 *
	 * @return the extClrCd
	 */
	public String getExtClrCd() {
		return extClrCd;
	}
	
	/**
	 * Sets the extClrCd
	 *
	 * @param extClrCd the extClrCd to set
	 */
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}
	
	/**
	 * Gets the intClrCd
	 *
	 * @return the intClrCd
	 */
	public String getIntClrCd() {
		return intClrCd;
	}
	
	/**
	 * Sets the intClrCd
	 *
	 * @param intClrCd the intClrCd to set
	 */
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}
	
	/**
	 * Gets the frznOrdrTkBsMnth
	 *
	 * @return the frznOrdrTkBsMnth
	 */
	public String getFrznOrdrTkBsMnth() {
		return frznOrdrTkBsMnth;
	}
	
	/**
	 * Sets the frznOrdrTkBsMnth
	 *
	 * @param frznOrdrTkBsMnth the frznOrdrTkBsMnth to set
	 */
	public void setFrznOrdrTkBsMnth(String frznOrdrTkBsMnth) {
		this.frznOrdrTkBsMnth = frznOrdrTkBsMnth;
	}
	
	/**
	 * Gets the frznPrdMnth
	 *
	 * @return the frznPrdMnth
	 */
	public String getFrznPrdMnth() {
		return frznPrdMnth;
	}
	
	/**
	 * Sets the frznPrdMnth
	 *
	 * @param frznPrdMnth the frznPrdMnth to set
	 */
	public void setFrznPrdMnth(String frznPrdMnth) {
		this.frznPrdMnth = frznPrdMnth;
	}
	
	/**
	 * Gets the frznTypCD
	 *
	 * @return the frznTypCD
	 */
	public String getFrznTypCD() {
		return frznTypCD;
	}
	
	/**
	 * Sets the frznTypCD
	 *
	 * @param frznTypCD the frznTypCD to set
	 */
	public void setFrznTypCD(String frznTypCD) {
		this.frznTypCD = frznTypCD;
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