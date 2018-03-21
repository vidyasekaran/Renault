/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000027
 * Module          :Send Monthly OCF Interface to NSC
 
 * Process Outline :Send Monthly OCF Interface to NSC
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 27-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000027.output;

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
public class I000027OutputBean implements Serializable{

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Interface Output Parameter ROW NUM */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;	
	
	/** Interface Output Parameter POR CD */
	@Column(name = "POR_CD")
	private String porCd;
	
	/** Interface Output Parameter CAR SRS */
	@Column(name = "CAR_SRS")
	private String crSrs;
	
	/** Interface Output Parameter FEAT CD */
	@Column(name = "FEAT_CD")
	private String featCd;
	
	/** Interface Output Parameter OCF FRME CD */
	@Column(name = "OCF_FRME_CD")
	private String ocfFrmCd;
	
	/** Interface Output Parameter FEAT SHRT DESC */
	@Column(name = "FEAT_SHRT_DESC")
	private String featShrtDesc;
	
	/** Interface Output Parameter OCF BUYER GRP CD */
	@Column(name = "OCF_BUYER_GRP_CD")
	private String ocfByrGrpCd;
	
	/** Interface Output Parameter CAR GRP */
	@Column(name = "CAR_GRP")
	private String crGrp;
	
	/** Interface Output Parameter FEAT TYPE CD */
	@Column(name = "FEAT_TYPE_CD")
	private String featTypCd;
	
	/** Interface Output Parameter ORDR TAKE BASE MNTH */
	@Column(name = "ORDR_TAKE_BASE_MNTH")
	private String ordrTkBsMnth;
	
	/** Interface Output Parameter PROD MNTH */
	@Column(name = "PROD_MNTH")
	private String prdMnth;
	
	/** Interface Output Parameter BUYER GRP CD */
	@Column(name = "BUYER_GRP_CD")
	private String byrGrpCd;
	
	/** Interface Output Parameter BUYER GRP OCF USG QTY */
	@Column(name = "BUYER_GRP_OCF_USG_QTY")
	private String byrGrpOcfUsgQty;
	
	/** Interface Output Parameter BUYER GRP OCF LMT QTY */
	@Column(name = "BUYER_GRP_OCF_LMT_QTY")
	private String byrGrpOcfLmtQty;
	
	/** Interface Output Parameter CRTD BY */
	@Column(name = "CRTD_BY")
	private String crtdUsrId;
	
	/** Interface Output Parameter CRTD DT */
	@Column(name = "CRTD_DT")
	private String crtdDtTime;
	
	/** Interface Output Parameter UPDTD BY */
	@Column(name = "UPDTD_BY")
	private String updtdUsrId;
	
	/** Interface Output Parameter UPDTD DT */
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
	
	/**
	 * Gets the featCd
	 *
	 * @return the featCd
	 */
	public String getFeatCd() {
		return featCd;
	}
	
	/**
	 * Sets the featCd
	 *
	 * @param featCd the featCd to set
	 */
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}
	
	/**
	 * Gets the ocfFrmCd
	 *
	 * @return the ocfFrmCd
	 */
	public String getOcfFrmCd() {
		return ocfFrmCd;
	}
	
	/**
	 * Sets the ocfFrmCd
	 *
	 * @param ocfFrmCd the ocfFrmCd to set
	 */
	public void setOcfFrmCd(String ocfFrmCd) {
		this.ocfFrmCd = ocfFrmCd;
	}
	
	/**
	 * Gets the featShrtDesc
	 *
	 * @return the featShrtDesc
	 */
	public String getFeatShrtDesc() {
		return featShrtDesc;
	}
	
	/**
	 * Sets the featShrtDesc
	 *
	 * @param featShrtDesc the featShrtDesc to set
	 */
	public void setFeatShrtDesc(String featShrtDesc) {
		this.featShrtDesc = featShrtDesc;
	}
	
	/**
	 * Gets the ocfByrGrpCd
	 *
	 * @return the ocfByrGrpCd
	 */
	public String getOcfByrGrpCd() {
		return ocfByrGrpCd;
	}
	
	/**
	 * Sets the ocfByrGrpCd
	 *
	 * @param rowNum the ocfByrGrpCd to set
	 */
	public void setOcfByrGrpCd(String ocfByrGrpCd) {
		this.ocfByrGrpCd = ocfByrGrpCd;
	}
	
	/**
	 * Gets the crGrp
	 *
	 * @return the crGrp
	 */
	public String getCrGrp() {
		return crGrp;
	}
	
	/**
	 * Sets the crGrp
	 *
	 * @param crGrp the crGrp to set
	 */
	public void setCrGrp(String crGrp) {
		this.crGrp = crGrp;
	}
	
	/**
	 * Gets the featTypCd
	 *
	 * @return the featTypCd
	 */
	public String getFeatTypCd() {
		return featTypCd;
	}
	
	/**
	 * Sets the featTypCd
	 *
	 * @param featTypCd the featTypCd to set
	 */
	public void setFeatTypCd(String featTypCd) {
		this.featTypCd = featTypCd;
	}
	
	/**
	 * Gets the ordrTkBsMnth
	 *
	 * @return the ordrTkBsMnth
	 */
	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}
	
	/**
	 * Sets the ordrTkBsMnth
	 *
	 * @param ordrTkBsMnth the ordrTkBsMnth to set
	 */
	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}
	
	/**
	 * Gets the prdMnth
	 *
	 * @return the prdMnth
	 */
	public String getPrdMnth() {
		return prdMnth;
	}
	
	/**
	 * Sets the prdMnth
	 *
	 * @param prdMnth the prdMnth to set
	 */
	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}
	
	/**
	 * Gets the byrGrpCd
	 *
	 * @return the byrGrpCd
	 */
	public String getByrGrpCd() {
		return byrGrpCd;
	}
	
	/**
	 * Sets the byrGrpCd
	 *
	 * @param byrGrpCd the byrGrpCd to set
	 */
	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}
	
	/**
	 * Gets the byrGrpOcfUsgQty
	 *
	 * @return the byrGrpOcfUsgQty
	 */
	public String getByrGrpOcfUsgQty() {
		return byrGrpOcfUsgQty;
	}
	
	/**
	 * Sets the byrGrpOcfUsgQty
	 *
	 * @param byrGrpOcfUsgQty the byrGrpOcfUsgQty to set
	 */
	public void setByrGrpOcfUsgQty(String byrGrpOcfUsgQty) {
		this.byrGrpOcfUsgQty = byrGrpOcfUsgQty;
	}
	
	/**
	 * Gets the byrGrpOcfLmtQty
	 *
	 * @return the byrGrpOcfLmtQty
	 */
	public String getByrGrpOcfLmtQty() {
		return byrGrpOcfLmtQty;
	}
	
	/**
	 * Sets the byrGrpOcfLmtQty
	 *
	 * @param byrGrpOcfLmtQty the byrGrpOcfLmtQty to set
	 */
	public void setByrGrpOcfLmtQty(String byrGrpOcfLmtQty) {
		this.byrGrpOcfLmtQty = byrGrpOcfLmtQty;
	}
	
}