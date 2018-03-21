package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNTHLY_ORDR database table.
 * 
 */
@Embeddable
public class TrnMnthlyOrdrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD", insertable=false, updatable=false)
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_ORDR_STAGE_CD")
	private String prodOrdrStageCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POT_CD", insertable=false, updatable=false)
	private String potCd;

	public TrnMnthlyOrdrPK() {
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
	public String getProdOrdrStageCd() {
		return this.prodOrdrStageCd;
	}
	public void setProdOrdrStageCd(String prodOrdrStageCd) {
		this.prodOrdrStageCd = prodOrdrStageCd;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnMnthlyOrdrPK)) {
			return false;
		}
		TrnMnthlyOrdrPK castOther = (TrnMnthlyOrdrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrtkBaseMnth.equals(castOther.ordrtkBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodOrdrStageCd.equals(castOther.prodOrdrStageCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.potCd.equals(castOther.potCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrtkBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodOrdrStageCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		
		return hash;
	}
}