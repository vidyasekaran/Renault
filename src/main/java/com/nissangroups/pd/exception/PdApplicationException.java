/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-PdApplicationException
 * Module          :Execption
 * Process Outline :Not Applicable
 *
 * <Revision History>
 * Date         Name(Company Name)             Description 
 * ----------   ------------------------------ ---------------------
 * 02 Jul 2015 	z015289(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.exception;

import java.util.Arrays;

/**
 * The Class PdApplicationException.
 */
public class PdApplicationException extends Exception{

	/** Serial Version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new pd application exception.
	 */
	public PdApplicationException() {
		super();
	}

	/**
	 * Instantiates a new pd application exception.
	 *
	 * @param message the message
	 */
	public PdApplicationException(String message) {
	    
		super(message);
	}
	
	public PdApplicationException(Object[] message) {	    
		super(Arrays.toString(message));
	}
}
