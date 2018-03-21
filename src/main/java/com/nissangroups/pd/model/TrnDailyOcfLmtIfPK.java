package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_DAILY_OCF_LMT_IF database table.
 * 
 */
@Embeddable
public class TrnDailyOcfLmtIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="OCF_FRME_SRT_CD")
	private String ocfFrmeSrtCd;

	@Column(name="OCF_MDL_GRP")
	private String ocfMdlGrp;

	@Column(name="OCF_IDNTCTN_CODE")
	private String ocfIdntctnCode;

	public TrnDailyOcfLmtIfPK() {
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
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	public String getLineClass() {
		return this.lineClass;
	}
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}
	public String getOcfFrmeSrtCd() {
		return this.ocfFrmeSrtCd;
	}
	public void setOcfFrmeSrtCd(String ocfFrmeSrtCd) {
		this.ocfFrmeSrtCd = ocfFrmeSrtCd;
	}
	public String getOcfMdlGrp() {
		return this.ocfMdlGrp;
	}
	public void setOcfMdlGrp(String ocfMdlGrp) {
		this.ocfMdlGrp = ocfMdlGrp;
	}
	public String getOcfIdntctnCode() {
		return this.ocfIdntctnCode;
	}
	public void setOcfIdntctnCode(String ocfIdntctnCode) {
		this.ocfIdntctnCode = ocfIdntctnCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnDailyOcfLmtIfPK)) {
			return false;
		}
		TrnDailyOcfLmtIfPK castOther = (TrnDailyOcfLmtIfPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.lineClass.equals(castOther.lineClass)
			&& this.ocfFrmeSrtCd.equals(castOther.ocfFrmeSrtCd)
			&& this.ocfMdlGrp.equals(castOther.ocfMdlGrp)
			&& this.ocfIdntctnCode.equals(castOther.ocfIdntctnCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.lineClass.hashCode();
		hash = hash * prime + this.ocfFrmeSrtCd.hashCode();
		hash = hash * prime + this.ocfMdlGrp.hashCode();
		hash = hash * prime + this.ocfIdntctnCode.hashCode();
		
		return hash;
	}
}