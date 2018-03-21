package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_BUYER_WKLY_OCF_USG database table.
 * 
 */
@Embeddable
public class TrnBuyerWklyOcfUsgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="POR_CD")
	private String porCd;

	public TrnBuyerWklyOcfUsgPK() {
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
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
		if (!(other instanceof TrnBuyerWklyOcfUsgPK)) {
			return false;
		}
		TrnBuyerWklyOcfUsgPK castOther = (TrnBuyerWklyOcfUsgPK)other;
		return 
			this.featCd.equals(castOther.featCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.prodWkNo.equals(castOther.prodWkNo)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.porCd.equals(castOther.porCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.porCd.hashCode();
		
		return hash;
	}
}