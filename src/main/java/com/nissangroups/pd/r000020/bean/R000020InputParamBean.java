/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-R000020/
 * Module          :MONTHLY ORDERING
 * Process Outline :Pojo Class to Hold the batch input parameters and parameters to pass through one processor to another processor 
 * in a composite item processor
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z011479(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.r000020.bean;

import java.util.List;


/**
 * This method is used to set the  
 * @author z011479
 *
 */
public class R000020InputParamBean {

	private String porCd;
	private String tableName;
	private String ocfRgnCd;
	private String ordrTkBsMnth;
	private List<String> prdStgCd;
	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}

	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}

	/**
	 * @return the prdStgCd
	 */
	public List<String> getPrdStgCd() {
		return prdStgCd;
	}

	/**
	 * @param prdStgCd the prdStgCd to set
	 */
	public void setPrdStgCd(List<String> prdStgCd) {
		this.prdStgCd = prdStgCd;
	}


	
	
}