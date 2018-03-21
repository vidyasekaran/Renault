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


/**
 * The Class B000047PipelineTrnDtls is to store the extracted column values from TRN_LGCL_PPLN database table values.
 * 
 * @author z016127
 */
public class B000047PipelineTrnDtls 
{

	/** Variable Ocf Region Cd */
	private String ocfRegionCd;
	
	/** Variable Production Month */
	private String prodMnth;
	
	/** Variable Car Series */
	private String carSrs;
	
	/** Variable Buyer Code */
	private String buyerCd;
	
	/** Variable Production Week No */
	private String prodWkNo;
	
	/** Variable Production Wk Number */
	private String prodWeekNo;
	
	/** Variable Production Order No */
	private String prodOrdrNo;
	
	/** Variable Vin No */
	private String vinNo;
	
	/** Variable End Item */
	private String endItem;
	
	/** Variable Color Cd */
	private String clrCd;
	
	/** Variable Spec Destination Cd */
	private String specDestnCd;
	
	/** Variable Sls Note No */
	private String slsNteNo;
	
	/** Variable Ex No */
	private String exNo;
	
	/** Variable Planned Offline Date */
	private String plndOfflnDate;
	
	/** Variable Planned Offline Date */
	private String offLnPlndDate;
	
	/** Variable Production Plant Cd */
	private String prodPlntCd;
	
	/** Variable Production Plant Cd */
	private String prodPlantCd;
	
	/** Variable Osei Id */
	private String oseiId;
	
	/** Variable Vehicle Sequence Id */
	private String vhcleSeqId;
	
	/** Variable Pot cd */
	private String potCd;
	
	/** Variable Case Error */
	private String cseErr;

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
	 * Get the prodWeekNo
	 *
	 * @return the prodWeekNo
	 */
	public String getProdWeekNo() {
		return prodWeekNo;
	}

	/**
	 * Sets the prodWeekNo
	 *
	 * @param prodWeekNo the prodWeekNo to set
	 */
	public void setProdWeekNo(String prodWeekNo) {
		this.prodWeekNo = prodWeekNo;
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
	 * Get the clrCd
	 *
	 * @return the clrCd
	 */
	public String getClrCd() {
		return clrCd;
	}

	/**
	 * Sets the clrCd
	 *
	 * @param clrCd the clrCd to set
	 */
	public void setClrCd(String clrCd) {
		this.clrCd = clrCd;
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
	 * Get the slsNteNo
	 *
	 * @return the slsNteNo
	 */
	public String getSlsNteNo() {
		return slsNteNo;
	}

	/**
	 * Sets the slsNteNo
	 *
	 * @param slsNteNo the slsNteNo to set
	 */
	public void setSlsNteNo(String slsNteNo) {
		this.slsNteNo = slsNteNo;
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
	 * Get the plndOfflnDate
	 *
	 * @return the plndOfflnDate
	 */
	public String getPlndOfflnDate() {
		return plndOfflnDate;
	}

	/**
	 * Sets the plndOfflnDate
	 *
	 * @param plndOfflnDate the plndOfflnDate to set
	 */
	public void setPlndOfflnDate(String plndOfflnDate) {
		this.plndOfflnDate = plndOfflnDate;
	}

	/**
	 * Get the offLnPlndDate
	 *
	 * @return the offLnPlndDate
	 */
	public String getOffLnPlndDate() {
		return offLnPlndDate;
	}

	/**
	 * Sets the offLnPlndDate
	 *
	 * @param offLnPlndDate the offLnPlndDate to set
	 */
	public void setOffLnPlndDate(String offLnPlndDate) {
		this.offLnPlndDate = offLnPlndDate;
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

	/**
	 * Get the prodPlantCd
	 *
	 * @return the prodPlantCd
	 */
	public String getProdPlantCd() {
		return prodPlantCd;
	}

	/**
	 * Sets the prodPlantCd
	 *
	 * @param prodPlantCd the prodPlantCd to set
	 */
	public void setProdPlantCd(String prodPlantCd) {
		this.prodPlantCd = prodPlantCd;
	}

	/**
	 * Get the oseiId
	 *
	 * @return the oseiId
	 */
	public String getOseiId() {
		return oseiId;
	}

	/**
	 * Sets the oseiId
	 *
	 * @param oseiId the oseiId to set
	 */
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	/**
	 * Get the vhcleSeqId
	 *
	 * @return the vhcleSeqId
	 */
	public String getVhcleSeqId() {
		return vhcleSeqId;
	}

	/**
	 * Sets the vhcleSeqId
	 *
	 * @param vhcleSeqId the vhcleSeqId to set
	 */
	public void setVhcleSeqId(String vhcleSeqId) {
		this.vhcleSeqId = vhcleSeqId;
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
	 * Sets the potCd
	 *
	 * @param potCd the potCd to set
	 */
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	/**
	 * Get the cseErr
	 *
	 * @return the cseErr
	 */
	public String getCseErr() {
		return cseErr;
	}

	/**
	 * Sets the cseErr
	 *
	 * @param cseErr the cseErr to set
	 */
	public void setCseErr(String cseErr) {
		this.cseErr = cseErr;
	}
	
}
