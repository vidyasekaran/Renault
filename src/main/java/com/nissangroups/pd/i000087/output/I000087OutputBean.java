/*
 * System Name     :Post Dragon 
 * Sub system Name :I Interface
 * Function ID     :PST_DRG_I000087
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly Production Schedule from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000087.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from CMN_INTERFACE_DATA database table values.
 * 
 * @author z016127
 *
 */
@Entity
public class I000087OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**Output Parameter Row number */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/**Output Parameter Column 1 */
	@Column(name = "Col1")
	private String col1;

	/**Output Parameter Column 3 */
	@Column(name = "Col3")
	private String col3;

	/**Output Parameter Column 4 */
	@Column(name = "Col4")
	private String col4;

	/**Output Parameter Column 5 */
	@Column(name = "Col5")
	private String col5;

	/**Output Parameter Column 6 */
	@Column(name = "Col6")
	private String col6;

	/**Output Parameter Column 7 */
	@Column(name = "Col7")
	private String col7;

	/**Output Parameter Column 8 */
	@Column(name = "Col8")
	private String col8;

	/**Output Parameter Column 9 */
	@Column(name = "Col9")
	private String col9;

	/**Output Parameter Column 10 */
	@Column(name = "Col10")
	private String col10;

	/**Output Parameter Column 11 */
	@Column(name = "Col11")
	private String col11;

	/**Output Parameter Column 14 */
	@Column(name = "Col14")
	private String col14;

	/**Output Parameter Column 16 */
	@Column(name = "Col16")
	private String col16;

	/**Output Parameter Column 18 */
	@Column(name = "Col18")
	private String col18;

	/**Output Parameter Column 19 */
	@Column(name = "Col19")
	private String col19;

	/**Output Parameter Column 20 */
	@Column(name = "Col20")
	private String col20;

	/**Output Parameter Column 23 */
	@Column(name = "Col23")
	private String col23;

	/**Output Parameter Column 24 */
	@Column(name = "Col24")
	private String col24;

	/**Output Parameter Column 25 */
	@Column(name = "Col25")
	private String col25;

	/**Output Parameter Column 26 */
	@Column(name = "Col26")
	private String col26;

	/**Output Parameter Column 30 */
	@Column(name = "Col30")
	private String col30;
	
	/**Output Parameter VAL 1 */
	@Column(name = "VAL1")
	private String val1;
	
	/**Output Parameter OSEI id */
	@Column(name = "OSEI_ID")
	private String oselId;

	/**
	 * Get the rowNum
	 *
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * Sets the rowNum
	 *
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
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

	/**
	 * Get the col7
	 *
	 * @return the col7
	 */
	public String getCol7() {
		return col7;
	}

	/**
	 * Sets the col7
	 *
	 * @param col7 the col7 to set
	 */
	public void setCol7(String col7) {
		this.col7 = col7;
	}

	/**
	 * Get the col8
	 *
	 * @return the col8
	 */
	public String getCol8() {
		return col8;
	}

	/**
	 * Sets the col8
	 *
	 * @param col8 the col8 to set
	 */
	public void setCol8(String col8) {
		this.col8 = col8;
	}

	/**
	 * Get the col9
	 *
	 * @return the col9
	 */
	public String getCol9() {
		return col9;
	}

	/**
	 * Sets the col9
	 *
	 * @param col9 the col9 to set
	 */
	public void setCol9(String col9) {
		this.col9 = col9;
	}

	/**
	 * Get the col10
	 *
	 * @return the col10
	 */
	public String getCol10() {
		return col10;
	}

	/**
	 * Sets the col10
	 *
	 * @param col10 the col10 to set
	 */
	public void setCol10(String col10) {
		this.col10 = col10;
	}

	/**
	 * Get the col11
	 *
	 * @return the col11
	 */
	public String getCol11() {
		return col11;
	}

	/**
	 * Sets the col11
	 *
	 * @param col11 the col11 to set
	 */
	public void setCol11(String col11) {
		this.col11 = col11;
	}

	/**
	 * Get the col14
	 *
	 * @return the col14
	 */
	public String getCol14() {
		return col14;
	}

	/**
	 * Sets the col14
	 *
	 * @param col14 the col14 to set
	 */
	public void setCol14(String col14) {
		this.col14 = col14;
	}

	/**
	 * Get the col16
	 *
	 * @return the col16
	 */
	public String getCol16() {
		return col16;
	}

	/**
	 * Sets the col16
	 *
	 * @param col16 the col16 to set
	 */
	public void setCol16(String col16) {
		this.col16 = col16;
	}

	/**
	 * Get the col18
	 *
	 * @return the col18
	 */
	public String getCol18() {
		return col18;
	}

	/**
	 * Sets the col18
	 *
	 * @param col18 the col18 to set
	 */
	public void setCol18(String col18) {
		this.col18 = col18;
	}

	/**
	 * Get the col19
	 *
	 * @return the col19
	 */
	public String getCol19() {
		return col19;
	}

	/**
	 * Sets the col19
	 *
	 * @param col19 the col19 to set
	 */
	public void setCol19(String col19) {
		this.col19 = col19;
	}

	/**
	 * Get the col20
	 *
	 * @return the col20
	 */
	public String getCol20() {
		return col20;
	}

	/**
	 * Sets the col20
	 *
	 * @param col20 the col20 to set
	 */
	public void setCol20(String col20) {
		this.col20 = col20;
	}

	/**
	 * Get the col23
	 *
	 * @return the col23
	 */
	public String getCol23() {
		return col23;
	}

	/**
	 * Sets the col23
	 *
	 * @param col23 the col23 to set
	 */
	public void setCol23(String col23) {
		this.col23 = col23;
	}

	/**
	 * Get the col24
	 *
	 * @return the col24
	 */
	public String getCol24() {
		return col24;
	}

	/**
	 * Sets the col24
	 *
	 * @param col24 the col24 to set
	 */
	public void setCol24(String col24) {
		this.col24 = col24;
	}

	/**
	 * Get the col25
	 *
	 * @return the col25
	 */
	public String getCol25() {
		return col25;
	}

	/**
	 * Sets the col25
	 *
	 * @param col25 the col25 to set
	 */
	public void setCol25(String col25) {
		this.col25 = col25;
	}

	/**
	 * Get the col26
	 *
	 * @return the col26
	 */
	public String getCol26() {
		return col26;
	}

	/**
	 * Sets the col26
	 *
	 * @param col26 the col26 to set
	 */
	public void setCol26(String col26) {
		this.col26 = col26;
	}

	/**
	 * Get the col30
	 *
	 * @return the col30
	 */
	public String getCol30() {
		return col30;
	}

	/**
	 * Sets the col30
	 *
	 * @param col30 the col30 to set
	 */
	public void setCol30(String col30) {
		this.col30 = col30;
	}

	/**
	 * Get the val1
	 *
	 * @return the val1
	 */
	public String getVal1() {
		return val1;
	}

	/**
	 * Sets the val1
	 *
	 * @param val1 the val1 to set
	 */
	public void setVal1(String val1) {
		this.val1 = val1;
	}

	/**
	 * Get the oselId
	 *
	 * @return the oselId
	 */
	public String getOselId() {
		return oselId;
	}

	/**
	 * Sets the oselId
	 *
	 * @param oselId the oselId to set
	 */
	public void setOselId(String oselId) {
		this.oselId = oselId;
	}
	
}
