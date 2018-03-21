package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_PHYSCL_PPLN database table.
 * 
 */
@Embeddable
public class TrnPhysclPplnPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="VIN_NO")
	private String vinNo;

	public TrnPhysclPplnPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getVinNo() {
		return this.vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnPhysclPplnPK)) {
			return false;
		}
		TrnPhysclPplnPK castOther = (TrnPhysclPplnPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.vinNo.equals(castOther.vinNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.vinNo.hashCode();
		
		return hash;
	}
}