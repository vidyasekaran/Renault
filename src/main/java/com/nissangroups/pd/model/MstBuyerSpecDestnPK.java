package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_BUYER_SPEC_DESTN database table.
 * 
 */
@Embeddable
public class MstBuyerSpecDestnPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PROD_REGION_CD", insertable=false, updatable=false)
	private String prodRegionCd;

	@Column(name="BUYER_CD", insertable=false, updatable=false)
	private String buyerCd;

	@Column(name="SPEC_DESTN_CD", insertable=false, updatable=false)
	private String specDestnCd;

	public MstBuyerSpecDestnPK() {
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
	public String getSpecDestnCd() {
		return this.specDestnCd;
	}
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstBuyerSpecDestnPK)) {
			return false;
		}
		MstBuyerSpecDestnPK castOther = (MstBuyerSpecDestnPK)other;
		return 
			this.prodRegionCd.equals(castOther.prodRegionCd)
			&& this.buyerCd.equals(castOther.buyerCd)
			&& this.specDestnCd.equals(castOther.specDestnCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.prodRegionCd.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		hash = hash * prime + this.specDestnCd.hashCode();
		
		return hash;
	}
}