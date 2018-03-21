package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_AUTO_DUE_DATE_PTTRN database table.
 * 
 */
@Embeddable
public class MstAutoDueDatePttrnPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="DUE_DATE_PTTRN_CD")
	private String dueDatePttrnCd;

	public MstAutoDueDatePttrnPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getDueDatePttrnCd() {
		return this.dueDatePttrnCd;
	}
	public void setDueDatePttrnCd(String dueDatePttrnCd) {
		this.dueDatePttrnCd = dueDatePttrnCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstAutoDueDatePttrnPK)) {
			return false;
		}
		MstAutoDueDatePttrnPK castOther = (MstAutoDueDatePttrnPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.dueDatePttrnCd.equals(castOther.dueDatePttrnCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.dueDatePttrnCd.hashCode();
		
		return hash;
	}
}