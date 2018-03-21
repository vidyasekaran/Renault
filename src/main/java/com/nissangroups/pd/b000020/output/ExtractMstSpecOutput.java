/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.output;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to Maintain the Master Spec Details inside the batch.
 * @author z011479
 *
 */
public class ExtractMstSpecOutput {

	List<Object[]> mstSpecDtls = new ArrayList<Object[]>();
	
	List<Object[]> mstSpecDtlsAll = new ArrayList<Object[]>();

	public List<Object[]> getMstSpecDtls() {
		return mstSpecDtls;
	}

	public void setMstSpecDtls(List<Object[]> mstSpecDtls) {
		this.mstSpecDtls = mstSpecDtls;
	}

	/**
	 * @return the mstSpecDtlsAll
	 */
	public List<Object[]> getMstSpecDtlsAll() {
		return mstSpecDtlsAll;
	}

	/**
	 * @param mstSpecDtlsAll the mstSpecDtlsAll to set
	 */
	public void setMstSpecDtlsAll(List<Object[]> mstSpecDtlsAll) {
		this.mstSpecDtlsAll = mstSpecDtlsAll;
	}

	
}
