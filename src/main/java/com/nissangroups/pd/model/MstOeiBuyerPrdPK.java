package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OEI_BUYER_PRD database table.
 * 
 */
@Embeddable
public class MstOeiBuyerPrdPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="EIM_MIN_ADPT_DATE")
	private String eimMinAdptDate;

	@Column(name="EIM_MAX_ABLSH_DATE")
	private String eimMaxAblshDate;

	public MstOeiBuyerPrdPK() {
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}
	public String getEimMinAdptDate() {
		return this.eimMinAdptDate;
	}
	public void setEimMinAdptDate(String eimMinAdptDate) {
		this.eimMinAdptDate = eimMinAdptDate;
	}
	public String getEimMaxAblshDate() {
		return this.eimMaxAblshDate;
	}
	public void setEimMaxAblshDate(String eimMaxAblshDate) {
		this.eimMaxAblshDate = eimMaxAblshDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOeiBuyerPrdPK)) {
			return false;
		}
		MstOeiBuyerPrdPK castOther = (MstOeiBuyerPrdPK)other;
		return 
			this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.eimMinAdptDate.equals(castOther.eimMinAdptDate)
			&& this.eimMaxAblshDate.equals(castOther.eimMaxAblshDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.eimMinAdptDate.hashCode();
		hash = hash * prime + this.eimMaxAblshDate.hashCode();
		
		return hash;
	}
}