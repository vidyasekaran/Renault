package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_EX_NO database table.
 * 
 */
@Embeddable
public class MstExNoPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="POT_CD")
	private String potCd;

	public MstExNoPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}
	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
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
		if (!(other instanceof MstExNoPK)) {
			return false;
		}
		MstExNoPK castOther = (MstExNoPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.potCd.equals(castOther.potCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		
		return hash;
	}
}