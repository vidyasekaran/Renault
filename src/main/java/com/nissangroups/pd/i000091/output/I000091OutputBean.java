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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from MST_BUYER ,MST_OCF_REGION AND MST_POR database table values.
 * 
 * @author z016127
 *
 */
@Entity
public class I000091OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**Output Parameter Row Number */
	@Id
	@Column(name = "ROWNUM")
	private String rownum;
	
	/**Output Parameter Ocf Auto Allocation Flag */
	@Column(name="OCF_AUTO_ALLCTN_FLAG")
	private String ocfAutoAllctnFlg;
	
	/**Output Parameter Ocf Region Code */
	@Column(name="OCF_REGION_CD")
	private String ocfRgnCd;

	/**Output Parameter Ocf Buyer Group Code */
	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfByrGrpCd;

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
	 * Get the ocfAutoAllctnFlg
	 *
	 * @return the ocfAutoAllctnFlg
	 */
	public String getOcfAutoAllctnFlg() {
		return ocfAutoAllctnFlg;
	}

	/**
	 * Sets the ocfAutoAllctnFlg
	 *
	 * @param ocfAutoAllctnFlg the ocfAutoAllctnFlg to set
	 */
	public void setOcfAutoAllctnFlg(String ocfAutoAllctnFlg) {
		this.ocfAutoAllctnFlg = ocfAutoAllctnFlg;
	}

	/**
	 * Get the ocfRgnCd
	 *
	 * @return the ocfRgnCd
	 */
	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	/**
	 * Sets the ocfRgnCd
	 *
	 * @param ocfRgnCd the ocfRgnCd to set
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	/**
	 * Get the ocfByrGrpCd
	 *
	 * @return the ocfByrGrpCd
	 */
	public String getOcfByrGrpCd() {
		return ocfByrGrpCd;
	}

	/**
	 * Sets the ocfByrGrpCd
	 *
	 * @param ocfByrGrpCd the ocfByrGrpCd to set
	 */
	public void setOcfByrGrpCd(String ocfByrGrpCd) {
		this.ocfByrGrpCd = ocfByrGrpCd;
	}

}
