package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNTHLY_OCF_IF database table.
 * 
 */
@Embeddable
public class TrnMnthlyOcfIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="CAR_SERIES")
	private String carSeries;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="FRME_SRT_CD")
	private String frmeSrtCd;

	public TrnMnthlyOcfIfPK() {
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
	public String getCarSeries() {
		return this.carSeries;
	}
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}
	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}
	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}
	public String getFrmeSrtCd() {
		return this.frmeSrtCd;
	}
	public void setFrmeSrtCd(String frmeSrtCd) {
		this.frmeSrtCd = frmeSrtCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnMnthlyOcfIfPK)) {
			return false;
		}
		TrnMnthlyOcfIfPK castOther = (TrnMnthlyOcfIfPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.carSeries.equals(castOther.carSeries)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
			&& this.frmeSrtCd.equals(castOther.frmeSrtCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.carSeries.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.frmeSrtCd.hashCode();
		
		return hash;
	}
}