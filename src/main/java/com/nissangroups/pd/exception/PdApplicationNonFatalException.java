/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-PdApplicationNonFatalException
 * Module          :Exception
 * Process Outline :Not Applicable
 *
 * <Revision History>
 * Date         Name(Company Name)             Description 
 * ----------   ------------------------------ ---------------------
 * 03 Jul 2015 	z015289(RNTBCI)                New Creation
 *
 */
package com.nissangroups.pd.exception;

/**
 * The Class PdApplicationNonFatalException.
 */
public class PdApplicationNonFatalException extends PdApplicationException {

	/** SerialVersion UID. */
	private static final long serialVersionUID = 4206585493015468023L;

	/**
	 * Instantiates a new pd application non fatal exception.
	 */
	public PdApplicationNonFatalException() {
		super();
	}

	/**
	 * Instantiates a new pd application non fatal exception.
	 *
	 * @param message
	 *            the message
	 */
	public PdApplicationNonFatalException(String message) {
		super(message);
	}
}
