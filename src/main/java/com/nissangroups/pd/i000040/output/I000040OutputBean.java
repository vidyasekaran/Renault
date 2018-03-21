/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000040
 * Module                 : OR Ordering		
 * Process Outline     	  : Send A0 ETA Designated parameter to PCS															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000040.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
* The persistent class for extracted column values from A0 ETA PARAMETER TRN and ORDERABLE END ITEM SPEC MST database table values.
* 
* @author z016127
*
*/
@Entity
public class I000040OutputBean implements Serializable{

	/**
	 * Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;
	
	public long getRowNum() {
		return rowNum;
	}

	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	@Column(name = "POR_CD")
	private String porCd;
	

	@Column(name = "PROD_PLNT_CD")
	private String prodPlntCd;
	
	@Column(name = "PROD_MNTH")
	private String prodMnth;
	
	@Column(name = "APPLD_MDL_CD")
	private String appldMdlCd;
	
	@Column(name = "PCK_CD")
	private String pckCd;
	
	@Column(name = "EX_NO")
	private String exNo;
	
	@Column(name = "SPEC_DESTN_CD")
	private String specDstnCd;
	
	
	@Column(name = "PROD_FMY_CD")
	private String prdFmlyCd;
	
	@Column(name = "OCF_REGION_CD")
	private String ocfRegion;
	
	@Column(name = "CAR_SRS")
	private String carSrs;
	
	@Column(name = "DATE_FRM_1")
	private String dateFrm1;
	
	@Column(name = "DATE_TO_1")
	private String dateTo1;
	
	@Column(name = "QTY_1")
	private String qty1;
	
	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getProdPlntCd() {
		return prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getProdMnth() {
		return prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getPckCd() {
		return pckCd;
	}

	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getSpecDstnCd() {
		return specDstnCd;
	}

	public void setSpecDstnCd(String specDstnCd) {
		this.specDstnCd = specDstnCd;
	}

	public String getPrdFmlyCd() {
		return prdFmlyCd;
	}

	public void setPrdFmlyCd(String prdFmlyCd) {
		this.prdFmlyCd = prdFmlyCd;
	}

	public String getOcfRegion() {
		return ocfRegion;
	}

	public void setOcfRegion(String ocfRegion) {
		this.ocfRegion = ocfRegion;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getDateFrm1() {
		return dateFrm1;
	}

	public void setDateFrm1(String dateFrm1) {
		this.dateFrm1 = dateFrm1;
	}

	public String getDateTo1() {
		return dateTo1;
	}

	public void setDateTo1(String dateTo1) {
		this.dateTo1 = dateTo1;
	}

	public String getQty1() {
		return qty1;
	}

	public void setQty1(String qty1) {
		this.qty1 = qty1;
	}
	
}
