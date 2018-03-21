package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_SERV_PRMTR database table.
 * 
 */
@Embeddable
public class MstServPrmtrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="BUYER_CD_CNDTN")
	private String buyerCdCndtn;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="PROD_MNTH_FRM")
	private String prodMnthFrm;

	@Column(name="PROD_MNTH_TO")
	private String prodMnthTo;

	@Column(name="UNQUE_SEQ_CD")
	private String unqueSeqCd;

	public MstServPrmtrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdFmyCd() {
		return this.prodFmyCd;
	}
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}
	public String getBuyerCdCndtn() {
		return this.buyerCdCndtn;
	}
	public void setBuyerCdCndtn(String buyerCdCndtn) {
		this.buyerCdCndtn = buyerCdCndtn;
	}
	public String getSpecDestnCd() {
		return this.specDestnCd;
	}
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}
	public String getProdMnthFrm() {
		return this.prodMnthFrm;
	}
	public void setProdMnthFrm(String prodMnthFrm) {
		this.prodMnthFrm = prodMnthFrm;
	}
	public String getProdMnthTo() {
		return this.prodMnthTo;
	}
	public void setProdMnthTo(String prodMnthTo) {
		this.prodMnthTo = prodMnthTo;
	}
	public String getUnqueSeqCd() {
		return this.unqueSeqCd;
	}
	public void setUnqueSeqCd(String unqueSeqCd) {
		this.unqueSeqCd = unqueSeqCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstServPrmtrPK)) {
			return false;
		}
		MstServPrmtrPK castOther = (MstServPrmtrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodFmyCd.equals(castOther.prodFmyCd)
			&& this.buyerCdCndtn.equals(castOther.buyerCdCndtn)
			&& this.specDestnCd.equals(castOther.specDestnCd)
			&& this.prodMnthFrm.equals(castOther.prodMnthFrm)
			&& this.prodMnthTo.equals(castOther.prodMnthTo)
			&& this.unqueSeqCd.equals(castOther.unqueSeqCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodFmyCd.hashCode();
		hash = hash * prime + this.buyerCdCndtn.hashCode();
		hash = hash * prime + this.specDestnCd.hashCode();
		hash = hash * prime + this.prodMnthFrm.hashCode();
		hash = hash * prime + this.prodMnthTo.hashCode();
		hash = hash * prime + this.unqueSeqCd.hashCode();
		
		return hash;
	}
}