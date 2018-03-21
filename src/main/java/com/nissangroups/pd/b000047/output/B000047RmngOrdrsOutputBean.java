/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.output;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from LOGICAL_PIPELINE_TRN, OCF_REGION_MST,BUYER_MST,POR_CAR_SERIES_MST database table values.
 * 
 * @author z016127
 */
@Entity
public class B000047RmngOrdrsOutputBean {
	
	
	/**Output Parameter Row Number */
	@Id
	@Column(name = "ROWNUM")
	private String rownum;
	
	/** Output Parameter Por Cd. */
	@Column(name="POR_CD")
	private String porCd;
	
	/** Output Parameter Ocf Region Cd. */
	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;
	
	/** Output Parameter Production month */
	@Column(name="PROD_MNTH")
	private String prodMnth;
	
	/** Output Parameter car series */
	@Column(name="CAR_SRS")
	private String carSrs;
	
	/** Output Parameter buyer code. */
	@Column(name="BUYER_CD")
	private String buyerCd;
	
	/** Output Parameter production week no */
	@Column(name="PROD_WK_NO")
	private String prodWkNo;
	
	/** Output Parameter production order no */
	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;
	
	/** Output Parameter applied model cd */
	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;
	
	/** Output Parameter pack cd */
	@Column(name="PCK_CD")
	private String pckCd;
	
	/** Output Parameter exterior color cd */
	@Column(name="EXT_CLR_CD")
	private String extClrCd;
	
	/** Output Parameter interior color cd */
	@Column(name="INT_CLR_CD")
	private String intClrCd;
	
	/** Output Parameter spec destination cd */
	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;
	
	/** Output Parameter sales note no */
	@Column(name="SALES_NOTE_NO")
	private String salesNoteNo;
	
	/** Output Parameter exno */
	@Column(name="EX_NO")
	private String exNo;
	
	/** Output Parameter offline plan date */
	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;
	
	/** Output Parameter production plant cd */
	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	/**
	 * Get the rownum
	 *
	 * @return the rownum
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * Sets the rownum
	 *
	 * @param rownum the rownum to set
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
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
	 * Sets the porCd
	 *
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
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
	 * Sets the ocfRegionCd
	 *
	 * @param ocfRegionCd the ocfRegionCd to set
	 */
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
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
	 * Sets the prodMnth
	 *
	 * @param prodMnth the prodMnth to set
	 */
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
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
	 * Sets the carSrs
	 *
	 * @param carSrs the carSrs to set
	 */
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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
	 * Sets the buyerCd
	 *
	 * @param buyerCd the buyerCd to set
	 */
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
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
	 * Sets the prodWkNo
	 *
	 * @param prodWkNo the prodWkNo to set
	 */
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
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
	 * Sets the prodOrdrNo
	 *
	 * @param prodOrdrNo the prodOrdrNo to set
	 */
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
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
	 * Sets the appldMdlCd
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
	 * Sets the pckCd
	 *
	 * @param pckCd the pckCd to set
	 */
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
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
	 * Sets the extClrCd
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
	 * Sets the intClrCd
	 *
	 * @param intClrCd the intClrCd to set
	 */
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
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
	 * Sets the specDestnCd
	 *
	 * @param specDestnCd the specDestnCd to set
	 */
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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
	 * Sets the salesNoteNo
	 *
	 * @param salesNoteNo the salesNoteNo to set
	 */
	public void setSalesNoteNo(String salesNoteNo) {
		this.salesNoteNo = salesNoteNo;
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
	 * Sets the exNo
	 *
	 * @param exNo the exNo to set
	 */
	public void setExNo(String exNo) {
		this.exNo = exNo;
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
	 * Sets the offlnPlanDate
	 *
	 * @param offlnPlanDate the offlnPlanDate to set
	 */
	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
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
	 * Sets the prodPlntCd
	 *
	 * @param prodPlntCd the prodPlntCd to set
	 */
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	
}