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
public class R000005ReportDao {
	
	
	private String porCd;
	private String ordrTkBsMnth;
	private String prdMnth;
	private String ocfRgn;
	private String carSrs;
	private String byrGrp;
	private String ocfFeatCd;
	private String ocdDescShrt;
	private String ocfDescLng;
	private String ocfLmt;
	private String usage;
	private String diff;
	private List<R000005ReportDao> reportList = new ArrayList<R000005ReportDao>();
	
	
	
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
	public String getOcfFeatCd() {
		return ocfFeatCd;
	}
	public void setOcfFeatCd(String ocfFeatCd) {
		this.ocfFeatCd = ocfFeatCd;
	}
	public String getOcdDescShrt() {
		return ocdDescShrt;
	}
	public void setOcdDescShrt(String ocdDescShrt) {
		this.ocdDescShrt = ocdDescShrt;
	}
	public String getOcfDescLng() {
		return ocfDescLng;
	}
	public void setOcfDescLng(String ocfDescLng) {
		this.ocfDescLng = ocfDescLng;
	}
	public String getOcfLmt() {
		return ocfLmt;
	}
	public void setOcfLmt(String ocfLmt) {
		this.ocfLmt = ocfLmt;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public List<R000005ReportDao> getReportList() {
		return reportList;
	}
	public void setReportList(List<R000005ReportDao> reportList) {
		this.reportList = reportList;
	}

}
