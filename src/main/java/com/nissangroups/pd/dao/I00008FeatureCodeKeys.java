/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-I00008FeatureCodeKeys
 * Module          :Interface
 * Process Outline :Common Interface Data to Feautre Code
 *
 * <Revision History>
 * Date          Name(Company Name)          Description 
 * ------------- --------------------------- ---------------------
 * 14-July-2015  z015399(RNTBCI)             New Creation
 *
 */
package com.nissangroups.pd.dao;

/**
 * The Class I00008FeatureCodeKeys.
 */
public class I00008FeatureCodeKeys {

	/** Variable por code. */
	private String porCode;

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
	 * Gets the car series.
	 *
	 * @return the car series
	 */
	public String getCarSeries() {
		return carSeries;
	}

	/**
	 * Sets the car series.
	 *
	 * @param carSeries
	 *            the new car series
	 */
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	/**
	 * Gets the ocf frame code.
	 *
	 * @return the ocf frame code
	 */
	public String getOcfFrameCode() {
		return ocfFrameCode;
	}

	/**
	 * Sets the ocf frame code.
	 *
	 * @param ocfFrameCode
	 *            the new ocf frame code
	 */
	public void setOcfFrameCode(String ocfFrameCode) {
		this.ocfFrameCode = ocfFrameCode;
	}

	/**
	 * Gets the short description.
	 *
	 * @return the short description
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * Sets the short description.
	 *
	 * @param shortDescription
	 *            the new short description
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * Gets the feature code.
	 *
	 * @return the feature code
	 */
	public long getFeatureCode() {
		return featureCode;
	}

	/**
	 * Sets the feature code.
	 *
	 * @param featureCode
	 *            the new feature code
	 */
	public void setFeatureCode(long featureCode) {
		this.featureCode = featureCode;
	}

	/** Variable car series. */
	private String carSeries;

	/**
	 * Gets the vs monitor.
	 *
	 * @return the vs monitor
	 */
	public char getVsMonitor() {
		return vsMonitor;
	}

	/**
	 * Sets the vs monitor.
	 *
	 * @param vsMonitor
	 *            the new vs monitor
	 */
	public void setVsMonitor(char vsMonitor) {
		this.vsMonitor = vsMonitor;
	}

	/** Variable ocf frame code. */
	private String ocfFrameCode;

	/** Variable short description. */
	private String shortDescription;

	/** Variable feature code. */
	private long featureCode;

	/** Variable vs monitor. */
	private char vsMonitor;

}
