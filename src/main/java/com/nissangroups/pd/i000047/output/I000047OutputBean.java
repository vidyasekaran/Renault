/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface 
 * Function ID     :PST-DRG-I000047
 * Module          :SP SPEC MASTER
 *  
 * Process Outline :Receive Week No Calendar Interface from Plant. 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 17-01-2016  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000047.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for extracted column values from CMN_INTERFACE_TABLE
 * database table values.
 * 
 * @author z014029
 * 
 */
@Entity
public class I000047OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Interface Output Parameter ROW NUM */
	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Interface Output Parameter INTERFACEF FILE ID */
	@Column(name = "IF_FILE_ID")
	private String ifFileId;

	/** Interface Output Parameter SEQUENCE NO */
	@Column(name = "SEQ_NO")
	private String seqNo;

	/** Interface Output Parameter COLOUMN1 */
	@Column(name = "Col1")
	private String col1;

	/** Interface Output Parameter COLOUMN2 */
	@Column(name = "Col2")
	private String col2;

	/** Interface Output Parameter COLOUMN3 */
	@Column(name = "Col3")
	private String col3;

	/** Interface Output Parameter COLOUMN4 */
	@Column(name = "Col4")
	private String col4;

	/** Interface Output Parameter COLOUMN5 */
	@Column(name = "Col5")
	private String col5;

	/** Interface Output Parameter COLOUMN6 */
	@Column(name = "Col6")
	private String col6;

	/** Interface Output Parameter COLOUMN7 */
	@Column(name = "Col7")
	private String col7;

	/** Interface Output Parameter COLOUMN8 */
	@Column(name = "Col8")
	private String col8;

	/** Interface Output Parameter COLOUMN9 */
	@Column(name = "Col9")
	private String col9;

	/** Interface Output Parameter COLOUMN10 */
	@Column(name = "Col10")
	private String col10;

	/** Interface Output Parameter COLOUMN11 */
	@Column(name = "Col11")
	private String col11;

	/** Interface Output Parameter COLOUMN12 */
	@Column(name = "Col12")
	private String col12;

	/** Interface Output Parameter COLOUMN13 */
	@Column(name = "Col13")
	private String col13;

	/** Interface Output Parameter COLOUMN14 */
	@Column(name = "Col14")
	private String col14;

	/** Interface Output Parameter COLOUMN15 */
	@Column(name = "Col15")
	private String col15;

	/** Interface Output Parameter COLOUMN16 */
	@Column(name = "Col16")
	private String col16;

	/** Interface Output Parameter COLOUMN17 */
	@Column(name = "Col17")
	private String col17;

	/** Interface Output Parameter COLOUMN18 */
	@Column(name = "Col18")
	private String col18;

	/** Interface Output Parameter COLOUMN19 */
	@Column(name = "Col19")
	private String col19;

	/** Interface Output Parameter COLOUMN20 */
	@Column(name = "Col20")
	private String col20;

	/** Interface Output Parameter COLOUMN21 */
	@Column(name = "Col21")
	private String col21;

	/** Interface Output Parameter COLOUMN22 */
	@Column(name = "Col22")
	private String col22;

	/** Interface Output Parameter COLOUMN23 */
	@Column(name = "Col23")
	private String col23;

	/**
	 * Get the ifFileId
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
	 * @param seqNo
	 *            the seqNo to set
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
	 * @param col1
	 *            the col1 to set
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
	 * @param col2
	 *            the col2 to set
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
	 * @param col3
	 *            the col3 to set
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
	 * @param col4
	 *            the col4 to set
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
	 * @param col5
	 *            the col5 to set
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
	 * @param col6
	 *            the col6 to set
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
	 * @param col7
	 *            the col7 to set
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
	 * @param col8
	 *            the col8 to set
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
	 * @param col9
	 *            the col9 to set
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
	 * @param col10
	 *            the col10 to set
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
	 * @param col11
	 *            the col11 to set
	 */
	public void setCol11(String col11) {
		this.col11 = col11;
	}

	/**
	 * Get the col12
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
	 * Get the col13
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
	 * @param col14
	 *            the col14 to set
	 */
	public void setCol14(String col14) {
		this.col14 = col14;
	}

	/**
	 * Get the col15
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
	 * @param col16
	 *            the col16 to set
	 */
	public void setCol16(String col16) {
		this.col16 = col16;
	}

	/**
	 * Get the col17
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
	 * @param col18
	 *            the col18 to set
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
	 * @param col19
	 *            the col19 to set
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
	 * @param col20
	 *            the col20 to set
	 */
	public void setCol20(String col20) {
		this.col20 = col20;
	}

	/**
	 * Get the col21
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
	 * Get the col22
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
	 * @param col23
	 *            the col23 to set
	 */
	public void setCol23(String col23) {
		this.col23 = col23;
	}

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
	 * @param rowNum
	 *            the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}
}