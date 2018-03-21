package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_PLNT_CD database table.
 * 
 */
@Embeddable
public class MstPlntCdPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PLNT_CD")
	private String plntCd;

	@Column(name="POR_CD")
	private String porCd;

	public MstPlntCdPK() {
	}
	public String getPlntCd() {
		return this.plntCd;
	}
	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstPlntCdPK)) {
			return false;
		}
		MstPlntCdPK castOther = (MstPlntCdPK)other;
		return 
			this.plntCd.equals(castOther.plntCd)
			&& this.porCd.equals(castOther.porCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.plntCd.hashCode();
		hash = hash * prime + this.porCd.hashCode();
		
		return hash;
	}
}