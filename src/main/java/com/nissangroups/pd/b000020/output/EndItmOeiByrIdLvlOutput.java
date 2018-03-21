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

/**
 * This class is used to set the
 * 
 * @author z011479
 *
 */
public class EndItmOeiByrIdLvlOutput {

	private String porCd;
	private String prdMnth;
	private String byrGrpCd;
	private String crSrs;
	private String oeiByrId;
	private float idlMxRatio;

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPrdMnth() {
		return prdMnth;
	}

	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	public String getByrGrpCd() {
		return byrGrpCd;
	}

	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}

	public String getCrSrs() {
		return crSrs;
	}

	public void setCrSrs(String crSrs) {
		this.crSrs = crSrs;
	}

	public String getOeiByrId() {
		return oeiByrId;
	}

	public void setOeiByrId(String oeiByrId) {
		this.oeiByrId = oeiByrId;
	}

	public float getIdlMxRatio() {
		return idlMxRatio;
	}

	public void setIdlMxRatio(float idlMxRatio) {
		this.idlMxRatio = idlMxRatio;
	}

	

}
