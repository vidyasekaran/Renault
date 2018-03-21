package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TMP_TRN_BUYER_MNTHLY_OCF_USG database table.
 * 
 */
@Embeddable
public class TmpTrnBuyerMnthlyOcfUsgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	public TmpTrnBuyerMnthlyOcfUsgPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TmpTrnBuyerMnthlyOcfUsgPK)) {
			return false;
		}
		TmpTrnBuyerMnthlyOcfUsgPK castOther = (TmpTrnBuyerMnthlyOcfUsgPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.featCd.equals(castOther.featCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.prodMnth.equals(castOther.prodMnth);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		
		return hash;
	}
}