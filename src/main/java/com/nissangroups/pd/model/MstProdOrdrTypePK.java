package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_PROD_ORDR_TYPE database table.
 * 
 */
@Embeddable
public class MstProdOrdrTypePK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	public MstProdOrdrTypePK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getPotCd() {
		return this.potCd;
	}
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstProdOrdrTypePK)) {
			return false;
		}
		MstProdOrdrTypePK castOther = (MstProdOrdrTypePK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.potCd.equals(castOther.potCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		
		return hash;
	}
}