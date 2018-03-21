package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the MST_AUTO_DUE_DATE_PRMTR database table.
 * 
 */
@Embeddable
public class MstAutoDueDatePrmtrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD", insertable=false, updatable=false)
	private String porCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	public MstAutoDueDatePrmtrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getBuyerCd() {
		return this.buyerCd;
	}
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstAutoDueDatePrmtrPK)) {
			return false;
		}
		MstAutoDueDatePrmtrPK castOther = (MstAutoDueDatePrmtrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.buyerCd.equals(castOther.buyerCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.prodPlntCd.equals(castOther.prodPlntCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		
		return hash;
	}
}