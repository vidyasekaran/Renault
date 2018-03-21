/*
 * System Name     : Post Dragon 
 * Sub system Name : Interface
 * Function ID     : I000062
 * Module          :
 * Process Outline : This interface is used to extract data from COMMON LAYER DATA table and insert the extracted informations in WEEKLY ORDER INTERFACE TRN table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000062.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class I000062OutputBean is used to set and retrieve the output parameter
 * value and allow control over the values passed
 * 
 * @author z016127
 *
 */
@Entity
public class I000062OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The row num. */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** The interface file id */
	@Column(name = "IF_FILE_ID")
	private String ifFileId;

	/** The sequence number */
	@Column(name = "SEQ_NO")
	private String seqNo;

	/** output parameter col1 */
	@Column(name = "Col1")
	private String col1;

	/** output parameter col2 */
	@Column(name = "Col2")
	private String col2;

	/** output parameter col3 */
	@Column(name = "Col3")
	private String col3;

	/** output parameter col4 */
	@Column(name = "Col4")
	private String col4;

	/** output parameter col5 */
	@Column(name = "Col5")
	private String col5;

	/** output parameter col6 */
	@Column(name = "Col6")
	private String col6;

	/** output parameter col7 */
	@Column(name = "Col7")
	private String col7;

	/** output parameter col8 */
	@Column(name = "Col8")
	private String col8;

	/** output parameter col9 */
	@Column(name = "Col9")
	private String col9;

	/** output parameter col10 */
	@Column(name = "Col10")
	private String col10;

	/** output parameter col11 */
	@Column(name = "Col11")
	private String col11;

	/** output parameter col12 */
	@Column(name = "Col12")
	private String col12;

	/** output parameter col13 */
	@Column(name = "Col13")
	private String col13;

	/** output parameter col14 */
	@Column(name = "Col14")
	private String col14;

	/** output parameter col15 */
	@Column(name = "Col15")
	private String col15;

	/** output parameter col16 */
	@Column(name = "Col16")
	private String col16;

	/** output parameter col17 */
	@Column(name = "Col17")
	private String col17;

	/** output parameter col18 */
	@Column(name = "Col18")
	private String col18;

	/** output parameter col19 */
	@Column(name = "Col19")
	private String col19;

	/** output parameter col20 */
	@Column(name = "Col20")
	private String col20;

	/** output parameter col21 */
	@Column(name = "Col21")
	private String col21;

	/** output parameter col22 */
	@Column(name = "Col22")
	private String col22;

	/** output parameter col23 */
	@Column(name = "Col23")
	private String col23;

	/** output parameter col24 */
	@Column(name = "Col24")
	private String col24;

	/** output parameter col25 */
	@Column(name = "Col25")
	private String col25;

	/** output parameter col26 */
	@Column(name = "Col26")
	private String col26;

	/**
	 * Gets the rowNum
	 *
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * Sets the rowNum
	 *
	 * @param rowNum
	 *            the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * Gets the ifFileId
	 *
	 * @return the ifFileId
	 */
	public String getIfFileId() {
		return ifFileId;
	}

	/**
	 * Sets the ifFileId
	 *
	 * @param ifFileId
	 *            the ifFileId to set
	 */
	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}

	/**
	 * Gets the seqNo
	 *
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * Sets the seqNo
	 *
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * Gets the col1
	 *
	 * @return the col1
	 */
	public String getCol1() {
		return col1;
	}

	/**
	 * Sets the col1
	 *
	 * @param col1
	 *            the col1 to set
	 */
	public void setCol1(String col1) {
		this.col1 = col1;
	}

	/**
	 * Gets the col2
	 *
	 * @return the col2
	 */
	public String getCol2() {
		return col2;
	}

	/**
	 * Sets the col2
	 *
	 * @param col2
	 *            the col2 to set
	 */
	public void setCol2(String col2) {
		this.col2 = col2;
	}

	/**
	 * Gets the col3
	 *
	 * @return the col3
	 */
	public String getCol3() {
		return col3;
	}

	/**
	 * Sets the col3
	 *
	 * @param col3
	 *            the col3 to set
	 */
	public void setCol3(String col3) {
		this.col3 = col3;
	}

	/**
	 * Gets the col4
	 *
	 * @return the col4
	 */
	public String getCol4() {
		return col4;
	}

	/**
	 * Sets the col4
	 *
	 * @param col4
	 *            the col4 to set
	 */
	public void setCol4(String col4) {
		this.col4 = col4;
	}

	/**
	 * Gets the col5
	 *
	 * @return the col5
	 */
	public String getCol5() {
		return col5;
	}

	/**
	 * Sets the col5
	 *
	 * @param col5
	 *            the col5 to set
	 */
	public void setCol5(String col5) {
		this.col5 = col5;
	}

	/**
	 * Gets the col6
	 *
	 * @return the col6
	 */
	public String getCol6() {
		return col6;
	}

	/**
	 * Sets the col6
	 *
	 * @param col6
	 *            the col6 to set
	 */
	public void setCol6(String col6) {
		this.col6 = col6;
	}

	/**
	 * Gets the col7
	 *
	 * @return the col7
	 */
	public String getCol7() {
		return col7;
	}

	/**
	 * Sets the col7
	 *
	 * @param col7
	 *            the col7 to set
	 */
	public void setCol7(String col7) {
		this.col7 = col7;
	}

	/**
	 * Gets the col8
	 *
	 * @return the col8
	 */
	public String getCol8() {
		return col8;
	}

	/**
	 * Sets the col8
	 *
	 * @param col8
	 *            the col8 to set
	 */
	public void setCol8(String col8) {
		this.col8 = col8;
	}

	/**
	 * Gets the col9
	 *
	 * @return the col9
	 */
	public String getCol9() {
		return col9;
	}

	/**
	 * Sets the col9
	 *
	 * @param col9
	 *            the col9 to set
	 */
	public void setCol9(String col9) {
		this.col9 = col9;
	}

	/**
	 * Gets the col10
	 *
	 * @return the col10
	 */
	public String getCol10() {
		return col10;
	}

	/**
	 * Sets the col10
	 *
	 * @param col10
	 *            the col10 to set
	 */
	public void setCol10(String col10) {
		this.col10 = col10;
	}

	/**
	 * Gets the col11
	 *
	 * @return the col11
	 */
	public String getCol11() {
		return col11;
	}

	/**
	 * Sets the col11
	 *
	 * @param col11
	 *            the col11 to set
	 */
	public void setCol11(String col11) {
		this.col11 = col11;
	}

	/**
	 * Gets the col12
	 *
	 * @return the col12
	 */
	public String getCol12() {
		return col12;
	}

	/**
	 * Sets the col12
	 *
	 * @param col12
	 *            the col12 to set
	 */
	public void setCol12(String col12) {
		this.col12 = col12;
	}

	/**
	 * Gets the col13
	 *
	 * @return the col13
	 */
	public String getCol13() {
		return col13;
	}

	/**
	 * Sets the col13
	 *
	 * @param col13
	 *            the col13 to set
	 */
	public void setCol13(String col13) {
		this.col13 = col13;
	}

	/**
	 * Gets the col14
	 *
	 * @return the col14
	 */
	public String getCol14() {
		return col14;
	}

	/**
	 * Sets the col14
	 *
	 * @param col14
	 *            the col14 to set
	 */
	public void setCol14(String col14) {
		this.col14 = col14;
	}

	/**
	 * Gets the col15
	 *
	 * @return the col15
	 */
	public String getCol15() {
		return col15;
	}

	/**
	 * Sets the col15
	 *
	 * @param col15
	 *            the col15 to set
	 */
	public void setCol15(String col15) {
		this.col15 = col15;
	}

	/**
	 * Gets the col16
	 *
	 * @return the col16
	 */
	public String getCol16() {
		return col16;
	}

	/**
	 * Sets the col16
	 *
	 * @param col16
	 *            the col16 to set
	 */
	public void setCol16(String col16) {
		this.col16 = col16;
	}

	/**
	 * Gets the col17
	 *
	 * @return the col17
	 */
	public String getCol17() {
		return col17;
	}

	/**
	 * Sets the col17
	 *
	 * @param col17
	 *            the col17 to set
	 */
	public void setCol17(String col17) {
		this.col17 = col17;
	}

	/**
	 * Gets the col18
	 *
	 * @return the col18
	 */
	public String getCol18() {
		return col18;
	}

	/**
	 * Sets the col18
	 *
	 * @param col18
	 *            the col18 to set
	 */
	public void setCol18(String col18) {
		this.col18 = col18;
	}

	/**
	 * Gets the col19
	 *
	 * @return the col19
	 */
	public String getCol19() {
		return col19;
	}

	/**
	 * Sets the col19
	 *
	 * @param col19
	 *            the col19 to set
	 */
	public void setCol19(String col19) {
		this.col19 = col19;
	}

	/**
	 * Gets the col20
	 *
	 * @return the col20
	 */
	public String getCol20() {
		return col20;
	}

	/**
	 * Sets the col20
	 *
	 * @param col20
	 *            the col20 to set
	 */
	public void setCol20(String col20) {
		this.col20 = col20;
	}

	/**
	 * Gets the col21
	 *
	 * @return the col21
	 */
	public String getCol21() {
		return col21;
	}

	/**
	 * Sets the col21
	 *
	 * @param col21
	 *            the col21 to set
	 */
	public void setCol21(String col21) {
		this.col21 = col21;
	}

	/**
	 * Gets the col22
	 *
	 * @return the col22
	 */
	public String getCol22() {
		return col22;
	}

	/**
	 * Sets the col22
	 *
	 * @param col22
	 *            the col22 to set
	 */
	public void setCol22(String col22) {
		this.col22 = col22;
	}

	/**
	 * Gets the col23
	 *
	 * @return the col23
	 */
	public String getCol23() {
		return col23;
	}

	/**
	 * Sets the col23
	 *
	 * @param col23
	 *            the col23 to set
	 */
	public void setCol23(String col23) {
		this.col23 = col23;
	}

	/**
	 * Gets the col24
	 *
	 * @return the col24
	 */
	public String getCol24() {
		return col24;
	}

	/**
	 * Sets the col24
	 *
	 * @param col24
	 *            the col24 to set
	 */
	public void setCol24(String col24) {
		this.col24 = col24;
	}

	/**
	 * Gets the col25
	 *
	 * @return the col25
	 */
	public String getCol25() {
		return col25;
	}

	/**
	 * Sets the col25
	 *
	 * @param col25
	 *            the col25 to set
	 */
	public void setCol25(String col25) {
		this.col25 = col25;
	}

	/**
	 * Gets the col26
	 *
	 * @return the col26
	 */
	public String getCol26() {
		return col26;
	}

	/**
	 * Sets the col26
	 *
	 * @param col26
	 *            the col26 to set
	 */
	public void setCol26(String col26) {
		this.col26 = col26;
	}

	/**
	 * Gets the col27
	 *
	 * @return the col27
	 */
	public String getCol27() {
		return col27;
	}

	/**
	 * Sets the col27
	 *
	 * @param col27
	 *            the col27 to set
	 */
	public void setCol27(String col27) {
		this.col27 = col27;
	}

	/** output parameter col27 */
	@Column(name = "Col27")
	private String col27;

}
