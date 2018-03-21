/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class B000014InputMapperPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String POR_CD;
	
	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ORDR_TAKE_BASE_MNTH;

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
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof B000014InputMapperPK)) {
			return false;
		}
		B000014InputMapperPK castOther = (B000014InputMapperPK)other;
		
		return this.POR_CD.equals(castOther.getPOR_CD()) &&
				this.ORDR_TAKE_BASE_MNTH.equals(castOther.getORDR_TAKE_BASE_MNTH());
		
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		hash = hash * prime + this.POR_CD.hashCode();
		hash = hash * prime + this.ORDR_TAKE_BASE_MNTH.hashCode();
		
		return hash;
	}	

}
