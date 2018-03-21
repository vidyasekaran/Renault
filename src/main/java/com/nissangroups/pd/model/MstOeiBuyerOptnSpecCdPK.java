package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OEI_BUYER_OPTN_SPEC_CD database table.
 * 
 */
@Embeddable
public class MstOeiBuyerOptnSpecCdPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="OPTN_SPEC_CODE")
	private String optnSpecCode;

	public MstOeiBuyerOptnSpecCdPK() {
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}
	public String getOptnSpecCode() {
		return this.optnSpecCode;
	}
	public void setOptnSpecCode(String optnSpecCode) {
		this.optnSpecCode = optnSpecCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOeiBuyerOptnSpecCdPK)) {
			return false;
		}
		MstOeiBuyerOptnSpecCdPK castOther = (MstOeiBuyerOptnSpecCdPK)other;
		return 
			this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.optnSpecCode.equals(castOther.optnSpecCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.optnSpecCode.hashCode();
		
		return hash;
	}
}