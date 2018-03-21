/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */  
package com.nissangroups.pd.b000027.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OrdrTkBsPrdMstRowMapperPK implements Serializable{
	
	/** Variable por cd. */
	@Column(name="POR_CD")
	private String POR_CD;
	
	/** Variable Order take base month */
	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ORDR_TAKE_BASE_MNTH;

	/**
	 * Gets the por cd
	 *
	 * @return the por cd
	 */
	public String getPOR_CD() {
		return POR_CD;
	}


	/**
	 * Sets the por cd
	 *
	 * @param pOR_CD the pOR_CD to set
	 */
	public void setPOR_CD(String pOR_CD) {
		POR_CD = pOR_CD;
	}


	/**
	 * Gets the ordrTakeBaseMonth
	 *
	 * @return the oRDR_TAKE_BASE_MNTH
	 */
	public String getORDR_TAKE_BASE_MNTH() {
		return ORDR_TAKE_BASE_MNTH;
	}

	/**
	 * Sets the ordrTakeBaseMonth
	 *
	 * @param oRDR_TAKE_BASE_MNTH the ordrTakeBaseMonth to set
	 */
	public void setORDR_TAKE_BASE_MNTH(String oRDR_TAKE_BASE_MNTH) {
		ORDR_TAKE_BASE_MNTH = oRDR_TAKE_BASE_MNTH;
	}
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrdrTkBsPrdMstRowMapperPK)) {
			return false;
		}
		OrdrTkBsPrdMstRowMapperPK castOther = (OrdrTkBsPrdMstRowMapperPK)other;
		
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
