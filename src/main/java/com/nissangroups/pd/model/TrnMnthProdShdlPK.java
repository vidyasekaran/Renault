package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNTH_PROD_SHDL database table.
 * 
 */
@Embeddable
public class TrnMnthProdShdlPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="SEQ_ID")
	private String seqId;

	public TrnMnthProdShdlPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrtkBaseMnth() {
		return this.ordrtkBaseMnth;
	}
	public void setOrdrtkBaseMnth(String ordrtkBaseMnth) {
		this.ordrtkBaseMnth = ordrtkBaseMnth;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getSeqId() {
		return this.seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnMnthProdShdlPK)) {
			return false;
		}
		TrnMnthProdShdlPK castOther = (TrnMnthProdShdlPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrtkBaseMnth.equals(castOther.ordrtkBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.seqId.equals(castOther.seqId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrtkBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.seqId.hashCode();
		
		return hash;
	}
}