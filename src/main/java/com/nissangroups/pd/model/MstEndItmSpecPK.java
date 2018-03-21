package com.nissangroups.pd.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the MST_END_ITM_SPEC database table.
 * 
 */
@Embeddable
public class MstEndItmSpecPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="SEQ_ID")
	private BigDecimal seqId;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="PROD_STAGE_CD")
	private String prodStageCd;

	@Column(name="GSIS_REGION_GRND")
	private String gsisRegionGrnd;

	@Column(name="GSIS_APPLD_MDL_NO")
	private String gsisAppldMdlNo;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="PCK_CD")
	private String pckCd;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	@Column(name="EIM_SPEC_ADPT_DATE")
	private String eimSpecAdptDate;

	@Column(name="EIM_SPEC_ABLSH_DATE")
	private String eimSpecAblshDate;

	public MstEndItmSpecPK() {
	}
	public BigDecimal getSeqId() {
		return this.seqId;
	}
	public void setSeqId(BigDecimal seqId) {
		this.seqId = seqId;
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdFmyCd() {
		return this.prodFmyCd;
	}
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}
	public String getProdStageCd() {
		return this.prodStageCd;
	}
	public void setProdStageCd(String prodStageCd) {
		this.prodStageCd = prodStageCd;
	}
	public String getGsisRegionGrnd() {
		return this.gsisRegionGrnd;
	}
	public void setGsisRegionGrnd(String gsisRegionGrnd) {
		this.gsisRegionGrnd = gsisRegionGrnd;
	}
	public String getGsisAppldMdlNo() {
		return this.gsisAppldMdlNo;
	}
	public void setGsisAppldMdlNo(String gsisAppldMdlNo) {
		this.gsisAppldMdlNo = gsisAppldMdlNo;
	}
	public String getBuyerCd() {
		return this.buyerCd;
	}
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}
	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}
	public String getPckCd() {
		return this.pckCd;
	}
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}
	public String getExtClrCd() {
		return this.extClrCd;
	}
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}
	public String getIntClrCd() {
		return this.intClrCd;
	}
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}
	public String getSpecDestnCd() {
		return this.specDestnCd;
	}
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}
	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}
	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}
	public String getEimSpecAdptDate() {
		return this.eimSpecAdptDate;
	}
	public void setEimSpecAdptDate(String eimSpecAdptDate) {
		this.eimSpecAdptDate = eimSpecAdptDate;
	}
	public String getEimSpecAblshDate() {
		return this.eimSpecAblshDate;
	}
	public void setEimSpecAblshDate(String eimSpecAblshDate) {
		this.eimSpecAblshDate = eimSpecAblshDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstEndItmSpecPK)) {
			return false;
		}
		MstEndItmSpecPK castOther = (MstEndItmSpecPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodFmyCd.equals(castOther.prodFmyCd)
			&& this.prodStageCd.equals(castOther.prodStageCd)
			&& this.gsisRegionGrnd.equals(castOther.gsisRegionGrnd)
			&& this.gsisAppldMdlNo.equals(castOther.gsisAppldMdlNo)
			&& this.buyerCd.equals(castOther.buyerCd)
			&& this.appldMdlCd.equals(castOther.appldMdlCd)
			&& this.pckCd.equals(castOther.pckCd)
			&& this.extClrCd.equals(castOther.extClrCd)
			&& this.intClrCd.equals(castOther.intClrCd)
			&& this.specDestnCd.equals(castOther.specDestnCd)
			&& this.adtnlSpecCd.equals(castOther.adtnlSpecCd)
			&& this.eimSpecAdptDate.equals(castOther.eimSpecAdptDate)
			&& this.eimSpecAblshDate.equals(castOther.eimSpecAblshDate)
			&& this.seqId == (castOther.seqId);
		
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.seqId.hashCode();
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodFmyCd.hashCode();
		hash = hash * prime + this.prodStageCd.hashCode();
		hash = hash * prime + this.gsisRegionGrnd.hashCode();
		hash = hash * prime + this.gsisAppldMdlNo.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		hash = hash * prime + this.appldMdlCd.hashCode();
		hash = hash * prime + this.pckCd.hashCode();
		hash = hash * prime + this.extClrCd.hashCode();
		hash = hash * prime + this.intClrCd.hashCode();
		hash = hash * prime + this.specDestnCd.hashCode();
		hash = hash * prime + this.adtnlSpecCd.hashCode();
		hash = hash * prime + this.eimSpecAdptDate.hashCode();
		hash = hash * prime + this.eimSpecAblshDate.hashCode();
		
		return hash;
	}
}