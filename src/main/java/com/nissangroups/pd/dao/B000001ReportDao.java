/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date        Name(Company Name)             Description 
 * ----------  ------------------------------ ---------------------
 * 03-Aug-2015 z002548(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;

/**
 * The Class B000001ReportDao.
 *
 * @author z002548
 */
@Scope("job")
public class B000001ReportDao implements Comparable<B000001ReportDao> {

	/** Variable error type code. */
	private String errorTypeCode;

	/** Variable seq id. */
	private String seqId;

	/** Variable por code. */
	private String porCode;

	/** Variable production family code. */
	private String productionFamilyCode;

	/** Variable production stage code. */
	private String productionStageCode;

	/** Variable buyer code. */
	private String buyerCode;

	/** Variable end item model code. */
	private String endItemModelCode;

	/** Variable color code. */
	private String colorCode;

	/** Variable addition spec code. */
	private String additionSpecCode;

	/** Variable spec destination code. */
	private String specDestinationCode;

	/** Variable adopt month. */
	private String adoptMonth;

	/** Variable abolist month. */
	private String abolistMonth;

	/** Variable comments. */
	private String comments;

	/** Variable internal errorcd. */
	private String internalErrorcd;

	/** report list. */
	private List<B000001ReportDao> reportList = new ArrayList<B000001ReportDao>();

	/**
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public List<B000001ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the report list.
	 *
	 * @param reportList
	 *            the new report list
	 */
	public void setReportList(List<B000001ReportDao> reportList) {
		this.reportList = reportList;
	}

	/**
	 * Gets the error type code.
	 *
	 * @return the error type code
	 */
	public String getErrorTypeCode() {
		return errorTypeCode;
	}

	/**
	 * Sets the error type code.
	 *
	 * @param errorTypeCode
	 *            the new error type code
	 */
	public void setErrorTypeCode(String errorTypeCode) {
		this.errorTypeCode = errorTypeCode;
	}

	/**
	 * Gets the seq id.
	 *
	 * @return the seq id
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * Sets the seq id.
	 *
	 * @param seqId
	 *            the new seq id
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * Gets the por code.
	 *
	 * @return the por code
	 */
	public String getPorCode() {
		return porCode;
	}

	/**
	 * Sets the por code.
	 *
	 * @param porCode
	 *            the new por code
	 */
	public void setPorCode(String porCode) {
		this.porCode = porCode;
	}

	/**
	 * Gets the production family code.
	 *
	 * @return the production family code
	 */
	public String getProductionFamilyCode() {
		return productionFamilyCode;
	}

	/**
	 * Sets the production family code.
	 *
	 * @param productionFamilyCode
	 *            the new production family code
	 */
	public void setProductionFamilyCode(String productionFamilyCode) {
		this.productionFamilyCode = productionFamilyCode;
	}

	/**
	 * Gets the production stage code.
	 *
	 * @return the production stage code
	 */
	public String getProductionStageCode() {
		return productionStageCode;
	}

	/**
	 * Sets the production stage code.
	 *
	 * @param productionStageCode
	 *            the new production stage code
	 */
	public void setProductionStageCode(String productionStageCode) {
		this.productionStageCode = productionStageCode;
	}

	/**
	 * Gets the buyer code.
	 *
	 * @return the buyer code
	 */
	public String getBuyerCode() {
		return buyerCode;
	}

	/**
	 * Sets the buyer code.
	 *
	 * @param buyerCode
	 *            the new buyer code
	 */
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	/**
	 * Gets the end item model code.
	 *
	 * @return the end item model code
	 */
	public String getEndItemModelCode() {
		return endItemModelCode;
	}

	/**
	 * Sets the end item model code.
	 *
	 * @param endItemModelCode
	 *            the new end item model code
	 */
	public void setEndItemModelCode(String endItemModelCode) {
		this.endItemModelCode = endItemModelCode;
	}

	/**
	 * Gets the color code.
	 *
	 * @return the color code
	 */
	public String getColorCode() {
		return colorCode;
	}

	/**
	 * Sets the color code.
	 *
	 * @param colorCode
	 *            the new color code
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	/**
	 * Gets the addition spec code.
	 *
	 * @return the addition spec code
	 */
	public String getAdditionSpecCode() {
		return additionSpecCode;
	}

	/**
	 * Sets the addition spec code.
	 *
	 * @param additionSpecCode
	 *            the new addition spec code
	 */
	public void setAdditionSpecCode(String additionSpecCode) {
		this.additionSpecCode = additionSpecCode;
	}

	/**
	 * Gets the spec destination code.
	 *
	 * @return the spec destination code
	 */
	public String getSpecDestinationCode() {
		return specDestinationCode;
	}

	/**
	 * Sets the spec destination code.
	 *
	 * @param specDestinationCode
	 *            the new spec destination code
	 */
	public void setSpecDestinationCode(String specDestinationCode) {
		this.specDestinationCode = specDestinationCode;
	}

	/**
	 * Gets the adopt month.
	 *
	 * @return the adopt month
	 */
	public String getAdoptMonth() {
		return adoptMonth;
	}

	/**
	 * Sets the adopt month.
	 *
	 * @param adoptMonth
	 *            the new adopt month
	 */
	public void setAdoptMonth(String adoptMonth) {
		this.adoptMonth = adoptMonth;
	}

	/**
	 * Gets the abolist month.
	 *
	 * @return the abolist month
	 */
	public String getAbolistMonth() {
		return abolistMonth;
	}

	/**
	 * Sets the abolist month.
	 *
	 * @param abolistMonth
	 *            the new abolist month
	 */
	public void setAbolistMonth(String abolistMonth) {
		this.abolistMonth = abolistMonth;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments
	 *            the new comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the internal errorcd.
	 *
	 * @return the internal errorcd
	 */
	public String getInternalErrorcd() {
		return internalErrorcd;
	}

	/**
	 * Sets the internal errorcd.
	 *
	 * @param internalErrorcd
	 *            the new internal errorcd
	 */
	public void setInternalErrorcd(String internalErrorcd) {
		this.internalErrorcd = internalErrorcd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/*
	 * Compare Report DAO objects
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(B000001ReportDao o) {
		return this.getErrorTypeCode().compareTo(o.getErrorTypeCode());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
