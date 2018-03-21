package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_LTST_MST_SHDL database table.
 * 
 */
@Embeddable
public class TrnLtstMstShdlPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="POR_CD")
	private String porCd;

	public TrnLtstMstShdlPK() {
	}
	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}
	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}
	public String getPotCd() {
		return this.potCd;
	}
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getProdWkNo() {
		return this.prodWkNo;
	}
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
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
		if (!(other instanceof TrnLtstMstShdlPK)) {
			return false;
		}
		TrnLtstMstShdlPK castOther = (TrnLtstMstShdlPK)other;
		return 
			this.prodOrdrNo.equals(castOther.prodOrdrNo)
			&& this.potCd.equals(castOther.potCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.prodWkNo.equals(castOther.prodWkNo)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.porCd.equals(castOther.porCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.prodOrdrNo.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.porCd.hashCode();
		
		return hash;
	}
}