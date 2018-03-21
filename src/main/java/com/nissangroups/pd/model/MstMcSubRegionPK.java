package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_MC_SUB_REGION database table.
 * 
 */
@Embeddable
public class MstMcSubRegionPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="MC_REGION_CD", insertable=false, updatable=false)
	private String mcRegionCd;

	@Column(name="SUB_REGION_CD")
	private String subRegionCd;

	public MstMcSubRegionPK() {
	}
	public String getMcRegionCd() {
		return this.mcRegionCd;
	}
	public void setMcRegionCd(String mcRegionCd) {
		this.mcRegionCd = mcRegionCd;
	}
	public String getSubRegionCd() {
		return this.subRegionCd;
	}
	public void setSubRegionCd(String subRegionCd) {
		this.subRegionCd = subRegionCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstMcSubRegionPK)) {
			return false;
		}
		MstMcSubRegionPK castOther = (MstMcSubRegionPK)other;
		return 
			this.mcRegionCd.equals(castOther.mcRegionCd)
			&& this.subRegionCd.equals(castOther.subRegionCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.mcRegionCd.hashCode();
		hash = hash * prime + this.subRegionCd.hashCode();
		
		return hash;
	}
}