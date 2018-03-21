/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-R000036
 * Module          :Ordering
 * Process Outline :
 *
 * <Revision History>
 * Date         Name(Company Name)            Description 
 * ----------   ----------------------------- ---------------------
 * 02-Nov-2015 z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.r000036.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;


/**
 * The Class R000036ReportDao.
 *
 * @author z014029
 */
@Scope("job")
public class R000036ReportDao {
	
	/** Variable por code. */
	private String porCode;
	
	/** Variable ordr take base mnth */
	private String OrdrTkBsMnth;
	
	/** Variable prd mnth. */
	private String prdMnth;
	
	/** Variable cr srs */
	private String crSrs;
	
	/** Variable ocf rgn cd. */
	private String ocfRgnCd;
	
	/** Variable ocf byr grp. */
	private String ocfByrGrp;
	
	/** Variable ocf frame cd. */
	private String ocfFrmCd;
	
	/** Variable feature cd */
	private String featureCd;	
	
	/** Variable ocf shrt desc */
	private String ocfShrtDesc;
	
	/** Variable ocf Lmt */
	private String ocfLmt;
	
	
	/** Variable err msg. */
	private String errMsg;
	

	/** Variable report list. */
	private List<R000036ReportDao> reportList = new ArrayList<R000036ReportDao>();
	
	/**
	 * Gets the por code.
	 *
	 * @return the por code
	 */
	public String getPorCode() {
		return porCode;
	}

	/**
	 * Sets the por code.
	 *
	 * @param porCode
	 *            the new por code
	 */

	public void setPorCode(String porCode) {
		this.porCode = porCode;
	}

	/**
	 * Gets the  ordr tk bs mnth.
	 *
	 * @return the  ordr tk bs mnth
	 */
	public String getOrdrTakeBaseMnth() {
		return OrdrTkBsMnth;
	}
	/**
	 * Sets the  ordr tk bs mnth.
	 *
	 * @param OrdrTkBsMnth
	 *            the new ordr tk bs mnth
	 */

	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.OrdrTkBsMnth = ordrTakeBaseMnth;
	}

	/**
	 * Gets the prd mnth.
	 *
	 * @return the prd mnth
	 */
	public String getPrdMnth() {
		return prdMnth;
	}

	/**
	 * Sets the prd mnth.
	 *
	 * @param prd mnth
	 *           
	 */
	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	/**
	 * Gets the cr srs.
	 *
	 * @return the cr srs
	 */
	public String getCrSrs() {
		return crSrs;
	}

	/**
	 * Sets the cr srs.
	 *
	 * @param cr srs
	 *           
	 */
	public void setCrSrs(String crSrs) {
		this.crSrs = crSrs;
	}

	/**
	 * Gets the ocf rgn cd.
	 *
	 * @return the ocf rgn cd
	 */
	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	/**
	 * Sets the ocf rgn cd.
	 *
	 * @param ocf rgn cd
	 *           
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	/**
	 * Gets the ocf byr grp.
	 *
	 * @return the ocf byr grp
	 */
	public String getOcfByrGrp() {
		return ocfByrGrp;
	}

	/**
	 * Sets the ocf byr grp.
	 *
	 * @param ocf byr grp
	 *           
	 */
	public void setOcfByrGrp(String ocfByrGrp) {
		this.ocfByrGrp = ocfByrGrp;
	}

	/**
	 * Gets the ocf frm cd.
	 *
	 * @return the ocf frm cd
	 */
	public String getOcfFrmCd() {
		return ocfFrmCd;
	}

	/**
	 * Sets the ocf frm cd.
	 *
	 * @param ocf frm cd
	 *           
	 */
	public void setOcfFrmCd(String ocfFrmCd) {
		this.ocfFrmCd = ocfFrmCd;
	}

	/**
	 * Gets the feature cd.
	 *
	 * @return the feature cd
	 */
	public String getFeatureCd() {
		return featureCd;
	}

	/**
	 * Sets the feature cd.
	 *
	 * @param feature cd.
	 *           
	 */
	public void setFeatureCd(String featureCd) {
		this.featureCd = featureCd;
	}

	/**
	 * Gets the ocf shrt desc.
	 *
	 * @return the ocf shrt desc
	 */
	public String getOcfShrtDesc() {
		return ocfShrtDesc;
	}

	/**
	 * Sets the ocf shrt desc.
	 *
	 * @param ocf shrt desc
	 *           
	 */
	public void setOcfShrtDesc(String ocfShrtDesc) {
		this.ocfShrtDesc = ocfShrtDesc;
	}

	/**
	 * Gets the ocf lmt.
	 *
	 * @return the ocf lmt
	 */
	public String getOcfLmt() {
		return ocfLmt;
	}

	/**
	 * Sets the ocf lmt.
	 *
	 * @param ocf lmt
	 *           
	 */
	public void setOcfLmt(String ocfLmt) {
		this.ocfLmt = ocfLmt;
	}

	/**
	 * Gets the err msg.
	 *
	 * @return the err msg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * Sets the err msg.
	 *
	 * @param err msg
	 *           
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public List<R000036ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the report list.
	 *
	 * @param reportList
	 *            the new report list
	 */
	public void setReportList(List<R000036ReportDao> reportList) {
		this.reportList = reportList;
	}
}
