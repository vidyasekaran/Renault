package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_POR_CAR_SRS database table.
 * 
 */
@Embeddable
public class MstPorCarSrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD", insertable=false, updatable=false)
	private String porCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	public MstPorCarSrPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdFmyCd() {
		return this.prodFmyCd;
	}
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstPorCarSrPK)) {
			return false;
		}
		MstPorCarSrPK castOther = (MstPorCarSrPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodFmyCd.equals(castOther.prodFmyCd)
			&& this.carSrs.equals(castOther.carSrs);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodFmyCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		
		return hash;
	}
}