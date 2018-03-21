package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_WKLY_PROD_SHDL database table.
 * 
 */
@Embeddable
public class TrnWklyProdShdlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="ORDR_TAKE_BASE_WK_NO")
	private String ordrTakeBaseWkNo;

	@Column(name="SEQ_ID")
	private String seqId;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	public TrnWklyProdShdlPK() {
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
	public String getOrdrTakeBaseWkNo() {
		return this.ordrTakeBaseWkNo;
	}
	public void setOrdrTakeBaseWkNo(String ordrTakeBaseWkNo) {
		this.ordrTakeBaseWkNo = ordrTakeBaseWkNo;
	}
	public String getSeqId() {
		return this.seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
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
		if (!(other instanceof TrnWklyProdShdlPK)) {
			return false;
		}
		TrnWklyProdShdlPK castOther = (TrnWklyProdShdlPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.ordrTakeBaseWkNo.equals(castOther.ordrTakeBaseWkNo)
			&& this.seqId.equals(castOther.seqId)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.ordrTakeBaseWkNo.hashCode();
		hash = hash * prime + this.seqId.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		
		return hash;
	}
}