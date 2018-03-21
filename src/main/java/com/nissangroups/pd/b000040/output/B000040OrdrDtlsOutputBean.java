/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000065 SEND FILE using FTP
 * Module          :
 * Process Outline :Send File details vo     
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000040.output;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class B000040OrdrDtlsOutputBean {
	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="DEALER_LIST")
	private String dealerList;

	@Column(name="END_ITEM")
	private String endItem;

	@Column(name="EX_NO")
	private String exNo;

	private String frozentypecd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Id
	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="OWNER_MANUAL")
	private String ownerManual;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_DAY_NO")
	private String prodDayNo;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="ROW_NO")
	private BigDecimal rowNo;

	@Column(name="SALES_NOTE_NO")
	private String salesNoteNo;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="WARRANTY_BOOKLET")
	private String warrantyBooklet;
	
	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}

	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
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

	public String getDealerList() {
		return this.dealerList;
	}

	public void setDealerList(String dealerList) {
		this.dealerList = dealerList;
	}

	public String getEndItem() {
		return this.endItem;
	}

	public void setEndItem(String endItem) {
		this.endItem = endItem;
	}

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getFrozentypecd() {
		return this.frozentypecd;
	}

	public void setFrozentypecd(String frozentypecd) {
		this.frozentypecd = frozentypecd;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getOseiId() {
		return this.oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getOwnerManual() {
		return this.ownerManual;
	}

	public void setOwnerManual(String ownerManual) {
		this.ownerManual = ownerManual;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdDayNo() {
		return this.prodDayNo;
	}

	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}

	public String getProdFmyCd() {
		return this.prodFmyCd;
	}

	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}

	public String getProdMnth() {
		return this.prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getProdWkNo() {
		return this.prodWkNo;
	}

	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	public BigDecimal getRowNo() {
		return this.rowNo;
	}

	public void setRowNo(BigDecimal rowNo) {
		this.rowNo = rowNo;
	}

	public String getSalesNoteNo() {
		return this.salesNoteNo;
	}

	public void setSalesNoteNo(String salesNoteNo) {
		this.salesNoteNo = salesNoteNo;
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	public String getWarrantyBooklet() {
		return this.warrantyBooklet;
	}

	public void setWarrantyBooklet(String warrantyBooklet) {
		this.warrantyBooklet = warrantyBooklet;
	}
}
