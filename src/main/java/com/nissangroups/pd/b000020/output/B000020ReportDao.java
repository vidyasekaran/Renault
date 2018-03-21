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

import org.springframework.context.annotation.Scope;


/**
 * The Class B000020ReportDao.
 *
 * @author z011479
 */
@Scope("job")
public class B000020ReportDao {
	
	private String srlNo;
	private String ordrTkBSMnth;
	private String prodMnth;
	private String por;
	private String carSrs;
	private String byrGrp;
	private String ordrStg;
	private String avgCalBy;
	private String eiBrkPntPrity;
	private String colorBrkdwnPrity;
	private String errType;
	private String errMsg;
	private String batchId;
	private String errId;
	private String time;
	private List<B000020ReportDao> reportList = new ArrayList<B000020ReportDao>();
	
	public String getSrlNo() {
		return srlNo;
	}
	public void setSrlNo(String srlNo) {
		this.srlNo = srlNo;
	}
	public String getOrdrTkBSMnth() {
		return ordrTkBSMnth;
	}
	public void setOrdrTkBSMnth(String ordrTkBSMnth) {
		this.ordrTkBSMnth = ordrTkBSMnth;
	}
	public String getProdMnth() {
		return prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getPor() {
		return por;
	}
	public void setPor(String por) {
		this.por = por;
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
	public String getOrdrStg() {
		return ordrStg;
	}
	public void setOrdrStg(String ordrStg) {
		this.ordrStg = ordrStg;
	}
	public String getAvgCalBy() {
		return avgCalBy;
	}
	public void setAvgCalBy(String avgCalBy) {
		this.avgCalBy = avgCalBy;
	}
	public String getEiBrkPntPrity() {
		return eiBrkPntPrity;
	}
	public void setEiBrkPntPrity(String eiBrkPntPrity) {
		this.eiBrkPntPrity = eiBrkPntPrity;
	}
	public String getColorBrkdwnPrity() {
		return colorBrkdwnPrity;
	}
	public void setColorBrkdwnPrity(String colorBrkdwnPrity) {
		this.colorBrkdwnPrity = colorBrkdwnPrity;
	}
	public String getErrType() {
		return errType;
	}
	public void setErrType(String errType) {
		this.errType = errType;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getErrId() {
		return errId;
	}
	public void setErrId(String errId) {
		this.errId = errId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<B000020ReportDao> getReportList() {
		return reportList;
	}
	public void setReportList(List<B000020ReportDao> reportList) {
		this.reportList = reportList;
	}
	
	
	
	

}
