package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_FEAT_GRP database table.
 * 
 */
@Embeddable
public class MstFeatGrpPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="FEAT_GRP_CD")
	private long featGrpCd;

	public MstFeatGrpPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public long getFeatGrpCd() {
		return this.featGrpCd;
	}
	public void setFeatGrpCd(long featGrpCd) {
		this.featGrpCd = featGrpCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstFeatGrpPK)) {
			return false;
		}
		MstFeatGrpPK castOther = (MstFeatGrpPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& (this.featGrpCd == castOther.featGrpCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + ((int) (this.featGrpCd ^ (this.featGrpCd >>> 32)));
		
		return hash;
	}
}