package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_AUTO_DUE_DATE_PRMTR_IF database table.
 * 
 */
@Embeddable
public class TrnAutoDueDatePrmtrIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	public TrnAutoDueDatePrmtrIfPK() {
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
	public String getBuyerCd() {
		return this.buyerCd;
	}
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}
	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnAutoDueDatePrmtrIfPK)) {
			return false;
		}
		TrnAutoDueDatePrmtrIfPK castOther = (TrnAutoDueDatePrmtrIfPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.buyerCd.equals(castOther.buyerCd)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.carSrs.equals(castOther.carSrs);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		
		return hash;
	}
}