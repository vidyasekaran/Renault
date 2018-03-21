package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OSEI_DTL database table.
 * 
 */
@Embeddable
public class MstOseiDtlPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="OSEI_ADPT_DATE")
	private String oseiAdptDate;

	@Column(name="OSEI_SUSPENDED_DATE")
	private String oseiSuspendedDate;

	@Column(name="OSEI_ABLSH_DATE")
	private String oseiAblshDate;

	public MstOseiDtlPK() {
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getOseiAdptDate() {
		return this.oseiAdptDate;
	}
	public void setOseiAdptDate(String oseiAdptDate) {
		this.oseiAdptDate = oseiAdptDate;
	}
	public String getOseiSuspendedDate() {
		return this.oseiSuspendedDate;
	}
	public void setOseiSuspendedDate(String oseiSuspendedDate) {
		this.oseiSuspendedDate = oseiSuspendedDate;
	}
	public String getOseiAblshDate() {
		return this.oseiAblshDate;
	}
	public void setOseiAblshDate(String oseiAblshDate) {
		this.oseiAblshDate = oseiAblshDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOseiDtlPK)) {
			return false;
		}
		MstOseiDtlPK castOther = (MstOseiDtlPK)other;
		return 
			this.oseiId.equals(castOther.oseiId)
			&& this.oseiAdptDate.equals(castOther.oseiAdptDate)
			&& this.oseiSuspendedDate.equals(castOther.oseiSuspendedDate)
			&& this.oseiAblshDate.equals(castOther.oseiAblshDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.oseiAdptDate.hashCode();
		hash = hash * prime + this.oseiSuspendedDate.hashCode();
		hash = hash * prime + this.oseiAblshDate.hashCode();
		
		return hash;
	}
}