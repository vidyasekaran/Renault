package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OSEI_FRZN database table.
 * 
 */
@Embeddable
public class MstOseiFrznPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD", insertable=false, updatable=false)
	private String porCd;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="FRZN_ORDR_TAKE_BASE_MNTH", insertable=false, updatable=false)
	private String frznOrdrTakeBaseMnth;

	@Column(name="FRZN_PROD_MNTH", insertable=false, updatable=false)
	private String frznProdMnth;

	@Column(name="CAR_SRS", insertable=false, updatable=false)
	private String carSrs;

	@Column(name="OCF_REGION_CD", insertable=false, updatable=false)
	private String ocfRegionCd;

	@Column(name="FRZN_PRITY_CD", insertable=false, updatable=false)
	private String frznPrityCd;

	public MstOseiFrznPK() {
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
	public String getFrznOrdrTakeBaseMnth() {
		return this.frznOrdrTakeBaseMnth;
	}
	public void setFrznOrdrTakeBaseMnth(String frznOrdrTakeBaseMnth) {
		this.frznOrdrTakeBaseMnth = frznOrdrTakeBaseMnth;
	}
	public String getFrznProdMnth() {
		return this.frznProdMnth;
	}
	public void setFrznProdMnth(String frznProdMnth) {
		this.frznProdMnth = frznProdMnth;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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
		if (!(other instanceof MstOseiFrznPK)) {
			return false;
		}
		MstOseiFrznPK castOther = (MstOseiFrznPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.oseiId.equals(castOther.oseiId)
			&& this.frznOrdrTakeBaseMnth.equals(castOther.frznOrdrTakeBaseMnth)
			&& this.frznProdMnth.equals(castOther.frznProdMnth)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.frznPrityCd.equals(castOther.frznPrityCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.oseiId.hashCode();
		hash = hash * prime + this.frznOrdrTakeBaseMnth.hashCode();
		hash = hash * prime + this.frznProdMnth.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.frznPrityCd.hashCode();
		
		return hash;
	}
}