/*
 * System Name       : Post Dragon 
 * Sub system Name   : I Interface
 * Function ID       : PST_DRG_I000103
 * Module            : CM Common		
 * Process Outline   : Interface for Receive User Master from SAP															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 11-01-2016  	  z016127(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000103.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *The persistent class for extracted column values from CMN_INTERFACE_DATA database table values.
 * 
 * @author z016127
 *
 */
@Entity
public class I000103OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**the output parameter row number */
	@Id
	@Column(name="ROWNUM")
	private String rowNum;

	/**the output parameter sequence number */
	@Column(name = "SEQ_NO")
	private String seqNo;

	/**the output parameter column 1 */
	@Column(name = "Col1")
	private String col1;

	/**the output parameter column 2 */
	@Column(name = "Col2")
	private String col2;

	/**the output parameter column 3 */
	@Column(name = "Col3")
	private String col3;

	/**the output parameter column 4 */
	@Column(name = "Col4")
	private String col4;

	/**the output parameter column 5 */
	@Column(name = "Col5")
	private String col5;

	/**the output parameter column 6 */
	@Column(name = "Col6")
	private String col6;

	/**
	 * Get the rowNum
	 *
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * Sets the rowNum
	 *
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * Get the seqNo
	 *
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * Sets the seqNo
	 *
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * Get the col1
	 *
	 * @return the col1
	 */
	public String getCol1() {
		return col1;
	}

	/**
	 * Sets the col1
	 *
	 * @param col1 the col1 to set
	 */
	public void setCol1(String col1) {
		this.col1 = col1;
	}

	/**
	 * Get the col2
	 *
	 * @return the col2
	 */
	public String getCol2() {
		return col2;
	}

	/**
	 * Sets the col2
	 *
	 * @param col2 the col2 to set
	 */
	public void setCol2(String col2) {
		this.col2 = col2;
	}

	/**
	 * Get the col3
	 *
	 * @return the col3
	 */
	public String getCol3() {
		return col3;
	}

	/**
	 * Sets the col3
	 *
	 * @param col3 the col3 to set
	 */
	public void setCol3(String col3) {
		this.col3 = col3;
	}

	/**
	 * Get the col4
	 *
	 * @return the col4
	 */
	public String getCol4() {
		return col4;
	}

	/**
	 * Sets the col4
	 *
	 * @param col4 the col4 to set
	 */
	public void setCol4(String col4) {
		this.col4 = col4;
	}

	/**
	 * Get the col5
	 *
	 * @return the col5
	 */
	public String getCol5() {
		return col5;
	}

	/**
	 * Sets the col5
	 *
	 * @param col5 the col5 to set
	 */
	public void setCol5(String col5) {
		this.col5 = col5;
	}

	/**
	 * Get the col6
	 *
	 * @return the col6
	 */
	public String getCol6() {
		return col6;
	}

	/**
	 * Sets the col6
	 *
	 * @param col6 the col6 to set
	 */
	public void setCol6(String col6) {
		this.col6 = col6;
	}
	
}
