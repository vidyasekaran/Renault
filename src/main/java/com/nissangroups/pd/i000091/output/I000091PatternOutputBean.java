/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.i000091.output;

import java.io.Serializable;

/**
 * Class I000091PatternOutputBean is to set the extracted column values from TRN_REGIONAL_WKLY_OCF_LMT database table values.
 * 
 * @author z016127
 *
 */
public class I000091PatternOutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**Output Parameter Row Number */
	private String rownum;
	
	/**Output Parameter Por Code */
	private String porCd;
	
	/**Output Parameter Car Series */
	private String carSrs;

	/**Output Parameter Feature Code */
	private String featCd;

	/**Output Parameter Feature Type Code */
	private String featTypeCd;

	/**Output Parameter Production Month */
	private String prodMnth;

	/**Output Parameter Production Week Number */
	private String prodWkNo;

	/**Output Parameter Production Day No */
	private String prodDayNo;

	/**Output Parameter Line Class */
	private String lineClass;

	/**Output Parameter Plant Code */
	private String plantCd;
	
	/**Output Parameter Buyer Group Code */
	private String buyerGrpCd;

	/**Output Parameter Regional Ocf Limit Quantity */
	private String rgnlOcfLmtQty;

	/**Output Parameter Regional Ocf Usage Quantity */
	private String rgnlOcfUsgQty;
	
	/**Output Parameter Production Period Code */
	private String prodPerdCd;

	/**Output Parameter Created BY */
	private String crtdBy;

	/**Output Parameter Created Date */
	private String crtdDt;

	/**Output Parameter Updated By */
	private String updtdBy;

	/**Output Parameter Updated Date */
	private String updtdDt;

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
	 * Get the featCd
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
	 * Get the featTypeCd
	 *
	 * @return the featTypeCd
	 */
	public String getFeatTypeCd() {
		return featTypeCd;
	}

	/**
	 * Sets the featTypeCd
	 *
	 * @param featTypeCd the featTypeCd to set
	 */
	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
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
	 * Get the prodDayNo
	 *
	 * @return the prodDayNo
	 */
	public String getProdDayNo() {
		return prodDayNo;
	}

	/**
	 * Sets the prodDayNo
	 *
	 * @param prodDayNo the prodDayNo to set
	 */
	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
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
	 * Sets the lineClass
	 *
	 * @param lineClass the lineClass to set
	 */
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	/**
	 * Get the plantCd
	 *
	 * @return the plantCd
	 */
	public String getPlantCd() {
		return plantCd;
	}

	/**
	 * Sets the plantCd
	 *
	 * @param plantCd the plantCd to set
	 */
	public void setPlantCd(String plantCd) {
		this.plantCd = plantCd;
	}

	/**
	 * Get the buyerGrpCd
	 *
	 * @return the buyerGrpCd
	 */
	public String getBuyerGrpCd() {
		return buyerGrpCd;
	}

	/**
	 * Sets the buyerGrpCd
	 *
	 * @param buyerGrpCd the buyerGrpCd to set
	 */
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	/**
	 * Get the rgnlOcfLmtQty
	 *
	 * @return the rgnlOcfLmtQty
	 */
	public String getRgnlOcfLmtQty() {
		return rgnlOcfLmtQty;
	}

	/**
	 * Sets the rgnlOcfLmtQty
	 *
	 * @param rgnlOcfLmtQty the rgnlOcfLmtQty to set
	 */
	public void setRgnlOcfLmtQty(String rgnlOcfLmtQty) {
		this.rgnlOcfLmtQty = rgnlOcfLmtQty;
	}

	/**
	 * Get the rgnlOcfUsgQty
	 *
	 * @return the rgnlOcfUsgQty
	 */
	public String getRgnlOcfUsgQty() {
		return rgnlOcfUsgQty;
	}

	/**
	 * Sets the rgnlOcfUsgQty
	 *
	 * @param rgnlOcfUsgQty the rgnlOcfUsgQty to set
	 */
	public void setRgnlOcfUsgQty(String rgnlOcfUsgQty) {
		this.rgnlOcfUsgQty = rgnlOcfUsgQty;
	}

	/**
	 * Get the prodPerdCd
	 *
	 * @return the prodPerdCd
	 */
	public String getProdPerdCd() {
		return prodPerdCd;
	}

	/**
	 * Sets the prodPerdCd
	 *
	 * @param prodPerdCd the prodPerdCd to set
	 */
	public void setProdPerdCd(String prodPerdCd) {
		this.prodPerdCd = prodPerdCd;
	}

	/**
	 * Get the crtdBy
	 *
	 * @return the crtdBy
	 */
	public String getCrtdBy() {
		return crtdBy;
	}

	/**
	 * Sets the crtdBy
	 *
	 * @param crtdBy the crtdBy to set
	 */
	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	/**
	 * Get the crtdDt
	 *
	 * @return the crtdDt
	 */
	public String getCrtdDt() {
		return crtdDt;
	}

	/**
	 * Sets the crtdDt
	 *
	 * @param crtdDt the crtdDt to set
	 */
	public void setCrtdDt(String crtdDt) {
		this.crtdDt = crtdDt;
	}

	/**
	 * Get the updtdBy
	 *
	 * @return the updtdBy
	 */
	public String getUpdtdBy() {
		return updtdBy;
	}

	/**
	 * Sets the updtdBy
	 *
	 * @param updtdBy the updtdBy to set
	 */
	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	/**
	 * Get the updtdDt
	 *
	 * @return the updtdDt
	 */
	public String getUpdtdDt() {
		return updtdDt;
	}

	/**
	 * Sets the updtdDt
	 *
	 * @param updtdDt the updtdDt to set
	 */
	public void setUpdtdDt(String updtdDt) {
		this.updtdDt = updtdDt;
	}
}
