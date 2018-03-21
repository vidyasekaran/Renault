package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_PROD_TYPE database table.
 * 
 */
@Embeddable
public class MstProdTypePK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="CAR_SRS_PRITY_CD")
	private String carSrsPrityCd;

	public MstProdTypePK() {
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
	public String getProdFmyCd() {
		return this.prodFmyCd;
	}
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}
	public String getProdPlntCd() {
		return this.prodPlntCd;
	}
	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}
	public String getCarSrsPrityCd() {
		return this.carSrsPrityCd;
	}
	public void setCarSrsPrityCd(String carSrsPrityCd) {
		this.carSrsPrityCd = carSrsPrityCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstProdTypePK)) {
			return false;
		}
		MstProdTypePK castOther = (MstProdTypePK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.prodFmyCd.equals(castOther.prodFmyCd)
			&& this.prodPlntCd.equals(castOther.prodPlntCd)
			&& this.carSrsPrityCd.equals(castOther.carSrsPrityCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.prodFmyCd.hashCode();
		hash = hash * prime + this.prodPlntCd.hashCode();
		hash = hash * prime + this.carSrsPrityCd.hashCode();
		
		return hash;
	}
}