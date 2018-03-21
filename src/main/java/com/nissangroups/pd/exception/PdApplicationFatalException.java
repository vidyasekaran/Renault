/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-PdApplicationFatalException
 * Module          :Exception
 * Process Outline :Not Applicable
 *
 * <Revision History>
 * Date         Name(Company Name)            Description 
 * ------------ ----------------------------- --------------------
 * 02 Jul 2015 	z015289(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.exception;


/**
 * The Class PdApplicationFatalException.
 */
public class PdApplicationFatalException extends PdApplicationException{

	/** SerialVersion UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new pd application fatal exception.
	 */
	public PdApplicationFatalException(){
		super();
	}
	
	/**
	 * Instantiates a new pd application fatal exception.
	 *
	 * @param message the message
	 */
	public PdApplicationFatalException(String message){
		super(message);
	}
}
