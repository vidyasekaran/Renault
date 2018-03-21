package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_AO_ETA_PRMTR database table.
 * 
 */
@Embeddable
public class TrnAoEtaPrmtrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="PCK_CD")
	private String pckCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	public TrnAoEtaPrmtrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getExNo() {
		return this.exNo;
	}
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}
	public String getSpecDestnCd() {
		return this.specDestnCd;
	}
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}
	public String getIntClrCd() {
		return this.intClrCd;
	}
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}
	public String getExtClrCd() {
		return this.extClrCd;
	}
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}
	public String getPckCd() {
		return this.pckCd;
	}
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}
	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnAoEtaPrmtrPK)) {
			return false;
		}
		TrnAoEtaPrmtrPK castOther = (TrnAoEtaPrmtrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.exNo.equals(castOther.exNo)
			&& this.specDestnCd.equals(castOther.specDestnCd)
			&& this.intClrCd.equals(castOther.intClrCd)
			&& this.extClrCd.equals(castOther.extClrCd)
			&& this.pckCd.equals(castOther.pckCd)
			&& this.appldMdlCd.equals(castOther.appldMdlCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.exNo.hashCode();
		hash = hash * prime + this.specDestnCd.hashCode();
		hash = hash * prime + this.intClrCd.hashCode();
		hash = hash * prime + this.extClrCd.hashCode();
		hash = hash * prime + this.pckCd.hashCode();
		hash = hash * prime + this.appldMdlCd.hashCode();
		
		return hash;
	}
}