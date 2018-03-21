/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-PDBatchCommonConstants
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class PDBatchCommonConstants.
 */
public class PDBatchCommonConstants {
	
	/** Variable B000002. */
	public String B000002 = "Create Spec Masters";
	
	/** Variable eim stts cd. */
	public String eimSttsCd = "10";
	
	/**
	 * Method to append Object values to the Reader reult List data.
	 *
	 * @param val the val
	 * @param a the a
	 * @return Object[] temp
	 */
	
	public Object[] appendvalue(Object[] val, Object a) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(val));
		temp.add(a);
		return temp.toArray();

	}

}
