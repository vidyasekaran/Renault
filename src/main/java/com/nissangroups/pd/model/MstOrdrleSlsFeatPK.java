package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OSEI_FEAT database table.
 * 
 */
@Embeddable
public class MstOrdrleSlsFeatPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="OSEIF_ADPT_DATE")
	private String oseifAdptDate;

	@Column(name="OSEIF_ABLSH_DATE")
	private String oseifAblshDate;

	public MstOrdrleSlsFeatPK() {
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
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
	public String getOseifAdptDate() {
		return this.oseifAdptDate;
	}
	public void setOseifAdptDate(String oseifAdptDate) {
		this.oseifAdptDate = oseifAdptDate;
	}
	public String getOseifAblshDate() {
		return this.oseifAblshDate;
	}
	public void setOseifAblshDate(String oseifAblshDate) {
		this.oseifAblshDate = oseifAblshDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOrdrleSlsFeatPK)) {
			return false;
		}
		MstOrdrleSlsFeatPK castOther = (MstOrdrleSlsFeatPK)other;
		return 
			this.oseiId.equals(castOther.oseiId)
			&& this.featCd.equals(castOther.featCd)
			&& this.featTypeCd.equals(castOther.featTypeCd)
			&& this.oseifAdptDate.equals(castOther.oseifAdptDate)
			&& this.oseifAblshDate.equals(castOther.oseifAblshDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.featTypeCd.hashCode();
		hash = hash * prime + this.oseifAdptDate.hashCode();
		hash = hash * prime + this.oseifAblshDate.hashCode();
		
		return hash;
	}
}