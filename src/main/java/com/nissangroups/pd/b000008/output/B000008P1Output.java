/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000008.output;

import java.util.List;

public class B000008P1Output {

	private List<Object[]> selectOverlap;
	private List<Object[]> carSrsHrzn;
	private List<Object[]> hrznList;
	private List<Object[]> maxPrdList;
	private String overLapFlg;


	public String getOverLapFlg() {
		return overLapFlg;
	}

	public void setOverLapFlg(String overLapFlg) {
		this.overLapFlg = overLapFlg;
	}

	public List<Object[]> getMaxPrdList() {
		return maxPrdList;
	}

	public void setMaxPrdList(List<Object[]> maxPrdList) {
		this.maxPrdList = maxPrdList;
	}

	public List<Object[]> getHrznList() {
		return hrznList;
	}

	public void setHrznList(List<Object[]> hrznList) {
		this.hrznList = hrznList;
	}

	public List<Object[]> getSelectOverlap() {
		return selectOverlap;
	}

	public void setSelectOverlap(List<Object[]> selectOverlap) {
		this.selectOverlap = selectOverlap;
	}

	public List<Object[]> getCarSrsHrzn() {
		return carSrsHrzn;
	}

	public void setCarSrsHrzn(List<Object[]> carSrsHrzn) {
		this.carSrsHrzn = carSrsHrzn;
	}


	

	
}
