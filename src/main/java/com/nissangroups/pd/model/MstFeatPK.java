package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_FEAT database table.
 * 
 */
@Embeddable
public class MstFeatPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD", insertable=false, updatable=false)
	private String porCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="FEAT_TYPE_CD", insertable=false, updatable=false)
	private String featTypeCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	public MstFeatPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}
	public String getFeatTypeCd() {
		return this.featTypeCd;
	}
	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
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
		if (!(other instanceof MstFeatPK)) {
			return false;
		}
		MstFeatPK castOther = (MstFeatPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.featCd.equals(castOther.featCd)
			&& this.featTypeCd.equals(castOther.featTypeCd)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.featTypeCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		
		return hash;
	}
}