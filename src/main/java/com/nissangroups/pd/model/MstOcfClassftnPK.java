package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OCF_CLASSFTN database table.
 * 
 */
@Embeddable
public class MstOcfClassftnPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="OCF_PRITY_CD")
	private String ocfPrityCd;

	@Column(name="OCF_MODEL_GRP")
	private String ocfModelGrp;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="SHRT_DESC")
	private String shrtDesc;

	@Column(name="OCF_FRME_SRT_CD")
	private String ocfFrmeSrtCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="POR_CD")
	private String porCd;

	public MstOcfClassftnPK() {
	}
	public String getOcfPrityCd() {
		return this.ocfPrityCd;
	}
	public void setOcfPrityCd(String ocfPrityCd) {
		this.ocfPrityCd = ocfPrityCd;
	}
	public String getOcfModelGrp() {
		return this.ocfModelGrp;
	}
	public void setOcfModelGrp(String ocfModelGrp) {
		this.ocfModelGrp = ocfModelGrp;
	}
	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}
	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}
	public String getShrtDesc() {
		return this.shrtDesc;
	}
	public void setShrtDesc(String shrtDesc) {
		this.shrtDesc = shrtDesc;
	}
	public String getOcfFrmeSrtCd() {
		return this.ocfFrmeSrtCd;
	}
	public void setOcfFrmeSrtCd(String ocfFrmeSrtCd) {
		this.ocfFrmeSrtCd = ocfFrmeSrtCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOcfClassftnPK)) {
			return false;
		}
		MstOcfClassftnPK castOther = (MstOcfClassftnPK)other;
		return 
			this.ocfPrityCd.equals(castOther.ocfPrityCd)
			&& this.ocfModelGrp.equals(castOther.ocfModelGrp)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
			&& this.shrtDesc.equals(castOther.shrtDesc)
			&& this.ocfFrmeSrtCd.equals(castOther.ocfFrmeSrtCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.porCd.equals(castOther.porCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ocfPrityCd.hashCode();
		hash = hash * prime + this.ocfModelGrp.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.shrtDesc.hashCode();
		hash = hash * prime + this.ocfFrmeSrtCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.porCd.hashCode();
		
		return hash;
	}
}