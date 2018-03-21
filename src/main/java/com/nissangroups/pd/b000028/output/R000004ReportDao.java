/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.output;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;


/**
 * The Class B000007ReportDao.
 *
 * @author z011479
 */
@Scope("job")
public class R000004ReportDao {

	private String porCd;
	private String ordrTkBsMnth;
	private String prdMnth;
	private String ocfRgn;
	private String carSrs;
	private String byrGrp;
	private String byrCd;
	private String volAlloc;
	private String ordrQty;
	private String diff;
	private String autoAdjust;
	private String ordrQuanToPlnt;
	private String endItmApp;
	private String endItmPk;
	private String specCd;
	private String pot;
	private String extClr;
	private String intClr;
	private String exNo;
	private String ordrQtyCs;
	private String autoAdjustCs;
	private String ordrQuanToPlntCs;
	private String recTypCd;
	private String comments;
	
	private List<R000004ReportDao> reportList = new ArrayList<R000004ReportDao>();

	public String getOrdrQtyCs() {
		return ordrQtyCs;
	}

	public void setOrdrQtyCs(String ordrQtyCs) {
		this.ordrQtyCs = ordrQtyCs;
	}

	public String getOrdrQuanToPlntCs() {
		return ordrQuanToPlntCs;
	}

	public void setOrdrQuanToPlntCs(String ordrQuanToPlntCs) {
		this.ordrQuanToPlntCs = ordrQuanToPlntCs;
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}

	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}

	public String getPrdMnth() {
		return prdMnth;
	}

	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	public String getOcfRgn() {
		return ocfRgn;
	}

	public void setOcfRgn(String ocfRgn) {
		this.ocfRgn = ocfRgn;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getByrGrp() {
		return byrGrp;
	}

	public void setByrGrp(String byrGrp) {
		this.byrGrp = byrGrp;
	}

	public String getByrCd() {
		return byrCd;
	}

	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}

	public String getVolAlloc() {
		return volAlloc;
	}

	public void setVolAlloc(String volAlloc) {
		this.volAlloc = volAlloc;
	}

	public String getOrdrQty() {
		return ordrQty;
	}

	public void setOrdrQty(String ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getAutoAdjust() {
		return autoAdjust;
	}

	public void setAutoAdjust(String autoAdjust) {
		this.autoAdjust = autoAdjust;
	}

	public String getOrdrQuanToPlnt() {
		return ordrQuanToPlnt;
	}

	public void setOrdrQuanToPlnt(String ordrQuanToPlnt) {
		this.ordrQuanToPlnt = ordrQuanToPlnt;
	}

	public String getEndItmApp() {
		return endItmApp;
	}

	public void setEndItmApp(String endItmApp) {
		this.endItmApp = endItmApp;
	}

	public String getEndItmPk() {
		return endItmPk;
	}

	public void setEndItmPk(String endItmPk) {
		this.endItmPk = endItmPk;
	}

	public String getSpecCd() {
		return specCd;
	}

	public void setSpecCd(String specCd) {
		this.specCd = specCd;
	}

	public String getPot() {
		return pot;
	}

	public void setPot(String pot) {
		this.pot = pot;
	}

	public String getExtClr() {
		return extClr;
	}

	public void setExtClr(String extClr) {
		this.extClr = extClr;
	}

	public String getIntClr() {
		return intClr;
	}

	public void setIntClr(String intClr) {
		this.intClr = intClr;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}


	public String getAutoAdjustCs() {
		return autoAdjustCs;
	}

	public void setAutoAdjustCs(String autoAdjustCs) {
		this.autoAdjustCs = autoAdjustCs;
	}

	public String getRecTypCd() {
		return recTypCd;
	}

	public void setRecTypCd(String recTypCd) {
		this.recTypCd = recTypCd;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<R000004ReportDao> getReportList() {
		return reportList;
	}

	public void setReportList(List<R000004ReportDao> reportList) {
		this.reportList = reportList;
	}
	
}
