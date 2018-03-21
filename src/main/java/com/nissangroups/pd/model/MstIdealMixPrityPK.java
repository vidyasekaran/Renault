package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the MST_IDEAL_MIX_FRCST_RATIO database table.
 * 
 */
@Embeddable
public class MstIdealMixPrityPK implements Serializable {
	

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="POR_CD")
	private String porCd;


	public MstIdealMixPrityPK() {
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}


	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstIdealMixFrcstRatioPK)) {
			return false;
		}
		MstIdealMixPrityPK castOther = (MstIdealMixPrityPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.buyerGrpCd.equals(castOther.buyerGrpCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.buyerGrpCd.hashCode();
		
		return hash;
	}

}