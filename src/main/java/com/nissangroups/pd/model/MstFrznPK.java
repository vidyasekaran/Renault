package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_FRZN database table.
 * 
 */
@Embeddable
public class MstFrznPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="FRZN_TYPE_CD", insertable=false, updatable=false)
	private String frznTypeCd;

	@Column(name="FRZN_PROD_MNTH")
	private String frznProdMnth;

	@Column(name="FRZN_ORDR_TAKE_BASE_MNTH")
	private String frznOrdrTakeBaseMnth;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="FRZN_PRITY_CD")
	private String frznPrityCd;

	public MstFrznPK() {
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
	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}
	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}
	public String getFrznProdMnth() {
		return this.frznProdMnth;
	}
	public void setFrznProdMnth(String frznProdMnth) {
		this.frznProdMnth = frznProdMnth;
	}
	public String getFrznOrdrTakeBaseMnth() {
		return this.frznOrdrTakeBaseMnth;
	}
	public void setFrznOrdrTakeBaseMnth(String frznOrdrTakeBaseMnth) {
		this.frznOrdrTakeBaseMnth = frznOrdrTakeBaseMnth;
	}
	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}
	public String getFrznPrityCd() {
		return this.frznPrityCd;
	}
	public void setFrznPrityCd(String frznPrityCd) {
		this.frznPrityCd = frznPrityCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstFrznPK)) {
			return false;
		}
		MstFrznPK castOther = (MstFrznPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.frznTypeCd.equals(castOther.frznTypeCd)
			&& this.frznProdMnth.equals(castOther.frznProdMnth)
			&& this.frznOrdrTakeBaseMnth.equals(castOther.frznOrdrTakeBaseMnth)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.frznPrityCd.equals(castOther.frznPrityCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.frznTypeCd.hashCode();
		hash = hash * prime + this.frznProdMnth.hashCode();
		hash = hash * prime + this.frznOrdrTakeBaseMnth.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.frznPrityCd.hashCode();
		
		return hash;
	}
}