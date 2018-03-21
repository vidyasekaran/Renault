package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_WKLY_ORDR_TEMP database table.
 * 
 */
@Embeddable
public class TrnWklyOrdrTempPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POT_CD")
	private String potCd;

	public TrnWklyOrdrTempPK() {
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
	public String getProdWkNo() {
		return this.prodWkNo;
	}
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}
	public String getOseiId() {
		return this.oseiId;
	}
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}
	public String getPotCd() {
		return this.potCd;
	}
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnWklyOrdrTempPK)) {
			return false;
		}
		TrnWklyOrdrTempPK castOther = (TrnWklyOrdrTempPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.potCd.equals(castOther.potCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.potCd.hashCode();
		
		return hash;
	}
}