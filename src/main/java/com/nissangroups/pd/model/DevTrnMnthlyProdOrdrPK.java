package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DEV_TRN_MNTHLY_PROD_ORDR database table.
 * 
 */
@Embeddable
public class DevTrnMnthlyProdOrdrPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	public DevTrnMnthlyProdOrdrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrtkBaseMnth() {
		return this.ordrtkBaseMnth;
	}
	public void setOrdrtkBaseMnth(String ordrtkBaseMnth) {
		this.ordrtkBaseMnth = ordrtkBaseMnth;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getPotCd() {
		return this.potCd;
	}
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}
	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DevTrnMnthlyProdOrdrPK)) {
			return false;
		}
		DevTrnMnthlyProdOrdrPK castOther = (DevTrnMnthlyProdOrdrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrtkBaseMnth.equals(castOther.ordrtkBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.potCd.equals(castOther.potCd)
			&& this.prodOrdrNo.equals(castOther.prodOrdrNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrtkBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		hash = hash * prime + this.prodOrdrNo.hashCode();
		
		return hash;
	}
}