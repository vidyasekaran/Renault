/*
 * System Name     : Post Dragon 
 * Sub system Name : I Interface
 * Function ID     : PST_DRG_I000091
 * Module          : OR Ordering
 * Process Outline : Send _Weekly_OCF_to_NSC(Standard_layout)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z016127(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.i000091.output;

import java.io.Serializable;

/**
 * Class I000091Bean is to set the extracted column values from MST_PRMTR database table values.
 * 
 * @author z016127
 *
 */
public class I000091Bean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**Output parameter row number */
	private String rownum;
	
	/**Output parameter Buyer Group Code */
	private String byrGrpCd;

	/**Output parameter Pattern flag */
	private String ptrnFlg;

	/**
	 * Get the rownum
	 *
	 * @return the rownum
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * Sets the rownum
	 *
	 * @param rownum the rownum to set
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	/**
	 * Get the byrGrpCd
	 *
	 * @return the byrGrpCd
	 */
	public String getByrGrpCd() {
		return byrGrpCd;
	}

	/**
	 * Sets the byrGrpCd
	 *
	 * @param byrGrpCd the byrGrpCd to set
	 */
	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}

	/**
	 * Get the ptrnFlg
	 *
	 * @return the ptrnFlg
	 */
	public String getPtrnFlg() {
		return ptrnFlg;
	}

	/**
	 * Sets the ptrnFlg
	 *
	 * @param ptrnFlg the ptrnFlg to set
	 */
	public void setPtrnFlg(String ptrnFlg) {
		this.ptrnFlg = ptrnFlg;
	}

	
}
