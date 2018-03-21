package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_BUYER database table.
 * 
 */
@Embeddable
public class MstBuyerPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PROD_REGION_CD", insertable=false, updatable=false)
	private String prodRegionCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	public MstBuyerPK() {
	}
	public String getProdRegionCd() {
		return this.prodRegionCd;
	}
	public void setProdRegionCd(String prodRegionCd) {
		this.prodRegionCd = prodRegionCd;
	}
	public String getBuyerCd() {
		return this.buyerCd;
	}
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstBuyerPK)) {
			return false;
		}
		MstBuyerPK castOther = (MstBuyerPK)other;
		return 
			this.prodRegionCd.equals(castOther.prodRegionCd)
			&& this.buyerCd.equals(castOther.buyerCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.prodRegionCd.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		
		return hash;
	}
}