/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :S SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 14-Jul-2015  	  z013865(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;

/**
 * The Class B000003ReportDao.
 */
@Scope("job")
public class B000003ReportDao{

	/** Variable error type code. */
	private String errorTypeCode;

	/** Variable por_cd. */
	private String por_cd;

	/** Variable production_stage_cd. */
	private String production_stage_cd;

	/** Variable production_family_cd. */
	private String production_family_cd;

	/** Variable buyer_cd. */
	private String buyer_cd;

	/** Variable end_item_model_cd. */
	private String end_item_model_cd;

	/** Variable end_item_color_cd. */
	private String end_item_color_cd;

	/** Variable spec_destination_cd. */
	private String spec_destination_cd;

	/** Variable additional_spec_cd. */
	private String additional_spec_cd;

	/** Variable adopt_month. */
	private String adopt_month;

	/** Variable abolish_month. */
	private String abolish_month;

	/** Variable error_code. */
	private String error_code;

	/** Variable error_message. */
	private String error_message;

	/** Variable report list. */
	private List<B000003ReportDao> reportList = new ArrayList<B000003ReportDao>();

	/**
	 * Gets the production_stage_cd.
	 *
	 * @return the production_stage_cd
	 */
	public String getProduction_stage_cd() {
		return production_stage_cd;
	}

	/**
	 * Sets the production_stage_cd.
	 *
	 * @param production_stage_cd
	 *            the new production_stage_cd
	 */
	public void setProduction_stage_cd(String production_stage_cd) {
		this.production_stage_cd = production_stage_cd;
	}

	/**
	 * Gets the production_family_cd.
	 *
	 * @return the production_family_cd
	 */
	public String getProduction_family_cd() {
		return production_family_cd;
	}

	/**
	 * Sets the production_family_cd.
	 *
	 * @param production_family_cd
	 *            the new production_family_cd
	 */
	public void setProduction_family_cd(String production_family_cd) {
		this.production_family_cd = production_family_cd;
	}

	/**
	 * Gets the buyer_cd.
	 *
	 * @return the buyer_cd
	 */
	public String getBuyer_cd() {
		return buyer_cd;
	}

	/**
	 * Sets the buyer_cd.
	 *
	 * @param buyer_cd
	 *            the new buyer_cd
	 */
	public void setBuyer_cd(String buyer_cd) {
		this.buyer_cd = buyer_cd;
	}

	/**
	 * Gets the end_item_model_cd.
	 *
	 * @return the end_item_model_cd
	 */
	public String getEnd_item_model_cd() {
		return end_item_model_cd;
	}

	/**
	 * Sets the end_item_model_cd.
	 *
	 * @param end_item_model_cd
	 *            the new end_item_model_cd
	 */
	public void setEnd_item_model_cd(String end_item_model_cd) {
		this.end_item_model_cd = end_item_model_cd;
	}

	/**
	 * Gets the end_item_color_cd.
	 *
	 * @return the end_item_color_cd
	 */
	public String getEnd_item_color_cd() {
		return end_item_color_cd;
	}

	/**
	 * Sets the end_item_color_cd.
	 *
	 * @param end_item_color_cd
	 *            the new end_item_color_cd
	 */
	public void setEnd_item_color_cd(String end_item_color_cd) {
		this.end_item_color_cd = end_item_color_cd;
	}

	/**
	 * Gets the spec_destination_cd.
	 *
	 * @return the spec_destination_cd
	 */
	public String getSpec_destination_cd() {
		return spec_destination_cd;
	}

	/**
	 * Sets the spec_destination_cd.
	 *
	 * @param spec_destination_cd
	 *            the new spec_destination_cd
	 */
	public void setSpec_destination_cd(String spec_destination_cd) {
		this.spec_destination_cd = spec_destination_cd;
	}

	/**
	 * Gets the additional_spec_cd.
	 *
	 * @return the additional_spec_cd
	 */
	public String getAdditional_spec_cd() {
		return additional_spec_cd;
	}

	/**
	 * Sets the additional_spec_cd.
	 *
	 * @param additional_spec_cd
	 *            the new additional_spec_cd
	 */
	public void setAdditional_spec_cd(String additional_spec_cd) {
		this.additional_spec_cd = additional_spec_cd;
	}

	/**
	 * Gets the adopt_month.
	 *
	 * @return the adopt_month
	 */
	public String getAdopt_month() {
		return adopt_month;
	}

	/**
	 * Sets the adopt_month.
	 *
	 * @param adopt_month
	 *            the new adopt_month
	 */
	public void setAdopt_month(String adopt_month) {
		this.adopt_month = adopt_month;
	}

	/**
	 * Gets the abolish_month.
	 *
	 * @return the abolish_month
	 */
	public String getAbolish_month() {
		return abolish_month;
	}

	/**
	 * Sets the abolish_month.
	 *
	 * @param abolish_month
	 *            the new abolish_month
	 */
	public void setAbolish_month(String abolish_month) {
		this.abolish_month = abolish_month;
	}

	/**
	 * Gets the error_code.
	 *
	 * @return the error_code
	 */
	public String getError_code() {
		return error_code;
	}

	/**
	 * Sets the error_code.
	 *
	 * @param error_code
	 *            the new error_code
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	/**
	 * Gets the error_message.
	 *
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}

	/**
	 * Sets the error_message.
	 *
	 * @param error_message
	 *            the new error_message
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	
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
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public List<B000003ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the report list.
	 *
	 * @param reportList
	 *            the new report list
	 */
	public void setReportList(List<B000003ReportDao> reportList) {
		this.reportList = reportList;
	}

	/**
	 * Gets the por_cd.
	 *
	 * @return the por_cd
	 */
	public String getPor_cd() {
		return por_cd;
	}

	/**
	 * Sets the por_cd.
	 *
	 * @param por_cd
	 *            the new por_cd
	 */
	public void setPor_cd(String por_cd) {
		this.por_cd = por_cd;
	}


}
