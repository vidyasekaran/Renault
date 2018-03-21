package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_MNTH_ORDR_TAKE_BASE_PD database table.
 * 
 */
@Embeddable
public class MstMnthOrdrTakeBasePdPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	public MstMnthOrdrTakeBasePdPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstMnthOrdrTakeBasePdPK)) {
			return false;
		}
		MstMnthOrdrTakeBasePdPK castOther = (MstMnthOrdrTakeBasePdPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		
		return hash;
	}
}