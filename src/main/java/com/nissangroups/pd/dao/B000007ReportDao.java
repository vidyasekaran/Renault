/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date         Name(Company Name)            Description 
 * ----------   ----------------------------- ---------------------
 * 05-Jul-20115 z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;

/**
 * The Class B000007ReportDao.
 *
 * @author z011479
 */
@Scope("job")
public class B000007ReportDao {

	/** Variable por code. */
	private String porCode;

	/** Variable prd fml cd. */
	private String prdFmlCd;

	/** Variable frzn ordr tk bs mnth. */
	private String frznOrdrTkBsMnth;

	/** Variable frzn type. */
	private String frznType;

	/** Variable frzn tmng. */
	private String frznTmng;

	/** Variable priority. */
	private String priority;

	/** Variable frzn prd mnth. */
	private String frznPrdMnth;

	/** Variable ocf rgn cd. */
	private String ocfRgnCd;

	/** Variable Prfx yes. */
	private String PrfxYes;

	/** Variable Prfx no. */
	private String PrfxNo;

	/** Variable sfx yes. */
	private String sfxYes;

	/** Variable sfx no. */
	private String sfxNo;

	/** Variable ext1. */
	private String ext1;

	/** Variable ext2. */
	private String ext2;

	/** Variable ext3. */
	private String ext3;

	/** Variable ext4. */
	private String ext4;

	/** Variable ext5. */
	private String ext5;

	/** Variable int1. */
	private String int1;

	/** Variable int2. */
	private String int2;

	/** Variable int3. */
	private String int3;

	/** Variable int4. */
	private String int4;

	/** Variable int5. */
	private String int5;

	/** Variable dest1. */
	private String dest1;

	/** Variable dest2. */
	private String dest2;

	/** Variable dest3. */
	private String dest3;

	/** Variable dest4. */
	private String dest4;

	/** Variable dest5. */
	private String dest5;

	/** Variable err msg. */
	private String errMsg;

	/** Variable report list. */
	private List<B000007ReportDao> reportList = new ArrayList<B000007ReportDao>();

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
	 * Gets the prd fml cd.
	 *
	 * @return the prd fml cd
	 */
	public String getPrdFmlCd() {
		return prdFmlCd;
	}

	/**
	 * Sets the prd fml cd.
	 *
	 * @param prdFmlCd
	 *            the new prd fml cd
	 */
	public void setPrdFmlCd(String prdFmlCd) {
		this.prdFmlCd = prdFmlCd;
	}

	/**
	 * Gets the frzn ordr tk bs mnth.
	 *
	 * @return the frzn ordr tk bs mnth
	 */
	public String getFrznOrdrTkBsMnth() {
		return frznOrdrTkBsMnth;
	}

	/**
	 * Sets the frzn ordr tk bs mnth.
	 *
	 * @param frznOrdrTkBsMnth
	 *            the new frzn ordr tk bs mnth
	 */
	public void setFrznOrdrTkBsMnth(String frznOrdrTkBsMnth) {
		this.frznOrdrTkBsMnth = frznOrdrTkBsMnth;
	}

	/**
	 * Gets the frzn type.
	 *
	 * @return the frzn type
	 */
	public String getFrznType() {
		return frznType;
	}

	/**
	 * Sets the frzn type.
	 *
	 * @param frznType
	 *            the new frzn type
	 */
	public void setFrznType(String frznType) {
		this.frznType = frznType;
	}

	/**
	 * Gets the frzn tmng.
	 *
	 * @return the frzn tmng
	 */
	public String getFrznTmng() {
		return frznTmng;
	}

	/**
	 * Sets the frzn tmng.
	 *
	 * @param frznTmng
	 *            the new frzn tmng
	 */
	public void setFrznTmng(String frznTmng) {
		this.frznTmng = frznTmng;
	}

	/**
	 * Gets the frzn prd mnth.
	 *
	 * @return the frzn prd mnth
	 */
	public String getFrznPrdMnth() {
		return frznPrdMnth;
	}

	/**
	 * Sets the frzn prd mnth.
	 *
	 * @param frznPrdMnth
	 *            the new frzn prd mnth
	 */
	public void setFrznPrdMnth(String frznPrdMnth) {
		this.frznPrdMnth = frznPrdMnth;
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
	 * @param ocfRgnCd
	 *            the new ocf rgn cd
	 */
	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	/**
	 * Gets the prfx yes.
	 *
	 * @return the prfx yes
	 */
	public String getPrfxYes() {
		return PrfxYes;
	}

	/**
	 * Sets the prfx yes.
	 *
	 * @param prfxYes
	 *            the new prfx yes
	 */
	public void setPrfxYes(String prfxYes) {
		PrfxYes = prfxYes;
	}

	/**
	 * Gets the prfx no.
	 *
	 * @return the prfx no
	 */
	public String getPrfxNo() {
		return PrfxNo;
	}

	/**
	 * Sets the prfx no.
	 *
	 * @param prfxNo
	 *            the new prfx no
	 */
	public void setPrfxNo(String prfxNo) {
		PrfxNo = prfxNo;
	}

	/**
	 * Gets the sfx yes.
	 *
	 * @return the sfx yes
	 */
	public String getSfxYes() {
		return sfxYes;
	}

	/**
	 * Sets the sfx yes.
	 *
	 * @param sfxYes
	 *            the new sfx yes
	 */
	public void setSfxYes(String sfxYes) {
		this.sfxYes = sfxYes;
	}

	/**
	 * Gets the sfx no.
	 *
	 * @return the sfx no
	 */
	public String getSfxNo() {
		return sfxNo;
	}

	/**
	 * Sets the sfx no.
	 *
	 * @param sfxNo
	 *            the new sfx no
	 */
	public void setSfxNo(String sfxNo) {
		this.sfxNo = sfxNo;
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
	 * @param errMsg
	 *            the new err msg
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public List<B000007ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the report list.
	 *
	 * @param reportList
	 *            the new report list
	 */
	public void setReportList(List<B000007ReportDao> reportList) {
		this.reportList = reportList;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets the ext1.
	 *
	 * @return the ext1
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * Sets the ext1.
	 *
	 * @param ext1
	 *            the new ext1
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * Gets the ext2.
	 *
	 * @return the ext2
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * Sets the ext2.
	 *
	 * @param ext2
	 *            the new ext2
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * Gets the ext3.
	 *
	 * @return the ext3
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * Sets the ext3.
	 *
	 * @param ext3
	 *            the new ext3
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * Gets the ext4.
	 *
	 * @return the ext4
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * Sets the ext4.
	 *
	 * @param ext4
	 *            the new ext4
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * Gets the ext5.
	 *
	 * @return the ext5
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * Sets the ext5.
	 *
	 * @param ext5
	 *            the new ext5
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	/**
	 * Gets the int1.
	 *
	 * @return the int1
	 */
	public String getInt1() {
		return int1;
	}

	/**
	 * Sets the int1.
	 *
	 * @param int1
	 *            the new int1
	 */
	public void setInt1(String int1) {
		this.int1 = int1;
	}

	/**
	 * Gets the int2.
	 *
	 * @return the int2
	 */
	public String getInt2() {
		return int2;
	}

	/**
	 * Sets the int2.
	 *
	 * @param int2
	 *            the new int2
	 */
	public void setInt2(String int2) {
		this.int2 = int2;
	}

	/**
	 * Gets the int3.
	 *
	 * @return the int3
	 */
	public String getInt3() {
		return int3;
	}

	/**
	 * Sets the int3.
	 *
	 * @param int3
	 *            the new int3
	 */
	public void setInt3(String int3) {
		this.int3 = int3;
	}

	/**
	 * Gets the int4.
	 *
	 * @return the int4
	 */
	public String getInt4() {
		return int4;
	}

	/**
	 * Sets the int4.
	 *
	 * @param int4
	 *            the new int4
	 */
	public void setInt4(String int4) {
		this.int4 = int4;
	}

	/**
	 * Gets the int5.
	 *
	 * @return the int5
	 */
	public String getInt5() {
		return int5;
	}

	/**
	 * Sets the int5.
	 *
	 * @param int5
	 *            the new int5
	 */
	public void setInt5(String int5) {
		this.int5 = int5;
	}

	/**
	 * Gets the dest1.
	 *
	 * @return the dest1
	 */
	public String getDest1() {
		return dest1;
	}

	/**
	 * Sets the dest1.
	 *
	 * @param dest1
	 *            the new dest1
	 */
	public void setDest1(String dest1) {
		this.dest1 = dest1;
	}

	/**
	 * Gets the dest2.
	 *
	 * @return the dest2
	 */
	public String getDest2() {
		return dest2;
	}

	/**
	 * Sets the dest2.
	 *
	 * @param dest2
	 *            the new dest2
	 */
	public void setDest2(String dest2) {
		this.dest2 = dest2;
	}

	/**
	 * Gets the dest3.
	 *
	 * @return the dest3
	 */
	public String getDest3() {
		return dest3;
	}

	/**
	 * Sets the dest3.
	 *
	 * @param dest3
	 *            the new dest3
	 */
	public void setDest3(String dest3) {
		this.dest3 = dest3;
	}

	/**
	 * Gets the dest4.
	 *
	 * @return the dest4
	 */
	public String getDest4() {
		return dest4;
	}

	/**
	 * Sets the dest4.
	 *
	 * @param dest4
	 *            the new dest4
	 */
	public void setDest4(String dest4) {
		this.dest4 = dest4;
	}

	/**
	 * Gets the dest5.
	 *
	 * @return the dest5
	 */
	public String getDest5() {
		return dest5;
	}

	/**
	 * Sets the dest5.
	 *
	 * @param dest5
	 *            the new dest5
	 */
	public void setDest5(String dest5) {
		this.dest5 = dest5;
	}

}
