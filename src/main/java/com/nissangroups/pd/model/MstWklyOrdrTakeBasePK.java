package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_WKLY_ORDR_TAKE_BASE database table.
 * 
 */
@Embeddable
public class MstWklyOrdrTakeBasePK implements Serializable {
	//default serial version id, required for serializable classes.
	

	private String por;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="ORDR_TAKE_BASE_WK_NO")
	private String ordrTakeBaseWkNo;

	public MstWklyOrdrTakeBasePK() {
	}
	public String getPor() {
		return this.por;
	}
	public void setPor(String por) {
		this.por = por;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}
	public String getOrdrTakeBaseWkNo() {
		return this.ordrTakeBaseWkNo;
	}
	public void setOrdrTakeBaseWkNo(String ordrTakeBaseWkNo) {
		this.ordrTakeBaseWkNo = ordrTakeBaseWkNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstWklyOrdrTakeBasePK)) {
			return false;
		}
		MstWklyOrdrTakeBasePK castOther = (MstWklyOrdrTakeBasePK)other;
		return 
			this.por.equals(castOther.por)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.ordrTakeBaseWkNo.equals(castOther.ordrTakeBaseWkNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.por.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.ordrTakeBaseWkNo.hashCode();
		
		return hash;
	}
}