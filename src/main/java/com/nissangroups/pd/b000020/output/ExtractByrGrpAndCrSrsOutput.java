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
 * This Class is used to set the ExtractByrGrpAndCrSrsProcess Output values. 
 * @author z011479
 *
 */
public class ExtractByrGrpAndCrSrsOutput {	

	List<Object[]> byrGrp = new ArrayList<Object[]>();
	List<Object[]> crSrs = new ArrayList<Object[]>();

	public List<Object[]> getByrGrp() {
		return byrGrp;
	}

	public void setByrGrp(List<Object[]> byrGrp) {
		this.byrGrp = byrGrp;
	}

	public List<Object[]> getCrSrs() {
		return crSrs;
	}

	public void setCrSrs(List<Object[]> crSrs) {
		this.crSrs = crSrs;
	}

}
