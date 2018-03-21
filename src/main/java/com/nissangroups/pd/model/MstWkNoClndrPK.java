package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_WK_NO_CLNDR database table.
 * 
 */
@Embeddable
public class MstWkNoClndrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	public MstWkNoClndrPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstWkNoClndrPK)) {
			return false;
		}
		MstWkNoClndrPK castOther = (MstWkNoClndrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		
		return hash;
	}
}