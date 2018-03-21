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
 * This class is used to maintain Batch Param Values.
 * 
 * @author z011479
 *
 */
public class B000020ParamOutput {

	private String porCd;
	private String scrnOnlyFlg;
	private String productionStageCd;
	private String stgCode;
	private String ocfRgnCd;
	private String ocfByrGrp;
	private String byrGrp;
	private String seqId;
	private String crSrs;
	private String ordTkBsMnth;
	private String avgCalBy;
	private String prdMnthFrm;
	private String prdMnthTo;
	private String errorPath;
	
	private List<Object[]> dstnctByrGrpLst = new ArrayList<Object[]>();

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getScrnOnlyFlg() {
		return scrnOnlyFlg;
	}

	public void setScrnOnlyFlg(String scrnOnlyFlg) {
		this.scrnOnlyFlg = scrnOnlyFlg;
	}

	public String getProductionStageCd() {
		return productionStageCd;
	}

	public void setProductionStageCd(String productionStageCd) {
		this.productionStageCd = productionStageCd;
	}

	public String getStgCode() {
		return stgCode;
	}

	public void setStgCode(String stgCode) {
		this.stgCode = stgCode;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	public String getOcfByrGrp() {
		return ocfByrGrp;
	}

	public void setOcfByrGrp(String ocfByrGrp) {
		this.ocfByrGrp = ocfByrGrp;
	}

	public String getByrGrp() {
		return byrGrp;
	}

	public void setByrGrp(String byrGrp) {
		this.byrGrp = byrGrp;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getCrSrs() {
		return crSrs;
	}

	public void setCrSrs(String crSrs) {
		this.crSrs = crSrs;
	}

	public String getOrdTkBsMnth() {
		return ordTkBsMnth;
	}

	public void setOrdTkBsMnth(String ordTkBsMnth) {
		this.ordTkBsMnth = ordTkBsMnth;
	}

	public String getAvgCalBy() {
		return avgCalBy;
	}

	public void setAvgCalBy(String avgCalBy) {
		this.avgCalBy = avgCalBy;
	}

	public List<Object[]> getDstnctByrGrpLst() {
		return dstnctByrGrpLst;
	}

	public void setDstnctByrGrpLst(List<Object[]> dstnctByrGrpLst) {
		this.dstnctByrGrpLst = dstnctByrGrpLst;
	}

	public String getPrdMnthFrm() {
		return prdMnthFrm;
	}

	public void setPrdMnthFrm(String prdMnthFrm) {
		this.prdMnthFrm = prdMnthFrm;
	}

	public String getPrdMnthTo() {
		return prdMnthTo;
	}

	public void setPrdMnthTo(String prdMnthTo) {
		this.prdMnthTo = prdMnthTo;
	}

	public String getErrorPath() {
		return errorPath;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

}
