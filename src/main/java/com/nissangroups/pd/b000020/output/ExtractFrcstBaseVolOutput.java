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

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to maintain the Extract Forecast Base Volume Processor
 * output.
 * 
 * @author z011479
 *
 */
public class ExtractFrcstBaseVolOutput {

	Map<String, Object[]> nscFrcstVol = new HashMap<String, Object[]>();
	Map<String, Object[]> nscByrGrpOcfLmt = new HashMap<String, Object[]>();
	Map<String, Object[]> byrGrpPrMap = new HashMap<String, Object[]>();

	public Map<String, Object[]> getNscFrcstVol() {
		return nscFrcstVol;
	}

	public void setNscFrcstVol(Map<String, Object[]> nscFrcstVol) {
		this.nscFrcstVol = nscFrcstVol;
	}

	public Map<String, Object[]> getNscByrGrpOcfLmt() {
		return nscByrGrpOcfLmt;
	}

	public void setNscByrGrpOcfLmt(Map<String, Object[]> nscByrGrpOcfLmt) {
		this.nscByrGrpOcfLmt = nscByrGrpOcfLmt;
	}

	public Map<String, Object[]> getByrGrpPrMap() {
		return byrGrpPrMap;
	}

	public void setByrGrpPrMap(Map<String, Object[]> byrGrpPrMap) {
		this.byrGrpPrMap = byrGrpPrMap;
	}

}