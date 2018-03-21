package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OSEI database table.
 * 
 */
@Embeddable
public class MstOseiPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	public MstOseiPK() {
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}
	public String getExtClrCd() {
		return this.extClrCd;
	}
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}
	public String getIntClrCd() {
		return this.intClrCd;
	}
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOseiPK)) {
			return false;
		}
		MstOseiPK castOther = (MstOseiPK)other;
		return 
			this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.extClrCd.equals(castOther.extClrCd)
			&& this.intClrCd.equals(castOther.intClrCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.extClrCd.hashCode();
		hash = hash * prime + this.intClrCd.hashCode();
		
		return hash;
	}
}