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

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;

/**
 * The Class B000047PipelineTrnDtls create error report header names
 * 
 * @author z016127
 */
@Scope("job")
public class B000047ReportDao{

	/** Variable error type code. */
	private String warngCd;

	/** Variable por cd. */
	private String porCd;

	/** Variable Ocf Region Cd. */
	private String ocfRgnCd;

	/** Variable Production Month. */
	private String prodMnth;
	
	/** Variable Car Series. */
	private String carSrs;

	/** Variable Buyer Cd. */
	private String buyerCd;

	/** Variable Order Production Week No. */
	private String ordrProdWkNo;

	/** Variable Vin Production Week No. */
	private String vinProdWkNo;

	/** Variable Production Order No. */
	private String prodOrdrNo;

	/** Variable Vin No. */
	private String vinNo;

	/** Variable End Item. */
	private String endItem;

	/** Variable Color Code. */
	private String colorCd;

	/** Variable Spec Destination Cd. */
	private String specDestnCd;

	/** Variable Sales Note No. */
	private String salesNteNo;
	
	/** Variable Ex No. */
	private String exNo;

	/** Variable Order Offline Date. */
	private String ordrOfflnDte_YYYY_MM_DD;;

	/** Variable Vin Offline Date. */
	private String vinOfflnDte_YYYY_MM_DD;

	/** Variable Order Plant Cd. */
	private String ordrPlntCd;

	/** Variable Vin Plant Cd. */
	private String vinPlntCd;
	
	/** Variable error_message. */
	private String warngMsg;

	/** Variable report list. */
	private List<B000047ReportDao> reportList = new ArrayList<B000047ReportDao>();
	
	/** Variable vins report list. */
	private List<B000047ReportDao> vinsReportList = new ArrayList<B000047ReportDao>();
	
	/** Variable remaining orders report list. */
	private List<B000047ReportDao> rmngOrdrsReportList = new ArrayList<B000047ReportDao>();

	/**
	 * Get the warngCd
	 *
	 * @return the warngCd
	 */
	public String getWarngCd() {
		return warngCd;
	}

	/**
	 * Sets the warngCd
	 *
	 * @param warngCd the warngCd to set
	 */
	public void setWarngCd(String warngCd) {
		this.warngCd = warngCd;
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
	 * Get the ocfRgnCd
	 *
	 * @return the ocfRgnCd
	 */
	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	/**
	 * Sets the ocfRgnCd
	 *
	 * @param ocfRgnCd the ocfRgnCd to set
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
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
	 * Get the ordrProdWkNo
	 *
	 * @return the ordrProdWkNo
	 */
	public String getOrdrProdWkNo() {
		return ordrProdWkNo;
	}

	/**
	 * Sets the ordrProdWkNo
	 *
	 * @param ordrProdWkNo the ordrProdWkNo to set
	 */
	public void setOrdrProdWkNo(String ordrProdWkNo) {
		this.ordrProdWkNo = ordrProdWkNo;
	}

	/**
	 * Get the vinProdWkNo
	 *
	 * @return the vinProdWkNo
	 */
	public String getVinProdWkNo() {
		return vinProdWkNo;
	}

	/**
	 * Sets the vinProdWkNo
	 *
	 * @param vinProdWkNo the vinProdWkNo to set
	 */
	public void setVinProdWkNo(String vinProdWkNo) {
		this.vinProdWkNo = vinProdWkNo;
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
	 * Get the vinNo
	 *
	 * @return the vinNo
	 */
	public String getVinNo() {
		return vinNo;
	}

	/**
	 * Sets the vinNo
	 *
	 * @param vinNo the vinNo to set
	 */
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	/**
	 * Get the endItem
	 *
	 * @return the endItem
	 */
	public String getEndItem() {
		return endItem;
	}

	/**
	 * Sets the endItem
	 *
	 * @param endItem the endItem to set
	 */
	public void setEndItem(String endItem) {
		this.endItem = endItem;
	}

	/**
	 * Get the colorCd
	 *
	 * @return the colorCd
	 */
	public String getColorCd() {
		return colorCd;
	}

	/**
	 * Sets the colorCd
	 *
	 * @param colorCd the colorCd to set
	 */
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
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
	 * Get the salesNteNo
	 *
	 * @return the salesNteNo
	 */
	public String getSalesNteNo() {
		return salesNteNo;
	}

	/**
	 * Sets the salesNteNo
	 *
	 * @param salesNteNo the salesNteNo to set
	 */
	public void setSalesNteNo(String salesNteNo) {
		this.salesNteNo = salesNteNo;
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
	 * Get the ordrOfflnDte_YYYY_MM_DD
	 *
	 * @return the ordrOfflnDte_YYYY_MM_DD
	 */
	public String getOrdrOfflnDte_YYYY_MM_DD() {
		return ordrOfflnDte_YYYY_MM_DD;
	}

	/**
	 * Sets the ordrOfflnDte_YYYY_MM_DD
	 *
	 * @param ordrOfflnDte_YYYY_MM_DD the ordrOfflnDte_YYYY_MM_DD to set
	 */
	public void setOrdrOfflnDte_YYYY_MM_DD(String ordrOfflnDte_YYYY_MM_DD) {
		this.ordrOfflnDte_YYYY_MM_DD = ordrOfflnDte_YYYY_MM_DD;
	}

	/**
	 * Get the vinOfflnDte_YYYY_MM_DD
	 *
	 * @return the vinOfflnDte_YYYY_MM_DD
	 */
	public String getVinOfflnDte_YYYY_MM_DD() {
		return vinOfflnDte_YYYY_MM_DD;
	}

	/**
	 * Sets the vinOfflnDte_YYYY_MM_DD
	 *
	 * @param vinOfflnDte_YYYY_MM_DD the vinOfflnDte_YYYY_MM_DD to set
	 */
	public void setVinOfflnDte_YYYY_MM_DD(String vinOfflnDte_YYYY_MM_DD) {
		this.vinOfflnDte_YYYY_MM_DD = vinOfflnDte_YYYY_MM_DD;
	}

	/**
	 * Get the ordrPlntCd
	 *
	 * @return the ordrPlntCd
	 */
	public String getOrdrPlntCd() {
		return ordrPlntCd;
	}

	/**
	 * Sets the ordrPlntCd
	 *
	 * @param ordrPlntCd the ordrPlntCd to set
	 */
	public void setOrdrPlntCd(String ordrPlntCd) {
		this.ordrPlntCd = ordrPlntCd;
	}

	/**
	 * Get the vinPlntCd
	 *
	 * @return the vinPlntCd
	 */
	public String getVinPlntCd() {
		return vinPlntCd;
	}

	/**
	 * Sets the vinPlntCd
	 *
	 * @param vinPlntCd the vinPlntCd to set
	 */
	public void setVinPlntCd(String vinPlntCd) {
		this.vinPlntCd = vinPlntCd;
	}

	/**
	 * Get the warngMsg
	 *
	 * @return the warngMsg
	 */
	public String getWarngMsg() {
		return warngMsg;
	}

	/**
	 * Sets the warngMsg
	 *
	 * @param warngMsg the warngMsg to set
	 */
	public void setWarngMsg(String warngMsg) {
		this.warngMsg = warngMsg;
	}

	/**
	 * Get the reportList
	 *
	 * @return the reportList
	 */
	public List<B000047ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the reportList
	 *
	 * @param reportList the reportList to set
	 */
	public void setReportList(List<B000047ReportDao> reportList) {
		this.reportList = reportList;
	}

	/**
	 * Get the vinsReportList
	 *
	 * @return the vinsReportList
	 */
	public List<B000047ReportDao> getVinsReportList() {
		return vinsReportList;
	}

	/**
	 * Sets the vinsReportList
	 *
	 * @param vinsReportList the vinsReportList to set
	 */
	public void setVinsReportList(List<B000047ReportDao> vinsReportList) {
		this.vinsReportList = vinsReportList;
	}

	/**
	 * Get the rmngOrdrsReportList
	 *
	 * @return the rmngOrdrsReportList
	 */
	public List<B000047ReportDao> getRmngOrdrsReportList() {
		return rmngOrdrsReportList;
	}

	/**
	 * Sets the rmngOrdrsReportList
	 *
	 * @param rmngOrdrsReportList the rmngOrdrsReportList to set
	 */
	public void setRmngOrdrsReportList(List<B000047ReportDao> rmngOrdrsReportList) {
		this.rmngOrdrsReportList = rmngOrdrsReportList;
	}

}
