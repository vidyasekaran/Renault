package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_MNTHLY_ORDR_TAKE_LCK database table.
 * 
 */
@Embeddable
public class MstMnthlyOrdrTakeLckPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="PROD_ORDR_STAGE_CD")
	private String prodOrdrStageCd;

	public MstMnthlyOrdrTakeLckPK() {
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
	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getProdOrdrStageCd() {
		return this.prodOrdrStageCd;
	}
	public void setProdOrdrStageCd(String prodOrdrStageCd) {
		this.prodOrdrStageCd = prodOrdrStageCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstMnthlyOrdrTakeLckPK)) {
			return false;
		}
		MstMnthlyOrdrTakeLckPK castOther = (MstMnthlyOrdrTakeLckPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.buyerGrpCd.equals(castOther.buyerGrpCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.prodOrdrStageCd.equals(castOther.prodOrdrStageCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.buyerGrpCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.prodOrdrStageCd.hashCode();
		
		return hash;
	}
}