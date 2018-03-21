package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_BUYER_GRP_MNTHLY_OCF_LMT database table.
 * 
 */
@Embeddable
public class TrnBuyerGrpMnthlyOcfLmtPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="FEAT_CD")
	private String featCd;

	public TrnBuyerGrpMnthlyOcfLmtPK() {
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
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnBuyerGrpMnthlyOcfLmtPK)) {
			return false;
		}
		TrnBuyerGrpMnthlyOcfLmtPK castOther = (TrnBuyerGrpMnthlyOcfLmtPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.buyerGrpCd.equals(castOther.buyerGrpCd)
			&& this.featCd.equals(castOther.featCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.buyerGrpCd.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		
		return hash;
	}
}