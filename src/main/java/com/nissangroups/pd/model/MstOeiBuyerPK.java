package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OEI_BUYER database table.
 * 
 */
@Embeddable
public class MstOeiBuyerPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OEI_SPEC_ID")
	private String oeiSpecId;

	@Column(name="BUYER_CD")
	private String buyerCd;

	public MstOeiBuyerPK() {
	}
	public String getOeiSpecId() {
		return this.oeiSpecId;
	}
	public void setOeiSpecId(String oeiSpecId) {
		this.oeiSpecId = oeiSpecId;
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
		if (!(other instanceof MstOeiBuyerPK)) {
			return false;
		}
		MstOeiBuyerPK castOther = (MstOeiBuyerPK)other;
		return 
			this.oeiSpecId.equals(castOther.oeiSpecId)
			&& this.buyerCd.equals(castOther.buyerCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oeiSpecId.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		
		return hash;
	}
}