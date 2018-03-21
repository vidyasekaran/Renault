package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OSEI_PROD_TYPE database table.
 * 
 */
@Embeddable
public class MstOseiProdTypePK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	public MstOseiProdTypePK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
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
	public String getProdWkNo() {
		return this.prodWkNo;
	}
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOseiProdTypePK)) {
			return false;
		}
		MstOseiProdTypePK castOther = (MstOseiProdTypePK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		
		return hash;
	}
}