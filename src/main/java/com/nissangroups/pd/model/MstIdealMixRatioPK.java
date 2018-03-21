package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_IDEAL_MIX_RATIO database table.
 * 
 */
@Embeddable
public class MstIdealMixRatioPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	public MstIdealMixRatioPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstIdealMixRatioPK)) {
			return false;
		}
		MstIdealMixRatioPK castOther = (MstIdealMixRatioPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.oeiBuyerId.equals(castOther.oeiBuyerId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.oeiBuyerId.hashCode();
		
		return hash;
	}
}