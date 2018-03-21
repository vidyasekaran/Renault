package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OCF_REGION database table.
 * 
 */
@Embeddable
public class MstOcfRegionPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PROD_REGION_CD", insertable=false, updatable=false)
	private String prodRegionCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	public MstOcfRegionPK() {
	}
	public String getProdRegionCd() {
		return this.prodRegionCd;
	}
	public void setProdRegionCd(String prodRegionCd) {
		this.prodRegionCd = prodRegionCd;
	}
	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}
	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}
	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOcfRegionPK)) {
			return false;
		}
		MstOcfRegionPK castOther = (MstOcfRegionPK)other;
		return 
			this.prodRegionCd.equals(castOther.prodRegionCd)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.prodRegionCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		
		return hash;
	}
}