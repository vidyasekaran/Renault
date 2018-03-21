package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNTHLY_OCF database table.
 * 
 */
@Embeddable
public class TrnMnthlyOcfPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="FRME_SRT_CD")
	private String frmeSrtCd;

	@Column(name="OCF_MDL_GRP")
	private String ocfMdlGrp;

	@Column(name="OCF_IDNTCTN_CD")
	private String ocfIdntctnCd;

	public TrnMnthlyOcfPK() {
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
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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
	public String getOcfMdlGrp() {
		return this.ocfMdlGrp;
	}
	public void setOcfMdlGrp(String ocfMdlGrp) {
		this.ocfMdlGrp = ocfMdlGrp;
	}
	public String getOcfIdntctnCd() {
		return this.ocfIdntctnCd;
	}
	public void setOcfIdntctnCd(String ocfIdntctnCd) {
		this.ocfIdntctnCd = ocfIdntctnCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnMnthlyOcfPK)) {
			return false;
		}
		TrnMnthlyOcfPK castOther = (TrnMnthlyOcfPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrtkBaseMnth.equals(castOther.ordrtkBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
			&& this.frmeSrtCd.equals(castOther.frmeSrtCd)
			&& this.ocfMdlGrp.equals(castOther.ocfMdlGrp)
			&& this.ocfIdntctnCd.equals(castOther.ocfIdntctnCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrtkBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.frmeSrtCd.hashCode();
		hash = hash * prime + this.ocfMdlGrp.hashCode();
		hash = hash * prime + this.ocfIdntctnCd.hashCode();
		
		return hash;
	}
}