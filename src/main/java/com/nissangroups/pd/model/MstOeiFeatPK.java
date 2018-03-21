package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OEI_FEAT database table.
 * 
 */
@Embeddable
public class MstOeiFeatPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="OEIF_ADPT_DATE")
	private String oeifAdptDate;

	@Column(name="OEIF_ABLSH_DATE")
	private String oeifAblshDate;

	public MstOeiFeatPK() {
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}
	public String getFeatTypeCd() {
		return this.featTypeCd;
	}
	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
	}
	public String getOeifAdptDate() {
		return this.oeifAdptDate;
	}
	public void setOeifAdptDate(String oeifAdptDate) {
		this.oeifAdptDate = oeifAdptDate;
	}
	public String getOeifAblshDate() {
		return this.oeifAblshDate;
	}
	public void setOeifAblshDate(String oeifAblshDate) {
		this.oeifAblshDate = oeifAblshDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOeiFeatPK)) {
			return false;
		}
		MstOeiFeatPK castOther = (MstOeiFeatPK)other;
		return 
			this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.featCd.equals(castOther.featCd)
			&& this.featTypeCd.equals(castOther.featTypeCd)
			&& this.oeifAdptDate.equals(castOther.oeifAdptDate)
			&& this.oeifAblshDate.equals(castOther.oeifAblshDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.featTypeCd.hashCode();
		hash = hash * prime + this.oeifAdptDate.hashCode();
		hash = hash * prime + this.oeifAblshDate.hashCode();
		
		return hash;
	}
}