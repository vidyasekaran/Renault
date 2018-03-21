package com.nissangroups.pd.b000031.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class B000031InputMapperPK implements Serializable{
	
	@Column(name="POR")
	private String POR_CD;
	
	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ORDR_TAKE_BASE_MNTH;
	
	@Column(name="ORDR_TAKE_BASE_WK_NO")
	private String ORDR_TAKE_BASE_WK_NO;

	public String getPOR_CD() {
		return POR_CD;
	}

	public void setPOR_CD(String pOR_CD) {
		POR_CD = pOR_CD;
	}

	public String getORDR_TAKE_BASE_MNTH() {
		return ORDR_TAKE_BASE_MNTH;
	}

	public void setORDR_TAKE_BASE_MNTH(String oRDR_TAKE_BASE_MNTH) {
		ORDR_TAKE_BASE_MNTH = oRDR_TAKE_BASE_MNTH;
	}

	public String getORDR_TAKE_BASE_WK_NO() {
		return ORDR_TAKE_BASE_WK_NO;
	}

	public void setORDR_TAKE_BASE_WK_NO(String oRDR_TAKE_BASE_WK_NO) {
		ORDR_TAKE_BASE_WK_NO = oRDR_TAKE_BASE_WK_NO;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof B000031InputMapperPK)) {
			return false;
		}
		B000031InputMapperPK castOther = (B000031InputMapperPK)other;
		
		return this.POR_CD.equals(castOther.getPOR_CD()) &&
				this.ORDR_TAKE_BASE_MNTH.equals(castOther.getORDR_TAKE_BASE_MNTH()) &&
				this.ORDR_TAKE_BASE_WK_NO.equals(castOther.getORDR_TAKE_BASE_WK_NO());
		
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		hash = hash * prime + this.POR_CD.hashCode();
		hash = hash * prime + this.ORDR_TAKE_BASE_MNTH.hashCode();
		hash = hash * prime + this.ORDR_TAKE_BASE_WK_NO.hashCode();
		
		return hash;
	}	

}
