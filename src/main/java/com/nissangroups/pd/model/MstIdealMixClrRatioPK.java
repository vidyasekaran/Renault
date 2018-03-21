package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_IDEAL_MIX_CLR_RATIO database table.
 * 
 */
@Embeddable
public class MstIdealMixClrRatioPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	public MstIdealMixClrRatioPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
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
	public String getExtClrCd() {
		return this.extClrCd;
	}
	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}
	public String getIntClrCd() {
		return this.intClrCd;
	}
	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstIdealMixClrRatioPK)) {
			return false;
		}
		MstIdealMixClrRatioPK castOther = (MstIdealMixClrRatioPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.buyerGrpCd.equals(castOther.buyerGrpCd)
			&& this.extClrCd.equals(castOther.extClrCd)
			&& this.intClrCd.equals(castOther.intClrCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.buyerGrpCd.hashCode();
		hash = hash * prime + this.extClrCd.hashCode();
		hash = hash * prime + this.intClrCd.hashCode();
		
		return hash;
	}
}