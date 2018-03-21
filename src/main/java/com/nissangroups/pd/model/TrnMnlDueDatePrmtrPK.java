package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNL_DUE_DATE_PRMTR database table.
 * 
 */
@Embeddable
public class TrnMnlDueDatePrmtrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="POT_CD")
	private String potCd;

	public TrnMnlDueDatePrmtrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
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
		if (!(other instanceof TrnMnlDueDatePrmtrPK)) {
			return false;
		}
		TrnMnlDueDatePrmtrPK castOther = (TrnMnlDueDatePrmtrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.oeiBuyerId.equals(castOther.oeiBuyerId)
			&& this.potCd.equals(castOther.potCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.oeiBuyerId.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		
		return hash;
	}
}