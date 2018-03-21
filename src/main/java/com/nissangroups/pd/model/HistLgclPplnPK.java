package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the HIST_LGCL_PPLN database table.
 * 
 */
@Embeddable
public class HistLgclPplnPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="SUB_SEQ_ID")
	private String subSeqId;

	public HistLgclPplnPK() {
	}
	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}
	public String getSubSeqId() {
		return this.subSeqId;
	}
	public void setSubSeqId(String subSeqId) {
		this.subSeqId = subSeqId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HistLgclPplnPK)) {
			return false;
		}
		HistLgclPplnPK castOther = (HistLgclPplnPK)other;
		return 
			this.prodOrdrNo.equals(castOther.prodOrdrNo)
			&& this.subSeqId.equals(castOther.subSeqId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.prodOrdrNo.hashCode();
		hash = hash * prime + this.subSeqId.hashCode();
		
		return hash;
	}
}